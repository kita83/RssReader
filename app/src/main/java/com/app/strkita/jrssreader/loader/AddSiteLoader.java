package com.app.strkita.jrssreader.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.text.TextUtils;

import com.app.strkita.jrssreader.data.Link;
import com.app.strkita.jrssreader.data.Site;
import com.app.strkita.jrssreader.database.RssRepository;
import com.app.strkita.jrssreader.net.HttpGet;
import com.app.strkita.jrssreader.parser.RSSParser;

import java.io.InputStream;
import java.util.List;

/**
 *
 * Created by kitada on 2017/06/09.
 */

public class AddSiteLoader extends AsyncTaskLoader<Site> {

    private String url;

    public AddSiteLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    public Site loadInBackground() {

        if (!TextUtils.isEmpty(this.url)) {
            // RSSフィードをダウンロード
            HttpGet httpGet = new HttpGet(this.url);
            if (!httpGet.get()) {
                // 通信失敗
                return null;
            }

            InputStream in = httpGet.getResponse();
            RSSParser parser = new RSSParser();

            if (!parser.parse(in)) {
                return null;
            }

            Site site = parser.getSite();
            List<Link> links = parser.getLinkList();

            site.setUrl(url);
            site.setLinkCount(links.size());

            long feedId = RssRepository.insertSite(getContext(), site);
            site.setId(feedId);

            if (feedId > 0 && links.size() > 0) {
                RssRepository.insertLinks(getContext(), feedId, links);

                return site;
            }
        }
        return null;
    }
}
