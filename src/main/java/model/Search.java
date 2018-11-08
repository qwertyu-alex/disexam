package model;

import java.util.ArrayList;

public class Search {
  private String string;
  private ArrayList<Review> reviews;
  private int id;

  public Search(String string, ArrayList<Review> reviews) {
    this.string = string;
    this.reviews = reviews;
  }

  public Search(int id, ArrayList<Review> reviews){
    this.id = id;
    this.reviews = reviews;
  }

  public String getString() {
    return string;
  }

  public ArrayList<Review> getReviews() {
    return reviews;
  }

  public int getId() {
    return id;
  }
}
