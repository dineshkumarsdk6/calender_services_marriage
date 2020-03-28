package nithra.calender.marriage.services.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import nithra.calender.marriage.services.MarriageServices.ActivityPayment;
import nithra.calender.marriage.services.GlideApp;
import nithra.calender.marriage.services.HttpHandler;
import nithra.calender.marriage.services.MarriageServices.ActivityMarriageServicePreview;
import nithra.calender.marriage.services.Pojo.City_Model;
import nithra.calender.marriage.services.R;
import nithra.calender.marriage.services.SharedPreference;
import nithra.calender.marriage.services.Utils;

import static nithra.calender.marriage.services.MarriageServices.ActivityMarriageServiceMain.filter_values;

public class FragmentAdminView extends Fragment {
    SharedPreference sharedPreference;

    int preLast;
    int Last;
    int preLast_id = 0;
    int end = 0, end1 = 0;
    ArrayList<HashMap<String, Object>> arrayListMandapam;
    CustomListAdapter adapter;

    ListView listView;
    SwipeRefreshLayout swipe_refresh_layout;

    LayoutInflater inflater;
    View mProgressBarFooter;
    View footerView;

    RelativeLayout empty_lay;
    ImageView empty_imgg;
    TextView empty_txttt;

    String district_txt = "";
    String taluk_txt = "";
    String cate = "";
    String gender = "";
    String education = "";
    String exp_minValue = "";


    int first_size = 0;
    int last_size = 0;

    int stop_load = 0;

    String empty_str = "";

    TextView district_spinner, taluk_spinner;
    int click_val = 0;
    ArrayList<City_Model> dist_item = new ArrayList<>();
    ArrayList<City_Model> city_models = new ArrayList<>();

    String server_url = "" + Utils.url_calender_service;
    String user_service_type_name = "";
    String action_type = "";

    public FragmentAdminView() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View main = inflater.inflate(R.layout.layout_mandapam_listview, container, false);
        sharedPreference = new SharedPreference();
        user_service_type_name = sharedPreference.getString(getActivity(), "user_service_type_name");

        if (user_service_type_name.equals("1")) {
            action_type = "get_mandapam_admin";
        }

        if (user_service_type_name.equals("2")) {
            action_type = "get_decoration_admin";
        }

        if (user_service_type_name.equals("3")) {
            action_type = "get_photography_admin";

        }

        if (user_service_type_name.equals("4")) {
            action_type = "get_music_admin";
        }

        if (user_service_type_name.equals("5")) {
            action_type = "get_parlour_admin";
        }

        if (user_service_type_name.equals("6")) {
            action_type = "get_catering_admin";
        }

        empty_lay = main.findViewById(R.id.empty_lay);
        empty_imgg = main.findViewById(R.id.empty_imgg);
        empty_txttt = main.findViewById(R.id.empty_txttt);

        district_spinner = main.findViewById(R.id.district_spinner);
        taluk_spinner = main.findViewById(R.id.taluk_spinner);
        district_spinner.setVisibility(View.GONE);
        taluk_spinner.setVisibility(View.GONE);


        listView = main.findViewById(R.id.list);
        swipe_refresh_layout = main.findViewById(R.id.swipe_refresh_layout);
        arrayListMandapam = new ArrayList<HashMap<String, Object>>();
        adapter = new CustomListAdapter(getActivity(), arrayListMandapam);

        swipe_refresh_layout.setColorSchemeResources(android.R.color.holo_green_light,
                android.R.color.holo_blue_dark,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        mProgressBarFooter = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.progress_bar_footer, null, false);

