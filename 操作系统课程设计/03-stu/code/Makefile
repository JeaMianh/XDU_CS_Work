CC=gcc
CFLAGS=-std=c11

ifneq  ($(KERNELRELEASE),)
obj-m:=edu_dev_blank.o
else
KDIR := /lib/modules/$(shell uname -r)/build
PWD:=$(shell pwd)
endif

all:
	make -C $(KDIR) M=$(PWD) modules

clean:
	rm -f *.ko *.o *.symvers *.cmd *.cmd.o .*.cmd *.mod *.mod.c *.order

install:
	make -C $(KDIR) M=$(PWD) modules
	sudo insmod edu_dev_blank.ko
	sudo mknod /dev/edu c 200 200 

run:
	dmesg -w > kernel_log.txt&
	$(CC) user_space.c -o user_space
	./user_space

uninstall:
	sudo rmmod edu_dev_blank
	sudo rm -rf /dev/edu
