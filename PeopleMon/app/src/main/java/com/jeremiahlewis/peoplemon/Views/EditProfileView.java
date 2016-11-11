package com.jeremiahlewis.peoplemon.Views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jeremiahlewis.peoplemon.MainActivity;
import com.jeremiahlewis.peoplemon.Models.Account;
import com.jeremiahlewis.peoplemon.Models.ImageLoadedEvent;
import com.jeremiahlewis.peoplemon.Network.RestClient;
import com.jeremiahlewis.peoplemon.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jeremiahlewis on 11/9/16.
 */

public class EditProfileView extends RelativeLayout {
    private Context context;

    @Bind(R.id.edit_name_field)
    EditText editNameField;

    @Bind(R.id.change_username)
    Button changeUsername;

    @Bind(R.id.user_name)
    TextView userName;

    @Bind(R.id.email_address)
    TextView emailAddress;

    @Bind(R.id.profile_avatar)
    ImageView profileAvatar;

    private String selectedImage;
    public Bitmap scaledImage;


    public EditProfileView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);

        //Sets the content for the user profile page
        makeApiCallForProfile();
        EventBus.getDefault().register(this);
    }

    @OnClick(R.id.upload_avatar)
    public void uploadAvatar(){
        ((MainActivity)context).getImage();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setSelectedImage(ImageLoadedEvent event){
        selectedImage = event.selectedImage;
        Bitmap image = BitmapFactory.decodeFile(selectedImage);
        profileAvatar.setImageBitmap(image);
    }

    @OnClick(R.id.change_username)
    public void editProfile(){

        //Hide keyboard when editing field
        InputMethodManager imm = (InputMethodManager)context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editNameField.getWindowToken(), 0);

        //Captures input from edit name field to a variable
        String name = editNameField.getText().toString();

        Account account = new Account(null,name);
        RestClient restClient = new RestClient();
        restClient.getApiService().postUserInfo(account).enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                // Is the server response between 200 to 299
                if (response.isSuccessful()){
                    makeApiCallForProfile();
                    Toast.makeText(context,"Changed Profile Name Successfully", Toast.LENGTH_LONG).show();
                    // makeApiCallForProfile();
                }else{
                    resetView();
                    Toast.makeText(context,"Get User Info Failed" + ": " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                resetView();
                Toast.makeText(context,"Get User Info Failed", Toast.LENGTH_LONG).show();
            }
        });


    }

    private void resetView(){
        changeUsername.setEnabled(true);
//        progressBar.setVisibility(GONE);
    }

    private void makeApiCallForProfile(){
        RestClient restClient = new RestClient();
        restClient.getApiService().getUserInfo().enqueue(new Callback<Account>() {

            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                // Is the server response between 200 to 299
                if (response.isSuccessful()){

                    //Get user that is returned
                    Account authUser = response.body();
                    Log.d(authUser.getFullName(),"****RESPONSE NAME****");
                    Log.d(authUser.getEmail(),"****RESPONSE EMAIL****");
                    Log.d(authUser.getAvatarBase64(),"****RESPONSE AVATAR****");

                    String encodedImage = authUser.getAvatarBase64();
                    byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);


                    userName.setText(authUser.getFullName());
                    emailAddress.setText(authUser.getEmail());
                    profileAvatar.setImageBitmap(decodedByte);
//
//                    //Get auth token and expiration date
//                    UserStore.getInstance().setToken(authUser.getToken());
//                    UserStore.getInstance().setTokenExpiration(authUser.getExpires());
//
//                    //Get main flow and set to single history
//                    Flow flow = getMainFlow();
//                    History newHistory = History.single(new GameMainStage());
//                    flow.setHistory(newHistory, Flow.Direction.REPLACE);

                }else{
                    resetView();
                    Toast.makeText(context,"Get User Info Failed" + ": " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                resetView();
                Toast.makeText(context,"Get User Info Failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onDetachedFromWindow() {
        EventBus.getDefault().unregister(this);
        super.onDetachedFromWindow();
    }

}
