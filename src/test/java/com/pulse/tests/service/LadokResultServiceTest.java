package com.pulse.tests.service;

import com.pulse.dao.LadokResultDAO;
import com.pulse.entity.LadokResultEntity;
import com.pulse.service.LadokResultService;
import com.pulse.tests.util.service.ServiceTestData;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link LadokResultService}.
 *
 * These tests use simple anonymous subclasses of {@link LadokResultDAO} to
 * avoid touching a real database and to capture interactions with the DAO.
 */
public class LadokResultServiceTest {

	@Test
	void registerResult_savesAndReturnsResult_whenInputsValid() {

	final String personalNo = ServiceTestData.PERSONAL_NO;
		final String courseId = ServiceTestData.COURSE_ID;
		final String moduleCode = ServiceTestData.MODULE_CODE_1;
		final LocalDate examDate = ServiceTestData.EXAM_DATE;
		final String grade = ServiceTestData.GRADE_A;

	// Holder to capture the entity passed to saveResult
	final LadokResultEntity[] saved = new LadokResultEntity[1];

		LadokResultDAO stubDao = new LadokResultDAO(null) {
			@Override
			public void saveResult(LadokResultEntity result) {
				// capture what was passed in for assertions
				saved[0] = result;
			}
		};

	LadokResultService service = new LadokResultService(stubDao);

	LadokResultEntity returned = service.registerResult(personalNo, courseId, moduleCode, examDate, grade);

		// Verify DAO was called and returned entity matches expected values
		assertNotNull(saved[0], "Expected DAO.saveResult to be called with an entity");
		assertNotNull(returned);
		assertEquals(ServiceTestData.PERSONAL_NO, saved[0].getPersonalNo());
		assertEquals(ServiceTestData.COURSE_ID, saved[0].getCourseId());
		assertEquals(ServiceTestData.MODULE_CODE_1, saved[0].getModuleCode());
		assertEquals(ServiceTestData.EXAM_DATE, saved[0].getExamDate());
		assertEquals(ServiceTestData.GRADE_A, saved[0].getGrade());
	}

	@Test
	void registerResult_throwsIllegalArgumentException_whenPersonalNoMissing() {
	// test value constraints
	final String courseId = "CS101";
	final String moduleCode = "M1";
	final LocalDate examDate = LocalDate.of(2025, 6, 1);
	final String grade = "A";

	LadokResultDAO stubDao = new LadokResultDAO(null) {};
	LadokResultService service = new LadokResultService(stubDao);

	assertThrows(IllegalArgumentException.class,
		() -> service.registerResult(null, courseId, moduleCode, examDate, grade));
	}
}
