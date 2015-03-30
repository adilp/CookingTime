package com.adilpatel.adil.cookingtime;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Adil on 2/21/2015.
 */
public class MyListAdapter extends BaseAdapter {
    private ArrayList<Foods> foods;
    private Context mContext;

    public MyListAdapter(ArrayList<Foods> foods, Context mContext) {
        this.foods = foods;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return foods.size();
    }

    @Override
    public Object getItem(int position) {
        return foods.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Foods p = (Foods) this.getItem(position);

        ViewHolder holder;

        if (convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_cell, parent, false);
            holder.name = (TextView) convertView.findViewById(R.id.mMainLabel);
            holder.time = (TextView) convertView.findViewById(R.id.mCityLabel);
            convertView.setTag(holder);
        } else{
            holder = (ViewHolder) convertView.getTag();
            holder.name.setText(p.get_foodName());
            holder.time.setText(Integer.toString(p.getTime()));
        }


        return convertView;
    }

    private static class ViewHolder {

        TextView name;
        TextView time;

    }
}