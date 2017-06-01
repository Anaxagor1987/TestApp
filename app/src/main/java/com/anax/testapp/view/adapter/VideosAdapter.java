package com.anax.testapp.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anax.testapp.R;
import com.anax.testapp.model.Video;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

/**
 * @author yuriiostrovskyi on 5/31/17.
 */

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.ViewHolder> {
	public interface OnVideoClickListener {
		void onClick(Video video);
	}

	private OnVideoClickListener mListener;
	private List<Video> mItems = Collections.emptyList();

	public VideosAdapter(OnVideoClickListener listener) {
		mListener = listener;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item, parent, false));
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		holder.bind(mItems.get(position), mListener);
	}

	@Override
	public int getItemCount() {
		return mItems.size();
	}

	public void setItems(List<Video> items) {
		mItems = items;
		notifyDataSetChanged();
	}

	static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		private ImageView mThumbnail;
		private TextView mTitle;
		private TextView mStudio;

		private Video mVideo;
		private OnVideoClickListener mListener;

		private ViewHolder(View itemView) {
			super(itemView);

			mTitle = (TextView) itemView.findViewById(R.id.title);
			mStudio = (TextView) itemView.findViewById(R.id.studio);
			mThumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);

			itemView.setOnClickListener(this);
		}

		private void bind(Video video, OnVideoClickListener listener) {
			mVideo = video;
			mListener = listener;
			mTitle.setText(video.title);
			mStudio.setText(video.studio);
			Picasso.with(itemView.getContext()).load(video.thumbnailUrl).into(mThumbnail);
		}


		@Override
		public void onClick(View v) {
			if (mListener != null) {
				mListener.onClick(mVideo);
			}
		}
	}
}
