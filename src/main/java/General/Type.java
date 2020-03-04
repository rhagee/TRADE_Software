package General;

import Data.Item;
import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.HTMLParser;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import com.gargoylesoftware.htmlunit.util.NameValuePair;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Type
{
    String typename;
    String research;
    public Type(String typename)
    {
        this.typename=typename;
    }
    public Type(String typename,String research)
    {
        this.typename=typename;
        this.research=research;
    }
    public String getTypename()
    {
        return this.typename;
    }

    public List<String> getItemsNames(String filter) throws IOException {
        List<String> items = new ArrayList<>();

        if(typename.equals("Amazon") || typename.equals("amazon"))
        {
            Document doc = DocGenerator.Amazon(filter);
            Elements names = doc.select("span.a-size-medium").select("span.a-color-base").select("span.a-text-normal");
            names.addAll(doc.select("span.a-size-base-plus").select("span.a-color-base").select("span.a-text-normal"));
            for(Element name : names)
            {
                items.add(name.html());
            }
        }
        else
        {
            return null;
        }

        return items;
    }


    public List<String> getItemsNames() throws IOException {
        List<String> items = new ArrayList();

        /*AMAZON*/
        if(typename.equals("Amazon") || typename.equals("amazon") || typename.equals("AMAZON"))
        {
            Document doc = DocGenerator.Amazon(research);
            Elements names = doc.select("span.a-size-medium").select("span.a-color-base").select("span.a-text-normal");
            names.addAll(doc.select("span.a-size-base-plus").select("span.a-color-base").select("span.a-text-normal"));
            for(Element name : names)
            {
                items.add(name.html());
            }
        }

        /*EBAY*/
        else if(typename.equals("EBay") || typename.equals("ebay") || typename.equals("Ebay") || typename.equals("eBay") || typename.equals("EBAY"))
        {
            Document doc = DocGenerator.EBay(research);
            Elements names = doc.select("h3.lvtitle").select("a.vip");
            for(Element name : names)
            {
                items.add(name.html());
            }
        }

        /*Subito*/
        else if(typename.equals("subito") || typename.equals("Subito") || typename.equals("subito.it") ||typename.equals("SUBITO"))
        {
            Document doc = DocGenerator.EBay(research);
            Elements names = doc.select("h3.lvtitle").select("a.vip");
            for(Element name : names)
            {
                items.add(name.html());
            }
        }

        /*KIJIJI*/
        else if(typename.equals("Kijiji") || typename.equals("KIJIJI") || typename.equals("K"))
        {
            Document doc = DocGenerator.Kijiji(research);
            Elements names = doc.select("a.cta");
            for(Element name : names)
            {
                items.add(name.html());
            }
        }

        /*REBUY*/
        else if(typename.equals("ReBuy") || typename.equals("rebuy") || typename.equals("REBUY"))
        {
            Document doc = DocGenerator.Kijiji(research);
            Elements names = doc.select("div.ry-product-item-content__name");
            for(Element name : names)
            {
                items.add(name.html());
            }
        }
        /*FACEBOOK*/
        else if(typename.equals("facebook") || typename.equals("Facebook") || typename.equals("FB") ||typename.equals("fb") || typename.equals("facebook marketplace") ||typename.equals("fb marketplace") || typename.equals("FACEBOOK"))
        {
            Document doc = DocGenerator.Facebook(research);
            Elements names = doc.select("p.l9j0dhe7").select("p.stjgntxs").select("p.ni8dbmo4");
            for(Element name : names)
            {
                items.add(name.html());
            }
        }
        else
        {
            return null;
        }

        return items;
    }


    /*Complete itemList*/
    public List<Item> getItems() throws IOException {

        List<Item> items = new ArrayList<>();

        /*AMAZON*/
        if(typename.equals("Amazon") || typename.equals("amazon"))
        {
            Document doc = DocGenerator.Amazon(research);

            Elements names = doc.select("span.a-size-medium")
                                .select("span.a-color-base")
                                .select("span.a-text-normal");
            names.addAll(doc.select("span.a-size-base-plus")
                            .select("span.a-color-base")
                            .select("span.a-text-normal"));
            Elements prices = doc.select("span.a-price")
                                 .not("span.a-text-price")
                                 .select("span.a-price-whole");
            Elements symbols = doc.select("span.a-price")
                                  .select("span.a-price-symbol");
            Elements links = doc.select("a.a-link-normal")
                                .select("a.a-text-normal");

            for(int i=0;i<names.size() && i<prices.size();i++)
            {
                String fix_price = FixPrice(prices.get(i).html());
                char value=symbols.get(i).html().charAt(0);
                String link = "https://www.amazon.it/"+links.get(i).attr("href");
                items.add(new Item(names.get(i).html(),Float.parseFloat(fix_price),value,link));
            }
        }

        /*EBAY*/
        else if(typename.equals("EBay") || typename.equals("ebay") || typename.equals("Ebay") || typename.equals("eBay") || typename.equals("EBAY"))
        {
            Document doc = DocGenerator.EBay(research);

            Elements names = doc.select("h3.lvtitle")
                                .select("a.vip");
            Elements prices = doc.select("li.lvprice")
                                 .select("li.prc")
                                 .select("span.bold")
                                 .not("b");
            Elements links = doc.select("h3.lvtitle")
                                .select("a.vip");

            for(int i=0;i<names.size() && i<prices.size();i++)
            {
                String fix_price = FixPrice(prices.get(i).html());
                String fix_name = names.get(i).html().replace("<span class=\"newly\">Nuova inserzione</span>","");
                char value = '€';
                String link = links.get(i).attr("href");
                items.add(new Item(fix_name,Float.parseFloat(fix_price),value,link));
            }
        }

        /*Subito*/
        else if(typename.equals("subito") || typename.equals("Subito") || typename.equals("subito.it") ||typename.equals("SUBITO"))
        {
            Document doc = DocGenerator.Subito(research);

            Elements names = doc.select("h2.Atoms__TextAtom--sbt-text-atom-L2hvbWUv")
                                .select("h2.size-normal").select("h2.Atoms__TextAtom--weight-semibold-L2hvbWUv")
                                .select("h2.item-title").select("h2.jsx-837743620");

            Elements prices = doc.select("div.jsx-837743620")
                                 .select("div.feature-row")
                                 .select("div.AdElements__ItemRow--container-L2hvbWUv");
            Elements links = doc.select("a.jsx-837743620").select("a.link");

            for(int i=0;i<names.size() && i<prices.size();i++)
            {

                Elements singleprice = prices.get(i)
                        .select("h6.Atoms__TextAtom--sbt-text-atom-L2hvbWUv")
                        .select("h6.Atoms__TextAtom--token-h6-L2hvbWUv")
                        .select("h6.size-normal")
                        .select("h6.Atoms__TextAtom--weight-semibold-L2hvbWUv")
                        .select("h6.AdElements__ItemPrice--price-L2hvbWUv");

                String fix_price="0";
                char value = 'U';
                String link = links.get(i).attr("href");
                if(singleprice.size()>0)
                {
                    fix_price = FixPrice(prices.get(i).html());
                    value='€';
                }
                if(names.get(i).html().contains(research) || names.get(i).html().contains(research.substring(1)) || names.get(i).html().contains(research.substring(0,research.length()-1)))
                    items.add(new Item(names.get(i).html(),Float.parseFloat(fix_price),value,link));
            }
        }


        /*KIJIJI*/
        else if(typename.equals("Kijiji") || typename.equals("KIJIJI") || typename.equals("K"))
        {
            Document doc = DocGenerator.Kijiji(research);

            Elements names = doc.select("a.cta");
            Elements prices = doc.select("div.item-content")
                                 .select("h4.price");
            Elements links = doc.select("a.cta");

            for(int i=0;i<names.size() && i<prices.size();i++)
            {
                    String fix_price="0";
                    char value = 'U';
                    String link = links.get(i).attr("href");
                    if(!prices.get(i).html().contains("Contatta l'utente"))
                    {
                        fix_price = FixPrice(prices.get(i).html());
                        value=prices.get(i).html().charAt(prices.get(i).html().length()-1);
                    }
                    items.add(new Item(names.get(i).html(),Float.parseFloat(fix_price),value,link));
            }
        }

        /*REBUY*/
        else if(typename.equals("ReBuy") || typename.equals("rebuy") || typename.equals("REBUY"))
        {
            Document doc = DocGenerator.ReBuy(research);

            Elements names = doc.select("div.ry-product-item-content__name");
            Elements prices = doc.select("strong.ry-price__amount");
            Elements links = doc.select("a.center-block")
                                .select("a.ry-product-item__link");

            for(int i=0;i<names.size() && i<prices.size();i++)
            {
                String fix_price = FixPrice(prices.get(i).html());
                char value = '€';
                String link = "https://www.rebuy.it/"+links.get(i).attr("href");
                items.add(new Item(names.get(i).html(),Float.parseFloat(fix_price),value,link));
            }
        }

        /*Facebook ERRATA*/
        else if(typename.equals("facebook") || typename.equals("Facebook") || typename.equals("FB") ||typename.equals("fb") || typename.equals("facebook marketplace") ||typename.equals("fb marketplace") || typename.equals("FACEBOOK"))
        {
            Document doc = DocGenerator.Facebook(research);

            Elements names = doc.select("p.l9j0dhe7")
                                .select("p.stjgntxs")
                                .select("p.ni8dbmo4");
            Elements prices = doc.select("span.oi732d6d")
                                 .select("span.ik7dh3pa")
                                 .select("span.d2edcug0")
                                 .select("span.qv66sw1b")
                                 .select("span.c1et5uql")
                                 .select("span.a8c37x1j")
                                 .select("span.s89635nw")
                                 .select("span.ew0dbk1b")
                                 .select("span.a5q79mjw")
                                 .select("span.g1cxx5fr")
                                 .select("span.hnhda86s")
                                 .select("span.oo9gr5id");
            Elements links = doc.select("CANT");

            for(int i=0;i<names.size() && i<prices.size();i++)
            {
                String fix_price = FixPrice(prices.get(i).html());
                String link = links.get(i).attr("href");
                items.add(new Item(names.get(i).html(),Float.parseFloat(fix_price),'€',link));
            }
        }
        else
        {
            return null;
        }

        return items;
    }


    public String FixPrice(String input)
    {
        return input
                .replace(".","")
                .replace(",",".")
                .replace("€","")
                .replace("$","")
                .replace("&nbsp;","")
                .replace("&nbsp;","")
                .replace("<b>EUR</b>","")
                .replace("\"","");
    }


    public void setResearch(String research)
    {
        this.research=research;
    }
}
