package Processes;

import Data.Item;
import Utils.AuthThread;
import org.jsoup.*;
import Data.Parameters;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.net.ssl.*;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.List;

public class Chooser extends AuthThread
{
    Parameters param;
    DBManager db;

    public Chooser(Parameters param)
    {
        this.param=param;
    }


    @Override
    public void run()
    {
        try
        {
           List<Item> FirstItems = param.getFirst().getItems();
           List<Item> SecondItems = param.getSecond().getItems();
           for(Item single : SecondItems)
           {
               System.out.println(single.toString());
           }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
