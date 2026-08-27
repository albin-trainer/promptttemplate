package com.example.demo.tools;

import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Service;

@Service
public class EmployeeTools {
//Spring AI's annotation-based approach automatically scans 
	//these MCP annotations and registers the methods as MCP tools.
    @McpTool(description = "Get employee details")
    public String getEmployeeDetails(
            @McpToolParam(description = "Employee ID", required = true)
            String employeeId) {

        return "Employee: " + employeeId +
               ", Name: Albin, Department: IT";
    }

    @McpTool(description = "Get employee leave balance")
    public String getLeaveBalance(
            @McpToolParam(description = "Employee ID", required = true)
            String employeeId) {

        return "Employee " + employeeId +
               " has 0 leave days remaining";
    }
}