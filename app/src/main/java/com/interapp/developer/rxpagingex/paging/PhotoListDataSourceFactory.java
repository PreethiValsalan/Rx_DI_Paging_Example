package com.interapp.developer.rxpagingex.paging;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.interapp.developer.rxpagingex.model.BasicPhoto;
import com.interapp.developer.rxpagingex.model.ErrorModel;
import com.interapp.developer.rxpagingex.network.PhotoSearchRetriever;

/**
 * Created by Preethi Valsalan on 8/14/19
 * Factor creating data source for paging
 */
public class PhotoListDataSourceFactory<Key, Value> extends DataSource.Factory<Integer, BasicPhoto>{
    private PhotoListPagingDataSource photoListPagingDataSource;

    private MutableLiveData<PhotoListPagingDataSource> sourceLiveData;
    private PhotoSearchRetriever rxDataRetriever = null;
    private MutableLiveData<ErrorModel> networkState = null;

    /**
     * Constructor accpting classs used for network access. DI
     * @param dataRetriever
     */
    public PhotoListDataSourceFactory(PhotoSearchRetriever dataRetriever, MutableLiveData<ErrorModel> state) {
        this.rxDataRetriever = dataRetriever;
        this.sourceLiveData = new MutableLiveData<>();
        this.networkState = state;
    }

    @Override
    public DataSource create() {
        photoListPagingDataSource = new PhotoListPagingDataSource(this.rxDataRetriever, this.networkState);
        sourceLiveData.postValue(photoListPagingDataSource);
        return photoListPagingDataSource;
    }

    /**
     * used to invalidate
     * @return
     */
    public PhotoListPagingDataSource getDataSource() {
        return photoListPagingDataSource;
    }

}
