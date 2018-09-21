package siecola.com.br.dm114.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import siecola.com.br.dm114.R;
import siecola.com.br.dm114.gcm.GCMRegister;
import siecola.com.br.dm114.gcm.GCMRegisterEvents;

public class GCMFragment extends Fragment implements GCMRegisterEvents {

    private SharedPreferences preferences;

    private Button btnUnregister;
    private Button btnRegister;
    private Button btnClearMessage;
    private TextView txtRegistrationID;
    private TextView txtOrderId;
    private TextView txtEmail;
    private TextView txtStatus;
    private TextView txtReason;

    private String registrationID;
    private GCMRegister gcmRegister;
    //private OrderInfo orderInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().setTitle("GCM - Notificações");

        // Infla a activity a ser exibida
        View rootView = inflater.inflate(R.layout.fragment_gcm, container, false);

        // Instancia do shared preferences
        preferences = getActivity().getSharedPreferences(getActivity().getClass().getSimpleName(), Context.MODE_PRIVATE);

        btnRegister = (Button) rootView.findViewById(R.id.btnRegister);
        btnUnregister = (Button) rootView.findViewById(R.id.btnUnregister);
        btnClearMessage = (Button) rootView.findViewById(R.id.btnClearMessage);
        txtRegistrationID = (TextView) rootView.findViewById(R.id.txtRegistrationID);
        txtOrderId = (TextView) rootView.findViewById(R.id.txtOrderId);
        txtEmail = (TextView) rootView.findViewById(R.id.txtEmail);
        txtStatus = (TextView) rootView.findViewById(R.id.txtStatus);
        txtReason = (TextView) rootView.findViewById(R.id.txtReason);


        // botao de registrar
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // pega o geistration id digitado
                registrationID = gcmRegister.getRegistrationId();


                if ( registrationID.isEmpty() ) {
                    Toast.makeText(getActivity(), "Digite o registration ID", Toast.LENGTH_SHORT).show();
                }
                else {
                    // se nao for vazio registra
                    register(registrationID);
                }
            }
        });

        //botao de unregistrar
        btnUnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gcmRegister.unRegister();
            }
        });

        // botao de limpar
        btnClearMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // limpa os campos
                txtOrderId.setText("");
                txtEmail.setText("");
                txtStatus.setText("");
                txtReason.setText("");
            }
        });

        return rootView;
    }

    private void unregister() {
    }

    private void register(String registrationID) {
    }


    @Override
    public void gcmRegisterFinished(String registrationID) {

    }

    @Override
    public void gcmRegisterFailed(IOException ex) {

    }

    @Override
    public void gcmUnregisterFinished() {

    }

    @Override
    public void gcmUnregisterFailed(IOException ex) {

    }
}
