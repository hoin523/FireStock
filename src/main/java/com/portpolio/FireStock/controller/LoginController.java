package com.portpolio.FireStock.controller;

import com.portpolio.FireStock.service.LoginService;
import com.portpolio.FireStock.service.PortTService;
import com.portpolio.FireStock.vo.LoginRequest;
import com.portpolio.FireStock.vo.ResponseInfo;
import com.portpolio.FireStock.vo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    LoginService loginService;

    @Autowired
    PortTService portTService;
    /**
     *
     * 회원가입
     *
     * */
    @PostMapping("/join")
    public ResponseInfo createUser(@RequestBody User user){
        ResponseInfo responseInfo = new ResponseInfo();
        String id = loginService.createUser(user);
        portTService.createPolio(id);
        responseInfo.setReturnCode(0);
        responseInfo.setReturnMsg("Success");
        responseInfo.setData(id);
        return responseInfo;
    }


    /**
     *
     * 로그인시 토큰을 return 받음
     *
     *  */
    @PostMapping("/login")
    public ResponseInfo login(@RequestBody LoginRequest loginRequest){
        ResponseInfo responseInfo = loginService.createToken(loginRequest);
        return responseInfo;
    }

//    @GetMapping("/info")
//    public ResponseEntity<UserResponse> getUserFromToken(HttpServletRequest request) {
//        String name = (String) request.getAttribute("name");
//        User user = userService.findByName((String) request.getAttribute("name"));
//        return ResponseEntity.ok().body(UserResponse.of(user));
//    }
}
