package com.appsomniac.doubtapp.base;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import com.appsomniac.doubtapp.R;
import com.appsomniac.doubtapp.activity.IntroActivity;
import com.appsomniac.doubtapp.activity.LoginActivity;
import com.appsomniac.doubtapp.fragments.MyCoinsFragment;
import com.appsomniac.doubtapp.fragments.OnlineCoursesFragment;
import com.appsomniac.doubtapp.model.User;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.clans.fab.FloatingActionButton;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appsomniac.doubtapp.ClassFragments.AskFragment;
import com.appsomniac.doubtapp.ClassFragments.ActivityFragment;
import com.appsomniac.doubtapp.ClassFragments.TrendingFragment;
import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, MyCoinsFragment.OnFragmentInteractionListener,
        OnlineCoursesFragment.OnFragmentInteractionListener{

    // tags used to attach the fragments
    private ViewPager viewPager;
    private DrawerLayout drawer;
    private Handler mHandler;
    public static final String ANONYMOUS = "anonymous";

    //add Firebase Database stuff
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userID;

    private FirebaseUser mFirebaseUser;
    private String mUsername;

    View navHeader;
    NavigationView nav;
    public android.support.design.widget.FloatingActionButton fab_activity, fab_trending;
    public FloatingActionMenu fab_ask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();


        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        } else {
            mUsername = mFirebaseUser.getDisplayName();

        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setUserProfileInNavigationView();

        mHandler = new Handler();
        fab_activity = (android.support.design.widget.FloatingActionButton) findViewById(R.id.fab_activity);
        fab_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(getApplicationContext(), IntroActivity.class);
                startActivity(intent);

            }
        });
        final View f = findViewById(R.id.layout_fab_ask);
        fab_ask = f.findViewById(R.id.menu);

        fab_trending = (android.support.design.widget.FloatingActionButton) findViewById(R.id.fab_trending);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    fab_activity.show();
                    f.setVisibility(View.GONE);
                    fab_trending.hide();
                } else
                    if(position == 1) {
                        fab_activity.hide();
                        f.setVisibility(View.VISIBLE);
                        fab_trending.hide();
                    }else
                        if(position == 2){
                            fab_activity.hide();
                            f.setVisibility(View.GONE);
                            fab_trending.show();
                        }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // Set Tabs inside Toolbar
        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    public void addUserToDatabase(){

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        userID = user.getUid();

        User userInformation = new User(user.getEmail(),user.getDisplayName(),user.getPhoneNumber());
        myRef.child("users").child(userID).setValue(userInformation);

    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        mFirebaseAuth.addAuthStateListener(mAuthListener);
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        if (mAuthListener != null) {
//            mFirebaseAuth.removeAuthStateListener(mAuthListener);
//        }
//    }

    public void setUserProfileInNavigationView(){

        nav = ( NavigationView ) findViewById( R.id.nav_view );
        if( nav != null ){
            LinearLayout mParent = ( LinearLayout ) nav.getHeaderView( 0 );

            if( mParent != null ){
                // Set your values to the image and text view by declaring and setting as you need to here.

                SharedPreferences prefs = getSharedPreferences("user_data", MODE_PRIVATE);
                String photoUrl = prefs.getString("photo_url", null);
                String user_name = prefs.getString("name", "User");

                if(photoUrl!=null) {
                    Log.e("Photo Url: ", photoUrl);

                    TextView userName = mParent.findViewById(R.id.user_name);
                    userName.setText(user_name);

                    ImageView user_imageView = mParent.findViewById(R.id.avatar);

                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.placeholder(R.drawable.ic_user_24dp);
                    requestOptions.error(R.drawable.ic_user_24dp);

                    Glide.with(this).load(photoUrl)
                            .apply(requestOptions).thumbnail(0.5f).into(user_imageView);

                }
            }
        }
    }

    // Add Fragments to Tabs
    private void setupViewPager(ViewPager viewPager) {

        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new ActivityFragment(), "Activity");
        adapter.addFragment(new AskFragment(), "Ask");
        adapter.addFragment(new TrendingFragment(), "Trending");
        viewPager.setAdapter(adapter);
    }
    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                mFirebaseAuth.signOut();
                //Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                mUsername = ANONYMOUS;
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_dashboard) {



            // Handle the camera action
        } else if (id == R.id.nav_coins) {

        } else if (id == R.id.nav_oneToOne) {

        } else if (id == R.id.nav_online_courses) {

        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_refer_and_earn) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
