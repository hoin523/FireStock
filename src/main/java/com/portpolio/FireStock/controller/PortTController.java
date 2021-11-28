package com.portpolio.FireStock.controller;

import com.portpolio.FireStock.service.PortTService;
import com.portpolio.FireStock.vo.ReqBodyFormat;
import com.portpolio.FireStock.vo.ResponseInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1//portpolio")
public class PortTController {

    @Autowired
    PortTService portTService;

    /** 회원 가입시에 이루어짐*/
    @RequestMapping("/create/polio")
    public ResponseInfo createPolio(
            @RequestParam(name = "userId", required = true, defaultValue = "") String userId
    ) {
        ResponseInfo responseInfo = portTService.createPolio(userId);
        return responseInfo;
    }

    /**포트폴리오 이름 설정 */
    @RequestMapping("/create/polioname")
    public ResponseInfo createPolio(
            @RequestParam(name = "userId", required = true, defaultValue = "") String userId,
            @RequestParam(name = "portPolioName", required = true, defaultValue = "") String portPolioName
    ) {
        ResponseInfo responseInfo = portTService.createPolioName(userId,portPolioName);
        return responseInfo;
    }

    @RequestMapping("/find/stock")
    public ResponseInfo findStock(
            @RequestParam(name = "userId", required = true, defaultValue = "") String userId,
            @RequestParam(name = "type", required = true, defaultValue = "") String type,
            @RequestParam(name = "portPolioName", required = true, defaultValue = "") String portPolioName

    ) {
        ResponseInfo responseInfo = portTService.findStock(type,userId,portPolioName);
        return responseInfo;
    }

    @RequestMapping("/work/stock")
    public ResponseInfo workStock(
            @RequestBody ReqBodyFormat reqBodyFormat,
            @RequestParam(name = "type", required = true, defaultValue = "") String type,
            @RequestParam(name = "userId", required = true, defaultValue = "") String userId,
            @RequestParam(name = "portPolioName", required = true, defaultValue = "") String portPolioName,
            @RequestParam(name = "method", required = true, defaultValue = "") String method /** update, delete */

    ) {
        ResponseInfo responseInfo = new ResponseInfo();
        if (type.equals("domestic")) {
            responseInfo = portTService.workDomesticStock(reqBodyFormat, method, userId, portPolioName);
        } else if (type.equals("overseas")) {
            responseInfo = portTService.workOverseasStock(reqBodyFormat, method, userId, portPolioName);
        } else if (type.equals("isa")) {
            responseInfo = portTService.workISA(reqBodyFormat, method, userId, portPolioName);
        } else if (type.equals("personal")) {
            responseInfo = portTService.workPersonal(reqBodyFormat, method, userId, portPolioName);
        } else if (type.equals("retirement")) {
            responseInfo = portTService.workRetirement(reqBodyFormat, method, userId, portPolioName);
        } else if (type.equals("coin")) {
            responseInfo = portTService.workCoin(reqBodyFormat, method, userId, portPolioName);
        } else if (type.equals("noncurrent")) {
            responseInfo = portTService.workNonCurrent(reqBodyFormat, method, userId, portPolioName);
        } else {
            responseInfo.setReturnCode(-1);
            responseInfo.setReturnMsg("Wrong type!!");
        }
        return responseInfo;
    }

}
