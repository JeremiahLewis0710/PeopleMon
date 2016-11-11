package com.jeremiahlewis.peoplemon.Views;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jeremiahlewis.peoplemon.Adapter.ViewNearbyAdapter;
import com.jeremiahlewis.peoplemon.Models.User;
import com.jeremiahlewis.peoplemon.Network.RestClient;
import com.jeremiahlewis.peoplemon.R;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jeremiahlewis on 11/11/16.
 */

public class ViewNearbyView extends LinearLayout {

    private Context context;
    private ViewNearbyAdapter nearbyAdapter;

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;


    public ViewNearbyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);

        nearbyAdapter = new ViewNearbyAdapter(new ArrayList<User>(), context);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(nearbyAdapter);

        listCaughtPeople();
    }

    private void listCaughtPeople(){

        RestClient restClient = new RestClient();
        restClient.getApiService().findUsersNearby(500).enqueue(new Callback<User[]>() {

            @Override
            public void onResponse(Call<User[]> call, Response<User[]> response) {
                // Is the server response between 200 to 299
                if (response.isSuccessful()){
                    int i = 0;

                    nearbyAdapter.users = new ArrayList<User>(Arrays.asList(response.body()));

                    for (User user : nearbyAdapter.users){
                        Log.d(user.getUserName(),"***NEARBY***");
                        Log.d(user.getAvatarBase64(),"***NEARBY***");

//                        ViewNearbyAdapter.notifyDataSetChanged();
                    }

                }else{
                    Toast.makeText(context,"Get User Info Failed" + ": " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User[]> call, Throwable t) {
                Toast.makeText(context,"Get User Info Failed", Toast.LENGTH_LONG).show();
            }
        });
    }
}

