#include "MineSweeper.h"
#include <locale.h>
#include <filesystem>
#include <sstream>
#include <list>
#include <vector>
#include <memory>
#include <fstream>

using namespace std;
using std::__fs::filesystem::directory_iterator;
namespace fs = std::__fs::filesystem;
const string PATH = std::__fs::filesystem::current_path().u8string()+"/saved";

MineSweeper::MineSweeper(const string& title)
    :title(title), row(0), col(0)
{
    
}
MineSweeper:: ~MineSweeper()
{
    if(msMap!=nullptr)
    {
        for(int i =0;i<row;i++)
        {
            delete [] msMap[i];
            delete [] numMines[i];
            delete [] mines[i];
            delete [] tempMap[i];
        }
        delete [] msMap;
        delete [] numMines;
        delete [] mines;
        delete [] tempMap;
        msMap=nullptr;
        numMines=nullptr;
        mines=nullptr;
        tempMap=nullptr;
    }
}
void MineSweeper::start()
{
    setlocale(LC_ALL, "");
    initscr();
    noecho();
    cbreak();
    int parent_y,parent_x;
    getmaxyx(stdscr, parent_y, parent_x);
    WINDOW* win= newwin(parent_y, parent_x, 0, 0);
    keypad(stdscr, TRUE);
    keypad(win, TRUE);
    mousemask(ALL_MOUSE_EVENTS | REPORT_MOUSE_POSITION, NULL);
    mouseinterval(0);
    
    draw_borders(win);
    mvwprintw(win, 2, (parent_x/2)-((float)title.length()/2), title.c_str());
    mvwprintw(win, (parent_y/2)-6, (parent_x/2)-((float)title.length()/2)-10, "지뢰찾기 게임에 온 것을 환영합니다!");
    mvwprintw(win, (parent_y/2)-4, (parent_x/2)-((float)title.length()/2)-10, "*********승리 조건*********");
    mvwprintw(win, (parent_y/2)-3, (parent_x/2)-((float)title.length()/2)-10, "우클릭하여 지뢰를 모두 표시하고 ");
    mvwprintw(win, (parent_y/2)-2, (parent_x/2)-((float)title.length()/2)-10, "지뢰밭의 모든 타일을 열면 승리합니다.");
    mvwprintw(win, (parent_y/2), (parent_x/2)-((float)title.length()/2)-10, "*********패배 조건*********");
    mvwprintw(win, (parent_y/2)+1, (parent_x/2)-((float)title.length()/2)-10, "지뢰를 좌클릭으로 열면");
    mvwprintw(win, (parent_y/2)+2, (parent_x/2)-((float)title.length()/2)-10, "게임에 패배합니다.");
    mvwprintw(win, (parent_y/2)+4, (parent_x/2)-((float)title.length()/2)-10, "y를 눌러 시작하세요: ");
    mvwprintw(win, (parent_y/2)+5, (parent_x/2)-((float)title.length()/2)-10, "n를 눌러 종료하세요: ");
    wrefresh(win);
    while((isPlaying = wgetch(win)) != 'y')
    {
        int new_y, new_x;
        if (is_term_resized(parent_y, parent_x) )
        {
            getmaxyx(stdscr, new_y, new_x);
            parent_x = new_x;
            parent_y = new_y;

            wresize(win, new_y, new_x);
            mvwin(win, new_y, 0);

            wclear(stdscr);
            wclear(win);
        }
        mvwprintw(win, 2, (parent_x/2)-((float)title.length()/2), title.c_str());
        mvwprintw(win, (parent_y/2)-6, (parent_x/2)-((float)title.length()/2)-10, "지뢰찾기 게임에 온 것을 환영합니다!");
        mvwprintw(win, (parent_y/2)-4, (parent_x/2)-((float)title.length()/2)-10, "*********승리 조건*********");
        mvwprintw(win, (parent_y/2)-3, (parent_x/2)-((float)title.length()/2)-10, "우클릭하여 지뢰를 모두 표시하고 ");
        mvwprintw(win, (parent_y/2)-2, (parent_x/2)-((float)title.length()/2)-10, "지뢰밭의 모든 타일을 열면 승리합니다.");
        mvwprintw(win, (parent_y/2), (parent_x/2)-((float)title.length()/2)-10, "*********패배 조건*********");
        mvwprintw(win, (parent_y/2)+1, (parent_x/2)-((float)title.length()/2)-10, "지뢰를 좌클릭으로 열면");
        mvwprintw(win, (parent_y/2)+2, (parent_x/2)-((float)title.length()/2)-10, "게임에 패배합니다.");
        mvwprintw(win, (parent_y/2)+4, (parent_x/2)-((float)title.length()/2)-10, "y를 눌러 시작하세요: ");
        mvwprintw(win, (parent_y/2)+5, (parent_x/2)-((float)title.length()/2)-10, "n를 눌러 종료하세요: ");
        draw_borders(win);
        wrefresh(win);
        if(isPlaying=='n')
            return;
        std::this_thread::sleep_for(std::chrono::milliseconds(50));
    }
    wclear(stdscr);
    wclear(win);
    draw_borders(win);
    mvwprintw(win, 2, (parent_x/2)-((float)title.length()/2), title.c_str());
    int count=0;
    string PATH=std::__fs::filesystem::current_path();
    PATH= PATH+"/saved";
    vector<string> filelist;
    
    //saved폴더가 없다면 새로 생성하기
    if(!fs::exists(PATH))
        fs::create_directory(PATH);
    
    bool fromfile=false;
    string str;
    int size;
    //saved폴더에 있는 항목 읽어오기
    if(!fs::is_empty(PATH))
    {
        for(const auto& entry:directory_iterator(PATH))
        {
            if(fs::path(entry.path()).filename()==".DS_Store")
                continue;
            count++;
            mvwprintw(win, count+10, (parent_x/2)-20, "%d.%s",count,fs::path(entry.path()).filename().c_str());
            filelist.push_back(entry.path().filename());
        }
        mvwprintw(win, count+11, (parent_x/2)-20, "%d.세이브 파일을 불러오지 않는다.",count+1);
        mvwprintw(win, 9, (parent_x/2)-((float)title.length()/2)-10, "불러올 세이브 파일을 선택하세요: ");
        wrefresh(win);
        while(1)
        {
            int new_y, new_x;
            if (is_term_resized(parent_y, parent_x) )
            {
                getmaxyx(stdscr, new_y, new_x);
                parent_x = new_x;
                parent_y = new_y;

                wresize(win, new_y, new_x);
                mvwin(win, new_y, 0);

                wclear(stdscr);
                wclear(win);
            }
            do
            {
                while(size!='\n')
                {
                    size = wgetch(win);
                    if(isdigit(size))
                    {
                        str.push_back(size);
                    }
                }
                if(stoi(str)>filelist.size()+1 || stoi(str)==0)
                {
                    mvwprintw(win, count+13, (parent_x/2)-((float)title.length()/2)-10, "올바르지 않은 입력!");
                    wrefresh(win);
                    std::this_thread::sleep_for(std::chrono::milliseconds(500));
                    mvwprintw(win, count+13, (parent_x/2)-((float)title.length()/2)-10, "                     ");
                    mvwprintw(win, count+13, (parent_x/2)-((float)title.length()/2)-10, "");
                    str="";
                    size=0;
                }
                else if(stoi(str)!=filelist.size()+1)
                {
                    if(fs::path(filelist.at(stoi(str)-1)).extension() != ".txt")
                    {
                        mvwprintw(win, count+13, (parent_x/2)-((float)title.length()/2)-10, ".txt파일을 선택하세요!");
                        wrefresh(win);
                        std::this_thread::sleep_for(std::chrono::milliseconds(500));
                        mvwprintw(win, count+13, (parent_x/2)-((float)title.length()/2)-10, "                      ");
                        mvwprintw(win, count+13, (parent_x/2)-((float)title.length()/2)-10, "");
                        str="";
                        size=0;
                    }
                    else
                    {
                        mvwprintw(win, count+13, (parent_x/2)-((float)title.length()/2)-10, "불러올 파일:%s",filelist.at(stoi(str)-1).c_str());
                        wrefresh(win);
                        std::this_thread::sleep_for(std::chrono::milliseconds(500));
                        mvwprintw(win, count+13, (parent_x/2)-((float)title.length()/2)-15, "                                                       ");
                        mvwprintw(win, count+13, (parent_x/2)-((float)title.length()/2)-15, "");
                        fromfile=true;
                        if(msMap!=nullptr)
                        {
                            if(row!=0)
                            {
                                for(int i =0;i<row;i++)
                                {
                                    delete [] msMap[i];
                                    delete [] numMines[i];
                                    delete [] mines[i];
                                    delete [] tempMap[i];
                                }
                                delete [] msMap;
                                delete [] numMines;
                                delete [] mines;
                                delete [] tempMap;
                                msMap=nullptr;
                                numMines=nullptr;
                                mines=nullptr;
                                tempMap=nullptr;
                            }
                            movesMade.clear();
                        }
                        saveFileName=filelist.at(stoi(str)-1);
                        ifstream f((PATH+"/"+filelist.at(stoi(str)-1)).c_str());
                        string s;
                        int line=0;
                        while(getline(f, s))
                        {
                            if(line==0)
                            {
                                string rc = "";
                                int count=0;
                                for (auto x : s)
                                {
                                    if (x == '\t')
                                    {
                                        rc = "";
                                        count++;
                                    }
                                    else
                                    {
                                        rc = rc + x;
                                        if(count==0)
                                            row=stoi(rc);
                                        else if(count==1)
                                            col=stoi(rc);
                                        else if(count==2)
                                        {
                                            remainingMines=stoi(rc);
                                            initRM=remainingMines;
                                        }
                                        else if(count==3)
                                            difficulty=stoi(rc);
                                    }
                                }
                                line++;
                            }
                            else if(line<row+1)
                            {
                                if(line==1)
                                {
                                    msMap = new string*[row];
                                    numMines = new int*[row];
                                    mines = new string*[row];
                                    tempMap= new string*[row];
                                    for(size_t r =0;r<row;r++)
                                    {
                                        msMap[r] = new string[col];
                                        numMines[r]= new int[col];
                                        mines[r]=new string[col];
                                        tempMap[r]=new string[col];
                                    }
                                }
                                for(int i=0;i<col;i++)
                                {
                                    if(s.length()==col)
                                    {
                                        if(string(1,s[i])=="R")
                                        {
                                            msMap[line-1][i]="■";
                                            tempMap[line-1][i]="■";
                                        }
                                        else if(string(1,s[i])=="F")
                                        {
                                            msMap[line-1][i]="□";
                                            tempMap[line-1][i]="□";
                                        }
                                        else
                                        {
                                            msMap[line-1][i]= string(1,s[i]);
                                            tempMap[line-1][i]=string(1,s[i]);
                                        }
                                    }
                                    else
                                    {
                                        msMap[line-1][i]="";
                                        tempMap[line-1][i]="";
                                    }
                                }
                                line++;
                            }
                            else if(line> row && line<(row*2)+1)
                            {
                                for(int i=0;i<col;i++)
                                {
                                    if(s.length()==col)
                                    {
                                        if(string(1,s[i])=="B")
                                            mines[line-row-1][i]="▲";
                                        else
                                            mines[line-row-1][i]=string(1,s[i]);
                                    }
                                    else
                                        mines[line-row-1][i]="";
                                }
                                line++;
                            }
                            else if((line> (row*2)) && (line<(row*3)+1))
                            {
                                for(int i=0;i<col;i++)
                                {
                                    if(s.length()==col)
                                        numMines[line-(row*2)-1][i]=stoi(string(1,s[i]));
                                    else
                                        numMines[line-(row*2)-1][i]=-1;
                                }
                                line++;
                            }
                        }
                        f.close();
                        if(row==0|| col==0||difficulty==0)
                        {
                            mvwprintw(win, count+13, (parent_x/2)-((float)title.length()/2)-10, "올바른 형식의 세이브 파일이 아닙니다!");
                            wrefresh(win);
                            std::this_thread::sleep_for(std::chrono::milliseconds(500));
                            mvwprintw(win, count+13, (parent_x/2)-((float)title.length()/2)-10, "                                          ");
                            mvwprintw(win, count+13, (parent_x/2)-((float)title.length()/2)-10, "");
                            str="";
                            size=0;
                            fromfile=false;
                        }
                        else
                        {
                            for(int r =0;r<row;r++)
                            {
                                for(int c =0;c<col;c++)
                                {
                                    if(msMap[r][c]==""||tempMap[r][c]==""||mines[r][c]==""||numMines[r][c]==-1)
                                    {
                                        mvwprintw(win, count+13, (parent_x/2)-((float)title.length()/2)-10, "올바른 형식의 세이브 파일이 아닙니다!");
                                        wrefresh(win);
                                        std::this_thread::sleep_for(std::chrono::milliseconds(500));
                                        mvwprintw(win, count+13, (parent_x/2)-((float)title.length()/2)-10, "                                        ");
                                        mvwprintw(win, count+13, (parent_x/2)-((float)title.length()/2)-10, "");
                                        saveFileName="";
                                        str="";
                                        size=0;
                                        fromfile=false;
                                    }
                                }
                            }
                        }
                    }
                }
            }while(str=="");
            mvwprintw(win, 9, (parent_x/2)-((float)title.length()/2)-10, "불러올 세이브 파일을 선택하세요: ");
            wrefresh(win);
            break;
        }
        std::this_thread::sleep_for(std::chrono::milliseconds(1000));
    }
    wclear(stdscr);
    wclear(win);
    if(!fromfile)
    {
        if(msMap!=nullptr)
        {
            if(row!=0)
            {
                for(int i =0;i<row;i++)
                {
                    delete [] msMap[i];
                    delete [] numMines[i];
                    delete [] mines[i];
                    delete [] tempMap[i];
                }
                delete [] msMap;
                delete [] numMines;
                delete [] mines;
                delete [] tempMap;
                msMap=nullptr;
                numMines=nullptr;
                mines=nullptr;
                tempMap=nullptr;
            }
            movesMade.clear();
        }
        saveFileName="";
        draw_borders(win);
        setDifficulty(win, parent_x, parent_y);
        wclear(stdscr);
        wclear(win);
        draw_borders(win);
        initMap(win, parent_x, parent_y);
        playGame(win, parent_x, parent_y);
    }
    else
    {
        std::this_thread::sleep_for(std::chrono::milliseconds(1000));
        wclear(stdscr);
        wclear(win);
        draw_borders(win);
        playGame(win, parent_x, parent_y);
    }
    endwin();
}
void MineSweeper::draw_borders(WINDOW* screen)
{
      int x, y, i;

      getmaxyx(screen, y, x);

      mvwprintw(screen, 0, 0, "+");
      mvwprintw(screen, y - 1, 0, "+");
      mvwprintw(screen, 0, x - 1, "+");
      mvwprintw(screen, y - 1, x - 1, "+");

      // sides
      for (i = 1; i < (y - 1); i++) {
        mvwprintw(screen, i, 0, "|");
        mvwprintw(screen, i, x - 1, "|");
      }

      // top and bottom
      for (i = 1; i < (x - 1); i++) {
        mvwprintw(screen, 0, i, "-");
        mvwprintw(screen, y - 1, i, "-");
      }
}

