package com.sergeydeveloper7.cityemergencyservice.view.holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.sergeydeveloper7.cityemergencyservice.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by serg on 15.02.18.
 */

public class ItemBidChooseStatusViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.chooseStatusSpinner) public Spinner chooseStatusSpinner;
    private String[] data = { "Новые Заявки",
                              "Активные Заявки",
                              "Выполненые Заявки",
                              "Отмененные Заявки" };

    public ItemBidChooseStatusViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bindView(Context context, String status){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chooseStatusSpinner.setAdapter(adapter);
        chooseStatusSpinner.setPrompt("Title");

        if(status != null && !status.isEmpty()){
            if(status.equals(context.getString(R.string.workers_screen_active_bids_server_status))){
                chooseStatusSpinner.setSelection(0);
            } else if(status.equals(context.getString(R.string.workers_screen_in_progress_bids_server_status))){
                chooseStatusSpinner.setSelection(1);
            } else if(status.equals(context.getString(R.string.workers_screen_completed_bids_server_status))){
                chooseStatusSpinner.setSelection(2);
            } else if(status.equals(context.getString(R.string.workers_screen_canceled_bids_server_status))){
                chooseStatusSpinner.setSelection(3);
            }
        }
    }
}
