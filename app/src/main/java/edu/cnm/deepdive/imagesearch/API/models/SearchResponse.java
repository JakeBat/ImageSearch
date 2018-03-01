package edu.cnm.deepdive.imagesearch.API.models;

import java.util.ArrayList;
import java.util.List;

public class SearchResponse {

  public List<Item> getItems() {
    return items;
  }

  private class RequestData {
      private String title = null;
      private String totalResult = null;
      private int count = 0;
      private int startIndex = 0;
  }

  public class Item {
    private String title = null;
    private String link = null;

    public String getTitle() {
      return title;
    }

    public void setTitle(String title) {
      this.title = title;
    }

    public String getLink() {
      return link;
    }

    public void setLink(String link) {
      this.link = link;
    }
  }
  private RequestData request = null;
  private RequestData nextpage = null;
  private List<Item> items = null;

}
