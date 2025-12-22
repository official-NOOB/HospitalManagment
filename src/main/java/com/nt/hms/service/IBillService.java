package com.nt.hms.service;

import org.springframework.data.domain.Page;

import com.nt.hms.Entity.Bill;

public interface IBillService {
	
	public Page<Bill> getAllBills(int page, int size);
	public Bill getBillById(Long id);
	public Bill saveBill(Bill bill);
	public void deleteBillById(Long id);
	public Bill updateBillById(Long id, Bill updateBill);
}