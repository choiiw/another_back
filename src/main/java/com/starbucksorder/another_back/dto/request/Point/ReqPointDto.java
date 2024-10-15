package com.starbucksorder.another_back.dto.request.Point;

import com.starbucksorder.another_back.entity.Point;
import lombok.Data;

import java.util.Date;

@Data
public class ReqPointDto {
    private String phoneNumber;
    private Long cartId;
    private Long pointType;
    private int total; // 포트원에서 주는 총금액
//    private int orderAmount;
}
