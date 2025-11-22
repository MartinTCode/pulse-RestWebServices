CREATE SCHEMA IF NOT EXISTS studentits;

CREATE TABLE IF NOT EXISTS studentits.student_account (
    student_id VARCHAR(50) PRIMARY KEY,
    personal_no VARCHAR(20) NOT NULL
);