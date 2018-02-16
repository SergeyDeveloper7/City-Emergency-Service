package com.sergeydeveloper7.cityemergencyservice.view.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.sergeydeveloper7.cityemergencyservice.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by serg on 23.01.18.
 */

public class RequestViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.requestsNumberTextViewDetail) public TextView requestsNumberTextViewDetail;
    @BindView(R.id.requestsTitleTextViewDetail) public TextView requestsTitleTextViewDetail;
//    @BindView(R.id.requestsStatusTextViewDetail) public TextView requestsStatusTextViewDetail;

    public RequestViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
