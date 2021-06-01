

	// 유저 설정이 루이지인지 마리오인지, 디폴트는 마리오.
	var player = "mario"; 
	// JavaScript Document
	var gameOver;
	//canvas
	var context;
	var WIDTH = 990; 
	var HEIGHT = 600;
	var nextcheck = false;
	var hasHitBall = false;
	//balls 
	var balls ;
	var threeBallsAtWork;
	var star;
	var fastDuration;
	var fast;
	var ballsCount;
	var score = 100;
	var coin = 999;
	
	//상점 공 아이템
	var ballitem = false;
	//상점 목숨 아이템
	var lifeitem = 0;
	//====================
	//선택된 스테이지는 true로 변경
	var stage1 = false;
	var stage2 = false;
	var stage3 = false;
	var stage4 = false;
	var stage5 = false;

	var life = 0;
	var stage1life = 5 + lifeitem;
	var stage2life = 5 + lifeitem;
	var stage3life = 4 + lifeitem;
	var stage4life = 4 + lifeitem;
	var stage5life = 3 + lifeitem;
	var applyball = "images/ball1.png";
	//====================
	var ballImage = new Image();
	//ballImage.src = applyball;
	//ballImage.src;
	var ballImagesSrc = ["images/ball1.png", "images/ball2.png", "images/ball3.png", "images/ball4.png",
	"images/ball5.png", "images/ball6.png", "images/ball7.png"];

	var koopaImage = new Image();
	koopaImage.src="images/Koopa1.png";
	var healthBar = new Image();
	healthBar.src= "images/health.png";
	var koopa=["images/Koopa1.png", "images/Koopa1_d1.png","images/Koopa1_d2.png"];
	var koopaHealth = 800;
	var koopaMove =0;
	var boss=false;
	var bossTick=0;
	var bossMessages=["Stage clear!", " ", "어라..?","이 노래는..?", "쿠파가 나타났다!"];
	var index=-1;
	var fireIndex=0;
	var fireProb = 0.005;
	var debuffProb = 0;
	//ball + direction and speed
	
	//============
	var callBackCounter=0;	

	//===========
	var radius = 12;
	
	//paddle
	
	var paddlex=650;
	var paddleh=80;
	//원래 패들 길이
	var bpaddlew = 200;
	//상점에서 아이템 보정값
	var paddleitem = 0;
	//기본 처음 시작할 때 패들 길이
	var paddlew = bpaddlew + paddleitem;
	

	var paddleColor;
	var paddleWidenDuration;
	var paddleShrinkDuration;
	var bossHitDuration=0;
	var paddleLastToken;
	var paddlePreviousToken;
	var bar = new Image();
	bar.src = "images/bar.jpg";
	var bar_luigi = new Image(), bar_mario = new Image();
	bar_luigi.src = "images/luigi.png";
	bar_mario.src = "images/mario.png";
	
	
	//bricks
	var bricks=[];
	var n_brick_cols = 5; // 13
	var n_brick_rows = 5; // 6
	var brick_padding = 5;
	var brick_width = (WIDTH - (brick_padding * (n_brick_cols + 1))) / n_brick_cols;
	//console.log(brick_width);
	var brick_height = 30; 
	var block = new Image();
	block.src = "images/blocks/block1.jpg";
	
	
	pickBrickColor = ["#63ad55", "#6db75f","#77c169","#81cb73","#8Bd57d","#95df87","#9fe991" ];
	
	bricks = new Array(n_brick_rows);
	for (i=0; i<n_brick_rows; i++){
		bricks[i] = new Array(n_brick_cols);
		for (j=0; j<n_brick_cols; j++){
			bricks[i][j] = 1;
		}
	}
	var change_image;
//keyboard
var paddleRight = false;
var paddleLeft = false;

var rightDown = false;
var leftDown = false;

//pause/gameover
var pause;
var prepause;
var lostCount;

//mouse
var canvasMinX;
var canvasMaxX;
var canvasStart;


//Tokens
var tokenLetters = new Object();

function initial(){
	callBackCounter++;

	for(ball in balls)
	{
		delete balls[ball];  
	}
	for(token in tokenLetters)
	{
		delete tokenLetters[token]; 
	}
	balls = { 0 : {x : 100, y : 300, dx : 12, dy: 6 }};

	ballsCount = 0;
	
	fastDuration = 0;
	paddleShrinkDuration = 0;
	paddleWidenDuration = 0;
	paddleColor = "#d23b2f";
	
	for (i=0; i<n_brick_rows; i++){
		bricks[i] = new Array(n_brick_cols);
		for (j=0; j<n_brick_cols; j++){
			bricks[i][j] = 1;
		}
	}

	fast = false;
	threeBallsAtWork = false;
	star = false;
	
	gameOver = false;
	stage1life = 5 + lifeitem;
	stage2life = 5 + lifeitem;
	stage3life = 4 + lifeitem;
	stage4life = 4 + lifeitem;
	stage5life = 3 + lifeitem;

	pause = false;
	lostCount = 0;
	prepause = true;
	
	
	$(document).keydown(keyDown);
	$(document).keyup(keyUp);
	$(document).mousemove(onMouseMove);

	if(callBackCounter==1)
	{
		$("#canvas").click(initPause);
		$("#announce").click(initPause);
	}

	context = document.getElementById("canvas").getContext("2d");
	startAnimate();
	var canvasStart = $("#canvas").offset().left;
	$("#coin").html(coin);

	if(stage1){
		mySound.Stop();
		mySound.Play('stage1', 'bg');
		$("#life").html(stage1life);
		life = stage1life;
		window.setTimeout(function(){frames =  setInterval(draw, 20);prepause = false;$("#canvas").css("background-image","url(images/normal1.JPG)");},0);
		change_image = setInterval(switchImg, 10000);
		block.src = "images/blocks/block1.jpg";
	}
	else if(stage2){
		mySound.Stop();
		mySound.Play('stage2', 'bg');
		$("#life").html(stage2life);
		life = stage2life;
		window.setTimeout(function(){frames =  setInterval(draw, 20);prepause = false;$("#canvas").css("background-image","url(images/desert1.JPG)");},0);
		change_image = setInterval(switchImg, 30000);
		block.src = "images/blocks/block2.jpg";
	}
	else if(stage3){
		mySound.Stop();
		mySound.Play('stage3', 'bg');
		$("#life").html(stage3life);
		life = stage3life;
		window.setTimeout(function(){frames =  setInterval(draw, 20);prepause = false;$("#canvas").css("background-image","url(images/beach1.JPG)");},0);
		change_image = setInterval(switchImg, 30000);
		block.src = "images/blocks/block3.jpg";
	}
	else if(stage4){
		mySound.Stop();
		mySound.Play('stage4', 'bg');
		$("#life").html(stage4life);
		life = stage4life;
		window.setTimeout(function(){frames =  setInterval(draw, 20);prepause = false;$("#canvas").css("background-image","url(images/snow1.JPG)");},0);
		change_image = setInterval(switchImg, 30000);
		block.src = "images/blocks/block4.jpg";
	}
	else if(stage5){
		mySound.Stop();
		mySound.Play('stage5', 'bg');
		$("#life").html(stage5life);
		life = stage5life;
		window.setTimeout(function(){frames =  setInterval(draw, 20);prepause = false;$("#canvas").css("background-image","url(images/castle1.JPG)");},0);
		change_image = setInterval(switchImg, 30000);
		block.src = "images/blocks/block5.jpg";
	}
	
	
}
$.fn.isBound = function(type, fn) {
	var data = this.data('events')[type];

	if (data === undefined || data.length === 0) {
		return false;
	}

	return (-1 !== $.inArray(fn, data));
};

