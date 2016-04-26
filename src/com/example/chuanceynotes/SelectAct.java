package com.example.chuanceynotes;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

public class SelectAct extends Activity implements OnClickListener {

	private static final Context context = null;
	private Button s_delete, s_back;
	private ImageView s_img;
	private VideoView s_video;
	private TextView s_tv;
	private NotesDB notesDB;
	private SQLiteDatabase dbWriter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select);
		// System.out.println(getIntent().getIntExtra(NotesDB.ID, 0));
		s_delete = (Button) findViewById(R.id.s_delete);
		s_back = (Button) findViewById(R.id.s_back);
		s_img = (ImageView) findViewById(R.id.s_img);
		s_video = (VideoView) findViewById(R.id.s_video);
		s_tv = (TextView) findViewById(R.id.s_tv);
		notesDB = new NotesDB(this);
		dbWriter = notesDB.getWritableDatabase();
		s_back.setOnClickListener(this);
		s_delete.setOnClickListener(this);

		if (getIntent().getStringExtra(NotesDB.PATH).equals("null")) {
			s_img.setVisibility(View.GONE);
		} else {
			s_img.setVisibility(View.VISIBLE);
		}
		if (getIntent().getStringExtra(NotesDB.VIDEO).equals("null")) {
			s_video.setVisibility(View.GONE);
		} else {
			s_video.setVisibility(View.VISIBLE);
		}
		
		s_tv.setText(getIntent().getStringExtra(NotesDB.TIME)+"\n\n\n"+getIntent().getStringExtra(NotesDB.CONTENT));
		Bitmap bitmap = BitmapFactory.decodeFile(getIntent().getStringExtra(
				NotesDB.PATH));
		s_img.setImageBitmap(bitmap);
		s_video.setVideoURI(Uri
				.parse(getIntent().getStringExtra(NotesDB.VIDEO)));
		s_video.start();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.s_delete:
			AlertDialog a = new AlertDialog.Builder(this).setTitle("温馨提示")
			.setPositiveButton("确定", 
					new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							// TODO Auto-generated method stub
							deleteDate();
							skip();
						}
					})
			.setNegativeButton("取消", null).setMessage("确认删除该数据吗？")
			.create();
			a.show();
			
            //builder.


			//finish();
			break;

		case R.id.s_back:
			finish();
			break;
		}
	}

	public void deleteDate() {
		dbWriter.delete(NotesDB.TABLE_NAME,
				"_id=" + getIntent().getIntExtra(NotesDB.ID, 0), null);
	}
	
	public void skip(){
		Intent intent = new Intent(this,MainActivity.class);
		startActivity(intent);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		
		super.onDestroy();
	}
}
