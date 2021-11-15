package com.tajway.tajwaycabs.Activities;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.tajway.tajwaycabs.R;
import com.tajway.tajwaycabs.apiJsonResponse.ProfileJsonResponse;
import com.tajway.tajwaycabs.apiJsonResponse.UpdateBankDetailsJsonResponse;
import com.tajway.tajwaycabs.apiJsonResponse.UpdatePancardDetailsJsonResponse;
import com.tajway.tajwaycabs.apiJsonResponse.UpdateProfileJonsResponse;
import com.tajway.tajwaycabs.commonutils.CommonUtils;
import com.tajway.tajwaycabs.requestdata.ProfileRequest;
import com.tajway.tajwaycabs.requestdata.UpdateBanlDetailsRequest;
import com.tajway.tajwaycabs.requestdata.UpdatePancardRequest;
import com.tajway.tajwaycabs.requestdata.UpdateProfileRequest;
import com.tajway.tajwaycabs.retrofitwebservices.ApiExecutor;
import com.tajway.tajwaycabs.session.SessonManager;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyProfileActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText et_mobile_bank, et_holder_name_bank, et_account_number_bank, et_bank_name_bank, et_ifsc_code_bank;
    //Pancard Deatils
    EditText et_name_pancard, et_pan_number_pan, et_date_of_birth_pan;
    //Personal deatils;
    EditText et_name_personal, et_company_name_personal, et_email_personal, et_mobile_personal, et_alternative_mobile_personal;

    CheckBox checkBox_personal;
    Spinner spinnerCity, spinnerState;

    Button btn_uploadBankDetail, btn_uploadPANDetail, btn_add_address;
    SessonManager sessonManager;
    String one = "2";

    String []city= {"Delhi","Noida","Greater Noida"};
    String[] state = {"Andhra Pradesh","Delhi","Maharashtra","Punjab","Harayana","Uttar Pradesh","Uttrakhand" };
    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_my_profile, container, false);*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_my_profile);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        sessonManager = new SessonManager(MyProfileActivity.this);
        et_mobile_bank = findViewById(R.id.et_mobile_bank);
        et_holder_name_bank = findViewById(R.id.et_holder_name_bank);
        et_account_number_bank = findViewById(R.id.et_account_number_bank);
        et_bank_name_bank = findViewById(R.id.et_bank_name_bank);
        et_ifsc_code_bank = findViewById(R.id.et_ifsc_code_bank);

        et_name_pancard = findViewById(R.id.et_name_pancard);
        et_pan_number_pan = findViewById(R.id.et_pan_number_pan);
        et_date_of_birth_pan = findViewById(R.id.et_date_of_birth_pan);

        et_name_personal = findViewById(R.id.et_name_personal);
        et_company_name_personal = findViewById(R.id.et_company_name_personal);
        et_email_personal = findViewById(R.id.et_email_personal);
        et_mobile_personal = findViewById(R.id.et_mobile_personal);
        et_alternative_mobile_personal = findViewById(R.id.et_alternative_mobile_personal);
        spinnerCity = findViewById(R.id.addresscity);
        spinnerState = findViewById(R.id.addressState);


        checkBox_personal = findViewById(R.id.checkBox_personal);


        btn_uploadBankDetail = findViewById(R.id.btn_uploadBankDetail);
        btn_uploadPANDetail = findViewById(R.id.btn_uploadPANDetail);
        btn_add_address = findViewById(R.id.btn_done_);


        //Set Spinner
        ArrayAdapter<String> adapterCity = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,city);
        adapterCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCity.setAdapter(adapterCity);
        spinnerCity.setOnItemSelectedListener(this);

        ArrayAdapter<String> adapterState = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,state);
        adapterState.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerState.setAdapter(adapterState);
        spinnerState.setOnItemSelectedListener(this);

        btn_uploadBankDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (setUploadBankDetails()) {
                    UpdateBankDetailsAPI();
                }
            }
        });

        btn_uploadPANDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (setPancard()) {
                    UpdatePancardDetailsAPI();
                }
            }
        });

        btn_add_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (setPersonalDetails()) {
                    UpdateProfileAPI();
                }
            }
        });


        checkBox_personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                one = "1";
            }
        });



        setGameDetailsAPI();

        // ProfileAPI();
        //  setGameDetailsAPI();


    }


    private boolean setMobileBank() {
        if (et_mobile_bank.getText().toString().length() > 9) {
            return true;
        } else {
            et_mobile_bank.setError("Please enter 10 digit mobile no");
            et_mobile_bank.requestFocus();
            return false;
        }
    }


    private boolean setHolderName() {
        if (et_holder_name_bank.getText().toString().length() > 0) {
            return true;

        } else {
            et_holder_name_bank.setError("Please input account holder name");
            et_holder_name_bank.requestFocus();
            return false;
        }
    }


    private boolean setAccountNumber() {

        if (et_account_number_bank.getText().toString().length() > 11) {
            return true;
        } else {
            et_account_number_bank.setError("Please enter less than 12 more than 16 digit mobile no");
            et_account_number_bank.requestFocus();
            return false;
        }
    }


    private boolean setBankName() {
        if (et_bank_name_bank.getText().toString().length() > 0) {
            return true;
        } else {
            et_bank_name_bank.setError("Please input bank name");
            et_bank_name_bank.requestFocus();
            return false;
        }
    }

    private boolean setIFSC() {
        if (et_ifsc_code_bank.getText().toString().length() > 5) {
            return true;
        } else {
            et_ifsc_code_bank.setError("Please input IFSC code");
            et_ifsc_code_bank.requestFocus();
            return false;
        }
    }


    private boolean setUploadBankDetails() {
        if (!setMobileBank()) {
            return false;
        } else if (!setHolderName()) {
            return false;
        } else if (!setAccountNumber()) {
            return false;
        } else if (!setBankName()) {
            return false;
        } else return setIFSC();
    }


    private boolean setNamePan() {
        if (et_name_pancard.getText().toString().length() > 0) {
            return true;

        } else {
            et_name_pancard.setError("Please input  name");
            et_name_pancard.requestFocus();
            return false;
        }
    }


    private boolean setPanNumberPan() {

        if (et_pan_number_pan.getText().toString().length() > 8) {
            return true;
        } else {
            et_pan_number_pan.setError("Please enter less than 12 more than 16 digit mobile no");
            et_pan_number_pan.requestFocus();
            return false;
        }
    }


    private boolean setDateOfBirthPan() {

        if (et_date_of_birth_pan.getText().toString().length() > 8) {
            return true;
        } else {
            et_date_of_birth_pan.setError("Please enter date of birth");
            et_date_of_birth_pan.requestFocus();
            return false;
        }
    }


    private boolean setPancard() {
        if (!setNamePan()) {
            return false;
        } else if (!setPanNumberPan()) {
            return false;
        } else return setDateOfBirthPan();
    }


    private boolean setMobilePersonal() {
        if (et_mobile_personal.getText().toString().length() > 9) {
            return true;
        } else {
            et_mobile_personal.setError("Please enter 10 digit mobile no");
            et_mobile_personal.requestFocus();
            return false;
        }
    }


    private boolean setCheck() {

        if (one.equalsIgnoreCase("1")) {
            return true;
        } else {
            Toast.makeText(MyProfileActivity.this, "Please select term of services", Toast.LENGTH_SHORT).show();
            return false;
        }


    }



    private boolean setPersonalDetails() {
        if (!setMobilePersonal()) {
            return false;
        }else return setCheck();
    }












