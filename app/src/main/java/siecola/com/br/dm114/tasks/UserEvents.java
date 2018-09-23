package siecola.com.br.dm114.tasks;

import siecola.com.br.dm114.webservices.WebServiceResponse;

public interface UserEvents {

    void putUserGCMFinished(WebServiceResponse webServiceResponse);
    void putUserGCMFailed(WebServiceResponse webServiceResponse);

}
