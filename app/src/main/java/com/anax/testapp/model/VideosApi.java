package com.anax.testapp.model;

import com.anax.testapp.model.serialization.VideosDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author yuriiostrovskyi on 5/31/17.
 */

public class VideosApi {

	/**
	 * Base url needed for retrofit
	 */
	public static final String BASE_URL =
			"http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/";

	private VideosInterface mVideosInterface;

	public VideosApi() {

		Gson gson = new GsonBuilder()
				.registerTypeHierarchyAdapter(VideoPojo[].class, new VideosDeserializer())
				.create();

		mVideosInterface = new Retrofit.Builder()
				.baseUrl(BASE_URL)
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.addConverterFactory(GsonConverterFactory.create(gson))
				.build()
				.create(VideosInterface.class);
	}

	public Observable<Video> getVideos() {
		return mVideosInterface.getVideos()
				.flatMap(SPLIT)
				.map(BUILD)
				.subscribeOn(Schedulers.computation());
	}

	private static final Func1<VideoPojo[], Observable<VideoPojo>> SPLIT = new Func1<VideoPojo[], Observable<VideoPojo>>() {
		@Override
		public Observable<VideoPojo> call(VideoPojo[] videoBuilders) {
			return Observable.from(videoBuilders);
		}
	};

	private static Func1<VideoPojo, Video> BUILD = new Func1<VideoPojo, Video>() {
		@Override
		public Video call(VideoPojo videoBuilder) {
			return videoBuilder.build();
		}
	};
}
