/**
 * @author tonylp
 */

function init(){
	initTable();
	//getData();
	
}

var tableTest;
var charts;
//初始化表格表头
function initTable(){
    tableTest = $('#tableTest').dataTable({
        "bJQueryUI": true,
        "sPaginationType": "full_numbers",
        'aaSorting':[ [1,'asc'],[2,'asc'] ],
        'aoColumns':[
        {'sTitle':'Date', 'sWidth':'10%','sClass':'center'},
        {'sTitle':'Locks', 'sWidth':'20%','sClass':'center'},
        {'sTitle':'Stocks', 'sWidth':'20%','sClass':'center'},
        {'sTitle':'Barrels', 'sWidth':'20%','sClass':'center'},
        ]
    });
}

function getData(){
	var date = new Date();
	var month = date.getMonth()+1;
	$.ajax({
		url:'getSalespersonStatAction.action?month='+month,
		type:'get',
		async:false,
		datatype:'json',
		success:function(data){
			var sellsource = data.stats;
			tableTest.fnClearTable();
			tableTest.fnAddData(sellsource);
		}
	});
}

