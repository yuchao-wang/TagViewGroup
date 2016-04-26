package wang.yuchao.android.library.view.tagviewgroup;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;

/**
 * ViewGroup动态计算内部的子View位置
 * Created by wangyuchao on 15/8/21.
 */
public class TagViewGroup extends ViewGroup {

    private static final int DEFAULT_MARGIN = 4;
    private static final int DEFAULT_TEXT_SIZE = 13;

    private boolean singleLine;

    private int tagBackground;

    private int tagTextAppearance;

    /**
     * 默认4dp
     */
    private float tagMarginLeft;
    private float tagMarginTop;
    private float tagMarginRight;
    private float tagMarginBottom;

    private OnTagItemClickListener onTagItemClickListener;
    private OnTagItemCheckListener onTagItemCheckListener;

    public TagViewGroup(Context context) {
        this(context, null);
    }

    public TagViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.parseAttributeSet(context, attrs);
    }

    private void parseAttributeSet(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TagViewGroup);
        if (array.hasValue(R.styleable.TagViewGroup_singleLine)) {
            setSingleLine(array.getBoolean(R.styleable.TagViewGroup_singleLine, false));
        }

        if (array.hasValue(R.styleable.TagViewGroup_tagTextAppearance)) {
            setTagTextAppearance(array.getResourceId(R.styleable.TagViewGroup_tagTextAppearance, R.style.TagTextAppearance));
        }
        if (array.hasValue(R.styleable.TagViewGroup_tagBackground)) {
            setTagBackground(array.getResourceId(R.styleable.TagViewGroup_tagBackground, R.drawable.tag_view_group));
        }

        if (array.hasValue(R.styleable.TagViewGroup_tagMarginLeft)) {
            setTagMarginLeft(array.getDimension(R.styleable.TagViewGroup_tagMarginLeft, DEFAULT_MARGIN));
        }
        if (array.hasValue(R.styleable.TagViewGroup_tagMarginTop)) {
            setTagMarginTop(array.getDimension(R.styleable.TagViewGroup_tagMarginTop, DEFAULT_MARGIN));
        }
        if (array.hasValue(R.styleable.TagViewGroup_tagMarginRight)) {
            setTagMarginRight(array.getDimension(R.styleable.TagViewGroup_tagMarginRight, DEFAULT_MARGIN));
        }
        if (array.hasValue(R.styleable.TagViewGroup_tagMarginBottom)) {
            setTagMarginBottom(array.getDimension(R.styleable.TagViewGroup_tagMarginBottom, DEFAULT_MARGIN));
        }
        if (array.hasValue(R.styleable.TagViewGroup_tagMargin)) {
            float margin = array.getDimension(R.styleable.TagViewGroup_tagMargin, DEFAULT_MARGIN);
            setTagMarginLeft(margin);
            setTagMarginTop(margin);
            setTagMarginRight(margin);
            setTagMarginBottom(margin);
        }
        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 获得它的父容器为它设置的测量模式和大小
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        int measuredWidth = 0;
        int measuredHeight = 0;

        int length = 0;
        int rows = 0;

        for (int index = 0; index < getChildCount(); index++) {
            View child = getChildAt(index);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams marginLayoutParams = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin;
            int childHeight = child.getMeasuredHeight() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;
            if ((length + childWidth) > sizeWidth) {
                if (singleLine) {
                    measuredHeight = childHeight;
                    break;
                } else {
                    if (length != 0) {
                        length = 0;
                        rows++;
                    }
                }
            }
            length = length + childWidth;
            measuredWidth = Math.max(length, measuredWidth);
            measuredHeight = (rows + 1) * childHeight;
        }
        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? sizeWidth : measuredWidth,
                (heightMode == MeasureSpec.EXACTLY) ? sizeHeight : measuredHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int parentWidth = right - left;
        int width = 0;
        int height = 0;
        int rows = 0;//行号

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.setVisibility(View.INVISIBLE);
            MarginLayoutParams marginLayoutParams = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin;
            int childHeight = child.getMeasuredHeight() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;
            if ((childWidth + width) > parentWidth) {
                if (singleLine) {
                    if (width == 0) {//如果第一行太长
                        child.setVisibility(View.VISIBLE);
                        child.layout(width + marginLayoutParams.leftMargin, height + marginLayoutParams.topMargin,
                                parentWidth - marginLayoutParams.rightMargin, height + childHeight - marginLayoutParams.bottomMargin);
                    }
                    break;
                } else {
                    if (width != 0) {
                        width = 0;
                        rows++;
                    }
                }
            }

            height = rows * childHeight;//起点

            child.setVisibility(View.VISIBLE);
            child.layout(width + marginLayoutParams.leftMargin, height + marginLayoutParams.topMargin,
                    Math.min((childWidth + width), parentWidth) - marginLayoutParams.rightMargin,
                    height + childHeight - marginLayoutParams.bottomMargin);

            width = width + childWidth;
        }
    }

    /**
     * 是否单行显示
     */
    public void setSingleLine(boolean isSingleLine) {
        this.singleLine = isSingleLine;
        this.requestLayout();
    }

    /**
     * 设置标签背景
     */
    public void setTagBackground(int tagBackground) {
        this.tagBackground = tagBackground;
    }

    public void setTagTextAppearance(int tagTextAppearance) {
        this.tagTextAppearance = tagTextAppearance;
    }

    public void setTagMarginLeft(float tagMarginLeft) {
        this.tagMarginLeft = tagMarginLeft;
    }

    public void setTagMarginTop(float tagMarginTop) {
        this.tagMarginTop = tagMarginTop;
    }

    public void setTagMarginRight(float tagMarginRight) {
        this.tagMarginRight = tagMarginRight;
    }

    public void setTagMarginBottom(float tagMarginBottom) {
        this.tagMarginBottom = tagMarginBottom;
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    /**
     * 更新UI[Button]
     * <pre>
     *     由于需要使用标签名称，因此没有使用泛型，但是把点击的position返回了
     * </pre>
     */
    public void updateButton(ArrayList<String> tags) {
        if (tags == null) {
            return;
        }

        this.removeAllViews();
        for (int i = 0; i < tags.size(); i++) {
            final int position = i;
            String name = tags.get(i);
            if (!TextUtils.isEmpty(name)) {

                Button button = new Button(getContext());
                button.setText(name);
                button.setTextAppearance(getContext(), tagTextAppearance);
                button.setBackgroundResource(tagBackground);
                MarginLayoutParams marginLayoutParams = new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                marginLayoutParams.setMargins((int) tagMarginLeft, (int) tagMarginTop, (int) tagMarginRight, (int) tagMarginBottom);
                button.setLayoutParams(marginLayoutParams);
                button.setOnClickListener(new TagOnClickListener(position));
                this.addView(button);

            }
        }
    }

    /**
     * 更新UI[CheckBox]
     * <pre>
     *     由于需要使用标签名称，因此没有使用泛型，但是把点击的position返回了
     * </pre>
     */
    public void updateCheckBox(ArrayList<String> tags) {
        if (tags == null) {
            return;
        }

        this.removeAllViews();
        for (int i = 0; i < tags.size(); i++) {
            final int position = i;
            String name = tags.get(i);
            if (!TextUtils.isEmpty(name)) {
                CheckBox checkBox = new CheckBox(getContext());
                checkBox.setText(name);
                checkBox.setTextAppearance(getContext(), tagTextAppearance);
                checkBox.setButtonDrawable(getResources().getDrawable(android.R.color.transparent));
                checkBox.setBackgroundResource(tagBackground);

                MarginLayoutParams marginLayoutParams = new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                marginLayoutParams.setMargins((int) tagMarginLeft, (int) tagMarginTop, (int) tagMarginRight, (int) tagMarginBottom);
                checkBox.setLayoutParams(marginLayoutParams);
                checkBox.setOnCheckedChangeListener(new TagOnCheckListener(position));
                this.addView(checkBox);
            }
        }
    }

    public void setOnTagItemClickListener(OnTagItemClickListener onTagItemClickListener) {
        this.onTagItemClickListener = onTagItemClickListener;
    }

    public void setOnTagItemCheckListener(OnTagItemCheckListener onTagItemCheckListener) {
        this.onTagItemCheckListener = onTagItemCheckListener;
    }

    public interface OnTagItemClickListener {
        void onTagItemClick(int position);
    }

    public interface OnTagItemCheckListener {
        void onTagItemCheck(int position, CompoundButton buttonView, boolean isChecked);
    }

    private class TagOnClickListener implements OnClickListener {

        private int position;

        public TagOnClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if (onTagItemClickListener != null) {
                onTagItemClickListener.onTagItemClick(position);
            }
        }
    }

    private class TagOnCheckListener implements CompoundButton.OnCheckedChangeListener {

        private int position;

        public TagOnCheckListener(int position) {
            this.position = position;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (onTagItemCheckListener != null) {
                onTagItemCheckListener.onTagItemCheck(position,buttonView, isChecked);
            }
        }
    }

}
