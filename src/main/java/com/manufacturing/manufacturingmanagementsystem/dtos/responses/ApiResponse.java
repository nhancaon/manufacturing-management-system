package com.manufacturing.manufacturingmanagementsystem.dtos.responses;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ApiResponse {
    @Builder.Default
    private int code = 200;

    private String message;

    private Object result;
}