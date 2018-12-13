package com.annotion.ruiyi.rgsdk;

import android.content.Context;
import android.content.pm.PackageManager;
import android.media.JetPlayer;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.shuame.rootgenius.sdk.JniHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    static {
        System.loadLibrary("rgsdk");
    }
    enum Enum {
        A,
        B,
        C;
    }


    private JniHelper.Data data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ReadFileUtils.copyFileTo();
        String str  =  "mount -o remount ,rw /system;BIN=" + getFilesDir().getPath()+ ";chmod 777 $BIN/busybox;";
        String str22 = ReadFileUtils.replaceStr(str +
                        CommonConstant.COMMON_SHELL,CommonConstant.APK_PATH,"/system/app");

        Result result = ReadFileUtils.execCommand(new String[]{str22}, true, true);

        Log.e("MainActivity" ,""+result);
//        if (ReadFileUtils.getFileInputStream("abc_action_bar_elevation_material") != null){
//            Log.e("MainActivity","加载文件成功");
//        }
//
//        ReadFileUtils.loadLibraryFromSDCard("rgsdk");
//        Log.e("MainActivity","加载so成功");

        init();
        findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,""+ JniHelper.verify(MainActivity.this),Toast.LENGTH_LONG).show();

            }
        });
        Log.e("MainActivity",""+Enum.A.ordinal());
    }

    private void init() {
        File file = new File(Environment.getExternalStorageDirectory()+"/TIT_killer.apk");
        Log.e("---1111---",getFilesDir().getPath());
        Log.e("----1111--",getFilesDir().getAbsolutePath());
        data = new JniHelper.Data();
        data.cntx = this;
        data.field = new HashMap();
        data.field.put("callFunc", "main");
        data.field.put("executePath", getFilesDir().getAbsolutePath() + "/" + "Data/Bin/");
        data.field.put("rootgeniusVer", "84");
        data.field.put("androidSdkVer", "19");
        data.field.put("productId", "query-empty");
        data.field.put("androidVer", "");
        data.field.put("buildDescription", "");
        data.field.put("coreVer", "");
        data.field.put("serial", "");
        data.field.put("macAddr", "");
        data.field.put("workDir", getFilesDir().getAbsolutePath() + "/");
        data.field.put("tempDir", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/");
        data.field.put("installScript", "1");
        data.field.put("instSuScript", "su");
        data.field.put("tempRoot", "false");
        data.field.put("IMEI", "1");
        data.field.put("IMSI", "1");
        data.field.put("rid", "1");
        data.field.put("productModel", "1");
        data.field.put("productDevice", "1");
        data.field.put("roHardware", "1");
        data.field.put("neo", "1");
        data.field.put("phoneId", "1");
        data.field.put("versionName", "general_mobile");
//        if (v5.rootEngine != null) {
            data.field.put("rootEngineUrl", "1");
            data.field.put("rootEngineMd5", "1");
//        }
        data.field.put("phoneId", 22);

//        Iterator entries = data.field.entrySet().iterator();
//        while (entries.hasNext()) {
//
//            Map.Entry entry = (Map.Entry) entries.next();
//
//            Integer key = (Integer)entry.getKey();
//
//            Integer value = (Integer)entry.getValue();
//
//            System.out.println("Key = " + key + ", Value = " + value);
//
//        }

    }





}
