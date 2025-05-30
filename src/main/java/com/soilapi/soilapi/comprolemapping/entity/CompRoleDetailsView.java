package com.soilapi.soilapi.comprolemapping.entity;

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
@Table(name= "vw_CompRoleDetails", schema= "dbo")
public class CompRoleDetailsView {
     @Id
    @Column(name = "ViewUID")
    private String viewUID;

    @Column(name = "SourceType")
    private String sourceType;

    @Column(name = "RoleID")
    private Long roleId;

    @Column(name = "RoleName")
    private String roleName;

    @Column(name = "CID")
    private Long CID;

    @Column(name = "CompID")
    private String compId;

    @Column(name = "CompName")
    private String compName;

    @Column(name = "CompType")
    private String compType;
}
