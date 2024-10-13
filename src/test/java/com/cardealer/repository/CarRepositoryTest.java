package com.cardealer.repository;

import com.cardealer.dto.CarDto;
import com.cardealer.entity.Manufacturer;
import com.cardealer.util.DataBaseUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarRepositoryTest {

    @InjectMocks
    private CarRepository carRepository;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    private MockedStatic<DataBaseUtil> mockedDataBaseUtil;

    @BeforeEach
    void setUp() throws SQLException {
        mockedDataBaseUtil = Mockito.mockStatic(DataBaseUtil.class);
        when(DataBaseUtil.getConnection()).thenReturn(connection);
    }

    @AfterEach
    void tearDown() {
        mockedDataBaseUtil.close();
    }

    @Test
    public void testGetCarById() throws SQLException {
        // Arrange
        int carId = 1;
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id")).thenReturn(carId);
        when(resultSet.getString("model")).thenReturn("Toyota");
        when(resultSet.getInt("manufacturerId")).thenReturn(1);

        // Act
        CarDto result = carRepository.getCarById(carId);

        // Assert
        assertNotNull(result);
        assertEquals(carId, result.getId());
        assertEquals("Toyota", result.getModel());
        assertEquals(1, result.getManufacturerId());

        // Verify that the correct methods were called
        verify(preparedStatement).setInt(1, carId);
        verify(preparedStatement).executeQuery();
    }

    @Test
    public void testGetCarById2() throws SQLException {
        // Arrange
        int carId = 2;
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false); // No results

        // Act
        CarDto result = carRepository.getCarById(carId);

        // Assert
        assertNull(result);

        // Verify that the correct methods were called
        verify(preparedStatement).setInt(1, carId);
        verify(preparedStatement).executeQuery();
    }

    @Test
    void testUpdateCar() throws SQLException {
        CarDto carDto = new CarDto();
        carDto.setId(1);
        carDto.setModel("Toyota");
        carDto.setManufacturerId(2);
        List<Integer> customerIds = new ArrayList<>();
        customerIds.add(1);
        customerIds.add(2);
        carDto.setCustomerIds(customerIds);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        // Мокаем ResultSet для getManufacturerById
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("id")).thenReturn(2);
        when(resultSet.getString("name")).thenReturn("Toyota Manufacturer");

        // Мокаем executeUpdate для обновления машины и клиентов
        when(preparedStatement.executeUpdate()).thenReturn(1);

        // Вызываем метод updateCar
        carRepository.updateCar(carDto);

        // Используем InOrder для проверки последовательности вызовов executeUpdate
        InOrder inOrder = inOrder(preparedStatement);

        // Проверяем, что сначала обновлена машина
        inOrder.verify(preparedStatement, times(1)).setString(1, carDto.getModel());
        inOrder.verify(preparedStatement, times(1)).setInt(2, carDto.getManufacturerId());
        inOrder.verify(preparedStatement, times(1)).setInt(3, carDto.getId());
        inOrder.verify(preparedStatement, times(1)).executeUpdate();

        // Затем должны обновиться связи с клиентами
        inOrder.verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void testGetManufacturerById() throws SQLException {
        // Arrange
        Manufacturer expectedManufacturer = new Manufacturer();
        expectedManufacturer.setId(1);
        expectedManufacturer.setName("Honda");

        // Mock behavior of the connection and statement
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true); // Simulate that the result set has data
        when(resultSet.getInt("id")).thenReturn(expectedManufacturer.getId());
        when(resultSet.getString("name")).thenReturn(expectedManufacturer.getName());

        // Act
        Manufacturer actualManufacturer = carRepository.getManufacturerById(1);

        // Assert
        assertEquals(expectedManufacturer.getId(), actualManufacturer.getId());
        assertEquals(expectedManufacturer.getName(), actualManufacturer.getName());
    }

    @Test
    public void testAddCar() throws SQLException {
        // Arrange
        CarDto carDto = new CarDto();
        carDto.setId(1);
        carDto.setModel("Ford Focus");
        carDto.setManufacturerId(1);
        carDto.setCustomerIds(Arrays.asList(1));

        when(connection.prepareStatement(any(String.class))).thenThrow(new SQLException("Database error"));

        // Act & Assert
        assertThrows(SQLException.class, () -> carRepository.addCar(carDto));

        // Verify that no methods related to the successful addition of a car were called
        verify(preparedStatement, never()).executeUpdate();
    }

    @Test
    void testDeleteCar() throws SQLException {
        int carId = 1;

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        carRepository.deleteCar(carId);

        // Используем inOrder для проверки порядка вызовов методов
        InOrder inOrder = inOrder(preparedStatement);
        inOrder.verify(preparedStatement, times(1)).setInt(1, carId);  // для deleteCarCustomers
        inOrder.verify(preparedStatement, times(1)).executeUpdate();
        inOrder.verify(preparedStatement, times(1)).setInt(1, carId);  // для deleteCarById
        inOrder.verify(preparedStatement, times(1)).executeUpdate();
    }
}
