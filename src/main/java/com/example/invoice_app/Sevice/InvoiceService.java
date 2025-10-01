package com.example.invoice_app.Sevice;

import com.example.invoice_app.dto.InvoiceRequestDTO;
import com.example.invoice_app.dto.InvoiceResponseDTO;
import com.example.invoice_app.model.Invoice;
import com.example.invoice_app.model.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceService {

    // beinjekáljuk az invoice repository-t hogy képesek legyünk az adatbázisba menteni
    private InvoiceRepository invoiceRepository;
    public InvoiceService(InvoiceRepository invoiceRepository){
        this.invoiceRepository = invoiceRepository;
    }

    // Invoice entity létrehozása InvoiceRequestDTO-ból
    public InvoiceResponseDTO createInvoice(InvoiceRequestDTO request){
        Invoice invoice = new Invoice();
        invoice.setBuyername(request.getBuyer());
        invoice.setCreatedate(request.getCreatedate());
        invoice.setDuedate(request.getDuedate());
        invoice.setProduct(request.getProduct());
        invoice.setComment(request.getComment());
        invoice.setPrice(request.getPrice()
        );

        Invoice saved = invoiceRepository.save(invoice);
        return toResponse(saved);
    }

    // Az összes invoice lekérése
    public List<InvoiceResponseDTO> listAllInvoices( ){
        return invoiceRepository.findAll().stream()
                .map(this::toResponse).collect(Collectors.toList());
    }

    // Egy Invoice enitity lekérése és átalakítása DTO-vá
    public InvoiceResponseDTO getInvoiceById(long id){
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Invoice not found"));
        return toResponse(invoice);
    }

    // Invoice entity átalakítása InvoiceRequestDTO-vá
    private InvoiceResponseDTO toResponse(Invoice invoice){
        return new InvoiceResponseDTO(
                invoice.getId(),
                invoice.getBuyername(),
                invoice.getCreatedate(),
                invoice.getDuedate(),
                invoice.getProduct(),
                invoice.getComment(),
                invoice.getPrice()
        );
    }
}
