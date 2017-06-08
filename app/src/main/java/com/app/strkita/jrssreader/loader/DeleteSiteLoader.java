package com.app.strkita.jrssreader.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.app.strkita.jrssreader.database.RssRepository;

/**
 * サイト情報削除用Loader
 * Created by kitada on 2017/06/08.
 */

public class DeleteSiteLoader extends AsyncTaskLoader<Integer> {
    private long id;

    public DeleteSiteLoader(Context context, long id) {
        super(context);
        this.id = id;
    }

    public long getTargetId() {
        return id;
    }

    @Override
    public Integer loadInBackground() {
        return RssRepository.deleteSite(getContext(), id);
    }
}
