package nithra.calender.marriage.services.MarriageServices;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatTextView;
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

public class ActivityNumberReg extends AppCompatActivity {
    CardView cardEnter;
    EditText editTextName, editTextMobile;
    AppCompatCheckBox checkboxTerms;
    String url = "" + Utils.url_calender_service;
    SharedPreference sharedPreference;
    String userName, userMobile;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_mobile_verification);
        sharedPreference = new SharedPreference();
        cardEnter = findViewById(R.id.cardEnter);
        editTextName = findViewById(R.id.editTextName);
        editTextMobile = findViewById(R.id.editTextMobile);
        checkboxTerms = findViewById(R.id.checkboxTerms);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setTitle("Mobile Verification");
        getSupportActionBar().setTitle("Mobile Verification");

        cardEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (editTextName.getText().toString().length() == 0) {
                    Utils.toast_center(ActivityNumberReg.this, "தங்களின் பெயரை உள்ளிடவும்");
                    return;
                }

                if (editTextMobile.getText().toString().length() == 0) {
                    Utils.toast_center(ActivityNumberReg.this, "தங்களின் அலைபேசி எண்ணை உள்ளிடவும்");
                    return;
                }

                if (editTextMobile.getText().toString().length() < 10) {
                    Utils.toast_center(ActivityNumberReg.this, "தங்களின் அலைபேசி எண்ணை சரிபார்க்கவும்");
                    return;
                }

                if (!checkboxTerms.isChecked()) {
                    Utils.toast_center(ActivityNumberReg.this, "Please Accept Our Terms & Conditions");
                    return;
                }

                sent();

            }
        });

        editTextMobile.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void afterTextChanged(Editable s) {
                if (s.length() == 10) {

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editTextMobile.getWindowToken(), 0);

                }

            }
        });

        checkboxTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkboxTerms.setChecked(false);
                info_dia();
            }
        });

    }

    private void sent() {
        userName = editTextName.getText().toString();
        userMobile = editTextMobile.getText().toString();
        final Dialog no_datefun = new Dialog(ActivityNumberReg.this, android.R.style.Theme_DeviceDefault_Dialog_NoActionBar_MinWidth);
        no_datefun.setContentView(R.layout.nodate_dia);
        no_datefun.setCanceledOnTouchOutside(false);
        no_datefun.setCancelable(false);
        TextView btnSet = no_datefun.findViewById(R.id.text_no);
        TextView btnok = no_datefun.findViewById(R.id.text_yes);
        AppCompatTextView head_txt = no_datefun.findViewById(R.id.text_head);
        AppCompatTextView editText1 = no_datefun.findViewById(R.id.text_content);
        btnSet.setText("தொடர");
        btnok.setText("திருத்து");
        head_txt.setText("சேவை மையம்");
        head_txt.setVisibility(View.GONE);

        String call = "நீங்கள் பதிவு செய்த பெயர் : <font color=red><u>" + userName + "</u></font> மற்றும் அலைபேசி எண் : <font color=red><u>" + userMobile + "</u></font> சரி எனில்  <font color=blue>தொடர</font> கிளிக் செய்யவும், தவறு எனில் <font color=blue>திருத்து</font> கிளிக் செய்யவும்.";

        editText1.setText(Html.fromHtml(call));

        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isNetworkAvailable(ActivityNumberReg.this)) {
                    no_datefun.dismiss();
                    new userReg().execute();

                } else {
                    Utils.toast_normal(ActivityNumberReg.this, "இணையதள சேவையை சரிபார்க்கவும் ");
                    return;
                }

            }
        });

        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                no_datefun.dismiss();
            }
        });


        no_datefun.show();
    }

    public void info_dia() {
        final Dialog rating_dialog = new Dialog(ActivityNumberReg.this,
                android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
        rating_dialog.setContentView(R.layout.info_dia);
        AppCompatButton btnSend = rating_dialog.findViewById(R.id.btnSend);
        AppCompatTextView textt = rating_dialog.findViewById(R.id.textt);
        CardView cancel_card = rating_dialog.findViewById(R.id.cancel_card);
        CardView ok_card = rating_dialog.findViewById(R.id.ok_card);
        AppCompatTextView textView1 = rating_dialog.findViewById(R.id.textView1);

        final AppCompatCheckBox tc_chcek1 = rating_dialog.findViewById(R.id.tc_chcek);
        tc_chcek1.setVisibility(View.VISIBLE);

        cancel_card.setCardBackgroundColor(Utils.get_color(ActivityNumberReg.this));
        ok_card.setCardBackgroundColor(Utils.get_color(ActivityNumberReg.this));
        textView1.setBackgroundColor(Utils.get_color(ActivityNumberReg.this));

        textt.setText("1. இந்த பகுதியானது உங்கள் தகவலை காண்பிக்க மட்டுமே. இதனால் ஏற்படும் எந்தவொரு விவகாரங்களுக்கும் “நித்ரா நிறுவனம்” " +
                "எந்த வகையிலும் பொறுப்பேற்காது.\n" +
                "\n" +
                "\n" +
                "2. நீங்கள் பதிவிடும் தகவல் நீங்கள் தேர்வு செய்த மாவட்டத்தில் சீரற்ற வரிசையில் காண்பிக்கப்படும்.\n" +
                "\n" +
                "\n" +
                "3. இதில் பதிவிடும் உங்கள் தகவலை சரிபார்த்துவிட்டு பதிவிடவும். உங்கள் தகவலுக்கு நீங்கள் மட்டுமே முழு பொறுப்பாவீர்கள்.\n" +
                "\n" +
                "\n" +
                "4. ஏதேனும் தவறு ஏற்படும் பட்சத்தில் உங்கள் பதிவு எந்தவொரு முன்னறிவிப்புமின்றி நீக்கப்படும்.");


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rating_dialog.dismiss();
            }
        });

        rating_dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                checkboxTerms.setChecked(tc_chcek1.isChecked());
            }
        });


        if (!isFinishing())
            rating_dialog.show();
    }

    public class userReg extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            Utils.mProgress(ActivityNumberReg.this, "ஏற்றுகிறது. காத்திருக்கவும் ", false).show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpHandler httpHandler = new HttpHandler();
            ArrayList<HashMap<String, String>> parms = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> postDataParams = new HashMap<String, String>();
            postDataParams.put("action", "check_user");
            postDataParams.put("mobileno", userMobile);
            postDataParams.put("name", userName);
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

                    sharedPreference.putString(ActivityNumberReg.this, "user_reg_otp", json_data.getString("otp"));
                    sharedPreference.putString(ActivityNumberReg.this, "user_reg_status", json_data.getString("status"));
                    sharedPreference.putString(ActivityNumberReg.this, "user_reg_id", json_data.getString("userid"));
                    sharedPreference.putString(ActivityNumberReg.this, "user_reg_name", json_data.getString("name"));
                    sharedPreference.putString(ActivityNumberReg.this, "user_reg_mobile", json_data.getString("mobile"));
                    sharedPreference.putString(ActivityNumberReg.this, "user_reg_email", json_data.getString("email"));
                    sharedPreference.putString(ActivityNumberReg.this, "user_reg_district_name", json_data.getString("district_name"));
                    sharedPreference.putString(ActivityNumberReg.this, "user_reg_city_name", json_data.getString("city_name"));
                    sharedPreference.putString(ActivityNumberReg.this, "user_reg_district_id", json_data.getString("district"));
                    sharedPreference.putString(ActivityNumberReg.this, "user_reg_city_id", json_data.getString("city"));

                    Intent i = new Intent(ActivityNumberReg.this, AcitivtyOtpVerification.class);
                    finish();
                    startActivity(i);


                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("==Error " + e);
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
