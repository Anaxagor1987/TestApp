package com.anax.testapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author yuriiostrovskyi on 5/31/17.
 */
public class VideoPojo {

	@SerializedName("title")
	private String mTitle;

	@SerializedName("image-780x1200")
	private String mThumbnailBig;

	@SerializedName("studio")
	private String mStudio;

	@SerializedName("sources")
	private List<String> mSources;

	Video build() {
		return new Video(mTitle, mStudio, mThumbnailBig, mSources);
	}
}
