# MEngine

![](https://hexleo.github.io/mengine/icon.png)

>项目定位：使用Web技术写Native应用，更接近原生体验，简单容易上手，能快速开发小型应用，典型的Hybrid应用框架。

>实现目标：更接近Native应用体验。为此做了一些新的尝试（对比PhoneGap），突出在M（Multi） Engine 多引擎上，通过业务逻辑线程分离与多页面同时加载，加快WebView的渲染，让应用感觉更接近Native体验。

先给个简单总结，多引擎加快页面渲染的技术特点(所谓多引擎指多线程与多WebView)

* UI与业务线程分离
* 多WebView且同时加载

ps：目前仅支持Android
![![![输入图片说明](https://static.oschina.net/uploads/img/201706/08210144_nebm.png "在这里输入图片标题")](https://static.oschina.net/uploads/img/201706/08210144_nebm.png "在这里输入图片标题")](https://static.oschina.net/uploads/img/201706/08210144_nebm.png "在这里输入图片标题")
# 1 Hello World
上手只用三分钟
### Step 1
创建文件夹，名称自定义（推荐以bundle开头），如：bundleHelloWorld

每一个独立的页面属于一个bundle
### Step 2
在bundleHelloWorld中创建index.html文件，并写自己的HTML代码

说明：每一个bundle文件夹包含index.html（当前bundle显示的页面），与app.js（在异步线程处理业务逻辑的js代码，不需要在index.html中声明，此文件可以不写）

### Step 3
创建res文件夹，并放入一张logo图片，如icon.png
## Step 4
创建 config.json 文件（复制内容时请先删除注释）
```java
{
  // 页面配置
  "pageConfig":{
    // 配置启动页面
    "splash":{
      "splashIcon":"icon.png",
      "splashBgColor":"#f5f5f5"
    },
    // 配置导航页面（首屏展示的页面）
    "nav":[
      {
        "bundleName":"bundleHelloWorld",
        "navIcon":"icon.png",
        "navName":"首页"
      }
    ]
  },
   // 全局配置（目前只支持标题栏背景颜色与刷新按钮颜色配置）
  "globalConfig":{
    "titleColor": "#000000",
    "refreshColor": "#000000"
  },
    // 页面bundle的配置，有多少个页面就有多少个bundle
  "bundleConfig":[
    {
        // 页面bundle的名称
      "bundleName":"bundleHelloWorld",
        // bundle所在目录名称（必须在assets目录下）
      "path":"bundleHelloWorld",
      "lazyInit":"false",
      "enableRefresh":"false"
    }
  ]
}
```

### Step 5
现在的目录结构如下
```
─┬config.json
  ├res
  │ └icon.png
  └bundleHelloWorld
     └index.html
```
复制所有文件到到项目的app/src/main/assets目录下（先备份assets目录下文件，删除后覆盖在此目录下）

### Step 6
这个项目可以用Android Studio打开，最后编译运行OK。（这个作者比较懒，还没开发出自动打包工具）

项目提供了一个简单的HelloWorld可以直接运行，文件在DemoHelloWorld中，参考README.md里的说明运行。

# 2 项目介绍
项目定位：更接近原生体验，简单容易上手，能快速开发小型应用。

项目特点

* Hybrid应用
* 每个bundle都是独立页面，拒绝使用单页面应用（现在的手机内存相对较大可以不用再局限于单页面的应用）
* 每个bundle分为index.html显示页面，于app.js业务逻辑处理js。
 * index.html只负责Web页面的渲染与消息传递，并不负责业务逻辑处理，其工作在UI线程（index.html里js的运行会阻塞UI渲染）
 * app.js只处理业务逻辑，不能直接进行UI交互，其Runtime不包含document等页面渲染使用的类（app.js运行在工作线程，不会阻塞UI线程渲染）
 * 通过将渲染与业务逻辑线程的分离，尽可能的提高渲染效率
        
* 所有WebView默认在应用启动时创建，打开新页面时不用等待页面加载，加快页面打开速度。如果为内存考虑，可以设置延迟加载（lazyInit），如果设置延迟加载则会在打开时再加载页面，一旦页面加载完成则不会释放
* 可以使用Native自定义的一些UI控件，协助WebView的操作，比如下拉刷新、首页导航、标题栏与返回导航等
* 页面返回(关闭页面)后WebView并不会被销毁，再次打开时会遗留有上次的数据，所以需要在finish（页面关闭回调）中做些清理操作，这样做的目的是为了减少WebView的重复创建开销

ps：本项目目前还处于初级阶段，以后还会引入更多的特性，使其更接近Native应用。

# 3 详细文档
可以直接安装DemoApk里的包，DemoApk的样例直接使用此框架实现，样例中有详细的使用文档，每个页面都是一个特性的样例，可以直接参考使用。



# 4 GitHub
https://github.com/hexleo/MEngine

