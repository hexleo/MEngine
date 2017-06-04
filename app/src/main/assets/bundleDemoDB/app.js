
// 接收页面的事件通知
function receiveFromWebView(data) {
    var json = stringToJson(data);
    if (json["action"] == "w") {
        keyValueWrite(json["key"] , json["value"]);
    } else if (json["action"] == "r") {
        var value = keyValueRead(json["key"]);
        sendToWebView(jsonToString({"action":"r", "value" : value}));
    }
}

