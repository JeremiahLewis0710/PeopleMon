package com.jeremiahlewis.peoplemon;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import com.davidstemmer.flow.plugin.screenplay.ScreenplayDispatcher;
import com.jeremiahlewis.peoplemon.Network.UserStore;
import com.jeremiahlewis.peoplemon.Stages.LoginStage;
import com.jeremiahlewis.peoplemon.Stages.MapViewStage;

import butterknife.Bind;
import butterknife.ButterKnife;
import flow.Flow;
import flow.History;

public class MainActivity extends AppCompatActivity {
    private Flow flow;
    private ScreenplayDispatcher dispatcher;
    private String TAG = "Main Acitivity";
    public Bundle savedInstanceState;


    @Bind(R.id.container)
    RelativeLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        flow = PeopleMonApplication.getMainFlow();
        dispatcher = new ScreenplayDispatcher(this, container);
        dispatcher.setUp(flow);

//        testCalls();

        if (UserStore.getInstance().getToken() == null ||
                UserStore.getInstance().getTokenExpiration() == null){
            History newHistory = History.single(new LoginStage());
            flow.setHistory(newHistory, Flow.Direction.REPLACE);
        }

        if (Build.VERSION.SDK_INT >= 23) {
            if (!(ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) ==//will need to replace read external storage to access location
                    PackageManager.PERMISSION_GRANTED)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
    }


    @Override
    public void onBackPressed() {
        if(!flow.goBack()) {
            flow.removeDispatcher(dispatcher);
            flow.setHistory(History.single(new MapViewStage()), Flow.Direction.BACKWARD);
            super.onBackPressed();
        }
    }

//        private void testCalls(){
//        RestClient restClient = new RestClient();
//        restClient.getApiService().getPost(1).enqueue(new Callback<TestPost>() {
//            @Override
//            public void onResponse(Call<TestPost> call, Response<TestPost> response) {
//                Log.d(TAG, "GetPost - Title: " + response.body().getTitle()
//                        + "/nBody: " + response.body().getBody());
//            }
//            @Override
//            public void onFailure(Call<TestPost> call, Throwable t) {
//                Log.d(TAG, "GetPost failed");
//            }
//        });



    }

