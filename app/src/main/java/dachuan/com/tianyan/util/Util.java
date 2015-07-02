package dachuan.com.tianyan.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dachuan.com.tianyan.AppContext;


/**
 * @author linghong@xizi.com
 * @date 2014-7-2 下午05:05:31
 * @description: 公用静态方法
 */
public class Util {

    private static long lastClickTime;

    /**
     * @param context
     * @return int
     * @description: 获取屏幕宽
     */
    public static int screenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * @param context
     * @return int
     * @description: 获取屏幕高
     */
    public static int screenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * @return boolean
     * @descirption 判断控件点击事件的触发事件间隔，防止用户多次点击
     */
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 800) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * @param before
     * @return String
     * @description: 获取md5加密后的字符串
     */
    public static String getMD5String(String before) {
        MD5 md5 = new MD5();
        String after = md5.getCode(before);
        return after;
    }

    /**
     * @param s
     * @return String
     * @description: 截取md5加密后的字符串的16位
     */
    public static String getSubMD5String(String s) {
        if (StringUtils.isEmpty(getMD5String(s))) {
            return null;
        }
        return getMD5String(s).substring(6, 22);
    }

    /**
     * @param id
     * @return 根据config.xml里面获取值
     */
    public static boolean getBooleanFromConfig(int id) {
        try {
            String string = AppContext.instance().getResources().getString(id);
            return Boolean.valueOf(string);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public static String getStringFromConfig(int id) {
        try {
            String string = AppContext.instance().getResources().getString(id);
            return string;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }



    /**
     * @param chars
     * @param length
     * @return String
     * @description: 获得随机字符串random，可控制生成长度
     */
    public static String getRandomString(String chars, int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }


    /**
     * @param randomString 未经过AES加密的random字符串
     * @return String
     * @throws Exception
     * @description: 生成所需的xzkey，经过MD5加密
     */
    public static String getXZKey(String appKey, String randomString) {
        return getMD5String(appKey + randomString);
    }

    /**
     * @return String
     * @description: 获取设备唯一标识
     */
    public static String getDeviceId() {
        TelephonyManager telephonyManager = (TelephonyManager) AppContext.instance()
                .getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * @param context
     * @param dpValue
     * @return int
     * @description: 单位转换：dp转px
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * @param str
     * @param compile
     * @return Matcher
     * @description: 正则匹配
     */
    public static Matcher pattern(String str, String compile) {
        Pattern pattern = Pattern.compile(compile);

        return pattern.matcher(str);
    }

    /**
     * 去除一个字符串中的数据
     *
     * @param source
     * @param orther
     * @return Create at 2014-8-7 下午04:24:25
     */
    public static String splitString(String source, String... orther) {

        for (int i = 0; i < orther.length; i++) {
            source = source.replace(orther[i], "");
        }

        return source;
    }

    public static String getResuorceString(Context context, int id) {
        return context.getResources().getString(id);
    }




    /**
     * 加载本地图片
     *
     * @param url
     * @return
     */
    public static Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis); // /把流转化为Bitmap图片

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 复制文件
     *
     * @param sourceLocation
     * @param targetLocation
     * @throws IOException Create at 2014-8-21 上午10:47:55
     */
    public static void copyDirectory(File sourceLocation, File targetLocation)
            throws IOException {
        if (sourceLocation.isDirectory()) {
            if (!targetLocation.exists()) {
                targetLocation.mkdir();
            }

            String[] children = sourceLocation.list();
            for (int i = 0; i < sourceLocation.listFiles().length; i++) {

                copyDirectory(new File(sourceLocation, children[i]), new File(
                        targetLocation, children[i]));
            }
        } else {

            InputStream in = new FileInputStream(sourceLocation);

            OutputStream out = new FileOutputStream(targetLocation);

            // Copy the bits from instream to outstream
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        }

    }

    /**
     * 删除指定文件夹下所有文件 param path 文件夹完整绝对路径
     */
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件

                File myFilePath = new File(path + "/" + tempList[i]);
                myFilePath.delete(); // 再删除空文件夹

                flag = true;
            }
        }
        return flag;
    }

    /**
     * 设置margin
     *
     * @param v
     * @param l
     * @param t
     * @param r
     * @param b Create at 2014-9-1 下午03:36:11
     */
    public static void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v
                    .getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    /**
     * @param is
     * @return
     * @throws IOException
     * @Title: Inputstream2String
     * @author zhenglinfa
     * @Description: Inputstream转换成String, 不转换编码格式
     */
    public static String inputstream2String(InputStream is) throws IOException {
        if (is != null) {
            int BUFFER_SIZE = 4096;
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] data = new byte[BUFFER_SIZE];
            int count = -1;
            while ((count = is.read(data, 0, BUFFER_SIZE)) != -1) {
                outStream.write(data, 0, count);
            }
            data = null;
            String outString = new String(outStream.toByteArray());
            outStream.close();

            return outString;
        }

        return null;
    }

    public static void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * @return boolean
     * @description: 判断两个long型的时间是否为同一天
     */
    @SuppressLint("SimpleDateFormat")
    public static boolean isTheSameDay(long lastTime, long currenTime) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String last_time = sf.format(lastTime);
        String current_time = sf.format(currenTime);
        return last_time.equals(current_time);
    }

    public static boolean isFastMobileNetwork(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        switch (telephonyManager.getNetworkType()) {
            case TelephonyManager.NETWORK_TYPE_1xRTT:
                return false; // ~ 50-100 kbps
            case TelephonyManager.NETWORK_TYPE_CDMA:
                return false; // ~ 14-64 kbps
            case TelephonyManager.NETWORK_TYPE_EDGE:
                return false; // ~ 50-100 kbps
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                return true; // ~ 400-1000 kbps
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                return true; // ~ 600-1400 kbps
            case TelephonyManager.NETWORK_TYPE_GPRS:
                return false; // ~ 100 kbps
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                return true; // ~ 2-14 Mbps
            case TelephonyManager.NETWORK_TYPE_HSPA:
                return true; // ~ 700-1700 kbps
            case TelephonyManager.NETWORK_TYPE_HSUPA:
                return true; // ~ 1-23 Mbps
            case TelephonyManager.NETWORK_TYPE_UMTS:
                return true; // ~ 400-7000 kbps
            case TelephonyManager.NETWORK_TYPE_EHRPD:
                return true; // ~ 1-2 Mbps
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
                return true; // ~ 5 Mbps
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return true; // ~ 10-20 Mbps
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return false; // ~25 kbps
            case TelephonyManager.NETWORK_TYPE_LTE:
                return true; // ~ 10+ Mbps
            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                return false;
            default:
                return false;
        }
    }


    /**
     * 判断字符串是否为整数
     *
     * @param str
     * @return boolean
     */
    public static boolean isNumber(String str) {
        return Pattern.compile("^[-\\+]?[\\d]+$").matcher(str).matches();
    }

    public static boolean isURL(String url) {
        if (StringUtils.isEmpty(url)) {
            return false;
        } else {
            return url.startsWith("http://");
        }
    }


    /**
     * 获取设备Imei
     */
    public static int getImei(Context context) {
        return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId().hashCode();
    }

}



