package com.behavior.liberty.rxjavademo.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.behavior.liberty.rxjavademo.R;
import com.behavior.liberty.rxjavademo.bean.ItemWrapper;
import com.behavior.liberty.rxjavademo.bean.Menu;
import com.behavior.liberty.rxjavademo.bean.MenuTypes;
import com.liberty.libertylibrary.adapter.base.BaseHolder;
import com.liberty.libertylibrary.adapter.base.BaseRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liberty on 2019/1/8.
 */

public class CookAdapter extends BaseRecyclerAdapter<ItemWrapper> {

    private static final int MENU_TYPE = 0x11;
    private static final int MENU = 0x12;

    public CookAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseHolder onCreateHolder(ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case MENU_TYPE: {
                return new BaseHolder<MenuTypes.MenuType>(viewGroup, R.layout.menu_type_layout) {
                    @Override
                    public void setData(MenuTypes.MenuType item) {
                        super.setData(item);
                        ((TextView) getView(R.id.menu_type)).setText(item.getName());
                    }
                };
            }
            case MENU: {
                return new BaseHolder<Menu>(viewGroup, R.layout.menu_layout) {
                    @Override
                    public void setData(Menu item) {
                        super.setData(item);
                        ((TextView) getView(R.id.menu)).setText(item.getName());
                    }
                };
            }
        }

        return null;
    }


    @Override
    public int getViewType(int position) {
        ItemWrapper itemWrapper = getItem(position);
        if (itemWrapper instanceof MenuTypes.MenuType) {
            return MENU_TYPE;
        } else if (itemWrapper instanceof Menu) {
            return MENU;
        }

        return super.getViewType(position);
    }

    public void flatArray(List<MenuTypes.MenuType> menuTypeList) {
        List<ItemWrapper> itemWrapperList = new ArrayList<>();
        for (MenuTypes.MenuType menuType :
                menuTypeList) {
            itemWrapperList.add(menuType);
            itemWrapperList.addAll(menuType.getList());
        }
        addAll(itemWrapperList);
    }
}
