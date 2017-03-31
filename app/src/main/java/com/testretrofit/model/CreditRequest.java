package com.testretrofit.model;

import java.io.Serializable;

/**
 * Created by hclpc on 1/12/2017.
 */

public class CreditRequest implements Serializable {

    private String portfolio_id;
    private String creation_date;
    private String customer_id;
    private String type;
    private String created_by;
    private String status;

    public CreditRequest(String portfolio_id, String creation_date, String customer_id, String type, String created_by, String status) {
        this.portfolio_id = portfolio_id;
        this.creation_date = creation_date;
        this.customer_id = customer_id;
        this.type = type;
        this.created_by = created_by;
        this.status = status;
    }

    public String getPortfolio_id() {
        return portfolio_id;
    }

    public void setPortfolio_id(String portfolio_id) {
        this.portfolio_id = portfolio_id;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
