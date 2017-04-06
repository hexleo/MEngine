
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
    common1();
    common2();
    requestGet("https://hexleo.github.io/refresh.html", // data.json
    {"a":"cool", "b":123},
    function(data) {
        // data need decodeURIComponet
        MLog("success:" + decodeURIComponent(data));
        finishRefresh();
        param = {"action":"refresh", "uiData":data};
        sendToWebView(JSON.stringify(param));
    },
    function(error) {
        MLog("error:" + data);
        finishRefresh();
    });

}

