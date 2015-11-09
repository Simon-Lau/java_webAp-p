/**
 * @author tonylp
 */

var ws;
var username;
var friendId;
var userId;
var flag = 0;
function init(){
	var data = location.search.substring(1).split("&");
	friendId = data[0].substring(data[0].indexOf("=")+1);
	userId = data[1].substring(data[1].indexOf("=")+1);
	startService();
	getUserName();
	sendMesg();
}

function getUserName(){
	$.ajax({
		url:'getUserNameAction.action?uid='+userId,
		type:'get',
		datatype:'json',
		success:function(data){
			username = data.username;
			$('#username').html(username);
		}
	});
}

function startService(){
	var url = "ws://127.0.0.1:8080/WebChat/echo.do";
	if ('WebSocket' in window) {
		ws = new WebSocket(url);
	} else if ('MozWebSocket' in window) {
		ws = new MozWebSocket(url);
	} else {
		alert("你的浏览器过时了！都不支持WebSocket！");
		return;
	}

	ws.onopen = function() {
		document.getElementById("remind").innerHTML += 'websocket open! Welcome!<br />';
	};
	ws.onmessage = function(event) {
		var data = event.data;
		var cont  = data.split(':');
		document.getElementById("content").innerHTML += cont[0]+" "+getDate()+'<br />'+
		cont[1]+ '<br />';
	};
	ws.onclose = function() {
		document.getElementById("remind").innerHTML += 'websocket closed! Byebye!<br />';
	};
}
function clean(){
	$('#content').html("");
}

function showRecord(){
	$('#content').html("");
	$.ajax({
		url:'showRecordAction.action?uid='+userId+'&fid='+friendId,
		type:'get',
		datatype:'json',
		success:function(data){
			console.log(data.record);
			var scont = "聊天纪录<br />用户名          好友名              时间                                                                  内容<br />";
			var record = data.record;
			for(var i = 0 ; i <record.length;i++){
				scont += record[i][0]+"   "+record[i][1]+"   "+record[i][2]+"   "+record[i][3]+"<br />";
			}
			$('#content').html(scont);
		}
	});
}

function addBack(){
	
}
function sendMesg(){
	var textMessage = $('.chatMes').val();
	console.log();
	var msg = userId+'#'+friendId+'#'+textMessage;
	if(flag == 0){
		ws.send(userId+'#'+friendId+'#$');
	}
	if (ws != null && textMessage != '') {
		ws.send(msg);
		 $('.chatMes').val("");
	}
	flag = 1;
}

function getDate(){
	var date = new Date();
	var year = date.getFullYear();
	var month = date.getMonth()+1;
	var day = date.getDay();
	var hours = date.getHours();
	var minutes = date.getMinutes();
	var seconds = date.getSeconds();
	var cont= year+"-"+month+"-"+day+" "+hours+":"+minutes+":"+seconds;
	return cont;
}
