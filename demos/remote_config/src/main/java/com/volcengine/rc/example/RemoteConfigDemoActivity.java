package com.volcengine.rc.example;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.volcengine.rc.example.pages.ChildProcessActivity;
import com.volcengine.rc.example.pages.GetAPITest;
import com.volcengine.rc.test.VERCTestActivity;

public class RemoteConfigDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rc_activity_main);

        findViewById(R.id.config_publish_test).setOnClickListener(clickListener);
        findViewById(R.id.api_call_test).setOnClickListener(clickListener);
        findViewById(R.id.child_process_call_test).setOnClickListener(clickListener);
    }

    private View.OnClickListener clickListener = v -> {
        int id = v.getId();
        if (id == R.id.config_publish_test) {
            jumpPage(VERCTestActivity.class);
        } else if (id == R.id.api_call_test) {
            jumpPage(GetAPITest.class);
        } else if (id == R.id.child_process_call_test) {
            jumpPage(ChildProcessActivity.class);
        }
    };

    private void jumpPage(Class jumpClass) {
        final Intent intent = new Intent(RemoteConfigDemoActivity.this, jumpClass);
        startActivity(intent);
    }
}