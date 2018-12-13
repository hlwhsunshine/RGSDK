package com.annotion.ruiyi.rgsdk;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Function:
 * Project:RGSDK
 * Date:2018/12/4
 * Created by xiaojun .
 */

public class ReadFileUtils {

    public static final String COMMON_SHELL = "chmod 777 $BIN/busybox;" +
            "API=$(cat /system/build.prop | $BIN/busybox grep \"ro.build.version.sdk=\" | dd bs=1 skip=21 count=2);" +
            "MANU=$(cat /system/build.prop | $BIN/busybox grep \"ro.product.manufacturer=\" | dd bs=1 skip=24);" +
            "SUMOD=06755;" +
            "if [ \"$API\" -lt \"16\" ]; " +
            "then SUMOD=06755; " +
            "elif [ \"$API\" -ge \"18\" ]; " +
            "then SUMOD=0755; " +
            "elif [ \"$API\" -ge \"16\" ] && [ \"$API\" -lt \"18\" ] && [ \"$MANU\" == \"yulong\" ]; " +
            "then SUMOD=0755; " +
            "elif [ \"$API\" -gt \"16\" ] && [ \"$API\" -lt \"18\" ] && [ \"$MANU\" != \"coolpad\" ]; " +
            "then SUMOD=06755; " +
            "fi;" +
            "chattr -i -a /system/lib/libsupol.so;" +//设置文件的权限，比chmod更高级，libsupol.so是supersu的so
            "$BIN/busybox chattr -i -a /system/lib/libsupol.so;" +
            "rm /system/lib/libsupol.so;" +//删除system/lib/libsupol.so
            "$BIN/busybox rm /system/lib/libsupol.so;" +//删除system/lib/libsupol.so,两种删除方式有疑问
            "chattr -i -a /system/xbin/supolicy;" +
            "$BIN/busybox chattr -i -a /system/xbin/supolicy;" +
            "rm /system/xbin/supolicy;" +//删除system/xbin/supolicy文件
            "$BIN/busybox rm /system/xbin/supolicy;" +
            "rm -r /system/app/Superuser.apk;" +
            "rm -r /system/app/Kingouser.apk;" +
            "rm -r /system/app/Kingo*.apk;" +
            "chattr -i -a /system/xbin/su;" +//su
            "$BIN/busybox chattr -i -a /system/xbin/su;" +
            "rm -r /system/xbin/su;" +
            "$BIN/busybox rm /system/xbin/su;" +
            "chattr -i -a /system/bin/su;" +
            "$BIN/busybox chattr -i -a /system/bin/su;" +
            "rm -r /system/bin/su;$BIN/busybox rm /system/bin/su;" +
            "chattr -i -a /system/xbin/daemonsu;" +//supersu的东西
            "$BIN/busybox chattr -i -a /system/xbin/daemonsu;" +
            "rm -r /system/xbin/daemonsu;" +
            "$BIN/busybox rm /system/xbin/daemonsu;" +
            "set_perm(){ " +
            "chown $1.$2 $4;" +//改变文件的拥有者
            "chown $1:$2 $4; " +
            "chmod $3 $4; };" +
            "ch_con(){ " +
            "/system/bin/toolbox chcon u:object_r:system_file:s0 $1; " +
            "chcon u:object_r:system_file:s0 $1; };" +//修改用户的安全上下文

            "if [ -f /system/bin/ddexe ] && [ ! -f /system/bin/ddexe_real ] && [ -f $BIN/common/ddexe ];" +//判断是否为常规文件
            "then cat /system/bin/ddexe > /system/bin/ddexe_real;" +//把BIN/common/ddexe文件内容写入到/system/bin/ddexe_real
            "chmod 755 /system/bin/ddexe_real;ch_con system/bin/ddexe_real;" +
            "rm /system/bin/ddexe;" +
            "cat $BIN/common/ddexe > /system/bin/ddexe;" +//把BIN/common/ddexe文件内容写入到/system/bin/ddexe中
            "set_perm 0 0 0755 /system/bin/ddexe;" +//改变文件所有者
            "ch_con /system/bin/ddexe; " +//修改用户的安全上下文

            "elif [ -f /system/bin/ddexe ] && [ -f /system/bin/ddexe_real ] && [ -f $BIN/common/ddexe ];" +
            "then chattr -i -a /system/bin/ddexe;" +
            "$BIN/busybox chattr -i -a /system/bin/ddexe;" +
            "rm /system/bin/ddexe;" +
            "$BIN/busybox rm /system/bin/ddexe;" +
            "cat $BIN/common/ddexe > /system/bin/ddexe;" +
            "set_perm 0 0 0755 /system/bin/ddexe;" +
            "ch_con /system/bin/ddexe;" +
            "fi;" +

