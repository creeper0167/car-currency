package com.bashir.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import com.bashir.demo.dto.CarReportDTO;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FullCarReportDTO {

	private String carCompanyName;
	private List<CarReportDTO> cars = new ArrayList<>();
	
	public void addToCarsList(String name, String price) {
		CarReportDTO c = new CarReportDTO();
		c.setName(name);
		c.setPrice(price);
		this.cars.add(c);
	}
}
