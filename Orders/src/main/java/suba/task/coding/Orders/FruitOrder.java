package suba.task.coding.Orders;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class FruitOrder  {
    @Id
    int order_Number;
    double total_cost;
    @ElementCollection
    List<Item> items = new ArrayList<Item>();

    public List<Item> getItems() {
        return items;
    }

    public FruitOrder() {
    }

    public FruitOrder(int order_Number, int apple_count, int orange_count, double apple_total, double orange_total) {
        this.order_Number = order_Number;
        Item Apples = new Item("Apples",apple_count,apple_total);
        Item Oranges = new Item("Oranges",orange_count, orange_total);
        items.add(Apples);
        items.add(Oranges);
        this.total_cost=0;
        for (Item item : items){ this.total_cost += item.total;}

    }

//getters & setters
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
@Embeddable
class Item{

    String name;
    int count;
    double total;

    public Item() {
    }

    public Item( String name,int count, double total_itemcost) {
        this.name = name;
        this.count = count;
        this.total = total_itemcost;
    }

    public String getName() { return name; }

    public int getCount() {
        return count;
    }

    public double getTotal() {
        return total;
    }
}
