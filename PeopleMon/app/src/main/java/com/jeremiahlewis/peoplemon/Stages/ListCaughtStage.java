package com.jeremiahlewis.peoplemon.Stages;

import android.app.Application;

import com.jeremiahlewis.peoplemon.PeopleMonApplication;
import com.jeremiahlewis.peoplemon.R;
import com.jeremiahlewis.peoplemon.Riggers.SlideRigger;

/**
 * Created by jeremiahlewis on 11/9/16.
 */

public class ListCaughtStage extends IndexedStage {
    private final SlideRigger rigger;

    public ListCaughtStage(Application context){
        super(ListCaughtStage.class.getName());
        this.rigger = new SlideRigger(context);
    }
    public ListCaughtStage() {
        this(PeopleMonApplication.getInstance());
    }

    @Override
    public int getLayoutId() {
        return R.layout.list_caught_view;
    }

    @Override
    public Rigger getRigger() {
        return rigger;
    }

}
