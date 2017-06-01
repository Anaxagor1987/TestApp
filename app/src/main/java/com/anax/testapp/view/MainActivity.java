package com.anax.testapp.view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.anax.testapp.R;
import com.anax.testapp.model.Video;
import com.anax.testapp.presenter.VideosPresenter;
import com.anax.testapp.view.adapter.VideosAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity implements VideosView,
		DialogInterface.OnClickListener, VideosAdapter.OnVideoClickListener {

	private static final String TAG = "MainActivity";

	private AlertDialog mRetryDialog;
	private ProgressDialog mProgressDialog;

	private VideosAdapter mAdapter;
	private RecyclerView mRecyclerView;

	private VideosPresenter mVideosPresenter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mAdapter = new VideosAdapter(this);

		mRecyclerView = new RecyclerView(this);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		mRecyclerView.setAdapter(mAdapter);

		setContentView(mRecyclerView);

		mRetryDialog = createAlertDialog();
		mProgressDialog = new ProgressDialog(this);

		mVideosPresenter = VideosPresenter.getInstance();
	}

	@Override
	public void onStart() {
		super.onStart();

		mVideosPresenter.bindView(this);
	}

	@Override
	protected void onStop() {
		super.onStop();

		mVideosPresenter.unbindView();
	}

	@Override
	public void call(List<Video> videos) {
		setItems(videos);
	}

	@Override
	public void showProgress() {
		Log.d(TAG, "showProgress:");
		showProgressDialog();
	}

	@Override
	public void playVideo(Video video) {
		Intent intent = new Intent(this, PlayerActivity.class);
		intent.putExtra(PlayerActivity.VIDEO_TAG, video);
		startActivity(intent);
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		mVideosPresenter.onRetryClicked();
	}

	@Override
	public void onClick(Video video) {
		mVideosPresenter.onVideoClicked(video);
	}

	public void showError() {
		Log.d(TAG, "showError:");
		mProgressDialog.dismiss();
		if (!mRetryDialog.isShowing()) {
			mRetryDialog.show();
		}
	}

	private void setItems(List<Video> videos) {
		Log.d(TAG, "setItems: " + videos);
		mAdapter.setItems(videos);
		dismissProgressDialog();
	}

	private void showProgressDialog() {
		mProgressDialog.show();
		mRetryDialog.dismiss();
		mRecyclerView.animate().alpha(0);
	}

	private void dismissProgressDialog() {
		mProgressDialog.dismiss();
		mRecyclerView.animate().alpha(1);
	}

	private AlertDialog createAlertDialog() {
		return new AlertDialog.Builder(this)
				.setTitle(R.string.error_title)
				.setPositiveButton(R.string.retry_message, this)
				.create();
	}
}
