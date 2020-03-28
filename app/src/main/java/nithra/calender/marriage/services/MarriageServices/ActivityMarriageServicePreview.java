package nithra.calender.marriage.services.MarriageServices;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.Settings;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import nithra.calender.marriage.services.GlideApp;
import nithra.calender.marriage.services.HttpHandler;
import nithra.calender.marriage.services.R;
import nithra.calender.marriage.services.SharedPreference;
import nithra.calender.marriage.services.Utils;
import nithra.calender.marriage.services.image_slider.Activity_slider;
import nithra.calender.marriage.services.viewpagerindicator.CirclePageIndicator;


public class ActivityMarriageServicePreview extends AppCompatActivity {

    SharedPreference sharedPreference = new SharedPreference();
    ArrayList<HashMap<String, Object>> arrayListMandapam;
    WebView content_view;
    ViewPager mPager;
    LinearLayout main_lay;
    String Url_str = "";
    Toolbar mToolbar;
    Menu menu;
    CardView cardPay;
    Button buttonPay;
    String[] ImagesArray;

    String server_url = Utils.getAllDetails;
    String action_type = "";
    String click_from = "";
    String reportResult = "";
    String user_service_type_name = "";
    String list_id = "";
    String list_images = "";
    LinearLayout layout_text, layoutPlan, layoutEdit;
    TextView title;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_preview);

        Intent intent = getIntent();
        if (intent != null) {
            click_from = intent.getStringExtra("click_from");
            list_id = intent.getStringExtra("list_id");
            list_images = intent.getStringExtra("list_images");
            user_service_type_name = intent.getStringExtra("user_service_type_name");

        }

        System.out.println("intent : " + click_from);
        System.out.println("intent : " + list_id);
        System.out.println("intent : " + list_images);
        System.out.println("intent : " + user_service_type_name);

        mToolbar = findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if (user_service_type_name.equals("1")) {
            action_type = "get_mandapam_admin";
            getSupportActionBar().setTitle("மண்டப விவரங்கள்");
        }

        if (user_service_type_name.equals("2")) {
            action_type = "get_decoration_admin";
            getSupportActionBar().setTitle("டெக்கரேஷன் விவரங்கள்");
        }

        if (user_service_type_name.equals("3")) {
            action_type = "get_photography_admin";
            getSupportActionBar().setTitle("போட்டோகிராபி விவரங்கள்");
        }

        if (user_service_type_name.equals("4")) {
            action_type = "get_music_admin";
            getSupportActionBar().setTitle("இசை குழு விவரங்கள்");
        }

        if (user_service_type_name.equals("5")) {
            action_type = "get_parlour_admin";
            getSupportActionBar().setTitle("அழகு நிலைய விவரங்கள்");
        }

        if (user_service_type_name.equals("6")) {
            action_type = "get_catering_admin";
            getSupportActionBar().setTitle("கேட்டரிங் விவரங்கள்");
        }

        main_lay = findViewById(R.id.main_lay);
        content_view = findViewById(R.id.web);
        title = findViewById(R.id.title);
        layout_text = findViewById(R.id.layout_text);
        layoutPlan = findViewById(R.id.layoutPlan);
        layoutEdit = findViewById(R.id.layoutEdit);

        WebSettings ws = content_view.getSettings();
        ws.setJavaScriptEnabled(true);

        content_view.addJavascriptInterface(new WebAppInterface(ActivityMarriageServicePreview.this), "Android");
        content_view.setInitialScale(1);
        content_view.getSettings().setLoadWithOverviewMode(true);
        content_view.getSettings().setUseWideViewPort(true);
        content_view.getSettings().setJavaScriptEnabled(true);
        content_view.clearHistory();
        content_view.clearFormData();
        content_view.clearCache(true);
        WebSettings webSettings = content_view.getSettings();
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        arrayListMandapam = new ArrayList<HashMap<String, Object>>();

        mPager = findViewById(R.id.pager);
        CirclePageIndicator indicator = findViewById(R.id.indicator);

        ImagesArray = list_images.split(",");

        if (ImagesArray.length == 0) {
            ImagesArray[0] = "nooo";
        }

        mPager.setAdapter(new SlidingImage_Adapter(ActivityMarriageServicePreview.this, ImagesArray));
        indicator.setViewPager(mPager);
        float density = getResources().getDisplayMetrics().density;
        indicator.setRadius(5 * density);

        //String color = String.format("#%06X", (0xFFFFFF & Utils.get_color(ActivityMandapamPreview.this)));
        //Url_str = Utils.progi_share + sharedPreference.getString(ActivityMandapamPreview.this, "item_type_id") + "&color=" + color.substring(1);

        if (!click_from.equals("dataId")) {
            content_view.loadUrl(server_url + "" + list_id + "&color=EB226C&share=ok&service_type=" + user_service_type_name);
        } else {
            content_view.loadUrl(server_url + "" + list_id + "&color=EB226C&share=ok&service_type=" + user_service_type_name + "&usertype=admin");
        }

        content_view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });


        content_view.setWebViewClient(new WebViewClient() {

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                // Handle the error
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //view.loadUrl(url);
                System.out.println("web url " + url);
                if (url.contains("https://nithra.mobi/calender/services/thirumanam/api/viewplan.php")) {
                    Intent i = new Intent(ActivityMarriageServicePreview.this, ActivityPayment.class);
                    i.putExtra("paymentUrl", "" + url);
                    startActivity(i);

                } else {
                    try {
                        Utils.custom_tabs(ActivityMarriageServicePreview.this, url);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                try {
                    Utils.mProgress(ActivityMarriageServicePreview.this, "ஏற்றுகிறது. காத்திருக்கவும் ", false).show();
                } catch (Exception e) {

                }
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                try {
                    Utils.mProgress.dismiss();
                } catch (Exception e) {

                }
                super.onPageFinished(view, url);
            }
        });

        title.setText("நீங்கள் பதிவு செய்த தகவல்கள் அனைத்தும் சரி எனில் Choose Plan தேர்ந்தெடுக்கவும். தவறு எனில் திருத்து தேர்ந்தெடுக்கவும்..");

        layoutPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = Utils.pay_url + sharedPreference.getString(ActivityMarriageServicePreview.this, "user_reg_id") + "&st=" + user_service_type_name + "&sid=" + list_id;

                if (Utils.isNetworkAvailable(ActivityMarriageServicePreview.this)) {
                    Intent i = new Intent(ActivityMarriageServicePreview.this, ActivityPayment.class);
                    i.putExtra("paymentUrl", "" + url);
                    finish();
                    startActivity(i);

                } else {
                    Utils.toast_center(ActivityMarriageServicePreview.this, "இணைய சேவையை சரிபார்க்கவும்...");
                }


            }
        });

        layoutEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog no_datefun = new Dialog(ActivityMarriageServicePreview.this, android.R.style.Theme_DeviceDefault_Dialog_NoActionBar_MinWidth);
                no_datefun.setContentView(R.layout.nodate_dia);
                TextView btnSet = no_datefun.findViewById(R.id.text_no);
                TextView btnok = no_datefun.findViewById(R.id.text_yes);
                AppCompatTextView head_txt = no_datefun.findViewById(R.id.text_head);
                AppCompatTextView editText1 = no_datefun.findViewById(R.id.text_content);
                btnSet.setText("ஆம்");
                btnok.setText("இல்லை");
                head_txt.setVisibility(View.GONE);

                editText1.setText("உங்களின் விவரங்களை திருத்த விரும்புகிறீர்களா?");

                btnSet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new data_load_for_edit().execute(Utils.url_calender_service);
                        no_datefun.dismiss();

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
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolmenu_share, menu);
        MenuItem action_edit = menu.findItem(R.id.action_edit);
        MenuItem action_share = menu.findItem(R.id.action_share);

        if (!click_from.equals("dataId")) {
            action_edit.setVisible(true);
            action_edit.setIcon(R.drawable.ic_info_outline_black_24dp);
            action_share.setVisible(true);
            action_share.setIcon(R.drawable.icon_share);
        } else {
            action_edit.setVisible(true);
            action_edit.setIcon(R.drawable.ic_edit_black_24dp);
            action_share.setVisible(true);
            action_share.setIcon(R.drawable.icon_call);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }

        if (menuItem.getItemId() == R.id.action_share) {
            //sharePermissionFun();
            if (!click_from.equals("dataId")) {
                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
            } else {
                final Dialog no_datefun = new Dialog(ActivityMarriageServicePreview.this, android.R.style.Theme_DeviceDefault_Dialog_NoActionBar_MinWidth);
                no_datefun.setContentView(R.layout.nodate_dia);
                no_datefun.setCanceledOnTouchOutside(false);
                no_datefun.setCancelable(false);
                TextView btnSet = no_datefun.findViewById(R.id.text_no);
                TextView btnok = no_datefun.findViewById(R.id.text_yes);
                AppCompatTextView head_txt = no_datefun.findViewById(R.id.text_head);
                AppCompatTextView editText1 = no_datefun.findViewById(R.id.text_content);
                btnSet.setText("அழைக்க");
                btnok.setText("வெளியேற");
                head_txt.setText("சேவை மையம்");

                String call = "ஏதேனும் சந்தேகம் இருப்பின் <font color=blue><u>9597215816</u></font> என்ற எண்ணிற்கு எங்களை தொடர்பு கொள்ளவும். (திங்கள் - வெள்ளி  10:00 AM - 05:00 PM)";

                editText1.setText(Html.fromHtml(call));

                btnSet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + "9597215816"));
                        startActivity(intent);
                        //no_datefun.dismiss();

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
        }

        if (menuItem.getItemId() == R.id.action_edit) {

            if (!click_from.equals("dataId")) {

                final Dialog report = new Dialog(ActivityMarriageServicePreview.this);
                report.setContentView(R.layout.layout_report);
                ImageView close_btn = report.findViewById(R.id.close_btn);
                EditText editTextOthers = report.findViewById(R.id.editTextOthers);
                Button card_report_submit = report.findViewById(R.id.cardSubmit);
                RadioGroup radioGroupReport = report.findViewById(R.id.radioReport);
                LinearLayout layoutOthers = report.findViewById(R.id.layoutOthers);
                LinearLayout layoutOthersLine = report.findViewById(R.id.layoutOthersLine);
                radioGroupReport.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                        Utils.hideKeyboardFrom(ActivityMarriageServicePreview.this, radioGroup);
                        RadioButton radioGroupReportButton = radioGroup.findViewById(checkedId);
                        boolean isChecked = radioGroupReportButton.isChecked();

                        if (isChecked) {

                            if (radioGroupReportButton.getId() == R.id.radioReport3) {
                                layoutOthers.setVisibility(View.VISIBLE);
                                layoutOthersLine.setVisibility(View.VISIBLE);
                                reportResult = "" + editTextOthers.getText().toString().trim();
                            }

                            if (radioGroupReportButton.getId() == R.id.radioReport1) {
                                layoutOthers.setVisibility(View.GONE);
                                layoutOthersLine.setVisibility(View.GONE);
                                reportResult = "" + radioGroupReportButton.getText().toString().trim();
                            }

                            if (radioGroupReportButton.getId() == R.id.radioReport2) {
                                layoutOthers.setVisibility(View.GONE);
                                layoutOthersLine.setVisibility(View.GONE);
                                reportResult = "" + radioGroupReportButton.getText().toString().trim();
                            }
                        }
                    }
                });

                card_report_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (!Utils.isNetworkAvailable(ActivityMarriageServicePreview.this)) {
                            Toast.makeText(ActivityMarriageServicePreview.this, "இணைய சேவையை சரிபார்க்கவும்...", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (layoutOthers.getVisibility() == View.VISIBLE) {

                            if (editTextOthers.getText().toString().trim().length() == 0) {
                                Toast.makeText(ActivityMarriageServicePreview.this, "உங்கள் கருத்தை தட்டச்சு செய்து அனுப்பவும்,நன்றி", Toast.LENGTH_SHORT).show();
                                return;
                            } else {
                                reportResult = "" + editTextOthers.getText().toString().trim();
                            }
                        }

                        if (reportResult.length() == 0) {
                            Toast.makeText(ActivityMarriageServicePreview.this, "உங்கள் கருத்தை தட்டச்சு செய்து அனுப்பவும்,நன்றி", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        //Toast.makeText(ActivityMandapamPreview.this, "" + reportResult, Toast.LENGTH_SHORT).show();

                        new sent_feedback().execute(reportResult);

                        report.dismiss();
                    }
                });

                close_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        report.dismiss();
                    }
                });
                report.show();

            } else {
                final Dialog no_datefun = new Dialog(ActivityMarriageServicePreview.this, android.R.style.Theme_DeviceDefault_Dialog_NoActionBar_MinWidth);
                no_datefun.setContentView(R.layout.nodate_dia);
                TextView btnSet = no_datefun.findViewById(R.id.text_no);
                TextView btnok = no_datefun.findViewById(R.id.text_yes);
                AppCompatTextView head_txt = no_datefun.findViewById(R.id.text_head);
                AppCompatTextView editText1 = no_datefun.findViewById(R.id.text_content);
                btnSet.setText("ஆம்");
                btnok.setText("இல்லை");
                head_txt.setVisibility(View.GONE);

                editText1.setText("உங்களின் விவரங்களை திருத்த விரும்புகிறீர்களா?");

                btnSet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new data_load_for_edit().execute(Utils.url_calender_service);
                        no_datefun.dismiss();

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
        }

        return super.onOptionsItemSelected(menuItem);
    }

    private class data_load_for_edit extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Utils.mProgress(ActivityMarriageServicePreview.this, "ஏற்றுகிறது. காத்திருக்கவும் ", false).show();
        }

        @Override
        protected String doInBackground(String... strr) {
            System.out.println("====edit data user_id " + sharedPreference.getString(ActivityMarriageServicePreview.this, "user_reg_id"));
            System.out.println("====edit data item_id " + list_id);
            HttpHandler sh = new HttpHandler();
            ArrayList<HashMap<String, String>> parms = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> postDataParams = new HashMap<String, String>();
            postDataParams.put("action", "" + action_type);
            postDataParams.put("userid", "" + sharedPreference.getString(ActivityMarriageServicePreview.this, "user_reg_id"));
            postDataParams.put("sid", "" + list_id);
            parms.add(postDataParams);
            String result = sh.makeServiceCall(strr[0], parms);

            Log.i("Edit Data Response", "response : " + result);
            return "" + result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            JSONArray jArray = null;

            System.out.println(" date_load : Update===" + result);
            try {

                jArray = new JSONArray(result);

                JSONObject json_data = null;

                arrayListMandapam.clear();
                if (jArray.length() > 0) {
                    String strr = result.replaceAll("\"", "");
                    if (strr.contains("status:No Data")) {
                        System.out.println(" date_load : Update=== no ");

                    } else {
                        System.out.println(" date_load : Update=== yes");
                        for (int i = 0; i < jArray.length(); i++) {
                            json_data = jArray.getJSONObject(i);
                            sharedPreference.putString(ActivityMarriageServicePreview.this, sharedPreference.getString(ActivityMarriageServicePreview.this, "service_name") + "id", json_data.getString("id"));
                            sharedPreference.putString(ActivityMarriageServicePreview.this, sharedPreference.getString(ActivityMarriageServicePreview.this, "service_name") + "org_name", json_data.getString("org_name"));
                            sharedPreference.putString(ActivityMarriageServicePreview.this, sharedPreference.getString(ActivityMarriageServicePreview.this, "service_name") + "incharger_name", json_data.getString("incharge_name"));
                            sharedPreference.putString(ActivityMarriageServicePreview.this, sharedPreference.getString(ActivityMarriageServicePreview.this, "service_name") + "phone_no", json_data.getString("phone_no"));
                            sharedPreference.putString(ActivityMarriageServicePreview.this, sharedPreference.getString(ActivityMarriageServicePreview.this, "service_name") + "alternative_mno", json_data.getString("alternative_mno"));
                            sharedPreference.putString(ActivityMarriageServicePreview.this, sharedPreference.getString(ActivityMarriageServicePreview.this, "service_name") + "address", json_data.getString("address"));
                            sharedPreference.putString(ActivityMarriageServicePreview.this, sharedPreference.getString(ActivityMarriageServicePreview.this, "service_name") + "district_id", json_data.getString("district"));
                            sharedPreference.putString(ActivityMarriageServicePreview.this, sharedPreference.getString(ActivityMarriageServicePreview.this, "service_name") + "city_id", json_data.getString("city"));
                            sharedPreference.putString(ActivityMarriageServicePreview.this, sharedPreference.getString(ActivityMarriageServicePreview.this, "service_name") + "district_name", json_data.getString("district_name"));
                            sharedPreference.putString(ActivityMarriageServicePreview.this, sharedPreference.getString(ActivityMarriageServicePreview.this, "service_name") + "city_name", json_data.getString("city_name"));
                            sharedPreference.putString(ActivityMarriageServicePreview.this, sharedPreference.getString(ActivityMarriageServicePreview.this, "service_name") + "pincode", json_data.getString("pincode"));
                            sharedPreference.putString(ActivityMarriageServicePreview.this, sharedPreference.getString(ActivityMarriageServicePreview.this, "service_name") + "landmark", json_data.getString("landmark"));
                            sharedPreference.putString(ActivityMarriageServicePreview.this, sharedPreference.getString(ActivityMarriageServicePreview.this, "service_name") + "images", json_data.getString("images"));
                            sharedPreference.putString(ActivityMarriageServicePreview.this, sharedPreference.getString(ActivityMarriageServicePreview.this, "service_name") + "insta_url", json_data.getString("insta_url"));
                            sharedPreference.putString(ActivityMarriageServicePreview.this, sharedPreference.getString(ActivityMarriageServicePreview.this, "service_name") + "fb_url", json_data.getString("fb_url"));
                            sharedPreference.putString(ActivityMarriageServicePreview.this, sharedPreference.getString(ActivityMarriageServicePreview.this, "service_name") + "youtube_url", json_data.getString("youtube_url"));
                            sharedPreference.putString(ActivityMarriageServicePreview.this, sharedPreference.getString(ActivityMarriageServicePreview.this, "service_name") + "website", json_data.getString("website"));
                            sharedPreference.putString(ActivityMarriageServicePreview.this, sharedPreference.getString(ActivityMarriageServicePreview.this, "service_name") + "others", json_data.getString("others"));
                            sharedPreference.putString(ActivityMarriageServicePreview.this, sharedPreference.getString(ActivityMarriageServicePreview.this, "service_name") + "is_active", json_data.getString("is_active"));

                            if (user_service_type_name.equals("1")) {
                                sharedPreference.putString(ActivityMarriageServicePreview.this, sharedPreference.getString(ActivityMarriageServicePreview.this, "service_name") + "google_map_url", json_data.getString("google_map_url"));
                                sharedPreference.putString(ActivityMarriageServicePreview.this, sharedPreference.getString(ActivityMarriageServicePreview.this, "service_name") + "type", json_data.getString("hall_type"));
                                sharedPreference.putString(ActivityMarriageServicePreview.this, sharedPreference.getString(ActivityMarriageServicePreview.this, "service_name") + "rent_type", json_data.getString("hall_rent_type"));
                                sharedPreference.putString(ActivityMarriageServicePreview.this, sharedPreference.getString(ActivityMarriageServicePreview.this, "service_name") + "event_name", json_data.getString("hall_event_name"));
                                sharedPreference.putString(ActivityMarriageServicePreview.this, sharedPreference.getString(ActivityMarriageServicePreview.this, "service_name") + "dining_hall_type", json_data.getString("dining_hall_type"));
                                sharedPreference.putString(ActivityMarriageServicePreview.this, sharedPreference.getString(ActivityMarriageServicePreview.this, "service_name") + "car_parking", json_data.getString("car_parking"));
                                sharedPreference.putString(ActivityMarriageServicePreview.this, sharedPreference.getString(ActivityMarriageServicePreview.this, "service_name") + "car_parking_count", json_data.getString("car_parking_count"));
                                sharedPreference.putString(ActivityMarriageServicePreview.this, sharedPreference.getString(ActivityMarriageServicePreview.this, "service_name") + "room", json_data.getString("room"));
                                sharedPreference.putString(ActivityMarriageServicePreview.this, sharedPreference.getString(ActivityMarriageServicePreview.this, "service_name") + "room_count", json_data.getString("room_count"));
                                sharedPreference.putString(ActivityMarriageServicePreview.this, sharedPreference.getString(ActivityMarriageServicePreview.this, "service_name") + "generator", json_data.getString("generator"));
                                sharedPreference.putString(ActivityMarriageServicePreview.this, sharedPreference.getString(ActivityMarriageServicePreview.this, "service_name") + "audio", json_data.getString("audio"));
                                sharedPreference.putString(ActivityMarriageServicePreview.this, sharedPreference.getString(ActivityMarriageServicePreview.this, "service_name") + "latitude", json_data.getString("latitude"));
                                sharedPreference.putString(ActivityMarriageServicePreview.this, sharedPreference.getString(ActivityMarriageServicePreview.this, "service_name") + "longitude", json_data.getString("longitude"));
                            }

                            if (user_service_type_name.equals("2")) {
                                sharedPreference.putString(ActivityMarriageServicePreview.this, sharedPreference.getString(ActivityMarriageServicePreview.this, "service_name") + "google_map_url", json_data.getString("google_map_url"));
                                sharedPreference.putString(ActivityMarriageServicePreview.this, sharedPreference.getString(ActivityMarriageServicePreview.this, "service_name") + "email_id", json_data.getString("email_id"));
                                sharedPreference.putString(ActivityMarriageServicePreview.this, sharedPreference.getString(ActivityMarriageServicePreview.this, "service_name") + "experince", json_data.getString("exp"));
                                sharedPreference.putString(ActivityMarriageServicePreview.this, sharedPreference.getString(ActivityMarriageServicePreview.this, "service_name") + "type", json_data.getString("dec_type"));
                                sharedPreference.putString(ActivityMarriageServicePreview.this, sharedPreference.getString(ActivityMarriageServicePreview.this, "service_name") + "event_name", json_data.getString("dec_event_name"));
                                sharedPreference.putString(ActivityMarriageServicePreview.this, sharedPreference.getString(ActivityMarriageServicePreview.this, "service_name") + "service_plan", json_data.getString("service_plan"));
                                sharedPreference.putString(ActivityMarriageServicePreview.this, sharedPreference.getString(ActivityMarriageServicePreview.this, "service_name") + "service_plan_name", json_data.getString("service_plan_name"));
                            }

                            if (user_service_type_name.equals("3")) {
                                sharedPreference.putString(ActivityMarriageServicePreview.this, sharedPreference.getString(ActivityMarriageServicePreview.this, "service_name") + "email_id", json_data.getString("email_id"));
                                sharedPreference.putString(ActivityMarriageServicePreview.this, sharedPreference.getString(ActivityMarriageServicePreview.this, "service_name") + "experince", json_data.getString("exp"));
                                sharedPreference.putString(ActivityMarriageServicePreview.this, sharedPreference.getString(ActivityMarriageServicePreview.this, "service_name") + "type", json_data.getString("features"));
                                sharedPreference.putString(ActivityMarriageServicePreview.this, sharedPreference.getString(ActivityMarriageServicePreview.this, "service_name") + "camera_features", json_data.getString("camera_features"));
                                sharedPreference.putString(ActivityMarriageServicePreview.this, sharedPreference.getString(ActivityMarriageServicePreview.this, "service_name") + "event_name", json_data.getString("event_name"));
                            }

                            if (user_service_type_name.equals("4")) {
                                sharedPreference.putString(ActivityMarriageServicePreview.this, sharedPreference.getString(ActivityMarriageServicePreview.this, "service_name") + "email_id", json_data.getString("email_id"));
                                sharedPreference.putString(ActivityMarriageServicePreview.this, sharedPreference.getString(ActivityMarriageServicePreview.this, "service_name") + "experince", json_data.getString("exp"));
                                sharedPreference.putString(ActivityMarriageServicePreview.this, sharedPreference.getString(ActivityMarriageServicePreview.this, "service_name") + "type", json_data.getString("music_type"));
                                sharedPreference.putString(ActivityMarriageServicePreview.this, sharedPreference.getString(ActivityMarriageServicePreview.this, "service_name") + "event_name", json_data.getString("event_name"));
                            }

                            if (user_service_type_name.equals("5")) {
                                sharedPreference.putString(ActivityMarriageServicePreview.this, sharedPreference.getString(ActivityMarriageServicePreview.this, "service_name") + "email_id", json_data.getString("email_id"));
                                sharedPreference.putString(ActivityMarriageServicePreview.this, sharedPreference.getString(ActivityMarriageServicePreview.this, "service_name") + "experince", json_data.getString("exp"));
                                sharedPreference.putString(ActivityMarriageServicePreview.this, sharedPreference.getString(ActivityMarriageServicePreview.this, "service_name") + "type", json_data.getString("features_type"));
                                sharedPreference.putString(ActivityMarriageServicePreview.this, sharedPreference.getString(ActivityMarriageServicePreview.this, "service_name") + "event_name", json_data.getString("event_name"));
                                sharedPreference.putString(ActivityMarriageServicePreview.this, sharedPreference.getString(ActivityMarriageServicePreview.this, "service_name") + "parlour_gender_type", json_data.getString("parlour_for"));
                            }

                            if (user_service_type_name.equals("6")) {
                                sharedPreference.putString(ActivityMarriageServicePreview.this, sharedPreference.getString(ActivityMarriageServicePreview.this, "service_name") + "email_id", json_data.getString("email_id"));
                                sharedPreference.putString(ActivityMarriageServicePreview.this, sharedPreference.getString(ActivityMarriageServicePreview.this, "service_name") + "experince", json_data.getString("exp"));
                                sharedPreference.putString(ActivityMarriageServicePreview.this, sharedPreference.getString(ActivityMarriageServicePreview.this, "service_name") + "type", json_data.getString("other_features"));
                                sharedPreference.putString(ActivityMarriageServicePreview.this, sharedPreference.getString(ActivityMarriageServicePreview.this, "service_name") + "event_name", json_data.getString("event_name"));
                                sharedPreference.putString(ActivityMarriageServicePreview.this, sharedPreference.getString(ActivityMarriageServicePreview.this, "service_name") + "samayal_type", json_data.getString("samayal_type"));
                                sharedPreference.putString(ActivityMarriageServicePreview.this, sharedPreference.getString(ActivityMarriageServicePreview.this, "service_name") + "camera_features", json_data.getString("cooking_count"));
                            }


                        }
                    }
                }

            } catch (JSONException e1) {
                System.out.println("EXJSONException===" + e1);
            }

            try {
                Utils.mProgress.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (sharedPreference.getString(ActivityMarriageServicePreview.this, sharedPreference.getString(ActivityMarriageServicePreview.this, "service_name") + "is_active").equals("1")) {
                Intent i = new Intent(ActivityMarriageServicePreview.this, ActivityMarriageServiceAdd.class);
                i.putExtra("data_status", "from_edit");
                i.putExtra("user_service_type_name", "" + user_service_type_name);
                finish();
                startActivity(i);
            } else {
                //Toast.makeText(ActivityMandapamPreview.this, "blocked user", Toast.LENGTH_SHORT).show();
                dialog_warning();
            }


        }
    }

    public void dialog_warning() {
        final Dialog rating_dialog = new Dialog(ActivityMarriageServicePreview.this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
        rating_dialog.setContentView(R.layout.info_dia);
        rating_dialog.setCancelable(false);
        AppCompatButton btnSend = rating_dialog.findViewById(R.id.btnSend);
        AppCompatTextView textt = rating_dialog.findViewById(R.id.textt);
        AppCompatTextView textView1 = rating_dialog.findViewById(R.id.textView1);
        CardView ok_card = rating_dialog.findViewById(R.id.ok_card);
        btnSend.setText("சரி");
        ok_card.setCardBackgroundColor(Utils.get_color(ActivityMarriageServicePreview.this));
        textView1.setBackgroundColor(Utils.get_color(ActivityMarriageServicePreview.this));
        textt.setText("தங்களின் கணக்கு தற்காலிகமாக முடக்கப்பட்டுள்ளது. ஏதேனும் சந்தேகம் இருப்பின் 9597215816 என்ற எண்ணிற்கு எங்களை தொடர்பு கொள்ளவும். (திங்கள் - வெள்ளி  10:00 AM - 05:00 PM)");
        textt.setGravity(Gravity.CENTER);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rating_dialog.dismiss();
            }
        });
        rating_dialog.show();
    }


    public void sharePermissionFun() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                final Dialog dialog = new Dialog(ActivityMarriageServicePreview.this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
                dialog.setContentView(R.layout.permission_dialog_layout);
                TextView txtfd = dialog.findViewById(R.id.txtfd);
                txtfd.setBackgroundColor(Utils.get_color(ActivityMarriageServicePreview.this));
                TextView ok = dialog.findViewById(R.id.permission_ok);
                TextView txt = dialog.findViewById(R.id.txt);
                if (sharedPreference.getInt(ActivityMarriageServicePreview.this, "permission") == 2) {
                    txt.setText("இந்த தகவலை பகிர settings பகுதியில் உள்ள storage permission -யை allow செய்ய வேண்டும்");
                } else {
                    txt.setText("இந்த தகவலை பகிர பின்வரும் அனுமதிகளை (Permission) அனுமதிக்கவும்");
                }
                ok.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(View v) {
                        if (sharedPreference.getInt(ActivityMarriageServicePreview.this, "permission") == 2) {
                            Intent intent = new Intent();
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getApplicationContext().getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);
                            dialog.dismiss();
                        } else {
                            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 153);
                            dialog.dismiss();
                        }
                    }
                });
                dialog.show();
            } else {
                share_agee(main_lay);

            }
        } else {
            share_agee(main_lay);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 153: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sharedPreference.putInt(ActivityMarriageServicePreview.this, "permission", 1);
                    share_agee(main_lay);
                } else {
                    if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                        boolean showRationale = shouldShowRequestPermissionRationale(permissions[0]);
                        if (!showRationale) {
                            sharedPreference.putInt(ActivityMarriageServicePreview.this, "permission", 2);
                        } else if (android.Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(permissions[0])) {
                            sharedPreference.putInt(ActivityMarriageServicePreview.this, "permission", 0);
                        }
                    }
                }
            }
        }
    }


    public void share_agee(final LinearLayout Main_layout) {
        Utils.mProgress(ActivityMarriageServicePreview.this, "ஏற்றுகிறது. காத்திருக்கவும்  ...", false).show();

        Main_layout.postDelayed(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = Bitmap.createBitmap(Main_layout.getWidth(), Main_layout.getHeight(), Bitmap.Config.ARGB_8888);

                Canvas canvas = new Canvas(bitmap);
                Main_layout.draw(canvas);


                String root = Environment.getExternalStorageDirectory().toString();
                File mydir = new File(root + "/Nithra/Tamil Calendar");
                mydir.mkdirs();
                String fname = "image_mandapam_share.jpg";
                final File file = new File(mydir, fname);

                if (file.exists()) {
                    file.delete();
                }

                try {
                    FileOutputStream out = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                    out.flush();
                    out.close();

                    if (file.exists()) {
                        Uri uri = Uri.fromFile(file);
                        Intent share = new Intent();
                        share.setAction(Intent.ACTION_SEND);
                        share.setType("image/*");
                        share.putExtra(Intent.EXTRA_STREAM, uri);
                        share.putExtra(Intent.EXTRA_SUBJECT,
                                "நித்ரா தமிழ் நாட்காட்டி");

                        String str = "மண்டபத்தின்";
                       /* if (sharedPreference.getString(ActivityMandapamPreview.this, "progithar_type").equals("ஐயர்")) {
                            str = "ஐயர் / புரோகிதர் பற்றிய";
                        } else if (sharedPreference.getString(ActivityMandapamPreview.this, "progithar_type").equals("ஜோதிடர்")) {
                            str = "ஜோதிடர் பற்றிய";
                        } else if (sharedPreference.getString(ActivityMandapamPreview.this, "progithar_type").equals("வாஸ்து நிபுணர்")) {
                            str = "வாஸ்து நிபுணர் பற்றிய";
                        }*/
                        share.putExtra(
                                Intent.EXTRA_TEXT,
                                "நித்ரா தமிழ் நாட்காட்டி வழியாக பகிரப்பட்ட " + str + " தகவல். ஆன்ட்ராய்டு மொபைலில் தரவிறக்கம் செய்ய :  http://bit.ly/2tbwcsn\n" +
                                        "\n" +
                                        "ஆப்பிள் மொபைலில் தரவிறக்கம் செய்ய : http://bit.ly/iostamilcal\n" +
                                        "\n" +
                                        "மேலும் விவரங்களுக்கு : " + Url_str);
                        startActivity(Intent.createChooser(share, "Share"));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("share error " + e.getMessage());
                }
                Utils.mProgress.dismiss();
            }
        }, 10);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                finish();
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public class SlidingImage_Adapter extends PagerAdapter {


        private String[] IMAGES;
        private LayoutInflater inflater;
        private Context context;


        public SlidingImage_Adapter(Context context, String[] IMAGES) {
            this.context = context;
            this.IMAGES = IMAGES;
            inflater = LayoutInflater.from(context);


        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return IMAGES.length;
        }

        @Override
        public Object instantiateItem(ViewGroup view, final int position) {
            View imageLayout = inflater.inflate(R.layout.slidingimages_layout, view, false);

            assert imageLayout != null;
            final ImageView imageView = imageLayout
                    .findViewById(R.id.image);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Utils.isNetworkAvailable(context)) {
                        if (list_images.split(",")[0].length() > 5) {
                            sharedPreference.putString(ActivityMarriageServicePreview.this, "imageurl", list_images);
                            sharedPreference.putInt(ActivityMarriageServicePreview.this, "image_poss_val", position);
                            Intent i = new Intent(ActivityMarriageServicePreview.this, Activity_slider.class);
                            startActivity(i);
                        } else {
                            Utils.toast_center(ActivityMarriageServicePreview.this, "புகைப்படம் ஏதும் இல்லை");
                        }

                    } else {
                        Utils.toast_center(ActivityMarriageServicePreview.this, "இணையதள சேவையை சரிபார்க்கவும் ");
                    }

                }
            });

            int drawble = R.drawable.mandapamsample;
            if (sharedPreference.getInt(ActivityMarriageServicePreview.this, "activity_value") == 1) {
                GlideApp.with(context)
                        .load(IMAGES[position])
                        .placeholder(drawble)
                        .error(drawble)
                        .into(imageView);
            } else {
                GlideApp.with(context)
                        .load(IMAGES[position])
                        .placeholder(R.drawable.mandapamsample)
                        .error(R.drawable.mandapamsample)
                        .into(imageView);
            }
            view.addView(imageLayout, 0);


            return imageLayout;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }


    }

    private class WebAppInterface {
        Context context;

        public WebAppInterface(ActivityMarriageServicePreview ActivityPayment) {
            this.context = ActivityPayment;
        }

        /**
         * Show a toast from the web page
         * Payment Success,start_date,end_datePayment PendingInvalid Payment
         */
        @JavascriptInterface
        public void showToast(String history, String start_date, String end_date) {
            System.out.println("response : " + history + " - " + start_date + " - " + end_date);


            if (history.toLowerCase().equals("history")) {

                Intent i = new Intent(ActivityMarriageServicePreview.this, ActivityPaymentHistory.class);
                i.putExtra("user_service_item_id", "" + list_id);
                i.putExtra("user_service_type_id", "" + user_service_type_name);
                startActivity(i);

//                Toast.makeText(context, "" + history, Toast.LENGTH_SHORT).show();

            }

        }
    }

    private class sent_feedback extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Utils.mProgress(ActivityMandapamPreview.this, "ஏற்றுகிறது. காத்திருக்கவும் ", false).show();
        }

        @Override
        protected String doInBackground(String... strr) {

            HttpHandler sh = new HttpHandler();
            ArrayList<HashMap<String, String>> parms = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> postDataParams = new HashMap<String, String>();
            postDataParams.put("action", "add_feedback");
            postDataParams.put("userid", "" + sharedPreference.getString(ActivityMarriageServicePreview.this, "user_reg_id"));
            postDataParams.put("sid", "" + list_id);
            postDataParams.put("st", "" + user_service_type_name);
            postDataParams.put("feedback", "" + strr[0]);
            postDataParams.put("android_id", "" + Utils.getAndroidId(ActivityMarriageServicePreview.this));
            parms.add(postDataParams);
            String result = sh.makeServiceCall(Utils.url_calender_service, parms);

            Log.i("EVENT", "response : " + result);
            return "" + result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                JSONObject jsonObject = new JSONObject(result);
                System.out.println(" date_load : Update===" + jsonObject.getString("status"));
                if (jsonObject.getString("status").equals("Feedback Insert Successfully")) {

                    Toast.makeText(ActivityMarriageServicePreview.this, "உங்களின் கருத்து ஏற்கப்பட்டது, நன்றி", Toast.LENGTH_SHORT).show();

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            /*try {
                Utils.mProgress.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }*/
        }
    }
}