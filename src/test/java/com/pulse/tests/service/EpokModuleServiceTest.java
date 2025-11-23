package com.pulse.tests.service;

import com.pulse.dao.EpokModuleDAO;
import com.pulse.entity.EpokModuleEntity;
import com.pulse.service.EpokModuleService;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import com.pulse.tests.util.service.ServiceTestData;

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
		// test value constraints (use TestData factories/constants)
	EpokModuleEntity m1 = ServiceTestData.module1();
	EpokModuleEntity m2 = ServiceTestData.module2();

		EpokModuleDAO stubDao = new EpokModuleDAO(null) {
			@Override
			public List<EpokModuleEntity> findModulesByCourseId(String id) {
				// simple stub behaviour: return two modules when the requested id
				// equals the test course id, otherwise return an empty list
				if (ServiceTestData.COURSE_ID.equals(id)) {
					return Arrays.asList(m1, m2);
				}
				return List.of();
			}
		};

		EpokModuleService service = new EpokModuleService(stubDao);

		// Use the shared TestData constant when calling the service
	List<EpokModuleEntity> result = service.getModulesByCourseId(ServiceTestData.COURSE_ID);

		assertNotNull(result);
		assertEquals(2, result.size());
		// Assert using the TestData constants to avoid duplicating literals
	assertEquals(ServiceTestData.MODULE_CODE_1, result.get(0).getModuleCode());
	assertEquals(ServiceTestData.MODULE_CODE_2, result.get(1).getModuleCode());
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
