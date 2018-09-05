package siecola.com.br.dm114.webservices;

public class WebServiceResponse {

    // modelo de comunicação
    // resultado que o webservice retorna

    private int responseCode;
    private String responseMessage;
    private String resultMessage;


    //getters and setters
    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

}
