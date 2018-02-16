package com.sergeydeveloper7.cityemergencyservice.view.fragments;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.sergeydeveloper7.cityemergencyservice.R;
import com.sergeydeveloper7.cityemergencyservice.model.request.NewBidRequestModel;
import com.sergeydeveloper7.cityemergencyservice.presenter.NewBidScreenPresenter;
import com.sergeydeveloper7.cityemergencyservice.utils.Const;
import com.sergeydeveloper7.cityemergencyservice.view.activities.MainActivity;
import com.sergeydeveloper7.cityemergencyservice.view.adapters.NewBidAdapter;
import com.sergeydeveloper7.cityemergencyservice.view.basic.NewBidScreenView;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

public class NewBidFragment extends Fragment implements NewBidScreenView {

    private static final String   TAG = NewBidFragment.class.getSimpleName();
    private static final int      PLACE_PICKER_REQUEST = 1;
    private Context               context;
    private NewBidAdapter         adapter;
    private MainActivity          mainActivity;
    private NewBidScreenPresenter presenter;

    @BindView(R.id.newBidRecyclerView) RecyclerView newBidRecyclerView;
    @BindView(R.id.newBidProgressBar) ProgressBar newBidProgressBar;

    private ArrayList<String>   index = new ArrayList<>(Arrays.asList(
            Const.REGISTER_FIELD_CUSTOMERS_NAME,
            Const.REGISTER_FIELD_CUSTOMERS_PHONE_NUMBER,
            Const.REGISTER_FIELD_CUSTOMERS_ADDRESS,
            Const.REGISTER_FIELD_CUSTOMERS_DISTRICT,
            Const.REGISTER_FIELD_BIDS_TITLE,
            Const.REGISTER_FIELD_CUSTOMERS_COMMENTS,
            Const.REGISTER_FIELD_CUSTOMERS_BUTTON));

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        presenter = new NewBidScreenPresenter(context, TAG, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_new_bid, container, false);
        ButterKnife.bind(this, rootView);
        setBidRecyclerView();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mainActivity = (MainActivity) getActivity();
        if (mainActivity != null && mainActivity.getToolbar() != null) {
            mainActivity.getToolbar().setVisibility(View.GONE);
            mainActivity.getAppBarLayout().setVisibility(View.GONE);
            mainActivity.getToolbar().setTitle("");
        }
        adapter.notifyItemChanged(index.indexOf(Const.REGISTER_FIELD_CUSTOMERS_ADDRESS));
    }

    private void setBidRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        newBidRecyclerView.setHasFixedSize(false);
        newBidRecyclerView.setLayoutManager(layoutManager);
        newBidRecyclerView.setItemViewCacheSize(50);
        newBidRecyclerView.setDrawingCacheEnabled(true);
        RecyclerView.ItemAnimator animator = newBidRecyclerView.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }
        adapter = new NewBidAdapter(context, index, this);
        newBidRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode){
                case PLACE_PICKER_REQUEST:
                    Place place = PlacePicker.getPlace(mainActivity, data);
                    String placeName = String.valueOf(place.getAddress());
                    double latitude = place.getLatLng().latitude;
                    double longitude = place.getLatLng().longitude;
                    adapter.getNewBidRequestModel().setCustomersAddressTitle(placeName);
                    adapter.getNewBidRequestModel().setCustomersAddressLatitude(latitude);
                    adapter.getNewBidRequestModel().setCustomersAddressLongtitude(longitude);
                    adapter.notifyItemChanged(index.indexOf(Const.REGISTER_FIELD_CUSTOMERS_ADDRESS));
            }
        }
    }

    public void openPlacePicker(){
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    public void newBid(NewBidRequestModel newBidRequestModel){
        presenter.newBid(newBidRequestModel);
    }

    @Override
    public void beforeRequest() {
        newBidRecyclerView.setVisibility(View.GONE);
        newBidProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void requestRuntimeError(String error) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
        newBidRecyclerView.setVisibility(View.VISIBLE);
        newBidProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void newBidResponse(boolean isSuccess) {
        newBidRecyclerView.setVisibility(View.VISIBLE);
        newBidProgressBar.setVisibility(View.GONE);
        if(isSuccess){
            Toast.makeText(context, context.getString(R.string.new_request_screen_create_successful), Toast.LENGTH_SHORT).show();
            mainActivity.onBackPressed();
        } else {
            Toast.makeText(context, context.getString(R.string.new_request_screen_create_no_successful), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void connectionErrorResponse() {
        newBidRecyclerView.setVisibility(View.VISIBLE);
        newBidProgressBar.setVisibility(View.GONE);
    }
}
