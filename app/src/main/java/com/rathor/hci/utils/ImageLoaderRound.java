package com.rathor.hci.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.rathor.hci.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageLoaderRound {
    private static Context mContext;
    private boolean mIsImageRound = false;
    private  MemoryCache memoryCache = new MemoryCache();
    private static FileCache fileCache;
    private Map<ImageView, String> imageViews = Collections.synchronizedMap(new WeakHashMap<ImageView, String>());
   private static ExecutorService executorService;
    Handler handler = new Handler();// handler to display images in UI thread
    private static ProgressBar mProgressbar;
    int stub_id;
    int widht;


    private static ImageLoaderRound imageLoaderRound;
    public static ImageLoaderRound getInstance(Context cntx) {
        if (imageLoaderRound == null) {
            imageLoaderRound = new ImageLoaderRound(cntx, R.mipmap.avtar);
            fileCache = new FileCache(cntx);
            executorService = Executors.newFixedThreadPool(5);
            mContext = cntx;
            mProgressbar = new ProgressBar(mContext, null, android.R.attr.progressBarStyleLarge);
        }
        return imageLoaderRound;
    }

     public ImageLoaderRound(Context context, int stub_idx) {
        fileCache = new FileCache(context);
        executorService = Executors.newFixedThreadPool(5);
        stub_id = stub_idx;
        if (mContext == null)
            mContext = context;

        if (mProgressbar == null)
            mProgressbar = new ProgressBar(mContext, null, android.R.attr.progressBarStyleLarge);
    }



    public void DisplayImage(String url, ImageView imageView, int widht, ProgressBar bar, boolean isImageRound) {
        if (bar == null) {
            bar = mProgressbar;
            bar.setVisibility(View.GONE);
        }

        this.widht = widht;
        this.mIsImageRound = isImageRound;

        imageViews.put(imageView, url);
        Bitmap bitmap = memoryCache.get(url);
        if (bitmap != null) {
            bar.setVisibility(View.GONE);
            if (mIsImageRound) {
                imageView.setImageBitmap(RoundedImageView.getCroppedBitmap(bitmap, 100));
            } else {
                imageView.setImageBitmap(bitmap);
            }
        } else {
            queuePhoto(url, imageView, widht, bar);
            System.out.println("testing" + mContext + "   " + stub_id);
            Bitmap bmap = BitmapFactory.decodeResource(mContext.getResources(), stub_id);
            if (mIsImageRound == true) {
                imageView.setImageBitmap(RoundedImageView.getCroppedBitmap(bmap, 100));
            } else {
                imageView.setImageBitmap(bmap);
            }

        }
    }

    private void queuePhoto(String url, ImageView imageView, int w, ProgressBar bar) {
        PhotoToLoad p = new PhotoToLoad(url, imageView, bar);
        executorService.submit(new PhotosLoader(p, w));
    }

    public Bitmap getBitmap(String url, int w) {
        File f = fileCache.getFile(url);

        // from SD cache
        Bitmap b = decodeFile(f, w);
        if (b != null)
            return b;

        // from web
        try {
            Bitmap bitmap = null;

            String ALLOWED_URI_CHARS = "@#&=*+-_.,:!?()/~'%";
            String urlEncoded = Uri.encode(url, ALLOWED_URI_CHARS);
            URL imageUrl = new URL(urlEncoded);
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.setInstanceFollowRedirects(true);
            InputStream is = conn.getInputStream();
            OutputStream os = new FileOutputStream(f);
            Utils.CopyStream(is, os);
            os.close();
            conn.disconnect();
            bitmap = decodeFile(f, w);
            return bitmap;
        } catch (Throwable ex) {
            ex.printStackTrace();
            if (ex instanceof OutOfMemoryError)
                memoryCache.clear();
            return null;
        }
    }

    // decodes image and scales it to reduce memory consumption
    private Bitmap decodeFile(File f, int w) {
        try {
            // decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            FileInputStream stream1 = new FileInputStream(f);
            BitmapFactory.decodeStream(stream1, null, o);
            stream1.close();
            // Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE = w;
            System.out.println("screen wdth " + widht);
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            System.out.println("image with === " + width_tmp);
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            // decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            FileInputStream stream2 = new FileInputStream(f);
            Bitmap bitmap = BitmapFactory.decodeStream(stream2, null, o2);
            stream2.close();
            return bitmap;
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Task for the queue
    private class PhotoToLoad {
        public String url;
        public ImageView imageView;
        public ProgressBar bar;

        public PhotoToLoad(String u, ImageView i, ProgressBar progressBar) {
            url = u;
            imageView = i;
            bar = progressBar;
        }
    }

    class PhotosLoader implements Runnable {
        PhotoToLoad photoToLoad;
        int w;

        PhotosLoader(PhotoToLoad photoToLoad, int w) {
            this.photoToLoad = photoToLoad;
            this.w = w;

        }

        @Override
        public void run() {
            try {
                if (imageViewReused(photoToLoad))
                    return;
                Bitmap bmp = getBitmap(photoToLoad.url, w);
                memoryCache.put(photoToLoad.url, bmp);
                if (imageViewReused(photoToLoad))
                    return;
                BitmapDisplayer bd = new BitmapDisplayer(bmp, photoToLoad);
                handler.post(bd);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    boolean imageViewReused(PhotoToLoad photoToLoad) {
        String tag = imageViews.get(photoToLoad.imageView);
        return tag == null || !tag.equals(photoToLoad.url);
    }

    // Used to display bitmap in the UI thread
    class BitmapDisplayer implements Runnable {
        Bitmap bitmap;
        PhotoToLoad photoToLoad;

        public BitmapDisplayer(Bitmap b, PhotoToLoad p) {
            bitmap = b;
            photoToLoad = p;
        }

        public void run() {
            if (imageViewReused(photoToLoad))
                return;
            if (bitmap != null) {
                if (mIsImageRound) {
                    photoToLoad.imageView.setImageBitmap(RoundedImageView.getCroppedBitmap(bitmap, 100));
                } else {
                    photoToLoad.imageView.setImageBitmap(bitmap);
                }

            } else
                photoToLoad.imageView.setImageResource(stub_id);

            photoToLoad.bar.setVisibility(View.GONE);
        }
    }

    public void clearCache() {
        memoryCache.clear();
        fileCache.clear();
    }

}
