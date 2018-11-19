package com.a59070180.skyzwing.healthy.desll.healthy.Workshop3;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.a59070180.skyzwing.healthy.desll.healthy.R;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class FramgentPost extends Fragment {

    OkHttpClient okHttpClient = new OkHttpClient();
    String result;
    JSONArray jsonArray;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initBackBtn();
        initRestAPI();
    }

    void initBackBtn() {
        Button button = getView().findViewById(R.id.postBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
    }

    void initRestAPI() {
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    Request request = new Request.Builder().url("https://jsonplaceholder.typicode.com/posts").build();

                    Response response = okHttpClient.newCall(request).execute();
                    result = response.body().string();
                    jsonArray = new JSONArray(result);
                } catch (IOException e) {
                    Log.d("post", "catch IOException : " + e.getMessage());
                } catch (JSONException e) {
                    Log.d("test", "catch JSONException : " + e.getMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                try {
                    final ArrayList<JSONObject> postList = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        postList.add(obj);
                    }
                    ProgressBar progressBar = getView().findViewById(R.id.post_progressBar);
                    progressBar.setVisibility(View.GONE);
                    ListView postListView = getView().findViewById(R.id.post_list);
                    PostAdapter postAdapter = new PostAdapter(getContext(), R.layout.fragment_post_item, postList);
                    postListView.setAdapter(postAdapter);
                    postListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Bundle bundle = new Bundle();
                            try {
                                bundle.putInt("post id", postList.get(position).getInt("id"));
                            } catch (JSONException e) {
                                Log.d("post", "catch JSONException in onItemClick : " + e.getMessage());
                            }

                            Fragment fragment = new CommentFragment();
                            fragment.setArguments(bundle);
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                            transaction.replace(R.id.main_view, fragment).addToBackStack(null).commit();
                        }
                    });
                } catch (JSONException e) {
                    Log.d("post", "catch JSONException : " + e.getMessage());
                }
            }
        };
        task.execute();
    }
}