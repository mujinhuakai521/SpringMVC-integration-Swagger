### DOM探索之基础详解篇

来源：慕课网课程

#### 案例

四个img最开始是平铺布局，通过添加css时四个图片堆叠在一起，并相对于父元素使用绝对定位。

这样后边三个图片会被第一个图片遮挡，通过js操作：

```
for(var i=1,len=imgs.length;i<len;i++) {
  imgs[i].style.left = i * exposeWidth + 'px';
}
```

让后边三个图片依次向右偏移 exposeWidth 距离。

但是这样第一个图片没有完全显示，将后边三个图片再向右偏移 imgWidth - exposeWidth 个距离，这样计算出来偏移的距离就是：

```
imgs[i].style.left = imgWidth + (i - 1) * exposeWidth + 'px';
```

然后添加鼠标事件，为了方便计算是每次都把整个布局还原到初始状态。而第一个图片初始就是全部展示的，故只需要重新计算后三个图片。
观察可以知道每一个图片都只显示了左边 exposeWidth 的部分，隐藏了右边 imgWidth - exposeWidth 的部分，要显示当前鼠标所在的图片，
只需要第二个图片到当前图片向左移动 imgWidth - exposeWidth 的距离。所以有如下代码：

```
var translate = imgWidth - exposeWidth;
imgs[j].style.left = parseInt(imgs[j].style.left, 10) - translate + 'px';
```

这里有一个关于闭包的小知识点：
每一次 `imgs[i].onmouseover = function(){}` 都会创建了一个闭包，如果在这个匿名函数中直接使用`for`循环中的变量，所有 `img` 的 `onmouseover` 会共享一个 i, 而最终的 i 是累加到 4 的，也就是所有的 onmouseover 上都变成了如下函数，img[j] 会超出索引范围。

```
function() {
  resetImgs();
  for (var j = 1; j<=4;j++) {
    imgs[j].style.left = parseInt(imgs[j].style.left, 10) - translate + 'px';
  }
}
```

这里解决方法就是声明一个方法然后立即执行，也就是再见一个闭包，将当前循环的 i 保存在此闭包中，与其他循环里的 i 区分开。

#### DOM 知识

DOM 节点类型：

| 节点类型       | 数值常量         | 字符常量  |     nodeNmae |     nodeValue |
| ------------- |:-------------:| -----:|---------:|---------:|
| Element 元素节点      | 1 | ELEMENT_NOE |标签名|null|
| Attr  属性节点   | 2      |   ATTRIBUTE_NOE |属性名|属性值|
| Text 文本节点 | 3      |    TEXT_NODE |#text|节点包含的文本|
| Comment 注释节点 | 8      |    COMMENT_NODE |＃comment|注释的内容|
| Document 文档节点 | 8      |    DOCUMENT_NODE |||
| DocumentType 文档类型节点 | 8      |    DOCUMENT_TYPE_NODE |doctype的名称|null|
| DocumentFragment 文档片段节点 | 8      |    DOCUMENT_FRAGMENT_NODE |#document-fragment|null|

数值常量兼容所有浏览器。

#### dom ready 实现方法

根据浏览器区分：

1. 存在DOMContentLoaded事件的，直接注册事件回调函数。
2. 不存在DOMContentLoaded事件的IE, 使用 `documentElement.doScroll('left');`轮询，需要注意只调用一次回调函数。
3. 在iframe中则通过document的 onreadystatechange来实现。

#### 判断节点类型

参考 ./NodeType/example.html

#### 节点继承层次

```
document.createElement('p');
```

Object <--- Function <--- EventTarget <--- Node <--- Element <--- HTMLElement <--- HTMLParagraphElement

```
document.crateTextNode('xx');
```

Object <--- Function <--- EventTarget <--- Node <--- CharacterData <--- Text

虚拟dom操作

#### HTML 嵌套规则

块状元素：自成一行。

内联元素：多个时在同一行，无width与height。

嵌套规则：
1. 块状元素可包含块状元素与内联元素，内联元素不能包含块状元素。
2. p标签内不能包括其他块状元素
3. h1、h2、h3、h4、h5、h6、p、dt 这些块状元素不能包含块状元素
4. 块状元素与块状元素并列，内联元素与内联元素并列。

小知识：
1. select 标签的属性 mutiple，与内部标签 optgroup。
2. img 标签的 usermap 属性与 map 标签。
3. object 标签与 param 标签。
4. table 标签内的 colgroup 与 col 标签
