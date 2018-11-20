package com.cbsexam;

import cache.UserCache;
import com.google.gson.Gson;
import controllers.UserController;
import java.util.ArrayList;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.User;
import utils.Encryption;
import utils.Log;

@Path("user")
public class UserEndpoints {

  /**
   * @param idUser
   * @return Responses
   */
  @GET
  @Path("/{idUser}")
  public Response getUser(@PathParam("idUser") int idUser) {

    // Use the ID to get the user from the controller.
    User user = UserController.getUser(idUser);

    // Convert the user object to json in order to return the object
    String json = Encryption.encryptDecryptXOR(new Gson().toJson(user));

    // Return the user with the status code 200
    // TODO: What should happen if something breaks down?
    return Response.status(200).type(MediaType.APPLICATION_JSON_TYPE).entity(json).build();
  }

  /** @return Responses */
  @GET
  @Path("/")
  public Response getUsers() {

    // Write to log that we are here
    Log.writeLog(this.getClass().getName(), this, "Get all users", 0);

    // Get a list of users
    ArrayList<User> users = UserCache.getUsers(false);

    // Transfer users to json in order to return it to the user
    String json = Encryption.encryptDecryptXOR(new Gson().toJson(users));

    // Return the users with the status code 200
    return Response.status(200).type(MediaType.APPLICATION_JSON).entity(json).build();
  }

  @POST
  @Path("/")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response createUser(String body) {

    // Read the json from body and transfer it to a user class
    User newUser = new Gson().fromJson(body, User.class);

    // Use the controller to add the user
    User createUser = UserController.createUser(newUser);

    // Get the user back with the added ID and return it to the user
    String json = Encryption.encryptDecryptXOR(new Gson().toJson(createUser));

    // Return the data to the user
    if (createUser != null) {
      // Return a response with status 200 and JSON as type
      return Response.status(201).type(MediaType.APPLICATION_JSON_TYPE).entity(json).build();
    } else {
      return Response.status(400).entity("Email already exists").build();
    }
  }

  // TODO: Make the system able to login users and assign them a token to use throughout the system. DONE
  @POST
  @Path("/login")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response loginUser(String x) {

    // Read the json from body and transfer it to a user class
    User newUser = new Gson().fromJson(x, User.class);

    String authToken = UserController.authenticateUser(newUser);

    if (authToken != null){
      newUser.setAuthToken(authToken);

      String json = Encryption.encryptDecryptXOR(new Gson().toJson(newUser));

      // Return a response with status 200 and JSON as type
      return Response.status(200).type(MediaType.APPLICATION_JSON_TYPE).entity(json).build();
    } else {
      return Response.status(401).entity("Unathorized access").build();
    }
  }

  /**
   *
   * @param x a json string that cointains an ID for a user.
   * @return
   */
  @POST
  @Path("/delete")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response deleteUser(String x) {
    // Read the json from body and transfer it to a user class
    User user = new Gson().fromJson(x, User.class);

    if (UserController.deleteUser(user)){
      // Return a response with status 200 and JSON as type
      return Response.status(200).entity("User deleted").build();
    } else {
      // Return a response with status 200 and JSON as type
      return Response.status(400).entity("User not found").build();
    }
  }

  /**
   *
   * @param x is some json with a user object. Not all of the attributes must be filled out but ID must be defined.
   * @return
   */
  @POST
  @Path("/update")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response updateUser(String x) {

    User user = new Gson().fromJson(x, User.class);

    if (UserController.updateUser(user)){
      // Return a response with status 200 and JSON as type
      return Response.status(200).entity("User updated").build();
    } else {
      return Response.status(400).entity("User not found").build();
    }
  }
}
