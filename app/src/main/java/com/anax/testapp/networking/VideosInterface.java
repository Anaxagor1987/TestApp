package com.anax.testapp.networking;

import com.anax.testapp.model.VideoBuilder;

import retrofit2.http.GET;
import rx.Observable;

/**
 * @author yuriiostrovskyi on 5/31/17.
 */
public interface VideosInterface {

	@GET("videos-enhanced-c.json")
	Observable<VideoBuilder[]> getVideos();
}
