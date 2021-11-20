package com.portpolio.FireStock.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.HashMap;

@Document("portpolio")
@Getter
@Setter
@ToString
public class PortPolio {

    @Id
    String _id;

    HashMap<String,PortPolioDetail> portPolioDetailMap;

//    @Builder
//    public PortPolio(String _id, List<DomesticStock> domesticStocks){
//        this._id = _id;
//        this.domesticStocks = domesticStocks;
//    }

//    @Builder
//    public PortPolio(String _id, List<DomesticStock> domesticStocks, List<OverseasStock> overseasStocks, List<ISA> isas, List<PersonalPension> personalPensions, List<RetirementPension> retirementPensions, List<CryptoCurrency> cryptoCurrencys, List<NonCurrentAssets> nonCurrentAssets) {
//        this._id = _id;
//        this.domesticStocks = domesticStocks;
//        this.overseasStocks = overseasStocks;
//        this.isas = isas;
//        this.personalPensions = personalPensions;
//        this.retirementPensions = retirementPensions;
//        this.cryptoCurrencys = cryptoCurrencys;
//        this.nonCurrentAssets = nonCurrentAssets;
//    }

    @Builder
    public PortPolio(String _id, HashMap<String,PortPolioDetail> portPolioDetailMap) {
        this._id = _id;
        this.portPolioDetailMap = portPolioDetailMap;
    }
}
