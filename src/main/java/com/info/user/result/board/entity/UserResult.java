package com.info.user.result.board.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class UserResult implements Serializable {

    private int user_id;
    private int level_id;
    private int result;

    @Override
    public String toString() {
        return "{" +
                "user_id='" + user_id + '\'' +
                ", level_id='" + level_id + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
