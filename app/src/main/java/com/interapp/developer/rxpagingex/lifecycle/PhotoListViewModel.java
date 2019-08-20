package com.interapp.developer.rxpagingex.lifecycle;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.interapp.developer.rxpagingex.MainApplication;
import com.interapp.developer.rxpagingex.model.BasicPhoto;
import com.interapp.developer.rxpagingex.model.ErrorModel;
import com.interapp.developer.rxpagingex.network.IFlickrService;
import com.interapp.developer.rxpagingex.network.NetworkState;
import com.interapp.developer.rxpagingex.network.PhotoSearchRetriever;
import com.interapp.developer.rxpagingex.paging.PhotoListDataSourceFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.interapp.developer.rxpagingex.util.AppUtil.PAGE_SIZE;

/**
 * Created by Preethi Valsalan on 2019-08-19
 */
public class PhotoListViewModel extends AndroidViewModel {
    private static final String TAG = PhotoListViewModel.class.getSimpleName();
    private MutableLiveData<ErrorModel> networkState;

    private LiveData<PagedList<BasicPhoto>> photoListLiveData;
    private PhotoListDataSourceFactory<Integer, BasicPhoto> photoListDataFactory;
    private PhotoSearchRetriever searchRetriever = null;

    public PhotoListViewModel(@NonNull Application application) {
        super(application);

        networkState = new MutableLiveData<>();
        IFlickrService service = ((MainApplication)application).getConnectionComponent().flickrService();
        searchRetriever = new PhotoSearchRetriever(service);
        initPaging();
    }

    private void initPaging() {

        Executor executor = Executors.newFixedThreadPool(1);

        photoListDataFactory
                = new PhotoListDataSourceFactory<Integer, BasicPhoto>(searchRetriever, networkState);

        PagedList.Config pagedListConfig = new PagedList.Config.Builder()
                .setPageSize(PAGE_SIZE)
                .setPrefetchDistance(PAGE_SIZE *2)
                .setEnablePlaceholders(true)
                .build();

        photoListLiveData = (new LivePagedListBuilder<Integer, BasicPhoto>(photoListDataFactory, pagedListConfig))
                .setFetchExecutor(executor)
                .build();

    }

    public void clear() {
        Log.d(TAG, "clear ############");
        searchRetriever.clear();
    }

    /**
     * set the textso the that paging reloads  data
     * @param text
     */
    public void setSearchText(String text) {
        if(!text.equalsIgnoreCase(searchRetriever.getSearchText())) {
            Log.d(TAG, "Search Text " + text);
            searchRetriever.setSearchText(text);
            photoListDataFactory.getDataSource().invalidate();
        }

    }

    public LiveData<ErrorModel> getNetworkState() {
        return networkState;
    }

    public LiveData<PagedList<BasicPhoto>> getPhotoListLiveData() {
        return photoListLiveData;
    }
}
