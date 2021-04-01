<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
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


			<div id="table">

				<h4>주문상품 정보</h4>

				<table class="table">
					<tr>
						<th>이미지</th>
						<th>No.</th>
						<th>상품정보</th>
						<th>판매자</th>
						<th>가격</th>
						<th>수량</th>
						<th>총 가격</th>
					</tr>
					<tr>
						<td><img src="image/5005_1000712345_L.jpg"
							onmouseenter="zoomIn(event)" onmouseleave="zoomOut(event)"></td>
						<td>1</td>
						<td>새우깡</td>
						<td>좋은식품</td>
						<td>2000원</td>
						<td>5</td>
						<td>10000원</td>
					</tr>
					<tr>
						<td><img src="image/5005_1000712345_L.jpg"
							onmouseenter="zoomIn(event)" onmouseleave="zoomOut(event)"></td>
						<td>2</td>
						<td>양파</td>
						<td>좋은식품</td>
						<td>1500원</td>
						<td>3</td>
						<td>4500원</td>
					</tr>
					<tr>
						<td><img src="image/5005_1000712345_L.jpg"
							onmouseenter="zoomIn(event)" onmouseleave="zoomOut(event)"></td>
						<td>2</td>
						<td>양파</td>
						<td>좋은식품</td>
						<td>1500원</td>
						<td>3</td>
						<td>4500원</td>
					</tr>
					<tr>
						<td><img src="image/5005_1000712345_L.jpg"
							onmouseenter="zoomIn(event)" onmouseleave="zoomOut(event)"></td>
						<td>2</td>
						<td>양파</td>
						<td>좋은식품</td>
						<td>1500원</td>
						<td>3</td>
						<td>4500원</td>
					</tr>
					<tr>
						<td><img src="image/5005_1000712345_L.jpg"
							onmouseenter="zoomIn(event)" onmouseleave="zoomOut(event)"></td>
						<td>2</td>
						<td>양파</td>
						<td>좋은식품</td>
						<td>1500원</td>
						<td>3</td>
						<td>4500원</td>
					</tr>
				</table>
			</div>
		</section>
		<div>

			<h4>최종결제 정보</h4>

			<div class="count">
				<ul>
					<li><span class="ordertilte__title">총 금액</span> <span
						class="orderlast__info">50000원</span></li>

					<li><span class="ordertilte__title">할인금액</span> <span
						class="orderlast__info2">0원</span></li>

					<li class="od" style="border-top: 1px solid #C8D1DC;"></li>

					<li><span class="ordertilte__title2">최종결제 금액</span> <span
						class="orderlast__info3">50000원</span></li>

				</ul>

				<div class="order_div">

					<a class="order_btn" href='javascript:void(0);' onclick="check();">
						<span class="order_span">결제하기</span>

					</a>

				</div>


			</div>



		</div>

	</div>

</body>


<script>
	function start() {

		var msg = "${msg}";

		if (msg != "") {

			alert(msg);

		}

	}

	function zoomIn(event) {
		event.target.style.transform = "scale(2)";
		event.target.style.zIndex = 1;
		event.target.style.transition = "all 0.5s";
	}

	function zoomOut(event) {
		event.target.style.transform = "scale(1)";
		event.target.style.zIndex = 0;
		event.target.style.transition = "all 0.5s";
	}
	
	function check(){
		
		alert("ok");
		
	}
</script>

</html>