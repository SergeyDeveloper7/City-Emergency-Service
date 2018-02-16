package com.sergeydeveloper7.cityemergencyservice.view.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.sergeydeveloper7.cityemergencyservice.R;
import com.sergeydeveloper7.cityemergencyservice.model.general.User;
import com.sergeydeveloper7.cityemergencyservice.utils.Const;
import com.sergeydeveloper7.cityemergencyservice.view.fragments.AdminFragment;
import com.sergeydeveloper7.cityemergencyservice.view.fragments.MainScreenFragment;
import com.sergeydeveloper7.cityemergencyservice.view.fragments.WorkersScreenFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.toolbarMain) Toolbar toolbar;
    @BindView(R.id.mainAppBar) AppBarLayout appBarLayout;
    @BindView(R.id.container) LinearLayout container;
    private ActionBar         mActionBar;
    private boolean           isUserLogin = false;
    private SharedPreferences sharedPreferences;
    private User              user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppThemeNoActionBar);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        user = new Gson().fromJson(sharedPreferences.getString("user", null), User.class);
        isUserLogin = sharedPreferences.getBoolean(Const.SHARED_PREFERENCE_IS_USER_LOGIN, false);
        mActionBar = getSupportActionBar();
        toolbar.setVisibility(View.GONE);
        checkLogin();
    }

    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount() > 0)
            getFragmentManager().popBackStack();
        else
            super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_top_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == android.R.id.home){
            onBackPressed();
            return true;
        }

        if(id == R.id.action_log_out){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("user", new Gson().toJson(user));
            editor.putBoolean(Const.SHARED_PREFERENCE_IS_USER_LOGIN, false);
            editor.apply();
            this.navigator.startFragmentNoBackStack(this, new MainScreenFragment(), Const.MAIN_SCREEN_FRAGMENT_ID);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void checkLogin() {
        if(isUserLogin) {
            if(user.getRole().equals(getString(R.string.role_admin))) {
                navigator.startFragmentNoBackStack(this, new AdminFragment(), Const.ADMIN_FRAGMENT_ID);
            } else if(user.getRole().equals(getString(R.string.role_worker))) {
                navigator.startFragmentNoBackStack(this, new WorkersScreenFragment(), Const.WORKER_FRAGMENT_ID);
            }
        } else
            this.navigator.startFragmentNoBackStack(this, new MainScreenFragment(), Const.MAIN_SCREEN_FRAGMENT_ID);
    }

    public Toolbar getToolbar(){
        return toolbar;
    }

    public AppBarLayout getAppBarLayout() {
        return appBarLayout;
    }
}
