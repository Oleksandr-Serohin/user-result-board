package com.info.user.result.board.comparators;


import com.info.user.result.board.entity.UserResult;

import java.util.Comparator;

public class CompareByResult implements Comparator<UserResult> {

    @Override
    public int compare(UserResult o1, UserResult o2) {
        return Integer.compare(o2.getResult(), o1.getResult());
    }
}
