package cn.edu.nchu.comicstrip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.NoCopySpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.litepal.LitePal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import cn.edu.nchu.comicstrip.Entity.Page;
import cn.edu.nchu.comicstrip.fragment.ContentFragment;
import cn.edu.nchu.comicstrip.fragment.EndFragment;
import cn.edu.nchu.comicstrip.fragment.WelcomeFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private FragmentAdapter fragmentAdapter;
    private List<Fragment> fragmentList;
    private ViewPager2 viewPager2;
    private String[] permission = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    private Toolbar toolbar;
    private boolean isGranted = true;
    TabLayout tabLayout;
    List<String> titles = new ArrayList<>();
    int choice = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //判断是否授权了
        for (String s : permission) {
            if (ContextCompat.checkSelfPermission(this, s) != PackageManager.PERMISSION_GRANTED) {
                isGranted = false;
            }
        }
        if (!isGranted) {
            //请求权限
            ActivityCompat.requestPermissions(this, permission, 1);
        }

            int pageId = getIntent().getIntExtra("pageId", 0);
            tabLayout = findViewById(R.id.tab_layout);
            toolbar = (Toolbar)findViewById(R.id.toolbar);

            toolbar = findViewById(R.id.toolbar);
            toolbar.setTitle("重走长征路");
            toolbar.inflateMenu(R.menu.main_menu);
            setSupportActionBar(toolbar);
            viewPager2 = (ViewPager2) findViewById(R.id.vp);
            fragmentList = new ArrayList<>();
            fragmentList.add(new WelcomeFragment());
            List<Page> pages = LitePal.findAll(Page.class);
            System.out.println("pageSize" + pages.size());
            titles.add("welcome");
            for (int i = 0; i < pages.size(); i++) {
                fragmentList.add(new ContentFragment());
                titles.add(String.valueOf(pages.get(i).getId()));
            }
            titles.add("end");

            fragmentList.add(new EndFragment());


            fragmentAdapter = new FragmentAdapter(MainActivity.this, fragmentList);
            viewPager2.setAdapter(fragmentAdapter);
            viewPager2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
            System.out.println("pageId:" + pageId);
            if (pageId > 0) {
                viewPager2.setCurrentItem(pageId);
            }

            System.out.println(titles.size());
            //TabLayout和Viewpager2进行关联
            new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
                @Override
                public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                    tab.setText(titles.get(position));
                }
            }).attach();

            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.markmanager:
                            Intent intent3 = new Intent(MainActivity.this, MarkActivity.class);
                            startActivity(intent3);
                            break;
                        case R.id.setword:
                            String[] items = {"小号", "默认", "中号", "大号", "超大"};
                            AlertDialog.Builder singleChoiceDialog = new AlertDialog.Builder(MainActivity.this);
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
                                    Toast.makeText(MainActivity.this, "您选择了" + items[choice], Toast.LENGTH_SHORT).show();
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
                                    ExternalConfigure configure = new ExternalConfigure(MainActivity.this);
                                    Properties properties = new Properties();
                                    Toast.makeText(MainActivity.this,"字体修改为："+items[choice]+":"+size,Toast.LENGTH_SHORT).show();
                                    properties.setProperty("textSize",String.valueOf(size));
                                    try {
                                        configure.writeDataToEx("word.ini",properties);

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    Intent intent = new Intent(MainActivity.this,MainActivity.class);
                                    startActivity(intent);
                                }
                            });
                            singleChoiceDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(MainActivity.this, "您取消了选择", Toast.LENGTH_SHORT).show();
                                }
                            });
                            singleChoiceDialog.show();
                            break;
                        case R.id.changeType:
                            if(viewPager2.getOrientation() == ViewPager2.ORIENTATION_HORIZONTAL){
                                viewPager2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
                                item.setTitle("左右翻页");
                            }else {
                                viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
                                item.setTitle("上下翻页");
                            }
                            Toast.makeText(MainActivity.this, "切换成功！", Toast.LENGTH_SHORT).show();
                            break;
                    }


                return true;
                }
            });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void onClick(View view) {

    }
}