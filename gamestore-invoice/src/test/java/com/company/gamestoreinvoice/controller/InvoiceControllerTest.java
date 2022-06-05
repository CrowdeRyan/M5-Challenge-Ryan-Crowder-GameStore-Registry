package com.company.gamestoreinvoice.controller;


import com.company.gamestoreinvoice.service.InvoiceServiceLayer;
import com.company.gamestoreinvoice.viewModel.InvoiceViewModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(InvoiceController.class)
@AutoConfigureMockMvc(addFilters = false)
public class InvoiceControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private InvoiceServiceLayer invoiceServiceLayer;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void shouldAddPurchase() throws Exception{
        String outputJson;
        String inputJson;

        InvoiceViewModel inInvoice = new InvoiceViewModel();
        inInvoice.setName("Joe Black");
        inInvoice.setStreet("Once Blvd");
        inInvoice.setCity("New York");
        inInvoice.setState("NY");
        inInvoice.setZipcode("10016");
        inInvoice.setItemType("T-Shirt");
        inInvoice.setItemId(12);
        inInvoice.setUnitPrice(new BigDecimal("12.50"));
        inInvoice.setQuantity(2);

        InvoiceViewModel savedInvoice = new InvoiceViewModel();
        savedInvoice.setName("Joe Black");
        savedInvoice.setStreet("Once Blvd");
        savedInvoice.setCity("New York");
        savedInvoice.setState("NY");
        savedInvoice.setZipcode("10016");
        savedInvoice.setItemType("T-Shirt");
        savedInvoice.setItemId(12);
        savedInvoice.setUnitPrice(new BigDecimal("12.50"));
        savedInvoice.setQuantity(2);
        savedInvoice.setSubtotal(inInvoice.getUnitPrice().multiply(new BigDecimal(inInvoice.getQuantity())));
        savedInvoice.setTax(savedInvoice.getSubtotal().multiply(new BigDecimal("0.06")));
        savedInvoice.setProcessingFee(new BigDecimal("10.00"));
        savedInvoice.setTotal(savedInvoice.getSubtotal().add(savedInvoice.getTax()).add(savedInvoice.getProcessingFee()));
        savedInvoice.setId(22);

        inputJson = mapper.writeValueAsString(inInvoice);
        outputJson = mapper.writeValueAsString(savedInvoice);

        when(invoiceServiceLayer.createInvoice(inInvoice)).thenReturn(savedInvoice);

        this.mockMvc.perform(post("/invoice")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldFindInvoice() throws Exception{

        InvoiceViewModel savedInvoice = new InvoiceViewModel();
        savedInvoice.setName("Joe Black");
        savedInvoice.setStreet("Once Blvd");
        savedInvoice.setCity("New York");
        savedInvoice.setState("NY");
        savedInvoice.setZipcode("10016");
        savedInvoice.setItemType("T-Shirt");
        savedInvoice.setItemId(12);
        savedInvoice.setUnitPrice(new BigDecimal("12.50"));
        savedInvoice.setQuantity(2);
        savedInvoice.setSubtotal(savedInvoice.getUnitPrice().multiply(new BigDecimal(savedInvoice.getQuantity())));
        savedInvoice.setTax(savedInvoice.getSubtotal().multiply(new BigDecimal("0.06")));
        savedInvoice.setProcessingFee(new BigDecimal("10.00"));
        savedInvoice.setTotal(savedInvoice.getSubtotal().add(savedInvoice.getTax()).add(savedInvoice.getProcessingFee()));
        savedInvoice.setId(22);

        String outputJson = mapper.writeValueAsString(savedInvoice);

        when(invoiceServiceLayer.getInvoice(22)).thenReturn(savedInvoice);

        this.mockMvc.perform(get("/invoice/{id}", 22))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));

        when(invoiceServiceLayer.getInvoice(-1)).thenReturn(null);

