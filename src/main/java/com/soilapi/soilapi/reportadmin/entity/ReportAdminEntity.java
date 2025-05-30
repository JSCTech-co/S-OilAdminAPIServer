package com.soilapi.soilapi.reportadmin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table(name = "vw_ReportAdmin")
@Getter
@Setter
public class ReportAdminEntity {
    @Id
    @Column(name = "CompID")
    private String compId;

    @Column(name = "CompType")
    private String compType;

    @Column(name = "CompName")
    private String compName;

    @Column(name = "CompNameKorean")
    private String compNameKorean;

    @Column(name = "SourceSys")
    private String sourceSys;

    @Column(name = "SourceSysName")
    private String sourceSysName;
    
    @Column(name = "OwnerDept")
    private String ownerDept;

    @Column(name = "HyperLink")
    private String hyperLink;

    @Column(name = "IsActive")
    private String isActive;

    @Column(name = "ETLJobName")
    private String etlJobName;

    @Column(name = "CompSeq")
    private Integer compSeq;

    // Getter/Setter 생략 가능 (Lombok 사용 시 @Getter, @Setter 등으로 대체 가능)
    // 생성자도 필요시 추가
}
