package com.jeremiahlewis.peoplemon.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jeremiahlewis.peoplemon.Models.User;
import com.jeremiahlewis.peoplemon.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jeremiahlewis on 11/11/16.
 */

public class ViewNearbyAdapter extends RecyclerView.Adapter<ViewNearbyAdapter.UserHolder> {

    public ArrayList<User> users;
    private Context context;

    public ViewNearbyAdapter(ArrayList<User> users, Context context) {
        this.users = users;
        this.context = context;
    }


    @Override
    public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflateView = LayoutInflater.from(context).inflate(R.layout.nearby_list_item, parent, false);
        return new UserHolder(inflateView);
    }

    @Override
    public void onBindViewHolder(UserHolder holder, int position) {

//        for (User user : users){
//            holder.bindUser(user);
//            Toast.makeText(context, user.getUserName(), Toast.LENGTH_SHORT).show();
//        }
        if(position <users.size()){
            User user = users.get(position);
            holder.bindUser(user);

            Toast.makeText(context, user.getUserName(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
//        return users == null ? 0 : users.size();
        return users.size()+1;
    }


    class UserHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.nearby_username)
        TextView nearbyUsername;

        public UserHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        //        Lets put our data in our UI
        public void bindUser(User user){
            nearbyUsername.setText(user.getUserName());

            //Need to add in imageview and decoding process
//            caughtAvatar.setText
        }
    }
}

