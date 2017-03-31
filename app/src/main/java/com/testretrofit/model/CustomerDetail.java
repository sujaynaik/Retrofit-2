package com.testretrofit.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hclpc on 1/3/2017.
 */

public class CustomerDetail implements Serializable {

    private List<CreditRequest> credit_requests;
    private Customer customer;
    private Customer cutomer;

    public CustomerDetail(List<CreditRequest> credit_requests, Customer customer) {
        this.credit_requests = credit_requests;
        this.customer = customer;
    }

    public CustomerDetail(Customer cutomer, List<CreditRequest> credit_requests) {
        this.cutomer = cutomer;
        this.credit_requests = credit_requests;
    }

    public List<CreditRequest> getCredit_requests() {
        return credit_requests;
    }

    public void setCredit_requests(List<CreditRequest> credit_requests) {
        this.credit_requests = credit_requests;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Customer getCutomer() {
        return cutomer;
    }

    public void setCutomer(Customer cutomer) {
        this.cutomer = cutomer;
    }
}
