-- Insert testdata for Epok (Courses + Modules)

-- Courses
INSERT INTO epok.course (course_id, course_name) VALUES
('D0031N', 'Enterprise Architecture & SOA'),
('D0025E', 'Data Mining'),
('D0023E', 'Forskningsmetoder inom informationssystem');

-- Modules for D0031N (EA & SOA)

INSERT INTO epok.module (course_id, module_code, module_name) VALUES
('D0031N', '0005', 'Inlämningsuppgifter'),
('D0031N', '0006', 'Projekt');

-- Modules for D0025E (Data Mining)

INSERT INTO epok.module (course_id, module_code, module_name) VALUES
('D0025E', '0001', 'Projektuppgifter / Individuella uppgifter'),
('D0025E', '0002', 'Tentamen');

-- Modules for D0023E (Forskningsmetoder)

INSERT INTO epok.module (course_id, module_code, module_name) VALUES
('D0023E', '0003', 'Individuell uppgift'),
('D0023E', '0004', 'Projekt');