package com.jeremiahlewis.peoplemon.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Patterns;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jeremiahlewis.peoplemon.Models.Account;
import com.jeremiahlewis.peoplemon.Network.RestClient;
import com.jeremiahlewis.peoplemon.PeopleMonApplication;
import com.jeremiahlewis.peoplemon.R;
import com.jeremiahlewis.peoplemon.Stages.MapViewStage;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import flow.Flow;
import flow.History;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jeremiahlewis on 11/7/16.
 */

public class RegisterView extends LinearLayout {

    private Context context;
    private String avatar = "string";

    @Bind(R.id.setUsername_field)
    EditText setUsername;

    @Bind(R.id.setPassword_field)
    EditText setPassword;

    @Bind(R.id.confirmPassword_field)
    EditText confirmPassword;

    @Bind(R.id.setEmail_field)
    EditText setEmail;

    @Bind(R.id.register_button)
    Button registerButton;




    public RegisterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);

//        ((MainActivity)context).showMenuItem(false);
    }

    @OnClick(R.id.register_button)
    public void register(){
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(setUsername.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(setPassword.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(confirmPassword.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(setEmail.getWindowToken(), 0);

        String username = setUsername.getText().toString();
        String password = setPassword.getText().toString();
        String confirm = confirmPassword.getText().toString();
        String email = setEmail.getText().toString();

        if(username.isEmpty()|| email.isEmpty() || password.isEmpty() || confirm.isEmpty()){
            Toast.makeText(context, R.string.fill_out_all_fields, Toast.LENGTH_LONG).show();
        } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(context, R.string.must_provide_valid_email, Toast.LENGTH_LONG).show();
        } else if(!password.equals(confirm)){
            Toast.makeText(context, R.string.passwords_do_not_match, Toast.LENGTH_LONG).show();
        } else {
            registerButton.setEnabled(false);

            Account account = new Account(email, username, avatar,password);
            RestClient restClient = new RestClient();
            restClient.getApiService().register(account).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if(response.isSuccessful()){

                        Flow flow = PeopleMonApplication.getMainFlow();
                        History newHistory = History.single(new MapViewStage());
                        flow.setHistory(newHistory, Flow.Direction.REPLACE);

                    } else{
                        resetView();
                        Toast.makeText(context, "Registration Failed", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });



        }

    }

    private void resetView(){
        registerButton.setEnabled(true);
    }
}
