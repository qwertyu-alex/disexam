package com.cbsexam;
import cache.ReviewCache;
import com.google.gson.Gson;
import controllers.ReviewController;
import java.util.ArrayList;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.Review;
import utils.Encryption;

@Path("search")
public class ReviewEndpoints {

  /**
   * @param reviewTitle
   * @return Responses
   */
  @GET
  @Path("/title/{title}")
  public Response searchWithTitle(@PathParam("title") String reviewTitle) {

    // Call our controller-layer in order to get the order from the DB
    ArrayList<Review> reviews = ReviewCache.searchByTitle(reviewTitle, false);

    // We convert the java object to json with GSON library imported in Maven
    String json = Encryption.encryptDecryptXOR(new Gson().toJson(reviews));

    // Return a response with status 200 and JSON as type
    return Response.status(200).type(MediaType.APPLICATION_JSON).entity(json).build();
  }

  /**
   * @param reviewID
   * @return Responses
   */
  @GET
  @Path("/id/{id}")
  public Response searchWithID(@PathParam("id") int reviewID) {

    // Call our controller-layer in order to get the order from the DB
    ArrayList<Review> reviews = ReviewCache.searchByID(reviewID, false);

    // We convert the java object to json with GSON library imported in Maven
    String json = Encryption.encryptDecryptXOR(new Gson().toJson(reviews));

    // Return a response with status 200 and JSON as type
    return Response.status(200).type(MediaType.APPLICATION_JSON).entity(json).build();
  }

  /**
   * @param reviewAuthor
   * @return Responses
   */
  @GET
  @Path("/author/{author}")
  public Response searchWithAuthor(@PathParam("author") String reviewAuthor) {

    // Call our controller-layer in order to get the order from the DB
    ArrayList<Review> reviews = ReviewCache.searchByAuthor(reviewAuthor, false);

    // We convert the java object to json with GSON library imported in Maven
    String json = Encryption.encryptDecryptXOR(new Gson().toJson(reviews));

    // Return a response with status 200 and JSON as type
    return Response.status(200).type(MediaType.APPLICATION_JSON).entity(json).build();
  }

}
