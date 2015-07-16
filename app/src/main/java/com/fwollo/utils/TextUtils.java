package com.fwollo.utils;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

public class TextUtils {

    public static void setHighlightedText(TextView textView, String text, String linkPart, final View.OnClickListener onClickListener)  {
        SpannableString ss = new SpannableString(text);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                onClickListener.onClick(textView);
            }
        };

        int i = text.indexOf(linkPart);

        ss.setSpan(clickableSpan, i, i + linkPart.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new UnderlineSpan(),  i, i + linkPart.length(), 0);

        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setText(ss);
    }
 }
