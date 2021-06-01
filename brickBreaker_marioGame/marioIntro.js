// JavaScript Document
var blinkCount=0;
var blinking;

// 스테이지 클리어 여부
var clearstage1 = false; //false;
var clearstage2 = false; //false;
var clearstage3 = false; //false;
var clearstage4 = false; //false;

var scenes = ["s2","s3","s4","s5","s6","s7"];
var player = 'mario';

var context;

var mySound = new Sound();

$(document).ready(function() {
	$("#coin").html(coin);
	$("#score").html(score);

	context = document.getElementById("canvas").getContext("2d");
	intro();

	// 메뉴 버튼 hover 이벤트
	$(".lobby.menubutton").mouseover(function() {
		var imgSrc = $(this).attr("src");
		imgSrc = imgSrc.replace(".", "_click.");
		$(this).attr("src", imgSrc);
	});
	
	$(".lobby.menubutton").mouseout(function(){
		var imgSrc = $(this).attr("src");
		imgSrc = imgSrc.replace("_click.", ".");
		$(this).attr("src", imgSrc);
	});

	// 사이드바 배경 음악 소리 설정
	mySound.Play('intro', 'bg');
	$("#togglesound").click(function() {
		mySound.muteClick();
		if(($(this).css("background-image")).includes("sound_off") ){
			$(this).css({"background":"url(images/sound_on.png)"});
		}
		else{
			$(this).css({"background":"url(images/sound_off.png)"});
		}
	});
	

});


function startGame(){
	document.getElementById('introSound').muted = true;
	for(scene in scenes){
		window.clearTimeout(scenes[scene]);
	}
	blinking ? clearInterval(blinkLetters): blinking;
	initial();
}

var countnext = 0;
var instructionArr = ["images/instruction1.jpg","images/instruction2.jpg","images/instruction3.jpg"];



