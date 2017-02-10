### 全屏切换效果

来源：慕课网

#### 全屏css

1. 全屏的元素与其父元素都要设置 height: 100%
2. 将 html body 标签设置 height:100%

#### 上下全屏滚动

```
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>Document</title>
    <style media="screen">
      * {
        padding: 0;
        margin: 0;
      }
      html, body {
        height: 100%;
        /*overflow: hidden;*/
      }
      #container,.sections,.section {
        height: 100%;
      }
      #section0,
      #section1,
      #section2,
      #section3 {
        background-color: #000;
        background-size: cover;
        background-position: 50% 50%;
        text-align: center;
        color: white;
      }
      #section0 {
        background-image: url(images/1.jpg);
      }
      #section1 {
        background-image: url(images/2.jpg);
      }
      #section2 {
        background-image: url(images/3.jpg);
      }
      #section3 {
        background-image: url(images/4.jpg);
      }
    </style>
  </head>
  <body>
    <div id="container">
      <div class="sections">
        <div class="section" id="section0">
          <h3>this is the page</h3>
        </div>
        <div class="section"  id="section1">
          <h3>this is the page</h3>
        </div>
        <div class="section" id="section2">
          <h3>this is the page</h3>
        </div>
        <div class="section"  id="section3">
          <h3>this is the page</h3>
        </div>
      </div>
    </div>
  </body>
</html>

```

#### 左右滑动

```
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>Document</title>
    <style media="screen">
      * {
        padding: 0;
        margin: 0;
      }
      html, body {
        height: 100%;
        /*overflow: hidden;*/
      }
      #container,.sections,.section {
        height: 100%;
      }
      #section0,
      #section1,
      #section2,
      #section3 {
        background-color: #000;
        background-size: cover;
        background-position: 50% 50%;
        text-align: center;
        color: white;
      }
      #section0 {
        background-image: url(images/1.jpg);
      }
      #section1 {
        background-image: url(images/2.jpg);
      }
      #section2 {
        background-image: url(images/3.jpg);
      }
      #section3 {
        background-image: url(images/4.jpg);
      }
      .left {
        float: left;
        width: 25%;
      }
      #container {
        width: 400%;
      }
    </style>
  </head>
  <body>
    <div id="container">
      <div class="sections">
        <div class="section left" id="section0">
          <h3>this is the page</h3>
        </div>
        <div class="section left"  id="section1">
          <h3>this is the page</h3>
        </div>
        <div class="section left" id="section2">
          <h3>this is the page</h3>
        </div>
        <div class="section left"  id="section3">
          <h3>this is the page</h3>
        </div>
      </div>
    </div>
  </body>
</html>
```

#### JQuery 插件开发

两种方式：
1. 静态方法： `$.myPlugin = function(){}`
2. 对象方法: `$.fn.myPlugin = function(){}`

单例模式：

```
$.fn.MyPlugin = function(){
  var me = $(this), instance = me.data('myPlugin');
  if(!instance) {
    me.data('myPlugin', (instance = new MyPlugin()))
  }
}
```
