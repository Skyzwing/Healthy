package com.a59070180.skyzwing.healthy.desll.healthy.Workshop3;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.a59070180.skyzwing.healthy.desll.healthy.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PostAdapter extends ArrayAdapter{
    ArrayList<JSONObject> arrayArrayList;
    Context context;

    public PostAdapter(Context context, int resource, ArrayList<JSONObject> objects){
        super(context, resource, objects);
        this.arrayArrayList = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View postItem = LayoutInflater.from(context).inflate(R.layout.fragment_post_item, parent, false);
        JSONObject jsonObject = arrayArrayList.get(position);
        TextView postHeader = postItem.findViewById(R.id.post_item_title);
        TextView postBody  = postItem.findViewById(R.id.post_item_body);

        try{
            postHeader.setText(jsonObject.getString("id") + " : " + jsonObject.getString("title"));
            postBody.setText(jsonObject.getString("body"));
        }catch (JSONException e){
            Log.d("post", "JSONException : "+ e.getMessage());
        }

        return postItem;
    }
}
