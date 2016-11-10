package com.jeremiahlewis.peoplemon.Views;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jeremiahlewis.peoplemon.Adapter.CaughtPeopleListAdapter;
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
 * Created by jeremiahlewis on 11/10/16.
 */

public class ListCaughtView extends LinearLayout {

    private Context context;
    private CaughtPeopleListAdapter caughtAdapter;

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;


    public ListCaughtView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);

        caughtAdapter = new CaughtPeopleListAdapter(new ArrayList<User>(), context);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(caughtAdapter);

        listCaughtPeople();
    }

    private void listCaughtPeople(){

        RestClient restClient = new RestClient();
        restClient.getApiService().viewCaught().enqueue(new Callback<User[]>() {

            @Override
            public void onResponse(Call<User[]> call, Response<User[]> response) {
                // Is the server response between 200 to 299
                if (response.isSuccessful()){
                    int i = 0;

                    caughtAdapter.users = new ArrayList<User>(Arrays.asList(response.body()));

                    for (User user : caughtAdapter.users){

                        Log.d(user.getUserName(),"***CAUGHT***");
                        Log.d(user.getAvatarBase64(),"***CAUGHT***");

                        caughtAdapter.notifyDataSetChanged();
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

