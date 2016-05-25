package android_hddl_framework.util;

import com.example.android_hddl_framework.BuildConfig;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

public class ImageLoaderDisplay {

	private static ImageLoader imageLoader;
	private static DisplayImageOptions options;

	public static void displayImage(Context context, String url,ImageView imageView,int drawable) {
		initImageLoader(context,drawable);
		imageLoader.displayImage(url, imageView, options);
	}

	public static void initImageLoader(Context context,int drawable) {
		ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO);
		if (BuildConfig.DEBUG) {
			builder.writeDebugLogs();
		}
		options = new DisplayImageOptions.Builder()
				.showStubImage(drawable)
				.showImageForEmptyUri(drawable)
				.showImageOnFail(drawable).cacheInMemory(true)
				.cacheOnDisc(true).bitmapConfig(Bitmap.Config.ARGB_8888)
				.build();
		if(imageLoader==null)
		imageLoader.getInstance().init(builder.build());
		imageLoader = ImageLoader.getInstance();
	}
	
	public static void clear(){
		imageLoader = ImageLoader.getInstance();
		if(imageLoader.isInited())
		imageLoader.clearMemoryCache();
	}
	
}
