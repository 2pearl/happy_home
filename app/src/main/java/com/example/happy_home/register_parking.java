package com.example.happy_home;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class register_parking extends AppCompatActivity {

    Button add_parking_button;
    private IntentIntegrator qrScan;
    SharedPreferences sharedPreferences;
    String user_residence;
    String device_code="";

    String parking_name;
    Integer parking_max_num;

    //뒤로가기 눌렀을 때
    @Override
    public void onBackPressed() {

        Intent intent= new Intent(getApplicationContext(), Fragment_admin_main.class);
        intent.putExtra("menu_type",2);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_parking);

        sharedPreferences=this.getSharedPreferences("my_info", MODE_PRIVATE);
        user_residence = sharedPreferences.getString("residence", "");//사용자 거주지 저장하는 변수

        //주차시설 등록
        Button add_parking_button=(Button) findViewById(R.id.add_park_device);
        add_parking_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                parking_name = ((EditText) findViewById(R.id.parking_name)).getText().toString();
                String parking_max=((EditText) findViewById(R.id.parking_max_num)).getText().toString();

                if(parking_name.length()==0||String.valueOf(parking_max).length()==0)
                    Toast.makeText(register_parking.this, "입력하지않은 부분이 있습니다!", Toast.LENGTH_SHORT).show();
                else{
                    parking_max_num = Integer.parseInt(parking_max);
                    addQr();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null) {
            //qrcode 가 없으면
            if (result.getContents() == null) {

                Toast.makeText(register_parking.this, "취소!", Toast.LENGTH_SHORT).show();
            }
            else {//qrcode 결과가 있으면

                try {//data를 json으로 변환

                    JSONObject obj = new JSONObject(result.getContents());
                    device_code=obj.getString("device_code");

                    if(device_code.contains("count")){
                        Toast.makeText(register_parking.this, "스캔완료!", Toast.LENGTH_SHORT).show();

                        //add_parking_button.setEnabled(false);

                        //파이어베이스에 장치 추가
                        DatabaseReference deviceCode_ref = FirebaseDatabase.getInstance().getReference();

                        HashMap<String, Object> new_device = new HashMap();
                        new_device.put("/device/"+device_code+"/residence/",user_residence);
                        new_device.put("/device/"+device_code+"/deviceCode/",device_code);
                        new_device.put("/device/"+device_code+"/type/","parking");
                        deviceCode_ref.updateChildren(new_device);

                        //파이어베이스에 편의시설 추가
                        DatabaseReference park_ref = FirebaseDatabase.getInstance().getReference();

                        HashMap<String, Object> new_park = new HashMap();
                        new_park.put("/residence/"+user_residence+"/parking/"+device_code+"/facility_name/",parking_name);
                        new_park.put("/residence/"+user_residence+"/parking/"+device_code+"/device_code/",device_code);
                        new_park.put("/residence/"+user_residence+"/parking/"+device_code+"/max_num/",parking_max_num);
                        new_park.put("/residence/"+user_residence+"/parking/"+device_code+"/count/",0);
                        park_ref.updateChildren(new_park);

                        finish();

                        Intent intent= new Intent(getApplicationContext(), Fragment_admin_main.class);
                        intent.putExtra("menu_type",2);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(register_parking.this, "잘못스캔하셨습니다!", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void addQr(){

        qrScan = new IntentIntegrator(this);
        qrScan.setOrientationLocked(false);
        qrScan.setPrompt("QR코드를 인식해주세요.");
        qrScan.initiateScan();
    }
}