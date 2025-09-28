package com.example.invoice_app.Sevice;

import com.example.invoice_app.dto.InvoiceRequestDTO;
import com.example.invoice_app.model.Invoice;
import com.example.invoice_app.model.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceService {

    private InvoiceRepository invoiceRepository;

    public InvoiceService(InvoiceRepository invoiceRepository){
        this.invoiceRepository = invoiceRepository;
    }

    public InvoiceRequestDTO createInvoice(InvoiceRequestDTO request){
        Invoice invoice = new Invoice();
        invoice.setBuyername(request.buyer());
        invoice.setCreatedate(request.createdate());
        invoice.setDuedate(request.duedate());
        invoice.setProduct(request.product());
        invoice.setComment(request.comment());
        invoice.setPrice(request.price());

        Invoice saved = invoiceRepository.save(invoice);
        return toResponse(saved);
    }

    public List<InvoiceRequestDTO> listAllInvoices( ){
        return invoiceRepository.findAll().stream()
                .map(invoice -> new InvoiceRequestDTO(
                        invoice.getId(),
                        invoice.getBuyername(),
                        invoice.getCreatedate(),
                        invoice.getDuedate(),
                        invoice.getProduct(),
                        invoice.getComment(),
                        invoice.getPrice()
                )).collect(Collectors.toList());
    }

    public InvoiceRequestDTO getInvoiceById(long id){
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Invoice not found"));
        return toResponse(invoice);
    }

    private InvoiceRequestDTO toResponse(Invoice invoice){
        return new InvoiceRequestDTO(
                invoice.getId(),
                invoice.getBuyername(),
                invoice.getCreatedate(),
                invoice.getDuedate(),
                invoice.getProduct(),
                invoice.getComment(),
                invoice.getPrice());
    }
}
