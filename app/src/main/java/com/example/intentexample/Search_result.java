package com.example.intentexample;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Search_result extends AppCompatActivity implements Serializable {
    private DatabaseReference mDatabaseRef;
    TextView search_result_txt;
    String aptCode;
    private String aptName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        search_result_txt = findViewById(R.id.search_result_txt);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("homes");
        String name = getIntent().getStringExtra("selectedItem");
        HashMap<String, String> aptcode_list = (HashMap<String, String>) getIntent().getSerializableExtra("aptCodeList");
        aptCode = aptcode_list.get(name);
        System.out.println("selected item: " + name);
        System.out.println("apartment code: " + aptCode);

        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1613000/AptBasisInfoService1/getAphusBassInfo"); /*URL*/
        try {
            urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=Xh5RbZxdYWENWxQmXkQ0C74QjxWhBkB4Bs/RoF9na3zU0JG1zIyAc5cobBL0UuzLkiKpku8rSFF7g/yUWFX/Fw=="); /*Service Key*/
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        try {
            urlBuilder.append("&" + URLEncoder.encode("kaptCode","UTF-8") + "=" + URLEncoder.encode(aptCode, "UTF-8")); /*시도코드*/
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        URL url = null;
        try {
            url = new URL(urlBuilder.toString());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            conn.setRequestMethod("GET");
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        }
        conn.setRequestProperty("Content-type", "application/json");
        try {
            System.out.println("Response code: " + conn.getResponseCode());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BufferedReader rd;
        try {
            if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                System.out.println("API Connection: " + conn);
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while (true) {
            try {
                if ((line = rd.readLine()) == null) break;
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
            sb.append(line);
        }
        try {
            rd.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        conn.disconnect();

        // xml을 파싱해주는 객체를 생성
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }

        // xml 문자열은 InputStream으로 변환
        InputStream is = new ByteArrayInputStream(sb.toString().getBytes());
        // 파싱 시작
        Document doc = null;
        try {
            doc = documentBuilder.parse(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
        // 최상위 노드 찾기
        Element element = doc.getDocumentElement();
        String bjdCode = element.getElementsByTagName("bjdCode").item(0).getFirstChild().getNodeValue();
//        search_result_txt.append("bjdCode: " + bjdCode + "\n");
        String kaptCode = element.getElementsByTagName("codeAptNm").item(0).getFirstChild().getNodeValue();
//        search_result_txt.append("kaptCode: " + kaptCode + "\n");
        String codeHallNm = element.getElementsByTagName("codeHallNm").item(0).getFirstChild().getNodeValue();
//        search_result_txt.append("codeHallNm: " + codeHallNm + "\n");
        String codeMgrNm = element.getElementsByTagName("codeMgrNm").item(0).getFirstChild().getNodeValue();
//        search_result_txt.append("codeMgrNm: " + codeMgrNm + "\n");
        String codeSaleNm = element.getElementsByTagName("codeSaleNm").item(0).getFirstChild().getNodeValue();
//        search_result_txt.append("codeSaleNm: " + codeSaleNm + "\n");
        String doroJuso = element.getElementsByTagName("doroJuso").item(0).getFirstChild().getNodeValue();
//        search_result_txt.append("doroJuso: " + doroJuso + "\n");
        String hoCnt = element.getElementsByTagName("hoCnt").item(0).getFirstChild().getNodeValue();
//        search_result_txt.append("hoCnt: " + hoCnt + "\n");
        String kaptAcompany = element.getElementsByTagName("kaptAcompany").item(0).getFirstChild().getNodeValue();
//        search_result_txt.append("kaptAcompany: " + kaptAcompany + "\n");
        String kaptBcompany = element.getElementsByTagName("kaptBcompany").item(0).getFirstChild().getNodeValue();
//        search_result_txt.append("kaptBcompany: " + kaptBcompany + "\n");
        String kaptAddr = element.getElementsByTagName("kaptAddr").item(0).getFirstChild().getNodeValue();
//        search_result_txt.append("kaptAddr: " + kaptAddr + "\n");
        String kaptDongCnt = element.getElementsByTagName("kaptDongCnt").item(0).getFirstChild().getNodeValue();
//        search_result_txt.append("kaptDongCnt: " + kaptDongCnt + "\n");
        String kaptFax = element.getElementsByTagName("kaptFax").item(0).getFirstChild().getNodeValue();
//        search_result_txt.append("kaptFax: " + kaptFax + "\n");
        String kaptMarea = element.getElementsByTagName("kaptMarea").item(0).getFirstChild().getNodeValue();
//        search_result_txt.append("kaptMarea: " + kaptMarea + "\n");
        String kaptMparea_60 = element.getElementsByTagName("kaptMparea_60").item(0).getFirstChild().getNodeValue();
//        search_result_txt.append("kaptMparea_60: " + kaptMparea_60 + "\n");
        String kaptMparea_85 = element.getElementsByTagName("kaptMparea_85").item(0).getFirstChild().getNodeValue();
//        search_result_txt.append("kaptMparea_85: " + kaptMparea_85 + "\n");
        String kaptMparea_135 = element.getElementsByTagName("kaptMparea_135").item(0).getFirstChild().getNodeValue();
//        search_result_txt.append("kaptMparea_135: " + kaptMparea_135 + "\n");
        String kaptMparea_136 = element.getElementsByTagName("kaptMparea_136").item(0).getFirstChild().getNodeValue();
//        search_result_txt.append("kaptMparea_136: " + kaptMparea_136 + "\n");
        String kaptTarea = element.getElementsByTagName("kaptTarea").item(0).getFirstChild().getNodeValue();
//        search_result_txt.append("kaptTarea: " + kaptTarea + "\n");
        String kaptTel = element.getElementsByTagName("kaptTel").item(0).getFirstChild().getNodeValue();
//        search_result_txt.append("kaptTel: " + kaptTel + "\n");
        String kaptUrl = element.getElementsByTagName("kaptUrl").item(0).getFirstChild().getNodeValue();
//        search_result_txt.append("kaptUrl: " + kaptUrl + "\n");
        String kaptUsedate = element.getElementsByTagName("kaptUsedate").item(0).getFirstChild().getNodeValue();
//        search_result_txt.append("kaptUsedate: " + kaptUsedate + "\n");
        String kaptdaCnt = element.getElementsByTagName("kaptTarea").item(0).getFirstChild().getNodeValue();
//        search_result_txt.append("kaptdaCnt: " + kaptdaCnt + "\n");
        String privArea = element.getElementsByTagName("privArea").item(0).getFirstChild().getNodeValue();
//        search_result_txt.append("privArea: " + privArea + "\n");

        search_result_txt.append(name);
        TableLayout tableLayout = findViewById(R.id.tableLayout);
        TableRow headerRow = tableLayout.findViewById(R.id.headerRow);

//        addRowToTable("법정동 주소", kaptAddr, tableLayout);
        addRowToTable("도로명 주소", doroJuso, tableLayout);
//        addRowToTable("법정 코드", bjdCode, tableLayout);
        addRowToTable("단지 코드", kaptCode, tableLayout);
        addRowToTable("시행사", kaptAcompany, tableLayout);
        addRowToTable("시공사", kaptBcompany, tableLayout);
        addRowToTable("사용승인일", kaptUsedate, tableLayout);
        addRowToTable("동 수", kaptDongCnt, tableLayout);
        addRowToTable("총 세대수", hoCnt, tableLayout);
        addRowToTable("복도 유형", codeHallNm, tableLayout);
        addRowToTable("관리 방식", codeMgrNm, tableLayout);
        addRowToTable("분양 형태", codeSaleNm, tableLayout);
//        addRowToTable("세대수", kaptdaCnt, tableLayout);
        addRowToTable("60㎡이하 세대수", kaptMparea_60, tableLayout);
        addRowToTable("85㎡이하 세대수", kaptMparea_85, tableLayout);
        addRowToTable("135㎡이하 세대수", kaptMparea_135, tableLayout);
        addRowToTable("136㎡이하 세대수", kaptMparea_136, tableLayout);
        addRowToTable("단지 전용면적합", privArea+"㎡", tableLayout);
        addRowToTable("관리비부과 면적", kaptMarea+"㎡", tableLayout);
        addRowToTable("건물대장 연면적", kaptTarea+"㎡", tableLayout);
        addRowToTable("관리사무소 연락처", kaptTel, tableLayout);
        addRowToTable("관리사무소 팩스", kaptFax, tableLayout);
        addRowToTable("홈페이지 주소", kaptUrl, tableLayout);


        search_result_txt = findViewById(R.id.search_result_txt);
        Button btn_checkList = findViewById(R.id.btn_checkList);
        btn_checkList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aptName = search_result_txt.getText().toString();
                Intent intent = new Intent(Search_result.this, WriteCheckList.class);
                intent.putExtra("aptName",aptName);
                startActivity(intent);
            }
        });

    }

    private void addRowToTable(String key, String value, TableLayout tableLayout) {
        TableRow row = new TableRow(this);
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
        );
        row.setLayoutParams(layoutParams);

        TextView keyTextView = new TextView(this);
        keyTextView.setText(key);
        keyTextView.setPadding(8, 8, 8, 8);
        row.addView(keyTextView);

        TextView valueTextView = new TextView(this);
        valueTextView.setText(value);
        valueTextView.setPadding(8, 8, 8, 8);
        row.addView(valueTextView);

        tableLayout.addView(row);
    }
}
