package nithra.calender.marriage.services.MarriageServices;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;

import nithra.calender.marriage.services.R;
import nithra.calender.marriage.services.SharedPreference;
import nithra.calender.marriage.services.Utils;


public class ActivityPayment extends AppCompatActivity {

    String user_id;
    String user_type;
    String paymentUrl;
    WebView webView;
    SharedPreference sharedPreference = new SharedPreference();
//    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_lay1);
//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        Intent intent = getIntent();
        if (intent != null) {
            paymentUrl = intent.getStringExtra("paymentUrl");
        } else {
            paymentUrl = "";
        }


        webView = findViewById(R.id.web);
        WebSettings ws = webView.getSettings();
        ws.setJavaScriptEnabled(true);


        webView.loadUrl(paymentUrl);
        webView.addJavascriptInterface(new WebAppInterface(this), "Android");
        webView.setInitialScale(1);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.clearHistory();
        webView.clearFormData();
        webView.clearCache(true);
        WebSettings webSettings = webView.getSettings();
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        //Only disabled the horizontal scrolling:
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

//To disabled the horizontal and vertical scrolling:
  /*      webView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return (event.getAction() == MotionEvent.ACTION_MOVE);
            }
        });*/
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (!Utils.isNetworkAvailable(ActivityPayment.this)) {
                    Utils.toast_center(ActivityPayment.this, "இணையதள சேவையை சரிபார்க்கவும் ");
                    return true;
                }
                view.loadUrl(url);

                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (url.contains("viewplan.php")) {
                    try {
                        Utils.mProgress(ActivityPayment.this, "ஏற்றுகிறது. காத்திருக்கவும் ", false).show();
                    } catch (Exception e) {

                    }
                }

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                try {
                    if (Utils.mProgress != null) {
                        Utils.mProgress.dismiss();
                    }
                } catch (Exception e) {

                }
                super.onPageFinished(view, url);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //LinearLayout ads_lay = findViewById(R.id.addview);
//        mFirebaseAnalytics.setCurrentScreen(Paytm_activity1.this, "TC_ADS_Paytm_activity", null);
    }

    public void down_info(final int val, final String start_date, final String end_date) {
        final Dialog rating_dialog = new Dialog(ActivityPayment.this,
                android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
        rating_dialog.setContentView(R.layout.info_dia);
        rating_dialog.setCancelable(false);
        AppCompatButton btnSend = rating_dialog.findViewById(R.id.btnSend);
        AppCompatTextView textt = rating_dialog.findViewById(R.id.textt);
        AppCompatTextView textView1 = rating_dialog.findViewById(R.id.textView1);

        CardView ok_card = rating_dialog.findViewById(R.id.ok_card);
        btnSend.setText("சரி");


        ok_card.setCardBackgroundColor(Utils.get_color(ActivityPayment.this));
        textView1.setBackgroundColor(Utils.get_color(ActivityPayment.this));


        if (val == 0) {
            textt.setText("உங்களது கட்டணம் வெற்றிகரமாக செலுத்தப்பட்டது.உங்கள் பதிவானது பயனாளர்களுக்கு காண்பிக்கப்படும். இருப்பினும் உங்கள் பதிவானது எங்களது நிறுவனத்தினரால் பரிசீலனை செய்யப்படும்");
        } else if (val == 2) {
            textt.setText("உங்களது account -ல் இருந்து பணம் எடுக்கப்பட்டிருந்தால் மூன்று நாட்களுக்குள் உங்களது தகவல் activate செய்யப்படும். ஏதேனும் சந்தேகம் இருப்பின் 9597215816 என்ற எண்ணிற்கு எங்களை தொடர்பு கொள்ளவும். (திங்கள் - வெள்ளி & 10:00 AM - 05:00 PM)");
        } else {
            textt.setText("Paymrnt failure. மீண்டும் முயற்சிக்கவும். ஏதேனும் சந்தேகம் இருப்பின் 9597215816 என்ற எண்ணிற்கு எங்களை தொடர்பு கொள்ளவும். (திங்கள் - வெள்ளி & 10:00 AM - 05:00 PM)");
        }
        textt.setGravity(Gravity.CENTER);


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rating_dialog.dismiss();
              /*  if (val == 0) {
                    sharedPreference.putString(Paytm_activity1.this, "progithar_reg_start_date" +
                            sharedPreference.getString(Paytm_activity1.this, "progithar_type"), start_date);
                    sharedPreference.putString(Paytm_activity1.this, "progithar_reg_end_date" +
                            sharedPreference.getString(Paytm_activity1.this, "progithar_type"), end_date);
                    sharedPreference.putString(Paytm_activity1.this, "progithar_reg_paystatus" +
                            sharedPreference.getString(Paytm_activity1.this, "progithar_type"), "paid");
                    progithar_main.onedit = 1;
                    if (sharedPreference.getInt(Paytm_activity1.this, "Progi_Reg_Click") == 1) {
                        finish();
                        Intent i = new Intent(Paytm_activity1.this, progithar_main.class);
                        startActivity(i);
                    } else {
                        finish();
                    }
                } else {
                    if (sharedPreference.getInt(Paytm_activity1.this, "Progi_Reg_Click") == 1) {
                        finish();
                        Intent i = new Intent(Paytm_activity1.this, progithar_main.class);
                        startActivity(i);
                    } else {
                        finish();
                    }
                }*/
            }
        });
        rating_dialog.show();
    }

    private class WebAppInterface {
        Context context;

        public WebAppInterface(ActivityPayment ActivityPayment) {
            this.context = ActivityPayment;
        }

        /**
         * Show a toast from the web page
         * Payment Success,start_date,end_datePayment PendingInvalid Payment
         */
        @JavascriptInterface
        public void showToast(String payment, String start_date, String end_date) {
            System.out.println("response : " + payment + " - " + start_date + " - " + end_date);
          /*  if (payment.toLowerCase().contains("success")) {
                down_info(0, start_date, end_date);
            } else if (payment.toLowerCase().contains("pending")) {
                down_info(2, start_date, end_date);
            } else {
                down_info(1, start_date, end_date);
            }*/

            if (payment.toLowerCase().equals("close")) {

              //  if (sharedPreference.getInt(Paytm_activity1.this, "Progi_Reg_Click") == 1) {
                    finish();
                   /* Intent i = new Intent(ActivityPayment.this, ActivityMandapam.class);
                    startActivity(i);*/
//                } else {
//                    finish();
//                }


            }

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
       /* if (sharedPreference.getInt(Paytm_activity1.this, "Progi_Reg_Click") == 1) {
            finish();
            Intent i = new Intent(Paytm_activity1.this, progithar_main.class);
            startActivity(i);
        } else {
            finish();
        }*/
    }

}
