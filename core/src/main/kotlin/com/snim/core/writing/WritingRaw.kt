package com.snim.core.writing

import com.snim.core.Service
import com.snim.core.ServiceContainer
import org.springframework.data.cassandra.core.cql.PrimaryKeyType
import org.springframework.data.cassandra.core.mapping.*


@Table(value = "writing")
data class WritingRaw(
    @field:PrimaryKey
    val pk: WritingKey,

    @field:Column(value = "data")
    val data: WritingData,

    // TODO : content 형 writing 으로 확장 시 구현
    // @field:Column(value = "content")
    // val content: WritingContent
)

@PrimaryKeyClass
data class WritingKey(
    @PrimaryKeyColumn(value = "service", type = PrimaryKeyType.PARTITIONED, ordinal = 0)
    @CassandraType(type = CassandraType.Name.TEXT)
    private val service: Service,

    @PrimaryKeyColumn(value = "partition_id", type = PrimaryKeyType.PARTITIONED, ordinal = 1)
    @CassandraType(type = CassandraType.Name.TEXT)
    private val partitionId: String,

    @PrimaryKeyColumn(value = "kind", type = PrimaryKeyType.CLUSTERED, ordinal = 0)
    @CassandraType(type = CassandraType.Name.TEXT)
    val kind: String,

    @PrimaryKeyColumn(value = "id", type = PrimaryKeyType.CLUSTERED, ordinal = 1)
    @CassandraType(type = CassandraType.Name.BIGINT)
    val id: Long?,

    ) : ServiceContainer, WritingPartitioning {
    override fun getService() = service
    override fun getPartitionId() = partitionId
    override fun getWritingId() = id
}

data class WritingData(
    val title: String,
    val body: String
)