package com.mesbahi.billing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RefreshScope
public class ConsulConfigController {
    @Autowired
    private MyConsulConfig myConsulConfig;
    @Autowired
    private MyVaultConfig myVaultConfig;
    @GetMapping("/myConsul")
    public Map<String,Object> myConfig(){
        return Map.of("ConsulConfig",myConsulConfig,"VaultConfig",myVaultConfig);
    }

    //***************************************************************
//    @Value("${token.AccessTokenTimeOut}")
//    private long accessTokenTimeout;
//    @Value("${token.RefreshTokenTimeOut}")
//    private long refreshTokenTimeout;
//    @GetMapping("/myConsul")
//    public Map<String,Object> myConfig(){
//        return Map.of("accessTokenTimeout",accessTokenTimeout, "refreshTokenTimeout",refreshTokenTimeout);
//    }
    //*****************************************************************
//    @Autowired
//    private MyConsulConfig myConsulConfig;
//    @GetMapping("/myConsul")
//    public Map<String,Object> myConfig(){
//        return Map.of("ConsulConfig",myConsulConfig);
//    }
}
