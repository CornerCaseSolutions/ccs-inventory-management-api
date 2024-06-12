package com.ccs.inventorymanagement.config;

import com.ccs.inventorymanagement.service.ClothingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

    public static final String CLOTHING_PATH = "/clothing";

    public static final String ID_VARIABLE = "id";

    public static final String FIND_CLOTHING_BY_ID_PATH = CLOTHING_PATH + "/{" + ID_VARIABLE + "}";
}
