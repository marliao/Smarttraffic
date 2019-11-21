package com.lenovo.smarttraffic;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.lenovo.smarttraffic.bean.NewListBean;
import com.lenovo.smarttraffic.bean.PBean;
import com.lenovo.smarttraffic.bean.Park;
import com.lenovo.smarttraffic.bean.RoadLatlng;
import com.lenovo.smarttraffic.bean.SubLineListBean;
import com.lenovo.smarttraffic.bean.UBean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Amoly
 * @date 2019/4/11.
 * description：
 */
public class App extends MultiDexApplication {
    private static SubLineListBean subLineListBean;
    private static NewListBean newListBean;
    public static int Money;
    public static PBean.P Pinfo;
    public static UBean.U User;
    public static Park.ROWSDETAILBean carparking;
    public static RoadLatlng roadLatlng;
    public static String endpostion;
    public static String startpostion;
    private static RequestQueue queue;
    private static Toast toast;
    private static ProgressDialog progressDialog;
    private static Handler mainHandler;
    private static Context AppContext;
    private Set<Activity> allActivities;
    public static List<Integer> carAccountlist = new ArrayList<>();

    private static App instance = null; //此处提示内存泄漏

    public static synchronized App getInstance() {
        synchronized (App.class) {
            if (instance == null) {
                instance = new App();
            }
        }
        return instance;
    }

    public static NewListBean getNewListBean() {
        return newListBean;
    }

    public static void setNewListBean(NewListBean newListBean) {
        App.newListBean = newListBean;
    }

    public static SubLineListBean getSubLineListBean() {
        return subLineListBean;
    }

    public static void setSubLineListBean(SubLineListBean subLineListBean) {
        App.subLineListBean = subLineListBean;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        AppContext = this;
        instance = this;
        mainHandler = new Handler();
        toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        queue = Volley.newRequestQueue(this);
    }

    public static Context getContext() {
        return AppContext;
    }

    public static Handler getHandler() {
        return mainHandler;
    }

    public static RequestQueue getQueue() {
        return queue;
    }

    public interface ChuLi {
        void jump();
    }

    public static void showToast(String msg) {
        toast.setText(msg);
        toast.show();
    }

    public static void showDialog(FragmentActivity activity, String msg) {
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                return;
            }
        }
        progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle("提示");
        progressDialog.setMessage(msg);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    public static void disDialog(FragmentActivity activity, ChuLi chuLi) {
        if (progressDialog != null) {
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    try {
                        sleep(800);
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                            if (chuLi != null) {
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        chuLi.jump();
                                    }
                                });
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }

    public void addActivity(Activity act) {
        if (allActivities == null) {
            allActivities = new HashSet<>();
        }
        allActivities.add(act);
    }

    public void removeActivity(Activity act) {
        if (allActivities != null) {
            allActivities.remove(act);
        }
    }

    public void exitApp() {
        if (allActivities != null) {
            synchronized (allActivities) {
                for (Activity act : allActivities) {
                    act.finish();
                }
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

}
