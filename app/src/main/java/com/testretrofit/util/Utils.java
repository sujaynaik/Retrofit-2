package com.testretrofit.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.DatePickerDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Point;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SealedObject;
import javax.crypto.spec.SecretKeySpec;

public class Utils {

    private static String TAG = Utils.class.getSimpleName();
    static Context context;

    public Utils(Context context) {
        Utils.context = context;
    }

    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        return width;
    }

    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = size.y;
        return height;
    }

    static int getAppVersion() {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    public void showToast(final String txt) {
        ((Activity) context).runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(context, txt, Toast.LENGTH_LONG).show();
            }
        });
    }

    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }

    public static void setListViewHeightBasedOnChildrenOld(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public static void logw(String TAG, String message) {
        if (Constant.DEBUG)
            Log.w(TAG, message);
    }

    public static void logi(String TAG, String message) {
        if (Constant.DEBUG)
            Log.i(TAG, message);
    }

    public static void loge(String TAG, String message) {
        if (Constant.DEBUG)
            Log.e(TAG, message);
    }

    public static void loge(String TAG, String message, Object value) {
        if (Constant.DEBUG)
            Log.e(TAG, message + " " + String.valueOf(value));
    }

    public static void logd(String TAG, String message) {
        if (Constant.DEBUG)
            Log.d(TAG, message);
    }

    public static void toast(Context context, String message) {
        LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflate.inflate(R.layout.toast, null);
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
//        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(view);
        toast.setText(message);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
//        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void toastAlert(Context context, String message) {
        LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflate.inflate(R.layout.toast, null);
        CardView cardView = (CardView) view.findViewById(R.id.layoutCard);
        cardView.setCardBackgroundColor(context.getResources().getColor(R.color.red));
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
//        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(view);
        toast.setText(message);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
//        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void toastLong(Context context, String message) {
        LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflate.inflate(R.layout.toast, null);
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
//        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(view);
        toast.setText(message);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
//        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static Boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
//        listView.setNestedScrollingEnabled(true);
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            Utils.logd(TAG, "listItem null");
            return;
        }

        int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();
        int adapterCount = listAdapter.getCount();
        for (int i = 0; i < adapterCount; i++) {
            View listItem = listAdapter.getView(i, null, listView);
            if (listItem instanceof ViewGroup) {
                listItem.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
            }

            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public static String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                /*while (h.length() < 2)
                    h = "0" + h;*/
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static int getPixelsFromDPs(Context context, int dps) {
        Resources r = context.getResources();
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dps, r.getDisplayMetrics()));
    }

    public static int getPixelsFromDP(Context context, int dps) {
        float d = context.getResources().getDisplayMetrics().density;
        int margin = (int) (dps * d); // margin in pixels
        return margin;
    }

    public static void hideKeyboard(Context context, View view) {
        if (view != null) {
            ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static String getMapsApiDirectionsUrl(LatLng origin, LatLng dest) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor;

        // Output format
        String output = "json";

        // Building the url to the web service

        return "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;

    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public static void updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources resources = context.getResources();

        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;

        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }

    public static void changeSetting(String language) {
        try {
            Class<?> activityManagerNative = Class.forName("android.app.ActivityManagerNative");
            Object am = activityManagerNative.getMethod("getDefault").invoke(activityManagerNative);
            Object config = am.getClass().getMethod("getConfiguration").invoke(am);
            config.getClass().getDeclaredField("locale").set(config, language);
            config.getClass().getDeclaredField("userSetLocale").setBoolean(config, true);

            am.getClass().getMethod("updateConfiguration", Configuration.class).invoke(am, config);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void hideKeyboard(Context context, View view) {
        if (view != null) {
            ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                /*while (h.length() < 2)
                    h = "0" + h;*/
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (byte b : data) {
            int halfbyte = (b >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                buf.append((0 <= halfbyte) && (halfbyte <= 9) ? (char) ('0' + halfbyte) : (char) ('a' + (halfbyte - 10)));
                halfbyte = b & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    public static String sha1(String text) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
            byte[] textBytes = text.getBytes("iso-8859-1");
            md.update(textBytes, 0, textBytes.length);
            byte[] sha1hash = md.digest();
            return convertToHex(sha1hash);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String toRoman(int number) {
        Utils.loge(TAG, "toRoman : " + number);
        // Gate
        if (number < 1 || number > 22) return "Number out of range...";

        // Body
        String result = "";
        String symbol1 = "I";
        String symbol2 = "V";
        String symbol3 = "X";
        result = result + toRoman(number, symbol1, symbol2, symbol3);

        return result;
    }

    private static String toRoman(int v, String symbol1, String symbol2, String symbol3) {
        String result = "";
        Utils.loge(TAG, "toRoman : " + v + ", " + symbol1 + ", " + symbol2 + ", " + symbol3);
        switch (v) {
            case 1:
                result = symbol1;
                break;

            case 2:
                result = symbol1 + symbol1;
                break;

            case 3:
                result = symbol1 + symbol1 + symbol1;
                break;

            case 4:
                result = symbol1 + symbol2;
                break;

            case 5:
                result = symbol2;
                break;

            case 6:
                result = symbol2 + symbol1;
                break;

            case 7:
                result = symbol2 + symbol1 + symbol1;
                break;

            case 8:
                result = symbol2 + symbol1 + symbol1 + symbol1;
                break;

            case 9:
                result = symbol1 + symbol3;
                break;

            case 10:
                result = symbol3;
                break;

            case 11:
                result = symbol3 + symbol1;
                break;

            case 12:
                result = symbol3 + symbol1 + symbol1;
                break;

            case 13:
                result = symbol3 + symbol1 + symbol1 + symbol1;
                break;

            case 14:
                result = symbol3 + symbol1 + symbol2;
                break;

            case 15:
                result = symbol3 + symbol2;
                break;

            case 16:
                result = symbol3 + symbol2 + symbol1;
                break;

            case 17:
                result = symbol3 + symbol2 + symbol1 + symbol1;
                break;

            case 18:
                result = symbol3 + symbol2 + symbol1 + symbol1 + symbol1;
                break;

            case 19:
                result = symbol3 + symbol1 + symbol3;
                break;

            case 20:
                result = symbol3 + symbol3;
                break;

            case 21:
                result = symbol3 + symbol3 + symbol1;
                break;

            case 22:
                result = symbol3 + symbol3 + symbol1 + symbol1;
                break;
        }
        return result;
    }

    public static DatePickerDialog createDialogWithoutDateField(Context context, DatePickerDialog.OnDateSetListener onDateSetListener) {
        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog dpd = new DatePickerDialog(context, onDateSetListener, newCalendar.get(Calendar.YEAR),
                newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        try {
            java.lang.reflect.Field[] datePickerDialogFields = dpd.getClass().getDeclaredFields();
            for (java.lang.reflect.Field datePickerDialogField : datePickerDialogFields) {
                if (datePickerDialogField.getName().equals("mDatePicker")) {
                    datePickerDialogField.setAccessible(true);
                    DatePicker datePicker = (DatePicker) datePickerDialogField.get(dpd);
                    java.lang.reflect.Field[] datePickerFields = datePickerDialogField.getType().getDeclaredFields();
                    for (java.lang.reflect.Field datePickerField : datePickerFields) {
                        Utils.loge(TAG, datePickerField.getName());
                        if ("mDaySpinner".equals(datePickerField.getName())) {
                            datePickerField.setAccessible(true);
                            Object dayPicker = datePickerField.get(datePicker);
                            ((View) dayPicker).setVisibility(View.GONE);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        dpd.show();
        return dpd;
    }

    public static int blendColors(int color1, int color2, float ratio) {
        final float inverseRation = 1f - ratio;
        float r = (Color.red(color1) * ratio) + (Color.red(color2) * inverseRation);
        float g = (Color.green(color1) * ratio) + (Color.green(color2) * inverseRation);
        float b = (Color.blue(color1) * ratio) + (Color.blue(color2) * inverseRation);
        return Color.rgb((int) r, (int) g, (int) b);
    }

    public static Bitmap checkOrientation(Context mContext, Bitmap pImage, Uri uriPath) {
        //Utils.logd("ExifInteface .........", "check orientation");
        try {
            String[] projection = {MediaStore.Images.Media.DATA};
            CursorLoader loader = new CursorLoader(mContext, uriPath, projection, null, null, null);
            Cursor cursor = loader.loadInBackground();

            int column_index_data = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();

            // Rotation is stored in an EXIF tag, and this tag seems to return 0 for URIs.
            // Hence, we retrieve it using an absolute path instead!
            int rotation = 0;
            String realPath = cursor.getString(column_index_data);
            if (realPath != null) {

                try {
                    ExifInterface exif = new ExifInterface(realPath);
                    rotation = (exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL));


                    //Utils.logd("ExifInteface .........", "rotation =" + rotation);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                pImage = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), uriPath);

                return rotateBitmap(pImage, rotation, new File(realPath));

            }

            // Now we can load the bitmap from the Uri, using the correct rotation.
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Bitmap rotateBitmap(Bitmap bmRotated, int orientation, File file) {

        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                break;
            //return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:

                break;
        }
        try {
            bmRotated = Bitmap.createBitmap(bmRotated, 0, 0, bmRotated.getWidth(), bmRotated.getHeight(), matrix, true);
            return bmRotated;

        } catch (OutOfMemoryError e) {


            e.printStackTrace();
        }
        //Utils.loge("image ", " image out of memory");
        return null;
    }

    public static String saveToExternalStorage(Context context, Bitmap profileImage, String picName) {
        String root = Environment
                .getExternalStorageDirectory()
                + "/Freight" + "/ProfilePictures/";
        File myDir = new File(root);
        myDir.mkdirs();

        File file = new File(myDir, picName + ".jpg");

        FileOutputStream fo;
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        try {

            file.createNewFile();

            fo = new FileOutputStream(file);
            profileImage.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file.getPath();
    }

    public static String saveBitmapInFile(Context context, File file, Bitmap profileImage) {
        FileOutputStream fo;
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        try {
            file.createNewFile();
            fo = new FileOutputStream(file);
            profileImage.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file.getPath();
    }

    public static void performCrop(Context mContext, File crop_file, int request_code) {
        try {
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            // indicate image type and Uri
            Uri contentUri;
            if (android.os.Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
                contentUri = Uri.fromFile(crop_file);
            } else {
                contentUri = FileProvider.getUriForFile(mContext, mContext.getString(R.string.file_provider_authority), crop_file);
                mContext.grantUriPermission("com.android.camera", contentUri,
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }

            Utils.logd(TAG, "crop contentUri : " + contentUri);

            cropIntent.setDataAndType(contentUri, "image/*");

            if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                //Android N need set permission to uri otherwise system camera don't has permission to access file wait crop
                cropIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                cropIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                cropIntent.putExtra("outputFormat", "JPEG");
                cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
            }
            // set crop properties
            cropIntent.putExtra("crop", "true");
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            // indicate output X and Y
            cropIntent.putExtra("outputX", 280);
            cropIntent.putExtra("outputY", 280);

            // retrieve data on return
            cropIntent.putExtra("return-data", true);

            // start the activity - we handle returning in onActivityResult
            mContext.startActivityForResult(cropIntent, request_code);

        } catch (ActivityNotFoundException anfe) {
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            Utils.loge(TAG, "crop : " + errorMessage);
            Utils.toastAlert(mContext, errorMessage);
        }
    }

    public static boolean isValidEmail(CharSequence email) {
        return email != null && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static String getInitials(String name) {
        String firstName = "";
        if (name.split("\\w+").length > 1) {
            firstName = name.substring(0, name.lastIndexOf(' '));
        } else {
            firstName = name;
        }
        return firstName;
    }

    public static void hideSoftKeyboard(Context activity, View view) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getRootView().getWindowToken(), 0);
//        getWindow().getDecorView().getRootView().getWindowToken()
//        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    /**
     * DATE-TIME
     */
    public static String createTimestamp(Date date) {
        long date_timestamp = date.getTime();
//        Utils.logd(TAG, "date_timestamp : " + date_timestamp);
        return String.valueOf(date_timestamp);
    }

    public static Date getDateFromTimestamp(String timestamp) {
        Date date = null;
        try {
            long time;
            if (timestamp.length() == 13) {
                time = Long.parseLong(timestamp);

            } else {
                time = Long.parseLong(timestamp) * 1000;
            }

            date = new Date(time);
//            Utils.logd(TAG, "date : " + date);

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String formatDateTime(Date date, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        return dateFormat.format(date);
    }

    public static Date formatStringDate(String date, String pattern) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        return dateFormat.parse(date);
    }

    public static int compareDates(String str_date1, String str_date2) {
        Utils.logd(TAG, "str_date1 : " + str_date1);
        Utils.logd(TAG, "str_date2 : " + str_date2);
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(Constant.DATE_FORMAT_6, Locale.ENGLISH);

            Date date1 = formatter.parse(str_date1);
            Date date2 = formatter.parse(str_date2);

            if (date1.compareTo(date2) < 0) {
                Utils.logd(TAG, "date2 is Greater than my date1");

            } else if (date1.compareTo(date2) > 0) {
                Utils.logd(TAG, "date1 is Greater than my date2");

            } else {
                Utils.logd(TAG, "date1 is equal to date2");
            }

            return date1.compareTo(date2);
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        return 2;
    }

    public static String getDateTime(String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static void pickupTime(Context context, final View view) {
        final Calendar myCalendar = Calendar.getInstance();
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                String min = String.valueOf(minute);
//                Utils.logd(TAG, "min : " + min + ", length : " + min.length());
                if (min.length() == 1) {
                    min = "0" + min;
                }

                if (view instanceof EditText) {
                    ((EditText) view).setText(hourOfDay + ":" + min);

                } else if (view instanceof TextView) {
                    ((TextView) view).setText(hourOfDay + ":" + min);
                }
            }
        };

        new TimePickerDialog(context, onTimeSetListener, myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE),
                DateFormat.is24HourFormat(context)).show();
    }

    public static void logout(Context context) {
        MyPreferences.clearPreferences(context);
        /*Intent intent = new Intent(context, SignupActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);*/
    }

    public static void createCircularReveal(LinearLayout mainLayout, View view) {//view - previously visiblity = gone
        if (!Constant.DEBUG_ANIM) {
            view.setVisibility(View.VISIBLE);
            return;
        }
        // get the center for the clipping circle
        int cx = mainLayout.getWidth() / 2;
        int cy = mainLayout.getHeight() / 2;

        // get the final radius for the clipping circle
        float finalRadius = (float) Math.hypot(cx, cy);

        // create the animator for this view (the start radius is zero)
        Animator anim = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);
        }

        // make the view visible and start the animation
        view.setVisibility(View.VISIBLE);

        if (anim != null) {
            anim.start();
        }
    }

    public static void createCircularReveal(View view) {// previously invisible view
        if (!Constant.DEBUG_ANIM) {
            view.setVisibility(View.VISIBLE);
            return;
        }
        // get the center for the clipping circle
        int cx = view.getWidth() / 2;
        int cy = view.getHeight()/* / 2*/;

        // get the final radius for the clipping circle
        float finalRadius = (float) Math.hypot(cx, cy);
        float startRadius = finalRadius / 2; //0

        // create the animator for this view (the start radius is zero)
        Animator anim = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, startRadius, finalRadius);
        }

        // make the view visible and start the animation
        view.setVisibility(View.VISIBLE);

        if (anim != null) {
            anim.start();
        }
    }

    public static void createCircularReveal2(View view) {// previously invisible view
        if (!Constant.DEBUG_ANIM) {
            view.setVisibility(View.VISIBLE);
            return;
        }
        // get the center for the clipping circle
        int cx = view.getWidth() / 2;
        int cy = view.getHeight() / 2;

        // get the final radius for the clipping circle
        float finalRadius = (float) Math.hypot(cx, cy);
        float startRadius = 0; //0

        // create the animator for this view (the start radius is zero)
        Animator anim = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, startRadius, finalRadius);
        }

        // make the view visible and start the animation
        view.setVisibility(View.VISIBLE);

        if (anim != null) {
            anim.start();
        }
    }

    public static void hideCircularReveal2(final View view) {// previously visible view
        if (!Constant.DEBUG_ANIM) {
            return;
        }
        // get the center for the clipping circle
        int cx = view.getWidth() / 2;
        int cy = view.getHeight();

        // get the final radius for the clipping circle
        float finalRadius = (float) Math.hypot(cx, cy);

        // create the animator for this view (the start radius is zero)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);
            anim.setDuration(500);
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    view.setVisibility(View.GONE);
                    animation.removeAllListeners();
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    super.onAnimationCancel(animation);
                    view.setVisibility(View.GONE);
                    animation.removeAllListeners();
                }

                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                }
            });
            // start the animation
            anim.start();

        } else {
            view.setVisibility(View.GONE);
        }
    }

    public static void hideCircularReveal(final View view) {// previously visible view
        if (!Constant.DEBUG_ANIM) {
            return;
        }
        // get the center for the clipping circle
        int cx = view.getWidth() / 2;
        int cy = view.getHeight() / 2;

        // get the initial radius for the clipping circle
        float initialRadius = (float) Math.hypot(cx, cy);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            // create the animation (the final radius is zero)
            Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, initialRadius, 0);

            // make the view invisible when the animation is done
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    view.setVisibility(View.INVISIBLE);
                }
            });

            // start the animation
            anim.start();
        } else {
            view.setVisibility(View.INVISIBLE);
        }
    }

    public static synchronized void animate(final Animation animation, final View view, final int duration) {
        if (!Constant.DEBUG_ANIM) {
            view.setVisibility(View.VISIBLE);
            return;
        }
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                animation.setDuration(duration);
                animation.setInterpolator(new AnticipateOvershootInterpolator());//AccelerateDecelerateInterpolator
                animation.setFillEnabled(true);
                animation.setFillAfter(true);
                animation.setStartOffset(0);
                animation.reset();
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        view.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                view.startAnimation(animation);
            }
        };
        handler.postDelayed(runnable, 80);
    }

    public static synchronized void animate(final Animation animation, final View view, final int duration, long delayMillis) {
        if (!Constant.DEBUG_ANIM) {
            view.setVisibility(View.VISIBLE);
            return;
        }
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                animation.setDuration(duration);
                animation.setInterpolator(new AnticipateOvershootInterpolator());//AccelerateDecelerateInterpolator
                animation.setFillEnabled(true);
                animation.setFillAfter(true);
                animation.setStartOffset(0);
                animation.reset();
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        view.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                view.startAnimation(animation);
            }
        };
        handler.postDelayed(runnable, delayMillis);
    }

    public static synchronized void animate(final Animation animation, final View view, final View parent_view,
                                            final int duration, long delayMillis) {
        if (!Constant.DEBUG_ANIM) {
            view.setVisibility(View.VISIBLE);
            parent_view.setVisibility(View.VISIBLE);
            return;
        }
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                animation.setDuration(duration);
                animation.setInterpolator(new AnticipateOvershootInterpolator());//AccelerateDecelerateInterpolator
                animation.setFillEnabled(true);
                animation.setFillAfter(true);
                animation.setStartOffset(0);
                animation.reset();
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        view.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        if (parent_view instanceof CardView) {
                            Utils.createCircularReveal2(parent_view);
                        } else {
                            Utils.createCircularReveal(parent_view);
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                view.startAnimation(animation);
            }
        };
        handler.postDelayed(runnable, delayMillis);
    }

    public static synchronized void animateGone(final Animation animation, final View view, final int duration) {
        if (!Constant.DEBUG_ANIM) {
            view.setVisibility(View.GONE);
            return;
        }
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                animation.setDuration(duration);
                animation.setInterpolator(new AnticipateOvershootInterpolator());//AccelerateDecelerateInterpolator
                animation.setFillEnabled(true);
                animation.setFillAfter(true);
                animation.setStartOffset(0);
                animation.reset();
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        view.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                view.startAnimation(animation);
            }
        };
        handler.postDelayed(runnable, 80);
    }

    public static synchronized void animateGone(final Animation animation, final View view, final int duration, long delayMillis) {
        if (!Constant.DEBUG_ANIM) {
            view.setVisibility(View.GONE);
            return;
        }
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                animation.setDuration(duration);
                animation.setInterpolator(new AnticipateOvershootInterpolator());//AccelerateDecelerateInterpolator
                animation.setFillEnabled(true);
                animation.setFillAfter(true);
                animation.setStartOffset(0);
                animation.reset();
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        view.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                view.startAnimation(animation);
            }
        };
        handler.postDelayed(runnable, delayMillis);
    }

    public static InputFilter setTextFilter() {
        return new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
//                Utils.logd(TAG, "source : " + source);
//                Utils.logd(TAG, "start : " + start);
//                Utils.logd(TAG, "end : " + end);
                if (source != null && source.length() > 0) {
                    if (!source.toString().equals(" ")) {
                        for (int i = start; i < end; i++) {
//                            Utils.logd(TAG, "i : " + i);
                            int type = Character.getType(source.charAt(i));

                            if (type == Character.SURROGATE) {
                                return source.subSequence(start, i);
                            }
                            if (!Character.isLetter(source.charAt(i))) {
                                return source.subSequence(start, i);
                            }
                        }
                    }
                }
                return null;
            }
        };
    }

    public static InputFilter setPhoneFilter() {
        return new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
//                Utils.logd(TAG, "source : " + source);
//                Utils.logd(TAG, "start : " + start);
//                Utils.logd(TAG, "end : " + end);
                if (source != null && source.length() > 0) {
                    for (int i = start; i < end; i++) {
//                        Utils.logd(TAG, "i : " + i);
                        if (Character.isDigit(source.charAt(i)) || String.valueOf(source.charAt(i)).equals("+")) {
                        } else {
                            return source.subSequence(start, i);
                        }
                        if (source.length() > 13) {
                            return source.subSequence(start, i);
                        }
                    }
                }
                return null;
            }
        };
    }
    
}
