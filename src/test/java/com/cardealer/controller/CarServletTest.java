package com.cardealer.controller;

import com.cardealer.dto.CarDto;
import com.cardealer.service.CarService;
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

class CarServletTest {
    private CarServlet carServlet;
    private CarService carService;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        carService = mock(CarService.class);
        carServlet = new CarServlet();
        carServlet.carService = carService;
        objectMapper = new ObjectMapper();
        carServlet.objectMapper = objectMapper;

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
    }

    private void setUpWriterMock() throws IOException {
        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);
    }

    @Test
    void testDoGet_CarFound() throws Exception {
        CarDto carDto = new CarDto();
        carDto.setId(1);
        carDto.setModel("Test Model");
        when(request.getParameter("id")).thenReturn("1");
        when(carService.getCarById(1)).thenReturn(carDto);

        setUpWriterMock();

        carServlet.doGet(request, response);

        verify(response).setContentType("application/json");
        verify(response.getWriter()).write(objectMapper.writeValueAsString(carDto));
    }

    @Test
    void testDoGet_CarNotFound() throws Exception {
        when(request.getParameter("id")).thenReturn("1");
        when(carService.getCarById(1)).thenReturn(null);

        setUpWriterMock();

        carServlet.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_NOT_FOUND);
        verify(response.getWriter()).write("Car not found");
    }

    @Test
    void testDoGet_MissingIdParameter() throws Exception {
        when(request.getParameter("id")).thenReturn(null);

        setUpWriterMock();

        carServlet.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        verify(response.getWriter()).write("Missing id parameter");
    }

    @Test
    void testDoPost_CarAdded() throws Exception {
        CarDto carDto = new CarDto();
        carDto.setId(1);
        carDto.setModel("Test Model");
        String carJson = objectMapper.writeValueAsString(carDto);
        InputStream inputStream = new ByteArrayInputStream(carJson.getBytes());
        when(request.getInputStream()).thenReturn(new MockServletInputStream(inputStream));

        setUpWriterMock();

        carServlet.doPost(request, response);

        verify(carService).addCar(any(CarDto.class));
        verify(response).setStatus(HttpServletResponse.SC_CREATED);
    }

    @Test
    void testDoPut_CarUpdated() throws Exception {
        CarDto carDto = new CarDto();
        carDto.setId(1);
        carDto.setModel("Updated Model");
        String carJson = objectMapper.writeValueAsString(carDto);
        InputStream inputStream = new ByteArrayInputStream(carJson.getBytes());
        when(request.getInputStream()).thenReturn(new MockServletInputStream(inputStream));

        setUpWriterMock();

        carServlet.doPut(request, response);

        verify(carService).updateCar(any(CarDto.class));
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    void testDoDelete_CarDeleted() throws Exception {
        when(request.getParameter("id")).thenReturn("1");

        setUpWriterMock();

        carServlet.doDelete(request, response);

        verify(carService).deleteCar(1);
        verify(response).setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @Test
    void testDoDelete_MissingIdParameter() throws Exception {
        when(request.getParameter("id")).thenReturn(null);

        setUpWriterMock(); // Настраиваем PrintWriter

        carServlet.doDelete(request, response);

        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        verify(response.getWriter()).write("Missing id parameter");
    }

    // Утилита для Mock InputStream
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
