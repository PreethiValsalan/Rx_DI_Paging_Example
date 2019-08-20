package com.interapp.developer.rxpagingex.search;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Created by Preethi Valsalan on 8/15/19
 * Class to implement storing of Suggestions in searchfield
 */
public class SearchTextSuggestionsProvider extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = "com.interapp.developer.rxpagingex.search.SearchTextSuggestionsProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public SearchTextSuggestionsProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}
