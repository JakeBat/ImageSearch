package edu.cnm.deepdive.imagesearch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import edu.cnm.deepdive.imagesearch.API.API;
import edu.cnm.deepdive.imagesearch.API.GoogleClient;
import edu.cnm.deepdive.imagesearch.API.models.SearchResponse;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

  private EditText txtSearch = null;
  private ListView listView = null;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    API.init(this);
    txtSearch = findViewById(R.id.txtSearch);
    listView = findViewById(R.id.listView);

    GoogleClient.imageSearch(this, "cars")
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<SearchResponse>() {
                     @Override
                     public void call(SearchResponse searchResponse) {
                       Log.d("API", "Success");

                     }
                   },
            new Action1<Throwable>() {
              @Override
              public void call(Throwable throwable) {
                Log.e("API", "Error");

              }
            });
  }
}
