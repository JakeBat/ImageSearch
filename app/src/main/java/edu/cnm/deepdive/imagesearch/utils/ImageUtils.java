package edu.cnm.deepdive.imagesearch.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageUtils {

  private static class DownloadImageTask extends AsyncTask<String, Void, Boolean> {

    public DownloadImageTask(Context context, Runnable callback) {
      this.context = context;
      this.callback = callback;
    }

    private Context context;
    private Runnable callback;

    @Override
    protected Boolean doInBackground(String... strings) {
      downloadFile(context, strings[0]);
      return null;
    }

    @Override
    protected void onPostExecute(Boolean result) {
      context = null;
      if (callback != null) {
        callback.run();
      }
    }
  }

  private static class OpenImageTask extends AsyncTask<String, Void, Bitmap> {

    public OpenImageTask(Context context, ImageView imageView) {
      this.context = context;
      this.imageView = imageView;
    }

    private Context context;
    private ImageView imageView;

    @Override
    protected Bitmap doInBackground(String... strings) {
      return getDownloadImage(context, strings[0]);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
      imageView.setImageBitmap(bitmap);
    }
  }

  public static void downloadImage(final Context context, final String url, Runnable callback) {
      new DownloadImageTask(context, callback).execute(url);
  }

//  public static void getDownloadedImageInBackground(Context context, String url, ImageView imageView) {
//    new DownloadImageTask(context, imageView).execute(url);
//  }

  public static Bitmap getDownloadImage(Context context, String url) {
    Bitmap bitmap = null;
    if (url != null && url.indexOf("/") > 0) {
      String filename = url.substring(url.lastIndexOf("/") + 1);
      String dlpath = context.getCacheDir().getAbsolutePath();
      File f = new File(dlpath, filename);
      if (f.exists()) {
        BitmapFactory.decodeFile(f.getAbsolutePath());
      }
    }
    return bitmap;
  }

  public static boolean downloadFile(Context context, String url) {
    boolean success = false;
    if (url != null && url.indexOf("/") > 0) {
      String filename = url.substring(url.lastIndexOf("/") + 1);

      try {
        URL u = new URL(url);
        HttpURLConnection uc = (HttpURLConnection) u.openConnection();
        uc.setRequestProperty("Accept", "image/*");

        InputStream in = uc.getInputStream();

        if (in != null) {
          int length = uc.getContentLength();

          String dlpath = context.getCacheDir().getAbsolutePath();
          File f = new File(dlpath, filename);
          FileOutputStream outfile = new FileOutputStream(f);
          byte[] buffer = new byte[1024];
          int len;

          while ((len = in.read(buffer)) != -1) {
            outfile.write(buffer, 0, len);
          }

          in.close();
          outfile.close();

          File file = new File(dlpath, filename);
          if (file.length() < length && file.exists()) {
            file.delete();
          }
          else {
            success = true;
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    return success;
  }
}
