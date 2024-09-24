# include <iostream>
# include <vector>
# include <algorithm>

void sortData(std::vector<int>& data) {
    std::sort(data.begin(), data.end());
}

void sortData(std::vector<double>& data) {
   std::sort(data.begin(), data.end());
}

int main() {
    std::vector<double> data;
    std::cout << "Enter a few numbers: ";
    double num;
    while (std::cin.peek() != '\n') {
        std::cin >> num;
        data.push_back(num);
    }
    sortData(data);
    for (const auto& value : data) {
        std::cout << value << " ";
    }
    std::cout << std::endl;
    return 0;
}