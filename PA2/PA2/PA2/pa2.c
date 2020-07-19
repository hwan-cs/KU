#include <stdio.h>
#include <conio.h>
#include <stdlib.h>
#include <Windows.h>
#include <stdbool.h>
#include "easy_mode.h"
#include "normal_mode.h"
#include "difficult_mode.h"
#include "bonus_stage.h"
#define ROW 20
#define COL 20
#define UP 72
#define DOWN 80
#define LEFT 75
#define RIGHT 77
#define PLAYER_MARK "O"

static char map[20][20] = { {'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#'},
							{'#',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','#'},
							{'#',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','#'},
							{'#',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','#'},
							{'#',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','#'},
							{'#',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','#'},
							{'#',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','#'},
							{'#',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','#'},
							{'#',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','#'},
							{'#',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','#'},
							{'#',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','#'},
							{'#',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','#'},
							{'#',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','#'},
							{'#',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','#'},
							{'#',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','#'},
							{'#',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','#'},
							{'#',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','#'},
							{'#',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','#'},
							{'#',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','#'},
						    {'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#'} };

static int treasure[2][20];

static int record[50];

void gotoxy(int x, int y);
void loadmap();
int highestScore(int n[], int num);
static bool win;
static bool temp = false;
static bool bRounds = false;
static int counter;
static int pos_x, pos_y;
static int diff = 0;
static	int rand1 = 0, rand2 = 0, rand3 = 0;
static char yorn;
static int moves;
static int score = 0;
static int rounds = 0;

int main()
{
	srand((unsigned)time(NULL));
	gotoxy(50, 50);
	do
	{
		yorn = 'N';
		win = false;
		if (diff == 0)
		{
			printf("난이도를 선택하세요: \n");
			printf("쉬움: 1\n");
			printf("중간: 2\n");
			printf("어려움: 3\n");
			printf("난이도 입력: ");
			scanf("%d", &diff);
		}
		switch (diff)
		{
		case(1):
			system("cls");
			printf("맵 생성중...");
			Sleep(1000);
			system("cls");
			moves = 60;
			counter = 90;
			flag_easy(&map, &treasure);
			loadmap();
			easyRandNum(&rand1, &rand2, &rand3);
			break;
		case(2):
			system("cls");
			if (temp)
				printf("다음 단계로 넘어가는 중...");
			else
				printf("맵 생성중...");
			Sleep(1000);
			system("cls");
			temp = false;
			moves = 40;
			counter = 60;
			flag_normal(&map, &treasure);
			loadmap();
			normalRandNum(&rand1, &rand2, &rand3);
			break;
		case(3):
			system("cls");
			if (temp)
				printf("마지막 단계로 넘어가는 중...");
			else
				printf("맵 생성중...");
			Sleep(1000);
			system("cls");
			temp = false;
			moves = 40;
			counter = 60;
			flag_difficult(&map, &treasure);
			loadmap();
			difficultRandNum(&rand1, &rand2, &rand3);
			break;
		case(4):
			system("cls");
			printf("보너스 스테이지로 넘어가는 중...");
			Sleep(1000);
			system("cls");
			moves = 100;
			counter = 10;
			flag_bonus(&map, &treasure);
			loadmap();
			break;
		}
		pos_x = 1, pos_y = 22;
		gotoxy(pos_x, pos_y);
		printf(PLAYER_MARK);
		if (rounds >= 1 &&  diff<4)
		{
			gotoxy(25, 1);
			printf("현재까지 최고 점수: %d", highestScore(record, rounds));
			gotoxy(25, 2);
			printf("여태까지 모든 점수: \n");
			for (int i = 0; i < rounds; i++)
			{
				gotoxy(25, 3+i);
				printf("%d", record[i]);
			}
		}
		do
		{
			gotoxy(3, 0);
			printf("현재 점수: %d", score);
			gotoxy(3, 1);
			printf("                    \b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b");
			printf("타이머: %d초\n", counter);
			printf("                    \b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b");
			printf("남은 이동 횟수: %d", moves);
			Sleep(1000);
			counter--;
			gotoxy(pos_x, pos_y);
			while (_kbhit())
			{
				char ch = _getch();
				switch (ch)
				{
				case(UP):
					moves--;
					if (map[pos_y - 5][pos_x] == '#')
					{
						gotoxy(4, 12);
						printf("이곳은 지나갈\n    수 없습니다");
						Sleep(1000);
						loadmap();
						break;
					}
					else if (map[pos_y - 5][pos_x] == 'H')
					{
						gotoxy(4, 12);
						printf("장애물은 지나갈\n    수 없습니다");
						Sleep(1000);
						loadmap();
						break;
					}
					printf(" ");
					pos_y--;
					break;
				case(DOWN):
					moves--;
					if(map[pos_y - 3][pos_x] == '#')
					{
						gotoxy(4, 12);
						printf("이곳은 지나갈\n    수 없습니다");
						Sleep(1000);
						loadmap();
						break;
					}
					else if (map[pos_y - 3][pos_x] == 'H')
					{
						gotoxy(4, 12);
						printf("장애물은 지나갈\n    수 없습니다");
						Sleep(1000);
						loadmap();
						break;
					}
					printf(" ");
					pos_y++;
					break;
				case(LEFT):
					moves--;
					if (map[pos_y - 4][pos_x-1] == '#')
					{
						gotoxy(4, 12);
						printf("이곳은 지나갈\n    수 없습니다");
						Sleep(1000);
						loadmap();
						break;
					}
					else if (map[pos_y - 4][pos_x-1] == 'H')
					{
						gotoxy(4, 12);
						printf("장애물은 지나갈\n    수 없습니다");
						Sleep(1000);
						loadmap();
						break;
					}
					printf(" ");
					pos_x--;
					break;
				case(RIGHT):
					moves--;
					if (map[pos_y - 4][pos_x+1] == '#')
					{
						gotoxy(4, 12);
						printf("이곳은 지나갈\n    수 없습니다");
						Sleep(1000);
						loadmap();
						break;
					}
					else if (map[pos_y - 4][pos_x+1] == 'H')
					{
						gotoxy(4, 12);
						printf("장애물은 지나갈\n    수 없습니다");
						Sleep(1000);
						loadmap();
						break;
					}
					printf(" ");
					pos_x++;
					break;
				}
			}
			gotoxy(pos_x, pos_y);
			printf(PLAYER_MARK);
			if (map[pos_y - 4][pos_x] == '$')
			{
				map[pos_y - 4][pos_x] = 'X';
				if (diff == 1)
				{
					if (rand1 == rand() % 3)
					{
						int num1 = rand() % 10;
						int num2 = rand() % 10;
						while (map[treasure[0][num1]][treasure[1][num1]] == '*' ||
							map[treasure[0][num1]][treasure[1][num1]] == 'X' || num1 == num2)
						{
							num1 = rand() % 10;
						}
						while (map[treasure[0][num2]][treasure[1][num2]] == '*' ||
							map[treasure[0][num2]][treasure[1][num2]] == 'X' || num1==num2)
						{
							num2 = rand() % 10;
						}
						map[treasure[0][num1]][treasure[1][num1]] = '*';
						map[treasure[0][num2]][treasure[1][num2]] = '*';
						loadmap();
					}
					else if (rand2 == rand() % 4 || rand3 == rand() % 4)
					{
						gotoxy(4, 13);
						printf("   보물을 \n   찾으셨습니다!");
						win = true;
						temp = true;
						score = score + 100;
						Sleep(5000);
						system("cls");
						diff++;
						break;
					}
					else
					{
						pos_x = 1, pos_y = 22;
						gotoxy(pos_x, pos_y);
						printf(PLAYER_MARK);
					}
				}
				else if (diff == 2)
				{
					if (rand1 == rand() % 3)
					{
						int num1 = rand() % 10;
						int num2 = rand() % 10;
						while (map[treasure[0][num1]][treasure[1][num1]] == '*' ||
							map[treasure[0][num1]][treasure[1][num1]] == 'X' || num1 == num2)
						{
							num1 = rand() % 10;
						}
						while (map[treasure[0][num2]][treasure[1][num2]] == '*' ||
							map[treasure[0][num2]][treasure[1][num2]] == 'X' || num1 == num2)
						{
							num2 = rand() % 10;
						}
						map[treasure[0][num1]][treasure[1][num1]] = '*';
						map[treasure[0][num2]][treasure[1][num2]] = '*';
						loadmap();
					}
					else if (rand2 == rand() % 8 || rand3 == rand() % 8)
					{
						gotoxy(4, 13);
						printf("   보물을 \n   찾으셨습니다!");
						win = true;
						temp = true;
						score = score + 200;
						Sleep(5000);
						system("cls");
						diff++;
						break;
					}
					else
					{
						pos_x = 1, pos_y = 22;
						gotoxy(pos_x, pos_y);
						printf(PLAYER_MARK);
					}
				}
				else if (diff == 3)
				{
					if (rand1 == rand() % 4)
					{
						int num1 = rand() % 20;
						int num2 = rand() % 20;
						while (map[treasure[0][num1]][treasure[1][num1]] == '*' ||
							map[treasure[0][num1]][treasure[1][num1]] == 'X' || num1 == num2)
						{
							num1 = rand() % 20;
						}
						while (map[treasure[0][num2]][treasure[1][num2]] == '*' ||
							map[treasure[0][num2]][treasure[1][num2]] == 'X' || num1 == num2)
						{
							num2 = rand() % 20;
						}
						map[treasure[0][num1]][treasure[1][num1]] = '*';
						map[treasure[0][num2]][treasure[1][num2]] = '*';
						loadmap();
					}
					else if (rand2 == rand() % 10)
					{
						gotoxy(4, 13);
						printf("   보물을 \n   찾으셨습니다!");
						win = true;
						diff++;
						score = score + 500;
						Sleep(5000);
						system("cls");
						if (score >= 800)
						{
							rounds++;
							bRounds = true;
						}
						break;
					}
					else if (rand3 == rand() % 5)
						moves = moves + 5;
					else
					{
						pos_x = 1, pos_y = 22;
						gotoxy(pos_x, pos_y);
						printf(PLAYER_MARK);
					}
				}
				else if (diff >= 4)
				{
					win = true;
					gotoxy(4, 13);
					printf("   보물을 \n   찾으셨습니다!");
					score = score + 100;
					Sleep(500);
					system("cls");
					diff++;
				}
				loadmap();
			}
			else if (map[pos_y - 4][pos_x] == '*')
			{
				score = score + 50;
				map[pos_y - 4][pos_x] = 'X';
				loadmap();
			}
		}while (counter >= 1 && moves > 0);
		if (!win)
		{
			gotoxy(7, 14);
			printf("실패! \n");
			Sleep(5000);
			system("cls");
			printf("   다시 하시겠습니까? \n  (Y/N):\n");
			yorn = _getch();
		}
		if (diff > 4)
		{
			system("cls");
			gotoxy(15, 15);
			printf("최종 점수는: %d 점", score);
			Sleep(5000);
			system("cls");
			diff = 0;
			if(bRounds)
				record[rounds - 1] = score;
			score = 0;
			printf("   다시 하시겠습니까? \n  (Y/N):\n");
			yorn = _getch();
		}
	}while (yorn == 'Y' || win); 
}
void gotoxy(int x, int y)
{
	COORD Cur = { x,y };
	SetConsoleCursorPosition(GetStdHandle(STD_OUTPUT_HANDLE), Cur);
}

void loadmap()
{
	gotoxy(0, 4);
	for (int r = 0; r < ROW; r++)
	{
		for (int c = 0; c < COL; c++)
		{
			printf("%c", map[r][c]);
		}
		printf("\n");
	}
}
int highestScore(int n[], int num)
{
	int max;
	max = n[0];
	for (int i = 0; i < num; i++)
	{
		if (n[i + 1] > max)
			max = n[i + 1];
	}
	return max;
}

