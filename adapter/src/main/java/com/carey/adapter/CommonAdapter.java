package com.carey.adapter;

import android.content.Context;
import android.view.LayoutInflater;

import java.util.List;

public abstract class CommonAdapter<T> extends BaseAdapter<T> {

    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mData;
    protected LayoutInflater mInflater;

    public CommonAdapter(final Context context, final int layoutId, List<T> data) {
        super(context, data);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mData = data;

        addItemView(new ItemView<T>() {
            @Override
            public int getItemViewLayoutId()
            {
                return layoutId;
            }

            @Override
            public boolean isForViewType( T item, int position)
            {
                return true;
            }

            @Override
            public void convert(BaseViewHolder holder, T t, int position) {
                CommonAdapter.this.convert(holder, t, position);
            }
        });
    }

    protected abstract void convert(BaseViewHolder holder, T t, int position);

}
