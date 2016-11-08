package com.jeremiahlewis.peoplemon.Stages;

import android.app.Application;

import com.jeremiahlewis.peoplemon.PeopleMonApplication;
import com.jeremiahlewis.peoplemon.R;
import com.jeremiahlewis.peoplemon.Riggers.SlideRigger;

/**
 * Created by jeremiahlewis on 11/7/16.
 */

public class MapViewStage extends IndexedStage {

    private final SlideRigger rigger;

    public MapViewStage(Application context){
        super(MapViewStage.class.getName());
        this.rigger = new SlideRigger(context);

    }
    public MapViewStage() {
        this(PeopleMonApplication.getInstance());
    }

    @Override
    public int getLayoutId() {
        return R.layout.map_view;
    }

    @Override
    public SlideRigger getRigger() {
        return rigger;
    }
}
