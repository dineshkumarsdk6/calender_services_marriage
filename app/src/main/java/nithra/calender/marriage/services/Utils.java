package nithra.calender.marriage.services;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import static android.text.TextUtils.isEmpty;
import static androidx.core.content.pm.PackageInfoCompat.getLongVersionCode;


/**
 * Created by NITHRA-G5 on 12/2/2015.
 */
public class Utils {

    public static String service_type_mandapam = "1";


   /* //local server
    public static String getDistrictCity = "http://192.168.1.101/calender_service/services/api/registration.php";
    public static String url_calender_service = "http://192.168.1.101/calender_service/services/api/registration.php";
    public static String url_calender_service_decerate = "http://192.168.1.101/calender_service/services/api/registration.php";
    public static String getAllDetails = "http://192.168.1.101/calender_service/services/api/viewdetails.php?id=";
    public static String pay_url = "http://192.168.1.101/calender_service/services/api/viewplan.php?userid=";*/

    //local server
    public static String getDistrictCity = " https://nithra.mobi/calendar/services/thirumanam/api/registration.php";
    public static String url_calender_service = " https://nithra.mobi/calendar/services/thirumanam/api/registration.php";
    public static String url_calender_service_decerate = " https://nithra.mobi/calendar/services/thirumanam/api/registration.php";
    public static String getAllDetails = " https://nithra.mobi/calendar/services/thirumanam/api/viewdetails.php?id=";
    public static String pay_url = " https://nithra.mobi/calendar/services/thirumanam/api/viewplan.php?userid=";

/*    //local server
    public static String getDistrictCity = "http://192.168.43.98/calender_service/services/api/registration.php";
    public static String url_calender_service = "http://192.168.43.98/calender_service/services/api/registration.php";
    public static String url_calender_service_decerate = "http://192.168.43.98/calender_service/services/api/registration.php";
    public static String getAllDetails = "http://192.168.43.98/calender_service/services/api/viewdetails.php?id=";
    public static String pay_url = "http://192.168.43.98/calender_service/services/api/viewplan.php?userid=";*/

    /*public static String url_1 = "http://192.168.57.28/apps/tc_apps/api/today_event.php";*/
    public static String url_1 = "https://nithra.mobi/apps/tc_apps/api/today_event.php";

    /*public static String url_2 = "http://192.168.57.28/apps/tc_apps/api/simple_reg.php";*/
    public static String url_2 = "https://nithra.mobi/apps/tc_apps/api/simple_reg.php";


    public static String progi_reg = "http://192.168.57.27/calendar/services/api/data.php";
    public static String progi_invoice = "http://192.168.27.25/calendar/services/pdf/CALENDAR_";
    public static String progi_plan = "http://192.168.57.27/calendar/services/api/viewplan.php?type=";
    public static String progi_Image1 = "http://192.168.57.27/calendar/services/profile_pic/progimg_";
    public static String progi_Image2 = "http://192.168.57.27/calendar/services/profile_pic/prog_otherimg_";
    public static String progi_preview = "http://192.168.57.27/calendar/services/api/viewdetails.php?id=";
    public static String progi_share = "http://192.168.57.27/calendar/services/api/shareview.php?id=";
    public static String progi_privacy = "http://192.168.57.27/calendar/services/terms_conditions.php";

   /* public static String progi_reg = "https://www.nithra.mobi/calendar/services/api/data.php";
    public static String progi_invoice = "https://nithra.mobi/calendar/services/pdf/CALENDAR_";
    public static String progi_plan = "https://nithra.mobi/calendar/services/api/viewplan.php?type=";
    public static String progi_Image1 = "https://nithra.mobi/calendar/services/profile_pic/progimg_";
    public static String progi_Image2 = "https://nithra.mobi/calendar/services/profile_pic/prog_otherimg_";
    public static String progi_preview = "https://nithra.mobi/calendar/services/api/viewdetails.php?id=";
    public static String progi_share = "https://www.nithra.mobi/calendar/services/api/shareview.php?id=";
    public static String progi_privacy = "https://www.nithra.mobi/calendar/services/terms_conditions.php";*/


   // public static ArrayList<Dist_item> dist_item = new ArrayList<>();

    public static ProgressDialog mProgress;
    public static String[] tam_months = {"ஜனவரி", "பிப்ரவரி", "மார்ச்", "ஏப்ரல்", "மே", "ஜூன்", "ஜூலை", "ஆகஸ்ட்", "செப்டம்பர்", "அக்டோபர்", "நவம்பர்", "டிசம்பர்"};
    static String tam_weeks[] = {"ஞாயிறு", "திங்கள்", "செவ்வாய்", "புதன்", "வியாழன்", "வெள்ளி", "சனி"};

    public static String tam_viratham1[] = {"அமாவாசை", "பௌர்ணமி", "கிருத்திகை", "திருவோணம்", "சஷ்டி", "பிரதோஷம்", "சதுர்த்தி", "சங்கடஹர சதுர்த்தி", "ஏகாதசி", "சிவராத்திரி"};


