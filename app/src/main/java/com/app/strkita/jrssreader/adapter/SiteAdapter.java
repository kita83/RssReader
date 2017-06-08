package com.app.strkita.jrssreader.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.strkita.jrssreader.R;
import com.app.strkita.jrssreader.data.Site;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * Created by kitada on 2017/05/24.
 */

public class SiteAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Site> mSites;

    public SiteAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mSites = new ArrayList<>();
    }

    public void addItem(Site site) {
        mSites.add(site);
        notifyDataSetChanged();
    }

    public void addAll(List<Site> sites) {
        mSites.addAll(sites);
        notifyDataSetChanged();
    }

    public void removeItem(long siteId) {
        Iterator<Site> iterator = mSites.iterator();
        while(iterator.hasNext()) {
            Site site = iterator.next();
            if (siteId == site.getId()) {
                iterator.remove();
                notifyDataSetChanged();
                return;
            }
        }
    }

    @Override
    public int getCount() {
        return mSites.size();
    }

    @Override
    public Object getItem(int position) {
        return mSites.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SiteViewHolder holder;
        View itemView;

        if (convertView == null) {
            itemView = mInflater.inflate(R.layout.item_site, parent, false);
            holder = new SiteViewHolder(itemView);
            itemView.setTag(holder);
        } else {
            itemView = convertView;
            holder = (SiteViewHolder)convertView.getTag();
        }

        Site site = (Site) getItem(position);

        holder.title.setText(site.getTitle());
        holder.linksCount.setText(mContext.getString(R.string.site_link_count, site.getLinkCount()));

        return itemView;
    }

    private static class SiteViewHolder {
        private TextView title;
        private TextView linksCount;

        public SiteViewHolder(View itemView) {
            title = (TextView)itemView.findViewById(R.id.Title);
            linksCount = (TextView)itemView.findViewById(R.id.ArticlesCount);
        }
    }
}
