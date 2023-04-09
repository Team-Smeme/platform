package com.snim.core

import com.snim.core.exception.CommonException

interface NumericStringSlotBased : StringSlotBased {
    fun getNo(): Long
    fun getDelimiter(): Int

    override fun getSlot(): String {
        if (getDelimiter() == 0) {
            throw CommonException("delimiter cannot be 0")
        }

        return (getNo() / getDelimiter()).toString()
    }
}