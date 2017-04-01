
function init(param) {
    MLog("app.js MLog init recv param=" + param);
}

function receiveFromWebView(data) {
    if (data == "onload") {
        sendToWebView("you should do something to refresh ui");
    } else {
        MLog("app.js MLog " + data);
    }
}

function onRefresh() {
    MLog("app.js MLog onRefresh");
    var v = globalVarRead("key1");
    MLog("app.js MLog globalVar(key1)=" + v);
    finishRefresh();
}

