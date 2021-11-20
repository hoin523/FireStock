package com.portpolio.FireStock.service;

import com.portpolio.FireStock.repository.PortPolioMongoRespository;
import com.portpolio.FireStock.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class PortTService {

    @Autowired
    PortPolioMongoRespository portPolioMongoRespository;

    public ResponseInfo createPolio(String userId) {
        Boolean isExist = portPolioMongoRespository.existsBy_id(userId);
        if (!isExist) {
            portPolioMongoRespository.save(new PortPolio(userId, new HashMap<>()));
            return new ResponseInfo(0, "Create Success");
        } else {
            return new ResponseInfo(-1, "userId Already Exists!");
        }
    }

    public ResponseInfo createPolioName(String userId, String portPolioName) {
        PortPolio portPolio = portPolioMongoRespository.findBy_id(userId);
        HashMap<String, PortPolioDetail> map = portPolio.getPortPolioDetailMap();
        map.put(portPolioName,new PortPolioDetail(new ArrayList<>(),new ArrayList<>(),new ArrayList<>(),new ArrayList<>(),new ArrayList<>(),new ArrayList<>(),new ArrayList<>()));
        portPolio.setPortPolioDetailMap(map);
        portPolioMongoRespository.save(portPolio);
        return new ResponseInfo(0,"Success");
    }

    public ResponseInfo findStock(String type, String userId, String portPolioName) {
        PortPolio polio = portPolioMongoRespository.findBy_id(userId);
        PortPolioDetail portPolioDetail = polio.getPortPolioDetailMap().get(portPolioName);
        if (type.equals("all")) {
            return new ResponseInfo(0, "Success", portPolioDetail);
        } else if (type.equals("domestic")) {
            return new ResponseInfo(0, "Success", portPolioDetail.getDomesticStocks());
        } else if (type.equals("overseas")) {
            return new ResponseInfo(0, "Success", portPolioDetail.getOverseasStocks());
        } else if (type.equals("isa")) {
            return new ResponseInfo(0, "Success", portPolioDetail.getIsas());
        } else if (type.equals("personal")) {
            return new ResponseInfo(0, "Success", portPolioDetail.getPersonalPensions());
        } else if (type.equals("retirement")) {
            return new ResponseInfo(0, "Success", portPolioDetail.getRetirementPensions());
        } else if (type.equals("coin")) {
            return new ResponseInfo(0, "Success", portPolioDetail.getCryptoCurrencys());
        } else if (type.equals("noncurrent")) {
            return new ResponseInfo(0, "Success", portPolioDetail.getNonCurrentAssets());
        } else {
            return new ResponseInfo(0, "Wrong type!!");
        }
    }

    public ResponseInfo workDomesticStock(ReqBodyFormat reqBodyFormat, String method, String userId, String portPolioName) {
        PortPolio polio = portPolioMongoRespository.findBy_id(userId);
        HashMap<String,PortPolioDetail> map = polio.getPortPolioDetailMap();
        PortPolioDetail portPolioDetail = map.get(portPolioName);

        List<DomesticStock> domesticStockList = new ArrayList<>();
        if(!portPolioDetail.getDomesticStocks().isEmpty()){
            domesticStockList = portPolioDetail.getDomesticStocks();
        }

        DomesticStock domesticStock = new DomesticStock();
        domesticStock.setStockName(reqBodyFormat.getStockName());
        domesticStock.setStockCode(reqBodyFormat.getStockCode());
        domesticStock.setAveragePrice(reqBodyFormat.getAveragePrice());
        domesticStock.setCurrentPrice(reqBodyFormat.getCurrentPrice());
        domesticStock.setQuantity(reqBodyFormat.getQuantity());
        domesticStock.setTodaysFluctuationRate(reqBodyFormat.getTodaysFluctuationRate());
        domesticStock.setYield(reqBodyFormat.getYield());
        domesticStock.setValuationLoss(reqBodyFormat.getValuationLoss());
        domesticStock.setPurchaseAmount(reqBodyFormat.getPurchaseAmount());
        domesticStock.setBalanceAssessment(reqBodyFormat.getBalanceAssessment());

        if (method.equals("delete")) {
            int idx = domesticStockList.indexOf(domesticStock);
            if (idx == -1) {
                return new ResponseInfo(-1, "Delete Fail");
            }
            domesticStockList.remove(idx);
        } else if (method.equals("update")) {
            /** update */
            /** 이미 존재한다면 기존에 있던것을 삭제하고 새로운 데이터를 집어넣음*/
            int idx = domesticStockList.indexOf(domesticStock);
            if (idx == -1) {
                domesticStockList.add(domesticStock);
            } else {
                domesticStockList.remove(idx);
                domesticStockList.add(domesticStock);
            }
        } else {
            return new ResponseInfo(-1, "Wrong method");
        }
        portPolioDetail.setDomesticStocks(domesticStockList);
        map.put(portPolioName,portPolioDetail);
        portPolioMongoRespository.save(new PortPolio(userId,map));
        return new ResponseInfo(0, method + " 성공", domesticStockList);
    }

    public ResponseInfo workOverseasStock(ReqBodyFormat reqBodyFormat, String method, String userId, String portPolioName) {
        PortPolio polio = portPolioMongoRespository.findBy_id(userId);
        HashMap<String,PortPolioDetail> map = polio.getPortPolioDetailMap();
        PortPolioDetail portPolioDetail = map.get(portPolioName);

        List<OverseasStock> overseasStockList = new ArrayList<>();
        if(!portPolioDetail.getOverseasStocks().isEmpty()){
            overseasStockList = portPolioDetail.getOverseasStocks();
        }

        OverseasStock overseasStock = new OverseasStock();
        overseasStock.setStockName(reqBodyFormat.getStockName());
        overseasStock.setAveragePrice(reqBodyFormat.getAveragePrice());
        overseasStock.setCurrentPrice(reqBodyFormat.getCurrentPrice());
        overseasStock.setQuantity(reqBodyFormat.getQuantity());
        overseasStock.setTodaysFluctuationRate(reqBodyFormat.getTodaysFluctuationRate());
        overseasStock.setYield(reqBodyFormat.getYield());
        overseasStock.setValuationLoss(reqBodyFormat.getValuationLoss());
        overseasStock.setPurchaseAmount(reqBodyFormat.getPurchaseAmount());
        overseasStock.setBalanceAssessment(reqBodyFormat.getBalanceAssessment());

        if (method.equals("delete")) {
            int idx = overseasStockList.indexOf(overseasStock);
            if (idx == -1) {
                return new ResponseInfo(-1, "Delete Fail");
            }
            overseasStockList.remove(idx);
        } else if (method.equals("update")) {
            /** update */
            int idx = overseasStockList.indexOf(overseasStock);
            if (idx == -1) {
                overseasStockList.add(overseasStock);
            } else {
                overseasStockList.remove(idx);
                overseasStockList.add(overseasStock);
            }
        } else {
            return new ResponseInfo(-1, "Wrong method");
        }

        portPolioDetail.setOverseasStocks(overseasStockList);
        map.put(portPolioName,portPolioDetail);
        portPolioMongoRespository.save(new PortPolio(userId,map));
        return new ResponseInfo(0, method + " 성공", overseasStockList);
    }

    public ResponseInfo workISA(ReqBodyFormat reqBodyFormat, String method, String userId,String portPolioName) {
        PortPolio polio = portPolioMongoRespository.findBy_id(userId);
        HashMap<String,PortPolioDetail> map = polio.getPortPolioDetailMap();
        PortPolioDetail portPolioDetail = map.get(portPolioName);

        List<ISA> isaList  = new ArrayList<>();
        if(!portPolioDetail.getIsas().isEmpty()){
            isaList = portPolioDetail.getIsas();
        }

        ISA isa = new ISA();
        isa.setStockName(reqBodyFormat.getStockName());
        isa.setStockCode(reqBodyFormat.getStockCode());
        isa.setAveragePrice(reqBodyFormat.getAveragePrice());
        isa.setCurrentPrice(reqBodyFormat.getCurrentPrice());
        isa.setQuantity(reqBodyFormat.getQuantity());
        isa.setTodaysFluctuationRate(reqBodyFormat.getTodaysFluctuationRate());
        isa.setYield(reqBodyFormat.getYield());
        isa.setValuationLoss(reqBodyFormat.getValuationLoss());
        isa.setPurchaseAmount(reqBodyFormat.getPurchaseAmount());
        isa.setBalanceAssessment(reqBodyFormat.getBalanceAssessment());

        if (method.equals("delete")) {
            int idx = isaList.indexOf(isa);
            if (idx == -1) {
                return new ResponseInfo(-1, "Delete Fail");
            }
            isaList.remove(idx);
        } else if (method.equals("update")) {
            /** update */
            int idx = isaList.indexOf(isa);
            if (idx == -1) {
                isaList.add(isa);
            } else {
                isaList.remove(idx);
                isaList.add(isa);
            }
        } else {
            return new ResponseInfo(-1, "Wrong method");
        }

        portPolioDetail.setIsas(isaList);
        map.put(portPolioName,portPolioDetail);
        portPolioMongoRespository.save(new PortPolio(userId,map));
        return new ResponseInfo(0, method + " 성공", isaList);
    }

    public ResponseInfo workPersonal(ReqBodyFormat reqBodyFormat, String method, String userId,String portPolioName) {
        PortPolio polio = portPolioMongoRespository.findBy_id(userId);
        HashMap<String,PortPolioDetail> map = polio.getPortPolioDetailMap();
        PortPolioDetail portPolioDetail = map.get(portPolioName);

        List<PersonalPension> personalPensionList  = new ArrayList<>();
        if(!portPolioDetail.getPersonalPensions().isEmpty()){
            personalPensionList = portPolioDetail.getPersonalPensions();
        }

        PersonalPension personalPension = new PersonalPension();
        personalPension.setStockName(reqBodyFormat.getStockName());
        personalPension.setStockCode(reqBodyFormat.getStockCode());
        personalPension.setAveragePrice(reqBodyFormat.getAveragePrice());
        personalPension.setCurrentPrice(reqBodyFormat.getCurrentPrice());
        personalPension.setQuantity(reqBodyFormat.getQuantity());
        personalPension.setYield(reqBodyFormat.getYield());
        personalPension.setPurchaseAmount(reqBodyFormat.getPurchaseAmount());
        personalPension.setBalanceAssessment(reqBodyFormat.getBalanceAssessment());
        personalPension.setCurrentWeight(reqBodyFormat.getCurrentWeight());
        personalPension.setTargetWeight(reqBodyFormat.getTargetWeight());
        personalPension.setTargetQuantity(reqBodyFormat.getTargetQuantity());
        personalPension.setOperationFee(reqBodyFormat.getOperationFee());
        personalPension.setPayCalculation(reqBodyFormat.getPayCalculation());

        if (method.equals("delete")) {
            int idx = personalPensionList.indexOf(personalPension);
            if (idx == -1) {
                return new ResponseInfo(-1, "Delete Fail");
            }
            personalPensionList.remove(idx);
        } else if (method.equals("update")) {
            /** update */
            int idx = personalPensionList.indexOf(personalPension);
            if (idx == -1) {
                personalPensionList.add(personalPension);
            } else {
                personalPensionList.remove(idx);
                personalPensionList.add(personalPension);
            }
        } else {
            return new ResponseInfo(-1, "Wrong method");
        }
        portPolioDetail.setPersonalPensions(personalPensionList);
        map.put(portPolioName,portPolioDetail);
        portPolioMongoRespository.save(new PortPolio(userId,map));
        return new ResponseInfo(0, method + " 성공", personalPensionList);
    }

    public ResponseInfo workRetirement(ReqBodyFormat reqBodyFormat, String method, String userId,String portPolioName) {
        PortPolio polio = portPolioMongoRespository.findBy_id(userId);
        HashMap<String,PortPolioDetail> map = polio.getPortPolioDetailMap();
        PortPolioDetail portPolioDetail = map.get(portPolioName);

        List<RetirementPension> retirementPensionList = new ArrayList<>();
        if(!portPolioDetail.getRetirementPensions().isEmpty()){
            retirementPensionList = portPolioDetail.getRetirementPensions();
        }

        RetirementPension retirementPension = new RetirementPension();
        retirementPension.setStockName(reqBodyFormat.getStockName());
        retirementPension.setStockCode(reqBodyFormat.getStockCode());
        retirementPension.setAveragePrice(reqBodyFormat.getAveragePrice());
        retirementPension.setCurrentPrice(reqBodyFormat.getCurrentPrice());
        retirementPension.setQuantity(reqBodyFormat.getQuantity());
        retirementPension.setYield(reqBodyFormat.getYield());
        retirementPension.setPurchaseAmount(reqBodyFormat.getPurchaseAmount());
        retirementPension.setBalanceAssessment(reqBodyFormat.getBalanceAssessment());
        retirementPension.setCurrentWeight(reqBodyFormat.getCurrentWeight());
        retirementPension.setTargetWeight(reqBodyFormat.getTargetWeight());
        retirementPension.setTargetQuantity(reqBodyFormat.getTargetQuantity());

        if (method.equals("delete")) {
            int idx = retirementPensionList.indexOf(retirementPension);
            if (idx == -1) {
                return new ResponseInfo(-1, "Delete Fail");
            }
            retirementPensionList.remove(idx);
        } else if (method.equals("update")) {
            /** update */
            int idx = retirementPensionList.indexOf(retirementPension);
            if (idx == -1) {
                retirementPensionList.add(retirementPension);
            } else {
                retirementPensionList.remove(idx);
                retirementPensionList.add(retirementPension);
            }
        } else {
            return new ResponseInfo(-1, "Wrong method");
        }
        portPolioDetail.setRetirementPensions(retirementPensionList);
        map.put(portPolioName,portPolioDetail);
        portPolioMongoRespository.save(new PortPolio(userId,map));
        return new ResponseInfo(0, method + " 성공", retirementPensionList);
    }

    public ResponseInfo workCoin(ReqBodyFormat reqBodyFormat, String method, String userId, String portPolioName) {
        PortPolio polio = portPolioMongoRespository.findBy_id(userId);
        HashMap<String,PortPolioDetail> map = polio.getPortPolioDetailMap();
        PortPolioDetail portPolioDetail = map.get(portPolioName);

        List<CryptoCurrency> cryptoCurrencyList  = new ArrayList<>();
        if(!portPolioDetail.getCryptoCurrencys().isEmpty()){
            cryptoCurrencyList = portPolioDetail.getCryptoCurrencys();
        }

        CryptoCurrency cryptoCurrency = new CryptoCurrency();
        cryptoCurrency.setStockName(reqBodyFormat.getStockName());
        cryptoCurrency.setExchange(reqBodyFormat.getExchange());
        cryptoCurrency.setAveragePrice(reqBodyFormat.getAveragePrice());
        cryptoCurrency.setCurrentPrice(reqBodyFormat.getCurrentPrice());
        cryptoCurrency.setQuantity(reqBodyFormat.getQuantity());
        cryptoCurrency.setYield(reqBodyFormat.getYield());
        cryptoCurrency.setValuationLoss(reqBodyFormat.getValuationLoss());
        cryptoCurrency.setPurchaseAmount(reqBodyFormat.getPurchaseAmount());
        cryptoCurrency.setBalanceAssessment(reqBodyFormat.getBalanceAssessment());

        if (method.equals("delete")) {
            int idx = cryptoCurrencyList.indexOf(cryptoCurrency);
            if (idx == -1) {
                return new ResponseInfo(-1, "Delete Fail");
            }
            cryptoCurrencyList.remove(idx);
        } else if (method.equals("update")) {
            /** update */
            int idx = cryptoCurrencyList.indexOf(cryptoCurrency);
            if (idx == -1) {
                cryptoCurrencyList.add(cryptoCurrency);
            } else {
                cryptoCurrencyList.remove(idx);
                cryptoCurrencyList.add(cryptoCurrency);
            }
        } else {
            return new ResponseInfo(-1, "Wrong method");
        }
        portPolioDetail.setCryptoCurrencys(cryptoCurrencyList);
        map.put(portPolioName,portPolioDetail);
        portPolioMongoRespository.save(new PortPolio(userId,map));
        return new ResponseInfo(0, method + " 성공", cryptoCurrencyList);
    }

    public ResponseInfo workNonCurrent(ReqBodyFormat reqBodyFormat, String method, String userId, String portPolioName) {
        PortPolio polio = portPolioMongoRespository.findBy_id(userId);
        HashMap<String,PortPolioDetail> map = polio.getPortPolioDetailMap();
        PortPolioDetail portPolioDetail = map.get(portPolioName);

        List<NonCurrentAssets> nonCurrentAssetsList = new ArrayList<>();
        if(!portPolioDetail.getNonCurrentAssets().isEmpty()){
            nonCurrentAssetsList = portPolioDetail.getNonCurrentAssets();
        }

        NonCurrentAssets nonCurrentAssets = new NonCurrentAssets();
        nonCurrentAssets.setStockName(reqBodyFormat.getStockName());
        nonCurrentAssets.setMonthlyPayment(reqBodyFormat.getMonthlyPayment());
        nonCurrentAssets.setMonthlyPaymentMonth(reqBodyFormat.getMonthlyPaymentMonth());
        nonCurrentAssets.setTotalPayment(reqBodyFormat.getTotalPayment());
        nonCurrentAssets.setTotalAppraisalValue(reqBodyFormat.getTotalAppraisalValue());

        if (method.equals("delete")) {
            int idx = nonCurrentAssetsList.indexOf(nonCurrentAssets);
            if (idx == -1) {
                return new ResponseInfo(-1, "Delete Fail");
            }
            nonCurrentAssetsList.remove(idx);
        } else if (method.equals("update")) {
            /** update */
            int idx = nonCurrentAssetsList.indexOf(nonCurrentAssets);
            if (idx == -1) {
                nonCurrentAssetsList.add(nonCurrentAssets);
            } else {
                nonCurrentAssetsList.remove(idx);
                nonCurrentAssetsList.add(nonCurrentAssets);
            }
        } else {
            return new ResponseInfo(-1, "Wrong method");
        }
        portPolioDetail.setNonCurrentAssets(nonCurrentAssetsList);
        map.put(portPolioName,portPolioDetail);
        portPolioMongoRespository.save(new PortPolio(userId,map));
        return new ResponseInfo(0, method + " 성공", nonCurrentAssetsList);
    }
}