void MineSweeper::makeMines()
{
    for(int r=0;r<row;r++)
    {
        for(int c=0;c<col;c++)
        {
            for(int i=0;i<3;i++)
            {
                for(int j=0;j<3;j++)
                {
                    if(i-1 == 0 && j-1 == 0)
                        continue;
                    if(isValid(r+i-1, c+j-1))
                    {
                        if (mines[r+i-1][c+j-1]=="▲")
                        {
                            numMines[r][c]++;
                        }
                    }
                }
            }
        }
    }
}
bool MineSweeper::isEmpty(int r, int c)
{
    bool flag=true;
    for(int i=0;i<3;i++)
    {
        for(int j=0;j<3;j++)
        {
            if(i-1 == 0 && j-1 == 0)
                continue;
            if(isValid(r+i-1, c+j-1))
            {
                if (mines[r+i-1][c+j-1]=="▲")
                {
                    flag=false;
                    return flag;
                }
            }
        }
    }
    return flag;
}
bool MineSweeper::isValid(int r, int c)
{
    return (r >= 0) && (r < row) &&
           (c >= 0) && (c < col);
}
void MineSweeper::revealMap(int x, int y)
{
    int xcord;
    int ycord;
    for(int i=-1;i<2;i++)
    {
        for(int j=-1;j<2;j++)
        {
            xcord=x+j;
            ycord=y+i;
            if(isValid(xcord, ycord))
            {
                if(!replay)
                {
                    if(msMap[xcord][ycord]=="■" && mines[xcord][ycord]!="▲")
                    {
                        msMap[xcord][ycord]=to_string(numMines[xcord][ycord]);
                        if(numMines[xcord][ycord]==0)
                            revealMap(xcord, ycord);
                    }
                }
                else
                {
                    if(tempMap[xcord][ycord]=="■" && mines[xcord][ycord]!="▲")
                    {
                        tempMap[xcord][ycord]=to_string(numMines[xcord][ycord]);
                        if(numMines[xcord][ycord]==0)
                            revealMap(xcord, ycord);
                    }
                }
            }
        }
    }
}
void MineSweeper::setDifficulty(WINDOW *win, int parent_x, int parent_y)
{
    int diff;
    mvwprintw(win, 2, (parent_x/2)-((float)title.length()/2), title.c_str());
    mvwprintw(win, (parent_y/2)-2, (parent_x/2)-((float)title.length()/2)-10, "1를 입력하세요 (1, 2, 3): ");
    mvwprintw(win, (parent_y/2)-2, (parent_x/2)-((float)title.length()/2)-10, "난이도를 입력하세요 (1, 2, 3): ");
    mvwprintw(win, (parent_y/2)-1, (parent_x/2)-((float)title.length()/2)-10, "1:하, 맵 크기의 10% 만큼 지뢰 개수");
    mvwprintw(win, (parent_y/2), (parent_x/2)-((float)title.length()/2)-10, "2:중, 맵 크기의 20% 만큼 지뢰 개수");
    mvwprintw(win, (parent_y/2)+1, (parent_x/2)-((float)title.length()/2)-10, "3:상, 맵 크기의 30% 만큼 지뢰 개수");
    wrefresh(win);
    while(1)
    {
        while(diff!='1' && diff!='2'&& diff!='3')
        {
            diff = wgetch(win);
        }
        int new_y, new_x;
        if (is_term_resized(parent_y, parent_x) )
        {
            getmaxyx(stdscr, new_y, new_x);
            parent_x = new_x;
            parent_y = new_y;

            wresize(win, new_y, new_x);
            mvwin(win, new_y, 0);

            wclear(stdscr);
            wclear(win);
        }
        mvwprintw(win, 2, (parent_x/2)-((float)title.length()/2), title.c_str());
        mvwprintw(win, (parent_y/2)-2, (parent_x/2)-((float)title.length()/2)-10, "난이도를 입력하세요 (1, 2, 3): ");
        mvwprintw(win, (parent_y/2)-1, (parent_x/2)-((float)title.length()/2)-10, "1:하, 맵 크기의 10% 만큼 지뢰 개수");
        mvwprintw(win, (parent_y/2), (parent_x/2)-((float)title.length()/2)-10, "2:중, 맵 크기의 20% 만큼 지뢰 개수");
        mvwprintw(win, (parent_y/2)+1, (parent_x/2)-((float)title.length()/2)-10, "3:상, 맵 크기의 30% 만큼 지뢰 개수");
        draw_borders(win);
        if(diff=='1'||diff=='2'||diff=='3')
        {
            difficulty=diff-48;
            break;
        }
        wrefresh(win);
        std::this_thread::sleep_for(std::chrono::milliseconds(50));
    }
}

