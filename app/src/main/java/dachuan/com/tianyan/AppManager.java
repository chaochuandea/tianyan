package dachuan.com.tianyan;

import java.util.ArrayList;
import java.util.Stack;


import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

/**
 * @author linghong@xizi.com   
 * @date 2014-6-24 上午10:20:50
 * @description: 应用程序Activity管理类：用于Activity管理和应用程序退出
 * @version
 */
public class AppManager {

    private static Stack<Activity> activityStack;
    private static AppManager instance;

    private AppManager() {
    }

    /**
     * 单一实例
     */
    public static AppManager getAppManager() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
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
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
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
     * 结束所有Activity
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
     * 退出应用程序
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
     * 将指定Activity移除堆栈
     */
    public void removeActivityFromStack(Activity activity) {
        activityStack.remove(activity);
    }

    /**
     * 搜索栈中是否存在当前类名Activity
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
