package com.freakydevs.kolkatalocal.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.freakydevs.kolkatalocal.R;
import com.freakydevs.kolkatalocal.adapter.MyPagerAdapter;
import com.freakydevs.kolkatalocal.connection.DownloadManager;
import com.freakydevs.kolkatalocal.connection.Updater;
import com.freakydevs.kolkatalocal.customview.MySnackBar;
import com.freakydevs.kolkatalocal.fragment.PnrStatusFragment;
import com.freakydevs.kolkatalocal.fragment.SearchFragment;
import com.freakydevs.kolkatalocal.fragment.TrainRouteFragment;
import com.freakydevs.kolkatalocal.interfaces.MainActivityInterface;
import com.freakydevs.kolkatalocal.resources.MyDataBaseHandler;
import com.freakydevs.kolkatalocal.utils.Internet;
import com.freakydevs.kolkatalocal.utils.SharedPreferenceManager;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.io.File;


/**
 * Created by PURUSHOTAM on 10/30/2017.
 */

public class MainActivity extends AppCompatActivity implements MainActivityInterface {

    private ViewPager viewPager;
    private MyPagerAdapter myPagerAdapter;
    private Toolbar toolbar;
    private AdView mAdView;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Context context;
    private KProgressHUD hud;
    private ConstraintLayout parentLayout;
    private InterstitialAd mInterstitialad;
    private boolean doubleBackToExitPressedOnce = false;

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        initView();
        initViewPager();

