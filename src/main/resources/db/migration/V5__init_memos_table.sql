create table memos
(
    id int primary key auto_increment,
    description varchar(100) not null,
    done bit
);

alter table tasks add column memo_id int null;
alter table tasks add foreign key (memo_id) references memos (id);
