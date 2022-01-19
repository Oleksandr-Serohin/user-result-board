package com.info.user.result.board.services;

import com.info.user.result.board.comparators.CompareByResult;
import com.info.user.result.board.database.DataBase;
import com.info.user.result.board.entity.UserResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
public class UserResultServices {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserResultServices.class);

    private final DataBase dataBase = new DataBase();

    @Async
    public Future<Boolean> setUserResult(UserResult userResult) {
        final long start = System.currentTimeMillis();
        List<UserResult> userResultByLevel = dataBase.getDataBase().stream()
                .filter(user -> user.getLevel_id() == userResult.getLevel_id())
                .collect(Collectors.toList());
        boolean newBestResult = userResultByLevel.stream().anyMatch(user -> user.getResult() < userResult.getResult());
        if (newBestResult || userResultByLevel.isEmpty()) {
            if(userResultByLevel.size() >= 20) {
                UserResult minResult = userResultByLevel.stream().min(Comparator.comparingInt(UserResult::getResult)).get();
                dataBase.getDataBase().remove(minResult);
            }
            dataBase.getDataBase().add(userResult);
            LOGGER.info("User '{}' set new top result:'{}', for level '{}'",
                    userResult.getUser_id(), userResult.getResult(), userResult.getLevel_id());
            LOGGER.info("Elapsed time: {}", (System.currentTimeMillis() - start));
            return AsyncResult.forValue(true);
        } else {
            LOGGER.info("User '{}' has to lover result:'{}', for level '{}'",
                    userResult.getUser_id(), userResult.getResult(), userResult.getLevel_id());
            LOGGER.info("Elapsed time: {}", (System.currentTimeMillis() - start));
            return AsyncResult.forValue(false);
        }
    }

    @Async
    public CompletableFuture<List<UserResult>> getUserResultByLevelId(int level_id) {
        LOGGER.info("Request to get a list of UserInfo by level id");
        final List<UserResult> listUserinfo = dataBase.getDataBase().stream()
                .filter(userResult -> userResult.getLevel_id() == level_id)
                .sorted(new CompareByResult())
                .collect(Collectors.toList());
        return CompletableFuture.completedFuture(listUserinfo);
    }

    @Async
    public CompletableFuture<List<UserResult>> getUserResultByUserId(int user_id) {
        LOGGER.info("Request to get a list of UserInfo by user id");
        final List<UserResult> listUserinfo = dataBase.getDataBase().stream()
                .filter(userResult -> userResult.getUser_id() == user_id)
                .sorted(new CompareByResult())
                .limit(20)
                .collect(Collectors.toList());
        return CompletableFuture.completedFuture(listUserinfo);
    }

    @Async
    void clearBase() {
        LOGGER.info("Clear base");
        dataBase.getDataBase().clear();
    }
}
