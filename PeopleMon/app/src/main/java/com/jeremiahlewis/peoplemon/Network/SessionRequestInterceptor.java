package com.jeremiahlewis.peoplemon.Network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by jeremiahlewis on 11/7/16.
 */

public class SessionRequestInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        if (UserStore.getInstance().getToken() != null) {
            Request.Builder builder = request.newBuilder();
            builder.header("Authorization", "Bearer " +
                    UserStore.getInstance().getToken());
            request = builder.build();
        }
        return chain.proceed(request);
        //http client is getting ready to send off info, requestinterceptor places an authorization header in the get or post request if it has a token for that request
    }
}

