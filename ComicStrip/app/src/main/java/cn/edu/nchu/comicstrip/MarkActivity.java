package cn.edu.nchu.comicstrip;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;


import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.nchu.comicstrip.Entity.Bookmark;

public class MarkActivity extends AppCompatActivity {
    List<Map<String,Object>> list = new ArrayList<>();
    private ListView lv;
    private Toolbar mark;

    EditText edit_search;
    Button btn_search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark);
        edit_search = findViewById(R.id.edit_search);
        btn_search = findViewById(R.id.btn_search);
        //获取ListView组件
        lv = findViewById(R.id.lv);

        mark = findViewById(R.id.mark_toolbar);
        mark.setTitle("重走长征路");
        mark.setSubtitle("书签管理");
        mark.inflateMenu(R.menu.mark_menu);
        setSupportActionBar(mark);

        mark.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.backtomain:
                        Intent intent = new Intent(MarkActivity.this,MainActivity.class);
                        startActivity(intent);
                        break;
                }
               return true;
            }
         });

        List<Bookmark> bookmarks = LitePal.findAll(Bookmark.class);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int BookMarkid = bookmarks.get(i).getId();
                String BookMarkname = bookmarks.get(i).getName();
                int pageId =bookmarks.get(i).getPageId();
                //设置点击删除和查看详情事件
                //ContentFragment中的内容根据传过来的pageId去数据库里查询
                Intent intent = new Intent(MarkActivity.this, UpdateMark.class);
                intent.putExtra("BookmarkObject", new Bookmark(BookMarkid,BookMarkname,pageId));
                startActivity(intent);
            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取输入框输入信息
                String key = String.valueOf(edit_search.getText());
                List<Bookmark> bookmarks = LitePal.where("name like ? ", "%" + key + "%").find(Bookmark.class);
                updateListView(bookmarks);
            }
        });

        updateListView(bookmarks);
    }


    /**
     * 更新显示列表
     * @param bookmark
     */
    public void updateListView(List<Bookmark> bookmark) {
        list.clear();
        if (bookmark != null){
            for (int i = 0; i < bookmark.size(); i++) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("bookMarkId",bookmark.get(i).getId());
                map.put("bookMarkName",bookmark.get(i).getName());
                map.put("pageId",bookmark.get(i).getPageId());
                list.add(map);
            }
            SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.listview_item,
                    new String[]{"bookMarkId", "bookMarkName"}, new int[]{R.id.bookMarkId, R.id.bookMarkName});
            lv.setAdapter(adapter);
        }else{
            System.out.println("SearchFailed");
        }
    }


    public ArrayList<Bookmark> transform(Bookmark[] markArray){
        ArrayList<Bookmark> transformArray = new ArrayList<>();
        for (int i = 0; i < markArray.length; i++) {
            transformArray.add(new Bookmark(markArray[i].getId(),markArray[i].getName(),markArray[i].getPageId()));
        }
        return transformArray;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.mark_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}