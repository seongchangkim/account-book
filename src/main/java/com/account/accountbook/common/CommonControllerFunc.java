package com.account.accountbook.common;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public class CommonControllerFunc {

    // response Map 반환(공통 메소드)
    public ResponseEntity<Map<String, Object>> getResponseMap(Map<String, Object> result) {
        return ResponseEntity.ok().header("Content-Type", "application/json;charset=UTF-8").body(result);
    }

    //    public ResponseEntity<Object> getResponseDTO(Object result) {
//        return ResponseEntity.ok().header("Content-Type", "application/json;charset=UTF-8").body(result);
//    }
}
