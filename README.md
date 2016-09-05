# XUI
XUI 是一个安卓开发辅助项目库，它将项目中常用的功能、模块进行抽象整理，供新项目引用，这样可以加快项目的开发速度。


# 简述
XUI 为集功能、界面模块库，其中封装了 HTTP 网络请求、JSON 数据解析、文件操作等模块。写这个项目的初衷就是将我们平时写项目中的
	重复性工作进行抽象封装，这样可以省去大量的时间，提高工作的效率。目前这个项目库仍在继续的完善中。


### 版本说明
##### 时间：2016-08-26 V_1.0 初次版本
##### 说明：
1. AbstractAdapter -> 数据源基类
		说明：平时我们在写项目的时候，用到 Adapter 的时候喜欢继承 BaseAdapter 类，现在你可以直接继承 AbstractAdapter。
			AbstractAdapter 中对于数据列表 List，列表 Item 对应的 View 缓存进行一定的优化封装，省去重复工作，节省大量时间。

2. ViewPagerAdapter -> android.support.v4.view.PagerAdapter 实现类
		说明：与android.support.v4.view.ViewPager 类配合使用，使用者直接调用即可。

3. ViewHolderAdapter -> View 数据源基类
		说明：基于 AbstractAdapter 专门对于界面显示优化一个 Adapter，此类需要配合 ViewHolder 的实现类使用，具体 Item 对应的
			View 界面更新由 ViewHolder 来控制，这样可以将数据源与具体的界面显示分离，易于项目的维护与调试。

4. ViewHolder ->  View 界面数据填充实现基类
		说明：配合 ViewAdapter 使用，参考 ViewAdapter 说明。

5. AbstractActivity -> 抽象Activity
		说明：平时我们写 Activity 的时候，通常会继承 android.app.Activity ，然后再定制自己的具体实现 Activity。AbstractActivity
			封装了我们平时写一个界面是用到的标题、返回、右上角按纽等。AbstractActivity 已经实现 android.view.View.OnClickListener
			如果需要设置其它 View 的 Click 事件，可以传入当前 AbstractActivity ，然后复写 AbstractActivity 中的 onClick 方法。

6. ImageViewerActivity -> 图片浏览
		说明：根据 ImageViewActivity 中代码实现，传入相应的标题显示，图片路径列表，即可左右滑动浏览图片。

7. WebViewActivity -> 网页浏览界面
		说明：通过 Intent 传入网址、标题即可，加载网页时在标题栏显示加载进度。

8. coder 包 -> 数据加解密处理
		说明：目前有 DES 加解密工具类 DESCoder。

9. AbstractFragment -> 抽象 Fragment
		说明：基于 android.app.Fragment 封装的 AbstractFragment ，可以保存当前 Fragment 显示的状态信息，非首次加载可以显示之前保存
			的显示状态。

10. AbstractListViewFragment -> 抽象列表 ListView 的 Fragment
		说明：基于 AbstractFragment 封装添加了 ListView 控件，用于常用的列表场合。

11. AbstractPullRefreshListViewFragment -> 抽象上下拉刷新列表的 Fragment
		说明：基于 AbstractFragment 封装添加了 AutoListView 控件，用于常用的上拉、下拉刷新场合。

12. net 包 -> HTTP 网络请求

		Params：网络请求参数封装类
		Headers：网络请求头数据封装类
		AsynHttpRequest：异步 HTTP 网络请求
		SyncHttpRequest：同步 HTTP 网络请求
		FileUploader：文件上件封装类，简化文件上传。
		注：在使用网络请求包前请查看 ThreadUtil 辅助类的使用说明。

13. storage 包 -> 数据存储管理

		CacheStorage：缓存数据管理
		DataStorage：加解密数据管理
		DownloadTask：网络数据下载任务类
		SharePrefManager：对于 SharePrefercen 操作进行封装
		注：如果需拓展，详见 Storage 类，继承 Storage 即可。

14. 、util 包 -> 工具类

	说明：根据工具类的命名比较好猜出对应的工具类作用。下面单间说下几个重要的工具类。其中 ThreadUtil 这类非常重要。
	
		(1)Debugger：调试输出辅助类，建议应用中所有需要打印的地方都通过此类来实现。
		(2）FileUtil：文件操作辅助类，封装了大量关于文件操作的功能，例如文件的打开、读取、输入流、输出流控制等。
		(3）ImageLoader：网络图片加载显示辅助类，将网络图片缓存到本地然后显示到 ImageView 控件。
		(4）JSONUtil：基于谷歌的 Gson 封装了大量关于 JSON 操作。
		(5）ResourceFinder：资源映射辅助类，通过资源名称字符查询资源对应ID数字。
		(6）ThreadUtil：这个非常重要，HTTP 网络请求包、ToastUtil 等地方都会使用到 ThreadUtil 提供了线程池。
						   在使用 ThreadUtil 前，要在程序对应的 Application 实现类的 onCreate 中初始化 ThreadUtil。
						   ThreadUtil 中提供了一个最多7个线程了池环境。同时还有全局添加到 UI 线程、线程池等接口。
		(7）ToastUtil：封装了 Android 中的 Toast 显示功能，在应用任意地方均可调用，即使在非 UI 线程中。
		(8）ViewFinder：封装了 Android 中常见的控件强制转换，使用此类无需转换。
		(9）ZipUtil：封装了文件解压缩相关功能。
	
15. view 包 -> 自定义控件类

	主要是自定义或者从网络中获取项目中常见的控件，控件比较多，简单介绍每个控件的功能。
	
		(1）CircleImageView：圆形 ImageView 控件。
		(2）ClearEditText：带有清空按钮的 EditText 控件。
		(3）AutoListView：下拉、上拉刷新列表控件。
		(4）BottomPopupMenu：底部弹出菜单控件
		(5）AutoRadioButton:通过 style_xui_radio_button.xml 中的属性值可以实现单选按钮点击后实现不同的效果。
			   				  例如图片更换、显示通知红色小点等。目前可以用它来实现首页底部tab切换功能。
		(6）AutoLinearLayout：基于 LinearLayout 类，动态调整子控件进行换行显示。使用时注意在布局文件中设置 orientation 为水平布局。
		(7）FeedImageAdapter 与 FeedGridView 配合使用，可以实现微信朋友圈，多张图片网络显示效果。
		(8）HorizontalProgressBar：水平方向的进度条控件
		(9）LooperViewAdapter：基于 android.support.v4.view.ViewPager，添加了自动滑动的功能。
		(10）PopopMenu：基于 PopupWindow 封装弹出菜单或者界面控件。
		(11）PullRefreshListView：上拉、下拉刷新控件。
		(12）RoundProgresBar：圆形进度条控件。
		(13）VerifcationCode：项目的常用的点击获取验证码控件，显示计时效果。
		(14）WrapperListView：自动高度 ListView 控件。

