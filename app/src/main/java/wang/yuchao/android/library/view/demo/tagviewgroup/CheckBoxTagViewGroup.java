package wang.yuchao.android.library.view.demo.tagviewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import wang.yuchao.android.library.view.tagviewgroup.TagViewGroup;

/**
 * Created by wangyuchao on 16/4/27.
 */
public class CheckBoxTagViewGroup extends TagViewGroup<CheckBox, String> {

    private OnTagCheckedChangeListener onTagCheckedChangeListener;
    private boolean isRadio;

    public CheckBoxTagViewGroup(Context context) {
        super(context);
    }

    public CheckBoxTagViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setRadio(boolean radio) {
        isRadio = radio;
    }

    @Override
    public CheckBox getTagView(final int position, final String tag) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.tag_view_group_default_check_box, null);
        //tag布局不能包含在别的布局中，否则会报错 The specified child already has a parent. You must call removeView() on the child's parent first.
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.tag);
        checkBox.setText(tag);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (onTagCheckedChangeListener != null) {
                    if (isRadio && checked) {
                        radio(position);
                    }
                    onTagCheckedChangeListener.onTagCheckedChanged(compoundButton, checked, position, tag);
                }
            }
        });
        return checkBox;
    }

    /**
     * 单选某个position
     */
    private void radio(int position) {
        for (int i = 0; i < getChildCount(); i++) {
            CheckBox checkBox = (CheckBox) getChildAt(i);
            //把之前选中的置为false
            if (i != position) {
                checkBox.setChecked(false);
            }
        }
    }

    public void setOnTagCheckedChangeListener(OnTagCheckedChangeListener onTagCheckedChangeListener) {
        this.onTagCheckedChangeListener = onTagCheckedChangeListener;
    }

    public interface OnTagCheckedChangeListener {
        void onTagCheckedChanged(CompoundButton compoundButton, boolean b, int position, String tag);
    }
}
