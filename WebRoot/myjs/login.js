/**
 * @author tonylp
 */
function loginValidate(){
	$('#error').text("");
	var username = $('#userName').val();
	var password = $('#passWord').val();
	console.log(username+password);
		$.ajax({
			url:'loginAction.action?username='+username+'&pwd='+password,
			type:'get',
			async:false,
			dataType:'json',
			success:function(data){
				if(data.resultflag == 0){
					$('#error').text("UserName or Password is Error!");
				}else if(data.resultflag == -1){
					$('#error').text("No such username");
				}else{
					location.href="./index.html";
				}
			}
		});
}

function Register(){
	location.href="./register.html";
}
