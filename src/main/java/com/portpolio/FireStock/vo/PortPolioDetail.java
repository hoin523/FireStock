package com.portpolio.FireStock.vo;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PortPolioDetail {

    List<DomesticStock> domesticStocks;

    List<OverseasStock> overseasStocks;

    List<ISA> isas;

    List<PersonalPension> personalPensions;

    List<RetirementPension> retirementPensions;

    List<CryptoCurrency> cryptoCurrencys;

    List<NonCurrentAssets> nonCurrentAssets;

    @Builder
    public PortPolioDetail(List<DomesticStock> domesticStocks, List<OverseasStock> overseasStocks, List<ISA> isas, List<PersonalPension> personalPensions, List<RetirementPension> retirementPensions, List<CryptoCurrency> cryptoCurrencys, List<NonCurrentAssets> nonCurrentAssets) {
        this.domesticStocks = domesticStocks;
        this.overseasStocks = overseasStocks;
        this.isas = isas;
        this.personalPensions = personalPensions;
        this.retirementPensions = retirementPensions;
        this.cryptoCurrencys = cryptoCurrencys;
        this.nonCurrentAssets = nonCurrentAssets;
    }
}
