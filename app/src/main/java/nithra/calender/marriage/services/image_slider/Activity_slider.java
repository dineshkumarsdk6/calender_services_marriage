package nithra.calender.marriage.services.image_slider;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import nithra.calender.marriage.services.GlideApp;
import nithra.calender.marriage.services.R;
import nithra.calender.marriage.services.SharedPreference;
import nithra.calender.marriage.services.viewpagerindicator.CirclePageIndicator;


public class Activity_slider extends AppCompatActivity {
    SharedPreference sharedPreference;
    ViewPager mPager;
    String[] ImagesArray;

   // private FirebaseAnalytics mFirebaseAnalytics;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);
        sharedPreference = new SharedPreference();
        //mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        mPager = findViewById(R.id.pager);
        CirclePageIndicator indicator = findViewById(R.id.indicator);

        ImagesArray = sharedPreference.getString(Activity_slider.this, "imageurl").split(",");


        if (ImagesArray.length == 0) {
            ImagesArray[0] = "nooo";
        }

        mPager.setAdapter(new SlidingImage_Adapter(Activity_slider.this, ImagesArray));
        indicator.setViewPager(mPager);
        float density = getResources().getDisplayMetrics().density;

        indicator.setRadius(5 * density);
        mPager.setCurrentItem(sharedPreference.getInt(Activity_slider.this, "image_poss_val"));
    }

    @Override
    protected void onResume() {
        super.onResume();
//        mFirebaseAnalytics.setCurrentScreen(Activity_slider.this, "TC_IMAGE_SLIDER", null);
    }

    public class SlidingImage_Adapter extends PagerAdapter {


        private String[] IMAGES;
        private LayoutInflater inflater;
        private Context context;
        TouchImageView imageView;

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
        public Object instantiateItem(ViewGroup view, int position) {
            View imageLayout = inflater.inflate(R.layout.slidingimages_layout1, view, false);

            assert imageLayout != null;
             imageView = imageLayout
                    .findViewById(R.id.image);

            int drawble;

           /* if (sharedPreference.getString(context, "vanga_item").equals("madu")) {
                drawble = R.drawable.cow1;
            } else {*/
                drawble = R.drawable.mandapamsample;
          /*  }*/

          /*  if (sharedPreference.getInt(Activity_slider.this, "activity_value") == 1) {*/
                GlideApp.with(context)
                        .load(IMAGES[position])
                        //.centerCrop()
                        .placeholder(drawble)
                        .error(drawble)
                        /* .transition(DrawableTransitionOptions.withCrossFade()) //Optional
                         .skipMemoryCache(true)  //No memory cache
                         .diskCacheStrategy(DiskCacheStrategy.NONE)*/
                        .into(imageView);
           /* } else {
                GlideApp.with(context)
                        .load(IMAGES[position])
                        //.centerCrop()
                        .placeholder(R.drawable.events_empty)
                        .error(R.drawable.events_empty)
                        *//*.transition(DrawableTransitionOptions.withCrossFade()) //Optional
                        .skipMemoryCache(true)  //No memory cache
                        .diskCacheStrategy(DiskCacheStrategy.NONE)*//*
                        .into(imageView);
            }*/
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