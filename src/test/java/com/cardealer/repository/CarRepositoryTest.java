//package com.cardealer.repository;
//
//import com.cardealer.dto.CarDto;
//import com.cardealer.entity.Customer;
//import com.cardealer.entity.Manufacturer;
//import com.cardealer.util.DataBaseUtil;
//import com.zaxxer.hikari.HikariDataSource;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.sql.*;
//import java.util.Arrays;
//
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.*;
//
//public class CarRepositoryTest {
//    private CarRepository carRepository;
//
//    @Mock
//    private HikariDataSource mockDataSource;
//
//    @Mock
//    private Connection mockConnection;
//
//    @Mock
//    private PreparedStatement mockStatement;
//
//    @Mock
//    private ResultSet mockResultSet;
//
//    @Mock
//    private Manufacturer mockManufacturer;
//
//    @Mock
//    private Customer mockCustomer;
//
//    @BeforeEach
//    public void setUp() throws SQLException {
//        MockitoAnnotations.openMocks(this);
//        when(mockDataSource.getConnection()).thenReturn(mockConnection);
//        DataBaseUtil.setDataSource(mockDataSource);
//        carRepository = new CarRepository();
//
//        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
//    }
//
//    @Test
//    public void testAddCar() throws SQLException {
//        CarDto carDto = new CarDto();
//        carDto.setModel("New Model");
//        carDto.setManufacturerId(1);
//        carDto.setCustomerIds(Arrays.asList(1, 2));
//
//        when(carRepository.getManufacturerById(1)).thenReturn(mockManufacturer);
//
//        when(carRepository.getCustomersByIds(Arrays.asList(1, 2))).thenReturn(Arrays.asList(mockCustomer, mockCustomer));
//
//        when(mockStatement.executeUpdate()).thenReturn(1);
//
//        carRepository.addCar(carDto);
//
//        verify(mockStatement, times(1)).executeUpdate();
//    }
//
//    @Test
//    public void testUpdateCar() throws SQLException {
//        CarDto carDto = new CarDto();
//        carDto.setId(1);
//        carDto.setModel("Updated Model");
//        carDto.setManufacturerId(1);
//        carDto.setCustomerIds(Arrays.asList(1, 2));
//
//        when(carRepository.getManufacturerById(1)).thenReturn(mockManufacturer);
//
//        when(carRepository.getCustomersByIds(Arrays.asList(1, 2))).thenReturn(Arrays.asList(mockCustomer, mockCustomer));
//
//        when(mockStatement.executeUpdate()).thenReturn(1);
//
//        carRepository.updateCar(carDto);
//
//        verify(mockStatement, times(1)).executeUpdate();
//    }
//}
