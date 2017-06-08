
// 接收Native的刷新请求
function onRefresh() {
	// 处理业务逻辑
	var result = "AppJs_just_return_string";
	// 结束刷新按钮
	finishRefresh();
	// 向index.html返回处理结果
	sendToWebView(result);
}



