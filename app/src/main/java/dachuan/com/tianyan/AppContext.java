package dachuan.com.tianyan;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.testin.agent.TestinAgent;

import dachuan.com.tianyan.util.log.FakeCrashLibrary;
import de.greenrobot.event.EventBus;
import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by maibenben on 2015/7/2.
 */
public class AppContext extends Application {

    private static EventBus bus;
   private static AppContext instance;
    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG){
            Timber.plant(new Timber.DebugTree());
        }else{
            Timber.plant(new CrashReportingTree());
        }
        bus = new EventBus();
        instance = this;
        TestinAgent.init(this, "764a9393a5053e48d6927c4877bb87c8", "");
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/RobotoCondensed-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }

    public static AppContext instance(){
        return instance;
    }

    public static EventBus getBus(){
        return bus;
    }

    /** A tree which logs important information for crash reporting. */
    private static class CrashReportingTree extends Timber.Tree {
        @Override protected void log(int priority, String tag, String message, Throwable t) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return;
            }
            FakeCrashLibrary.log(priority, tag, message);

            if (t != null) {
                if (priority == Log.ERROR) {
                    FakeCrashLibrary.logError(t);
                } else if (priority == Log.WARN) {
                    FakeCrashLibrary.logWarning(t);
                }
            }
        }
    }
}
