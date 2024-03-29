package siecola.com.br.dm114.webservices;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

import siecola.com.br.dm114.R;
import siecola.com.br.dm114.models.AccessToken;
import siecola.com.br.dm114.utils.WSConstants;
import siecola.com.br.dm114.utils.WSUtil;


public class WebServiceClient {

    private static final String GET_TOKEN = "/oauth/token";
    private static final String TAG = "WebServiceClient";
    private static HttpURLConnection conn;
    private static AccessToken accessToken;

    private WebServiceClient() {
    }


    public static WebServiceResponse init(Context context, String host, String method) throws IOException {

        WebServiceResponse webServiceResponse;

        // se o token nao for valido, pega um novo token
        if (!isTokenValid(context)) {
            webServiceResponse = authenticate(context);
            if (webServiceResponse.getResponseCode() != 200) {
                return webServiceResponse;
            }
        } else {
            webServiceResponse = new WebServiceResponse();
            webServiceResponse.setResponseCode(200);
        }

        URL url = new URL(host);
        conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(WSConstants.READ_TIMEOUT);
        conn.setConnectTimeout(WSConstants.CONNECTION_TIMEOUT);
        conn.setRequestMethod(method);
        conn.setRequestProperty("Accept", WSConstants.CONTENT_TYPE_JSON);
        conn.setRequestProperty("Content-Type", WSConstants.CONTENT_TYPE_JSON);

        if (accessToken != null) {
            conn.setRequestProperty("Authorization", "Bearer " +
                    accessToken.getAccess_token());
        }

        return webServiceResponse;
    }


    // Pega o token de acesso
    private static WebServiceResponse authenticate(Context context) {
        WebServiceResponse webServiceResponse = new WebServiceResponse();
        String baseAddress = WSUtil.getHostAddress(context);
        accessToken = null;

        try {

            URL url = new URL(baseAddress + GET_TOKEN);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(WSConstants.READ_TIMEOUT);
            conn.setConnectTimeout(WSConstants.CONNECTION_TIMEOUT);
            conn.setRequestMethod(WSConstants.METHOD_POST);
            conn.setRequestProperty("Accept", WSConstants.CONTENT_TYPE_JSON);
            conn.setRequestProperty("Content-Type", WSConstants.CONTENT_TYPE_URL_ENCODED);

            //ClientId e secret (matilde:siecola em Base64)
            conn.setRequestProperty("Authorization", "Basic c2llY29sYTptYXRpbGRl");
            SharedPreferences sharedSettings = PreferenceManager.getDefaultSharedPreferences(context);
            String wsUsername = sharedSettings.getString(context.getString(R.string.pref_user_login), context.getString(R.string.pref_ws_default_username));
            String wsPassword = sharedSettings.getString(context.getString(R.string.pref_user_password), context.getString(R.string.pref_ws_default_password));

            String grantType = generateGrantType(wsUsername, wsPassword);
            conn.setDoOutput(true);
            conn.setFixedLengthStreamingMode(grantType.length());
            conn.getOutputStream().write(grantType.getBytes("UTF-8"));
            conn.getOutputStream().flush();
            conn.getOutputStream().close();
            conn.connect();
            InputStream is = new BufferedInputStream(conn.getInputStream());

            if (conn.getResponseCode() == 200) {
                String tokenResponse = readIt(is);
                Gson tokenGson = new Gson();

                accessToken = tokenGson.fromJson(tokenResponse,
                        AccessToken.class);

                // Salva o access token no shared preferences
                SharedPreferences.Editor editor = sharedSettings.edit();
                editor.putString(context.getString(
                        R.string.pref_ws_access_token),
                        accessToken.getAccess_token());
                editor.putLong(context.getString(
                        R.string.pref_ws_access_token_expiration),
                        Calendar.getInstance().getTime().getTime() +
                                accessToken.getExpires_in() * 1000);
                editor.commit();

                webServiceResponse.setResponseCode(200);
                webServiceResponse.setResponseMessage(tokenResponse);
            } else {
                webServiceResponse.setResultMessage(readIt(is));
                webServiceResponse.setResponseCode(conn.getResponseCode());
                webServiceResponse.setResponseMessage(
                        conn.getResponseMessage());
            }
            is.close();
        } catch (IOException e) {
            webServiceResponse.setResponseCode(0);
            webServiceResponse.setResponseMessage(e.getMessage());
        }
        return webServiceResponse;
    }


