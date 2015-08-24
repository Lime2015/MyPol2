package com.lime.mypol.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kakao.APIErrorResult;
import com.kakao.LogoutResponseCallback;
import com.kakao.UserManagement;
import com.lime.mypol.R;
import com.lime.mypol.adapter.MainPagerAdapter;
import com.lime.mypol.manager.DatabaseManager;
import com.lime.mypol.manager.NetworkManager;
import com.lime.mypol.models.MemberInfo;
import com.lime.mypol.result.CheckTagResult;
import com.lime.mypol.utils.BadgeUtil;
import com.lime.mypol.utils.ImageUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Gordon Wong on 7/17/2015.
 * <p>
 * Main activity for material sheet fab sample.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    final String SERVER_DISCONNECT = "서버접속 에러!!";

    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;
    //    private MaterialSheetFab materialSheetFab;
    private int statusBarColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setTitle(R.string.app_name);
        setContentView(R.layout.activity_main);

        checkServerTag();

        setupActionBar();
        setupDrawer();
//        setupFab();
        setupTabs();

        BadgeUtil.setBadge(this, 1);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

//    @Override
//    public void onBackPressed() {
//        if (materialSheetFab.isSheetVisible()) {
//            materialSheetFab.hideSheet();
//        } else {
//            super.onBackPressed();
//        }
//    }

    /**
     * Sets up the action bar.
     */
    private void setupActionBar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * Sets up the navigation drawer.
     */
    private MemberInfo memberInfo;
//    private DisplayImageOptions options;

    private NavigationView navigationView;

    private void setupDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.opendrawer, R.string.closedrawer);
        drawerLayout.setDrawerListener(drawerToggle);

        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                if (!memberInfo.isLogOn()) {
                    drawerMenuHideOption(R.id.menu_drawer_member_favorate);
                    drawerMenuHideOption(R.id.menu_drawer_member_profile);
                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.menu_drawer_member_data:
                        if (mDbTag.getTagList().get(0) == 0) {
//                            initServerDataWithProgress();
                            initServerData();
                        } else if (mRequireUpdateData) {
//                            updateServerDataWithProgress();
                            updateServerData();
                        } else {
                            Toast.makeText(MainActivity.this, "최신 데이터입니다.", Toast.LENGTH_SHORT).show();
                        }
                        return true;

                    default:
                        return false;
                }
            }
        });
