package edu.cnm.deepdive.imagesearch;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import edu.cnm.deepdive.imagesearch.API.API;
import edu.cnm.deepdive.imagesearch.API.GoogleClient;
import edu.cnm.deepdive.imagesearch.API.models.SearchResponse;
import edu.cnm.deepdive.imagesearch.API.models.SearchResponse.Item;
import edu.cnm.deepdive.imagesearch.adapters.ImagesAdapter;
import java.util.ArrayList;
import java.util.List;
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

    txtSearch.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {

      }

      @Override
      public void afterTextChanged(Editable s) {
        saveSearchQuery(s.toString());
        GoogleClient.imageSearch(MainActivity.this, s.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<SearchResponse>() {
                         @Override
                         public void call(SearchResponse searchResponse) {
                           Log.d("API", "Success");
                           populateList(searchResponse.getItems());

                         }
                       },
                new Action1<Throwable>() {
                  @Override
                  public void call(Throwable throwable) {
                    Log.e("API", "Error");

                  }
                });
      }
    });

    txtSearch.setText(getSavedSearchQuery());
  }

  private void populateList(List<SearchResponse.Item> items) {
    if (listView != null && items != null) {
      List<String> names = new ArrayList<>();
      for (Item item :items) {
        names.add(item.getTitle());
      }
      listView.setAdapter(new ImagesAdapter(this, items));
    }
  }

  private void saveSearchQuery(String query) {
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
    SharedPreferences.Editor prefsEdit = preferences.edit();
    prefsEdit.putString("query", query);
    prefsEdit.apply();
  }

  private String getSavedSearchQuery() {
    String query = "";
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
    if (preferences != null && preferences.contains("query")) {
      query = preferences.getString("query", "");
    }

    return query;
  }
}
