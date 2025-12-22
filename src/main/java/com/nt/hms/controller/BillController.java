package com.nt.hms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nt.hms.Entity.Bill;
import com.nt.hms.serviceImpl.BillServiceImpl;

@RestController
@RequestMapping("/api/v1/bills")
public class BillController {
	
	@Autowired
	private BillServiceImpl billService;
	
	@GetMapping
	public Page<Bill> getAllBills(@RequestParam(defaultValue = "0")int page, @RequestParam(defaultValue = "2")int size){
		System.out.println("Fetching the bills");
		return billService.getAllBills(page, size);
		
	}
	
	@PostMapping
	public ResponseEntity<Bill> createBill(@RequestBody Bill bill) {
		System.out.println("Creating bill ");
		return ResponseEntity.ok(billService.saveBill(bill));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Bill> getBillById(@PathVariable Long id) {
		System.out.println("Fetching bill by ID: "+id);
		return ResponseEntity.ok(billService.getBillById(id));
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Bill> updateBill(@PathVariable Long id,@RequestBody Bill bill) {
		System.out.println("Updating bill with id: "+id);
		return ResponseEntity.ok(billService.updateBillById(id, bill));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBill(@PathVariable Long id) {
		System.out.println("Deleting bill with id: "+id);
		billService.deleteBillById(id);
		return ResponseEntity.noContent().build();
	}
	
}
