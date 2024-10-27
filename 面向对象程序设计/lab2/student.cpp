

#include <iostream>
#include <cstring>
using namespace std;

class Student {
private:
    char name[18];
    int num;
    int mathScore;
    int englishScore;
    static int count;             // 人数
    static int mathTotalScore;    // 数学总成绩
    static int englishTotalScore; // 英语总成绩

public:
    // 构造函数
    Student(const char* nm, int nu, int math, int english) 
        : num(nu), mathScore(math), englishScore(english) {
        strncpy(name, nm, 18);
        count++;
        mathTotalScore += math;
        englishTotalScore += english;
    }

    // 显示基本数据函数
    void ShowBase() const {
        cout << "姓名: " << name << ", 学号: " << num 
             << ", 数学成绩: " << mathScore << ", 英语成绩: " << englishScore << endl;
    }

    // 显示静态数据函数
    static void showStatic() {
        cout << "人数: " << count 
             << ", 数学总成绩: " << mathTotalScore 
             << ", 英语总成绩: " << englishTotalScore << endl;
    }
};

// 初始化静态成员变量
int Student::count = 0;
int Student::mathTotalScore = 0;
int Student::englishTotalScore = 0;

int main() {
    Student s1("Alice", 1001, 85, 90);
    Student s2("Bob", 1002, 78, 82);

    s1.ShowBase();
    s2.ShowBase();
    Student::showStatic();

    return 0;
}
