package com.bashir.demo.controller;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bashir.demo.dto.FullCarReportDTO;
import com.bashir.demo.service.ScrapService;

@RequestMapping("/cars")
@RestController
public class HomeController {

	@Autowired
	private ScrapService scrapService;
	
	@GetMapping("/show-full-report")
	public List<FullCarReportDTO> showReport() throws IOException{
		return scrapService.getReport();
	}
	
	@GetMapping("/{modelName}")
	public List<FullCarReportDTO> show(@PathVariable String carModel){
		return scrapService.getByModel(carModel);
	}
}
