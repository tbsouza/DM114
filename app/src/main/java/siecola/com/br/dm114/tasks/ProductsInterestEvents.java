package siecola.com.br.dm114.tasks;

import java.util.List;

import siecola.com.br.dm114.models.ProductInterest;
import siecola.com.br.dm114.webservices.WebServiceResponse;

public interface ProductsInterestEvents {

    void getProductsInterestFinished(List<ProductInterest> productsInterest);
    void getProductsInterestFailed(WebServiceResponse webServiceResponse);
    void getProductsInterestByEmailFinished(List<ProductInterest> productsInterest);
    void getProductsInterestByEmailFailed(WebServiceResponse webServiceResponse);
    void postProductInterestFinished(String message);
    void postProductInterestFailed(WebServiceResponse webServiceResponse);
    void deleteProductInterestFinished(String message);
    void deleteProductInterestFailed(WebServiceResponse webServiceResponse);

}
