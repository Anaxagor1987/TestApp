package com.anax.testapp.model;

import retrofit2.http.GET;
import rx.Observable;

/**
 * @author yuriiostrovskyi on 5/31/17.
 */
interface VideosInterface {

	@GET("videos-enhanced-c.json")
	Observable<VideoPojo[]> getVideos();
}
