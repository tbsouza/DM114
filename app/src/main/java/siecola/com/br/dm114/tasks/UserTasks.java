package siecola.com.br.dm114.tasks;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import siecola.com.br.dm114.utils.WSUtil;
import siecola.com.br.dm114.webservices.WebServiceClientMessage;
import siecola.com.br.dm114.webservices.WebServiceResponse;

public class UserTasks {

    // Endpoint GCM
    private static final String PUT_GCM = "/api/users/updatereggcm?email=";
    private static String PREF_LOGIN = "pref_login";

    private UserEvents userEvents;
    private Context context;
    private String baseAddress;

    public UserTasks(Context context, UserEvents userEvents) {
        // Pega o endereco de mensagem
        baseAddress = WSUtil.getHostMessageAddress(context);

        this.context = context;
        this.userEvents = userEvents;
    }


    @SuppressLint("StaticFieldLeak")
    public void putGCM(final String gcmID) {
        new AsyncTask<Void, Void, WebServiceResponse>() {
            @Override
            protected WebServiceResponse doInBackground(Void... params) {

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                String user = preferences.getString(PREF_LOGIN,"");

                WebServiceResponse resp = WebServiceClientMessage.put(context,
                        baseAddress + PUT_GCM + user + "&reggcm=" + gcmID);

                return resp;
            }

            @Override
            protected void onPostExecute(WebServiceResponse webServiceResponse) {
                if (webServiceResponse.getResponseCode() == 200 || webServiceResponse.getResponseCode() == 201) {
                    userEvents.putUserGCMFinished(webServiceResponse);
                } else {
                    userEvents.putUserGCMFailed(webServiceResponse);
                }
            }
        }.execute(null, null, null);
    }


}
