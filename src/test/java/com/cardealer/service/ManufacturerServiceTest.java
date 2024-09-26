package com.cardealer.service;

import com.cardealer.dto.ManufacturerDto;
import com.cardealer.repository.ManufacturerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ManufacturerServiceTest {

    @Mock
    private ManufacturerRepository manufacturerRepository;

    @InjectMocks
    private ManufacturerService manufacturerService;
    private ManufacturerDto manufacturerDTO;

    @BeforeEach
    void setUp() {
        manufacturerDTO = new ManufacturerDto();
        manufacturerDTO.setId(3);
        manufacturerDTO.setName("John Doe");
    }

    @Test
    void testGetManufacturerById() throws Exception {
        when(manufacturerService.getManufacturerById(3)).thenReturn(manufacturerDTO);
        ManufacturerDto result = manufacturerService.getManufacturerById(3);
        assertEquals(manufacturerDTO, result);
        verify(manufacturerRepository, times(1)).getManufacturerById(3);
    }

    @Test
    void testGetManufacturerByIdException() throws Exception {
        when(manufacturerRepository.getManufacturerById(3)).thenThrow(SQLException.class);
        assertThrows(SQLException.class, () -> manufacturerService.getManufacturerById(3));
        verify(manufacturerRepository, times(1)).getManufacturerById(3);
    }

    @Test
    void testAddManufacturer() throws Exception {
        manufacturerService.addManufacturer(manufacturerDTO);
        verify(manufacturerRepository, times(1)).addManufacturer(manufacturerDTO);
    }

    @Test
    void testAddManufacturerException() throws Exception {
        doThrow(SQLException.class).when(manufacturerRepository).addManufacturer(manufacturerDTO);
        assertThrows(SQLException.class, () -> manufacturerService.addManufacturer(manufacturerDTO));
        verify(manufacturerRepository, times(1)).addManufacturer(manufacturerDTO);
    }

    @Test
    void testUpdateManufacturer() throws Exception {
        manufacturerService.updateManufacturer(manufacturerDTO);
        verify(manufacturerRepository, times(1)).updateManufacturer(manufacturerDTO);
    }

    @Test
    void testUpdateManufacturerException() throws Exception {
        doThrow(SQLException.class).when(manufacturerRepository).updateManufacturer(manufacturerDTO);
        assertThrows(SQLException.class, () -> manufacturerService.updateManufacturer(manufacturerDTO));
        verify(manufacturerRepository, times(1)).updateManufacturer(manufacturerDTO);
    }

    @Test
    void testDeleteManufacturer() throws Exception {
        manufacturerService.deleteManufacturer(3);
        verify(manufacturerRepository, times(1)).deleteManufacturer(3);
    }

    @Test
    void testDeleteManufacturerException() throws Exception {
        doThrow(SQLException.class).when(manufacturerRepository).deleteManufacturer(3);
        assertThrows(SQLException.class, () -> manufacturerService.deleteManufacturer(3));
        verify(manufacturerRepository, times(1)).deleteManufacturer(3);
    }
}
