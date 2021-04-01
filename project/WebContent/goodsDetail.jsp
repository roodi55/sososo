<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script src="https://kit.fontawesome.com/301043e4a8.js"
	crossorigin="anonymous"></script>
<title>GoodsDetail Page</title>
<link rel="icon" type="image/png" href="image/icia-logo.png">
<link rel="stylesheet" href="css/style_goods.css">
</head>
<body onLoad="init('${gInfo}')">
	<!-- Header -->
	<header id="header">
		<div class="search">
			<span class="search__logo"> <a href="https://www.icia.co.kr/"><img
					id="logo2" src="image/icia-logo2.png"></a>
			</span> <span class="search__input"> <input type="text" name="word"
				id="word">
				<button type="button" onclick="searchGoods()" id="button">
					<i class="fas fa-search"></i>
				</button>
			</span>
			<div class="sendBox">
			<!-- 	<input class="send" type="button" value="My Bag |"onClick="basket()" />  -->
				<input class="send" type="button"value="LogOut |" onClick="sendLogOut()" /> 
				<input class="send"type="button" value="My Page" onClick="privacy()" />
			</div>
		</div>
	</header>

	<!-- Section -->
	<section id="goodsdeatil">
		<!-- 상품정보 -->
		<div id="title">
			<!-- 상품이미지 -->
			<div id="goodsImg" class="goodsinfo">
				<img id="goodsImg__img" src="${goodsImage}">
			</div>
			<!--상품개요  -->
			<div id="goodsSummary" class="goodsinfo">
				<!-- 상품명 -->
				<div id="goods-name">${item }</div>
				<!-- 상품가격 -->
				<div id="goods-price">${price }원</div>
				<!-- 상품옵션선택 -->
				<div id="option">
					<div class="option__label">
						<label for="opt">옵션선택</label>
					</div>
					<div class="option__select">
						<!--  <input type="button" name="opt" id="opt" value="1"> -->
						<input type="text" name="opt" id="opt" value="1" readOnly
							class="opt"> <input type="button" value="+" class="opt"
							onClick="res(this)";> <input type="button" value="-"
							class="opt" onClick="res(this)";>
					</div>
				</div>
				<!-- 장바구니 | 구매하기 -->
				<div id="order">
					<!-- 서버 -->
					<span class="order__Btn"><input class="order__input c1"
						type="button" value="장바구니" onClick="order(true,'${gInfo}')"></span>
					<span class="order__Btn"><input class="order__input c2"
						type="button" value="구매하기" onClick="order(false,'${gInfo}')"></span>
				</div>
			</div>
		</div>
		<!-- 상품상세정보 :: image -->
		<div id="detail">
			<div id="detail__img">
				<img src="${detailImage }" />
			</div>
			<div id="detail__seller">${seller }</div>
		</div>
	</section>

	<!-- Footer -->
	<footer id="footer">
		<span class="footer__icon"><a href="https://www.icia.co.kr/"><img
				id="footer__icon" src="image/icia-logo.png" alt=""></a></span> <span
			class="footer__rights">Copyright <b>Sookyeong Lee.</b> All
			Rights Reserved.
		</span>
	</footer>
</body>

<script>
	function init(gInfo) {

		var mType = "${mType}";
		var msg = "${msg}";
		var check;
		if (mType) {
			check = confirm(msg);
			if (check) {
				var info = gInfo.split(":");

				var form = document.createElement("form");
				form.action = "BasketCheck?gInfo=" + info[0];
				form.method = "post";
				document.body.appendChild(form);
				form.submit();
			}

		} else if (msg != "") {
			alert(msg);
		}
	}

	function res(obj) {

		var opt = document.getElementById("opt");

		//		(obj.value=="+") ? opt.value = parseInt(opt.value) + parseInt("1") : (opt.value>1) ? opt.value = parseInt(opt.value) - parseInt("1") : opt.value="1" ;

		if (obj.value == "+") {

			opt.value = parseInt(opt.value) + parseInt("1");

		} else {

			(opt.value > 1) ? opt.value = parseInt(opt.value) - parseInt("1")
					: opt.value = "1";

		}
	}

	function order(type, gInfo) {

		var form = document.createElement("form");
		form.method = "post";
		form.action = (type) ? "Basket" : "GoodsOrderForm";

		//gInfo가  : 로 구분되어서 값이 들어오므로 다 나누어줘야한다

		var info = gInfo.split(":");
		for (index = 0; index < info.length + 1; index++) {
			var input = document.createElement("input");
			input.name = "gInfo";
			//input.value = (index == info.length) ? document.getElementsByName("opt")[0].value ? info[index] ;
			input.value = info[index];
			form.appendChild(input);
		}

		form.appendChild(document.getElementsByName("opt")[0]);

		document.body.appendChild(form);
		form.submit();
	}

	function searchGoods() {

		var form = document.createElement("form");
		form.action = "Search";
		form.method = "post";

		form.appendChild(document.getElementsByName("word")[0]);

		document.body.appendChild(form);

		form.submit();

	}
	//회원정보 수정
	function privacy() {
		var form = document.createElement("form");
		form.method = "post";
		form.action = "MyPageForm";

		document.body.appendChild(form);
		form.submit();
	}
	//로그아웃

	function sendLogOut() {

		var msg = "로그아웃 하시겠습니까?";
		var chk;
		if (true) {
			chk = confirm(msg);
			if (chk) {
				var form = document.createElement("form");
				form.action = "LogOut";
				form.method = "post";

				// LogOut을 할 멤버 정보 가져오기

				document.body.appendChild(form);

				form.submit();
			}
		}

	}
</script>


</html>

