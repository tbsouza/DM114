package siecola.com.br.dm114.adapter;


import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import siecola.com.br.dm114.R;
import siecola.com.br.dm114.models.Order;

// Adaptador de orders
public class OrderAdapter extends BaseAdapter {

    private final Activity activity;

    // Lista de orders
    List<Order> orders;

    public OrderAdapter(Activity activity, List<Order> orders) {
        this.activity = activity;
        this.orders = orders;
    }

    @Override
    public int getCount() {
        return orders.size();
    }

    @Override
    public Object getItem(int position) {
        return orders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return orders.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = activity.getLayoutInflater().inflate(R.layout.order_item, null);
        Order order = orders.get(position);

        TextView orderListItemNumber = (TextView) view.findViewById(R.id.orderListItemNumber);
        orderListItemNumber.setText(Integer.toString(position + 1));

        TextView orderListItemId = (TextView) view.findViewById(R.id.orderListItemId);
        orderListItemId.setText(Long.toString(order.getId()));

        TextView orderListFreigth = (TextView) view.findViewById(R.id.orderListFreigth);
        orderListFreigth.setText(Double.toString(order.getFreightPrice()));

        TextView orderListItems = (TextView) view.findViewById(R.id.orderListItems);
        orderListItems.setText(Integer.toString(order.getOrderItems().size()));

        return view;
    }
}
