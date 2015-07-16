package com.fwollo.activities;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.fwollo.R;
import com.fwollo.utils.Dialog;
import com.fwollo.utils.TextUtils;


public class ActivateAccountActivity extends BaseActivity {

    private EditText tvCode;
    private TextView tvDeclimer;

    @Override
    int layoutId() {
        return R.layout.activity_activate_account;
    }

    @Override
    void inflateViews() {
        tvCode = (EditText) findViewById(R.id.et_code);
        tvDeclimer = (TextView) findViewById(R.id.tv_declimer);

        TextUtils.setHighlightedText(tvDeclimer, getResources().getString(R.string.access_code_note_label), "CODE", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog.showAlert(ActivateAccountActivity.this, "Code", "Code was resent");
            }
        });
    }

    @Override
    void viewsWereInflated() {
        initiateTextWatcher();
    }

    @Override
    String title() {
        return "Phone Login";
    }


    private void initiateTextWatcher() {
        tvCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.length() > 3) {
                    startActivity(new Intent(ActivateAccountActivity.this, UserProfileActivity.class));
                    finish();
                }
            }
        });
    }

}