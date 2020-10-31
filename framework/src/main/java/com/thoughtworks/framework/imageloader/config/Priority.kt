package com.thoughtworks.framework.imageloader.config

enum class Priority(val value: Int) {
    /**
     * 立即，最高优先级
     */
    IMMEDIATE(1),

    /**
     * 高优先级
     */
    HIGH(2),

    /**
     * 一般优先级
     */
    NORMAL(3),

    /**
     * 低优先级
     */
    LOW(4),
}
