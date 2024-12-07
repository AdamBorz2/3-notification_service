package com.itm.space.notificationservice.dto.response;

public record HttpErrorResponse(int code, String type, String message) {
}
