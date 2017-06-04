// 进入页面时用init函数监听
function init(param) {
    sendToWebView(param);
}
// 接收页面的事件通知
function receiveFromWebView(data) {
    // 跳转页面，并传入参数
    jumpTo(data, "someParams");
}

