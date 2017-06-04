// 定义在common文件夹下的所有js文件都会与app.js合并
// 其变量只有bundle内部可用，不同bundle间不能通过common.js进行共享
// 实质创建bundle时，其只在对应bundle运行时中运行
function commonFunction() {
    sendToWebView("call_from_commonFunction");
}

// json字符串转成json对象
function stringToJson(data) {
    return JSON.parse(decodeURIComponent(data));
}

// json对象转换成字符串
function jsonToString(json) {
    return JSON.stringify(json);
}
