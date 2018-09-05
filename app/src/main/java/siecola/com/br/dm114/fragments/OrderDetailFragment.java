package siecola.com.br.dm114.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import siecola.com.br.dm114.R;
import siecola.com.br.dm114.models.Order;
import siecola.com.br.dm114.tasks.OrderEvents;
import siecola.com.br.dm114.tasks.OrderTasks;
import siecola.com.br.dm114.utils.CheckNetworkConnection;
import siecola.com.br.dm114.webservices.WebServiceResponse;

public class OrderDetailFragment extends Fragment implements OrderEvents {

    private TextView txtId;
    private TextView txtEmail;
    private TextView txtFrete;
    private TextView txtQuantidade;


    public OrderDetailFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_order_detail, container, false);
        getActivity().setTitle("Orders");


        txtId = rootView.findViewById(R.id.txtId);
        //txtEmail = rootView.findViewById(R.id.txtEmail);
        txtFrete = rootView.findViewById(R.id.txtFrete);
        txtQuantidade = rootView.findViewById(R.id.txtQuantidade);


        getActivity().setTitle("Order Detail");


        Bundle arguments = getArguments();

        if (arguments != null && arguments.containsKey("order_id")) {
            long orderId = arguments.getLong("order_id");

            if (CheckNetworkConnection.isNetworkConnected(getActivity())) {
                OrderTasks orderTasks = new OrderTasks(getActivity(), this);
                orderTasks.getOrderById(orderId);
            } else {
                Toast.makeText(getActivity(), "Dispositivo não conectado a internet!", Toast.LENGTH_SHORT).show();
            }

        }

        return rootView;
    }


    @Override
    public void getOrdersFinished(List<Order> orders) {

    }

    @Override
    public void getOrdersFailed(WebServiceResponse webServiceResponse) {

        Toast.makeText(getActivity(), "Falha na consulta do pedido" +
                webServiceResponse.getResponseMessage() + "- Código do erro: " +
                webServiceResponse.getResponseCode(), Toast.LENGTH_SHORT).show();

    }


    @Override
    public void getOrderByIdFinished(Order order) {
        txtId.setText(String.valueOf(order.getId()));
        txtEmail.setText(String.valueOf(order.getEmail()));
        txtFrete.setText(String.valueOf(order.getFreightPrice()));
        txtQuantidade.setText(String.valueOf(order.getOrderItems().size()));
    }


    @Override
    public void getOrderByIdFailed(WebServiceResponse webServiceResponse) {

    }
}
