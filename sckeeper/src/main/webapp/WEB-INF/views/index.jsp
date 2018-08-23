<%@ page language="java" pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
<head>
<title>密码守护者</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script
		src="${pageContext.request.contextPath}/resource/jQuery-1.11.3.min.js"
		type="text/javascript"></script>
	<script>
		var updateConfig = createUpdateConfig();
		
		function createUpdateConfig(){ 
			var o = {}; 
			o.updateId = ""; 
			o.updateFlag = false;
			o.update = function(id){ 
				this.updateId = id; 
				this.updateFlag = true;
			}; 
			o.finished = function(){
				this.updateId = ""; 
				this.updateFlag = false;
			}
			o.updating = function(){
				return this.updateFlag;
			}
			return o; 
		}
		
		function mainPage(){
			window.location.href="${pageContext.request.contextPath}/index";
		}
		
		function loginOut() {
			window.location.href = "${pageContext.request.contextPath}/logOut";
		}

		function unlockSecret() {
			var sk = $("#secretKey").val().trim();
			if (sk !== "") {
				$.ajax({
					type : 'POST',
					url : '${pageContext.request.contextPath}/unlock',
					dataType : 'json',
					data : {
						"secretKey" : sk
					},
					success : function(data) {
							$('#secretKey').val("");
							$('#secretKey').hide();
							$('#unlock_btn').hide();
							$('#lock_btn').show();
							$('#secretSpan').hide();
					},
					error : function(xhr,type,exception) {
						alert((JSON.parse(xhr.responseText)).errorInfo);
					}

				});
			} else {
				alert("请输入解锁密钥！");
			}
		}

		function lockSecret() {
			$.ajax({
				type : 'POST',
				url : '${pageContext.request.contextPath}/lock',
				dataType : 'json',
				success : function(data) {
					$('#secretSpan').show();
					$('#secretKey').show();
					$('#unlock_btn').show();
					$('#lock_btn').hide();
				},
				error : function(xhr,type,exception) {
					alert((JSON.parse(xhr.responseText)).errorInfo);
				}
			});
		}

		function showSecrete(id) {
			var path = '${pageContext.request.contextPath}/img/keeper/show?keeperId='+id+'&'+Math.floor(Math.random()*100);
			$("#" + id + "_img").attr('src',path); 
			$("#" + id + "_img").show();
			$("#" + id + "_show").hide();
		}

		function hideSecrete(id) {
			var path = '${pageContext.request.contextPath}/img/Code';
			$("#" + id + "_img").attr('src',path); 
			$("#" + id + "_img").hide();
			$("#" + id + "_show").show();
		}
		
		function deleteSecret(id){
			if(updateConfig.updating()){
				alert("存在未完成的修改业务！");
				return false;
			}
			if(confirm("确认删除？")){
				$.ajax({
					type : 'POST',
					url : '${pageContext.request.contextPath}/keeper/delete',
					dataType : 'json',
					data : {
						"keeperId" : id
					},
					success : function(data) {
						alert(data.reqMsg);
						mainPage();
					},
					error : function(xhr,type,exception) {
						alert((JSON.parse(xhr.responseText)).errorInfo);
					}
				});
			}
		}
		
		function addSecret(){
			if(updateConfig.updating()){
				alert("存在未完成的修改业务！");
				return false;
			}
			var sk = $("#secretKey").val().trim();
			if(sk !== ""){
				$.ajax({
					type : 'POST',
					url : '${pageContext.request.contextPath}/secret/add',
					dataType : 'json',
					data : {
						"secretKey" : sk
					},
					success : function(data) {
						alert(data.reqMsg);
						mainPage();
					},
					error : function(xhr,type,exception) {
						alert((JSON.parse(xhr.responseText)).errorInfo);
					}
				});
			}else {
				alert("请输入密钥！");
			}
		}
		
		function addKeeper(){
			if(updateConfig.updating()){
				alert("存在未完成的修改业务！");
				return false;
			}
			var kp = $("#keeperKey").val().trim();
			var kpv = $("#keeperValue").val().trim();
			var note = $("#note").val().trim();
			if(kp !=="" && kpv!==""){
				$.ajax({
					type : 'POST',
					url : '${pageContext.request.contextPath}/keeper/add',
					dataType : 'json',
					data : {
						"keeperKey" : kp,
						"keeperValue" : kpv,
						"note" : note
					},
					success : function(data) {
						alert(data.reqMsg);
						mainPage();
					},
					error : function(xhr,type,exception) {
						alert((JSON.parse(xhr.responseText)).errorInfo);
					}
				});
			}else {
				alert("请输入密钥！");
			}
		}
		
		function updateSecret(id){
			if(updateConfig.updating()){
				if(updateConfig.updateId!=id){
					alert("存在未完成的修改业务！");
					return false;
				}
			}
			if(confirm("确认修改？")){
				var keeperKey = $("#key_input_"+id).val().trim();
				var keeperValue = $("#pwd_"+id).val().trim();
				var keeperNote = $("#key_input_"+id).val().trim();
				if(keeperKey !== "" && keeperValue!==""){
					$.ajax({
						type : 'POST',
						url : '${pageContext.request.contextPath}/keeper/update',
						dataType : 'json',
						data : {
							"keeperId" : id,
							"keeperKey" : keeperKey,
							"keeperValue" : keeperValue,
							"note" : keeperNote
						},
						success : function(data) {
							alert(data.reqMsg);
							mainPage();
						},
						error : function(xhr,type,exception) {
							alert((JSON.parse(xhr.responseText)).errorInfo);
						}
					});
				}else {
					alert("请输入名称或密码！");
				}
			}
		}
		
		function showUpdate(id){
			if(updateConfig.updating()){
				alert("存在未完成的修改业务！");
				return false;
			}
			updateConfig.update(id);
			$("#pwd_" + id).show();
			$("#tr_" + id).css('background-color','orange');
			$("#show_sk_" + id).hide();
			$("#" + id+"_show").hide();
			$("#key_" + id).hide();
			$("#note_" + id).hide();
			$("#a_up_" + id).hide();
			$("#a_del_" + id).hide();
			$("#key_input_" + id).show();
			$("#note_input_" + id).show();
			$("#a_over_" + id).show();
			$("#a_undo_" + id).show();
		} 
		
		function unUpdate(id){
			updateConfig.finished();
			$("#tr_" + id).css('background-color','white');
			$("#a_up_" + id).show();
			$("#a_del_" + id).show();
			$("#key_" + id).show();
			$("#note_" + id).show();
			$("#show_sk_" + id).show();
			$("#" + id+"_show").show();
			$("#a_over_" + id).hide();
			$("#a_undo_" + id).hide();
			$("#key_input_" + id).hide();
			$("#note_input_" + id).hide();
			$("#pwd_" + id).hide();
			$("#key_input_" + id).val($("#note_" + id).text());
			$("#note_input_" + id).val($("#key_" + id).text());
			$$("#pwd_" + id).val("");
		}
	</script>
