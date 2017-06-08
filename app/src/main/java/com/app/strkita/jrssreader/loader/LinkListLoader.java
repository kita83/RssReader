package com.app.strkita.jrssreader.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.app.strkita.jrssreader.data.Link;
import com.app.strkita.jrssreader.database.RssRepository;

import java.util.List;

/**
 * リンク一覧取得用Loader
 * Created by kitada on 2017/06/08.
 */

public class LinkListLoader extends AsyncTaskLoader<List<Link>> {

    public LinkListLoader(Context context) {
        super(context);
    }

    @Override
    public List<Link> loadInBackground() {
        return RssRepository.getAllLinks(getContext());
    }
}
