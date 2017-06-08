package com.app.strkita.jrssreader;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.strkita.jrssreader.adapter.LinkAdapter;
import com.app.strkita.jrssreader.data.Link;
import com.app.strkita.jrssreader.loader.LinkListLoader;

import java.util.List;


/**
 * 記事一覧を表示するFragment
 * Created by kitada on 2017/06/08.
 */

public class LinkListFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Link>>,
        LinkAdapter.OnItemClickListener {

    // LoaderのID
    private static final int LOADER_LINKS = 1;

    public LinkListFragment() {}

    public interface LinkListFragmnetListener {
        void onLinkClicked(@NonNull Link link);
    }

    private LinkAdapter mAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(context instanceof LinkListFragmnetListener)) {
            // Activityがリスナを実装していない場合
            throw new RuntimeException(context.getClass().getSimpleName() + " does not implement LinkListFragmentListener");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Loader初期化
        getLoaderManager().initLoader(LOADER_LINKS, null, this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // Loader破棄
        getLoaderManager().destroyLoader(LOADER_LINKS);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        // View生成
        View v = inflater.inflate(R.layout.fragment_links, container, false);
        Context context = inflater.getContext();
        RecyclerView recyclerView = (RecyclerView)v.findViewById(R.id.LinkList);
        // 必ずLayoutManagerを破棄する
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        mAdapter = new LinkAdapter(context);
        // リストのタップイベントをいったんフラグメントで受け取る
        mAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(mAdapter);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onItemClick(Link link) {
        LinkListFragmnetListener listener = (LinkListFragmnetListener)getActivity();
        if (listener != null) {
            // アクティビティにタップされたリンクを伝える
            listener.onLinkClicked(link);
        }
    }

    // RSS配信サイトが削除されたときに、それに紐づく記事も削除する
    public void removeLinks(long siteId) {
        mAdapter.removeItem(siteId);
    }

    // RSS配信サイトが追加されたときに、そのフィードのリンクもリストに反映する
    public void reload() {
        mAdapter.clearItems();

        Loader loader = getLoaderManager().getLoader(LOADER_LINKS);
        if (loader != null) {
            loader.forceLoad();
        } else {
            getLoaderManager().restartLoader(LOADER_LINKS, null, this);
        }
    }

    @Override
    public Loader<List<Link>> onCreateLoader(int id, Bundle args) {
        if (id == LOADER_LINKS) {
            LinkListLoader loader = new LinkListLoader(getActivity());
            loader.forceLoad();
            return loader;
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<Link>> loader, List<Link> data) {
        int id = loader.getId();
        if (id == LOADER_LINKS && data != null && data.size() > 0) {
            mAdapter.addItems(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Link>> loader) {

    }
}
