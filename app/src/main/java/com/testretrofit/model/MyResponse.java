package com.testretrofit.model;

import java.io.Serializable;

/**
 * Created by hclpc on 1/13/2017.
 */

public class MyResponse implements Serializable {

    private String result;
    private String activity;
    private String portfolio_id;

    public MyResponse(String result, String activity) {
        this.result = result;
        this.activity = activity;
    }

    public MyResponse(String result, String activity, String portfolio_id) {
        this.result = result;
        this.activity = activity;
        this.portfolio_id = portfolio_id;
    }

    @Override
    public String toString() {
        return "MyResponse : " + result + ", " + activity;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getPortfolio_id() {
        return portfolio_id;
    }

    public void setPortfolio_id(String portfolio_id) {
        this.portfolio_id = portfolio_id;
    }
}
