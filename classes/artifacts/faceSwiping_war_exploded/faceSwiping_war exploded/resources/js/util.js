///**
// * 
// * js工具类
// * 
// * @author 谢长春 2015-11-5 14:35:11
// */
//// Base64编码集定义
//var _keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
///**
// * 返回信息代码表
// * 
// * 当请求查询列表时，>0表示成功返回数据条数 =0表示无数据 <0表示异常代码表
// * 
// * 当请求操作（增、删、改）时，=1表示操作成功 =0表示失败 <0表示异常代码表
// * 
// * @author Jason Xie 2015-11-5 14:42:41
// * 
// */
//var Codes = {
//	success : 1 // 成功
//	,failure : 0 // 查询表示无数据，增删改表示失败
//	,params_null : -1 // 请求参数列表为空
//	,params_parse_error : -2 // 请求参数解析失败
//	,user_not_exist : -3 // 用户不存在
//	,pwd_error : -4 // 密码错误
//};
//
///* 将字符串转换为数值，当值为空时 返回0 */
//function parseToInt(value){
//	value = $.replaceAll(value,",","");
//	if(isNull(value)){
//		return 0;
//	}
//	return parseInt(value);
//};
///* 将字符串转换为浮点型数值，当值为空时 返回0 */
//function parseToFloat(value){
//	value = $.replaceAll(value,",","");
//	if(isNull(value)){
//		return 0;
//	}
//	return parseFloat(value);
//};
///* 取得字符串的字节长度 */
//function strLength(str) {
//		var i;
//		var len;
//		len = 0;
//		for (i = 0; i < str.length; i++) {
//			if (str.charCodeAt(i) > 255) {
//				len += 2;
//			} else {
//				len++;
//			}
//		}
//		return len;
//	};
///* 判断是否为数字，是则返回true,否则返回false */
//function checkNumber(value) {
//		if (/^\d+$/.test(value)) {
//			return true;
//		} else {
//			return false;
//		}
//	};
///* 判断是否为自然数，是则返回true,否则返回false */
//function checkNaturalNumber(value) {
//		if (/^[0-9]+$/.test(value) && (value > 0)) {
//			return true;
//		} else {
//			return false;
//		}
//	};
///* 判断是否为整数，是则返回true,否则返回false */
//function checkInteger(value) {
//		if (/^(\+|-)?\d+$/.test(value)) {
//			return true;
//		} else {
//			return false;
//		}
//	};
///* 判断是否为实数，是则返回true,否则返回false */
//function checkFloat(value) {
//		if (/^(\+|-)?\d+($|\.\d+$)/.test(value)) {
//			return true;
//		} else {
//			return false;
//		}
//	};
///* 用途：检查输入字符串是否只由汉字组成 如果通过验证返回true,否则返回false */
//function checkZh(value) {
//		if (/^[\u4e00-\u9fa5]+$/.test(value)) {
//			return true;
//		}
//		return false;
//	};
///* 判断是否为小写英文字母，是则返回true,否则返回false */
//function checkLowercase(value) {
//	if (/^[a-z]+$/.test(value)) {
//		return true;
//	}
//	return false;
//};
///* 判断是否为大写英文字母，是则返回true,否则返回false */
//function checkUppercase(value) {
//		if (/^[A-Z]+$/.test(value)) {
//			return true;
//		}
//		return false;
//	};
///* 判断是否为英文字母，是则返回true,否则返回false */
//function checkLetter(value) {
//		if (/^[A-Za-z]+$/.test(value)) {
//			return true;
//		}
//		return false;
//	};
///* 判断是否为英文字母和下划线，是则返回true,否则返回false */
//function checkLetterOrUnderline(value) {
//		return new RegExp("[A-Za-z_]").test(value);
//	};
///**
// * 用途：检查输入字符串是否只由汉字、字母、数字组成 输入： value：字符串 返回： 如果通过验证返回true,否则返回false
// */
//function checkIntervalOrNumOrLett(value) {
//		/* 判断是否是汉字、字母、数字组成 */
//		var regu = "^[0-9a-zA-Z\u4e00-\u9fa5]+$";
//		var re = new RegExp(regu);
//		if (re.test(value)) {
//			return true;
//		}
//		return false;
//	};
//	/**
//	 * 用途：检查输入字符串是否只由字母、数字组成 输入： value：字符串 返回： 如果通过验证返回true,否则返回false
//	 */
//	function checkStrLettOrNum(value) {    
//	/* 判断是否是字母、数字组成 */
//		var regu = "^[0-9a-zA-Z_]+$";
//		var re = new RegExp(regu);
//		if (re.test(value)) {
//			return true;
//		}
//		return false;
//	};
//
//// /**
//// *用途：检查输入对象的值是否符合E-Mail格式
//// *输入：str 输入的字符串
//// *返回：如果通过验证返回true,否则返回false
//// */
//// $.checkEmail = function (value) {
//// var myReg = /^([-_A-Za-z0-9\.]+)@([_A-Za-z0-9]+\.)+[A-Za-z0-9]{2,3}$/;
//// if (myReg.test(value)) {
//// return true;
//// }
//// return false;
//// };
//// /**
//// *要求：一、移动电话号码为11或12位，如果为12位,那么第一位为0
//// *二、11位移动电话号码的第一位和第二位为"13"
//// *三、12位移动电话号码的第二位和第三位为"13"
//// *用途：检查输入手机号码是否正确
//// *输入：
//// *s：字符串
//// *返回：
//// *如果通过验证返回true,否则返回false
//// */
//// $.checkMobile = function (value) {
//// var regu = /(^[1][3][0-9]{9}$)|(^0[1][3][0-9]{9}$)/;
//// var re = new RegExp(regu);
//// if (re.test(value)) {
//// return true;
//// }
//// return false;
//// };
//// /**
//// *要求：一、电话号码由数字、"("、")"和"-"构成
//// *二、电话号码为3到8位
//// *三、如果电话号码中包含有区号，那么区号为三位或四位
//// *四、区号用"("、")"或"-"和其他部分隔开
//// *用途：检查输入的电话号码格式是否正确
//// *输入：
//// *strPhone：字符串
//// *返回：
//// *如果通过验证返回true,否则返回false
//// */
//// $.checkPhone = function (value) {
//// var regu =
//// /(^([0][1-9]{2,3}[-])?\d{3,8}(-\d{1,6})?$)|(^\([0][1-9]{2,3}\)\d{3,8}(\(\d{1,6}\))?$)|(^\d{3,8}$)/;
//// var re = new RegExp(regu);
//// if (re.test(value)) {
//// return true;
//// }
//// return false;
//// };
//// /**
//// *功能：判断是否为日期(格式:yyyy年MM月dd日,yyyy-MM-dd,yyyy/MM/dd,yyyyMMdd)
//// *提示信息：未输入或输入的日期格式错误！
//// *使用：f_check_date(obj)
//// *返回：bool
//// */
//// $.checkDate = function (obj) {
//// var date = Trim(obj.value);
//// var dtype = obj.eos_datatype;
//// var format = dtype.substring(dtype.indexOf("(") + 1, dtype.indexOf(")"));
//// /*日期格式 */
//// var year, month, day, datePat, matchArray;
//// if (/^(y{4})(-|\/)(M{1,2})\2(d{1,2})$/.test(format)) {
//// datePat = /^(\d{4})(-|\/)(\d{1,2})\2(\d{1,2})$/;
//// } else {
//// if (/^(y{4})(年)(M{1,2})(月)(d{1,2})(日)$/.test(format)) {
//// datePat = /^(\d{4})年(\d{1,2})月(\d{1,2})日$/;
//// } else {
//// if (format == "yyyyMMdd") {
//// datePat = /^(\d{4})(\d{2})(\d{2})$/;
//// } else {
//// return false;
//// }
//// }
//// }
//// matchArray = date.match(datePat);
//// if (matchArray == null) {
//// return false;
//// }
//// if (/^(y{4})(-|\/)(M{1,2})\2(d{1,2})$/.test(format)) {
//// year = matchArray[1];
//// month = matchArray[3];
//// day = matchArray[4];
//// } else {
//// year = matchArray[1];
//// month = matchArray[2];
//// day = matchArray[3];
//// }
//// if (month < 1 || month > 12) {
//// return false;
//// }
//// if (day < 1 || day > 31) {
//// return false;
//// }
//// if ((month == 4 || month == 6 || month == 9 || month == 11) && day == 31) {
//// return false;
//// }
//// if (month == 2) {
//// var isleap = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
//// if (day > 29) {
//// return false;
//// }
//// if ((day == 29) && (!isleap)) {
//// return false;
//// }
//// }
//// return true;
//// };
//// /**
//// *功能：校验的格式为yyyy年MM月dd日HH时mm分ss秒,yyyy-MM-dd HH:mm:ss,yyyy/MM/dd
//// HH:mm:ss,yyyyMMddHHmmss
//// *提示信息：未输入或输入的时间格式错误
//// *使用：checkTime(obj)
//// *返回：bool
//// */
//// $.checkTime = function (obj) {
//// var time = Trim(obj.value);
//// var dtype = obj.eos_datatype;
//// var format = dtype.substring(dtype.indexOf("(") + 1, dtype.indexOf(")"));
//// /*日期格式 */
//// var datePat, matchArray, year, month, day, hour, minute, second;
//// if (/^(y{4})(-|\/)(M{1,2})\2(d{1,2}) (HH:mm:ss)$/.test(format)) {
//// datePat = /^(\d{4})(-|\/)(\d{1,2})\2(\d{1,2})
//// (\d{1,2}):(\d{1,2}):(\d{1,2})$/;
//// } else {
//// if (/^(y{4})(年)(M{1,2})(月)(d{1,2})(日)(HH时mm分ss秒)$/.test(format)) {
//// datePat = /^(\d{4})年(\d{1,2})月(\d{1,2})日(\d{1,2})时(\d{1,2})分(\d{1,2})秒$/;
//// } else {
//// if (format == "yyyyMMddHHmmss") {
//// datePat = /^(\d{4})(\d{2})(\d{2})(\d{2})(\d{2})(\d{2})$/;
//// } else {
//// return false;
//// }
//// }
//// }
//// matchArray = time.match(datePat);
//// if (matchArray == null) {
//// return false;
//// }
//// if (/^(y{4})(-|\/)(M{1,2})\2(d{1,2}) (HH:mm:ss)$/.test(format)) {
//// year = matchArray[1];
//// month = matchArray[3];
//// day = matchArray[4];
//// hour = matchArray[5];
//// minute = matchArray[6];
//// second = matchArray[7];
//// } else {
//// year = matchArray[1];
//// month = matchArray[2];
//// day = matchArray[3];
//// hour = matchArray[4];
//// minute = matchArray[5];
//// second = matchArray[6];
//// }
//// if (month < 1 || month > 12) {
//// return false;
//// }
//// if (day < 1 || day > 31) {
//// return false;
//// }
//// if ((month == 4 || month == 6 || month == 9 || month == 11) && day == 31) {
//// return false;
//// }
//// if (month == 2) {
//// var isleap = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
//// if (day > 29) {
//// return false;
//// }
//// if ((day == 29) && (!isleap)) {
//// return false;
//// }
//// }
//// if (hour < 0 || hour > 23) {
//// return false;
//// }
//// if (minute < 0 || minute > 59) {
//// return false;
//// }
//// if (second < 0 || second > 59) {
//// return false;
//// }
//// return true;
//// };
//// /*判断当前对象是否可见*/
//// $.isVisible = function (obj) {
//// var visAtt, disAtt;
//// try {
//// disAtt = obj.style.display;
//// visAtt = obj.style.visibility;
//// }
//// catch (e) {
//// }
//// if (disAtt == "none" || visAtt == "hidden") {
//// return false;
//// }
//// return true;
//// };
//// /*判断当前对象及其父对象是否可见*/
//// $.checkPrVis = function (obj) {
//// var pr = obj.parentNode;
//// do {
//// if (pr == undefined || pr == "undefined") {
//// return true;
//// } else {
//// if (!$.isVisible(pr)) {
//// return false;
//// }
//// }
//// } while (pr = pr.parentNode);
//// return true;
//// };
//	/* 检测字符串是否为空 */
//function isNull(str) {
//		if ("undefined" == (str+"") || str == null || str == "null") {
//			return true;
//		}
//		str = Trim(str);
//		if (str.length == 0) {
//			return true;
//		}
//		return false;
//	};
//	
//	/**
//	 * 根据日期格式，将字符串转换成Date对象。 格式：yyyy-年，MM-月，dd-日，HH-时，mm-分，ss-秒。
//	 * （格式必须写全，例如:yy-M-d，是不允许的，否则返回null；格式与实际数据不符也返回null。） 默认格式：yyyy-MM-dd
//	 * HH:mm:ss,yyyy-MM-dd。
//	 */
//function getDateByFormat(str) {
//		var dateReg, format;
//		var y, M, d, H, m, s, yi, Mi, di, Hi, mi, si;
//		if ((arguments[1] + "") == "undefined") {
//			format = "yyyy-MM-dd HH:mm:ss";
//		} else {
//			format = arguments[1];
//		}
//		yi = format.indexOf("yyyy");
//		Mi = format.indexOf("MM");
//		di = format.indexOf("dd");
//		Hi = format.indexOf("HH");
//		mi = format.indexOf("mm");
//		si = format.indexOf("ss");
//		if (yi == -1 || Mi == -1 || di == -1) {
//			return null;
//		} else {
//			y = parseInt(str.substring(yi, yi + 4));
//			M = parseInt(str.substring(Mi, Mi + 2));
//			d = parseInt(str.substring(di, di + 2));
//		}
//		if (isNaN(y) || isNaN(M) || isNaN(d)) {
//			return null;
//		}
//		if (Hi == -1 || mi == -1 || si == -1) {
//			return new Date(y, M - 1, d);
//		} else {
//			H = str.substring(Hi, Hi + 4);
//			m = str.substring(mi, mi + 2);
//			s = str.substring(si, si + 2);
//		}
//		if (isNaN(parseInt(y)) || isNaN(parseInt(M)) || isNaN(parseInt(d))) {
//			return new Date(y, M - 1, d);
//		} else {
//			return new Date(y, M - 1, d, H, m, s);
//		}
//	};
//	/* LTrim(string):去除左边的空格 */
//function LTrim(str) {
//		var whitespace = new String(" \t\n\r");
//		var s = new String(str);
//		if (whitespace.indexOf(s.charAt(0)) != -1) {
//			var j = 0, i = s.length;
//			while (j < i && whitespace.indexOf(s.charAt(j)) != -1) {
//				j++;
//			}
//			s = s.substring(j, i);
//		}
//		return s;
//	};
//	/* RTrim(string):去除右边的空格 */
//function RTrim(str) {
//		var whitespace = new String(" \t\n\r");
//		var s = new String(str);
//		if (whitespace.indexOf(s.charAt(s.length - 1)) != -1) {
//			var i = s.length - 1;
//			while (i >= 0 && whitespace.indexOf(s.charAt(i)) != -1) {
//				i--;
//			}
//			s = s.substring(0, i + 1);
//		}
//		return s;
//	};
//	/* Trim(string):去除字符串两边的空格 */
//function Trim(str) {
//		return RTrim(LTrim(str));
//	};
//	/* 获取明天0点时间 */
//function getTomorrowDate(){
//		var date = new Date();
//		date.setDate(date.getDate() + 1);
//		return date.getFullYear() + "-" + (date.getMonth()+1) + "-" + date.getDate() + " 00:00:00";
//	};
//	/* 获取当前日期（年-月-日） */
//	function getCurrentDate(){
//		var date = new Date();
//		return date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate();
//	};
//	/* 获取当前时间（年-月-日 时:分:秒） */
//function getCurrentDateTime(){
//		var date = new Date();
//		return date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
//	};
//	/** 比较时间 */
//function compareDateTime(begin,end){
//		var beginDate = Date.parse(begin.replace(/-/g,"/"));
//		var endDate = Date.parse(end.replace(/-/g,"/"));
//		return (endDate-beginDate);
//	};
//	// 禁用右键、文本选择功能、禁用复制按键
//	 function disabledTextSelected() {
//		$(document).bind("contextmenu", function () {
//			return false;
//		});
//		$(document).bind("selectstart", function () {
//			return false;
//		});
//	};
//	// 禁用后退按钮
//	function disabledBack(selector){
//		$(selector).keydown(function (event) {
//			if (((event.altKey) && ((event.keyCode == 37) || (event.keyCode == 39))) || (event.keyCode == 8)) {
//				return false;
//			}
//		});
//	};
//	
//
//	// Base64编码
//function encode(input) {
//		var output = "";
//		if(isNull(input)){
//			return output;
//		}
//		var chr1, chr2, chr3, enc1, enc2, enc3, enc4;
//		var i = 0;
//		input = _utf8_encode(input);
//		while (i < input.length) {
//			chr1 = input.charCodeAt(i++);
//			chr2 = input.charCodeAt(i++);
//			chr3 = input.charCodeAt(i++);
//			enc1 = chr1 >> 2;
//			enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
//			enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
//			enc4 = chr3 & 63;
//			if (isNaN(chr2)) {
//				enc3 = enc4 = 64;
//			} else if (isNaN(chr3)) {
//				enc4 = 64;
//			}
//			output = output +
//			_keyStr.charAt(enc1) + _keyStr.charAt(enc2) +
//			_keyStr.charAt(enc3) + _keyStr.charAt(enc4);
//		}
//		output = $.replaceAll(output, "\\/", "-");
//		output = $.replaceAll(output, "\\=","_");
//		return output;
//	}
//	// private method for UTF-8 encoding
//function _utf8_encode(string) {
//		string = string.replace(/\r\n/g,"\n");
//		var utftext = "";
//		for (var n = 0; n < string.length; n++) {
//			var c = string.charCodeAt(n);
//			if (c < 128) {
//				utftext += String.fromCharCode(c);
//			} else if((c > 127) && (c < 2048)) {
//				utftext += String.fromCharCode((c >> 6) | 192);
//				utftext += String.fromCharCode((c & 63) | 128);
//			} else {
//				utftext += String.fromCharCode((c >> 12) | 224);
//				utftext += String.fromCharCode(((c >> 6) & 63) | 128);
//				utftext += String.fromCharCode((c & 63) | 128);
//			}
//		}
//		return utftext;
//	}
//	// Base64解码
//	function decode(input) {
//		var output = "";
//		if(isNull(input)){
//			return output;
//		}
//		input = $.replaceAll(input, "\\-", "/");
//		input = $.replaceAll(input, "\\_", "=");
//		var chr1, chr2, chr3;
//		var enc1, enc2, enc3, enc4;
//		var i = 0;
//		input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");
//		while (i < input.length) {
//			enc1 = _keyStr.indexOf(input.charAt(i++));
//			enc2 = _keyStr.indexOf(input.charAt(i++));
//			enc3 = _keyStr.indexOf(input.charAt(i++));
//			enc4 = _keyStr.indexOf(input.charAt(i++));
//			chr1 = (enc1 << 2) | (enc2 >> 4);
//			chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
//			chr3 = ((enc3 & 3) << 6) | enc4;
//			output = output + String.fromCharCode(chr1);
//			if (enc3 != 64) {
//				output = output + String.fromCharCode(chr2);
//			}
//			if (enc4 != 64) {
//				output = output + String.fromCharCode(chr3);
//			}
//		}
//		output = _utf8_decode(output);
//		return output;
//	}
//	// private method for UTF-8 decoding
//function _utf8_decode(utftext) {
//		var string = "";
//		var i = 0;
//		var c = c1 = c2 = 0;
//		while ( i < utftext.length ) {
//			c = utftext.charCodeAt(i);
//			if (c < 128) {
//				string += String.fromCharCode(c);
//				i++;
//			} else if((c > 191) && (c < 224)) {
//				c2 = utftext.charCodeAt(i+1);
//				string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));
//				i += 2;
//			} else {
//				c2 = utftext.charCodeAt(i+1);
//				c3 = utftext.charCodeAt(i+2);
//				string += String.fromCharCode(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));
//				i += 3;
//			}
//		}
//		return string;
//	}
//	/**
//	 * post方式请求服务器
//	 * 
//	 * @param requestUrl
//	 *            请求url
//	 * @param params
//	 *            请求参数对象
//	 */
//function ajaxRequest(requestUrl, paramsObj, callback){
//		if (paramsObj == null) {
//			paramsObj = {};
//		}
//// console.log(toJSON(paramsObj));
//// console.log(path + requestUrl +"?params="+encode(toJSON(paramsObj)));
//		$.ajax({
//					type : "POST",
//					url : path + requestUrl,
//					data:{params:encode(toJSON(paramsObj))},
//					beforeSend : function() {
//// $.showLoader(null);
//					},
//					complete : function() {
//// $.hideLoader();
//					},
//					success : function(resposeText) {
//						var data = parseJSON(decode(resposeText));
//						if("undefined" == callback+""){
//							
//						} else {
//// switch(data.code){
//// case 0:
////									
//// break;
//// case -1:
//// $.MsgBox.Alert("提示", "请求参数列表为空！");
//// break;
//// case -2:
//// $.MsgBox.Alert("提示", "请求参数解析失败！");
//// break;
//// case -3:
//// case -4:
//// $.MsgBox.Alert("提示", "用户名或密码错误！");
//// break;
//// case -5:
//// $.MsgBox.Alert("提示", "请求失败，服务处理异常！");
//// break;
//// case -6:
//// $.MsgBox.Alert("提示", "登录超时！");
//// break;
//// case -7:
//// $.MsgBox.Alert("提示", "参数非空校验失败！");
//// break;
//// case -8:
//// $.MsgBox.Alert("提示", "用户已存在！");
//// break;
//// case -9:
//// $.MsgBox.Alert("提示", data.message);
//// break;
//// case -10:
//// $.MsgBox.Alert("提示", "投票已结束！");
//// break;
//// case -11:
//// $.MsgBox.Alert("提示", "会议已结束！");
//// break;
//// case -12:
//// $.MsgBox.Alert("提示", "议题已结束！");
//// break;
//// case -13:
//// $.MsgBox.Alert("提示", "此议程无需投票！");
//// break;
//// case -14:
//// $.MsgBox.Alert("提示", "此议程投票信息有多项！");
//// break;
//// case -15:
//// $.MsgBox.Alert("提示", "投票未结束，禁止结束议题！");
//// break;
//// case -16:
//// $.MsgBox.Alert("提示", "议题未结束，禁止结束会议！");
//// break;
//// }
//							callback(data);
//						}
//						// 弹出提示信息
//						// $("#GettingProgress").attr('data-rel', 'dialog');
//						// $("#GettingProgress").trigger('create');
//						// $("#popdiv").popup("open");
//						// setTimeout(function() {
//						// $("#popdiv").popup("close");
//						// }, 2000);
//					},
//					error : function() {
//// $.MsgBox.Alert("提示", "请求发送失败");
//					}
//				});
//	/* JQuery 公用方法 */
//(function ($) {
//	/* 替换文本 str:输入的字符串(test) 将 s1(t) 替换为 s2(s), 结果(sess) */
//	$.replaceAll = function replace(str,s1,s2) {
//		return str.replace(new RegExp(s1,"g"),s2); 
//	}
//	
// 
//    
//})(jQuery);
//
//
//// function compareDateTime(begin,end){
//// var beginDate = Date.parse(begin.replace(/-/g,"/"));
//// var endDate = Date.parse(end.replace(/-/g,"/"));
//// return (endDate-beginDate);
//// };
//
