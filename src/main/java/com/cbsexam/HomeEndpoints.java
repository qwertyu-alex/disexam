package com.cbsexam;

import com.google.gson.Gson;
import controllers.OrderController;
import model.Order;
import utils.Encryption;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class HomeEndpoints {
  @GET
  @Path("/")
  public Response getFrontPage() {


    String html = "<HTML><HEAD><TITLE>Hello</TITLE></HEAD><BODY><form action=\"/user\">\n" +
            "    <input type=\"submit\" value=\"Go to Google\" />\n" +
            "</form></BODY></HTML>";

    // Return a response with status 200 and JSON as type
    return Response.status(200).type(MediaType.TEXT_HTML_TYPE).entity(html).build();
  }
}
