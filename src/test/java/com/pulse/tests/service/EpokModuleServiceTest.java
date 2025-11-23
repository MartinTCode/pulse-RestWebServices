package com.pulse.tests.service;

import com.pulse.dao.EpokModuleDAO;
import com.pulse.entity.EpokCourseEntity;
import com.pulse.entity.EpokModuleEntity;
import com.pulse.service.EpokModuleService;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EpokModuleServiceTest {

	/**
	 * Unit tests for EpokModuleService.
	 *
	 * These tests use a small anonymous stub of EpokModuleDAO to avoid hitting a real
	 * database. The stub returns deterministic values so the service behavior can be
	 * asserted in isolation.
	 */

	@Test
	void getModulesByCourseId_returnsModules_whenCourseIdIsValid() {
		//test value constraints
		final String courseId = "CS101";
		final String courseName = "Intro to Testing";

		final String moduleCode1 = "M1";
		final String moduleName1 = "Module One";
		final String moduleCode2 = "M2";
		final String moduleName2 = "Module Two";

		EpokCourseEntity course = new EpokCourseEntity(courseId, courseName);

		EpokModuleEntity m1 = new EpokModuleEntity(moduleCode1, moduleName1, course);
		EpokModuleEntity m2 = new EpokModuleEntity(moduleCode2, moduleName2, course);

		EpokModuleDAO stubDao = new EpokModuleDAO(null) {
			@Override
			public List<EpokModuleEntity> findModulesByCourseId(String id) {
				// simple stub behaviour: return two modules when the requested id
				// equals the test course id, otherwise return an empty list
				if (courseId.equals(id)) {
					return Arrays.asList(m1, m2);
				}
				return List.of();
			}
		};

		EpokModuleService service = new EpokModuleService(stubDao);

		// Use the local constant when calling the service
		List<EpokModuleEntity> result = service.getModulesByCourseId(courseId);

		assertNotNull(result);
		assertEquals(2, result.size());
		// Assert using the extracted constants to avoid duplicating literals
		assertEquals(moduleCode1, result.get(0).getModuleCode());
		assertEquals(moduleCode2, result.get(1).getModuleCode());
	}


    /**
     * When a null courseId is passed, the service is expected to validate input
     * and throw an IllegalArgumentException. The stub DAO here will not be invoked
     * if validation happens correctly.
     */
    @Test
    void getModulesByCourseId_throwsIllegalArgumentException_whenCourseIdIsNull() {
		EpokModuleDAO stubDao = new EpokModuleDAO(null) {
			@Override
			public List<EpokModuleEntity> findModulesByCourseId(String courseId) {
				return List.of();
			}
		};

		EpokModuleService service = new EpokModuleService(stubDao);

		assertThrows(IllegalArgumentException.class, () -> service.getModulesByCourseId(null));
	}


    /**
     * Blank (whitespace-only) course id should also be rejected by input validation.
     */
    @Test
    void getModulesByCourseId_throwsIllegalArgumentException_whenCourseIdIsBlank() {
		EpokModuleDAO stubDao = new EpokModuleDAO(null) {
			@Override
			public List<EpokModuleEntity> findModulesByCourseId(String courseId) {
				return List.of();
			}
		};

		EpokModuleService service = new EpokModuleService(stubDao);

		assertThrows(IllegalArgumentException.class, () -> service.getModulesByCourseId("   "));
	}
}