var image_index = 1;
function switchImg(){
	if(image_index == 3){
		image_index = 1
	}else{
		image_index++;
	}
	if(stage1){
		switch (image_index) {
			case 1: $("#canvas").css("background-image","url(images/normal1.JPG)")
			break;
			case 2: $("#canvas").css("background-image","url(images/normal2.JPG)")
			break;
			default: $("#canvas").css("background-image","url(images/normal3.JPG)")
		} 
	}
	else if(stage2){
		switch (image_index) {
			case 1: $("#canvas").css("background-image","url(images/desert1.JPG)")
			break;
			case 2: $("#canvas").css("background-image","url(images/desert2.JPG)")
			break;
			default: $("#canvas").css("background-image","url(images/desert3.JPG)")
		}
	}
	else if(stage3){
		switch (image_index) {
			case 1: $("#canvas").css("background-image","url(images/beach1.JPG)")
			break;
			case 2: $("#canvas").css("background-image","url(images/beach2.JPG)")
			break;
			default: $("#canvas").css("background-image","url(images/beach3.JPG)")
		}
	}
	else if(stage4){
		switch (image_index) {
			case 1: $("#canvas").css("background-image","url(images/snow1.JPG)")
			break;
			case 2: $("#canvas").css("background-image","url(images/snow2.JPG)")
			break;
			default: $("#canvas").css("background-image","url(images/snow3.JPG)")
		}
	}
	else if(stage5){
		switch (image_index) {
			case 1: $("#canvas").css("background-image","url(images/castle1.JPG)")
			break;
			case 2: $("#canvas").css("background-image","url(images/castle2.JPG)")
			break;
			default: $("#canvas").css("background-image","url(images/castle3.JPG)")
		}
	}
	
}


function draw(){
	
	
	clear();
	drawBalls();
	paddle();
	drawBricks();
	bounce();
	Tokens();
}

function rect(c,x,y,w,h) 
{
	context.fillStyle = c;
	context.beginPath();
	context.rect(x,y,w,h);
	context.closePath();
	context.fill();
}


function drawBalls(){

	if(fastDuration){
		fastDuration--;
	}else if(fast){
		balls[ball].dx *= (2/3);
		balls[ball].dy *= (2/3);
		fast = false;
	}
	else if(fast && boss==true)
	{
		//1.5배된 속도를 원상태로 되돌려놓음
		balls[ball].dx /= 1.75;
		balls[ball].dy /= 1.75;
		fast = false;
	}

	
	for(ball in balls){
		if(stage1){
			if(ballitem){
				var dx = balls[ball].dx * (2/3);	
				var dy = balls[ball].dy * (2/3);
			}else{
				var dx = balls[ball].dx;	
				var dy = balls[ball].dy;
			}
		}
		if(stage2){
			if(ballitem){
				var dx = balls[ball].dx * (11/10) * (2/3);	
				var dy = balls[ball].dy * (11/10) * (2/3);
			}else{
				var dx = balls[ball].dx * (11/10);	
				var dy = balls[ball].dy * (11/10);
			}
		}
		if(stage3){
			if(ballitem){
				var dx = balls[ball].dx * (12/10) * (2/3);	
				var dy = balls[ball].dy * (12/10) * (2/3);
			}else{
				var dx = balls[ball].dx * (12/10);	
				var dy = balls[ball].dy * (12/10);
			}
		}
		if(stage4){
			if(ballitem){
				var dx = balls[ball].dx * (13/10) * (2/3);	
				var dy = balls[ball].dy * (13/10) * (2/3);
			}else{
				var dx = balls[ball].dx * (13/10);	
				var dy = balls[ball].dy * (13/10);
			}
		}
		if(stage5){
			if(ballitem){
				var dx = balls[ball].dx * (14/10) * (2/3);	
				var dy = balls[ball].dy * (14/10) * (2/3);
			}else{
				var dx = balls[ball].dx * (14/10);	
				var dy = balls[ball].dy * (14/10);
			}
		}
		
		
		balls[ball].x += dx;
		balls[ball].y += dy;
		
		var y = balls[ball].y;
		var x = balls[ball].x;
		if(star){
			var srcIndex = Math.floor(Math.random()* 7);
			ballImage.src = ballImagesSrc[srcIndex];
		}
		else
			ballImage.src = applyball;

		context.drawImage(ballImage, x, y, radius*2, radius*2);
	}
	
}

function clear(){
	context.clearRect(0,0,WIDTH, HEIGHT);
}

