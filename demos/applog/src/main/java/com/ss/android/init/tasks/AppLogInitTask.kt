package com.ss.android.init.tasks

import android.content.Context
import com.bytedance.applog.AppLog
import com.bytedance.applog.InitConfig
import com.bytedance.lego.init.annotation.InitTask
import com.bytedance.lego.init.model.IInitTask
import com.bytedance.lego.init.model.InitPeriod
import com.volcengine.onekit.OneKitApp
import com.volcengine.onekit.service.AppInfo

@InitTask(
    id = "AppLogInitTask",
    desc = "applog初始化",
    earliestPeriod = InitPeriod.APP_ONCREATE2SUPER,
    latestPeriod = InitPeriod.APP_ONCREATE2SUPER
)
class AppLogInitTask : IInitTask() {

    override fun run() {
        val appInfo = OneKitApp.getInstance().get(AppInfo::class.java)
        val context = OneKitApp.getInstance().get(Context::class.java)
        val aid = "164314"
        val channel = appInfo.channel
        AppLog.init(context, InitConfig(aid, channel))
    }
}