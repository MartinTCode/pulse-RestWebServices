-- Initial schema for students, courses, and results (Epok, StudentITS, Ladok)

CREATE SCHEMA IF NOT EXISTS epok;
CREATE SCHEMA IF NOT EXISTS studentits;
CREATE SCHEMA IF NOT EXISTS ladok;

-- Epok Schema
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

-- StudentITS Schema
CREATE TABLE IF NOT EXISTS studentits.student_account (
    student_id VARCHAR(50) PRIMARY KEY,
    personal_no VARCHAR(20) NOT NULL
);

-- Ladok Schema
CREATE TABLE IF NOT EXISTS ladok.result (
    result_id INT PRIMARY KEY,
    personal_no VARCHAR(20) NOT NULL,
    course_id VARCHAR(50) NOT NULL,
    module_code VARCHAR(50) NOT NULL,
    exam_date DATE NOT NULL,
    grade VARCHAR(5) NOT NULL
);