function paddle(){

	if(stage1){
		bpaddlew = 200;
	}
	else if(stage2){
		bpaddlew = 200 * (9/10);
	}
	else if(stage3){
		bpaddlew = 200 * (8/10);
	}
	else if(stage4){
		bpaddlew = 200 * (7/10);
	}
	else if(stage5){
		bpaddlew = 200 * (6/10);
	}

	if((rightDown) && ((paddlex + paddlew) < WIDTH)) 
	{
		paddlex += 15;
	}
	if((leftDown) && (paddlex > 0)) 
	{
		paddlex -= 15;
	} 
	
	
	if(paddleWidenDuration || paddleShrinkDuration){
		if(paddlew == bpaddlew + paddleitem){
			if(paddleLastToken === "images/bigmushroom.png") paddlew += 100;
			else paddlew -= 100;
		}
		if(paddlew == bpaddlew + paddleitem - 100){
			if(!paddleShrinkDuration || paddleLastToken === "images/bigmushroom.png") paddlew += 100;
		}
		if(paddlew == bpaddlew + paddleitem + 100){
			if(paddleLastToken === "images/nepenthes.png") paddlew -= 100;
			
		}
	}else paddlew = bpaddlew + paddleitem;
	
	paddleWidenDuration && paddleWidenDuration--;
	paddleShrinkDuration && paddleShrinkDuration--;
	

	context.drawImage(bar, paddlex, HEIGHT-paddleh, paddlew, paddleh/4);
	if(player == "mario"){
		context.drawImage(bar_mario, paddlex+paddlew/2-15, HEIGHT-paddleh*3/4,30,paddleh*3/4);
	}else{
		context.drawImage(bar_luigi, paddlex+paddlew/2-15, HEIGHT-paddleh*3/4,30,paddleh*3/4);
	}
}


function drawBricks(){
	var isWon = true;
	for(i=0; i<n_brick_rows; i++)
	{
		for(j=0; j<n_brick_cols; j++)
		{	
			if(bricks[i][j] == 1){
				isWon = false;
				context.drawImage(
					block,
					(brick_padding + (j*(brick_width+brick_padding))),
					(brick_padding + (i*(brick_height+brick_padding))),
					brick_width, brick_height);
		}
	}
}


if(isWon == true && boss==false)
{
	mySound.Stop();
	mySound.Play('stageClear', 'ef');
	if(stage5)
	{
		koopaHealth = 800;
		boss=true;
		gameOver=true;
		clearInterval(frames);
		showMessages($("#announce"),bossMessages[0]);
		window.setTimeout(function(){frames =  setInterval(bossStage, 20);prepause = false;$("#canvas").css("background-image","url(images/bg-castle.png)");},16500);

    }
    else
    {
		//아이템 초기화
		ballitem = false;
		paddleitem = 0;
		lifeitem = 0;

		clearInterval(frames);
		$("#announce").html("You won!");
		$("#announce").show(); 
		$("#announce").fadeOut(2000);
		clearInterval(change_image);

		
		if(!stage5){
			nextcheck = confirm( 'GO NEXT STAGE?' );
		}
		for(ball in balls){
			delete balls[ball];  
			gameOver=true;
		}
		$("#canvas").hide();
		$(".intro").show();


		if(stage1){
			stage1 = false;
			clearstage1 = true;
			if(nextcheck){
				nextcheck = false;
				clear();
				$('#startIMG').trigger('click');
				$('#stage2IMG').trigger('click');
			}
			else{
				mySound.Stop();
				mySound.Play('intro', 'bg');
			}
		}
		else if(stage2){
			stage2 = false;
			clearstage2 = true;
			if(nextcheck){
				nextcheck = false;
				clear();
				$('#startIMG').trigger('click');
				$('#stage3IMG').trigger('click');
			}
			else{
				mySound.Stop();
				mySound.Play('intro', 'bg');
			}
		}
		else if(stage3){
			stage3 = false;
			clearstage3 = true;
			if(nextcheck){
				nextcheck = false;
				clear();
				$('#startIMG').trigger('click');
				$('#stage4IMG').trigger('click');
			}
			else{
				mySound.Stop();
				mySound.Play('intro', 'bg');
			}
		}
		else if(stage4){
			stage4 = false;
			clearstage4 = true;
			if(nextcheck){
				nextcheck = false;
				clear();
				$('#startIMG').trigger('click');
				$('#stage5IMG').trigger('click');
			}
			else{
				mySound.Stop();
				mySound.Play('intro', 'bg');
			}
		}
	}
}



}


function Tokens(){
	for(token in tokenLetters){
		
		var changedTokenColor;
		tokenSpeed = 10;
		//hit bottom
		if(tokenLetters[token].y > HEIGHT) {delete tokenLetters[token]; break;}
		//collect
		if((tokenLetters[token].y + tokenSpeed > (HEIGHT - paddleh)) && (tokenLetters[token].x - 5 > paddlex) && (tokenLetters[token].x + 5 < paddlex + paddlew)){
			switch(tokenLetters[token].type)
			{
				case "images/mushroom.png":
					mySound.Play('upsoundeffect', 'ef');
					score +=100;
					life++;
					$("#life").html(life);					

					$("#life").html(life);
					$('#score').html(score);
					delete tokenLetters[token];
					break;
				case "images/bigmushroom.png":
					mySound.Play('mushroomsoundeffect', 'ef');
					score +=20;
					$('#score').html(score);
					if(!paddleWidenDuration) paddlex -= 50;
					paddleWidenDuration += 150;
					paddlePrevousToken = paddleLastToken;
					paddleLastToken = "images/bigmushroom.png";
					delete tokenLetters[token];
					break;
				case "images/star.png":
					mySound.Play('bonus', 'ef');
					score +=50;
					$('#score').html(score);
					starItem();
					delete tokenLetters[token];
					break;
				case "images/bluemushroom.png":
					mySound.Play('bad_token', 'ef');
					score -=100;
					$('#score').html(score);
					if(!fastDuration){
						for(ball in balls){
							balls[ball].dx *= 1.5;	
							balls[ball].dy *= 1.5;
						}
						fast = true;
					}
					fastDuration += 100;
					delete tokenLetters[token];
					break;
				case "images/nepenthes.png":
					mySound.Play('bad_token', 'ef');
					score -=100;
					$('#score').html(score);
					if(!paddleShrinkDuration) paddlex += 50;
					paddleShrinkDuration += 150;
					paddlePrevousToken = paddleLastToken;
					paddleLastToken = "images/nepenthes.png";
					delete tokenLetters[token];
					break;
			}
		}
		//아이템 그리기
		if(tokenLetters[token])
		{
			var tokenX = tokenLetters[token].x;
			var tokenY = tokenLetters[token].y += tokenSpeed;
			var type = tokenLetters[token].type;
			var img = new Image();
			if(type!=null)
				img.src = type;
			img.height=50;
			img.width=50;
			context.drawImage(img,tokenX,tokenY);
		}
	}


	function starItem()
	{
		star = true;
		setTimeout(function(){star=false;},5*1000);
		mySound.muteBG();
		mySound.setTimePlay('star', 'ef', 5000);
		setTimeout(function(){mySound.playBG()},5*1000);
	}

}


