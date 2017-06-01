package com.anax.testapp.networking;

import com.anax.testapp.model.VideoBuilder;
import com.anax.testapp.model.serialization.VideosDeserializer;
import com.anax.testapp.model.Video;
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

//	private final static VideosApi sInstance = new VideosApi();

	private VideosInterface mVideosInterface;

	public VideosApi() {

		Gson gson = new GsonBuilder()
				.registerTypeHierarchyAdapter(VideoBuilder[].class, new VideosDeserializer())
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

	private static final Func1<VideoBuilder[], Observable<VideoBuilder>> SPLIT = new Func1<VideoBuilder[], Observable<VideoBuilder>>() {
		@Override
		public Observable<VideoBuilder> call(VideoBuilder[] videoBuilders) {
			return Observable.from(videoBuilders);
		}
	};

	private static Func1<VideoBuilder, Video> BUILD = new Func1<VideoBuilder, Video>() {
		@Override
		public Video call(VideoBuilder videoBuilder) {
			return videoBuilder.build();
		}
	};
}
