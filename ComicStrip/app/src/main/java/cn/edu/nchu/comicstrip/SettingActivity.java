package cn.edu.nchu.comicstrip;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.Properties;

import cn.edu.nchu.comicstrip.Entity.Page;

public class SettingActivity extends AppCompatActivity {
    private Button bt_updateWord;
    private Button bt_changeType;
    int choice = 2;
    int typechoice = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        bt_updateWord = (Button) findViewById(R.id.bt_updateWord);
        bt_changeType = (Button) findViewById(R.id.bt_changeType);

        ExternalConfigure configure = new ExternalConfigure(SettingActivity.this);

        bt_updateWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] items = {"小号", "默认", "中号", "大号", "超大"};
                AlertDialog.Builder singleChoiceDialog = new AlertDialog.Builder(SettingActivity.this);
                singleChoiceDialog.setTitle("设置字体大小");
                singleChoiceDialog.setIcon(R.mipmap.ic_launcher);
                singleChoiceDialog.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        choice = i;
                    }
                });
                singleChoiceDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int size = 20;
                        Toast.makeText(SettingActivity.this, "您选择了" + items[choice], Toast.LENGTH_SHORT).show();
                        switch (items[choice]) {
                            case "小号":
                               size = 10;
                                break;
                            case "默认":
                                size = 20;
                                break;
                            case "中号":
                                size = 30;
                                break;
                            case "大号":
                                size = 40;
                                break;
                            case "超大":
                                size = 50;
                                break;
                            default:
                                size = 20;

                        }
                        Properties properties = new Properties();
                        properties.setProperty("textSize",String.valueOf(size));
                        try {
                            configure.writeDataToEx("word.ini",properties);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Intent intent = new Intent(SettingActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                });
                singleChoiceDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(SettingActivity.this, "您取消了选择", Toast.LENGTH_SHORT).show();
                    }
                });
                singleChoiceDialog.show();
            }

        });

        bt_changeType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] items = {"左右滑动", "上下滑动"};
                AlertDialog.Builder singleChoiceDialog = new AlertDialog.Builder(SettingActivity.this);
                singleChoiceDialog.setTitle("设置翻页方式");
                singleChoiceDialog.setIcon(R.mipmap.ic_launcher);
                singleChoiceDialog.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        typechoice = i;
                    }
                });
                singleChoiceDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String type = "1";
                        Toast.makeText(SettingActivity.this, "您选择了" + items[typechoice], Toast.LENGTH_SHORT).show();
                        switch (items[typechoice]) {
                            case "左右滑动":
                                type = "2";
                                break;
                            case "上下滑动":
                                type = "1";
                                break;
                            default:
                                type = "1";

                        }
                        Properties properties = new Properties();
                        properties.setProperty("changeType",type);
                        try {
                            configure.writeDataToEx("changeType.ini",properties);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Intent intent = new Intent(SettingActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                });
                singleChoiceDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(SettingActivity.this, "您取消了选择", Toast.LENGTH_SHORT).show();
                    }
                });
                singleChoiceDialog.show();

            }


        });

    }
}