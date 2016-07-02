drop table if exists Dim_Job_Classify;
create table Dim_Job_Classify(
jobclassifyid        bigint not null,
jobclassifyname      varchar(80) not null
);

drop table if exists Dim_Job_Status;
create table Dim_Job_Status(
jobstatusid        bigint not null,
jobstatusname      varchar(80) not null
);

INSERT INTO Dim_Job_Classify VALUES(101,'美工设计');
INSERT INTO Dim_Job_Classify VALUES(102,'软件开发');
INSERT INTO Dim_Job_Classify VALUES(103,'文档处理');
INSERT INTO Dim_Job_Classify VALUES(104,'其他类型');


INSERT INTO Dim_Job_Status VALUES(201,'待审核');
INSERT INTO Dim_Job_Status VALUES(202,'发布成功');
INSERT INTO Dim_Job_Status VALUES(203,'竞标中');
INSERT INTO Dim_Job_Status VALUES(204,'开发中');
INSERT INTO Dim_Job_Status VALUES(205,'开发完成');
INSERT INTO Dim_Job_Status VALUES(206,'验收中');
INSERT INTO Dim_Job_Status VALUES(207,'验收完成');
INSERT INTO Dim_Job_Status VALUES(208,'待开发者评价');
INSERT INTO Dim_Job_Status VALUES(209,'待需求方评价');
INSERT INTO Dim_Job_Status VALUES(210,'完成');