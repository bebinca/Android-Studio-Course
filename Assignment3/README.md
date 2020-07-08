# Assignment3

代码在Homework文件夹中。

由于Github对md文件中图片格式的显示问题，缩放没有办法正常显示，本readme在网页端的图片大小可能看起来过大。可以clone下来，在其他软件上查看。

## Implementation

页面1：勾选复选框，`Lottie`动画自动循环播放；取消复选框，只能通过`SeekBar`手动调整动画进度

页面2：在变换颜色、可以手动设置频率和颜色的页面基础上，实现大小缩放和透明度的变换动画

页面3： 做一个简单版的好友列表界面，可滑动，对于好友列表 `Fragment`，使用`Lottie`实现Loading效果，在 5s 后展示实际的列表，要求这里的动效是淡入淡出
## Environment

开发环境：Android Studio

## Summary

由于结果部分有图片可能内容过长，这里把总结思考部分前置。

1. `ObjectAnimator`对`target`控件的背景色进行修改使用`ofArgb`：ARGB值分别对应透明度，R，G，B，用`ofArgb`带来的变换效果是颜色渐变的；但如果用`ofInt`，视觉效果是颜色不断突变而非渐变。Int上的转换并不是颜色空间上一个逐渐的转换。

2. 在写完好友列表界面的时候发现了一个现象，`viewPager`（包含三个子界面）相邻页面相互滑动，loading动画都不会再次出现，但是如果从第一个页面快速滑到第三个页面，或者反向执行这个操作，都会看到loading动画，而第二个页面的loading动画在刚进入时加载了之后就没有再出现了。

   通过调试发现，`viewPager`在当前页会检测邻居页有没有`create`，如果没有的话会预先`create`；并且检测非邻居页有没有还是活跃的，有的话就会`destroy`掉。因此从HELLO 0划到HELLO 1，会加载HELLO 2；再划到HELLO 2，会销毁HELLO 0。这样如果快速滑回HELLO 0，就会看到HELLO 0的loading动画。

   在和老师交流后，我也得知`viewPager.setOffscreenPageLimit()`方法可以设置预加载的邻居数量`L`。实验发现，默认的加载数量`L`是1，最大缓存数量是1*2+1=3（即当前页面加上左右各`L`个）。所以对于3个子页面的情况，如果设置`L`=2，就不会再有loading动画多次重复出现，因为所有的页面都被加载完成。

## Result

页面1：勾选复选框，`Lottie`动画自动循环播放

<img src="D:/AS/Android-Studio-Course/Assignment3/Images/1.jpg" alt="1-w70" style="zoom:20%;" />

取消复选框，通过`SeekBar`手动调整动画进度

<img src="D:/AS/Android-Studio-Course/Assignment3/Images/2.jpg" alt="1-w70" style="zoom:20%;" />

<img src="D:/AS/Android-Studio-Course/Assignment3/Images/3.jpg" alt="1-w70" style="zoom:20%;" />

页面2：实现大小缩放和透明度的变换动画

<img src="D:/AS/Android-Studio-Course/Assignment3/Images/4.jpg" alt="1-w70" style="zoom:20%;" />

<img src="D:/AS/Android-Studio-Course/Assignment3/Images/5.jpg" alt="1-w70" style="zoom:20%;" />

页面3： 做一个简单版的好友列表界面，可滑动，对于好友列表 `Fragment`，使用`Lottie`实现Loading效果，在 5s 后展示实际的列表，要求这里的动效是淡入淡出

可以看出淡入淡出效果：

<img src="D:/AS/Android-Studio-Course/Assignment3/Images/6.jpg" alt="1-w70" style="zoom:20%;" />

好友消息列表：

<img src="D:/AS/Android-Studio-Course/Assignment3/Images/7.jpg" alt="1-w70" style="zoom:20%;" />

loading动画：

<img src="D:/AS/Android-Studio-Course/Assignment3/Images/8.jpg" alt="1-w70" style="zoom:20%;" />