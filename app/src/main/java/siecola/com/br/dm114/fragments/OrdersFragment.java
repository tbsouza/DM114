package siecola.com.br.dm114.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import siecola.com.br.dm114.R;
import siecola.com.br.dm114.models.Order;
import siecola.com.br.dm114.models.Pedido;
import siecola.com.br.dm114.tasks.OrderEvents;
import siecola.com.br.dm114.tasks.OrderTasks;
import siecola.com.br.dm114.utils.CheckNetworkConnection;
import siecola.com.br.dm114.webservices.WebServiceResponse;

public class OrdersFragment extends Fragment implements OrderEvents {

    // Mostra detalhes do pedido

    private ListView listViewOrders;
    private List<Order> orders;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_orders_list, container, false);
        getActivity().setTitle("Pedidos");

        // Acao de click para exibir detalhes
        listViewOrders = (ListView) rootView.findViewById(R.id.orders_list);
        listViewOrders.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        startOrderDetail(id);
                    }
                }
        );

        // Verifica se tem conexao
        if (CheckNetworkConnection.isNetworkConnected(getActivity())) {
            OrderTasks orderTasks = new OrderTasks(getActivity(), this);
            // pega todas as orders
            orderTasks.getOrders();
        }

        return rootView;
    }


    private void startOrderDetail(long orderId) {
        Class fragmentClass;
        Fragment fragment = null;

        fragmentClass = OrderDetailFragment.class;

        try {
            fragment = (Fragment) fragmentClass.newInstance();

            if (orderId >= 0) {
                Bundle args = new Bundle();
                args.putLong("order_id", orderId);
                fragment.setArguments(args);
            }

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            transaction.replace(R.id.container, fragment, OrderDetailFragment.class.getCanonicalName());
            transaction.addToBackStack(OrdersFragment.class.getCanonicalName());

            transaction.commit();
        } catch (Exception e) {
            try {
                Toast.makeText(getActivity(), "Erro ao tentar abrir detalhes do pedido", Toast.LENGTH_LONG).show();
            } catch (Exception e1) {
            }
        }
    }


    @Override
    public void getOrdersFailed(WebServiceResponse webServiceResponse) {
        Toast.makeText(getActivity(), "Falha na consulta da lista de pedidos" +
                webServiceResponse.getResultMessage() + " - Código do erro: " +
                webServiceResponse.getResponseCode(), Toast.LENGTH_LONG).show();
    }


    @Override
    public void getOrderByIdFinished(Order order) {

    }

    @Override
    public void getOrderByIdFailed(WebServiceResponse webServiceResponse) {

    }


    @Override
    public void getOrdersFinished(List<Order> orders) {
        this.orders = orders; // salva as order na variavel

       // OrderAdapter orderAdapter = new OrderAdapter(getActivity(), orders);
       // listViewOrders.setAdapter(orderAdapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Gson gson = new Gson();
        // pega a variavel com orders e salva so shared preferences para nao perder ao virar a tela
        outState.putString("orders", gson.toJson(orders));

        super.onSaveInstanceState(outState);
    }


}
