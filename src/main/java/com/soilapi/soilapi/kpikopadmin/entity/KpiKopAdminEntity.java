package com.soilapi.soilapi.kpikopadmin.entity;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Immutable
@Getter
@Setter
@Table(name="vw_KPIKOPAdmin", schema= "dbo")
public class KpiKopAdminEntity {
    @Id
    @Column(name = "CID")
    private Long cid;

    @Column(name = "CompID")
    private String compId;

    @Column(name = "Name_ENG")
    private String nameEng;

    @Column(name = "Name_KOR")
    private String nameKor;

    @Column(name = "WidgetObjectId")
    private String widgetObjectId;

    @Column(name = "OverviewWidgetId")
    private String overviewWidgetId;

    @Column(name = "currentMonth")
    private String currentMonth;

    @Column(name = "flag")
    private String flag;

    @Column(name = "secondaryName")
    private String secondaryName;

    @Column(name = "secondaryValue")
    private String secondaryValue;

    @Column(name = "Reloaddate")
    private String reloaddate;

    @Column(name = "CompName")
    private String compName;

    @Column(name = "UOM")
    private String uom;

    @Column(name = "UOMKorean")
    private String uomKorean;

    @Column(name = "PerformanceArea")
    private String performanceArea;

    @Column(name = "ETLJobName")
    private String etlJobName;

    @Column(name = "isActive")
    private String isActive;
    
}