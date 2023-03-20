package com.snim.core.writing

import com.snim.core.DepthBearing
import com.snim.core.Partitioning
import com.snim.core.SlotBased
import org.apache.commons.lang3.StringUtils

interface WritingPartitioning : Partitioning, SlotBased, DepthBearing {
    fun getWriter(): String {
        if (StringUtils.countMatches(getPartitionId(), SEPARATOR) == 1) {
            return StringUtils.substring(getPartitionId(), indexOfSlot2WriterId() + 1)
        }
        return StringUtils.substringBetween(getPartitionId(), SEPARATOR, SEPARATOR)
    }

    fun getWritingId(): Long?

    companion object {
        const val SEPARATOR = "-"
    }

    override fun getSlot(): String = StringUtils.substringBefore(getPartitionId(), SEPARATOR)

    override fun getDepth() = StringUtils.countMatches(getPartitionId(), SEPARATOR)

    private fun indexOfSlot2WriterId(): Int = StringUtils.indexOf(getPartitionId(), SEPARATOR)
}