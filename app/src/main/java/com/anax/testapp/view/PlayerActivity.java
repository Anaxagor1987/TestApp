package com.anax.testapp.view;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.VideoView;

import com.anax.testapp.R;
import com.anax.testapp.model.Video;

/**
 * @author yuriiostrovskyi on 5/31/17.
 */

public class PlayerActivity extends AppCompatActivity {
	public static final String VIDEO_TAG = "VIDEO_TAG";
	public static final String POSITION_TAG = "POSITION_TAG";

	private VideoView mVideoView;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player);

		mVideoView = (VideoView) findViewById(R.id.video_view);

		Video video = getIntent().getParcelableExtra("VIDEO_TAG");

		mVideoView.setVideoURI(Uri.parse(video.sources.get(0)));
		if (savedInstanceState != null) {
			int position = savedInstanceState.getInt(POSITION_TAG);
			mVideoView.seekTo(position);
		}
		mVideoView.start();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putInt(POSITION_TAG, mVideoView.getCurrentPosition());
	}
}
