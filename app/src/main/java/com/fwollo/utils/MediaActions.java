
package com.fwollo.utils;

import android.app.Activity;
import android.content.Intent;
import android.provider.MediaStore;
import android.view.View;

/**
 * Created by vgogunsky on 4/6/15.
 */
public class MediaActions {

    public static final int ACTION_IMAGE_CAPTURE = 0;
    public static final int ACTION_IMAGE_SELECT = 1;

    public static void openMediaIntent(final Activity activity, final int action) {
        switch (action) {
            case ACTION_IMAGE_CAPTURE: {
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                activity.startActivityForResult(takePicture, action);
            }
            break;

            case ACTION_IMAGE_SELECT: {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activity.startActivityForResult(pickPhoto, ACTION_IMAGE_SELECT);
            }
        }
    }
}