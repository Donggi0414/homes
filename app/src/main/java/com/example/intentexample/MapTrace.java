package com.example.intentexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
//import android.location.LocationRequest;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import android.Manifest;
import android.widget.Toast;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;


public class MapTrace extends AppCompatActivity implements OnMapReadyCallback {

    private FragmentManager fragmentManager;
    private MapFragment mapFragment;

    private GoogleMap googleMap;

//    private List<LatLng> polylinePoints;
//
//    private Polyline polyline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_trace);

        fragmentManager = getFragmentManager();
        mapFragment = (MapFragment) fragmentManager.findFragmentById(R.id.googleMap);
        mapFragment.getMapAsync(this);

//        polylinePoints = new ArrayList<>();

        Button btn_backToMain = findViewById(R.id.btn_backToMain);
        btn_backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapTrace.this, SubActivity.class);
                startActivity(intent);
            }
        });

    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;

        LatLng kaist = new LatLng(36.3680066, 127.3658049); // 카이스트 E3-2
        MarkerOptions m_kaist = new MarkerOptions();
        m_kaist.title("카이스트(KAIST) 전산학동");
        m_kaist.snippet("한국과학기술원");
        m_kaist.position(kaist);
        this.googleMap.addMarker(m_kaist);

        LatLng clover = new LatLng(36.352428, 127.392585); // 크로바아파트
        MarkerOptions m_clover = new MarkerOptions();
        m_clover.title("크로바 아파트");
        m_clover.snippet("1,632세대 / 30년 / 3,577만원");
        m_clover.position(clover);
        m_clover.icon(BitmapDescriptorFactory.fromResource(R.drawable.apartment));


        LatLng mokRyeon = new LatLng(36.35007, 127.392062); // 목련아파트
        MarkerOptions m_mokRyeon = new MarkerOptions();
        m_mokRyeon.title("목련 아파트");
        m_mokRyeon.snippet("1,166세대 / 30년 / 2,827만원");
        m_mokRyeon.position(mokRyeon);
        m_mokRyeon.icon(BitmapDescriptorFactory.fromResource(R.drawable.apartment));


        LatLng hanmaru = new LatLng(36.354743, 127.392966); // 한마루럭키아파트
        MarkerOptions m_hanmaru = new MarkerOptions();
        m_hanmaru.title("한마루럭키 아파트");
        m_hanmaru.snippet("700세대 / 30년 / 2,303만원");
        m_hanmaru.position(hanmaru);
        m_hanmaru.icon(BitmapDescriptorFactory.fromResource(R.drawable.apartment));


        LatLng hanmaruSamsung = new LatLng(36.354743, 127.391420); // 한마루삼성아파트
        MarkerOptions m_hanmaruSamsung = new MarkerOptions();
        m_hanmaruSamsung.title("한마루삼성 아파트");
        m_hanmaruSamsung.snippet("700세대 / 30년 / 2,303만원");
        m_hanmaruSamsung.position(hanmaruSamsung);
        m_hanmaruSamsung.icon(BitmapDescriptorFactory.fromResource(R.drawable.apartment));


        LatLng yungjin = new LatLng(36.356312, 127.392448); // 영진햇님아파트
        MarkerOptions m_yungjin = new MarkerOptions();
        m_yungjin.title("영진햇님 아파트");
        m_yungjin.snippet("660세대 / 30년 / 2,234만원");
        m_yungjin.position(yungjin);
        m_yungjin.icon(BitmapDescriptorFactory.fromResource(R.drawable.apartment));


        LatLng doongji = new LatLng(36.358846, 127.392080); // 한신둥지아파트
        MarkerOptions m_doongji = new MarkerOptions();
        m_doongji.title("한신둥지 아파트");
        m_doongji.snippet("1,230세대 / 29년 / 1,750만원");
        m_doongji.position(doongji);
        m_doongji.icon(BitmapDescriptorFactory.fromResource(R.drawable.apartment));


        LatLng sujung = new LatLng(36.358293, 127.397653); // 수정타운아파트
        MarkerOptions m_sujung = new MarkerOptions();
        m_sujung.title("수정타운 아파트");
        m_sujung.snippet("2,010세대 / 30년 / 1,316만원");
        m_sujung.position(sujung);
        m_sujung.icon(BitmapDescriptorFactory.fromResource(R.drawable.apartment));


        LatLng garam = new LatLng(36.356453, 127.398101); // 둔산가람아파트
        MarkerOptions m_garam = new MarkerOptions();
        m_garam.title("둔산가람 아파트");
        m_garam.snippet("1,260세대 / 32년 / 1,946만원");
        m_garam.position(garam);
        m_garam.icon(BitmapDescriptorFactory.fromResource(R.drawable.apartment));


        LatLng sindonga = new LatLng(36.355153, 127.396504); // 국화신동아아파트
        MarkerOptions m_sindonga = new MarkerOptions();
        m_sindonga.title("국화신동아 아파트");
        m_sindonga.snippet("666세대 / 30년 / 1,863만원");
        m_sindonga.position(sindonga);
        m_sindonga.icon(BitmapDescriptorFactory.fromResource(R.drawable.apartment));


        LatLng life = new LatLng(36.354695, 127.398340); // 국화라이프아파트
        MarkerOptions m_life = new MarkerOptions();
        m_life.title("국화라이프 아파트");
        m_life.snippet("560세대 / 30년 / 1,690만원");
        m_life.position(life);
        m_life.icon(BitmapDescriptorFactory.fromResource(R.drawable.apartment));


        LatLng dongsung = new LatLng(36.354613, 127.399763); // 국화동성아파트
        MarkerOptions m_dongsung = new MarkerOptions();
        m_dongsung.title("국화동성 아파트");
        m_dongsung.snippet("672세대 / 31년 / 1,587만원");
        m_dongsung.position(dongsung);
        m_dongsung.icon(BitmapDescriptorFactory.fromResource(R.drawable.apartment));


        LatLng woosung = new LatLng(36.353536, 127.396526); // 국화우성아파트
        MarkerOptions m_woosung = new MarkerOptions();
        m_woosung.title("국화우성 아파트");
        m_woosung.snippet("562세대 / 30년 / 1,850만원");
        m_woosung.position(woosung);
        m_woosung.icon(BitmapDescriptorFactory.fromResource(R.drawable.apartment));


        LatLng hansin = new LatLng(36.352134, 127.396444); // 국화한신아파트
        MarkerOptions m_hansin = new MarkerOptions();
        m_hansin.title("국화한신 아파트");
        m_hansin.snippet("450세대 / 31년 / 2,104만원");
        m_hansin.position(hansin);
        m_hansin.icon(BitmapDescriptorFactory.fromResource(R.drawable.apartment));


        LatLng cheongsol = new LatLng(36.352162, 127.398612); // 청솔아파트
        MarkerOptions m_cheongsol = new MarkerOptions();
        m_cheongsol.title("청솔 아파트");
        m_cheongsol.snippet("980세대 / 31년 / 1,620만원");
        m_cheongsol.position(cheongsol);
        m_cheongsol.icon(BitmapDescriptorFactory.fromResource(R.drawable.apartment));


        LatLng bora1 = new LatLng(36.352901, 127.400603); // 보라1단지아파트
        MarkerOptions m_bora1 = new MarkerOptions();
        m_bora1.title("보라1단지 아파트");
        m_bora1.snippet("870세대 / 32년 / 800만원");
        m_bora1.position(bora1);
        m_bora1.icon(BitmapDescriptorFactory.fromResource(R.drawable.apartment));


        LatLng bora2 = new LatLng(36.351899, 127.400720); // 보라2단지아파트
        MarkerOptions m_bora2 = new MarkerOptions();
        m_bora2.title("보라2단지 아파트");
        m_bora2.snippet("630세대 / 32년 / 1,181만원");
        m_bora2.position(bora2);
        m_bora2.icon(BitmapDescriptorFactory.fromResource(R.drawable.apartment));


        LatLng dreamTree = new LatLng(36.360230, 127.391883); // 대우꿈나무아파트
        MarkerOptions m_dreamTree = new MarkerOptions();
        m_dreamTree.title("대우꿈나무 아파트");
        m_dreamTree.snippet("540세대 / 29년 / 1,330만원");
        m_dreamTree.position(dreamTree);
        m_dreamTree.icon(BitmapDescriptorFactory.fromResource(R.drawable.apartment));


        LatLng samhead1 = new LatLng(36.363300, 127.392006); // 샘머리1단지아파트
        MarkerOptions m_samhead1 = new MarkerOptions();
        m_samhead1.title("샘머리1단지 아파트");
        m_samhead1.snippet("1,350세대 / 24년 / 1,503만원");
        m_samhead1.position(samhead1);
        m_samhead1.icon(BitmapDescriptorFactory.fromResource(R.drawable.apartment));


        LatLng samhead2 = new LatLng(36.361384, 127.393377); // 샘머리2단지아파트
        MarkerOptions m_samhead2 = new MarkerOptions();
        m_samhead2.title("샘머리2단지 아파트");
        m_samhead2.snippet("2,200세대 / 24년 / 1,574만원");
        m_samhead2.position(samhead2);
        m_samhead2.icon(BitmapDescriptorFactory.fromResource(R.drawable.apartment));


        LatLng silver = new LatLng(36.359975, 127.394067); // 은초롱아파트
        MarkerOptions m_silver = new MarkerOptions();
        m_silver.title("은초롱 아파트");
        m_silver.snippet("120세대 / 29년 / 1,296만원");
        m_silver.position(silver);
        m_silver.icon(BitmapDescriptorFactory.fromResource(R.drawable.apartment));


        LatLng milkyway = new LatLng(36.349522, 127.379544); // 둔산은하수아파트
        MarkerOptions m_milkyway = new MarkerOptions();
        m_milkyway.title("둔산은하수 아파트");
        m_milkyway.snippet("816세대 / 28년 / 1,503만원");
        m_milkyway.position(silver);
        m_milkyway.icon(BitmapDescriptorFactory.fromResource(R.drawable.apartment));


        LatLng nokwon = new LatLng(36.347622, 127.380583); // 둔산녹원아파트
        MarkerOptions m_nokwon = new MarkerOptions();
        m_nokwon.title("둔산녹원 아파트");
        m_nokwon.snippet("1,200세대 / 28년 / 1,566만원");
        m_nokwon.position(nokwon);
        m_nokwon.icon(BitmapDescriptorFactory.fromResource(R.drawable.apartment));


        LatLng hyangchon = new LatLng(36.354033, 127.375504); // 향촌아파트
        MarkerOptions m_hyangchon = new MarkerOptions();
        m_hyangchon.title("향촌 아파트");
        m_hyangchon.snippet("1,650세대 / 28년 / 1,430만원");
        m_hyangchon.position(hyangchon);
        m_hyangchon.icon(BitmapDescriptorFactory.fromResource(R.drawable.apartment));


        LatLng bluebird = new LatLng(36.356354, 127.375475); // 둔산파랑새아파트
        MarkerOptions m_bluebird = new MarkerOptions();
        m_bluebird.title("둔산파랑새 아파트");
        m_bluebird.snippet("406세대 / 28년 / 1,653만원");
        m_bluebird.position(bluebird);
        m_bluebird.icon(BitmapDescriptorFactory.fromResource(R.drawable.apartment));


        int desiredWidth = 100; // 원하는 아이콘의 너비
        int desiredHeight = 100; // 원하는 아이콘의 높이

        BitmapDescriptor originalBitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.apartment);

        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.apartment);
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, desiredWidth, desiredHeight, false);
        BitmapDescriptor resizedBitmapDescriptor = BitmapDescriptorFactory.fromBitmap(resizedBitmap);

        m_clover.icon(resizedBitmapDescriptor);
        m_mokRyeon.icon(resizedBitmapDescriptor);
        m_hanmaru.icon(resizedBitmapDescriptor);
        m_hanmaruSamsung.icon(resizedBitmapDescriptor);
        m_yungjin.icon(resizedBitmapDescriptor);
        m_doongji.icon(resizedBitmapDescriptor);
        m_garam.icon(resizedBitmapDescriptor);
        m_sindonga.icon(resizedBitmapDescriptor);
        m_sujung.icon(resizedBitmapDescriptor);
        m_life.icon(resizedBitmapDescriptor);
        m_dongsung.icon(resizedBitmapDescriptor);
        m_woosung.icon(resizedBitmapDescriptor);
        m_hansin.icon(resizedBitmapDescriptor);
        m_cheongsol.icon(resizedBitmapDescriptor);
        m_bora1.icon(resizedBitmapDescriptor);
        m_bora2.icon(resizedBitmapDescriptor);
        m_dreamTree.icon(resizedBitmapDescriptor);
        m_samhead1.icon(resizedBitmapDescriptor);
        m_samhead2.icon(resizedBitmapDescriptor);
        m_silver.icon(resizedBitmapDescriptor);
        m_milkyway.icon(resizedBitmapDescriptor);
        m_nokwon.icon(resizedBitmapDescriptor);
        m_hyangchon.icon(resizedBitmapDescriptor);
        m_bluebird.icon(resizedBitmapDescriptor);


