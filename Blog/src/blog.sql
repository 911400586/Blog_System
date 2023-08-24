create database if not exists Blog_System charset=utf8mb4;
use Blog_System;

drop table if exists user;
drop table if exists blog;
drop table if exists mark;

create table blog (
    blogid int primary key auto_increment,
    title varchar(128),
    content varchar(4096),
    posttime datetime ,
    userid int
);
create table user (
    userid int primary key auto_increment,
    username varchar(20) unique,
    password varchar(20),
    img varchar(128) default "/root/apache-tomcat-8.5.92/webapps/source/img/dog.jpeg"
);
create table mark (
    username varchar(20) ,
    blogid int,
    content varchar(150),
    posttime datetime
);

insert into user (username,password) values("张三","123456");
insert into user (username,password) values("王五","123456");
insert into user (username,password) values("李四","123456");
insert into blog (title,content,posttime,userid) values("这是王五的文章","从今天开始我要好好敲代码！！！",now(),2);
insert into blog (title,content,posttime,userid) values("这是张三的文章","从今天开始我要好好敲代码！！！",now(),1);