function bounce()
{
	 //bricks

	 function brickHit(i,j,y,x)
	 {

	 	bricks[i][j] = 0;
	 	mySound.Play('brick', 'ef');
	 	score += 12;
	 	var num = Math.floor(Math.random()*10+1);
	 	coin += num;
	 	$('#score').html(score);
	 	$("#coin").html(coin);
	 	var random = Math.random();
	 	if(random < 0.7){
	 		tokenLetters["pos-i"+i+"j"+j] = new Object;
	 		var newToken = tokenLetters["pos-i"+i+"j"+j];
	 		newToken["y"] = y;
	 		newToken["x"] = x;
	 		newToken["color"] = 0;

	 		random = Math.random();
	 		if(!star)
	 		{
	 			if(random <= 0.2)newToken["type"] = "images/mushroom.png";
	 			else if(random <= 0.4)newToken["type"] = "images/bigmushroom.png";	
				else if(random <= 0.6)newToken["type"] = "images/star.png";
				else if(random <= 0.8)newToken["type"] = "images/bluemushroom.png";
				else if(random <= 1)newToken["type"] = "images/nepenthes.png";
			}
		}
		
	}
	for(ball in balls)
	{
		var y = balls[ball].y;
		var x = balls[ball].x;	
		var dx = balls[ball].dx;	
		var dy = balls[ball].dy;	
		if((y < (HEIGHT-paddleh)) &&hasHitBall==true)
		{
			$(document).unbind();
			$(document).keydown(keyDown);
			$(document).keyup(keyUp);
			$(document).mousemove(onMouseMove);
			hasHitBall=false;
		}
	}
	
	
	
	
	for(i=0; i<n_brick_rows; i++)
	{
		for(j=0; j<n_brick_cols; j++)
		{
			
			var brick_x_l = brick_padding + (j*(brick_width+brick_padding)); //left coordinate of brick x;
			var brick_x_r = brick_width + brick_padding + (j*(brick_width+brick_padding)); // right coordinate of brick x;
			var brick_y_b =  (brick_padding + brick_height + (i*(brick_height+brick_padding))); //bottom coordinate of brick y;
			var brick_y_t =  (brick_padding + (i*(brick_height+brick_padding))); //top coordinate of brcik y;
			var brick_edge = brick_height *0.4; 
			var tokenY = brick_y_b;
			var tokenX = (0.5*brick_width) + brick_padding + (j*(brick_width+brick_padding)); //center x coordinate of brick;
			
			if(bricks[i][j] == 1)
			{

				for(ball in balls)
				{
					var y = balls[ball].y;
					var x = balls[ball].x;	
					var dx = balls[ball].dx;	
					var dy = balls[ball].dy;	

					//directions
					var  ballDirection;
					
					
					if((dx<0) && (dy<0)) ballDirection = "up left";
					if((dx>0) && (dy<0)) ballDirection = "up right";
					if((dx<0) && (dy>0)) ballDirection = "down left";
					if((dx>0) && (dy>0)) ballDirection = "down right";
					if(!star)
					{
						if( ballDirection == "up left" && ((y + dy - radius) <= brick_y_b) && ((y + dy + radius) >= brick_y_t) && ( ((x + dx - radius) >= brick_x_l) && ( (x + dx - radius) <= brick_x_r) )  )
						{
							if((y-radius < brick_y_b) )
							{
							 balls[ball].dx = -(dx); 
							}
							else
							{
								balls[ball].dy = -(dy);
							}
							brickHit(i,j, tokenY,tokenX);

						}
						else if( ballDirection == "down left" && ((y + dy + radius) >= (brick_y_t)) && ((y + dy - radius) <= brick_y_b) && ( ((x + dx - radius) >= brick_x_l) && ( (x + dx - radius) <= brick_x_r ) )  )
						{
							if( (y+radius > (brick_y_t))  )
							{
							 balls[ball].dx = -(dx); 
							}
							else
							{
								balls[ball].dy = -(dy);
							}
							brickHit(i,j, tokenY,tokenX);

						}
						else if( ballDirection == "up right" && ((y + dy - radius) <= brick_y_b) && ((y + dy + radius) >= brick_y_t) && ( ((x + dx + radius) >= brick_x_l) && ( (x + dx + radius) <= brick_x_r ) )  )
						{
							if((y-radius < brick_y_b) )
							{
							 balls[ball].dx = -(dx); 
							}
							else
							{
								balls[ball].dy = -(dy);
							}
							brickHit(i,j, tokenY,tokenX);

						}
						else if( ballDirection == "down right" && ((y + dy + radius) >= brick_y_t) && ((y + dy - radius) <= brick_y_b) && ( ((x + dx + radius) >= brick_x_l) && ( (x + dx + radius) < brick_x_r ) )  )
						{
							if( (y+radius > (brick_y_t)) )
							{
							 balls[ball].dx = -(dx); 
							}
							else
							{
								balls[ball].dy = -(dy);
							}
							brickHit(i,j, tokenY,tokenX);
						}
					}
					else 
					{
						if(((y + dy - radius) <= brick_y_b) && ((y + dy + radius) >= brick_y_t) && (((x + dx - radius) >= brick_x_l) && ( (x + dx - radius) <= brick_x_r)))
							brickHit(i,j, tokenY,tokenX);
						else if(((y + dy + radius) >= (brick_y_t)) && ((y + dy - radius) <= brick_y_b) && ( ((x + dx - radius) >= brick_x_l) && ( (x + dx - radius) <= brick_x_r )))
							brickHit(i,j, tokenY,tokenX);
						else if(((y + dy - radius) <= brick_y_b) && ((y + dy + radius) >= brick_y_t) && ( ((x + dx + radius) >= brick_x_l) && ( (x + dx + radius) <= brick_x_r )))
							brickHit(i,j, tokenY,tokenX);
						else if(((y + dy + radius) >= brick_y_t) && ((y + dy - radius) <= brick_y_b) && ( ((x + dx + radius) >= brick_x_l) && ( (x + dx + radius) < brick_x_r)))
							brickHit(i,j, tokenY,tokenX);
					}
				}

			}
		}

	 //walls
	 for(ball in balls)
	 {
	 	var y = balls[ball].y;
	 	var x = balls[ball].x;	
	 	var dx = balls[ball].dx;	
	 	var dy = balls[ball].dy;


	 	if ((x + dx + radius) > WIDTH || (x + dx - radius) < 0)
	 	{
	 		balls[ball].dx = -(dx);
	 		mySound.Play('paddle', 'ef');
	 	}

	 	if((y + dy - radius) < 0)
	 	{
	 		balls[ball].dy = -(dy);
	 		mySound.Play('paddle', 'ef');
	 	}


		//paddle
		if ((y + dy > (HEIGHT-radius-paddleh)) && (lostCount == 0))
		{
			if( ((x+dx) > (paddlex-(radius*2))) && ((x+dx) < (paddlex + paddlew+(radius*2)) ))
			{
	   			 //below surface or on edge
	   			 if( ((HEIGHT-y-dy) < paddleh) || ( (x+dx) < ((paddlex + (paddlew*0.1))+(radius*2)) ) || (  (x+dx) > (paddlex + (paddlew*0.9)-(radius*2))   ) ) 
	   			 {
					 //considering current movement
					 if( ( ((rightDown) && (dx > 0)) || ((leftDown) && (dx < 0)) ) && hasHitBall ==false)
					 {
					 	balls[ball].dy = -(dy);
					 	mySound.Play('paddle', 'ef');
					 	$(document).unbind();
						hasHitBall=true;
						rightDown =false;
					 	leftDown=false;
					 }
					 else
					 {
					 	if(hasHitBall==false)
					 	{
						 	balls[ball].dy = -(dy);
						 	balls[ball].dx = -(dx);
						 	mySound.Play('paddle', 'ef');
						 	$(document).unbind();
						 	rightDown =false;
					 		leftDown=false;
							hasHitBall=true;
					 	}
					 }

				}
				else 
				{
					balls[ball].dy = -(dy);
					mySound.Play('paddle', 'ef');

				}
			}
		}
		//lost
		if(y > (HEIGHT+radius))
		{
			//생명이 1일때 죽엇을 때 게임 오버. 게임 오버 음악과 메세지 출력하는 함수.
			if(life==1)
			{
				delete balls[ball];
				if(isEmpty())
				{
					life--;
					$("#life").html(life);

					clearInterval(frames);
					$("#announce").html("game over");
					$("#announce").show();
					
					//아이템 초기화
					ballitem = false;
					paddleitem = 0;
					lifeitem = 0;


					clearInterval(change_image);
					mySound.Stop();
					mySound.Play('gameover', 'ef');

					gameOver=true;
					if(stage1){
						stage1 = false;
					}
					else if(stage2){
						stage2 = false;
					}
					else if(stage3){
						stage3 = false;
					}
					else if(stage4){
						stage4 = false;
					}
					else if(stage5){
						stage5 = false;
					}
					$("#announce").fadeOut(5000);
					setTimeout(function(){

						$("#canvas").hide();
						$(".intro").show();
						mySound.Stop();
						mySound.Play('intro', 'bg');
					}, 3000);
				}		
			}
			//생명이 1이 아닐때는 life--하고 게임 재개하는 부분
			else
			{
				life--;
				if(star)
				{
					star=false;
					mySound.Mute();
					mySound.playBG();
				}				
				clearInterval(frames);
				for(ball in balls)
				{
					delete balls[ball]; 
				}
				for(token in tokenLetters)
				{
					delete tokenLetters[token]; 
				}
				balls = { 0 : {x : 100, y : 300, dx : 12, dy: 6 }};
				fastDuration = 0;
				paddleShrinkDuration = 0;
				paddleWidenDuration = 0;
				
				fast = false;
				star = false;

				gameOver = false;
				pause = false;
				prepause = true;
				lostCount = 0;
				$("#announce").html("Life -1!");
				$("#announce").show().delay(1000).fadeOut();
				$('#score').html(score);
				$("#coin").html(coin);
				if(stage1){
					$("#life").html(life);

					window.setTimeout(function(){frames =  setInterval(draw, 20);prepause = false;$("#canvas").css("background-image","url(images/normal1.JPG)");},0);
				}
				else if(stage2){
					$("#life").html(life);

					window.setTimeout(function(){frames =  setInterval(draw, 20);prepause = false;$("#canvas").css("background-image","url(images/desert1.JPG)");},0);
				}
				else if(stage3){
					$("#life").html(life);

					window.setTimeout(function(){frames =  setInterval(draw, 20);prepause = false;$("#canvas").css("background-image","url(images/beach1.JPG)");},0);
				}
				else if(stage4){
					$("#life").html(life);

					window.setTimeout(function(){frames =  setInterval(draw, 20);prepause = false;$("#canvas").css("background-image","url(images/snow1.JPG)");},0);
				}
				else if(stage5){
					$("#life").html(life);

					window.setTimeout(function(){frames =  setInterval(draw, 20);prepause = false;$("#canvas").css("background-image","url(images/castle1.JPG)");},0);
				}
			}
			
		} 	
	}

}
}


