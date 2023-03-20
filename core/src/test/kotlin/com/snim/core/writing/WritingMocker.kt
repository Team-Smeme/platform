package com.snim.core.writing

class WritingMocker {

    class Pk {
        companion object {
            fun withWritingIdAndPartitionId(
                writingId: Long? = null,
                partitionId: String = "1-daehwan"
            ) = object : WritingPartitioning {
                override fun getWritingId() = writingId
                override fun getPartitionId() = partitionId
            }

        }
    }
}