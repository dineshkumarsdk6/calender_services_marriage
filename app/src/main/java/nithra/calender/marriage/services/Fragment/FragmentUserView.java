package nithra.calender.marriage.services.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import nithra.calender.marriage.services.GlideApp;
import nithra.calender.marriage.services.HttpHandler;
import nithra.calender.marriage.services.MarriageServices.ActivityMarriageServicePreview;
import nithra.calender.marriage.services.Pojo.City_Model;
import nithra.calender.marriage.services.R;
import nithra.calender.marriage.services.SharedPreference;
import nithra.calender.marriage.services.Utils;
import nithra.calender.marriage.services.searchdialog.ContactSearchDialogCompat;
import nithra.calender.marriage.services.searchdialog.core.BaseSearchDialogCompat;
import nithra.calender.marriage.services.searchdialog.core.SearchResultListener;

import static nithra.calender.marriage.services.MarriageServices.ActivityMarriageServiceMain.filter_values;
import static nithra.calender.marriage.services.MarriageServices.ActivityMarriageServiceMain.onload;

public class FragmentUserView extends Fragment {
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
    String event = "";

    int first_size = 0;
    int last_size = 0;

    int stop_load = 0;

    String empty_str = "தகவல்கள் ஏதும் பதிவு செய்யப்படவில்லை";

    TextView district_spinner, taluk_spinner;
    int click_val = 0;
    ArrayList<City_Model> dist_item = new ArrayList<>();
    ArrayList<City_Model> city_models = new ArrayList<>();

    String server_url = "" + Utils.url_calender_service;

    String user_service_type_name = "";
    String action_type = "";

    public FragmentUserView() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View main = inflater.inflate(R.layout.layout_mandapam_listview, container, false);
        sharedPreference = new SharedPreference();

        user_service_type_name = sharedPreference.getString(getActivity(), "user_service_type_name");

        if (user_service_type_name.equals("1")) {
            action_type = "get_mandapam_list";
        }

        if (user_service_type_name.equals("2")) {
            action_type = "get_decoration_list";
        }

        if (user_service_type_name.equals("3")) {
            action_type = "get_photography_list";
        }

        if (user_service_type_name.equals("4")) {
            action_type = "get_music_list";
        }

        if (user_service_type_name.equals("5")) {
            action_type = "get_parlour_list";
        }

        if (user_service_type_name.equals("6")) {
            action_type = "get_catering_list";
        }


        empty_lay = main.findViewById(R.id.empty_lay);
        empty_imgg = main.findViewById(R.id.empty_imgg);
        empty_txttt = main.findViewById(R.id.empty_txttt);

        district_spinner = main.findViewById(R.id.district_spinner);
        taluk_spinner = main.findViewById(R.id.taluk_spinner);
        district_spinner.setVisibility(View.VISIBLE);
        taluk_spinner.setVisibility(View.VISIBLE);


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


       /* if (sharedPreference.getString(getActivity(), "progithar_reg_comp_status" +
                sharedPreference.getString(getActivity(), "progithar_type")).equals("Registration complete")) {
            if (sharedPreference.getString(getActivity(), "progithar_type").equals("ஐயர்")) {
                empty_str = sharedPreference.getString(getActivity(), "fess_title") + " விவரங்கள் ஏதும் இல்லை.";
            } else if (sharedPreference.getString(getActivity(), "progithar_type").equals("ஜோதிடர்")) {
                empty_str = sharedPreference.getString(getActivity(), "fess_title") + " விவரங்கள் ஏதும் இல்லை.";
            } else if (sharedPreference.getString(getActivity(), "progithar_type").equals("வாஸ்து நிபுணர்")) {
                empty_str = sharedPreference.getString(getActivity(), "fess_title") + " விவரங்கள் ஏதும் இல்லை.";
            }
        } else {
            if (sharedPreference.getString(getActivity(), "progithar_type").equals("ஐயர்")) {
                empty_str = sharedPreference.getString(getActivity(), "fess_title") + " விவரங்கள் ஏதும் இல்லை. நீங்கள் புரோகிதர் எனில், மேலே உள்ள  + பட்டனை கிளிக் செய்து உங்களது தகவல்களை பதிவு செய்து கொள்ளுங்கள்.";
            } else if (sharedPreference.getString(getActivity(), "progithar_type").equals("ஜோதிடர்")) {
                empty_str = sharedPreference.getString(getActivity(), "fess_title") + " விவரங்கள் ஏதும் இல்லை. நீங்கள் ஜோதிடர் எனில், மேலே உள்ள  + பட்டனை கிளிக் செய்து உங்களது தகவல்களை பதிவு செய்து கொள்ளுங்கள்.";
            } else if (sharedPreference.getString(getActivity(), "progithar_type").equals("வாஸ்து நிபுணர்")) {
                empty_str = sharedPreference.getString(getActivity(), "fess_title") + " விவரங்கள் ஏதும் இல்லை. நீங்கள் வாஸ்து நிபுணர் எனில், மேலே உள்ள  + பட்டனை கிளிக் செய்து உங்களது தகவல்களை பதிவு செய்து கொள்ளுங்கள்.";
            }
        }*/


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
                filter_values.clear();
                district_txt = "";
                taluk_txt = "";
                event = "";

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

