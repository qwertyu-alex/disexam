package cache;

import controllers.OrderController;
import model.Order;
import utils.Config;

import java.util.ArrayList;

public class OrderCache {

    // List of orders
    static private ArrayList<Order> orders = new ArrayList<>();

    // Time cache should live
    static private long ttl = Config.getOrderTtl();

    // Sets when the cache has been created
    static private long created;

    static public ArrayList<Order> getOrders(Boolean forceUpdate) {

        //If cache is disabled -> get data from database
        if (!Config.getCache()){
            return OrderController.getOrders();
        }

        // If we wish to clear cache, we can set force update.
        // Otherwise we look at the age of the cache and figure out if we should update.
        // If the list is empty we also check for new orders
        if (forceUpdate
                || ((created + ttl) >= (System.currentTimeMillis() / 1000L))
                || orders.isEmpty()) {
            System.out.println("Updating Order Cache");
            // Get orders from controller, since we wish to update.
            orders = OrderController.getOrders();

            // Set orders for the instance and set created timestamp
            created = System.currentTimeMillis() / 1000L;
        }

        // Return the documents
        return orders;
    }

    static public Order getOrder (int id){
      //If cache is disabled -> get data from database
      if (!Config.getCache()){
        return OrderController.getOrder(id);
      }


      for (Order order : orders) {
          if (order.getId() == id) {
              return order;
          }
      }

      Order noncache = OrderController.getOrder(id);
      if (noncache != null){
          orders.add(noncache);
          return noncache;
      }

      return null;
    }

}
