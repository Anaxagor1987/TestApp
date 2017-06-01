package com.anax.testapp.view;

import com.anax.testapp.model.Video;

import java.util.List;

import rx.functions.Action1;

public interface VideosView extends Action1<List<Video>> {

	void showProgress();

	void showError();

	void playVideo(Video video);
}
