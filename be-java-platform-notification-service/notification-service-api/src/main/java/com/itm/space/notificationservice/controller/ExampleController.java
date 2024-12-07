package com.itm.space.notificationservice.controller;

import com.itm.space.notificationservice.dto.request.ExampleRequest;
import com.itm.space.notificationservice.dto.response.ExampleResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.itm.space.notificationservice.constant.ApiConstant.EXAMPLE_URL;

@RequestMapping(EXAMPLE_URL)
public interface ExampleController {

    @PostMapping
    ExampleResponse exampleRequest(@RequestBody ExampleRequest request);
}
