package com.sergeydeveloper7.cityemergencyservice.view.holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sergeydeveloper7.cityemergencyservice.R;
import com.sergeydeveloper7.cityemergencyservice.model.general.Bid;
import com.sergeydeveloper7.cityemergencyservice.utils.Const;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by serg on 15.02.18.
 */

public class ItemBidInfoViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.titleMainLabel)          TextView titleMainLabel;
    @BindView(R.id.titleDetailLabel)        TextView titleDetailLabel;
    @BindView(R.id.fieldButton)             public Button fieldButton;
    @BindView(R.id.editInformationEditText) public EditText editInformationEditText;

    public ItemBidInfoViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bindViews(Context context, String index, Bid bid){
        switch (index){
            case Const.REGISTER_FIELD_CUSTOMERS_NAME:
                titleMainLabel.setText(context.getString(R.string.new_request_screen_customers_name));
                fieldButton.setVisibility(View.GONE);
                if(bid.getCustomersName() != null && !bid.getCustomersName().isEmpty())
                    titleDetailLabel.setText(bid.getCustomersName());
                else
                    titleDetailLabel.setText(context.getString(R.string.bid_detail_screen_no_information));
                break;
            case Const.REGISTER_FIELD_CUSTOMERS_PHONE_NUMBER:
                titleMainLabel.setText(context.getString(R.string.new_request_screen_customers_phone_number));
                fieldButton.setVisibility(View.GONE);
                if(bid.getCustomersPhone() != null && !bid.getCustomersPhone().isEmpty())
                    titleDetailLabel.setText(bid.getCustomersPhone());
                else
                    titleDetailLabel.setText(context.getString(R.string.bid_detail_screen_no_information));
                break;
            case Const.REGISTER_FIELD_CUSTOMERS_ADDRESS:
                titleMainLabel.setText(context.getString(R.string.new_request_screen_customers_address));
                fieldButton.setVisibility(View.VISIBLE);
                if(bid.getCustomersAddressTitle() != null && !bid.getCustomersAddressTitle().isEmpty())
                    titleDetailLabel.setText(bid.getCustomersAddressTitle());
                else
                    titleDetailLabel.setText(context.getString(R.string.bid_detail_screen_no_information));
                break;
            case Const.REGISTER_FIELD_CUSTOMERS_DISTRICT:
                titleMainLabel.setText(context.getString(R.string.new_request_screen_customers_district));
                fieldButton.setVisibility(View.GONE);
                if(bid.getCustomersDistrict() != null && !bid.getCustomersDistrict().isEmpty())
                    titleDetailLabel.setText(bid.getCustomersDistrict());
                else
                    titleDetailLabel.setText(context.getString(R.string.bid_detail_screen_no_information));
                break;
            case Const.REGISTER_FIELD_CUSTOMERS_COMMENTS:
                titleMainLabel.setText(context.getString(R.string.new_request_screen_customers_comments));
                fieldButton.setVisibility(View.GONE);
                if(bid.getComments() != null && !bid.getComments().isEmpty())
                    titleDetailLabel.setText(bid.getComments());
                else
                    titleDetailLabel.setText(context.getString(R.string.bid_detail_screen_no_information));
                break;
            case Const.REGISTER_FIELD_BIDS_TITLE:
                titleMainLabel.setText(context.getString(R.string.new_request_screen_bids_title));
                fieldButton.setVisibility(View.GONE);
                if(bid.getTitle() != null && !bid.getTitle().isEmpty())
                    titleDetailLabel.setText(bid.getTitle());
                else
                    titleDetailLabel.setText(context.getString(R.string.bid_detail_screen_no_information));
                break;
            case Const.REGISTER_FIELD_BIDS_STATUS:
                titleMainLabel.setText(context.getString(R.string.new_request_screen_bids_status));
                fieldButton.setVisibility(View.GONE);
                if(bid.getStatus() != null && !bid.getStatus().isEmpty()){
                    if(bid.getStatus().equals(context.getString(R.string.workers_screen_active_bids_server_status))){
                        titleDetailLabel.setText(context.getString(R.string.workers_screen_new_bids));
                    } else if(bid.getStatus().equals(context.getString(R.string.workers_screen_in_progress_bids_server_status))){
                        titleDetailLabel.setText(context.getString(R.string.workers_screen_active_bids));
                    } else if(bid.getStatus().equals(context.getString(R.string.workers_screen_completed_bids_server_status))){
                        titleDetailLabel.setText(context.getString(R.string.workers_screen_completed_bids));
                    } else if(bid.getStatus().equals(context.getString(R.string.workers_screen_canceled_bids_server_status))){
                        titleDetailLabel.setText(context.getString(R.string.workers_screen_canceled_bids));
                    }
                }
                else
                    titleDetailLabel.setText(context.getString(R.string.bid_detail_screen_no_information));
                break;

            case Const.EDIT_REGISTER_FIELD_CUSTOMERS_NAME:
                titleMainLabel.setText(context.getString(R.string.new_request_screen_customers_name));
                fieldButton.setVisibility(View.GONE);
                titleDetailLabel.setVisibility(View.GONE);
                editInformationEditText.setVisibility(View.VISIBLE);
                if(bid.getCustomersName() != null && !bid.getCustomersName().isEmpty())
                    editInformationEditText.setText(bid.getCustomersName());
                break;
            case Const.EDIT_REGISTER_FIELD_CUSTOMERS_PHONE_NUMBER:
                titleMainLabel.setText(context.getString(R.string.new_request_screen_customers_phone_number));
                fieldButton.setVisibility(View.GONE);
                titleDetailLabel.setVisibility(View.GONE);
                editInformationEditText.setVisibility(View.VISIBLE);
                if(bid.getCustomersPhone() != null && !bid.getCustomersPhone().isEmpty())
                    editInformationEditText.setText(bid.getCustomersPhone());
                break;
            case Const.EDIT_REGISTER_FIELD_CUSTOMERS_ADDRESS:
                titleMainLabel.setText(context.getString(R.string.new_request_screen_customers_address));
                fieldButton.setVisibility(View.VISIBLE);
                titleDetailLabel.setVisibility(View.GONE);
                editInformationEditText.setVisibility(View.VISIBLE);
                if(bid.getCustomersAddressTitle() != null && !bid.getCustomersAddressTitle().isEmpty())
                    editInformationEditText.setText(bid.getCustomersAddressTitle());
                break;
            case Const.EDIT_REGISTER_FIELD_CUSTOMERS_DISTRICT:
                titleMainLabel.setText(context.getString(R.string.new_request_screen_customers_district));
                fieldButton.setVisibility(View.GONE);
                titleDetailLabel.setVisibility(View.GONE);
                editInformationEditText.setVisibility(View.VISIBLE);
                if(bid.getCustomersDistrict() != null && !bid.getCustomersDistrict().isEmpty())
                    editInformationEditText.setText(bid.getCustomersDistrict());
                break;
            case Const.EDIT_REGISTER_FIELD_CUSTOMERS_COMMENTS:
                titleMainLabel.setText(context.getString(R.string.new_request_screen_customers_comments));
                fieldButton.setVisibility(View.GONE);
                titleDetailLabel.setVisibility(View.GONE);
                editInformationEditText.setVisibility(View.VISIBLE);
                if(bid.getComments() != null && !bid.getComments().isEmpty())
                    editInformationEditText.setText(bid.getComments());
                break;
            case Const.EDIT_REGISTER_FIELD_BIDS_TITLE:
                titleMainLabel.setText(context.getString(R.string.new_request_screen_bids_title));
                fieldButton.setVisibility(View.GONE);
                titleDetailLabel.setVisibility(View.GONE);
                editInformationEditText.setVisibility(View.VISIBLE);
                if(bid.getTitle() != null && !bid.getTitle().isEmpty())
                    editInformationEditText.setText(bid.getTitle());
                break;
        }
    }
}