        this.mockMvc.perform(get("/invoice/{id}", -1))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    public void shouldFindAllInvoices() throws Exception{
        InvoiceViewModel savedInvoice1 = new InvoiceViewModel();
        savedInvoice1.setName("Joe Black");
        savedInvoice1.setStreet("Once Blvd");
        savedInvoice1.setCity("New York");
        savedInvoice1.setState("NY");
        savedInvoice1.setZipcode("10016");
        savedInvoice1.setItemType("T-Shirt");
        savedInvoice1.setItemId(12);
        savedInvoice1.setUnitPrice(new BigDecimal("12.50"));
        savedInvoice1.setQuantity(2);
        savedInvoice1.setSubtotal(savedInvoice1.getUnitPrice().multiply(new BigDecimal(savedInvoice1.getQuantity())));
        savedInvoice1.setTax(savedInvoice1.getSubtotal().multiply(new BigDecimal("0.06")));
        savedInvoice1.setProcessingFee(new BigDecimal("10.00"));
        savedInvoice1.setTotal(savedInvoice1.getSubtotal().add(savedInvoice1.getTax()).add(savedInvoice1.getProcessingFee()));
        savedInvoice1.setId(22);

        InvoiceViewModel savedInvoice2 = new InvoiceViewModel();
        savedInvoice2.setName("Johnny Dangerously");
        savedInvoice2.setStreet("888 Main St");
        savedInvoice2.setCity("any town");
        savedInvoice2.setState("NJ");
        savedInvoice2.setZipcode("08234");
        savedInvoice2.setItemType("Console");
        savedInvoice2.setItemId(120);
        savedInvoice2.setUnitPrice(new BigDecimal("129.50"));
        savedInvoice2.setQuantity(1);
        savedInvoice2.setSubtotal(savedInvoice2.getUnitPrice().multiply(new BigDecimal(savedInvoice2.getQuantity())));
        savedInvoice2.setTax(savedInvoice2.getSubtotal().multiply(new BigDecimal("0.08")));
        savedInvoice2.setProcessingFee(new BigDecimal("10.00"));
        savedInvoice2.setTotal(savedInvoice2.getSubtotal().add(savedInvoice2.getTax()).add(savedInvoice2.getProcessingFee()));
        savedInvoice2.setId(12);

        InvoiceViewModel savedInvoice3 = new InvoiceViewModel();
        savedInvoice3.setName("Sandy Beach");
        savedInvoice3.setStreet("123 Broad St");
        savedInvoice3.setCity("any where");
        savedInvoice3.setState("CA");
        savedInvoice3.setZipcode("90016");
        savedInvoice3.setItemType("Game");
        savedInvoice3.setItemId(19);
        savedInvoice3.setUnitPrice(new BigDecimal("12.50"));
        savedInvoice3.setQuantity(4);
        savedInvoice3.setSubtotal(savedInvoice3.getUnitPrice().multiply(new BigDecimal(savedInvoice3.getQuantity())));
        savedInvoice3.setTax(savedInvoice3.getSubtotal().multiply(new BigDecimal("0.09")));
        savedInvoice3.setProcessingFee(BigDecimal.ZERO);
        savedInvoice3.setTotal(savedInvoice3.getSubtotal().add(savedInvoice3.getTax()).add(savedInvoice3.getProcessingFee()));
        savedInvoice3.setId(73);

        List<InvoiceViewModel> foundAllInvoices = new ArrayList<>();
        foundAllInvoices.add(savedInvoice1);
        foundAllInvoices.add(savedInvoice2);
        foundAllInvoices.add(savedInvoice3);

        String outputJson = mapper.writeValueAsString(foundAllInvoices);

        when(invoiceServiceLayer.getAllInvoices()).thenReturn(foundAllInvoices);

        this.mockMvc.perform(get("/invoice"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));

        when(invoiceServiceLayer.getAllInvoices()).thenReturn(null);

        this.mockMvc.perform(get("/invoice"))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    public void shouldFindInvoicesByCustomerName() throws Exception{
        InvoiceViewModel savedInvoice1 = new InvoiceViewModel();
        savedInvoice1.setName("Sandy Beach");
        savedInvoice1.setStreet("Once Blvd");
        savedInvoice1.setCity("New York");
        savedInvoice1.setState("NY");
        savedInvoice1.setZipcode("10016");
        savedInvoice1.setItemType("T-Shirt");
        savedInvoice1.setItemId(12);
        savedInvoice1.setUnitPrice(new BigDecimal("12.50"));
        savedInvoice1.setQuantity(2);
        savedInvoice1.setSubtotal(savedInvoice1.getUnitPrice().multiply(new BigDecimal(savedInvoice1.getQuantity())));
        savedInvoice1.setTax(savedInvoice1.getSubtotal().multiply(new BigDecimal("0.06")));
        savedInvoice1.setProcessingFee(new BigDecimal("10.00"));
        savedInvoice1.setTotal(savedInvoice1.getSubtotal().add(savedInvoice1.getTax()).add(savedInvoice1.getProcessingFee()));
        savedInvoice1.setId(22);

        InvoiceViewModel savedInvoice2 = new InvoiceViewModel();
        savedInvoice2.setName("Johnny Dangerously");
        savedInvoice2.setStreet("888 Main St");
        savedInvoice2.setCity("any town");
        savedInvoice2.setState("NJ");
        savedInvoice2.setZipcode("08234");
        savedInvoice2.setItemType("Console");
        savedInvoice2.setItemId(120);
        savedInvoice2.setUnitPrice(new BigDecimal("129.50"));
        savedInvoice2.setQuantity(1);
        savedInvoice2.setSubtotal(savedInvoice2.getUnitPrice().multiply(new BigDecimal(savedInvoice2.getQuantity())));
        savedInvoice2.setTax(savedInvoice2.getSubtotal().multiply(new BigDecimal("0.08")));
        savedInvoice2.setProcessingFee(new BigDecimal("10.00"));
        savedInvoice2.setTotal(savedInvoice2.getSubtotal().add(savedInvoice2.getTax()).add(savedInvoice2.getProcessingFee()));
        savedInvoice2.setId(12);

        InvoiceViewModel savedInvoice3 = new InvoiceViewModel();
        savedInvoice3.setName("Sandy Beach");
        savedInvoice3.setStreet("123 Broad St");
        savedInvoice3.setCity("any where");
        savedInvoice3.setState("CA");
        savedInvoice3.setZipcode("90016");
        savedInvoice3.setItemType("Game");
        savedInvoice3.setItemId(19);
        savedInvoice3.setUnitPrice(new BigDecimal("12.50"));
        savedInvoice3.setQuantity(4);
        savedInvoice3.setSubtotal(savedInvoice3.getUnitPrice().multiply(new BigDecimal(savedInvoice3.getQuantity())));
        savedInvoice3.setTax(savedInvoice3.getSubtotal().multiply(new BigDecimal("0.09")));
        savedInvoice3.setProcessingFee(BigDecimal.ZERO);
        savedInvoice3.setTotal(savedInvoice3.getSubtotal().add(savedInvoice3.getTax()).add(savedInvoice3.getProcessingFee()));
        savedInvoice3.setId(73);

        List<InvoiceViewModel> foundAllInvoices = new ArrayList<>();
        foundAllInvoices.add(savedInvoice1);
        foundAllInvoices.add(savedInvoice3);

        String outputJson = mapper.writeValueAsString(foundAllInvoices);

        when(invoiceServiceLayer.getInvoicesByCustomerName("Sandy Beach")).thenReturn(foundAllInvoices);

        this.mockMvc.perform(get("/invoice/cname/{name}","Sandy Beach"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));

        when(invoiceServiceLayer.getInvoicesByCustomerName("no customer")).thenReturn(null);

        this.mockMvc.perform(get("/invoice/cname/{name}","no customer"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldFailCreateInvoiceWithBadData() throws Exception{
        InvoiceViewModel inInvoiceMV = new InvoiceViewModel();
        inInvoiceMV.setName("");
        inInvoiceMV.setStreet("Once Blvd");
        inInvoiceMV.setCity("New York");
        inInvoiceMV.setState("NY");
        inInvoiceMV.setZipcode("10016");
        inInvoiceMV.setItemType("T-Shirt");
        inInvoiceMV.setItemId(12);
        inInvoiceMV.setUnitPrice(new BigDecimal("12.50"));
        inInvoiceMV.setQuantity(2);
        inInvoiceMV.setSubtotal(inInvoiceMV.getUnitPrice().multiply(new BigDecimal(inInvoiceMV.getQuantity())));
        inInvoiceMV.setTax(inInvoiceMV.getSubtotal().multiply(new BigDecimal("0.06")));
        inInvoiceMV.setProcessingFee(new BigDecimal("10.00"));
        inInvoiceMV.setTotal(inInvoiceMV.getSubtotal().add(inInvoiceMV.getTax()).add(inInvoiceMV.getProcessingFee()));

        when(this.invoiceServiceLayer.createInvoice(inInvoiceMV)).thenReturn(null);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/invoice")
                                .content(mapper.writeValueAsString(inInvoiceMV)) 
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity()); 

        inInvoiceMV = new InvoiceViewModel();
        inInvoiceMV.setName(null);
        inInvoiceMV.setStreet("Once Blvd");
        inInvoiceMV.setCity("New York");
        inInvoiceMV.setState("NY");
        inInvoiceMV.setZipcode("10016");
        inInvoiceMV.setItemType("T-Shirt");
        inInvoiceMV.setItemId(12);
        inInvoiceMV.setUnitPrice(new BigDecimal("12.50"));
        inInvoiceMV.setQuantity(2);
        inInvoiceMV.setSubtotal(inInvoiceMV.getUnitPrice().multiply(new BigDecimal(inInvoiceMV.getQuantity())));
        inInvoiceMV.setTax(inInvoiceMV.getSubtotal().multiply(new BigDecimal("0.06")));
        inInvoiceMV.setProcessingFee(new BigDecimal("10.00"));
        inInvoiceMV.setTotal(inInvoiceMV.getSubtotal().add(inInvoiceMV.getTax()).add(inInvoiceMV.getProcessingFee()));

        when(this.invoiceServiceLayer.createInvoice(inInvoiceMV)).thenReturn(null);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/invoice")
                                .content(mapper.writeValueAsString(inInvoiceMV)) 
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) 
                .andExpect(status().isUnprocessableEntity()); 

        inInvoiceMV = new InvoiceViewModel();
        inInvoiceMV.setName("Johnny Dangerously");
        inInvoiceMV.setStreet("");
        inInvoiceMV.setCity("New York");
        inInvoiceMV.setState("NY");
        inInvoiceMV.setZipcode("10016");
        inInvoiceMV.setItemType("T-Shirt");
        inInvoiceMV.setItemId(12);
        inInvoiceMV.setUnitPrice(new BigDecimal("12.50"));
        inInvoiceMV.setQuantity(2);
        inInvoiceMV.setSubtotal(inInvoiceMV.getUnitPrice().multiply(new BigDecimal(inInvoiceMV.getQuantity())));
        inInvoiceMV.setTax(inInvoiceMV.getSubtotal().multiply(new BigDecimal("0.06")));
        inInvoiceMV.setProcessingFee(new BigDecimal("10.00"));
        inInvoiceMV.setTotal(inInvoiceMV.getSubtotal().add(inInvoiceMV.getTax()).add(inInvoiceMV.getProcessingFee()));

        when(this.invoiceServiceLayer.createInvoice(inInvoiceMV)).thenReturn(null);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/invoice")
                                .content(mapper.writeValueAsString(inInvoiceMV)) 
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) 
                .andExpect(status().isUnprocessableEntity()); 

        inInvoiceMV = new InvoiceViewModel();
        inInvoiceMV.setName("Johnny Dangerously");
        inInvoiceMV.setStreet(null);
        inInvoiceMV.setCity("New York");
        inInvoiceMV.setState("NY");
        inInvoiceMV.setZipcode("10016");
        inInvoiceMV.setItemType("T-Shirt");
        inInvoiceMV.setItemId(12);
        inInvoiceMV.setUnitPrice(new BigDecimal("12.50"));
        inInvoiceMV.setQuantity(2);
        inInvoiceMV.setSubtotal(inInvoiceMV.getUnitPrice().multiply(new BigDecimal(inInvoiceMV.getQuantity())));
        inInvoiceMV.setTax(inInvoiceMV.getSubtotal().multiply(new BigDecimal("0.06")));
        inInvoiceMV.setProcessingFee(new BigDecimal("10.00"));
        inInvoiceMV.setTotal(inInvoiceMV.getSubtotal().add(inInvoiceMV.getTax()).add(inInvoiceMV.getProcessingFee()));

        when(this.invoiceServiceLayer.createInvoice(inInvoiceMV)).thenReturn(null);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/invoice")
                                .content(mapper.writeValueAsString(inInvoiceMV)) 
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) 
                .andExpect(status().isUnprocessableEntity()); 

        inInvoiceMV = new InvoiceViewModel();
        inInvoiceMV.setName("Johnny Dangerously");
        inInvoiceMV.setStreet("Once Blvd");
        inInvoiceMV.setCity("");
        inInvoiceMV.setState("NY");
        inInvoiceMV.setZipcode("10016");
        inInvoiceMV.setItemType("T-Shirt");
        inInvoiceMV.setItemId(12);
        inInvoiceMV.setUnitPrice(new BigDecimal("12.50"));
        inInvoiceMV.setQuantity(2);
        inInvoiceMV.setSubtotal(inInvoiceMV.getUnitPrice().multiply(new BigDecimal(inInvoiceMV.getQuantity())));
        inInvoiceMV.setTax(inInvoiceMV.getSubtotal().multiply(new BigDecimal("0.06")));
        inInvoiceMV.setProcessingFee(new BigDecimal("10.00"));
        inInvoiceMV.setTotal(inInvoiceMV.getSubtotal().add(inInvoiceMV.getTax()).add(inInvoiceMV.getProcessingFee()));

        when(this.invoiceServiceLayer.createInvoice(inInvoiceMV)).thenReturn(null);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/invoice")
                                .content(mapper.writeValueAsString(inInvoiceMV))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) 
                .andExpect(status().isUnprocessableEntity());

