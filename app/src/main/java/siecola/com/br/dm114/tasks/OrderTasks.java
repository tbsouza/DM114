package siecola.com.br.dm114.tasks;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import siecola.com.br.dm114.models.Order;
import siecola.com.br.dm114.models.User;
import siecola.com.br.dm114.utils.WSUtil;
import siecola.com.br.dm114.webservices.WebServiceClient;
import siecola.com.br.dm114.webservices.WebServiceResponse;

public class OrderTasks {

    private static final String GET_ORDERS = "/api/orders";
    private static final String GET_ORDER_BY_ID = "/api/orders";
    private static final String GET_ORDER_BY_EMAIL = "/api/orders/byemail?email=";
    private OrderEvents orderEvents;
    private Context context;
    private String baseAddress;

    public OrderTasks(Context context, OrderEvents orderEvents) {
        String host;
        int port;
        this.context = context;
        this.orderEvents = orderEvents;
        baseAddress = WSUtil.getHostAddress(context);
    }

    @SuppressLint("StaticFieldLeak")
    public void getOrders() {

        // Cria e execua uma thread assincrona
        new AsyncTask<Void, Void, WebServiceResponse>() {

            @Override
            protected WebServiceResponse doInBackground(Void... params) {
                return WebServiceClient.get(context, baseAddress + GET_ORDERS);
            }

            @Override
            protected void onPostExecute( WebServiceResponse webServiceResponse) {
                if (webServiceResponse.getResponseCode() == 200) {
                    Gson gson = new Gson();
                    try {
                        // Converte de JSON para Lista de orders
                        List<Order> orders = gson.fromJson(
                                webServiceResponse.getResultMessage(),
                                new TypeToken<List<Order>>() {
                                }.getType());
                        orderEvents.getOrdersFinished(orders);
                    } catch (Exception e) {
                        orderEvents.getOrdersFailed(webServiceResponse);
                    }
                } else {
                    orderEvents.getOrdersFailed(webServiceResponse);
                }
            }
        }.execute(null, null, null);
    }


    @SuppressLint("StaticFieldLeak")
    public void getOrderById(long id) {

        new AsyncTask<Long, Void, WebServiceResponse>() {

            @Override
            protected WebServiceResponse doInBackground(Long... id) {
                return WebServiceClient.get(context, baseAddress + GET_ORDER_BY_ID + "/" + Long.toString(id[0]));
            }

            @Override
            protected void onPostExecute(WebServiceResponse webServiceResponse) {

                if (webServiceResponse.getResponseCode() == 200) {
                    Gson gson = new Gson();
                    try {
                        Order order = gson.fromJson(webServiceResponse.getResultMessage(), Order.class);
                        orderEvents.getOrderByIdFinished(order);
                    } catch (Exception e) {
                        orderEvents.getOrderByIdFailed(webServiceResponse);
                    }
                } else {
                    orderEvents.getOrderByIdFailed(webServiceResponse);
                }
            }

        }.execute(id, null, null);
    }

    @SuppressLint("StaticFieldLeak")
    public void getOrdersByEmail(final String email) {

        new AsyncTask<Long, Void, WebServiceResponse>() {

            @Override
            protected WebServiceResponse doInBackground(Long... id) {
                return WebServiceClient.get(context, baseAddress + GET_ORDER_BY_EMAIL + email );
            }

            @Override
            protected void onPostExecute(WebServiceResponse webServiceResponse) {

                if (webServiceResponse.getResponseCode() == 200) {
                    Gson gson = new Gson();
                    try {
                        Order order = gson.fromJson(webServiceResponse.getResultMessage(), Order.class);
                        orderEvents.getOrderByIdFinished(order);
                    } catch (Exception e) {
                        orderEvents.getOrderByIdFailed(webServiceResponse);
                    }
                } else {
                    orderEvents.getOrderByIdFailed(webServiceResponse);
                }
            }

        }.execute(null, null, null);
    }





}
