<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>장바구니</title>
<link rel="stylesheet" href="css/style_basket.css">
</head>
<body onLoad="init()">	
	<span id="submit2" onClick="movePage(false)">로그아웃</span>
	<!-- 주문서 -->
	<div class="ordertitle">
		<span class="ordertilte__title">장바구니</span> <span
			class="ordertilte__info">장바구니 > <span
			class="ordertitle-emphasis">주문서 > 주문완료</span>
		</span>
	</div>
	${orderInfo }
</body>
<script>
	var checkCount = 0;

	function init() {
		var message = "${message}";
		if (message != "") {
			alert(message);
		}
	}

	function checkState(obj) {

		checkCount += (obj.checked ? 1 : -1);

	}

	function order(gInfo) {
		var message;
		message = confirm("상품을 주문하시겠습니까?");
		if (message) {
			var goods = gInfo.split(":");
			var check = document.getElementsByName("check");
				var gInfo = "";
				var count = 0;
				for (i = 0; i < check.length; i++) {
					if (check[i].checked) {
						count++;
						gInfo += (goods[i] + (checkCount == count ? "" : ":"));
					}
				}
			if(count!=0){
				var m=alert("총" + count + "개의 상품을 주문합니다.");
				var form = document.createElement("form");
				form.action = "BasketOrderForm?gInfo="+gInfo;
				form.method = "post";
				document.body.appendChild(form);
				form.submit();
			} else {
				alert("선택한 상품이 없습니다.");
			}
		} else {
			var info = gInfo.split(",");
			var form = document.createElement("form");

			form.action = "BasketCheck?gInfo=" + info[0];
			form.method = "post";
			document.body.appendChild(form);
			form.submit();
		}
	}

	function remove(gInfo) {
	      var message;
	      message = confirm("상품을 삭제하시겠습니까?");
	      if (message) {
	         alert(message);
	         var goods = gInfo.split(":");
	         var check = document.getElementsByName("check");
	            var gInfo = "";
	            var count = 0;
	            for (i = 0; i < check.length; i++) {
	               if (check[i].checked) {
	                  count++;
	                  gInfo += (goods[i] + (checkCount == count ? "" : ":"));
	               }
	            }
	         if(count!=0){
	            alert("총" + count + "개의 상품을 삭제 완료하였습니다.");
	            var form = document.createElement("form");
	            form.action = "BasketDelete?gInfo=" + gInfo;
	            form.method = "post";
	            document.body.appendChild(form);
	            form.submit();
	         } else {
	            alert("선택한 상품이 없습니다.");
	         }
	      } else {
	         alert(message);
	         var info = gInfo.split(",");
	         alert(info);
	         var form = document.createElement("form");

	         form.action = "BasketCheck?gInfo=" + info[0];
	         form.method = "post";
	         document.body.appendChild(form);
	         form.submit();
	      }
	   }
	function delgoods(gInfo) {
		var check;
		var gInfo = gInfo;
		check = confirm("삭제하시겠습니까?");
		if (check) {
			var form = document.createElement("form");
			form.action = "BasketDelete?gInfo=" + gInfo;
			form.method = "post";
			document.body.appendChild(form);
			form.submit();
		} else {
			var info = gInfo.split(",");
			alert(info);
			var form = document.createElement("form");
			form.action = "BasketCheck?gInfo=" + info[0];
			form.method = "post";
			document.body.appendChild(form);
			form.submit();
		}
	}

	// 버튼 증가 감소 함수
	function alter(sel, index) {
		var index = "" + index;
		var num = parseInt(document.getElementById(index).value);
		if (sel) {
			num = num + 1;
		} else {
			if (num > 1) {
				num = num - 1;
			} else {
				alert('최소수량은 1 입니다.');
			}
		}
		document.getElementById(index).value = num;
	}

	function AllCheck() {

		var check = document.getElementsByName("check");
		var Allcheck = document.getElementById("Allcheck");

		for (i = 0; i < check.length; i++) {
			if (Allcheck.checked == true) {
				check[i].checked = true;
			} else {
				check[i].checked = false;
			}
		}
	}

	function movePage(selection) {
		var form = document.createElement("form");
		form.action = (selection) ? "List" : "LogOut";
		form.method = "post";

		document.body.appendChild(form);
		form.submit();
	}

	function change(gInfo, i) {
		var i = "" + i;
		var form = document.createElement("form");
		//var qty = document.getElementById(index).value;
		//alert(qty);
		form.method = "post";
		form.action = "Basket";
		//gInfo --> id:gocode:secode
		var info = gInfo.split(",");
		for (index = 0; index < info.length + 1; index++) {
			var input = document.createElement("input");
			input.name = "gInfo";
			input.value = (index == info.length) ? document.getElementById(i)
					: info[index];
			//input.value=document.getElementsByName("opt")[0].value;
			form.appendChild(input);
		}
		form.appendChild(document.getElementById(i));
		document.body.appendChild(form);
		form.submit();
		//alert(form.gInfo[0].value);
	}
</script>

</html>