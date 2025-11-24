package com.pulse.tests.util.service;

import com.pulse.entity.EpokCourseEntity;
import com.pulse.entity.EpokModuleEntity;
import com.pulse.entity.StudentitsStudentAccountEntity;
import com.pulse.entity.LadokResultEntity;

import java.time.LocalDate;
import java.util.List;

/**
 * Service-layer test fixtures: constants and factories used by service unit tests.
 */
public final class ServiceTestData {
    private ServiceTestData() {}

    public static final String COURSE_ID = "CS101";
    public static final String COURSE_NAME = "Intro to Testing";

    public static final String MODULE_CODE_1 = "M1";
    public static final String MODULE_NAME_1 = "Module One";

    public static final String MODULE_CODE_2 = "M2";
    public static final String MODULE_NAME_2 = "Module Two";

    public static final String STUDENT_ID = "s123";
    public static final String PERSONAL_NO = "900101-1234";
    public static final String MISSING_STUDENT_ID = "unknown";

    public static final LocalDate EXAM_DATE = LocalDate.of(2025, 6, 1);
    public static final String GRADE_A = "A";

    public static EpokCourseEntity course() {
        return new EpokCourseEntity(COURSE_ID, COURSE_NAME);
    }

    public static EpokModuleEntity module1() {
        return new EpokModuleEntity(MODULE_CODE_1, MODULE_NAME_1, course());
    }

    public static EpokModuleEntity module2() {
        return new EpokModuleEntity(MODULE_CODE_2, MODULE_NAME_2, course());
    }

    public static List<EpokModuleEntity> modulesForCourse() {
        return List.of(module1(), module2());
    }

    public static StudentitsStudentAccountEntity studentAccount() {
        return new StudentitsStudentAccountEntity(STUDENT_ID, PERSONAL_NO);
    }

    public static LadokResultEntity ladokResult() {
        return new LadokResultEntity(PERSONAL_NO, COURSE_ID, MODULE_CODE_1, EXAM_DATE, GRADE_A);
    }
}
