package com.interapp.developer.rxpagingex.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.interapp.developer.rxpagingex.R;
import com.interapp.developer.rxpagingex.model.BasicPhoto;
import com.interapp.developer.rxpagingex.network.NetworkState;
import com.squareup.picasso.Picasso;

import static com.interapp.developer.rxpagingex.util.AppUtil.THUMBNAIL_PHOTO_API;

/**
 * Created by Preethi Valsalan on 8/14/19
 * Adapter class which has paging
 */
public class PhotoListAdapter extends PagedListAdapter<BasicPhoto, RecyclerView.ViewHolder> {

    private static final int LOADING_VIEW_TYPE = 1;
    private static final int ROW_VIEW_TYPE = 2;

    private NetworkState networkState;

    public PhotoListAdapter() {
        super(BasicPhoto.DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if(viewType == ROW_VIEW_TYPE) {
            View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.grid_row_photo, viewGroup, false);
            return new PhotoRowHolder(itemView);
        }
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.progress_bar, viewGroup, false);
        return new LoadHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if(viewHolder instanceof PhotoRowHolder) {
            BasicPhoto basicPhoto = getItem(i);
            viewHolder.itemView.setTag(basicPhoto);
            if(basicPhoto != null)
                ((PhotoRowHolder)viewHolder).setValues(basicPhoto);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (hasExtraRow() && position == getItemCount() - 1) {
            return LOADING_VIEW_TYPE;
        } else {
            return ROW_VIEW_TYPE;
        }
    }


    /**
     * Update the view with state change values
     * @param newNetworkState
     */
    public void setNetworkState(NetworkState newNetworkState) {
        NetworkState previousState = this.networkState;
        boolean hadExtraRow = hasExtraRow();
        this.networkState = newNetworkState;
        boolean hasExtraRowNow = hasExtraRow();
        if (hadExtraRow != hasExtraRowNow) {
            if (hadExtraRow) {
                notifyItemRemoved(getItemCount());
            } else {
                notifyItemInserted(getItemCount());
            }
        } else if (hasExtraRowNow && previousState != newNetworkState) {
            notifyItemChanged(getItemCount() - 1);
        }
    }


    private boolean hasExtraRow() {
        if (networkState != null && networkState != NetworkState.SUCCESS && networkState != NetworkState.FAILED) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * View holder class
     */
    public static final class PhotoRowHolder extends RecyclerView.ViewHolder {

        private TextView titleTxt;
        private ImageView photoView;
        public PhotoRowHolder(@NonNull View itemView) {
            super(itemView);
            photoView = itemView.findViewById(R.id.photoThumbNail);
            titleTxt = itemView.findViewById(R.id.photoThumbTxt);
//            photoView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    openSinglePhotoView();
//                }
//            });
        }

//        private void openSinglePhotoView() {
//            BasicPhoto basicPhoto = (BasicPhoto)this.itemView.getTag();
//            Intent openIntent = new Intent(this.itemView.getContext(), SinglePhotoActivity.class);
//            openIntent.putExtra("PHOTO_ID", basicPhoto.getId());
//            openIntent.putExtra("PHOTO_TITLE", basicPhoto.getTitle());
//            this.itemView.getContext().startActivity(openIntent);
//
//        }

        public void setValues(BasicPhoto model) {
            final String imageUri = String.format(THUMBNAIL_PHOTO_API, model.getFarm(), model.getServer(), model.getId(), model.getSecret());
            Picasso.with(this.itemView.getContext()).load(imageUri).fit().placeholder(R.drawable.photo_avatar).into(photoView);

            titleTxt.setText(model.getTitle());
        }
    }

    /**
     * Progress spinner to load more data fro the serer
     */
    private final class LoadHolder extends RecyclerView.ViewHolder {

        private ProgressBar progressBar = null;

        public LoadHolder(@NonNull View itemView) {
            super(itemView);
            this.progressBar = itemView.findViewById(R.id.progressBar);
            this.progressBar.setIndeterminate(true);
        }

    }
}
