package siecola.com.br.dm114.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import siecola.com.br.dm114.R;
import siecola.com.br.dm114.models.Product;

// Adaptador de produtos
public class ProductAdapter extends BaseAdapter {

    private final Activity activity;

    // Lista de pedidos
    List<Product> products;

    public ProductAdapter(Activity activity, List<Product> products) {
        this.activity = activity;
        this.products = products;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // infla o fragmento do produto
        View view = activity.getLayoutInflater().inflate(R.layout.product_item, null);

        Product product = products.get(position);

        TextView productListItemNumber = (TextView) view.findViewById(R.id.productListItemNumber);
        productListItemNumber.setText(Integer.toString(position + 1));

        TextView productListItemCode = (TextView) view.findViewById(R.id.productListItemCode);
        productListItemCode.setText(product.getCode());

        TextView productListItemName = (TextView) view.findViewById(R.id.productListItemName);
        productListItemName.setText(product.getName());

        TextView productListItemPrice = (TextView) view.findViewById(R.id.productListItemPrice);
        productListItemPrice.setText(Double.toString(product.getPrice()));

        return view;
    }
}
