package com.JobFindingPlatform.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.JobFindingPlatform.DTO.PaymentRequestDTO;
import com.JobFindingPlatform.DTO.PaymentResponseDTO;
import com.JobFindingPlatform.Entity.Payment;
import com.JobFindingPlatform.Entity.SubscriptionPlan;
import com.JobFindingPlatform.Repository.PaymentRepository;
import com.JobFindingPlatform.Repository.SubscriptionPlanRepository;
import com.JobFindingPlatform.Service.InvoiceService;
import com.JobFindingPlatform.Service.PaymentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {
	
	@Autowired
	private PaymentService paymentService;
	@Autowired
	private SubscriptionPlanRepository subPlanRepo;
	@Autowired
	private PaymentRepository paymentRepo;
	@Autowired
	private InvoiceService invoiceService;
	
	
	@PostMapping("/process")
	public ResponseEntity<PaymentResponseDTO>process(@RequestBody PaymentRequestDTO dto ){
		return ResponseEntity.ok(paymentService.processPayment(dto));
	}
	
	@GetMapping("/plans")
	public ResponseEntity<List<SubscriptionPlan>>getPlans(){
		return ResponseEntity.ok(subPlanRepo.findAll());
	}
	@PostMapping ResponseEntity<SubscriptionPlan>createPlans(@RequestBody SubscriptionPlan plan){
		return ResponseEntity.ok(subPlanRepo.save(plan));
	}
    @GetMapping("/history/{userId}")
    public ResponseEntity<List<Payment>>history(@PathVariable Long userId){
    	return ResponseEntity.ok(paymentRepo.findByUserId(userId));
    }
	
	
    @GetMapping("/invoice/{paymentId}")
	public ResponseEntity<byte[]>downloadInvoice(@PathVariable Long paymentId){
		
		Payment pay = paymentRepo.findById(paymentId).orElseThrow(()-> new RuntimeException("Payment not found"));
		byte[] pdfBytes = invoiceService.generateInvoice(pay);
		
		return ResponseEntity.ok()
				.header("Content-Disposition", "attachment; filename=\"Invoice-" + pay.getTransactionId() + ".pdf\"")
				.contentType(org.springframework.http.MediaType.APPLICATION_PDF).body(pdfBytes);
	}
	

}