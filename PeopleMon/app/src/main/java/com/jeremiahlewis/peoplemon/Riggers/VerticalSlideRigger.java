package com.jeremiahlewis.peoplemon.Riggers;

import android.app.Application;

import com.davidstemmer.screenplay.stage.rigger.AnimResources;
import com.davidstemmer.screenplay.stage.rigger.TweenRigger;
import com.jeremiahlewis.peoplemon.R;

/**
 * Created by andrewlewis on 10/31/16.
 */

    public class VerticalSlideRigger extends TweenRigger {
        private static final AnimResources params = new AnimResources();

        static{
            params.forwardIn = R.anim.slide_in_right;
            params.backIn = R.anim.slide_in_left;
            params.backOut = R.anim.slide_out_right;
            params.forwardOut = R.anim.slide_out_left;
        }
        public VerticalSlideRigger(Application context){
            super(context, params);
        }

    }

