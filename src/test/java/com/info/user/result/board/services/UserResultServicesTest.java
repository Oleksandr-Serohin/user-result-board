package com.info.user.result.board.services;

import com.info.user.result.board.entity.UserResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserResultServicesTest {
    UserResultServices userResultServices = new UserResultServices();
    UserResult userResult1 = new UserResult(1, 1, 1);
    UserResult userResult2 = new UserResult(1, 1, 2);
    UserResult userResult3 = new UserResult(2, 1, 4);
    UserResult userResult4 = new UserResult(3, 2, 3);
    UserResult userResult5 = new UserResult(1, 2, 3);

    @AfterEach
    void afterEach() {
        userResultServices.clearBase();
    }

    @Test
    void shouldReturnTrueWhenSetNewUserResult() throws ExecutionException, InterruptedException {
        Boolean result = userResultServices.setUserResult(userResult1).get();
        assertTrue(result);
    }

    @Test
    void returnFalseWhenUserResultLowestOldOrSame() throws ExecutionException, InterruptedException {
        Boolean bestResult = userResultServices.setUserResult(userResult2).get();
        Boolean worseResult = userResultServices.setUserResult(userResult1).get();

        assertTrue(bestResult);
        assertFalse(worseResult);
    }

    @Test
    void returnSortedListUserResultsByLevel() throws ExecutionException, InterruptedException {
        userResultServices.setUserResult(userResult1);
        userResultServices.setUserResult(userResult3);
        userResultServices.setUserResult(userResult4);
        List<UserResult> userResults = userResultServices.getUserResultByLevelId(1).get();

        assertFalse(userResults.contains(userResult4));
        assertEquals(userResult3, userResults.get(0));
        assertEquals(userResult1, userResults.get(1));
    }

    @Test
    void returnListUserWithSetIdSortedByResults() throws ExecutionException, InterruptedException {
        userResultServices.setUserResult(userResult2);
        userResultServices.setUserResult(userResult3);
        userResultServices.setUserResult(userResult5);
        List<UserResult> userResults = userResultServices.getUserResultByUserId(1).get();

        assertFalse(userResults.contains(userResult3));
        assertEquals(userResult5, userResults.get(0));
        assertEquals(userResult2, userResults.get(1));
    }
}