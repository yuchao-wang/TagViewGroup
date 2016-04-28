## TagViewGroup For Android（标签布局）

- TagViewGroup is a ViewGroup layout for tags;
- Min sdk on support is 15(Android 4.0.3)

### Demo

![pic is here](https://github.com/yuchao-wang/TagViewGroup/blob/master/image/screenshot.png)

### Update

- 1.1.1 优化1.1.0的margin和marginTop,marginBottom,marginLeft,marginRight同时使用的策略
- 1.1.0 单选、复选、点击、三种类型的标签完成
- 1.0.0 基本功能完善版本

### Dependence 

```
compile 'wang.yuchao.android.library.view.tagviewgroup:TagViewGroupLibrary:1.1.1'
```

### How To Use

**java file** 

```
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
```

**xml file**

```
<wang.yuchao.android.library.view.demo.tagviewgroup.ButtonTagViewGroup
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            TagViewGroup:isRadio="true"
            TagViewGroup:singleLine="true"
            TagViewGroup:tagMargin="@dimen/margin_little"/>
```

**declarations**

|param|statement|
|:---|:---|
|isRadio|单选-复选|
|singleLine|单行显示-全部显示|
|tagMargin|tag之间的边距|
|tagMarginLeft|tag左边距|
|tagMarginRight|tag右边距|
|tagMarginTop|tag上边距|
|tagMarginBottom|tag底边距|



### Proguard

```
-keep class wang.yuchao.android.library.** { *; }
-dontwarn wang.yuchao.android.library.**
```

### Problem

1. java.lang.IllegalStateException: The specified child already has a parent. You must call removeView() on the child's parent first.

Sub tag layout must be like this . As follows

```
<?xml version="1.0" encoding="utf-8"?>
<Button
    android:id="@+id/tag"
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="test"/>
```

### [About Me](http://yuchao.wang)


### License

```
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```