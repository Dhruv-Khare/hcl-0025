CREATE DATABASE school_db;

USE school_db;

CREATE TABLE students (
    student_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE,
    age INT CHECK (age >= 5),
    city VARCHAR(50) DEFAULT 'Delhi',
    admission_date DATE,
    CHECK (TRIM(name) <> '')
);

DESC students;

SELECT DATABASE();

INSERT INTO students (name, email, age, city, admission_date)
VALUES
('Rahul Sharma', 'rahul@gmail.com', 20, 'Delhi', '2024-07-15'),
('Priya Verma', 'priya@gmail.com', 21, 'Lucknow', '2023-08-10'),
('Amit Kumar', 'amit@gmail.com', 19, 'Ghaziabad', '2024-07-20'),
('Sneha Singh', 'sneha@gmail.com', 22, 'Kanpur', '2022-07-12'),
('Rohit Gupta', 'rohit@gmail.com', 20, 'Noida', '2024-08-01'),
('Anjali Mishra', 'anjali@gmail.com', 21, 'Hardoi', '2023-07-18'),
('Vikas Yadav', 'vikas@gmail.com', 23, 'Agra', '2022-08-05'),
('Neha Pandey', 'neha@gmail.com', 19, 'Varanasi', '2024-07-25'),
('Arjun Singh', 'arjun@gmail.com', 22, 'Meerut', '2023-08-15'),
('Kavita Sharma', 'kavita@gmail.com', 20, 'Delhi', '2024-09-01');

select * from students;

CREATE TABLE marks (
    mark_id INT PRIMARY KEY,
    student_id INT,
    subject VARCHAR(30),
    score INT,
    FOREIGN KEY (student_id) REFERENCES students(student_id)
);

ALTER TABLE students
ADD COLUMN phone INT;

ALTER TABLE students
MODIFY COLUMN phone VARCHAR(15);

ALTER TABLE students
RENAME COLUMN city TO hometown;

CREATE INDEX idx_student_name
ON students(name);

CREATE TABLE students_backup
LIKE students;

DROP TABLE students_backup;

INSERT INTO students
(name, email, age, hometown, admission_date, phone)
VALUES
('Ravi Kumar', 'ravi@gmail.com', 21, 'Delhi', '2024-07-15', '9876543210');

INSERT INTO students
(name, email, age, hometown, admission_date, phone)
VALUES
('Aman Sharma', 'aman@gmail.com', 20, 'Noida', '2024-07-10', '9876543211'),
('Karan Gupta', 'karan1@gmail.com', 21, 'Ghaziabad', '2023-09-15', '9876543214'),
('Ankit Yadav', 'ankit@gmail.com', 23, 'Hardoi', '2022-07-18', '9876543215');

INSERT INTO marks (mark_id, student_id, subject, score)
VALUES
(1, 1, 'Maths', 85),
(2, 2, 'Maths', 90),
(3, 3, 'Maths', 78),
(4, 4, 'Maths', 88),

(5, 1, 'Science', 92),
(6, 2, 'Science', 86),
(7, 3, 'Science', 80),

(8, 1, 'English', 89),
(9, 2, 'English', 95),
(10, 4, 'English', 84);


INSERT INTO students
(name, email, age, hometown, admission_date)
VALUES
('Rahul Sharma', 'rahul@gmail.com', 21, 'Delhi', '2024-07-15');

UPDATE marks
SET score = score + 5
WHERE subject = 'Maths';

SELECT * FROM marks
WHERE subject = 'Maths';

UPDATE students
SET hometown = 'Noida'
WHERE student_id = 2;

SELECT * FROM students
WHERE student_id = 2;

UPDATE students
SET age = age + 1
WHERE hometown = 'Delhi';

SELECT * FROM students
WHERE hometown = 'Delhi';

DELETE FROM students
WHERE student_id = 6;

DELETE FROM marks
WHERE student_id = 6;

DELETE FROM students
WHERE student_id = 6;

SELECT * FROM students
WHERE student_id = 6;

SELECT * FROM marks
WHERE student_id = 6;