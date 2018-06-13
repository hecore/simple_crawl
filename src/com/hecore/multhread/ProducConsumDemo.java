
/**
 * @author Administrator
 *
 */
package com.hecore.multhread;

import java.util.Vector;

/**
 * 工厂
 * 
 * @author hecore
 *
 */
class Factory {
	private Vector<String> goods;
	// 标志货物上限
	private int goodFlag = 5;

	public Factory() {
		goods = new Vector<String>();
	}

	// 生产
	public synchronized void production() {
		if (goods.size() < goodFlag) {
			goods.addElement("货物" + (goods.size() + 1));
			System.out.printf("%s生产货物：货物%d，现有货物数量：%d\n", Thread.currentThread().getName(), (goods.size() + 1),
					goods.size());
			// 唤醒消费线程
			notifyAll();
		} else {
			System.out.println("货物已满可以取货");
			try {
				// 货物满等待消费
				wait();
			} catch (InterruptedException e) {
				System.out.println("生产事故...");
			}
		}
	}

	// 消费
	public synchronized void getProduction() {
		if (goods.size() < 1) {
			try {
				System.out.println("货物取完...");
				// 等待生产
				wait();
			} catch (InterruptedException e) {
				System.out.println("账户余额不足...");
			}
		} else {
			System.out.printf("%s取走货物：%s，还有货物数量：%d\n", Thread.currentThread().getName(),
					goods.elementAt(goods.size() - 1), goods.size());
			goods.remove(goods.size() - 1);
			// 唤醒生产线程
			notifyAll();
		}
	}
}

/**
 * 生产者
 * 
 * @author hecore
 *
 */
class ProductThread extends Thread {
	private Factory factory = null;

	public ProductThread(String name, Factory factory) {
		super(name);
		this.factory = factory;
	}

	@Override
	public void run() {
		while (true) {
			factory.production();// 生产货物
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				System.out.println("生产事故...");
			}
		}
	}
}

/**
 * 消费者
 * 
 * @author hecore
 *
 */
class ConsumThread extends Thread {
	private Factory factory = null;

	public ConsumThread(String name, Factory factory) {
		super(name);
		this.factory = factory;
	}

	@Override
	public void run() {
		while (true) {
			factory.getProduction();// 消费货物
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				System.out.println("账户余额不足...");
			}
		}
	}
}

/**
 * 生产者-消费者 持续的生产和消费 注意使用的是同一个对象作为参数传入
 * 
 * @author hecore
 *
 */
public class ProducConsumDemo {
	public static void main(String[] args) {
		Factory factory = new Factory();
		ProductThread productThread = new ProductThread("生产工厂", factory);
		ConsumThread consumThread = new ConsumThread("消费者", factory);
		productThread.start();
		consumThread.start();
	}
}

