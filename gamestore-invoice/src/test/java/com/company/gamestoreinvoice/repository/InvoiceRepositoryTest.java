package com.company.gamestoreinvoice.repository;


import com.company.gamestoreinvoice.model.Invoice;
import com.company.gamestoreinvoice.model.ProcessingFee;
import com.company.gamestoreinvoice.model.Tax;
import com.company.gamestoreinvoice.util.Catalog;
import com.company.gamestoreinvoice.viewModel.TshirtViewModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest

public class InvoiceRepositoryTest {

    @Autowired
    InvoiceRepository invoiceRepository;
    @Autowired
    TaxRepository taxRepository;
    @Autowired
    ProcessingFeeRepository processingFeeRepository;
    @Autowired
    Catalog serviceClient;

    @Before
    public void setUp() throws Exception {
        invoiceRepository.deleteAll();
        processingFeeRepository.deleteAll();
        setUpFeignClientMock();

        ProcessingFee tShirtProcessingFee = new ProcessingFee();
        tShirtProcessingFee.setProductType("T-Shirts");
        tShirtProcessingFee.setFee(new BigDecimal("1.98"));

        ProcessingFee consoleProcessingFee = new ProcessingFee();
        consoleProcessingFee.setProductType("Consoles");
        consoleProcessingFee.setFee(new BigDecimal("14.99"));

        ProcessingFee gameProcessingFee = new ProcessingFee();
        gameProcessingFee.setProductType("Games");
        gameProcessingFee.setFee(new BigDecimal("1.49"));

        processingFeeRepository.save(tShirtProcessingFee);
        processingFeeRepository.save(consoleProcessingFee);
        processingFeeRepository.save(gameProcessingFee);
    }

    @Test
    public void shouldAddFindDeleteInvoice() {

        TshirtViewModel tShirt1 = new TshirtViewModel();
        tShirt1.setSize("M");
        tShirt1.setColor("Blue");
        tShirt1.setDescription("v-neck short sleeve");

        tShirt1.setPrice(new BigDecimal("15.99"));

        tShirt1.setQuantity(8);
        tShirt1 = serviceClient.getTShirt(2);

        Invoice invoice1 = new Invoice();
        invoice1.setName("Joe Black");
        invoice1.setStreet("123 Main St");
        invoice1.setCity("any City");
        invoice1.setState("NY");
        invoice1.setZipcode("10016");
        invoice1.setItemType("T-Shirts");
        invoice1.setItemId(tShirt1.getId());
        invoice1.setUnitPrice(tShirt1.getPrice());
        invoice1.setQuantity(2);

        invoice1.setSubtotal(
                tShirt1.getPrice().multiply(
                        new BigDecimal(invoice1.getQuantity()))
        );

        Optional<Tax> tax = taxRepository.findById(invoice1.getState());
        assertTrue(tax.isPresent());
        invoice1.setTax(invoice1.getSubtotal().multiply(tax.get().getRate()));

        Optional<ProcessingFee> processingFee = processingFeeRepository.findById(invoice1.getItemType());
        assertTrue(processingFee.isPresent());
        invoice1.setProcessingFee(processingFee.get().getFee());

        invoice1.setTotal(invoice1.getSubtotal().add(invoice1.getTax()).add(invoice1.getProcessingFee()));

        invoice1 = invoiceRepository.save(invoice1);
        Optional<Invoice> invoice2 = invoiceRepository.findById(invoice1.getId());

        assertTrue(invoice2.isPresent());
        assertEquals(invoice1, invoice2.get());

        invoiceRepository.deleteById(invoice1.getId());
        invoice2 = invoiceRepository.findById(invoice1.getId());

        assertFalse(invoice2.isPresent());
    }

    @Test
    public void shouldFindByName() {

        TshirtViewModel tShirt1 = new TshirtViewModel();
        tShirt1.setSize("M");
        tShirt1.setColor("Blue");
        tShirt1.setDescription("v-neck short sleeve");

        tShirt1.setPrice(new BigDecimal("15.99"));

        tShirt1.setQuantity(8);
        tShirt1 = serviceClient.getTShirt(2);

        Invoice invoice1 = new Invoice();
        invoice1.setName("Joe Black");
        invoice1.setStreet("123 Main St");
        invoice1.setCity("any City");
        invoice1.setState("NY");
        invoice1.setZipcode("10016");
        invoice1.setItemType("T-Shirts");
        invoice1.setItemId(tShirt1.getId());
        invoice1.setUnitPrice(tShirt1.getPrice());
        invoice1.setQuantity(2);

        invoice1.setSubtotal(tShirt1.getPrice().multiply(new BigDecimal(invoice1.getQuantity())));

        Optional<Tax> tax = taxRepository.findById(invoice1.getState());
        assertTrue(tax.isPresent());
        invoice1.setTax(invoice1.getSubtotal().multiply(tax.get().getRate()));

        Optional<ProcessingFee> processingFee = processingFeeRepository.findById(invoice1.getItemType());
        assertTrue(processingFee.isPresent());
        invoice1.setProcessingFee(processingFee.get().getFee());

        invoice1.setTotal(invoice1.getSubtotal().add(invoice1.getTax()).add(invoice1.getProcessingFee()));

        invoice1 = invoiceRepository.save(invoice1);

        List<Invoice> foundNoInvoice = invoiceRepository.findByName("invalidValue");

        List<Invoice> foundOneInvoice = invoiceRepository.findByName(invoice1.getName());

        assertEquals(foundOneInvoice.size(),1);

        assertEquals(foundNoInvoice.size(),0);
    }

    private void setUpFeignClientMock() {
        serviceClient = mock(Catalog.class);

        TshirtViewModel savedTShirt1 = new TshirtViewModel();
        savedTShirt1.setId(2);
        savedTShirt1.setSize("M");
        savedTShirt1.setColor("Blue");
        savedTShirt1.setDescription("v-neck short sleeve");
        savedTShirt1.setPrice(new BigDecimal("15.99"));
        savedTShirt1.setQuantity(8);

        doReturn(savedTShirt1).when(serviceClient).getTShirt(2);
    }
}