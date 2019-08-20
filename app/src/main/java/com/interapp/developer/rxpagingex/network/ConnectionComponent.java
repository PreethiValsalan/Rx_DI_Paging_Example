package com.interapp.developer.rxpagingex.network;


import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Preethi Valsalan on 2019-08-18
 * Class which provides the services DI
 */

@Singleton
@Component(modules ={ConnectionModule.class})
public interface ConnectionComponent {

    IFlickrService flickrService();

}