    public static String porutham_tit[] = {"தினப்பொருத்தம்", "கணப்பொருத்தம்","மகேந்திர பொருத்தம்", "ஸ்திரி தீர்க்கம்", "யோனிப் பொருத்தம்",
            "ராசி பொருத்தம் ", "ராசி அதிபதி பொருத்தம் ", "வசிய பொருத்தம் ", "ரச்சுப் பொருத்தம்", "வேதை  பொருத்தம்",
            "நாடிப் பொருத்தம்", "விருட்ச(மரம்) பொருத்தம்"};
    public static String porutham_item[] = {"தினம் என்றால் நட்சத்திரம் எனப் பொருள். நாள்தோறும் சந்திர பகவான் தங்குமிடம் அல்லது ஷேத்திரம். அதுவே நாள் ஷேத்திரம். இது மருவி நட்சத்திரம் என ஆகியது. எனவே இந்த தினப்பொருத்தம் முக்கியமான ஒன்று. நட்சத்திரங்கள் மொத்தம் 27 ஆகும்.",
            "கணம் என்றால் கூட்டம் என பொருள்படும். மூன்று வகை கணங்களாக அல்லது கூட்டமாக 27 நட்சத்திரங்களும் பிரிவினை செய்யப்பட்டுள்ளன. இதன் மூலம் இருவரின் இல்லற சுகம், ஒற்றுமை, இவை தீர்மானிக்கப்படும். தேவ கணம் - மனுஷ கணம் - ராட்சஸ கணம்  என மூன்று  வகைப்படும்.",
            "புத்திம விருத்தி தரும் பொருத்தம் இது பெண்ணின் நட்சத்திரம் தொட்டு ஆண் நட்சத்திரம் வரை எண்ணிய தொகை 4, 7, 10, 13, 16, 19, 22, 25 ஆவது எனில் மகேந்திர பொருத்தம் உண்டு எனலாம். மற்றவை எனில் பொருத்தமில்லை. இப்பொருத்தம் அவசியமே. ஆனால் முக்கியமானது அல்ல. இப்பொருத்தம் இல்லையெனில் ஜாதகங்களில் புத்திரஸ்தான பலனை கொண்டு ஜோதிடர் தீர்மானிப்பார்.",
            "பெயரே சொல்கிறது. பெண்ணின் தீர்க்கம் அல்லது ஆயுள், ஆரோக்கியம் ஆணின் நட்சத்திர தொடர்பால் எவ்விதம் மாறுபாடு அடைகிறது என்பதை சொல்லும்! பெண் நட்சத்திரம் தொட்டு ஆண் நட்சத்திரம் ஏழுக்குள் எனில் பொருத்தம் இல்லை. ஏழுக்கு மேல் பதிமூன்று வரை எனில் மத்திம பொருத்தமே. பதிமூன்றுக்கு மேல் என்றால் உத்தமம். இப்பொருத்தம் இல்லையெனிலும் பெண் ஜாதகத்தின் ஆறாம் ஸ்தானம் அல்லது எட்டாம் ஸ்தானம் அல்லது ஆண் ஜாதகத்தில் பனிரெண்டாம் ஸ்தானம் அல்லது இரண்டாம் ஸ்தான பலம் மூலம் ஜோதிடர்கள் தீர்மானிக்க இயலும்.",
            "இது முக்கியமான ஒன்றாகும். தாம்பத்ய உறவின் சுகம், திருப்தி நிலை இவற்றை தீர்மானிக்கும் அளவு கோல் எனலாம். ஆண், பெண் யோனி நிலையை மிருகங்களாக உருவகம் செய்து பொருத்தம் பார்க்கும் முறை இது.  நட்சத்திரங்கள் 13 மிருகங்களாக ஆண் - பெண் என பிரிக்கப்பட்டுள்ளது. உத்திராடம் நட்சத்திரம் மட்டும் கீரி எனவும், சில சாஸ்திர  நூல்கள் மலட்டு பசு எனவும் சொல்கின்றன. ஒவ்வொரு மிருகத்திற்கும் பகை மிருகம் உண்டு. ஆண் - பெண் பகை மிருகமெனில் மட்டுமே பொருத்தமில்லை எனலாம். ஆணிற்கு ஆண் மிருகமும், பெண்ணிற்கு பெண் மிருகம் எனினும் உத்தமமே! பெண்ணிற்கு ஆண் மிருகமும்,  ஆணிற்கு பெண் மிருகம் என்றாலும் உத்தமமே. பகை மிருகம் மட்டும் சேர்க்கக் கூடாது. மற்ற பொருத்தம் எத்தனை இருந்தாலும் இது முக்கியம்.",
            "பெண் ராசி  தொட்டு ஆண் ராசி 6 க்கு மேல் எனில் பொருத்தம் உண்டு என்கிறது சாஸ்திரம். ஆனால் அனுபவத்தில் ஒரே ராசி எனில் உத்தமமே! ஆனால் நட்சத்திரம் மாறுபட்டு இருக்க வேண்டும்! எனவே பெண் ராசி தொட்டு 2, 3, 4, 5 மற்றும் 6ம் ராசி எனில் பொருத்தமில்லை. அதேபோல் பெண் ராசி தொட்டு ஆண் ராசி 8 எனிலும் பொருத்தம் அதிகம் இல்லை. எனவே பெண் ராசி முதல் ஆண் ராசி 1, 7, 9, 10, 11, 12 ஆகிய ஆறு ராசிகள் பொருத்தம் எனலாம்.",
            "பனிரெண்டு ராசிகட்கும் அதிபதி உண்டு. அந்த அதிபதி கிரகத்திற்கு நட்பு, சமம், பகை என மூன்று வகையால்  மற்ற கிரகங்களுடன் உறவும் உண்டு. பெண்ணின் ராசி அதிபதி, ஆண் ராசி அதிபதிக்கு பகை என்று உறவு எனில் மட்டுமே பொருத்தம் இல்லை. நட்பு, சமம் எனில் பொருத்தம் உண்டு.",
            "இப்பொருத்தம் கணவண் - மனைவி அன்னியோன்ய உறவை குறிகாட்டும். ஒரு ராசிக்கு ஒன்று அல்லது இரண்டு ராசிகளே வசியமாக அமையும். இது அமைந்தால் இன்னும் சிறப்பாகும். மற்றபடி இப்பொருத்தம் இல்லை எனினும் பாதகம் இல்லை.",
            "சரசோதிமலை எனும் தமிழ் ஜோதிட காவியம் இவ்வித பத்து பொருத்தங்களினாலும் உண்டாகும் பலன் எவை என குறிப்பிடும் சமயம்  இரச்சுமங்கிலியங் என தெளிவாக சொல்கிறது. இவ்விருவர் இணைவால் உண்டாகும் திருமண வாழ்வின் நீண்ட, மத்திம குறுகிய ஆயுளை ரச்சுப் பொருத்தம் தீர்மானிக்கிறது. இதை நாட்டுபுற வழக்கில் சரடு பொருத்தம் என்றும் கூறுவார்கள். இந்து சாஸ்திரத்தின் படி மிகவும் புனிதமாக ஏற்கப்பட்டுள்ள திருமாங்கலிய கயிறு - அதன் ஆயுளை தீர்மானிப்பதால் இது முக்கியமாக ஏற்கப்படுகிறது. ஏனைய பொருத்தம் அமைந்த இந்த ரச்சு எனும் மங்கல்ய சரடு பொருத்தம் இல்லையெனில் நன்மை இல்லை. ஏனைய பொருத்தம் அதிகம் இல்லாமல் ரச்சு மட்டுமே பலமாக அமைந்தால் கூட சுகவாழ்வில் சிக்கல் வந்தாலும் திருமண வாழ்வின் ஆயுள் நீண்டு அமையும். கூடி அமைந்த காதல் திருமணங்கள் தோல்வியை அடைவது ரச்சுப் பொருத்தம் காரணம் என்பது எமது அனுபவம். இனி இது எவ்விதம் உண்டாகும் என பார்வை செய்வோம். நட்சத்திரங்கள்  ஐந்து வகை ரச்சு என பிரிக்கப்பட்டுள்ளன.  அவை பாதம், தொடை, உதரம், கண்டம், சிரசு எனப்படும். ஆண் - பெண் ஒரே ரச்சுவாக இருக்கக்கூடாது. இதை ஆரோகணம் (ஏறுமுக ரச்சு) மற்றும் அவரோகணம் (இறங்கு முக ரச்சு) எனவும் பிரிவினை செய்துள்ளனர். ",
            "வேதை எனும் சொல்லுக்கு துன்ப நிலை என பொருள்படும். ஒவ்வொரு நட்சத்திரத்திற்கும் ஒரே ஒரு நட்சத்திரம் வேதையாக அமையும். இவ்வித வேதை நட்சத்திரம் ஒரே ரச்சுவாக ரச்சு பொருத்தம் இல்லாத நட்சத்திரமாகவே அமையும். ரச்சு பொருத்தம் குறுகிய கால மணவாழ்வு கூட சந்தோஷமாக அமைந்து முடியலாம். ஆனால் வேதை நட்சத்திரம் இணைந்தால் அந்த குறுகிய கால மணவாழ்வும் துன்பமாகவே அமையும்.",
            "நம்முடைய உடலில் மூவகை நாடி உள்ளது. அவை பாதம், பித்தம், கபம் அல்லது சிலேத்துமம் ஆகும். இவ்வகை நாடி மூன்றும் சம அளவில் உடலில் அமைந்தால் உடல் சீராக, ஆரோக்கியமாக இருக்கும். நட்சத்திரங்களும் முறையே பார்சுவம் (வாத நாடி) மத்யம் (பித்தம்) மற்றும் சமானம் (சிலேத்துமம் அல்லது கபம் நாடி) என பிரிக்கப்பட்டுள்ளன. பொதுவாக ஆண் - பெண் இருவரும் ஒரே நாடியாக இருக்கக்கூடாது. வெவ்வேறு நாடியாக அமைதல் உத்தமம், ரச்சு - வேதை - தினம் இம்மூன்றும் பொருத்தமாக அமைந்தால் நாடிப் பொருத்தம் மிகவும் அவசியம் இல்லை.",
            "விருட்சம் அல்லது மரம் இரண்டு வகைப்படும். ஒன்று நெகிழ்வு உள்ள இளகிய பால் மரங்கள். மற்றொன்று உறுதியான நெகிழ்வு குறைந்த வயிரம் மரங்கள் ஆகும். பெண் பால்மர நட்சத்திரங்களில் என்றால் நெகிழ்வு உண்டாகி வயிரத்தை கொள்ளும். ஆண் வயிர மரம் எனில் நெகிழ்வான பால் மரத்தை ஊடுருவி வெற்றி காணலாம். எனவே, பெண் பால் மர நட்சத்திரமும், ஆண் வயிர மர நட்சத்திரமும் எனில் மிகவும் உன்னதம் ஆகும். இதனால் தாம்பத்ய வாழ்வில் சந்தோஷமும், புத்திரவிருத்தி உண்டாகும்."};

