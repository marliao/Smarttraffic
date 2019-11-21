package com.lenovo.smarttraffic.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.lenovo.smarttraffic.App;
import com.lenovo.smarttraffic.Constant;
import com.lenovo.smarttraffic.R;
import com.lenovo.smarttraffic.bean.Sense;
import com.lenovo.smarttraffic.bean.Weather;
import com.lenovo.smarttraffic.ui.customerActivity.SubActivity;
import com.lenovo.smarttraffic.ui.customerActivity.UserQianDaoActivity;
import com.lenovo.smarttraffic.ui.lzz.CityCarParkingActivity;
import com.lenovo.smarttraffic.ui.lzz.WeathrActivity;
import com.lenovo.smarttraffic.usermanger.UserMangerActivity;
import com.lenovo.smarttraffic.util.SpUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


/**
 * @author Amoly
 * @date 2019/4/11.
 * description：主页面
 */
public class MainContentFragment extends BaseFragment {
    private static MainContentFragment instance = null;
    private com.github.mikephil.charting.charts.PieChart piechart;
    private TextView tvPmStatus;
    private RelativeLayout rlToday;
    private TextView testDay;
    private ImageView homeDayIncome;
    private TextView tvWeather;
    private TextView tv_weather_tomer;
    private RelativeLayout rlTomor;
    private TextView testYear;
    private ImageView homeYearIncome;
    private TextView tvLight;
    private TextView tvSport;
    private TextView tvYifu;
    private TextView tvCold;
    private RelativeLayout homeOperationCenter;
    private ImageView homeOcImage;
    private RelativeLayout homeConsumeCenter;
    private ImageView homeCcImage;
    private RelativeLayout homeManagerCenter;
    private ImageView homeMcImage;
    private RelativeLayout homeDataCenter;
    private ImageView homeDcImage;
    private Random random;
    private ArrayList<Weather> weatherList;
    private Timer timer;
    private TimerTask timerTask;
    private Sense sense;

    public static MainContentFragment getInstance() {
        if (instance == null) {
            instance = new MainContentFragment();
        }
        return instance;
    }


    @Override
    protected View getSuccessView() {
        View view = View.inflate(getActivity(), R.layout.fragment_home, null);
        initView(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initdata();
        initUI();
        initlistener();
        startTImer();
    }

    private void initUI() {
        testDay.setText("今日天气" + weatherList.get(0).getMintemp());
        homeDayIncome.setImageResource(getResources().getIdentifier(weatherList.get(0).getWeather_en(), "mipmap", getActivity().getPackageName()));
        tvWeather.setText(weatherList.get(0).getWeather_zh());
        testYear.setText("今日天气" + weatherList.get(1).getMintemp());
        homeYearIncome.setImageResource(getResources().getIdentifier(weatherList.get(1).getWeather_en(), "mipmap", getActivity().getPackageName()));
        tv_weather_tomer.setText(weatherList.get(1).getWeather_zh());
    }

    private void startTImer() {
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            initSense();
                            initPIechart();
                            initSenseData();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        timer.schedule(timerTask, 0, 3000);

    }

    private void initSenseData() {
        int light = sense.getLight();
        if (light < 1000) {
            tvLight.setText("弱");
            tvLight.setTextColor(Color.parseColor("#4472c4"));
        } else if (light >= 1000 && light <= 3000) {
            tvLight.setText("中等");
            tvLight.setTextColor(Color.parseColor("#00b050"));
        } else {
            tvLight.setText("强");
            tvLight.setTextColor(Color.parseColor("#ff0000"));
        }

        int co2 = sense.getCo2();
        if (co2 < 3000) {
            tvSport.setText("适宜");
            tvSport.setTextColor(Color.parseColor("#44dc68"));
        } else if (co2 > 3000 && co2 < 6000) {
            tvSport.setText("中");
            tvSport.setTextColor(Color.parseColor("#ffc000"));
        } else {
            tvSport.setText("较不宜");
            tvSport.setTextColor(Color.parseColor("#8149ac"));
        }

        int temp = sense.getTemp();
        if (temp < 12) {
            tvYifu.setText("冷");
            tvYifu.setTextColor(Color.parseColor("#3462f4"));
        } else if (light > 12 && light < 21) {
            tvYifu.setText("舒适");
            tvYifu.setTextColor(Color.parseColor("#92d050"));
        } else if (light > 21 && light < 35) {
            tvYifu.setText("温暖");
            tvYifu.setTextColor(Color.parseColor("#44dc68"));
        } else {
            tvYifu.setText("热");
            tvYifu.setTextColor(Color.parseColor("#ff0000"));
        }
        int humidity = sense.getHumidity();
        if (humidity < 50) {
            tvCold.setText("较易发");
            tvCold.setTextColor(Color.parseColor("#ff0000"));
        } else {
            tvCold.setText("少发");
            tvCold.setTextColor(Color.parseColor("#ffff40"));
        }

        int pm25 = sense.getPm25();
        if (pm25 < 35) {
            tvPmStatus.setText("优");
        } else if (pm25 > 35 && pm25 < 75) {
            tvPmStatus.setText("良");
        } else if (pm25 > 75 && pm25 < 115) {
            tvPmStatus.setText("轻度污染");
        } else if (pm25 > 115 && pm25 < 150) {
            tvPmStatus.setText("中度污染");
        } else {
            tvPmStatus.setText("重度污染");
        }

    }

    private void initPIechart() {
        List<PieEntry> pieEntryList = new ArrayList<>();
        pieEntryList.add(new PieEntry(sense.getPm25()));
        pieEntryList.add(new PieEntry(random.nextInt(200)));
        PieDataSet pieDataSet = new PieDataSet(pieEntryList, "");
        List<Integer> colorlist = new ArrayList<>();
        colorlist.add(Color.GREEN);
        colorlist.add(Color.RED);
        pieDataSet.setColors(colorlist);
        PieData pieData = new PieData(pieDataSet);
        pieData.setDrawValues(false);
        piechart.setHoleRadius(40);
        piechart.getLegend().setEnabled(false);
        piechart.getDescription().setEnabled(false);
        piechart.setData(pieData);
        piechart.invalidate();
    }

    private void initSense() {
        sense = new Sense();
        sense.setLight(500 + random.nextInt(3000));
        sense.setHumidity(30 + random.nextInt(80));
        sense.setTemp(6 + random.nextInt(35));
        sense.setCo2(2000 + random.nextInt(4000));
        sense.setPm25(20 + random.nextInt(130));
        List<String> rainlist = new ArrayList<>();
        rainlist.add("当天和次日有雨");
        rainlist.add("三天之内有雨");
        rainlist.add("三天之内没雨");
        sense.setRain(rainlist.get(random.nextInt(3)));
    }

    @Override
    public void onStop() {
        super.onStop();
        stopTImer();
    }

    private void stopTImer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
    }

