package cn.edu.nchu.comicstrip.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.LitePal;

import java.io.IOException;
import java.util.List;

import cn.edu.nchu.comicstrip.Entity.Bookmark;
import cn.edu.nchu.comicstrip.Entity.Page;
import cn.edu.nchu.comicstrip.ExternalConfigure;
import cn.edu.nchu.comicstrip.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContentFragment extends Fragment {
    ImageButton ib_collect;
    ImageButton ib_addmark;
    ImageView imageView;
    ImageView iv_ismark;
    TextView textView;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ContentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContentFragment newInstance(String param1, String param2) {
        ContentFragment fragment = new ContentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        ib_collect = (ImageButton) view.findViewById(R.id.ib_collect);
        ib_addmark = (ImageButton)view.findViewById(R.id.ib_addmark);
        imageView = (ImageView) view.findViewById(R.id.image);
        textView = (TextView) view.findViewById(R.id.content);
        iv_ismark = (ImageView)view.findViewById(R.id.iv_ismark);

        ExternalConfigure externalConfigure = new ExternalConfigure(getActivity());

        try {
            externalConfigure.readDataFromEx("word.ini");
            textView.setTextSize(Integer.parseInt(externalConfigure.getIniKey("textSize")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ApplicationInfo appInfo = getActivity().getApplicationInfo();
//        System.out.println("mparam1"+mParam1);
        Page page  = LitePal.find(Page.class,Integer.parseInt(mParam1));
        if(page.isCollect()){
            ib_collect.setImageResource(R.drawable.collect);
        }
        int resID = getResources().getIdentifier("img_"+ page.getImageId(), "drawable", appInfo.packageName);
        imageView.setImageResource(resID);
        textView.setText(page.getContent());

        List<Bookmark> findBookMarks  = LitePal.where("pageId  = ?" , String.valueOf(page.getId())).find(Bookmark.class);

        if(findBookMarks.size() != 0){
            iv_ismark.setImageResource(R.drawable.ismark);
            ib_addmark.setVisibility(View.INVISIBLE);
        }else{
            iv_ismark.setImageResource(0);
            ib_addmark.setVisibility(View.VISIBLE);
        }

        ib_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //初始化信息
//              从数据库中取数据
                //点击修改；
                if(ib_collect.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.collect).getConstantState())){
                    ib_collect.setImageResource(R.drawable.uncollect);
                    //修改数据库里的信息
                    page.setCollect(false);
                    page.update(page.getId());
                }else {
                    ib_collect.setImageResource(R.drawable.collect);
                    page.setCollect(true);
                    page.update(page.getId());
                }
            }
        });
        ib_addmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText ed =  new EditText(getActivity());
                AlertDialog.Builder builder = new AlertDialog.Builder((getActivity()));
                builder.setTitle("请输入书签名称").setView(ed);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Bookmark bookmark = new Bookmark(ed.getText().toString(),page.getId());
                        boolean result = bookmark.save();
                        if(result){
                            Toast.makeText(getContext(),"添加书签成功！！",Toast.LENGTH_SHORT).show();
                            iv_ismark.setImageResource(R.drawable.ismark);
                            ib_addmark.setVisibility(View.INVISIBLE);
                        }else {
                            Toast.makeText(getContext(),"添加书签失败！！",Toast.LENGTH_SHORT).show();
                            iv_ismark.setImageResource(0);
                        }
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getContext(),"您已取消",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });


        return view;
    }

}