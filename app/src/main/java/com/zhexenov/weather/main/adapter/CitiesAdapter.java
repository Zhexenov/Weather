package com.zhexenov.weather.main.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhexenov.weather.R;
import com.zhexenov.weather.data.City;
import com.zhexenov.weather.data.Weather;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.ViewHolder> {

    private List<WeatherAdapterModel> dataSet;

    public CitiesAdapter(List<WeatherAdapterModel> dataSet) {
        this.dataSet = dataSet;
    }

    public void updateDataSet(List<WeatherAdapterModel> dataSet) {
        final CitiesDiffCallback diffCallback = new CitiesDiffCallback(this.dataSet, dataSet);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        this.dataSet.clear();
        this.dataSet.addAll(dataSet);
        diffResult.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_city, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WeatherAdapterModel item = dataSet.get(position);
        holder.bind(item.getCity(), item.getWeather());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.city_name)
        TextView cityName;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(City city, @Nullable Weather weather) {
            if (weather != null) {
                if (weather.getCityId() == -1) {
                    cityName.setText(String.format("%s: %s", city.getName(), "Error"));
                } else {
                    cityName.setText(String.format("%s: %s", city.getName(), (int) weather.getTemp()));
                }
            } else {
                cityName.setText(String.format("%s: %s", city.getName(), "Loading..."));
            }

        }
    }

}
