drop table if exists `task` CASCADE;
drop table if exists `taskList` CASCADE;
 
create table if not exists taskList (id int PRIMARY KEY AUTO_INCREMENT, `name` varchar(255));
create table if not exists task(id int PRIMARY KEY AUTO_INCREMENT, name varchar(255) not null, dueDate varchar(255), completed boolean not null, task_list_id bigint);