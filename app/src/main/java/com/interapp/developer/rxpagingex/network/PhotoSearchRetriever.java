package com.interapp.developer.rxpagingex.network;

import android.annotation.SuppressLint;
import android.util.Log;

import com.interapp.developer.rxpagingex.model.BasicPhoto;
import com.interapp.developer.rxpagingex.model.ErrorModel;
import com.interapp.developer.rxpagingex.model.PhotoListWrapper;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.interapp.developer.rxpagingex.util.AppUtil.API_KEY;
import static com.interapp.developer.rxpagingex.util.AppUtil.PAGE_SIZE;

/**
 * Created by Preethi Valsalan on 2019-08-19
 */
public class PhotoSearchRetriever {

    private static final String TAG = PhotoSearchRetriever.class.getSimpleName();
    protected INetwokCallBack<List<BasicPhoto>> networkCallBack;
    private Single<PhotoListWrapper> singleObservable = null;
    private IFlickrService service;

    private String searchText = null;
    private CompositeDisposable compositeDisposable = null;

    public PhotoSearchRetriever(IFlickrService flickerService) {
        service = flickerService;
        compositeDisposable = new CompositeDisposable();
    }

    /**
     * Emit the list of photos based on the search
     *
     * @return
     */
    protected SingleObserver<PhotoListWrapper> getPhotosObserver(final int page) {
        return new SingleObserver<PhotoListWrapper>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onSuccess(PhotoListWrapper photoListWrapper) {
                if (networkCallBack != null)
                    networkCallBack.displayResult(photoListWrapper.getPhotos().getPhotos(), page);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                if (networkCallBack != null)
                    networkCallBack.displayError(new ErrorModel(NetworkState.FAILED, e.getMessage()));

            }
        };
    }


    public String getSearchText() {
        return searchText;
    }

    public void clear() {
        this.searchText = null;
        if(!compositeDisposable.isDisposed())
            compositeDisposable.dispose();
    }
    /**
     * to stop the observer from emiting
     */
    public void invalidate() {
        if(singleObservable != null)
            singleObservable.unsubscribeOn(Schedulers.io());
        singleObservable = null;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    protected Single<PhotoListWrapper> getPhotosObservable(String text, int pageNo) {
        Log.d(TAG, "getPhotosObservable ***************** "+text);
        if(text == null) {
            return service.getPopularPhotos(API_KEY,PAGE_SIZE, pageNo);
        }
        return service.getSearchResults(API_KEY, text, PAGE_SIZE, pageNo);
    }


    public void getData(INetwokCallBack<List<BasicPhoto>> callBack, int p) {
        this.networkCallBack = callBack;

        singleObservable = getPhotosObservable(searchText, p);
        singleObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getPhotosObserver(p));

    }

}