// 마커 추가
//        this.googleMap.addMarker(m_clover);
//        this.googleMap.addMarker(m_mokRyeon);

        this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(kaist, 16));

        Marker cloverMarker = this.googleMap.addMarker(m_clover);
        Marker mokRyeonMarker = this.googleMap.addMarker(m_mokRyeon);
        Marker hanmaruMarker = this.googleMap.addMarker(m_hanmaru);
        Marker hanmaruSamsungMarker = this.googleMap.addMarker(m_hanmaruSamsung);
        Marker yungjinMarker = this.googleMap.addMarker(m_yungjin);
        Marker doongjiMarker = this.googleMap.addMarker(m_doongji);
        Marker garamMarker = this.googleMap.addMarker(m_garam);
        Marker sindongaMarker = this.googleMap.addMarker(m_sindonga);
        Marker sujungMarker = this.googleMap.addMarker(m_sujung);
        Marker lifeMarker = this.googleMap.addMarker(m_life);
        Marker dongsungMarker = this.googleMap.addMarker(m_dongsung);
        Marker woosungMarker = this.googleMap.addMarker(m_woosung);
        Marker hansinMarker = this.googleMap.addMarker(m_hansin);
        Marker cheongsolMarker = this.googleMap.addMarker(m_cheongsol);
        Marker bora1Marker = this.googleMap.addMarker(m_bora1);
        Marker bora2Marker = this.googleMap.addMarker(m_bora2);
        Marker dreamTreeMarker = this.googleMap.addMarker(m_dreamTree);
        Marker samhead1Marker = this.googleMap.addMarker(m_samhead1);
        Marker samhead2Marker = this.googleMap.addMarker(m_samhead2);
        Marker silverMarker = this.googleMap.addMarker(m_silver);
        Marker milkywayMarker = this.googleMap.addMarker(m_milkyway);
        Marker nokwonMarker = this.googleMap.addMarker(m_nokwon);
        Marker hyangchonMarker = this.googleMap.addMarker(m_hyangchon);
        Marker bluebirdMarker = this.googleMap.addMarker(m_bluebird);


        googleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                float zoomLevel = googleMap.getCameraPosition().zoom;

                int desiredWidth = (int) (10 * zoomLevel); // 원하는 아이콘의 너비
                int desiredHeight = (int) (10 * zoomLevel); // 원하는 아이콘의 높이

                Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.apartment);
                Bitmap resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, desiredWidth, desiredHeight, false);
                BitmapDescriptor resizedBitmapDescriptor = BitmapDescriptorFactory.fromBitmap(resizedBitmap);

                cloverMarker.setIcon(resizedBitmapDescriptor);
                mokRyeonMarker.setIcon(resizedBitmapDescriptor);
                hanmaruMarker.setIcon(resizedBitmapDescriptor);
                hanmaruSamsungMarker.setIcon(resizedBitmapDescriptor);
                yungjinMarker.setIcon(resizedBitmapDescriptor);
                doongjiMarker.setIcon(resizedBitmapDescriptor);
                garamMarker.setIcon(resizedBitmapDescriptor);
                sindongaMarker.setIcon(resizedBitmapDescriptor);
                sujungMarker.setIcon(resizedBitmapDescriptor);
                lifeMarker.setIcon(resizedBitmapDescriptor);
                dongsungMarker.setIcon(resizedBitmapDescriptor);
                woosungMarker.setIcon(resizedBitmapDescriptor);
                hansinMarker.setIcon(resizedBitmapDescriptor);
                cheongsolMarker.setIcon(resizedBitmapDescriptor);
                bora1Marker.setIcon(resizedBitmapDescriptor);
                bora2Marker.setIcon(resizedBitmapDescriptor);
                dreamTreeMarker.setIcon(resizedBitmapDescriptor);
                samhead1Marker.setIcon(resizedBitmapDescriptor);
                samhead2Marker.setIcon(resizedBitmapDescriptor);
                silverMarker.setIcon(resizedBitmapDescriptor);
                milkywayMarker.setIcon(resizedBitmapDescriptor);
                nokwonMarker.setIcon(resizedBitmapDescriptor);
                hyangchonMarker.setIcon(resizedBitmapDescriptor);
                bluebirdMarker.setIcon(resizedBitmapDescriptor);

            }
        });


