package siecola.com.br.dm114.fragments;

import java.util.List;

import siecola.com.br.dm114.models.ProductInterest;
import siecola.com.br.dm114.tasks.ProductsInterestEvents;
import siecola.com.br.dm114.webservices.WebServiceResponse;

public class InterestFragment implements ProductsInterestEvents {




    @Override
    public void getProductsInterestFinished(List<ProductInterest> productsInterest) {

    }

    @Override
    public void getProductsInterestFailed(WebServiceResponse webServiceResponse) {

    }

    @Override
    public void getProductsInterestByEmailFinished(List<ProductInterest> productsInterest) {

    }

    @Override
    public void getProductsInterestByEmailFailed(WebServiceResponse webServiceResponse) {

    }

    @Override
    public void postProductInterestFinished(String message) {

    }

    @Override
    public void postProductInterestFailed(WebServiceResponse webServiceResponse) {

    }

    @Override
    public void deleteProductInterestFinished(String message) {

    }

    @Override
    public void deleteProductInterestFailed(WebServiceResponse webServiceResponse) {

    }
}
