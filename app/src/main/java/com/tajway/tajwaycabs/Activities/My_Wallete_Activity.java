package com.tajway.tajwaycabs.Activities;

import static com.google.firebase.inappmessaging.internal.Logging.logd;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.Api;
import com.payumoney.core.PayUmoneyConfig;
import com.payumoney.core.PayUmoneySdkInitializer;
import com.payumoney.core.entity.TransactionResponse;
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;
import com.payumoney.sdkui.ui.utils.ResultModel;
import com.tajway.tajwaycabs.R;
import com.tajway.tajwaycabs.apiJsonResponse.WalletJsonResponse;
import com.tajway.tajwaycabs.commonutils.AppConstants;
import com.tajway.tajwaycabs.commonutils.CommonUtils;
import com.tajway.tajwaycabs.requestdata.OnewayTripRequest;
import com.tajway.tajwaycabs.responsedata.WalletData;
import com.tajway.tajwaycabs.retrofitModel.InitiatPaymentRsponse;
import com.tajway.tajwaycabs.retrofitModel.WalletBalanceModel;
import com.tajway.tajwaycabs.retrofitModel.WalletRechargeModel;
import com.tajway.tajwaycabs.retrofitwebservices.ApiExecutor;
import com.tajway.tajwaycabs.retrofitwebservices.Apiclient;
import com.tajway.tajwaycabs.retrofitwebservices.RequestUrl;
import com.tajway.tajwaycabs.session.SessonManager;
import com.google.gson.Gson;
import com.tajway.tajwaycabs.session.SharedPreference;
import com.tajway.tajwaycabs.util.ParseManager;
import com.tajway.tajwaycabs.util.Progressbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class My_Wallete_Activity extends AppCompatActivity {

    TextView walleteBalence;
    private AppPreference mAppPreference;
    //RecyclerView RvWallet;
    WalletAdapter walletAdapter;
    ArrayList<WalletData> arListWallet;
    Progressbar progressbar;
    private boolean isDisableExitConfirmation = false;
    private PayUmoneySdkInitializer.PaymentParam mPaymentParams;
    String orderId, msgDiscount, use_baclance, use_points, totel_amt, razorpayId, rozorpayPymtId, rozorpaySingature, use_checkboxtext, emails, mobile,amount,order_id,order_amount,productinfo;
    String hashkey;
    String name_user;
    String product_purchase;


   // Progressbar progressbar;
    TextView TvOneThousnads, TvTwoThousnads, TvThreeThousnads, TvWalletBalance, TvAddMoney, TvBalanceHistory, TvCashBackHistory;
    LinearLayout linear_balance_history, linear_cash_back_history;
    EditText EtAmount;

    Typeface font;

    SharedPreference sharedPreference;
    SessonManager sessonManager;
/*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_my__wallete_, container, false);*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_my__wallete_);
        getSupportActionBar().setTitle("Wallet");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TvOneThousnads = (TextView) findViewById(R.id.tv_one_thousands);
        TvTwoThousnads = (TextView)findViewById(R.id.tv_two_thousands);
        TvThreeThousnads = (TextView) findViewById(R.id.tv_three_thousands);
        EtAmount = (EditText) findViewById(R.id.tv_amount_add_money);
        TvAddMoney = (TextView)findViewById(R.id.tv_add_money_add_money);
        TvWalletBalance = (TextView) findViewById(R.id.tv_wallet_balance_add_money);
        linear_balance_history = (LinearLayout) findViewById(R.id.linear_balance_history);
        linear_cash_back_history = (LinearLayout) findViewById(R.id.linear_cash_back_history);
        mAppPreference = new AppPreference();

        TvBalanceHistory = (TextView)findViewById(R.id.tv_balance_history_add);
        TvCashBackHistory = (TextView) findViewById(R.id.tv_cash_back_history_add);

        TvOneThousnads.setText("+ " + "\u20B9 " + 1000);
        TvTwoThousnads.setText("+ " + "\u20B9 " + 2000);
        TvThreeThousnads.setText("+ " + "\u20B9 " + 3000);
       // font = Typeface.createFromAsset(My_Wallete_Activity.this.getAssets(), AppConstants.APP_FONT);

        sharedPreference = SharedPreference.getInstance(My_Wallete_Activity.this);


        sessonManager = new SessonManager(My_Wallete_Activity.this);
        //RvWallet = findViewById(R.id.rv_wallet);
        //walleteBalence = findViewById(R.id.walleteBalence);
        arListWallet = new ArrayList<>();
        progressbar = new Progressbar();
        //setRvWallet();
        //WalletAPI();

        TvOneThousnads.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                //Drawable mDrawable=getContext().getResources().getDrawable(R.drawable.ic_car_black_24dp);
                TvTwoThousnads.setBackground(My_Wallete_Activity.this.getDrawable(R.drawable.shape_add_money));
                TvThreeThousnads.setBackground(My_Wallete_Activity.this.getDrawable(R.drawable.shape_add_money));
                TvOneThousnads.setBackground(My_Wallete_Activity.this.getDrawable(R.drawable.one_thansouns));
                TvOneThousnads.setTextColor(getResources().getColor(R.color.white));
                TvTwoThousnads.setTextColor(getResources().getColor(R.color.colorPrimary));
                TvThreeThousnads.setTextColor(getResources().getColor(R.color.colorPrimary));
                EtAmount.setText("1000");
                amount = "1000";
                // Log.d("aaaaAmountAaa1",amount);

            }
        });

        TvTwoThousnads.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                TvOneThousnads.setBackground(My_Wallete_Activity.this.getDrawable(R.drawable.shape_add_money));
                TvThreeThousnads.setBackground(My_Wallete_Activity.this.getDrawable(R.drawable.shape_add_money));
                TvTwoThousnads.setBackground(My_Wallete_Activity.this.getDrawable(R.drawable.one_thansouns));
                TvTwoThousnads.setTextColor(getResources().getColor(R.color.white));
                TvOneThousnads.setTextColor(getResources().getColor(R.color.colorPrimary));
                TvThreeThousnads.setTextColor(getResources().getColor(R.color.colorPrimary));
                EtAmount.setText("2000");
                amount = "2000";
                //   Log.d("aaaaAmountAaa2",amount);
            }
        });

        TvThreeThousnads.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                TvOneThousnads.setBackground(My_Wallete_Activity.this.getDrawable(R.drawable.shape_add_money));
                TvTwoThousnads.setBackground(My_Wallete_Activity.this.getDrawable(R.drawable.shape_add_money));
                TvThreeThousnads.setBackground(My_Wallete_Activity.this.getDrawable(R.drawable.one_thansouns));
                TvThreeThousnads.setTextColor(getResources().getColor(R.color.white));
                TvOneThousnads.setTextColor(getResources().getColor(R.color.colorPrimary));
                TvTwoThousnads.setTextColor(getResources().getColor(R.color.colorPrimary));
                EtAmount.setText("3000");
                amount = "3000";
                //Log.d("aaaaAmountAaa3",amount);
            }
        });


        linear_balance_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(My_Wallete_Activity.this, BothHistoryActivity.class);
                intent.putExtra("position","0");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                My_Wallete_Activity.this.overridePendingTransition(R.anim.fade, R.anim.fadeout);
            }
        });

        linear_cash_back_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(My_Wallete_Activity.this, BothHistoryActivity.class);
                intent.putExtra("position","1");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                My_Wallete_Activity.this.overridePendingTransition(R.anim.fade, R.anim.fadeout);
            }
        });
        TvAddMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String amt=EtAmount.getText().toString();
                if (amt.equals("")){
                    Toast.makeText(My_Wallete_Activity.this, "Before enter amount", Toast.LENGTH_SHORT).show();
                }else {
                    //hitInitiatePaymentApi();
                   hitWalletRechargeApi(amt);
                }

            }
        });

        hitWalletBalanceApi();

        //setFont();


    }

    public void hitWalletBalanceApi(){
        progressbar.showProgress(My_Wallete_Activity.this);
       Call<WalletBalanceModel> call1 = Apiclient.getMyService().getWalletBalance("Bearer "+sessonManager.getToken());
       call1.enqueue(new Callback<WalletBalanceModel>() {
           @Override
            public void onResponse(Call<WalletBalanceModel> call, retrofit2.Response<WalletBalanceModel> response) {
               progressbar.hideProgress();
                if (response.isSuccessful()) {
                    WalletBalanceModel balanceModel = response.body();
                    TvWalletBalance.setText("Rs. "+balanceModel.balance);

                } else {
                    Toast.makeText(My_Wallete_Activity.this, "" + response, Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<WalletBalanceModel> call, Throwable t) {
                Toast.makeText(My_Wallete_Activity.this, "onFailure called ", Toast.LENGTH_SHORT).show();
                call.cancel();
                progressbar.hideProgress();
            }
        });
    }
//    private void setRvWallet() {
//        RvWallet.setHasFixedSize(true);
//        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(My_Wallete_Activity.this, 1);
//        RvWallet.setLayoutManager(layoutManager);
//
//        walletAdapter = new WalletAdapter(My_Wallete_Activity.this, arListWallet);
//        RvWallet.setAdapter(walletAdapter);
//
//    }


    public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.ViewHolder> {

        ArrayList<WalletData> history;
        Context context;

        public WalletAdapter(Context context, ArrayList<WalletData> history) {
            this.context = context;
            this.history = history;
        }


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_wallet, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            holder.tv_date_walle.setText(history.get(position).getDate());
            holder.tv_credit_walle.setText(history.get(position).getAmount());
            holder.tv_remark_walle.setText(history.get(position).getDescription());
            holder.tv_cr_db.setText(history.get(position).getType());

        }

        @Override
        public int getItemCount() {
            return history.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView tv_date_walle, tv_credit_walle, tv_remark_walle, tv_cr_db;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tv_date_walle = itemView.findViewById(R.id.tv_date_walle);
                tv_credit_walle = itemView.findViewById(R.id.tv_credit_walle);
                tv_remark_walle = itemView.findViewById(R.id.tv_remark_walle);
                tv_cr_db = itemView.findViewById(R.id.tv_cr_db);

            }
        }
    }

    private void hitWalletRechargeApi(String amount) {
        final WalletRechargeModel rechargeModel = new WalletRechargeModel(amount);
        Call<WalletRechargeModel> call1 =Apiclient.getMyService().postamount("Bearer " + sessonManager.getToken(),rechargeModel);
        call1.enqueue(new Callback<WalletRechargeModel>() {
            @Override
            public void onResponse(Call<WalletRechargeModel> call, Response<WalletRechargeModel> response) {
                WalletRechargeModel welletResponse = response.body();

                Log.d("walletresponse",new Gson().toJson(response.body()));
                if (welletResponse != null) {
                    order_id=welletResponse.data.refid;
                    order_amount=welletResponse.data.total;
                    emails=welletResponse.data.email;
                    mobile=welletResponse.data.mobile;
                    hashkey = welletResponse.data.hashdata;
                    name_user=welletResponse.data.name;
                    productinfo=welletResponse.data.product;

                    Log.d("hashkey", "onResponse: "+hashkey);
                    Log.d("hashkey", "order_id: "+order_id+", order_amount"+order_amount+", emails"+emails+", mobile"+mobile+", name_user"+name_user+", productinfo"+productinfo);




                  /* Intent intent = new Intent(WalletActivity.this, PaymentGetway_Activity.class);
                    intent.putExtra("order_id",order_id);
                    intent.putExtra("order_amount",order_amount);
                    intent.putExtra("email",email);
                    intent.putExtra("mobile",mobile);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    WalletActivity.this.overridePendingTransition(R.anim.fade, R.anim.fadeout);

                    */

                    launchPayUMoneyFlow();
                }


            }

            @Override
            public void onFailure(Call<WalletRechargeModel> call, Throwable t) {
                Toast.makeText(My_Wallete_Activity.this, "onFailure called ", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });
    }


    private void WalletAPI() {
        if (CommonUtils.isOnline(My_Wallete_Activity.this)) {
            final ProgressDialog dialog = ProgressDialog.show(My_Wallete_Activity.this, null, getString(R.string.loading));
            OnewayTripRequest request = new OnewayTripRequest();
            Call<WalletJsonResponse> call = ApiExecutor.getApiService(My_Wallete_Activity.this).apiWalletHistory("Bearer " + sessonManager.getToken());

            Log.d("URLshgghd", String.valueOf(call.request().url()));
            System.out.println("API url ---" + call.request().url());
            System.out.println("API request  ---" + new Gson().toJson(request));
            call.enqueue(new Callback<WalletJsonResponse>() {
                             @Override
                             public void onResponse(Call<WalletJsonResponse> call, Response<WalletJsonResponse> response) {
                                 System.out.println("ViewVisitorType " + "API Data" + new Gson().toJson(response.body()));

                                 String respon = new Gson().toJson(response.body());
                                 Log.d("OneWaResponse", respon);

                                 if (dialog != null && dialog.isShowing()) {
                                     dialog.dismiss();
                                 }
                                 if (response.body() != null) {
                                     if (response.body().status != null && response.body().status.equals("success")) {

                                         String balance = response.body().data.balance;
                                         walleteBalence.setText("Points " + balance);
                                         // Log.d("shgjdbalance", balance);

                                        if (response.body().data.history != null && response.body().data.history.size() > 0) {
                                             arListWallet.clear();
                                            arListWallet.addAll(response.body().data.history);
                                             walletAdapter.notifyDataSetChanged();
                                         } else {
                                            arListWallet.clear();
                                             arListWallet.addAll(response.body().data.history);
                                            walletAdapter.notifyDataSetChanged();
                                         }

                                     } else {
                                         //  tv_norecord_one.setVisibility(View.VISIBLE);
                                         // RvOneWay.setVisibility(View.GONE);
                                         //  CommonUtils.showToast(My_Wallete_Activity.this, response.body().status);
                                     }
                                 } else {
                                     if (dialog != null && dialog.isShowing()) {
                                         dialog.dismiss();
                                     }
                                     //CommonUtils.setSnackbar(tvClassName, getString(R.string.server_not_responding));
                                 }
                             }

                             @Override
                             public void onFailure(Call<WalletJsonResponse> call, Throwable t) {
                                 if (dialog != null && dialog.isShowing()) {
                                     dialog.dismiss();
                                 }
                                 System.out.println("API Data Error : " + t.getMessage());
                             }
                         }
            );
        } else {
            CommonUtils.showToastInCenter(My_Wallete_Activity.this, getString(R.string.please_check_network));
        }
    }

    private void launchPayUMoneyFlow() {

        PayUmoneyConfig payUmoneyConfig = PayUmoneyConfig.getInstance();
        payUmoneyConfig.disableExitConfirmation(isDisableExitConfirmation);
        PayUmoneySdkInitializer.PaymentParam.Builder builder = new PayUmoneySdkInitializer.PaymentParam.Builder();
        String amount="";
        //try {
        amount = order_amount;

        // amount="1093";
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        String txnId = order_id;
        //String txnId = "TXNID720431525261327973";
        String phone = mobile;

        // String phone="8767854111";
        String productName = productinfo;
        String firstName = name_user;
        String email = emails;
        String udf1 = "";
        String udf2 = "";
        String udf3 = "";
        String udf4 = "";
        String udf5 = "";
        String udf6 = "";
        String udf7 = "";
        String udf8 = "";
        String udf9 = "";
        String udf10 = "";


        // BaseApplicationpay BaseApplicationpay=new BaseApplicationpay();
        AppEnvironment appEnvironment = AppEnvironment.PRODUCTION;

        Log.d("checkout_data", txnId+"-->"+phone+"-->"+productName+"-->"+firstName+"-->"+email+"-->"+appEnvironment.surl()+"-->"+appEnvironment.furl()+"-->"+amount);


        builder.setAmount(amount)
                .setTxnId(txnId)
                .setPhone(phone)
                .setProductName(productName)
                .setFirstName(firstName)
                .setEmail(email)
                .setsUrl(appEnvironment.surl())
                .setfUrl(appEnvironment.furl())
                .setUdf1(udf1)
                .setUdf2(udf2)
                .setUdf3(udf3)
                .setUdf4(udf4)
                .setUdf5(udf5)
                .setUdf6(udf6)
                .setUdf7(udf7)
                .setUdf8(udf8)
                .setUdf9(udf9)
                .setUdf10(udf10)

                .setIsDebug(false)
                .setKey("3uj3A3Eg")
                .setMerchantId("7290629");

        try {
            mPaymentParams = builder.build();

            /*
             * Hash should always be generated from your server side.
             * */
            //    generateHashFromServer(mPaymentParams);

            /*            *//**
             * Do not use below code when going live
             * Below code is provided to generate hash from sdk.
             * It is recommended to generate hash from server side only.
             * */

            //
            mPaymentParams.setMerchantHash(hashkey);

            Log.d("mPaymentParams", String.valueOf(hashkey));

            // mPaymentParams = calculateServerSideHashAndInitiatePayment1(mPaymentParams);

            if (AppPreference.selectedTheme != -1) {

                Log.d("lakshikant","1");
                PayUmoneyFlowManager.startPayUMoneyFlow(mPaymentParams,My_Wallete_Activity.this, AppPreference.selectedTheme,true);
            } else {
                Log.d("lakshikant","0");
                PayUmoneyFlowManager.startPayUMoneyFlow(mPaymentParams,My_Wallete_Activity.this, R.style.AppTheme_default, false);
            }

        } catch (Exception e) {
            // some exception occurred
            Log.d("lakshmierror",e.getMessage());
            // Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            //  payNowButton.setEnabled(true);
        }
    }
    public void hitInitiatePaymentApi() {
        RequestQueue requestQueue = Volley.newRequestQueue(My_Wallete_Activity.this);
        progressbar.showProgress(My_Wallete_Activity.this);
        String url="";
        url = RequestUrl.INITIATE_PAYMENT + orderId;
//       if (value.equals("1")) {
//
//       } else {
//           //url = Api.INITIATE_PAYMENT + orderId + "?type=cod";
//      }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressbar.hideProgress();
                Log.d("DFSFDSDSDS", response);
                try {
                    InitiatPaymentRsponse paymentRsponse = ParseManager.getInstance().fromJSON(response, InitiatPaymentRsponse.class);
                    if (paymentRsponse != null) {
                        String msg = paymentRsponse.message;
                        if (paymentRsponse.status.equals("success")) {
                            String payment_done = paymentRsponse.data.paymentDone;
                            razorpayId = paymentRsponse.data.refid;
                            totel_amt = paymentRsponse.data.total;
                            emails = paymentRsponse.data.email;
                            mobile = paymentRsponse.data.mobile;
                            name_user=paymentRsponse.data.name;
                            hashkey=paymentRsponse.data.hashdata;
                            //  paymentRsponse.data.p
                            //product_purchase="Product Purchase at Veganfresh";
                            if (payment_done.equalsIgnoreCase("yes")) {
                                Toast.makeText(My_Wallete_Activity.this, "" + msg, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(My_Wallete_Activity.this, OrderSuccessfully_Activity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("ref_id", paymentRsponse.data.refid);
                                startActivity(intent);
                            } else {
                                //startPaymentGetway();
                                launchPayUMoneyFlow();
                            }

                        } else {
                            Toast.makeText(My_Wallete_Activity.this, "" + msg, Toast.LENGTH_SHORT).show();
                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //  Log.d("ldfslj",volleyError.getMessage());
                progressbar.hideProgress();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<>();
                //hashMap.put("coupon", couponEDT);
                //hashMap.put("time_slot", slot_id);
//                if (radioPointsBTN.isChecked()) {
//                    hashMap.put("use_points", use_points);
//                } else {
//                    hashMap.put("use_points", "");
//                }
//                if (radioWalletBTN.isChecked()) {
//                    hashMap.put("use_balance", use_baclance);
//                } else {
//                    hashMap.put("use_balance", "");
//                }
//                if (checkBoxTXT.isChecked()) {
//                    hashMap.put("express_delivery", use_checkboxtext);
//                } else {
//                    hashMap.put("express_delivery", "");
//                }
//                Log.d("dvsvvvv", String.valueOf(hashMap));
                return hashMap;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<String, String>();
                headerMap.put("Authorization", "Bearer " + sessonManager.getToken());
                // Log.d("rfgdfdf", sharedPreference.getString("auth_token", ""));
                return headerMap;
            }
        };
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result Code is -1 send from Payumoney activity
        Log.d("MainActivity", "request code " + requestCode + " resultcode " + resultCode);

        Log.d("transactional","lakshmi");
        if (requestCode == PayUmoneyFlowManager.REQUEST_CODE_PAYMENT && resultCode == RESULT_OK && data !=
                null) {
            TransactionResponse transactionResponse = data.getParcelableExtra(PayUmoneyFlowManager
                    .INTENT_EXTRA_TRANSACTION_RESPONSE);

            //Log.d("transactional",)
            Log.d("transactional",transactionResponse.getPayuResponse());

            ResultModel resultModel = data.getParcelableExtra(PayUmoneyFlowManager.ARG_RESULT);

            // Check which object is non-null
            if (transactionResponse != null && transactionResponse.getPayuResponse() != null) {
                if (transactionResponse.getTransactionStatus().equals(TransactionResponse.TransactionStatus.SUCCESSFUL)) {


                   // hitVerifyPaymentApi(transactionResponse.getPayuResponse());

                    //Success Transaction
                    new AlertDialog.Builder(this)
                            .setCancelable(false)
                            .setMessage("Payment Sucessfully Done!!!")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {

                                    dialog.dismiss();

                                   Intent intent = new Intent(My_Wallete_Activity.this, MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();

                                }
                            }).show();


                } else {
                    //Failure Transaction
                }

                // Response from Payumoney
                String payuResponse = transactionResponse.getPayuResponse();

                // Response from SURl and FURL
                String merchantResponse = transactionResponse.getTransactionDetails();




            } else if (resultModel != null && resultModel.getError() != null) {
                Log.d("shopr", "Error response : " + resultModel.getError().getTransactionResponse());
            } else {
                Log.d("shopr", "Both objects are null!");
            }
        }
    }

    private void hitVerifyPaymentApi(final String checkdata) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
       // progressbar.showProgress(My_Wallete_Activity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RequestUrl.VERIFY_PAYMENT, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
              //  progressbar.hideProgress();
                Log.d("GETpayRESPONSE", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String msg = jsonObject.getString("message");
                    String status = jsonObject.getString("status");
                    if (status.equalsIgnoreCase("success")) {
                        Intent intent = new Intent(My_Wallete_Activity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        Toast.makeText(My_Wallete_Activity.this, "" + msg, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(My_Wallete_Activity.this, "" + msg, Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressbar.hideProgress();

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<String, String>();
                headerMap.put("Authorization", "Bearer " + sessonManager.getToken());
                Log.d("hkjhjk", sessonManager.getToken());
                return headerMap;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("json_data", checkdata);
                //  hashMap.put("razorpay_payment_id", rozorpayPymtId);
                //  hashMap.put("razorpay_signature", rozorpaySingature);
                Log.d("checkparms", String.valueOf(hashMap));

                return hashMap;
            }
        };
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

/*@Override
public void onPaymentError(int code, String response, PaymentData data) {
    Toast.makeText(this, "Your payment is not seccessfull!", Toast.LENGTH_LONG).show();
    startActivity(new Intent(My_Wallete_Activity.this, HomeActivity.class));

}*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}