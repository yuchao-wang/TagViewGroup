package wang.yuchao.android.library.view.tagviewgroup.sample;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import wang.yuchao.android.library.view.sample.tagviewgroup.R;
import wang.yuchao.android.library.view.tagviewgroup.OnTagCheckedChangeListener;
import wang.yuchao.android.library.view.tagviewgroup.OnTagClickListener;

public class MainActivity extends FragmentActivity {

    private static int i = 0;
    private ButtonTagViewGroup buttonTagViewGroup;
    private CheckBoxTagViewGroup checkBoxTagViewGroup;
    private CheckBoxTagViewGroup checkRadioBoxTagViewGroup;
    private TextView test_tv;

    private ArrayList<String> allText = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.test_tv = (TextView) findViewById(R.id.test_tv);
        this.buttonTagViewGroup = (ButtonTagViewGroup) findViewById(R.id.button_tags_view_group);
        this.checkBoxTagViewGroup = (CheckBoxTagViewGroup) findViewById(R.id.checkbox_tags_view_group);
        this.checkRadioBoxTagViewGroup = (CheckBoxTagViewGroup) findViewById(R.id.checkbox_radio_tags_view_group);

        //添加
        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allText.add("王玉超" + i * i);
                i++;
                buttonTagViewGroup.update(allText);
                checkBoxTagViewGroup.update(allText);
                checkRadioBoxTagViewGroup.update(allText);
            }
        });

        //移除
        findViewById(R.id.btn_remove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i--;
                allText.remove(i);
                buttonTagViewGroup.update(allText);
                checkBoxTagViewGroup.update(allText);
                checkRadioBoxTagViewGroup.update(allText);
            }
        });

        //单行显示
        findViewById(R.id.btn_hide).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonTagViewGroup.setSingleLine(true);
                test_tv.setVisibility(View.VISIBLE);

                checkBoxTagViewGroup.setSingleLine(true);
                checkRadioBoxTagViewGroup.setSingleLine(true);
            }
        });

        //全部显示
        findViewById(R.id.btn_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonTagViewGroup.setSingleLine(false);
                test_tv.setVisibility(View.GONE);

                checkBoxTagViewGroup.setSingleLine(false);
                checkRadioBoxTagViewGroup.setSingleLine(false);
            }
        });

        //点击
        buttonTagViewGroup.setOnTagClickListener(new OnTagClickListener() {
            @Override
            public void onTagClick(int position, Object tag) {
                String tagString = (String) tag;
                Toast.makeText(getApplicationContext(), "点击：第" + position + "个，值为：" + tagString, Toast.LENGTH_SHORT).show();
            }
        });

        //复选框
        checkBoxTagViewGroup.setOnTagCheckedChangeListener(new OnTagCheckedChangeListener() {
            @Override
            public void onTagCheckedChanged(CompoundButton compoundButton, boolean b, int position, Object tag) {
                String tagString = (String) tag;
                Toast.makeText(getApplicationContext(), "复选：第" + position + "个，值为：" + tagString, Toast.LENGTH_SHORT).show();
            }
        });
        //单选框
        checkRadioBoxTagViewGroup.setOnTagCheckedChangeListener(new OnTagCheckedChangeListener() {
            @Override
            public void onTagCheckedChanged(CompoundButton compoundButton, boolean b, int position, Object tag) {
                String tagString = (String) tag;
                Toast.makeText(getApplicationContext(), "单选：第" + position + "个，值为：" + tagString, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
