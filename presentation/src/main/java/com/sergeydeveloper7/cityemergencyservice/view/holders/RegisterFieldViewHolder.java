package com.sergeydeveloper7.cityemergencyservice.view.holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sergeydeveloper7.cityemergencyservice.R;
import com.sergeydeveloper7.cityemergencyservice.utils.Const;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by serg on 10.01.18.
 */

public class RegisterFieldViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.registerFieldLabelTextView) public TextView registerFieldLabelTextView;
    @BindView(R.id.registerFieldEnterInformationEditText) public EditText registerFieldEnterInformationEditText;

    public RegisterFieldViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bindViews(Context context, String index){
        switch (index){
            case Const.REGISTER_FIELD_LOGIN:
                registerFieldLabelTextView.setText(context.getString(R.string.main_screen_email_address));
                registerFieldEnterInformationEditText.setHint(context.getString(R.string.register_screen_login_hint));
                break;
            case Const.REGISTER_FIELD_PASSWORD:
                registerFieldLabelTextView.setText(context.getString(R.string.main_screen_password));
                registerFieldEnterInformationEditText.setHint(context.getString(R.string.register_screen_password_hint));
                registerFieldEnterInformationEditText.setInputType(InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_VARIATION_PASSWORD);
                break;
            case Const.REGISTER_FIELD_FIRST_NAME:
                registerFieldLabelTextView.setText(context.getString(R.string.register_screen_first_name));
                registerFieldEnterInformationEditText.setHint(context.getString(R.string.register_screen_first_name_hint));
                break;
            case Const.REGISTER_FIELD_LAST_NAME:
                registerFieldLabelTextView.setText(context.getString(R.string.register_screen_last_name));
                registerFieldEnterInformationEditText.setHint(context.getString(R.string.register_screen_last_name_hint));
                break;
            case Const.REGISTER_FIELD_PHONENUMBER:
                registerFieldLabelTextView.setText(context.getString(R.string.register_screen_phone_number));
                registerFieldEnterInformationEditText.setHint(context.getString(R.string.register_screen_phone_number_hint));
                registerFieldEnterInformationEditText.setInputType(InputType.TYPE_CLASS_PHONE);
                break;
            case Const.REGISTER_FIELD_CUSTOMERS_NAME:
                registerFieldLabelTextView.setText(context.getString(R.string.new_request_screen_customers_name));
                registerFieldEnterInformationEditText.setHint(context.getString(R.string.new_request_screen_customers_name_hint));
                break;
            case Const.REGISTER_FIELD_CUSTOMERS_PHONE_NUMBER:
                registerFieldLabelTextView.setText(context.getString(R.string.new_request_screen_customers_phone_number));
                registerFieldEnterInformationEditText.setHint(context.getString(R.string.new_request_screen_customers_phone_number_hint));
                registerFieldEnterInformationEditText.setInputType(InputType.TYPE_CLASS_PHONE);
                break;
            case Const.REGISTER_FIELD_CUSTOMERS_DISTRICT:
                registerFieldLabelTextView.setText(context.getString(R.string.new_request_screen_customers_district));
                registerFieldEnterInformationEditText.setHint(context.getString(R.string.new_request_screen_customers_district_hint));
                break;
            case Const.REGISTER_FIELD_CUSTOMERS_COMMENTS:
                registerFieldLabelTextView.setText(context.getString(R.string.new_request_screen_customers_comments));
                registerFieldEnterInformationEditText.setHint(context.getString(R.string.new_request_screen_customers_comments_hint));
                break;
            case Const.REGISTER_FIELD_BIDS_TITLE:
                registerFieldLabelTextView.setText(context.getString(R.string.new_request_screen_bids_title));
                registerFieldEnterInformationEditText.setHint(context.getString(R.string.new_request_screen_bids_title_hint));
                break;
        }
    }
}
