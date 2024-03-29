package siecola.com.br.dm114.utils;

public class WSConstants {

    private WSConstants() {
    }

    // Constantes usadas na conexao http e tokens

    public static final String CONTENT_TYPE_JSON = "application/json";
    public static final String CONTENT_TYPE_URL_ENCODED = "application/x-www-form-urlencoded";

    public static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "POST";
    public static final String METHOD_PUT = "PUT";
    public static final String METHOD_DELETE = "DELETE";

    public static final int READ_TIMEOUT = 10000;
    public static final int CONNECTION_TIMEOUT = 15000;

    public static final String PREF_WS_ACCESS_TOKEN = "pref_ws_access_token";
    public static final String PREF_WS_ACCESS_TOKEN_EXPIRATION = "pref_ws_access_token_expiration";

    private static String PREF_LOGIN = "pref_login";
    private static String PREF_PASSWORD = "pref_password";

}
