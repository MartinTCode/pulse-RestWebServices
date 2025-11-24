package com.pulse.tests.service;

import com.pulse.dao.StudentitsStudentAccountDAO;
import com.pulse.entity.StudentitsStudentAccountEntity;
import com.pulse.service.StudentitsStudentService;
import com.pulse.tests.util.service.ServiceTestData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link StudentitsStudentService}.
 */
public class StudentServiceTest {

	@Test
	void getStudentById_returnsEntity_whenIdIsValid() {
	final String studentId = ServiceTestData.STUDENT_ID;
	final String personalNo = ServiceTestData.PERSONAL_NO;

	StudentitsStudentAccountEntity entity = ServiceTestData.studentAccount();

		StudentitsStudentAccountDAO stubDao = new StudentitsStudentAccountDAO(null) {
			@Override
			public StudentitsStudentAccountEntity findByStudentId(String id) {
				if (studentId.equals(id)) return entity;
				return null;
			}
		};

		StudentitsStudentService service = new StudentitsStudentService(stubDao);

		StudentitsStudentAccountEntity result = service.getStudentById(studentId);

		assertNotNull(result);
		assertEquals(studentId, result.getStudentId());
		assertEquals(personalNo, result.getPersonalNo());
	}

	@Test
	void getPersonalNumber_throws_whenStudentNotFound() {
	// test value constraints
	final String missingStudentId = ServiceTestData.MISSING_STUDENT_ID;

		StudentitsStudentAccountDAO stubDao = new StudentitsStudentAccountDAO(null) {
			@Override
			public StudentitsStudentAccountEntity findByStudentId(String id) {
				return null; // simulate missing student
			}
		};

		StudentitsStudentService service = new StudentitsStudentService(stubDao);

		assertThrows(IllegalArgumentException.class, () -> service.getPersonalNumber(missingStudentId));
	}
}
