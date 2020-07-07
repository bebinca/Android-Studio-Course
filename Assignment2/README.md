# Assignment2

代码在Message1文件夹中。

由于Github对md文件中图片格式的显示问题，缩放没有办法正常显示，本readme在网页端的图片大小可能看起来过大。可以clone下来，在其他软件上查看。

## Implementation

实现了一个类似抖音消息页面的页面；

点击消息列表，会跳转到新的页面显示是第几个item；

点击联系人，出现隐式intent调用；

开发过程中可以通过查看log，看到view的统计数量

## Environment

开发环境：Android Studio

## Result

初始界面：

<img src=".\Images\1.jpg" alt="1-w70" style="zoom:20%;" />

（这里放一张目前版本抖音的图，信息为临时测试，后期做了部分马赛克处理）

<img src=".\Images\6.jpg" alt="1-w70" style="zoom:20%;" />

点击右上角的联系人，出现以下界面（隐式intent）

<img src=".\Images\2.jpg" alt="1-w70" style="zoom:20%;" />

点击消息列表的一些item：

<img src=".\Images\3.jpg" alt="1-w70" style="zoom:20%;" />

<img src=".\Images\4.jpg" alt="1-w70" style="zoom:20%;" />

本界面可以滑动：

<img src=".\Images\7.jpg" alt="1-w70" style="zoom:20%;" />

开发过程的部分日志显示：（view的统计都是从mainActivity.xml根部）

可以看到，一开始view的统计只有12个，后来还出现82个，76个等数字。这是因为消息列表处我们使用了RecycleView，它并不会一开始就把所有的数据加载完成，所以刚开始我们只有顶部和底部的view数目，也就是12个。在点击页面中的一个消息返回之后，RecycleView加载了这一部分的信息，此时为82个。而滑动到下面之后，上面的内存又被释放了，此时再计算view就变成了76个。view的数量也能够印证RecycleView的优越性。

<img src=".\Images\5.png" alt="1-w70"/>