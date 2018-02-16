package com.sergeydeveloper7.cityemergencyservice.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sergeydeveloper7.cityemergencyservice.R;
import com.sergeydeveloper7.cityemergencyservice.model.request.NewBidRequestModel;
import com.sergeydeveloper7.cityemergencyservice.network.Connection;
import com.sergeydeveloper7.cityemergencyservice.utils.Const;
import com.sergeydeveloper7.cityemergencyservice.view.fragments.NewBidFragment;
import com.sergeydeveloper7.cityemergencyservice.view.holders.ChoosePlaceViewHolder;
import com.sergeydeveloper7.cityemergencyservice.view.holders.RegisterButtonViewHolder;
import com.sergeydeveloper7.cityemergencyservice.view.holders.RegisterFieldViewHolder;

import java.util.ArrayList;

/**
 * Created by serg on 23.01.18.
 */

public class NewBidAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int          VIEW_TYPE_FIELD = 0;
    private final int          VIEW_TYPE_BUTTON = 1;
    private final int          VIEW_TYPE_ADDRESS = 2;
    private Context            context;
    private ArrayList<String>  index;
    private NewBidFragment     fragment;
    private NewBidRequestModel newBidRequestModel = new NewBidRequestModel();

    public NewBidAdapter(Context context, ArrayList<String> index, NewBidFragment fragment) {
        this.context = context;
        this.index = index;
        this.fragment = fragment;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_FIELD) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_register_field_edit_text, parent, false);
            return new RegisterFieldViewHolder(layoutView);
        } else if (viewType == VIEW_TYPE_BUTTON) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_register_field_button, parent, false);
            return new RegisterButtonViewHolder(layoutView);
        } else if (viewType == VIEW_TYPE_ADDRESS) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_register_field_edit_text_map, parent, false);
            return new ChoosePlaceViewHolder(layoutView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof RegisterFieldViewHolder){
            RegisterFieldViewHolder viewHolder = (RegisterFieldViewHolder) holder;
            viewHolder.bindViews(context, index.get(viewHolder.getAdapterPosition()));
            viewHolder.registerFieldEnterInformationEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    switch (index.get(viewHolder.getAdapterPosition())){
                        case Const.REGISTER_FIELD_CUSTOMERS_NAME:
                            newBidRequestModel.setCustomersName(s.toString());
                            break;
                        case Const.REGISTER_FIELD_CUSTOMERS_PHONE_NUMBER:
                            newBidRequestModel.setCustomersPhone(s.toString());
                            break;
                        case Const.REGISTER_FIELD_CUSTOMERS_DISTRICT:
                            newBidRequestModel.setCustomersDistrict(s.toString());
                            break;
                        case Const.REGISTER_FIELD_CUSTOMERS_COMMENTS:
                            newBidRequestModel.setComments(s.toString());
                            break;
                        case Const.REGISTER_FIELD_BIDS_TITLE:
                            newBidRequestModel.setTitle(s.toString());
                            break;
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        } else if(holder instanceof RegisterButtonViewHolder){
            RegisterButtonViewHolder viewHolder = (RegisterButtonViewHolder) holder;
            viewHolder.bindViews(context, index.get(viewHolder.getAdapterPosition()));
            viewHolder.fieldButton.setOnClickListener((View v) -> {
                if(newBidRequestModel.getCustomersName().isEmpty()){
                    Toast.makeText(context, context.getString(R.string.new_request_screen_customers_name_hint), Toast.LENGTH_SHORT).show();
                } else if(newBidRequestModel.getCustomersPhone().isEmpty()){
                    Toast.makeText(context, context.getString(R.string.new_request_screen_customers_phone_number_hint), Toast.LENGTH_SHORT).show();
                } else if(newBidRequestModel.getCustomersAddressTitle().isEmpty()){
                    Toast.makeText(context, context.getString(R.string.new_request_screen_customers_choose_your_address), Toast.LENGTH_SHORT).show();
                } else if(newBidRequestModel.getCustomersDistrict().isEmpty()){
                    Toast.makeText(context, context.getString(R.string.new_request_screen_customers_district_hint), Toast.LENGTH_SHORT).show();
                } else if(newBidRequestModel.getTitle().isEmpty()){
                    Toast.makeText(context, context.getString(R.string.new_request_screen_bids_title_hint), Toast.LENGTH_SHORT).show();
                } else
                    fragment.newBid(newBidRequestModel);
            });
        } else if(holder instanceof ChoosePlaceViewHolder){
            ChoosePlaceViewHolder viewHolder = (ChoosePlaceViewHolder) holder;
            viewHolder.choosePlaceButton.setVisibility(View.VISIBLE);
            viewHolder.placepickerProgressBar.setVisibility(View.GONE);

            if(!newBidRequestModel.getCustomersAddressTitle().isEmpty())
                viewHolder.registerFieldPlaceTextView.setText(newBidRequestModel.getCustomersAddressTitle());

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
    public int getItemCount() {
        return this.index == null ? 0 : this.index.size();
    }

    @Override
    public int getItemViewType(int position) {
        switch(index.get(position)){
            case Const.REGISTER_FIELD_CUSTOMERS_BUTTON:
                return VIEW_TYPE_BUTTON;
            case Const.REGISTER_FIELD_CUSTOMERS_ADDRESS:
                return VIEW_TYPE_ADDRESS;
            default:
                return VIEW_TYPE_FIELD;
        }
    }

    public NewBidRequestModel getNewBidRequestModel() {
        return newBidRequestModel;
    }
}
