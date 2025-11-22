CREATE SCHEMA IF NOT EXISTS epok;

CREATE TABLE IF NOT EXISTS epok.course (
    course_id VARCHAR(50) PRIMARY KEY,
    course_name VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS epok.module (
    module_id INT PRIMARY KEY,
    course_id VARCHAR(50) REFERENCES epok.course(course_id),
    module_code VARCHAR(50) NOT NULL,
    module_name VARCHAR(100) NOT NULL
);