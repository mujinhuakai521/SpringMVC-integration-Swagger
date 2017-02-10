function myReady(fn) {

  if(document.addEventListener) {
    document.addEventListener('DOMContentLoaded', fn, false);
  } else {
    IEContentLoaded(fn);
  }

  //IE 模拟 DOMContentLoaded
  function IEContentLoaded(fn) {
    var d = window.document;
    var done = false;
    //只执行一次用户的回调函数init
    var init = function() {
      if(!done) {
        done = true;
        fu();
      }
    };

    (function(){
      try {
        d.documentElement.doScroll('left');
      } catch (e) {
        setTimeout(arguments.callee, 50);
        return;
      }
      init();
    })();
    
    //监听document的加载状态
    d.onreadystatechange = function() {
        if(d.readyState == 'complete') {
          d.onreadystatechange = null;
          init();
        }
    }
  }
}
