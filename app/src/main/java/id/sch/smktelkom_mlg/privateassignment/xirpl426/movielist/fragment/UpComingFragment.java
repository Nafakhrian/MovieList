package id.sch.smktelkom_mlg.privateassignment.xirpl426.movielist.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import java.util.ArrayList;

import id.sch.smktelkom_mlg.privateassignment.xirpl426.movielist.R;
import id.sch.smktelkom_mlg.privateassignment.xirpl426.movielist.adapter.SourceAdapter;
import id.sch.smktelkom_mlg.privateassignment.xirpl426.movielist.model.Source;
import id.sch.smktelkom_mlg.privateassignment.xirpl426.movielist.model.SourcesResponse;
import id.sch.smktelkom_mlg.privateassignment.xirpl426.movielist.service.GsonGetRequest;
import id.sch.smktelkom_mlg.privateassignment.xirpl426.movielist.service.VolleySingleton;

public class UpComingFragment extends Fragment {
    ArrayList<Source> mList = new ArrayList<>();
    SourceAdapter mAdapter;

    public UpComingFragment() {
        // Required empty public constructor
    }

    public static UpComingFragment newInstance() {
        UpComingFragment fragment = new UpComingFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_up_coming, container, false);


    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.up);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mAdapter = new SourceAdapter(this.getActivity(), mList);
        recyclerView.setAdapter(mAdapter);

        downloadDataSource();
    }

    private void downloadDataSource() {

        //String url = "https://newsapi.org/v1/sources?language=en";
        String url = "https://api.themoviedb.org/3/movie/upcoming?api_key=0332c5b960c9cd82c1de2e30e11407a2";

        GsonGetRequest<SourcesResponse> myRequest = new GsonGetRequest<SourcesResponse>
                (url, SourcesResponse.class, null, new Response.Listener<SourcesResponse>() {

                    @Override
                    public void onResponse(SourcesResponse response) {
                        Log.d("FLOW", "onResponse: " + (new Gson().toJson(response)));
                        if (response.page.equals("1")) {
                            //fillColor(response.results);
                            mList.addAll(response.results);
                            mAdapter.notifyDataSetChanged();
                        }
                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("FLOW", "onErrorResponse: ", error);
                    }
                });
        VolleySingleton.getInstance(this.getActivity()).addToRequestQueue(myRequest);


    }

}
