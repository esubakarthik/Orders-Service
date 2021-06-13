package suba.task.coding.Orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
public class FruitOrderService {
    @Autowired
    DataRepo datarepo;
//get all orders
        public ArrayList<FruitOrder> GetAllOrder() {
            ArrayList<FruitOrder> fruitorders = new ArrayList<FruitOrder>();
        datarepo.findAll().forEach(fruitOrder -> fruitorders.add(fruitOrder));
            return fruitorders;
        }
//get entries based on id
    public FruitOrder getByOrderId(int id)
    {
        try {
            return datarepo.findById(id).get();
        }catch (java.util.NoSuchElementException no){
            return null;
        }
    }
//save entries to database
    public void saveOrUpdate(FruitOrder fruitOrder)
    {
        datarepo.save(fruitOrder);
    }


}
