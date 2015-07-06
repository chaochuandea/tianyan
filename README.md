# tianyan
模仿天眼
##环境初始化
>
1. studio 需安装lombok插件
2. java sdk需安装>1.8版本

##依赖说明
>
1. [Butternife]( http://jakewharton.github.io/butterknife/) 这是依赖注入，省掉老是findviewByid的烦恼
2. [sugar](https://github.com/satyan/sugar) android sqlite orm
3. [retrofit&okhttp](https://github.com/square/retrofit) 基于注解的网络请求框架，内部使用动态代理实现，封装了网络请求细节，只需要关心真正返回内容
4. [rxjava](https://github.com/ReactiveX/RxAndroid)  一切皆流的思想，让代码逻辑变得异常清晰明了
5. [lombok](https://projectlombok.org/) pojo 的setter和getter以及constructor的注解完成
6. [leakcanary](https://github.com/square/leakcanary) 分析内存溢出的一款工具
7. [timber](https://github.com/JakeWharton/timber) 日志打印工具
8. [fresco](https://github.com/facebook/fresco) android 图片框架，他的实现机制与rxjava的实现如出一辙。他能很好的避免内存溢出
9. [eventbus](https://github.com/greenrobot/EventBus) android 事件总线工具，虽然rxjava能很好的替代这个，但是用起来还是觉得这个很顺手
10. [retrolambda](https://github.com/orfjackal/retrolambda) 支持lambda的语法，简化代码
11 [calligraphy](https://github.com/chrisjenx/Calligraphy)textview 更换字体

## 技术点分析
>
1.  放大ImageView动画![放大ImageView动画](https://github.com/chaochuandea/tianyan/blob/master/screen_capture/zoomiamgeview.png)
2.  loading效果![loading效果](https://github.com/chaochuandea/tianyan/blob/master/screen_capture/loading.png)
3.  Recycle+PullTorefresh+LoadMore![Recycle+PullTorefresh+LoadMore](https://github.com/chaochuandea/tianyan/blob/master/screen_capture/pulltorefresh.png)
4.  抽屉下拉效果![抽屉下拉效果](https://github.com/chaochuandea/tianyan/blob/master/screen_capture/drag_down.png)
5.  recycle_view点击后item过度效果，textview动画和typeface设置，毛玻璃效果![recycle_view点击后item过度效果，textview动画和typeface设置，毛玻璃效果](https://github.com/chaochuandea/tianyan/blob/master/screen_capture/blur.png)
6.  视频播放![视频播放](https://github.com/chaochuandea/tianyan/blob/master/screen_capture/media.png)




