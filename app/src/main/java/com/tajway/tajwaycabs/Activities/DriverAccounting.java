package com.tajway.tajwaycabs.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tajway.tajwaycabs.R;
import com.tajway.tajwaycabs.adapter.BookingIDAdapter;
import com.tajway.tajwaycabs.adapter.DriveAccountAdapter;
import com.tajway.tajwaycabs.apiJsonResponse.DriverCreateJsonResponse;
import com.tajway.tajwaycabs.commonutils.CommonUtils;
import com.tajway.tajwaycabs.helper.DBManagerQuery;
import com.tajway.tajwaycabs.helper.DatabaseHelper;
import com.tajway.tajwaycabs.helper.DriverApplication;
import com.tajway.tajwaycabs.helper.callback.GetDriverAccountDataCallback;
import com.tajway.tajwaycabs.helper.callback.InsertDriverAccountDataCallback;
import com.tajway.tajwaycabs.helper.db.GetDriverAccountDataBase;
import com.tajway.tajwaycabs.helper.db.InsertDriverAccountDataInDatabase;
import com.tajway.tajwaycabs.requestdata.DriverCreateRequest;
import com.tajway.tajwaycabs.requestdata.DriverRequest;
import com.tajway.tajwaycabs.responsedata.DriverAccount;
import com.tajway.tajwaycabs.retrofitModel.DriverAccountingModel;
import com.tajway.tajwaycabs.retrofitModel.InfoStatusModel;
import com.tajway.tajwaycabs.retrofitwebservices.ApiExecutor;
import com.tajway.tajwaycabs.session.SessonManager;
import com.tajway.tajwaycabs.session.SharedPrefManager;
import com.tajway.tajwaycabs.util.ApiFactory;
import com.tajway.tajwaycabs.util.Helper;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

