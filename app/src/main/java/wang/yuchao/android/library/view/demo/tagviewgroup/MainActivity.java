package wang.yuchao.android.library.view.demo.tagviewgroup;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import wang.yuchao.android.library.view.tagviewgroup.TagViewGroup;

public class MainActivity extends FragmentActivity {

    private static int i = 0;
    private TagViewGroup tagViewGroup;
    private TextView test_tv;
    private Context context;

    private ArrayList<String> text = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.tagViewGroup = (TagViewGroup) findViewById(R.id.tags_view_group);
        this.tagViewGroup = (TagViewGroup) findViewById(R.id.tags_view_group);
        this.test_tv = (TextView) findViewById(R.id.test_tv);

        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.add("王玉超" + i * i);
                i++;
                tagViewGroup.updateButton(text);
            }
        });

        findViewById(R.id.btn_remove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i--;
                text.remove(i);
                tagViewGroup.updateButton(text);
            }
        });

        findViewById(R.id.btn_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tagViewGroup.setSingleLine(false);
                test_tv.setVisibility(View.GONE);
            }
        });

        findViewById(R.id.btn_hide).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tagViewGroup.setSingleLine(true);
                test_tv.setVisibility(View.VISIBLE);
            }
        });

        tagViewGroup.setOnTagItemClickListener(new TagViewGroup.OnTagItemClickListener() {
            @Override
            public void onTagItemClick(int position) {
                Toast.makeText(getApplicationContext(), "点击的是第" + position + "个，值为：" + text.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
