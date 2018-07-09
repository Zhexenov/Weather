package com.zhexenov.weather.main.adapter;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhexenov.weather.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.ViewHolder> {

    private Application context;

    private List<WeatherAdapterModel> dataSet;

    public CitiesAdapter(Application context, List<WeatherAdapterModel> dataSet) {
        this.context = context;
        this.dataSet = dataSet;
    }

    public void updateDataSet(List<WeatherAdapterModel> dataSet) {
        final CitiesDiffCallback diffCallback = new CitiesDiffCallback(this.dataSet, dataSet);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        diffResult.dispatchUpdatesTo(this);
        this.dataSet.clear();
        this.dataSet.addAll(dataSet);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_city, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()){
            super.onBindViewHolder(holder, position, payloads);
        } else {
            Bundle o = (Bundle) payloads.get(0);
            for (String key : o.keySet()) {
                if (key.equals("value")) {
                    holder.bind(dataSet.get(position), context);
                }
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(dataSet.get(position), context);
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

        void bind(WeatherAdapterModel item, Context context) {

            Resources res = context.getResources();
            String text;

            if (item.getWeather() == null) {
                text = String.format("%s:  %s\n%s:  Loading...", res.getString(R.string.region), item.getCity().getName(),
                        res.getString(R.string.weather));
            } else if (item.getWeather().getCityId() == -1) {
                text = String.format("%s:  %s\n%s:  - °C", res.getString(R.string.region), item.getCity().getName(),
                        res.getString(R.string.weather));
            } else {
                text = String.format("%s:  %s\n%s:  %s °C", res.getString(R.string.region), item.getCity().getName(),
                        res.getString(R.string.weather), (int) item.getWeather().getTemp());
            }
            cityName.setText(text);
        }
    }

}
