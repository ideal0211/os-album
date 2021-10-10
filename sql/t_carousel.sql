drop table if exists t_carousel;

create table t_carousel
(
    id          int(10)      not null auto_increment primary key,
    title       varchar(255) not null,
    image       varchar(255) null,
    album_id    int(10)      not null,
    create_time datetime     not null,
    update_time datetime     not null,
    deleted     bit(1)       not null
);

select c.id          c_id,
       c.title       c_title,
       c.image       c_image,
       c.album_id    c_album_id,
       c.create_time c_create_time,
       c.update_time c_update_time,
       c.deleted     c_deleted
from t_carousel as c;