    public static Dialog down_dia;

    public static TextView down_state;
  //  public static ArcProgress arc_progress;


    public static final String URL_REGEX = "^((https?|ftp)://|(www|ftp)\\.)?[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$";


    public static Dialog dialog_msg;


    public static String ram_s = "<html><meta charset='UTF-8'><b><center><font color=#438910>\n" +
            "ஸ்ரீராம ஆரூடச் சக்கரம் </font></center><br>\n" +
            "<div align=justify><font color=#3D434C>\n" +
            "&#x1F496;ஆரூடச் சக்கரம் என்பது நம் மனதில் கொண்டுள்ள கேள்விகளுக்கு எளிய முறையில் பதில் அளிக்கும் சக்கரம் ஆகும்.<br><br>&#x1F49D;\n" +
            "ராமர் ஆரூடச் சக்கரத்தின் மூலமாக நீங்கள் செய்ய இருக்கும் செயல்களால் உண்டாகும் எதிர்கால நன்மைகள் மற்றும் பலன்களை அறிந்து கொள்ள குலதெய்வம், இஷ்ட தெய்வம் மற்றும் ராமரை மனதில் தியானித்து ஆரூடச் சக்கரதில் உள்ள ஏதேனும் ஓர் எண்ணைத் தொட்டு உங்களின் எதிர்கால பலன்களை அறிந்து வாழ்க்கையில் முன்னேற்றம் காணலாம்.<hr>\n" +
            "</font></div></b> <b><center><font color=#438910>\n" +
            "சீதா ஆரூடச் சக்கரம் </font></center><br>\n" +
            "<div align=justify><font color=#3D434C>\n" +
            "\n" +
            "&#x1F496;ஆரூடச் சக்கரம் என்பது நம் மனதில் கொண்டுள்ள கேள்விகளுக்கு எளிய முறையில் பதில் அளிக்கும் சக்கரம் ஆகும்.<br><br>\n" +
            "&#x1F49D;சீதா ஆரூடச் சக்கரத்தின் மூலமாக நீங்கள் செய்ய இருக்கும் செயல்களால் உண்டாகும் எதிர்கால நன்மைகள் மற்றும் பலன்களை அறிந்து கொள்ள குலதெய்வம், இஷ்ட தெய்வம் மற்றும் சீதாவை மனதில் தியானித்து ஆரூடச் சக்கரதில் உள்ள ஏதேனும் ஓர் எண்ணைத் தொட்டு உங்களின் எதிர்கால பலன்களை அறிந்து வாழ்க்கையில் முன்னேற்றம் காணலாம்.<hr>\n" +
            "</font></div></b></html>";


/*
    public static final String NATIVE_AD_UNIT = "ca-app-pub-4267540560263635/5918419908";
    public static final String ADMOB_APP_ID = "ca-app-pub-4267540560263635~7529582306";
    public static final String Tamil_Calendar_Banner_Activity = "ca-app-pub-4267540560263635/3500998874";
    public static final String Tamil_Calendar_Banner_Home = "ca-app-pub-4267540560263635/9608389808";
    public static final String Tamil_Calendar_Banner_Noti = "ca-app-pub-4267540560263635/6404298503";
    // public static final String Tamil_Calendar_Cat_Exit_Ins = "ca-app-pub-4267540560263635/2771734709";
    public static final String Tamil_Calendar_INS_Exit = "ca-app-pub-4267540560263635/1483048704";
    //public static final String Tamil_Calendar_INS_Noti_Exit = "ca-app-pub-4267540560263635/2212131506";
    public static final String Tamil_Calendar_Noti_Offline_Banner = "ca-app-pub-4267540560263635/4432035500";
    public static final String Tamil_Calendar_Noti_Offline_INS = "ca-app-pub-4267540560263635/5908768702";
    public static final String Tamil_Calendar_Off_ON_Noti_Activity_Banner = "ca-app-pub-4267540560263635/4160942451";
*/


    public static final String NATIVE_AD_UNIT = "ca-app-pub-3940256099942544/2247696110";
    public static final String ADMOB_APP_ID = "ca-app-pub-4267540560263635~7529582306";
    public static final String Tamil_Calendar_Banner_Activity = "ca-app-pub-3940256099942544/6300978111";
    public static final String Tamil_Calendar_Banner_Home = "ca-app-pub-3940256099942544/6300978111";
    public static final String Tamil_Calendar_Banner_Noti = "ca-app-pub-3940256099942544/6300978111";
    //  public static final String Tamil_Calendar_Cat_Exit_Ins = "ca-app-pub-3940256099942544/1033173712";
    public static final String Tamil_Calendar_INS_Exit = "ca-app-pub-3940256099942544/1033173712";
    // public static final String Tamil_Calendar_INS_Noti_Exit = "ca-app-pub-3940256099942544/1033173712";
    public static final String Tamil_Calendar_Noti_Offline_Banner = "ca-app-pub-3940256099942544/6300978111";
    public static final String Tamil_Calendar_Noti_Offline_INS = "ca-app-pub-3940256099942544/1033173712";
    public static final String Tamil_Calendar_Off_ON_Noti_Activity_Banner = "ca-app-pub-3940256099942544/6300978111";