            "pm uninstall com.kingouser.com;" +//卸载com.kingouser.com
            "rm /system/app/KingoUserxxx;" +
            "rm /system/app/KingoUser.apk;" +
            "rm -r /system/app/KingoUser;" +
            "cat $BIN/KingoUser.apk > %apk_path%/Kingouserxxx;" +
            "chmod 644 %apk_path%/Kingouserxxx;" +
            "mv %apk_path%/Kingouserxxx %apk_path%/KingoUser.apk;" +
            "rm %apk_path%/KingoUserxxx;cat $BIN/libsupol.so > /system/lib/libsupol.so;" +
            "cat $BIN/supolicy > /system/xbin/supolicy;" +
            "cat $BIN/common/install-recovery.sh > /system/etc/install-recovery.sh;" +
            "cat $BIN/common/install-recovery.sh > /system/etc/install_recovery.sh;" +
            "cat $BIN/common/99SuperSUDaemon > /system/etc/init.d/99SuperSUDaemon;" +
            "cat $BIN/busybox > /system/xbin/busybox;" +
            "set_perm 0 0 0755 /system/xbin/busybox;" +
            "cat %copy_path% > /system/xbin/daemonsu;" +
            "cat %copy_path% > /system/bin/.ext/.su;" +
            "cat %copy_path% > /system/xbin/su;" +
            "cat %copy_path% > /system/sbin/su;" +
            "cat %copy_path% > /system/bin/su;set_perm 0 0 0777 /system/bin/.ext;" +
            "set_perm 0 0 $SUMOD /system/bin/.ext/.su;" +
            "set_perm 0 0 $SUMOD /system/xbin/su;" +
            "set_perm 0 0 $SUMOD /system/sbin/su;" +
            "set_perm 0 0 $SUMOD /system/bin/su;" +
            "set_perm 0 0 $SUMOD /system/xbin/daemonsu;" +
            "set_perm 0 0 0666 /system/lib/libsupol.so;" +
            "set_perm 0 0 0755 /system/xbin/supolicy;" +
            "set_perm 0 0 0755 /system/etc/install-recovery.sh;" +
            "set_perm 0 0 0755 /system/etc/install_recovery.sh;" +
            "set_perm 0 0 0755 /system/etc/init.d/99SuperSUDaemon;" +
            "set_perm 0 0 0644 /system/etc/.has_su_daemon;set_perm 0 0 0644 /system/etc/.installed_su_daemon;" +
            "ch_con /system/bin/.ext/.su;" +
            "ch_con /system/xbin/su;" +
            "ch_con /system/bin/su;" +
            "ch_con /system/xbin/daemonsu;ch_con /system/lib/libsupol.so;" +
            "ch_con /system/xbin/supolicy;ch_con /system/etc/install-recovery.sh;" +
            "ch_con /system/etc/install_recovery.sh;" +
            "ch_con /system/etc/init.d/99SuperSUDaemon;" +
            "ch_con /system/etc/.has_su_daemon;" +
            "ch_con /system/etc/.installed_su_daemon;" +
            "echo  > /sys/kernel/uevent_helper;" +
            "$BIN/busybox chattr +i +a /system/xbin/su;" +
            "$BIN/busybox chattr +i +a /system/bin/su;" +
            "$BIN/busybox chattr +i +a /system/sbin/su;" +
            "rm -r $BIN/root.jar;" +
            "rm -r $BIN/script/mkdevsh;" +
            "mount -o ro,remount /system;" +
            "/system/xbin/su --auto-daemon &";