        district_spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click_val = 0;
                if (Utils.isNetworkAvailable(getActivity()) == false) {
                    Utils.toast_center(getActivity(), "இணையதள சேவையை சரிபார்க்கவும் ");
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
                if (Utils.isNetworkAvailable(getActivity()) == false) {
                    Utils.toast_center(getActivity(), "இணையதள சேவையை சரிபார்க்கவும் ");
                    return;
                }
                if (district_spinner.getText().toString().length() != 0) {
                    if (dist_item.size() != 0) {
                        city_list();
                    } else {
                        new dist_load().execute();
                    }
                } else {
                    Utils.toast_center(getActivity(), "மாவட்டம் தேர்வு செய்க");
                }
            }
        });

        on_load();
        return main;
    }

    public void on_load() {
        preLast_id = 0;
        preLast = 0;
        Last = 0;

        filter_values.clear();
        district_txt = "";
        taluk_txt = "";
        event = "";
        arrayListMandapam.clear();
        listView.removeFooterView(mProgressBarFooter);
        listView.removeFooterView(footerView);
        listView.removeFooterView(footerView);
        adapter.notifyDataSetChanged();
        swipe_refresh_layout.setRefreshing(true);

        empty_lay.setVisibility(View.GONE);


        new date_load().execute();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (onload != 0) {
            onload = 0;
            preLast_id = 0;
            preLast = 0;
            Last = 0;
            arrayListMandapam.clear();
            listView.removeFooterView(mProgressBarFooter);
            listView.removeFooterView(footerView);
            adapter.notifyDataSetChanged();
            swipe_refresh_layout.setRefreshing(true);
            empty_lay.setVisibility(View.GONE);
            stop_load = 0;


            if (filter_values.size() != 0) {

                System.out.println("==== resume set");
                System.out.println("==== resume set " + filter_values.get(0).get("district_txt").toString());
                System.out.println("==== resume set " + filter_values.get(0).get("city_txt").toString());
                district_txt = "" + filter_values.get(0).get("district").toString();
                taluk_txt = "" + filter_values.get(0).get("city").toString();
                district_spinner.setText("" + filter_values.get(0).get("district_txt").toString());
                district_spinner.setTag("" + filter_values.get(0).get("district").toString());
                taluk_spinner.setText("" + filter_values.get(0).get("city_txt").toString());
                taluk_spinner.setTag("" + filter_values.get(0).get("city").toString());
                event = "" + filter_values.get(0).get("category").toString();

            } else {
                System.out.println("==== resume not set");
                district_txt = "";
                taluk_txt = "";
                event = "";

                district_spinner.setText("");
                district_spinner.setTag("");

                taluk_spinner.setText("");
                taluk_spinner.setTag("");
            }


            System.out.println("====== filter values resume " + district_spinner.getText().toString());
            System.out.println("====== filter values resume " + taluk_spinner.getText().toString());
            System.out.println("====== filter values resume " + event);
            new date_load().execute();
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
            temp.put("action", "" + action_type);
            temp.put("limit", "" + preLast_id);
            temp.put("district", "" + district_txt);
            temp.put("city", "" + taluk_txt);
            temp.put("event", "" + event);
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

            System.out.println("=== data load for all " + action_type);
            System.out.println("=== data load for all " + preLast_id);
            System.out.println("=== data load for all " + district_txt);
            System.out.println("=== data load for all " + taluk_txt);
            System.out.println("=== data load for all " + event);

            HttpHandler sh = new HttpHandler();
            ArrayList<HashMap<String, String>> parms = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> postDataParams = new HashMap<String, String>();
            postDataParams.put("action", "" + action_type);
            postDataParams.put("limit", "" + preLast_id);
            postDataParams.put("district", "" + district_txt);
            postDataParams.put("city", "" + taluk_txt);
            postDataParams.put("event", "" + event);
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
                        System.out.println(" date_load : Update=== no data" + result);
                        end1 = 1;
                    } else {
                        System.out.println(" date_load : Update=== yes data" + result);
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
                view = inflater.inflate(R.layout.layout_mandapam_list_item_all, null);

            TextView mandapamName = view.findViewById(R.id.textMandapamName);
            TextView mandapamInchargeName = view.findViewById(R.id.textMandapamContactName);
            TextView mandapamMobile = view.findViewById(R.id.textMandapamContactNum);
            TextView mandapamDistrict = view.findViewById(R.id.textMandapamContactDistrict);
            TextView mandapamTaluk = view.findViewById(R.id.textMandapamContactTaluk);
            TextView textImageCount = view.findViewById(R.id.textImageCount);
            ImageView mandapamImage = view.findViewById(R.id.imageMandapam);
            LinearLayout layoutCall = view.findViewById(R.id.layoutCall);

            mandapamName.setText("" + arrayListMandapam.get(position).get("list_name").toString());
            mandapamInchargeName.setText("" + arrayListMandapam.get(position).get("list_incharger_name").toString());
            mandapamMobile.setText("" + arrayListMandapam.get(position).get("list_phone_no").toString());
            mandapamDistrict.setText("" + arrayListMandapam.get(position).get("list_district").toString() + " மாவட்டம்");
            mandapamTaluk.setText("" + arrayListMandapam.get(position).get("list_taulk").toString());

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

            layoutCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + arrayListMandapam.get(position).get("list_phone_no").toString()));
                    startActivity(intent);
                }
            });

            /*mandapamImage.setOnClickListener(new View.OnClickListener() {
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
            });*/

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Utils.isNetworkAvailable(activity)) {
                        Intent i = new Intent(activity, ActivityMarriageServicePreview.class);
                        i.putExtra("click_from", "dataAll");
                        i.putExtra("list_id", "" + arrayListMandapam.get(position).get("list_id").toString());
                        i.putExtra("list_images", "" + arrayListMandapam.get(position).get("list_images").toString());
                        i.putExtra("user_service_type_name", "" + user_service_type_name);
                        startActivity(i);
                    } else {
                        Utils.toast_center(activity, "இணைய சேவையை சரிபார்க்கவும்...");
                    }
                }
            });

            return view;
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
                        first_size = arrayListMandapam.size();

                    }

                }
            }

        }
    }

    private class dist_load extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Utils.mProgress(getActivity(), "ஏற்றுகிறது. காத்திருக்கவும் ", false).show();
        }

        @Override
        protected String doInBackground(String... strr) {
            HttpHandler httpHandler = new HttpHandler();
            ArrayList<HashMap<String, String>> parms = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> temp = new HashMap<String, String>();
            temp.put("action", "getdistrict");
            parms.add(temp);
            String result = httpHandler.makeServiceCall(server_url, parms);
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
                    dialog.dismiss();
                    dist_choose();
                }
            };

            ContactSearchDialogCompat contactSearchDialogCompat = new ContactSearchDialogCompat(getActivity(),
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
            new ContactSearchDialogCompat<>(getActivity(), "நகரத்தை தேர்வு செய்க ", "நகரத்தை தேட", null, city_models,
                    new SearchResultListener<City_Model>() {
                        @Override
                        public void onSelected(BaseSearchDialogCompat dialog, City_Model item, int position) {
                            taluk_spinner.setText("" + item.getTitle());
                            taluk_spinner.setTag("" + item.getId());
                            dialog.dismiss();
                            dist_choose();

                        }
                    }).show();
        }

    }

    public void dist_choose() {

        if (filter_values.size() == 0) {
            HashMap<String, Object> temp = new HashMap<String, Object>();
            if (district_spinner.getText().toString().trim().length() != 0) {
                temp.put("district_txt", "" + district_spinner.getText().toString());
                temp.put("district", "" + district_spinner.getTag().toString());
            } else {
                temp.put("district", "");
                temp.put("district_txt", "");
            }

            if (taluk_spinner.getText().toString().trim().length() != 0) {
                temp.put("city_txt", "" + taluk_spinner.getText().toString());
                temp.put("city", "" + taluk_spinner.getTag().toString());
            } else {
                temp.put("city", "");
                temp.put("city_txt", "");
            }
            temp.put("category", "");
            filter_values.add(temp);
        } else {
            filter_values.clear();
            HashMap<String, Object> temp = new HashMap<String, Object>();
            if (district_spinner.getText().toString().trim().length() != 0) {
                temp.put("district_txt", "" + district_spinner.getText().toString());
                temp.put("district", "" + district_spinner.getTag().toString());
                district_txt = "" + district_spinner.getTag().toString();
            } else {
                temp.put("district", "");
                temp.put("district_txt", "");
                district_txt = "";
            }

            if (taluk_spinner.getText().toString().trim().length() != 0) {
                temp.put("city_txt", "" + taluk_spinner.getText().toString());
                temp.put("city", "" + taluk_spinner.getTag().toString());
                taluk_txt = "" + taluk_spinner.getTag().toString();
            } else {
                temp.put("city", "");
                temp.put("city_txt", "");
                taluk_txt = "";
            }
            temp.put("category", event);
            filter_values.add(temp);

        }
        preLast_id = 0;
        preLast = 0;
        Last = 0;
        arrayListMandapam.clear();
        listView.removeFooterView(mProgressBarFooter);
        listView.removeFooterView(footerView);
        adapter.notifyDataSetChanged();
        swipe_refresh_layout.setRefreshing(true);
        empty_lay.setVisibility(View.GONE);

        stop_load = 0;

        if (filter_values.size() != 0) {
            district_txt = "" + filter_values.get(0).get("district").toString();
            taluk_txt = "" + filter_values.get(0).get("city").toString();
            event = "" + filter_values.get(0).get("category").toString();
        } else {
            district_txt = "";
            taluk_txt = "";
            event = "";
        }
        System.out.println("filter_val - " + "district_txt" + " : " + district_txt);
        System.out.println("filter_val - " + "taluk_txt" + " : " + taluk_txt);
        System.out.println("filter_val - " + "category" + " : " + event);
        new date_load().execute();
    }
}