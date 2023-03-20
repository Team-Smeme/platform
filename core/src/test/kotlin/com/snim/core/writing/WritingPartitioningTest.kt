package com.snim.core.writing

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class WritingPartitioningTest {
    @Nested
    inner class GetWriter {
        @Test
        fun `1 depth writing 의 경우 작가를 정상적으로 추출해낼 수 있다`() {
            assert(WritingMocker.Pk.withWritingIdAndPartitionId().getWriter().equals("daehwan"))
        }

        @Test
        fun `2+ depth writing 의 경우 작가를 정상적으로 추출해낼 수 있다`() {
            assertAll(
                {
                    assert(
                        WritingMocker.Pk.withWritingIdAndPartitionId(partitionId = "1-daehwan-1").getWriter()
                            .equals("daehwan")
                    )
                },
                {
                    assert(
                        WritingMocker.Pk.withWritingIdAndPartitionId(partitionId = "1-daehwan-1-2").getWriter()
                            .equals("daehwan")
                    )
                },
            )
        }
    }

    @Nested
    inner class GetSlot {
        @Test
        fun `numeric String slot 을 추출해낼 수 있다`() {
            assert(WritingMocker.Pk.withWritingIdAndPartitionId(partitionId = "100-daehwan").getSlot().equals("100"))
        }

        @Test
        fun `String slot 을 추출해낼 수 있다`() {
            assert(WritingMocker.Pk.withWritingIdAndPartitionId(partitionId = "abc-daehwan").getSlot().equals("abc"))
        }

        @Test
        fun `1 depth writing 의 slot 을 추출해 낼 수 있다`() {
            assert(WritingMocker.Pk.withWritingIdAndPartitionId().getSlot().equals("1"))
        }

        @Test
        fun `2+ depth writing 의 slot 을 추출해 낼 수 있다`() {
            assertAll(
                {
                    assert(
                        WritingMocker.Pk.withWritingIdAndPartitionId(partitionId = "1-daehwan-1").getSlot().equals("1")
                    )
                },
                {
                    assert(
                        WritingMocker.Pk.withWritingIdAndPartitionId(partitionId = "1-daehwan-1-2").getSlot()
                            .equals("1")
                    )
                },
            )
        }
    }

    @Nested
    inner class GetDepth {
        @Test
        fun `1 depth writing 의 경우 1 depth 가 나온다`() {
            assert(WritingMocker.Pk.withWritingIdAndPartitionId().getDepth() == 1)
        }


        @Test
        fun `2+ depth writing 의 경우 2+ depth 가 나온다`() {
            assertAll(
                {
                    assert(WritingMocker.Pk.withWritingIdAndPartitionId(partitionId = "1-daehwan-1").getDepth() == 2)
                },
                {
                    assert(WritingMocker.Pk.withWritingIdAndPartitionId(partitionId = "1-daehwan-1-3").getDepth() == 3)
                }
            )
        }
    }
}