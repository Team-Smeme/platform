CREATE TABLE writing (
    service varchar,
    partition_id varchar,
    kind varchar,
    id bigint,
    data varchar,
    content varchar,
    PRIMARY KEY ((service, partition_id), kind, id)
) WITH CLUSTERING ORDER BY (kind asc, id desc);