/*
    private void ProfileAPI() {
        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "", "Loading....", false);
        StringRequest request = new StringRequest(Request.Method.GET, Constant.profile, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("ProfileRe" + "", response);
                dialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status=jsonObject.getString("status");
                    JSONObject dataObject=jsonObject.getJSONObject("data");

                    JSONObject bankdetailsObject=dataObject.getJSONObject("bankdetails");
                    String account_holder_name=bankdetailsObject.getString("account_holder_name");
                    String account_mobile=bankdetailsObject.getString("account_mobile");
                    String account_no=bankdetailsObject.getString("account_no");
                    String bank_name=bankdetailsObject.getString("bank_name");
                    String bank_status=bankdetailsObject.getString("bank_status");
                    String ifsc_code=bankdetailsObject.getString("ifsc_code");


                    if(bank_status.equalsIgnoreCase("1")){

                        et_mobile_bank.setText(account_mobile);
                        et_holder_name_bank.setText(account_holder_name);
                        et_account_number_bank.setText(account_no);
                        et_bank_name_bank.setText(bank_name);
                        et_ifsc_code_bank.setText(ifsc_code);

                        et_mobile_bank.setEnabled(false);
                        et_holder_name_bank.setEnabled(false);
                        et_account_number_bank.setEnabled(false);
                        et_bank_name_bank.setEnabled(false);
                        et_ifsc_code_bank.setEnabled(false);
                        btn_uploadBankDetail.setBackgroundColor(Color.parseColor("#03DAC5"));

                    }else {
                        et_mobile_bank.setEnabled(true);
                        et_holder_name_bank.setEnabled(true);
                        et_account_number_bank.setEnabled(true);
                        et_bank_name_bank.setEnabled(true);
                        et_ifsc_code_bank.setEnabled(true);
                        btn_uploadBankDetail.setBackgroundColor(Color.parseColor("#B1ABAB"));
                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("Authorization", "Bearer " + sessonManager.getToken());
                Log.d("Home", sessonManager.getToken());
                return params;
            }


        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.getCache().clear();
        requestQueue.add(request);

    }
*/


    private void setGameDetailsAPI() {
        if (CommonUtils.isOnline(MyProfileActivity.this)) {
            final ProgressDialog dialog = ProgressDialog.show(MyProfileActivity.this, null, getString(R.string.loading));
            ProfileRequest request = new ProfileRequest();

            Call<ProfileJsonResponse> call = ApiExecutor.getApiService(MyProfileActivity.this).apiProfile("Bearer " + sessonManager.getToken());
            System.out.println("API url ---" + call.request().url());
            System.out.println("API request  ---" + new Gson().toJson(request));
            call.enqueue(new Callback<ProfileJsonResponse>() {
                             @Override
                             public void onResponse(@NotNull Call<ProfileJsonResponse> call, @NotNull Response<ProfileJsonResponse> response) {
                                 System.out.println("ViewVisitorType " + "API Data" + new Gson().toJson(response.body()));
                                 String respon = new Gson().toJson(response.body());
                                 Log.d("CookingFood", respon);

                                 if (dialog != null && dialog.isShowing()) {
                                     dialog.dismiss();
                                 }
                                 if (response.body() != null) {
                                     if (response.body().status != null && response.body().status.equals("success")) {
                                         if (response.body().data != null) {

                                             String account_mobile = response.body().data.bankdetails.account_mobile;
                                             String account_holder_name = response.body().data.bankdetails.account_holder_name;
                                             String bank_status = response.body().data.bankdetails.bank_status;
                                             String account_no = response.body().data.bankdetails.account_no;
                                             String bank_name = response.body().data.bankdetails.bank_name;
                                             String ifsc_code = response.body().data.bankdetails.ifsc_code;

                                             if (bank_status.equalsIgnoreCase("1")) {
                                                 et_mobile_bank.setText(account_mobile);
                                                 et_holder_name_bank.setText(account_holder_name);
                                                 et_account_number_bank.setText(account_no);
                                                 et_bank_name_bank.setText(bank_name);
                                                 et_ifsc_code_bank.setText(ifsc_code);
                                                 et_mobile_bank.setEnabled(false);
                                                 et_holder_name_bank.setEnabled(false);
                                                 et_account_number_bank.setEnabled(false);
                                                 et_bank_name_bank.setEnabled(false);
                                                 et_ifsc_code_bank.setEnabled(false);
                                                 btn_uploadBankDetail.setBackgroundColor(Color.parseColor("#03DAC5"));
                                             } else {
                                                 et_mobile_bank.setEnabled(true);
                                                 et_holder_name_bank.setEnabled(true);
                                                 et_account_number_bank.setEnabled(true);
                                                 et_bank_name_bank.setEnabled(true);
                                                 et_ifsc_code_bank.setEnabled(true);
                                                 btn_uploadBankDetail.setBackgroundColor(Color.parseColor("#B1ABAB"));
                                             }


                                             String pan_holder_name = response.body().data.pancarddetails.pan_holder_name;
                                             String pan_number = response.body().data.pancarddetails.pan_number;
                                             String dob = response.body().data.pancarddetails.dob;
                                             String pan_status = response.body().data.pancarddetails.pan_status;


                                             if (pan_status.equalsIgnoreCase("1")) {
                                                 et_name_pancard.setText(pan_holder_name);
                                                 et_pan_number_pan.setText(pan_number);
                                                 et_date_of_birth_pan.setText(dob);
                                                 et_name_pancard.setEnabled(false);
                                                 et_pan_number_pan.setEnabled(false);
                                                 et_date_of_birth_pan.setEnabled(false);
                                                 btn_uploadPANDetail.setBackgroundColor(Color.parseColor("#03DAC5"));

                                             } else {
                                                 et_name_pancard.setEnabled(true);
                                                 et_pan_number_pan.setEnabled(true);
                                                 et_date_of_birth_pan.setEnabled(true);
                                                 btn_uploadPANDetail.setBackgroundColor(Color.parseColor("#B1ABAB"));

                                             }


                                             String name = response.body().data.profile.name;
                                             String email = response.body().data.profile.email;
                                             String mobile = response.body().data.profile.mobile;
                                             String company_name = response.body().data.profile.company_name;
                                            // String address = response.body().data.profile.address;
                                             String city = response.body().data.profile.city;
                                             //String state = response.body().data.profile.state;
                                             String alternate_no = response.body().data.profile.alternate_no;

                                             et_name_personal.setText(name);
                                             et_company_name_personal.setText(company_name);
                                             et_email_personal.setText(email);
                                             et_mobile_personal.setText(mobile);
                                             et_mobile_personal.setEnabled(false);
                                             et_alternative_mobile_personal.setText(alternate_no);



                                         }
                                     }


                                 } else {


                                     if (dialog != null && dialog.isShowing()) {
                                         dialog.dismiss();


                                     }
                                     //CommonUtils.setSnackbar(tvClassName, getString(R.string.server_not_responding));
                                 }
                             }

                             @Override
                             public void onFailure(@NotNull Call<ProfileJsonResponse> call, @NotNull Throwable t) {
                                 if (dialog != null && dialog.isShowing()) {
                                     dialog.dismiss();

                                     Toast.makeText(MyProfileActivity.this, "Good", Toast.LENGTH_SHORT).show();
                                 }
                                 System.out.println("API Data Error : " + t.getMessage());
                             }
                         }
            );
        } else {
            CommonUtils.showToastInCenter(MyProfileActivity.this, getString(R.string.please_check_network));
        }
    }


    private void UpdateBankDetailsAPI() {
        if (CommonUtils.isOnline(MyProfileActivity.this)) {
            final ProgressDialog dialog = ProgressDialog.show(MyProfileActivity.this, null, getString(R.string.loading));
            UpdateBanlDetailsRequest request = new UpdateBanlDetailsRequest();

            request.setBank_name(et_bank_name_bank.getText().toString());
            request.setAccount_no(et_account_number_bank.getText().toString());
            request.setAccount_mobile(et_mobile_bank.getText().toString());
            request.setAccount_holder_name(et_holder_name_bank.getText().toString());
            request.setIfsc_code(et_ifsc_code_bank.getText().toString());
            Call<UpdateBankDetailsJsonResponse> call = ApiExecutor.getApiService(MyProfileActivity.this).apiUpdateBankDetail("Bearer " + sessonManager.getToken(), request);
            Log.d("url","API url ---" + call.request().url());
            System.out.println("API url ---" + call.request().url());
            System.out.println("API request  ---" + new Gson().toJson(request));
            call.enqueue(new Callback<UpdateBankDetailsJsonResponse>() {
                             @Override
                             public void onResponse(@NotNull Call<UpdateBankDetailsJsonResponse> call, @NotNull Response<UpdateBankDetailsJsonResponse> response) {
                                 String respon = new Gson().toJson(response.body());
                                 dialog.dismiss();
                                 Log.d("responLogin", respon);
                                 System.out.println("MobileVerifyActivity" + "API Data" + new Gson().toJson(response.body()));

                                 if (response.body() != null) {
                                     if (response.body().status != null && response.body().status.equals("success")) {
                                         String message = response.body().message;
                                         Toast.makeText(MyProfileActivity.this, message, Toast.LENGTH_SHORT).show();

                                     } else {
                                         CommonUtils.showToast(MyProfileActivity.this, response.body().message);

                                     }
                                 } else {
                                     if (dialog.isShowing()) {
                                         dialog.dismiss();
                                     }
                                     // CommonUtils.setSnackbar(tvLogin, getString(R.string.server_not_responding));
                                 }


                             }

                             @Override
                             public void onFailure(@NotNull Call<UpdateBankDetailsJsonResponse> call, @NotNull Throwable t) {
                                 if (dialog != null && dialog.isShowing()) {
                                     dialog.dismiss();
                                 }
                                 Log.d("errormeasge", "onFailure: "+t.getMessage());
                                 System.out.println("API Data Error : " + t.getMessage());
                             }
                         }
            );
        } else {
            CommonUtils.showToastInCenter(MyProfileActivity.this, getString(R.string.please_check_network));
        }
    }


    private void UpdatePancardDetailsAPI() {
        if (CommonUtils.isOnline(MyProfileActivity.this)) {
            final ProgressDialog dialog = ProgressDialog.show(MyProfileActivity.this, null, getString(R.string.loading));
            UpdatePancardRequest request = new UpdatePancardRequest();
            request.setPan_holder_name(et_name_pancard.getText().toString());
            request.setPan_number(et_pan_number_pan.getText().toString());
            request.setDob(et_date_of_birth_pan.getText().toString());
        Log.d("chkparms",request+"");
        Log.d("check token","value---"+"Bearer " + sessonManager.getToken());
            Call<UpdatePancardDetailsJsonResponse> call = ApiExecutor.getApiService(MyProfileActivity.this).apiUpdatePanCardDetail("Bearer " + sessonManager.getToken(), request);
            System.out.println("API url ---" + call.request().url());
            System.out.println("API request  ---" + new Gson().toJson(request));
            call.enqueue(new Callback<UpdatePancardDetailsJsonResponse>() {
                             @Override
                             public void onResponse(@NotNull Call<UpdatePancardDetailsJsonResponse> call, @NotNull Response<UpdatePancardDetailsJsonResponse> response) {
                                 String respon = new Gson().toJson(response.body());
                                 dialog.dismiss();
                                 Log.d("responLogin", respon);
                                 System.out.println("MobileVerifyActivity" + "API Data" + new Gson().toJson(response.body()));

                                 if (response.body() != null) {
                                     if (response.body().status != null && response.body().status.equals("success")) {
                                         String message = response.body().message;
                                         Toast.makeText(MyProfileActivity.this, message, Toast.LENGTH_SHORT).show();

                                     } else {
                                         CommonUtils.showToast(MyProfileActivity.this, response.body().message);

                                     }
                                 } else {
                                     if (dialog.isShowing()) {
                                         dialog.dismiss();
                                     }
                                     // CommonUtils.setSnackbar(tvLogin, getString(R.string.server_not_responding));
                                 }


                             }

                             @Override
                             public void onFailure(@NotNull Call<UpdatePancardDetailsJsonResponse> call, @NotNull Throwable t) {
                                 if (dialog != null && dialog.isShowing()) {
                                     dialog.dismiss();
                                 }
                                 System.out.println("API Data Error : " + t.getMessage());
                             }
                         }
            );
        } else {
            CommonUtils.showToastInCenter(MyProfileActivity.this, getString(R.string.please_check_network));
        }
    }




    private void UpdateProfileAPI() {
        if (CommonUtils.isOnline(MyProfileActivity.this)) {
            final ProgressDialog dialog = ProgressDialog.show(MyProfileActivity.this, null, getString(R.string.loading));
            UpdateProfileRequest request = new UpdateProfileRequest();
            request.setName(et_name_personal.getText().toString());
            request.setCompany_name(et_company_name_personal.getText().toString());
            request.setEmail(et_email_personal.getText().toString());
            request.setAlternate_no(et_alternative_mobile_personal.getText().toString());


            Call<UpdateProfileJonsResponse> call = ApiExecutor.getApiService(MyProfileActivity.this).apiUpdateProfile("Bearer " + sessonManager.getToken(), request);
            System.out.println("API url ---" + call.request().url());
            System.out.println("API request  ---" + new Gson().toJson(request));
            call.enqueue(new Callback<UpdateProfileJonsResponse>() {
                             @Override
                             public void onResponse(@NotNull Call<UpdateProfileJonsResponse> call, @NotNull Response<UpdateProfileJonsResponse> response) {
                                 String respon = new Gson().toJson(response.body());
                                 dialog.dismiss();
                                 Log.d("responLogin", respon);
                                 System.out.println("MobileVerifyActivity" + "API Data" + new Gson().toJson(response.body()));

                                 if (response.body() != null) {
                                     if (response.body().status != null && response.body().status.equals("success")) {
                                         String message = response.body().message;
                                         Toast.makeText(MyProfileActivity.this, message, Toast.LENGTH_SHORT).show();

                                     } else {
                                         CommonUtils.showToast(MyProfileActivity.this, response.body().message);

                                     }
                                 } else {
                                     if (dialog.isShowing()) {
                                         dialog.dismiss();
                                     }
                                     // CommonUtils.setSnackbar(tvLogin, getString(R.string.server_not_responding));
                                 }


                             }

                             @Override
                             public void onFailure(@NotNull Call<UpdateProfileJonsResponse> call, @NotNull Throwable t) {
                                 if (dialog != null && dialog.isShowing()) {
                                     dialog.dismiss();
                                 }
                                 System.out.println("API Data Error : " + t.getMessage());
                             }
                         }
            );
        } else {
            CommonUtils.showToastInCenter(MyProfileActivity.this, getString(R.string.please_check_network));
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}