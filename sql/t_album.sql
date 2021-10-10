drop table if exists t_album;

create table t_album
(
    id          int(10)       not null auto_increment primary key,
    category_id int(10)       not null,
    title       varchar(255)  not null,
    sub_title   varchar(255)  null,
    description varchar(255)  null,
    covers      varchar(255)  null,
    image_json  varchar(1000) null,
    order_num   int(10)       not null,
    create_time datetime      not null,
    update_time datetime      not null,
    deleted     bit(1)        not null
);
alter table t_album
    add column show_on_home bit(1) not null default false after order_num;

select a.id           a_id,
       a.category_id  a_category_id,
       a.title        a_title,
       a.sub_title    a_sub_title,
       a.description  a_description,
       a.covers       a_covers,
       a.image_json   a_image_json,
       a.order_num    a_order_num,
       a.show_on_home a_show_on_home,
       a.create_time  a_create_time,
       a.update_time  a_update_time,
       a.deleted      a_deleted
from t_album as a
where a.deleted = false;