package suba.task.coding.Orders;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class OrderController {

    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/neworder")
    public Object order(@RequestParam(value = "Apple", defaultValue = "0") String apple_count, @RequestParam(value = "Orange", defaultValue = "0") String orange_count){
        int apple = Integer.parseInt(apple_count);
        int orange = Integer.parseInt(orange_count);
        if((apple<0)||(orange<0)) return "Invaild input";

        if((apple+orange)<=0) return "NO item in the order";

            double apple_total = (apple*.60);
            double orange_total = (orange*.25);
          return   new FruitOrder((int) counter.incrementAndGet(),apple,orange,apple_total,orange_total);

       // return   new Order((int) counter.incrementAndGet(),apple,orange,0,0);

    }


}
