
// 接收页面的事件通知
function receiveFromWebView(data) {
    var json = stringToJson(data);
    if (json["action"] == "w") {
        globalVarWrite(json["key"] , json["value"]);
    } else if (json["action"] == "r") {
        var value = globalVarRead(json["key"]);
        sendToWebView(jsonToString({"action":"r", "value" : value}));
    }
}

