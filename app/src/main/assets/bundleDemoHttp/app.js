
// 接收页面的事件通知
function receiveFromWebView(data) {
    // 发起get的请求
    requestGet("https://hexleo.github.io/refresh.html",
        {"p1":"cool", "p2":123}, // 参数
        function(data) {
            sendToWebView(data);
        },
        function(error) {
            sendToWebView(error);
            MLog("error:" + data);
    });
}

