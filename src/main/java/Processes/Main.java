package Processes;

import Data.Parameters;
import General.Type;

import java.util.ArrayList;
import java.util.List;

public class Main
{
    /*
    * MultiThread -> Every Thread has 2 Sites in Memory Passed by Father
    *
    * The idea is that:
    * Every Thread check a single product of a category
    * Get the name of the product, search in Description for features
    * Get the Price of the product, then he will compare it
    * in another Site with another Product with same (similar) name
    * same features, and check for the price.
    *
    * If there is a good chance to get Profit in trading the item from
    * 1 site to the other one, so he will "purpose" it to the User
    *
    * The user will Accept or Deny the Offer basing on own searches
    * (I believe they will be not necessary).
    *
    * The new Item Offer From-To site will be added to the Database
    *
    * The Database will Refresh the page on the Client (Android/PC)
    *
    * This is the SERVER-SIDE Jar Algorithm.
    * */
    public static void main(String Args[])
    {
        List<Chooser> workers = new ArrayList<>();
        List<Parameters> couples = new ArrayList<>();

        couples.add(new Parameters(new Type("EBay"),new Type("Amazon"),"Surface Go","Cover,Pellicola,Adattatore,Penna,Custodia,Protezione"));//AGGIUNGE un Thread con 2 siti ,  1 Ricerca e le parole da filtrare dalla ricerca

        for(int i=0;i<couples.size();i++) //CREA TUTTI I THREAD
        {
            workers.add(new Chooser(couples.get(i)));
            workers.get(i).start();
        }
    }
}
