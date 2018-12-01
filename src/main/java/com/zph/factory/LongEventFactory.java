package com.zph.factory;

import com.lmax.disruptor.EventFactory;
import com.zph.entity.LongEvent;

/**
 *
 * @author: zph
 * @data: 2018/12/01 12:12
 */
public class LongEventFactory implements EventFactory<LongEvent> {
    public LongEvent newInstance() {
        return new LongEvent();
    }
}
