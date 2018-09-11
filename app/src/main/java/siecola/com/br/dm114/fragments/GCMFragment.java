package siecola.com.br.dm114.fragments;

import android.app.Fragment;

import java.io.IOException;

import siecola.com.br.dm114.gcm.GCMRegisterEvents;

public class GCMFragment extends Fragment implements GCMRegisterEvents {


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
