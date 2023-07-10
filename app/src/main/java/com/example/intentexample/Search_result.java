package com.example.intentexample;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import android.widget.CheckBox;
import android.widget.CheckBox;
import android.widget.CompoundButton;


public class Search_result extends AppCompatActivity implements Serializable {
    private DatabaseReference mDatabaseRef;
    private FirebaseAuth mFirebaseAuth;

    TextView search_result_txt;
    String aptCode;
    private String aptName;
    CheckBox myCheckBox;
    HashMap<String , ?> apt_checklist;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        search_result_txt = findViewById(R.id.search_result_txt);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("homes");
        mFirebaseAuth = FirebaseAuth.getInstance();
        myCheckBox = findViewById(R.id.myCheckBox);
        String name = getIntent().getStringExtra("selectedItem");
        HashMap<String, String> aptcode_list = (HashMap<String, String>) getIntent().getSerializableExtra("aptCodeList");
        aptCode = aptcode_list.get(name);
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        ListView search_result_lv = findViewById(R.id.search_result_lv);
        System.out.println("selected item: " + name);
        System.out.println("apartment code: " + aptCode);


        search_result_txt.append(name);
        TableLayout tableLayout = findViewById(R.id.tableLayout);
        TableRow headerRow = tableLayout.findViewById(R.id.headerRow);

