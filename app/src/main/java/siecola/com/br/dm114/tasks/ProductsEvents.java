package siecola.com.br.dm114.tasks;

import java.util.List;

import siecola.com.br.dm114.models.Product;
import siecola.com.br.dm114.webservices.WebServiceResponse;

public interface ProductsEvents {

    void getProductsFinished(List<Product> products);
    void getProductsFailed(WebServiceResponse webServiceResponse);
    void getProductByCodeFinished(Product product);
    void getProductByCodeFailed(WebServiceResponse webServiceResponse);

}
