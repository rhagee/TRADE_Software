package Data;

import General.Type;

public class Parameters
{
    Type first;
    Type second;
    String itemName;
    String ignore;
    String maxPrice;
    String minDifference;

    public Parameters(Type first,Type second,String itemName)
    {
        this.first = first;
        this.second = second;
        this.itemName=itemName;
        first.setResearch(itemName);
        second.setResearch(itemName);
    }

    public Parameters(Type first,Type second,String itemName,String ignore)
    {
        this.first = first;
        this.second = second;
        this.itemName=itemName;
        first.setResearch(itemName);
        second.setResearch(itemName);
        this.ignore=ignore;
    }

    public String getIgnored() { return this.ignore; }
    public String getItemName() { return this.itemName;}
    public Type getFirst(){return first;}
    public Type getSecond(){return second;}

}
