package com.sergeydeveloper7.cityemergencyservice.view.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sergeydeveloper7.cityemergencyservice.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by serg on 23.01.18.
 */

public class ChoosePlaceViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.registerFieldPlaceTextView) public TextView registerFieldPlaceTextView;
    @BindView(R.id.choosePlaceButton)          public Button choosePlaceButton;
    @BindView(R.id.placepickerProgressBar)     public ProgressBar placepickerProgressBar;
    @BindView(R.id.registerFieldLabelTextView) public TextView registerFieldLabelTextView;
    @BindView(R.id.backgroundRelativeLayout)   public RelativeLayout backgroundRelativeLayout;

    public ChoosePlaceViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
