package com.jeremiahlewis.peoplemon.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jeremiahlewis.peoplemon.Models.Account;
import com.jeremiahlewis.peoplemon.Network.RestClient;
import com.jeremiahlewis.peoplemon.Network.UserStore;
import com.jeremiahlewis.peoplemon.R;
import com.jeremiahlewis.peoplemon.Stages.MapViewStage;
import com.jeremiahlewis.peoplemon.Stages.RegisterStage;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import flow.Flow;
import flow.History;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.jeremiahlewis.peoplemon.Components.Constants.GRANT_TYPE;
import static com.jeremiahlewis.peoplemon.PeopleMonApplication.getMainFlow;

/**
 * Created by jeremiahlewis on 11/7/16.
 */

public class LoginView extends LinearLayout {
    private Context context;

    @Bind(R.id.username_field)
    EditText email;

    @Bind(R.id.password_field)
    EditText password;

    @Bind(R.id.login_button)
    Button loginButton;

    @Bind(R.id.register_button)
    Button registerButton;


    public LoginView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }


    @OnClick(R.id.register_button)
    public void showRegisterView() {
        Flow flow = getMainFlow();
        History newHistory = flow.getHistory().buildUpon()
                .push(new RegisterStage())
                .build();
        flow.setHistory(newHistory, Flow.Direction.FORWARD);
    }

    @OnClick(R.id.login_button)
    public void login() {

        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(email.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(password.getWindowToken(), 0);

        String usernameField = email.getText().toString();
        String passwordField = password.getText().toString();
        String grantType = GRANT_TYPE;


        if (usernameField.isEmpty() || passwordField.isEmpty()) {
            Toast.makeText(context, R.string.must_provide_password,
                    Toast.LENGTH_LONG).show();
        } else {
            loginButton.setEnabled(true);
            registerButton.setEnabled(true);

           // Account account = new Account(grantType,usernameField, passwordField);
            RestClient restClient = new RestClient();
            restClient.getApiService().login(grantType,usernameField, passwordField).enqueue(new Callback<Account>() {
                @Override
                public void onResponse(Call<Account> call, Response<Account> response) {
                    if(response.isSuccessful()){
                        Account authAccount = response.body();

                        UserStore.getInstance().setToken(authAccount.getToken());
                        UserStore.getInstance().setTokenExpiration(authAccount.getExpiration());

                        Flow flow = getMainFlow();
                        History newHistory = History.single(new MapViewStage());
                        flow.setHistory(newHistory, Flow.Direction.REPLACE);

                        Toast.makeText(context,R.string.hello_world, Toast.LENGTH_LONG).show();
                    } else{
                        resetView();
                        Toast.makeText(context, R.string.login_failed +": " + response.code(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Account> call, Throwable t) {

                }
            });
        }
    }

    private void resetView(){
        loginButton.setEnabled(true);
        registerButton.setEnabled(true);
    }

}
