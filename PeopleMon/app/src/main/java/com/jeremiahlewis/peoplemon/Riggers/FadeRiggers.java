package com.jeremiahlewis.peoplemon.Riggers;

import android.app.Application;

import com.davidstemmer.screenplay.stage.rigger.AnimResources;
import com.davidstemmer.screenplay.stage.rigger.TweenRigger;
import com.jeremiahlewis.peoplemon.R;

/**
 * Created by andrewlewis on 10/31/16.
 */

public class FadeRiggers extends TweenRigger {
    private static final AnimResources params = new AnimResources();

    static{
        params.forwardIn = R.anim.fade_in;
        params.backIn = -1;
        params.backOut = R.anim.fade_out;
        params.forwardOut = -1;
    }
    public FadeRiggers(Application context){
        super(context,params);
    }

}
