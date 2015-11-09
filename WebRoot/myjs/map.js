/*
 1.全局变量 map，通过
new BMap.Map('allMap')设置，
其中allMap是div标签。
*/
var map;
var tableTest;
var uid;
function initMap(){
     	 var point = null;    // 创建点坐标  
	  $.ajax({
	   url:  'getSelfPositionAction.action',
	   type: 'get',
	   async: false,
	   datatype: 'json',
	   success: function(data){
		   //console.log(data);
	       var lng = data.lng;
	       var lat = data.lat;
	       uid = data.uid;
	   	if(lng != null && lat != null){
	   		 point = new BMap.Point(lng,lat);
	 		 }
	 	 } 	
	});
   map = new BMap.Map("allmap");                    // 创建Map实例
   map.centerAndZoom(point,14);                     // 初始化地图,设置中心点坐标和地图级别。
   map.enableScrollWheelZoom();                            //启用滚轮放大缩小
   map.addControl(new BMap.NavigationControl());               // 添加平移缩放控件
   map.addControl(new BMap.ScaleControl());                    // 添加比例尺控件
   map.addControl(new BMap.OverviewMapControl());              //添加缩略地图控件
   map.addControl(new BMap.MapTypeControl());             //添加地图类型控件
   map.setCurrentCity("哈尔滨");          // 设置地图显示的城市 此项是必须设置的
}

function getMark(){
	$.ajax({
		url: 'getPositionAction.action',  //replace with the real action
		type: 'post',
		async: false,	
		datatype: 'json',
		success: function(data){
			var position = data.position;
			console.log(position);
			for(var i=0;i<position.length;i++){
				//console.log(position[i][4]);
				var point = new BMap.Point(position[i][4],position[i][5]);
				addMarker(point,position[i][0],position[i][1],position[i][2],position[i][3]);	
			}			
		}
	});	
} 

function addMarker(point,fid,username,sentence,pic){
	var marker = new BMap.Marker(point);
			
	marker.addEventListener("click", function(e){   
	   var infoWindow = getInfo(fid,username,sentence,pic);
	   this.openInfoWindow(infoWindow);
	   document.getElementById('imgDemo').onload = function(){
		   infoWindow.redraw();
	   };
	});
	map.addOverlay(marker);
}

function getInfo(fid,username,sentence,pic){
	console.log(fid+username+sentence+pic);
	var sContent ="<h4 style='margin:0 0 5px 0;padding:0.2em 0'>"+username+"</h4>" + 
	"<img style='float:right;margin:4px' id='imgDemo' src='mycss/images/headIcons/"+pic+".gif' width='50' height='65' title='头像'/>" + 
	"<p style='margin:0;line-height:1.5;font-size:13px;text-indent:2em'>"+sentence+"<br /><a href='chat.html?fid="+fid+"&uid="+uid+"'>聊天</a>" +
			"&nbsp;&nbsp;&nbsp;&nbsp;<a href='#' onclick='addFriend("+fid+")'>添加好友</a></p>" +
	"</div>";
	var infoWindow = new BMap.InfoWindow(sContent);  // 创建信息窗口对象
	return infoWindow;
}

function getData(){
	$('#userList').html("");
	var content ="<p>姓名            个性签名             操作</p>";
	$.ajax({
		url:'getAllUserAction.action',
		type:'post',
		datatype:'json',
		success:function(data){
			var userInfo = data.userInfo;
			for(var i = 0; i <userInfo.length;i++){
				content	+= "<p>"+userInfo[i][0]+"   "+userInfo[i][1]+"   "+"<a href='chat.html?fid="+userInfo[i][2]+"&uid="+uid+"'>聊天</a>&nbsp;&nbsp;&nbsp;&nbsp;"+
				"<a href='#' onclick='addFriend("+userInfo[i][2]+")'>添加好友</a></p>";
			}
			$('#userList').html(content);
		}
	});
}

function addFriend(fid){
	$.ajax({
		url:'addFriendAction.action?uid='+uid+'&fid='+fid,
		type:'post',
		datatype:'json',
		success:function(data){
			if(data.resultflag == 1){
				alert("成功添加好友！");
			}else{
				alert("添加失败！");
			}
		}
	});
}
