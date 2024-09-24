# include <iostream>

int getMax(int a, int b, int c = 0) {
    if (a > b && a > c) {
        return a;
    } else if (b > a && b > c) {
        return b;
    } else {
        return c;
    }
}

int main() {
    int a, b, c;
    std::cout << "Enter two or three positive integers: ";
    std::cin >> a >> b;
    if (std::cin.peek() != '\n') {
        std::cin >> c;
    }
    
    std::cout << getMax(a, b, c) << std::endl;
    return 0;
}