//mouse and keyboard

function keyDown(key){
	if (key.keyCode == 39) rightDown = true; 
	else if (key.keyCode == 37) leftDown = true;
	else if (key.keyCode == 32) {
		initPause();
	}

}	

function initPause()
{
	if(!gameOver && !prepause){
		if(!pause) {
			clearInterval(frames);
			pause = true;
			$("#announce").html("pause");
			$("#announce").show();
			pauseAnimate();
		}else if(pause&&boss==false)
		{
			frames = setInterval(draw, 20);
			pause = false;
			$("#announce").hide();
		}
		else if(pause&&boss==true)
		{
			frames = setInterval(bossStage, 20);
			pause = false;
			$("#announce").hide();
			pauseAnimate();
		}
	}
}


function keyUp(key){
	if (key.keyCode == 39) rightDown = false;
	else if (key.keyCode == 37) leftDown = false;
	else if(key.keyCode == 67){
		bricksclear();
		setTimeout(stopbricksclear, 550);
		if(boss==true)
			koopaHealth = koopaHealth-100;
	}
}

var CHEATevent;
// bricks=[];
// console.log(bricks);
function bricksclear(){
	var brick_clear_rows = 0;
	var brick_clear_cols = 0;
	function cleartimeevent() {
		if(brick_clear_rows==n_brick_rows || brick_clear_cols==n_brick_cols)
			return;
		if(brick_clear_rows < n_brick_rows && brick_clear_cols < n_brick_cols)
		{
			if(bricks[brick_clear_rows][brick_clear_cols]  && bricks[brick_clear_rows][brick_clear_cols] != 0){
				bricks[brick_clear_rows][brick_clear_cols] = 0;
			}
			if(brick_clear_cols >= n_brick_cols-1){
				brick_clear_cols = 0;
				brick_clear_rows=brick_clear_rows+1;
			}
			else{
				brick_clear_cols=brick_clear_cols+1;
			}

		}
	}
	CHEATevent = setInterval(cleartimeevent, 10);
}
function stopbricksclear(){
	clearInterval(CHEATevent);
}

