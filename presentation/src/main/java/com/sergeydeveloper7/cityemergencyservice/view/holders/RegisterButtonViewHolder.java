package com.sergeydeveloper7.cityemergencyservice.view.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.sergeydeveloper7.cityemergencyservice.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by serg on 11.01.18.
 */

public class RegisterButtonViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.registerButton) public Button registerButton;

    public RegisterButtonViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
