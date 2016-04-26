package com.example.chuanceynotes;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.VideoView;

public class AddContent extends Activity implements OnClickListener {

	private String val;
	private Button savebtn, deletebtn;
	private EditText ettext;
	private ImageView c_img;
	private VideoView v_video;
	private NotesDB notesDB;
	private SQLiteDatabase dbWriter;
	private File phoneFile, videoFile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addcontent);
		val = getIntent().getStringExtra("flag");
		savebtn = (Button) findViewById(R.id.save);
		deletebtn = (Button) findViewById(R.id.delete);
		ettext = (EditText) findViewById(R.id.ettext);
		c_img = (ImageView) findViewById(R.id.c_img);
		v_video = (VideoView) findViewById(R.id.c_video);
		savebtn.setOnClickListener(this);
		deletebtn.setOnClickListener(this);
		notesDB = new NotesDB(this);
		dbWriter = notesDB.getWritableDatabase();
		initView();
	}

	public void initView() {
		if (val.equals("1")) {
			c_img.setVisibility(View.GONE);
			v_video.setVisibility(View.GONE);
		}
		if (val.equals("2")) {
			c_img.setVisibility(View.VISIBLE);
			v_video.setVisibility(View.GONE);
			Intent iimg = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			phoneFile = new File(Environment.getExternalStorageDirectory()
					.getAbsoluteFile() + "/0_img/" + getTime() + ".jpg");
			iimg.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(phoneFile));
			startActivityForResult(iimg, 1);
		}
		if (val.equals("3")) {
			c_img.setVisibility(View.GONE);
			v_video.setVisibility(View.VISIBLE);
			Intent video = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
			videoFile = new File(Environment.getExternalStorageDirectory()
					.getAbsoluteFile() + "/0_img/" + getTime() + ".mp4");
			video.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(videoFile));
			startActivityForResult(video, 2);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.save:
			addDB();
			finish();
			break;

		case R.id.delete:
			finish();
			break;
		}
	}

	public void addDB() {
		ContentValues cv = new ContentValues();
		cv.put(NotesDB.CONTENT, ettext.getText().toString());
		cv.put(NotesDB.TIME, getTime());
		cv.put(NotesDB.PATH, phoneFile + "");
		cv.put(NotesDB.VIDEO, videoFile + "");
		dbWriter.insert(NotesDB.TABLE_NAME, null, cv);
	}

	private String getTime() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyƒÍMM‘¬dd»’");
		Date curDate = new Date();
		String str = format.format(curDate);
		return str;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
//		super.onActivityResult(requestCode, resultCode, data);
//		if (requestCode == 1) {
//			Bitmap bitmap = BitmapFactory.decodeFile(phoneFile
//					.getAbsolutePath());
//			c_img.setImageBitmap(bitmap);
//		}
//		if (requestCode == 2) {
//			v_video.setVideoURI(Uri.fromFile(videoFile));
//			v_video.start();
//		}
		
		super.onActivityResult(requestCode, resultCode, data);
    		if (requestCode == 1) {
    			Bitmap bitmap = BitmapFactory.decodeFile(phoneFile.getAbsolutePath());
    			c_img.setImageBitmap(bitmap);
    		}
    		else if(requestCode == 2){
			Bitmap bitmap = BitmapFactory.decodeFile(videoFile.getAbsolutePath());
			v_video.setVideoURI(Uri.fromFile(videoFile));
			v_video.start();
        }
    }
}
