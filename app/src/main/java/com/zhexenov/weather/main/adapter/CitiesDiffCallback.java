package com.zhexenov.weather.main.adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;

import com.zhexenov.weather.data.City;
import com.zhexenov.weather.data.Weather;

import java.util.List;
import java.util.Map;

public class CitiesDiffCallback extends DiffUtil.Callback {

    private final List<WeatherAdapterModel> oldList;
    private final List<WeatherAdapterModel> newList;

    CitiesDiffCallback(List<WeatherAdapterModel> oldList, List<WeatherAdapterModel> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    /**
     * Returns the size of the old list.
     *
     * @return The size of the old list.
     */
    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    /**
     * Returns the size of the new list.
     *
     * @return The size of the new list.
     */
    @Override
    public int getNewListSize() {
        return newList.size();
    }

    /**
     * Called by the DiffUtil to decide whether two object represent the same Item.
     * <p>
     * For example, if your items have unique ids, this method should check their id equality.
     *
     * @param oldItemPosition The position of the item in the old list
     * @param newItemPosition The position of the item in the new list
     * @return True if the two items represent the same object or false if they are different.
     */
    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getCity().getId() == newList.get(newItemPosition).getCity().getId();
    }

    /**
     * Called by the DiffUtil when it wants to check whether two items have the same data.
     * DiffUtil uses this information to detect if the contents of an item has changed.
     * <p>
     * DiffUtil uses this method to check equality instead of {@link Object#equals(Object)}
     * so that you can change its behavior depending on your UI.
     * For example, if you are using DiffUtil with a
     * {@link RecyclerView.Adapter RecyclerView.Adapter}, you should
     * return whether the items' visual representations are the same.
     * <p>
     * This method is called only if {@link #areItemsTheSame(int, int)} returns
     * {@code true} for these items.
     *
     * @param oldItemPosition The position of the item in the old list
     * @param newItemPosition The position of the item in the new list which replaces the
     *                        oldItem
     * @return True if the contents of the items are the same or false if they are different.
     */
    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final WeatherAdapterModel oldItem = oldList.get(oldItemPosition);
        final WeatherAdapterModel newItem = newList.get(newItemPosition);
        return oldItem.getCity().getId() == newItem.getCity().getId() && oldItem.getWeather() == newItem.getWeather();
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        final WeatherAdapterModel oldItem = oldList.get(oldItemPosition);
        final WeatherAdapterModel newItem = newList.get(newItemPosition);
        Bundle diff = new Bundle();

        if (!newItem.toString().equals(oldItem.toString())) {
            diff.putString("value", newItem.toString());
        } else {
            return null;
        }
        return diff;
    }
}
