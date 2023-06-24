package com.account.accountbook.common;

import org.springframework.http.ResponseEntity;

import java.util.Map;

// 지네릭 메소드
public class CommonControllerFunc<T> {

    // response Map 반환(공통 메소드)
    public ResponseEntity<Map<String, T>> getResponseMap(Map<String, T> result) {
        return ResponseEntity.ok().header("Content-Type", "application/json;charset=UTF-8").body(result);
    }

    public ResponseEntity<T> getResponseDTO(T result) {
        return ResponseEntity.ok().header("Content-Type", "application/json;charset=UTF-8").body(result);
    }
}
