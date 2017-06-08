package com.app.strkita.jrssreader.parser;

import com.app.strkita.jrssreader.data.Link;
import com.app.strkita.jrssreader.data.Site;

import org.w3c.dom.Document;

import java.util.List;

/**
 *
 * Created by kitada on 2017/06/08.
 */

public interface FeedParser {

    boolean parse(Document document);

    Site getSite();
    List<Link> getLinkList();
}
