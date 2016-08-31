package com.dinkevin.xui.util;

import java.io.File;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * 图片工具类
 * 
 * @author chengpengfei
 *
 */
public final class BitmapUtil {

	private BitmapUtil() {}

	/**
	 * 压缩图片
	 * @param localFilePath 本地文件路径
	 * @return 压缩后的图片大小
	 */
	public static Bitmap comparess(String localFilePath) {
		File file = new File(localFilePath);
		if (file.exists()) {
			final BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(localFilePath, options);
			// Calculate inSampleSize
			options.inSampleSize = calculateInSampleSize(options, 480, 480);

			// Decode bitmap with inSampleSize set
			options.inJustDecodeBounds = false;
			return BitmapFactory.decodeFile(localFilePath, options);
		}
		return null;
	}
	
	/**
	 * 压缩图片至指定的高度与宽度
	 * @param imagePath 本地文件路径
	 * @param requireWidth 指定的高度
	 * @param requireHeight 指定的宽度
	 * @return
	 */
	public static Bitmap comparess(String imagePath,int requireWidth,int requireHeight){
		File file = new File(imagePath);
		if (file.exists()) {
			final BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(imagePath, options);
			
			// Calculate inSampleSize
			options.inSampleSize = calculateInSampleSize(options, requireWidth, requireHeight);

			// Decode bitmap with inSampleSize set
			options.inJustDecodeBounds = false;
			return BitmapFactory.decodeFile(imagePath, options);
		}
		return null;
	}

	private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {

		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			// Calculate ratios of height and width to requested height and
			// width
			final int heightRatio = Math.round((float) height / (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			// Choose the smallest ratio as inSampleSize value, this will
			// guarantee
			// a final image with both dimensions larger than or equal to the
			// requested height and width.
			inSampleSize = heightRatio < widthRatio ? widthRatio : heightRatio;
		}

		return inSampleSize;
	}
}
