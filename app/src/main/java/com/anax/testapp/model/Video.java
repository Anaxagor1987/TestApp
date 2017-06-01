package com.anax.testapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.anax.testapp.networking.VideosApi;

import java.util.List;

public class Video implements Parcelable {
	public final String title;
	public final String studio;
	public final String thumbnailUrl;
	public final List<String> sources;

	public Video(String title, String studio, String thumbnailUrl, List<String> sources) {
		this.title = title;
		this.studio = studio;
		this.thumbnailUrl = VideosApi.BASE_URL + thumbnailUrl;
		this.sources = sources;
	}

	protected Video(Parcel in) {
		title = in.readString();
		studio = in.readString();
		thumbnailUrl = in.readString();
		sources = in.createStringArrayList();
	}

	public static final Creator<Video> CREATOR = new Creator<Video>() {
		@Override
		public Video createFromParcel(Parcel in) {
			return new Video(in);
		}

		@Override
		public Video[] newArray(int size) {
			return new Video[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(title);
		dest.writeString(studio);
		dest.writeString(thumbnailUrl);
		dest.writeStringList(sources);
	}
}
