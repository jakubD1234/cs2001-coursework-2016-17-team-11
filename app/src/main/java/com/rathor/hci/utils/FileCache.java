package com.rathor.hci.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

import static android.os.Environment.isExternalStorageEmulated;

public class FileCache {

	private File cacheDir;


	public FileCache(Context context) {
		// Find the dir to save cached images


        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !isExternalStorageEmulated())
        {
            cacheDir = new File(
                    Environment.getExternalStorageDirectory(),
                    "LazyList");
        } else {
            cacheDir =  new File(context.getCacheDir().toString()+"/LazyList");
        }
		/*if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED))
			cacheDir = new File(
					android.os.Environment.getExternalStorageDirectory(),
					"LazyList");
		else
			cacheDir =  new File(context.getCacheDir().toString()+"/LazyList");
*/
		if (!cacheDir.exists())
			cacheDir.mkdirs();
	}



    public File getFile(String url) {
		// I identify images by hashcode. Not a perfect solution, good for the
		// demo.
		String filename = String.valueOf(url.hashCode());
		// Another possible solution (thanks to grantland)
		// String filename = URLEncoder.encode(url);
		File f = new File(cacheDir, filename);
		return f;

	}

	public void clear() {
		File[] files = cacheDir.listFiles();
		if (files == null)
			return;
		for (File f : files)
			f.delete();
	}

}