package com.soilapi.soilapi.kpikopfilteradmin.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.soilapi.soilapi.kpikopfilteradmin.dto.KpiKopFilterAdminInsertRequest;
import com.soilapi.soilapi.kpikopfilteradmin.dto.KpiKopFilterAdminSelectFlatResponse;
import com.soilapi.soilapi.kpikopfilteradmin.dto.KpiKopFilterAdminSelectRequest;
import com.soilapi.soilapi.kpikopfilteradmin.dto.KpiKopFilterAdminSelectResponse;
import com.soilapi.soilapi.kpikopfilteradmin.dto.KpiKopFilterAdminUpdateRequest;
import com.soilapi.soilapi.kpikopfilteradmin.util.KpiKopFilterAdminProcedureBinder;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import jakarta.persistence.StoredProcedureQuery;

@Service
public class KpiKopFilterAdminService {
    
    @PersistenceContext
    private EntityManager eManager;

    public Page<KpiKopFilterAdminSelectResponse> selectKpiKopFilterAdmin(KpiKopFilterAdminSelectRequest dto){
        try{
            StoredProcedureQuery query = eManager.createStoredProcedureQuery("sg_SelectKpikopFilterAdmin");
            KpiKopFilterAdminProcedureBinder.bindKpiKopFilterAdminParams(query, dto);
            query.execute();

            @SuppressWarnings("unchecked")
            List<Object[]> resultList = query.getResultList();
            List<KpiKopFilterAdminSelectFlatResponse> responseList = resultList.stream().map(row -> {
                KpiKopFilterAdminSelectFlatResponse response = new KpiKopFilterAdminSelectFlatResponse();
                response.setCompId((String)row[0]);
                response.setCompName((String)row[1]);
                response.setCompNameKorean((String)row[2]);
                response.setCompType((String)row[3]);

                response.setFilterId(row[4] == null ? null : ((Number) row[4]).intValue());
                response.setFilterName((String)row[5]);
                response.setFilterLabel((String)row[6]);
                response.setFilterType((String)row[7]);
                response.setFilterSequence(row[8] == null ? null : ((Number) row[8]).intValue());

                response.setWidgetObjectId((String)row[9]);
                response.setFilterObjId((String)row[10]);
                response.setFilterVariableOptionId(row[11] == null ? null : ((Number) row[11]).intValue());
                response.setPageName((String)row[12]);
                response.setFilterQlikId((String)row[13]);
                response.setFieldName((String)row[14]);
                
                return response;
            }).collect(Collectors.toList());

            Map<String, KpiKopFilterAdminSelectResponse> compMap = new LinkedHashMap<>();

            for (KpiKopFilterAdminSelectFlatResponse flat : responseList) {
                String compId = flat.getCompId();

                // 컴포넌트가 없으면 새로 생성
                compMap.computeIfAbsent(compId, id -> {
                    KpiKopFilterAdminSelectResponse response = new KpiKopFilterAdminSelectResponse();
                    response.setCompId(flat.getCompId());
                    response.setCompName(flat.getCompName());
                    response.setCompNameKorean(flat.getCompNameKorean());
                    response.setCompType(flat.getCompType());
                    response.setFilters(new ArrayList<>());
                    return response;
                });

                // Filter 하위 항목 생성 및 추가
                if(flat.getFilterId() != null){
                    KpiKopFilterAdminSelectResponse.FilterInfo filter  = new KpiKopFilterAdminSelectResponse.FilterInfo();
                    filter.setFilterId(flat.getFilterId());
                    filter.setFilterName(flat.getFilterName());
                    filter.setFilterLabel(flat.getFilterLabel());
                    filter.setFilterType(flat.getFilterType());
                    filter.setFilterSequence(flat.getFilterSequence());
                    filter.setWidgetObjectId(flat.getWidgetObjectId());
                    filter.setFilterObjId(flat.getFilterObjId());
                    filter.setFilterVariableOptionId(flat.getFilterVariableOptionId());
                    filter.setPageName(flat.getPageName());
                    filter.setFilterQlikId(flat.getFilterQlikId());
                    filter.setFieldName(flat.getFieldName());

                    compMap.get(compId).getFilters().add(filter);
                }
            }
            List<KpiKopFilterAdminSelectResponse> responseMapList = new ArrayList<>();
            for (KpiKopFilterAdminSelectResponse comp : compMap.values()) {
                List<KpiKopFilterAdminSelectResponse.FilterInfo> filters = comp.getFilters();
                comp.setFilterCount(filters != null ? filters.size() : 0);
                responseMapList.add(comp);
            }

            String baseQuery = "SELECT COUNT(DISTINCT CompID) AS TotalCount FROM vw_KPIKOPFilterAdmin WHERE 1=1 ";
            String searchType = dto.getSearchType();
            if("compId".equalsIgnoreCase(searchType) && StringUtils.hasText(searchType)){
                baseQuery += "AND compId LIKE :searchKeyword";
            }else if("compName".equalsIgnoreCase(searchType) && StringUtils.hasText(searchType)){
                baseQuery += "AND compName LIKE :searchKeyword";
            }else if("compNameKorean".equalsIgnoreCase(searchType) && StringUtils.hasText(searchType)){
                baseQuery += "AND compNameKorean LIKE :searchKeyword";
            }else if("compType".equalsIgnoreCase(searchType) && StringUtils.hasText(searchType)){
                baseQuery += "AND compType LIKE :searchKeyword";
            }else if("filterName".equalsIgnoreCase(searchType) && StringUtils.hasText(searchType)){
                baseQuery += "AND filterName LIKE :searchKeyword";
            }else if("filterType".equalsIgnoreCase(searchType) && StringUtils.hasText(searchType)){
                baseQuery += "AND filterType LIKE :searchKeyword";
            }
            Query countQuery = eManager.createNativeQuery(baseQuery);
            if(StringUtils.hasText(dto.getSearchKeyword())){
                countQuery.setParameter("searchKeyword", "%"+dto.getSearchKeyword()+"%");
            }
            Long totalCount = ((Number) countQuery.getSingleResult()).longValue();
            Pageable pageable = PageRequest.of(dto.getPageNo() - 1, dto.getPageSize());
            return new PageImpl<>(responseMapList, pageable, totalCount);
        }catch(Exception e){
            throw new RuntimeException("KPI/Kop Filter Select 중 문제 발생.", e);
        }
    }