</head>
<body>
	<div id="wrap">
		<div id="top_content">
			<div id="header">
				<div id="rightheader" style="height: 15px; text-align: center;"></div>
				<div id="topheader">
					<h1 id="title" style="text-align: center;">
						<a href="#">Secret Keeper</a>
					</h1>
					<span style="font-size: 7pt; float: right; margin-right: 30px;">
						<span style="color: red;">${user.nickName}</span> 你好,欢迎使用! <a
						id="logout" href="#" style="color: blue;" onclick="loginOut();">注销</a>
					</span>
				</div>
			</div>

			<c:choose>
				<c:when test="${lockFlag=='true'}">
					<span id="secretSpan">密钥</span>
					<input type="password" id="secretKey" name="secretKey" />
					<input id="unlock_btn" type="button" value="解锁"
						onclick="unlockSecret();" />
					<input style="display: none" id="lock_btn" type="button" value="加锁"
						onclick="lockSecret();" />
					<br />
					<br />
				</c:when>
				<c:when test="${lockFlag=='false'}">
					<span id="secretSpan" style="display: none">密钥</span>
					<input style="display: none" type="password" id="secretKey"
						name="secretKey" />
					<input style="display: none" id="unlock_btn" type="button"
						value="解锁" onclick="unlockSecret();" />
					<input id="lock_btn" type="button" value="加锁"
						onclick="lockSecret();" />
					<br />
					<br />
				</c:when>
				<c:otherwise>
					<div>
						密钥<input type="password" id="secretKey" name="secretKey" value="" />
						<input type="button" value="新增" onclick="addSecret();" />
					</div>
				</c:otherwise>
			</c:choose>

			<br /> <br />

			<c:choose>
				<c:when test="${secretFlag=='true'}">
					<div>
						名称<input type="text" id="keeperKey" name="keeperKey" value="" />
						密码<input type="password" id="keeperValue" name="keeperValue"
							value="" /> 说明<input type="text" id="note" name="note" value="" />
						<input type="button" value="新增" onclick="addKeeper();" />
					</div>
					<br />
					<br />
				</c:when>

			</c:choose>



			<div id="content">
				<p id="whereami">
					&nbsp;&nbsp;&nbsp;<span id="bgclock"></span>
				</p>
				<div style="width: 98%;" align="center;">
					<table cellpadding="0" cellspacing="0" border="0"
						class="form_table"
						style="border: solid green 1px; width: 80%; margin-left: 9%;">
						<tr>
							<td
								style="font-size: 20pt; border: solid green 1px; width: 4%; font-weight: bold; text-align: center;">序号</td>
							<td
								style="font-size: 20pt; border: solid green 1px; width: 16%; font-weight: bold; text-align: center;">名称</td>
							<td
								style="font-size: 20pt; border: solid green 1px; width: 28%; font-weight: bold; text-align: center;">密码</td>
							<td
								style="font-size: 20pt; border: solid green 1px; width: 24%; font-weight: bold; text-align: center;">说明</td>
							<td
								style="font-size: 20pt; border: solid green 1px; width: 8%; font-weight: bold; text-align: center;">操作</td>
						</tr>

						<c:forEach items="${keeper}" var="kp" varStatus="ix">
							<tr style="text-align: center;" id="tr_${kp.id}">
								<td style="border: solid green 1px;" id="td1_${kp.id}"><c:out
										value="${ix.index+1}" /></td>
								<td style="border: solid green 1px;" id="td2_${kp.id}"><span
									id="key_${kp.id}"><c:out value="${kp.key}" /></span> <input
									style="display: none; width: 95%; height: 80%;" type="text"
									id="key_input_${kp.id}" value="${kp.key}"></td>
								<td style="border: solid green 1px; height: 46px; width: 321px;"
									id="td3_${kp.id}"><span
									style="display: block; height: 45px; text-align: center; line-height: 45px; float: left; margin-left: 48px;"
									id="${kp.id}_show"> <c:out value="${kp.value}" /></span> <img
									id="${kp.id}_img"
									style="display: none; margin-top: 2px; margin-right: 3px;"
									src="${pageContext.request.contextPath}/img/Code" /> <a
									id="show_sk_${kp.id}"
									style="display: block; height: 45px; text-align: center; line-height: 45px; text-decoration: none; margin-right: 5px; float: right;"
									href="" onclick="showSecrete(${kp.id});return false"
									onmouseout="hideSecrete(${kp.id});">[明文]</a> <input
									style="display: none; width: 60%; height: 50%;" type="password"
									id="pwd_${kp.id}" value=""></td>

								<td style="border: solid green 1px;" id="td4_${kp.id}"><span
									id="note_${kp.id}"><c:out value="${kp.note}" /></span> <input
									style="display: none; width: 95%; height: 80%;" type="text"
									id="note_input_${kp.id}" value="${kp.note}"></td>

								<td style="border: solid green 1px;" id="td5_${kp.id}"><a
									href="" id="a_up_${kp.id}" style="text-decoration: none;"
									onclick="showUpdate(${kp.id});return false">[修改]</a> <a href=""
									id="a_del_${kp.id}" style="text-decoration: none;"
									onclick="deleteSecret(${kp.id});return false">[删除]</a> <a
									href="" id="a_over_${kp.id}"
									style="display: none; text-decoration: none;"
									onclick="updateSecret(${kp.id});return false">[完成]</a> <a
									href="" id="a_undo_${kp.id}"
									style="display: none; text-decoration: none;"
									onclick="unUpdate(${kp.id});return false">[取消]</a></td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>
		</div>

	</div>
</body>
</html>
