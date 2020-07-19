#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <time.h>
#include <windows.h>

void flag_difficult(char(*temp)[20][20], int(*num)[2][20])
{
	srand((unsigned)time(NULL));
	for (int r = 0; r < 20; r++)
	{
		for (int c = 0; c < 20; c++)
		{
			if ((*temp)[r][c] == '$' || (*temp)[r][c] == '*' || (*temp)[r][c] == 'X' || (*temp)[r][c] == 'H')
				(*temp)[r][c] = ' ';
		}
	}
	for (int i = 0; i < 20; i++)
	{
		int xrand = rand() % 18 + 1;
		int yrand = rand() % 18 + 1;
		while (xrand == 18 && yrand == 1)
		{
			xrand = rand() % 18 + 1;
			yrand = rand() % 18 + 1;
		}
		(*temp)[xrand][yrand] = '$';
		(*num)[0][i] = xrand;
		(*num)[1][i] = yrand;
	}
	for (int n = 0; n < 7; n++)
	{
		int x = rand() % 18 + 1;
		int y = rand() % 18 + 1;
		while (1)
		{
			if ((*temp)[x][y] == ' ' && (*temp)[x + 1][y] == ' ' && (*temp)[x+2][y] == ' '
				&& (*temp)[x + 3][y] == ' ')
			{
				(*temp)[x][y] = 'H';
				(*temp)[x + 1][y] = 'H';
				(*temp)[x + 2][y] = 'H';
				(*temp)[x + 3][y] = 'H';
				break;
			}
			else if ((*temp)[x][y] == ' ' && (*temp)[x - 1][y] == ' ' && (*temp)[x-2][y] == ' '
				&& (*temp)[x - 3][y] == ' ')
			{
				(*temp)[x][y] = 'H';
				(*temp)[x - 1][y] = 'H';
				(*temp)[x - 2][y] = 'H';
				(*temp)[x - 3][y] = 'H';
				break;
			}
			else if ((*temp)[x][y] == ' ' && (*temp)[x][y + 1] == ' '&& (*temp)[x][y+2] == ' '
				&& (*temp)[x][y + 3] == ' ')
			{
				(*temp)[x][y] = 'H';
				(*temp)[x][y + 1] = 'H';
				(*temp)[x][y + 2] = 'H';
				(*temp)[x][y + 3] = 'H';
				break;
			}
			else if ((*temp)[x][y] == ' ' && (*temp)[x][y - 1] == ' '&& (*temp)[x][y-2] == ' '
				&& (*temp)[x][y - 3] == ' ')
			{
				(*temp)[x][y] = 'H';
				(*temp)[x][y - 1] = 'H';
				(*temp)[x][y - 2] = 'H';
				(*temp)[x][y - 3] = 'H';
				break;
			}
			else
			{
				x = rand() % 18 + 1;
				y = rand() % 18 + 1;
			}
		}
	}
}

void difficultRandNum(int* a, int* b, int* c)
{
	srand((unsigned)time(NULL));
	*a = rand() % 4;
	*b = rand() % 10;
	*c = rand() % 5;
	while (*a == *b || *b == *c || *a == *c)
	{
		*a = rand() % 4;
		*b = rand() % 10;
		*c = rand() % 5;
	}
}