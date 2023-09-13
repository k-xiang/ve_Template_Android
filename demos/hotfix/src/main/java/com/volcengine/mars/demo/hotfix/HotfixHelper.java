package com.volcengine.mars.demo.hotfix;

import android.app.Application;
import androidx.annotation.NonNull;
import com.bytedance.frankie.Frankie;
import com.bytedance.frankie.FrankieConfigExternalAdapter;

public class HotfixHelper {

    /**
     * channel description, you can choose specific channel
     * in patch publish service
     */
    private static final String CHANNEL = "local_test";

    private static volatile HotfixHelper sInstance = null;

    public static HotfixHelper getInstance() {
        if (sInstance == null) {
            synchronized (HotfixHelper.class) {
                if (sInstance == null) {
                    sInstance = new HotfixHelper();
                }
            }
        }
        return sInstance;
    }

    public void init(@NonNull Application application) {
        Frankie.getInstance().init(new DemoFrankieConfig(application));
    }

    /**
     * need to create a config class extends {@link FrankieConfigExternalAdapter},
     * and provide some necessary info
     */
    private static final class DemoFrankieConfig extends FrankieConfigExternalAdapter {



        public DemoFrankieConfig(@NonNull Application application) {
            super(application);
        }

        @Override
        public String getChannel() {
            return CHANNEL;
        }

        @Override
        public boolean isMainProcess() {
            return true;
        }
    }

}
