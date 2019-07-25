/**
 * 加载开始和结束日期选择框
 * @param startId 开始日期选择框ID
 * @param endId 结束日期选择框ID
 */
function loadStartAndEndDatetimepicker(startId,endId){
    // 开始时间：
    $('#'+startId).datetimepicker({
        autoclose : true,
        bootcssVer:3, // 显示箭头
        format: 'yyyy-mm-dd',
        startView: 2, // 日期时间选择器打开之后首先显示的视图。
        minView: 2, // 日期时间选择器所能够提供的最精确的时间选择视图。
        forceParse: false, // 当选择器关闭的时候，是否强制解析输入框中的值。
        language: 'zh-CN',
        clearBtn: true
    }).on('changeDate',function(e){
        var jjStartTime = $('#'+startId).val();
        $('#'+endId).datetimepicker('setStartDate',jjStartTime);
        $('#'+startId).datetimepicker('hide');
    });
    // 结束时间：
    $('#'+endId).datetimepicker({
        autoclose : true,
        bootcssVer:3, // 显示箭头
        format: 'yyyy-mm-dd',
        weekStart: 1,
        startView: 2,
        minView: 2,
        forceParse: false,
        language: 'zh-CN',
        clearBtn: true
    }).on('changeDate',function(e){
        var jjEndTime = $('#'+endId).val();
        $('#'+startId).datetimepicker('setEndDate',jjEndTime);
        $('#'+endId).datetimepicker('hide');
    });
}
/**
 * 处理table表头和内容对不齐问题
 */
function clTable(){

    //var h = $('.fixed-table-header ').find("table");
    var b = $('.fixed-table-body ').find("table");

    //判断浏览器类型
    var userAgent=window.navigator.userAgent;

    $.each(b,function(index,item){
        $(this).find("tr").eq(0).find("th").each(function(i,m){
            var thW = $(this).width();
            var h = $('.fixed-table-header ').find("table").eq(index);
            var hthW = h.find("th").eq(i);
            //var hthW = h.find("th").eq(i);

            if(userAgent.indexOf('Firefox')>-1){

                hthW.find('.fht-cell').width(thW);
            }else if(userAgent.indexOf('Chrome')>-1){

                hthW.width(thW);
            }
        });
    });
    /*b.find("tr").eq(0).find("th").each(function(index,item){
        var thW = $(this).width();
        var hthW = h.find("th").eq(index);

        if(userAgent.indexOf('Firefox')>-1){

            hthW.find('.fht-cell').width(thW);
        }else if(userAgent.indexOf('Chrome')>-1){

            hthW.width(thW);
        }
    })*/
}
/**
 * 设置日期类型value值
 * @param id 表单类型ID
 * @param value 需要回显的Value
 */
function setDateValue(id,value){

    if(value != null){
        var date = new Date(value);
        $("#"+id).val(dateFormat("yyyy-MM-dd",date));
    }
}
/*****时间格式化处理****/
function dateFormat(fmt,date) {
    var o = {
        "M+" : date.getMonth()+1,                 //月份
        "d+" : date.getDate(),                    //日
        "h+" : date.getHours(),                   //小时
        "m+" : date.getMinutes(),                 //分
        "s+" : date.getSeconds(),                 //秒
        "q+" : Math.floor((date.getMonth()+3)/3), //季度
        "S"  : date.getMilliseconds()             //毫秒
};
    if(/(y+)/.test(fmt))
        fmt=fmt.replace(RegExp.$1, (date.getFullYear()+"").substr(4 - RegExp.$1.length));
    for(var k in o)
        if(new RegExp("("+ k +")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
    return fmt;
}

/**
 * 加载日期下拉框
 * @param id 需要加载下拉框的表单项id
 */
function loadDatetimepicker(id){

    $("#" + id).datetimepicker({
        format: "yyyy-mm-dd",
        todayBtn : true,
        language:'zh-CN',
        minView : 'month',
        autoclose:true
    });
}

/**
 * 加载网点信息选择框
 * @param selectId 选择框表单项ID
 * @param value 根据id值回显对应的选择项
 */
function loadBankInfoSelectOption(selectId,value){

    $.get("/bankInfo/findAll",function(data){

        var bankInfoSelect = $("#" + selectId);
        bankInfoSelect.html("");
        if(data != null){

            bankInfoSelect.html("<option value = ''>请选择</option>");
            $.each(data,function(index,item){

                if(item.id == value){

                    bankInfoSelect.append("<option value = '"+item.id+"' selected>"+item.bankName+"</option>");
                }else{

                    bankInfoSelect.append("<option value = '"+item.id+"'>"+item.bankName+"</option>");
                }
            });
        }else{

            bankInfoSelect.append("<option value = ''>未加载到网点信息</option>");
        }
    })
}