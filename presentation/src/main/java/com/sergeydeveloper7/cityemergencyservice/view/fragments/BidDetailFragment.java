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
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.gson.Gson;
import com.sergeydeveloper7.cityemergencyservice.R;
import com.sergeydeveloper7.cityemergencyservice.model.general.Bid;
import com.sergeydeveloper7.cityemergencyservice.navigation.Navigator;
import com.sergeydeveloper7.cityemergencyservice.presenter.BidDetailScreenPresenter;
import com.sergeydeveloper7.cityemergencyservice.utils.Const;
import com.sergeydeveloper7.cityemergencyservice.view.activities.MainActivity;
import com.sergeydeveloper7.cityemergencyservice.view.adapters.BidDetailAdapter;
import com.sergeydeveloper7.cityemergencyservice.view.basic.BidDetailScreenView;

import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

public class BidDetailFragment extends Fragment implements BidDetailScreenView {

    private static final String      TAG = MainScreenFragment.class.getSimpleName();
    private static final int         PLACE_PICKER_REQUEST = 1;
    private Context                  context;
    private MainActivity             mainActivity;
    private Bid                      bid;
    private BidDetailAdapter         adapter;
    private ArrayList<String>        index = new ArrayList<>();
    private BidDetailScreenPresenter presenter;

    @BindView(R.id.bidDetailRecyclerView) RecyclerView bidDetailRecyclerView;
    @BindView(R.id.bidDetailEditButton)   Button bidDetailEditButton;
    @BindView(R.id.bidDetailDeleteButton) Button bidDetailDeleteButton;
    @BindView(R.id.bidDetailProgressBar)  ProgressBar bidDetailProgressBar;

    @Inject Navigator navigator;

    private ArrayList<String> viewIndex = new ArrayList<>(Arrays.asList(
            Const.REGISTER_FIELD_CUSTOMERS_NAME,
            Const.REGISTER_FIELD_CUSTOMERS_PHONE_NUMBER,
            Const.REGISTER_FIELD_CUSTOMERS_ADDRESS,
            Const.REGISTER_FIELD_CUSTOMERS_DISTRICT,
            Const.REGISTER_FIELD_BIDS_TITLE,
            Const.REGISTER_FIELD_CUSTOMERS_COMMENTS,
            Const.REGISTER_FIELD_BIDS_STATUS));

    private ArrayList<String> editIndex = new ArrayList<>(Arrays.asList(
            Const.EDIT_REGISTER_FIELD_CUSTOMERS_NAME,
            Const.EDIT_REGISTER_FIELD_CUSTOMERS_PHONE_NUMBER,
            Const.EDIT_REGISTER_FIELD_CUSTOMERS_ADDRESS,
            Const.EDIT_REGISTER_FIELD_CUSTOMERS_DISTRICT,
            Const.EDIT_REGISTER_FIELD_BIDS_TITLE,
            Const.EDIT_REGISTER_FIELD_CUSTOMERS_COMMENTS,
            Const.EDIT_REGISTER_FIELD_BIDS_STATUS));

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        setHasOptionsMenu(true);
        mainActivity = (MainActivity)getActivity();
        ((MainActivity)getActivity()).getApplicationComponent().inject(this);
        if(getArguments() != null){
            if(getArguments().getString("bid") != null && !getArguments().getString("bid").isEmpty()){
                bid = new Gson().fromJson(getArguments().getString("bid"), Bid.class);
            }
        }
        presenter = new BidDetailScreenPresenter(context, TAG, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bid_detail, container, false);
        ButterKnife.bind(this, rootView);
        index.addAll(viewIndex);
        setBidRecyclerView();
        return rootView;
    }

    private void setBidRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        bidDetailRecyclerView.setHasFixedSize(false);
        bidDetailRecyclerView.setLayoutManager(layoutManager);
        bidDetailRecyclerView.setItemViewCacheSize(50);
        bidDetailRecyclerView.setDrawingCacheEnabled(true);
        RecyclerView.ItemAnimator animator = bidDetailRecyclerView.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }
        adapter = new BidDetailAdapter(context, index, bid, this);
        bidDetailRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        mainActivity = (MainActivity) getActivity();
        if (mainActivity != null && mainActivity.getToolbar() != null) {
            mainActivity.getToolbar().setVisibility(View.VISIBLE);
            mainActivity.getAppBarLayout().setVisibility(View.VISIBLE);
            mainActivity.getToolbar().setTitle(context.getString(R.string.bid_detail_screen_title));
        }
        adapter.notifyItemChanged(index.indexOf(Const.EDIT_REGISTER_FIELD_CUSTOMERS_ADDRESS));
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
                    adapter.getEditBidRequestModel().setCustomersAddressTitle(placeName);
                    adapter.getEditBidRequestModel().setCustomersAddressLatitude(latitude);
                    adapter.getEditBidRequestModel().setCustomersAddressLongtitude(longitude);
                    adapter.notifyItemChanged(index.indexOf(Const.EDIT_REGISTER_FIELD_CUSTOMERS_ADDRESS));
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

    public void viewMap(){
        Fragment fragment = new AddressMapFragment();
        Bundle args = new Bundle();
        args.putDouble("lat", bid.getCustomersAddressLatitude());
        args.putDouble("lng", bid.getCustomersAddressLongtitude());
        args.putString("address", bid.getCustomersAddressTitle());
        fragment.setArguments(args);
        navigator.startFragmentWithBackStack(context, fragment, Const.ADDRESS_MAP_FRAGMENT_ID);
    }

    @OnClick(R.id.bidDetailEditButton) void edit(){
        if(bidDetailEditButton.getText().equals(context.getString(R.string.bid_detail_screen_edit))){
            bidDetailEditButton.setText(context.getString(R.string.bid_detail_screen_edit_done));
            bidDetailDeleteButton.setVisibility(View.GONE);
            index.clear();
            index.addAll(editIndex);
            adapter.notifyDataSetChanged();
        } else {
            presenter.editBid(adapter.getEditBidRequestModel());

        }
    }

    @OnClick(R.id.bidDetailDeleteButton) void delete(){
        presenter.deleteBid(bid.getId());
    }

    @Override
    public void beforeRequest() {
        bidDetailRecyclerView.setVisibility(View.GONE);
        bidDetailProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void requestRuntimeError(String error) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
        bidDetailRecyclerView.setVisibility(View.VISIBLE);
        bidDetailProgressBar.setVisibility(View.GONE);
        bidDetailEditButton.setText(context.getString(R.string.bid_detail_screen_edit));
        bidDetailDeleteButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void editBidResponse(boolean isSuccess, Bid bid) {
        bidDetailRecyclerView.setVisibility(View.VISIBLE);
        bidDetailProgressBar.setVisibility(View.GONE);
        adapter.setBid(bid);
        adapter.notifyDataSetChanged();
        bidDetailEditButton.setText(context.getString(R.string.bid_detail_screen_edit));
        bidDetailDeleteButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void deleteBidResponse(boolean isSuccess, Bid bid) {
        if(isSuccess){
            Toast.makeText(context, context.getString(R.string.bid_detail_screen_delete_successful), Toast.LENGTH_SHORT).show();
            mainActivity.onBackPressed();
        } else {
            Toast.makeText(context, context.getString(R.string.bid_detail_screen_delete_no_successful), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void connectionErrorResponse() {
        bidDetailRecyclerView.setVisibility(View.VISIBLE);
        bidDetailProgressBar.setVisibility(View.GONE);
        bidDetailEditButton.setText(context.getString(R.string.bid_detail_screen_edit));
        bidDetailDeleteButton.setVisibility(View.VISIBLE);
    }
}
