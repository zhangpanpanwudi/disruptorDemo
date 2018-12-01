package com.zph.producer;

import com.lmax.disruptor.RingBuffer;
import com.zph.entity.LongEvent;

import java.nio.ByteBuffer;

/**
 * @author: zph
 * @data: 2018/12/01 12:18
 */
public class LongEventProducer {
    public final RingBuffer<LongEvent> ringBuffer;

    public LongEventProducer(RingBuffer<LongEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    //生产者将数据放入缓存队列中
    public void onData(ByteBuffer byteBuffer){
        long next = ringBuffer.next();
        Long data = null;
        try {
            LongEvent longEvent = ringBuffer.get(next);
            data=byteBuffer.getLong(0);
            longEvent.setValue(data);
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            System.out.println("生产者准备发送数据");
            ringBuffer.publish(next);
        }
    }
}
