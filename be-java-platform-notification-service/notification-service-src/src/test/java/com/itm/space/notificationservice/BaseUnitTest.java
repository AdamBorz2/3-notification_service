package com.itm.space.notificationservice;

import com.itm.space.notificationservice.util.JsonParserUtil;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public abstract class BaseUnitTest {

    protected static JsonParserUtil jsonParserUtil = new JsonParserUtil();
}