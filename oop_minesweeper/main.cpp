#include <iostream>
#include "MineSweeper.h"
using namespace std;

//go to terminal, cd to project dir, do g++ -std=c++11 main.cpp MineSweeper.cpp -lncurses, leaks -atExit -- ./a.out
int main()
{
    MineSweeper foo("202011298 박정환");
    foo.start();
    return 0;
}
