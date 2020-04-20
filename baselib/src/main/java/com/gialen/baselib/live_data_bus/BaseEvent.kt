package com.gialen.baselib.live_data_bus


open class BaseEvent {
    open var eventCode: Int? = null

    constructor(eventCode: Int?) {
        this.eventCode = eventCode
    }
}
