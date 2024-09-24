# include <iostream>

void sortThreeNum(int& a, int& b, int& c) {
    if (a > b) std::swap(a, b);
    if (a > c) std::swap(a, c);
    if (b > c) std::swap(b, c);
}


int main() {
    int a, b, c;
    std::cout << "Enter three integers: ";
    std::cin >> a >> b >> c;
    sortThreeNum(a, b, c);
    std::cout << a << " " << b << " " << c << std::endl;
    return 0;
}