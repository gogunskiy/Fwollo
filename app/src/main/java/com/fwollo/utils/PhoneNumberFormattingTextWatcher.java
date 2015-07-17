package com.fwollo.utils;

import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;

import java.util.Locale;

public class PhoneNumberFormattingTextWatcher implements TextWatcher {

    static private int sFormatType;
    static private Locale sCachedLocale;
    private boolean mFormatting;
    private boolean mDeletingHyphen;
    private int mHyphenStart;
    private boolean mDeletingBackward;


    public PhoneNumberFormattingTextWatcher(String countryCode) {
        sCachedLocale = new Locale("en", countryCode);
        sFormatType = PhoneNumberUtils.getFormatTypeForLocale(sCachedLocale);
    }

    public PhoneNumberFormattingTextWatcher() {
        if (sCachedLocale == null || sCachedLocale != Locale.getDefault()) {
            sCachedLocale = Locale.getDefault();
            sFormatType = PhoneNumberUtils.getFormatTypeForLocale(sCachedLocale);
        }
    }

    public synchronized void afterTextChanged(Editable text) {
        // Make sure to ignore calls to afterTextChanged caused by the work done below
        if (!mFormatting) {
            mFormatting = true;

            // If deleting the hyphen, also delete the char before or after that
            if (mDeletingHyphen && mHyphenStart > 0) {
                if (mDeletingBackward) {
                    if (mHyphenStart - 1 < text.length()) {
                        text.delete(mHyphenStart - 1, mHyphenStart);
                    }
                } else if (mHyphenStart < text.length()) {
                    text.delete(mHyphenStart, mHyphenStart + 1);
                }
            }

            PhoneNumberUtils.formatNumber(text, sFormatType);

            mFormatting = false;
        }
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // Check if the user is deleting a hyphen
        if (!mFormatting) {
            // Make sure user is deleting one char, without a selection
            final int selStart = Selection.getSelectionStart(s);
            final int selEnd = Selection.getSelectionEnd(s);
            if (s.length() > 1 // Can delete another character
                    && count == 1 // Deleting only one character
                    && after == 0 // Deleting
                    && s.charAt(start) == '-' // a hyphen
                    && selStart == selEnd) { // no selection
                mDeletingHyphen = true;
                mHyphenStart = start;
                // Check if the user is deleting forward or backward
                if (selStart == start + 1) {
                    mDeletingBackward = true;
                } else {
                    mDeletingBackward = false;
                }
            } else {
                mDeletingHyphen = false;
            }
        }
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // Does nothing
    }
}