package com.zph.consumer;

import com.lmax.disruptor.EventHandler;
import com.zph.entity.LongEvent;

/**
 * 消费者
 * @author: zph
 * @data: 2018/12/01 12:13
 */
public class LongEventHandler implements EventHandler<LongEvent> {

    public void onEvent(LongEvent longEvent, long l, boolean b) throws Exception {
        System.out.println("消费者："+longEvent.getValue());
    }
}
