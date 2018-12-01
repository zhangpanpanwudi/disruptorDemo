package com.zph.producer;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.zph.consumer.LongEventHandler;
import com.zph.entity.LongEvent;
import com.zph.factory.LongEventFactory;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 调用类
 * @author: zph
 * @data: 2018/12/01 12:18
 */
public class DisruptorMain {

    public static void main(String[] args) {
        //1、创建缓存队列
        ExecutorService executor = Executors.newCachedThreadPool();
        //2、创建工厂
        EventFactory<LongEvent> factory =new LongEventFactory();
        //3、设置ringBuffer大小
        int ringBufferSize = 1024*1024;
        //4、创建disruptor
        Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(factory, ringBufferSize, executor, ProducerType.SINGLE, new YieldingWaitStrategy());
        //5、连接消费端
        disruptor.handleEventsWith(new LongEventHandler());
        //6、启动
        disruptor.start();
        //7、创建ringBuffer容器
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
        //8、创建生产者
        LongEventProducer producer = new LongEventProducer(ringBuffer);
        //9、指定缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        for(int i=0;i<=100;i++){
            byteBuffer.putLong(0,i);
            producer.onData(byteBuffer);
        }

        //关闭disrutor和executor
        disruptor.shutdown();
        executor.shutdown();
    }

}
