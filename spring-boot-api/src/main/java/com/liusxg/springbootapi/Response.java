package com.liusxg.springbootapi;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by liusxg on 2018/5/10.
 */
@Data
@AllArgsConstructor
public class Response implements Serializable {
    String result;
}
