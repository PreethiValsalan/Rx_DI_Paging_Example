package com.interapp.developer.rxpagingex.network;

import com.interapp.developer.rxpagingex.model.PhotoListWrapper;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Preethi Valsalan on 8/14/19
 * Service methods used by the application
 */
public interface IFlickrService {

    @GET("/services/rest/?method=flickr.photos.getRecent&nojsoncallback=1&format=json")
    Single<PhotoListWrapper> getPopularPhotos(@Query("api_key") String apiKey,
                                              @Query("per_page") int pageSize,
                                              @Query("page") int pageNo);



    @GET("/services/rest/?method=flickr.photos.search&nojsoncallback=1&format=json")
    Single<PhotoListWrapper> getSearchResults(@Query("api_key") String apiKey,
                                              @Query("text") String searchText,
                                              @Query("per_page") int pageSize,
                                              @Query("page") int pageNo);
}


