package suba.task.coding.Orders;


import javax.persistence.*;
import java.text.DecimalFormat;

@Entity
public class FruitOrder {
    @Id
    int order_Number;
    double total_cost;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "count", column = @Column(name = "apple_count")),
            @AttributeOverride( name = "total", column = @Column(name = "apple_totalcost")),
    })
    Item Apples;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "count", column = @Column(name = "orange_count")),
            @AttributeOverride( name = "total", column = @Column(name = "orange_totalcost"))
    })
    Item Oranges;


    public Item getApples() {
        return Apples;
    }

    public Item getOranges() {
        return Oranges;
    }

    public FruitOrder() {
    }

    public FruitOrder(int order_Number, int apple_count, int orange_count, double apple_total, double orange_total) {
        this.order_Number = order_Number;
        Apples = new Item(apple_count,apple_total);
        Oranges = new Item(orange_count, orange_total);
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
@Embeddable
class Item{


    int count;
    double total;

    public Item() {
    }

    public Item( int count, double total_itemcost) {

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
