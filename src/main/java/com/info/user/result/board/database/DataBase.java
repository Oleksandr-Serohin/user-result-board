package com.info.user.result.board.database;

import com.info.user.result.board.entity.UserResult;

import java.util.HashSet;

import java.util.Set;


public class DataBase {

    private static final Set<UserResult> USER_INFOS = new HashSet<>();

    public Set<UserResult> getDataBase() {
        return USER_INFOS;
    }
}
