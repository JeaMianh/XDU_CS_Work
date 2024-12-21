#include <linux/module.h>
#include <linux/kernel.h>
#include <linux/init.h>
#include <linux/sched.h>
#include <linux/kthread.h>
#include <linux/slab.h>
#include <linux/delay.h>
#include <linux/workqueue.h>
#include <linux/atomic.h>

MODULE_LICENSE("GPL");
MODULE_AUTHOR("FuShengyuan");
MODULE_DESCRIPTION("deferred work");

/// @brief 存放work_struct与id信息
struct work_ctx
{
    struct work_struct work;
    int current_id;
};
struct delayed_work my_work;

struct work_ctx works[10];

struct task_struct *threads[10];

/// @brief kthread执行体
/// @param data 传入参数
/// @return
int kthread_handler(void *data)
{
	//TODO Add Code here
    struct work_ctx *ctx = (struct work_ctx *)data; // 获取传入参数
    // while (!kthread_should_stop()) { // 不需要循环
        printk("kthread :%d\n", ctx->current_id);
        
        // msleep(500);
    // }
    return 0;
}
/// @brief work queue执行体
/// @param work
void work_queue_handler(struct work_struct *work)
{
	//TODO Add Code here
    struct work_ctx *ctx = container_of(work, struct work_ctx, work); // 获取 work_ctx 结构体
    printk("work queue : %d\n", ctx->current_id);
}
/// @brief delayed work执行体
/// @param work 
void delayed_work_handler(struct work_struct *work)
{
    printk("delayed work!\n");
}

/// @brief 内核模块初始化
/// @param
/// @return
int deferred_work_init(void)
{
    printk(KERN_INFO "deferred work module init\n");
    int i;
  	//TODO Add Code here,init workqueue and kernel thread

    for (i = 0; i < 10; i++) {
        works[i].current_id = i + 256;
        INIT_WORK(&works[i].work, work_queue_handler); // 将执行函数和 work 结构体绑定
        if (!schedule_work(&works[i].work)) { // 将 work 结构体加入工作队列
            printk("schedule work failed\n");
            return -1;
        }

        threads[i] = kthread_run(kthread_handler, &works[i], "kthread%d", i); // 创建内核线程
        if (IS_ERR(threads[i])) {
            printk("create kthread failed\n");
            return -1;
        }

    }

    INIT_DELAYED_WORK(&my_work, delayed_work_handler); // 初始化延迟 work
    schedule_delayed_work(&my_work, 5 * HZ); // 将延迟 work 加入工作队列, jiffies = seconds * HZ
  	
    return 0;
}
/// @brief 内核模块退出
/// @param
void deferred_work_exit(void)
{
	//TODO Add Code here
    int i;
    cancel_delayed_work(&my_work); // 取消延迟 work
    
    for (i = 0; i < 10; i++) {
        if (threads[i] && !IS_ERR(threads[i])) {    
            kthread_stop(threads[i]);
            threads[i] = NULL;
        }

        cancel_work_sync(&works[i].work); // 取消 work
        
    }

    printk(KERN_INFO "deferred work module exit\n");
}

module_init(deferred_work_init);
module_exit(deferred_work_exit);
