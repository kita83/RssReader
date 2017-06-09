package com.app.strkita.jrssreader;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;

/**
 *
 * Created by strkita on 2017/06/09.
 */

public class SiteListFragment extends Fragment
        implements LoaderManager.LoaderCallbacks, AdapterView.OnItemClickListener{
    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {

    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
