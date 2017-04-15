# SideBar-Demo
#一个列表导航控件

![这里写图片描述](http://img.blog.csdn.net/20170415114516019?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdTAxNDUyNzMyMw==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

##自定义属性

 
| 属性 | 含义|
| ------------- |:-------------:|
| sidebar_background | 默认背景色|
| sidebar_background_hint | 触摸后的背景色 |
| sidebar_textcolor | 导航字体颜色 |
| sidebar_textsize | 导航字号|
| dialog_background | 弹出框背景 |
| dialog_width | 弹出框宽度 |
| dialog_height | 弹出框高度 |
| dialog_textsize | 弹出框字号 |
| dialog_textcolor | 弹出框字体颜色 |
| sidebar_gidits | 导航文字 |



##使用

1. 导入库

```
compile 'com.github.695336128:SideBar-Demo:v1.0'
```
2. 添加控件

```
   <com.zhang.sidebar_demo.SideBar
        android:id="@+id/sideBar"
        android:layout_width="25dp"
        android:layout_height="match_parent"
        android:layout_alignParentEnd="true"
        android:layout_gravity="end|center_vertical" />
```
3. Adapter 实现 SectionIndexer 接口，并重写 getPositionForSection 方法

举个栗子：

```
@Override
    public int getPositionForSection(int sectionIndex) {
        CityBean bean ;
        String firstLetter;
        if (sectionIndex == '!'){
            return 0;
        }else {
            for (int i = 0; i < mList.size();i++){
                bean = mList.get(i);
                // 取首字母
                firstLetter = PinyinUtils.getPinyinFirstLetter(bean.getCity());
                char firstChar = firstLetter.toUpperCase().charAt(0);
                if (firstChar == sectionIndex) {
                    return i;
                }
            }
        }
        bean = null;
        firstLetter = null;
        return -1;
    }
```
4. 初始化sidebar，并设置 setOnSelecListener 监听
```

sideBar.setSectionIndexer((SectionIndexer) mRecyclerView.getAdapter());
        sideBar.setOnSelecListener(new SideBar.onSelecListener() {
            @Override
            public void setSelection(int position) {
                smoothMoveToPosition(mRecyclerView,position);
            }
        });
```
