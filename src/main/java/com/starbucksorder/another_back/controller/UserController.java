package com.starbucksorder.another_back.controller;

import com.starbucksorder.another_back.aspect.annotation.Log;
import com.starbucksorder.another_back.dto.admin.request.ReqAdminUserDto;
import com.starbucksorder.another_back.entity.User;
import com.starbucksorder.another_back.service.PointService;
import com.starbucksorder.another_back.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// HACK: 매핑명 변경 /points -> point
public class UserController {

    @Autowired
    private PointService pointService;

    @Autowired
    private UserService userService;

    // HACK: 응답 데이터 확인
    // 포인트 사용을 위한 조회요청
    @Log
    @GetMapping("/point/user/reward")
    public ResponseEntity<?> getRewardPoint(@RequestParam String phoneNumber) {
        return ResponseEntity.ok().body(pointService.getUserIdByPhoneNumber(phoneNumber));
    }

    // NOTE: 관리자 회원관리

    @ApiOperation(value = "사용자 등록")
    @PostMapping("/admin/user")
    public ResponseEntity<?> addUser(@RequestBody ReqAdminUserDto dto) {
        return ResponseEntity.ok().body(userService.addUser(dto));
    }

    @ApiOperation(value = "사용자 전체 조회")
    @GetMapping("/admin/user")
    public ResponseEntity<?> getUserAll() {
        return ResponseEntity.ok().body(userService.getUserAll());
    }
    @ApiOperation(value = "사용자 단 건 조회 상세보기")
    @GetMapping("/admin/user/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        userService.getUserById(userId);
        return ResponseEntity.ok().body(null);
    }

    @ApiOperation(value = "회원 삭제")
    @DeleteMapping
    @Log
    public ResponseEntity<?> deleteUser(@RequestParam List<Long> userIds) {
        return ResponseEntity.ok().body(null);
    }
    @ApiOperation(value = "회원 수정")
    @PatchMapping
    public ResponseEntity<?> updateUser(@RequestBody ReqAdminUserDto dto) {
        return ResponseEntity.ok().body(null);
    }
}