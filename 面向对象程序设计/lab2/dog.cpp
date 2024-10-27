#include <iostream>
#include <cstring>
using namespace std;

class Dog {
private:
    char* name;
    int age;
    char sex;
    int weight;

public:
    // 构造函数
    Dog(const char* nm, int a, char s, int w) : age(a), sex(s), weight(w) {
        name = new char[strlen(nm) + 1];
        strcpy(name, nm);
    }

    // 析构函数
    ~Dog() {
        delete[] name;
    }

    // 设置和获取属性
    void setName(const char* nm) {
        delete[] name;
        name = new char[strlen(nm) + 1];
        strcpy(name, nm);
    }

    const char* getName() const { return name; }
    int getAge() const { return age; }
    char getSex() const { return sex; }
    int getWeight() const { return weight; }

    // 显示信息
    void show() const {
        cout << "姓名: " << name << ", 年龄: " << age 
             << ", 性别: " << sex << ", 体重: " << weight << " kg" << endl;
    }
};

int main() {
    Dog* dogPtr = new Dog("Buddy", 3, 'M', 20);
    dogPtr->show();
    dogPtr->setName("Max");
    dogPtr->show();

    delete dogPtr;
    return 0;
}
