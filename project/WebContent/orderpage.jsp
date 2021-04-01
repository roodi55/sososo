<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>1조-주문서</title>
<link rel="stylesheet" href="css/order.css">
</head>


<body onLoad="start()">

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
				<span class="ordertilte__title">주문서</span> <span
					class="ordertilte__info">장바구니 > <span
					class="ordertitle-emphasis">주문서</span> > 주문완료
				</span>
			</div>

			${orderInfo }


		</section>
		<div>

			<h4>
				최종결제 정보 <span style="color: red; font-weight: bold;">:::::
					특별할인이벤트 :::: 10% 할인진행중 !!</span>
			</h4>

			<div class="count">
				<ul>
					<li><span class="ordertilte__title">총 금액</span> <span
						class="orderlast__info">${sumtotal }원</span></li>

					<li><span class="ordertilte__title">할인금액</span> <span
						class="orderlast__info2">${salePrice }원</span></li>

					<li class="od" style="border-top: 1px solid #C8D1DC;"></li>

					<li><span class="ordertilte__title2">최종결제 금액</span> <span
						class="orderlast__info3">${total }원</span></li>

				</ul>

				<div class="order_div">
					
					<div class="list_btn" onClick="move(true)">메인으로</div>
					<div class="order_btn" onClick="order()">주문하기</div>
					<div class="basket_btn" onClick="move(false)">장바구니</div>

				</div>


			</div>



		</div>

	</div>

</body>


<script>

	function move(page){
		
		var id = "${ID}";
		
		var form = document.createElement("form");
		form.target = "_blank"
		form.action = (page) ? "List" : "BasketCheck?gInfo=" + id;
		form.method = "post";
		
		document.body.appendChild(form);
		form.submit();
		
	}

	function moveGoods(gocode,secode){
		
		var form = document.createElement("form");
		form.target="_blank"
		form.method = "post";
		form.action = "GoodsDetail?code=" + gocode + "&code=" + secode;
		document.body.appendChild(form);
		form.submit();
		
	}	

	function start(str) {

		var msg = "${msg}";
		str = String(str);
		var minus = str.substring(0, 1);
		var money = document.getElementById("money");

		str = str.replace(/[^\d]+/g, '');
		str = str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');

		if (msg != "") {

			alert(msg);

		}

	}

	function zoomIn(event) {
		event.target.style.transform = "scale(4)";
		event.target.style.zIndex = 1;
		event.target.style.transition = "all 0.5s";
	}

	function zoomOut(event) {
		event.target.style.transform = "scale(1)";
		event.target.style.zIndex = 0;
		event.target.style.transition = "all 0.5s";
	}

	function order() {

		var odList = "${orderList}";
		
		var form = document.createElement("form");
		form.method = "post";
		form.action = "Order";
		
		var input = document.createElement("input");
		input.name="orderInfo";
		input.value=odList;
		form.appendChild(input);
		
		document.body.appendChild(form);

		form.submit();

	}
</script>

</html>