function init_mouse() {
	canvasStart = $("#canvas").offset().left;
	canvasMinX = canvasStart - (paddlew*0.2);
	canvasMaxX = canvasStart + WIDTH - (paddlew*0.2);
}

function onMouseMove(evt) 
{
	init_mouse();
	if (evt.pageX > canvasMinX && evt.pageX < canvasMaxX) 
	{
		paddlex = evt.pageX - canvasStart;

	}
}


//animations

function startAnimate()
{
	
	$('#canvas').css({height: 550, width: 850, opacity: 0});
	$('.sidebar').css({right:150, opacity:0})
	$('#canvas').animate({width: "990px", height: "600px", opacity: 1}, 0);
	$('.sidebar').delay(250).animate({right: "0px", opacity: 1}, 550);

}

function pauseAnimate(){
	
	if(pause){
		$('#announce').animate( {top : 230}, 350, function(){
			$('#announce').animate( {top : 235}, 350, function(){

				pauseAnimate();

			});
			
			
		});
	}
}

function isEmpty(){
	for(ball in balls){
		return false;
	}
	return true;
}


// 보스 부분

function bossStage()
{
	clear();
	drawBalls();
	paddle();
	drawBoss();
	boss_bounce();
	boss_Tokens();
}


function boss_Tokens()
{
	for(token in tokenLetters)
	{
		tokenSpeed = 10;
		//hit bottom
		if(tokenLetters[token].y > HEIGHT) {delete tokenLetters[token]; break;}
		//collect
		if((tokenLetters[token].y + tokenSpeed > (HEIGHT - paddleh)) && (tokenLetters[token].x - 5 > paddlex) && (tokenLetters[token].x + 5 < paddlex + paddlew))
		{
			switch(tokenLetters[token].type)
			{
				case "images/fire_down1.png":
				if(life==1)
				{
					delete balls[ball];
					if(isEmpty())
					{
						clearInterval(frames);
						$("#announce").html("game over");
						$("#announce").show();
						clearInterval(change_image);
						//bgm.pause();
						gameOver=true;
						mySound.Stop();
						mySound.Play('gameover', 'ef');
						ballitem = false;
						paddleitem = 0;
						lifeitem = 0;
						boss = false;
						stage5 = false;
						$("#announce").fadeOut(5000);
						setTimeout(function(){$("#canvas").hide();$(".intro").show();}, 3000);
					}		
				}
				//생명이 1이 아닐때는 life--하고 게임 재개하는 부분
				else
				{
					life--;			
					clearInterval(frames);
					for(ball in balls)
					{
						delete balls[ball]; 
					}
					balls = { 0 : {x : 100, y : 300, dx : 12, dy: 6 }};
					fastDuration = 0;
					paddleShrinkDuration = 0;
					paddleWidenDuration = 0;
					
					fast = false;
					star = false;

					gameOver = false;
					pause = false;
					prepause = true;
					lostCount = 0;
					bossHitDuration=0;
					$("#announce").html("Life -1!");
					$("#announce").show().delay(1000).fadeOut();
					$('#score').html(score);
					$("#life").html(life);
					delete tokenLetters[token];
					window.setTimeout(function(){frames =  setInterval(bossStage, 20);prepause = false;$("#canvas").css("background-image","url(images/bg-castle.png)");},1200);
					break;
				}
			}
		}
		//아이템 그리기
		if(tokenLetters[token])
		{
			var tokenX = tokenLetters[token].x;
			var tokenY = tokenLetters[token].y += tokenSpeed;
			var type = tokenLetters[token].type;
			var img = new Image();
			if(type!=null)
				img.src = type;
			img.height=200;
			img.width=200;
			context.drawImage(img,tokenX,tokenY);
		}
	}
}


