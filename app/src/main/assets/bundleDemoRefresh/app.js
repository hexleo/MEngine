
// 接收页面的事件通知
function onRefresh() {
    // 发起get的请求
    requestGet("https://hexleo.github.io/mengine/demo_http_request.html",
        {"p1":"cool", "p2":123}, // 参数
        function(data) {
            finishRefresh();
            sendToWebView(data);
        },
        function(error) {
            finishRefresh();
            sendToWebView(error);
            MLog("error:" + data);
    });
}



