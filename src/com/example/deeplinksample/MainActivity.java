package com.example.deeplinksample;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.preference.PreferenceManager;

public class MainActivity extends Activity {
    // Constants
    private static final String MARKET_SCHEME_PREFIX = "market://";
    private static final String MARKET_SCHEME_QUERY_STRING_ID = "market_scheme_query_string";
    private static final String DEFAULT_MARKET_SCHEME_QUERY_STRING = "details?id=com.nokia.app.mixradio.client";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment()).commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        initView();
    }

    @Override
    protected void onPause() {
        Log.d("DeepLinkSample/MainActivity", "onPause()");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final EditText queryEditText = (EditText) findViewById(R.id.marketSchemeQueryEditText);
        preferences.edit().putString(MARKET_SCHEME_QUERY_STRING_ID, queryEditText.getText().toString()).commit();
        super.onPause();
    }

    private void launchMarketScheme(String queryString) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(MARKET_SCHEME_PREFIX + queryString));
        startActivity(intent);
    }

    private void initView() {
        final Button marketSchemeGoButton = (Button) findViewById(R.id.marketSchemeGoButton);
        marketSchemeGoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final EditText queryEditText = (EditText) findViewById(R.id.marketSchemeQueryEditText);
                launchMarketScheme(queryEditText.getText().toString());
            }
        });
        
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final EditText queryEditText = (EditText) findViewById(R.id.marketSchemeQueryEditText);
        queryEditText.setText(preferences.getString(MARKET_SCHEME_QUERY_STRING_ID, DEFAULT_MARKET_SCHEME_QUERY_STRING));
    }
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container,
                    false);
            return rootView;
        }
    }

}
