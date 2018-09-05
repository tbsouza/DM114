package siecola.com.br.dm114.tasks;

import java.util.List;

import siecola.com.br.dm114.models.Order;
import siecola.com.br.dm114.webservices.WebServiceResponse;

public interface OrderEvents {

    void getOrdersFinished(List<Order> orders);
    void getOrdersFailed(WebServiceResponse webServiceResponse);
    void getOrderByIdFinished(Order order);
    void getOrderByIdFailed(WebServiceResponse webServiceResponse);

}
