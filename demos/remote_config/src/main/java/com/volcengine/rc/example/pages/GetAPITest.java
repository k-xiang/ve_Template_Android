package com.volcengine.rc.example.pages;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.volcengine.rc.VEConfigManager;
import com.volcengine.rc.VEConfigManager.IFetchCallback;
import com.volcengine.rc.example.R;

public class GetAPITest extends AppCompatActivity {
    final static String TAG = "GET_API_TEST";

    private String keyInputValue;
    private String defaultInputValue;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rc_activity_get_api);
        handleClick();
    }

    private void handleClick() {
        findViewById(R.id.fetch).setOnClickListener(clickListener);
        findViewById(R.id.fetch_force).setOnClickListener(clickListener);
        findViewById(R.id.get).setOnClickListener(clickListener);
        findViewById(R.id.get_bool).setOnClickListener(clickListener);
        findViewById(R.id.get_int).setOnClickListener(clickListener);
        findViewById(R.id.get_float).setOnClickListener(clickListener);
        findViewById(R.id.get_string).setOnClickListener(clickListener);
    }

    private View.OnClickListener clickListener = v -> {
        int id = v.getId();
        if (id == R.id.fetch) {
            handleFetch(false);
        } else if (id == R.id.fetch_force) {
            handleFetch(true);
        } else if (id == R.id.get) {
            get();
        } else if (id == R.id.get_bool) {
            getBoolean();
        } else if (id == R.id.get_int) {
            getInt();
        } else if (id == R.id.get_float) {
            getFloat();
        } else if (id == R.id.get_string) {
            getString();
        }
    };

    private void handleFetch(boolean force) {
        final IFetchCallback callback = new IFetchCallback() {
            @Override
            public void onSuccess() {
                runOnUiThread(() -> {
                    Toast.makeText(GetAPITest.this, "fetch success, please see detail in log", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, VEConfigManager.getInstance().getAllConifgs().toString());
                });
            }

            @Override
            public void onError(int errorCode, String errorMessage) {
                runOnUiThread(() -> Toast.makeText(GetAPITest.this, "fetch config error. errorCode=" + errorCode + ", errorMessage=" + errorMessage, Toast.LENGTH_SHORT).show());
            }
        };

        if (force) {
            VEConfigManager.getInstance().fetchForce(callback);
        } else {
            VEConfigManager.getInstance().fetch(callback);
        }

    }

    private void get() {
        showDialog(false, () -> {
            final Object value = VEConfigManager.getInstance().get(keyInputValue);
            if (value != null) {
                Toast.makeText(this, "get success, type:" + value.getClass().getSimpleName() + ",value:" + value.toString(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "get success, value is null", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getBoolean() {
        showDialog(true, () -> {
            final boolean value;
            value = (null == defaultInputValue) ? VEConfigManager.getInstance().getBoolean(keyInputValue) : VEConfigManager.getInstance().getBoolean(keyInputValue, Boolean.parseBoolean(defaultInputValue));
            Toast.makeText(this, "getBoolean success, value:" + value, Toast.LENGTH_SHORT).show();
        });
    }

    private void getInt() {
        showDialog(true, () -> {
            final int value;
            value = (null == defaultInputValue) ? VEConfigManager.getInstance().getInt(keyInputValue) : VEConfigManager.getInstance().getInt(keyInputValue, Integer.parseInt(defaultInputValue));
            Toast.makeText(this, "getInt success, value:" + value, Toast.LENGTH_SHORT).show();
        });
    }

    private void getFloat() {
        showDialog(true, () -> {
            final float value;
            value = (null == defaultInputValue) ? VEConfigManager.getInstance().getFloat(keyInputValue) : VEConfigManager.getInstance().getFloat(keyInputValue, Float.parseFloat(defaultInputValue));
            Toast.makeText(this, "getFloat success, value:" + value, Toast.LENGTH_SHORT).show();
        });
    }

    private void getString() {
        showDialog(true, () -> {
            final String value;
            value = (null == defaultInputValue) ? VEConfigManager.getInstance().getString(keyInputValue) : VEConfigManager.getInstance().getString(keyInputValue, defaultInputValue);
            if (value != null) {
                Toast.makeText(this, "getString success, value:" + value, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "getString success, value is null", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDialog(boolean showDefaultInput, Runnable doGet) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.rc_dialog_input);
        if (showDefaultInput) {
            dialog.findViewById(R.id.default_layout).setVisibility(View.VISIBLE);
        }
        Button btnConfirm = dialog.findViewById(R.id.dialog_confirm);
        btnConfirm.setOnClickListener(v -> {
            dialog.dismiss();

            final EditText keyInput = dialog.findViewById(R.id.key_input);
            keyInputValue = keyInput.getText().toString();
            if (showDefaultInput) {
                final EditText defaultInput = dialog.findViewById(R.id.default_input);
                defaultInputValue = defaultInput.getText().toString();
                if (TextUtils.isEmpty(defaultInputValue)) {
                    defaultInputValue = null;
                }
            }

            doGet.run();
        });

        dialog.show();
    }
}
