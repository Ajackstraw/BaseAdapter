package com.carey.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pink-jackstraw
 * @date 2019/1/1
 * @describe 通用adapter
 */
public class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    protected List<T> mData;
    protected Context mContext;

    protected ItemViewManager mItemViewManager;
    private OnItemChildClickListener mOnItemChildClickListener;
    private OnItemChildLongClickListener mOnItemChildLongClickListener;

    /**
     * Same as BaseAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data        A new list is created out of this one to avoid mutable list
     */
    public BaseAdapter(Context context, @Nullable List<T> data) {
        mContext = context;
        this.mData = data == null ? new ArrayList<T>() : data;
        mItemViewManager = new ItemViewManager();
    }

    @Override
    public int getItemViewType(int position) {
        if (!checkItemViewManager()){ return super.getItemViewType(position);}
        return mItemViewManager.getItemViewType(mData.get(position), position);
    }

    /**
     * setting up a new instance to data;
     *
     * @param data
     */
    public void setNewData(@Nullable List<T> data) {
        this.mData = data == null ? new ArrayList<T>() : data;
        notifyDataSetChanged();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemView itemView = mItemViewManager.getItemView(viewType);
        int layoutId = itemView.getItemViewLayoutId();
        BaseViewHolder holder = BaseViewHolder.createViewHolder(mContext, parent, layoutId);
        holder.setAdapter(this);
        return holder;
    }

    public void convert(BaseViewHolder holder, T t) {
        mItemViewManager.convert(holder, t, holder.getAdapterPosition());
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        convert(holder, mData.get(position));
    }

    @Override
    public int getItemCount() {
        int itemCount = mData.size();
        return itemCount;
    }

    /**
     *
     * @param itemView
     * @return
     */
    public BaseAdapter addItemView(ItemView<T> itemView) {
        mItemViewManager.addItemView(itemView);
        return this;
    }

    /**
     *
     * @return
     */
    protected boolean checkItemViewManager() {
        return mItemViewManager.getItemViewCount() > 0;
    }

    /**
     * Interface definition for a callback to be invoked when an itemchild in this
     * view has been clicked
     */
    public interface OnItemChildClickListener {
        /**
         * callback method to be invoked when an item in this view has been
         * click and held
         *
         * @param view     The view whihin the ItemView that was clicked
         * @param position The position of the view int the adapter
         */
        void onItemChildClick(BaseAdapter adapter, View view, int position);
    }

    /**
     * Interface definition for a callback to be invoked when an childView in this
     * view has been clicked and held.
     */
    public interface OnItemChildLongClickListener {
        /**
         * callback method to be invoked when an item in this view has been
         * click and held
         *
         * @param view     The childView whihin the itemView that was clicked and held.
         * @param position The position of the view int the adapter
         * @return true if the callback consumed the long click ,false otherwise
         */
        boolean onItemChildLongClick(BaseAdapter adapter, View view, int position);
    }

    /**
     * Register a callback to be invoked when an itemchild in View has
     * been  clicked
     *
     * @param listener The callback that will run
     */
    public void setOnItemChildClickListener(OnItemChildClickListener listener) {
        mOnItemChildClickListener = listener;
    }

    /**
     * Register a callback to be invoked when an itemchild  in this View has
     * been long clicked and held
     *
     * @param listener The callback that will run
     */
    public void setOnItemChildLongClickListener(OnItemChildLongClickListener listener) {
        mOnItemChildLongClickListener = listener;
    }

    /**
     * @return The callback to be invoked with an itemchild in this RecyclerView has
     * been clicked, or null id no callback has been set.
     */
    @Nullable
    public final OnItemChildClickListener getOnItemChildClickListener() {
        return mOnItemChildClickListener;
    }

    /**
     * @return The callback to be invoked with an itemChild in this RecyclerView has
     * been long clicked, or null id no callback has been set.
     */
    @Nullable
    public final OnItemChildLongClickListener getOnItemChildLongClickListener() {
        return mOnItemChildLongClickListener;
    }
}
