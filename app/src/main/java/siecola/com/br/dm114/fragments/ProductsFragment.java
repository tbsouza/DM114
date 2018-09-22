package siecola.com.br.dm114.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import siecola.com.br.dm114.R;
import siecola.com.br.dm114.models.Order;
import siecola.com.br.dm114.models.Pedido;
import siecola.com.br.dm114.tasks.OrderEvents;
import siecola.com.br.dm114.webservices.WebServiceResponse;

public class ProductsFragment extends Fragment implements OrderEvents {

    private ListView listViewPedidos;
    private List<Pedido> pedidos;

    public ProductsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_lista_produtos, container, false);

        getActivity().setTitle("Pedidos");

        // diz ao android que esta classe possui um menu proprio
        setHasOptionsMenu(true);


        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //inflater.inflate(R.menu.pedidos_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.novo_pedido:
//                Toast.makeText(getActivity(), "Novo pedido", Toast.LENGTH_SHORT).show();
//                return true;
//        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void getOrdersFinished(List<Order> orders) {

        Toast.makeText(getActivity(), "Numero de pedidos: " + orders.size(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void getOrdersFailed(WebServiceResponse webServiceResponse) {
        Toast.makeText(getActivity(), "Erro", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getOrderByIdFinished(Order order) {

    }

    @Override
    public void getOrderByIdFailed(WebServiceResponse webServiceResponse) {

    }
}
