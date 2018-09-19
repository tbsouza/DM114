package siecola.com.br.dm114.fragments;

import android.app.Activity;
import android.app.Fragment;
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
import siecola.com.br.dm114.models.Order;
import siecola.com.br.dm114.tasks.OrderEvents;
import siecola.com.br.dm114.utils.CheckNetworkConnection;
import siecola.com.br.dm114.utils.WSConstants;
import siecola.com.br.dm114.utils.WSUtil;
import siecola.com.br.dm114.webservices.WebServiceClient;
import siecola.com.br.dm114.webservices.WebServiceResponse;

public class LoginFragment extends Fragment implements OrderEvents {

    // Nome do shared preference de login e senha do usuario
    private static String PREF_LOGIN = "pref_login";
    private static String PREF_PASSWORD = "pref_password";
    private SharedPreferences preferences;
    private GCMRegister gcmRegister;

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

        // Infla a activity a ser exibida
        View rootView = inflater.inflate(R.layout.content_main, container, false);

        // Instancia do shared preferences
        preferences = getActivity().getSharedPreferences(getActivity().getClass().getSimpleName(), Context.MODE_PRIVATE);

        editLogin = (EditText) rootView.findViewById(R.id.editLogin);
        editSenha = (EditText) rootView.findViewById(R.id.editSenha);
        btnLogin = (Button) rootView.findViewById(R.id.btnLogin);


        // Registra access token
        // registra GCM


        // verifica se ja esta salvo no shared preferences
        login = preferences.getString(PREF_LOGIN, "");
        password = preferences.getString(PREF_PASSWORD, "");

        if (!login.isEmpty() && !password.isEmpty()) {
            // tenta fazer autenticação e login
            autenticar();
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


    }


    private void autenticar() {

        if (CheckNetworkConnection.isNetworkConnected(getActivity())) {
            try {
                WebServiceResponse response = WebServiceClient.init(getActivity(), WSUtil.getHostAddress(getActivity()), WSConstants.METHOD_GET);
                Toast.makeText(getActivity(), "Autenticação: " + response.getResponseMessage(), Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getActivity(), "Sem conexão com internet.", Toast.LENGTH_SHORT).show();
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
    public void getOrdersFinished(List<Order> orders) {

    }

    @Override
    public void getOrdersFailed(WebServiceResponse webServiceResponse) {

    }

    @Override
    public void getOrderByIdFinished(Order order) {

    }

    @Override
    public void getOrderByIdFailed(WebServiceResponse webServiceResponse) {

    }
}
