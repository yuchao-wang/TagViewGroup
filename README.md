## TagViewGroup For Android（标签布局）

- TagViewGroup is a ViewGroup layout for tags;
- Min sdk on support is 15(Android 4.0.3)

### Demo

[pic is here](https://github.com/yuchao-wang/TagViewGroup/blob/master/image/screenshot.png)

![demo](http://ww2.sinaimg.cn/mw690/726a6a6fjw1f3benszmhqj20qo1betfp.jpg)

### How To Use

add this to model dependence

```
compile 'wang.yuchao.android.library.view.tagviewgroup:TagViewGroupLibrary:1.1.0'
```

if can't find it or gradle failed ,you need add something to project gradle file like this

```
allprojects {
    repositories {
        maven {
            url 'https://dl.bintray.com/yuchao-wang/maven'
        }
        jcenter()
    }
}
```


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