    /*    public static final String SDCARD_PATH = Environment.getExternalStorageDirectory().getPath() + File.separator + "rootfile"+File.separator;
    
        public static InputStream getFileInputStream(String fileName) {
            try {
                Log.e("ReadFileLog","read file:"+SDCARD_PATH+fileName);
                if (fileName.contains("."))
                return new FileInputStream(new File(SDCARD_PATH + fileName));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            }
            return null;
        }
    
        public static void loadLibraryFromSDCard(String fileName) {
            Log.e("ReadFileLog","read file:"+fileName);
            StringBuilder path = new StringBuilder();
            path.append(SDCARD_PATH)
                    .append("lib")
                    .append(fileName)
                    .append(".so")
                    ;
            Log.e("ReadFileLog","read file:"+path.toString());
            System.load(path.toString());
        }*/
    public static void copyFile() {
        String[] s = {"root.zip", "busybox", "KingoUser.apk", "mkdevsh_sdk"};
        String path = "/data/data/com.kingoapp.apk/files/";

        String path1 = Environment.getExternalStorageDirectory().getPath() + File.separator + "copyfile" + File.separator;
        FileChannel input = null;
        FileChannel output = null;
        for (int i = 0; i < s.length; i++) {
            try {

                File file = new File(path + s[i]);
                if (!file.exists()) {
                    continue;
                }
                input = new FileInputStream(file).getChannel();
                output = new FileOutputStream(new File(path1 + s[i])).getChannel();

                output.transferFrom(input, 0, input.size());

                Log.e("RootLog", "复制文件" + s[i] + "成功！");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (input != null) {
                        input.close();
                    }
                    if (output != null) {
                        output.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }


    public static void copyFileTo() {
        String[] s = {"root.zip", "busybox", "KingoUser.apk", "mkdevsh_sdk"};
        String path = "/data/data/com.annotion.ruiyi.rgsdk/files/";

        String path1 = Environment.getExternalStorageDirectory().getPath() + File.separator + "copyfile" + File.separator;
        FileChannel input = null;
        FileChannel output = null;
        for (int i = 0; i < s.length; i++) {
            try {

                File file = new File(path1 + s[i]);
                if (!file.exists()) {
                    continue;
                }
                input = new FileInputStream(file).getChannel();
                output = new FileOutputStream(new File(path + s[i])).getChannel();

                output.transferFrom(input, 0, input.size());

                Log.e("RootLog", "复制文件" + s[i] + "到执行目录成功！");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (input != null) {
                        input.close();
                    }
                    if (output != null) {
                        output.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    public static String replaceStr(String str, String str2, String str3) {

        if (str.indexOf(str2) != -1) {
            return str.replace(str2, str3);
        }
        Log.e("RootLog",str);
        return str;
    }


    public static Result execCommand(String[] shellString, boolean isRoot, boolean arg13) {
        Result result;
        StringBuilder errorBuilder;
        int processInt;
        BufferedReader resultSteam;
        BufferedReader resultErrorSteam;
        StringBuilder resultBulder;
        DataOutputStream dataOutputStream;
        Process process;
        String cmdString;
        int int2 = 0;
        int v3 = -1;
        String string = null;
        Class cls = ReadFileUtils.class;
        synchronized (cls) {
            try {
                Log.e("RootLog", "命令execCommand:" + shellString[0]);
                if (shellString != null && shellString.length != 0) {

                    Runtime runtime = Runtime.getRuntime();
                    cmdString = isRoot ? "su" : "sh";
                    process = runtime.exec(cmdString);

                    dataOutputStream = new DataOutputStream(process.getOutputStream());

                    int length = shellString.length;

                    for (int i = 0; i < length; i++) {
                        String shell = shellString[i];
                        if (shell != null) {
                            dataOutputStream.write(shell.getBytes());
                            dataOutputStream.writeBytes("\n");
                            dataOutputStream.flush();
                        }
                    }
                    dataOutputStream.writeBytes("exit\n");
                    dataOutputStream.flush();
                    processInt = process.waitFor();
                    if (!arg13) {
                        //goto label_107;
                    }
                    resultBulder = new StringBuilder();
                    errorBuilder = new StringBuilder();
                    resultSteam = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    resultErrorSteam = new BufferedReader(new InputStreamReader(process.getErrorStream()));

                    while (!TextUtils.isEmpty(resultSteam.readLine())){
                        resultBulder.append(resultSteam.readLine());
                        resultBulder.append("\n");
                    }

                    while (!TextUtils.isEmpty(resultErrorSteam.readLine())){
                        errorBuilder.append(resultErrorSteam.readLine());
                        errorBuilder.append("\n");
                    }

                    if (dataOutputStream != null){
                        dataOutputStream.close();
                    }

                    if (resultSteam != null) {
                        resultSteam.close();
                    }
                    if (resultErrorSteam != null){
                        resultErrorSteam.close();
                    }

                    result = new Result(processInt, TextUtils.isEmpty(resultBulder.toString())?"":resultBulder.toString(),
                            TextUtils.isEmpty(errorBuilder.toString())?"":errorBuilder.toString());
                    return result;
                }

                result = new Result(-1, null, null);
                return result;

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("RootLog","命令执行发生异常！");
                return null;

            }
        }

    }
}
