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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sergeydeveloper7.cityemergencyservice.R;
import com.sergeydeveloper7.cityemergencyservice.model.general.Bid;
import com.sergeydeveloper7.cityemergencyservice.presenter.WorkerScreenPresenter;
import com.sergeydeveloper7.cityemergencyservice.view.activities.MainActivity;
import com.sergeydeveloper7.cityemergencyservice.view.adapters.BidsAdapter;
import com.sergeydeveloper7.cityemergencyservice.view.basic.WorkerScreenView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WorkersScreenFragment extends Fragment implements WorkerScreenView{

    private static final String   TAG = WorkersScreenFragment.class.getSimpleName();
    private Context               context;
    private MainActivity          mainActivity;
    private BidsAdapter adapter;
    private ArrayList<Bid>        bids = new ArrayList<>();
    private WorkerScreenPresenter presenter;

    @BindView(R.id.bidsRecyclerView)        RecyclerView bidsRecyclerView;
    @BindView(R.id.workersProgressBar)      ProgressBar workersProgressBar;
    @BindView(R.id.workersBottomNavigation) BottomNavigationView workersBottomNavigation;
    @BindView(R.id.noBidsTextView)          TextView noBidsTextView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        setHasOptionsMenu(true);
        presenter = new WorkerScreenPresenter(context, TAG, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_workers_screen, container, false);
        ButterKnife.bind(this, rootView);
        workersBottomNavigation.setOnNavigationItemSelectedListener((@NonNull MenuItem item) -> {
            switch (item.getItemId()) {
                case R.id.action_new_bids:
                    presenter.getBids(context.getString(R.string.workers_screen_active_bids_server_status));
                    break;
                case R.id.action_active_bids:

                    break;
                case R.id.action_completed_bids:

                    break;
            }
            return true;
        });
        setRecyclerView();
        presenter.getBids(context.getString(R.string.workers_screen_active_bids_server_status));
        return rootView;
    }

    private void setRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        bidsRecyclerView.setHasFixedSize(false);
        bidsRecyclerView.setLayoutManager(layoutManager);
        bidsRecyclerView.setItemViewCacheSize(50);
        bidsRecyclerView.setDrawingCacheEnabled(true);
        RecyclerView.ItemAnimator animator = bidsRecyclerView.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }
        adapter = new BidsAdapter(context, bids, this);
        bidsRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        mainActivity = (MainActivity) getActivity();
        if (mainActivity != null && mainActivity.getToolbar() != null) {
            mainActivity.getToolbar().setVisibility(View.VISIBLE);
            mainActivity.getAppBarLayout().setVisibility(View.VISIBLE);
            mainActivity.getToolbar().setTitle(context.getString(R.string.workers_screen_main_title));
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.workers_top_menu, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void beforeRequest() {
        bidsRecyclerView.setVisibility(View.GONE);
        workersProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void requestRuntimeError(String error) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
        bidsRecyclerView.setVisibility(View.VISIBLE);
        workersProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void getAllBidsRequest(ArrayList<Bid> bids) {
        bidsRecyclerView.setVisibility(View.VISIBLE);
        workersProgressBar.setVisibility(View.GONE);
        this.bids.clear();
        this.bids.addAll(bids);
        adapter.notifyDataSetChanged();
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
        workersProgressBar.setVisibility(View.GONE);
    }
}
