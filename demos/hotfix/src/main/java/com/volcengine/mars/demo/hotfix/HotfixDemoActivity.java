package com.volcengine.mars.demo.hotfix;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bytedance.frankie.Frankie;
import com.bytedance.hotfix.base.patch.ModifyMark;
import com.bytedance.hotfix.base.patch.annotation.Modify;
import java.io.File;

public class HotfixDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotfix_demo);
        HotfixHelper.getInstance().init(this.getApplication());

        findViewById(R.id.btn_fetch_patch_from_net).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Frankie.getInstance().loadRemotePatch();
            }
        });

        findViewById(R.id.btn_fetch_patch_from_local).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateLocalPatch();
            }
        });

        findViewById(R.id.btn_java_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                javaTest();
            }
        });

        findViewById(R.id.btn_clear_patch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Frankie.getInstance().clearPatchForCrash();
            }
        });
    }

    /**
     * install patch from local file
     */
    private void updateLocalPatch() {
        ModifyMark.modify();
        File file = new File(getExternalFilesDir("patch"), "app.patch");
        if (!file.exists()) {
            Toast.makeText(this, "本地patch不存在(" + file.getAbsolutePath() + ")",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        Frankie.getInstance().updateFromLocal(file);
    }

    @Modify
    private void javaTest() {
        ModifyMark.modify();
        String desc = "text before fix";
        ((TextView) findViewById(R.id.tv_show)).setText(desc);
//        throw new RuntimeException("Simulate app crash");
    }
}
