#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <time.h>
#include <windows.h>

void flag_easy(char (*temp)[20][20], int (*num)[2][20])
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
	for (int i = 0; i < 10; i++)
	{
		int xrand = rand() % 18 +1;
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
}

void easyRandNum(int* a, int* b, int* c)
{
	srand((unsigned)time(NULL));
	*a = rand() % 3;
	*b = rand() % 4;
	*c = rand() % 4;
	while (*a == *b || *b == *c || *a == *c)
	{
		*a = rand() % 3;
		*b = rand() % 4;
		*c = rand() % 4;
	}
}