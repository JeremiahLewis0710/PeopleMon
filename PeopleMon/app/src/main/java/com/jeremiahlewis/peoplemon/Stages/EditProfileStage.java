package com.jeremiahlewis.peoplemon.Stages;

import android.app.Application;

import com.jeremiahlewis.peoplemon.PeopleMonApplication;
import com.jeremiahlewis.peoplemon.R;
import com.jeremiahlewis.peoplemon.Riggers.SlideRigger;

/**
 * Created by jeremiahlewis on 11/9/16.
 */

public class EditProfileStage extends IndexedStage {
    private final SlideRigger rigger;

    public EditProfileStage(Application context){
        super(EditProfileStage.class.getName());
        this.rigger = new SlideRigger(context);
    }
    public EditProfileStage() {
        this(PeopleMonApplication.getInstance());
    }

    @Override
    public int getLayoutId() {
        return R.layout.edit_profile_view;
    }

    @Override
    public Rigger getRigger() {
        return rigger;
    }
}
