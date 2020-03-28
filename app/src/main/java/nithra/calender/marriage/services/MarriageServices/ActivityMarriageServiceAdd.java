package nithra.calender.marriage.services.MarriageServices;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nithra.calender.marriage.services.GlideApp;
import nithra.calender.marriage.services.HttpHandler;
import nithra.calender.marriage.services.ImageLoadingUtils;
import nithra.calender.marriage.services.MultipartUtility;
import nithra.calender.marriage.services.Pojo.City_Model;
import nithra.calender.marriage.services.Pojo.State;
import nithra.calender.marriage.services.R;
import nithra.calender.marriage.services.SharedPreference;
import nithra.calender.marriage.services.Utils;
import nithra.calender.marriage.services.crop_image.CropImage;
import nithra.calender.marriage.services.crop_image.CropImageView;
import nithra.calender.marriage.services.image_slider.Activity_slider;
import nithra.calender.marriage.services.searchdialog.ContactSearchDialogCompat;
import nithra.calender.marriage.services.searchdialog.core.BaseSearchDialogCompat;
import nithra.calender.marriage.services.searchdialog.core.SearchResultListener;
import nithra.calender.marriage.services.viewpagerindicator.CirclePageIndicator;

public class ActivityMarriageServiceAdd extends AppCompatActivity implements View.OnClickListener {

    ImageView imagePhoneAdd;
    LinearLayout layoutAlterMobileNumber,
            layoutAlterMobileNumberLine,
            layoutCarParking,
            layoutExtraCarParkLine,
            layoutExtraRoom,
            layoutExtraRoomLine,
            layoutAlterEmailTitle,
            layoutAlterEmail,
            layoutAlterEmailLine;
    TextView district_spinner, taluk_spinner, district_spinner_service, text_title_vila, text_title_type, text_title_camera;
    ImageView image_title_camera;
    int click_val = 0;
    ArrayList<City_Model> dist_item = new ArrayList<>();
    ArrayList<City_Model> city_models = new ArrayList<>();
    ArrayList<State> avail_check_item = new ArrayList<>();
    ArrayList<State> avail_check_item_type = new ArrayList<>();
    final ArrayList<State> district_array = new ArrayList<>();
    final ArrayList<State> district_array_checked = new ArrayList<>();
    ArrayList<String> selectedDistrict = new ArrayList<>();
    ArrayList<String> selectedDistrictName = new ArrayList<>();
    Customs_Adapter_list adapter;
    public static ArrayList<HashMap<String, Object>> filter_values;
    LinearLayout flexboxLayoutCheckboxVila, flexboxLayoutCheckboxType;
    ;
    private RadioGroup
            radioMandapamVagai,
            radioMandapamRent,
            radioDiningHall,
            radioCarParking,
            radioRoom,
            radioGenerator,
            radioAudio,
            radioExperince,
            radioSamayalType,
            radioParlourType;

    private RadioButton
            radioMandapamVagaiButton,
            radioMandapamRentButton,
            radioDiningHallButton,
            radioCarParkingButton,
            radioRoomButton,
            radioGeneratorButton,
            radioAudioButton,
            radioExperinceButton,
            radioSamayalTypeButton,
            radioParlourTypeButton;

    private RadioButton radioParlourType1, radioParlourType2, radioParlourType3, radioParlourType4;
    private RadioButton radioSamayalType1, radioSamayalType2, radioSamayalType3;
    private RadioButton radioExperince1, radioExperince2, radioExperince3, radioExperince4;
    private RadioButton radioMandapamVagai1, radioMandapamVagai2, radioMandapamVagai3;
    private RadioButton radioMandapamRent1, radioMandapamRent2, radioMandapamRent3;
    private RadioButton radioDiningHall1, radioDiningHall2, radioDiningHall3;
    private RadioButton radioCarParkingYes, radioCarParkingNo;
    private RadioButton radioRoomYes, radioRoomNo;
    private RadioButton radioGeneratorYes, radioGeneratorNo;
    private RadioButton radioAudioYes, radioAudioNo;

    Button cardSubmit;
    String result = "";
    Toolbar mToolbar;
    String action_type = "",
            userId = "",
            str_district_id = "",
            str_city_id = "",
            str_vagai = "",
            str_rent_vagai = "",
            str_vila = "",
            str_type = "",
            str_dining = "",
            str_carparking = "",
            str_carparking_text = "",
            str_room = "",
            str_room_text = "",
            str_generator = "",
            str_audio = "",
            str_map = "",
            str_long = "",
            str_lat = "",
            str_exp = "",
            str_email = "",
            str_camera_feature = "",
            str_service_place = "",
            str_service_place_name = "",
            str_org_name = "",
            str_contact_name = "",
            str_contact_number1 = "",
            str_contact_number2 = "",
            str_address = "",
            str_landmark = "",
            str_pincode = "",
            str_insta = "",
            str_fb = "",
            str_youtube = "",
            str_web = "",
            str_others = "",
            str_samayal_type = "",
            str_parlour_type = "";

    AppCompatEditText editTextOrgName,
            editTextContactName,
            editTextContactNumber1,
            editTextContactNumber2,
            editTextEmail,
            editTextAddress,
            editTextPincode,
            editTextLandmark,
            editTextInsta,
            editTextFb,
            editTextYoutube,
            editTextWeb,
            editTextOther,
            editTextLatitude,
            editTextLongitude,
            editTextMap,
            editTextCarPark,
            editTextRoom,
            editTextCameraFeature;

    ImageView img1, img2, img3, img4, img5, img6, img7, img8, img9, img10, img11, img12;
    ImageView img1_r, img2_r, img3_r, img4_r, img5_r, img6_r, img7_r, img8_r, img9_r, img10_r, img11_r, img12_r;
    int img_click;
    String str_img1 = "", str_img2 = "", str_img3 = "", str_img4 = "",
            str_img5 = "", str_img6 = "", str_img7 = "", str_img8 = "",
            str_img9 = "", str_img10 = "", str_img11 = "", str_img12 = "";
    String list_id = "", data_status = "", user_service_type_name = "";
    String list_images = "";
    private ImageLoadingUtils utils;
    SharedPreference sharedPreference = new SharedPreference();
    String image_tag_url = "";
    int edit = 0;
    LayoutInflater inflater;

    CardView card_parlour_type,
            card_samayal_type,
            card_camera_features,
            card_deceration_type,
            card_deceration_service_place,
            card_mandapa_vagai,
            card_mandapa_rent_vagai,
            card_mandapa_dining,
            card_mandapa_car,
            card_mandapa_room,
            card_mandapa_generator,
            card_mandapa_audio,
            card_experince,
            card_map,
            card_submit;

    TextView textViewOrgName;

    @Override
    public void onBackPressed() {
        backWarn();
    }

    public void backWarn() {

        final Dialog no_datefun = new Dialog(ActivityMarriageServiceAdd.this,
                android.R.style.Theme_DeviceDefault_Dialog_NoActionBar_MinWidth);
        no_datefun.setContentView(R.layout.nodate_dia);
        TextView btnSet = no_datefun.findViewById(R.id.text_no);
        TextView btnok = no_datefun.findViewById(R.id.text_yes);
        AppCompatTextView head_txt = no_datefun.findViewById(R.id.text_head);
        AppCompatTextView editText1 = no_datefun.findViewById(R.id.text_content);
        btnSet.setText("ஆம்");
        btnok.setText("இல்லை");
        head_txt.setVisibility(View.GONE);

        if (edit != 1) {
            editText1.setText("நீங்கள் எந்த விவரங்களையும் சேமிக்கவில்லை. இந்த பகுதியில் இருந்து வெளியேற வேண்டுமா?");
        } else {
            editText1.setText("நீங்கள் இந்த பகுதியில் இருந்து வெளியேற வேண்டுமா?");
        }


 /*       btnSet.setBackgroundColor(Utils.get_color(ActivityMandapamAdd.this));
        btnok.setBackgroundColor(Utils.get_color(ActivityMandapamAdd.this));*/

        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                no_datefun.dismiss();
                finish();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_mandapam);

        textViewOrgName = findViewById(R.id.textViewOrgName);

        editTextOrgName = findViewById(R.id.editTextOrgName);
        editTextContactName = findViewById(R.id.editTextContactName);
        editTextContactNumber1 = findViewById(R.id.editTextContactNumber1);
        editTextContactNumber2 = findViewById(R.id.editTextContactNumber2);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextPincode = findViewById(R.id.editTextPincode);
        editTextLandmark = findViewById(R.id.editTextLandmark);
        editTextInsta = findViewById(R.id.editTextInsta);
        editTextFb = findViewById(R.id.editTextFb);
        editTextYoutube = findViewById(R.id.editTextYoutube);
        editTextWeb = findViewById(R.id.editTextWeb);
        editTextOther = findViewById(R.id.editTextOther);
        editTextLatitude = findViewById(R.id.editTextLatitude);
        editTextLongitude = findViewById(R.id.editTextLongitude);
        editTextCarPark = findViewById(R.id.editTextCarPark);
        editTextRoom = findViewById(R.id.editTextRoom);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextCameraFeature = findViewById(R.id.editTextCameraFeature);

        card_samayal_type = findViewById(R.id.card_samayal_type);
        card_parlour_type = findViewById(R.id.card_parlour_type);
        card_camera_features = findViewById(R.id.card_camera_features);
        card_deceration_type = findViewById(R.id.card_deceration_type);
        card_deceration_service_place = findViewById(R.id.card_deceration_service_place);
        card_mandapa_vagai = findViewById(R.id.card_mandapa_vagai);
        card_mandapa_rent_vagai = findViewById(R.id.card_mandapa_rent_vagai);
        card_mandapa_dining = findViewById(R.id.card_mandapa_dining);
        card_mandapa_car = findViewById(R.id.card_mandapa_car);
        card_mandapa_room = findViewById(R.id.card_mandapa_room);
        card_mandapa_generator = findViewById(R.id.card_mandapa_generator);
        card_mandapa_audio = findViewById(R.id.card_mandapa_audio);
        card_map = findViewById(R.id.card_map);
        card_experince = findViewById(R.id.card_experince);

        radioMandapamVagai = findViewById(R.id.radioMandapamVagai);
        radioMandapamRent = findViewById(R.id.radioMandapamRent);
        radioDiningHall = findViewById(R.id.radioDiningHall);
        radioCarParking = findViewById(R.id.radioCarParking);
        radioRoom = findViewById(R.id.radioRoom);
        radioGenerator = findViewById(R.id.radioGenerator);
        radioAudio = findViewById(R.id.radioAudio);
        radioExperince = findViewById(R.id.radioExperince);
        radioSamayalType = findViewById(R.id.radioSamayalType);
        radioParlourType = findViewById(R.id.radioParlourType);

        radioParlourType1 = findViewById(R.id.radioParlourType1);
        radioParlourType2 = findViewById(R.id.radioParlourType2);
        radioParlourType3 = findViewById(R.id.radioParlourType3);
        radioSamayalType1 = findViewById(R.id.radioSamayalType1);
        radioSamayalType2 = findViewById(R.id.radioSamayalType2);
        radioSamayalType3 = findViewById(R.id.radioSamayalType3);
        radioExperince1 = findViewById(R.id.radioExperince1);
        radioExperince2 = findViewById(R.id.radioExperince2);
        radioExperince3 = findViewById(R.id.radioExperince3);
        radioExperince4 = findViewById(R.id.radioExperince4);
        radioMandapamVagai1 = findViewById(R.id.radioMandapamVagai1);
        radioMandapamVagai2 = findViewById(R.id.radioMandapamVagai2);
        radioMandapamVagai3 = findViewById(R.id.radioMandapamVagai3);
        radioMandapamRent1 = findViewById(R.id.radioMandapamRent1);
        radioMandapamRent2 = findViewById(R.id.radioMandapamRent2);
        radioMandapamRent3 = findViewById(R.id.radioMandapamRent3);
        radioDiningHall1 = findViewById(R.id.radioDiningHall1);
        radioDiningHall2 = findViewById(R.id.radioDiningHall2);
        radioDiningHall3 = findViewById(R.id.radioDiningHall3);
        radioCarParkingYes = findViewById(R.id.radioCarParkingYes);
        radioCarParkingNo = findViewById(R.id.radioCarParkingNo);
        radioRoomYes = findViewById(R.id.radioRoomYes);
        radioRoomNo = findViewById(R.id.radioRoomNo);
        radioGeneratorYes = findViewById(R.id.radioGeneratorYes);
        radioGeneratorNo = findViewById(R.id.radioGeneratorNo);
        radioAudioYes = findViewById(R.id.radioAudioYes);
        radioAudioNo = findViewById(R.id.radioAudioNo);

        imagePhoneAdd = findViewById(R.id.imagePhoneAdd);
        cardSubmit = findViewById(R.id.cardSubmit);
        layoutAlterMobileNumber = findViewById(R.id.layoutAlterMobileNumber);
        layoutAlterMobileNumberLine = findViewById(R.id.layoutAlterMobileNumberLine);
        layoutCarParking = findViewById(R.id.layoutCarParking);
        layoutExtraCarParkLine = findViewById(R.id.layoutExtraCarParkLine);
        layoutExtraRoom = findViewById(R.id.layoutExtraRoom);
        layoutExtraRoomLine = findViewById(R.id.layoutExtraRoomLine);
        layoutAlterEmailTitle = findViewById(R.id.layoutAlterEmailTitle);
        layoutAlterEmail = findViewById(R.id.layoutAlterEmail);
        layoutAlterEmailLine = findViewById(R.id.layoutAlterEmailLine);
        flexboxLayoutCheckboxVila = findViewById(R.id.cate_lay_vila);
        flexboxLayoutCheckboxType = findViewById(R.id.cate_lay_type);
        district_spinner = findViewById(R.id.district_spinner);
        taluk_spinner = findViewById(R.id.taluk_spinner);
        district_spinner_service = findViewById(R.id.district_spinner_service);
        text_title_vila = findViewById(R.id.text_title_vila);
        text_title_type = findViewById(R.id.text_title_type);
        text_title_camera = findViewById(R.id.text_title_camera);
        image_title_camera = findViewById(R.id.image_title_camera);

        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
        img4 = findViewById(R.id.img4);
        img5 = findViewById(R.id.img5);
        img6 = findViewById(R.id.img6);
        img7 = findViewById(R.id.img7);
        img8 = findViewById(R.id.img8);
        img9 = findViewById(R.id.img9);
        img10 = findViewById(R.id.img10);
        img11 = findViewById(R.id.img11);
        img12 = findViewById(R.id.img12);

