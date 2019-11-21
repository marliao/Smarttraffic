package com.lenovo.smarttraffic.ui.lzz;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.lenovo.smarttraffic.App;
import com.lenovo.smarttraffic.R;
import com.lenovo.smarttraffic.bean.Sense;
import com.lenovo.smarttraffic.bean.Weather;
import com.lenovo.smarttraffic.ui.customerActivity.RootActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class WeathrActivity extends RootActivity {

    private TextView tv_temp_top;
    private ImageView iv_weather_top;
    private TextView tv_weather_top;
    private LineChart linechart;
    private TextView tv_light_index;
    private TextView tv_air_index;
    private TextView tv_sport_index;
    private TextView tv_yifu_index;
    private TextView tv_cold_index;
    private TextView tv_car_index;
    private TextView tv_light_level;
    private TextView tv_air_level;
    private TextView tv_sport_level;
    private TextView tv_yifu_level;
    private TextView tv_cold_level;
    private TextView tv_car_level;
    private List<Weather> weatherList;
    private Random random;
    private Sense sense;
    private Timer timer;
    private TimerTask timerTask;
    private MyAdsarter myAdsarter;
    private GridView gv_weawther;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(View.inflate(this, R.layout.activity_weathr, findViewById(R.id.ll_root)));
        initView();
        setTitle("天气预报");
        App.showDialog(this, null);
        App.disDialog(this, null);
        statTimer();
        initData();
        initlienchart();
        initAdapter();
    }

    private void initAdapter() {
        myAdsarter = new MyAdsarter();
        gv_weawther.setAdapter(myAdsarter);
    }

    public class MyAdsarter extends BaseAdapter {

        private ViewHolder viewHolder;

        @Override
        public int getCount() {
            return weatherList.size();
        }

        @Override
        public Weather getItem(int position) {
            return weatherList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(WeathrActivity.this, R.layout.item_weather, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            Weather item = getItem(position);
            viewHolder.tv_date.setText("6月" + (position + 2) + "日");
            switch (position) {
                case 0:
                    viewHolder.tv_day.setText("今日（周日）");
                    break;
                case 1:
                    viewHolder.tv_day.setText("周一");
                    break;
                case 2:
                    viewHolder.tv_day.setText("周二");
                    break;
                case 3:
                    viewHolder.tv_day.setText("周三");
                    break;
                case 4:
                    viewHolder.tv_day.setText("周四");
                    break;
                case 5:
                    viewHolder.tv_day.setText("周五");
                    break;
            }
            viewHolder.tv_weather.setText(item.getWeather_zh());
            viewHolder.iv_weather.setImageResource(getResources().getIdentifier(item.getWeather_en(), "mipmap", getPackageName()));
            if (position == weatherList.size() - 1) {
                LinearLayout.LayoutParams tv1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                tv1.setMargins(1, 1, 1, 0);
                LinearLayout.LayoutParams tv2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                tv2.setMargins(1, 1, 1, 0);
                LinearLayout.LayoutParams tv3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                tv3.setMargins(1, 1, 1, 0);
                LinearLayout.LayoutParams tv4 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                tv4.setMargins(1, 1, 1, 1);
                viewHolder.tv_date.setLayoutParams(tv1);
                viewHolder.tv_day.setLayoutParams(tv2);
                viewHolder.tv_weather.setLayoutParams(tv3);
                viewHolder.iv_weather.setLayoutParams(tv4);
            }
            return convertView;
        }

        public class ViewHolder {
            public View rootView;
            public TextView tv_date;
            public TextView tv_day;
            public TextView tv_weather;
            public ImageView iv_weather;

            public ViewHolder(View rootView) {
                this.rootView = rootView;
                this.tv_date = (TextView) rootView.findViewById(R.id.tv_date);
                this.tv_day = (TextView) rootView.findViewById(R.id.tv_day);
                this.tv_weather = (TextView) rootView.findViewById(R.id.tv_weather);
                this.iv_weather = (ImageView) rootView.findViewById(R.id.iv_weather);
            }

        }
    }

    private void initlienchart() {
        List<Entry> maxlist = new ArrayList<>();
        List<Entry> minlist = new ArrayList<>();
        for (int i = 0; i < weatherList.size(); i++) {
            maxlist.add(new Entry(i, weatherList.get(i).getMaxtemp()));
            minlist.add(new Entry(i, weatherList.get(i).getMintemp()));
        }
        LineDataSet lineDataSet1 = new LineDataSet(minlist, "");
        lineDataSet1.setDrawCircleHole(false);
        lineDataSet1.setColor(Color.BLUE);
        lineDataSet1.setCircleColor(Color.BLUE);
        LineDataSet lineDataSet2 = new LineDataSet(maxlist, "");
        lineDataSet2.setDrawCircleHole(false);
        lineDataSet2.setColor(Color.YELLOW);
        lineDataSet2.setCircleColor(Color.YELLOW);
        List<ILineDataSet> iLineDataSe = new ArrayList<>();
        iLineDataSe.add(lineDataSet1);
        iLineDataSe.add(lineDataSet2);
        LineData lineData = new LineData(iLineDataSe);
        lineData.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                String format = new DecimalFormat("##").format(value);
                return format + "°";
            }
        });
        linechart.getXAxis().setEnabled(false);
        linechart.getAxisLeft().setEnabled(false);
        linechart.getAxisRight().setEnabled(false);
        linechart.getLegend().setEnabled(false);
        linechart.getDescription().setEnabled(false);
        linechart.setData(lineData);
        linechart.invalidate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTimer();
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
    }

    private void statTimer() {
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            initUi();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        timer.schedule(timerTask, 0, 3000);
    }

    private void initUi() {
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
        Weather weather = weatherList.get(0);
        tv_temp_top.setText(weather.getMintemp() + "度");
        tv_weather_top.setText(weather.getWeather_zh());
        iv_weather_top.setImageResource(getResources().getIdentifier(weather.getWeather_en(), "mipmap", getPackageName()));

        int light = sense.getLight();
        if (light < 1000) {
            tv_light_index.setText(light + "");
            tv_light_level.setText("弱");
            tv_light_level.setTextColor(Color.parseColor("#4472c4"));
        } else if (light >= 1000 && light <= 3000) {
            tv_light_index.setText(light + "");
            tv_light_level.setText("中等");
            tv_light_level.setTextColor(Color.parseColor("#00b050"));
        } else {
            tv_light_index.setText(light + "");
            tv_light_level.setText("强");
            tv_light_level.setTextColor(Color.parseColor("#ff0000"));
        }
        int humidity = sense.getHumidity();
        if (humidity < 50) {
            tv_cold_index.setText(humidity + "");
            tv_cold_level.setText("较易发");
            tv_cold_level.setTextColor(Color.parseColor("#ff0000"));
        } else {
            tv_cold_index.setText(humidity + "");
            tv_cold_level.setText("少发");
            tv_cold_level.setTextColor(Color.parseColor("#ffff40"));
        }
        int temp = sense.getTemp();
        if (temp < 12) {
            tv_yifu_index.setText(temp + "");
            tv_yifu_level.setText("冷");
            tv_yifu_level.setTextColor(Color.parseColor("#3462f4"));
        } else if (light > 12 && light < 21) {
            tv_yifu_index.setText(temp + "");
            tv_yifu_level.setText("舒适");
            tv_yifu_level.setTextColor(Color.parseColor("#92d050"));
        } else if (light > 21 && light < 35) {
            tv_yifu_index.setText(temp + "");
            tv_yifu_level.setText("温暖");
            tv_yifu_level.setTextColor(Color.parseColor("#44dc68"));
        } else {
            tv_yifu_index.setText(temp + "");
            tv_yifu_level.setText("热");
            tv_yifu_level.setTextColor(Color.parseColor("#ff0000"));
        }

        int co2 = sense.getCo2();
        if (co2 < 3000) {
            tv_sport_index.setText(co2 + "");
            tv_sport_level.setText("适宜");
            tv_sport_level.setTextColor(Color.parseColor("#44dc68"));
        } else if (co2 > 3000 && co2 < 6000) {
            tv_sport_index.setText(co2 + "");
            tv_sport_level.setText("中");
            tv_sport_level.setTextColor(Color.parseColor("#ffc000"));
        } else {
            tv_sport_index.setText(co2 + "");
            tv_sport_level.setText("较不宜");
            tv_sport_level.setTextColor(Color.parseColor("#8149ac"));
        }

        int pm25 = sense.getPm25();
        if (pm25 < 35) {
            tv_air_index.setText(pm25 + "");
            tv_air_level.setText("优");
            tv_air_level.setTextColor(Color.parseColor("#44dc68"));
        } else if (pm25 > 35 && pm25 < 75) {
            tv_air_index.setText(pm25 + "");
            tv_air_level.setText("良");
            tv_air_level.setTextColor(Color.parseColor("#44dc68"));
        } else if (pm25 > 75 && pm25 < 115) {
            tv_air_index.setText(pm25 + "");
            tv_air_level.setText("轻度污染");
            tv_air_level.setTextColor(Color.parseColor("#44dc68"));
        } else if (pm25 > 115 && pm25 < 150) {
            tv_air_index.setText(pm25 + "");
            tv_air_level.setText("中度污染");
            tv_air_level.setTextColor(Color.parseColor("#44dc68"));
        } else {
            tv_air_index.setText(pm25 + "");
            tv_air_level.setText("重度污染");
            tv_air_level.setTextColor(Color.parseColor("#44dc68"));
        }
        String rain = sense.getRain();
        if (rain.equals("当天和次日有雨")) {
            tv_car_index.setText(rain);
            tv_car_level.setText("不适宜");
            tv_car_level.setTextColor(Color.BLACK);
        } else if (rain.equals("三天之内有雨")) {
            tv_car_index.setText(rain);
            tv_car_level.setText("不太适宜 ");
            tv_car_level.setTextColor(Color.BLACK);
        } else {
            tv_car_index.setText(rain);
            tv_car_level.setText("适宜 ");
            tv_car_level.setTextColor(Color.BLACK);
        }
    }

    private void initData() {
        random = new Random();
        List<String> weather_en = new ArrayList<>();
        weather_en.add("cloudy");
        weather_en.add("drizzle");
        weather_en.add("sunny");
        weather_en.add("overcast");
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

    private void initView() {
        tv_temp_top = (TextView) findViewById(R.id.tv_temp_top);
        iv_weather_top = (ImageView) findViewById(R.id.iv_weather_top);
        tv_weather_top = (TextView) findViewById(R.id.tv_weather_top);
        linechart = (LineChart) findViewById(R.id.linechart);
        tv_light_index = (TextView) findViewById(R.id.tv_light_index);
        tv_air_index = (TextView) findViewById(R.id.tv_air_index);
        tv_sport_index = (TextView) findViewById(R.id.tv_sport_index);
        tv_yifu_index = (TextView) findViewById(R.id.tv_yifu_index);
        tv_cold_index = (TextView) findViewById(R.id.tv_cold_index);
        tv_car_index = (TextView) findViewById(R.id.tv_car_index);
        tv_light_level = (TextView) findViewById(R.id.tv_light_level);
        tv_air_level = (TextView) findViewById(R.id.tv_air_level);
        tv_sport_level = (TextView) findViewById(R.id.tv_sport_level);
        tv_yifu_level = (TextView) findViewById(R.id.tv_yifu_level);
        tv_cold_level = (TextView) findViewById(R.id.tv_cold_level);
        tv_car_level = (TextView) findViewById(R.id.tv_car_level);
        gv_weawther = (GridView) findViewById(R.id.gv_weawther);
    }
}
