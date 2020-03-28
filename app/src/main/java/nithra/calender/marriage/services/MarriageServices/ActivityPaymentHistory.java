package nithra.calender.marriage.services.MarriageServices;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import nithra.calender.marriage.services.HttpHandler;
import nithra.calender.marriage.services.R;
import nithra.calender.marriage.services.SharedPreference;
import nithra.calender.marriage.services.Utils;


public class ActivityPaymentHistory extends AppCompatActivity {

    int preLast;
    int Last;
    int preLast_id = 0;
    int end = 0, end1 = 0;
    int first_size = 0;
    int last_size = 0;
    int stop_load = 0;

    RelativeLayout empty_lay;
    ImageView empty_imgg;
    TextView empty_txttt;

    ListView listView;
    LayoutInflater inflater;
    SwipeRefreshLayout swipe_refresh_layout;
    View mProgressBarFooter;
    View footerView;
    ArrayList<HashMap<String, Object>> arrayListPaymentHistory;
    CustomListAdapter adapter;

    SharedPreference sharedPreference = new SharedPreference();

    String user_id = "", user_service_item_id = "", user_service_type_id = "";
    Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_payment_history);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Payment History");

        empty_lay = findViewById(R.id.empty_lay);
        empty_imgg = findViewById(R.id.empty_imgg);
        empty_txttt = findViewById(R.id.empty_txttt);
        listView = findViewById(R.id.list);
        swipe_refresh_layout = findViewById(R.id.swipe_refresh_layout);
        arrayListPaymentHistory = new ArrayList<HashMap<String, Object>>();
        adapter = new CustomListAdapter(ActivityPaymentHistory.this, arrayListPaymentHistory);

        Intent intent = getIntent();

        if (intent != null) {
            user_service_item_id = intent.getStringExtra("user_service_item_id");
            user_service_type_id = intent.getStringExtra("user_service_type_id");
        }

        user_id = sharedPreference.getString(ActivityPaymentHistory.this, "user_reg_id");

        System.out.println("==payment data " + user_id);
        System.out.println("==payment data " + user_service_item_id);
        System.out.println("==payment data " + user_service_type_id);


        swipe_refresh_layout.setColorSchemeResources(android.R.color.holo_green_light,
                android.R.color.holo_blue_dark,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        mProgressBarFooter = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.progress_bar_footer, null, false);

        footerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_layout, null, false);

        listView.setAdapter(adapter);
        listView.setDivider(new ColorDrawable(this.getResources().getColor(android.R.color.transparent)));

        swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                stop_load = 0;
                preLast_id = 0;
                preLast = 0;
                Last = 0;

                arrayListPaymentHistory.clear();
                listView.removeFooterView(mProgressBarFooter);
                listView.removeFooterView(footerView);
                listView.removeFooterView(footerView);
                adapter.notifyDataSetChanged();


                swipe_refresh_layout.setRefreshing(true);
                empty_lay.setVisibility(View.GONE);


                new date_load().execute();


            }
        });

        on_load();
    }

    public void on_load() {
        preLast_id = 0;
        preLast = 0;
        Last = 0;
        arrayListPaymentHistory.clear();
        listView.removeFooterView(mProgressBarFooter);
        listView.removeFooterView(footerView);
        listView.removeFooterView(footerView);
        adapter.notifyDataSetChanged();
        swipe_refresh_layout.setRefreshing(true);

        empty_lay.setVisibility(View.GONE);


        new date_load().execute();
    }

    private class date_load extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Utils.mProgress(ActivityPaymentHistory.this, "ஏற்றுகிறது. காத்திருக்கவும் ", false).show();
        }

        @Override
        protected String doInBackground(String... strr) {

            System.out.println("==== sent " + user_id);
            System.out.println("==== sent " + preLast_id);
            System.out.println("==== sent " + user_service_item_id);
            System.out.println("==== sent " + user_service_type_id);

            HttpHandler sh = new HttpHandler();
            ArrayList<HashMap<String, String>> parms = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> postDataParams = new HashMap<String, String>();
            postDataParams.put("action", "payment_history");
            postDataParams.put("userid", "" + user_id);
            postDataParams.put("limit", "" + preLast_id);
            postDataParams.put("sid", "" + user_service_item_id);
            postDataParams.put("st", "" + user_service_type_id);
            parms.add(postDataParams);
            String result = sh.makeServiceCall(Utils.url_calender_service, parms);

            Log.i("EVENT", "response : " + result);
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

                arrayListPaymentHistory.clear();
                if (jArray.length() > 0) {
                    String strr = result.replaceAll("\"", "");
                    if (strr.contains("status:No Data")) {
                        System.out.println(" date_load : Update=== no ");

                        end1 = 1;
                    } else {
                        System.out.println(" date_load : Update=== yes");
                        end1 = 0;
                        for (int i = 0; i < jArray.length(); i++) {
                            json_data = jArray.getJSONObject(i);
                            System.out.println("==== sent " + json_data.getString("plan_name"));
                            System.out.println("==== sent " + json_data.getString("planid"));

                            HashMap<String, Object> temp = new HashMap<String, Object>();
                            temp.put("service_type", json_data.getString("service_type"));
                            temp.put("planid", json_data.getString("planid"));
                            temp.put("plan_name", json_data.getString("plan_name"));
                            temp.put("payment_start_date", json_data.getString("payment_start_date"));
                            temp.put("payment_end_date", json_data.getString("payment_end_date"));
                            temp.put("validity", json_data.getString("validity"));
                            temp.put("invoice_url", json_data.getString("invoice_url"));
                            arrayListPaymentHistory.add(temp);
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

            listView.removeFooterView(mProgressBarFooter);
            swipe_refresh_layout.setRefreshing(false);


            if (end1 == 0) {
                preLast_id = preLast_id + 1;
                if (arrayListPaymentHistory.size() > 4) {
                    if (jArray != null) {
                        preLast = preLast + jArray.length();
                    }
                    adapter.notifyDataSetChanged();
                    listView.removeFooterView(footerView);
                    listView.addFooterView(mProgressBarFooter);
                    listView.setOnScrollListener(new scrollListener());
                } else {
                    adapter.notifyDataSetChanged();
                }
            }
            if (arrayListPaymentHistory.size() == 0) {
                empty_lay.setVisibility(View.VISIBLE);

             /*   if (filter_values.size() == 0) {
                    if (Utils.isNetworkAvailable(getActivity())) {
                        empty_imgg.setImageResource(R.drawable.search_empty_state);
                        empty_txttt.setText(empty_str);
                    } else {
                        empty_imgg.setImageResource(R.drawable.no_data_icon);
                        empty_txttt.setText("இணைய சேவையை சரிபார்க்கவும்...");
                    }
                } else {
                    if (Utils.isNetworkAvailable(getActivity())) {
                        empty_imgg.setImageResource(R.drawable.search_empty_state);
                        empty_txttt.setText("தேர்வு செய்யப்பட்ட பகுதியில் விவரங்கள் ஏதும் இல்லை");
                    } else {
                        empty_imgg.setImageResource(R.drawable.no_data_icon);
                        empty_txttt.setText("இணைய சேவையை சரிபார்க்கவும்...");
                    }
                }*/
                listView.setVisibility(View.GONE);
            } else {
                empty_lay.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
            }
        }
    }

    private class load_filtter_below extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strr) {
            HttpHandler sh = new HttpHandler();
            ArrayList<HashMap<String, String>> parms = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> temp = new HashMap<String, String>();
            temp.put("action", "payment_history");
            temp.put("userid", "" + user_id);
            temp.put("limit", "" + preLast_id);
            temp.put("sid", "" + user_service_item_id);
            temp.put("st", "" + user_service_type_id);
            parms.add(temp);
            String result = sh.makeServiceCall(Utils.url_calender_service, parms);
            return "" + result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            JSONArray jArray = null;

            System.out.println("load_filtter_below : Update===" + result);
            try {

                jArray = new JSONArray(result);

                JSONObject json_data = null;


                if (jArray.length() > 0) {
                    json_data = jArray.getJSONObject(0);
                    String strr = result.replaceAll("\"", "");

                    if (strr.contains("status:No Data")) {
                        end1 = 1;
                    } else {
                        end1 = 0;

                        for (int i = 0; i < jArray.length(); i++) {
                            json_data = jArray.getJSONObject(i);
                            HashMap<String, Object> temp = new HashMap<String, Object>();
                            temp.put("service_type", json_data.getString("service_type"));
                            temp.put("planid", json_data.getString("planid"));
                            temp.put("plan_name", json_data.getString("plan_name"));
                            temp.put("payment_start_date", json_data.getString("payment_start_date"));
                            temp.put("payment_end_date", json_data.getString("payment_end_date"));
                            temp.put("validity", json_data.getString("validity"));
                            temp.put("invoice_url", json_data.getString("invoice_url"));
                            arrayListPaymentHistory.add(temp);
                        }
                    }
                }

            } catch (JSONException e1) {
                System.out.println("EXJSONException===" + e1);
            }

            last_size = arrayListPaymentHistory.size();
            listView.removeFooterView(mProgressBarFooter);
            swipe_refresh_layout.setRefreshing(false);

            if (arrayListPaymentHistory.size() > 4) {
                if (first_size == last_size) {
                    stop_load = 1;
                    listView.addFooterView(footerView);
                }
            }


            if (end1 == 0) {
                preLast_id = preLast_id + 1;
                adapter.notifyDataSetChanged();
                if (jArray != null) {
                    preLast = preLast + jArray.length();
                    if (jArray.length() == 5) {
                        listView.setOnScrollListener(new scrollListener());
                        listView.removeFooterView(footerView);
                        listView.addFooterView(mProgressBarFooter);
                    }
                }


            }

        }


    }


    public class scrollListener implements AbsListView.OnScrollListener {

        public scrollListener() {
        }

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            if (preLast != 0) {
                if (preLast > Last) {

                    if ((firstVisibleItem) == (totalItemCount - (visibleItemCount))) {
                        Last = preLast;

                        if (stop_load == 0) {
                            new load_filtter_below().execute();
                        }
                        first_size = arrayListPaymentHistory.size();

                    }

                }
            }

        }
    }

    public class CustomListAdapter extends BaseAdapter {

        ArrayList<HashMap<String, Object>> arrayListPaymentHistory;
        private Context activity;


        public CustomListAdapter(Context context, ArrayList<HashMap<String, Object>> arrayListPaymentHistory) {
            this.activity = context;
            this.arrayListPaymentHistory = arrayListPaymentHistory;
        }

        @Override
        public int getCount() {
            return arrayListPaymentHistory.size();
        }

        @Override
        public Object getItem(int location) {
            return arrayListPaymentHistory.get(location);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View view, ViewGroup parent) {

            if (inflater == null)
                inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (view == null)
                view = inflater.inflate(R.layout.layout_payment_list_item, null);

            TextView plan_name = view.findViewById(R.id.plan_name);
            TextView plan_start_date = view.findViewById(R.id.plan_start_date);
            TextView plan_end_date = view.findViewById(R.id.plan_end_date);
            TextView plan_validity = view.findViewById(R.id.plan_validity);
            TextView invoice = view.findViewById(R.id.invoice);

            plan_name.setText("" + arrayListPaymentHistory.get(position).get("plan_name").toString());
            plan_start_date.setText("" + arrayListPaymentHistory.get(position).get("payment_start_date").toString());
            plan_end_date.setText("" + arrayListPaymentHistory.get(position).get("payment_end_date").toString());
            plan_validity.setText("" + arrayListPaymentHistory.get(position).get("validity").toString() + " days");

            if(arrayListPaymentHistory.get(position).get("invoice_url").toString().equals("")){
                invoice.setVisibility(View.GONE);
            }

            invoice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Utils.isNetworkAvailable(activity)) {
                        try {

                            Utils.custom_tabs(activity, arrayListPaymentHistory.get(position).get("invoice_url").toString());

                        } catch (Exception e) {
                            System.out.println("Preview Error " + e.getMessage());

                        }

                    } else {
                        Utils.toast_center(activity, "இணைய சேவையை சரிபார்க்கவும்...");
                    }
                }
            });

            return view;
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
