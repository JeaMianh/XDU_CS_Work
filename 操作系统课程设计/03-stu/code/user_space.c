#include <stdio.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/ioctl.h>
#include <fcntl.h>
#include <unistd.h>

int main()
{
	int result;
    int fd = open("/dev/edu", O_RDONLY);
    if (fd == -1)
    {
		printf("can not open\n");
        return -1;
    }
    int ret = ioctl(fd, 0, 10);
	printf("ioctl write1: %d\n", 10);
    //cout << "ioctl write1: " << 10 << endl;
    sleep(1);
    ret = ioctl(fd, 1, &result);
	if (ret)
	{
		perror("ioctl err!\n");
	}
	printf("ioctl read1: %d\n", result);
    //cout << "ioctl read1: " << ret << endl;
    ret = ioctl(fd, 0, 5);
	printf("ioctl write2: %d\n", 5);
    //cout << "ioctl write2: " << 5 << endl;
    sleep(1);
    ret = ioctl(fd,1,&result);
	if (ret)
		perror("ioctl err!\n");
	printf("ioctl read2: %d\n", result);
    //cout << "ioctl read2: " << ret << endl;
    close(fd);
}
