## Email

- [ ] ddd
- [x] ddd

yuchao.wang@yuchao.wang

## TagViewGroup For Android

- TagViewGroup is a ViewGroup layout for tags;
- Min sdk on support is 15(Android 4.0.3)

### Demo

[Download](https://codeload.github.com/yuchao-wang/TagViewGroup/zip/master)

![pic is here](https://github.com/yuchao-wang/TagViewGroup/blob/master/image/screenshot.png)

### Update

#### 1.1.3

- fix radio button listener

#### 1.1.1 

- optimize the strategy of 1.1.0 for margin 

#### 1.1.0

- Support Single Click
- Support CheckBox 
- Support Radio

#### 1.0.0

- Basic Function

### Dependence 

```
compile 'wang.yuchao.android.library.view.tagviewgroup:TagViewGroupLibrary:1.1.2'
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
|isRadio|radio or checkbox|
|singleLine|show in singleLine|
|tagMargin|tag margin|
|tagMarginLeft|tag left margin|
|tagMarginRight|tag right margin|
|tagMarginTop|tag top margin|
|tagMarginBottom|tag bottom margin|

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
