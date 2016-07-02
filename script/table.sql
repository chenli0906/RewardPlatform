drop table if exists User_Info;
create table User_Info(
userid              bigint not null auto_increment,
username            varchar(80) not null,
password            varchar(80) not null,
role                varchar(80) not null DEFAULT 'user',
phonenum            varchar(80),
email               varchar(100),
account             double default 0,
tag                 text,
introduction        text,
registtime          timestamp,
publishingnum       bigint default 0,
publishednum        bigint default 0,
developednum        bigint default 0,
developingnum       bigint default 0,
avgpublishscore     double,
avgdevelopscore     double,
PRIMARY KEY  (userid),
UNIQUE (username)
) engine innodb;
ALTER TABLE User_Info ADD INDEX dataindex_key (userid, username);

drop table if exists Job_Info;
create table Job_Info(
jobid                bigint not null auto_increment,
jobclassifyid        bigint not null,
jobname              varchar(255) not null,
jobdescription       text,
fee                  double,
jobcommittime        timestamp,
jobexpectfinishtime  timestamp,
jobpublishtime       timestamp,
jobstarttime         timestamp,
jobfinishtime        timestamp,
jobstatusid          bigint DEFAULT 201,
publishuserid        bigint not null,
developuserid        bigint,
tenderidlist         text,
PRIMARY KEY  (jobid)
) engine innodb;
ALTER TABLE Job_Info ADD INDEX dataindex_key (jobid, jobclassifyid);

drop table if exists User_Judgement_Info;
create table User_Judgement_Info(
jobid                 bigint,
publisherid           bigint not null,
developerid           bigint not null,
publisherJudgeTime    timestamp,
publisherJudge        text,
developerScore        double,
developerJudgeTime    timestamp,
developerJudge        text,
publisherScore        double
);


