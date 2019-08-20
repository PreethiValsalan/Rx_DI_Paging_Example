package com.interapp.developer.rxpagingex.model;


import com.interapp.developer.rxpagingex.network.NetworkState;

/**
 * Created by Preethi Valsalan on 8/17/19
 */
public class ErrorModel {

    private NetworkState netwrokState;

    private String errorMessage;

    public ErrorModel(NetworkState state, String message) {
        this.netwrokState = state;
        this.errorMessage = message;
    }

    public ErrorModel(NetworkState state) {
        this.netwrokState = state;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public NetworkState getNetwrokState() {
        return netwrokState;
    }

    public void setNetwrokState(NetworkState state) {
        this.netwrokState = state;
    }
}
