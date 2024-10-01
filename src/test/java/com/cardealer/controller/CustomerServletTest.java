package com.cardealer.controller;

import com.cardealer.dto.CustomerDto;
import com.cardealer.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CustomerServletTest {
    private CustomerServlet customerServlet;
    private CustomerService customerService;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        customerService = mock(CustomerService.class);
        customerServlet = new CustomerServlet();
        customerServlet.customerService = customerService;
        objectMapper = new ObjectMapper();
        customerServlet.objectMapper = objectMapper;

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
    }

    private void setUpWriterMock() throws IOException {
        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);
    }

    @Test
    void testDoGet_CustomerFound() throws Exception {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(1);
        customerDto.setName("Test Customer");
        when(request.getParameter("id")).thenReturn("1");
        when(customerService.getCustomerById(1)).thenReturn(customerDto);

        setUpWriterMock();

        customerServlet.doGet(request, response);

        verify(response).setContentType("application/json");
        verify(response.getWriter()).write(objectMapper.writeValueAsString(customerDto));
    }

    @Test
    void testDoGet_CustomerNotFound() throws Exception {
        when(request.getParameter("id")).thenReturn("1");
        when(customerService.getCustomerById(1)).thenReturn(null);

        setUpWriterMock();

        customerServlet.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_NOT_FOUND);
        verify(response.getWriter()).write("Customer not found");
    }

    @Test
    void testDoGet_MissingIdParameter() throws Exception {
        when(request.getParameter("id")).thenReturn(null);

        setUpWriterMock();

        customerServlet.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        verify(response.getWriter()).write("Missing id parameter");
    }

    @Test
    void testDoPost_CustomerAdded() throws Exception {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(1);
        customerDto.setName("Test Customer");
        String customerJson = objectMapper.writeValueAsString(customerDto);
        InputStream inputStream = new ByteArrayInputStream(customerJson.getBytes());
        when(request.getInputStream()).thenReturn(new MockServletInputStream(inputStream));

        setUpWriterMock();

        customerServlet.doPost(request, response);

        verify(customerService).addCustomer(any(CustomerDto.class));
        verify(response).setStatus(HttpServletResponse.SC_CREATED);
    }

    @Test
    void testDoPut_CustomerUpdated() throws Exception {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(1);
        customerDto.setName("Updated Customer");
        String customerJson = objectMapper.writeValueAsString(customerDto);
        InputStream inputStream = new ByteArrayInputStream(customerJson.getBytes());
        when(request.getInputStream()).thenReturn(new MockServletInputStream(inputStream));

        setUpWriterMock();

        customerServlet.doPut(request, response);

        verify(customerService).updateCustomer(any(CustomerDto.class));
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    void testDoDelete_CustomerDeleted() throws Exception {
        when(request.getParameter("id")).thenReturn("1");

        setUpWriterMock();

        customerServlet.doDelete(request, response);

        verify(customerService).deleteCustomer(1);
        verify(response).setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @Test
    void testDoDelete_MissingIdParameter() throws Exception {
        when(request.getParameter("id")).thenReturn(null);

        setUpWriterMock();

        customerServlet.doDelete(request, response);

        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        verify(response.getWriter()).write("Missing id parameter");
    }

    private static class MockServletInputStream extends ServletInputStream {
        private final InputStream inputStream;

        MockServletInputStream(InputStream inputStream) {
            this.inputStream = inputStream;
        }

        @Override
        public boolean isFinished() {
            try {
                return inputStream.available() == 0;
            } catch (IOException e) {
                return true;
            }
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener readListener) {

        }

        @Override
        public int read() throws IOException {
            return inputStream.read();
        }
    }
}
