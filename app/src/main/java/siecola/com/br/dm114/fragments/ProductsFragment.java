package siecola.com.br.dm114.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import siecola.com.br.dm114.R;
import siecola.com.br.dm114.adapter.ProductAdapter;
import siecola.com.br.dm114.models.Order;
import siecola.com.br.dm114.models.Pedido;
import siecola.com.br.dm114.models.Product;
import siecola.com.br.dm114.tasks.OrderEvents;
import siecola.com.br.dm114.tasks.ProductTasks;
import siecola.com.br.dm114.tasks.ProductsEvents;
import siecola.com.br.dm114.utils.CheckNetworkConnection;
import siecola.com.br.dm114.webservices.WebServiceResponse;

public class ProductsFragment extends Fragment implements ProductsEvents {

    private ListView listViewProducts;
    private List<Product> products;

    public ProductsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_lista_produtos, container, false);

        getActivity().setTitle("Produtos");

        // diz ao android que esta classe possui um menu proprio
        setHasOptionsMenu(true);


        listViewProducts = (ListView) rootView.findViewById(R.id.listProdutos);
        // Acao de click do product
        listViewProducts.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent,
                                            View view, int position, long id) {
                        getProductDetails(position);
                    }
                });

        if (savedInstanceState != null && savedInstanceState.containsKey("products")) {
            String strObjects = savedInstanceState.getString("products");

            Gson gson = new Gson();
            this.products = gson.fromJson(strObjects, new TypeToken<List<Product>>() {
            }.getType());

            // Aplica o adapter do produto
            ProductAdapter productAdapter = new ProductAdapter(getActivity(), products);
            listViewProducts.setAdapter(productAdapter);
        } else {
            if (CheckNetworkConnection.isNetworkConnected(getActivity())) {
                ProductTasks productTasks = new ProductTasks(getActivity(), this);
                // PEga todos os produtos
                productTasks.getProducts();
            }
        }

        return rootView;
    }

    private void getProductDetails(int position) {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void getProductsFinished(List<Product> products) {
        Toast.makeText(getActivity(), "Numero de produtos listados: " + products.size(), Toast.LENGTH_SHORT).show();

        // Aplica o adapter do produto
        ProductAdapter productAdapter = new ProductAdapter(getActivity(), products);
        listViewProducts.setAdapter(productAdapter);
    }

    @Override
    public void getProductsFailed(WebServiceResponse webServiceResponse) {
        Toast.makeText(getActivity(), "Erro ao listar produtos ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getProductByCodeFinished(Product product) {

    }

    @Override
    public void getProductByCodeFailed(WebServiceResponse webServiceResponse) {

    }


}