    public void insertKpiKopFilterAdmin(KpiKopFilterAdminInsertRequest dto){
        try{
            StoredProcedureQuery query = eManager.createStoredProcedureQuery("sg_InsertKpikopFilterAdmin");
            KpiKopFilterAdminProcedureBinder.bindKpiKopFilterAdminInsertParams(query, dto);
            System.out.println(dto);
            query.execute();
        }catch(PersistenceException e){
            throw new RuntimeException("Filter 저장 중 DB 오류 발생", e);

        }catch(Exception e){
            throw new RuntimeException("Filter 저장 중 예상치 못한 오류 발생", e);
        }
                
    }

    public void updateKpiKopFilterAdmin(KpiKopFilterAdminUpdateRequest dto){
        try{
            StoredProcedureQuery query = eManager.createStoredProcedureQuery("sg_UpdateKpikopFilterAdmin");
            KpiKopFilterAdminProcedureBinder.bindKpiKopFilterAdminUpdateParams(query, dto);
            System.out.println(dto);
            query.execute();
        }catch(PersistenceException e){
            throw new RuntimeException("Filter 수정 중 DB 오류 발생", e);

        }catch(Exception e){
            throw new RuntimeException("Filter 수정 중 예상치 못한 오류 발생", e);
        }           
    }

    public void deleteKpiKopFilterAdmin(int filterId){
        try{
            StoredProcedureQuery query = eManager.createStoredProcedureQuery("sg_DeleteKpikopFilterAdmin");
            KpiKopFilterAdminProcedureBinder.bindKpiKopFilterAdminDeleteParams(query, filterId);
            System.out.println(filterId);
            query.execute();
        }catch(PersistenceException e){
            throw new RuntimeException("Filter 삭제 중 DB 오류 발생", e);

        }catch(Exception e){
            throw new RuntimeException("Filter 삭제 중 예상치 못한 오류 발생", e);
        }      
    }

}
