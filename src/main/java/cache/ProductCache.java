package cache;

import controllers.ProductController;
import java.util.ArrayList;
import model.Product;
import utils.Config;

public class ProductCache {

  // List of products
  private static ArrayList<Product> products;

  // Time cache should live
  private static long ttl;

  // Sets when the cache has been created
  private static long created;

  public static ArrayList<Product> getProducts(Boolean forceUpdate) {

    //If cache is disabled -> get data from database
    if (!Config.getCache()){
      return ProductController.getProducts();
    }

    ttl = Config.getProductTtl();
    // If we wish to clear cache, we can set force update.
    // Otherwise we look at the age of the cache and figure out if we should update.
    // If the list is empty we also check for new products
    if (forceUpdate
        || ((created + ttl) <= (System.currentTimeMillis() / 1000L))
        || products.isEmpty()) {
      System.out.println("Updating Product Cache");

      // Get products from controller, since we wish to update.
      products = ProductController.getProducts();

      // Set products for the instance and set created timestamp
      created = System.currentTimeMillis() / 1000L;
    }

    // Return the documents
    return products;
  }

  public static Product getProduct(int id){

    if (!Config.getCache()){
      return ProductController.getProduct(id);
    }

    for (Product product : products) {
      if (id == product.getId()){
        return product;
      }
    }

    Product noncache = ProductController.getProduct(id);
    if (noncache != null){
      products.add(noncache);
      return noncache;
    }

    return null;
  }
}
