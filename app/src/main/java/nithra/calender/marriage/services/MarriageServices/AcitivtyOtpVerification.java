package nithra.calender.marriage.services.MarriageServices;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import nithra.calender.marriage.services.HttpHandler;
import nithra.calender.marriage.services.R;
import nithra.calender.marriage.services.SharedPreference;
import nithra.calender.marriage.services.Utils;


public class AcitivtyOtpVerification extends AppCompatActivity {

    AppCompatEditText editTextOtp1;
    AppCompatEditText editTextOtp2;
    AppCompatEditText editTextOtp3;
    AppCompatEditText editTextOtp4;
    AppCompatEditText editTextOtp5;
    AppCompatEditText editTextOtp6;
    TextView resend;
    CardView cardOk;
    public int counter = 90;
    SharedPreference sharedPreference;
    String otp;
    String url = "" + Utils.url_calender_service;
    Toolbar mToolbar;
    String user_service_type_name = "";
    String st_resend = "<font color=blue> <u>RESEND OTP</u></font>";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_otp_verification);
        sharedPreference = new SharedPreference();
        user_service_type_name = sharedPreference.getString(AcitivtyOtpVerification.this, "user_service_type_name");

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setTitle("OTP Verification");
        getSupportActionBar().setTitle("OTP Verification");

        editTextOtp1 = findViewById(R.id.editTextOtp1);
        editTextOtp2 = findViewById(R.id.editTextOtp2);
        editTextOtp3 = findViewById(R.id.editTextOtp3);
        editTextOtp4 = findViewById(R.id.editTextOtp4);
        editTextOtp5 = findViewById(R.id.editTextOtp5);
        editTextOtp6 = findViewById(R.id.editTextOtp6);
        cardOk = findViewById(R.id.cardOk);
        resend = findViewById(R.id.textResend);
        cardOk.setAlpha(0.5f);


        new CountDownTimer(90000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                resend.setText("" + String.valueOf(counter));
                counter--;
            }

            @Override
            public void onFinish() {

                resend.setText(Html.fromHtml(st_resend));
            }
        }.start();

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Utils.isNetworkAvailable(AcitivtyOtpVerification.this)) {
                    Utils.toast_normal(AcitivtyOtpVerification.this, "இணையதள சேவையை சரிபார்க்கவும் ");
                    return;
                }
                System.out.println("==== print " + resend.getText().toString());
                if (resend.getText().toString().equals("RESEND OTP")) {
                    resend();
                    counter = 90;
                    new CountDownTimer(90000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            resend.setText(String.valueOf(counter));
                            counter--;
                        }

                        @Override
                        public void onFinish() {
                            resend.setText(Html.fromHtml(st_resend));
                        }
                    }.start();
                }
            }
        });

        //cardOk.setCardBackgroundColor(Utils.get_color(Main_otpcheck.this));

        int maxLengthofEditText = 1;
        editTextOtp1.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLengthofEditText)});
        editTextOtp2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLengthofEditText)});
        editTextOtp3.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLengthofEditText)});
        editTextOtp4.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLengthofEditText)});
        editTextOtp5.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLengthofEditText)});
        editTextOtp6.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLengthofEditText)});

        cardOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otp = sharedPreference.getString(AcitivtyOtpVerification.this, "user_reg_otp");
                Utils.hideKeyboardFrom(AcitivtyOtpVerification.this, cardOk);
                int code = editTextOtp1.getText().toString().length()
                        + editTextOtp2.getText().toString().length()
                        + editTextOtp3.getText().toString().length()
                        + editTextOtp4.getText().toString().length()
                        + editTextOtp5.getText().toString().length()
                        + editTextOtp6.getText().toString().length();

                String password = "" + editTextOtp1.getText().toString()
                        + editTextOtp2.getText().toString()
                        + editTextOtp3.getText().toString()
                        + editTextOtp4.getText().toString()
                        + editTextOtp5.getText().toString()
                        + editTextOtp6.getText().toString();

                System.out.println("==code " + code);
                System.out.println("==password " + password);

                if (Utils.isNetworkAvailable(AcitivtyOtpVerification.this)) {
                    if (code == 6) {
                        if (otp.equals(password)) {

                            Toast.makeText(AcitivtyOtpVerification.this, "OTP Verified Successfully", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(AcitivtyOtpVerification.this, ActivityUserReg.class);
                            i.putExtra("from","reg");
                            finish();
                            startActivity(i);

                           /* if (!sharedPreference.getString(AcitivtyOtpVerification.this, "user_reg_status").equals("OTP sent for existing  user")){

                            } else {
                                sharedPreference.putString(AcitivtyOtpVerification.this, "user_reg_status", "User Registered Successfully");
                                Intent i = new Intent(AcitivtyOtpVerification.this, ActivityMandapamAdd.class);
                                i.putExtra("data_status", "not_from_edit");
                                i.putExtra("user_service_type_name", user_service_type_name);
                                finish();
                                startActivity(i);
                            }*/


                          /*  if (sharedPreference.getString(AcitivtyOtpVerification.this, "user_reg_status").equals("OTP sent to New User")) {
                                Intent i = new Intent(AcitivtyOtpVerification.this, ActivityUserReg.class);
                                finish();
                                startActivity(i);
                            } else {
                                Intent i = new Intent(AcitivtyOtpVerification.this, ActivityMandapamAdd.class);
                                finish();
                                startActivity(i);
                            }*/

                        } else {
                            Toast.makeText(AcitivtyOtpVerification.this, "சரியான OTP-ஐ கொடுக்கவும்", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(AcitivtyOtpVerification.this, "சரியான OTP-ஐ கொடுக்கவும", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(AcitivtyOtpVerification.this, "இணைய சேவையை சரிபார்க்கவும்...", Toast.LENGTH_SHORT).show();
                }
            }
        });

        editTextOtp1.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    editTextOtp1.clearFocus();
                    editTextOtp2.requestFocus();
                }

            }
        });

        editTextOtp2.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    editTextOtp2.clearFocus();
                    editTextOtp3.requestFocus();
                }

                if (s.length() == 0) {
                    editTextOtp1.requestFocus();
                    editTextOtp2.clearFocus();
                }

            }
        });

        editTextOtp3.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    editTextOtp3.clearFocus();
                    editTextOtp4.requestFocus();
                }

                if (s.length() == 0) {
                    editTextOtp2.requestFocus();
                    editTextOtp3.clearFocus();
                }

            }
        });

        editTextOtp4.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    editTextOtp4.clearFocus();
                    editTextOtp5.requestFocus();
                }

                if (s.length() == 0) {
                    editTextOtp3.requestFocus();
                    editTextOtp4.clearFocus();
                }

            }
        });

        editTextOtp5.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    editTextOtp5.clearFocus();
                    editTextOtp6.requestFocus();
                }

                if (s.length() == 0) {
                    editTextOtp4.requestFocus();
                    editTextOtp5.clearFocus();
                }

            }
        });

        editTextOtp6.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editTextOtp6.getWindowToken(), 0);
                    cardOk.setAlpha(1.0f);
                }

                if (s.length() == 0) {
                    editTextOtp5.requestFocus();
                    editTextOtp6.clearFocus();
                }

            }
        });
    }

    public void resend() {
        if (Utils.isNetworkAvailable(AcitivtyOtpVerification.this)) {
            new resendOtp().execute(
                    sharedPreference.getString(AcitivtyOtpVerification.this, "user_reg_mobile"),
                    sharedPreference.getString(AcitivtyOtpVerification.this, "user_reg_name"));
        } else {
            Utils.toast_normal(AcitivtyOtpVerification.this, "இணையதள சேவையை சரிபார்க்கவும் ");
        }

    }


    public class resendOtp extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            Utils.mProgress(AcitivtyOtpVerification.this, "ஏற்றுகிறது. காத்திருக்கவும் ", false).show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpHandler httpHandler = new HttpHandler();
            ArrayList<HashMap<String, String>> parms = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> postDataParams = new HashMap<String, String>();
            postDataParams.put("action", "check_user");
            postDataParams.put("mobileno", strings[0]);
            postDataParams.put("name", strings[1]);
            parms.add(postDataParams);
            String response = httpHandler.makeServiceCall(url, parms);
            System.out.println("response : " + response);
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            try {
                Utils.mProgress.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (result != null) {

                JSONArray jArray;
                JSONObject json_data = null;
                try {
                    jArray = new JSONArray(result);
                    System.out.println("Update===" + result);
                    json_data = jArray.getJSONObject(0);

                    sharedPreference.putString(AcitivtyOtpVerification.this, "user_reg_otp", json_data.getString("otp"));
                    sharedPreference.putString(AcitivtyOtpVerification.this, "user_reg_status", json_data.getString("status"));
                    sharedPreference.putString(AcitivtyOtpVerification.this, "user_reg_id", json_data.getString("userid"));
                    sharedPreference.putString(AcitivtyOtpVerification.this, "user_reg_mobile", json_data.getString("mobile"));
                    sharedPreference.putString(AcitivtyOtpVerification.this, "user_reg_name", json_data.getString("name"));
                    sharedPreference.putString(AcitivtyOtpVerification.this, "user_reg_district_name", json_data.getString("district_name"));
                    sharedPreference.putString(AcitivtyOtpVerification.this, "user_reg_city_name", json_data.getString("city_name"));
                    sharedPreference.putString(AcitivtyOtpVerification.this, "user_reg_district_id", json_data.getString("district"));
                    sharedPreference.putString(AcitivtyOtpVerification.this, "user_reg_city_id", json_data.getString("city"));


                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Update=== JSONException " + e);
                }
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Utils.toast_center(AcitivtyOtpVerification.this, "OTP has been resent");
                    editTextOtp1.setText("");
                    editTextOtp2.setText("");
                    editTextOtp3.setText("");
                    editTextOtp4.setText("");
                    editTextOtp5.setText("");
                    editTextOtp6.setText("");
                    editTextOtp1.clearFocus();
                    editTextOtp2.clearFocus();
                    editTextOtp3.clearFocus();
                    editTextOtp4.clearFocus();
                    editTextOtp5.clearFocus();
                    editTextOtp6.clearFocus();
                    editTextOtp1.requestFocus();
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }

}
