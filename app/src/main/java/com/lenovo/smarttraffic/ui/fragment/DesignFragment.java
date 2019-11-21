package com.lenovo.smarttraffic.ui.fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.lenovo.smarttraffic.Constant;
import com.lenovo.smarttraffic.R;
import com.lenovo.smarttraffic.bean.CarCross;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


/**
 * @author Amoly
 * @date 2019/4/11.
 * description：设计页面
 */
public class DesignFragment extends BaseFragment {
    private static DesignFragment instance = null;
    private com.github.mikephil.charting.charts.RadarChart radarchart;
    private android.widget.TextView tv1;
    private android.widget.TextView tv2;
    private android.widget.TextView tv3;
    private android.widget.TextView tv4;
    private com.github.mikephil.charting.charts.LineChart linchart;
    private Random random;
    private List<CarCross> carCrossList4;
    private List<CarCross> carCrossList3;
    private List<CarCross> carCrossList2;
    private List<CarCross> carCrossList1;
    private List<Integer> roadStatuslist;
    private Timer timer;
    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            try {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        linechart();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public static DesignFragment getInstance() {
        if (instance == null) {
            instance = new DesignFragment();
        }
        return instance;
    }


    @Override
    protected View getSuccessView() {
        View view = View.inflate(getActivity(), R.layout.fragment_design, null);
        initView(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        random = new Random();
        carCrossList1 = new ArrayList<>();
        carCrossList2 = new ArrayList<>();
        carCrossList3 = new ArrayList<>();
        carCrossList4 = new ArrayList<>();
        initradarchart();
        invalidLInecharta();
        startTimer();
    }

    private void startTimer() {
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            linechart();
                            intiRItgh();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        timer.schedule(timerTask, 0, 2000);
    }

    private void intiRItgh() {
        tv1.setText(Integer.parseInt(tv1.getText().toString().trim())+carCrossList1.get(carCrossList1.size()-1).getNumber()+"");
        tv2.setText(Integer.parseInt(tv2.getText().toString().trim())+carCrossList2.get(carCrossList2.size()-1).getNumber()+"");
        tv3.setText(Integer.parseInt(tv3.getText().toString().trim())+carCrossList3.get(carCrossList3.size()-1).getNumber()+"");
        tv4.setText(Integer.parseInt(tv4.getText().toString().trim())+carCrossList4.get(carCrossList4.size()-1).getNumber()+"");
    }

    private void linechart() {
        if (carCrossList1.size() > 10) {
            carCrossList1.remove(0);
            carCrossList2.remove(0);
            carCrossList3.remove(0);
            carCrossList4.remove(0);
        }
        String format = new SimpleDateFormat("mm:ss").format(new Date(System.currentTimeMillis()));
        carCrossList1.add(new CarCross(format, random.nextInt(10) + 1));
        carCrossList2.add(new CarCross(format, random.nextInt(10) + 1));
        carCrossList3.add(new CarCross(format, random.nextInt(10) + 1));
        carCrossList4.add(new CarCross(format, random.nextInt(10) + 1));
        List<String> xValues = new ArrayList<>();
        List<Entry> yValues1 = new ArrayList<>();
        List<Entry> yValues2 = new ArrayList<>();
        List<Entry> yValues3 = new ArrayList<>();
        List<Entry> yValues4 = new ArrayList<>();
        for (int i = 0; i < carCrossList1.size(); i++) {
            xValues.add(carCrossList1.get(i).getTime());
            yValues1.add(new Entry(i, carCrossList1.get(i).getNumber()));
            yValues2.add(new Entry(i, carCrossList2.get(i).getNumber()));
            yValues3.add(new Entry(i, carCrossList3.get(i).getNumber()));
            yValues4.add(new Entry(i, carCrossList4.get(i).getNumber()));
        }
        LineDataSet lineDataSet1 = new LineDataSet(yValues1, "");
        lineDataSet1.setDrawCircleHole(false);
        lineDataSet1.setDrawCircles(false);
        lineDataSet1.setDrawFilled(true);
        lineDataSet1.setColor(Color.RED);
        LineDataSet lineDataSet2 = new LineDataSet(yValues2, "");
        lineDataSet2.setDrawCircleHole(false);
        lineDataSet2.setDrawCircles(false);
        lineDataSet2.setDrawFilled(true);
        lineDataSet2.setColor(Color.BLUE);
        LineDataSet lineDataSet3 = new LineDataSet(yValues3, "");
        lineDataSet3.setDrawCircleHole(false);
        lineDataSet3.setDrawCircles(false);
        lineDataSet3.setDrawFilled(true);
        lineDataSet3.setColor(Color.GREEN);
        LineDataSet lineDataSet4 = new LineDataSet(yValues4, "");
        lineDataSet4.setDrawCircleHole(false);
        lineDataSet4.setDrawCircles(false);
        lineDataSet4.setDrawFilled(true);
        lineDataSet4.setColor(Color.YELLOW);
        List<ILineDataSet> iLineDataSets = new ArrayList<>();
        iLineDataSets.add(lineDataSet1);
        iLineDataSets.add(lineDataSet2);
        iLineDataSets.add(lineDataSet3);
        iLineDataSets.add(lineDataSet4);
        LineData lineData = new LineData(iLineDataSets);
        lineData.setDrawValues(false);
        XAxis xAxis = linchart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xValues.get((int) value);
            }
        });
        linchart.getAxisLeft().setTextColor(Color.WHITE);
        linchart.getAxisRight().setEnabled(false);
        linchart.getLegend().setEnabled(false);
        linchart.getDescription().setEnabled(false);
        linchart.setTouchEnabled(false);
        linchart.setData(lineData);
        linchart.invalidate();
    }

    @Override
    public void onStop() {
        super.onStop();
        stoptimer();
    }

    private void stoptimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
    }

    private void invalidLInecharta() {
        long l = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            long time = l - 2000 * i;
            String format = new SimpleDateFormat("mm:ss").format(new Date(time));
            carCrossList1.add(new CarCross(format, random.nextInt(10) + 1));
            carCrossList2.add(new CarCross(format, random.nextInt(10) + 1));
            carCrossList3.add(new CarCross(format, random.nextInt(10) + 1));
            carCrossList4.add(new CarCross(format, random.nextInt(10) + 1));
        }
    }

    private void initradarchart() {
        initradardata();
        List<RadarEntry> radarEntryList = new ArrayList<>();
        for (Integer integer : roadStatuslist) {
            radarEntryList.add(new RadarEntry(integer));
        }
        RadarDataSet radarDataSet = new RadarDataSet(radarEntryList, "");
        radarDataSet.setDrawFilled(true);
        radarDataSet.setColor(Color.WHITE);
        RadarData radarData = new RadarData(radarDataSet);
        radarData.setDrawValues(false);
        radarchart.setWebColor(Color.WHITE);
        radarchart.setWebColorInner(Color.WHITE);
        radarchart.getXAxis().setTextColor(Color.WHITE);
        radarchart.getYAxis().setTextColor(Color.WHITE);
        radarchart.getLegend().setEnabled(false);
        radarchart.getDescription().setEnabled(false);
        radarchart.setData(radarData);
        radarchart.invalidate();
    }

    private void initradardata() {
        roadStatuslist = new ArrayList<>();
        roadStatuslist.add(2);
        roadStatuslist.add(3);
        roadStatuslist.add(4);
        roadStatuslist.add(5);
        roadStatuslist.add(4);
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
        radarchart = (RadarChart) view.findViewById(R.id.radarchart);
        tv1 = (TextView) view.findViewById(R.id.tv_1);
        tv2 = (TextView) view.findViewById(R.id.tv_2);
        tv3 = (TextView) view.findViewById(R.id.tv_3);
        tv4 = (TextView) view.findViewById(R.id.tv_4);
        linchart = (LineChart) view.findViewById(R.id.linchart);
    }
}