function Sound(){

	this.stage1 = new Audio("audio/stage1.mp3");
	this.stage2 = new Audio("audio/stage2.mp3");
	this.stage3 = new Audio("audio/stage3.mp3");
	this.stage4 = new Audio("audio/stage4.mp3");
	this.stage5 = new Audio("audio/stage5.mp3");
	this.intro = new Audio("audio/intro.mp3");
	this.upsoundeffect = new Audio("audio/1upsoundeffect.mp3");
	this.bad_token = new Audio("audio/bad_token.wav");
	this.bonus = new Audio("audio/bonus.wav");
	this.bossMusic = new Audio("audio/bossMusic.mp3");
	this.brick = new Audio("audio/brick.wav");
	this.gameover = new Audio("audio/gameover.mp3");
	this.mushroomsoundeffect = new Audio("audio/mushroomsoundeffect.mp3");
	this.paddle = new Audio("audio/paddle.wav");
	this.keyboard = new Audio("audio/keyboard.mp3");
	this.stageClear = new Audio("audio/stageClear.mp3");
	this.star = new Audio("audio/star.mp3");
	this.start = new Audio("audio/start.mp3");
	this.wall = new Audio("audio/wall.wav");

	map = new Map();
	map.set('stage1', this.stage1);
	map.set('stage2', this.stage2);
	map.set('stage3', this.stage3);
	map.set('stage4', this.stage4);
	map.set('stage5', this.stage5);
	map.set('intro', this.intro);
	map.set('upsoundeffect', this.upsoundeffect);
	map.set('bad_token', this.bad_token);
	map.set('bonus', this.bonus);
	map.set('bossMusic', this.bossMusic);
	map.set('brick', this.brick);
	map.set('gameover', this.gameover);
	map.set('mushroomsoundeffect', this.mushroomsoundeffect);
	map.set('paddle', this.paddle);
	map.set('keyboard', this.keyboard);
	map.set('stageClear', this.stageClear);
	map.set('star', this.star);
	map.set('start', this.start);
	map.set('wall', this.wall);


	this.playBGList = [];
	this.playEFList = [];
	this.mute = true;
	this.Mute = function(){
		for(var i = 0; i < this.playEFList.length; i++){
			this.playEFList[i].pause();
			this.playEFList[i].currentTime = 0;
		}
		this.playEFList = [];
		for(var i = 0; i < this.playBGList.length; i++){
			this.playBGList[i].pause();
		}
	};
	this.muteBG = function(){
		if(!this.mute){
			for(var i = 0; i < this.playBGList.length; i++){
				this.playBGList[i].pause();
			}
		}
	};
	this.setTimeMuteBG = function(time){
		for(var i = 0; i < this.playBGList.length; i++){
			this.playBGList[i].pause();
		}
		var len = this.playBGList.length;
		setTimeout(function()
			{
				for(var i = 0; i < len; i++){
					this.playBGList[i].play();
				}
			}, time);
	};
	this.stopEffect = function(){
		for(var i = 0; i < this.playEFList.length; i++){
			this.playEFList[i].pause();
			this.playEFList[i].currentTime = 0;
		}
		this.playEFList = [];
	};
	this.Stop = function(){
		for(var i = 0; i < this.playEFList.length; i++){
			this.playEFList[i].pause();
			this.playEFList[i].currentTime = 0;
		}
		this.playEFList = [];
		for(var i = 0; i < this.playBGList.length; i++){
			this.playBGList[i].pause();
			this.playBGList[i].currentTime = 0;
		}
		this.playBGList = [];

	};
	this.Play = function(name, type){
		var p = map.get(name);
		p.volume = 0.08;
		if(type == 'bg'){
			p.loop = true;
			this.playBGList.push(p);
		}
		if(!this.mute){
			if(type == 'ef'){
				this.playEFList.push(p);
			}
			p.play();
		}
	};
	this.playBG = function(){
		if(!this.mute){
			for(var i = 0; i < this.playBGList.length; i++){
				this.playBGList[i].play();
			}
		}
	};
	this.setTimePlay = function(name, type, time){
		var p = map.get(name);
		p.volume = 0.08;
		if(type == 'bg'){
			p.loop = true;
			this.playBGList.push(p);
		}
		if(!this.mute){
			if(type == 'ef'){
				this.playEFList.push(p);
			}
			p.play();
			setTimeout(function()
			{
				p.pause();
				p.currentTime = 0;
			}, time);
		}
	};
	this.muteClick = function(){
		this.mute = !this.mute;
		if(this.mute){
			this.Mute();
		}
		else{
			for(var i = 0; i < this.playBGList.length; i++){
				this.playBGList[i].play();
			}
		}
	};

}

