package com.interapp.developer.rxpagingex;

import android.app.Application;

import com.interapp.developer.rxpagingex.network.ConnectionComponent;
import com.interapp.developer.rxpagingex.network.ConnectionModule;
import com.interapp.developer.rxpagingex.network.DaggerConnectionComponent;
import com.interapp.developer.rxpagingex.util.AppUtil;

import static com.interapp.developer.rxpagingex.util.AppUtil.ENDPOINT_URL;

/**
 * Created by Preethi Valsalan on 2019-08-18
 * USED FOR DAGGER INJECTION
 */
public class MainApplication extends Application {

    private ConnectionComponent connectionComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        connectionComponent = DaggerConnectionComponent.builder()
                .connectionModule(new ConnectionModule(ENDPOINT_URL))
                .build();
    }

    public ConnectionComponent getConnectionComponent() {
        return connectionComponent;
    }
}
