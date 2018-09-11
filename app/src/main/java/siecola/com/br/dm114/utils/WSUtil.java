package siecola.com.br.dm114.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import siecola.com.br.dm114.R;

public class WSUtil {

    // Pega infos do endereco do servidor no shared preference
    //  e devolve ja formatada

    private WSUtil() {
    }

    // Pega o endereco do host do servico de vendas
    public static String getHostAddress(Context context) {
        String host;
        String baseAddress;
        int port;
        SharedPreferences sharedSettings = PreferenceManager.getDefaultSharedPreferences(context);

        host = sharedSettings.getString(
                context.getString(R.string.pref_ws_host),
                context.getString(R.string.pref_ws_default_host));

        port = sharedSettings.getInt(context.getString(R.string.pref_host_port),
                Integer.parseInt(context.getString(R.string.pref_ws_default_port)));

        if (host.endsWith("/")) {
            host = host.substring(0, host.length() - 1);
        }

        if (!host.startsWith("http://")) {
            host = "https://" + host;
        }


        baseAddress = host + ":" + port;
        return baseAddress;
    }


    // Pega o endereco do host do servico de mensagem
    public static String getHostMessageAddress (Context context) {
        String host;
        String baseAddress;
        int port;

        SharedPreferences sharedSettings = PreferenceManager.getDefaultSharedPreferences(context);

        host = sharedSettings.getString(context.getString(R.string.pref_ws_host_message),
                context.getString(R.string.pref_ws_default_host_message));

        port = sharedSettings.getInt(context.getString(R.string.pref_host_port),
                Integer.parseInt(context.getString(R.string.pref_ws_default_port)));

        if (host.endsWith("/")) {
            host = host.substring(0, host.length() - 1);
        }

        if (!host.startsWith("http://")) {
            host = "http://" + host;
        }

        baseAddress = host + ":" + port;

        return baseAddress;
    }
}
