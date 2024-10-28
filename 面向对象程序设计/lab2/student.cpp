/*
定义一个描述学生（Student）基本情况的类，数据成员包括姓名 (name)、学号 (num)、数学成绩 (mathScore)、英语成绩 (englishScore)、人数 (count)、数学总成绩 (mathTotalScore) 和英语总成绩 (englishTotalScore)。其中姓名定义为长度为 18 的字符数组，其他数据成员类型为整型，数学总成绩、英语总成绩和人数为静态数据成员，函数成员包括构造函数、显示基本数据函数（ShowBase）和显示静态数据函数 (showStatic)，其中构造函数由已知参数姓名 (nm)、学号 (nu)、数学成绩 (math) 和英语成绩 (english) 构造对象，显示基本数据函数用于显示学生的姓名、学号、数学成绩、英语成绩，显示静态数据函数为静态成员函数，用于显示人数、数学总成绩、英语总成绩；要求所有数据成员为 private 访问权限，所有成员函数为 public 访问权限，在主函数中定义若干个学生对象，分别显示学生基本信息，以及显示学生人数，数学总成绩与英语总成绩。
*/

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
