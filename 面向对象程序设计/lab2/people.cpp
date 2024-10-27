/*
编写设计一个 People（人）类。该类的数据成员有年龄（age）、身高（height）、体重（weight）和人数（num），其中人数为静态数据成员，成员函数有构造函数（People）、进食（Eating）、运动（Sporting）、睡眠（Sleeping）、显示（Show）和显示人数（ShowNum）。其中构造函数由已知参数年龄 (a)、身高 (h) 和体重 (w) 构造对象，进食函数使体重加 1，运动函数使身高加 1，睡眠函数使年龄、身高、体重各加 1，显示函数用于显示人的年龄、身高、体重，显示人数函数为静态成员函数，用于显示人的个数。假设年龄的单位为岁，身高的单位为厘米，体重的单位为市斤，要求所有数据成员为 protected 访问权限，所有成员函数为 public 访问权限，在主函数中通过对象直接访问类的所有成员函数。
*/

#include <iostream>
using namespace std;

class People {
protected:
    int age;
    int height;
    int weight;
    static int num; // 静态变量表示人数

public:
    // 构造函数
    People(int a, int h, int w) : age(a), height(h), weight(w) {
        num++; // 每创建一个对象，人数加1
    }

    // 进食函数
    void Eating() {
        weight += 1;
    }

    // 运动函数
    void Sporting() {
        height += 1;
    }

    // 睡眠函数
    void Sleeping() {
        age += 1;
        height += 1;
        weight += 1;
    }

    // 显示函数
    void Show() const {
        cout << "年龄: " << age << ", 身高: " << height << " cm, 体重: " << weight << " kg" << endl;
    }

    // 显示人数函数
    static void ShowNum() {
        cout << "人数: " << num << endl;
    }
};

// 初始化静态成员变量
int People::num = 0;

int main() {
    People p1(25, 175, 65);
    p1.Show();
    p1.Eating();
    p1.Sporting();
    p1.Sleeping();
    p1.Show();
    People::ShowNum();

    People p2(30, 180, 75);
    p2.Show();
    People::ShowNum();

    return 0;
}
