package com.fwollo.utils;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * File system helper methods
 */
public final class FileUtils {

    /**
     * Check sdcard mounted
     */
    public static boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }

    public static File getExternalStorage() {
        return Environment.getExternalStorageDirectory();
    }

    public static String getStringFromAsset(String asset, Context context) {
        try {
            InputStream is = context.getApplicationContext().getAssets().open(asset);
            return readInputStream(is);
        } catch (IOException ex) {
            return null;
        }
    }

    public static String readInputStream(InputStream stream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        String line = null;
        StringBuilder stringBuilder = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        return stringBuilder.toString();
    }

    public static String readFromFile(String fileName,  Context ctx) {
        InputStream in = null;
        try {
            in = ctx.openFileInput(fileName);

            return readInputStream(in);
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            return null;
        }           finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {

                }
            }
        }
    }

    public static boolean writeToFile(String filename, String content, Context ctx) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = ctx.openFileOutput(filename, Context.MODE_PRIVATE);
            byte[] bytes = content.getBytes();
            fileOutputStream.write(bytes,0, bytes.length);
            return true;
        } catch (FileNotFoundException e) {
            return false;
        }catch(IOException e){
            return false;
        }    finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {

                }
            }
        }
    }

    public static boolean removeFile(Context context, String fileName) {
        File file = new File(context.getFilesDir() + "/" + fileName);
        boolean isDeleted = file.delete();
        return isDeleted;
    }

    private FileUtils() {
    }
}