public class DriverAccounting extends AppCompatActivity implements AdapterView.OnItemSelectedListener,
        View.OnClickListener, InsertDriverAccountDataCallback, GetDriverAccountDataCallback {
    EditText etAmount, etRemarks, etFinalRemarks;
    Button tvSubmit;
    TextView tvFinalAmount, tvViewAll;
    SessonManager sessonManager;
    RecyclerView recyclerviewNavOct;
    List<String> cards = new ArrayList<>();
    List<String> categories = new ArrayList<String>();
    Spinner spinnerCard, spinner, spinnerBookingId;
    List<DriverAccountingModel> accountingModels = new ArrayList<>();
    String cardType, particular;
    int totalAmount = 0;
    private Button tvUpload;
    List<DriverAccount> listBookingId = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_accounting);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Driver Accounting");
        sessonManager = new SessonManager(this);
        spinner = findViewById(R.id.spinner);
        spinnerCard = findViewById(R.id.spinnerCard);
        spinnerBookingId = findViewById(R.id.spinnerBookingId);
        etAmount = findViewById(R.id.etAmount);
        etRemarks = findViewById(R.id.etRemarks);
        tvFinalAmount = findViewById(R.id.tvFinalAmount);
        tvSubmit = findViewById(R.id.tvSubmit);
        tvUpload = findViewById(R.id.tvUpload);
        tvViewAll = findViewById(R.id.tvViewAll);
        etFinalRemarks = findViewById(R.id.etFinalRemarks);
        recyclerviewNavOct = findViewById(R.id.recyclerviewNavOct);
        SharedPrefManager.getInstance(this).set(SharedPrefManager.Key.TOTAL_AMOUNT, ApiFactory.TOTAL_AMOUNT);
        //tvFinalAmount.setText(""+SharedPrefManager.getInstance(this).get(SharedPrefManager.Key.TOTAL_AMOUNT, 0));
        hitCompletedBookingId();
        setCardType();
        setParticular();
        DriverApplication.dbManagerQuery.removeAllLocalDriverAccount();
        accountingModels.clear();
        tvSubmit.setOnClickListener(this);
        new GetDriverAccountDataBase(this).execute();
        // Cursor csr = dbManagerQuery.getDataFetch();
        Log.e("onClick", "value-toa-" + SharedPrefManager.getInstance(this).get(SharedPrefManager.Key.TOTAL_AMOUNT, 0));
        tvUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String finalRemarks = etFinalRemarks.getText().toString();
                Log.e("finalAccount", "bookingId1-" + bookingId1);
                Log.e("finalAccount", "driver_id-" + driver_id);
                Log.e("finalAccount", "user_id-" + user_id);
                Log.e("finalAccount", "source-" + source);
                Log.e("finalAccount", "destination-" + destination);
                Log.e("finalAccount", "finalRemarks-" + finalRemarks);
                if (finalRemarks.isEmpty()) {
                    Toast.makeText(DriverAccounting.this, "Enter Remarks", Toast.LENGTH_SHORT).show();
                } else if (bookingId1.isEmpty()) {
                    Toast.makeText(DriverAccounting.this, "Select Booking ID", Toast.LENGTH_SHORT).show();
                } else if (driver_id.isEmpty()) {
                    Toast.makeText(DriverAccounting.this, "Select Driver ID", Toast.LENGTH_SHORT).show();
                } else if (user_id.isEmpty()) {
                    Toast.makeText(DriverAccounting.this, "Select User ID", Toast.LENGTH_SHORT).show();
                } else if (source.isEmpty()) {
                    Toast.makeText(DriverAccounting.this, "Source", Toast.LENGTH_SHORT).show();
                } else if (destination.isEmpty()) {
                    Toast.makeText(DriverAccounting.this, "Destination", Toast.LENGTH_SHORT).show();
                } else {
                    setDriverAccountingAPI(bookingId1, driver_id, user_id, source, destination, finalRemarks);
                }

            }
        });
        tvViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DriverAccounting.this, DriverAccountingViewAll.class));
            }
        });

    }

    String bookingId1, driver_id, user_id, source, destination;

    private void setCardType() {
        cards.clear();
        cards.add("Select Type");
        cards.add("Credit");
        cards.add("Debit");
        cards.add("None");
        // Creating adapter for spinner
        ArrayAdapter<String> cardAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cards);
        // Drop down layout style - list view with radio button
        cardAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinnerCard.setAdapter(cardAdapter);
        spinnerCard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // do something upon option selection
                cardType = parent.getItemAtPosition(position).toString();

                if (!cardType.equalsIgnoreCase("Select Type")) {
                    Toast.makeText(parent.getContext(), "card: " + cardType, Toast.LENGTH_LONG).show();
                }

                // Showing selected spinner item

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // can leave this empty
            }
        });
    }

    private void setParticular() {
        categories.clear();
        categories.add("Select Particular");
        categories.add("Amount Per Km Wala");
        categories.add("Package");
        categories.add("Extra Km Charge");
        categories.add("Extra Hour Charge");
        categories.add("MCD Tag");
        categories.add("MCD Cash");

        categories.add("Toll Tag");
        categories.add("Toll Cash");
        categories.add("State Tax Office");
        categories.add("MState Tax Cash");
        categories.add("DA Office");
        categories.add("DA Client");

        categories.add("Parking Office");
        categories.add("Diesel Office");
        categories.add("Diesel Client");
        categories.add("Maintanence Office");
        categories.add("Maintanence Driver");

        categories.add("Advance Office 1");
        categories.add("Advance Office 2");
        categories.add("Other 1");
        categories.add("Other 2");
        categories.add("Other 3");
        categories.add("Other 4");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

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
        // On selecting a spinner item
        particular = parent.getItemAtPosition(position).toString();
        if (!particular.equalsIgnoreCase("Select Particular")) {
            Toast.makeText(parent.getContext(), "Selected: " + particular, Toast.LENGTH_LONG).show();
        }
        // Showing selected spinner item

    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvSubmit:

                String remarks = etRemarks.getText().toString();
                String amount = etAmount.getText().toString();
                if (particular.isEmpty()) {
                    Toast.makeText(this, "Please Select Particular", Toast.LENGTH_SHORT).show();
                } else if (cardType.isEmpty()) {
                    Toast.makeText(this, "Please Select Card Type", Toast.LENGTH_SHORT).show();
                } else if (amount.isEmpty()) {
                    Toast.makeText(this, "Please Enter amount", Toast.LENGTH_SHORT).show();
                } else if (remarks.isEmpty()) {
                    Toast.makeText(this, "Please Enter Remark", Toast.LENGTH_SHORT).show();
                } else {
                    String mobile = sessonManager.getString("userNumber", "");
                    int amt = Integer.parseInt(amount);
                    if (cardType.equalsIgnoreCase("Credit")) {
                        ApiFactory.TOTAL_AMOUNT = SharedPrefManager.getInstance(this).get(SharedPrefManager.Key.TOTAL_AMOUNT, 0) + amt;
                        Log.e("onClick", "value-mobile-" + mobile);
                        Log.e("onClick", "value-particular-" + particular);
                        Log.e("onClick", "value-cardType-" + cardType);
                        Log.e("onClick", "value-amount-" + amount);
                        Log.e("onClick", "value-totalAmount-credit-" + ApiFactory.TOTAL_AMOUNT);
                        Log.e("onClick", "value-remarks-" + remarks);
                        SharedPrefManager.getInstance(this).set(SharedPrefManager.Key.TOTAL_AMOUNT, ApiFactory.TOTAL_AMOUNT);
                        Log.e("onClick", "value-totalAmount credit-" + SharedPrefManager.getInstance(this).get(SharedPrefManager.Key.TOTAL_AMOUNT, 0));
                        new InsertDriverAccountDataInDatabase(mobile, particular, cardType, amount, "0", "-", String.valueOf(ApiFactory.TOTAL_AMOUNT), remarks, this).execute();
                        tvFinalAmount.setText("" + ApiFactory.TOTAL_AMOUNT);
                    } else if (cardType.equalsIgnoreCase("Debit")) {
                        ApiFactory.TOTAL_AMOUNT = SharedPrefManager.getInstance(this).get(SharedPrefManager.Key.TOTAL_AMOUNT, 0) - amt;
                        Log.e("onClick", "value-mobile-" + mobile);
                        Log.e("onClick", "value-particular-" + particular);
                        Log.e("onClick", "value-cardType-" + cardType);
                        Log.e("onClick", "value-amount-" + amount);
                        Log.e("onClick", "value-totalAmount debit-" + ApiFactory.TOTAL_AMOUNT);
                        Log.e("onClick", "value-remarks-" + remarks);
                        SharedPrefManager.getInstance(this).set(SharedPrefManager.Key.TOTAL_AMOUNT, ApiFactory.TOTAL_AMOUNT);
                        Log.e("onClick", "value-totalAmount debit-" + SharedPrefManager.getInstance(this).get(SharedPrefManager.Key.TOTAL_AMOUNT, 0));
                        new InsertDriverAccountDataInDatabase(mobile, particular, cardType, "0", amount, "-", String.valueOf(ApiFactory.TOTAL_AMOUNT), remarks, this).execute();
                        tvFinalAmount.setText("" + ApiFactory.TOTAL_AMOUNT);
                    } else if (cardType.equalsIgnoreCase("None")) {
                        Log.e("onClick", "value-mobile-" + mobile);
                        Log.e("onClick", "value-particular-" + particular);
                        Log.e("onClick", "value-cardType-" + cardType);
                        Log.e("onClick", "value-amount-" + amount);
                        Log.e("onClick", "value-totalAmount-none-" + ApiFactory.TOTAL_AMOUNT);
                        Log.e("onClick", "value-remarks-" + remarks);
                        SharedPrefManager.getInstance(this).set(SharedPrefManager.Key.TOTAL_AMOUNT, ApiFactory.TOTAL_AMOUNT);
                        Log.e("onClick", "value-totalAmount none-" + SharedPrefManager.getInstance(this).get(SharedPrefManager.Key.TOTAL_AMOUNT, 0));
                        new InsertDriverAccountDataInDatabase(mobile, particular, cardType, "0", amount, "-", String.valueOf(ApiFactory.TOTAL_AMOUNT), remarks, this).execute();
                        tvFinalAmount.setText("" + ApiFactory.TOTAL_AMOUNT);
                    }
                }
                break;
        }
    }

    @Override
    public void isDriverAccountDataInserted(Boolean status) {
        if (status) {
            Toast.makeText(DriverAccounting.this, "Successfully Send", Toast.LENGTH_SHORT).show();
            etAmount.setText("");
            etRemarks.setText("");
            setCardType();
            setParticular();
            new GetDriverAccountDataBase(this).execute();
        } else {
            Toast.makeText(this, "Something went wrong, try again", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDriverAccountDataFetch(Cursor csr) {
        Log.e("onDriver", "csr--" + csr.getCount());
        accountingModels.clear();
        if (csr != null) {
            if (csr.moveToFirst()) {
                do {
                    String particular = csr.getString(csr.getColumnIndex(DatabaseHelper.PARTICULAR));
                    String cardType = csr.getString(csr.getColumnIndex(DatabaseHelper.CARD_TYPE));
                    String credit = csr.getString(csr.getColumnIndex(DatabaseHelper.CREDIT));
                    String debit = csr.getString(csr.getColumnIndex(DatabaseHelper.DEBIT));
                    String none = csr.getString(csr.getColumnIndex(DatabaseHelper.NONE));
                    String totalAmount = csr.getString(csr.getColumnIndex(DatabaseHelper.TOTAL_AMOUNT));
                    String remark = csr.getString(csr.getColumnIndex(DatabaseHelper.REMARKS));
                    accountingModels.add(new DriverAccountingModel(particular, cardType, credit, debit, none, totalAmount, remark));
                } while (csr.moveToNext());
            }
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerviewNavOct.setLayoutManager(layoutManager);
            recyclerviewNavOct.setNestedScrollingEnabled(false);
            DriveAccountAdapter adapter = new DriveAccountAdapter(this, accountingModels);
            recyclerviewNavOct.setAdapter(adapter);
        }
    }

    private void hitCompletedBookingId() {
        if (CommonUtils.isOnline(this)) {
            listBookingId.clear();
            final ProgressDialog dialog = ProgressDialog.show(DriverAccounting.this, null, getString(R.string.loading));
            String mobile = sessonManager.getString("userNumber", "");
            Call<InfoStatusModel> call = ApiExecutor.getApiService(DriverAccounting.this).completedBookingId(mobile);

            call.enqueue(new Callback<InfoStatusModel>() {
                             @Override
                             public void onResponse(Call<InfoStatusModel> call, Response<InfoStatusModel> response) {
                                 // System.out.println("ViewVisitorType " + "API Data" + new Gson().toJson(response.body()));
                                 listBookingId.clear();
                                 String respon = new Gson().toJson(response.body());
                                 Log.d("OneWaResponse", respon);

                                 if (dialog != null && dialog.isShowing()) {
                                     dialog.dismiss();
                                 }
                                 if (response.body() != null) {
                                     if (response.body().getStatus() != null && response.body().getStatus().equals("success")) {
                                         if (response.body().getAccount_data() != null) {
                                             for (int i = 0; i < response.body().getAccount_data().size(); i++) {
                                                 listBookingId.add(new DriverAccount(i, response.body().getAccount_data().get(i).getBooking_id(), response.body().getAccount_data().get(i).getDriver_id(),
                                                         response.body().getAccount_data().get(i).getUser_id(), response.body().getAccount_data().get(i).getSource_book(), response.body().getAccount_data().get(i).getDestination_book()));
                                             }
                                             setBookingId(listBookingId);

                                             // listBookingId.addAll()response.body().getAccount_data();
                                         } else {
                                             listBookingId.clear();
                                             setBookingId(listBookingId);
                                         }
                                     } else {
                                         setBookingId(listBookingId);
                                     }
                                 } else {
                                     if (dialog != null && dialog.isShowing()) {
                                         dialog.dismiss();
                                     }
                                     Log.d("sdsasadsda", response.message());
                                     //CommonUtils.setSnackbar(tvClassName, getString(R.string.server_not_responding));
                                 }
                             }

                             @Override
                             public void onFailure(Call<InfoStatusModel> call, Throwable t) {
                                 if (dialog != null && dialog.isShowing()) {
                                     dialog.dismiss();
                                 }
                                 System.out.println("API Data Error : " + t.getMessage());
                             }
                         }
            );
        } else {
            CommonUtils.showToastInCenter(DriverAccounting.this, getString(R.string.please_check_network));
        }
    }

    private void setBookingId(final List<DriverAccount> listBookingId) {
        listBookingId.add(0, new DriverAccount(-1, "Select Booking ID", "", "", "", ""));
        BookingIDAdapter adapter = new BookingIDAdapter(this, listBookingId);
        spinnerBookingId.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        spinnerBookingId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // do something upon option selection
                String bookingId = listBookingId.get(position).getBookingL_id();
                if (!bookingId.equalsIgnoreCase("Select Booking ID")) {
                    bookingId1 = bookingId;
                    driver_id = listBookingId.get(position).getDriverL_id();
                    user_id = listBookingId.get(position).getDriverL_id();
                    source = listBookingId.get(position).getSource();
                    destination = listBookingId.get(position).getDestination();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // can leave this empty
            }
        });
    }

    // String currentDateTimeString;
    List<InfoStatusModel.Data> list = new ArrayList<>();

    private void setDriverAccountingAPI(String bookingId1, String driver_id, String user_id, String source, String destination, String finalRemarks) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = dateFormat.format(calendar.getTime());

        String amountType;
        if (String.valueOf(ApiFactory.TOTAL_AMOUNT).contains("-")) {
            amountType = "cr";
        } else {
            amountType = "dr";
        }
        Log.e("finalAccount", "amountType-" + amountType);
        Log.e("finalAccount", "currentDateTimeString-" + date);
        Log.e("finalAccount", "totalAmount-" + String.valueOf(ApiFactory.TOTAL_AMOUNT));
        if (CommonUtils.isOnline(DriverAccounting.this)) {
            final ProgressDialog dialog = ProgressDialog.show(DriverAccounting.this, null, getString(R.string.loading));
            Call<InfoStatusModel> call = ApiExecutor.getApiService(DriverAccounting.this).apiDriverAccounting(bookingId1, driver_id, destination, String.valueOf(ApiFactory.TOTAL_AMOUNT),
                    amountType, date, finalRemarks, user_id, source);

            System.out.println("API url ---" + call.request().url());
            call.enqueue(new Callback<InfoStatusModel>() {
                             @Override
                             public void onResponse(@NotNull Call<InfoStatusModel> call, @NotNull Response<InfoStatusModel> response) {
                                 String respon = new Gson().toJson(response.body());
                                 dialog.dismiss();
                                 Log.d("responseCreate", respon);
                                 System.out.println("MobileVerifyActivity" + "API Data" + new Gson().toJson(response.body()));

                                 if (response.body() != null) {
                                     if (response.body().getStatus() != null && response.body().getStatus().equals("success")) {
                                         String message = response.body().getMessage();
                                         Toast.makeText(DriverAccounting.this, message, Toast.LENGTH_SHORT).show();
                                         DriverApplication.dbManagerQuery.removeAllLocalDriverAccount();
                                         accountingModels.clear();
                                         etFinalRemarks.setText("");
                                         tvFinalAmount.setText("0");
                                         new GetDriverAccountDataBase(DriverAccounting.this).execute();
                                         hitCompletedBookingId();
                                     } else {
                                         CommonUtils.showToast(DriverAccounting.this, response.body().getMessage());

                                     }
                                 } else {
                                     if (dialog.isShowing()) {
                                         dialog.dismiss();
                                     }

                                 }
                             }

                             @Override
                             public void onFailure(@NotNull Call<InfoStatusModel> call, @NotNull Throwable t) {
                                 if (dialog != null && dialog.isShowing()) {
                                     dialog.dismiss();
                                 }
                                 System.out.println("API Data Error : " + t.getMessage());
                             }
                         }
            );
        } else {
            CommonUtils.showToastInCenter(DriverAccounting.this, getString(R.string.please_check_network));
        }
    }

}

