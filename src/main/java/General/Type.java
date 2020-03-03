package General;

import Data.Item;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
        if(typename.equals("Amazon") || typename.equals("amazon"))
        {
            Document doc = Amazon(research);
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

    public List<Item> getItems() throws IOException {
        List<Item> items = new ArrayList();
        if(typename.equals("Amazon") || typename.equals("amazon"))
        {
            Document doc = Amazon(research);
            Elements names = doc.select("span.a-size-medium").select("span.a-color-base").select("span.a-text-normal");
            names.addAll(doc.select("span.a-size-base-plus").select("span.a-color-base").select("span.a-text-normal"));
            Elements prices = doc.select("span.a-price").not("span.a-text-price").select("span.a-offscreen");
            System.out.println("names "+names.size()+" Prizes "+prices.size());
            for(int i=0;i<names.size() && i<prices.size();i++)
            {
                items.add(new Item(names.get(i).html(),Float.parseFloat(prices.get(i).html().substring(1).replace(",","").replace(Pattern.quote("."),",")),prices.get(i).html().charAt(0)));
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
        return Jsoup.connect("https://www.amazon.com/s")
                .data("k", filter)
                .data("ref", "nb_sb_noss")
                .userAgent("Chrome")
                .get();
    }

    public void setResearch(String research)
    {
        this.research=research;
    }
}
