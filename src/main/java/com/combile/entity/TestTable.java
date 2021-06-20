package com.combile.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@TableName("test1")
@Data
public class TestTable {
    private Long id;

    /**
     * 名称
     */
    private String name;

    private String code;

    private String name2;

    private String name3;

    private String name4;

    private String name5;

    private String name6;

    private String name7;

    private String name8;

    private String name9;

}