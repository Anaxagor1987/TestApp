package com.anax.testapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author yuriiostrovskyi on 5/31/17.
 */

public class VideoBuilder {

	@SerializedName("title")
	private String mTitle;

	@SerializedName("image-780x1200")
	private String mThumbnailBig;

	@SerializedName("studio")
	private String mStudio;

	@SerializedName("sources")
	private List<String> mSources;

	public void setTitle(String title) {
		mTitle = title;
	}

	public void setThumbnailBig(String thumbnailBig) {
		mThumbnailBig = thumbnailBig;
	}

	public void setStudio(String studio) {
		mStudio = studio;
	}

	public void setSources(List<String> sources) {
		mSources = sources;
	}

	public Video build() {
		return new Video(mTitle, mStudio, mThumbnailBig, mSources);
	}
}
