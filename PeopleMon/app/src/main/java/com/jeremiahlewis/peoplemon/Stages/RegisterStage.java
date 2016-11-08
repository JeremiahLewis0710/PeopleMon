package com.jeremiahlewis.peoplemon.Stages;

import android.app.Application;

import com.jeremiahlewis.peoplemon.PeopleMonApplication;
import com.jeremiahlewis.peoplemon.R;
import com.jeremiahlewis.peoplemon.Riggers.SlideRigger;

/**
 * Created by jeremiahlewis on 11/7/16.
 */

public class RegisterStage extends IndexedStage {
    private final SlideRigger rigger;

    public RegisterStage(Application context){
        super(LoginStage.class.getName());
        this.rigger = new SlideRigger(context);
    }
    public RegisterStage() {
        this(PeopleMonApplication.getInstance());
    }

    @Override
    public int getLayoutId() {
        return R.layout.register_view;
    }

    @Override
    public Rigger getRigger() {
        return rigger;
    }

}