        if (SharedPreferenceManager.isFirstTime(getApplicationContext())) {
            checkDatabase();
        } else if (SharedPreferenceManager.isupdateTime(getApplicationContext())) {
            new Updater(context, false, 0);
        }
    }

    private void initView() {
        viewPager = findViewById(R.id.pager);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView = findViewById(R.id.navigation_view);
        drawerLayout = findViewById(R.id.drawer);
        mAdView = findViewById(R.id.adView);
        parentLayout = findViewById(R.id.parent_layout);

        viewPager.setOffscreenPageLimit(3);

        hud = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.6f);

        Menu menu = navigationView.getMenu();

        MenuItem target = menu.findItem(R.id.remove_ad);

        target.setVisible(SharedPreferenceManager.isRemoveAd(getApplicationContext()));

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawers();
                switch (item.getItemId()) {

                    case R.id.nav_home:
                        viewPager.setCurrentItem(0);
                        return true;
                    case R.id.nav_train_route:
                        viewPager.setCurrentItem(1);
                        return true;
                    case R.id.nav_pnr:
                        viewPager.setCurrentItem(2);
                        return true;
                    case R.id.nav_share:
                        String share = "Download this App for 'Offline' Train Schedule. http://play.google.com/store/apps/details?id=com.freakydevs.kolkatalocal";
                        Intent sharing = new Intent(Intent.ACTION_SEND);
                        sharing.setType("text/plain");
                        sharing.putExtra(Intent.EXTRA_SUBJECT, "Kolkata Sub");
                        sharing.putExtra(Intent.EXTRA_TEXT, share);
                        startActivity(Intent.createChooser(sharing, "Share with"));
                        return true;
                    case R.id.nav_rateus:
                        String url = "http://play.google.com/store/apps/details?id=com.freakydevs.kolkatalocal";
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                        return true;
                    case R.id.nav_contactus:
                        startActivity(new Intent(getApplicationContext(), ContactusActivity.class));
                        return true;
                    case R.id.nav_aboutus:
                        startActivity(new Intent(getApplicationContext(), AboutusActivity.class));
                        return true;
                    case R.id.nav_appupdate:
                        if (Internet.isConnected(context)) {
                            new Updater(context, true, 1);
                        }
                        return true;
                    case R.id.nav_databaseupdate:
                        if (Internet.isConnected(context)) {
                            new Updater(context, true, 2);
                        }
                        return true;
                    case R.id.buy_me_cofee:
                        startActivity(new Intent(getApplicationContext(), BuymeCofee.class));
                        return true;
                    case R.id.remove_ad:
                        startActivity(new Intent(getApplicationContext(), RemoveAd.class));
                        return true;
                    default:
                        return true;
                }
            }
        });

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

    }

    private void initViewPager() {
        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        myPagerAdapter.addFragment(new SearchFragment(), "SearchFragment");
        myPagerAdapter.addFragment(new TrainRouteFragment(), "Train");
        myPagerAdapter.addFragment(new PnrStatusFragment(), "PNR Status");
        viewPager.setAdapter(myPagerAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (SharedPreferenceManager.isShowAd(getApplicationContext())) {
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
            mAdView.setVisibility(View.VISIBLE);
            mInterstitialad = new InterstitialAd(this);
            mInterstitialad.setAdUnitId(getApplicationContext().getString(R.string.interstitial_ad_unit_id));
            mInterstitialad.loadAd(adRequest);
        } else {
            mAdView.setVisibility(View.GONE);
        }
    }

    @Override
    public void notifyDatabaseDownload() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder
                .setMessage("Database Not Downloaded Yet!\n" +
                        "Or Database Corrupted!\n" +
                        "Please Download it.")
                .setCancelable(false);
        alertDialogBuilder.setPositiveButton("Download", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (Internet.isConnected(context)) {
                    new DownloadManager(context);
                }
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void noTrainDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder
                .setMessage("No Direct Train Available for this Route!")
                .setCancelable(false);

        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();
    }

    @Override
    public void showLoader() {
        hud.setLabel("Loading...");
        hud.show();
    }

    @Override
    public void hideLoader() {
        if (hud.isShowing()) {
            hud.dismiss();
        }
    }

    @Override
    public void showDatabaseUpdate() {

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder
                .setMessage("Database Update Available!!")
                .setCancelable(false);
        alertDialogBuilder.setPositiveButton("Download", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (Internet.isConnected(context)) {
                    new DownloadManager(context);
                }
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    @Override
    public void showAppUpdate() {

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder
                .setMessage("App Update Available!!")
                .setCancelable(false);
        alertDialogBuilder.setPositiveButton("Download", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (Internet.isConnected(context)) {
                    String url = "http://play.google.com/store/apps/details?id=com.freakydevs.kolkatalocal";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    @Override
    public void showUpdateLoader() {
        hud.setLabel("Checking...");
        hud.show();
    }

    @Override
    public void hideUpdateLoader() {
        hud.dismiss();
    }

    @Override
    public void noAppUpdate() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder
                .setMessage("You have latest version of App!")
                .setCancelable(false);

        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();
    }

    @Override
    public void noDbUpdate() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder
                .setMessage("You have latest version of Database!")
                .setCancelable(false);

        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();
    }

    @Override
    public void invalidPnr() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder
                .setMessage("Please Enter valid PNR and Try Again!!")
                .setCancelable(false);

        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();
    }

    private void checkDatabase() {
        MyDataBaseHandler myDataBaseHandler = new MyDataBaseHandler(context);
        if (!myDataBaseHandler.checkDataBase()) {
            notifyDatabaseFirstTime();
        }
    }

    private void notifyDatabaseFirstTime() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder
                .setMessage("Download DataBase for First Time.\n" +
                        "It is less than 1 MB!")
                .setCancelable(false);
        alertDialogBuilder.setPositiveButton("Download", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (Internet.isConnected(context)) {
                    new DownloadManager(context);
                }
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            openQuitDialog();
        }
    }

    public void openQuitDialog() {

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();

            if (mInterstitialad.isLoaded() && SharedPreferenceManager.isShowAd(getApplicationContext())) {
                mInterstitialad.show();
            } else {
                deleteCache(context);
            }
            return;
        }

        this.doubleBackToExitPressedOnce = true;

        MySnackBar.show(getApplicationContext(), parentLayout, "Please click BACK again to exit!");

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
