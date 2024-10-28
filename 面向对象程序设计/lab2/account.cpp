/*
 管理个人活期账户：个人储蓄活期账户包括账号、户名、密码、余额、活期年利率等信息。要求能够对个人账户进行存钱、取钱、计算年利息、打印账户相关信息等操作。编写主函数测试账户相关功能。
*/

#include <iostream>
#include <cstring>
using namespace std;

class Account {
private:
    int accountNumber;
    char accountHolder[20];
    int password;
    double balance;
    static double annualInterestRate;

public:
    // 构造函数
    Account(int accNum, const char* holder, int pass, double bal)
        : accountNumber(accNum), password(pass), balance(bal) {
        strncpy(accountHolder, holder, 20);
    }

    // 存钱
    void deposit(double amount) {
        balance += amount;
        cout << "存款成功！当前余额: " << balance << endl;
    }

    // 取钱
    void withdraw(double amount) {
        if (amount > balance) {
            cout << "余额不足！" << endl;
        } else {
            balance -= amount;
            cout << "取款成功！当前余额: " << balance << endl;
        }
    }

    // 计算年利息
    double calculateAnnualInterest() const {
        return balance * annualInterestRate;
    }

    // 打印账户信息
    void showAccountInfo() const {
        cout << "账号: " << accountNumber << ", 户名: " << accountHolder 
             << ", 余额: " << balance << ", 年利率: " << annualInterestRate * 100 << "%" << endl;
    }

    // 设置年利率
    static void setAnnualInterestRate(double rate) {
        annualInterestRate = rate;
    }
};

// 初始化静态成员变量
double Account::annualInterestRate = 0.03;

int main() {
    Account acc1(1001, "Alice", 1234, 5000);
    acc1.deposit(1000);
    acc1.withdraw(200);
    acc1.showAccountInfo();
    cout << "年利息: " << acc1.calculateAnnualInterest() << endl;

    Account::setAnnualInterestRate(0.035);
    Account acc2(1002, "Bob", 5678, 8000);
    acc2.showAccountInfo();
    cout << "年利息: " << acc2.calculateAnnualInterest() << endl;

    return 0;
}
