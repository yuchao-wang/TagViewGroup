package wang.yuchao.android.library.view.tagviewgroup.sample;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import wang.yuchao.android.library.view.tagviewgroup.TagViewGroup;


/**
 * Created by wangyuchao on 16/4/26.
 */
public class ButtonTagViewGroup extends TagViewGroup<Button, String> {
    public ButtonTagViewGroup(Context context) {
        super(context);
    }

    public ButtonTagViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public Button getTagItemView(int position, String tag) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.tag_view_group_default_button, null);
        //tag布局不能包含在别的布局中，否则会报错 The specified child already has a parent. You must call removeView() on the child's parent first.
        Button button = (Button) view.findViewById(R.id.tag);
        button.setText(tag);
        return button;
    }
}
