package com.interapp.developer.rxpagingex.paging;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.ItemKeyedDataSource;

import com.interapp.developer.rxpagingex.model.BasicPhoto;
import com.interapp.developer.rxpagingex.model.ErrorModel;
import com.interapp.developer.rxpagingex.network.INetwokCallBack;
import com.interapp.developer.rxpagingex.network.NetworkState;
import com.interapp.developer.rxpagingex.network.PhotoSearchRetriever;

import java.util.List;

/**
 * Created by Preethi Valsalan on 8/14/19
 * DataSource for paging photos
 */
public class PhotoListPagingDataSource extends ItemKeyedDataSource<String, BasicPhoto> {

    private static final String TAG = PhotoListPagingDataSource.class.getSimpleName();

    private PhotoSearchRetriever photoListHandler = null;
    private MutableLiveData<ErrorModel> networkState = null;

    private int nextPage = 0;


    /**
     * Accepts the data retriver which access server services as parameter.
     * @param dataRetriever
     */
    public PhotoListPagingDataSource(PhotoSearchRetriever dataRetriever, MutableLiveData state) {
        photoListHandler = dataRetriever;
        this.networkState = state;
    }

    @Override
    public void invalidate() {
        super.invalidate();
        Log.d(TAG, "invalidate");
        this.nextPage = 0;
        photoListHandler.invalidate();
        networkState.postValue(new ErrorModel(NetworkState.FAILED));
    }


    @Override
    public void loadInitial(@NonNull LoadInitialParams<String> params, @NonNull final LoadInitialCallback<BasicPhoto> callback) {
        networkState.postValue(new ErrorModel(NetworkState.LOADING));
        nextPage = 1;
        Log.d(TAG, "loadInitial  -------------------------------------");
        photoListHandler.getData(new INetwokCallBack<List<BasicPhoto>>() {

            @Override
            public void displayResult(List<BasicPhoto> dataList, int pageNo) {
                nextPage = 2;
                callback.onResult(dataList, 0, dataList.size());
                networkState.postValue(new ErrorModel(NetworkState.SUCCESS));
            }

            @Override
            public void displayError(ErrorModel error) {
                networkState.setValue(error);
            }
         }, nextPage);
    }


    @Override
    public void loadBefore(@NonNull LoadParams<String> params, @NonNull LoadCallback<BasicPhoto> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<String> params, @NonNull final LoadCallback<BasicPhoto> callback) {
        networkState.postValue(new ErrorModel(NetworkState.LOADING));

        photoListHandler.getData(new INetwokCallBack<List<BasicPhoto>>() {
            @Override
            public void displayResult(List<BasicPhoto> dataList, int pageNo) {
                nextPage = nextPage + 1;
                callback.onResult(dataList);
                networkState.postValue(new ErrorModel(NetworkState.SUCCESS));
            }

            @Override
            public void displayError(ErrorModel error) {
                networkState.setValue(error);
            }

        }, nextPage);
    }


    @NonNull
    @Override
    public String getKey(@NonNull BasicPhoto item) {
        return item.getId();
    }
}
