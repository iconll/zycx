$(function () {

    $.fn.dictUtil = function(options){
        var opts = $.extend({}, $.fn.dictUtil.defaults, options);
        if(dictValueMap.size()==0){
            $.post(opts.url,function(r){
                if(r.result){

                    $.each(r.data,function(index,info){
                        dictValueMap.put(info.type+"_"+info.code,info.value+"|"+info.text);
                        dictCodeMap.put(info.type+"_"+info.value,info.code+"|"+info.text)


                        var jsonDict = {"id":info.id,"text":info.text};

                        if(dictInfoMap.get(info.type) == null){

                            var dictList = [jsonDict];
                            dictInfoMap.put(info.type,dictList);
                        }else{
                            var dictList = dictInfoMap.get(info.type);
                            dictList.push(jsonDict);
                            dictInfoMap.put(info.type,dictList);
                        }
                    })
                }
            });
        }
    }
    $.fn.dictUtil.defaults = {url:"/dict/loadDict"}
})

dictValueMap= parent.dictValueMap;
if(dictValueMap==undefined){
    dictValueMap = new Map();
}
dictCodeMap = parent.dictCodeMap;
if(dictCodeMap==undefined){
    dictCodeMap = new Map();
}
dictInfoMap = parent.dictInfoMap;
if(dictInfoMap==undefined){
    dictInfoMap = new Map();
}
// 根据type和value来获取数据字典的code值
function getCodeDictCode(type,value){
    return dictCodeMap.get(type+"_"+value).split("|")[0];
}

// 根据type和value来获取数据字典的text值
function getCodeDictText(type,value){
    return dictCodeMap.get(type+"_"+value).split("|")[1];
}

//根据type和code来获取数据字典的value值
function getDictValue(type,code){
    var value = dictValueMap.get(type+"_"+code);
    return value.split("|")[0];
}
//根据type和code来获取数据字典的text值
function getDictText(type,code){
    var value = dictValueMap.get(type+"_"+code);
    return value.split("|")[1];
}

/**
 * 根据字典表type类型加载对应的数据
 * @param type
 * @returns list
 */
function getDictInfoListByType(type){

    return dictInfoMap.get(type);
}
/**
 * 加载字典值到指定的选择框中
 * @param selectId 选择框ID
 * @param type  字典值类型
 * @param id    需要设置为选中状态的数据ID
 */
function loadDictIntoToSelectOption(selectId,type,id){
    var selectItem = $("#" + selectId);

    selectItem.append("<option value=''>请选择</option>");
    var dictList = getDictInfoListByType(type);

    if(dictList != null && dictList.length > 0){
        $.each(dictList,function(index,item){
            if(id == item.id){

                selectItem.append("<option value='"+item.id+"' selected>"+item.text+"</option>");
            }else{

                selectItem.append("<option value='"+item.id+"'>"+item.text+"</option>");
            }
        });
    }
}
function Map() {
    var struct = function(key, value) {
        this.key = key;
        this.value = value;
    }

    var put = function(key, value){
        for (var i = 0; i < this.arr.length; i++) {
            if ( this.arr[i].key === key ) {
                this.arr[i].value = value;
                return;
            }
        }
        this.arr[this.arr.length] = new struct(key, value);
    }

    var get = function(key) {
        for (var i = 0; i < this.arr.length; i++) {
            if ( this.arr[i].key === key ) {
                return this.arr[i].value;
            }
        }
        return null;
    }

    var remove = function(key) {
        var v;
        for (var i = 0; i < this.arr.length; i++) {
            v = this.arr.pop();
            if ( v.key === key ) {
                continue;
            }
            this.arr.unshift(v);
        }
    }

    var size = function() {
        return this.arr.length;
    }

    var isEmpty = function() {
        return this.arr.length <= 0;
    }
    this.arr = new Array();
    this.get = get;
    this.put = put;
    this.remove = remove;
    this.size = size;
    this.isEmpty = isEmpty;
}