        Button btn_checkList = findViewById(R.id.btn_checkList);
        btn_checkList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aptName = search_result_txt.getText().toString();
                Intent intent = new Intent(Search_result.this, WriteCheckList.class);
                intent.putExtra("aptName", aptName);
                startActivity(intent);
            }
        });
        CheckBox saveAptCodeCheckBox = findViewById(R.id.myCheckBox);
        saveAptCodeCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // 체크박스가 체크되었을 때
                    // 파이어베이스에 aptCode 저장
                    mDatabaseRef.child("UserAccount")
                            .child(mFirebaseAuth.getUid())
                            .child("favorite")
                            .child(name)
                            .setValue(aptCode);

                } else {
                    // 체크박스가 해제되었을 때
                    // 파이어베이스에서 aptCode 삭제
                    mDatabaseRef.child("UserAccount")
                            .child(mFirebaseAuth.getUid())
                            .child("favorite")
                            .child(name)
                            .removeValue();

                }
            }
        });


        // get other checklists
        ArrayList<ApartmentInfo> aptname_list = new ArrayList<ApartmentInfo>();
        mDatabaseRef.child("Checklist").child(name).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                apt_checklist = (HashMap<String, ?>) task.getResult().getValue();
                if (apt_checklist != null) {
                    for (String i : apt_checklist.keySet()){ //저장된 key값 확인
                        System.out.println("[Key]:" + i + " [Value]:" + apt_checklist.get(i));
                        HashMap<String, String> comment = (HashMap<String, String>) apt_checklist.get(i);
                        String user = i;
                        aptname_list.add(new ApartmentInfo(name, comment.get("한줄평"), "", user));
                    }

                    checklistAdapter adapter = new checklistAdapter(getApplicationContext(), aptname_list);
                    search_result_lv.setAdapter(adapter);
                }

            }
        });

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
        if (element != null) {

            String kaptCode = "";
            NodeList codeAptNmList = element.getElementsByTagName("codeAptNm");
            if (codeAptNmList.getLength() > 0) {
                Node codeAptNmNode = codeAptNmList.item(0);
                if (codeAptNmNode.hasChildNodes()) {
                    kaptCode = codeAptNmNode.getFirstChild().getNodeValue();
                }
            }

            String codeHallNm = "";
            NodeList codeHallNmList = element.getElementsByTagName("codeHallNm");
            if (codeHallNmList.getLength() > 0) {
                Node codeHallNmNode = codeHallNmList.item(0);
                if (codeHallNmNode.hasChildNodes()) {
                    codeHallNm = codeHallNmNode.getFirstChild().getNodeValue();
                }
            }

            String codeMgrNm = "";
            NodeList codeMgrNmList = element.getElementsByTagName("codeMgrNm");
            if (codeMgrNmList.getLength() > 0) {
                Node codeMgrNmNode = codeMgrNmList.item(0);
                if (codeMgrNmNode.hasChildNodes()) {
                    codeMgrNm = codeMgrNmNode.getFirstChild().getNodeValue();
                }
            }

            String doroJuso = "";
            NodeList doroJusoList = element.getElementsByTagName("doroJuso");
            if (doroJusoList.getLength() > 0) {
                Node doroJusoNode = doroJusoList.item(0);
                if (doroJusoNode.hasChildNodes()) {
                    doroJuso = doroJusoNode.getFirstChild().getNodeValue();
                }
            }

            String hoCnt = "";
            NodeList hoCntList = element.getElementsByTagName("hoCnt");
            if (hoCntList.getLength() > 0) {
                Node hoCntNode = hoCntList.item(0);
                if (hoCntNode.hasChildNodes()) {
                    hoCnt = hoCntNode.getFirstChild().getNodeValue();
                }
            }

            String kaptAcompany = "";
            NodeList kaptAcompanyList = element.getElementsByTagName("kaptAcompany");
            if (kaptAcompanyList.getLength() > 0) {
                Node kaptAcompanyNode = kaptAcompanyList.item(0);
                if (kaptAcompanyNode.hasChildNodes()) {
                    kaptAcompany = kaptAcompanyNode.getFirstChild().getNodeValue();
                }
            }

            String kaptBcompany = "";
            NodeList kaptBcompanyList = element.getElementsByTagName("kaptBcompany");
            if (kaptBcompanyList.getLength() > 0) {
                Node kaptBcompanyNode = kaptBcompanyList.item(0);
                if (kaptBcompanyNode.hasChildNodes()) {
                    kaptBcompany = kaptBcompanyNode.getFirstChild().getNodeValue();
                }
            }

            String kaptDongCnt = "";
            NodeList kaptDongCntList = element.getElementsByTagName("kaptDongCnt");
            if (kaptDongCntList.getLength() > 0) {
                Node kaptDongCntNode = kaptDongCntList.item(0);
                if (kaptDongCntNode.hasChildNodes()) {
                    kaptDongCnt = kaptDongCntNode.getFirstChild().getNodeValue();
                }
            }

            String kaptMparea_60 = "";
            NodeList kaptMparea_60List = element.getElementsByTagName("kaptMparea_60");
            if (kaptMparea_60List.getLength() > 0) {
                Node kaptMparea_60Node = kaptMparea_60List.item(0);
                if (kaptMparea_60Node.hasChildNodes()) {
                    kaptMparea_60 = kaptMparea_60Node.getFirstChild().getNodeValue();
                }
            }

            String kaptMparea_85 = "";
            NodeList kaptMparea_85List = element.getElementsByTagName("kaptMparea_85");
            if (kaptMparea_85List.getLength() > 0) {
                Node kaptMparea_85Node = kaptMparea_85List.item(0);
                if (kaptMparea_85Node.hasChildNodes()) {
                    kaptMparea_85 = kaptMparea_85Node.getFirstChild().getNodeValue();
                }
            }

            String kaptMparea_135 = "";
            NodeList kaptMparea_135List = element.getElementsByTagName("kaptMparea_135");
            if (kaptMparea_135List.getLength() > 0) {
                Node kaptMparea_135Node = kaptMparea_135List.item(0);
                if (kaptMparea_135Node.hasChildNodes()) {
                    kaptMparea_135 = kaptMparea_135Node.getFirstChild().getNodeValue();
                }
            }

            String kaptMparea_136 = "";
            NodeList kaptMparea_136List = element.getElementsByTagName("kaptMparea_136");
            if (kaptMparea_136List.getLength() > 0) {
                Node kaptMparea_136Node = kaptMparea_136List.item(0);
                if (kaptMparea_136Node.hasChildNodes()) {
                    kaptMparea_136 = kaptMparea_136Node.getFirstChild().getNodeValue();
                }
            }


            String kaptTel = "";
            NodeList kaptTelList = element.getElementsByTagName("kaptTel");
            if (kaptTelList.getLength() > 0) {
                Node kaptTelNode = kaptTelList.item(0);
                if (kaptTelNode.hasChildNodes()) {
                    kaptTel = kaptTelNode.getFirstChild().getNodeValue();
                }
            }

            String kaptUsedate = "";
            NodeList kaptUsedateList = element.getElementsByTagName("kaptUsedate");
            if (kaptUsedateList.getLength() > 0) {
                Node kaptUsedateNode = kaptUsedateList.item(0);
                if (kaptUsedateNode.hasChildNodes()) {
                    kaptUsedate = kaptUsedateNode.getFirstChild().getNodeValue();
                }
            }


            addRowToTable("도로명 주소", doroJuso, tableLayout);
            addRowToTable("단지 코드", kaptCode, tableLayout);
            addRowToTable("시행사", kaptAcompany, tableLayout);
            addRowToTable("시공사", kaptBcompany, tableLayout);
            addRowToTable("사용승인일", kaptUsedate, tableLayout);
            addRowToTable("동 수", kaptDongCnt, tableLayout);
            addRowToTable("총 세대수", hoCnt, tableLayout);
            addRowToTable("복도 유형", codeHallNm, tableLayout);
            addRowToTable("관리 방식", codeMgrNm, tableLayout);
            addRowToTable("60㎡이하 세대수", kaptMparea_60, tableLayout);
            addRowToTable("85㎡이하 세대수", kaptMparea_85, tableLayout);
            addRowToTable("135㎡이하 세대수", kaptMparea_135, tableLayout);
            addRowToTable("136㎡이하 세대수", kaptMparea_136, tableLayout);
            addRowToTable("관리사무소 연락처", kaptTel, tableLayout);
        }



        search_result_txt = findViewById(R.id.search_result_txt);
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