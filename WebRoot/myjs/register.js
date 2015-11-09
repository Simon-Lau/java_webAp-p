/**
 * @author tonylp
 */

var lng;
var lat;
function init(){
	map.setDefaultCursor("pointer");
	map.addEventListener("click",function(e){
    var content = e.point.lng+","+e.point.lat;
    lng = e.point.lng;
    lat = e.point.lat;
    $('#locPoint').html(content);
});
}

function changepic(){
	var num = $('#pic').find('option:selected').val();
	var pic = 'mycss/images/headIcons/'+num+'.gif';
	$('#showpic').attr('src',pic);
}


function regSubmit(){
	$('#errorName').html("");
	$('#errorPwd').html("");
	if($('#passWord').val() == $('#pwd2').val() ){
		var userName = $('#userName').val();
		var passWord = $('#passWord').val();
		var pic = $('#pic').find('option:selected').val();
		var location = $('#suggestId').val();
		$.ajax({
			url:'registerAction.action?username='+userName+'&pwd='+passWord+'&pic='+pic+
			'&location='+location+'&lang='+lng+'&lat='+lat,
			type:'get',
			async:false,
			dataType:'json',
			success:function(data){
				console.log(data.resultflag);
				if(data.resultflag == -1){
					$('#errorName').html("用户名已经存在");
					$('#userName').html("");
				}else{
					location.href="./login.html";
				}
			}
		});
	}else{
		$('#errorPwd').html("密码不一致！");
	}
	
}

function regReset(){
	$('#errorPwd').html("");
	$('#errorName').html("");
	$('#username').val("");
	$('#passWord').val("");
	$('#pwd2').val("");
	$('#suggestId').val("");
}
