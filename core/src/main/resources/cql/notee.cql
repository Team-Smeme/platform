CREATE TABLE topic_notification (
    slot int,
    notification_key varchar,
    topic varchar,
    no bigint,
    device_token varchar,
    registered_at bigint,
    PRIMARY KEY ((slot, notification_key, topic), no, device_token)
) WITH CLUSTERING ORDER BY (no asc);

CREATE TABLE cycle_notification (
    slot int,
    notification_key varchar,
    hour int,
    minute int,
    day varchar,
    no bigint,
    device_token varchar,
    registered_at bigint,
    PRIMARY KEY ((slot, notification_key, hour, minute, day), no, device_token)
) WITH CLUSTERING ORDER BY (no asc);

CREATE TABLE notification_per_user (
    user_id varchar,
    service varchar,
    notification_method varchar,
    notification_type varchar,
    device_token varchar,
    data varchar,
    PRIMARY KEY ((user_id, service), notification_method, notification_type, device_token)
);