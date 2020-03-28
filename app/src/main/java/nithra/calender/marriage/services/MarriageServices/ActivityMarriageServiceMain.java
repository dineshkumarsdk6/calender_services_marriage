package nithra.calender.marriage.services.MarriageServices;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;

import nithra.calender.marriage.services.Fragment.FragmentUserView;
import nithra.calender.marriage.services.Fragment.FragmentAdminView;
import nithra.calender.marriage.services.Pojo.City_Model;
import nithra.calender.marriage.services.Pojo.State;
import nithra.calender.marriage.services.R;
import nithra.calender.marriage.services.SharedPreference;
import nithra.calender.marriage.services.Utils;


public class ActivityMarriageServiceMain extends AppCompatActivity {
    Menu menu;
    Toolbar mToolbar;
    SharedPreference sharedPreference = new SharedPreference();
    TabLayout tabLayout;
    FrameLayout container;
    public static ArrayList<HashMap<String, Object>> filter_values;
    ArrayList<City_Model> dist_item = new ArrayList<>();
    ArrayList<City_Model> city_models = new ArrayList<>();
    ArrayList<State> avail_check_item = new ArrayList<>();
    int click_val = 0;
    public static int onload = 0;
    public static int onedit = 0;
    int val = 0;
    int val1 = 0;
    int clickFlag = 0;
    String user_service_type_name = "";
    AppCompatEditText district_spinner, taluk_spinner;
    LinearLayout flexboxLayoutCheckboxVila;
    String result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_mandapam_list);

        user_service_type_name = sharedPreference.getString(ActivityMarriageServiceMain.this, "user_service_type_name");
        System.out.println("ClickType : " + sharedPreference.getString(ActivityMarriageServiceMain.this, "user_service_type_name"));

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        filter_values = new ArrayList<>();

        tabLayout = findViewById(R.id.tablayout);
        container = findViewById(R.id.container);

        if (user_service_type_name.equals("1")) {
            getSupportActionBar().setTitle("மண்டபங்கள்");
            tabLayout.addTab(tabLayout.newTab().setText("மண்டபங்கள்"));
            tabLayout.addTab(tabLayout.newTab().setText("எனது மண்டபங்கள்"));
        }

        if (user_service_type_name.equals("2")) {
            getSupportActionBar().setTitle("டெக்கரேஷன்");
            tabLayout.addTab(tabLayout.newTab().setText("டெக்கரேஷன்"));
            tabLayout.addTab(tabLayout.newTab().setText("எனது டெக்கரேஷன்"));
        }

        if (user_service_type_name.equals("3")) {
            getSupportActionBar().setTitle("போட்டோகிராபி");
            tabLayout.addTab(tabLayout.newTab().setText("போட்டோகிராபி"));
            tabLayout.addTab(tabLayout.newTab().setText("எனது போட்டோகிராபி"));
        }

        if (user_service_type_name.equals("4")) {
            getSupportActionBar().setTitle("இசை குழு");
            tabLayout.addTab(tabLayout.newTab().setText("இசை குழு"));
            tabLayout.addTab(tabLayout.newTab().setText("எனது இசை குழு"));
        }

        if (user_service_type_name.equals("5")) {
            getSupportActionBar().setTitle("அழகு நிலையம்");
            tabLayout.addTab(tabLayout.newTab().setText("அழகு நிலையம்"));
            tabLayout.addTab(tabLayout.newTab().setText("எனது அழகு நிலையம்"));
        }

        if (user_service_type_name.equals("6")) {
            getSupportActionBar().setTitle("கேட்டரிங்");
            tabLayout.addTab(tabLayout.newTab().setText("கேட்டரிங்"));
            tabLayout.addTab(tabLayout.newTab().setText("எனது கேட்டரிங்"));
        }


        //mToolbar.setBackgroundColor(Utils.get_color(ActivityMandapam.this));

        if (!sharedPreference.getString(ActivityMarriageServiceMain.this, "user_reg_status").equals("User Registered Successfully")) {
            if (val1 == 0) {
                val1 = 1;
                frag_set(-1);
            }
        }


        // frag_set(-1);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                onedit = 0;
                frag_set(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sharedPreference.getString(ActivityMarriageServiceMain.this, "user_reg_status").equals("User Registered Successfully")) {
            if (val == 0) {
                val = 1;
                tabLayout.setVisibility(View.VISIBLE);
                TabLayout.Tab tab = tabLayout.getTabAt(1);
                tab.select();
                if (menu != null) {
                    MenuItem action_filter = menu.findItem(R.id.action_filter);
                    action_filter.setIcon(R.drawable.ic_account_circle_black_24dp);
                    action_filter.setVisible(true);
                }
            }

        } else {
            tabLayout.setVisibility(View.GONE);
        }
    }


    public void frag_set(int val) {
        container.removeAllViews();
        if (val == 0) {
            clickFlag = 0;
            FragmentUserView fragment = new FragmentUserView();
            getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentById(R.id.container)).commit();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.container, fragment, fragment.toString());
            //fragmentTransaction.add(R.id.container, fragment);
            fragmentTransaction.commit();

            if (menu != null) {
                MenuItem action_filter = menu.findItem(R.id.action_filter);
                action_filter.setIcon(R.drawable.filter);
                action_filter.setVisible(true);
            }

        } else if (val == 1) {
            clickFlag = 1;
            onload = 0;
            FragmentAdminView fragment = new FragmentAdminView();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.container, fragment);
            fragmentTransaction.commit();
            if (menu != null) {
                MenuItem action_filter = menu.findItem(R.id.action_filter);
                action_filter.setIcon(R.drawable.ic_account_circle_black_24dp);
                action_filter.setVisible(true);
            }
        } else {
            clickFlag = 0;
            FragmentUserView fragment = new FragmentUserView();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.container, fragment);
            fragmentTransaction.commit();
            if (menu != null) {
                MenuItem action_filter = menu.findItem(R.id.action_filter);
                action_filter.setIcon(R.drawable.filter);
                action_filter.setVisible(true);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu _menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu = _menu;
        getMenuInflater().inflate(R.menu.toolmenu_mandapam, _menu);
        MenuItem action_filter = _menu.findItem(R.id.action_filter);
        MenuItem action_dash = _menu.findItem(R.id.action_add_new);
        action_filter.setVisible(true);
        if (clickFlag == 0) {
            action_filter.setIcon(R.drawable.filter);
        } else {
            action_filter.setIcon(R.drawable.ic_account_circle_black_24dp);
        }
        return super.onCreateOptionsMenu(_menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }
        if (menuItem.getItemId() == R.id.action_filter) {
            if (Utils.isNetworkAvailable(ActivityMarriageServiceMain.this)) {
                if (clickFlag == 0) {
                    Intent i = new Intent(ActivityMarriageServiceMain.this, ActivityMarriageServiceFilter.class);
                    i.putExtra("user_service_type_name", "" + user_service_type_name);
                    startActivity(i);

                } else {
                    Intent i = new Intent(ActivityMarriageServiceMain.this, ActivityUserReg.class);
                    i.putExtra("from", "not_reg");
                    startActivity(i);
                }

            } else {
                Utils.toast_center(ActivityMarriageServiceMain.this, "இணையதள சேவையை சரிபார்க்கவும் ");
            }

        }
        if (menuItem.getItemId() == R.id.action_add_new) {
            if (!sharedPreference.getString(ActivityMarriageServiceMain.this, "user_reg_status").equals("User Registered Successfully")) {
                //sharedPreference.putString(ActivityMandapam.this, "prigi_modes" + sharedPreference.getString(ActivityMandapam.this, "progithar_type"), "register");
                if (Utils.isNetworkAvailable(ActivityMarriageServiceMain.this)) {
                    Intent i = new Intent(ActivityMarriageServiceMain.this, ActivityNumberReg.class);
                    startActivity(i);
                } else {
                    Utils.toast_center(ActivityMarriageServiceMain.this, "இணையதள சேவையை சரிபார்க்கவும் ");
                }

            } else {
                if (Utils.isNetworkAvailable(ActivityMarriageServiceMain.this)) {
                    Intent i = new Intent(ActivityMarriageServiceMain.this, ActivityMarriageServiceAdd.class);
                    i.putExtra("data_status", "not_from_edit");
                    i.putExtra("user_service_type_name", user_service_type_name);
                    startActivity(i);

                } else {
                    Utils.toast_center(ActivityMarriageServiceMain.this, "இணையதள சேவையை சரிபார்க்கவும் ");
                }
            }

        }
        return super.onOptionsItemSelected(menuItem);
    }
}
