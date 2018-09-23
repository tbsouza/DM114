package siecola.com.br.dm114.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import siecola.com.br.dm114.R;
import siecola.com.br.dm114.models.ProductInterest;

public class InterestAdapter extends BaseAdapter {

    private final Activity activity;

    //Lista de produtos de interesse
    List<ProductInterest> productsInterest;


    public InterestAdapter(Activity activity, List<ProductInterest> productsInterest) {
        this.activity = activity;
        this.productsInterest = productsInterest;
    }


    @Override
    public int getCount() {
        return productsInterest.size();
    }

    @Override
    public Object getItem(int position) {
        return productsInterest.get(position);
    }

    @Override
    public long getItemId(int position) {
        return productsInterest.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = activity.getLayoutInflater().inflate(R.layout.interest_item, null);
        ProductInterest productInterest = productsInterest.get(position);

        TextView productListItemNumber = (TextView) view.findViewById(R.id.InterestItemNumber);
        productListItemNumber.setText(Integer.toString(position + 1));

        TextView productListItemCode = (TextView) view.findViewById(R.id.InterestItemCode);
        productListItemCode.setText(productInterest.getCode());

        TextView productListItemPrice = (TextView) view.findViewById(R.id.InterestItemPrice);
        productListItemPrice.setText(Double.toString(productInterest.getPrice()));

        return view;
    }
}
