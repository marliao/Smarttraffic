package com.lenovo.smarttraffic.ui.lzz;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.lenovo.smarttraffic.App;
import com.lenovo.smarttraffic.R;
import com.lenovo.smarttraffic.bean.Park;
import com.lenovo.smarttraffic.ui.customerActivity.RootActivity;
import com.lenovo.smarttraffic.util.FileUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CityCarParkingActivity extends RootActivity {

    private MapView mapview;
    private LinearLayout ll_location;
    private LinearLayout ll_marker;
    private ListView lv_car_parking;
    private Park park;
    private List<Park.ROWSDETAILBean> allParkinglist;
    private MyAdapte myAdapte;
    private AMap aMap;
    private Marker informarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(View.inflate(this, R.layout.activity_city_car_parking, findViewById(R.id.ll_root)));
        initView();
        setTitle("用户停车");
        mapview.onCreate(savedInstanceState);
        aMap = mapview.getMap();
        App.showDialog(this, null);
        App.disDialog(this, null);
        initData();
        initlistener();
    }

    private void initlistener() {
        ll_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMorenP();
            }
        });
        ll_marker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lv_car_parking.setVisibility(View.VISIBLE);
                setCarParkingMarker();
            }
        });
        aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                informarker = marker;
                LatLng position = marker.getPosition();
                if (position.latitude == Double.parseDouble(park.getLatitude()) && position.longitude == Double.parseDouble(park.getLongitude())) {
                    marker.setObject(park.getLocation());
                } else {
                    for (int i = 0; i < allParkinglist.size(); i++) {
                        Park.ROWSDETAILBean bean = allParkinglist.get(i);
                        String[] split = bean.getCoordinate().split(",");
                        LatLng latLng = new LatLng(Double.parseDouble(split[1]), Double.parseDouble(split[0]));
                        if (position.latitude == latLng.latitude && position.longitude == latLng.longitude) {
                            marker.setObject(bean.getName());
                            break;
                        }
                    }
                }
                aMap.setInfoWindowAdapter(new InforAdapter());
                marker.showInfoWindow();
                return true;
            }
        });
        aMap.setOnMapClickListener(new AMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                lv_car_parking.setVisibility(View.GONE);
                if (informarker != null) {
                    if (informarker.isInfoWindowShown()) {
                        informarker.hideInfoWindow();
                    }
                }
            }
        });
    }

    public class InforAdapter implements AMap.InfoWindowAdapter {

        private ViewHolder viewHolder;

        @Override
        public View getInfoWindow(Marker marker) {
            View view = View.inflate(CityCarParkingActivity.this, R.layout.infor_test, null);
            viewHolder = new ViewHolder(view);
            viewHolder.tv_text.setText((String) marker.getObject());
            return view;
        }

        @Override
        public View getInfoContents(Marker marker) {
            return null;
        }

        public class ViewHolder {
            public View rootView;
            public TextView tv_text;

            public ViewHolder(View rootView) {
                this.rootView = rootView;
                this.tv_text = (TextView) rootView.findViewById(R.id.tv_text);
            }

        }
    }

    private void setCarParkingMarker() {
        List<Integer> imaglist = new ArrayList<>();
        imaglist.add(R.mipmap.marker_one);
        imaglist.add(R.mipmap.marker_second);
        imaglist.add(R.mipmap.marker_thrid);
        imaglist.add(R.mipmap.marker_forth);
        imaglist.add(R.mipmap.location5_);
        imaglist.add(R.mipmap.location6_);
        imaglist.add(R.mipmap.location7_);
        imaglist.add(R.mipmap.location8_);
        imaglist.add(R.mipmap.location9_);
        imaglist.add(R.mipmap.location10_);
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (int i = 0; i < allParkinglist.size(); i++) {
            Park.ROWSDETAILBean bean = allParkinglist.get(i);
            String[] split = bean.getCoordinate().split(",");
            LatLng latLng = new LatLng(Double.parseDouble(split[1]), Double.parseDouble(split[0]));
            builder.include(latLng);
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), imaglist.get(i))));
            aMap.addMarker(markerOptions);
        }
        aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 30));
    }

    private void setMorenP() {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(Double.parseDouble(park.getLatitude()), Double.parseDouble(park.getLongitude())));
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.marker_self)));
        aMap.addMarker(markerOptions);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapview.onDestroy();
    }

    private void initData() {
        park = (Park) FileUtil.getFileCalss("Park", Park.class);
        allParkinglist = park.getROWS_DETAIL();
        initAdatpter();
    }

    private void initAdatpter() {
        for (Park.ROWSDETAILBean bean : allParkinglist) {
            String[] split = bean.getCoordinate().split(",");
            LatLng latLng = new LatLng(Double.parseDouble(split[1]), Double.parseDouble(split[0]));
            float distance = AMapUtils.calculateLineDistance(new LatLng(Double.parseDouble(park.getLatitude()), Double.parseDouble(park.getLongitude())), latLng);
            bean.setDistance(distance);
        }
        Collections.sort(allParkinglist, new Comparator<Park.ROWSDETAILBean>() {
            @Override
            public int compare(Park.ROWSDETAILBean o1, Park.ROWSDETAILBean o2) {
                return (int) (o1.getDistance() - o2.getDistance());
            }
        });
        myAdapte = new MyAdapte();
        lv_car_parking.setAdapter(myAdapte);
    }

    public class MyAdapte extends BaseAdapter {

        private ViewHolder viewHolder;

        @Override
        public int getCount() {
            return allParkinglist.size();
        }

        @Override
        public Park.ROWSDETAILBean getItem(int position) {
            return allParkinglist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(CityCarParkingActivity.this, R.layout.item_car_parking, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            Park.ROWSDETAILBean item = getItem(position);
            viewHolder.tv_1.setText(position + 1 + "");
            viewHolder.tv_name.setText(item.getName());
            viewHolder.tv_empty_space.setText("空车位" + item.getEmptySpace() + "个|停车费5元/小时");
            viewHolder.tv_distance.setText(item.getAddress() + new DecimalFormat("##").format(item.getDistance()) + "m");
            if (item.getOpen() == 1) {
                if (item.getEmptySpace() == 0) {
                    viewHolder.ll_color.setBackgroundColor(Color.GRAY);
                    viewHolder.iv_detail.setVisibility(View.GONE);
                    viewHolder.tv_status.setVisibility(View.VISIBLE);
                    viewHolder.tv_status.setText("已满");
                } else {
                    viewHolder.ll_color.setBackgroundColor(Color.WHITE);
                    viewHolder.tv_status.setVisibility(View.GONE);
                    viewHolder.iv_detail.setVisibility(View.VISIBLE);
                }
            } else {
                viewHolder.ll_color.setBackgroundColor(Color.GRAY);
                viewHolder.iv_detail.setVisibility(View.GONE);
                viewHolder.tv_status.setVisibility(View.VISIBLE);
                viewHolder.tv_status.setText("关闭");
            }
            viewHolder.iv_detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    App.carparking = item;
                    startActivity(new Intent(CityCarParkingActivity.this, CarParkingDetailActivity.class));
                }
            });
            return convertView;
        }


        public class ViewHolder {
            public View rootView;
            public TextView tv_1;
            public TextView tv_name;
            public TextView tv_empty_space;
            public TextView tv_distance;
            public ImageView iv_detail;
            public TextView tv_status;
            public LinearLayout ll_color;

            public ViewHolder(View rootView) {
                this.rootView = rootView;
                this.tv_1 = (TextView) rootView.findViewById(R.id.tv_1);
                this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
                this.tv_empty_space = (TextView) rootView.findViewById(R.id.tv_empty_space);
                this.tv_distance = (TextView) rootView.findViewById(R.id.tv_distance);
                this.iv_detail = (ImageView) rootView.findViewById(R.id.iv_detail);
                this.tv_status = (TextView) rootView.findViewById(R.id.tv_status);
                this.ll_color = (LinearLayout) rootView.findViewById(R.id.ll_color);
            }

        }
    }

    private void initView() {
        mapview = (MapView) findViewById(R.id.mapview);
        ll_location = (LinearLayout) findViewById(R.id.ll_location);
        ll_marker = (LinearLayout) findViewById(R.id.ll_marker);
        lv_car_parking = (ListView) findViewById(R.id.lv_car_parking);
    }
}
