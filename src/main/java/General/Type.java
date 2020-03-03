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

    public List<String> getItemsNames(String filter) throws IOException {
        List<String> items = new ArrayList();
        if(typename.equals("Amazon") || typename.equals("amazon"))
        {
            Document doc = Amazon(filter);
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
            Document doc = Amazon(research);
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
            Document doc = EBay(research);
            Elements names = doc.select("h3.lvtitle").select("a.vip");
            for(Element name : names)
            {
                items.add(name.html());
            }
        }

        /*Subito*/
        else if(typename.equals("subito") || typename.equals("Subito") || typename.equals("subito.it") ||typename.equals("SUBITO"))
        {
            Document doc = EBay(research);
            Elements names = doc.select("h3.lvtitle").select("a.vip");
            for(Element name : names)
            {
                items.add(name.html());
            }
        }

        /*Facebook*/
        else if(typename.equals("facebook") || typename.equals("Facebook") || typename.equals("FB") ||typename.equals("fb") || typename.equals("facebook marketplace") ||typename.equals("fb marketplace") || typename.equals("FACEBOOK"))
        {
            Document doc = Facebook(research);
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
        List<Item> items = new ArrayList();

        /*AMAZON*/
        if(typename.equals("Amazon") || typename.equals("amazon"))
        {
            Document doc = Amazon(research);
            Elements names = doc.select("span.a-size-medium").select("span.a-color-base").select("span.a-text-normal");
            names.addAll(doc.select("span.a-size-base-plus").select("span.a-color-base").select("span.a-text-normal"));
            Elements prices = doc.select("span.a-price").not("span.a-text-price").select("span.a-price-whole");
            Elements symbols = doc.select("span.a-price").select("span.a-price-symbol");
            for(int i=0;i<names.size() && i<prices.size();i++)
            {
                String fix_price = prices.get(i).html().replace(".","").replace(",",".").replace("&nbsp;€","").replace("&nbsp;$","");
                items.add(new Item(names.get(i).html(),Float.parseFloat(fix_price),symbols.get(i).html().charAt(0)));
            }
        }

        /*EBAY*/
        else if(typename.equals("EBay") || typename.equals("ebay") || typename.equals("Ebay") || typename.equals("eBay") || typename.equals("EBAY"))
        {
            Document doc = EBay(research);
            Elements names = doc.select("h3.lvtitle").select("a.vip");
            Elements prices = doc.select("li.lvprice").select("li.prc").select("span.bold").not("b");
            for(int i=0;i<names.size() && i<prices.size();i++)
            {
                String fix_price = prices.get(i).html().replace(".","").replace(",",".").replace("<b>EUR</b>","").replace("\"","");
                String fix_name = names.get(i).html().replace("<span class=\"newly\">Nuova inserzione</span>","");
                items.add(new Item(fix_name,Float.parseFloat(fix_price),'€'));
            }
        }

        /*Subito*/
        else if(typename.equals("subito") || typename.equals("Subito") || typename.equals("subito.it") ||typename.equals("SUBITO"))
        {
            Document doc = Subito(research);
            Elements names = doc.select("h2.Atoms__TextAtom--sbt-text-atom-L2hvbWUv").select("h2.size-normal").select("h2.Atoms__TextAtom--weight-semibold-L2hvbWUv").select("h2.item-title").select("h2.jsx-837743620");
            Elements prices = doc.select("div.jsx-837743620").select("div.feature-row").select("div.AdElements__ItemRow--container-L2hvbWUv");
            for(int i=0;i<names.size() && i<prices.size();i++)
            {
                Elements singleprice = prices.get(i).select("h6.Atoms__TextAtom--sbt-text-atom-L2hvbWUv").select("h6.Atoms__TextAtom--token-h6-L2hvbWUv").select("h6.size-normal").select("h6.Atoms__TextAtom--weight-semibold-L2hvbWUv").select("h6.AdElements__ItemPrice--price-L2hvbWUv");
                String fix_price="0";
                if(singleprice.size()>0)
                    fix_price = singleprice.get(0).html().replace(".","").replace(",",".").replace("€","");
                if(names.get(i).html().contains(research) || names.get(i).html().contains(research.substring(1)) || names.get(i).html().contains(research.substring(0,research.length()-1)))
                    items.add(new Item(names.get(i).html(),Float.parseFloat(fix_price),'€'));
            }
        }


        /*Facebook*/
        else if(typename.equals("facebook") || typename.equals("Facebook") || typename.equals("FB") ||typename.equals("fb") || typename.equals("facebook marketplace") ||typename.equals("fb marketplace") || typename.equals("FACEBOOK"))
        {
            Document doc = Facebook(research);
            Elements names = doc.select("p.l9j0dhe7").select("p.stjgntxs").select("p.ni8dbmo4");
            Elements prices = doc.select("span.oi732d6d").select("span.ik7dh3pa").select("span.d2edcug0").select("span.qv66sw1b").select("span.c1et5uql").select("span.a8c37x1j").select("span.s89635nw").select("span.ew0dbk1b").select("span.a5q79mjw").select("span.g1cxx5fr").select("span.hnhda86s").select("span.oo9gr5id");
            for(int i=0;i<names.size() && i<prices.size();i++)
            {
                String fix_price = prices.get(i).html().replace(".","").replace(",",".").replace("€","").replace("&nbsp;€","").replace("&nbsp;$","");
                items.add(new Item(names.get(i).html(),Float.parseFloat(fix_price),'€'));
            }
        }
        else
        {
            return null;
        }

        return items;
    }

    private Document Amazon(String filter) throws IOException
    {
        filter.replace(" ","+");
        return Jsoup.connect("https://www.amazon.it/s")
                .data("k", filter)
                .data("ref", "nb_sb_noss")
                .userAgent("Chrome")
                .get();

    }

    private Document EBay(String filter) throws IOException
    {
        filter.replace(" ","+");
        return Jsoup.connect(" https://www.ebay.it/sch/i.html")
                .data("_nkw", filter)
                .userAgent("Chrome")
                .get();
    }

    private Document Subito(String filter) throws IOException
    {
        //SOLO PIEMONTE : https://www.subito.it/annunci-piemonte/vendita/usato/
        filter.replace("","+");
        return Jsoup.connect("https://www.subito.it/annunci-italia/vendita/usato/")
                .data("q", filter)
                .data("from","top-bar")
                .userAgent("Chrome")
                .get();
    }

    private Document Facebook(String filter) throws IOException
    {
        //Solo torino
        filter.replace("","+");
        return  (Jsoup.connect("https://www.facebook.com/marketplace/turin/search/")
                .data("query", filter)
                .userAgent("Chrome")
                .method(Connection.Method.GET)
                .execute()).parse();
    }

    public void setResearch(String research)
    {
        this.research=research;
    }
}
