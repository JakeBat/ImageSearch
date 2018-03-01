package edu.cnm.deepdive.imagesearch.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import edu.cnm.deepdive.imagesearch.API.models.SearchResponse;
import edu.cnm.deepdive.imagesearch.R;
import edu.cnm.deepdive.imagesearch.utils.ImageUtils;
import java.util.List;

public class ImagesAdapter extends BaseAdapter {

  private List<SearchResponse.Item> items = null;
  private Context context = null;

  public ImagesAdapter(Context context, List<SearchResponse.Item>items) {
    this.context = context;
    this.items = items;
  }
  @Override
  public int getCount() {
    int count = 0;
    if (items != null) {
      count = items.size();
    }
    return count;
  }

  @Override
  public Object getItem(int position) {
    Object item = null;

    if (items != null && position < items.size()) {
      item = items.get(position);
    }
    return item;
  }

  private SearchResponse.Item getImageItem(int position) {
    Object item = getItem(position);
    if (item instanceof SearchResponse.Item) {
      return (SearchResponse.Item)item;
    } else {
      return null;
    }
  }

  @Override
  public long getItemId(int position) {
    return 0;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    if (convertView == null) {
      convertView = LayoutInflater.from(context).inflate(R.layout.image_list_item, parent, false);
    }
    TextView txtTitle = convertView.findViewById(R.id.txtTitle);
    final ImageView imageView = convertView.findViewById(R.id.imageView);

    SearchResponse.Item item = getImageItem(position);
    if (item != null && txtTitle != null && imageView != null) {
      txtTitle.setText(item.getTitle());

      final String url = item.getLink();
      ImageUtils.downloadImage(context, item.getLink(), new Runnable() {
        @Override
        public void run() {
          Bitmap image = ImageUtils.getDownloadImage(context, url);
          if (image != null) {
            imageView.setImageBitmap(image);
          }
        }
      });
    }
    return convertView;
  }
}

























