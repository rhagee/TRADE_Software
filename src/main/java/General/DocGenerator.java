package General;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class DocGenerator
{
    public static Document Amazon(String filter) throws IOException {
        filter.replace(" ","+");
        return Jsoup.connect("https://www.amazon.it/s")
                .data("k", filter)
                .data("ref", "nb_sb_noss")
                .userAgent("Chrome")
                .get();

    }

    public static Document EBay(String filter) throws IOException
    {
        filter.replace(" ","+");
        return Jsoup.connect(" https://www.ebay.it/sch/i.html")
                .data("_nkw", filter)
                .userAgent("Chrome")
                .get();
    }

    public static Document Subito(String filter) throws IOException
    {
        //SOLO PIEMONTE : https://www.subito.it/annunci-piemonte/vendita/usato/
        filter.replace(" ","+");
        return Jsoup.connect("https://www.subito.it/annunci-italia/vendita/usato/")
                .data("q", filter)
                .data("from","top-bar")
                .userAgent("Chrome")
                .get();
    }

    public static Document Kijiji(String filter) throws IOException
    {
        filter.replace(" ","+");
        return  Jsoup.connect("https://www.kijiji.it/"+filter+"/")
                .data("entryPoint", "sb")
                .userAgent("Chrome")
                .method(Connection.Method.GET)
                .get();
    }

    public static Document ReBuy(String filter) throws IOException
    {
        filter.replace(" ","+");

        return  Jsoup.connect("https://www.rebuy.it/comprare/search")
                .data("q", filter)
                .userAgent("Chrome")
                .get();
    }

    public static Document Facebook(String filter) throws IOException
    {
        //Solo torino
        filter.replace(" ","+");
        return  (Jsoup.connect("https://www.facebook.com/marketplace/turin/search/")
                .data("query", filter)
                .userAgent("Chrome")
                .method(Connection.Method.GET)
                .execute()).parse();
    }

}
