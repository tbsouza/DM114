package siecola.com.br.dm114.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;

import siecola.com.br.dm114.R;
import siecola.com.br.dm114.gcm.GCMRegisterEvents;

public class GCMFragment extends Fragment implements GCMRegisterEvents {

    private SharedPreferences preferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().setTitle("GCM - Notificações");

        // Infla a activity a ser exibida
        View rootView = inflater.inflate(R.layout.fragment_gcm, container, false);

        // Instancia do shared preferences
        preferences = getActivity().getSharedPreferences(getActivity().getClass().getSimpleName(), Context.MODE_PRIVATE);


        return rootView;
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
