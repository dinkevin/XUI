package com.dinkevin.xui.view;

import java.io.File;

import com.dinkevin.xui.R;
import com.dinkevin.xui.storage.CacheStorage;
import com.dinkevin.xui.util.TimeUtil;
import com.dinkevin.xui.util.ViewFinder;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

/**
 * 相册、相机底部弹出菜单
 * @author chengpengfei
 */
public class PictureSelectedPopupMenu extends PopupMenu implements View.OnClickListener {

	Button btn_cancel, btn_gallery, btn_camera;
	String cameraTakePicturePath = null;	// 照片拍摄临时地址

	@SuppressLint("InflateParams")
	public PictureSelectedPopupMenu(View locationView) {
		super();

		this.locationView = locationView;
		this.contentView = LayoutInflater.from(locationView.getContext()).inflate(R.layout.xui_view_picture_menu, null);
		this.direction = Direction.BUTTOM;

		viewFinder = new ViewFinder(contentView);
		btn_camera = viewFinder.findViewById(R.id.btn_picture_menu_camera);
		btn_camera.setOnClickListener(this);
		btn_gallery = viewFinder.findViewById(R.id.btn_picture_menu_gallery);
		btn_gallery.setOnClickListener(this);
		btn_cancel = viewFinder.findViewById(R.id.btn_picture_menu_cancel);
		btn_cancel.setOnClickListener(this);

		initial();
	}

	public static final int GALLERY_CODE = 100;
	public static final int CAMERA_CODE = 101;
	
	/**
	 * 获取相机拍摄的图片临时路径
	 * @return
	 */
	public String getCameraTakePicturePath(){
		return cameraTakePicturePath;
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		Activity activity = (Activity) locationView.getContext();

		cameraTakePicturePath = null;
		if (v.getId() == R.id.btn_picture_menu_camera) 
		{
			window.dismiss();
			cameraTakePicturePath = CacheStorage.getInstance().getDirRoot() + System.currentTimeMillis() + CacheStorage.SUFFIX;
			intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 10);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(cameraTakePicturePath)));
			activity.startActivityForResult(intent, CAMERA_CODE);
		} 
		else if (v.getId() == R.id.btn_picture_menu_gallery) {
			window.dismiss();
			intent.setAction(Intent.ACTION_GET_CONTENT);
			intent.setType("image/*");
			activity.startActivityForResult(Intent.createChooser(intent, "选择图片"), GALLERY_CODE);
		}
		else {
			window.dismiss();
		}
	}
	
	/**
	 * 获取从相册中选中的图片路径
	 * @param data onActivityResult 中系统回调的 Intent
	 * @return
	 */
	public String getSelectedPicturePathFromAlbum(Intent data){
		
		if (null == data || null == contentView) return null;
		ContentResolver resolver = contentView.getContext().getContentResolver();
		// 获得图片
		Uri originalUri = data.getData();
		// 这里开始的第二部分，获取图片的路径：
		String[] proj = { MediaStore.Images.Media.DATA };
		// 好像是android多媒体数据库的封装接口，具体的看Android文档
		Cursor cursor = resolver.query(originalUri, proj, null, null, null);
		if (cursor != null) {
			// 按我个人理解 这个是获得用户选择的图片的索引值
			int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			// 将光标移至开头 ，这个很重要，不小心很容易引起越界
			cursor.moveToFirst();
			// 最后根据索引值获取图片路径
			return cursor.getString(column_index);
		}
		return originalUri.getPath();
	}
	
	/**
	 * 将相机拍摄的缩略图缓存到本地并返回本地缓存路径，如想获取相机拍摄原图片，请调用 {{@link #getCameraTakePicturePath()}
	 * @param data 在 Activity 的实现类中 onActivityResult 中得到的 intent
	 * @return
	 */
	public String getThumbnailPathFromCamera(Intent data) {

		if (null == data) return null;
		// 防止没有返回结果
		if (data.getData() != null || data.getExtras() != null) {
			Bitmap photo = null;
			if (data.getData() != null) {
				photo = BitmapFactory.decodeFile(data.getData().getPath());  // 拿到缩略图片
			}

			if (photo == null) {
				if (data.getExtras() != null) {
					photo = (Bitmap) data.getExtras().get("data");
				}
			}
			return CacheStorage.getInstance().put(TimeUtil.getCurrentTimeStamp(),photo);
		}
		return null;
	}
}