        img1_r = findViewById(R.id.img1_r);
        img2_r = findViewById(R.id.img2_r);
        img3_r = findViewById(R.id.img3_r);
        img4_r = findViewById(R.id.img4_r);
        img5_r = findViewById(R.id.img5_r);
        img6_r = findViewById(R.id.img6_r);
        img7_r = findViewById(R.id.img7_r);
        img8_r = findViewById(R.id.img8_r);
        img9_r = findViewById(R.id.img9_r);
        img10_r = findViewById(R.id.img10_r);
        img11_r = findViewById(R.id.img11_r);
        img12_r = findViewById(R.id.img12_r);

        img1_r.setVisibility(View.GONE);
        img2_r.setVisibility(View.GONE);
        img3_r.setVisibility(View.GONE);
        img4_r.setVisibility(View.GONE);
        img5_r.setVisibility(View.GONE);
        img6_r.setVisibility(View.GONE);
        img7_r.setVisibility(View.GONE);
        img8_r.setVisibility(View.GONE);
        img9_r.setVisibility(View.GONE);
        img10_r.setVisibility(View.GONE);
        img11_r.setVisibility(View.GONE);
        img12_r.setVisibility(View.GONE);

        img1.setOnClickListener(this);
        img2.setOnClickListener(this);
        img3.setOnClickListener(this);
        img4.setOnClickListener(this);
        img5.setOnClickListener(this);
        img6.setOnClickListener(this);
        img7.setOnClickListener(this);
        img8.setOnClickListener(this);
        img9.setOnClickListener(this);
        img10.setOnClickListener(this);
        img11.setOnClickListener(this);
        img12.setOnClickListener(this);

        img1_r.setOnClickListener(this);
        img2_r.setOnClickListener(this);
        img3_r.setOnClickListener(this);
        img4_r.setOnClickListener(this);
        img5_r.setOnClickListener(this);
        img6_r.setOnClickListener(this);
        img7_r.setOnClickListener(this);
        img8_r.setOnClickListener(this);
        img9_r.setOnClickListener(this);
        img10_r.setOnClickListener(this);
        img11_r.setOnClickListener(this);
        img12_r.setOnClickListener(this);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        utils = new ImageLoadingUtils(ActivityMarriageServiceAdd.this);

        Intent intent = getIntent();
        if (intent != null) {
            data_status = intent.getStringExtra("data_status");
            user_service_type_name = intent.getStringExtra("user_service_type_name");
        } else {
            data_status = "";
            user_service_type_name = "";
        }

        editTextContactName.setHint("உரிமையாளர்/மேலாளர் பெயர்");
        editTextContactNumber1.setHint("அலைபேசி எண்");
        editTextContactNumber2.setHint("மாற்று அலைபேசி எண்");
        editTextEmail.setHint("மின்னஞ்சல்");

       /* editTextInsta.setHint("https://www.instagram.com/");
        editTextFb.setHint("https://www.facebook.com/");
        editTextYoutube.setHint("https://www.youtube.com/results?search_query=");*/

        if (user_service_type_name.equals("1")) {
            textViewOrgName.setText("மண்டபத்தின் பெயர்*");
            editTextOrgName.setHint("மண்டபத்தின் பெயர்");
            text_title_vila.setText("மண்டப விழா :");
            card_mandapa_vagai.setVisibility(View.VISIBLE);
            card_mandapa_rent_vagai.setVisibility(View.VISIBLE);
            card_mandapa_dining.setVisibility(View.VISIBLE);
            card_mandapa_car.setVisibility(View.VISIBLE);
            card_mandapa_room.setVisibility(View.VISIBLE);
            card_mandapa_generator.setVisibility(View.VISIBLE);
            card_mandapa_audio.setVisibility(View.VISIBLE);
            card_map.setVisibility(View.VISIBLE);
            card_experince.setVisibility(View.GONE);
            layoutAlterEmailTitle.setVisibility(View.GONE);
            layoutAlterEmail.setVisibility(View.GONE);
            layoutAlterEmailLine.setVisibility(View.GONE);

            if (data_status.equals("from_edit")) {
                getSupportActionBar().setTitle("மண்டபம் விவரங்கள் புதுப்பிக்க");
                cardSubmit.setText("புதுப்பிக்க");
            } else {
                getSupportActionBar().setTitle("மண்டபம் விவரங்கள் சேர்க்க");
                cardSubmit.setText("சேர்க்க");
            }

            action_type = "get_event";
            new getCategory().execute(action_type, user_service_type_name, Utils.url_calender_service);
        }

        if (user_service_type_name.equals("2")) {
            textViewOrgName.setText("டெக்கரேஷன் பெயர்*");
            editTextOrgName.setHint("டெக்கரேஷன் பெயர்");
            text_title_vila.setText("டெக்கரேஷன் விழா :");
            text_title_type.setText("டெக்கரேஷன் செய்யப்படும் வகைகள் :");
            card_deceration_type.setVisibility(View.GONE);
            card_deceration_service_place.setVisibility(View.VISIBLE);

            if (data_status.equals("from_edit")) {
                getSupportActionBar().setTitle("டெக்கரேஷன் விவரங்கள் புதுப்பிக்க");
                cardSubmit.setText("புதுப்பிக்க");
            } else {
                getSupportActionBar().setTitle("டெக்கரேஷன் விவரங்கள் சேர்க்க");
                cardSubmit.setText("சேர்க்க");
            }

            action_type = "get_event";
            new getCategory().execute(action_type, user_service_type_name, Utils.url_calender_service);
        }

        if (user_service_type_name.equals("3")) {
            textViewOrgName.setText("போட்டோகிராபி பெயர்*");
            editTextOrgName.setHint("போட்டோகிராபி பெயர்");
            text_title_vila.setText("போட்டோகிராபி விழா : ");
            text_title_type.setText("மற்ற சேவைகள் : ");
            editTextCameraFeature.setHint("");
            card_deceration_type.setVisibility(View.VISIBLE);
            card_camera_features.setVisibility(View.VISIBLE);

            if (data_status.equals("from_edit")) {
                getSupportActionBar().setTitle("போட்டோகிராபி விவரங்கள் புதுப்பிக்க");
                cardSubmit.setText("புதுப்பிக்க");
            } else {
                getSupportActionBar().setTitle("போட்டோகிராபி விவரங்கள் சேர்க்க");
                cardSubmit.setText("சேர்க்க");
            }

            action_type = "get_event";
            new getCategory().execute(action_type, user_service_type_name, Utils.url_calender_service);
        }

        if (user_service_type_name.equals("4")) {
            textViewOrgName.setText("இசை குழுவின் பெயர்*");
            editTextOrgName.setHint("இசை குழுவின் பெயர்");
            text_title_vila.setText("இசை குழு செய்யப்படும் விழா : ");
            text_title_type.setText("இசை குழுவின் வகைகள் : ");
            card_deceration_type.setVisibility(View.VISIBLE);

            if (data_status.equals("from_edit")) {
                getSupportActionBar().setTitle("இசை குழு விவரங்கள் புதுப்பிக்க");
                cardSubmit.setText("புதுப்பிக்க");
            } else {
                getSupportActionBar().setTitle("இசை குழு விவரங்கள் சேர்க்க");
                cardSubmit.setText("சேர்க்க");
            }

            action_type = "get_event";
            new getCategory().execute(action_type, user_service_type_name, Utils.url_calender_service);
        }

        if (user_service_type_name.equals("5")) {
            textViewOrgName.setText("அழகு நிலையத்தின் பெயர்*");
            editTextOrgName.setHint("அழகு நிலையத்தின் பெயர்");
            text_title_vila.setText("நிகழ்ச்சிகள் : ");
            text_title_type.setText("வசதிகள் : ");
            card_deceration_type.setVisibility(View.VISIBLE);
            card_parlour_type.setVisibility(View.VISIBLE);

            if (data_status.equals("from_edit")) {
                getSupportActionBar().setTitle("அழகு நிலைய விவரங்கள் புதுப்பிக்க");
                cardSubmit.setText("புதுப்பிக்க");
            } else {
                getSupportActionBar().setTitle("அழகு நிலைய விவரங்கள் சேர்க்க");
                cardSubmit.setText("சேர்க்க");
            }

            action_type = "get_event";
            new getCategory().execute(action_type, user_service_type_name, Utils.url_calender_service);
        }

        if (user_service_type_name.equals("6")) {
            textViewOrgName.setText("கேட்டரிங் நிறுவனத்தின் பெயர்*");
            editTextOrgName.setHint("கேட்டரிங் நிறுவனத்தின் பெயர்");
            text_title_vila.setText("கேட்டரிங் செய்யப்படும் நிகழ்ச்சிகள் : ");
            text_title_type.setText("மற்ற சேவைகள் : ");
            image_title_camera.setImageDrawable(getResources().getDrawable(R.drawable.icon_people_count));
            text_title_camera.setText("அதிகபட்ச விருந்தினர் எண்ணிக்கை :");
            editTextCameraFeature.setHint("");
            editTextCameraFeature.setInputType(InputType.TYPE_CLASS_NUMBER);
            card_deceration_type.setVisibility(View.VISIBLE);
            card_camera_features.setVisibility(View.VISIBLE);
            card_samayal_type.setVisibility(View.VISIBLE);

            if (data_status.equals("from_edit")) {
                getSupportActionBar().setTitle("கேட்டரிங் விவரங்கள் புதுப்பிக்க");
                cardSubmit.setText("புதுப்பிக்க");
            } else {
                getSupportActionBar().setTitle("கேட்டரிங் விவரங்கள் சேர்க்க");
                cardSubmit.setText("சேர்க்க");
            }

            action_type = "get_event";
            new getCategory().execute(action_type, user_service_type_name, Utils.url_calender_service);
        }


        filter_values = new ArrayList<>();
        userId = sharedPreference.getString(ActivityMarriageServiceAdd.this, "user_reg_id");

