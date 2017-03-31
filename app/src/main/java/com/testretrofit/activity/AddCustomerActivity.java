package com.testretrofit.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.testretrofit.R;
import com.testretrofit.model.Customer;
import com.testretrofit.model.MyResponse;
import com.testretrofit.util.Constant;
import com.testretrofit.util.RestClient;
import com.testretrofit.util.Utils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCustomerActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = AddCustomerActivity.class.getSimpleName();
    private Context context = AddCustomerActivity.this;
    @BindView(R.id.tvCustomerId)
    EditText tvCustomerId;
    @BindView(R.id.tvFirstName)
    EditText tvFirstName;
    @BindView(R.id.tvLastName)
    EditText tvLastName;
    @BindView(R.id.tvBirthPlace)
    EditText tvBirthPlace;
    @BindView(R.id.tvBirthDate)
    TextView tvBirthDate;
    @BindView(R.id.tvPhone)
    EditText tvPhone;
    @BindView(R.id.tvMother)
    EditText tvMother;
    @BindView(R.id.tvAddress)
    EditText tvAddress;
    @BindView(R.id.spType)
    Spinner spType;
    @BindView(R.id.tvLocation)
    TextView tvLocation;
    @BindView(R.id.tvOccupation)
    EditText tvOccupation;
    @BindView(R.id.tvOrganization)
    EditText tvOrganization;
    @BindView(R.id.rgGender)
    RadioGroup rgGender;
    @BindView(R.id.bSave)
    Button bSave;

    private DateFormat sdf;
    private Call<MyResponse> call = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);
        ButterKnife.bind(this);
        sdf = new SimpleDateFormat(Constant.DATE_FORMAT, Locale.ENGLISH);

        bSave.setOnClickListener(this);
        tvBirthDate.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bSave:
                saveForm();
                break;

            case R.id.tvBirthDate:
                Calendar newCalendar = Calendar.getInstance();
                new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        tvBirthDate.setText(sdf.format(newDate.getTime()));
                    }

                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
        }
    }

    private void saveForm() {
        bSave.setEnabled(false);
        if (validate()) {
            String id = tvCustomerId.getText().toString().trim();
            String fname = tvFirstName.getText().toString().trim();
            String lname = tvLastName.getText().toString().trim();
            String birthplace = tvBirthPlace.getText().toString().trim();
            String birthdate = tvBirthDate.getText().toString().trim();
            long phone = Long.parseLong(tvPhone.getText().toString().trim());
            String mother = tvMother.getText().toString().trim();
            String address = tvAddress.getText().toString().trim();
            String type = /*spType.getSelectedItem().toString().trim()*/"MAIN";
            String location = tvLocation.getText().toString().trim();
            String occupation = tvOccupation.getText().toString().trim();
            String organization = tvOrganization.getText().toString().trim();
            int selectedId = rgGender.getCheckedRadioButtonId();
            RadioButton radioSexButton = (RadioButton) findViewById(selectedId);
            String gender = radioSexButton.getText().toString();

            Customer customer = new Customer(id, fname, lname, birthplace, birthdate, phone, mother, address, type, location, occupation,
                    organization, gender, false + "");
            try {
                createCustomer(customer);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            bSave.setEnabled(true);
        }
    }

    /**
     * validate form
     *
     * @return true if form is valid
     */
    private boolean validate() {
        String id = tvCustomerId.getText().toString().trim();
        String fname = tvFirstName.getText().toString().trim();
        String lname = tvLastName.getText().toString().trim();
        String birthplace = tvBirthPlace.getText().toString().trim();
        String birthdate = tvBirthDate.getText().toString().trim();
        String phone = tvPhone.getText().toString().trim();
        String mother = tvMother.getText().toString().trim();
        String address = tvAddress.getText().toString().trim();
        String location = tvLocation.getText().toString().trim();
        String occupation = tvOccupation.getText().toString().trim();
        String organization = tvOrganization.getText().toString().trim();
        int selectedId = rgGender.getCheckedRadioButtonId();
        RadioButton radioSexButton = (RadioButton) findViewById(selectedId);
        String gender = radioSexButton.getText().toString();

        if (id.isEmpty()) {
            tvCustomerId.setError(getString(R.string.this_field_is_empty));
            return false;
        }

        if (fname.isEmpty()) {
            tvFirstName.setError(getString(R.string.this_field_is_empty));
            return false;
        }

        if (lname.isEmpty()) {
            tvLastName.setError(getString(R.string.this_field_is_empty));
            return false;
        }

        if (birthplace.isEmpty()) {
            tvBirthPlace.setError(getString(R.string.this_field_is_empty));
            return false;
        }

        if (birthdate.isEmpty()) {
            tvBirthDate.setError(getString(R.string.this_field_is_empty));
            return false;
        }

        if (phone.isEmpty()) {
            tvPhone.setError(getString(R.string.this_field_is_empty));
            return false;
        }

        if (mother.isEmpty()) {
            tvMother.setError(getString(R.string.this_field_is_empty));
            return false;
        }

        if (address.isEmpty()) {
            tvAddress.setError(getString(R.string.this_field_is_empty));
            return false;
        }

        if (location.isEmpty()) {
            tvLocation.setError(getString(R.string.this_field_is_empty));
            return false;
        }

        if (occupation.isEmpty()) {
            tvOccupation.setError(getString(R.string.this_field_is_empty));
            return false;
        }

        if (organization.isEmpty()) {
            tvOrganization.setError(getString(R.string.this_field_is_empty));
            return false;
        }

        if (gender.isEmpty()) {
            Utils.toast(context, getString(R.string.select_gender));
            return false;
        }

        return true;
    }

    /**
     * create customer server call
     *
     * @param customer : customer details to be created
     */
    private void createCustomer(final Customer customer) throws IOException {
        String userId = "0";
        int branchId = 1;
        String token = 1234567890 + "";

        if (Utils.isNetworkAvailable(context)) {
            call = RestClient.getTestApi().createCustomer(token, userId, branchId, customer);

            //synchronous
            /*MyResponse myResponse = call.execute().body();
            Utils.loge(TAG, "myResponse : " + myResponse.getResult());*/

            // asynchronous
            call.enqueue(new Callback<MyResponse>() {
                @Override
                public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                    // response.isSuccessful() is true if the response code is 2xx
                    if (response.isSuccessful()) {
                        MyResponse myResponse = response.body();
                        Utils.loge(TAG, "myResponse : " + myResponse.getResult());
                        Utils.toast(context, getString(R.string.customer_created));

                    } else {
                        int statusCode = response.code();
                        // handle request errors yourself
                        ResponseBody errorBody = response.errorBody();
                        try {
                            Utils.loge(TAG, "errorBody : " + errorBody.bytes().toString() + ", statusCode : " + statusCode);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Utils.toast(context, getString(R.string.failed));
                    }
                    bSave.setEnabled(true);
                }

                @Override
                public void onFailure(Call<MyResponse> call, Throwable t) {
                    Utils.toast(context, getString(R.string.failed));
                    bSave.setEnabled(true);
                }
            });

        } else {
            Utils.toast(context, getString(R.string.check_internet));
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (call != null) {
            call.cancel();
        }
    }
}
