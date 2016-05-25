package cn.edu.shu.scommunity.utils;


import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import cn.edu.shu.scommunity.BuildConfig;
import cn.edu.shu.scommunity.R;

public class ImageLoaderDisplay {

	private static ImageLoader imageLoader;
	private static DisplayImageOptions options;

	public static void displayImage(Context context, String url,
			ImageView imageView) {
		try{
			initImageLoader(context);
			imageLoader.displayImage(url, imageView, options);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	private static void initImageLoader(Context context) {
		ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO);
		if (BuildConfig.DEBUG) {
			builder.writeDebugLogs();
		}
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.ic_launcher)
				.showImageForEmptyUri(R.drawable.ic_launcher)
				.showImageOnFail(R.drawable.ic_launcher).cacheInMemory(true)
				.cacheOnDisc(true).bitmapConfig(Bitmap.Config.ARGB_8888)
				.build();
		if(imageLoader==null)
		imageLoader.getInstance().init(builder.build());
		imageLoader = ImageLoader.getInstance();
	}
	
	public static void clear(){
		imageLoader.clearDiscCache();
		imageLoader.clearMemoryCache();
	}
	
}
