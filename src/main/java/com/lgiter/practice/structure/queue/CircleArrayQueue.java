package com.lgiter.practice.structure.queue;

/**
 * Author: lixiaolong
 * Date: 2022/6/1
 * Desc:
 * 数组环形链表
 */
public class CircleArrayQueue {

    public static void main(String[] args) {
        Queue queue = new Queue(4);
        queue.push(1);
        queue.push(2);
        queue.push(3);
        // queue.pop();
        queue.push(4);
        queue.pop();
        queue.push(5);
        queue.pop();
        queue.pop();
        queue.pop();
        queue.pop();
        queue.pop();

    }

}

class Queue {

    /**
     * 数组中的第一个元素所在的下标位置，也就是说arr[front]永远是队列的第一个元素
     */
    private int front;
    /**
     * 数组中的最后一个元素所在的下标位置,也就是说arr[rear]永远是队列的最后一个元素
     */
    private int rear;
    /**
     * 数组最大长度，在给数组初始化时，需要把maxSize再+1，作为数组最大容量，原因如下：
     * 初始状态front == rear == 0
     * 首次push时 front == 0 rear == 1
     * 但是arr[front] 才有值，而arr[rear] 并没有元素进来，所以arr[rear]是永远没有元素的，所以需要多开辟一个空间
     */
    private int maxSize;
    /**
     * 1.如何判断队列已满？
     * 分析：rear初始值为0，maxSize = 4
     * 队列满时，rear 和 front会有四种情况
     * rear = 3, front = 0
     * rear = 0, front = 1
     * rear = 1, front = 2
     * rear = 2, front = 3
     * rear = 3, front = 0
     * 可知rear 和 front的关系满足： (rear + 1) % maxSize = front
     *
     * 2.如何判断队列已空？
     * front == rear
     *
     * 3.如何遍历队列？
     * 指针i从front开始，遍历次数为数组中元素个数
     * 由于是环形的，i达到最大值时，需要从0开始，因此遍历时数组的下标不是arr[i] 而是arr[i % maxSize]
     *
     * 4.push时rear如何变化
     * rear初始值为0，maxSize = 4
     * push时rear的值的变化应该是：0-1-2-3-0-1-2-3 ...
     * 规律 rear = (rear + 1) % maxSize
     *
     * 5.pop时front如何变化
     * 同push时rear的变化
     * front = (front + 1) % maxSize
     *
     * 6.当前队列大小
     * (rear + maxSize - front) % maxSize
     *
     * 7.总结：
     * 对于普通数组(非环形),指针从0到数组length可以完整遍历一遍
     * 而环形数组，当指针到达最大值时，需要从头开始，因此将自增操作 改为自增后取模操作，可完成环形遍历。
     * 取模其本质上就是循环。
     */
    private int[] arr;

    public Queue(int maxSize) {
        this.maxSize = maxSize + 1;
        arr = new int[this.maxSize];
    }

    public void list(){
        System.out.print("队列内容为：");
        for (int i = front; i < front + size(); i ++) {
            System.out.print(arr[i % maxSize] + "\t");

        }
    }

    public int size(){
        return (rear + maxSize - front) % maxSize;
    }

    public int pop(){
        if(isEmpty()){
            System.out.println("队列已空, rear = " + rear + ", front = " + front);
            throw new RuntimeException("队列已空");
        }
        int temp = arr[front];
        front = (front + 1) % maxSize;
        System.out.print("pop成功 rear = " + rear + ", front = " + front + "\t");
        list();
        System.out.println();
        return temp;
    }

    public void push(int value){
        if(isFull()){
            System.out.println("队列已满, rear = " + rear + ", front = " + front);
            return;
        }
        arr[rear] = value;
        rear = (rear + 1) % maxSize;
        System.out.print("push成功 rear = " + rear + ", front = " + front + "\t");
        list();
        System.out.println();

    }

    public boolean isEmpty(){
        return rear == front;
    }

    public boolean isFull(){
        return (rear + 1) % maxSize == front;
    }
}
