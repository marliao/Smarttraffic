package com.lenovo.smarttraffic.ui.lzz;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.PolylineOptions;
import com.android.volley.Response;
import com.google.gson.Gson;
import com.lenovo.smarttraffic.App;
import com.lenovo.smarttraffic.R;
import com.lenovo.smarttraffic.bean.RoadLatlng;
import com.lenovo.smarttraffic.ui.customerActivity.RootActivity;
import com.lenovo.smarttraffic.util.HttpUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapActivity extends RootActivity {

    private MapView mapview;
    private Spinner spinner_start_position;
    private Spinner spinner_end_position;
    private RelativeLayout rl_line;
    private List<String> allPositionlist;
    private TextView tv_to_detail;
    private RelativeLayout rl_click;
    private AMap aMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(View.inflate(this, R.layout.activity_map, findViewById(R.id.ll_root)));
        initView();
        setTitle("离线地图");
        App.showDialog(this, null);
        App.disDialog(this, null);
        getAllPosition();
        mapview.onCreate(savedInstanceState);
        aMap = mapview.getMap();
        initSpiner();
        initListener2();
    }

    private void initListener2() {
        rl_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aMap.clear();
                String start_position = (String) spinner_start_position.getSelectedItem();
                String end_position = (String) spinner_end_position.getSelectedItem();
                HashMap<Object, Object> params = new HashMap<>();
                params.put("Origin", start_position);
                params.put("Destination", end_position);
                HttpUtil.doPost("direction_driving", params, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        rl_click.setVisibility(View.VISIBLE);
                        RoadLatlng roadLatlng = new Gson().fromJson(jsonObject.toString(), RoadLatlng.class);
                        App.roadLatlng = roadLatlng;
                        App.startpostion = start_position;
                        App.endpostion = end_position;
                        setdata(roadLatlng);
                    }
                });
            }
        });
        tv_to_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MapActivity.this, RoadDetailActivity.class));
            }
        });
    }

    private void setdata(RoadLatlng roadLatlng) {
        List<LatLng> latLngList = new ArrayList<>();
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (RoadLatlng.RouteBean.PathsBean.StepsBean bean : roadLatlng.getRoute().getPaths().get(0).getSteps()) {
            String[] polyling = bean.getPolyline().split(";");
            for (String s : polyling) {
                String[] split = s.split(",");
                LatLng latLng = new LatLng(Double.parseDouble(split[1]), Double.parseDouble(split[0]));
                latLngList.add(latLng);
                builder.include(latLng);
            }
        }
        aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 30));
        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.addAll(latLngList);
        polylineOptions.color(Color.BLUE);
        polylineOptions.width(10);
        aMap.addPolyline(polylineOptions);
        setimage(roadLatlng.getRoute().getOrigin(), R.mipmap.start);
        setimage(roadLatlng.getRoute().getDestination(), R.mipmap.end);
    }

    private void setimage(String position, int image) {
        String[] split = position.split(",");
        LatLng latLng = new LatLng(Double.parseDouble(split[1]), Double.parseDouble(split[0]));
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), image)));
        aMap.addMarker(markerOptions);
    }

    private void initSpiner() {
        List<String> startPositionlist = new ArrayList<>();
        List<String> endPositionlist = new ArrayList<>();
        for (String s : allPositionlist) {
            startPositionlist.add(s);
            endPositionlist.add(s);
        }
        ArrayAdapter<String> startAdatper = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, startPositionlist);
        spinner_start_position.setAdapter(startAdatper);
        spinner_start_position.setSelection(0);
        endPositionlist.remove(0);
        ArrayAdapter<String> endAdatper = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, endPositionlist);
        spinner_end_position.setAdapter(endAdatper);
        spinner_end_position.setSelection(0);
        spinner_start_position.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) spinner_end_position.getSelectedItem();
                endPositionlist.clear();
                for (String s : allPositionlist) {
                    endPositionlist.add(s);
                }
                for (int i = 0; i < endPositionlist.size(); i++) {
                    if (endPositionlist.get(i).equals((String) spinner_start_position.getSelectedItem())) {
                        endPositionlist.remove(i);
                    }
                }
                endAdatper.notifyDataSetChanged();
                for (int i = 0; i < endPositionlist.size(); i++) {
                    if (endPositionlist.get(i).equals(selectedItem)) {
                        spinner_end_position.setSelection(i);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_end_position.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) spinner_start_position.getSelectedItem();
                startPositionlist.clear();
                for (String s : allPositionlist) {
                    startPositionlist.add(s);
                }
                for (int i = 0; i < startPositionlist.size(); i++) {
                    if (startPositionlist.get(i).equals((String) spinner_end_position.getSelectedItem())) {
                        startPositionlist.remove(i);
                    }
                }
                startAdatper.notifyDataSetChanged();
                for (int i = 0; i < startPositionlist.size(); i++) {
                    if (startPositionlist.get(i).equals(selectedItem)) {
                        spinner_start_position.setSelection(i);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapview.onDestroy();
    }

    private void getAllPosition() {
        allPositionlist = new ArrayList<>();
        allPositionlist.add("联想大厦");
        allPositionlist.add("西铁营桥");
        allPositionlist.add("G107");
        allPositionlist.add("G2路段");
        allPositionlist.add("联想总部园区");
        allPositionlist.add("首都机场");
        allPositionlist.add("北京西站");
        allPositionlist.add("北京南站");
        allPositionlist.add("天安门");
        allPositionlist.add("联想研究院");
    }

    private void initView() {
        mapview = (MapView) findViewById(R.id.mapview);
        spinner_start_position = (Spinner) findViewById(R.id.spinner_start_position);
        spinner_end_position = (Spinner) findViewById(R.id.spinner_end_position);
        rl_line = (RelativeLayout) findViewById(R.id.rl_line);
        tv_to_detail = (TextView) findViewById(R.id.tv_to_detail);
        rl_click = (RelativeLayout) findViewById(R.id.rl_click);
    }
}