    private void initdata() {
        getWeaher();
    }

    private void getWeaher() {
        random = new Random();
        List<String> weather_en = new ArrayList<>();
        weather_en.add("cloudy");
        weather_en.add("rain");
        weather_en.add("sun");
        weather_en.add("cloudy_");
        List<String> weather_zh = new ArrayList<>();
        weather_zh.add("多云");
        weather_zh.add("小雨");
        weather_zh.add("晴");
        weather_zh.add("阴");
        weatherList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            Weather weather = new Weather();
            weather.setMintemp(6 + random.nextInt(20) + 1);
            weather.setMaxtemp(weather.getMintemp() + random.nextInt(10) + 5);
            int index = random.nextInt(weather_en.size());
            weather.setWeather_en(weather_en.get(index));
            weather.setWeather_zh(weather_zh.get(index));
            weatherList.add(weather);
        }
    }

    private void initlistener() {
        homeOperationCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //城市地铁
                Intent intent = new Intent(getActivity(), SubActivity.class);
                intent.putExtra("title", "城市地铁");
                startActivity(intent);
            }
        });
        homeConsumeCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //消费中心
                Intent intent = new Intent(getContext(), CityCarParkingActivity.class);
                intent.putExtra("title", "消费中心");
                startActivity(intent);
            }
        });
        homeManagerCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //用户中心
                if (SpUtil.getBoolean(SpUtil.ISLOGIN, false)) {
                    Intent intent = new Intent(getContext(), UserMangerActivity.class);
                    intent.putExtra("title", "用户中心");
                    startActivity(intent);
                } else {
                    App.showToast("您未登录，请登录后查看");
                }
            }
        });
        homeDataCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UserQianDaoActivity.class);
                intent.putExtra("title", "用户签到");
                startActivity(intent);
            }
        });
        rlToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SpUtil.getBoolean(SpUtil.ISLOGIN, false)) {
                    startActivity(new Intent(getActivity(), WeathrActivity.class));
                } else {
                    App.showToast("您未登录，请登录后查看");
                }
            }
        });
        rlTomor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SpUtil.getBoolean(SpUtil.ISLOGIN, false)) {
                    startActivity(new Intent(getActivity(), WeathrActivity.class));
                } else {
                    App.showToast("您未登录，请登录后查看");
                }
            }
        });
    }

    @Override
    protected Object requestData() {
        return Constant.STATE_SUCCEED;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onDestroy() {
        if (instance != null) {
            instance = null;
        }
        super.onDestroy();
    }


    private void initView(View view) {
        piechart = (PieChart) view.findViewById(R.id.piechart);
        tvPmStatus = (TextView) view.findViewById(R.id.tv_pm_status);
        rlToday = (RelativeLayout) view.findViewById(R.id.rl_today);
        testDay = (TextView) view.findViewById(R.id.test_day);
        homeDayIncome = (ImageView) view.findViewById(R.id.home_day_income);
        tvWeather = (TextView) view.findViewById(R.id.tv_weather);
        tv_weather_tomer = (TextView) view.findViewById(R.id.tv_weather_tomer);
        rlTomor = (RelativeLayout) view.findViewById(R.id.rl_tomor);
        testYear = (TextView) view.findViewById(R.id.test_year);
        homeYearIncome = (ImageView) view.findViewById(R.id.home_year_income);
        tvLight = (TextView) view.findViewById(R.id.tv_light);
        tvSport = (TextView) view.findViewById(R.id.tv_sport);
        tvYifu = (TextView) view.findViewById(R.id.tv_yifu);
        tvCold = (TextView) view.findViewById(R.id.tv_cold);
        homeOperationCenter = (RelativeLayout) view.findViewById(R.id.home_operation_center);
        homeOcImage = (ImageView) view.findViewById(R.id.home_oc_image);
        homeConsumeCenter = (RelativeLayout) view.findViewById(R.id.home_consume_center);
        homeCcImage = (ImageView) view.findViewById(R.id.home_cc_image);
        homeManagerCenter = (RelativeLayout) view.findViewById(R.id.home_manager_center);
        homeMcImage = (ImageView) view.findViewById(R.id.home_mc_image);
        homeDataCenter = (RelativeLayout) view.findViewById(R.id.home_data_center);
        homeDcImage = (ImageView) view.findViewById(R.id.home_dc_image);
    }
}