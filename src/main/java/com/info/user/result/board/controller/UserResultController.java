package com.info.user.result.board.controller;

import com.info.user.result.board.entity.UserResult;
import com.info.user.result.board.services.UserResultServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

@RestController
public class UserResultController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserResultController.class);

    @Autowired
    UserResultServices userResultServices;

    @RequestMapping(value = "/setinfo", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    ResponseEntity<?> setInfo(@RequestBody UserResult userResult) {
        try {
           Boolean setNewResult = userResultServices.setUserResult(userResult).get();
           if(setNewResult){
               return ResponseEntity.status(HttpStatus.CREATED).build();
           }else return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        } catch (final Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(value = "/levelinfo/{level_id}", method = RequestMethod.GET)
    public @ResponseBody
    CompletableFuture<ResponseEntity<?>> getTopResultByLevelId(@PathVariable Integer level_id) {
        if(level_id == null){
            throw new InvalidRequestException("Level ID must not be null!");
        }else
        return userResultServices.getUserResultByLevelId(level_id).<ResponseEntity<?>>thenApply(ResponseEntity::ok)
                .exceptionally(throwableResponseEntityFunction);
    }

    @RequestMapping(value = "/userinfo/{user_id}", method = RequestMethod.GET)
    public @ResponseBody
    CompletableFuture<ResponseEntity<?>> getTopResultByUserId(@PathVariable Integer user_id) {
        if(user_id == null){
            throw new InvalidRequestException("User ID must not be null!");
        }else
        return userResultServices.getUserResultByUserId(user_id).<ResponseEntity<?>>thenApply(ResponseEntity::ok)
                .exceptionally(throwableResponseEntityFunction);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    static class InvalidRequestException extends RuntimeException {
        public InvalidRequestException(String s) {
            super(s);
        }
    }

    private static final Function<Throwable, ResponseEntity<? extends List<UserResult>>> throwableResponseEntityFunction = throwable -> {
        LOGGER.error("Failed to read records.", throwable);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    };
}
