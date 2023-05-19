package cn.edu.nchu.comicstrip;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import cn.edu.nchu.comicstrip.Entity.Bookmark;

public class UpdateMark extends AppCompatActivity {

    Bookmark bookmark;
    EditText edit_BookMarkName,edit_pageId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_mark);

        edit_BookMarkName = findViewById(R.id.edit_BookMarkName);
        edit_pageId = findViewById(R.id.edit_PageId);

        Intent intent = getIntent();
        bookmark = (Bookmark) intent.getSerializableExtra("BookmarkObject");

        edit_BookMarkName.setText(bookmark.getName());
        edit_pageId.setText(String.valueOf(bookmark.getPageId()));

    }

    public void myClick(View view) {

        Intent intent = new Intent(UpdateMark.this, MainActivity.class);
        switch (view.getId()) {

            case R.id.confirm_update:

                bookmark.setName(edit_BookMarkName.getText().toString());
                bookmark.setPageId(Integer.parseInt(String.valueOf(edit_pageId.getText())));

                ContentValues contentValues = new ContentValues();
                contentValues.put("name",bookmark.getName());
                contentValues.put("pageId",bookmark.getPageId());

                LitePal.update(Bookmark.class,contentValues,bookmark.getId());

                startActivity(intent);
                finish();
                break;

            case R.id.confirm_delete:
                AlertDialog ad = new AlertDialog.Builder(this)
                        .setTitle("删除提示")
                        .setMessage("是否确定删除该书签:<" + bookmark.getName() + ">?")
                        .setIcon(R.mipmap.ic_launcher)
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                LitePal.delete(Bookmark.class,bookmark.getId());
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("取消", null)
                        .create();
                ad.show();
                break;
            case R.id.confirm_viewDetail:
                //带着id跳转到相应页面
                int pageId = bookmark.getPageId();
                Intent intentTP = new Intent(this, MainActivity.class);
                intentTP.putExtra("pageId",pageId);
                startActivity(intentTP);
                finish();
                break;
            default:
                break;
        }
    }


}