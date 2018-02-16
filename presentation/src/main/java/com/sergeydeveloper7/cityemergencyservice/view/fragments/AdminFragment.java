package com.sergeydeveloper7.cityemergencyservice.view.fragments;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sergeydeveloper7.cityemergencyservice.R;
import com.sergeydeveloper7.cityemergencyservice.model.general.Bid;
import com.sergeydeveloper7.cityemergencyservice.navigation.Navigator;
import com.sergeydeveloper7.cityemergencyservice.presenter.AdminScreenPresenter;
import com.sergeydeveloper7.cityemergencyservice.utils.Const;
import com.sergeydeveloper7.cityemergencyservice.view.activities.MainActivity;
import com.sergeydeveloper7.cityemergencyservice.view.adapters.AdminAdapter;
import com.sergeydeveloper7.cityemergencyservice.view.adapters.BidsAdapter;
import com.sergeydeveloper7.cityemergencyservice.view.basic.AdminScreenView;

import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdminFragment extends Fragment implements AdminScreenView {

    private static final String  TAG = MainScreenFragment.class.getSimpleName();
    private Context              context;
    private MainActivity         mainActivity;
    private AdminAdapter         adminAdapter;
    private AdminScreenPresenter presenter;
    private BidsAdapter          bidsAdapter;
    private ArrayList<Bid>       bids = new ArrayList<>();
    private String[]             data = { "Новые Заявки",
                                          "Активные Заявки",
                                          "Выполненые Заявки",
                                          "Отмененные Заявки" };

    private ArrayList<String> indexWorkers = new ArrayList<>(Arrays.asList(
            Const.REGISTER_FIELD_WORKER,
            Const.REGISTER_FIELD_WORKER,
            Const.REGISTER_FIELD_WORKER,
            Const.REGISTER_FIELD_WORKER,
            Const.REGISTER_FIELD_WORKER));

    @BindView(R.id.bidsRecyclerView)           RecyclerView bidsRecyclerView;
    @BindView(R.id.workersRecyclerView)        RecyclerView workersRecyclerView;
    @BindView(R.id.bottom_navigation)          BottomNavigationView bottomNavigation;
    @BindView(R.id.adminsProgressBar)          ProgressBar adminsProgressBar;
    @BindView(R.id.noBidsTextView)             TextView noBidsTextView;
    @BindView(R.id.bidsSpinner)                Spinner bidsSpinner;
    @BindView(R.id.spinnerBackgroundImageView) ImageView spinnerBackgroundImageView;
    @BindView(R.id.bidsTitleTextView)          TextView bidsTitleTextView;

    @Inject Navigator navigator;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        setHasOptionsMenu(true);
        mainActivity = (MainActivity)getActivity();
        ((MainActivity)getActivity()).getApplicationComponent().inject(this);
        presenter = new AdminScreenPresenter(context, TAG, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_admin, container, false);
        ButterKnife.bind(this, rootView);
        setBidsRecyclerView();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bidsSpinner.setAdapter(adapter);
        bidsSpinner.setPrompt("Title");
        bidsSpinner.setSelection(0);
        bidsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        presenter.getBids(context.getString(R.string.workers_screen_active_bids_server_status));
                        break;
                    case 1:
                        presenter.getBids(context.getString(R.string.workers_screen_in_progress_bids_server_status));
                        break;
                    case 2:
                        presenter.getBids(context.getString(R.string.workers_screen_completed_bids_server_status));
                        break;
                    case 3:
                        presenter.getBids(context.getString(R.string.workers_screen_canceled_bids_server_status));
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        bottomNavigation.setOnNavigationItemSelectedListener((@NonNull MenuItem item) -> {
                        switch (item.getItemId()) {
                            case R.id.action_list:
                                bidsTitleTextView.setVisibility(View.VISIBLE);
                                spinnerBackgroundImageView.setVisibility(View.VISIBLE);
                                bidsSpinner.setVisibility(View.VISIBLE);
                                bidsRecyclerView.setVisibility(View.VISIBLE);
                                workersRecyclerView.setVisibility(View.GONE);
                                bidsSpinner.setSelection(0);
                                presenter.getBids(context.getString(R.string.workers_screen_active_bids_server_status));
                                break;
                            case R.id.action_people:
                                bidsTitleTextView.setVisibility(View.GONE);
                                spinnerBackgroundImageView.setVisibility(View.GONE);
                                bidsSpinner.setVisibility(View.GONE);
                                bidsRecyclerView.setVisibility(View.GONE);
                                workersRecyclerView.setVisibility(View.VISIBLE);
                                noBidsTextView.setVisibility(View.GONE);
                                break;
                        }
                        return true;
                });
        setWorkersRecyclerView();
        setBidsRecyclerView();
        bidsRecyclerView.setVisibility(View.VISIBLE);
        workersRecyclerView.setVisibility(View.GONE);
        presenter.getBids(context.getString(R.string.workers_screen_active_bids_server_status));
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mainActivity = (MainActivity) getActivity();
        if (mainActivity != null && mainActivity.getToolbar() != null) {
            mainActivity.getToolbar().setVisibility(View.VISIBLE);
            mainActivity.getAppBarLayout().setVisibility(View.VISIBLE);
            mainActivity.getToolbar().setTitle(context.getString(R.string.admin_screen));
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.admin_top_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            navigator.startFragmentWithBackStack(context, new NewBidFragment(), Const.NEW_REQUEST_FRAGMENT_ID);
        }
        return super.onOptionsItemSelected(item);
    }

    private void setWorkersRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        workersRecyclerView.setHasFixedSize(false);
        workersRecyclerView.setLayoutManager(layoutManager);
        workersRecyclerView.setItemViewCacheSize(50);
        workersRecyclerView.setDrawingCacheEnabled(true);
        RecyclerView.ItemAnimator animator = workersRecyclerView.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }
        adminAdapter = new AdminAdapter(context, indexWorkers);
        workersRecyclerView.setAdapter(adminAdapter);
    }

    private void setBidsRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        bidsRecyclerView.setHasFixedSize(false);
        bidsRecyclerView.setLayoutManager(layoutManager);
        bidsRecyclerView.setItemViewCacheSize(50);
        bidsRecyclerView.setDrawingCacheEnabled(true);
        RecyclerView.ItemAnimator animator = bidsRecyclerView.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }
        bidsAdapter = new BidsAdapter(context, bids, this);
        bidsRecyclerView.setAdapter(bidsAdapter);
    }

    public void viewBidDetails(Bid bid){
        Fragment fragment = new BidDetailFragment();
        Bundle args = new Bundle();
        args.putString("bid", new Gson().toJson(bid, Bid.class));
        fragment.setArguments(args);
        navigator.startFragmentWithBackStack(context, fragment, Const.BID_DETAIL_FRAGMENT_ID);
    }

    @Override
    public void beforeRequest() {
        bidsRecyclerView.setVisibility(View.GONE);
        adminsProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void requestRuntimeError(String error) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
        bidsRecyclerView.setVisibility(View.VISIBLE);
        adminsProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void getBidsRequest(ArrayList<Bid> bids) {
        bidsRecyclerView.setVisibility(View.VISIBLE);
        adminsProgressBar.setVisibility(View.GONE);
        this.bids.clear();
        this.bids.addAll(bids);
        bidsAdapter.notifyDataSetChanged();
        if(this.bids.isEmpty()){
            noBidsTextView.setVisibility(View.VISIBLE);
            bidsRecyclerView.setVisibility(View.GONE);
        } else {
            noBidsTextView.setVisibility(View.GONE);
            bidsRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void connectionErrorResponse() {
        bidsRecyclerView.setVisibility(View.VISIBLE);
        adminsProgressBar.setVisibility(View.GONE);
    }
}
