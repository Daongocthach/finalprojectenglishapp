package com.group15.finalprojectenglishapp.admin.dienkhuyet;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;



import java.util.Arrays;
import com.group15.finalprojectenglishapp.R;
import com.group15.finalprojectenglishapp.admin.bohoctap.AddBoHocTapActivity;
import com.group15.finalprojectenglishapp.database.Database;

public class AddDienKhuyetActivity extends AppCompatActivity {
    ImageView imgBack, imgAdd;
    EditText edtNoiDung, edtGoiY, edtDapAn;
    final String DATABASE_NAME = "HocNgonNgu.db";
    SQLiteDatabase database;
    int idBDK = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dienkhuyet);
        imgBack = (ImageView) findViewById(R.id.imgBackAddDK);
        imgAdd = (ImageView) findViewById(R.id.imgAddDK);
        edtNoiDung = (EditText) findViewById(R.id.edtCauHoiAddDK);
        edtGoiY = (EditText) findViewById(R.id.edtGoiYAddDK);
        edtDapAn = (EditText) findViewById(R.id.edtDapAnAddDK);
        idBDK = getIntent().getIntExtra("idBoDienKhuyet", -1);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddDienKhuyetActivity.this, AdminDienKhuyetActivity.class);
                intent.putExtra("idBoDienKhuyet", idBDK);
                startActivity(intent);
            }
        });
        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String noidung = edtNoiDung.getText().toString();
                String dapan = edtDapAn.getText().toString();
                String goiy = edtGoiY.getText().toString();
                if (noidung == "" || dapan == "" || goiy == ""){
                    showAlertDialog("Chưa điền đầy đủ thông tin");
                    Toast.makeText(AddDienKhuyetActivity.this, "Chưa điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else {
                    Boolean check = checkDapAnInGoiY(dapan, goiy);
                    if (check == true) {
                        Boolean result = addDienKhuyet(idBDK, noidung, dapan, goiy);
                        if (result == true) {
                            showAlertDialog("Thêm thành công");
                            Toast.makeText(AddDienKhuyetActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AddDienKhuyetActivity.this, AdminDienKhuyetActivity.class);
                            intent.putExtra("idBoDienKhuyet", idBDK);
                            startActivity(intent);
                        }
                        else {
                            showAlertDialog("Thêm thất bại");
                            Toast.makeText(AddDienKhuyetActivity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        showAlertDialog("Vui lòng nhập đúng đáp án");
                        Toast.makeText(AddDienKhuyetActivity.this, "Vui lòng nhập đúng đáp án", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private Boolean addDienKhuyet(int idbo, String noidung, String dapan, String goiy) {
        database = Database.initDatabase(AddDienKhuyetActivity.this, DATABASE_NAME);
        ContentValues values = new ContentValues();
        values.put("ID_Bo", idbo);
        values.put("NoiDung", noidung);
        values.put("DapAn", dapan);
        values.put("GoiY", goiy);
        long result = database.insert("DienKhuyet", null, values);
        if (result == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    private Boolean checkDapAnInGoiY(String dapan, String goiy) {
        goiy = goiy.replaceAll("\\W", " ");
        goiy = goiy.trim().replaceAll("\\s{2,}", " ");
        String[] dapAn = goiy.split(" ");
        if (Arrays.asList(dapAn).contains(dapan)) {
            return true;
        }
        else {
            return false;
        }
    }
    private void showAlertDialog(String message) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(AddDienKhuyetActivity.this);
        builder.setMessage(message);

        final AlertDialog dialog = builder.create();
        dialog.show();

        new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Không cần thực hiện hành động trong onTick()
            }

            @Override
            public void onFinish() {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        }.start();
    }
}