//        options = new DisplayImageOptions.Builder()
//                .showImageOnLoading(R.drawable.ic_face_white_48dp)
//                .showImageForEmptyUri(R.drawable.ic_face_white_48dp)
//                .showImageOnFail(R.drawable.ic_face_white_48dp)
//                .cacheInMemory(false)
//                .cacheOnDisc(false)
//                .considerExifParams(true)
////                .displayer(new RoundedBitmapDisplayer(120))
//                .build();

        Intent intent = getIntent();
        memberInfo = (MemberInfo) intent.getSerializableExtra("memberInfo");

        ActionBar bar = getSupportActionBar();
        TextView textView = (TextView) findViewById(R.id.member_id);
        ImageView imageView = (ImageView) findViewById(R.id.member_photo);

        if (memberInfo.isLogOn()) {
            textView.setText(memberInfo.getMemberNickname());
            bar.setTitle(memberInfo.getMemberNickname());
            ImageUtil.displayRoundImage(imageView, memberInfo.getUrlThumbnail(), null);
        } else {
            textView.setText(R.string.watchMod);
            bar.setTitle(R.string.watchMod);
            imageView.setImageResource(R.drawable.ic_face_white_48dp);
        }


    }

    private CheckTagResult mServerTag;
    private CheckTagResult mDbTag;
    private List<Boolean> mDownloadList;
    private boolean mRequireUpdateData;

    private void checkServerTag() {
        mDownloadList = new ArrayList<>();
        mRequireUpdateData = false;

        for (int i = 0; i < 6; i++) {
            mDownloadList.add(false);
        }

        mDbTag = DatabaseManager.getInstance(MainActivity.this).checkTag();
        NetworkManager.getInstance().checkServerTag(new NetworkManager.OnNetResultListener<CheckTagResult>() {
            @Override
            public void onSuccess(CheckTagResult result) {
                mServerTag = result;

                for (int i = 0; i < mServerTag.getTagList().size(); i++) {
                    if (mServerTag.getTagList().get(i) > mDbTag.getTagList().get(i)) {
                        mDownloadList.set(i, true);
                        mRequireUpdateData = true;
                    } else {
                        mDownloadList.set(i, false);
                    }
                }
            }

            @Override
            public void onFail(int code) {
                Toast.makeText(MainActivity.this, SERVER_DISCONNECT, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void updateServerData() {
        showProgressDialog();

        progressDialog.setMessage("데이터를 다운로드합니다...");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        progressThread.setState(ProgressThread.STATE_DONE);
        checkServerTag();
    }

//    private boolean mUpdateSeverDataState;
//
//    private void updateServerDataWithProgress() {
//
//        final ProgressDialog progDialog = new ProgressDialog(MainActivity.this);
//        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progDialog.setMessage("데이터 업데이트 중입니다...");
//        progDialog.setCanceledOnTouchOutside(false);
//        progDialog.show();
//
//        //Thread 사용은 선택이 아니라 필수
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                //TODO : 시간이 걸리는 처리 삽입
//                mUpdateSeverDataState = true;
//                while (mUpdateSeverDataState) {
//                    //서버 업데이트
//                    progressThread.setState(ProgressThread.STATE_DONE);
//
//                }
//                progDialog.dismiss();
//            }
//        }).start();
//    }


    private void initServerData() {
        showProgressDialog();

        progressDialog.setMessage("데이터를 다운로드합니다...");
        //서버 최초 동기화
        NetworkManager.getInstance().requestInitDatabase(new NetworkManager.OnNetResultListener<File>() {
            @Override
            public void onSuccess(File result) {

                progressDialog.setMessage("데이터베이스에 적용합니다....");
                if (!DatabaseManager.getInstance(MainActivity.this).initDatabase(result)) {
                    Toast.makeText(MainActivity.this, "데이터베이스 초기화 실패", Toast.LENGTH_SHORT).show();
                }

                //다운로드 파일 삭제
                result.delete();
                progressThread.setState(ProgressThread.STATE_DONE);
                checkServerTag();
            }

            @Override
            public void onFail(int code) {
                Toast.makeText(MainActivity.this, SERVER_DISCONNECT, Toast.LENGTH_SHORT).show();
                progressThread.setState(ProgressThread.STATE_DONE);
            }
        });
    }

//    private boolean mInitSeverDataState;

//    private void initServerDataWithProgress() {
//
//        final ProgressDialog progDialog = new ProgressDialog(MainActivity.this);
//        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progDialog.setMessage("데이터를 다운로드합니다...");
//        progDialog.setCanceledOnTouchOutside(false);
//        progDialog.show();
//
//        //Thread 사용은 선택이 아니라 필수
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                //TODO : 시간이 걸리는 처리 삽입
//                mInitSeverDataState = true;
//                while (mInitSeverDataState) {
//                    //서버 최초 동기화
//                    NetworkManager.getInstance().requestInitDatabase(new NetworkManager.OnNetResultListener<File>() {
//                        @Override
//                        public void onSuccess(File result) {
//
//                            progDialog.setMessage("데이터베이스에 적용합니다....");
//                            if (!DatabaseManager.getInstance(MainActivity.this).initDatabase(result)) {
//                                Toast.makeText(MainActivity.this, "데이터베이스 초기화 실패", Toast.LENGTH_SHORT).show();
//                            }
//
//                            //다운로드 파일 삭제
//                            result.delete();
//                            mInitSeverDataState = false;
//                            checkServerTag();
//                        }
//
//                        @Override
//                        public void onFail(int code) {
//                            Toast.makeText(MainActivity.this, SERVER_DISCONNECT, Toast.LENGTH_SHORT).show();
//                            mInitSeverDataState = false;
//                        }
//                    });
//
//                }
//                progDialog.dismiss();
//            }
//        }).start();
//    }

    /**
     * Sets up the tabs.
     */
    private void setupTabs() {
        // Setup view pager
        ViewPager viewpager = (ViewPager) findViewById(R.id.viewpager);
        MainPagerAdapter mainPagerAdapter = new MainPagerAdapter(this, getSupportFragmentManager());
        viewpager.setAdapter(mainPagerAdapter);
        viewpager.setOffscreenPageLimit(MainPagerAdapter.NUM_ITEMS);
//        updatePage(viewpager.getCurrentItem());

        // Setup tab layout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewpager);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
//                updatePage(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });

    }

    /**
     * Sets up the Floating action button.
     */
//    private void setupFab() {
//
//        Fab fab = (Fab) findViewById(R.id.fab);
//        View sheetView = findViewById(R.id.fab_sheet);
//        View overlay = findViewById(R.id.overlay);
//        int sheetColor = getResources().getColor(R.color.background_card);
//        int fabColor = getResources().getColor(R.color.blue_grey_400);
//
//        // Create material sheet FAB
//        materialSheetFab = new MaterialSheetFab<>(fab, sheetView, overlay, sheetColor, fabColor);
////        materialSheetFab = new MaterialSheetFab<>(fab, null, null, 0, fabColor);
//
//        // Set material sheet event listener
//        materialSheetFab.setEventListener(new MaterialSheetFabEventListener() {
//            @Override
//            public void onShowSheet() {
//                // Save current status bar color
//                statusBarColor = getStatusBarColor();
//                // Set darker status bar color to match the dim overlay
//                setStatusBarColor(getResources().getColor(R.color.theme_primary_dark2));
//            }
//
//            @Override
//            public void onHideSheet() {
//                // Restore status bar color
//                setStatusBarColor(statusBarColor);
//            }
//        });
//
////        // Set material sheet item click listeners
////        findViewById(R.id.fab_sheet_item_recording).setOnClickListener(this);
////        findViewById(R.id.fab_sheet_item_reminder).setOnClickListener(this);
////        findViewById(R.id.fab_sheet_item_photo).setOnClickListener(this);
////        findViewById(R.id.fab_sheet_item_note).setOnClickListener(this);
//    }

    /**
     * Called when the selected page changes.
     *
     * @param selectedPage selected page
     */
//    private void updatePage(int selectedPage) {
//        updateFab(selectedPage);
//        updateSnackbar(selectedPage);
//    }

    /**
     * Updates the FAB based on the selected page
     *
     * @param selectedPage selected page
     */

//    private void updateFab(int selectedPage) {
//        switch (selectedPage) {
//            case MainPagerAdapter.MENUS_POS:
////                materialSheetFab.showFab();
////                break;
//            case MainPagerAdapter.MYPOL_POS:
////                materialSheetFab.showFab(0, -getResources().getDimensionPixelSize(R.dimen.snackbar_height));
////                materialSheetFab.hideSheetThenFab();
//                break;
//            case MainPagerAdapter.COMMENT_POS:
//            default:
////                materialSheetFab.hideSheetThenFab();
////                materialSheetFab.showFab();
//                break;
//        }
//    }

    /**
     * Updates the snackbar based on the selected page
     *
     * @param selectedPage selected page
     */
//    private void updateSnackbar(int selectedPage) {
//        View snackbar = findViewById(R.id.snackbar);
//        switch (selectedPage) {
//            case MainPagerAdapter.MYPOL_POS:
////                snackbar.setVisibility(View.VISIBLE);
////                break;
//            case MainPagerAdapter.MENUS_POS:
//            case MainPagerAdapter.COMMENT_POS:
//            default:
//                snackbar.setVisibility(View.GONE);
//                break;
//        }
//    }

    /**
     * Toggles opening/closing the drawer.
     */
    private void toggleDrawer() {
        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    @Override
    public void onClick(View v) {
//        Toast.makeText(this, R.string.sheet_item_pressed, Toast.LENGTH_SHORT).show();
//        materialSheetFab.hideSheet();
    }

    private Menu menu;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (!memberInfo.isLogOn()) {
            menuHideOption(R.id.menu_main_member_favorate);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    private void menuHideOption(int id) {
        MenuItem item = menu.findItem(id);
        item.setVisible(false);
    }

    private void drawerMenuHideOption(int id) {
        MenuItem item = navigationView.getMenu().findItem(id);
        item.setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_main_logout:
                if (memberInfo.isLogOn()) {
                    redirectLogoutActivity();
                } else {
                    redirectMainActivity();
                }
                return true;

            case R.id.menu_main_member_favorate:
                return true;

            case android.R.id.home:
                toggleDrawer();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private int getStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return getWindow().getStatusBarColor();
        }
        return 0;
    }

    private void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(color);
        }
    }


    private void redirectLogoutActivity() {
        UserManagement.requestLogout(new LogoutResponseCallback() {
            @Override
            protected void onSuccess(long l) {
                memberInfo = new MemberInfo();
                redirectMainActivity();
            }

            @Override
            protected void onFailure(APIErrorResult apiErrorResult) {
            }
        });
    }

    public void redirectMainActivity() {
        Intent intent = new Intent(this, MainLoginTypeActivity.class);
        startActivity(intent);
        finish();
    }


    private ProgressDialog progressDialog;
    private ProgressThread progressThread;

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progressDialog.setMessage("데이터 업데이트 중입니다...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        progressThread = new ProgressThread(handler);
        progressThread.start();

//        //Thread 사용은 선택이 아니라 필수
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                //TODO : 시간이 걸리는 처리 삽입
//                boolean state = true;
//                while (state) {
//                    try {
//                        for (int i = 0; i < 50; i++) {
//                            Thread.sleep(100);
//                        }
//                    } catch (InterruptedException e) {
//
//                    }
//                    state = false;
//                }
//                progressDialog.dismiss();
//            }
//        }).start();
    }

    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            int total = msg.getData().getInt("total");
//            progressDialog.setProgress(total);
//            if(total>=100){
//                progressThread.setState(ProgressThread.STATE_DONE);
//            }
            progressDialog.dismiss();
        }
    };


    private class ProgressThread extends Thread {
        Handler mHandler;
        final static int STATE_DONE = 0;
        final static int STATE_RUNNING = 1;
        int mState;
        int total;

        ProgressThread(Handler h) {
            mHandler = h;
        }

        @Override
        public void run() {
            mState = STATE_RUNNING;
            total = 0;
            while (mState == STATE_RUNNING) {
//                try {
//                    sleep(100);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }

            mHandler.sendEmptyMessage(0);
//            progressDialog.dismiss();
        }

        public void setState(int state) {
            mState = state;
        }
    }
}