function boss_bounce()
{
	function bossHit()
	{
		koopaHealth -= 40;
		bossHitDuration+=20;
		score += 40;
		$('#score').html(score);
	}
	if(bossTick==0)
	{
		for(tokens in tokenLetters)
		{
			delete tokenLetters[token]; 
		}
		bossTick++;
	}
	var random = Math.random();
 	if(random < fireProb) //0.005
 	{
 		tokenLetters["fire-"+fireIndex] = new Object;
 		var newToken = tokenLetters["fire-"+fireIndex];
 		newToken["y"] = 0;
 		newToken["x"] = Math.random() * (990);
 		newToken["color"] = 0;
 		random = Math.random(); 
 		newToken["type"] = "images/fire_down1.png";
 		fireIndex++;
 	}
 	random = Math.random();
 	if(random < debuffProb)
 	{
 		var foo = Math.random();
 		if(foo <0.5)
 		{
 			mySound.Play('bad_token', 'ef');
 			score -=100;
 			$('#score').html(score);
 			if(!fastDuration){
 				for(ball in balls){
 					balls[ball].dx *= 1.75;	
 					balls[ball].dy *= 1.75;
 				}
 				fast = true;
 			}
 			fastDuration += 100;
 			$("#debuff").html("속도 디버프 적용!");
 			$("#debuff").show().delay(2000).fadeOut();
 		}
 		else
 		{
 			mySound.Play('bad_token', 'ef');
 			score -=100;
 			$('#score').html(score);
 			if(!paddleShrinkDuration) paddlex += 70;
 			paddleShrinkDuration += 150;
 			paddlePrevousToken = paddleLastToken;
 			$("#debuff").html("패들 길이 디버프 적용!");
 			$("#debuff").show().delay(3000).fadeOut();
 		}
 	}
	for(ball in balls)
	{
		var y = balls[ball].y;
		var x = balls[ball].x;	
		var dx = balls[ball].dx;	
		var dy = balls[ball].dy;	
		if((y < (HEIGHT-paddleh)) &&hasHitBall==true)
		{
			$(document).unbind();
			$(document).keydown(keyDown);
			$(document).keyup(keyUp);
			$(document).mousemove(onMouseMove);
			hasHitBall=false;
		}
	}
	//x=435, y=100에 120*150의 크기
	var brick_x_l = 420; //left coordinate of brick x;
	var brick_x_r = 525; // right coordinate of brick x;
	var brick_y_b = 250; //bottom coordinate of brick y;
	var brick_y_t =  100; //top coordinate of brcik y;
	// var brick_edge = brick_height *0.4; 
	var tokenY = brick_y_b;
	var tokenX = 435; //center x coordinate of brick;
	for(ball in balls)
	{
		var y = balls[ball].y;
		var x = balls[ball].x;	
		var dx = balls[ball].dx;	
		var dy = balls[ball].dy;	

		//directions
		var  ballDirection;
		
		if((dx<0) && (dy<0)) ballDirection = "up left";
		if((dx>0) && (dy<0)) ballDirection = "up right";
		if((dx<0) && (dy>0)) ballDirection = "down left";
		if((dx>0) && (dy>0)) ballDirection = "down right"; 
		if(!star)
		{
			if( ballDirection == "up left" && ((y + dy - radius) <= brick_y_b) && ((y + dy + radius) >= brick_y_t) && ( ((x + dx - radius) >= brick_x_l) && ( (x + dx - radius) <= brick_x_r) )  )
			{
				if((y-radius < brick_y_b) )
				{
				 balls[ball].dx = -(dx); 
				}
				else
				{
					balls[ball].dy = -(dy);
				}
				bossHit();
			}
			else if( ballDirection == "down left" && ((y + dy + radius) >= (brick_y_t)) && ((y + dy - radius) <= brick_y_b) && ( ((x + dx - radius) >= brick_x_l) && ( (x + dx - radius) <= brick_x_r ) )  )
			{
				if( (y+radius > (brick_y_t))  )
				{
				 balls[ball].dx = -(dx); 
				}
				else
				{
					balls[ball].dy = -(dy);
				}
				bossHit();

			}
			else if( ballDirection == "up right" && ((y + dy - radius) <= brick_y_b) && ((y + dy + radius) >= brick_y_t) && ( ((x + dx + radius) >= brick_x_l) && ( (x + dx + radius) <= brick_x_r ) )  )
			{
				if((y-radius < brick_y_b) )
				{
				 balls[ball].dx = -(dx); 
				}
				else
				{
					balls[ball].dy = -(dy);
				}
				bossHit();

			}
			else if( ballDirection == "down right" && ((y + dy + radius) >= brick_y_t) && ((y + dy - radius) <= brick_y_b) && ( ((x + dx + radius) >= brick_x_l) && ( (x + dx + radius) < brick_x_r ) )  )
			{
				if( (y+radius > (brick_y_t)) )
				{
				 balls[ball].dx = -(dx); 
				}
				else
				{
					balls[ball].dy = -(dy);
				}
				bossHit();
			}
		}
	}
		 //walls
		 for(ball in balls)
		 {
		 	var y = balls[ball].y;
		 	var x = balls[ball].x;	
		 	var dx = balls[ball].dx;	
		 	var dy = balls[ball].dy;


		 	if ((x + dx + radius) > WIDTH || (x + dx - radius) < 0)
		 	{
		 		balls[ball].dx = -(dx);
		 		mySound.Play('wall', 'ef');
		 	}

		 	if((y + dy - radius) < 0)
		 	{
		 		balls[ball].dy = -(dy);
		 		mySound.Play('wall', 'ef');
		 	}


		//paddle
		if ((y + dy > (HEIGHT-radius-paddleh)) && (lostCount == 0))
		{
			if( ((x+dx) > (paddlex-(radius*2))) && ((x+dx) < (paddlex + paddlew+(radius*2)) ))
			{
	   			 //below surface or on edge
	   			 if( ((HEIGHT-y-dy) < paddleh) || ( (x+dx) < ((paddlex + (paddlew*0.1))+(radius*2)) ) || (  (x+dx) > (paddlex + (paddlew*0.9)-(radius*2))   ) ) 
	   			 {
					 //considering current movement
					 if( ( ((rightDown) && (dx > 0)) || ((leftDown) && (dx < 0)) ) && hasHitBall ==false)
					 {
					 	balls[ball].dy = -(dy);
					 	mySound.Play('paddle', 'ef');
					 	$(document).unbind();
						rightDown =false;
					 	leftDown=false;
						hasHitBall=true;
					 }
					 else
					 {
					 	if(hasHitBall==false)
					 	{
						 	balls[ball].dy = -(dy);
						 	balls[ball].dx = -(dx);
						 	mySound.Play('paddle', 'ef');
						 	$(document).unbind();
						 	rightDown =false;
					 		leftDown=false;
					 		hasHitBall=true;
					 	}
					 }

				}
				else 
				{
					balls[ball].dy = -(dy);
					mySound.Play('paddle', 'ef');

				}
			}
		}
		//lost
		if(y > (HEIGHT+radius))
		{
			//생명이 1일때 죽엇을 때 게임 오버. 게임 오버 음악과 메세지 출력하는 함수.
			if(life==1)
			{
				delete balls[ball];
					if(isEmpty())
					{
						life--;
						$("#life").html(life);
						clearInterval(frames);
						$("#announce").html("game over");
						$("#announce").show();
						clearInterval(change_image);
						//bgm.pause();
						gameOver=true;
						mySound.Stop();
						mySound.Play('gameover', 'ef');
						ballitem = false;
						paddleitem = 0;
						lifeitem = 0;
						boss = false;
						stage5 = false
						$("#announce").fadeOut(5000);
						setTimeout(function(){$("#canvas").hide();$(".intro").show();mySound.Stop();mySound.Play('intro','bg');}, 3000);
					}		
			}
			//생명이 1이 아닐때는 life--하고 게임 재개하는 부분
			else
			{
				life--;
				if(star)
				{
					star=false;
					mySound.Mute();
					mySound.playBG();
				}				
				clearInterval(frames);
				for(ball in balls)
				{
					delete balls[ball]; 
				}
				for(token in tokenLetters)
				{
					delete tokenLetters[token]; 
				}
				balls = { 0 : {x : 100, y : 300, dx : 12, dy: 9 }};
				// $("#canvas").css("background-image","none");
				fastDuration = 0;
				paddleShrinkDuration = 0;
				paddleWidenDuration = 0;
				
				fast = false;
				star = false;

				gameOver = false;
				pause = false;
				prepause = true;
				lostCount = 0;
				bossHitDuration=0;
				$("#announce").html("Life -1!");
				$("#announce").show().delay(1000).fadeOut();
				$('#score').html(score);
				$("#life").html(life);
				window.setTimeout(function(){frames =  setInterval(bossStage, 20);prepause = false;$("#canvas").css("background-image","url(images/bg-castle.png)");},1200);
			}
			
		} 	
	}
}

