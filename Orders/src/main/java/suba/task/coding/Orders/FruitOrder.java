package suba.task.coding.Orders;


import javax.persistence.*;
import java.text.DecimalFormat;

@Entity
public class FruitOrder {
    @Id
    int order_Number;
    double total_cost;
    @OneToOne()
    Item Apples;
    @OneToOne()
    Item Oranges;

    public Item getApples() {
        return Apples;
    }

    public Item getOranges() {
        return Oranges;
    }

    public FruitOrder(int order_Number, int apple_count, int orange_count,double apple_total,double orange_total) {
        this.order_Number = order_Number;
        Apples = new Item("Apple",apple_count,apple_total);
        Oranges = new Item("Orange",orange_count, orange_total);
        this.total_cost= orange_total+apple_total;

    }

    public void setOrder_Number(int order_Number) {
        this.order_Number = order_Number;
    }

    public void setTotal_cost(Double total_cost) {
        this.total_cost = total_cost;
    }

    public int getOrder_Number() {
        return order_Number;
    }

    public double getTotal_cost() {
        return total_cost;
    }

}
@Entity
class Item{
    @Id
    String name;
    int count;
    double total;

    public Item(String name, int count,double total_itemcost) {
        this.name= name;
        this.count = count;
        this.total = total_itemcost;
    }


    public int getCount() {
        return count;
    }

    public double getTotal() {
        return total;
    }
}
