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
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import siecola.com.br.dm114.R;
import siecola.com.br.dm114.gcm.GCMRegister;
import siecola.com.br.dm114.gcm.GCMRegisterEvents;
import siecola.com.br.dm114.models.OrderInfo;

public class GCMFragment extends Fragment implements GCMRegisterEvents {

    private SharedPreferences preferences;
    static final String PROPERTY_SENDER_ID = "senderId";

    private Button btnUnregister;
    private Button btnRegister;
    private Button btnClearMessage;
    private TextView txtRegistrationID;
    private TextView txtOrderId;
    private TextView txtEmail;
    private TextView txtStatus;
    private TextView txtReason;
    private EditText edtSenderId;

    private OrderInfo orderInfo;
    private String registrationID;
    private GCMRegister gcmRegister;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().setTitle("GCM - Notificações");

        // Infla a activity a ser exibida
        View rootView = inflater.inflate(R.layout.fragment_gcm, container, false);

        // Instancia o shared preferences
        preferences = getActivity().getSharedPreferences(getActivity().getClass().getSimpleName(), Context.MODE_PRIVATE);

        // Instancia GCMRegister se não instanciado
        if (gcmRegister == null) {
            gcmRegister = new GCMRegister(getActivity(), this);
        }

        // bundle
        final Bundle bundle = this.getArguments();

        btnRegister = (Button) rootView.findViewById(R.id.btnRegister);
        btnUnregister = (Button) rootView.findViewById(R.id.btnUnregister);
        btnClearMessage = (Button) rootView.findViewById(R.id.btnClearMessage);
        txtRegistrationID = (TextView) rootView.findViewById(R.id.txtRegistrationID);
        txtOrderId = (TextView) rootView.findViewById(R.id.txtOrderId);
        txtEmail = (TextView) rootView.findViewById(R.id.txtEmail);
        txtStatus = (TextView) rootView.findViewById(R.id.txtStatus);
        txtReason = (TextView) rootView.findViewById(R.id.txtReason);
        edtSenderId = (EditText) rootView.findViewById(R.id.edtSenderID);

        btnUnregister.setEnabled(true);
        btnRegister.setEnabled(true);

        // botao de registrar
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = preferences.edit();

                // pega o registration id digitado
                String senderId = edtSenderId.getText().toString();
                editor.putString(PROPERTY_SENDER_ID, senderId);

                if(senderId.isEmpty()){
                    Toast.makeText(getActivity(), "Digite o sender ID", Toast.LENGTH_SHORT).show();
                }else {
                    registrationID = gcmRegister.getRegistrationId(senderId);

                    if ( (registrationID == null) || (registrationID.length() == 0) ) {
                        Toast.makeText(getActivity(), "Tentando Registrar dispositivo.", Toast.LENGTH_SHORT).show();
                        setValueClear(); // limpa os campos
                    } else {
                        // se nao for vazio registra
                        register();
                        setArgumentsGCM( bundle );
                    }
                }
            }
        });

        //botao de unregistrar
        btnUnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // unregistra do GCM
                unregister();
            }
        });

        // botao de limpar
        btnClearMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Limpa os campos do fragment
                txtOrderId.setText("");
                txtEmail.setText("");
                txtStatus.setText("");
                txtReason.setText("");
            }
        });

        // Se GCM não tiver expirado
        if (!gcmRegister.isRegistrationExpired()) {
            registrationID = gcmRegister.getCurrentRegistrationId();
            setValue(registrationID);
        } else {
            setValueClear(); // limpa os campos
        }

        setArgumentsGCM( this.getArguments() );

        return rootView;
    }

    private void setArgumentsGCM( Bundle bundle ){
        if ((bundle != null) && (bundle.containsKey("orderInfo"))) {
            orderInfo = (OrderInfo) bundle.getSerializable("orderInfo");
            txtOrderId.setText(Long.toString(orderInfo.getId()));
            txtEmail.setText(orderInfo.getEmail());
            txtStatus.setText(orderInfo.getStatus());
            txtReason.setText(orderInfo.getReason());
        }
    }

    private void unregister() {
        // unregistra do GCM
        gcmRegister.unRegister();
    }

    private void register() {
        // Verifica se ainda não expirou
        gcmRegister.registerBackground();
    }


    @Override
    public void gcmRegisterFinished(String registrationID) {
        // Apos registrar com sucesso
        Toast.makeText(getActivity(), "Registrado com sucesso.", Toast.LENGTH_LONG).show();
        // atualiza os campos na tela
        setValue(registrationID);
    }

    @Override
    public void gcmRegisterFailed(IOException ex) {
        // Registro com falha
        Toast.makeText(getActivity(), "Falha ao registrar.", Toast.LENGTH_SHORT).show();
        // atualiza os campos na tela
        setValueClear();
    }

    @Override
    public void gcmUnregisterFinished() {
        // Unregistro com sucesso
        Toast.makeText(getActivity(), "Unregistrado com sucesso.", Toast.LENGTH_SHORT).show();
        // Limpa os campos
        setValueClear();
    }

    @Override
    public void gcmUnregisterFailed(IOException ex) {
        // Unregistro com falha
        Toast.makeText(getActivity(), "Falha ao unregistrar.", Toast.LENGTH_SHORT).show();
    }


    public void setValue(String value) {
        txtRegistrationID.setText(value);
    }

    public void setValueClear() {
        txtRegistrationID.setText("");
    }
}
