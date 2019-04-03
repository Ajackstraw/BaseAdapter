package com.carey.adapter;

import android.support.v4.util.SparseArrayCompat;

public class ItemViewManager<T> {

    SparseArrayCompat<ItemView<T>> itemViewArray = new SparseArrayCompat();

    public int getItemViewCount() {
        return itemViewArray.size();
    }

    public ItemViewManager<T> addItemView(ItemView<T> delegate) {
        int viewType = itemViewArray.size();
        if (delegate != null) {
            itemViewArray.put(viewType, delegate);
            viewType++;
        }
        return this;
    }

    public ItemViewManager<T> addItemView(int viewType, ItemView<T> delegate) {
        if (itemViewArray.get(viewType) != null) {
            throw new IllegalArgumentException(
                    "An ItemView is already registered for the viewType = "
                            + viewType
                            + ". Already registered ItemView is "
                            + itemViewArray.get(viewType));
        }
        itemViewArray.put(viewType, delegate);
        return this;
    }

    public ItemViewManager<T> removeItemView(ItemView<T> delegate) {
        if (delegate == null) {
            throw new NullPointerException("ItemView is null");
        }
        int indexToRemove = itemViewArray.indexOfValue(delegate);

        if (indexToRemove >= 0) {
            itemViewArray.removeAt(indexToRemove);
        }
        return this;
    }

    public ItemViewManager<T> removeItemView(int itemType) {
        int indexToRemove = itemViewArray.indexOfKey(itemType);

        if (indexToRemove >= 0) {
            itemViewArray.removeAt(indexToRemove);
        }
        return this;
    }

    public int getItemViewType(T item, int position) {
        int delegatesCount = itemViewArray.size();
        for (int i = delegatesCount - 1; i >= 0; i--) {
            ItemView<T> itemView = itemViewArray.valueAt(i);
            if (itemView.isForViewType( item, position)) {
                return itemViewArray.keyAt(i);
            }
        }
        throw new IllegalArgumentException(
                "No ItemView added that matches position=" + position + " in data source");
    }

    public void convert(BaseViewHolder holder, T item, int position) {
        int itemCount = itemViewArray.size();
        for (int i = 0; i < itemCount; i++) {
            ItemView<T> itemView = itemViewArray.valueAt(i);

            if (itemView.isForViewType( item, position)) {
                itemView.convert(holder, item, position);
                return;
            }
        }
        throw new IllegalArgumentException(
                "No ItemViewManager added that matches position=" + position + " in data source");
    }


    public ItemView getItemView(int viewType) {
        return itemViewArray.get(viewType);
    }

    public int getItemViewLayoutId(int viewType) {
        return getItemView(viewType).getItemViewLayoutId();
    }

    public int getItemViewType(ItemView itemView) {
        return itemViewArray.indexOfValue(itemView);
    }
}
