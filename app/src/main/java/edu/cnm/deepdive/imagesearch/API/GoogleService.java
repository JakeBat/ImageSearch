package edu.cnm.deepdive.imagesearch.API;

import edu.cnm.deepdive.imagesearch.API.models.SearchResponse;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Single;

public interface GoogleService {

  @GET("customsearch/v1")
  Single<SearchResponse> imageSearch(
      @Query("key") String key,
      @Query("cx") String cx,
      @Query("q") String query,
      @Query("searchType") String searchType,
      @Query("num") int num,
      @Query("start") int start
  );

}
