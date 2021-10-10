drop table if exists t_album_category;

create table t_album_category
(
    id          int(10)      not null auto_increment primary key,
    name        varchar(255) not null,
    order_num   int(10)      not null,
    create_time datetime     not null,
    update_time datetime     not null,
    deleted     bit(1)       not null
);

select ac.id          ac_id,
       ac.name        ac_name,
       ac.order_num   ac_order_num,
       ac.create_time ac_create_time,
       ac.update_time ac_update_time,
       ac.deleted     ac_deleted
from t_album_category as ac
where ac.deleted = false;