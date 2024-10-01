package com.cardealer.controller;

import com.cardealer.dto.ManufacturerDto;
import com.cardealer.service.ManufacturerService;
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

public class ManufacturerServletTest {
    private ManufacturerServlet manufacturerServlet;
    private ManufacturerService manufacturerService;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        manufacturerService = mock(ManufacturerService.class);
        manufacturerServlet = new ManufacturerServlet();
        manufacturerServlet.manufacturerService = manufacturerService;
        objectMapper = new ObjectMapper();
        manufacturerServlet.objectMapper = objectMapper;

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
    }

    private void setUpWriterMock() throws IOException {
        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);
    }

    @Test
    void testDoGet_ManufacturerFound() throws Exception {
        ManufacturerDto manufacturerDto = new ManufacturerDto();
        manufacturerDto.setId(1);
        manufacturerDto.setName("Test Manufacturer");
        when(request.getParameter("id")).thenReturn("1");
        when(manufacturerService.getManufacturerById(1)).thenReturn(manufacturerDto);

        setUpWriterMock();

        manufacturerServlet.doGet(request, response);

        verify(response).setContentType("application/json");
        verify(response.getWriter()).write(objectMapper.writeValueAsString(manufacturerDto));
    }

    @Test
    void testDoGet_ManufacturerNotFound() throws Exception {
        when(request.getParameter("id")).thenReturn("1");
        when(manufacturerService.getManufacturerById(1)).thenReturn(null);

        setUpWriterMock();

        manufacturerServlet.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_NOT_FOUND);
        verify(response.getWriter()).write("Manufacturer not found");
    }

    @Test
    void testDoGet_MissingIdParameter() throws Exception {
        when(request.getParameter("id")).thenReturn(null);

        setUpWriterMock();

        manufacturerServlet.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        verify(response.getWriter()).write("Missing id parameter");
    }

    @Test
    void testDoPost_ManufacturerAdded() throws Exception {
        ManufacturerDto manufacturerDto = new ManufacturerDto();
        manufacturerDto.setId(1);
        manufacturerDto.setName("Test Manufacturer");
        String manufacturerJson = objectMapper.writeValueAsString(manufacturerDto);
        InputStream inputStream = new ByteArrayInputStream(manufacturerJson.getBytes());
        when(request.getInputStream()).thenReturn(new MockServletInputStream(inputStream));

        setUpWriterMock();

        manufacturerServlet.doPost(request, response);

        verify(manufacturerService).addManufacturer(any(ManufacturerDto.class));
        verify(response).setStatus(HttpServletResponse.SC_CREATED);
    }

    @Test
    void testDoPut_ManufacturerUpdated() throws Exception {
        ManufacturerDto manufacturerDto = new ManufacturerDto();
        manufacturerDto.setId(1);
        manufacturerDto.setName("Updated Manufacturer");
        String manufacturerJson = objectMapper.writeValueAsString(manufacturerDto);
        InputStream inputStream = new ByteArrayInputStream(manufacturerJson.getBytes());
        when(request.getInputStream()).thenReturn(new MockServletInputStream(inputStream));

        setUpWriterMock();

        manufacturerServlet.doPut(request, response);

        verify(manufacturerService).updateManufacturer(any(ManufacturerDto.class));
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    void testDoDelete_ManufacturerDeleted() throws Exception {
        when(request.getParameter("id")).thenReturn("1");

        setUpWriterMock();

        manufacturerServlet.doDelete(request, response);

        verify(manufacturerService).deleteManufacturer(1);
        verify(response).setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @Test
    void testDoDelete_MissingIdParameter() throws Exception {
        when(request.getParameter("id")).thenReturn(null);

        setUpWriterMock();

        manufacturerServlet.doDelete(request, response);

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
