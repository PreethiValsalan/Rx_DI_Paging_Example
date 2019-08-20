package com.interapp.developer.rxpagingex;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.SearchRecentSuggestions;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.interapp.developer.rxpagingex.adapter.PhotoListAdapter;
import com.interapp.developer.rxpagingex.lifecycle.PhotoListViewModel;
import com.interapp.developer.rxpagingex.model.BasicPhoto;
import com.interapp.developer.rxpagingex.model.ErrorModel;
import com.interapp.developer.rxpagingex.network.NetworkState;
import com.interapp.developer.rxpagingex.search.SearchTextSuggestionsProvider;
import com.interapp.developer.rxpagingex.util.AppUtil;

import java.lang.ref.WeakReference;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int DATA_RECEIVED = 200;
    private static final int NETWORK_STATE_RECEIVED = 100;

    protected PhotoListViewModel viewModel = null;

    private PhotoListAdapter mAdapter = null;
    private Handler mainHandler = null;
    private SearchSubmitTextListener searchTextListener;
    private SearchRecentSuggestions suggestions;
    private ProgressBar mProgressView  = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        mProgressView = findViewById(R.id.doctorListProgress);
        viewModel = ViewModelProviders.of(this).get(PhotoListViewModel.class);

        searchTextListener = new SearchSubmitTextListener();
        suggestions = new SearchRecentSuggestions(this,
                SearchTextSuggestionsProvider.AUTHORITY, SearchTextSuggestionsProvider.MODE);

        mainHandler = new MainHandler();


        int deviceOrientation = AppUtil.getScreenOrientation(getWindowManager().getDefaultDisplay());
        int noOfColumn = deviceOrientation == Configuration.ORIENTATION_LANDSCAPE ? 4 : 2;

        RecyclerView gridView = findViewById(R.id.mainGridView);
        gridView.setLayoutManager(new GridLayoutManager(this, noOfColumn, RecyclerView.VERTICAL, false));

        mAdapter = new PhotoListAdapter();
        gridView.setAdapter(mAdapter);
        viewModel.getPhotoListLiveData().observe(this, new ModelObserver(mainHandler));

        viewModel.getNetworkState().observe(this, new NetwrokStateObserver(mainHandler));
    }

    @Override
    protected void onStop() {
        super.onStop();
        viewModel.clear();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setBackground(getResources().getDrawable(R.drawable.text_border));

        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
        searchView.setSubmitButtonEnabled(true);
        searchView.setMaxWidth(3000);

        // Below event is triggered when submit search query.
        searchView.setOnQueryTextListener(searchTextListener);

        final TextView searchTextView = (TextView) searchView.findViewById(androidx.appcompat.R.id.search_src_text);

        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int i) {
                return false;
            }

            @Override
            public boolean onSuggestionClick(int i) {
                Cursor cursor= searchView.getSuggestionsAdapter().getCursor();
                cursor.moveToPosition(i);
                String suggestion =cursor.getString(2);
                searchTextView.setText(suggestion);
                return true;
            }
        });

        return true;
    }


    /**
     * listener class when a user press submit button
     */
    private final class SearchSubmitTextListener implements SearchView.OnQueryTextListener {

        @Override
        public boolean onQueryTextSubmit(String query) {
            suggestions.saveRecentQuery(query, null);
            viewModel.setSearchText(query);
            return true;
        }

        @Override
        public boolean onQueryTextChange(String s) {
            return false;
        }
    }

    /**
     * hanlder messages are posted to avoid memory leak
     */
    private final class MainHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == NETWORK_STATE_RECEIVED) {
                ErrorModel model = (ErrorModel)msg.obj;
                showProgress(model.getNetwrokState() == NetworkState.INITIAL_LOADING ? true : false);
                mAdapter.setNetworkState(model.getNetwrokState());

            } else {
                mAdapter.submitList((PagedList<BasicPhoto>) msg.obj);
            }
        }
    }

    private void showProgress(final boolean show) {
        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    /**
     * Observer to live data change
     */
    private final static class ModelObserver implements Observer<PagedList<BasicPhoto>> {
        private WeakReference<Handler> handlerWeakReference = null;

        public ModelObserver(Handler handler) {
            handlerWeakReference = new WeakReference<Handler>(handler);
        }

        @Override
        public void onChanged(@Nullable PagedList<BasicPhoto> doctors) {
            Handler handler = handlerWeakReference.get();
            if (handler != null) {
                Message message = new Message();
                message.what = DATA_RECEIVED;
                message.obj = doctors;
                handler.sendMessage(message);
            }
        }

    }

    /**
     * Observer to network data change
     */
    private final static class NetwrokStateObserver implements Observer<ErrorModel> {
        private WeakReference<Handler> handlerWeakReference = null;

        public NetwrokStateObserver(Handler handler) {
            handlerWeakReference = new WeakReference<Handler>(handler);
        }

        @Override
        public void onChanged(@Nullable ErrorModel errorModel) {
            Handler handler = handlerWeakReference.get();
            if (handler != null) {
                Message message = new Message();
                message.what = NETWORK_STATE_RECEIVED;
                message.obj = errorModel;
                handler.sendMessage(message);
            }
        }
    }
}
