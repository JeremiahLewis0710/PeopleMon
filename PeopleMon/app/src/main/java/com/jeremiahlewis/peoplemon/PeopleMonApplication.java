package com.jeremiahlewis.peoplemon;

import android.app.Application;

import com.jeremiahlewis.peoplemon.Stages.MapViewStage;

import flow.Flow;
import flow.History;

/**
 * Created by jeremiahlewis on 11/7/16.
 */

public class PeopleMonApplication extends Application {
    private static PeopleMonApplication application;
    public final Flow mainflow = new Flow(History.single(new MapViewStage()));

    public static final String API_BASE_URL = "https://efa-peoplemon-api.azurewebsites.net:443/";

    @Override
    public void onCreate() {
        super.onCreate();

        application = this;
    }

    public static PeopleMonApplication getInstance(){return application;}

    public static Flow getMainFlow(){return getInstance().mainflow;}
}
