# SideBar-Demo
一个列表导航控件
##自定义属性


 - 默认背景色
 - 触摸后的背景色
 - 导航字体大小、颜色
 - 弹出框背景
 - 弹出框的宽高
 - 弹出框字体大小、颜色
 - 导航的文字
 
 

<?xml version="1.0" encoding="utf-8"?>
<resources>
    <declare-styleable name="SideBar">
        <attr name="sidebar_background" format="color"/>
        <attr name="sidebar_background_hint" format="color"/>
        <attr name="sidebar_textcolor" format="color"/>
        <attr name="sidebar_textsize" format="dimension"/>
        <attr name="dialog_textcolor" format="color"/>
        <attr name="dialog_textsize" format="dimension"/>
        <attr name="dialog_background" format="color"/>
        <attr name="dialog_width" format="dimension"/>
        <attr name="dialog_height" format="dimension"/>
        <attr name="sidebar_gidits" format="string"/>
    </declare-styleable>
</resources>


##使用

   <com.zhang.sidebar_demo.SideBar
        android:id="@+id/sideBar"
        android:layout_width="25dp"
        android:layout_height="match_parent"
        android:layout_alignParentEnd="true"
        android:layout_gravity="end|center_vertical" />
        

        
