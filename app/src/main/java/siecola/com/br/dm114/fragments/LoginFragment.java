package siecola.com.br.dm114.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import siecola.com.br.dm114.R;
import siecola.com.br.dm114.gcm.GCMRegister;
import siecola.com.br.dm114.gcm.GCMRegisterEvents;
import siecola.com.br.dm114.models.Order;
import siecola.com.br.dm114.tasks.OrderEvents;
import siecola.com.br.dm114.utils.CheckNetworkConnection;
import siecola.com.br.dm114.utils.WSConstants;
import siecola.com.br.dm114.utils.WSUtil;
import siecola.com.br.dm114.webservices.WebServiceClient;
import siecola.com.br.dm114.webservices.WebServiceResponse;

public class LoginFragment extends Fragment implements GCMRegisterEvents {

    // Nome do shared preference de login e senha do usuario
    private static String PREF_LOGIN = "pref_login";
    private static String PREF_PASSWORD = "pref_password";
    private static String SENDER_ID = "574033931406";

    private SharedPreferences preferences;
    private GCMRegister gcmRegister;
    private String registrationID;

    // Variaveis de login e senha
    private String login;
    private String password;

    // Campos da activity
    private EditText editLogin;
    private EditText editSenha;
    private Button btnLogin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().setTitle("Login");

        // Infla a activity a ser exibida
        View rootView = inflater.inflate(R.layout.content_main, container, false);

        // Instancia do shared preferences
        preferences = getActivity().getSharedPreferences(getActivity().getClass().getSimpleName(), Context.MODE_PRIVATE);

        editLogin = (EditText) rootView.findViewById(R.id.editLogin);
        editSenha = (EditText) rootView.findViewById(R.id.editSenha);
        btnLogin = (Button) rootView.findViewById(R.id.btnLogin);

        // verifica se ja esta salvo no shared preferences
        login = preferences.getString(PREF_LOGIN, "");
        password = preferences.getString(PREF_PASSWORD, "");

        if (!login.isEmpty() && !password.isEmpty()) {
            // tenta fazer autenticação
            autenticar();
            registerGCM( getActivity() );
        }

        // Acao de click do botao de login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pega login e senha digitados pelo usuário
                login = editLogin.getText().toString();
                password = editSenha.getText().toString();

                // salva e tenta fazer a autenticacao
                if (!login.isEmpty() && !password.isEmpty()) {
                    saveLogin(); // salva na sharede preoferences
                    autenticar(); // tenta fazer autenticação e login
                    registerGCM( getActivity() );
                } else {
                    Toast.makeText(getActivity(), "Digite login e senha.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }

    private void registerGCM(Activity activity) {
        // Registr no GCM

        SharedPreferences.Editor editor = preferences.edit();

        // Instancia GCMRegister
        if (gcmRegister == null) {
            gcmRegister = new GCMRegister(getActivity(), this);
        }

        // Pega o Registration ID
        registrationID = gcmRegister.getRegistrationId(SENDER_ID);

        if ((registrationID == null) || (registrationID.length() == 0)) {
            Toast.makeText(getActivity(), "Tesntando registrar Dispositivo.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Dispositivo registrado no GCM.", Toast.LENGTH_SHORT).show();
            gcmRegister.registerBackground();
            Toast.makeText(getActivity(), registrationID, Toast.LENGTH_SHORT).show();
        }
    }

    private void autenticar() {

        if (CheckNetworkConnection.isNetworkConnected(getActivity())) {
            try {
                WebServiceResponse response = WebServiceClient.init(getActivity(), WSUtil.getHostAddress(getActivity()), WSConstants.METHOD_GET);
                if( response.getResponseCode() == 200 ){
                    Toast.makeText(getActivity(), "Autenticado com sucesso: ", Toast.LENGTH_SHORT).show();
                    // Redireciona para tela de pedidos
                    changeFragment( OrdersFragment.class );
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getActivity(), "Sem conexão com internet.", Toast.LENGTH_SHORT).show();
        }
    }

    private void changeFragment(Class fragmentClass) {

        try {
            Fragment fragment = (Fragment) fragmentClass.newInstance();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.main_container, fragment).commit();
        } catch ( Exception e) {
            e.printStackTrace();
        }
    }

    // Salva login e senha no shared preferences
    private void saveLogin() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_LOGIN, login);
        editor.putString(PREF_PASSWORD, password);
        editor.commit();
    }



    @Override
    public void gcmRegisterFinished(String registrationID) {
        Toast.makeText(getActivity(), "GCM registrado com sucesso.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void gcmRegisterFailed(IOException ex) {
        Toast.makeText(getActivity(), "Falha ao registrar no GCM.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void gcmUnregisterFinished() {

    }

    @Override
    public void gcmUnregisterFailed(IOException ex) {

    }
}