        radioSamayalType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                Utils.hideKeyboardFrom(ActivityMarriageServiceAdd.this, radioGroup);
                radioSamayalTypeButton = radioGroup.findViewById(checkedId);
                boolean isChecked = radioSamayalTypeButton.isChecked();
                if (isChecked) {

                    if (radioSamayalTypeButton.getText().equals("சைவம்")) {
                        str_samayal_type = "1";
                    } else if (radioSamayalTypeButton.getText().equals("அசைவம்")) {
                        str_samayal_type = "2";
                    } else if (radioSamayalTypeButton.getText().equals("இரண்டும்")) {
                        str_samayal_type = "3";
                    }
                }
            }
        });

        radioParlourType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                Utils.hideKeyboardFrom(ActivityMarriageServiceAdd.this, radioGroup);
                radioParlourTypeButton = radioGroup.findViewById(checkedId);
                boolean isChecked = radioParlourTypeButton.isChecked();
                if (isChecked) {

                    if (radioParlourTypeButton.getText().equals("ஆண்கள் மட்டும்")) {
                        str_parlour_type = "1";
                    } else if (radioParlourTypeButton.getText().equals("பெண்கள் மட்டும்")) {
                        str_parlour_type = "2";
                    } else if (radioParlourTypeButton.getText().equals("இருவருக்கும்")) {
                        str_parlour_type = "3";
                    }
                }
            }
        });

        radioExperince.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                Utils.hideKeyboardFrom(ActivityMarriageServiceAdd.this, radioGroup);
                radioExperinceButton = radioGroup.findViewById(checkedId);
                boolean isChecked = radioExperinceButton.isChecked();
                if (isChecked) {

                    if (radioExperinceButton.getText().equals("0 - 1 வருடம்")) {
                        str_exp = "1";
                    } else if (radioExperinceButton.getText().equals("1 - 3 வருடங்கள்")) {
                        str_exp = "2";
                    } else if (radioExperinceButton.getText().equals("3 - 5 வருடங்கள்")) {
                        str_exp = "3";
                    } else if (radioExperinceButton.getText().equals("5 வருடங்களுக்கு மேல்")) {
                        str_exp = "4";
                    }
                }
            }
        });

        radioMandapamVagai.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                Utils.hideKeyboardFrom(ActivityMarriageServiceAdd.this, radioGroup);
                radioMandapamVagaiButton = radioGroup.findViewById(checkedId);
                boolean isChecked = radioMandapamVagaiButton.isChecked();
                if (isChecked) {

                    if (radioMandapamVagaiButton.getText().equals("AC")) {
                        str_vagai = "1";
                    } else if (radioMandapamVagaiButton.getText().equals("NON AC")) {
                        str_vagai = "2";
                    } else if (radioMandapamVagaiButton.getText().equals("இரண்டும்")) {
                        str_vagai = "3";
                    }
                }
            }
        });

        radioMandapamRent.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                Utils.hideKeyboardFrom(ActivityMarriageServiceAdd.this, radioGroup);
                radioMandapamRentButton = radioGroup.findViewById(checkedId);
                boolean isChecked = radioMandapamRentButton.isChecked();
                if (isChecked) {

                    if (radioMandapamRentButton.getText().equals("நாள் வாடகை")) {
                        str_rent_vagai = "1";
                    } else if (radioMandapamRentButton.getText().equals("நேர வாடகை")) {
                        str_rent_vagai = "2";
                    } else if (radioMandapamRentButton.getText().equals("இரண்டும்")) {
                        str_rent_vagai = "3";
                    }
                }
            }
        });

        radioDiningHall.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                Utils.hideKeyboardFrom(ActivityMarriageServiceAdd.this, radioGroup);
                radioDiningHallButton = radioGroup.findViewById(checkedId);
                boolean isChecked = radioDiningHallButton.isChecked();
                if (isChecked) {

                    if (radioDiningHallButton.getText().equals("அமர்ந்து சாப்பிடும் முறை")) {
                        str_dining = "2";
                    } else if (radioDiningHallButton.getText().equals("பஃபே முறை")) {
                        str_dining = "1";
                    } else if (radioDiningHallButton.getText().equals("இரண்டும்")) {
                        str_dining = "3";
                    }

                }
            }
        });

        radioCarParking.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                Utils.hideKeyboardFrom(ActivityMarriageServiceAdd.this, radioGroup);
                radioCarParkingButton = radioGroup.findViewById(checkedId);
                boolean isChecked = radioCarParkingButton.isChecked();
                if (isChecked) {

                    if (radioCarParkingButton.getText().equals("உண்டு")) {
                        layoutCarParking.setVisibility(View.VISIBLE);
                        layoutExtraCarParkLine.setVisibility(View.VISIBLE);
                        str_carparking = "1";
                    } else {
                        layoutCarParking.setVisibility(View.GONE);
                        layoutExtraCarParkLine.setVisibility(View.GONE);
                        str_carparking = "2";
                    }
                }
            }
        });

        radioRoom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                Utils.hideKeyboardFrom(ActivityMarriageServiceAdd.this, radioGroup);
                radioRoomButton = radioGroup.findViewById(checkedId);
                boolean isChecked = radioRoomButton.isChecked();
                if (isChecked) {

                    if (radioRoomButton.getText().equals("உண்டு")) {
                        layoutExtraRoom.setVisibility(View.VISIBLE);
                        layoutExtraRoomLine.setVisibility(View.VISIBLE);
                        str_room = "1";
                    } else {
                        layoutExtraRoom.setVisibility(View.GONE);
                        layoutExtraRoomLine.setVisibility(View.GONE);
                        str_room = "2";
                    }
                }
            }
        });

        radioGenerator.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                Utils.hideKeyboardFrom(ActivityMarriageServiceAdd.this, radioGroup);
                radioGeneratorButton = radioGroup.findViewById(checkedId);
                boolean isChecked = radioGeneratorButton.isChecked();
                if (isChecked) {

                    if (radioGeneratorButton.getText().equals("உண்டு")) {
                        str_generator = "1";
                    } else {
                        str_generator = "2";
                    }
                }
            }
        });

        radioAudio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                Utils.hideKeyboardFrom(ActivityMarriageServiceAdd.this, radioGroup);
                radioAudioButton = radioGroup.findViewById(checkedId);
                boolean isChecked = radioAudioButton.isChecked();
                if (isChecked) {

                    if (radioAudioButton.getText().equals("உண்டு")) {
                        str_audio = "1";
                    } else {
                        str_audio = "2";
                    }
                }
            }
        });

        imagePhoneAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TransitionManager.beginDelayedTransition(layoutAlterMobileNumber);
                if (layoutAlterMobileNumber.getVisibility() == View.VISIBLE) {
                    layoutAlterMobileNumber.setVisibility(View.GONE);
                    layoutAlterMobileNumberLine.setVisibility(View.GONE);
                    imagePhoneAdd.setImageResource(R.drawable.ic_add_black_24dp);
                } else if (layoutAlterMobileNumber.getVisibility() == View.GONE) {
                    layoutAlterMobileNumber.setVisibility(View.VISIBLE);
                    layoutAlterMobileNumberLine.setVisibility(View.VISIBLE);
                    imagePhoneAdd.setImageResource(R.drawable.ic_remove_black_24dp);
                }

            }

        });

        district_spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click_val = 0;
                if (Utils.isNetworkAvailable(ActivityMarriageServiceAdd.this) == false) {
                    Utils.toast_center(ActivityMarriageServiceAdd.this, "இணையதள சேவையை சரிபார்க்கவும் ");
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
                if (Utils.isNetworkAvailable(ActivityMarriageServiceAdd.this) == false) {
                    Utils.toast_center(ActivityMarriageServiceAdd.this, "இணையதள சேவையை சரிபார்க்கவும் ");
                    return;
                }
                if (district_spinner.getText().toString().length() != 0) {
                    if (dist_item.size() != 0) {
                        city_list();
                    } else {
                        new dist_load().execute();
                    }
                } else {
                    Utils.toast_center(ActivityMarriageServiceAdd.this, "மாவட்டம் தேர்வு செய்க");
                }
            }
        });

        district_spinner_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                action_type = "getdistrict";
                new service_dist_load().execute(action_type, Utils.url_calender_service);
            }
        });

        cardSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                image_tag_url = "";
                str_vila = "";
                str_type = "";

                for (int i = 0; i < avail_check_item.size(); i++) {
                    if (avail_check_item.get(i).isSelected() == true) {
                        if (str_vila.length() > 0) {
                            str_vila = str_vila + "," + avail_check_item.get(i).getId();
                        } else {
                            str_vila = "" + avail_check_item.get(i).getId();
                        }
                    }
                }

                for (int i = 0; i < avail_check_item_type.size(); i++) {
                    if (avail_check_item_type.get(i).isSelected() == true) {
                        if (str_type.length() > 0) {
                            str_type = str_type + "," + avail_check_item_type.get(i).getId();
                        } else {
                            str_type = "" + avail_check_item_type.get(i).getId();
                        }
                    }
                }

                if (!Utils.isNetworkAvailable(ActivityMarriageServiceAdd.this)) {
                    Utils.toast_center(ActivityMarriageServiceAdd.this, "இணையதள சேவையை சரிபார்க்கவும் ");
                    return;
                }

                if (editTextOrgName.getText().toString().trim().length() == 0) {
                    Utils.toast_center(ActivityMarriageServiceAdd.this, "" + textViewOrgName.getText().toString().replace("*", "").trim() + " உள்ளிடவும்");
                    return;
                }

                if (editTextContactName.getText().toString().trim().length() == 0) {
                    Utils.toast_center(ActivityMarriageServiceAdd.this, "உரிமையாளர்/மேலாளர் பெயரை உள்ளிடவும்");
                    return;
                }

                if (editTextContactNumber1.getText().toString().trim().length() == 0) {
                    Utils.toast_center(ActivityMarriageServiceAdd.this, "அலைபேசி எண்ணை உள்ளிடவும்");
                    return;
                }

                if (editTextContactNumber1.getText().toString().trim().length() < 10) {
                    Utils.toast_center(ActivityMarriageServiceAdd.this, "அலைபேசி எண்ணை சரிபார்க்கவும்");
                    return;
                }

                if (editTextContactNumber2.getText().toString().trim().length() != 0) {
                    if (editTextContactNumber2.getText().toString().trim().length() < 10) {
                        Utils.toast_center(ActivityMarriageServiceAdd.this, "மாற்று அலைபேசி எண்ணை சரிபார்க்கவும்");
                        return;
                    }
                }

                if (district_spinner.getText().toString().trim().length() == 0) {
                    Utils.toast_center(ActivityMarriageServiceAdd.this, "மாவட்டத்தை தேர்ந்தெடுக்கவும்");
                    return;
                }

                if (taluk_spinner.getText().toString().trim().length() == 0) {
                    Utils.toast_center(ActivityMarriageServiceAdd.this, "நகரத்தை தேர்ந்தெடுக்கவும்");
                    return;
                }

                if (editTextEmail.getText().toString().trim().length() != 0) {

                    if (!EmailValidation(editTextEmail.getText().toString())) {
                        Utils.toast_center(ActivityMarriageServiceAdd.this, "மின்னஞ்சல் முகவரியை சரிப்பார்க்கவும்");
                        return;
                    }
                }

                if (editTextAddress.getText().toString().trim().length() == 0) {
                    Utils.toast_center(ActivityMarriageServiceAdd.this, "முகவரியை உள்ளிடவும்");
                    return;
                }



              /*  if (editTextLandmark.getText().toString().trim().length() == 0) {
                    Utils.toast_center(ActivityMandapamAdd.this, "Landmark-யை உள்ளிடவும்");
                    return;
                }*/


                if (editTextPincode.getText().toString().trim().length() == 0) {
                    Utils.toast_center(ActivityMarriageServiceAdd.this, "Pincode-யை உள்ளிடவும்");
                    return;
                }

                if (editTextPincode.getText().toString().trim().length() < 6) {
                    Utils.toast_center(ActivityMarriageServiceAdd.this, "Pincode-யை சரிப்பார்க்கவும்");
                    return;
                }

                if (!user_service_type_name.equals("1") && radioExperinceButton == null) {
                    Utils.toast_center(ActivityMarriageServiceAdd.this, "அனுபவத்தை தேர்ந்தெடுக்கவும்");
                    return;
                }

                if (user_service_type_name.equals("1") && radioMandapamVagaiButton == null) {
                    Utils.toast_center(ActivityMarriageServiceAdd.this, "மண்டபத்தின் வகையை தேர்ந்தெடுக்கவும்");
                    return;
                }

                if (user_service_type_name.equals("1") && radioMandapamRentButton == null) {
                    Utils.toast_center(ActivityMarriageServiceAdd.this, "மண்டபத்தின் வாடகை வகையை தேர்ந்தெடுக்கவும்");
                    return;
                }

               /* if (user_service_type_name.equals("2") && str_type.equals("")) {
                    Utils.toast_center(ActivityMandapamAdd.this, "வகையை தேர்ந்தெடுக்கவும்");
                    return;
                }*/

                if (str_vila.equals("")) {
                    Utils.toast_center(ActivityMarriageServiceAdd.this, "" + text_title_vila.getText().toString().replace(":", "").trim() + " தேர்ந்தெடுக்கவும்");
                    return;
                }

                if (user_service_type_name.equals("2") && str_service_place.equals("")) {
                    Utils.toast_center(ActivityMarriageServiceAdd.this, "சர்வீஸ் செய்யப்படும் மாவட்டம் தேர்ந்தெடுக்கவும்");
                    return;
                }

                int imageLength = str_img1.length()
                        + str_img2.length()
                        + str_img3.length()
                        + str_img4.length()
                        + str_img5.length()
                        + str_img6.length()
                        + str_img7.length()
                        + str_img8.length()
                        + str_img9.length()
                        + str_img10.length()
                        + str_img11.length()
                        + str_img12.length();


                if (data_status.equals("from_edit")) {

                    if (!img1_r.getTag().toString().equals("null")) {
                        image_tag_url = image_tag_url + img1_r.getTag().toString();
                    }

                    if (!img2_r.getTag().toString().equals("null")) {
                        if (img1_r.getTag().toString().equals("null")) {
                            image_tag_url = image_tag_url + img2_r.getTag().toString();
                        } else {
                            image_tag_url = image_tag_url + "," + img2_r.getTag().toString();
                        }
                    }

                    if (!img3_r.getTag().toString().equals("null")) {
                        image_tag_url = image_tag_url + "," + img3_r.getTag().toString();
                    }

                    if (!img4_r.getTag().toString().equals("null")) {
                        image_tag_url = image_tag_url + "," + img4_r.getTag().toString();
                    }

                    if (!img5_r.getTag().toString().equals("null")) {
                        image_tag_url = image_tag_url + "," + img5_r.getTag().toString();
                    }

                    if (!img6_r.getTag().toString().equals("null")) {
                        image_tag_url = image_tag_url + "," + img6_r.getTag().toString();
                    }

                    if (!img7_r.getTag().toString().equals("null")) {
                        image_tag_url = image_tag_url + "," + img7_r.getTag().toString();
                    }

                    if (!img8_r.getTag().toString().equals("null")) {
                        image_tag_url = image_tag_url + "," + img8_r.getTag().toString();
                    }

                    if (!img9_r.getTag().toString().equals("null")) {
                        image_tag_url = image_tag_url + "," + img9_r.getTag().toString();
                    }

                    if (!img10_r.getTag().toString().equals("null")) {
                        image_tag_url = image_tag_url + "," + img10_r.getTag().toString();
                    }

                    if (!img11_r.getTag().toString().equals("null")) {
                        image_tag_url = image_tag_url + "," + img11_r.getTag().toString();
                    }

                    if (!img12_r.getTag().toString().equals("null")) {
                        image_tag_url = image_tag_url + "," + img12_r.getTag().toString();
                    }

                    System.out.println("===image_tag_url " + image_tag_url);

                    if (image_tag_url.length() == 0) {

                        if (imageLength == 0) {
                            Utils.toast_center(ActivityMarriageServiceAdd.this, "குறைந்தது ஒரு புகைப்படத்தைத் தேர்ந்தெடுக்கவும்");
                            return;
                        }
                    }

                } else {
                    if (imageLength == 0) {
                        Utils.toast_center(ActivityMarriageServiceAdd.this, "குறைந்தது ஒரு புகைப்படத்தைத் தேர்ந்தெடுக்கவும்");
                        return;
                    }
                }

                if(card_map.getVisibility() == View.VISIBLE){
                    if (editTextLongitude.getText().toString().trim().length() != 0) {

                        if (editTextLatitude.getText().toString().trim().length() == 0) {
                            Utils.toast_center(ActivityMarriageServiceAdd.this, "Latitude-யை உள்ளிடவும்");
                            return;
                        }

                    }

                    if (editTextLatitude.getText().toString().trim().length() != 0) {
                        if (editTextLongitude.getText().toString().trim().length() == 0) {
                            Utils.toast_center(ActivityMarriageServiceAdd.this, "Longitude-யை உள்ளிடவும்");
                            return;
                        }
                    }
                }

                if (user_service_type_name.equals("1")) {
                    action_type = "add_mandapam";
                }

                if (user_service_type_name.equals("2")) {
                    action_type = "add_decoration";
                }

                if (user_service_type_name.equals("3")) {
                    action_type = "add_photography";
                }

                if (user_service_type_name.equals("4")) {
                    action_type = "add_music";
                }

                if (user_service_type_name.equals("5")) {
                    action_type = "add_parlour";
                }

                if (user_service_type_name.equals("6")) {
                    action_type = "add_catering";
                }


                try {
                    str_district_id = district_spinner.getTag().toString();
                    str_city_id = taluk_spinner.getTag().toString();
                    str_org_name = "" + editTextOrgName.getText().toString().trim();
                    str_contact_name = "" + editTextContactName.getText().toString().trim();
                    str_contact_number1 = "" + editTextContactNumber1.getText().toString().trim();
                    str_contact_number2 = "" + editTextContactNumber2.getText().toString().trim();
                    str_email = "" + editTextEmail.getText().toString().trim();
                    str_address = "" + editTextAddress.getText().toString().trim();
                    str_landmark = "" + editTextLandmark.getText().toString().trim();
                    str_pincode = "" + editTextPincode.getText().toString().trim();
                    str_insta = "" + editTextInsta.getText().toString().trim();
                    str_fb = "" + editTextFb.getText().toString().trim();
                    str_youtube = "" + editTextYoutube.getText().toString().trim();
                    str_web = "" + editTextWeb.getText().toString().trim();
                    str_others = "" + editTextOther.getText().toString().trim();
                    // str_map = "" + editTextMap.getText().toString().trim();
                    str_long = "" + editTextLongitude.getText().toString().trim();
                    str_lat = "" + editTextLatitude.getText().toString().trim();
                    str_carparking_text = "" + editTextCarPark.getText().toString();
                    str_room_text = "" + editTextRoom.getText().toString();
                    str_camera_feature = editTextCameraFeature.getText().toString().trim();

                    System.out.println("=== data add user id " + action_type);
                    System.out.println("=== data add user id " + userId);
                    System.out.println("=== data add org name " + str_org_name);
                    System.out.println("=== data add contact name " + str_contact_name);
                    System.out.println("=== data add ph number 1 " + str_contact_number1);
                    System.out.println("=== data add ph number 2 " + str_contact_number2);
                    System.out.println("=== data add email " + str_email);
                    System.out.println("=== data add district id " + str_district_id);
                    System.out.println("=== data add district name " + district_spinner.getText().toString());
                    System.out.println("=== data add city id " + str_city_id);
                    System.out.println("=== data add city name " + taluk_spinner.getText().toString());
                    System.out.println("=== data add address " + str_address);
                    System.out.println("=== data add pincode " + str_pincode);
                    System.out.println("=== data add landmark " + str_landmark);
                    System.out.println("=== data add url instagram  " + str_insta);
                    System.out.println("=== data add url facebook " + str_fb);
                    System.out.println("=== data add url youtube " + str_youtube);
                    System.out.println("=== data add url website " + str_web);
                    System.out.println("=== data add others " + str_others);
                    System.out.println("=== data add map " + str_map);
                    System.out.println("=== data add lat " + str_lat);
                    System.out.println("=== data add long " + str_long);

                } catch (Exception e) {

                    System.out.println("=== add mandapam " + e.getMessage());

                }
                new sent_data_to_server().execute();
            }

        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.img1) {
            img_click = 1;
            sharePermissionFun();
        }

        if (id == R.id.img2) {
            img_click = 2;
            sharePermissionFun();
        }

        if (id == R.id.img3) {
            img_click = 3;
            sharePermissionFun();
        }

        if (id == R.id.img4) {
            img_click = 4;
            sharePermissionFun();
        }

        if (id == R.id.img5) {
            img_click = 5;
            sharePermissionFun();
        }

        if (id == R.id.img6) {
            img_click = 6;
            sharePermissionFun();
        }

        if (id == R.id.img7) {
            img_click = 7;
            sharePermissionFun();
        }

        if (id == R.id.img8) {
            img_click = 8;
            sharePermissionFun();
        }

        if (id == R.id.img9) {
            img_click = 9;
            sharePermissionFun();
        }

        if (id == R.id.img10) {
            img_click = 10;
            sharePermissionFun();
        }

        if (id == R.id.img11) {
            img_click = 11;
            sharePermissionFun();
        }

        if (id == R.id.img12) {
            img_click = 12;
            sharePermissionFun();
        }

        if (id == R.id.img1_r) {
            dele_fun(1);
        }

        if (id == R.id.img2_r) {
            dele_fun(2);
        }

        if (id == R.id.img3_r) {
            dele_fun(3);
        }

        if (id == R.id.img4_r) {
            dele_fun(4);
        }

        if (id == R.id.img5_r) {
            dele_fun(5);
        }

        if (id == R.id.img6_r) {
            dele_fun(6);
        }

        if (id == R.id.img7_r) {
            dele_fun(7);
        }

        if (id == R.id.img8_r) {
            dele_fun(8);
        }

        if (id == R.id.img9_r) {
            dele_fun(9);
        }

        if (id == R.id.img10_r) {
            dele_fun(10);
        }

        if (id == R.id.img11_r) {
            dele_fun(11);
        }

        if (id == R.id.img12_r) {
            dele_fun(12);
        }
    }

    public void sharePermissionFun() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                final Dialog dialog = new Dialog(ActivityMarriageServiceAdd.this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
                dialog.setContentView(R.layout.permission_dialog_layout);
                TextView ok = dialog.findViewById(R.id.permission_ok);
                TextView txt = dialog.findViewById(R.id.txt);
                TextView txtfd = dialog.findViewById(R.id.txtfd);
                txtfd.setBackgroundColor(Utils.get_color(ActivityMarriageServiceAdd.this));
                if (sharedPreference.getInt(ActivityMarriageServiceAdd.this, "permission") == 2) {
                    txt.setText("புகைப்படத்தை தேர்வு செய்ய settings பகுதியில் உள்ள storage, camera permission -யை allow செய்ய வேண்டும்");
                } else {
                    txt.setText("புகைப்படத்தை தேர்வு செய்ய பின்வரும் அனுமதிகளை (Permission) அனுமதிக்கவும்");
                }
                ok.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(View v) {
                        if (sharedPreference.getInt(ActivityMarriageServiceAdd.this, "permission") == 2) {
                            Intent intent = new Intent();
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
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
                choose_imge();
            }
        } else {
            choose_imge();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 153: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sharedPreference.putInt(ActivityMarriageServiceAdd.this, "permission", 1);
                    choose_imge();
                } else {
                    if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                        boolean showRationale = shouldShowRequestPermissionRationale(permissions[0]);
                        if (!showRationale) {
                            sharedPreference.putInt(ActivityMarriageServiceAdd.this, "permission", 2);
                        } else if (android.Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(permissions[0])) {
                            sharedPreference.putInt(ActivityMarriageServiceAdd.this, "permission", 0);
                        }
                    }
                }
            }
        }
    }

    public void choose_imge() {
        try {
            CropImage.activity(null).setGuidelines(CropImageView.Guidelines.ON).start(ActivityMarriageServiceAdd.this);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            System.out.println("===Error " + e.getMessage());
            Utils.toast_center(ActivityMarriageServiceAdd.this, "Try again...");
        }
    }

    public void dele_fun(final int position) {

        final Dialog no_datefun = new Dialog(ActivityMarriageServiceAdd.this,
                android.R.style.Theme_DeviceDefault_Dialog_NoActionBar_MinWidth);
        no_datefun.setContentView(R.layout.nodate_dia);
        TextView btnSet = no_datefun.findViewById(R.id.text_no);
        TextView btnok = no_datefun.findViewById(R.id.text_yes);
        AppCompatTextView head_txt = no_datefun.findViewById(R.id.text_head);
        AppCompatTextView editText1 = no_datefun.findViewById(R.id.text_content);
        btnSet.setText("ஆம்");
        btnok.setText("இல்லை");
        head_txt.setVisibility(View.GONE);

        editText1.setText("இந்த புகைப்படத்தை நீக்க வேண்டுமா?");


 /*       btnSet.setBackgroundColor(Utils.get_color(ActivityMandapamAdd.this));
        btnok.setBackgroundColor(Utils.get_color(ActivityMandapamAdd.this));*/

        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (position == 1) {
                    str_img1 = "";
                    //img_status = "Remove_img";
                    // sharedPreference.putString(ActivityMandapamAdd.this, "progithar_reg_photo" + sharedPreference.getString(Main_Reg2.this, "progithar_type"), "");
                    img1_r.setVisibility(View.GONE);
                    img1_r.setTag("null");
                    img1.setImageResource(R.drawable.add_image);
                    image_del_fun(position);
                }

                if (position == 2) {
                    str_img2 = "";
                    //img_status = "Remove_img";
                    // sharedPreference.putString(ActivityMandapamAdd.this, "progithar_reg_photo" + sharedPreference.getString(Main_Reg2.this, "progithar_type"), "");
                    img2_r.setVisibility(View.GONE);
                    img2_r.setTag("null");
                    img2.setImageResource(R.drawable.add_image);
                    image_del_fun(position);
                }

                if (position == 3) {
                    str_img3 = "";
                    //img_status = "Remove_img";
                    // sharedPreference.putString(ActivityMandapamAdd.this, "progithar_reg_photo" + sharedPreference.getString(Main_Reg2.this, "progithar_type"), "");
                    img3_r.setVisibility(View.GONE);
                    img3_r.setTag("null");
                    img3.setImageResource(R.drawable.add_image);
                    image_del_fun(position);
                }

                if (position == 4) {
                    str_img4 = "";
                    //img_status = "Remove_img";
                    // sharedPreference.putString(ActivityMandapamAdd.this, "progithar_reg_photo" + sharedPreference.getString(Main_Reg2.this, "progithar_type"), "");
                    img4_r.setVisibility(View.GONE);
                    img4_r.setTag("null");
                    img4.setImageResource(R.drawable.add_image);
                    image_del_fun(position);
                }

                if (position == 5) {
                    str_img5 = "";
                    //img_status = "Remove_img";
                    // sharedPreference.putString(ActivityMandapamAdd.this, "progithar_reg_photo" + sharedPreference.getString(Main_Reg2.this, "progithar_type"), "");
                    img5_r.setVisibility(View.GONE);
                    img5_r.setTag("null");
                    img5.setImageResource(R.drawable.add_image);
                    image_del_fun(position);
                }

                if (position == 6) {
                    str_img6 = "";
                    //img_status = "Remove_img";
                    // sharedPreference.putString(ActivityMandapamAdd.this, "progithar_reg_photo" + sharedPreference.getString(Main_Reg2.this, "progithar_type"), "");
                    img6_r.setVisibility(View.GONE);
                    img6_r.setTag("null");
                    img6.setImageResource(R.drawable.add_image);
                    image_del_fun(position);
                }

                if (position == 7) {
                    str_img7 = "";
                    //img_status = "Remove_img";
                    // sharedPreference.putString(ActivityMandapamAdd.this, "progithar_reg_photo" + sharedPreference.getString(Main_Reg2.this, "progithar_type"), "");
                    img7_r.setVisibility(View.GONE);
                    img7_r.setTag("null");
                    img7.setImageResource(R.drawable.add_image);
                    image_del_fun(position);
                }

                if (position == 8) {
                    str_img8 = "";
                    //img_status = "Remove_img";
                    // sharedPreference.putString(ActivityMandapamAdd.this, "progithar_reg_photo" + sharedPreference.getString(Main_Reg2.this, "progithar_type"), "");
                    img8_r.setVisibility(View.GONE);
                    img8_r.setTag("null");
                    img8.setImageResource(R.drawable.add_image);
                    image_del_fun(position);
                }

                if (position == 9) {
                    str_img9 = "";
                    //img_status = "Remove_img";
                    // sharedPreference.putString(ActivityMandapamAdd.this, "progithar_reg_photo" + sharedPreference.getString(Main_Reg2.this, "progithar_type"), "");
                    img9_r.setVisibility(View.GONE);
                    img9_r.setTag("null");
                    img9.setImageResource(R.drawable.add_image);
                    image_del_fun(position);
                }

                if (position == 10) {
                    str_img10 = "";
                    //img_status = "Remove_img";
                    // sharedPreference.putString(ActivityMandapamAdd.this, "progithar_reg_photo" + sharedPreference.getString(Main_Reg2.this, "progithar_type"), "");
                    img10_r.setVisibility(View.GONE);
                    img10_r.setTag("null");
                    img10.setImageResource(R.drawable.add_image);
                    image_del_fun(position);
                }

                if (position == 11) {
                    str_img11 = "";
                    //img_status = "Remove_img";
                    // sharedPreference.putString(ActivityMandapamAdd.this, "progithar_reg_photo" + sharedPreference.getString(Main_Reg2.this, "progithar_type"), "");
                    img11_r.setVisibility(View.GONE);
                    img11_r.setTag("null");
                    img11.setImageResource(R.drawable.add_image);
                    image_del_fun(position);
                }

                if (position == 12) {
                    str_img12 = "";
                    //img_status = "Remove_img";
                    // sharedPreference.putString(ActivityMandapamAdd.this, "progithar_reg_photo" + sharedPreference.getString(Main_Reg2.this, "progithar_type"), "");
                    img12_r.setVisibility(View.GONE);
                    img12_r.setTag("null");
                    img12.setImageResource(R.drawable.add_image);
                    image_del_fun(position);
                }
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

    public void image_del_fun(int val) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

            } else {
                try {
                    File uploadFile1 = new File(Environment.getExternalStorageDirectory().getPath(), "Nithra/Tamil Calendar/user_image_" + val + ".jpg");
                    if (uploadFile1.exists()) {
                        uploadFile1.delete();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                File uploadFile1 = new File(Environment.getExternalStorageDirectory().getPath(), "Nithra/Tamil Calendar/user_image_" + val + ".jpg");
                if (uploadFile1.exists()) {
                    uploadFile1.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // handle result of CropImageActivity
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri uri = result.getUri();

                savefile(uri);

                image_compress(getFilename());

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Utils.toast_center(this, "Cropping failed: " + result.getError());
            }
        }
    }

    public String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "Nithra/Tamil Calendar/");
        if (!file.exists()) {
            file.mkdirs();
        }
        String uriSting = (file.getAbsolutePath() + "/" + "user_image_" + img_click + ".jpg");
        return uriSting;

    }

    public void savefile(Uri sourceuri) {
        String sourceFilename = sourceuri.getPath();
        /*String destinationFilename = android.os.Environment.getExternalStorageDirectory().getPath()+File.separatorChar+"abc.mp3";*/
        File mydir = new File(Environment.getExternalStorageDirectory().toString() + "/Nithra/Tamil Calendar/");
        mydir.mkdirs();
        String fname = "user_image_" + img_click + ".jpg";
        final File to = new File(mydir, fname);

        String destinationFilename = to.getAbsolutePath();

        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        try {
            bis = new BufferedInputStream(new FileInputStream(sourceFilename));
            bos = new BufferedOutputStream(new FileOutputStream(destinationFilename, false));
            byte[] buf = new byte[1024];
            bis.read(buf);
            do {
                bos.write(buf);
            } while (bis.read(buf) != -1);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bis != null) bis.close();
                if (bos != null) bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void image_compress(final String uri) {
        new AsyncTask<String, String, String>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected String doInBackground(String... params) {

                String filePath = getRealPathFromURI(uri);
                /*System.out.println("filePath"+filePath);*/

                /*String filePath = uri.getPath();*/
                Bitmap scaledBitmap = null;

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

                int actualHeight = options.outHeight;
                int actualWidth = options.outWidth;
                float maxHeight = 816.0f;
                float maxWidth = 612.0f;
                float imgRatio = actualWidth / actualHeight;
                float maxRatio = maxWidth / maxHeight;

                if (actualHeight > maxHeight || actualWidth > maxWidth) {
                    if (imgRatio < maxRatio) {
                        imgRatio = maxHeight / actualHeight;
                        actualWidth = (int) (imgRatio * actualWidth);
                        actualHeight = (int) maxHeight;
                    } else if (imgRatio > maxRatio) {
                        imgRatio = maxWidth / actualWidth;
                        actualHeight = (int) (imgRatio * actualHeight);
                        actualWidth = (int) maxWidth;
                    } else {
                        actualHeight = (int) maxHeight;
                        actualWidth = (int) maxWidth;

                    }
                }

                options.inSampleSize = utils.calculateInSampleSize(options, actualWidth, actualHeight);
                options.inJustDecodeBounds = false;
                options.inDither = false;
                options.inPurgeable = true;
                options.inInputShareable = true;
                options.inTempStorage = new byte[16 * 1024];

                try {
                    bmp = BitmapFactory.decodeFile(filePath, options);
                } catch (OutOfMemoryError exception) {
                    exception.printStackTrace();

                }
                try {
                    scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
                } catch (OutOfMemoryError exception) {
                    exception.printStackTrace();
                }

                float ratioX = actualWidth / (float) options.outWidth;
                float ratioY = actualHeight / (float) options.outHeight;
                float middleX = actualWidth / 2.0f;
                float middleY = actualHeight / 2.0f;

                Matrix scaleMatrix = new Matrix();
                scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

                Canvas canvas = new Canvas(scaledBitmap);
                canvas.setMatrix(scaleMatrix);
                canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));


                ExifInterface exif;
                try {
                    exif = new ExifInterface(filePath);

                    int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
                    Log.d("EXIF", "Exif: " + orientation);
                    Matrix matrix = new Matrix();
                    if (orientation == 6) {
                        matrix.postRotate(90);
                        Log.d("EXIF", "Exif: " + orientation);
                    } else if (orientation == 3) {
                        matrix.postRotate(180);
                        Log.d("EXIF", "Exif: " + orientation);
                    } else if (orientation == 8) {
                        matrix.postRotate(270);
                        Log.d("EXIF", "Exif: " + orientation);
                    }
                    scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                FileOutputStream out = null;
                String filename = getFilename();
                try {
                    out = new FileOutputStream(filename);
                    scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                File filee = new File(Environment.getExternalStorageDirectory().toString() + "//Nithra/Tamil Calendar/user_image_" + img_click + ".jpg");
                Bitmap bmp = BitmapFactory.decodeFile(filee.getPath());
                if (img_click == 1) {
                    img1_r.setVisibility(View.VISIBLE);
                    img1_r.setTag("null");
                    img1.setImageBitmap(bmp);
                    str_img1 = getStringImage(bmp);
                }
                if (img_click == 2) {
                    img2_r.setVisibility(View.VISIBLE);
                    img2_r.setTag("null");
                    img2.setImageBitmap(bmp);
                    str_img2 = getStringImage(bmp);
                }
                if (img_click == 3) {
                    img3_r.setVisibility(View.VISIBLE);
                    img3_r.setTag("null");
                    img3.setImageBitmap(bmp);
                    str_img3 = getStringImage(bmp);
                }
                if (img_click == 4) {
                    img4_r.setVisibility(View.VISIBLE);
                    img4_r.setTag("null");
                    img4.setImageBitmap(bmp);
                    str_img4 = getStringImage(bmp);
                }
                if (img_click == 5) {
                    img5_r.setVisibility(View.VISIBLE);
                    img5_r.setTag("null");
                    img5.setImageBitmap(bmp);
                    str_img5 = getStringImage(bmp);
                }
                if (img_click == 6) {
                    img6_r.setVisibility(View.VISIBLE);
                    img6_r.setTag("null");
                    img6.setImageBitmap(bmp);
                    str_img6 = getStringImage(bmp);
                }
                if (img_click == 7) {
                    img7_r.setVisibility(View.VISIBLE);
                    img7_r.setTag("null");
                    img7.setImageBitmap(bmp);
                    str_img7 = getStringImage(bmp);
                }
                if (img_click == 8) {
                    img8_r.setVisibility(View.VISIBLE);
                    img8_r.setTag("null");
                    img8.setImageBitmap(bmp);
                    str_img8 = getStringImage(bmp);
                }
                if (img_click == 9) {
                    img9_r.setVisibility(View.VISIBLE);
                    img9_r.setTag("null");
                    img9.setImageBitmap(bmp);
                    str_img9 = getStringImage(bmp);
                }
                if (img_click == 10) {
                    img10_r.setVisibility(View.VISIBLE);
                    img10_r.setTag("null");
                    img10.setImageBitmap(bmp);
                    str_img10 = getStringImage(bmp);
                }
                if (img_click == 11) {
                    img11_r.setVisibility(View.VISIBLE);
                    img11_r.setTag("null");
                    img11.setImageBitmap(bmp);
                    str_img11 = getStringImage(bmp);
                }
                if (img_click == 12) {
                    img12_r.setVisibility(View.VISIBLE);
                    img12_r.setTag("null");
                    img12.setImageBitmap(bmp);
                    str_img12 = getStringImage(bmp);
                }
            }
        }.execute();
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.WEBP, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    public String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    private class dist_load extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Utils.mProgress(ActivityMarriageServiceAdd.this, "ஏற்றுகிறது. காத்திருக்கவும் ", false).show();
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
                    dialog.dismiss();
                    // dist_choose();
                }
            };

            ContactSearchDialogCompat contactSearchDialogCompat = new ContactSearchDialogCompat(ActivityMarriageServiceAdd.this,
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
            new ContactSearchDialogCompat<>(ActivityMarriageServiceAdd.this, "நகரத்தை தேர்வு செய்க ", "நகரத்தை தேட", null, city_models,
                    new SearchResultListener<City_Model>() {
                        @Override
                        public void onSelected(BaseSearchDialogCompat dialog, City_Model item, int position) {
                            taluk_spinner.setText("" + item.getTitle());
                            taluk_spinner.setTag("" + item.getId());
                            dialog.dismiss();
                            // dist_choose();

                        }
                    }).show();
        }

    }

    public boolean available_for(String strr) {
        String[] vall = filter_values.get(0).get("cate").toString().split("\\,");

        for (int j = 0; j < vall.length; j++) {
            if (vall[j].equals("" + strr)) {
                return true;
            }
        }
        return false;
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

    private class sent_data_to_server extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Utils.mProgress(ActivityMarriageServiceAdd.this, "ஏற்றுகிறது. காத்திருக்கவும் ", false).show();
        }

        @Override
        protected String doInBackground(String... strr) {

            File uploadFile1 = new File(Environment.getExternalStorageDirectory().getPath(), "Nithra/Tamil Calendar/user_image_1.jpg");
            File uploadFile2 = new File(Environment.getExternalStorageDirectory().getPath(), "Nithra/Tamil Calendar/user_image_2.jpg");
            File uploadFile3 = new File(Environment.getExternalStorageDirectory().getPath(), "Nithra/Tamil Calendar/user_image_3.jpg");
            File uploadFile4 = new File(Environment.getExternalStorageDirectory().getPath(), "Nithra/Tamil Calendar/user_image_4.jpg");
            File uploadFile5 = new File(Environment.getExternalStorageDirectory().getPath(), "Nithra/Tamil Calendar/user_image_5.jpg");
            File uploadFile6 = new File(Environment.getExternalStorageDirectory().getPath(), "Nithra/Tamil Calendar/user_image_6.jpg");
            File uploadFile7 = new File(Environment.getExternalStorageDirectory().getPath(), "Nithra/Tamil Calendar/user_image_7.jpg");
            File uploadFile8 = new File(Environment.getExternalStorageDirectory().getPath(), "Nithra/Tamil Calendar/user_image_8.jpg");
            File uploadFile9 = new File(Environment.getExternalStorageDirectory().getPath(), "Nithra/Tamil Calendar/user_image_9.jpg");
            File uploadFile10 = new File(Environment.getExternalStorageDirectory().getPath(), "Nithra/Tamil Calendar/user_image_10.jpg");
            File uploadFile11 = new File(Environment.getExternalStorageDirectory().getPath(), "Nithra/Tamil Calendar/user_image_11.jpg");
            File uploadFile12 = new File(Environment.getExternalStorageDirectory().getPath(), "Nithra/Tamil Calendar/user_image_12.jpg");

            String response = null;

            try {
                MultipartUtility multipart = new MultipartUtility(Utils.url_calender_service, "UTF-8");
                multipart.addFormField("action", "" + action_type);
                multipart.addFormField("userid", "" + userId);
                multipart.addFormField("org_name", "" + str_org_name);
                multipart.addFormField("incharge_name", "" + str_contact_name);
                multipart.addFormField("phone_no", "" + str_contact_number1);
                multipart.addFormField("alternative_mno", "" + str_contact_number2);
                multipart.addFormField("district", "" + str_district_id);
                multipart.addFormField("city", "" + str_city_id);
                multipart.addFormField("address", "" + str_address);
                multipart.addFormField("pincode", "" + str_pincode);
                multipart.addFormField("landmark", "" + str_landmark);
                multipart.addFormField("insta_url", "" + str_insta);
                multipart.addFormField("fb_url", "" + str_fb);
                multipart.addFormField("youtube_url", "" + str_youtube);
                multipart.addFormField("website", "" + str_web);

                if (user_service_type_name.equals("1")) {
                    System.out.println("=== data add str_vagai " + str_vagai);
                    System.out.println("=== data add str_rent_vagai " + str_rent_vagai);
                    System.out.println("=== data add str_vila " + str_vila);
                    System.out.println("=== data add str_dining " + str_dining);
                    System.out.println("=== data add str_carparking " + str_carparking);
                    System.out.println("=== data add str_carparking_text " + str_carparking_text);
                    System.out.println("=== data add str_room " + str_room);
                    System.out.println("=== data add str_room_text " + str_room_text);
                    System.out.println("=== data add str_generator " + str_generator);
                    System.out.println("=== data add str_audio " + str_audio);

                    multipart.addFormField("hall_type", "" + str_vagai);
                    multipart.addFormField("hall_rent_type", "" + str_rent_vagai);
                    multipart.addFormField("hall_event_name", "" + str_vila);
                    multipart.addFormField("dining_hall_type", "" + str_dining);
                    multipart.addFormField("car_parking", "" + str_carparking);
                    multipart.addFormField("car_parking_count", "" + str_carparking_text);
                    multipart.addFormField("room", "" + str_room);
                    multipart.addFormField("room_count", "" + str_room_text);
                    multipart.addFormField("generator", "" + str_generator);
                    multipart.addFormField("audio", "" + str_audio);
                    multipart.addFormField("google_map_url", "" + str_map);
                    multipart.addFormField("latitude", "" + str_lat);
                    multipart.addFormField("longitude", "" + str_long);
                }

                if (user_service_type_name.equals("2")) {
                    System.out.println("=== data add str_type " + str_type);
                    System.out.println("=== data add str_vila " + str_vila);
                    System.out.println("=== data add str_service_place " + str_service_place);
                    System.out.println("=== data add str_exp " + str_exp);

                    multipart.addFormField("email", "" + str_email);
                    multipart.addFormField("exp", "" + str_exp);
                    multipart.addFormField("dec_type", "" + str_type);
                    multipart.addFormField("dec_event_name", "" + str_vila);
                    multipart.addFormField("service_plan", "" + str_service_place);
                }

                if (user_service_type_name.equals("3")) {
                    System.out.println("=== data add str_type " + str_type);
                    System.out.println("=== data add str_camera_feature " + str_camera_feature);
                    System.out.println("=== data add str_vila " + str_vila);
                    System.out.println("=== data add str_exp " + str_exp);

                    multipart.addFormField("email", "" + str_email);
                    multipart.addFormField("exp", "" + str_exp);
                    multipart.addFormField("features", "" + str_type);
                    multipart.addFormField("camera_features", "" + str_camera_feature);
                    multipart.addFormField("event_name", "" + str_vila);
                }

                if (user_service_type_name.equals("4")) {
                    System.out.println("=== data add str_type " + str_type);
                    System.out.println("=== data add str_vila " + str_vila);
                    System.out.println("=== data add str_exp " + str_exp);

                    multipart.addFormField("email", "" + str_email);
                    multipart.addFormField("exp", "" + str_exp);
                    multipart.addFormField("music_type", "" + str_type);
                    multipart.addFormField("event_name", "" + str_vila);
                }

                if (user_service_type_name.equals("5")) {
                    System.out.println("=== data add str_type " + str_type);
                    System.out.println("=== data add str_vila " + str_vila);
                    System.out.println("=== data add str_exp " + str_exp);
                    System.out.println("=== data add str_parlour_type " + str_parlour_type);

                    multipart.addFormField("email", "" + str_email);
                    multipart.addFormField("exp", "" + str_exp);
                    multipart.addFormField("features_type", "" + str_type);
                    multipart.addFormField("event_name", "" + str_vila);
                    multipart.addFormField("parlour_for", "" + str_parlour_type);
                }

                if (user_service_type_name.equals("6")) {
                    System.out.println("=== data add str_type " + str_type);
                    System.out.println("=== data add str_vila " + str_vila);
                    System.out.println("=== data add str_exp " + str_exp);
                    System.out.println("=== data add samayal_type " + str_samayal_type);
                    System.out.println("=== data add cooking_count " + str_camera_feature);

                    multipart.addFormField("email", "" + str_email);
                    multipart.addFormField("exp", "" + str_exp);
                    multipart.addFormField("features", "" + str_type);
                    multipart.addFormField("event_name", "" + str_vila);
                    multipart.addFormField("samayal_type", "" + str_samayal_type);
                    multipart.addFormField("cooking_count", "" + str_camera_feature);
                }

                if (edit == 1) {
                    edit = 0;
                    String edit_id = sharedPreference.getString(ActivityMarriageServiceAdd.this, sharedPreference.getString(ActivityMarriageServiceAdd.this, "service_name") + "id");
                    System.out.println("=== edit iddddddd " + edit_id);
                    multipart.addFormField("edit_id", "" + edit_id);
                }

                if (data_status.equals("from_edit")) {

                    if (image_tag_url.length() > 0) {
                        image_tag_url = image_tag_url + ",";
                    }

                    multipart.addFormField("edit_image", "" + image_tag_url);
                    String edit_id = sharedPreference.getString(ActivityMarriageServiceAdd.this, sharedPreference.getString(ActivityMarriageServiceAdd.this, "service_name") + "id");
                    multipart.addFormField("edit_id", "" + edit_id);


                    System.out.println("=== data add image url " + image_tag_url);
                    System.out.println("=== data add edit_id " + edit_id);


                    if (str_img1.length() > 5) {
                        System.out.println("result : str_img1 " + str_img1.length());
                        multipart.addFilePart("image[]", uploadFile1);
                    }

                    if (str_img2.length() > 5) {
                        System.out.println("result : str_img2 " + str_img2.length());
                        multipart.addFilePart("image[]", uploadFile2);
                    }

                    if (str_img3.length() > 5) {
                        System.out.println("result : str_img3 " + str_img3.length());
                        multipart.addFilePart("image[]", uploadFile3);
                    }

                    if (str_img4.length() > 5) {
                        System.out.println("result : str_img4 " + str_img4.length());
                        multipart.addFilePart("image[]", uploadFile4);
                    }

                    if (str_img5.length() > 5) {
                        System.out.println("result : str_img5 " + str_img5.length());
                        multipart.addFilePart("image[]", uploadFile5);
                    }

                    if (str_img6.length() > 5) {
                        System.out.println("result : str_img6 " + str_img6.length());
                        multipart.addFilePart("image[]", uploadFile6);
                    }

                    if (str_img7.length() > 5) {
                        System.out.println("result : str_img7 " + str_img7.length());
                        multipart.addFilePart("image[]", uploadFile7);
                    }

                    if (str_img8.length() > 5) {
                        System.out.println("result : str_img8 " + str_img8.length());
                        multipart.addFilePart("image[]", uploadFile8);
                    }

                    if (str_img9.length() > 5) {
                        System.out.println("result : str_img9 " + str_img9.length());
                        multipart.addFilePart("image[]", uploadFile9);
                    }

                    if (str_img10.length() > 5) {
                        System.out.println("result : str_img10 " + str_img10.length());
                        multipart.addFilePart("image[]", uploadFile10);
                    }

                    if (str_img11.length() > 5) {
                        System.out.println("result : str_img11 " + str_img11.length());
                        multipart.addFilePart("image[]", uploadFile11);
                    }

                    if (str_img12.length() > 5) {
                        System.out.println("result : str_img12 " + str_img12.length());
                        multipart.addFilePart("image[]", uploadFile12);
                    }


                } else {
                    if (str_img1.length() > 5) {
                        multipart.addFilePart("image[]", uploadFile1);
                    }

                    if (str_img2.length() > 5) {
                        multipart.addFilePart("image[]", uploadFile2);
                    }

                    if (str_img3.length() > 5) {
                        multipart.addFilePart("image[]", uploadFile3);
                    }

                    if (str_img4.length() > 5) {
                        multipart.addFilePart("image[]", uploadFile4);
                    }

                    if (str_img5.length() > 5) {
                        multipart.addFilePart("image[]", uploadFile5);
                    }

                    if (str_img6.length() > 5) {
                        multipart.addFilePart("image[]", uploadFile6);
                    }

                    if (str_img7.length() > 5) {
                        multipart.addFilePart("image[]", uploadFile7);
                    }

                    if (str_img8.length() > 5) {
                        multipart.addFilePart("image[]", uploadFile8);
                    }

                    if (str_img9.length() > 5) {
                        multipart.addFilePart("image[]", uploadFile9);
                    }

                    if (str_img10.length() > 5) {
                        multipart.addFilePart("image[]", uploadFile10);
                    }

                    if (str_img11.length() > 5) {
                        multipart.addFilePart("image[]", uploadFile11);
                    }

                    if (str_img12.length() > 5) {
                        multipart.addFilePart("image[]", uploadFile12);
                    }
                }

                multipart.addFormField("others", "" + str_others.trim());

                // response from server
                response = String.valueOf(multipart.finish());
                System.out.println("result from server : " + response);

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("result error : " + e.getMessage());
            }


            return "" + response;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            System.out.println("result : " + result);

            JSONArray jArray;
            if (result.contains("Successfully")) {

                //  sharedPreference.putString(Main_Reg2.this, "progithar_reg_comp_status" + sharedPreference.getString(Main_Reg2.this, "progithar_type"), "Registration complete");

                try {

                    jArray = new JSONArray(result);
                    JSONObject json_data = null;
                    json_data = jArray.getJSONObject(0);

                    sharedPreference.putString(ActivityMarriageServiceAdd.this, sharedPreference.getString(ActivityMarriageServiceAdd.this, "service_name") + "id", json_data.getString("id"));
                    list_id = json_data.getString("id");
                    list_images = json_data.getString("images").replaceAll("\\r\\n", "");
                    if (data_status.equals("from_edit")) {

                        if (Utils.isNetworkAvailable(ActivityMarriageServiceAdd.this)) {
                            System.out.println("=== result id " + json_data.getString("id"));
                            System.out.println("=== result images " + json_data.getString("images").replaceAll("\\r\\n", ""));

                            Utils.toast_center(ActivityMarriageServiceAdd.this, "தங்களின் விவரங்கள் புதுப்பிக்கப்பட்டது");
                            Intent i = new Intent(ActivityMarriageServiceAdd.this, ActivityMarriageServicePreview.class);
                            i.putExtra("click_from", "dataId");
                            i.putExtra("list_id", "" + json_data.getString("id"));
                            i.putExtra("list_images", "" + json_data.getString("images").replaceAll("\\r\\n", ""));
                            i.putExtra("user_service_type_name", "" + user_service_type_name);
                            finish();
                            startActivity(i);
                        } else {
                            Utils.toast_center(ActivityMarriageServiceAdd.this, "இணைய சேவையை சரிபார்க்கவும்...");
                        }

                        return;
                    }

                /*    if (Utils.isNetworkAvailable(ActivityMandapamAdd.this)) {
                        System.out.println("=== result id " + json_data.getString("id"));
                        System.out.println("=== result images " + json_data.getString("images"));

                        sharedPreference.putString(ActivityMandapamAdd.this, "reg", "1");
                        Utils.toast_center(ActivityMandapamAdd.this, "தங்களின் விவரங்கள் சேர்க்கப்பட்டது");
                        Intent i = new Intent(ActivityMandapamAdd.this, ActivityMandapamPreview.class);
                        i.putExtra("click_from", "dataId");
                        i.putExtra("list_id", "" + json_data.getString("id"));
                        i.putExtra("list_images", "" + json_data.getString("images").replaceAll("\\r\\n", ""));
                        i.putExtra("user_service_type_name", "" + user_service_type_name);
                        finish();
                        startActivity(i);
                    } else {
                        Utils.toast_center(ActivityMandapamAdd.this, "இணைய சேவையை சரிபார்க்கவும்...");
                    }*/


                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("result error : " + e.getMessage());
                }

                try {
                    Utils.mProgress.dismiss();
                    //finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                dialogPreview(list_id);

            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.toast_center(ActivityMarriageServiceAdd.this, "தவறு ஏற்பட்டுள்ளது மீண்டும் முயற்சிக்கவும்");
                    }
                });
                try {
                    Utils.mProgress.dismiss();
                    //finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }


    }

    public void dialogPreview(String id) {

        final Dialog dialogPreview = new Dialog(ActivityMarriageServiceAdd.this, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        dialogPreview.setContentView(R.layout.layout_preview_dialog);
        dialogPreview.setCancelable(false);
        dialogPreview.setCanceledOnTouchOutside(false);
        TextView title = dialogPreview.findViewById(R.id.title);
        LinearLayout layoutPlan = dialogPreview.findViewById(R.id.layoutPlan);
        LinearLayout layoutEdit = dialogPreview.findViewById(R.id.layoutEdit);
        WebView content_view = dialogPreview.findViewById(R.id.web);

        ViewPager mPager = dialogPreview.findViewById(R.id.pager);
        CirclePageIndicator indicator = dialogPreview.findViewById(R.id.indicator);

        String[] ImagesArray = list_images.split(",");

        if (ImagesArray.length == 0) {
            ImagesArray[0] = "nooo";
        }

        mPager.setAdapter(new SlidingImage_Adapter(ActivityMarriageServiceAdd.this, ImagesArray));
        indicator.setViewPager(mPager);
        float density = getResources().getDisplayMetrics().density;
        indicator.setRadius(5 * density);


        WebSettings ws = content_view.getSettings();
        ws.setJavaScriptEnabled(true);

        //content_view.addJavascriptInterface(new WebAppInterface(ActivityMandapamAdd.this), "Android");
        content_view.setInitialScale(1);
        content_view.getSettings().setLoadWithOverviewMode(true);
        content_view.getSettings().setUseWideViewPort(true);
        content_view.getSettings().setJavaScriptEnabled(true);
        content_view.clearHistory();
        content_view.clearFormData();
        content_view.clearCache(true);
        WebSettings webSettings = content_view.getSettings();
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        content_view.loadUrl(Utils.getAllDetails + "" + id + "&color=EB226C&share=ok&service_type=" + user_service_type_name);

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
                if (url.contains("calender_service/services/api/viewplan.php")) {
                    Intent i = new Intent(ActivityMarriageServiceAdd.this, ActivityPayment.class);
                    i.putExtra("paymentUrl", "" + url);
                    startActivity(i);

                } else {
                    try {
                        Utils.custom_tabs(ActivityMarriageServiceAdd.this, url);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                try {
                    Utils.mProgress(ActivityMarriageServiceAdd.this, "ஏற்றுகிறது. காத்திருக்கவும் ", false).show();
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

                String url = Utils.pay_url + userId + "&st=" + user_service_type_name + "&sid=" + id;

                if (Utils.isNetworkAvailable(ActivityMarriageServiceAdd.this)) {
                    Intent i = new Intent(ActivityMarriageServiceAdd.this, ActivityPayment.class);
                    i.putExtra("paymentUrl", "" + url);
                    finish();
                    startActivity(i);
                    dialogPreview.dismiss();

                } else {
                    Utils.toast_center(ActivityMarriageServiceAdd.this, "இணைய சேவையை சரிபார்க்கவும்...");
                }


            }
        });

        layoutEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit = 1;
                dialogPreview.dismiss();
            }
        });


        dialogPreview.show();
    }

    public class getCategory extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            Utils.mProgress(ActivityMarriageServiceAdd.this, "ஏற்றுகிறது. காத்திருக்கவும் ", false).show();
        }

        @Override
        protected String doInBackground(String... params) {

            HttpHandler sh = new HttpHandler();
            ArrayList<HashMap<String, String>> parms = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> temp = new HashMap<String, String>();
            temp.put("action", "" + params[0]);
            temp.put("st", "" + params[1]);
            parms.add(temp);
            String response = sh.makeServiceCall(params[2], parms);
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
                flexboxLayoutCheckboxType.removeAllViews();
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

                        if (data_status.equals("from_edit")) {
                            stateVO.setSelected(available_for_edit("" + sharedPreference.getString(ActivityMarriageServiceAdd.this, sharedPreference.getString(ActivityMarriageServiceAdd.this, "service_name") + "event_name"), json_data.getString("id")));
                            checkBox.setChecked(available_for_edit("" + sharedPreference.getString(ActivityMarriageServiceAdd.this, sharedPreference.getString(ActivityMarriageServiceAdd.this, "service_name") + "event_name"), json_data.getString("id")));
                        }
                        avail_check_item.add(stateVO);

                        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                Utils.hideKeyboardFrom(ActivityMarriageServiceAdd.this, buttonView);
                                avail_check_item.get(Integer.parseInt(checkBox.getTag().toString())).setSelected(isChecked);
                            }
                        });

                        flexboxLayoutCheckboxVila.addView(view);

                    }

                    if (!user_service_type_name.equals("1")) {
                        JSONArray jsonArrayType = new JSONArray(jsonObject.getString("features_type"));
                        for (int type_int = 0; type_int < jsonArrayType.length(); type_int++) {
                            JSONObject json_data = jsonArrayType.getJSONObject(type_int);
                            System.out.println("Update=== type " + json_data.getString("id"));
                            System.out.println("Update=== type " + json_data.getString("features_type"));

                            inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            view = (LinearLayout) inflater.inflate(R.layout.layout_checkbox, null);

                            AppCompatCheckBox checkBox = view.findViewById(R.id.checkbox);
                            checkBox.setId(Integer.parseInt(json_data.getString("id")));
                            checkBox.setTag("" + type_int);
                            checkBox.setText("" + json_data.getString("features_type").trim());

                            State stateVO = new State();
                            stateVO.setTitle("" + json_data.getString("features_type"));
                            stateVO.setEng_tit("");
                            stateVO.setId(Integer.parseInt(json_data.getString("id")));

                            if (data_status.equals("from_edit")) {
                                stateVO.setSelected(available_for_edit("" + sharedPreference.getString(ActivityMarriageServiceAdd.this, sharedPreference.getString(ActivityMarriageServiceAdd.this, "service_name") + "type"), json_data.getString("id")));
                                checkBox.setChecked(available_for_edit("" + sharedPreference.getString(ActivityMarriageServiceAdd.this, sharedPreference.getString(ActivityMarriageServiceAdd.this, "service_name") + "type"), json_data.getString("id")));
                            }
                            avail_check_item_type.add(stateVO);

                            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                    Utils.hideKeyboardFrom(ActivityMarriageServiceAdd.this, buttonView);
                                    avail_check_item_type.get(Integer.parseInt(checkBox.getTag().toString())).setSelected(isChecked);
                                }
                            });

                            flexboxLayoutCheckboxType.addView(view);
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("JSONException === // " + e);
                }

            }

            if (data_status.equals("from_edit")) {
                editTextOrgName.setText(sharedPreference.getString(ActivityMarriageServiceAdd.this, sharedPreference.getString(ActivityMarriageServiceAdd.this, "service_name") + "org_name"));
                editTextContactName.setText(sharedPreference.getString(ActivityMarriageServiceAdd.this, sharedPreference.getString(ActivityMarriageServiceAdd.this, "service_name") + "incharger_name"));
                editTextContactNumber1.setText(sharedPreference.getString(ActivityMarriageServiceAdd.this, sharedPreference.getString(ActivityMarriageServiceAdd.this, "service_name") + "phone_no"));
                editTextContactNumber2.setText(sharedPreference.getString(ActivityMarriageServiceAdd.this, sharedPreference.getString(ActivityMarriageServiceAdd.this, "service_name") + "alternative_mno"));
                district_spinner.setText(sharedPreference.getString(ActivityMarriageServiceAdd.this, sharedPreference.getString(ActivityMarriageServiceAdd.this, "service_name") + "district_name"));
                taluk_spinner.setText(sharedPreference.getString(ActivityMarriageServiceAdd.this, sharedPreference.getString(ActivityMarriageServiceAdd.this, "service_name") + "city_name"));
                district_spinner.setTag(sharedPreference.getString(ActivityMarriageServiceAdd.this, sharedPreference.getString(ActivityMarriageServiceAdd.this, "service_name") + "district_id"));
                taluk_spinner.setTag(sharedPreference.getString(ActivityMarriageServiceAdd.this, sharedPreference.getString(ActivityMarriageServiceAdd.this, "service_name") + "city_id"));
                editTextAddress.setText(sharedPreference.getString(ActivityMarriageServiceAdd.this, sharedPreference.getString(ActivityMarriageServiceAdd.this, "service_name") + "address"));
                editTextLandmark.setText(sharedPreference.getString(ActivityMarriageServiceAdd.this, sharedPreference.getString(ActivityMarriageServiceAdd.this, "service_name") + "landmark"));
                editTextPincode.setText(sharedPreference.getString(ActivityMarriageServiceAdd.this, sharedPreference.getString(ActivityMarriageServiceAdd.this, "service_name") + "pincode"));
                editTextInsta.setText(sharedPreference.getString(ActivityMarriageServiceAdd.this, sharedPreference.getString(ActivityMarriageServiceAdd.this, "service_name") + "insta_url"));
                editTextFb.setText(sharedPreference.getString(ActivityMarriageServiceAdd.this, sharedPreference.getString(ActivityMarriageServiceAdd.this, "service_name") + "fb_url"));
                editTextYoutube.setText(sharedPreference.getString(ActivityMarriageServiceAdd.this, sharedPreference.getString(ActivityMarriageServiceAdd.this, "service_name") + "youtube_url"));
                editTextWeb.setText(sharedPreference.getString(ActivityMarriageServiceAdd.this, sharedPreference.getString(ActivityMarriageServiceAdd.this, "service_name") + "website"));
                editTextOther.setText(sharedPreference.getString(ActivityMarriageServiceAdd.this, sharedPreference.getString(ActivityMarriageServiceAdd.this, "service_name") + "others"));
                editTextLatitude.setText(sharedPreference.getString(ActivityMarriageServiceAdd.this, sharedPreference.getString(ActivityMarriageServiceAdd.this, "service_name") + "latitude"));
                editTextLongitude.setText(sharedPreference.getString(ActivityMarriageServiceAdd.this, sharedPreference.getString(ActivityMarriageServiceAdd.this, "service_name") + "longitude"));
                editTextCameraFeature.setText(sharedPreference.getString(ActivityMarriageServiceAdd.this, sharedPreference.getString(ActivityMarriageServiceAdd.this, "service_name") + "camera_features"));
                editTextEmail.setText(sharedPreference.getString(ActivityMarriageServiceAdd.this, sharedPreference.getString(ActivityMarriageServiceAdd.this, "service_name") + "email_id"));

                str_samayal_type = "" + sharedPreference.getString(ActivityMarriageServiceAdd.this, sharedPreference.getString(ActivityMarriageServiceAdd.this, "service_name") + "samayal_type");
                if (str_samayal_type.equals("1")) {
                    radioSamayalType1.setChecked(true);
                }
                if (str_samayal_type.equals("2")) {
                    radioSamayalType2.setChecked(true);
                }
                if (str_samayal_type.equals("3")) {
                    radioSamayalType3.setChecked(true);
                }

                str_parlour_type = "" + sharedPreference.getString(ActivityMarriageServiceAdd.this, sharedPreference.getString(ActivityMarriageServiceAdd.this, "service_name") + "parlour_gender_type");
                if (str_parlour_type.equals("1")) {
                    radioParlourType1.setChecked(true);
                }
                if (str_parlour_type.equals("2")) {
                    radioParlourType2.setChecked(true);
                }
                if (str_parlour_type.equals("3")) {
                    radioParlourType3.setChecked(true);
                }

                str_exp = "" + sharedPreference.getString(ActivityMarriageServiceAdd.this, sharedPreference.getString(ActivityMarriageServiceAdd.this, "service_name") + "experince");
                if (str_exp.equals("1")) {
                    radioExperince1.setChecked(true);
                }
                if (str_exp.equals("2")) {
                    radioExperince2.setChecked(true);
                }
                if (str_exp.equals("3")) {
                    radioExperince3.setChecked(true);
                }
                if (str_exp.equals("4")) {
                    radioExperince4.setChecked(true);
                }

                str_vagai = "" + sharedPreference.getString(ActivityMarriageServiceAdd.this, sharedPreference.getString(ActivityMarriageServiceAdd.this, "service_name") + "type");
                if (str_vagai.equals("1")) {
                    radioMandapamVagai1.setChecked(true);
                }
                if (str_vagai.equals("2")) {
                    radioMandapamVagai2.setChecked(true);
                }
                if (str_vagai.equals("3")) {
                    radioMandapamVagai3.setChecked(true);
                }

                str_rent_vagai = "" + sharedPreference.getString(ActivityMarriageServiceAdd.this, sharedPreference.getString(ActivityMarriageServiceAdd.this, "service_name") + "rent_type");
                if (str_rent_vagai.equals("1")) {
                    radioMandapamRent1.setChecked(true);
                }
                if (str_rent_vagai.equals("2")) {
                    radioMandapamRent2.setChecked(true);
                }
                if (str_rent_vagai.equals("3")) {
                    radioMandapamRent3.setChecked(true);
                }

                str_dining = "" + sharedPreference.getString(ActivityMarriageServiceAdd.this, sharedPreference.getString(ActivityMarriageServiceAdd.this, "service_name") + "dining_hall_type");
                if (str_dining.equals("1")) {
                    radioDiningHall2.setChecked(true);
                }
                if (str_dining.equals("2")) {
                    radioDiningHall1.setChecked(true);
                }
                if (str_dining.equals("3")) {
                    radioDiningHall3.setChecked(true);
                }

                str_carparking = "" + sharedPreference.getString(ActivityMarriageServiceAdd.this, sharedPreference.getString(ActivityMarriageServiceAdd.this, "service_name") + "car_parking");
                if (str_carparking.equals("1")) {
                    radioCarParkingYes.setChecked(true);
                    editTextCarPark.setText(sharedPreference.getString(ActivityMarriageServiceAdd.this, sharedPreference.getString(ActivityMarriageServiceAdd.this, "service_name") + "car_parking_count"));

                }
                if (str_carparking.equals("2")) {
                    radioCarParkingNo.setChecked(true);
                }

                str_room = "" + sharedPreference.getString(ActivityMarriageServiceAdd.this, sharedPreference.getString(ActivityMarriageServiceAdd.this, "service_name") + "room");
                if (str_room.equals("1")) {
                    radioRoomYes.setChecked(true);
                    editTextRoom.setText(sharedPreference.getString(ActivityMarriageServiceAdd.this, sharedPreference.getString(ActivityMarriageServiceAdd.this, "service_name") + "room_count"));
                }
                if (str_room.equals("2")) {
                    radioRoomNo.setChecked(true);
                }

                str_generator = "" + sharedPreference.getString(ActivityMarriageServiceAdd.this, sharedPreference.getString(ActivityMarriageServiceAdd.this, "service_name") + "generator");
                if (str_generator.equals("1")) {
                    radioGeneratorYes.setChecked(true);
                }
                if (str_generator.equals("2")) {
                    radioGeneratorNo.setChecked(true);
                }

                str_audio = "" + sharedPreference.getString(ActivityMarriageServiceAdd.this, sharedPreference.getString(ActivityMarriageServiceAdd.this, "service_name") + "audio");
                if (str_audio.equals("1")) {
                    radioAudioYes.setChecked(true);
                }
                if (str_audio.equals("2")) {
                    radioAudioNo.setChecked(true);
                }

                str_service_place = "" + sharedPreference.getString(ActivityMarriageServiceAdd.this, sharedPreference.getString(ActivityMarriageServiceAdd.this, "service_name") + "service_plan");
                str_service_place_name = "" + sharedPreference.getString(ActivityMarriageServiceAdd.this, sharedPreference.getString(ActivityMarriageServiceAdd.this, "service_name") + "service_plan_name");

                if (!str_service_place.equals("")) {
                    String service_place_id[] = str_service_place.split(",");
                    for (int i = 0; i < service_place_id.length; i++) {
                        System.out.println("service_place_id[" + i + "] = " + service_place_id[i].trim());
                        selectedDistrict.add("" + service_place_id[i]);
                    }
                }

                if (!str_service_place_name.equals("")) {
                    district_spinner_service.setText("" + str_service_place_name);
                    String service_place_name[] = str_service_place_name.split(",");
                    for (int i = 0; i < service_place_name.length; i++) {
                        System.out.println("service_place_id[" + i + "] = " + service_place_name[i].trim());
                        selectedDistrictName.add("" + service_place_name[i]);
                    }
                }

                System.out.println("===edit image " + sharedPreference.getString(ActivityMarriageServiceAdd.this, sharedPreference.getString(ActivityMarriageServiceAdd.this, "service_name") + "images"));
                String images = sharedPreference.getString(ActivityMarriageServiceAdd.this, sharedPreference.getString(ActivityMarriageServiceAdd.this, "service_name") + "images");
                if (!images.equals("")) {
                    String imagesArray[] = images.split(",");
                    for (int i = 0; i < imagesArray.length; i++) {
                        System.out.println("imagesArray[" + i + "] = " + imagesArray[i].trim());

                        if (i == 0) {
                            GlideApp.with(ActivityMarriageServiceAdd.this)
                                    .load(imagesArray[i].trim())
                                    .placeholder(R.drawable.add_image)
                                    .error(R.drawable.add_image)
                                    .skipMemoryCache(true)  //No memory cache
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)   //No disk cache
                                    .into(img1);
                            img1_r.setVisibility(View.VISIBLE);
                            img1_r.setTag(imagesArray[i].trim());
                        }

                        if (i == 1) {
                            GlideApp.with(ActivityMarriageServiceAdd.this)
                                    .load(imagesArray[i].trim())
                                    .placeholder(R.drawable.add_image)
                                    .error(R.drawable.add_image)
                                    .skipMemoryCache(true)  //No memory cache
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)   //No disk cache
                                    .into(img2);
                            img2_r.setVisibility(View.VISIBLE);
                            img2_r.setTag(imagesArray[i].trim());
                        }

                        if (i == 2) {
                            GlideApp.with(ActivityMarriageServiceAdd.this)
                                    .load(imagesArray[i].trim())
                                    .placeholder(R.drawable.add_image)
                                    .error(R.drawable.add_image)
                                    .skipMemoryCache(true)  //No memory cache
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)   //No disk cache
                                    .into(img3);
                            img3_r.setVisibility(View.VISIBLE);
                            img3_r.setTag(imagesArray[i].trim());
                        }

                        if (i == 3) {
                            GlideApp.with(ActivityMarriageServiceAdd.this)
                                    .load(imagesArray[i].trim())
                                    .placeholder(R.drawable.add_image)
                                    .error(R.drawable.add_image)
                                    .skipMemoryCache(true)  //No memory cache
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)   //No disk cache
                                    .into(img4);
                            img4_r.setVisibility(View.VISIBLE);
                            img4_r.setTag(imagesArray[i].trim());
                        }

                        if (i == 4) {
                            GlideApp.with(ActivityMarriageServiceAdd.this)
                                    .load(imagesArray[i].trim())
                                    .placeholder(R.drawable.add_image)
                                    .error(R.drawable.add_image)
                                    .skipMemoryCache(true)  //No memory cache
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)   //No disk cache
                                    .into(img5);
                            img5_r.setVisibility(View.VISIBLE);
                            img5_r.setTag(imagesArray[i].trim());
                        }

                        if (i == 5) {
                            GlideApp.with(ActivityMarriageServiceAdd.this)
                                    .load(imagesArray[i].trim())
                                    .placeholder(R.drawable.add_image)
                                    .error(R.drawable.add_image)
                                    .skipMemoryCache(true)  //No memory cache
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)   //No disk cache
                                    .into(img6);
                            img6_r.setVisibility(View.VISIBLE);
                            img6_r.setTag(imagesArray[i].trim());
                        }

                        if (i == 6) {
                            GlideApp.with(ActivityMarriageServiceAdd.this)
                                    .load(imagesArray[i].trim())
                                    .placeholder(R.drawable.add_image)
                                    .error(R.drawable.add_image)
                                    .skipMemoryCache(true)  //No memory cache
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)   //No disk cache
                                    .into(img7);
                            img7_r.setVisibility(View.VISIBLE);
                            img7_r.setTag(imagesArray[i].trim());
                        }

                        if (i == 7) {
                            GlideApp.with(ActivityMarriageServiceAdd.this)
                                    .load(imagesArray[i].trim())
                                    .placeholder(R.drawable.add_image)
                                    .error(R.drawable.add_image)
                                    .skipMemoryCache(true)  //No memory cache
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)   //No disk cache
                                    .into(img8);
                            img8_r.setVisibility(View.VISIBLE);
                            img8_r.setTag(imagesArray[i].trim());
                        }

                        if (i == 8) {
                            GlideApp.with(ActivityMarriageServiceAdd.this)
                                    .load(imagesArray[i].trim())
                                    .placeholder(R.drawable.add_image)
                                    .error(R.drawable.add_image)
                                    .skipMemoryCache(true)  //No memory cache
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)   //No disk cache
                                    .into(img9);
                            img9_r.setVisibility(View.VISIBLE);
                            img9_r.setTag(imagesArray[i].trim());
                        }

                        if (i == 9) {
                            GlideApp.with(ActivityMarriageServiceAdd.this)
                                    .load(imagesArray[i].trim())
                                    .placeholder(R.drawable.add_image)
                                    .error(R.drawable.add_image)
                                    .skipMemoryCache(true)  //No memory cache
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)   //No disk cache
                                    .into(img10);
                            img10_r.setVisibility(View.VISIBLE);
                            img10_r.setTag(imagesArray[i].trim());
                        }

                        if (i == 10) {
                            GlideApp.with(ActivityMarriageServiceAdd.this)
                                    .load(imagesArray[i].trim())
                                    .placeholder(R.drawable.add_image)
                                    .error(R.drawable.add_image)
                                    .skipMemoryCache(true)  //No memory cache
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)   //No disk cache
                                    .into(img11);
                            img11_r.setVisibility(View.VISIBLE);
                            img11_r.setTag(imagesArray[i].trim());
                        }

                        if (i == 11) {
                            GlideApp.with(ActivityMarriageServiceAdd.this)
                                    .load(imagesArray[i].trim())
                                    .placeholder(R.drawable.add_image)
                                    .error(R.drawable.add_image)
                                    .skipMemoryCache(true)  //No memory cache
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)   //No disk cache
                                    .into(img12);
                            img12_r.setVisibility(View.VISIBLE);
                            img12_r.setTag(imagesArray[i].trim());
                        }

                    }
                }
            }

            try {
                Utils.mProgress.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private class service_dist_load extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Utils.mProgress(ActivityMarriageServiceAdd.this, "ஏற்றுகிறது. காத்திருக்கவும் ", false).show();
        }

        @Override
        protected String doInBackground(String... strr) {
            HttpHandler httpHandler = new HttpHandler();
            ArrayList<HashMap<String, String>> parms = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> temp = new HashMap<String, String>();
            temp.put("action", "" + strr[0]);
            parms.add(temp);
            String result = httpHandler.makeServiceCall(strr[1], parms);
            System.out.println("==result " + result);
            return "" + result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            JSONArray jArray;

            try {

                district_array.clear();

                jArray = new JSONArray(result);

                JSONObject json_data = null;

                for (int i = 0; i < jArray.length(); i++) {
                    json_data = jArray.getJSONObject(i);

                    System.out.println("=== service district id " + json_data.getString("id"));
                    System.out.println("=== service district name " + json_data.getString("tamil"));

                    State state = new State();
                    state.setTitle(json_data.getString("tamil"));
                    state.setEng_tit("");
                    state.setId(Integer.parseInt(json_data.getString("id")));
                    district_array.add(state);

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                Utils.mProgress.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }

            final Dialog dialog_list = new Dialog(ActivityMarriageServiceAdd.this);
            dialog_list.setContentView(R.layout.list_item_check);
            dialog_list.setCanceledOnTouchOutside(true);
            dialog_list.setCancelable(true);

            dialog_list.show();

            ListView listview = dialog_list.findViewById(R.id.list);
            card_submit = dialog_list.findViewById(R.id.card_submit);

            adapter = new Customs_Adapter_list(ActivityMarriageServiceAdd.this, R.layout.layout_checkbox, district_array);
            listview.setAdapter(adapter);

            card_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog_list.dismiss();
                    str_service_place = "";
                    String strSelectedDistrictName = "";

                    for (int i = 0; i < selectedDistrict.size(); i++) {

                        if (str_service_place.length() > 0) {
                            str_service_place = str_service_place + "," + selectedDistrict.get(i);
                        } else {
                            str_service_place = "" + selectedDistrict.get(i);
                        }

                    }

                    for (int i = 0; i < selectedDistrictName.size(); i++) {

                        if (strSelectedDistrictName.length() > 0) {
                            strSelectedDistrictName = strSelectedDistrictName + "," + selectedDistrictName.get(i);
                        } else {
                            strSelectedDistrictName = "" + selectedDistrictName.get(i);
                        }

                    }

                    district_spinner_service.setText("" + strSelectedDistrictName);
                    System.out.println("=== service seleted district id " + str_service_place);
                    System.out.println("=== service seleted district name " + strSelectedDistrictName);
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public class Customs_Adapter_list extends ArrayAdapter {

        Context context;
        LayoutInflater inflater;
        ArrayList<State> arraylist = new ArrayList<>();

        Customs_Adapter_list(Context context, int resource, ArrayList<State> arraylist) {
            super(context, resource, arraylist);
            this.context = context;
            this.arraylist = arraylist;

        }

        @NonNull
        @Override
        public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.layout_checkbox, null);

//            TextView txt = view.findViewById(R.id.name);
            final CheckBox checkBox = view.findViewById(R.id.checkbox);
            checkBox.setText(arraylist.get(position).getTitle());
            checkBox.setId(arraylist.get(position).getId());
            checkBox.setTag("" + position);


            if (selectedDistrict.contains("" + arraylist.get(position).getId())) {
                checkBox.setChecked(true);
            } else {
                checkBox.setChecked(false);
            }

            if (selectedDistrict.size() == 0) {
                card_submit.setAlpha(0.5F);
                card_submit.setEnabled(false);
            }

        /*    view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (checkBox.isChecked()) {
                        checkBox.setChecked(false);
                    } else {
                        checkBox.setChecked(true);
                    }
                }
            });*/

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    System.out.println("== array size " + selectedDistrict.size());

                    if (isChecked) {
                        if (selectedDistrict.size() < 1) {
                            card_submit.setAlpha(1F);
                            card_submit.setEnabled(true);
                            selectedDistrict.add("" + arraylist.get(position).getId());
                            selectedDistrictName.add("" + arraylist.get(position).getTitle());

                        } else {
                            //Toast.makeText(context, "நீங்கள் ஏற்கனவே மூன்று மாவட்டங்களை தேர்வு செய்துவிட்டீர்கள்", Toast.LENGTH_SHORT).show();
                            down_info();
                            notifyDataSetChanged();
                        }
                    } else {
                        selectedDistrict.remove("" + arraylist.get(position).getId());
                        selectedDistrictName.remove("" + arraylist.get(position).getTitle());
                        if (selectedDistrict.size() == 0) {
                            card_submit.setAlpha(0.5F);
                            card_submit.setEnabled(false);
                        }
                    }
                }
            });
            return view;
        }

        public void down_info() {
            final Dialog rating_dialog = new Dialog(ActivityMarriageServiceAdd.this,
                    android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
            rating_dialog.setContentView(R.layout.info_dia);
            rating_dialog.setCancelable(false);
            AppCompatButton btnSend = rating_dialog.findViewById(R.id.btnSend);
            AppCompatTextView textt = rating_dialog.findViewById(R.id.textt);
            AppCompatTextView textView1 = rating_dialog.findViewById(R.id.textView1);

            CardView ok_card = rating_dialog.findViewById(R.id.ok_card);
            btnSend.setText("சரி");


            ok_card.setCardBackgroundColor(Utils.get_color(ActivityMarriageServiceAdd.this));
            textView1.setBackgroundColor(Utils.get_color(ActivityMarriageServiceAdd.this));


            textt.setText("மேலும் மாவட்டங்களை தேர்வு செய்ய 9597215816 என்ற எண்ணிற்கு எங்களை தொடர்பு கொள்ளவும். (திங்கள் - வெள்ளி  10:00 AM - 05:00 PM)");
            textt.setGravity(Gravity.CENTER);


            btnSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    rating_dialog.dismiss();
                }
            });
            rating_dialog.show();
        }

    }

    public static boolean EmailValidation(String value) {
        String email = value.trim();
        Pattern pattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+");
        Matcher matcher = pattern.matcher(email);
        Log.e("email", "" + email + matcher.matches());
        return matcher.matches();
    }

    public void linkDialog() {

        final Dialog dialog = new Dialog(ActivityMarriageServiceAdd.this,
                android.R.style.Theme_DeviceDefault_Dialog_NoActionBar_MinWidth);
        dialog.setContentView(R.layout.link_dialog);

        LinearLayout layout_bg = dialog.findViewById(R.id.layout_bg);
        ImageView image_bg = dialog.findViewById(R.id.image_bg);
        TextView text_bg = dialog.findViewById(R.id.text_bg);
        AppCompatEditText editTextLink = dialog.findViewById(R.id.editTextLink);
        Button btnSend = dialog.findViewById(R.id.btnSend);
        btnSend.setText("Ok");

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            }
        });

        dialog.show();
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
                            sharedPreference.putString(ActivityMarriageServiceAdd.this, "imageurl", list_images);
                            sharedPreference.putInt(ActivityMarriageServiceAdd.this, "image_poss_val", position);
                            Intent i = new Intent(ActivityMarriageServiceAdd.this, Activity_slider.class);
                            startActivity(i);
                        } else {
                            Utils.toast_center(ActivityMarriageServiceAdd.this, "புகைப்படம் ஏதும் இல்லை");
                        }

                    } else {
                        Utils.toast_center(ActivityMarriageServiceAdd.this, "இணையதள சேவையை சரிபார்க்கவும் ");
                    }

                }
            });

            int drawble = R.drawable.mandapamsample;
            if (sharedPreference.getInt(ActivityMarriageServiceAdd.this, "activity_value") == 1) {
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

}
