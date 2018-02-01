package edu.cnm.deepdive.imagesearch.API.models;

import java.util.ArrayList;
import java.util.List;

public class SearchResponse {

  private class RequestData {
      private String title = null;
      private String totalResult = null;
      private int count = 0;
      private int startIndex = 0;
  }

  private class Item {
    private String link = null;
  }
  private RequestData request = null;
  private RequestData nextpage = null;
  private List<Item> items = null;

}
