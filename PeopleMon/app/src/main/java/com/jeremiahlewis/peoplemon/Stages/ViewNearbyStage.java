package com.jeremiahlewis.peoplemon.Stages;

import android.app.Application;

import com.jeremiahlewis.peoplemon.PeopleMonApplication;
import com.jeremiahlewis.peoplemon.R;
import com.jeremiahlewis.peoplemon.Riggers.SlideRigger;

/**
 * Created by jeremiahlewis on 11/11/16.
 */

   public class ViewNearbyStage extends IndexedStage {
        private final SlideRigger rigger;

        public ViewNearbyStage(Application context){
            super(com.jeremiahlewis.peoplemon.Stages.ViewNearbyStage.class.getName());
            this.rigger = new SlideRigger(context);
        }
        public ViewNearbyStage() {
            this(PeopleMonApplication.getInstance());
        }

        @Override
        public int getLayoutId() {
            return R.layout.view_nearby_listview;
        }

        @Override
        public Rigger getRigger() {
            return rigger;
        }

    }