    public static WebServiceResponse get(Context context, String host) {
        WebServiceResponse webServiceResponse;
        InputStream is = null;

        try {
            webServiceResponse = init(context, host, WSConstants.METHOD_GET);
            if (webServiceResponse.getResponseCode() != 200) {
                return webServiceResponse;
            }
            conn.setDoInput(true);
            conn.connect();
            if (conn.getResponseCode() == 401) {
                invalidateToken(context);
                webServiceResponse = init(context, host, WSConstants.METHOD_GET);
                if (webServiceResponse.getResponseCode() != 200) {
                    return webServiceResponse;
                } else {
                    conn.setDoInput(true);
                    conn.connect();
                }
            }

            is = conn.getInputStream();
            webServiceResponse = new WebServiceResponse();
            webServiceResponse.setResultMessage(readIt(is));
            webServiceResponse.setResponseCode(conn.getResponseCode());
            webServiceResponse.setResponseMessage(conn.getResponseMessage());
            is.close();

        } catch (IOException e) {
            webServiceResponse = new WebServiceResponse();
            webServiceResponse.setResponseCode(0);
            webServiceResponse.setResponseMessage(e.getMessage());
        } finally {
            conn.disconnect();
        }

        return webServiceResponse;
    }

    public static WebServiceResponse put(Context context, String host, String json) {
        WebServiceResponse webServiceResponse;
        InputStream is = null;

        try {
            webServiceResponse = init(context, host, WSConstants.METHOD_PUT);
            if (webServiceResponse.getResponseCode() != 200) {
                return webServiceResponse;
            }
            conn.setDoInput(true);
            conn.connect();
            if (conn.getResponseCode() == 401) {
                invalidateToken(context);
                webServiceResponse = init(context, host, WSConstants.METHOD_PUT);
                if (webServiceResponse.getResponseCode() != 200) {
                    return webServiceResponse;
                } else {
                    conn.setDoInput(true);
                    conn.setFixedLengthStreamingMode(json.length());
                    conn.getOutputStream().write(json.getBytes("UTF-8"));
                    conn.getOutputStream().flush();
                    conn.getOutputStream().close();
                    conn.connect();
                }
            }

            is = conn.getInputStream();
            webServiceResponse = new WebServiceResponse();
            webServiceResponse.setResultMessage(readIt(is));
            webServiceResponse.setResponseCode(conn.getResponseCode());
            webServiceResponse.setResponseMessage(conn.getResponseMessage());
            is.close();
        } catch (IOException e) {
            webServiceResponse = new WebServiceResponse();
            webServiceResponse.setResponseCode(0);
            webServiceResponse.setResponseMessage(e.getMessage());
        } finally {
            conn.disconnect();
        }

        return webServiceResponse;
    }


    //TODO delete method
    //TODO post method


    private static boolean isTokenValid(Context context) {
        SharedPreferences sharedSettings = PreferenceManager.getDefaultSharedPreferences(context);
        long expirationTime;

        if (sharedSettings.contains(
                context.getString(R.string.pref_ws_access_token)) &&
                sharedSettings.contains(context.getString(R.string.pref_ws_access_token_expiration))) {

            expirationTime = sharedSettings.getLong(context.getString(R.string.pref_ws_access_token_expiration), 0);

            if (Calendar.getInstance().getTime().getTime() < expirationTime) {
                accessToken = new AccessToken();
                accessToken.setAccess_token(sharedSettings.getString(context.getString(R.string.pref_ws_access_token), ""));
                accessToken.setExpires_in(expirationTime);
                return true;
            }
        }

        accessToken = null;
        return false;
    }


    private static String readIt(InputStream stream) throws IOException {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder stringBuilder = new StringBuilder();

            char[] buffer = new char[1];
            int charsRead;

            while ((charsRead = bufferedReader.read(buffer)) != -1) {
                stringBuilder.append(buffer, 0, charsRead);
            }

            return stringBuilder.toString();
        } finally {
            stream.close();
        }
    }


    private static String generateGrantType(String username, String password) {
        String grantType = "grant_type=password&username=" + username + "&password=" + password;

        return grantType;
    }


    private static void invalidateToken(Context context) {
        SharedPreferences sharedSettings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedSettings.edit();
        editor.remove(context.getString(R.string.pref_ws_access_token));

        editor.remove(context.getString(R.string.pref_ws_access_token_expiration));

        editor.commit();
    }


}