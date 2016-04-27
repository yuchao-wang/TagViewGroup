package wang.yuchao.android.library.view.tagviewgroup;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;

/**
 * <pre>
 * V -> view
 * T -> tag
 * </pre>
 * Created by wangyuchao on 15/8/21.
 */
public abstract class TagViewGroup<V extends Button, T> extends ViewGroup {

    private static final int DEFAULT_MARGIN = 4;

    private boolean singleLine;

    private float tagMarginLeft = DEFAULT_MARGIN;
    private float tagMarginTop = DEFAULT_MARGIN;
    private float tagMarginRight = DEFAULT_MARGIN;
    private float tagMarginBottom = DEFAULT_MARGIN;
    private OnTagClickListener onTagClickListener;
    private OnTagCheckedChangeListener onTagCheckedChangeListener;
    private boolean isRadio;

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
        if (array.hasValue(R.styleable.TagViewGroup_isRadio)) {
            setRadio(array.getBoolean(R.styleable.TagViewGroup_isRadio, false));
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

    public void setSingleLine(boolean isSingleLine) {
        this.singleLine = isSingleLine;
        this.requestLayout();
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

    public abstract V getTagItemView(int position, T tag);

    public void update(ArrayList<T> tags) {
        this.removeAllViews();
        for (int i = 0; i < tags.size(); i++) {
            T tag = tags.get(i);
            if (tag != null) {
                V view = getTagItemView(i, tag);
                MarginLayoutParams marginLayoutParams = new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                marginLayoutParams.setMargins((int) tagMarginLeft, (int) tagMarginTop, (int) tagMarginRight, (int) tagMarginBottom);
                view.setLayoutParams(marginLayoutParams);
                this.addView(view);
                setListener(view, i, tag);
            }
        }
    }

    private void setListener(V view, final int position, final T tag) {
        if (view instanceof CheckBox) {
            //如果是CheckBox
            CheckBox checkBox = (CheckBox) view;
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
        } else {
            //如果是Button
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onTagClickListener != null) {
                        onTagClickListener.onTagClick(position, tag);
                    }
                }
            });
        }
    }

    public void setOnTagClickListener(OnTagClickListener onTagClickListener) {
        this.onTagClickListener = onTagClickListener;
    }

    public void setRadio(boolean radio) {
        isRadio = radio;
    }

    public void setOnTagCheckedChangeListener(OnTagCheckedChangeListener onTagCheckedChangeListener) {
        this.onTagCheckedChangeListener = onTagCheckedChangeListener;
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

    public interface OnTagClickListener {
        void onTagClick(int position, Object tag);
    }

    public interface OnTagCheckedChangeListener {
        void onTagCheckedChanged(CompoundButton compoundButton, boolean b, int position, Object tag);
    }
}
