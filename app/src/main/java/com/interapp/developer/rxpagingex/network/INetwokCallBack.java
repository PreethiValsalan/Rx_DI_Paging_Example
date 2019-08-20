package com.interapp.developer.rxpagingex.network;

import com.interapp.developer.rxpagingex.model.ErrorModel;

/**
 * Created by Preethi Valsalan on 8/14/19
 * Call back from network
 */
public interface INetwokCallBack<T> {

    void displayResult(T object, int pageNo);

    void displayError(ErrorModel error);
}
