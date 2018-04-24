# thread-test
简介: 包含了一些关于JAVA多线程知识的测试代码.<br/>

-------------------------------------------------------------------

1. classic包 : 演示一个最简单的操作临界资源的多线程示例<br/>

2. copyonwritearraylist : 对比ArrayList , 探究CopyOnWriteArrayList在多线程环境中的表现.<br/>

3. countdownlatch : 探索CountDownLatch的作用.<br/>

4. mode : 包含一些实际开发时可能使用到的多线程编程模式的不同实现方式.<br/>
     |- blockingqueue: 使用BlockingQueue实现 生产者-消费者 模式.<br/>
     |- tradition: 使用传统的Lock/Condition实现 生产者-消费者 模式.
