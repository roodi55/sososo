<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>1조-결제내역확인</title>
<link rel="stylesheet" href="css/order.css">
</head>


<body onLoad="init()">

	<div class="fa">

		<header id="header">
			<div class="search">
				<span class="search__logo"> <a href="https://www.icia.co.kr/"><img
						id="logo2" src="image/icia-logo2.png"></a>
				</span>
			</div>
		</header>

		<section>

			<!-- 주문서 -->
			<div class="ordertitle">
				<span class="ordertilte__title">결제내역 확인</span> <span
					class="ordertilte__info">장바구니 > <span>주문서 ></span>
					<span class="ordertitle-emphasis">주문완료</span>
				</span>
			</div>
			
			<div class="ordertitle-emphasis2">이용해주셔서 감사합니다!</div>
			
			<div>
			
			${result }		
			
			</div>
			
			<div class="list_btn2" onClick="move()">메인으로</div>
			
		</section>

	</div>

</body>

<script>

	function init(){
		
		var msg = "${msg}";
		
		if(msg != ""){
			
			alert(msg);
			
		}
		
	}

	function move(){
	
		var form = document.createElement("form");
		form.target = "_blank";
		form.action = "List";
		form.method = "post";
	
		document.body.appendChild(form);
		form.submit();
	
	}

</script>

</html>