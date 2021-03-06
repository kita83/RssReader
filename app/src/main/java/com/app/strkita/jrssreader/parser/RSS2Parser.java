package com.app.strkita.jrssreader.parser;

import android.text.TextUtils;

import com.app.strkita.jrssreader.data.Link;
import com.app.strkita.jrssreader.data.Site;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

/**
 *
 * Created by kitada on 2017/06/08.
 */

public class RSS2Parser implements FeedParser {

    private Site site;

    private List<Link> links;

    @Override
    public boolean parse(Document document) {
        XPathFactory factory = XPathFactory.newInstance();
        XPath xPath = factory.newXPath();

        try {
            this.site = new Site();

            // チャンネルのタイトル
            String title = xPath.evaluate("//channel/title/text()", document);
            this.site.setTitle(title);

            // チャンネルの説明
            String description = xPath.evaluate("//channel/description/text()", document);
            this.site.setDescription(description);

            // リンクリスト
            this.links = new ArrayList<>();

            // このドキュメント内の<item>要素を全て取り出す
            NodeList items = (NodeList)xPath.evaluate("//item", document, XPathConstants.NODESET);

            // 日付文字列をDate型に変換するためのDateFormatter
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "EEE, dd MMM yyyy HH:mm:ss Z", Locale.US);

            // リンク数 
            int article_count = items.getLength();
            for (int i = 0; i < article_count; i++) {
                Node item = items.item(i);

                // リンク情報
                Link link = new Link();
                link.setTitle(xPath.evaluate("./title/text()", item)); // タイトル
                link.setUrl(xPath.evaluate("./link/text()", item)); // URL
                link.setDescription(xPath.evaluate("./description/text()", item)); // 説明

                String pubDate = xPath.evaluate("./pubDate/text()", item); // 発行日

                if (TextUtils.isEmpty(pubDate)) {
                    link.setPubDate(-1L);
                } else {
                    Date publishTime = dateFormat.parse(pubDate);
                    link.setPubDate(publishTime.getTime());
                }

                links.add(link);
            }

            return true;

        } catch (XPathExpressionException | ParseException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Site getSite() {
        return this.site;
    }

    @Override
    public List<Link> getLinkList() {
        return this.links;
    }
}