void MineSweeper::initMap(WINDOW *win, int parent_x, int parent_y)
{
    mvwprintw(win, 2, (parent_x/2)-((float)title.length()/2), title.c_str());
    mvwprintw(win, (parent_y/2)-2, (parent_x/2)-((float)title.length()/2)-10, "맵의 크기를 입력하세요 (행의 개수): ");
    wrefresh(win);
    int counter=0;
    string str;
    int size;
    while(1)
    {
        int new_y, new_x;
        if (is_term_resized(parent_y, parent_x) )
        {
            getmaxyx(stdscr, new_y, new_x);
            parent_x = new_x;
            parent_y = new_y;

            wresize(win, new_y, new_x);
            mvwin(win, new_y, 0);

            wclear(stdscr);
            wclear(win);
        }
        while(size!='\n')
        {
            size = wgetch(win);
            if(isdigit(size))
            {
                str.push_back(size);
            }
        }
        draw_borders(win);
        if(counter==0)
        {
            row=stoi(str);
            size=0;
            str="";
            counter++;
        }
        else if(counter==1)
        {
            col=stoi(str);
            counter++;
        }
        if(counter==0)
            mvwprintw(win, (parent_y/2)-2, (parent_x/2)-((float)title.length()/2)-10, "맵의 크기를 입력하세요 (행의 개수): ");
        else if(counter==1)
            mvwprintw(win, (parent_y/2)-2, (parent_x/2)-((float)title.length()/2)-10, "맵의 크기를 입력하세요 (열의 개수): ");
        else if(counter==2)
            break;
        wrefresh(win);
        std::this_thread::sleep_for(std::chrono::milliseconds(50));
    }
    wclear(stdscr);
    wclear(win);
    msMap = new string*[row];
    numMines = new int*[row];
    mines = new string*[row];
    tempMap=new string*[row];
    for(size_t r =0;r<row;r++)
    {
        msMap[r] = new string[col];
        numMines[r]= new int[col];
        mines[r]=new string[col];
        tempMap[r]=new string[col];
        for(size_t c =0;c<col;c++)
        {
            msMap[r][c]="■";
            numMines[r][c]=0;
            mines[r][c]="_";
            tempMap[r][c]="■";
        }
    }
    srand((unsigned) time(NULL));
    switch (difficulty)
    {
        case 1:
            remainingMines=(int)(row*col*0.1);
            initRM=remainingMines;
            for(size_t i=0;i<(int)(row*col*0.1);i++)
            {
                int num=rand()%(row*col-1);
                while(mines[num/col][num%col]=="▲")
                    num=rand()%(row*col-1);
                mines[num/col][num%col]="▲";
            }
            makeMines();
            break;
        case 2:
            remainingMines=(int)(row*col*0.2);
            initRM=remainingMines;
            for(size_t i=0;i<(int)(row*col*0.2);i++)
            {
                int num=rand()%(row*col-1);
                while(mines[num/col][num%col]=="▲")
                    num=rand()%(row*col-1);
                mines[num/col][num%col]="▲";
            }
            makeMines();
            break;
        case 3:
            remainingMines=(int)(row*col*0.3);
            initRM=remainingMines;
            for(size_t i=0;i<(int)(row*col*0.3);i++)
            {
                int num=rand()%(row*col-1);
                while(mines[num/col][num%col]=="▲")
                    num=rand()%(row*col-1);
                mines[num/col][num%col]="▲";
            }
            makeMines();
            break;
    }
}
void MineSweeper::playGame(WINDOW *win, int parent_x, int parent_y)
{
    MEVENT event;
    int click=0;
    while(1)
    {
        if(isPlaying=='n')
            return;
        wclear(stdscr);
        wclear(win);
        int new_y, new_x;
        if (is_term_resized(parent_y, parent_x) )
        {
            getmaxyx(stdscr, new_y, new_x);
            parent_x = new_x;
            parent_y = new_y;

            wresize(win, new_y, new_x);
            mvwin(win, new_y, 0);

            wclear(stdscr);
            wclear(win);
        }
        mvwprintw(win, 2, (parent_x/2)-((float)title.length()/2), title.c_str());
        mvwprintw(win, 3, (parent_x/2)-((float)title.length()/2), "남은 지뢰 개수: %d",remainingMines);
        mvwprintw(win, parent_y/2-2, (parent_x)-16, "------");
        mvwprintw(win, parent_y/2-1, (parent_x)-16, "|Quit|");
        mvwprintw(win, parent_y/2, (parent_x)-16, "------");
        bool isWon=true;
        int index=0;
        for(int r =0;r<row;r++)
        {
            for(int c =0;c<col;c++)
            {
                if(msMap[r][c]!="■")
                {
                    if(msMap[r][c]=="□")
                        mvwprintw(win, r+5, (int)((parent_x/2)-(col)+c-2+index), msMap[r][c].c_str());
                    else if(msMap[r][c]=="▲")
                    {
                        mvwprintw(win, r+5, (int)((parent_x/2)-(col)+c-2+index), msMap[r][c].c_str());
                        mvwprintw(win, 2,  (parent_x/2)-((float)title.length()/2), "GAME OVER!          ");
                        int idx=0;
                        for(int i =0;i<row;i++)
                        {
                            for(int j =0;j<col;j++)
                            {
                                if(mines[i][j] == "▲")
                                    mvwprintw(win, i+5, (int)((parent_x/2)-(col)+j-2+idx), mines[i][j].c_str());
                                else
                                {
                                    mvwprintw(win, i+5, (int)((parent_x/2)-(col)+j-2+idx), to_string(numMines[i][j]).c_str());
                                    mvwprintw(win, i+5, (int)((parent_x/2)-(col)+j-1+idx), " ");
                                    idx++;
                                }
                            }
                            idx=0;
                        }
                        draw_borders(win);
                        wrefresh(win);
                        std::this_thread::sleep_for(std::chrono::milliseconds(5000));
                        showReplay(win, parent_x, parent_y);
                        start();
                        if(isPlaying=='n')
                            return;
                    }
                    else
                    {
                        mvwprintw(win, r+5, (int)((parent_x/2)-(col)+c-2+index), to_string(numMines[r][c]).c_str());
                        mvwprintw(win, r+5, (int)((parent_x/2)-(col)+c-1+index), " ");
                        index++;
                    }
                }
                else
                {
                    mvwprintw(win, r+5, (int)((parent_x/2)-(col)+c-2+index), msMap[r][c].c_str());
                    isWon=false;
                }
            }
            index=0;
        }
        if(isWon==true && remainingMines==0)
        {
            wclear(stdscr);
            wclear(win);
            draw_borders(win);
            mvwprintw(win, (int)(parent_y/2)-2, (int)(parent_x/2)-5, "You won!");
            wrefresh(win);
            std::this_thread::sleep_for(std::chrono::milliseconds(3000));
            showReplay(win, parent_x, parent_y);
            start();
            if(isPlaying=='n')
                return;
        }
        draw_borders(win);
        srand((unsigned) time(NULL));
        click=wgetch(win);
        switch(click)
        {
            case KEY_MOUSE:
                if(getmouse(&event)==OK)
                {
                    if(event.bstate & BUTTON1_PRESSED)
                    {
                        int rtemp=6;
                        int ctemp=(int)((parent_x/2)-(col)-2)+1;
                        if(event.y+1==(parent_y/2) && (event.x+1>=parent_x-16 && event.x+1<=parent_x-8))
                        {
                            wclear(stdscr);
                            wclear(win);
                            string fileName=PATH+"/minesweeper"+to_string(rand()%(1000))+"_"+to_string(row)+"-"+to_string(col)+"-"+to_string(difficulty)+".txt";
                            if(saveFileName!="")
                                fileName=PATH+"/"+saveFileName;
                            ofstream saveFile(fileName.c_str());
                            if(saveFile.is_open())
                            {
                                saveFile<<row<<"\t"<<col<<"\t"<<remainingMines<<"\t"<<difficulty<<endl;
                                for(int i=0;i<row;i++)
                                {
                                    for(int j=0;j<col;j++)
                                    {
                                        if(msMap[i][j]=="■")
                                            saveFile<<"R";
                                        else if(msMap[i][j]=="□")
                                            saveFile<<"F";
                                        else
                                            saveFile<<msMap[i][j];
                                    }
                                    saveFile<<endl;
                                }
                                for(int i=0;i<row;i++)
                                {
                                    for(int j=0;j<col;j++)
                                    {
                                        if(mines[i][j]=="▲")
                                            saveFile<<"B";
                                        else
                                            saveFile<<mines[i][j];
                                    }
                                    saveFile<<endl;
                                }
                                for(int i=0;i<row;i++)
                                {
                                    for(int j=0;j<col;j++)
                                    {
                                        saveFile<<numMines[i][j];
                                    }
                                    saveFile<<endl;
                                }
                                saveFile.close();
                            }
                            wrefresh(win);
                            showReplay(win, parent_x, parent_y);
                            mvwprintw(win, (int)(parent_y/2)-2, (int)(parent_x/2)-5, "저장 완료!");
                            std::this_thread::sleep_for(std::chrono::milliseconds(500));
                            start();
                        }
                        if(event.y+1-rtemp>=0 && (event.y+1-rtemp) < row)
                        {
                            if(event.x+1-ctemp>=0 && (event.x+1-ctemp)/2<col)
                            {
                                if(mines[(event.y+1-rtemp)][(event.x+1-ctemp)/2]!="_")
                                {
                                    msMap[(event.y+1-rtemp)][(event.x+1-ctemp)/2]=mines[(event.y+1-rtemp)][(event.x+1-ctemp)/2];
                                }
                                else
                                {
                                    msMap[(event.y+1-rtemp)][(event.x+1-ctemp)/2]= to_string(numMines[(event.y+1-rtemp)][(event.x+1-ctemp)/2]);
                                    if(isEmpty((event.y+1-rtemp), (event.x+1-ctemp)/2))
                                        revealMap((event.y+1-rtemp), (event.x+1-ctemp)/2);
                                }
                                movesMade.push_back("L "+to_string((event.y+1-rtemp))+" "+to_string((event.x+1-ctemp)/2));
                            }
                        }
                    }
                    else if(event.bstate & BUTTON3_PRESSED)
                    {
                        int rtemp=6;
                        int ctemp=(int)((parent_x/2)-(col)-2)+1;

                        if(event.y+1-rtemp>=0 && (event.y+1-rtemp) < row)
                        {
                            if(event.x+1-ctemp>=0 && (event.x+1-ctemp)/2<col)
                            {
                                if(msMap[(event.y+1-rtemp)][(event.x+1-ctemp)/2]=="■")
                                {
                                    msMap[(event.y+1-rtemp)][(event.x+1-ctemp)/2]="□";
                                    remainingMines--;
                                }
                                else if(msMap[(event.y+1-rtemp)][(event.x+1-ctemp)/2]=="□")
                                {
                                    msMap[(event.y+1-rtemp)][(event.x+1-ctemp)/2]="■";
                                    remainingMines++;
                                }
                                else
                                {
                                    mvwprintw(win, 3, (parent_x/2)-((float)title.length()/2)-5, "이미 오픈된 자리입니다!");
                                    wrefresh(win);
                                    std::this_thread::sleep_for(std::chrono::milliseconds(500));
                                }
                                movesMade.push_back("R "+to_string((event.y+1-rtemp))+" "+to_string((event.x+1-ctemp)/2));
                            }
                        }
                    }
                }
                break;
        }
        wrefresh(win);
        std::this_thread::sleep_for(std::chrono::milliseconds(50));
    }
}
void MineSweeper::showReplay(WINDOW* win, int parent_x, int parent_y)
{
    replay=true;
    mvwprintw(win, 2, (parent_x/2)-((float)title.length()/2), title.c_str());
    mvwprintw(win, 3, (parent_x/2)-((float)title.length()/2), "남은 지뢰 개수: %d",initRM);
    mvwprintw(win, parent_y/2-2, (parent_x)-16, "------");
    mvwprintw(win, parent_y/2-1, (parent_x)-16, "|Quit|");
    mvwprintw(win, parent_y/2, (parent_x)-16, "------");
    draw_borders(win);
    for(auto it=movesMade.begin();it!=movesMade.end();it++)
    {
        draw_borders(win);
        mvwprintw(win, 2, (parent_x/2)-((float)title.length()/2), title.c_str());
        mvwprintw(win, 3, (parent_x/2)-((float)title.length()/2), "남은 지뢰 개수: %d",initRM);
        wrefresh(win);
        wclear(stdscr);
        wclear(win);
        int new_y, new_x;
        if (is_term_resized(parent_y, parent_x) )
        {
            getmaxyx(stdscr, new_y, new_x);
            parent_x = new_x;
            parent_y = new_y;

            wresize(win, new_y, new_x);
            mvwin(win, new_y, 0);

            wclear(stdscr);
            wclear(win);
        }
        string lr,rc;
        int rindex=0;
        int cindex=0;
        int count=0;
        int index=0;
        for(int r =0;r<row;r++)
        {
            for(int c =0;c<col;c++)
            {
                if(tempMap[r][c]!="■")
                {
                    if(tempMap[r][c]=="□"||tempMap[r][c]=="▲")
                        mvwprintw(win, r+5, (int)((parent_x/2)-(col)+c-2+index), tempMap[r][c].c_str());
                    else
                    {
                        mvwprintw(win, r+5, (int)((parent_x/2)-(col)+c-2+index), to_string(numMines[r][c]).c_str());
                        mvwprintw(win, r+5, (int)((parent_x/2)-(col)+c-1+index), " ");
                        index++;
                    }
                }
                else
                {
                    mvwprintw(win, r+5, (int)((parent_x/2)-(col)+c-2+index), tempMap[r][c].c_str());
                }
            }
            index=0;
        }
        draw_borders(win);
        mvwprintw(win, 2, (parent_x/2)-((float)title.length()/2), title.c_str());
        mvwprintw(win, 3, (parent_x/2)-((float)title.length()/2), "남은 지뢰 개수: %d",initRM);
        wrefresh(win);
        std::this_thread::sleep_for(std::chrono::milliseconds(500));
        for (auto x : *it)
        {
            if (x == ' ')
            {
                rc = "";
                count++;
            }
            else
            {
                rc = rc + x;
                if(count==0)
                    lr=rc;
                else if(count==1)
                    rindex=stoi(rc);
                else if(count==2)
                    cindex=stoi(rc);
            }
        }
        if(lr=="R")
        {
            if(tempMap[rindex][cindex]=="□")
            {
                tempMap[rindex][cindex]="■";
                initRM++;
            }
            else if(tempMap[rindex][cindex]=="■")
            {
                tempMap[rindex][cindex]="□";
                initRM--;
            }
        }
        else
        {
            if(mines[rindex][cindex]=="▲")
            {
                tempMap[rindex][cindex]="▲";
            }
            else
                tempMap[rindex][cindex]=to_string(numMines[rindex][cindex]);
            if(isEmpty(rindex, cindex))
               revealMap(rindex,cindex);
        }
        wclear(stdscr);
        wclear(win);
        for(int r =0;r<row;r++)
        {
            for(int c =0;c<col;c++)
            {
                if(tempMap[r][c]!="■")
                {
                    if(tempMap[r][c]=="□"||tempMap[r][c]=="▲")
                        mvwprintw(win, r+5, (int)((parent_x/2)-(col)+c-2+index), tempMap[r][c].c_str());
                    else
                    {
                        mvwprintw(win, r+5, (int)((parent_x/2)-(col)+c-2+index), to_string(numMines[r][c]).c_str());
                        mvwprintw(win, r+5, (int)((parent_x/2)-(col)+c-1+index), " ");
                        index++;
                    }
                }
                else
                {
                    mvwprintw(win, r+5, (int)((parent_x/2)-(col)+c-2+index), tempMap[r][c].c_str());
                }
            }
            index=0;
        }
        draw_borders(win);
        mvwprintw(win, 2, (parent_x/2)-((float)title.length()/2), title.c_str());
        mvwprintw(win, 3, (parent_x/2)-((float)title.length()/2), "남은 지뢰 개수: %d",initRM);
        wrefresh(win);
    }
    wclear(stdscr);
    wclear(win);
    int index=0;
    for(int r =0;r<row;r++)
    {
        for(int c =0;c<col;c++)
        {
            if(tempMap[r][c]!="■")
            {
                if(tempMap[r][c]=="□"||tempMap[r][c]=="▲")
                    mvwprintw(win, r+5, (int)((parent_x/2)-(col)+c-2+index), tempMap[r][c].c_str());
                else
                {
                    mvwprintw(win, r+5, (int)((parent_x/2)-(col)+c-2+index), to_string(numMines[r][c]).c_str());
                    mvwprintw(win, r+5, (int)((parent_x/2)-(col)+c-1+index), " ");
                    index++;
                }
            }
            else
            {
                mvwprintw(win, r+5, (int)((parent_x/2)-(col)+c-2+index), tempMap[r][c].c_str());
            }
        }
        index=0;
    }
    std::this_thread::sleep_for(std::chrono::milliseconds(1000));
    draw_borders(win);
    mvwprintw(win, 2, (parent_x/2)-((float)title.length()/2), title.c_str());
    mvwprintw(win, 3, (parent_x/2)-((float)title.length()/2), "남은 지뢰 개수: %d",initRM);
    wrefresh(win);
    replay=false;
}

