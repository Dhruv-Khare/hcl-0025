create database hcltraining_db;
use hcltraining_db;

CREATE TABLE Trainers (
    facultyid INT PRIMARY KEY,
    facName VARCHAR(50),
    designation VARCHAR(50),
    mobile_no VARCHAR(15),
    experience INT,
    salary DECIMAL(10,2)
);

ALTER TABLE trainers
ADD COLUMN age INT CHECK (age >= 0);

create table department(
    dep_id INT primary KEY AUTO_INCREMENT,
    
)
