package com.example.invoice_app;


import com.example.invoice_app.Sevice.InvoiceService;
import com.example.invoice_app.dto.CreateUserRequestDTO;
import com.example.invoice_app.dto.InvoiceRequestDTO;
import com.example.invoice_app.dto.InvoiceResponseDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class InvoiceServiceTest {

    @Autowired
    InvoiceService invoiceService;

    @BeforeEach
    public void createInvoices(){
        InvoiceRequestDTO invoiceDTO = new InvoiceRequestDTO(
                "Teszt Kft.",
                LocalDate.parse("2025-10-01"),
                LocalDate.parse("2025-10-01"),
                "Sajt",
                "",
                1500
        );
        InvoiceRequestDTO invoiceDTO2 = new InvoiceRequestDTO(
                "Teszt Kft.",
                LocalDate.parse("2025-10-01"),
                LocalDate.parse("2025-10-01"),
                "kenyér",
                "",
                1000
        );
        invoiceService.createInvoice(invoiceDTO);
        invoiceService.createInvoice(invoiceDTO2);
    }

    @Test
    public void testCreateInvoice(){
        InvoiceRequestDTO invoiceDTO = new InvoiceRequestDTO(
                "Teszt Kft.",
                LocalDate.parse("2025-10-01"),
                LocalDate.parse("2025-10-01"),
                "Sajt",
                "",
                1000
        );

        InvoiceResponseDTO InvoiceResponseDTO = invoiceService.createInvoice(invoiceDTO);

        Assertions.assertEquals(1000, InvoiceResponseDTO.price());
    }


    @Test
    public void testListAllInvoices(){
       List<InvoiceResponseDTO> invoiceResponseDTOList = invoiceService.listAllInvoices();

       Assertions.assertEquals(2, invoiceResponseDTOList.size());
    }

    @Test
    public void testGetInvoiceById(){
        InvoiceResponseDTO invoiceResponseDTO = invoiceService.getInvoiceById(1);

        Assertions.assertEquals("Sajt", invoiceResponseDTO.product());

        InvoiceResponseDTO invoiceResponseDTO2 = invoiceService.getInvoiceById(2);

        Assertions.assertEquals("kenyér", invoiceResponseDTO2.product());
    }
}
