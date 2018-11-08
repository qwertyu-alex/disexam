package cache;

import controllers.ReviewController;
import model.Review;
import model.Search;
import utils.Config;
import java.util.ArrayList;

public class ReviewCache {
  // List of Searches
  private static ArrayList<Search> titleSearches = new ArrayList<>();
  private static ArrayList<Search> idSearches = new ArrayList<>();
  private static ArrayList<Search> authorSearches = new ArrayList<>();

  // Time cache should live
  private static long ttl = Config.getUserTtl();

  // Sets when the cache has been created
  private static long created;

  public static ArrayList<Review> searchByTitle (String title, boolean forceUpdate){

    //If cache is disabled -> get data from database
    if (!Config.getCache()){
      return ReviewController.searchByTitle(title);
    }

    //Updates the cache
    if (forceUpdate
            || ((created + ttl) <= (System.currentTimeMillis() / 1000L))
            || titleSearches.isEmpty()) {

      System.out.println("Updating titleSearchesCache");

      //Updates the cache by searching for the title
      ArrayList<Review> foundReviews = ReviewController.searchByTitle(title);
      Search titleSearch = new Search(title, foundReviews);

      //Add it to the cache
      titleSearches.add(titleSearch);

      // Set created timestamp
      created = System.currentTimeMillis() / 1000L;

      return foundReviews;
    }

    //Looks in the cache for matching searches
    for (Search search: titleSearches) {
       if (search.getString().equalsIgnoreCase(title)){
         return search.getReviews();
       }
    }

    //Returns an empty list if nothing is found
    return new ArrayList<>();

  }

  public static ArrayList<Review> searchByID (int id, boolean forceUpdate){

    //If cache is disabled -> get data from database
    if (!Config.getCache()){
      return ReviewController.searchByID(id);
    }

    //Updates the cache
    if (forceUpdate
            || ((created + ttl) <= (System.currentTimeMillis() / 1000L))
            || idSearches.isEmpty()) {

      System.out.println("Updating idSearchesCache");

      //Updates the cache by searching for the id
      ArrayList<Review> foundReviews = ReviewController.searchByID(id);
      Search idSearch = new Search(id, foundReviews);

      //Add it to the cache
      idSearches.add(idSearch);

      // Set created timestamp
      created = System.currentTimeMillis() / 1000L;

      return foundReviews;
    }

    //Looks in the cache for matching searches
    for (Search search: idSearches) {
      if (search.getId() == id){
        return search.getReviews();
      }
    }

    //Returns an empty list if nothing is found
    return new ArrayList<>();

  }
  public static ArrayList<Review> searchByAuthor (String author, boolean forceUpdate){

    //If cache is disabled -> get data from database
    if (!Config.getCache()){
      return ReviewController.searchByAuthor(author);
    }

    //Updates the cache
    if (forceUpdate
            || ((created + ttl) <= (System.currentTimeMillis() / 1000L))
            || authorSearches.isEmpty()) {

      System.out.println("Updating authorSearchesCache");

      //Updates the cache by searching for the author
      ArrayList<Review> foundReviews = ReviewController.searchByAuthor(author);
      Search authorSearch = new Search(author, foundReviews);

      //Add it to the cache
      authorSearches.add(authorSearch);

      // Set created timestamp
      created = System.currentTimeMillis() / 1000L;

      return foundReviews;
    }

    //Looks in the cache for matching searches
    for (Search search: authorSearches) {
      if (search.getString().equalsIgnoreCase(author)){
        return search.getReviews();
      }
    }

    //Returns an empty list if nothing is found
    return new ArrayList<>();

  }

}
