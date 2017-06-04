// json字符串转成json对象
function stringToJson(data) {
    return JSON.parse(decodeURIComponent(data));
}

// json对象转换成字符串
function jsonToString(json) {
    return JSON.stringify(json);
}
