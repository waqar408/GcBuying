package com.gcbuying.app.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gcbuying.app.PopFragments.ChangePasswordFragment;
import com.gcbuying.app.R;
import com.gcbuying.app.SessionManager.SessionManager;
import com.gcbuying.app.utilities.Utilities;
import com.gcbuying.app.utils.BottomNavigationHelp;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class Home1Activity extends AppCompatActivity {
    public NavController navController;
    private DrawerLayout drawer;
    public static ImageView logo;
    NavigationView navigationView;

    private TextView tv_ChangePassword;
    BottomNavigationView bottomNavigationView;
    String accessToken = "";


    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        if (!navController.getCurrentDestination().getLabel().toString().equals("HomeFragment")) {
            super.onBackPressed();
        } else {

            showCustomDialogforExit();
        }

    }

    private ImageView menu;

    Fragment selectedFragment = null;
    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home1);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        String namee = Utilities.getString(Home1Activity.this, "user_name");
        String emailll = Utilities.getString(Home1Activity.this, "user_email");
//        String photo = Utilities.getString(Home1Activity.this, "user_image");

        View hView = navigationView.inflateHeaderView(R.layout.header);
        TextView nameeee = hView.findViewById(R.id.user_name);
        TextView emaillll = hView.findViewById(R.id.user_email);


        nameeee.setText(namee);
        emaillll.setText(emailll);

        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        tvTitle = findViewById(R.id.tvTitle);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        menu = (ImageView) findViewById(R.id.menu);
        View headerView = navigationView.getHeaderView(0);
        tv_ChangePassword = headerView.findViewById(R.id.tv_changePassword);

        tv_ChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangePasswordFragment newRespond = new ChangePasswordFragment();
                newRespond.show(getSupportFragmentManager(), "fragment");
                drawer.closeDrawer(GravityCompat.START);

            }
        });
        initNavigation();


    }

    private void initNavigation() {
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START, true);
            }
        });

        navController = Navigation.findNavController(this, R.id.user_container);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        NavigationUI.setupWithNavController(navigationView, navController);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if (destination.getLabel() != null) {
                    tvTitle.setText(destination.getLabel());

                    if (destination.getLabel().equals("Edit Profile") || destination.getLabel().equals("VendorProductsFragment") || destination.getLabel().equals("Products") || destination.getLabel().equals("PostAnProductFragmentNew")) {
//                        rlToolbar.setVisibility(View.GONE);
                    } else {
//                        rlToolbar.setVisibility(View.VISIBLE);
                    }
                }

            }
        });
        navigationView.getMenu().findItem(R.id.bankdetails).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                drawer.closeDrawer(GravityCompat.START, false);
                startActivity(new Intent(Home1Activity.this, BankDetailsActivity.class));
                return true;
            }
        });

        navigationView.getMenu().findItem(R.id.sellGiftCard).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                drawer.closeDrawer(GravityCompat.START, false);
                startActivity(new Intent(Home1Activity.this, SellGiftActivity.class));


                return true;
            }
        });

        navigationView.getMenu().findItem(R.id.sellbitcoin).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                drawer.closeDrawer(GravityCompat.START, false);
                startActivity(new Intent(Home1Activity.this, SellBitCoinActivity.class));
                return true;
            }
        });
        navigationView.getMenu().findItem(R.id.nav_login).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                drawer.closeDrawer(GravityCompat.START, false);
                showCustomDialog();
                return true;
            }
        });
    }

    private void showCustomDialog() {
        final PrettyDialog pDialog = new PrettyDialog(Home1Activity.this);
        pDialog
                .setTitle("Message")
                .setMessage("Are you sure you want to Logout?")
                .setIcon(R.drawable.pdlg_icon_info)
                .setIconTint(R.color.colorPrimary)
                .addButton(
                        "Yes",
                        R.color.pdlg_color_white,
                        R.color.colorPrimary,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                SessionManager sessionManager = new SessionManager(Home1Activity.this);
                                sessionManager.logoutUser();
                                Utilities.clearSharedPref(Home1Activity.this);
                                startActivity(new Intent(Home1Activity.this, MainActivity.class));
                                finish();
                                pDialog.dismiss();
                            }
                        }
                )
                .addButton("No",
                        R.color.pdlg_color_white,
                        R.color.pdlg_color_red,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                pDialog.dismiss();
                            }
                        })
                .show();

    }

    private void showCustomDialogforExit() {
        final PrettyDialog pDialog = new PrettyDialog(Home1Activity.this);
        pDialog
                .setTitle("Message")
                .setMessage("Are you sure you want to Exit?")
                .setIcon(R.drawable.pdlg_icon_info)
                .setIconTint(R.color.colorPrimary)
                .addButton(
                        "Yes",
                        R.color.colorPrimary,
                        R.color.pdlg_color_white,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                finishAffinity();
                                pDialog.dismiss();
                            }
                        }
                )
                .addButton("No",
                        R.color.pdlg_color_red,
                        R.color.pdlg_color_white,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                pDialog.dismiss();
                            }
                        })
                .show();
    }
}