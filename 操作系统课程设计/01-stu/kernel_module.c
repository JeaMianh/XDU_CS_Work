#include <linux/module.h>
#include <linux/kernel.h>
#include <linux/init.h>
#include <linux/list.h>
#include <linux/sched.h>
#include <linux/kthread.h>
#include <linux/rcupdate.h>
#include <linux/slab.h>
#include <linux/delay.h>
#include <linux/sched/signal.h>
#include <linux/string.h>

MODULE_LICENSE("GPL");

MODULE_AUTHOR("FuShengyuan");

MODULE_DESCRIPTION("A simple kernel module with a list and two threads.");

static struct list_head my_list;
static struct task_struct *thread1, *thread2;

spinlock_t lock;

// 定义链表节点结构
struct pid_node
{
    int pid;
    char comm[16];
    struct list_head list;
};

// thread1 函数体
static int thread1_func(void *data)
{

//TODO: add code here
	struct pid_node *new_node;
	struct task_struct *curr_task;

	while (!kthread_should_stop()) {
		for_each_process(curr_task) {
			new_node = kmalloc(sizeof(struct pid_node), GFP_KERNEL);
			if (!new_node) {
				printk(KERN_INFO "kmalloc failed\n");
				continue;
			}

			new_node->pid = curr_task->pid;
			strncpy(new_node->comm, curr_task->comm, 16);

			spin_lock(&lock);
			list_add_tail(&new_node->list, &my_list);
			spin_unlock(&lock);
		}

		msleep(500);
	}

    return 0;
}
// thread2 函数体
static int thread2_func(void *data)
{

//TODO: add code here
	struct pid_node *pos, *n;

	while (!kthread_should_stop()) {
		spin_lock(&lock);
		list_for_each_entry_safe(pos, n, &my_list, list) {
			printk(KERN_INFO "pid: %d, comm: %s\n", pos->pid, pos->comm);
			list_del(&pos->list);
			kfree(pos);
		}
		spin_unlock(&lock);
		
		msleep(500);
	}

    return 0;
}

// 模块初始化函数
int kernel_module_init(void)
{
    printk(KERN_INFO "List and thread module init\n");

//TODO: add code here
//	
	INIT_LIST_HEAD(&my_list);
	spin_lock_init(&lock);

	thread1 = kthread_run(thread1_func, NULL, "thread1");
	if (IS_ERR(thread1)) {
		printk(KERN_INFO "create thread1 failed\n");
		return PTR_ERR(thread1);
	}


	thread2 = kthread_run(thread2_func, NULL, "thread2");
	if (IS_ERR(thread1)) {
		printk(KERN_INFO "create thread2 failed\n");
		return PTR_ERR(thread1);
	}

    return 0;
}

// 模块清理函数
void kernel_module_exit(void)
{

   //TODO: add code here
   //
 	struct pid_node *pos, *n;

    // 停止线程1
	if (thread1) {
		kthread_stop(thread1);
	}

    // 停止线程2
	if (thread2) {
		kthread_stop(thread2);
	}

    // 清理链表

	spin_lock(&lock);

	list_for_each_entry_safe(pos, n, &my_list, list) {
		list_del(&pos->list);
		kfree(pos);
	}

	spin_unlock(&lock);

    printk(KERN_INFO "List and thread module exit\n");
}

module_init(kernel_module_init);

module_exit(kernel_module_exit);

