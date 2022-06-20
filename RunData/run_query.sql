USE RunData;

CREATE TABLE running_log (
ID int PRIMARY KEY NOT NULL AUTO_INCREMENT,
Run_date date,
Run_time time,
Run_location varchar(100),
Run_distance float,
Run_duration time,
Run_type varchar(100)
);

SELECT * FROM running_log;

DROP TABLE running_log;

DROP DATABASE RunData;

CREATE DATABASE RunData;

