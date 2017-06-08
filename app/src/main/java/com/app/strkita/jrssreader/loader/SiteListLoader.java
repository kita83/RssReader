package com.app.strkita.jrssreader.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.app.strkita.jrssreader.data.Site;
import com.app.strkita.jrssreader.database.RssRepository;

import java.util.List;

/**
 * サイト一覧取得用Loader
 * Created by kitada on 2017/06/08.
 */

public class SiteListLoader extends AsyncTaskLoader<List<Site>> {

    public SiteListLoader(Context context) {
        super(context);
    }

    @Override
    public List<Site> loadInBackground() {
        return RssRepository.getAllSites(getContext());
    }
}
