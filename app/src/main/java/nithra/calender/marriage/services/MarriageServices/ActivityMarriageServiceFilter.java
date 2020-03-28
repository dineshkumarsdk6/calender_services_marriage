package nithra.calender.marriage.services.MarriageServices;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.AppBarLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import nithra.calender.marriage.services.HttpHandler;
import nithra.calender.marriage.services.Pojo.City_Model;
import nithra.calender.marriage.services.Pojo.State;
import nithra.calender.marriage.services.R;
import nithra.calender.marriage.services.SharedPreference;
import nithra.calender.marriage.services.Utils;
import nithra.calender.marriage.services.searchdialog.ContactSearchDialogCompat;
import nithra.calender.marriage.services.searchdialog.core.BaseSearchDialogCompat;
import nithra.calender.marriage.services.searchdialog.core.SearchResultListener;

import static nithra.calender.marriage.services.MarriageServices.ActivityMarriageServiceMain.filter_values;
import static nithra.calender.marriage.services.MarriageServices.ActivityMarriageServiceMain.onload;

public class ActivityMarriageServiceFilter extends AppCompatActivity {

    SharedPreference sharedPreference;
    int click_val = 0;
    ArrayList<City_Model> dist_item = new ArrayList<>();
    ArrayList<City_Model> city_models = new ArrayList<>();
    ArrayList<State> avail_check_item = new ArrayList<>();
    Menu menu;
    String result = "";
    Toolbar toolbar;
    Button set_btn;
    AppCompatEditText district_spinner, taluk_spinner;
    LinearLayout flexboxLayoutCheckboxVila;
    String user_service_type_name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_filter);
        sharedPreference = new SharedPreference();
        AppBarLayout appbar = findViewById(R.id.appbar);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("வடிகட்டல்");
        getSupportActionBar().setTitle("வடிகட்டல்");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

       /* toolbar.setBackgroundColor(Utils.get_color(ActivityMandapamFilter.this));
        appbar.setBackgroundColor(Utils.get_color(ActivityMandapamFilter.this));*/

        flexboxLayoutCheckboxVila = findViewById(R.id.cate_lay_vila);
        district_spinner = findViewById(R.id.district_spinner);
        taluk_spinner = findViewById(R.id.taluk_spinner);
        set_btn = findViewById(R.id.set_btn);

        Intent intent = getIntent();
        if (intent != null) {
            user_service_type_name = intent.getStringExtra("user_service_type_name");
        } else {
            user_service_type_name = "";
        }


        if (filter_values.size() != 0) {

            if (filter_values.get(0).get("district_txt").toString().length() != 0) {
                district_spinner.setText("" + filter_values.get(0).get("district_txt").toString());
                district_spinner.setTag("" + filter_values.get(0).get("district").toString());
            }

            if (filter_values.get(0).get("city_txt").toString().length() != 0) {
                taluk_spinner.setText("" + filter_values.get(0).get("city_txt").toString());
                taluk_spinner.setTag("" + filter_values.get(0).get("city").toString());
            }
        }

        district_spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click_val = 0;
                if (Utils.isNetworkAvailable(ActivityMarriageServiceFilter.this) == false) {
                    Utils.toast_center(ActivityMarriageServiceFilter.this, "இணையதள சேவையை சரிபார்க்கவும் ");
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
                if (Utils.isNetworkAvailable(ActivityMarriageServiceFilter.this) == false) {
                    Utils.toast_center(ActivityMarriageServiceFilter.this, "இணையதள சேவையை சரிபார்க்கவும் ");
                    return;
                }
                if (district_spinner.getText().toString().length() != 0) {
                    if (dist_item.size() != 0) {
                        city_list();
                    } else {
                        new dist_load().execute();
                    }
                } else {
                    Utils.toast_center(ActivityMarriageServiceFilter.this, "மாவட்டத்தை தேர்வு செய்க");
                }
            }
        });

        set_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!Utils.isNetworkAvailable(ActivityMarriageServiceFilter.this)) {
                    Utils.toast_center(ActivityMarriageServiceFilter.this, "இணையதள சேவையை சரிபார்க்கவும் ");
                    return;
                }

                filter_values.clear();
                HashMap<String, Object> temp = new HashMap<String, Object>();
                if (district_spinner.getText().toString().trim().length() != 0) {
                    temp.put("district_txt", "" + district_spinner.getText().toString());
                    temp.put("district", "" + district_spinner.getTag().toString());
                    System.out.println("====== filter values " + district_spinner.getText().toString());
                    System.out.println("====== filter values " + district_spinner.getTag().toString());
                } else {
                    temp.put("district", "");
                    temp.put("district_txt", "");
                }

                if (taluk_spinner.getText().toString().trim().length() != 0) {
                    temp.put("city_txt", "" + taluk_spinner.getText().toString());
                    temp.put("city", "" + taluk_spinner.getTag().toString());
                    System.out.println("====== filter values " + taluk_spinner.getText().toString());
                    System.out.println("====== filter values " + taluk_spinner.getTag().toString());
                } else {
                    temp.put("city", "");
                    temp.put("city_txt", "");
                }

                String str_vila = "";

                for (int i = 0; i < avail_check_item.size(); i++) {
                    if (avail_check_item.get(i).isSelected() == true) {
                        if (str_vila.length() > 0) {
                            str_vila = str_vila + "," + avail_check_item.get(i).getId();
                        } else {
                            str_vila = "" + avail_check_item.get(i).getId();
                        }
                    }
                }

                temp.put("category", "" + str_vila);
                System.out.println("====== filter values " + str_vila);
                filter_values.add(temp);
                onload = 1;
                finish();
            }
        });

        new getCategory().execute();


    }

    public class getCategory extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            Utils.mProgress(ActivityMarriageServiceFilter.this, "ஏற்றுகிறது. காத்திருக்கவும் ", false).show();
        }

        @Override
        protected String doInBackground(String... params) {

            HttpHandler sh = new HttpHandler();
            ArrayList<HashMap<String, String>> parms = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> temp = new HashMap<String, String>();
            temp.put("action", "get_event");
            temp.put("st", "" + user_service_type_name);
            parms.add(temp);
            String response = sh.makeServiceCall(Utils.url_calender_service, parms);
            System.out.println("response : " + response);
            return response;
        }

        @Override
        protected void onPostExecute(final String _result) {
            // TODO Auto-generated method stub
            super.onPostExecute(_result);
            result = _result;
            if (result != null) {
                flexboxLayoutCheckboxVila.removeAllViews();
                System.out.println("Update===" + result);
                JSONArray jArray;
                JSONObject jsonObject = null;
                LayoutInflater inflater;
                LinearLayout view;
                try {

                    jArray = new JSONArray(result);
                    jsonObject = jArray.getJSONObject(0);

                    System.out.println("===result type " + jsonObject.getString("events"));
                    System.out.println("===result type " + jsonObject.getString("features_type"));

                    JSONArray jsonArrayVila = new JSONArray(jsonObject.getString("events"));
                    for (int vila_int = 0; vila_int < jsonArrayVila.length(); vila_int++) {
                        JSONObject json_data = jsonArrayVila.getJSONObject(vila_int);
                        System.out.println("Update=== events " + json_data.getString("id"));
                        System.out.println("Update=== events " + json_data.getString("event_name"));

                        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        view = (LinearLayout) inflater.inflate(R.layout.layout_checkbox, null);
                        AppCompatCheckBox checkBox = view.findViewById(R.id.checkbox);
                        checkBox.setId(Integer.parseInt(json_data.getString("id")));
                        checkBox.setTag("" + vila_int);
                        checkBox.setText("" + json_data.getString("event_name").trim());

                        State stateVO = new State();
                        stateVO.setTitle("" + json_data.getString("event_name"));
                        stateVO.setEng_tit("");
                        stateVO.setId(Integer.parseInt(json_data.getString("id")));

                        if (filter_values.size() != 0) {
                            stateVO.setSelected(available_for_edit("" + filter_values.get(0).get("category").toString(), json_data.getString("id")));
                            checkBox.setChecked(available_for_edit("" + filter_values.get(0).get("category").toString(), json_data.getString("id")));

                        }

                        avail_check_item.add(stateVO);

                        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                Utils.hideKeyboardFrom(ActivityMarriageServiceFilter.this, buttonView);
                                avail_check_item.get(Integer.parseInt(checkBox.getTag().toString())).setSelected(isChecked);
                            }
                        });

                        flexboxLayoutCheckboxVila.addView(view);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("JSONException === // " + e);
                }

            }

            try {
                Utils.mProgress.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public boolean available_for_edit(String strr, String strr2) {

        String arr[] = strr.split(",");

        for (int i = 0; i < arr.length; i++) {
            System.out.println("arr[" + i + "] = " + arr[i].trim());
            if (arr[i].equals("" + strr2)) {
                return true;
            }

        }

        return false;
    }

    public void remove_checkbox(String result) {
        if (result != null) {
            flexboxLayoutCheckboxVila.removeAllViews();
            System.out.println("Update===" + result);
            JSONArray jArray;
            JSONObject jsonObject = null;
            LayoutInflater inflater;
            LinearLayout view;
            try {

                jArray = new JSONArray(result);
                jsonObject = jArray.getJSONObject(0);

                System.out.println("===result type " + jsonObject.getString("events"));
                System.out.println("===result type " + jsonObject.getString("features_type"));

                JSONArray jsonArrayVila = new JSONArray(jsonObject.getString("events"));
                for (int vila_int = 0; vila_int < jsonArrayVila.length(); vila_int++) {
                    JSONObject json_data = jsonArrayVila.getJSONObject(vila_int);
                    System.out.println("Update=== events " + json_data.getString("id"));
                    System.out.println("Update=== events " + json_data.getString("event_name"));

                    inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    view = (LinearLayout) inflater.inflate(R.layout.layout_checkbox, null);
                    AppCompatCheckBox checkBox = view.findViewById(R.id.checkbox);
                    checkBox.setId(Integer.parseInt(json_data.getString("id")));
                    checkBox.setTag("" + vila_int);
                    checkBox.setText("" + json_data.getString("event_name").trim());

                    State stateVO = new State();
                    stateVO.setTitle("" + json_data.getString("event_name"));
                    stateVO.setEng_tit("");
                    stateVO.setId(Integer.parseInt(json_data.getString("id")));

                    avail_check_item.add(stateVO);

                    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            Utils.hideKeyboardFrom(ActivityMarriageServiceFilter.this, buttonView);
                            avail_check_item.get(Integer.parseInt(checkBox.getTag().toString())).setSelected(isChecked);
                        }
                    });

                    flexboxLayoutCheckboxVila.addView(view);

                }

            } catch (JSONException e) {
                e.printStackTrace();
                System.out.println("JSONException === // " + e);
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu _menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolmenu_dash, _menu);
        menu = _menu;
        MenuItem action_edit = _menu.findItem(R.id.action_dash);
        action_edit.setIcon(R.drawable.filter_remove);
        if (filter_values.size() != 0) {
            action_edit.setVisible(false);
            action_edit.setIcon(R.drawable.filter_remove);
        } else {
            action_edit.setVisible(false);
            action_edit.setIcon(R.drawable.filter);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        if (item.getItemId() == R.id.action_dash) {
            MenuItem action_edit = menu.findItem(R.id.action_dash);
            action_edit.setIcon(R.drawable.filter);
            action_edit.setVisible(false);
            filter_values.clear();
            clear_fun();
            onload = 1;
        }
        return super.onOptionsItemSelected(item);
    }

    public void clear_fun() {
        district_spinner.setText("");
        district_spinner.setTag("");

        taluk_spinner.setText("");
        taluk_spinner.setTag("");

        if (result.length() > 5) {
            remove_checkbox(result);
        }

    }

    private class dist_load extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Utils.mProgress(ActivityMarriageServiceFilter.this, "ஏற்றுகிறது. காத்திருக்கவும் ", false).show();
        }

        @Override
        protected String doInBackground(String... strr) {
            HttpHandler sh = new HttpHandler();
            ArrayList<HashMap<String, String>> parms = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> temp = new HashMap<String, String>();
            temp.put("action", "getdistrict");
            parms.add(temp);
            String result = sh.makeServiceCall(Utils.getDistrictCity, parms);
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
            new ContactSearchDialogCompat<>(ActivityMarriageServiceFilter.this, "மாவட்டத்தை தேர்வு செய்க ", "மாவட்டத்தை தேட", null, dist_item,
                    new SearchResultListener<City_Model>() {
                        @Override
                        public void onSelected(BaseSearchDialogCompat dialog, City_Model item, int position) {
                            district_spinner.setText("" + item.getTitle());
                            district_spinner.setTag("" + item.getId());
                            taluk_spinner.setText("");
                            taluk_spinner.setTag("");
                            dialog.dismiss();
                            Utils.hideKeyboardFrom(ActivityMarriageServiceFilter.this, district_spinner);
                        }
                    }).show();
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
            new ContactSearchDialogCompat<>(ActivityMarriageServiceFilter.this, "நகரத்தை தேர்வு செய்க ", "நகரத்தை தேட", null, city_models,
                    new SearchResultListener<City_Model>() {
                        @Override
                        public void onSelected(BaseSearchDialogCompat dialog, City_Model item, int position) {
                            taluk_spinner.setText("" + item.getTitle());
                            taluk_spinner.setTag("" + item.getId());
                            dialog.dismiss();
                            Utils.hideKeyboardFrom(ActivityMarriageServiceFilter.this, taluk_spinner);
                        }
                    }).show();
        }

    }

    @Override
    public void onBackPressed() {
       super.onBackPressed();
    }
}












