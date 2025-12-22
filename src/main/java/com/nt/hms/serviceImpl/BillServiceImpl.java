package com.nt.hms.serviceImpl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nt.hms.Entity.Bill;
import com.nt.hms.repository.BillRepository;
import com.nt.hms.service.IBillService;

@Service
public class BillServiceImpl implements IBillService {

    private static final Logger logger =
            LoggerFactory.getLogger(BillServiceImpl.class);

    @Autowired
    private BillRepository billRepository;

    @Override
    public Page<Bill> getAllBills(int page, int size) {
        try {
            logger.info("Fetching all bills | page={}, size={}", page, size);
            Pageable pageable = PageRequest.of(page, size);
            return billRepository.findAll(pageable);
        } catch (Exception e) {
            logger.error("Error occurred while fetching bills: {}", e.getMessage());
            return Page.empty();
        }
    }

    @Override
    public Bill getBillById(Long id) {
        try {
            logger.info("Fetching bill with id={}", id);

            Optional<Bill> optionalBill = billRepository.findById(id);

            if (optionalBill.isPresent()) {
                return optionalBill.get();
            } else {
                logger.warn("Bill not found with id={}", id);
                return null; // avoids NPE safely
            }

        } catch (Exception e) {
            logger.error("Error occurred while fetching bill with id={}. Reason: {}", id, e.getMessage());
            return null;
        }
    }

    @Override
    public Bill saveBill(Bill bill) {
        try {
            logger.info("Creating new bill");

            // Validation using Optional
            Optional.ofNullable(bill.getAmount())
                    .filter(amount -> amount > 0)
                    .orElseThrow(() ->
                            new IllegalArgumentException("Bill amount must be greater than zero"));

            // Default status
            Optional.ofNullable(bill.getStatus())
                    .filter(status -> !status.isBlank())
                    .orElseGet(() -> {
                        bill.setStatus("PENDING");
                        return "PENDING";
                    });

            Bill savedBill = billRepository.save(bill);
            logger.info("Bill created successfully with id={}", savedBill.getId());
            return savedBill;

        } catch (IllegalArgumentException e) {
            logger.error("Validation failed while creating bill: {}", e.getMessage());
            throw e;

        } catch (Exception e) {
            logger.error("Error occurred while creating bill. Reason: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public Bill updateBillById(Long id, Bill updatedBill) {
        try {
            logger.info("Updating bill with id={}", id);

            Optional<Bill> optionalBill = billRepository.findById(id);

            if (optionalBill.isEmpty()) {
                logger.warn("Bill not found with id={}", id);
                return null;
            }

            Bill existingBill = optionalBill.get();

            // Validation using Optional
            Optional.ofNullable(updatedBill.getAmount())
                    .filter(amount -> amount > 0)
                    .ifPresent(existingBill::setAmount);

            Optional.ofNullable(updatedBill.getStatus())
                    .filter(status -> !status.isBlank())
                    .ifPresent(existingBill::setStatus);

            Bill savedBill = billRepository.save(existingBill);
            logger.info("Bill updated successfully with id={}", savedBill.getId());
            return savedBill;

        } catch (IllegalArgumentException e) {
            logger.error("Validation failed while updating bill: {}", e.getMessage());
            throw e;

        } catch (Exception e) {
            logger.error("Error occurred while updating bill with id={}. Reason: {}", id, e.getMessage());
            return null;
        }
    }

    @Override
    public void deleteBillById(Long id) {
        try {
            logger.info("Deleting bill with id={}", id);

            Optional<Bill> optionalBill = billRepository.findById(id);

            optionalBill.ifPresentOrElse(
                    bill -> {
                        billRepository.deleteById(id);
                        logger.info("Bill deleted successfully with id={}", id);
                    },
                    () -> logger.warn("Bill not found with id={}", id)
            );

        } catch (Exception e) {
            logger.error("Error occurred while deleting bill with id={}. Reason: {}", id, e.getMessage());
        }
    }
}
