package com.fwollo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.baoyz.actionsheet.ActionSheet;
import com.fwollo.utils.MediaActions;
import com.fwollo.utils.MediaUtils;
import com.github.siyamed.shapeimageview.CircularImageView;

public class UserProfileActivity extends BaseActivity implements ActionSheet.ActionSheetListener {

    private TextView tvPickPhoto;
    private CircularImageView imageView;
    private Button btnContinue;

    @Override
    int layoutId() {
        return R.layout.activity_profile;
    }

    @Override
    void inflateViews() {

        //btnLogin = (Button) findViewById(R.id.btn_login);

        imageView = (CircularImageView)findViewById(R.id.image_user);
        imageView.setOnClickListener(onProfileImageClickListener);

        tvPickPhoto = (TextView) findViewById(R.id.tv_pick_photo);

        btnContinue = (Button) findViewById(R.id.btn_continue);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserProfileActivity.this, NavigationDrawerActivity.class));
                finish();
            }
        });
    }

    View.OnClickListener onProfileImageClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ActionSheet.createBuilder(UserProfileActivity.this, getSupportFragmentManager())
                    .setCancelButtonTitle("Cancel")
                    .setOtherButtonTitles("Camera", "Library")
                    .setCancelableOnTouchOutside(true)
                    .setListener(UserProfileActivity.this).show();
        }
    };

    @Override
    void viewsWereInflated() {
    }

    @Override
    String title() {
        return "Profile setup";
    }

    @Override
    public void onDismiss(ActionSheet actionSheet, boolean b) {

    }

    @Override
    public void onOtherButtonClick(ActionSheet actionSheet, int i) {
         MediaActions.openMediaIntent(this, i);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case MediaActions.ACTION_IMAGE_CAPTURE:
                if (resultCode == Activity.RESULT_OK) {
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    imageView.setImageBitmap(bitmap);
                    tvPickPhoto.setVisibility(View.GONE);
                }
                break;
            case MediaActions.ACTION_IMAGE_SELECT:
                if (resultCode == Activity.RESULT_OK) {
                    String picturePath = MediaUtils.getMediaFilePath(this, data);
                    Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
                    imageView.setImageBitmap(bitmap);
                    tvPickPhoto.setVisibility(View.GONE);
                }
                break;
        }
    }
}
