create table memo_groups
(
    id int primary key auto_increment,
    description varchar(100) not null
);
create table project_steps
(
id int primary key auto_increment,
description varchar(100) not null,
days_to_deadline int not null,
memo_group_id int not null,
foreign key (memo_group_id) references memo_groups (id)
);

alter table memos add column memo_group_id int null;
alter table memos add foreign key (memo_group_id) references memo_groups (id);
