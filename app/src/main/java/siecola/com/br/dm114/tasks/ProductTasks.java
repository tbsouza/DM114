package siecola.com.br.dm114.tasks;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import siecola.com.br.dm114.models.Product;
import siecola.com.br.dm114.utils.WSUtil;
import siecola.com.br.dm114.webservices.WebServiceClient;
import siecola.com.br.dm114.webservices.WebServiceResponse;

public class ProductTasks {

    private static final String GET_PRODUCTS = "/api/products";
    private static final String GET_PRODUCTS_BY_CODE = "/api/products/";
    private static final String GET_ORDERS_BY_EMAIL = "/api/orders/byemail?email=";
    private static final String REGISTER_GCM_EMAIL = "/api/users/updatereggcm?email=";
    private static final String REGISTER_GCM_REGGCM = "&reggcm=";

    private ProductsEvents productsEvents;
    private Context context;
    private String address;
    private String messageAddress;

    public ProductTasks(Context context, ProductsEvents productsEvents) {
        this.context = context;
        this.productsEvents = productsEvents;

        // Pega os enderecos
        address = WSUtil.getHostAddress(context);
        messageAddress = WSUtil.getHostMessageAddress(context);
    }

    @SuppressLint("StaticFieldLeak")
    public void getProducts() {
        new AsyncTask<Void, Void, WebServiceResponse>() {
            @Override
            protected WebServiceResponse doInBackground(Void... params) {
                return WebServiceClient.get(context, address + GET_PRODUCTS);
            }

            @Override
            protected void onPostExecute(WebServiceResponse webServiceResponse) {
                if (webServiceResponse.getResponseCode() == 200) {
                    Gson gson = new Gson();

                    try {
                        List<Product> products = gson.fromJson(webServiceResponse.getResultMessage(),
                                new TypeToken<List<Product>>(){}.getType());
                        productsEvents.getProductsFinished(products);
                    } catch (Exception e) {
                        productsEvents.getProductsFailed(webServiceResponse);
                    }
                } else {
                    productsEvents.getProductsFailed(webServiceResponse);
                }
            }
        }.execute(null, null, null);
    }

    @SuppressLint("StaticFieldLeak")
    public void getProductByCode(String code) {
        new AsyncTask<String, Void, WebServiceResponse>() {
            @Override
            protected WebServiceResponse doInBackground(String... code) {
                return WebServiceClient.get(context, address + GET_PRODUCTS_BY_CODE + code[0]);
            }

            @Override
            protected void onPostExecute(WebServiceResponse webServiceResponse) {
                if (webServiceResponse.getResponseCode() == 200) {
                    Gson gson = new Gson();
                    try {
                        Product product = gson.fromJson(
                                webServiceResponse.getResultMessage(),
                                Product.class);
                        productsEvents.getProductByCodeFinished(product);
                    } catch (Exception e) {
                        productsEvents.getProductByCodeFailed(webServiceResponse);
                    }
                } else {
                    productsEvents.getProductByCodeFailed(webServiceResponse);
                }
            }
        }.execute(code, null, null);
    }

}
