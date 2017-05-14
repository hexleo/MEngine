
var callBackCount = 0;
var callBackMap = {};

function genCallBackLink(callback) {
    callBackName = "cb_" + callBackCount++;
    callBackMap[callBackName] = callback;
    return callBackName;
}

function callBackListener(callBackName, data) {
    callback = callBackMap[callBackName];
    callback(data);
}

function requestGet(url,successCallBack, errorCallBack) {
    requestGet(url, "", successCallBack, errorCallBack);
}

function requestGet(url,param,successCallBack, errorCallBack) {
    request("GET", url, param, successCallBack, errorCallBack);
}
function requestPost(url,param,successCallBack, errorCallBack) {
    request("Post", url, param, successCallBack, errorCallBack);
}

function request(
method,
url,
param,
successCallBack,
errorCallBack
) {
    requestNative(method, url, JSON.stringify(param),
    genCallBackLink(successCallBack),
    genCallBackLink(errorCallBack));
}


