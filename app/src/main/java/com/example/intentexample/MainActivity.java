package com.example.intentexample;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.intentexample.SubActivity;
import com.example.intentexample.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity {

    private Button btn_login;
    private EditText et_id;
    private String str;

    private int[] apartmentImages = {R.drawable.apartment1, R.drawable.apartment2, R.drawable.apartment3};
    private ImageView imageView;
    private int currentIndex = 0;
    private Handler handler;
    private Runnable runnable;
    private DatabaseReference mDatabaseRef;
    ImageView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("homes");

        // Fetch Apartment Data
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

//        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1613000/AptListService2/getSidoAptList"); /*URL*/
//        try {
//            urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=Xh5RbZxdYWENWxQmXkQ0C74QjxWhBkB4Bs/RoF9na3zU0JG1zIyAc5cobBL0UuzLkiKpku8rSFF7g/yUWFX/Fw=="); /*Service Key*/
//        } catch (UnsupportedEncodingException e) {
//            throw new RuntimeException(e);
//        }
//        try {
//            urlBuilder.append("&" + URLEncoder.encode("sidoCode","UTF-8") + "=" + URLEncoder.encode("30", "UTF-8")); /*시도코드*/
//        } catch (UnsupportedEncodingException e) {
//            throw new RuntimeException(e);
//        }
//        try {
//            urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
//        } catch (UnsupportedEncodingException e) {
//            throw new RuntimeException(e);
//        }
//        try {
//            urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("30000", "UTF-8")); /*목록 건수*/
//        } catch (UnsupportedEncodingException e) {
//            throw new RuntimeException(e);
//        }
//        URL url = null;
//        try {
//            url = new URL(urlBuilder.toString());
//        } catch (MalformedURLException e) {
//            throw new RuntimeException(e);
//        }
//        HttpURLConnection conn = null;
//        try {
//            conn = (HttpURLConnection) url.openConnection();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        try {
//            conn.setRequestMethod("GET");
//        } catch (ProtocolException e) {
//            throw new RuntimeException(e);
//        }
//        conn.setRequestProperty("Content-type", "application/json");
//        try {
//            System.out.println("Response code: " + conn.getResponseCode());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        BufferedReader rd;
//        try {
//            if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
//                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//                System.out.println("API Connection: " + conn);
//            } else {
//                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        StringBuilder sb = new StringBuilder();
//        String line;
//        while (true) {
//            try {
//                if ((line = rd.readLine()) == null) break;
//            }
//            catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            sb.append(line);
//        }
//        try {
//            rd.close();
//        }
//        catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        conn.disconnect();
//        System.out.println("bbbbbbb " + sb.toString());
//        // xml을 파싱해주는 객체를 생성
//        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//        DocumentBuilder documentBuilder = null;
//        try {
//            documentBuilder = factory.newDocumentBuilder();
//        } catch (ParserConfigurationException e) {
//            throw new RuntimeException(e);
//        }
//
//        // xml 문자열은 InputStream으로 변환
//        InputStream is = new ByteArrayInputStream(sb.toString().getBytes());
//        // 파싱 시작
//        Document doc = null;
//        try {
//            doc = documentBuilder.parse(is);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } catch (SAXException e) {
//            throw new RuntimeException(e);
//        }
//        // 최상위 노드 찾기
//        Element element = doc.getDocumentElement();
//        NodeList items = element.getElementsByTagName("item");
//        NodeList kaptCode = element.getElementsByTagName("kaptCode");
//        NodeList kaptName = element.getElementsByTagName("kaptName");
//        int n = items.getLength();
//
//        for (int i=0; i<n; i++) {
//            // Get element
//            String name = kaptName.item(i).getFirstChild().getNodeValue();
//            String code = kaptCode.item(i).getFirstChild().getNodeValue();
//            mDatabaseRef.child("Apartments").child(name).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//                @Override
//                public void onComplete(@NonNull Task<DataSnapshot> task) {
//                    if (!task.isSuccessful()) {
//                        mDatabaseRef.child("Apartments").child(name).setValue(code);
//                    }
//                    else {
//                        Log.d("firebase", "Data Already Added");
//                    }
//                }
//            });
//        }

        Button btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent); // 액티비티 이동
            }
        });

        Button btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignIn.class);
                startActivity(intent);
            }
        });

    }


}