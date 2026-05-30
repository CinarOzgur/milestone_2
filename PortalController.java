package com.vicroadsafety.controller;

import com.vicroadsafety.model.AccidentSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class PortalController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/index")
    public String getDashboard(
            @RequestParam(name = "roadSurface", required = false, defaultValue = "Wet") String roadSurface,
            @RequestParam(name = "lightCondition", required = false, defaultValue = "Night (Street lights off)") String lightCondition,
            Model model) {

        // ==========================================
        // LEVEL 1: MACRO AGGREGATION COMPUTATION
        // ==========================================
        String macroSql = "SELECT COUNT(*) as total, SUM(fatal_count) as fatals FROM accident_records";
        Map<String, Object> macroData = jdbcTemplate.queryForMap(macroSql);
        model.addAttribute("totalStateAccidents", macroData.get("total"));
        model.addAttribute("totalStateFatalities", macroData.get("fatals"));

        // ==========================================
        // LEVEL 2: JOINING, FILTERING, AND SORTING
        // ==========================================
        String sql = "SELECT loc.lga_name, loc.region_type, COUNT(ar.accident_id) as total_accidents, " +
                     "SUM(ar.youth_serious_injury_count) as total_youth_injuries, SUM(ar.fatal_count) as total_fatals " +
                     "FROM accident_records ar " +
                     "INNER JOIN locations loc ON ar.zone_id = loc.zone_id " +
                     "WHERE ar.road_surface = ? AND ar.light_condition = ? " +
                     "GROUP BY loc.lga_name, loc.region_type " +
                     "ORDER BY total_accidents DESC";

        // Bind request parameters safely into standard SQL index frames
        List<AccidentSummary> summaries = jdbcTemplate.query(sql, 
            (rs, rowNum) -> new AccidentSummary(
                rs.getString("lga_name"),
                rs.getString("region_type"),
                rs.getInt("total_accidents"),
                rs.getInt("total_youth_injuries"),
                rs.getInt("total_fatals")
            ), 
            roadSurface, lightCondition
        );

        // Bind attributes back to web presentation stack to preserve state configurations
        model.addAttribute("results", summaries);
        model.addAttribute("selectedSurface", roadSurface);
        model.addAttribute("selectedLight", lightCondition);

        return "analytics";
    }
}