function drawBoss()
{
	if(bossHitDuration>=10 && koopaMove==0)
	{
		koopaImage.src="images/Koopa1_d2.png";
		bossHitDuration--;
	}
	else if(bossHitDuration >0 && koopaMove==0)
	{
		koopaImage.src="images/Koopa1_d1.png";
		bossHitDuration--;
	}
	else if(bossHitDuration ==0)
		koopaImage.src="images/Koopa1.png";
	var random = Math.random();
	if(random < 0.0015 && koopaMove ==0)
	{
		koopaMove+=120;
	}
	if(koopaMove >80)
	{
		koopaImage.src="images/Koopa1.png";
		koopaMove--;
	}
	else if(koopaMove <=80 && koopaMove > 40)
	{
		koopaImage.src="images/Koopa2.png";
		koopaMove--;
	}
	else if(koopaMove <=40 && koopaMove>0)
	{
		koopaImage.src="images/Koopa3.png";
		koopaMove--;	
	}
	context.drawImage(
		koopaImage, WIDTH/2-60, 100, 120, 150);
	context.lineWidth=4;
	context.strokeStyle="#333";
	if(koopaHealth>600)
		context.fillStyle="Green";
	else if(koopaHealth<=600 && koopaHealth >400)
	{
		context.fillStyle="#73A230";
		debuffProb=0.0025;
	}
	else if(koopaHealth<=400 && koopaHealth >200)
	{
		context.fillStyle="#E0DD48";
		fireProb=0.01;
		debuffProb=0.005;
	}
	else if(koopaHealth<=200 && koopaHealth>100)
	{
		context.fillStyle="#E56E2C";
		fireProb=0.02;
		debuffProb=0.01;
	}
	else if(koopaHealth<=100 && koopaHealth >0)
	{
		context.fillStyle="#D42E20";
		fireProb=0.04;
		debuffProb=0.02;
	}
	else if(koopaHealth<=0)
	{
		koopaHealth = 0;
		context.fillStyle="#D42E20";
		boss = false;
		stage5 = false;
		clearInterval(frames);
		clearInterval(change_image);
		gameOver=true;
		$("#announce").html("You won!");
		$("#announce").show();
		//bgm.pause();
		//bossBGM.pause();
		ballitem = false;
		paddleitem = 0;
		lifeitem = 0;
		for(ball in balls){
			delete balls[ball];  
			gameOver=true;
		}
		$("#announce").fadeOut(3000);
		mySound.Stop();
		mySound.Play('intro', 'bg');
		// 엔딩 이벤트
		setTimeout(ending, 3000);
		function ending() {
			$("#endingBox").attr("top", "0");
			$(".intro").hide();
			$(".ending").show();

			$("#endingBox").animate({
				top: "-4000px"
			}, 40000);
			function changeEnd(){
				$(".ending").hide();
				$("#canvas").hide();
				$(".intro").show();
				$(".lobby").show();
				mySound.Stop();
				mySound.Play('intro', 'bg');
	
				$("#endingBox").stop();
				$("#endingBox").css({
				   top: '0',
				});
				$(".ending").hide();
	
			 }
			var myEnd = setTimeout(changeEnd, 28000);
		}

	}
	context.fillRect(90,20,koopaHealth,25);
	context.strokeRect(90,20,800,25);
	context.drawImage(healthBar,55, 4);
}


function showMessages(elm, message)
{
	if(index+1==bossMessages.length)
	{
		index = -1;
		gameOver=false;
		return;
	}
	index++;
	elm.html(message);
	var m=bossMessages[index+1];
	if(index==0)
	{
		elm.show().delay(5000).fadeOut(1000, function() {
			showMessages(elm,m);
		});
	}
	else if(index ==1)
	{
		elm.show().delay(2000).fadeOut(1000, function() {
			mySound.Play('bossMusic', 'bg');
			showMessages(elm,m);
		});
	}
	else 
	{
		elm.show().delay(3000).fadeOut(1000, function() {
			showMessages(elm,m);
		});
	}
}







