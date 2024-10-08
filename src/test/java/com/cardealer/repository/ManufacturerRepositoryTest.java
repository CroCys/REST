package com.cardealer.repository;

import com.cardealer.dto.ManufacturerDto;
import com.cardealer.util.DataBaseUtil;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ManufacturerRepositoryTest {
    @InjectMocks
    private ManufacturerRepository manufacturerRepository;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    private static MockedStatic<DataBaseUtil> mockedDataBaseUtil;

    @BeforeAll
    static void setupClass() {
        mockedDataBaseUtil = Mockito.mockStatic(DataBaseUtil.class);
    }

    @BeforeEach
    void setUp() throws SQLException {
        when(DataBaseUtil.getConnection()).thenReturn(connection);
    }

    @AfterAll
    static void tearDownClass() {
        mockedDataBaseUtil.close();
    }

    @Test
    void testGetManufacturerById() throws SQLException {
        int manufacturerId = 1;
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id")).thenReturn(manufacturerId);
        when(resultSet.getString("name")).thenReturn("Toyota");

        ManufacturerDto result = manufacturerRepository.getManufacturerById(manufacturerId);

        assertNotNull(result);
        assertEquals(manufacturerId, result.getId());
        assertEquals("Toyota", result.getName());
        verify(preparedStatement).setInt(1, manufacturerId);
    }

    @Test
    void testAddManufacturer() throws SQLException {
        ManufacturerDto manufacturerDto = new ManufacturerDto();
        manufacturerDto.setName("Honda");

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        manufacturerRepository.addManufacturer(manufacturerDto);

        verify(preparedStatement).setString(1, manufacturerDto.getName());
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void testUpdateManufacturer() throws SQLException {
        ManufacturerDto manufacturerDto = new ManufacturerDto();
        manufacturerDto.setId(1);
        manufacturerDto.setName("Nissan");

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        manufacturerRepository.updateManufacturer(manufacturerDto);

        verify(preparedStatement).setString(1, manufacturerDto.getName());
        verify(preparedStatement).setInt(2, manufacturerDto.getId());
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void testDeleteManufacturer() throws SQLException {
        int manufacturerId = 1;
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        manufacturerRepository.deleteManufacturer(manufacturerId);

        verify(preparedStatement).setInt(1, manufacturerId);
        verify(preparedStatement).executeUpdate();
    }
}