function intro()
{
	

	// ============= 스테이지 구현 ===============================
	// 스타트 클릭
	$("#startIMG").click(function(){
		$(".menubutton").hide();
		$(".stage").show();
	});

	// 스테이지 버튼 hover 이벤트
	$(".stage").mouseover(function() {
		var imgSrc = $(this).attr("src");
		imgSrc = imgSrc.replace(".", "_click.");
		$(this).attr("src", imgSrc);
	});

	$(".stage").mouseout(function(){
		var imgSrc = $(this).attr("src");
		imgSrc = imgSrc.replace("_click.", ".");
		$(this).attr("src", imgSrc);
	});

	// 스테이지 back 클릭
	$("#stageback").click(function() {
		$(".stage").hide();
		$(".menubutton").show();
	});

	var saveBG;
	var tyInt;
	// stage 1
	$("#stage1IMG").click(function(){
		mySound.Stop();
		mySound.Play('keyboard', 'ef');
		$(".intro").hide();
		// stage1 스토리 출력
		if(player == 'mario'){
			$("#stage1_marioIMG").attr("src", "images/stageintro/mario.png");
			$("#stage1_textmario").text("마리오");
		}
		else{
			$("#stage1_marioIMG").attr("src", "images/stageintro/luigi.png");
			$("#stage1_textmario").text("루이지");
		}
		$("#enterstage1").show();
		saveBG = $("#mainback").attr("src");
		$("#mainback").attr("src", "images/lobby/blackbackground.png");
		var typingBool = false; 
		var typingIdx=0; 
		var liIndex = 0;
		var liLength = $(".typing-txt>ul>li").length;
		// 타이핑될 텍스트를 가져온다 
		var typingTxt = $(".typing-txt>ul>li").eq(liIndex).text(); 
		typingTxt=typingTxt.split(""); // 한글자씩 자른다. 
		if(typingBool==false){ // 타이핑이 진행되지 않았다면 
			typingBool=true; 
		    tyInt = setInterval(typing,100); // 반복동작 
		} 
		

		function typing(){ 
			$(".typing ul li").removeClass("on");
			$(".typing ul li").eq(liIndex).addClass("on");
		  if(typingIdx<typingTxt.length){ // 타이핑될 텍스트 길이만큼 반복 
		     $(".typing ul li").eq(liIndex).append(typingTxt[typingIdx]); // 한글자씩 이어준다. 
		     typingIdx++; 
		 } else{ if(liIndex<liLength-1){
		     //다음문장으로  가기위해 인덱스를 1증가
		     liIndex++; 
		     //다음문장을 타이핑하기위한 셋팅
		     typingIdx=0;
		     typingBool = false; 
		     typingTxt = $(".typing-txt>ul>li").eq(liIndex).text(); 
		
		     //다음문장 타이핑전 1초 쉰다
		     clearInterval(tyInt);
		          //타이핑종료
		
		          setTimeout(function(){
		           //1초후에 다시 타이핑 반복 시작
		           tyInt = setInterval(typing,100);
		       },1000);
		      } else if(liIndex==liLength-1){
		
		         //마지막 문장까지 써지면 반복종료
		         clearInterval(tyInt);
		     	}
		 	} 
		}  
    });

	$("#stage1_skip").click(function(){
		$(".typing > ul > li").stop();
		clearInterval(tyInt);
		$(".typing > ul > li").remove();
		$(".typing > ul").append($("<li></li><li></li><li></li>"));

		stage1 = true;
		//$("#mainback").hide();
		$("#enterstage1").hide();

		$("#mainback").attr("src", saveBG);
		$(".stage").hide();

		$(".lobby").show();
		$(".intro").hide();
		$("#canvas").show();
		// startGame(); 
		initial();
		//draw();
	});
	var tyInt;
    $("#stage2IMG").click(function(){
    	if(clearstage1){
    		mySound.Stop();
			mySound.Play('keyboard', 'ef');
    		$(".intro").hide();
			// stage2 스토리 출력
			if(player == 'mario'){
				$("#stage2_marioIMG").attr("src", "images/stageintro/mario.png");
				$("#stage2_textmario").text("마리오");
			}
			else{
				$("#stage2_marioIMG").attr("src", "images/stageintro/luigi.png");
				$("#stage2_textmario").text("루이지");
			}
			$("#enterstage2").show();
			saveBG = $("#mainback").attr("src");
			$("#mainback").attr("src", "images/lobby/blackbackground.png");
			var typingBool = false; 
			var typingIdx=0; 
			var liIndex = 0;
			var liLength = $(".typing-txt.2>ul>li").length;
				// 타이핑될 텍스트를 가져온다 
				var typingTxt = $(".typing-txt.2>ul>li").eq(liIndex).text(); 
				typingTxt=typingTxt.split(""); // 한글자씩 자른다. 
				if(typingBool==false){ // 타이핑이 진행되지 않았다면 
					typingBool=true; 
				    tyInt = setInterval(typing,100); // 반복동작 
				} 
				
				function typing(){ 
					$(".typing.2 ul li").removeClass("on");
					$(".typing.2 ul li").eq(liIndex).addClass("on");
				  if(typingIdx<typingTxt.length){ // 타이핑될 텍스트 길이만큼 반복 
				     $(".typing.2 ul li").eq(liIndex).append(typingTxt[typingIdx]); // 한글자씩 이어준다. 
				     typingIdx++; 
				 } else{ if(liIndex<liLength-1){
				     //다음문장으로  가기위해 인덱스를 1증가
				     liIndex++; 
				     //다음문장을 타이핑하기위한 셋팅
				     typingIdx=0;
				     typingBool = false; 
				     typingTxt = $(".typing-txt.2>ul>li").eq(liIndex).text(); 
				
				     //다음문장 타이핑전 1초 쉰다
				     clearInterval(tyInt);
				          //타이핑종료
				
				          setTimeout(function(){
				           //1초후에 다시 타이핑 반복 시작
				           tyInt = setInterval(typing,100);
				       },1000);
				      } else if(liIndex==liLength-1){
				
				         //마지막 문장까지 써지면 반복종료
				         clearInterval(tyInt);
				     	}
				 	} 
				}  
	
    		}
    	else{
    		alert("stage1을 클리어해야합니다.");
    	}
    });
	$("#stage2_skip").click(function(){
		$(".typing > ul > li").stop();
		clearInterval(tyInt);
		$(".typing > ul > li").remove();
		$(".typing > ul").append($("<li></li><li></li><li></li>"));

		stage2 = true;
		//$("#mainback").hide();
		$("#enterstage2").hide();

		$("#mainback").attr("src", saveBG);
		$(".stage").hide();

		$(".lobby").show();
		$(".intro").hide();
		$("#canvas").show();
		//startGame(); 
		initial();
		//draw();
	});
    $("#stage3IMG").click(function(){
    	if(clearstage2){
    		mySound.Stop();
    		mySound.Play('keyboard', 'ef');
    		$(".intro").hide();
			// stage2 스토리 출력
			if(player == 'mario'){
				$("#stage3_marioIMG").attr("src", "images/stageintro/mario.png");
				$("#stage3_textmario").text("마리오");
			}
			else{
				$("#stage3_marioIMG").attr("src", "images/stageintro/luigi.png");
				$("#stage3_textmario").text("루이지");
			}
			$("#enterstage3").show();
			saveBG = $("#mainback").attr("src");
			$("#mainback").attr("src", "images/lobby/blackbackground.png");
			var typingBool = false; 
			var typingIdx=0; 
			var liIndex = 0;
			var liLength = $(".typing-txt.3>ul>li").length;
				// 타이핑될 텍스트를 가져온다 
				var typingTxt = $(".typing-txt.3>ul>li").eq(liIndex).text(); 
				typingTxt=typingTxt.split(""); // 한글자씩 자른다. 
				if(typingBool==false){ // 타이핑이 진행되지 않았다면 
					typingBool=true; 
				    tyInt = setInterval(typing,100); // 반복동작 
				} 
				
				function typing(){ 
					$(".typing.3 ul li").removeClass("on");
					$(".typing.3 ul li").eq(liIndex).addClass("on");
				  if(typingIdx<typingTxt.length){ // 타이핑될 텍스트 길이만큼 반복 
				     $(".typing.3 ul li").eq(liIndex).append(typingTxt[typingIdx]); // 한글자씩 이어준다. 
				     typingIdx++; 
				 } else{ if(liIndex<liLength-1){
				     //다음문장으로  가기위해 인덱스를 1증가
				     liIndex++; 
				     //다음문장을 타이핑하기위한 셋팅
				     typingIdx=0;
				     typingBool = false; 
				     typingTxt = $(".typing-txt.3>ul>li").eq(liIndex).text(); 
				
				     //다음문장 타이핑전 1초 쉰다
				     clearInterval(tyInt);
				          //타이핑종료
				
				          setTimeout(function(){
				           //1초후에 다시 타이핑 반복 시작
				           tyInt = setInterval(typing,100);
				       },1000);
				      } else if(liIndex==liLength-1){
				
				         //마지막 문장까지 써지면 반복종료
				         clearInterval(tyInt);
				     	}
				 	} 
				}  
	
    	}
    	else{
    		alert("stage2를 클리어해야합니다.");
    	}
    });
    $("#stage3_skip").click(function(){
		$(".typing > ul > li").stop();
		$(".typing > ul > li").remove();
		clearInterval(tyInt);
		$(".typing > ul").append($("<li></li><li></li><li></li>"));

		stage3 = true;
		//$("#mainback").hide();
		$("#enterstage3").hide();


		$("#mainback").attr("src", saveBG);
		$(".stage").hide();

		$(".lobby").show();
		$(".intro").hide();
		$("#canvas").show();
		//startGame(); 
		initial();
		//draw();
	});
    $("#stage4IMG").click(function(){
    	if(clearstage3){
    		mySound.Stop();
    		mySound.Play('keyboard', 'ef');
    		$(".intro").hide();
			// stage2 스토리 출력
			if(player == 'mario'){
				$("#stage4_marioIMG").attr("src", "images/stageintro/mario.png");
				$("#stage4_textmario").text("마리오");
			}
			else{
				$("#stage4_marioIMG").attr("src", "images/stageintro/luigi.png");
				$("#stage4_textmario").text("루이지");
			}
			$("#enterstage4").show();
			saveBG = $("#mainback").attr("src");
			$("#mainback").attr("src", "images/lobby/blackbackground.png");
			var typingBool = false; 
			var typingIdx=0; 
			var liIndex = 0;
			var liLength = $(".typing-txt.4>ul>li").length;
				// 타이핑될 텍스트를 가져온다 
				var typingTxt = $(".typing-txt.4>ul>li").eq(liIndex).text(); 
				typingTxt=typingTxt.split(""); // 한글자씩 자른다. 
				if(typingBool==false){ // 타이핑이 진행되지 않았다면 
					typingBool=true; 
				    tyInt = setInterval(typing,100); // 반복동작 
				} 
				
				function typing(){ 
					$(".typing.4 ul li").removeClass("on");
					$(".typing.4 ul li").eq(liIndex).addClass("on");
				  if(typingIdx<typingTxt.length){ // 타이핑될 텍스트 길이만큼 반복 
				     $(".typing.4 ul li").eq(liIndex).append(typingTxt[typingIdx]); // 한글자씩 이어준다. 
				     typingIdx++; 
				 } else{ if(liIndex<liLength-1){
				     //다음문장으로  가기위해 인덱스를 1증가
				     liIndex++; 
				     //다음문장을 타이핑하기위한 셋팅
				     typingIdx=0;
				     typingBool = false; 
				     typingTxt = $(".typing-txt.4>ul>li").eq(liIndex).text(); 
				
				     //다음문장 타이핑전 1초 쉰다
				     clearInterval(tyInt);
				          //타이핑종료
				
				          setTimeout(function(){
				           //1초후에 다시 타이핑 반복 시작
				           tyInt = setInterval(typing,100);
				       },1000);
				      } else if(liIndex==liLength-1){
				
				         //마지막 문장까지 써지면 반복종료
				         clearInterval(tyInt);
				     	}
				 	} 
				}  
	
    	}
    	else{
    		alert("stage3를 클리어해야합니다");
    	}
    });
    $("#stage4_skip").click(function(){
		$(".typing > ul > li").stop();
		$(".typing > ul > li").remove();
		clearInterval(tyInt);
		$(".typing > ul").append($("<li></li><li></li><li></li>"));

		stage4 = true;
		//$("#mainback").hide();
		$("#enterstage4").hide();


		$("#mainback").attr("src", saveBG);
		$(".stage").hide();


		$(".lobby").show();
		$(".intro").hide();
		$("#canvas").show();
		//startGame(); 
		initial();
		//draw();
	});
    $("#stage5IMG").click(function(){
    	if(clearstage4){
    		mySound.Stop();
    		mySound.Play('keyboard', 'ef');
    		$(".intro").hide();
			// stage2 스토리 출력
			if(player == 'mario'){
				$("#stage5_marioIMG").attr("src", "images/stageintro/mario.png");
				$("#stage5_textmario").text("마리오");
			}
			else{
				$("#stage5_marioIMG").attr("src", "images/stageintro/luigi.png");
				$("#stage5_textmario").text("루이지");
			}
			$("#enterstage5").show();
			saveBG = $("#mainback").attr("src");
			$("#mainback").attr("src", "images/lobby/blackbackground.png");
			var typingBool = false; 
			var typingIdx=0; 
			var liIndex = 0;
			var liLength = $(".typing-txt.5>ul>li").length;
				// 타이핑될 텍스트를 가져온다 
				var typingTxt = $(".typing-txt.5>ul>li").eq(liIndex).text(); 
				typingTxt=typingTxt.split(""); // 한글자씩 자른다. 
				if(typingBool==false){ // 타이핑이 진행되지 않았다면 
					typingBool=true; 
				    tyInt = setInterval(typing,100); // 반복동작 
				} 
				
				function typing(){ 
					$(".typing.5 ul li").removeClass("on");
					$(".typing.5 ul li").eq(liIndex).addClass("on");
				  if(typingIdx<typingTxt.length){ // 타이핑될 텍스트 길이만큼 반복 
				     $(".typing.5 ul li").eq(liIndex).append(typingTxt[typingIdx]); // 한글자씩 이어준다. 
				     typingIdx++; 
				 } else{ if(liIndex<liLength-1){
				     //다음문장으로  가기위해 인덱스를 1증가
				     liIndex++; 
				     //다음문장을 타이핑하기위한 셋팅
				     typingIdx=0;
				     typingBool = false; 
				     typingTxt = $(".typing-txt.5>ul>li").eq(liIndex).text(); 
				
				     //다음문장 타이핑전 1초 쉰다
				     clearInterval(tyInt);
				          //타이핑종료
				
				          setTimeout(function(){
				           //1초후에 다시 타이핑 반복 시작
				           tyInt = setInterval(typing,100);
				       },1000);
				      } else if(liIndex==liLength-1){
				
				         //마지막 문장까지 써지면 반복종료
				         clearInterval(tyInt);
				     	}
				 	} 
				}  
	
    	}
    	else{
    		alert("stage4를 클리어해야합니다");
    	}
    });
    $("#stage5_skip").click(function(){
		$(".typing > ul > li").stop();
		$(".typing > ul > li").remove();
		clearInterval(tyInt);
		$(".typing > ul").append($("<li></li><li></li><li></li>"));

		stage5 = true;
		//$("#mainback").hide();
		$("#enterstage5").hide();


		$("#mainback").attr("src", saveBG);
		$(".stage").hide();


		$(".lobby").show();
		$(".intro").hide();
		$("#canvas").show();
		//startGame(); 
		initial();
		//draw();
	});

	//============== 상점 구현 ==========================================
	
	$("#storeIMG").click(function(){
		$(".lobby").hide();
		$(".shop").show();
	});
	$("#ballimg-explain").change(function(){
		var sball = $(this).val();
		//alert(sball);
		if(sball == "images/ball1.png") $("#ballimg").attr("src","images/ball1.png");
	    else if(sball == "images/ball2.png") $("#ballimg").attr("src","images/ball2.png");
	    else if(sball == "images/ball3.png") $("#ballimg").attr("src","images/ball3.png");
	    else if(sball == "images/ball4.png") $("#ballimg").attr("src","images/ball4.png");
	    else if(sball == "images/ball5.png") $("#ballimg").attr("src","images/ball5.png");
	    else if(sball == "images/ball6.png") $("#ballimg").attr("src","images/ball6.png");
	    else if(sball == "images/ball7.png") $("#ballimg").attr("src","images/ball7.png");
		else if(sball == "images/ball8.png") $("#ballimg").attr("src","images/ball8.png");
	    else if(sball == "images/ball9.png") $("#ballimg").attr("src","images/ball9.png");
	    else if(sball == "images/ball10.png") $("#ballimg").attr("src","images/ball10.png");
	});

	$("#itembar-buy").click(function(){
		if(coin >= 500){
		paddleitem = 50;
		alert("구매되었습니다.");
		coin -= 500;
		$("#coin").html(coin);
	    }
	    else{
	    	alert("코인이 부족합니다");
	    }
	});
	$("#ballspeed-buy").click(function(){
		if(coin >= 500){
		ballitem = true;
		alert("구매되었습니다.");
		coin -= 500;
		$("#coin").html(coin);
	    }
	    else{
	    	alert("코인이 부족합니다");
	    }
	});
	$("#lifeitem-buy").click(function(){
		if(coin >= 500){
		lifeitem += 1;
		alert("구매되었습니다.");
		coin -= 500;
		$("#coin").html(coin);
	    }
	    else{
	    	alert("코인이 부족합니다");
	    }
	});
	$("#ballimg-apply").click(function(){
		applyball = $("#ballimg-explain option:selected").val();
		//alert(applyball);
		ballImage.src = applyball;
		alert("적용되었습니다.");
	});
	$("#mainshow1").click(function(){
		$(".shop").hide();
		$(".lobby").show();
	});

	//================ 환경설정 구현 ===================
	$("#settingIMG").click(function(){
		$(".lobby").hide();
		$(".setting").show();
	});
	$("#luigi").click(function(){
        player = 'luigi';
        $("#luigi").attr("src","images/luigi.png");
        $("#mario").attr("src","images/negative_chara.png");
    });
    $("#mario").click(function(){
        player = 'mario';
        $("#mario").attr("src","images/mario.png");
        $("#luigi").attr("src","images/negative_chara.png");
    });
    $("#BGIbutton1").click(function(){
        $("#mainback").attr("src","images/lobby/back1.png");
    });
    $("#BGIbutton2").click(function(){
		$("#mainback").attr("src","images/titlescreen2.jpg");
    });
    $("#BGIbutton3").click(function(){
		$("#mainback").attr("src","images/titlescreen3.jpg");
    });
	$("#mainshowing").click(function(){
		$(".setting").hide();
		$(".lobby").show();
	});

    //================ 조작법 구현 ====================
    $("#informationIMG").click(function(){
    	$(".lobby").hide();
    	$(".instructionpage").show();
    	$("#instructionalbum").attr("src",instructionArr[countnext]);

    	$("#nextbutton").click(function(){
    		$("#instructionalbum").attr("src",instructionArr[++countnext]);
    		if(countnext==3){
    			countnext=0;
    			$("#instructionalbum").attr("src",instructionArr[countnext]);	
    		}
    	});
    });
    $("#mainshow2").click(function(){
    	countnext=0;
    	$(".instructionpage").hide();
    	$(".lobby").show();
    });
    
}

function toggleintroSound(){
	if(document.getElementById('introSound').muted){
		document.getElementById('introSound').muted = false;
		$("#togglesoundintro").css("background-image", "url(images/sound_on.png)");	
	}else{
		document.getElementById('introSound').muted = true;
		$("#togglesoundintro").css("background-image","url(images/sound_off.png)");	
	}
	
}

function blink(){
	
	if(!(blinkCount % 6)){
		$('#B').css("color", "#e6bb45");
		$('#S').css("color", "#FFF");
	}else{
		$('#B').css("color", "#000");
		$('#S').css("color", "red");	
	}
	
	if(!(blinkCount % 5)){
		$('#W').css("color", "#e6bb45");
		$('#F').css("color", "#FFF");
	}else{
		$('#W').css("color", "#000");
		$('#F').css("color", "red");	
	}
	
	if(!(blinkCount % 7)){
		$('#three').css("color","#0CC");
	}else{
		$('#three').css("color","#000");	
	}
	blinkCount++;
}

function clickToStart(){
	$('#ctg').animate({left:352}, 300, 
		function(){
			$('#ctg').animate({left:355}, 500,	
				clickToStart())
		}
		);
	
}



