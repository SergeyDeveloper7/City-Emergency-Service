package com.sergeydeveloper7.cityemergencyservice.view.holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.sergeydeveloper7.cityemergencyservice.R;
import com.sergeydeveloper7.cityemergencyservice.utils.Const;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by serg on 11.01.18.
 */

public class RegisterButtonViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.fieldButton) public Button fieldButton;

    public RegisterButtonViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bindViews(Context context, String index){
        switch (index){
            case Const.REGISTER_FIELD_BUTTON:
                fieldButton.setText(context.getString(R.string.main_screen_register_enter));
                break;
            case Const.REGISTER_FIELD_CUSTOMERS_BUTTON:
                fieldButton.setText(context.getString(R.string.new_request_screen_create));
                break;
        }
    }
}
