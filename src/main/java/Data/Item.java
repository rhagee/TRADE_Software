package Data;

public class Item
{
    String name; //Full item name
    float price; //Price of item
    char priceType; //Euro,Dollar, ecc.
    String Description; //Some important features to be shown on the screen
    int status; // 0->new 1->reconditionated 2->used 3->broken

    public Item(String name,float price,char priceType)
    {
        this.name=name;
        this.price=price;
        this.priceType=priceType;
    }

    public String toString()
    {
        return "Name :"+name+" - Price : "+price+" "+priceType+" - Desc: "+Description+" - "+" STATUS: "+status;
    }
}
