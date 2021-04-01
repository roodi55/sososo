<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script src="https://kit.fontawesome.com/301043e4a8.js"
	crossorigin="anonymous"></script>
<title>Goods Page</title>
<link rel="icon" type="image/png" href="image/icia-logo.png">
<link rel="stylesheet" href="css/style_goods.css">
</head>
<body onLoad="init()">
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
				<input class="send" type="button" value="My Bag |"
					onClick="basket()" /> <input class="send" type="button"
					value="LogOut |" onClick="sendLogOut()" /> <input class="send"
					type="button" value="My Page" onClick="privacy()" />
			</div>
		</div>
	</header>
	<!-- Tab Links -->
	<div class="tab1">
		<button class="tab1links" value="Menu" onclick="menu()">MENU</button>
		<button class="tab1links" value="Snack" onclick="openGoods(this ,1)">SNACK</button>
		<button class="tab1links" value="Noodle" onclick="openGoods(this,2)">NODDLE</button>
		<button class="tab1links" value="Drink" onclick="openGoods(this,3)">LIQUIR</button>
		<button class="tab1links" value="Frozen" onclick="openGoods(this,4)">FROZEN</button>
		<button class="tab1links" value="Fresh" onclick="openGoods(this,5)">FRESH</button>
	</div>
	<div class="blank"></div>
	<!-- Section -->
	<section id="section">${gList }</section>

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
	function init() {

		var message = "${message}";
		var msg = "${msg}";

		if (message != "") {
			alert(message);
		}
		if (msg != "") {
			alert(msg);
		}

	}

	function searchGoods() {

		var form = document.createElement("form");
		form.action = "Search";
		form.method = "post";

		form.appendChild(document.getElementsByName("word")[0]);

		document.body.appendChild(form);

		form.submit();

	}

	function goDetail(code) {

		var splitCode = code.split(":");

		//		var view = 'http://192.168.1.177/GoodsDetail?code='+ splitCode[0] + '&code?=' + splitCode[1] ;

		//		window.open(view);

		var form = document.createElement("form");
		form.action = "GoodsDetail";
		form.method = "post";
		form.target = "_blank";

		for (index = 0; index < splitCode.length; index++) {

			var input = document.createElement("input");
			input.name = "code";
			input.type = "hidden";
			input.value = splitCode[index];
			form.appendChild(input);

		}

		document.body.appendChild(form);

		form.submit();
	}
	function menu() {
		var form = document.createElement("form");
		form.method = "post";
		form.action = "Menu";

		document.body.appendChild(form);

		form.submit();
	}

	function openGoods(obj, num) {
		var form = document.createElement("form");
		form.method = "post";
		form.action = (obj.value == "Snack") ? "Snack"
				: (obj.value == "Noodle") ? "Noodle"
						: (obj.value == "Drink") ? "Drink"
								: (obj.value == "Frozen") ? "Frozen"
										: (obj.value == "Fresh") ? "Fresh"
												: " ";

		form.action += "?number=" + num;

		document.body.appendChild(form);

		form.submit();
	}

	//장바구니로 이동
	function basket() {

		var form = document.createElement("form");
		var gInfo = "${accessInfo}";
		form.action = "BasketCheck?gInfo=" + gInfo;
		form.method = "post";
		form.target = "_blank";
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





