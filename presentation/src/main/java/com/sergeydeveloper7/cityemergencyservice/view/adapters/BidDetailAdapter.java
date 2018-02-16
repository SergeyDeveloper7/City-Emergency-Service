package com.sergeydeveloper7.cityemergencyservice.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.sergeydeveloper7.cityemergencyservice.R;
import com.sergeydeveloper7.cityemergencyservice.model.general.Bid;
import com.sergeydeveloper7.cityemergencyservice.model.request.EditBidRequestModel;
import com.sergeydeveloper7.cityemergencyservice.network.Connection;
import com.sergeydeveloper7.cityemergencyservice.utils.Const;
import com.sergeydeveloper7.cityemergencyservice.view.fragments.BidDetailFragment;
import com.sergeydeveloper7.cityemergencyservice.view.holders.ChoosePlaceViewHolder;
import com.sergeydeveloper7.cityemergencyservice.view.holders.ItemBidChooseStatusViewHolder;
import com.sergeydeveloper7.cityemergencyservice.view.holders.ItemBidInfoViewHolder;

import java.util.ArrayList;

/**
 * Created by serg on 09.02.18.
 */

public class BidDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int           VIEW_TYPE_INFO = 0;
    private final int           VIEW_TYPE_SPINNER = 1;
    private final int           VIEW_TYPE_ADDRESS = 2;
    private Context             context;
    private ArrayList<String>   index;
    private Bid                 bid;
    private BidDetailFragment   fragment;
    private EditBidRequestModel editBidRequestModel = new EditBidRequestModel();

    public BidDetailAdapter(Context context, ArrayList<String> index, Bid bid, BidDetailFragment fragment) {
        this.context = context;
        this.index = index;
        this.bid = bid;
        this.fragment = fragment;
        setRequestModel();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_INFO) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bid_info_label, parent, false);
            return new ItemBidInfoViewHolder(layoutView);
        } else if (viewType == VIEW_TYPE_SPINNER) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bid_choose_status_label, parent, false);
            return new ItemBidChooseStatusViewHolder(layoutView);
        } else if (viewType == VIEW_TYPE_ADDRESS) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_register_field_edit_text_map, parent, false);
            return new ChoosePlaceViewHolder(layoutView);
        }
        return null;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ItemBidInfoViewHolder) {
            ItemBidInfoViewHolder viewHolder = (ItemBidInfoViewHolder) holder;
            viewHolder.bindViews(context, index.get(viewHolder.getAdapterPosition()), bid);
            viewHolder.fieldButton.setOnClickListener((View v) -> {
                fragment.viewMap();
            });
            viewHolder.editInformationEditText.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    switch (index.get(viewHolder.getAdapterPosition())){
                        case Const.EDIT_REGISTER_FIELD_CUSTOMERS_NAME:
                            editBidRequestModel.setCustomersName(s.toString());
                            break;
                        case Const.EDIT_REGISTER_FIELD_CUSTOMERS_PHONE_NUMBER:
                            editBidRequestModel.setCustomersPhone(s.toString());
                            break;
                        case Const.EDIT_REGISTER_FIELD_CUSTOMERS_DISTRICT:
                            editBidRequestModel.setCustomersDistrict(s.toString());
                            break;
                        case Const.EDIT_REGISTER_FIELD_CUSTOMERS_COMMENTS:
                            editBidRequestModel.setComments(s.toString());
                            break;
                        case Const.EDIT_REGISTER_FIELD_BIDS_TITLE:
                            editBidRequestModel.setTitle(s.toString());
                            break;
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        } else if(holder instanceof ItemBidChooseStatusViewHolder){
            ItemBidChooseStatusViewHolder viewHolder = (ItemBidChooseStatusViewHolder) holder;
            viewHolder.bindView(context, bid.getStatus());
            viewHolder.chooseStatusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    switch (position){
                        case 0:
                            editBidRequestModel.setStatus(context.getString(R.string.workers_screen_active_bids_server_status));
                            break;
                        case 1:
                            editBidRequestModel.setStatus(context.getString(R.string.workers_screen_in_progress_bids_server_status));
                            break;
                        case 2:
                            editBidRequestModel.setStatus(context.getString(R.string.workers_screen_completed_bids_server_status));
                            break;
                        case 3:
                            editBidRequestModel.setStatus(context.getString(R.string.workers_screen_canceled_bids_server_status));
                            break;
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> arg0) {

                }
            });

        } else if(holder instanceof ChoosePlaceViewHolder){
            ChoosePlaceViewHolder viewHolder = (ChoosePlaceViewHolder) holder;
            viewHolder.choosePlaceButton.setVisibility(View.VISIBLE);
            viewHolder.placepickerProgressBar.setVisibility(View.GONE);
            viewHolder.registerFieldPlaceTextView.setTextColor(context.getResources().getColor(R.color.black));
            viewHolder.registerFieldLabelTextView.setTextColor(context.getResources().getColor(R.color.black));
            viewHolder.backgroundRelativeLayout.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.rounded_background_black));

            if(!editBidRequestModel.getCustomersAddressTitle().isEmpty())
                viewHolder.registerFieldPlaceTextView.setText(editBidRequestModel.getCustomersAddressTitle());

            viewHolder.choosePlaceButton.setOnClickListener((View v) -> {
                if(Connection.isConnected()){
                    fragment.openPlacePicker();
                    viewHolder.choosePlaceButton.setVisibility(View.GONE);
                    viewHolder.placepickerProgressBar.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(context, R.string.internet_connection_is_not_available, Toast.LENGTH_SHORT).show();
                }
            });

        }

    }

    @Override
    public int getItemViewType(int position) {
        switch(index.get(position)){
            case Const.EDIT_REGISTER_FIELD_BIDS_STATUS:
                return VIEW_TYPE_SPINNER;
            case Const.EDIT_REGISTER_FIELD_CUSTOMERS_ADDRESS:
                return VIEW_TYPE_ADDRESS;
            default:
                return VIEW_TYPE_INFO;
        }
    }

    public void setRequestModel(){
        editBidRequestModel.setCustomersName(bid.getCustomersName());
        editBidRequestModel.setCustomersPhone(bid.getCustomersPhone());
        editBidRequestModel.setCustomersAddressTitle(bid.getCustomersAddressTitle());
        editBidRequestModel.setCustomersAddressLatitude(bid.getCustomersAddressLatitude());
        editBidRequestModel.setCustomersAddressLongtitude(bid.getCustomersAddressLongtitude());
        editBidRequestModel.setCustomersDistrict(bid.getCustomersDistrict());
        editBidRequestModel.setTitle(bid.getTitle());
        editBidRequestModel.setComments(bid.getComments());
        editBidRequestModel.setStatus(bid.getStatus());
        editBidRequestModel.setBidID(bid.getId());
    }

    public void setBid(Bid bid){
        this.bid = bid;
        setRequestModel();
    }

    @Override
    public int getItemCount() {
        return this.index == null ? 0 : this.index.size();
    }

    public EditBidRequestModel getEditBidRequestModel() {
        return editBidRequestModel;
    }
}
