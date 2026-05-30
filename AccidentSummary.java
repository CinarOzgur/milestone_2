package com.vicroadsafety.model;

public class AccidentSummary {
    private String lgaName;
    private String regionType;
    private int totalAccidents;
    private int youthInjuries;
    private int fatalCount;

     Secure Data Injection Constructor
    public AccidentSummary(String lgaName, String regionType, int totalAccidents, int youthInjuries, int fatalCount) {
        this.lgaName = lgaName;
        this.regionType = regionType;
        this.totalAccidents = totalAccidents;
        this.youthInjuries = youthInjuries;
        this.fatalCount = fatalCount;
    }

     Accessor Layer mapped for Thymeleaf template engine extraction
    public String getLgaName() { return lgaName; }
    public String getRegionType() { return regionType; }
    public int getTotalAccidents() { return totalAccidents; }
    public int getYouthInjuries() { return youthInjuries; }
    public int getFatalCount() { return fatalCount; }
}