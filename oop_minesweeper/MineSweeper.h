#pragma once
#include <iostream>
#include <string>
#include <ncurses.h>
#include <vector>
#include <chrono>
#include <sstream>
#include <thread>
#include <filesystem>
#include <fstream>

using namespace std;

class MineSweeper
{
private:
    vector<string> movesMade;
    string title;
    int isPlaying;
    string saveFileName="";
    string** msMap;
    string** tempMap;
    int** numMines;
    string** mines;
    bool replay=false;
    bool gameOver=false;
    int difficulty;
    int remainingMines;
    int initRM;
    int row;
    int col;
public:
    MineSweeper() = delete;
    ~MineSweeper();
    MineSweeper(const string& title);
    void start();
    void draw_borders(WINDOW* screen);
    void makeMines();
    void setDifficulty(WINDOW* win, int parent_x, int parent_y);
    void initMap(WINDOW* win, int parent_x, int parent_y);
    void playGame(WINDOW* win, int parent_x, int parent_y);
    void showReplay(WINDOW* win, int parent_x, int parent_y);
    void revealMap(int x, int y);
    bool isValid(int r, int c);
    bool isEmpty(int r, int c);
};
