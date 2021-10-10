drop table if exists t_user;

create table t_user
(
    id          int(10)      not null auto_increment primary key,
    nickname    varchar(255) null,
    avatar      varchar(255) null,
    open_id     varchar(255) null,
    role        int(2)       not null,
    create_time datetime     not null,
    update_time datetime     not null,
    deleted     bit(1)       not null
) charset utf8
  collate utf8_general_ci;

select u.id          u_id,
       u.nickname    u_nickname,
       u.avatar      u_avatar,
       u.open_id     u_open_id,
       u.role        u_role,
       u.create_time u_create_time,
       u.update_time u_update_time,
       u.deleted     u_deleted
from t_user as u;