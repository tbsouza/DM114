package siecola.com.br.dm114.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import siecola.com.br.dm114.R;
import siecola.com.br.dm114.models.Order;
import siecola.com.br.dm114.tasks.OrderEvents;
import siecola.com.br.dm114.utils.CheckNetworkConnection;
import siecola.com.br.dm114.webservices.WebServiceResponse;

public class LoginFragment extends Fragment implements OrderEvents {

    // Nome do shared preference de login e senha do usuario
    private static String PREF_LOGIN = "pref_login";
    private static String PREF_PASSWORD = "pref_password";
    private SharedPreferences preferences;

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

        // verifica se ja esta salvo
        if( savedInstanceState != null ) {
            login = savedInstanceState.getString(PREF_LOGIN, "");
            password = savedInstanceState.getString(PREF_PASSWORD, "");
        }else{
            login = preferences.getString(PREF_LOGIN, "");
            password = preferences.getString(PREF_PASSWORD, "");
        }

        // tenta fazer autenticação e login
        autenticar();

        // Acao de click do botao de login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pega login e senha digitados pelo usuário
                login = editLogin.getText().toString();
                password = editSenha.getText().toString();

                // salva e tenta fazer a autenticacao
                if ( !login.isEmpty() && !password.isEmpty() ) {
                    saveLogin();
                    autenticar();
                } else {
                    Toast.makeText(getActivity(), "Digite login e senha.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }


    private void autenticar() {

        if ( CheckNetworkConnection.isNetworkConnected(getActivity()) ){

        }

        //TODO pegar tolken de acesso
        //TODO fazer autenticação no servidor de vendas
        Toast.makeText(getActivity(), "Acesso com sucesso.", Toast.LENGTH_SHORT).show();
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
