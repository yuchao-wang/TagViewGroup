package wang.yuchao.android.library.view.tagviewgroup;

import android.widget.CompoundButton;

public interface OnTagCheckedChangeListener {
    void onTagCheckedChanged(CompoundButton compoundButton, boolean b, int position, Object tag);
}