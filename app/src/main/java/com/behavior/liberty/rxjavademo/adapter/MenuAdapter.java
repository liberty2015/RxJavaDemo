package com.behavior.liberty.rxjavademo.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.behavior.liberty.rxjavademo.R;
import com.behavior.liberty.rxjavademo.bean.CookMenu;
import com.bumptech.glide.Glide;
import com.liberty.libertylibrary.adapter.base.BaseHolder;
import com.liberty.libertylibrary.adapter.base.BaseRecyclerAdapter;

/**
 * Created by liberty on 2019/1/8.
 */

public class MenuAdapter extends BaseRecyclerAdapter<CookMenu> {
    public MenuAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseHolder onCreateHolder(ViewGroup viewGroup, int viewType) {
        return new BaseHolder<CookMenu>(viewGroup, R.layout.cook_menu_item) {

            @Override
            public void setData(CookMenu item) {
                super.setData(item);
                ImageView album = getView(R.id.album);
                Glide.with(getmContext()).load(item.getAlbums().get(0)).into(album);
                TextView title = getView(R.id.title);
                TextView tags = getView(R.id.tags);
                title.setText(item.getTitle());
                tags.setText(item.getTags());
            }
        };
    }
}
