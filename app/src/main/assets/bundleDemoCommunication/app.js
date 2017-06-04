// 接收来自index.html消息
function receiveFromWebView(data) {
    // 会在logcat中打印出日志
    MLog("bundleDemoCommunication app.js recv data=" + data);
    // 解码json格式数据
    param = JSON.parse(decodeURIComponent(data));
    // 向index.html发送消息
    message = JSON.stringify({"html": "RecvByAppJs=" + param["inputMsg"], "number": param["number"] ,"result" : f(param["number"])})
    sendToWebView(message);
}


function f(num) {
    if (num == 0) return 0;
    if (num == 1) return 1;
    return f(num - 1) + f(num - 2);
}

