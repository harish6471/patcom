package com.example.expo.blogapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import timber.log.Timber;

import com.example.expo.blogapp.Activities.AccountActivity;
import com.example.expo.blogapp.Activities.LoginActivity1;
import com.example.expo.blogapp.Activities.MainActivity;
import com.example.expo.blogapp.Activities.SettingsActivity;
import com.example.expo.blogapp.Activities.SettingsActivity2;
import com.example.expo.blogapp.Activities.Transcation;
import com.example.expo.blogapp.Activities.Upiactivity;
import com.example.expo.blogapp.Models.Api;
import com.example.expo.blogapp.Models.Checksum;
import com.example.expo.blogapp.Models.Constant;
import com.example.expo.blogapp.Models.Constants;
import com.example.expo.blogapp.Models.Paytm;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firestore.v1.StructuredQuery;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.payumoney.core.PayUmoneySdkInitializer;
import com.payumoney.core.entity.TransactionResponse;
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;
import com.razorpay.Checkout;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class cardpayment extends AppCompatActivity implements PaytmPaymentTransactionCallback {
    PayUmoneySdkInitializer.PaymentParam.Builder builder = new PayUmoneySdkInitializer.PaymentParam.Builder();
    //declare paymentParam object
    PayUmoneySdkInitializer.PaymentParam paymentParam = null;

    private CardView paytm1,upi2,pau2,razorpay1;
    private TextView title;
    private TextView account,profile,payment,help,lblStatus,statusMessage,responseTitle,btnCheckOrders;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private Toolbar toolbar;
    private ImageView bck_btn,iconStatus;
    LinearLayout layoutOrderPlaced;

    private Bundle bundle;
    private AVLoadingIndicatorView loader;


    final int UPI_PAYMENT = 0;
    String TAG = "Payment Error";
    String amount="";
    String upi="";
    String note="";
    String phonenumber1="";
    String phonenumber="";

    String name="";
    String hospitalname="";
    String hospitalbill="";
    String title1="";

    String  txnid ="txt12346", phone ="6284224993",
            prodname ="patcom", email ="chimataharish111@gmail.com",
            merchantId ="5884494", merchantkey="VDwPUOHI";
    Integer amount1;
    //int amount1="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardpayment);
        bundle = getIntent().getExtras();
        if (bundle != null) {
            amount= bundle.getString("amount");
            phonenumber1= bundle.getString("Phonenumber");
            hospitalname= bundle.getString("Hospitalname");
            hospitalbill= bundle.getString("HospitalBill");
            title1= bundle.getString("Title");
       //   phonenumber= bundle.getString("phone");
            note= bundle.getString("note");
            upi= bundle.getString("upiid");
            name= bundle.getString("name");


        }

        toolbar = findViewById(R.id.toolbar);
        title = findViewById(R.id.title);
        account = findViewById(R.id.account);
        profile = findViewById(R.id.profile);
        payment= findViewById(R.id.payment);
        help= findViewById(R.id.help);
        bck_btn=findViewById(R.id.back_btn);
      //  loader=findViewById(R.id.loader);
        iconStatus=findViewById(R.id.icon_status);
        statusMessage=findViewById(R.id.status_message);
        responseTitle=findViewById(R.id.title_status);
      //  btnCheckOrders=findViewById(R.id.btn_check_orders);



        paytm1 = findViewById(R.id.paytm1);
       upi2 = findViewById(R.id.upi2);
       pau2= findViewById(R.id.pau2);
        razorpay1= findViewById(R.id.razorpay1);

        init();


      /*  btnCheckOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   startActivity(new Intent(cardpayment.this, TransactionsActivity.class));
                finish();
            }
        });*/




        paytm1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Intent i=new Intent(cardpayment.this, paytmActivity.class);
               // startActivity(i);
                generateCheckSum();

            }
        });

        upi2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent i=new Intent(cardpayment.this, SettingsActivity.class);
               // startActivity(i);
                payUsingUpi(amount, upi, name, note);



            }
        });
        pau2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startpay();

            }
        });
        razorpay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              amount1=Integer.parseInt(amount);
              amount1=amount1*100;

                startPayment(amount);

            }
        });
        bck_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardpayment.super.onBackPressed();
            }
        });

    }

    private void init() {
        prepareOrder();
    }

    private void prepareOrder() {

    }

    private void startpay() {
        builder.setAmount(amount)                          // Payment amount
                .setTxnId(txnid)                     // Transaction ID
                .setPhone(phone)                   // User Phone number
                .setProductName(prodname)                   // Product Name or description
                .setFirstName(name)                              // User First name
                .setEmail(email)              // User Email ID
                .setsUrl("https://www.payumoney.com/mobileapp/payumoney/success.php")     // Success URL (surl)
                .setfUrl("https://www.payumoney.com/mobileapp/payumoney/failure.php")     //Failure URL (furl)
                .setUdf1("")
                .setUdf2("")
                .setUdf3("")
                .setUdf4("")
                .setUdf5("")
                .setUdf6("")
                .setUdf7("")
                .setUdf8("")
                .setUdf9("")
                .setUdf10("")
                .setIsDebug(true)                              // Integration environment - true (Debug)/ false(Production)
                .setKey(merchantkey)                        // Merchant key
                .setMerchantId(merchantId);


        try {
            paymentParam = builder.build();
            // generateHashFromServer(paymentParam );
            getHashkey();

        } catch (Exception e) {
            Log.e(TAG, " error s "+e.toString());
        }

    }

    private void getHashkey() {
        ServiceWrapper service = new ServiceWrapper(null);
        Call<String> call = service.newHashCall(merchantkey, txnid, amount, prodname,
                name, email);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e(TAG, "hash res "+response.body());
                String merchantHash= response.body();
                if (merchantHash.isEmpty() || merchantHash.equals("")) {
                    Toast.makeText(cardpayment.this, "Could not generate hash", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "hash empty");
                } else {
                    // mPaymentParams.setMerchantHash(merchantHash);
                    paymentParam.setMerchantHash(merchantHash);
                    // Invoke the following function to open the checkout page.
                    // PayUmoneyFlowManager.startPayUMoneyFlow(paymentParam, StartPaymentActivity.this,-1, true);
                    PayUmoneyFlowManager.startPayUMoneyFlow(paymentParam, cardpayment.this, R.style.AppTheme_default, false);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e(TAG, "hash error "+ t.toString());
            }
        });
    }

    private void startPayment(String amount) {
        Checkout checkout = new Checkout();

        /**
         * Set your logo here
         */
        // checkout.setImage(R.drawable.logo);

        /**
         * Reference to current activity
         */
        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {

          //  String note = noteEt.getText().toString();
            JSONObject options = new JSONObject();

            /**
             * Merchant Name
             * eg: ACME Corp || HasGeek etc.
             */
            options.put("name", "Techriz");

            /**
             * Description can be anything
             * eg: Order #123123
             *     Invoice Payment
             *     etc.
             */
            options.put("description", note);

            options.put("currency", "INR");

            /**
             * Amount is always passed in PAISE
             * Eg: "500" = Rs 5.00
             */
            options.put("amount",amount1);

            checkout.open(activity, options);
        } catch (Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }

    private void payUsingUpi(String amount, String upi, String name, String note) {
        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upi)
                .appendQueryParameter("pn", name)
                .appendQueryParameter("tn", note)
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", "INR")
                .build();


        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);

        // will always show a dialog to user to choose an app
        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");

        // check if intent resolves
        if (null != chooser.resolveActivity(getPackageManager())) {
            startActivityForResult(chooser, UPI_PAYMENT);
        } else {
            Toast.makeText(cardpayment.this, "No UPI app found, please install one to continue", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.e("StartPaymentActivity", "request code " + requestCode + " resultcode " + resultCode);
        if (requestCode == PayUmoneyFlowManager.REQUEST_CODE_PAYMENT && resultCode == RESULT_OK && data != null) {
            TransactionResponse transactionResponse = data.getParcelableExtra( PayUmoneyFlowManager.INTENT_EXTRA_TRANSACTION_RESPONSE );

            if (transactionResponse != null && transactionResponse.getPayuResponse() != null) {

                if(transactionResponse.getTransactionStatus().equals( TransactionResponse.TransactionStatus.SUCCESSFUL )){
                    //Success Transaction
                    Intent intent = new Intent(cardpayment.this,Transcation.class);
                    intent.putExtra("amount", amount);
                    intent.putExtra("Hospitalname", hospitalname);
                    intent.putExtra("HospitalBill", hospitalbill);
                    intent.putExtra("Title", title1);
                    intent.putExtra("name", name);
                } else{
                    //Failure Transaction
                }

                // Response from Payumoney
                String payuResponse = transactionResponse.getPayuResponse();

                // Response from SURl and FURL
                String merchantResponse = transactionResponse.getTransactionDetails();
                Log.e(TAG, "tran "+payuResponse+"---"+ merchantResponse);
            } /* else if (resultModel != null && resultModel.getError() != null) {
                Log.d(TAG, "Error response : " + resultModel.getError().getTransactionResponse());
            } else {
                Log.d(TAG, "Both objects are null!");
            }*/
        }

        switch (requestCode) {
            case UPI_PAYMENT:
                if ((RESULT_OK == resultCode) || (resultCode == 11)) {
                    if (data != null) {
                        String trxt = data.getStringExtra("response");
                        Log.d("UPI", "onActivityResult: " + trxt);
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add(trxt);
                        upiPaymentDataOperation(dataList);
                    } else {
                        Log.d("UPI", "onActivityResult: " + "Return data is null");
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add("nothing");
                        upiPaymentDataOperation(dataList);
                    }
                } else {
                    Log.d("UPI", "onActivityResult: " + "Return data is null"); //when user simply back without payment
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }
                break;
        }


    }

    private void upiPaymentDataOperation(ArrayList<String> data) {
        if (isConnectionAvailable(cardpayment.this)) {
            String str = data.get(0);
            Log.d("UPIPAY", "upiPaymentDataOperation: " + str);
            String paymentCancel = "";
            if (str == null) str = "discard";
            String status = "";
            String approvalRefNo = "";
            String response[] = str.split("&");
            for (int i = 0; i < response.length; i++) {
                String equalStr[] = response[i].split("=");
                if (equalStr.length >= 2) {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalStr[1].toLowerCase();
                    } else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                        approvalRefNo = equalStr[1];
                    }
                } else {
                    paymentCancel = "Payment cancelled by user.";
                }
            }

            if (status.equals("success")) {
                //Code to handle successful transaction here.
                Toast.makeText(cardpayment.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
                Log.d("UPI", "responseStr: " + approvalRefNo);
            } else if ("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText(cardpayment.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(cardpayment.this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(cardpayment.this, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }
    public void onPaymentSuccess(String s) {
        Toast.makeText(this, "Payment successful", Toast.LENGTH_SHORT).show();

    }

    public void onPaymentError(int i, String s) {
        /*
        try {
            Toast.makeText(this, "Payment failed: " + code + " " + response, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentError", e);
        }*/
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void generateCheckSum() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //creating the retrofit api service
        Api apiService = retrofit.create(Api.class);

        //creating paytm object
        //containing all the values required
        final Paytm paytm = new Paytm(
                Constants.M_ID,
                Constants.CHANNEL_ID,
                amount,
                Constants.WEBSITE,
                Constants.CALLBACK_URL,
                Constants.INDUSTRY_TYPE_ID
        );

        //creating a call object from the apiService
        Call<Checksum> call = apiService.getChecksum(
                paytm.getmId(),
                paytm.getOrderId(),
                paytm.getCustId(),
                paytm.getChannelId(),
                amount,
                paytm.getWebsite(),
                paytm.getCallBackUrl(),
                paytm.getIndustryTypeId()
        );
        call.enqueue(new Callback<Checksum>() {
            @Override
            public void onResponse(Call<Checksum> call, Response<Checksum> response) {
                if (!response.isSuccessful()) {
                    handleUnknownError();
                    showOrderStatus(false);
                    return;
                }
                //once we get the checksum we will initiailize the payment.
                //the method is taking the checksum we got and the paytm object as the parameter
                initializePaytmPayment(response.body().getChecksumHash(), paytm);
            }

            @Override
            public void onFailure(Call<Checksum> call, Throwable t) {
                handleError(t);
            //    showOrderStatus(false);

            }
        });



    }

    private void handleError(Throwable t) {
        showErrorDialog(getString(R.string.msg_unknown));
    }

    private void showErrorDialog(String message) {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.error))
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

    private void handleUnknownError() {
        showErrorDialog(getString(R.string.msg_unknown));

    }

    private void setStatus(int message) {
        lblStatus.setText(message);
    }



    private void initializePaytmPayment(String checksumHash, Paytm paytm) {
        PaytmPGService Service = PaytmPGService.getStagingService();

        //use this when using for production
        //PaytmPGService Service = PaytmPGService.getProductionService();

        //creating a hashmap and adding all the values required
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("MID", Constants.M_ID);
        paramMap.put("ORDER_ID", paytm.getOrderId());
        paramMap.put("CUST_ID", paytm.getCustId());
        paramMap.put("CHANNEL_ID", paytm.getChannelId());
        paramMap.put("TXN_AMOUNT",amount);
     //   paramMap.put("MOBILE_NO",phonenumber);
        paramMap.put("WEBSITE", paytm.getWebsite());
        paramMap.put("CALLBACK_URL", paytm.getCallBackUrl());
        paramMap.put("CHECKSUMHASH", checksumHash);
        paramMap.put("INDUSTRY_TYPE_ID", paytm.getIndustryTypeId());
        PaytmOrder order = new PaytmOrder((HashMap<String, String>) paramMap);

        //intializing the paytm service
        Service.initialize(order, null);

        //finally starting the payment transaction
        Service.startPaymentTransaction(this, true, true, this);
    }

    public void onTransactionResponse(Bundle inResponse) {

       // Toast.makeText(this, "Transcation Successfull", Toast.LENGTH_SHORT).show();
        String orderId = inResponse.getString("ORDERID");
        //verifyTransactionStatus(orderId);
      //  verifyTransactionStatus(orderId);
       // showOrderStatus(true);
        newpage();

       // Toast.makeText(this, inResponse.toString(), Toast.LENGTH_LONG).show();
    }

    private void showOrderStatus(boolean isSuccess) {
        loader.setVisibility(View.GONE);
        lblStatus.setVisibility(View.GONE);
        if (isSuccess) {
            iconStatus.setImageResource(R.drawable.baseline_check_black_48);
            iconStatus.setColorFilter(ContextCompat.getColor(this, R.color.colorGreen));
            responseTitle.setText(R.string.thank_you);
            statusMessage.setText(R.string.msg_order_placed_successfully);

            // as the order placed successfully, clear the cart
            //   AppDatabase.clearCart();
        } else {
            iconStatus.setImageResource(R.drawable.close);
            // iconStatus.setColorFilter(ContextCompat.getColor(this, R.color.btn_remove_item));
            responseTitle.setText(R.string.order_failed);
            statusMessage.setText(R.string.msg_order_placed_failed);
        }

        layoutOrderPlaced.setVisibility(View.VISIBLE);
    }


    private void newpage() {


        Intent intent = new Intent(cardpayment.this,Transcation.class);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //creating the retrofit api service
        Api apiService = retrofit.create(Api.class);

        final Paytm paytm = new Paytm(
                Constants.M_ID,
                Constants.CHANNEL_ID,
                amount,
                Constants.WEBSITE,
                Constants.CALLBACK_URL,
                Constants.INDUSTRY_TYPE_ID
        );

        //creating a call object from the apiService
        Call<Checksum> call = apiService.getChecksum(
                paytm.getmId(),
                paytm.getOrderId(),
                paytm.getCustId(),
                paytm.getChannelId(),
                amount,
                paytm.getWebsite(),
                paytm.getCallBackUrl(),
                paytm.getIndustryTypeId()
        );
        intent.putExtra("amount", amount);
        intent.putExtra("Hospitalname", hospitalname);
        intent.putExtra("HospitalBill", hospitalbill);
        intent.putExtra("Title", title1);
        intent.putExtra("name", name);

        intent.putExtra("orderId", paytm.getOrderId());




        startActivity(intent);



    }


    public void networkNotAvailable() {
        Toast.makeText(this, "Network error", Toast.LENGTH_LONG).show();
    }


    public void clientAuthenticationFailed(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }


    public void someUIErrorOccurred(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }


    public void onErrorLoadingWebPage(int i, String s, String s1) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }


    public void onBackPressedCancelTransaction() {
        Toast.makeText(this, "Transcation cancelled", Toast.LENGTH_LONG).show();
    }


    public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {

        Timber.e("onTransactionCancel: %s | %s", inErrorMessage, inResponse);
        finish();
    //   Toast.makeText(this, s + bundle.toString(), Toast.LENGTH_LONG).show();

    }

}
