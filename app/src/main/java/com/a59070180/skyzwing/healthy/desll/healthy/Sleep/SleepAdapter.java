package com.a59070180.skyzwing.healthy.desll.healthy.Sleep;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.a59070180.skyzwing.healthy.desll.healthy.R;

import java.util.List;

public class SleepAdapter extends ArrayAdapter {
        List<Sleep> sleeps;
        Context context;

    public SleepAdapter(Context context, int resource, List<Sleep> objects)
    {
        super(context, resource, objects);
        this.sleeps = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View _sleepItem = LayoutInflater.from(context).inflate(R.layout.fragment_sleep_item, parent, false);
        TextView _sleepDate = _sleepItem.findViewById(R.id.sleep_item_date);
        TextView _sleepAwake = _sleepItem.findViewById(R.id.sleep_item_awake);
        TextView _sleepSleep = _sleepItem.findViewById(R.id.sleep_list_item_time);

        _sleepDate.setText(sleeps.get(position).getDate());
        _sleepAwake.setText(sleeps.get(position).getAwake());
        _sleepSleep.setText(sleeps.get(position).getEndSleepTime());

        return _sleepItem;
    }
}