//        googleMap.setMyLocationEnabled(true);
//        googleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
//            @Override
//            public boolean onMyLocationButtonClick() {
//                startLocationUpdates();
//                return true;
//            }
//        });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
        } else {
            checkLocationPermissionWithRationale();
        }

    }


//    public BitmapDescriptor setIcon(Activity context, int drawableID) {
//        Drawable drawable = ActivityCompat.getDrawable(context, drawableID);
//        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
//        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(bitmap);
//        drawable.draw(canvas);
//
//        return BitmapDescriptorFactory.fromBitmap(bitmap);
//    }

//    private void startLocationUpdates() {
//        LocationRequest locationRequest = LocationRequest.create()
//                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
//                .setInterval(5000) // 5 seconds
//                .setFastestInterval(2000); // 2 seconds
//
//        LocationServices.getFusedLocationProviderClient(this)
//                .requestLocationUpdates(locationRequest, new LocationCallback() {
//                    @Override
//                    public void onLocationResult(LocationResult locationResult) {
//                        if (locationResult != null) {
//                            Location location = locationResult.getLastLocation();
//                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//                            polylinePoints.add(latLng);
//                            updatePolyline();
//                        }
//                    }
//                }, Looper.getMainLooper());
//    }
//
//    private void updatePolyline() {
//        if (polyline != null) {
//            polyline.remove();
//        }
//
//        PolylineOptions polylineOptions = new PolylineOptions()
//                .color(Color.BLUE)
//                .width(5f)
//                .addAll(polylinePoints);
//
//        polyline = googleMap.addPolyline(polylineOptions);
//    }
//
//    @Override
//    public void onLocationChanged(Location location) {
//        // 위치 변경 시 호출되는 메서드
//        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//        polylinePoints.add(latLng);
//        updatePolyline();
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        // 액티비티가 다시 활성화될 때 위치 업데이트를 시작
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            startLocationUpdates();
//        } else {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
//        }
//    }
//
//    private LocationCallback locationCallback = new LocationCallback() {
//        @Override
//        public void onLocationResult(LocationResult locationResult) {
//            if (locationResult != null) {
//                Location location = locationResult.getLastLocation();
//                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//                polylinePoints.add(latLng);
//                updatePolyline();
//            }
//        }
//    };
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        // 액티비티가 일시 정지될 때 위치 업데이트를 중지
//        LocationServices.getFusedLocationProviderClient(this).removeLocationUpdates(locationCallback);
//    }


    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private void checkLocationPermissionWithRationale() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(this)
                        .setTitle("위치정보")
                        .setMessage("이 앱을 사용하기 위해서는 위치정보에 접근이 필요합니다. 위치정보 접근을 허용하여 주세요.")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(MapTrace.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        }).create().show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {
            if (grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 위치 권한이 허용된 경우 처리할 작업
                    googleMap.setMyLocationEnabled(true);
                } else {
                    // 위치 권한이 거부된 경우 처리할 작업
                    // 예: 사용자에게 위치 권한이 필요하다는 메시지를 표시하거나 다른 조치를 취함
                    Toast.makeText(this, "위치 권한이 필요합니다.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
//
//    위의 코드는 버튼을 클릭하여 타임라인 기능을 토글하는 기능을 구현한 코드입니다. `onToggleTimelineClicked()` 메서드는 버튼 클릭 이벤트를 처리하고, 타임라인 기능 상태에 따라 `showTimelineRecords()` 메서드를 호출하여 타임라인 기록을 가져오거나 `clearTimeline()` 메서드를 호출하여 타임라인 기록을 제거합니다. 타임라인 기록을 가져오는 작업에는 Places API를 사용하여 현재 장소를 가져오는 예시가 포함되어 있습니다.
//    또한, 위치 권한을 확인하고 요청하는 부분도 포함되어 있어, 사용자가 위치 권한을 허용해야지만 내 타임라인 기록을 가져오고 폴리라인을 그릴 수 있습니다. `checkLocationPermissionWithRationale()` 메서드는 위치 권한을 확인하고, 권한이 없는 경우에는 권한 요청 다이얼로그를 표시합니다. 사용자가 위치 권한을 허용하거나 거부한 후에는 `onRequestPermissionsResult()` 메서드가 호출되어 처리합니다.
//    위의 코드를 참고하여 타임라인 기능을 토글하는 버튼을 구현하실 수 있습니다. 필요에 따라 추가적인 기능을 구현하고 수정하여 사용해보세요.