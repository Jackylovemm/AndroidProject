package cn.edu.nchu.comicstrip;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ExternalConfigure {
    private Context context;
    private Properties properties;

    public ExternalConfigure(Context context) {
        this.context = context;
    }

    //需要存储的内容有：
    // 按钮的界面背景颜色，字体大小、字体颜色等信息
    public void writeDataToEx(String filename ,Properties properties) throws IOException {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)){
            String sdPath = Environment.getExternalStorageDirectory().getCanonicalPath();
            File file = new File(sdPath,filename);
            if(!file.exists()){
                if(file.createNewFile()){
                    Toast.makeText(this.context,"文件创建成功!",Toast.LENGTH_SHORT).show();
                }
            }
            FileOutputStream fos = new FileOutputStream(file);
            for (String key : properties.stringPropertyNames()) {
                String s = key + " = " + properties.getProperty(key) + "\n";
                System.out.println(s);
                fos.write(s.getBytes());
            }

            fos.close();
        }else{
            Toast.makeText(context,"外存不可写！",Toast.LENGTH_SHORT).show();
        }
    }

    public void readDataFromEx(String filename) throws IOException {
        properties = new Properties();
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            filename = Environment.getExternalStorageDirectory() + "/" + filename;

            FileInputStream fis = new FileInputStream(filename);
            properties.load(fis);
            fis.close();
        }

    }

    /**
     * 返回指定key对应的value
     */
    public String getIniKey(String key) {
        if (!properties.containsKey(key)) {
            return null;
        }
        return String.valueOf(properties.get(key));
    }
}
