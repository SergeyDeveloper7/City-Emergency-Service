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
import com.sergeydeveloper7.cityemergencyservice.model.request.SignUpRequestModel;
import com.sergeydeveloper7.cityemergencyservice.utils.Const;
import com.sergeydeveloper7.cityemergencyservice.view.fragments.RegisterFragment;
import com.sergeydeveloper7.cityemergencyservice.view.holders.RegisterButtonViewHolder;
import com.sergeydeveloper7.cityemergencyservice.view.holders.RegisterFieldViewHolder;

import java.util.ArrayList;

/**
 * Created by serg on 09.01.18.
 */

public class RegisterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int          VIEW_TYPE_FIELD = 0;
    private final int          VIEW_TYPE_BUTTON = 1;
    private Context            context;
    private ArrayList<String>  index;
    private RegisterFragment   fragment;
    private SignUpRequestModel signUpRequestModel = new SignUpRequestModel();

    public RegisterAdapter(Context context, ArrayList<String> index, RegisterFragment fragment) {
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
                        case Const.REGISTER_FIELD_LOGIN:
                            signUpRequestModel.setUsername(s.toString());
                            break;
                        case Const.REGISTER_FIELD_PASSWORD:
                            if(!s.toString().isEmpty()){
                                signUpRequestModel.setPassword(s.toString());
                            }
                            break;
                        case Const.REGISTER_FIELD_FIRST_NAME:
                            signUpRequestModel.setFirstName(s.toString());
                            break;
                        case Const.REGISTER_FIELD_LAST_NAME:
                            signUpRequestModel.setLastName(s.toString());
                            break;
                        case Const.REGISTER_FIELD_PHONENUMBER:
                            signUpRequestModel.setPhoneNumber(s.toString());
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
                if(signUpRequestModel.getUsername().isEmpty()){
                    Toast.makeText(context, context.getString(R.string.register_screen_login_hint), Toast.LENGTH_SHORT).show();
                } else if(signUpRequestModel.getPassword().isEmpty()){
                    Toast.makeText(context, context.getString(R.string.register_screen_password_hint), Toast.LENGTH_SHORT).show();
                } else if(signUpRequestModel.getFirstName().isEmpty()){
                    Toast.makeText(context, context.getString(R.string.register_screen_first_name_hint), Toast.LENGTH_SHORT).show();
                } else if(signUpRequestModel.getLastName().isEmpty()){
                    Toast.makeText(context, context.getString(R.string.register_screen_last_name_hint), Toast.LENGTH_SHORT).show();
                } else if(signUpRequestModel.getPhoneNumber().isEmpty()){
                    Toast.makeText(context, context.getString(R.string.register_screen_phone_number_hint), Toast.LENGTH_SHORT).show();
                } else
                    fragment.register(signUpRequestModel);
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
            case Const.REGISTER_FIELD_BUTTON:
                return VIEW_TYPE_BUTTON;
            default:
                return VIEW_TYPE_FIELD;
        }
    }
}