    public static final String Full_Native = "1128938127143022_2667428689960617";
    public static final String Native_Banner = "1128938127143022_2667431246627028";
    /*public static final String Cat_in_ins_Calendar_Sheet = "1128938127143022_2667432299960256";
    public static final String Cat_in_Ins_Rasipalangal = "1128938127143022_2667433226626830";
    public static final String cat_in_ins_other_content = "1128938127143022_2667437496626403";*/
    public static final String Normal_Banner = "1128938127143022_2667438083293011";
    public static final String Noti_Banner = "1128938127143022_2667438643292955";
    public static final String Noti_Exit_Ins = "1128938127143022_2667439559959530";
    public static final String Activity_Rec = "1128938127143022_2667441409959345";
    public static final String Rasiapaln_Banner = "1128938127143022_2671542642882555";
    public static final String Daily_Sheet_Native_Banner = "1128938127143022_2673013839402102";
    public static final String Monthly_Sheet_Native_Banner = "1128938127143022_2673015512735268";
    public static final String banner_online = "1128938127143022_2671498059553680";
    public static final String Daily_Rasipalan_Banner = "1128938127143022_2685444498159036";
    public static final String Kiraga_peyarchi_Banner = "1128938127143022_2685445661492253";
    public static final String Semi_Noti_Ins_Ad_Break = "1128938127143022_2685449738158512";
    public static final String Holiday_Banner = "1128938127143022_2685457344824418";
    public static final String Viratham_Banner = "1128938127143022_2685462698157216";
    public static final String Suba_Muhurtham = "1128938127143022_2685463264823826";
    public static final String Kathaigal_Banner = "1128938127143022_2685465054823647";
    public static final String Old_Post_Banner = "1128938127143022_2685465534823599";

    public static ProgressDialog mProgress(Context context, String txt, Boolean aBoolean) {
        mProgress = new ProgressDialog(context);
        mProgress.setMessage(txt);
        mProgress.setCancelable(aBoolean);
        return mProgress;
    }

    public static void settypeface(Context context, TextView textView) {
        Typeface tf1 = Typeface.createFromAsset(context.getAssets(), "baamini.ttf");
        textView.setTypeface(tf1);
    }

    public static int versioncode_get(Context context) {
        PackageInfo pInfo = null;

        try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return (int) getLongVersionCode(pInfo);
    }

    public static String versionname_get(Context context) {
        PackageInfo pInfo = null;

        try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return pInfo.versionName;
    }

    public static String[] empty_array(int val) {
        String str[] = new String[val];
        for (int i = 0; i > val; i++) {
            str[i] = "";
        }
        return str;
    }

    public static int[] empty_array1(int val) {
        int str[] = new int[val];
        for (int i = 0; i > val; i++) {
            str[i] = 0;
        }
        return str;
    }


    public static String[] mergeArrays(String[] mainArray, String[] addArray) {
        String[] finalArray = new String[mainArray.length + addArray.length];
        System.arraycopy(mainArray, 0, finalArray, 0, mainArray.length);
        System.arraycopy(addArray, 0, finalArray, mainArray.length, addArray.length);

        return finalArray;
    }

    public static int[] mergeArrays1(int[] mainArray, int[] addArray) {
        int[] finalArray = new int[mainArray.length + addArray.length];
        System.arraycopy(mainArray, 0, finalArray, 0, mainArray.length);
        System.arraycopy(addArray, 0, finalArray, mainArray.length, addArray.length);

        return finalArray;
    }


    public static int get_weekday(String str) {
        int val = 0;
        if (str.equals("1")) {
            val = 0;
        } else if (str.equals("2")) {
            val = 1;
        } else if (str.equals("3")) {
            val = 2;
        } else if (str.equals("4")) {
            val = 3;
        } else if (str.equals("5")) {
            val = 4;
        } else if (str.equals("6")) {
            val = 5;
        } else if (str.equals("7")) {
            val = 6;
        }
        return val;
    }


    public static String get_month(int val) {
        String str = "";
        if (val == 0) {
            str = "ஜனவரி";
        } else if (val == 1) {
            str = "பிப்ரவரி";
        } else if (val == 2) {
            str = "மார்ச்";
        } else if (val == 3) {
            str = "ஏப்ரல்";
        } else if (val == 4) {
            str = "மே";
        } else if (val == 5) {
            str = "ஜூன்";
        } else if (val == 6) {
            str = "ஜூலை";
        } else if (val == 7) {
            str = "ஆகஸ்ட்";
        } else if (val == 8) {
            str = "செப்டம்பர்";
        } else if (val == 9) {
            str = "அக்டோபர்";
        } else if (val == 10) {
            str = "நவம்பர்";
        } else if (val == 11) {
            str = "டிசம்பர்";
        }

        return str;
    }


    public static int get_month_num(String str) {
        int val = 0;
        if (str.equals("ஜனவரி")) {
            val = 1;
        } else if (str.equals("பிப்ரவரி")) {
            val = 2;
        } else if (str.equals("மார்ச்")) {
            val = 3;
        } else if (str.equals("ஏப்ரல்")) {
            val = 4;
        } else if (str.equals("மே")) {
            val = 5;
        } else if (str.equals("ஜூன்")) {
            val = 6;
        } else if (str.equals("ஜூலை")) {
            val = 7;
        } else if (str.equals("ஆகஸ்ட்")) {
            val = 8;
        } else if (str.equals("செப்டம்பர்")) {
            val = 9;
        } else if (str.equals("அக்டோபர்")) {
            val = 10;
        } else if (str.equals("நவம்பர்")) {
            val = 11;
        } else if (str.equals("டிசம்பர்")) {
            val = 12;
        }

        return val;
    }

    public static void toast_normal(Context context, String str) {
        Toast.makeText(context, "" + str, Toast.LENGTH_SHORT).show();
    }

