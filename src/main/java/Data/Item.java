package Data;

public class Item
{

    String name; //Full item name
    float price; //Price of item
    String link; //Direct link of the product
    char priceType; //Euro,Dollar, ecc.
    String Description; //Some important features to be shown on the screen
    int status; // 0->new 1->reconditionated 2->used 3->broken

    public Item(String name,float price,char priceType,String link)
    {
        this.name=name;
        this.price=price;
        this.priceType=priceType;
        this.link=link;
    }

    public String getName() { return this.name;}
    public float getPrice() { return this.price;}
    public String getLink(){ return this.link;}
    public String toString()
    {
        return "Name :"+name+" - Price : "+price+" "+priceType+"- LINK : "+link+" - Desc: "+Description+" - "+" STATUS: "+status;
    }
}
