package com.face.util;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.log4j.Logger;

/**
 * 线程池管理
 * 
 * @author Jason Xie 2015-11-21 14:30:51
 */
public final class ThreadPoolManager {
	private static final Logger logger = Logger.getLogger(ThreadPoolManager.class);
	private ThreadPoolManager(){}
	
	private static ExecutorService executorService = null;
	
//	(1) newCachedThreadPool
//	创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
//	线程池为无限大，当执行第二个任务时第一个任务已经完成，会复用执行第一个任务的线程，而不用每次新建线程。
//	(2) newFixedThreadPool
//	创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
//	因为线程池大小为3，每个任务输出index后sleep 2秒，所以每两秒打印3个数字。
//	定长线程池的大小最好根据系统资源进行设置。如Runtime.getRuntime().availableProcessors()
//	(3)  newScheduledThreadPool
//	创建一个定长线程池，支持定时及周期性任务执行。
//	(4) newSingleThreadExecutor
//	创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
	
	/**
	 * 启动线程，执行run方法
	 * @param runnable 线程方法
	 */
	public static void execute(Runnable runnable){
		if (executorService == null) {
//			synchronized (runnable) {
				executorService = Executors.newCachedThreadPool();
//			}
		}
		logger.info("当前活动的线程数量："+((ThreadPoolExecutor)executorService).getActiveCount());
		logger.info("线程总数："+((ThreadPoolExecutor)executorService).getPoolSize());
		executorService.execute(runnable);
	}
}