    public static void toast_center(Context context, String str) {
        Toast toast = Toast.makeText(context, "" + str, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static String engtobamini(String str) {
        if (str.equals("hindu_fes")) {
            str = "இந்து பண்டிகைகள்";
        } else if (str.equals("muslim_fes")) {
            str = "முஸ்லீம் பண்டிகைகள்";

        } else if (str.equals("chirs_fes")) {
            str = "கிறிஸ்தவ பண்டிகைகள்";

        } else if (str.equals("gov_holiday")) {
            str = "அரசு விடுமுறை நாட்கள்";

        }
        return str;
    }


    public static String pad(String str) {
        if (str.length() == 1) {
            str = "0" + str;
        }
        return str;
    }


    public static String rasii(String str) {
        if (str.equals("மேஷம்")) {
            str = "mesam";
        } else if (str.equals("ரிஷபம்")) {
            str = "risibam";
        } else if (str.equals("மிதுனம்")) {
            str = "mithunam";
        } else if (str.equals("கடகம்")) {
            str = "kadakam";
        } else if (str.equals("சிம்மம்")) {
            str = "simmam";
        } else if (str.equals("கன்னி")) {
            str = "kanni";
        } else if (str.equals("துலாம்")) {
            str = "thulam";
        } else if (str.equals("விருச்சகம்")) {
            str = "viruchakam";
        } else if (str.equals("தனுசு")) {
            str = "dhanusu";
        } else if (str.equals("மகரம்")) {
            str = "makaram";
        } else if (str.equals("கும்பம்")) {
            str = "kumbam";
        } else if (str.equals("மீனம்")) {
            str = "meenam";
        } else if (str.equals("Nk~k;")) {
            str = "mesam";
        } else if (str.equals("up~gk;")) {
            str = "risibam";
        } else if (str.equals("kpJdk;")) {
            str = "mithunam";
        } else if (str.equals("flfk;")) {
            str = "kadakam";
        } else if (str.equals("rpk;kk;")) {
            str = "simmam";
        } else if (str.equals("fd;dp")) {
            str = "kanni";
        } else if (str.equals("Jyhk;")) {
            str = "thulam";
        } else if (str.equals("tpUr;rfk;")) {
            str = "viruchakam";
        } else if (str.equals("jDR")) {
            str = "dhanusu";
        } else if (str.equals("kfuk;")) {
            str = "makaram";
        } else if (str.equals("Fk;gk;")) {
            str = "kumbam";
        } else if (str.equals("kPdk;")) {
            str = "meenam";
        } else {
            str = "meenam";
        }
        return str;
    }


    public static String getDeviceName() {

        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        String Brand = Build.BRAND;
        String Product = Build.PRODUCT;

        return manufacturer + "-" + model + "-" + Brand + "-" + Product;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connec = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connec.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static boolean isNetworkAvailable1(Context context) {
        if (context != null) {
            ConnectivityManager connec = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connec.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        } else {
            return false;
        }

    }

    public static String am_pm(int hur, int min) {

        String AM_PM = "AM";

        if (hur >= 12) {
            hur = hur - 12;
            AM_PM = "PM";
        } else {
            AM_PM = "AM";

        }
        if (hur == 0) {
            hur = 12;
        }

        return Utils.pad("" + hur) + " : " + Utils.pad("" + min) + " " + AM_PM;
    }


    public static void data_insert(SQLiteDatabase myDB, String des, String date, String time, String day, String month, String year, String isremaind) {
        //notes (id integer NOT NULL PRIMARY KEY AUTOINCREMENT,des VARCHAR,date VARCHAR,time VARCHAR,day VARCHAR,month VARCHAR,year VARCHAR,isremaind INT(4),isclose INT(4));");
        ContentValues values = new ContentValues();

        values.put("des", des);
        values.put("date", date);
        values.put("time", time);
        values.put("day", day);
        values.put("month", month);
        values.put("year", year);
        values.put("isremaind", isremaind);
        values.put("isclose", "0");

        System.out.println("data_insert == " + values);
        myDB.insert("notes", null, values);
    }

    public static void data_update(SQLiteDatabase myDB, String des, String id, String time, String day, String month, String year, String isremaind, String datee) {
        ContentValues values = new ContentValues();
        values.put("des", des);
        values.put("date", datee);
        values.put("time", time);
        values.put("day", day);
        values.put("month", month);
        values.put("year", year);
        values.put("isremaind", isremaind);
        values.put("isclose", 0);
        System.out.println("data_update == " + values);
        myDB.update("notes", values, "id='" + id + "'", null);
    }

    public static int get_id(Context context, SQLiteDatabase myDB, String str, String str1, String msgg) {
        if (myDB == null) {
            myDB = context.openOrCreateDatabase("myDB", 0, null);
        }
        int val = 0;
        try {
            Cursor c = myDB.rawQuery("select id from notes where date = '" + str + "' AND time = '" + str1 + "' AND des = '" + msgg + "' order by id desc limit 1", null);
            if (c.getCount() != 0) {
                c.moveToFirst();
                val = c.getInt(0);
            }
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return val;
    }


    public static String tp_val(String val) {
        String str = "";
        if (val.equals("0")) {
            str = "இல்லை";
        } else if (val.equals("1")) {
            str = "மத்திமம்";
        } else if (val.equals("2")) {
            str = "உத்தமம்";
        } else if (val.equals("3")) {
            str = "உன்னதம்";
        } else if (val.equals("4")) {
            str = "அதி உன்னதம்";
        }
        return str;
    }

    public static String get_curday(int val) {
        Calendar cal = Calendar.getInstance();
        //  String month = Utils.get_month(cal.get(Calendar.MONTH));
        if (val != 0) {
            cal.add(Calendar.DAY_OF_MONTH, val);
        }
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return day + "/" + (month + 1) + "/" + year;
    }


/*    public static int get_cur_pos(DataBaseHelper db, String strr) {

        Cursor c = db.getQry("select date from main_table where  year > '2015' ");
        int poss = 0;
        for (int i = 0; i < c.getCount(); i++) {
            c.moveToPosition(i);
            if (c.getString(0).equals(strr)) {
                poss = i;
            }
        }

        return poss;
    }*/


/*    public static String[] get_year(DataBaseHelper db) {
        Cursor c = db.getQry("select distinct year from main_table where year > '2015'");
        String[] str = new String[c.getCount()];
        for (int i = 0; i < c.getCount(); i++) {
            c.moveToPosition(i);
            str[i] = c.getString(0);
        }

        return str;
    }*/

 /*   public static String[] get_stars(DataBaseHelper1 db) {
        Cursor c = db.getQry("select * from stars");
        String[] str = new String[c.getCount() + 1];
        str[0] = "நட்சத்திரத்தை தேர்வு செய்க";
        for (int i = 0; i < c.getCount(); i++) {
            c.moveToPosition(i);
            str[i + 1] = CodetoTamilUtil.convertToTamil(CodetoTamilUtil.BAMINI, c.getString(c.getColumnIndex("stars")));
        }

        return str;
    }

    public static String[] get_stars1(DataBaseHelper1 db) {
        Cursor c = db.getQry("select * from stars");
        String[] str = new String[c.getCount() + 1];
        str[0] = "நட்சத்திரத்தை தேர்வு செய்க";
        for (int i = 0; i < c.getCount(); i++) {
            c.moveToPosition(i);
            str[i + 1] = c.getString(c.getColumnIndex("stars"));
        }

        return str;
    }*/


    public static boolean checkGET_StoragePermission(Context context) {
        int result = ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }


    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


    public static boolean rate_check(Context context) {
        boolean shown = false;
        SharedPreference sharedPreference = new SharedPreference();


        Calendar calendar = Calendar.getInstance();
        long next_hour = calendar.getTimeInMillis();

        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/M/yyyy");
        Date result = new Date(next_hour);
        String formatted = sdf1.format(result);

        StringTokenizer st2 = new StringTokenizer(formatted, "/");
        int rep_day = Integer.parseInt(st2.nextToken());
        int rep_month = Integer.parseInt(st2.nextToken());
        int rep_year = Integer.parseInt(st2.nextToken());

        rep_month = rep_month - 1;

        String today_date = rep_day + "/" + rep_month + "/" + rep_year;

        Date date_today = null, date_app_update = null;

        try {
            date_today = sdf1.parse(today_date);
            if (!sharedPreference.getString(context, "rate_date").equals("")) {
                date_app_update = sdf1.parse(sharedPreference.getString(context, "rate_date"));
            } else {
                date_app_update = sdf1.parse(today_date);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (sharedPreference.getInt(context, "rate_show") == 0) {
            shown = true;
            sharedPreference.putInt(context, "rate_show", 1);
            Utils.date_put(context, "rate_date", 15);
        } else if (sharedPreference.getInt(context, "rate_show") == 1) {
            if (date_today.compareTo(date_app_update) >= 0) {
                shown = true;
                sharedPreference.putInt(context, "rate_show", 2);
                Utils.date_put(context, "rate_date", 30);
            } else {
                shown = false;
            }

        } else if (sharedPreference.getInt(context, "rate_show") == 2) {
            if (date_today.compareTo(date_app_update) >= 0) {
                shown = true;
                sharedPreference.putInt(context, "rate_show", 2);
                Utils.date_put(context, "rate_date", 60);
            } else {
                shown = false;
            }

        } else {
            shown = false;
        }

        return shown;
    }


    public static void date_put(Context context, String str, int val) {
        Calendar calendar = Calendar.getInstance();
        long next_hour = calendar.getTimeInMillis() + val * DateUtils.DAY_IN_MILLIS;

        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/M/yyyy");
        Date results = new Date(next_hour);
        String formatted = sdf1.format(results);

        StringTokenizer st2 = new StringTokenizer(formatted, "/");
        int rep_day = Integer.parseInt(st2.nextToken());
        int rep_month = Integer.parseInt(st2.nextToken());
        int rep_year = Integer.parseInt(st2.nextToken());

        rep_month = rep_month - 1;

        String strdate = rep_day + "/" + rep_month + "/" + rep_year;

        SharedPreference sharedPreference = new SharedPreference();
        sharedPreference.putString(context, str, strdate);
    }

    public static boolean update_check(Context context) {

        Boolean aBoolean = false;
        SharedPreference sharedPreference = new SharedPreference();
        Calendar calendar = Calendar.getInstance();
        long next_hour = calendar.getTimeInMillis();

        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/M/yyyy");
        Date result = new Date(next_hour);
        String formatted = sdf1.format(result);

        StringTokenizer st2 = new StringTokenizer(formatted, "/");
        int rep_day = Integer.parseInt(st2.nextToken());
        int rep_month = Integer.parseInt(st2.nextToken());
        int rep_year = Integer.parseInt(st2.nextToken());

        rep_month = rep_month - 1;

        String today_date = rep_day + "/" + rep_month + "/" + rep_year;

        Date date_today = null, date_app_update = null;

        try {
            date_today = sdf1.parse(today_date);
            if (!sharedPreference.getString(context, "update_date").equals("")) {
                date_app_update = sdf1.parse(sharedPreference.getString(context, "update_date"));
            } else {
                date_app_update = sdf1.parse(today_date);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }


        if (sharedPreference.getString(context, "update_date").equals("")) {
            Utils.date_put(context, "update_date", 7);
            aBoolean = true;
        } else {
            if (date_today.compareTo(date_app_update) >= 0) {
                Utils.date_put(context, "update_date", 7);
                aBoolean = true;
            }
        }

        System.out.println("putdate_update_date---" + aBoolean);

        return aBoolean;
    }


    public static String am_pm1(int hur, int min) {

        String AM_PM = "AM";

        if (hur >= 12) {
            hur = hur - 12;
            AM_PM = "PM";
        } else {
            AM_PM = "AM";

        }
        if (hur == 0) {
            hur = 12;
        }

        return Utils.pad("" + hur) + " : " + Utils.pad("" + min) + " " + AM_PM;
    }


    public static void ClearCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {
            System.out.println("clr_chace : error ClearCache");
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    public static Boolean clr_chace(Context context) {
        Boolean aBoolean = false;
        SharedPreference sharedPreference = new SharedPreference();
        Calendar calendar = Calendar.getInstance();
        long next_hour = calendar.getTimeInMillis();

        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/M/yyyy");
        Date result = new Date(next_hour);
        String formatted = sdf1.format(result);

        StringTokenizer st2 = new StringTokenizer(formatted, "/");
        int rep_day = Integer.parseInt(st2.nextToken());
        int rep_month = Integer.parseInt(st2.nextToken());
        int rep_year = Integer.parseInt(st2.nextToken());

        rep_month = rep_month - 1;

        String today_date = rep_day + "/" + rep_month + "/" + rep_year;

        Date date_today = null, date_app_update = null;

        try {
            date_today = sdf1.parse(today_date);
            if (!sharedPreference.getString(context, "clr_chace").equals("")) {
                date_app_update = sdf1.parse(sharedPreference.getString(context, "clr_chace"));
            } else {
                date_app_update = sdf1.parse(today_date);
            }

        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("clr_chace : error");
        }


        if (sharedPreference.getString(context, "clr_chace").equals("")) {

            aBoolean = true;
            System.out.println("clr_chace : " + aBoolean);
        } else {
            if (date_today.compareTo(date_app_update) >= 0) {
                aBoolean = true;
                System.out.println("clr_chace : " + aBoolean);
            }
        }


        return aBoolean;
    }


    public static void custom_tabs(Context context, String url) {
        SharedPreference sharedPreference = new SharedPreference();
        String colorr = "#C79500";
        if (sharedPreference.getInt(context, "tab_flag") == 0) {
            colorr = sharedPreference.getString(context, "color_codee");
        } else if (sharedPreference.getInt(context, "tab_flag") == 1) {
            colorr = "#3A7CEC";
        } else if (sharedPreference.getInt(context, "tab_flag") == 2) {
            colorr = "#C79500";
        } else if (sharedPreference.getInt(context, "tab_flag") == 3) {
            colorr = "#274200";
        } else if (sharedPreference.getInt(context, "tab_flag") == 4) {
            colorr = "#6FBF00";
        } else {
            colorr = "#3A7CEC";
        }
        try {
            CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
            intentBuilder.setExitAnimations(context, android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right);
            intentBuilder.setToolbarColor(Color.parseColor(colorr));
            //Open the Custom Tab
            intentBuilder.build().launchUrl(context, Uri.parse(url));
        } catch (ActivityNotFoundException | NullPointerException e) {
            e.printStackTrace();
            try {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_VIEW);
                i.addCategory(Intent.CATEGORY_BROWSABLE);
                i.setData(Uri.parse(url));
                context.startActivity(i);
            } catch (ActivityNotFoundException e1) {
                e1.printStackTrace();
            }
        }
    }

    public static String getAndroidId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }


    public static SharedPreference prefnull(SharedPreference sharedPreference) {
        if (sharedPreference == null)
            return sharedPreference = new SharedPreference();
        else
            return sharedPreference = new SharedPreference();
    }


    /*public static void kochaaaram_set(DataBaseHelper db, RichEditor1 kocharam, String date, String colorr) {
        Cursor cv4 = db.getQry("select * from kocharam  where date = '" + date + "'");
        if (cv4.getCount() == 0) {
            kocharam.setVisibility(View.GONE);
        } else {
            cv4.moveToFirst();
            kocharam.setVisibility(View.VISIBLE);
            cv4.moveToFirst();
            String c1 = "", c2 = "", c3 = "", c4 = "", c5 = "", c6 = "", c7 = "", c8 = "", c9 = "", c10 = "", c11 = "", c12 = "", Mainn = "";

            c1 = cv4.getString(1).replaceAll(",", " ");
            c1 = c1.replaceAll("-", "");

            c2 = cv4.getString(2).replaceAll(",", " ");
            c2 = c2.replaceAll("-", "");

            c3 = cv4.getString(3).replaceAll(",", " ");
            c3 = c3.replaceAll("-", "");

            c4 = cv4.getString(4).replaceAll(",", " ");
            c4 = c4.replaceAll("-", "");

            c5 = cv4.getString(5).replaceAll(",", " ");
            c5 = c5.replaceAll("-", "");

            c6 = cv4.getString(6).replaceAll(",", " ");
            c6 = c6.replaceAll("-", "");

            c7 = cv4.getString(7).replaceAll(",", " ");
            c7 = c7.replaceAll("-", "");

            c8 = cv4.getString(8).replaceAll(",", " ");
            c8 = c8.replaceAll("-", "");

            c9 = cv4.getString(9).replaceAll(",", " ");
            c9 = c9.replaceAll("-", "");

            c10 = cv4.getString(10).replaceAll(",", " ");
            c10 = c10.replaceAll("-", "");

            c11 = cv4.getString(11).replaceAll(",", " ");
            c11 = c11.replaceAll("-", "");

            c12 = cv4.getString(12).replaceAll(",", " ");
            c12 = c12.replaceAll("-", "");

            String str1 = "", str2 = "", str3 = "", str4 = "", str5 = "", str6 = "";
            if (cv4.getString(13).length() > 2) {
                str1 = cv4.getString(13) + "<br>";
            }
            if (cv4.getString(14).length() > 2) {
                str2 = cv4.getString(14) + "<br>";
            }
            if (cv4.getString(15).length() > 2) {
                str3 = cv4.getString(15) + "<br>";
            }
            if (cv4.getString(16).length() > 2) {
                str4 = cv4.getString(16) + "<br>";
            }
            if (cv4.getString(17).length() > 2) {
                str5 = cv4.getString(17) + "<br>";
            }
            if (cv4.getString(18).length() > 2) {
                str6 = cv4.getString(18) + "<br>";
            }

            Mainn = str1 + str2 + str3 + str4 + str5 + str6;


            kocharam.loadDataWithBaseURL("",
                    Utils.kocaram_create(c1, c2, c3, c4, c12, c5, c11, c6, c10, c9, c8, c7,
                            Mainn, colorr),
                    "text/html", "utf-8", null);
        }

        cv4.close();
    }*/

    public static String kocaram_create(String c1, String c2, String c3, String c4, String c5, String c6, String c7,
                                        String c8, String c9, String c10, String c11, String c12, String mainn, String color) {


        String str_val = "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<style>" +
                "table {" +
                "    border-collapse: collapse;" +
                "    width: 85%;" +
                "align : center" +
                "}" +
                "td, th {" +
                "    border: 2px solid " + color + ";" +
                "    text-align: center;" +
                "" +
                "}" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<table align='center'>" +
                "  <tr>" +
                "    <td width='55px' height='50px' >" + c1 + "</td>" +
                "    <td width='55px' height='50px'>" + c2 + "</td>" +
                "    <td width='55px' height='50px'>" + c3 + "</td>" +
                "    <td width='55px' height='50px'>" + c4 + "</td>" +
                "  </tr>" +
                "  <tr>" +
                "  <td width='55px' height='50px'>" + c5 + "</td>" +
                "    <td width='110px' height='100px'  rowspan='2'colspan='2'>" + mainn + "</td>" +
                "    <td width='55px' height='50px'>" + c6 + "</td>" +
                "  </tr>" +
                "  <tr>" +
                "    <td width='55px' height='50px'>" + c7 + "</td>" +
                "    <td width='55px' height='50px' colspan='2'>" + c8 + "</td> " +
                "  </tr>" +
                "  <tr>" +
                "    <td width='55px' height='50px'>" + c9 + "</td>" +
                "    <td width='55px' height='50px' >" + c10 + "</td>" +
                "    <td width='55px' height ='50px'>" + c11 + "</td>" +
                "<td width='55px' height='50px'>" + c12 + "</td>" +
                "  </tr>  " +
                "</table>" +
                "</body>" +
                "</html>";

        return str_val;
    }

 /*   public static Dialog down_dia(Context context) {
        down_dia = new Dialog(context, R.style.AppTheme);

        down_dia.setContentView(R.layout.down_progress);
        down_dia.setCancelable(false);

        down_dia.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#B3000000")));
        arc_progress = down_dia.findViewById(R.id.arc_progress);
        down_state = down_dia.findViewById(R.id.down_state);
        CardView tit_card = down_dia.findViewById(R.id.tit_card);
        down_state.setText("Starting download...");

        tit_card.setCardBackgroundColor(Utils.get_color(context));

        arc_progress.setText("");
        arc_progress.setSuffixText("");

        return down_dia;
    }*/

    public static String size(int size) {

        String hrSize;

        double m = size / 1048576.0;
        double g = size / 1073741824.0;


        DecimalFormat dec = new DecimalFormat("0.00");

        if (g > 1) {
            hrSize = dec.format(g).concat("GB");
        } else if (m > 1) {
            hrSize = dec.format(m).concat("MB");
        } else {
            hrSize = dec.format(size / 1048576.0).concat("MB");
        }

        return hrSize;

    }

    public static String size1(int size) {

        String hrSize;

        double m = size / 1048576.0;
        double g = size / 1073741824.0;


        DecimalFormat dec = new DecimalFormat("0.00");

        if (g > 1) {
            hrSize = dec.format(g).concat(" / Gbs");
        } else if (m > 1) {
            hrSize = dec.format(m).concat(" / Mbs");
        } else {
            hrSize = dec.format(size / 1024.0).concat(" / Kbs");
        }

        return hrSize;

    }

    public static void showError(final Context context, final String err) {
        SharedPreference sharedPreference = new SharedPreference();

        File mydir = new File(Environment.getExternalStorageDirectory().toString() + "/Nithra/Tamil Calendar/");
        // Set the input file stream up:
        if (sharedPreference.getString(context, "fess_title").contains("குழந்தை பெயர்கள்")) {
            final File file = new File(mydir, "baby_uni.db");
            if (file.exists()) {
                file.delete();
            }
        } else if (sharedPreference.getString(context, "fess_title").contains("திருக்குறள்")) {
            final File file = new File(mydir, "kural.db");
            if (file.exists()) {
                file.delete();
            }
        } else {
            final File file = new File(mydir, "tamilsms.db");
            if (file.exists()) {
                file.delete();
            }
        }

    }



    /*public static Dialog dialog_msg(Context context, String strr) {
        SharedPreference sharedPreference = new SharedPreference();
        dialog_msg = new Dialog(context,
                android.R.style.Theme_Translucent_NoTitleBar);
        dialog_msg.setContentView(R.layout.rate);
        AppCompatButton yesbut1 = dialog_msg.findViewById(R.id.button2);
        AppCompatButton nobut1 = dialog_msg.findViewById(R.id.button1);

        AppCompatTextView msg_txt1 = dialog_msg.findViewById(R.id.msg_txt);

        AppCompatImageView imgg = dialog_msg.findViewById(R.id.imgg);

        LinearLayout icon_back1 = dialog_msg.findViewById(R.id.icon_back);
        CardView cardd1 = dialog_msg.findViewById(R.id.cardd);

        msg_txt1.setText(strr);


        if (sharedPreference.getInt(context, "tab_flag") == 0) {
            icon_back1.setBackgroundColor(Color.parseColor(sharedPreference.getString(context, "color_codee")));
            msg_txt1.setTextColor(Color.parseColor(sharedPreference.getString(context, "color_codee")));
            nobut1.setTextColor(Color.parseColor(sharedPreference.getString(context, "color_codee")));
            cardd1.setCardBackgroundColor(Color.parseColor(sharedPreference.getString(context, "color_codee")));
        } else if (sharedPreference.getInt(context, "tab_flag") == 1) {
            icon_back1.setBackgroundColor(Color.parseColor("#3A7CEC"));
            msg_txt1.setTextColor(Color.parseColor("#3A7CEC"));
            nobut1.setTextColor(Color.parseColor("#3A7CEC"));
            cardd1.setCardBackgroundColor(Color.parseColor("#3A7CEC"));
        } else if (sharedPreference.getInt(context, "tab_flag") == 2) {
            icon_back1.setBackgroundColor(Color.parseColor("#C79500"));
            msg_txt1.setTextColor(Color.parseColor("#C79500"));
            nobut1.setTextColor(Color.parseColor("#C79500"));
            cardd1.setCardBackgroundColor(Color.parseColor("#C79500"));
        } else if (sharedPreference.getInt(context, "tab_flag") == 3) {
            icon_back1.setBackgroundColor(Color.parseColor("#274200"));
            msg_txt1.setTextColor(Color.parseColor("#274200"));
            nobut1.setTextColor(Color.parseColor("#274200"));
            cardd1.setCardBackgroundColor(Color.parseColor("#274200"));
        } else if (sharedPreference.getInt(context, "tab_flag") == 4) {
            icon_back1.setBackgroundColor(Color.parseColor("#6FBF00"));
            msg_txt1.setTextColor(Color.parseColor("#6FBF00"));
            nobut1.setTextColor(Color.parseColor("#6FBF00"));
            cardd1.setCardBackgroundColor(Color.parseColor("#6FBF00"));
        } else {
            icon_back1.setBackgroundColor(sharedPreference.getInt(context, "color_vibrant"));
            msg_txt1.setTextColor(sharedPreference.getInt(context, "color_vibrant"));
            nobut1.setTextColor(sharedPreference.getInt(context, "color_vibrant"));
            cardd1.setCardBackgroundColor(sharedPreference.getInt(context, "color_vibrant"));
        }


        yesbut1.setText("Try again");
        nobut1.setVisibility(View.GONE);
        imgg.setVisibility(View.GONE);

        yesbut1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog_msg.dismiss();
            }
        });

        nobut1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog_msg.dismiss();
            }
        });

        return dialog_msg;
    }*/

    public static int get_color(Context context) {
        SharedPreference sharedPreference = new SharedPreference();
        if (sharedPreference.getString(context, "color_codee").equals("")) {
            sharedPreference.putString(context, "color_codee", "#CC004C");
        }
        if (sharedPreference.getInt(context, "tab_flag") == 0) {
            return Color.parseColor(sharedPreference.getString(context, "color_codee"));
        } else if (sharedPreference.getInt(context, "tab_flag") == 1) {
            return Color.parseColor("#3A7CEC");
        } else if (sharedPreference.getInt(context, "tab_flag") == 2) {
            return Color.parseColor("#C79500");
        } else if (sharedPreference.getInt(context, "tab_flag") == 3) {
            return Color.parseColor("#274200");
        } else if (sharedPreference.getInt(context, "tab_flag") == 4) {
            return Color.parseColor("#6FBF00");
        } else {
            return sharedPreference.getInt(context, "color_vibrant");
        }

    }

    public static void share_fun(Context context, String str) {

        Intent i = new Intent();
        i.setAction(Intent.ACTION_VIEW);
        i.addCategory(Intent.CATEGORY_BROWSABLE);
        i.setData(Uri.parse("calendar://calendar/share:" + str.replaceAll("%", "%25")));
        context.startActivity(i);


    }

    public static void hideKeyboardFrom(Context context, View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }

    public static boolean fun_check(Context context, String strr, int vall, int nxt_val) {

        Boolean aBoolean = false;
        SharedPreference sharedPreference = new SharedPreference();
        Calendar calendar = Calendar.getInstance();
        long next_hour = calendar.getTimeInMillis();

        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/M/yyyy");
        Date result = new Date(next_hour);
        String formatted = sdf1.format(result);

        StringTokenizer st2 = new StringTokenizer(formatted, "/");
        int rep_day = Integer.parseInt(st2.nextToken());
        int rep_month = Integer.parseInt(st2.nextToken());
        int rep_year = Integer.parseInt(st2.nextToken());

        rep_month = rep_month - 1;

        String today_date = rep_day + "/" + rep_month + "/" + rep_year;

        Date date_today = null, date_app_update = null;

        try {
            date_today = sdf1.parse(today_date);
            if (!sharedPreference.getString(context, strr).equals("")) {
                date_app_update = sdf1.parse(sharedPreference.getString(context, strr));
            } else {
                //   date_app_update = sdf1.parse(today_date);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }


        if (sharedPreference.getString(context, strr).equals("")) {

            Utils.date_put(context, strr, vall);

            //aBoolean = false;
        } else {
            if (date_today.compareTo(date_app_update) >= 0) {
                if (nxt_val == 0) {
                    Utils.date_put(context, strr, vall);
                }
                aBoolean = true;
            }
        }

        System.out.println(strr + "_update_date---" + aBoolean);

        return aBoolean;
    }


    public static String[] substringsBetween(final String str, final String open, final String close) {
        if (str == null || isEmpty(open) || isEmpty(close)) {
            return null;
        }
        final int strLen = str.length();
        if (strLen == 0) {
            return new String[0];
        }
        final int closeLen = close.length();
        final int openLen = open.length();
        final List<String> list = new ArrayList<>();
        int pos = 0;
        while (pos < strLen - closeLen) {
            int start = str.indexOf(open, pos);
            if (start < 0) {
                break;
            }
            start += openLen;
            final int end = str.indexOf(close, start);
            if (end < 0) {
                break;
            }
            list.add(str.substring(start, end));
            pos = end + closeLen;
        }
        if (list.isEmpty()) {
            return null;
        }
        return list.toArray(new String[list.size()]);
    }


    public static String notes_date(String str) {
        String[] str_val = str.split("-");
        int day = Integer.parseInt(str_val[0]);
        int month = Integer.parseInt(str_val[1]);
        int year = Integer.parseInt(str_val[2]);
        return "" + day + "/" + month + "/" + year;
    }

    public static String notes_date1(String str) {
        String[] str_val = str.split("/");
        int day = Integer.parseInt(str_val[0]);
        int month = Integer.parseInt(str_val[1]);
        int year = Integer.parseInt(str_val[2]);
        return "" + Utils.pad("" + day) + "-" + Utils.pad("" + month) + "-" + year;
    }

    public static String date_change(String strr) {

        String[] str1 = strr.split("\\ ");


        String[] str2 = str1[0].split("\\-");

        /*String[] str3 = str1[1].split("\\:");*/

        return "" + str2[2] + "/" + str2[1] + "/" + str2[0];
    }


    public static boolean appInstalledOrNot(Context context, String uri) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }



}

