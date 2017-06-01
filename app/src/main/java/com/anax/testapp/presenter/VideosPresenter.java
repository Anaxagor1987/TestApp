package com.anax.testapp.presenter;

import android.util.Log;

import com.anax.testapp.model.Video;
import com.anax.testapp.networking.VideosApi;
import com.anax.testapp.view.VideosView;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;
import rx.subscriptions.Subscriptions;

/**
 * @author yuriiostrovskyi on 5/31/17.
 */

public class VideosPresenter extends Subscriber<List<Video>> {

	private static final String TAG = "VideosPresenter";

	private VideosApi mVideosApi;
	private VideosView mVideosView;

	private Subscription mApiSubscription = Subscriptions.unsubscribed();
	private Subscription mSubjectSubscription = Subscriptions.unsubscribed();
	private BehaviorSubject<List<Video>> mSubject = BehaviorSubject.create();

	private static VideosPresenter sInstance = new VideosPresenter();

	public static VideosPresenter getInstance() {
		return sInstance;
	}

	private VideosPresenter() {
		mVideosApi = new VideosApi();
		loadVideos(mVideosApi);
	}

	@Override
	public void onCompleted() {
		Log.d(TAG, "Loading completed");
	}

	@Override
	public void onError(Throwable e) {
		Log.d(TAG, "Error happened", e);
		mVideosView.showError();
	}

	@Override
	public void onNext(List<Video> videos) {
		Log.d(TAG, "onNext: Videos: " + videos);
		mSubject.onNext(videos);
	}

	public void bindView(VideosView videosView) {
		mVideosView = videosView;
		mSubjectSubscription.unsubscribe();
		mSubjectSubscription = mSubject
				.observeOn(AndroidSchedulers.mainThread())
				.subscribeOn(Schedulers.io())
				.subscribe(videosView);
		mVideosView.showProgress();
	}

	public void unbindView() {
		mVideosView = null;
		mSubjectSubscription.unsubscribe();
	}

	public void onVideoClicked(Video video) {
		mVideosView.playVideo(video);
	}

	public void onRetryClicked() {
		loadVideos(mVideosApi);
	}

	private void loadVideos(VideosApi api) {
		Log.d(TAG, "loadVideos: ");
		mApiSubscription.unsubscribe();
		mApiSubscription = api.getVideos()
				.observeOn(AndroidSchedulers.mainThread())
//				.filter(FILTER)
				.toList()
				.subscribe(this);
	}

	private static Func1<Video, Boolean> FILTER = new Func1<Video, Boolean>() {

		@Override
		public Boolean call(Video video) {
			return !video.sources.isEmpty();
		}
	};
}
