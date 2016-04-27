package wang.yuchao.android.library.view.demo.tagviewgroup;

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
    private OnTagClickListener onTagClickListener;

    public ButtonTagViewGroup(Context context) {
        super(context);
    }

    public ButtonTagViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public Button getTagView(final int position, String tag) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.tag_view_group_default_button, null);
        //tag布局不能包含在别的布局中，否则会报错 The specified child already has a parent. You must call removeView() on the child's parent first.
        Button button = (Button) view.findViewById(R.id.tag);
        button.setText(tag);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onTagClickListener != null) {
                    onTagClickListener.onTagClick(position);
                }
            }
        });
        return button;
    }

    public void setOnTagClickListener(OnTagClickListener onTagClickListener) {
        this.onTagClickListener = onTagClickListener;
    }

    public interface OnTagClickListener {
        void onTagClick(int position);
    }
}