        inInvoiceMV = new InvoiceViewModel();
        inInvoiceMV.setName("Johnny Dangerously");
        inInvoiceMV.setStreet("Once Blvd");
        inInvoiceMV.setCity(null);
        inInvoiceMV.setState("NY");
        inInvoiceMV.setZipcode("10016");
        inInvoiceMV.setItemType("T-Shirt");
        inInvoiceMV.setItemId(12);
        inInvoiceMV.setUnitPrice(new BigDecimal("12.50"));
        inInvoiceMV.setQuantity(2);
        inInvoiceMV.setSubtotal(inInvoiceMV.getUnitPrice().multiply(new BigDecimal(inInvoiceMV.getQuantity())));
        inInvoiceMV.setTax(inInvoiceMV.getSubtotal().multiply(new BigDecimal("0.06")));
        inInvoiceMV.setProcessingFee(new BigDecimal("10.00"));
        inInvoiceMV.setTotal(inInvoiceMV.getSubtotal().add(inInvoiceMV.getTax()).add(inInvoiceMV.getProcessingFee()));

        when(this.invoiceServiceLayer.createInvoice(inInvoiceMV)).thenReturn(null);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/invoice")
                                .content(mapper.writeValueAsString(inInvoiceMV))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());

        inInvoiceMV = new InvoiceViewModel();
        inInvoiceMV.setName("Johnny Dangerously");
        inInvoiceMV.setStreet("Once Blvd");
        inInvoiceMV.setCity("New York");
        inInvoiceMV.setState("");
        inInvoiceMV.setZipcode("10016");
        inInvoiceMV.setItemType("T-Shirt");
        inInvoiceMV.setItemId(12);
        inInvoiceMV.setUnitPrice(new BigDecimal("12.50"));
        inInvoiceMV.setQuantity(2);
        inInvoiceMV.setSubtotal(inInvoiceMV.getUnitPrice().multiply(new BigDecimal(inInvoiceMV.getQuantity())));
        inInvoiceMV.setTax(inInvoiceMV.getSubtotal().multiply(new BigDecimal("0.06")));
        inInvoiceMV.setProcessingFee(new BigDecimal("10.00"));
        inInvoiceMV.setTotal(inInvoiceMV.getSubtotal().add(inInvoiceMV.getTax()).add(inInvoiceMV.getProcessingFee()));

        when(this.invoiceServiceLayer.createInvoice(inInvoiceMV)).thenReturn(null);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/invoice")
                                .content(mapper.writeValueAsString(inInvoiceMV))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());

        inInvoiceMV = new InvoiceViewModel();
        inInvoiceMV.setName("Johnny Dangerously");
        inInvoiceMV.setStreet("Once Blvd");
        inInvoiceMV.setCity("New York");
        inInvoiceMV.setState(null);
        inInvoiceMV.setZipcode("10016");
        inInvoiceMV.setItemType("T-Shirt");
        inInvoiceMV.setItemId(12);
        inInvoiceMV.setUnitPrice(new BigDecimal("12.50"));
        inInvoiceMV.setQuantity(2);
        inInvoiceMV.setSubtotal(inInvoiceMV.getUnitPrice().multiply(new BigDecimal(inInvoiceMV.getQuantity())));
        inInvoiceMV.setTax(inInvoiceMV.getSubtotal().multiply(new BigDecimal("0.06")));
        inInvoiceMV.setProcessingFee(new BigDecimal("10.00"));
        inInvoiceMV.setTotal(inInvoiceMV.getSubtotal().add(inInvoiceMV.getTax()).add(inInvoiceMV.getProcessingFee()));

        when(this.invoiceServiceLayer.createInvoice(inInvoiceMV)).thenReturn(null);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/invoice")
                                .content(mapper.writeValueAsString(inInvoiceMV)) 
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) 
                .andExpect(status().isUnprocessableEntity()); 

        inInvoiceMV = new InvoiceViewModel();
        inInvoiceMV.setName("Johnny Dangerously");
        inInvoiceMV.setStreet("Once Blvd");
        inInvoiceMV.setCity("New York");
        inInvoiceMV.setState("NY");
        inInvoiceMV.setZipcode("");
        inInvoiceMV.setItemType("T-Shirt");
        inInvoiceMV.setItemId(12);
        inInvoiceMV.setUnitPrice(new BigDecimal("12.50"));
        inInvoiceMV.setQuantity(2);
        inInvoiceMV.setSubtotal(inInvoiceMV.getUnitPrice().multiply(new BigDecimal(inInvoiceMV.getQuantity())));
        inInvoiceMV.setTax(inInvoiceMV.getSubtotal().multiply(new BigDecimal("0.06")));
        inInvoiceMV.setProcessingFee(new BigDecimal("10.00"));
        inInvoiceMV.setTotal(inInvoiceMV.getSubtotal().add(inInvoiceMV.getTax()).add(inInvoiceMV.getProcessingFee()));

        when(this.invoiceServiceLayer.createInvoice(inInvoiceMV)).thenReturn(null);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/invoice")
                                .content(mapper.writeValueAsString(inInvoiceMV)) 
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity()); 

        inInvoiceMV = new InvoiceViewModel();
        inInvoiceMV.setName("Johnny Dangerously");
        inInvoiceMV.setStreet("Once Blvd");
        inInvoiceMV.setCity("New York");
        inInvoiceMV.setState("NY");
        inInvoiceMV.setZipcode(null);
        inInvoiceMV.setItemType("T-Shirt");
        inInvoiceMV.setItemId(12);
        inInvoiceMV.setUnitPrice(new BigDecimal("12.50"));
        inInvoiceMV.setQuantity(2);
        inInvoiceMV.setSubtotal(inInvoiceMV.getUnitPrice().multiply(new BigDecimal(inInvoiceMV.getQuantity())));
        inInvoiceMV.setTax(inInvoiceMV.getSubtotal().multiply(new BigDecimal("0.06")));
        inInvoiceMV.setProcessingFee(new BigDecimal("10.00"));
        inInvoiceMV.setTotal(inInvoiceMV.getSubtotal().add(inInvoiceMV.getTax()).add(inInvoiceMV.getProcessingFee()));

        when(this.invoiceServiceLayer.createInvoice(inInvoiceMV)).thenReturn(null);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/invoice")
                                .content(mapper.writeValueAsString(inInvoiceMV)) 
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity()); 

 
        inInvoiceMV = new InvoiceViewModel();
        inInvoiceMV.setName("Johnny Dangerously");
        inInvoiceMV.setStreet("Once Blvd");
        inInvoiceMV.setCity("New York");
        inInvoiceMV.setState("NY");
        inInvoiceMV.setZipcode("10016");
        inInvoiceMV.setItemType("");
        inInvoiceMV.setItemId(12);
        inInvoiceMV.setUnitPrice(new BigDecimal("12.50"));
        inInvoiceMV.setQuantity(2);
        inInvoiceMV.setSubtotal(inInvoiceMV.getUnitPrice().multiply(new BigDecimal(inInvoiceMV.getQuantity())));
        inInvoiceMV.setTax(inInvoiceMV.getSubtotal().multiply(new BigDecimal("0.06")));
        inInvoiceMV.setProcessingFee(new BigDecimal("10.00"));
        inInvoiceMV.setTotal(inInvoiceMV.getSubtotal().add(inInvoiceMV.getTax()).add(inInvoiceMV.getProcessingFee()));

        when(this.invoiceServiceLayer.createInvoice(inInvoiceMV)).thenReturn(null);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/invoice")
                                .content(mapper.writeValueAsString(inInvoiceMV)) 
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) 
                .andExpect(status().isUnprocessableEntity()); 

        inInvoiceMV = new InvoiceViewModel();
        inInvoiceMV.setName("Johnny Dangerously");
        inInvoiceMV.setStreet("Once Blvd");
        inInvoiceMV.setCity("New York");
        inInvoiceMV.setState("NY");
        inInvoiceMV.setZipcode("10016");
        inInvoiceMV.setItemType(null);
        inInvoiceMV.setItemId(12);
        inInvoiceMV.setUnitPrice(new BigDecimal("12.50"));
        inInvoiceMV.setQuantity(2);
        inInvoiceMV.setSubtotal(inInvoiceMV.getUnitPrice().multiply(new BigDecimal(inInvoiceMV.getQuantity())));
        inInvoiceMV.setTax(inInvoiceMV.getSubtotal().multiply(new BigDecimal("0.06")));
        inInvoiceMV.setProcessingFee(new BigDecimal("10.00"));
        inInvoiceMV.setTotal(inInvoiceMV.getSubtotal().add(inInvoiceMV.getTax()).add(inInvoiceMV.getProcessingFee()));

        when(this.invoiceServiceLayer.createInvoice(inInvoiceMV)).thenReturn(null);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/invoice")
                                .content(mapper.writeValueAsString(inInvoiceMV)) 
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) 
                .andExpect(status().isUnprocessableEntity()); 

 
        inInvoiceMV = new InvoiceViewModel();
        inInvoiceMV.setName("Johnny Dangerously");
        inInvoiceMV.setStreet("Once Blvd");
        inInvoiceMV.setCity("New York");
        inInvoiceMV.setState("NY");
        inInvoiceMV.setZipcode("10016");
        inInvoiceMV.setItemType("T-Shirt");
        inInvoiceMV.setItemId(12);
        inInvoiceMV.setUnitPrice(new BigDecimal("12.50"));
        inInvoiceMV.setQuantity(0);
        inInvoiceMV.setSubtotal(inInvoiceMV.getUnitPrice().multiply(new BigDecimal(inInvoiceMV.getQuantity())));
        inInvoiceMV.setTax(inInvoiceMV.getSubtotal().multiply(new BigDecimal("0.06")));
        inInvoiceMV.setProcessingFee(new BigDecimal("10.00"));
        inInvoiceMV.setTotal(inInvoiceMV.getSubtotal().add(inInvoiceMV.getTax()).add(inInvoiceMV.getProcessingFee()));

        when(this.invoiceServiceLayer.createInvoice(inInvoiceMV)).thenReturn(null);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/invoice")
                                .content(mapper.writeValueAsString(inInvoiceMV)) 
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) 
                .andExpect(status().isUnprocessableEntity()); 

        inInvoiceMV = new InvoiceViewModel();
        inInvoiceMV.setName("Johnny Dangerously");
        inInvoiceMV.setStreet("Once Blvd");
        inInvoiceMV.setCity("New York");
        inInvoiceMV.setState("NY");
        inInvoiceMV.setZipcode("10016");
        inInvoiceMV.setItemType("T-Shirt");
        inInvoiceMV.setItemId(12);
        inInvoiceMV.setUnitPrice(new BigDecimal("12.50"));
        inInvoiceMV.setQuantity(50001);
        inInvoiceMV.setSubtotal(inInvoiceMV.getUnitPrice().multiply(new BigDecimal(inInvoiceMV.getQuantity())));
        inInvoiceMV.setTax(inInvoiceMV.getSubtotal().multiply(new BigDecimal("0.06")));
        inInvoiceMV.setProcessingFee(new BigDecimal("10.00"));
        inInvoiceMV.setTotal(inInvoiceMV.getSubtotal().add(inInvoiceMV.getTax()).add(inInvoiceMV.getProcessingFee()));

        when(this.invoiceServiceLayer.createInvoice(inInvoiceMV)).thenReturn(null);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/invoice")
                                .content(mapper.writeValueAsString(inInvoiceMV)) 
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) 
                .andExpect(status().isUnprocessableEntity());

    }
}