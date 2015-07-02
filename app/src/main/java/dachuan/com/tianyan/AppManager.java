package dachuan.com.tianyan;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.ArrayList;
import java.util.Stack;


import java.util.ArrayList;
import java.util.Stack;


import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

/**
 * @author linghong@xizi.com
 * @date 2014-6-24 ����10:20:50
 * @description: Ӧ�ó���Activity�����ࣺ����Activity�����Ӧ�ó����˳�
 * @version
 */
public class AppManager {

    private static Stack<Activity> activityStack;
    private static AppManager instance;

    private AppManager() {
    }

    /**
     * ��һʵ��
     */
    public static AppManager getAppManager() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    /**
     * ���Activity����ջ
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }
    public boolean hasActivity(Class<?> activityclass){
        for (Activity activity : activityStack) {
            if (activity.getClass().getName().equals(activityclass.getName())) {
                return true;
            }
        }
        return false;
    }
    /**
     * ��ȡ��ǰActivity����ջ�����һ��ѹ��ģ�
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * ������ǰActivity����ջ�����һ��ѹ��ģ�
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * ����ָ����Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * ����ָ��������Activity
     */
    public void finishActivity(Class<?> cls) {
        ArrayList<Activity> delList = new ArrayList<Activity>();
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                delList.add(activity);
            }
        }
        for(Activity activity : delList){
            finishActivity(activity);
        }
    }

    /**
     * ��������Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            try {
                if (null != activityStack.get(i)) {
                    activityStack.get(i).finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        activityStack.clear();
    }

    /**
     * �˳�Ӧ�ó���
     */
    @SuppressWarnings("deprecation")
    public void AppExit(Context context) {
        try {
            finishAllActivity();
            ActivityManager activityMgr = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.restartPackage(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
        }
    }

    /**
     * ��ָ��Activity�Ƴ���ջ
     */
    public void removeActivityFromStack(Activity activity) {
        activityStack.remove(activity);
    }

    /**
     * ����ջ���Ƿ���ڵ�ǰ����Activity
     */
    public boolean contains(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                return true;
            }
        }
        return false;
    }

}

