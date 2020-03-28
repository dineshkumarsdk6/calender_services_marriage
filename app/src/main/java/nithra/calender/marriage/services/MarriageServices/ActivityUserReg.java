package nithra.calender.marriage.services.MarriageServices;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nithra.calender.marriage.services.HttpHandler;
import nithra.calender.marriage.services.Pojo.City_Model;
import nithra.calender.marriage.services.R;
import nithra.calender.marriage.services.SharedPreference;
import nithra.calender.marriage.services.Utils;
import nithra.calender.marriage.services.searchdialog.ContactSearchDialogCompat;
import nithra.calender.marriage.services.searchdialog.core.BaseSearchDialogCompat;
import nithra.calender.marriage.services.searchdialog.core.SearchResultListener;

public class ActivityUserReg extends AppCompatActivity {
    CardView cardEnter;
    EditText editTextName, editTextMobile, editTextEmail, district_spinner, taluk_spinner;
    SharedPreference sharedPreference;
    String userId, userName, userMobile, userEmail, districtId, cityId;
    Toolbar mToolbar;
    int click_val = 0;
    ArrayList<City_Model> dist_item = new ArrayList<>();
    ArrayList<City_Model> city_models = new ArrayList<>();
    String user_service_type_name = "";
    String from = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_user_details);
        sharedPreference = new SharedPreference();
        cardEnter = findViewById(R.id.cardEnter);
        editTextName = findViewById(R.id.editTextName);
        editTextMobile = findViewById(R.id.editTextMobile);
        editTextEmail = findViewById(R.id.editTextEmail);
        district_spinner = findViewById(R.id.editTextDistrict);
        taluk_spinner = findViewById(R.id.editTextCity);

        Intent intent = getIntent();
        if (intent != null){
            from = intent.getStringExtra("from");
        }


        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setTitle("சுயவிவரம்");
        getSupportActionBar().setTitle("சுயவிவரம்");

        System.out.println("=== user status " + sharedPreference.getString(ActivityUserReg.this, "user_reg_status"));
        System.out.println("=== user id " + sharedPreference.getString(ActivityUserReg.this, "user_reg_id"));
        System.out.println("=== user name " + sharedPreference.getString(ActivityUserReg.this, "user_reg_name"));
        System.out.println("=== user mobile " + sharedPreference.getString(ActivityUserReg.this, "user_reg_mobile"));
        System.out.println("=== user email " + sharedPreference.getString(ActivityUserReg.this, "user_reg_email"));
        System.out.println("=== user district name " + sharedPreference.getString(ActivityUserReg.this, "user_reg_district_name"));
        System.out.println("=== user city name " + sharedPreference.getString(ActivityUserReg.this, "user_reg_city_name"));
        System.out.println("=== user district id " + sharedPreference.getString(ActivityUserReg.this, "user_reg_district_id"));
        System.out.println("=== user city id " + sharedPreference.getString(ActivityUserReg.this, "user_reg_city_id"));


        userId = sharedPreference.getString(ActivityUserReg.this, "user_reg_id");
        user_service_type_name = sharedPreference.getString(ActivityUserReg.this, "user_service_type_name");
        editTextName.setText("" + sharedPreference.getString(ActivityUserReg.this, "user_reg_name"));
        editTextMobile.setText("" + sharedPreference.getString(ActivityUserReg.this, "user_reg_mobile"));
        editTextEmail.setText("" + sharedPreference.getString(ActivityUserReg.this, "user_reg_email"));
        district_spinner.setText("" + sharedPreference.getString(ActivityUserReg.this, "user_reg_district_name"));
        taluk_spinner.setText("" + sharedPreference.getString(ActivityUserReg.this, "user_reg_city_name"));
        district_spinner.setTag("" + sharedPreference.getString(ActivityUserReg.this, "user_reg_district_id"));
        taluk_spinner.setTag("" + sharedPreference.getString(ActivityUserReg.this, "user_reg_city_id"));

        /*if (sharedPreference.getString(ActivityUserReg.this, "user_reg_status").contains("existing  user")) {
            district_spinner.setTag("" + sharedPreference.getString(ActivityUserReg.this, "user_reg_district_id"));
            taluk_spinner.setTag("" + sharedPreference.getString(ActivityUserReg.this, "user_reg_city_id"));
            district_spinner.setText("" + sharedPreference.getString(ActivityUserReg.this, "user_reg_district_name"));
            taluk_spinner.setText("" + sharedPreference.getString(ActivityUserReg.this, "user_reg_city_name"));
        }*/

        district_spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click_val = 0;
                if (Utils.isNetworkAvailable(ActivityUserReg.this) == false) {
                    Utils.toast_center(ActivityUserReg.this, "இணையதள சேவையை சரிபார்க்கவும் ");
                    return;
                }
                if (dist_item.size() != 0 && city_models.size() != 0) {
                    dist_list();
                } else {
                    new dist_load().execute();
                }
            }
        });

        taluk_spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click_val = 1;
                if (Utils.isNetworkAvailable(ActivityUserReg.this) == false) {
                    Utils.toast_center(ActivityUserReg.this, "இணையதள சேவையை சரிபார்க்கவும் ");
                    return;
                }
                if (district_spinner.getText().toString().length() != 0) {
                    if (dist_item.size() != 0) {
                        city_list();
                    } else {
                        new dist_load().execute();
                    }
                } else {
                    Utils.toast_center(ActivityUserReg.this, "மாவட்டம் தேர்வு செய்க");
                }
            }
        });

        cardEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (editTextName.getText().toString().length() == 0) {
                    Utils.toast_center(ActivityUserReg.this, "தங்களின் பெயரை உள்ளிடவும்");
                    return;
                }

                if (editTextMobile.getText().toString().length() == 0) {
                    Utils.toast_center(ActivityUserReg.this, "தங்களின் அலைபேசி எண்ணை உள்ளிடவும்");
                    return;
                }

                if (editTextEmail.getText().toString().trim().length() != 0) {

                    if (!EmailValidation(editTextEmail.getText().toString())) {
                        Utils.toast_center(ActivityUserReg.this, "மின்னஞ்சல் முகவரியை சரிப்பார்க்கவும்");
                        return;
                    }
                }

                if (district_spinner.getText().toString().length() == 0) {
                    Utils.toast_center(ActivityUserReg.this, "மாவட்டம் தேர்வு செய்க");
                    return;
                }

                if (taluk_spinner.getText().toString().length() == 0) {
                    Utils.toast_center(ActivityUserReg.this, "நகரத்தை தேர்வு செய்க ");
                    return;
                }

                if (Utils.isNetworkAvailable(ActivityUserReg.this)) {
                    userId = sharedPreference.getString(ActivityUserReg.this, "user_reg_id");
                    userName = editTextName.getText().toString();
                    userMobile = editTextMobile.getText().toString();
                    userEmail = editTextEmail.getText().toString();
                    new userReg().execute("register",
                            userId,
                            userName,
                            userMobile,
                            userEmail,
                            district_spinner.getTag().toString(),
                            taluk_spinner.getTag().toString());
                } else {
                    Utils.toast_normal(ActivityUserReg.this, "இணையதள சேவையை சரிபார்க்கவும் ");
                }

            }
        });
    }

    public class userReg extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            Utils.mProgress(ActivityUserReg.this, "ஏற்றுகிறது. காத்திருக்கவும் ", false).show();
        }

        @Override
        protected String doInBackground(String... strings) {

            System.out.println("response sent : " + strings[0]);
            System.out.println("response sent : " + strings[1]);
            System.out.println("response sent : " + strings[2]);
            System.out.println("response sent : " + strings[3]);
            System.out.println("response sent : " + strings[4]);
            System.out.println("response sent : " + strings[5]);
            System.out.println("response sent : " + strings[6]);


            HttpHandler httpHandler = new HttpHandler();
            ArrayList<HashMap<String, String>> parms = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> postDataParams = new HashMap<String, String>();
            postDataParams.put("action", "" + strings[0]);
            postDataParams.put("userid", "" + strings[1]);
            postDataParams.put("name", "" + strings[2]);
            postDataParams.put("mobileno", "" + strings[3]);
            postDataParams.put("email", "" + strings[4]);
            postDataParams.put("district", "" + strings[5]);
            postDataParams.put("city", "" + strings[6]);
            parms.add(postDataParams);
            String response = httpHandler.makeServiceCall(Utils.url_calender_service, parms);
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

                    System.out.println("===result " + json_data.getString("status"));
                    System.out.println("===result " + json_data.getString("userid"));
                    System.out.println("===result " + json_data.getString("name"));
                    System.out.println("===result " + json_data.getString("mobileno"));
                    System.out.println("===result " + json_data.getString("email"));
                    System.out.println("===result " + json_data.getString("district_name"));
                    System.out.println("===result " + json_data.getString("city_name"));
                    System.out.println("===result " + json_data.getString("district"));
                    System.out.println("===result " + json_data.getString("city"));



                    sharedPreference.putString(ActivityUserReg.this, "user_reg_status", json_data.getString("status"));
                    sharedPreference.putString(ActivityUserReg.this, "user_reg_id", json_data.getString("userid"));
                    sharedPreference.putString(ActivityUserReg.this, "user_reg_name", json_data.getString("name"));
                    sharedPreference.putString(ActivityUserReg.this, "user_reg_mobile", json_data.getString("mobileno"));
                    sharedPreference.putString(ActivityUserReg.this, "user_reg_email", json_data.getString("email"));
                    sharedPreference.putString(ActivityUserReg.this, "user_reg_district_name", json_data.getString("district_name"));
                    sharedPreference.putString(ActivityUserReg.this, "user_reg_city_name", json_data.getString("city_name"));
                    sharedPreference.putString(ActivityUserReg.this, "user_reg_district_id", json_data.getString("district"));
                    sharedPreference.putString(ActivityUserReg.this, "user_reg_city_id", json_data.getString("city"));


                    if (json_data.getString("status").equals("User Registered Successfully")) {

                        if(from.equals("reg")){

                            Toast.makeText(ActivityUserReg.this, "தங்களின் சுயவிவரம் சேர்க்கப்பட்டது", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(ActivityUserReg.this, ActivityMarriageServiceAdd.class);
                            i.putExtra("data_status", "not_from_edit");
                            i.putExtra("user_service_type_name", user_service_type_name);
                            finish();
                            startActivity(i);

                        } else {
                            Toast.makeText(ActivityUserReg.this, "தங்களின் சுயவிவரம் புதுப்பிக்கப்பட்டது", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                    } else {
                        Toast.makeText(ActivityUserReg.this, "" + json_data.getString("status"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("==result " + e.getMessage());
                }
            }
        }
    }

    private class dist_load extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Utils.mProgress(ActivityUserReg.this, "ஏற்றுகிறது. காத்திருக்கவும் ", false).show();
        }

        @Override
        protected String doInBackground(String... strr) {
            HttpHandler httpHandler = new HttpHandler();
            ArrayList<HashMap<String, String>> parms = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> temp = new HashMap<String, String>();
            temp.put("action", "getdistrict");
            parms.add(temp);
            String result = httpHandler.makeServiceCall(Utils.getDistrictCity, parms);
            System.out.println("==result " + result);
            return "" + result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            JSONArray jArray;

            try {

                dist_item.clear();

                jArray = new JSONArray(result);

                JSONObject json_data = null;

                for (int i = 0; i < jArray.length(); i++) {
                    json_data = jArray.getJSONObject(i);

                    City_Model movie = new City_Model(json_data.getString("id"),
                            json_data.getString("tamil"), json_data.getString("city"));
                    dist_item.add(movie);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                Utils.mProgress.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (click_val == 0) {
                if (dist_item.size() != 0) {
                    dist_list();
                } else {
                    new dist_load().execute();
                }
            } else {
                if (dist_item.size() != 0) {
                    city_list();
                } else {
                    new dist_load().execute();
                }
            }


        }


    }

    public void dist_list() {

        if (!dist_item.isEmpty()) {

            SearchResultListener<City_Model> mSearchResultListener = new SearchResultListener<City_Model>() {
                @Override
                public void onSelected(BaseSearchDialogCompat dialog, City_Model item, int position) {
                    district_spinner.setText("" + item.getTitle());
                    district_spinner.setTag("" + item.getId());
                    taluk_spinner.setText("");
                    taluk_spinner.setTag("");
                    districtId = item.getId();
                    cityId = "";
                    System.out.println("==result district " + item.getId());
                    System.out.println("==result district " + item.getTitle());
                    dialog.dismiss();
                    // dist_choose();
                }
            };

            ContactSearchDialogCompat contactSearchDialogCompat = new ContactSearchDialogCompat(ActivityUserReg.this,
                    "மாவட்டத்தை தேர்வு செய்க ", "மாவட்டத்தை தேட", null, dist_item, mSearchResultListener);

            contactSearchDialogCompat.show();
        }


    }

    public void city_list() {

        for (int j = 0; j < dist_item.size(); j++) {

            if (dist_item.get(j).getId().equals("" + district_spinner.getTag().toString())) {

                JSONArray jArray;

                try {

                    city_models.clear();

                    jArray = new JSONArray(dist_item.get(j).getDid());

                    JSONObject json_data = null;

                    for (int i = 0; i < jArray.length(); i++) {
                        json_data = jArray.getJSONObject(i);

                        City_Model movie = new City_Model(json_data.getString("id"),
                                json_data.getString("tamil"), json_data.getString("tamil"));
                        city_models.add(movie);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }


        if (!city_models.isEmpty()) {
            new ContactSearchDialogCompat<>(ActivityUserReg.this, "நகரத்தை தேர்வு செய்க ", "நகரத்தை தேட", null, city_models,
                    new SearchResultListener<City_Model>() {
                        @Override
                        public void onSelected(BaseSearchDialogCompat dialog, City_Model item, int position) {
                            taluk_spinner.setText("" + item.getTitle());
                            taluk_spinner.setTag("" + item.getId());
                            cityId = item.getId();
                            System.out.println("==result city " + item.getId());
                            System.out.println("==result city " + item.getTitle());
                            dialog.dismiss();
                            // dist_choose();

                        }
                    }).show();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public static boolean EmailValidation(String value) {
        String email = value.trim();
        Pattern pattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+");
        Matcher matcher = pattern.matcher(email);
        Log.e("email", "" + email + matcher.matches());
        return matcher.matches();
    }
}
