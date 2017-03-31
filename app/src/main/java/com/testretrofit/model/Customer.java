package com.testretrofit.model;

import java.io.Serializable;

/**
 * Created by hclpc on 1/3/2017.
 */

public class Customer implements Serializable {

    private String customer_id;
    private String first_name;
    private String last_name;
    private String birthplace;
    private String birthdate;
    private long phone_number;
    private String mother_maiden_name;
    private String address;
    private String type_of_customer;
    private String geolocation;
    private String occupation;
    private String organization;
    private String gender;
//    private boolean prospect;
    private String prospect;

    /*private int branch_id;
    private boolean validated;
    private boolean active_status;
    private String termination_date;
    private String creation_date;
    private String created_by;*/

    public Customer(String customer_id, String first_name, String last_name, String birthplace, String birthdate, long phone_number,
                    String mother_maiden_name, String address, String type_of_customer, String geolocation, String occupation,
                    String organization, String gender, String prospect) {
        this.customer_id = customer_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.birthplace = birthplace;
        this.birthdate = birthdate;
        this.phone_number = phone_number;
        this.mother_maiden_name = mother_maiden_name;
        this.address = address;
        this.type_of_customer = type_of_customer;
        this.geolocation = geolocation;
        this.occupation = occupation;
        this.organization = organization;
        this.gender = gender;
        this.prospect = prospect;
    }

    /*public Customer(String customer_id, String first_name, String last_name, String birthplace, String birthdate, int phone_number,
                    String mother_maiden_name, String address, String type_of_customer, String geolocation, String occupation,
                    String organization, String gender, boolean prospect, int branch_id, boolean validated, boolean active_status,
                    String termination_date, String creation_date, String created_by) {
        this.customer_id = customer_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.birthplace = birthplace;
        this.birthdate = birthdate;
        this.phone_number = phone_number;
        this.mother_maiden_name = mother_maiden_name;
        this.address = address;
        this.type_of_customer = type_of_customer;
        this.geolocation = geolocation;
        this.occupation = occupation;
        this.organization = organization;
        this.gender = gender;
        this.prospect = prospect;
        this.branch_id = branch_id;
        this.validated = validated;
        this.active_status = active_status;
        this.termination_date = termination_date;
        this.creation_date = creation_date;
        this.created_by = created_by;
    }*/

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getBirthplace() {
        return birthplace;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public long getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(long phone_number) {
        this.phone_number = phone_number;
    }

    public String getMother_maiden_name() {
        return mother_maiden_name;
    }

    public void setMother_maiden_name(String mother_maiden_name) {
        this.mother_maiden_name = mother_maiden_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType_of_customer() {
        return type_of_customer;
    }

    public void setType_of_customer(String type_of_customer) {
        this.type_of_customer = type_of_customer;
    }

    public String getGeolocation() {
        return geolocation;
    }

    public void setGeolocation(String geolocation) {
        this.geolocation = geolocation;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String isProspect() {
        return prospect;
    }

    public void setProspect(String prospect) {
        this.prospect = prospect;
    }

    /*public int getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(int branch_id) {
        this.branch_id = branch_id;
    }

    public boolean isValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }

    public boolean isActive_status() {
        return active_status;
    }

    public void setActive_status(boolean active_status) {
        this.active_status = active_status;
    }

    public String getTermination_date() {
        return termination_date;
    }

    public void setTermination_date(String termination_date) {
        this.termination_date = termination_date;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }*/
}
