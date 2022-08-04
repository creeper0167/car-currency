package com.bashir.demo.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bashir.demo.dto.CarReportDTO;
import com.bashir.demo.dto.FullCarReportDTO;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
public class ScrapService {

//	@Value("#{'${web.url}'}")
	private String url = "https://www.hamrah-mechanic.com/carprice/";
	private final String TOOMAN_STR = "tooman";
	
	private final String PRICE_CLASS_NAME = "carsBrandPriceList_price__number__APBu0";
	private final String COMPANY_CLASS_NAME = "carsBrandPriceList_brand__name__Ohntn";
	private final String MODEL_CLASS_NAME = "carsBrandPriceList_model__name__fYre5";
	private final String TABLE_CLASS_NAME = "";
	
	public List<FullCarReportDTO> getReport() throws IOException{
		List<FullCarReportDTO> carReportDTOS = new ArrayList<>();
		return extractData(carReportDTOS, this.url);
	}
	
	private List<FullCarReportDTO> extractData(List<FullCarReportDTO> fullCarReportDTOS, String url) {
		try {
			Document document = Jsoup.connect(url).get();
			Elements modelName = document.getElementsByClass(MODEL_CLASS_NAME);
			Elements companyName = document.getElementsByClass(COMPANY_CLASS_NAME);
			Elements price = document.getElementsByClass(PRICE_CLASS_NAME);
//			Elements tbls = document.getElementsByTag("tr");
			Elements rows = document.getElementsByClass("carsBrandPriceList_price-list__0BSbT");
			
			for(Element el: companyName) {
				FullCarReportDTO cr = new FullCarReportDTO();
				cr.setCarCompanyName(el.html());
				fullCarReportDTOS.add(cr);
			}
			
			for(FullCarReportDTO fullReport: fullCarReportDTOS) {
				List<CarReportDTO> carReports = new ArrayList<>();
				CarReportDTO carReport = new CarReportDTO();
				for(Element r: rows) {
					if( r.children().text().contains(fullReport.getCarCompanyName()) ) {
						Elements tbls = r.children().select("div[class*=carsBrandPriceList_price__number__APBu0]");
						Elements carNames = r.children().select("tr[class*=carsBrandPriceList_price-table__row__Ev8Ts]");
//						Element mc = carNames.next().first();
						for(Element t: tbls) {
							for(Element cn: carNames) {
								if(cn.text().contains(cn.select("div[class*=carsBrandPriceList_price-table__right-content__nl31g]").text()) && cn.text().contains(t.text()))
								{carReport.setName(cn.select("div[class*=carsBrandPriceList_model__name__fYre5]").text());
								carReport.setPrice(t.text()+" "+TOOMAN_STR);
								fullReport.addToCarsList(carReport.getName(), carReport.getPrice());}
							}
						}
					}
				}
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			log.info(e.getMessage());
		}
		
		return fullCarReportDTOS;
	}
	
	public List<FullCarReportDTO> getByModel(String model){
		List<FullCarReportDTO> carReportDTOS = new ArrayList<>();
		return carReportDTOS; 
	}
}