        footerView = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_layout, null, false);

        listView.setAdapter(adapter);
        listView.setDivider(new ColorDrawable(this.getResources().getColor(android.R.color.transparent)));

        swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                preLast_id = 0;
                preLast = 0;
                Last = 0;

                arrayListMandapam.clear();
                listView.removeFooterView(mProgressBarFooter);
                listView.removeFooterView(footerView);
                listView.removeFooterView(footerView);
                adapter.notifyDataSetChanged();
                district_txt = "";
                taluk_txt = "";
                cate = "";
                gender = "";
                education = "";
                exp_minValue = "";
                district_spinner.setText("");
                district_spinner.setTag("");

                taluk_spinner.setText("");
                taluk_spinner.setTag("");

                preLast_id = 0;
                preLast = 0;
                Last = 0;

                swipe_refresh_layout.setRefreshing(true);
                empty_lay.setVisibility(View.GONE);

                stop_load = 0;

                new date_load().execute();


            }
        });

        // on_load();

        return main;
    }

    public void on_load() {
        preLast_id = 0;
        preLast = 0;
        Last = 0;

        arrayListMandapam.clear();
        listView.removeFooterView(mProgressBarFooter);
        listView.removeFooterView(footerView);
        listView.removeFooterView(footerView);
        adapter.notifyDataSetChanged();
        district_txt = "";
        taluk_txt = "";
        cate = "";
        gender = "";
        education = "";
        exp_minValue = "";
        district_spinner.setText("");
        district_spinner.setTag("");
        taluk_spinner.setText("");
        taluk_spinner.setTag("");

        swipe_refresh_layout.setRefreshing(true);
        empty_lay.setVisibility(View.GONE);

        stop_load = 0;

        new date_load().execute();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!Utils.isNetworkAvailable(getActivity())) {
            Utils.toast_center(getActivity(), "இணையதள சேவையை சரிபார்க்கவும் ");
            return;
        }

        on_load();
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
            temp.put("action", "" + action_type);
            temp.put("limit", "" + preLast_id);
            temp.put("district", "" + district_txt);
            temp.put("city", "" + taluk_txt);
            temp.put("userid", "" + sharedPreference.getString(getActivity(), "user_reg_id"));
            parms.add(temp);
            String result = sh.makeServiceCall(server_url, parms);
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
                            temp.put("list_userid", json_data.getString("user_id"));
                            temp.put("list_id", json_data.getString("id"));
                            temp.put("list_name", json_data.getString("org_name"));
                            temp.put("list_incharger_name", json_data.getString("incharge_name"));
                            temp.put("list_phone_no", json_data.getString("phone_no"));
                            temp.put("list_alternative_mno", json_data.getString("alternative_mno"));
                            temp.put("list_district", json_data.getString("district_name"));
                            temp.put("list_taulk", json_data.getString("city_name"));
                            temp.put("list_images", json_data.getString("images"));
                            temp.put("list_paystatus", json_data.getString("paystatus"));
                            temp.put("list_payurl", json_data.getString("payurl"));
                            temp.put("list_is_active", json_data.getString("is_active"));
                            temp.put("list_is_dnd", json_data.getString("is_dnd"));
                            arrayListMandapam.add(temp);


                        }
                    }
                }

            } catch (JSONException e1) {
                System.out.println("EXJSONException===" + e1);
            }

            last_size = arrayListMandapam.size();

            listView.removeFooterView(mProgressBarFooter);
            swipe_refresh_layout.setRefreshing(false);
            if (arrayListMandapam.size() > 4) {
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

    private class date_load extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Utils.mProgress(getActivity(), "ஏற்றுகிறது. காத்திருக்கவும் ", false).show();
        }

        @Override
        protected String doInBackground(String... strr) {
            System.out.println("====server action " + action_type);
            System.out.println("====server action " + sharedPreference.getString(getActivity(), "user_reg_id"));
            System.out.println("====server action " + preLast_id);
            System.out.println("====server action " + district_txt);
            System.out.println("====server action " + taluk_txt);

            HttpHandler sh = new HttpHandler();
            ArrayList<HashMap<String, String>> parms = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> postDataParams = new HashMap<String, String>();
            postDataParams.put("action", "" + action_type);
            postDataParams.put("userid", "" + sharedPreference.getString(getActivity(), "user_reg_id"));
            postDataParams.put("limit", "" + preLast_id);
            postDataParams.put("district", "" + district_txt);
            postDataParams.put("city", "" + taluk_txt);
            parms.add(postDataParams);
            String result = sh.makeServiceCall(server_url, parms);

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

                arrayListMandapam.clear();
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
                            HashMap<String, Object> temp = new HashMap<String, Object>();
                            temp.put("list_userid", json_data.getString("user_id"));
                            temp.put("list_id", json_data.getString("id"));
                            temp.put("list_name", json_data.getString("org_name"));
                            temp.put("list_incharger_name", json_data.getString("incharge_name"));
                            temp.put("list_phone_no", json_data.getString("phone_no"));
                            temp.put("list_alternative_mno", json_data.getString("alternative_mno"));
                            temp.put("list_district", json_data.getString("district_name"));
                            temp.put("list_taulk", json_data.getString("city_name"));
                            temp.put("list_images", json_data.getString("images"));
                            temp.put("list_paystatus", json_data.getString("paystatus"));
                            temp.put("list_payurl", json_data.getString("payurl"));
                            temp.put("list_is_active", json_data.getString("is_active"));
                            temp.put("list_is_dnd", json_data.getString("is_dnd"));
                            arrayListMandapam.add(temp);
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
                if (arrayListMandapam.size() > 4) {
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
            if (arrayListMandapam.size() == 0) {
                empty_lay.setVisibility(View.VISIBLE);

                if (filter_values.size() == 0) {
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
                }
                listView.setVisibility(View.GONE);
            } else {
                empty_lay.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
            }
        }
    }

    public class CustomListAdapter extends BaseAdapter {

        ArrayList<HashMap<String, Object>> arrayListMandapam;
        private Context activity;


        public CustomListAdapter(Context context, ArrayList<HashMap<String, Object>> arrayListMandapam) {
            this.activity = context;
            this.arrayListMandapam = arrayListMandapam;
        }

        @Override
        public int getCount() {
            return arrayListMandapam.size();
        }

        @Override
        public Object getItem(int location) {
            return arrayListMandapam.get(location);
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
                view = inflater.inflate(R.layout.layout_mandapam_list_item_user, null);

            TextView mandapamName = view.findViewById(R.id.textMandapamName);
            TextView mandapamInchargeName = view.findViewById(R.id.textMandapamContactName);
            TextView mandapamMobile = view.findViewById(R.id.textMandapamContactNum);
            TextView textImageCount = view.findViewById(R.id.textImageCount);
            TextView textDnd = view.findViewById(R.id.textDnd);
            ImageView mandapamImage = view.findViewById(R.id.imageMandapam);
            LinearLayout layout_dnd = view.findViewById(R.id.layout_dnd);

            TextView text_live = view.findViewById(R.id.text_live);
            TextView text_upgrade = view.findViewById(R.id.text_upgrade);
            CardView card_upgrade = view.findViewById(R.id.card_upgrade);
            TextView mandapamDistrict = view.findViewById(R.id.textMandapamContactDistrict);
            TextView mandapamTaluk = view.findViewById(R.id.textMandapamContactTaluk);
            Switch switch_dnd = view.findViewById(R.id.switch_dnd);

            if (!arrayListMandapam.get(position).get("list_is_dnd").toString().equals("0")) {
                switch_dnd.setChecked(true);
                textDnd.setText("Show");
            } else {
                switch_dnd.setChecked(false);
                textDnd.setText("Hide");
            }

            switch_dnd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!Utils.isNetworkAvailable(activity)) {
                        Toast.makeText(activity, "இணைய சேவையை சரிபார்க்கவும்...", Toast.LENGTH_SHORT).show();
                        if (switch_dnd.isChecked()) {
                            switch_dnd.setChecked(false);
                            textDnd.setText("Hide");
                        } else {
                            switch_dnd.setChecked(true);
                            textDnd.setText("Show");
                        }
                    } else {
                        final Dialog dialog_checkbox = new Dialog(activity, android.R.style.Theme_DeviceDefault_Dialog_NoActionBar_MinWidth);
                        dialog_checkbox.setContentView(R.layout.nodate_dia);
                        dialog_checkbox.setCanceledOnTouchOutside(false);
                        dialog_checkbox.setCancelable(false);
                        CardView card_yes = dialog_checkbox.findViewById(R.id.card_yes);
                        CardView card_no = dialog_checkbox.findViewById(R.id.card_no);
                        AppCompatTextView text_no = dialog_checkbox.findViewById(R.id.text_no);
                        AppCompatTextView text_yes = dialog_checkbox.findViewById(R.id.text_yes);
                        AppCompatTextView text_head = dialog_checkbox.findViewById(R.id.text_head);
                        AppCompatTextView text_content = dialog_checkbox.findViewById(R.id.text_content);
                        text_yes.setText("ஆம்");
                        text_no.setText("இல்லை");
                        text_head.setVisibility(View.GONE);

                        if (!switch_dnd.isChecked()) {
                            text_content.setText("உங்களின் அனைத்து விவரங்களையும் மற்ற பயனர்களுக்கு காண்பிக்க விரும்புகிறீர்களா?");
                        } else {
                            text_content.setText("உங்களின் அனைத்து விவரங்களையும் மற்ற பயனர்களிடமிருந்து மறைக்க விரும்புகிறீர்களா?");
                        }

                        card_yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (!Utils.isNetworkAvailable(activity)) {
                                    dialog_checkbox.dismiss();
                                    Toast.makeText(activity, "இணைய சேவையை சரிபார்க்கவும்...", Toast.LENGTH_SHORT).show();
                                    if (switch_dnd.isChecked()) {
                                        switch_dnd.setChecked(false);
                                        textDnd.setText("Hide");
                                    } else {
                                        switch_dnd.setChecked(true);
                                        textDnd.setText("Show");
                                    }
                                } else {
                                    dialog_checkbox.dismiss();
                                    if (switch_dnd.isChecked() == false) {
                                        System.out.println("==== switch_dnd false");
                                        new get_dnd_status().execute(arrayListMandapam.get(position).get("list_id").toString(), "0", user_service_type_name);
                                        switch_dnd.setChecked(false);
                                        textDnd.setText("Hide");
                                    }

                                    if (switch_dnd.isChecked() == true) {
                                        System.out.println("==== switch_dnd true");
                                        new get_dnd_status().execute(arrayListMandapam.get(position).get("list_id").toString(), "1", user_service_type_name);
                                        switch_dnd.setChecked(true);
                                        textDnd.setText("Show");
                                    }
                                }
                            }
                        });

                        card_no.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (switch_dnd.isChecked()) {
                                    switch_dnd.setChecked(false);
                                } else {
                                    switch_dnd.setChecked(true);
                                }
                                dialog_checkbox.dismiss();
                            }
                        });


                        dialog_checkbox.show();
                    }
                }
            });

            mandapamName.setText("" + arrayListMandapam.get(position).get("list_name").toString());
            mandapamInchargeName.setText("" + arrayListMandapam.get(position).get("list_incharger_name").toString());
            mandapamMobile.setText("" + arrayListMandapam.get(position).get("list_phone_no").toString());
            mandapamDistrict.setText("" + arrayListMandapam.get(position).get("list_district").toString() + " மாவட்டம்");
            mandapamTaluk.setText("" + arrayListMandapam.get(position).get("list_taulk").toString());

            if (arrayListMandapam.get(position).get("list_paystatus").toString().equals("notpaid") ||
                    arrayListMandapam.get(position).get("list_paystatus").toString().equals("Expired")) {
                card_upgrade.setVisibility(View.VISIBLE);
                text_upgrade.setText("Choose Plan");
            } else {
                text_upgrade.setText("" + arrayListMandapam.get(position).get("list_paystatus").toString());
            }

            if (arrayListMandapam.get(position).get("list_is_active").toString().equals("0")) {
                layout_dnd.setVisibility(View.GONE);
            }

            if (arrayListMandapam.get(position).get("list_paystatus").toString().equals("notpaid")) {
                text_live.setText("Inactive");
                layout_dnd.setVisibility(View.GONE);
            } else if (arrayListMandapam.get(position).get("list_paystatus").toString().equals("Expired")) {
                text_live.setText("Expired");
                layout_dnd.setVisibility(View.GONE);
            } else {
                text_live.setText("*Live");
                layout_dnd.setVisibility(View.VISIBLE);
            }

            if (arrayListMandapam.get(position).get("list_paystatus").toString().equals("Paid")) {
                card_upgrade.setVisibility(View.GONE);
            }

            String mobile = "" + arrayListMandapam.get(position).get("list_phone_no").toString();
            if (!arrayListMandapam.get(position).get("list_alternative_mno").toString().equals("")) {
                mobile = mobile + ", " + arrayListMandapam.get(position).get("list_alternative_mno").toString();
            }
            mandapamMobile.setText("" + mobile);

            String images = arrayListMandapam.get(position).get("list_images").toString();
            String[] separated = images.split(",");

            System.out.println("==result image count " + (separated.length - 1));
            if ((separated.length - 1) != 0) {
                textImageCount.setText("+ " + (separated.length - 1));
            }

            if (separated[0].length() > 5) {
                GlideApp.with(activity)
                        .load(separated[0])
                        .placeholder(R.drawable.mandapamsample)
                        .error(R.drawable.mandapamsample)
                        .into(mandapamImage);
            } else {
                mandapamImage.setImageResource(R.drawable.mandapamsample);
            }


            card_upgrade.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!Utils.isNetworkAvailable(activity)) {
                        Utils.toast_center(activity, "இணையதள சேவையை சரிபார்க்கவும் ");
                        return;
                    }

                    if (arrayListMandapam.get(position).get("list_is_active").toString().equals("0")) {
                        down_info();
                        return;
                    }

                    String paymentUrl = arrayListMandapam.get(position).get("list_payurl").toString();
                    Intent i = new Intent(activity, ActivityPayment.class);
                    i.putExtra("paymentUrl", "" + paymentUrl);
                    startActivity(i);
                }
            });

           /* mandapamImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utils.isNetworkAvailable(activity) == false) {
                        Utils.toast_center(activity, "இணையதள சேவையை சரிபார்க்கவும் ");
                        return;
                    }
                    if (arrayListMandapam.get(position).get("list_images").toString().split(",")[0].length() > 5) {
                        sharedPreference.putString(activity, "imageurl", arrayListMandapam.get(position).get("list_images").toString());
                        sharedPreference.putInt(activity, "image_poss_val", 0);
                        Intent i = new Intent(activity, Activity_slider.class);
                        startActivity(i);
                    }
                }
            });
*/
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Utils.isNetworkAvailable(activity)) {
                        try {
                            Intent i = new Intent(activity, ActivityMarriageServicePreview.class);
                            i.putExtra("click_from", "dataId");
                            i.putExtra("list_id", "" + arrayListMandapam.get(position).get("list_id").toString());
                            i.putExtra("list_images", "" + arrayListMandapam.get(position).get("list_images").toString());
                            i.putExtra("user_service_type_name", "" + user_service_type_name);
                            startActivity(i);
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

    private class get_dnd_status extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strr) {
            HttpHandler sh = new HttpHandler();
            ArrayList<HashMap<String, String>> parms = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> temp = new HashMap<String, String>();
            temp.put("action", "get_dnd");
            temp.put("dnd", "" + strr[1]);
            temp.put("sid", "" + strr[0]);
            temp.put("st", "" + strr[2]);
            parms.add(temp);
            String result = sh.makeServiceCall(server_url, parms);
            return "" + result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            System.out.println("server response dnd status : " + result);

            try {
                JSONObject jsonObject = new JSONObject(result);
                System.out.println("server response dnd status : " + jsonObject.getString("status"));
                //Toast.makeText(getActivity(), "" + jsonObject.getString("status"), Toast.LENGTH_SHORT).show();

            } catch (JSONException e) {
                System.out.println("EXJSONException===" + e.getMessage());
            }
        }
    }

    public void down_info() {
        final Dialog rating_dialog = new Dialog(getActivity(),
                android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
        rating_dialog.setContentView(R.layout.info_dia);
        rating_dialog.setCancelable(false);
        AppCompatButton btnSend = rating_dialog.findViewById(R.id.btnSend);
        AppCompatTextView textt = rating_dialog.findViewById(R.id.textt);
        AppCompatTextView textView1 = rating_dialog.findViewById(R.id.textView1);

        CardView ok_card = rating_dialog.findViewById(R.id.ok_card);
        btnSend.setText("சரி");


        ok_card.setCardBackgroundColor(Utils.get_color(getActivity()));
        textView1.setBackgroundColor(Utils.get_color(getActivity()));


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
                        first_size = arrayListMandapam.size();

                    }

                }
            }

        }
    }


}