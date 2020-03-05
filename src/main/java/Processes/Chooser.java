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
    public void run() //Questo è il metodo invocato quando il thread viene lanciato (Avremo piu thread per fare piu confronti contemporaneamente su coppie di siti)
    {
        try
        {
           List<Item> FirstItems = param.getFirst().getItems(); //prendo la lista degli item ottenuti dal primo sito con quella ricerca
           List<Item> SecondItems = param.getSecond().getItems(); //prendo la lista degli item ottenuti dal secondo sito con quella ricerca

           System.out.println("Ignored Words : "+param.getIgnored()); //Stampo solo le parole ignorate (per testing)

           for(int i=0;i<FirstItems.size();i++) //Per ogni item del primo sito
           {
               if(filterIgnored(FirstItems.get(i).getName(),param.getIgnored()) && FirstItems.get(i).getName().contains(param.getItemName())) //Se l'item è valido (Contiene la parola ricercata e non ha parole filtrate)
               {
                   System.out.println( "---------- MATCHING " + param.getFirst().getTypename() + " -> " + FirstItems.get(i).getName()+"-------"); //Stampa
                   for(int j=0;j<SecondItems.size();j++) //Scorre ogni item del secondo sito
                   {
                       if(filterIgnored(SecondItems.get(j).getName(),param.getIgnored()) && SecondItems.get(j).getName().contains(param.getItemName())) //Se l'item è valido (come sopra)
                       {
                           System.out.println("\n- " + param.getSecond().getTypename() + " -> " + SecondItems.get(j).getName()); //Stampo il suo nome
                            //Parte dei calcoli :
                           float price1 = FirstItems.get(i).getPrice(); //First Price
                           float price2 = SecondItems.get(j).getPrice(); //Second Price
                           float percent = 10.0f; //Choosen Percentage -> Percentuale di guadagno che vogliamo
                           float price1low = price1-Percentage(price1,percent); //Price 1 lowered by his percentage -> Primo prezzo diminuito della percentuale
                           float price1up = price1+Percentage(price1,percent); //Price 2 upped by his percentage -> Primo prezzo Aumentato della percentuale
                           //Se il secondo prezzo non è compreso nel range del Primo Diminuito e del Primo Aumentato (allora c'è un guadagno,in uno dei 2 sensi)
                           if(!(Included(price1low,price2,price1up))) //If the price 2 is not Included into Price1-HisPercentage and Price1+HisPercentage IT'S  a GOOD THING , someone has good.
                               //Se il secondo Prezzo è Minore del primo diminuito del guadagno, allora E' BUONO comprare dal PREZZO2 e vendere SOTTO il PREZZO1
                               if(price2<price1low) //If price2 is lower than price1 lowered by his percentage, so it's good to BUY from Site2 and SELL on Site1
                               {
                                   System.out.println("BUY from : " + param.getSecond().getTypename() + " at " + price2 + " and SELL on : " + param.getFirst().getTypename() + " at " + price1 + " or lower ");
                                   System.out.println("BUY : "+ SecondItems.get(j).getLink());
                                   System.out.println("SELL : "+ FirstItems.get(i).getLink());
                               }
                               else //Else is good doing the opposite
                               {
                                   System.out.println("BUY from : " + param.getFirst().getTypename() + " at " + price1 + " and SELL on : " + param.getSecond().getTypename() + " at " + price2 + " or lower ");
                                   System.out.println("BUY : "+ FirstItems.get(i).getLink());
                                   System.out.println("SELL : "+ SecondItems.get(j).getLink());
                               }
                               else
                                   System.out.println("THERE IS NOT GOOD EARNINGS FOR THIS COUPLE OF PRODUCTS");
                       }
                   }
                   System.out.println("\n----------------------------------------------\n\n");
               }
           }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public boolean filterIgnored(String toCompare,String ignored)
    {
        boolean result=true;

        String[] splitted = ignored.split(",");
        String toCompLow = toCompare.toLowerCase();

        for(int i=0;i<splitted.length && result;i++)
        {
            if(toCompLow.contains(splitted[i].toLowerCase()))
            {
                result=false;
            }
        }
        return result;
    }

    public float Percentage(float number,float percentage)
    {
        return ((number/100)*percentage);
    }

    public boolean Included(float compareLower,float number,float compareUpper)
    {
        return (number>=compareLower)&&(number<=compareUpper);
    }

}
