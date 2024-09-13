package com.externallogin.login;

import android.util.Log;

import com.google.gson.Gson;

import net.kdt.pojavlaunch.value.MinecraftAccount;

import okhttp3.*;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

public class OtherLoginApi {
    private static OkHttpClient client;
    private static final OtherLoginApi INSTANCE = new OtherLoginApi();
    private String baseUrl;

    private OtherLoginApi() {
        client = new OkHttpClient();
    }

    public static OtherLoginApi getINSTANCE() {
        return INSTANCE;
    }

    public void setBaseUrl(String baseUrl) {
        if (baseUrl.endsWith("/")) {
            baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
        }
        this.baseUrl = baseUrl;
        System.out.println(this.baseUrl);
    }

    public void login(String userName, String password, Listener listener) throws IOException {
        if (Objects.isNull(baseUrl)) {
            listener.onFailed("BaseUrl is not set");
            return;
        }
        AuthRequest authRequest = new AuthRequest();
        authRequest.setUsername(userName);
        authRequest.setPassword(password);
        AuthRequest.Agent agent = new AuthRequest.Agent();
        agent.setName("Client");
        agent.setVersion(1.0);
        authRequest.setAgent(agent);
        authRequest.setRequestUser(true);
        authRequest.setClientToken(UUID.randomUUID().toString().toLowerCase(Locale.ROOT));
        System.out.println(new Gson().toJson(authRequest));
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), new Gson().toJson(authRequest));
        Request request = new Request.Builder()
                .url(baseUrl + "/authserver/authenticate")
                .post(body)
                .build();
        Call call = client.newCall(request);
        Response response = call.execute();
        String res = response.body().string();
        System.out.println(res);
        if (response.code() == 200) {
            AuthResult result = new Gson().fromJson(res, AuthResult.class);
            listener.onSuccess(result);
        } else {
            listener.onFailed("error code：" + response.code() + "\n" + res);
        }
    }

    public void refresh(MinecraftAccount account, boolean select, Listener listener) throws IOException {
        if (Objects.isNull(baseUrl)) {
            listener.onFailed("BaseUrl is not set");
            return;
        }
        Refresh refresh = new Refresh();
        refresh.setClientToken(account.clientToken);
        refresh.setAccessToken(account.accessToken);
        if (select) {
            Refresh.SelectedProfile selectedProfile = new Refresh.SelectedProfile();
            selectedProfile.setName(account.username);
            selectedProfile.setId(account.profileId);
            refresh.setSelectedProfile(selectedProfile);
        }
        String data = new Gson().toJson(refresh);
        System.out.println(data);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), data);
        Request request = new Request.Builder()
                .url(baseUrl + "/authserver/refresh")
                .post(body)
                .build();
        Call call = client.newCall(request);
        Response response = call.execute();
        String res = response.body().string();
        System.out.println(res);
        if (response.code() == 200) {
            AuthResult result = new Gson().fromJson(res, AuthResult.class);
            listener.onSuccess(result);
        } else {
            listener.onFailed("error code：" + response.code() + "\n" + res);
        }
    }

    public String getServeInfo(String url) {
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();
            Call call = client.newCall(request);
            Response response = call.execute();
            String res = response.body().string();
            System.out.println(res);
            if (response.code() == 200) {
                return res;
            }
        } catch (Exception e) {
            Log.e("test", "" + e.toString());
        }
        return null;
    }

    public interface Listener {
        void onSuccess(AuthResult authResult);

        void onFailed(String error);
    }

}
