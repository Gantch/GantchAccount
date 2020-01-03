package com.gantch.common;

/**
 * @author lcw332
 * Date 2019-12-26-11:38
 * Description:  nbiot-management , com.gantch.nbiotmanagement.common
 * 封装API的错误码
 **/
public interface IErrorCode {
    long getCode();

    String getMessage();
}
