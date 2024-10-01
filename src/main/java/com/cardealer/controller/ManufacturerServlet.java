package com.cardealer.controller;

import com.cardealer.dto.ManufacturerDto;
import com.cardealer.repository.ManufacturerRepository;
import com.cardealer.service.ManufacturerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

public class ManufacturerServlet extends HttpServlet {
    protected ManufacturerService manufacturerService;
    protected ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void init() {
        objectMapper = new ObjectMapper();
        try {
            manufacturerService = new ManufacturerService(new ManufacturerRepository());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idParam = req.getParameter("id");
        if (idParam != null) {
            int id = Integer.parseInt(idParam);
            try {
                ManufacturerDto manufacturer = manufacturerService.getManufacturerById(id);
                if (manufacturer != null) {
                    resp.setContentType("application/json");
                    resp.getWriter().write(objectMapper.writeValueAsString(manufacturer));
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().write("Manufacturer not found");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Missing id parameter");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            ManufacturerDto manufacturerDTO = objectMapper.readValue(req.getInputStream(), ManufacturerDto.class);
            manufacturerService.addManufacturer(manufacturerDTO);
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (SQLException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            ManufacturerDto manufacturerDTO = objectMapper.readValue(req.getInputStream(), ManufacturerDto.class);
            manufacturerService.updateManufacturer(manufacturerDTO);
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (SQLException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idParam = req.getParameter("id");
        if (idParam != null) {
            int id = Integer.parseInt(idParam);
            try {
                manufacturerService.deleteManufacturer(id);
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } catch (SQLException e) {
                e.printStackTrace();
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Missing id parameter");
        }
    }
}
