package com.example.halykacademy;

import Service.OrganizationService;
import entity.Organization;

import java.io.*;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@WebServlet(name = "organizations", value = "/organizations")
public class HelloServlet extends HttpServlet {

    private OrganizationService organizationService;

    public HelloServlet() {
        this.organizationService = new OrganizationService();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String jsonResponse = this.organizationService.findAllOrganizations();
        this.outputResponse(response, jsonResponse, 200);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // add student from post body
        String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

        int rc = HttpServletResponse.SC_OK;
        boolean res = this.organizationService.createOrganization(requestBody);
        if (!res) {
            rc = HttpServletResponse.SC_BAD_REQUEST;
        }
        this.outputResponse(response, this.organizationService.findAllOrganizations(), rc);
    }
    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int rc = HttpServletResponse.SC_OK;
        Long id = Long.parseLong(request.getParameter("id"));
        boolean res = this.organizationService.delete(id);
        if (!res) {
            rc = HttpServletResponse.SC_NOT_FOUND;
        }
        this.outputResponse(response, this.organizationService.findAllOrganizations(), rc);
    }

    private void outputResponse(HttpServletResponse response, String payload, int status) {
        response.setHeader("Content-Type", "application/json");
        try {
            response.setStatus(status);
            if (payload != null) {
                OutputStream outputStream = response.getOutputStream();
                outputStream.write(payload.getBytes());
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}