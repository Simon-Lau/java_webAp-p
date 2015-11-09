
var tableTest;
var userId;
function initTable(){
	    tableTest = $('#tableTest').dataTable({
		"bJQueryUI": true,
		"sPaginationType": "full_numbers",
    	'aaSorting':[ [1,'asc'],[2,'asc'] ],
		'aoColumns':[
		{'sTitle':'头像','sClass':'center',
		 'mRender':function (data,type,full){
			 return '<img src="mycss/images/headIcons/'+data+'.gif"/>';
		}},
		{'sTitle':'姓名', 'sClass':'center'},
		{'sTitle':'个性签名', 'sClass':'center'},
		{'sTitle':'操作',"bSearchable": false, 'sClass':'center',
			"mRender": function ( data, type, full ) {
        		return '<a href="chat.html?friendId='+data+'&userId='+userId+'">聊天</a>&nbsp;&nbsp;&nbsp;&nbsp;'+
        		       '<a href="#" onclick="deleteFriend('+data+')">删除好友</a><br />';
      	}}
		]
	});
}

//为表格加载数据
function getData(){
	$.ajax({
		url:'getFriendsAction.action',
		type:'post',
		datatype:'json',
		success:function(data){
			var friendList = data.friendList;
			userId = data.userid;
			tableTest.fnClearTable();
			tableTest.fnAddData(friendList);
		}
	});
}

function deleteFriend(fid){
	$.ajax({
		url:'deleteFriendAction.action?uid='+userId+'&fid='+fid,
		type:'post',
		datatype:'json',
		success:function(data){
			if(data.resultflag == 1){
				alert("成功删除！");
			}else{
				alert("删除失败！");
			}
		}
	});
	  getData();
}