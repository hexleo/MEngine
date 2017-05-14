
function init(param) {
    MLog("MEngineBundle app.js MLog init recv param=" + param);
    MLog("MEngineBundle app.js MLog init recv param=" + param);
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
        MLog("read from db:" + keyValueRead("htmlData"));
        finishRefresh();
        param = {"action":"refresh", "uiData":data};
        sendToWebView(JSON.stringify(param));
        jumpTo("bundle3", "some params");
    },
    function(error) {
        MLog("error:" + data);
        finishRefresh();
    });
}

function finish() {
    MLog("bundle2 catch finish event");
}

