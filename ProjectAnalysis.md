
# SOIL API 프로젝트 분석

## 1. API 명세

### 1.1. AppMappingController (`/appmapping`)

| Method | URI | 설명 | Request | Response |
| --- | --- | --- | --- | --- |
| GET | /appList | 앱 마스터 목록 조회 (페이징) | `AppMasterListRequest` | `Page<AppMasterListResponse>` |
| POST | /insert | 앱 마스터 정보 추가 | `AppMasterInsertRequest` | `ResponseEntity<String>` |
| POST | /update | 앱 마스터 정보 수정 | `AppMasterUpdateRequest` | `ResponseEntity<String>` |
| GET | /AppToCompList | 앱-컴포넌트 매핑 목록 조회 | `aid` (int) | `AppToCompMappingListResponse` |
| POST | /AppToCompModify | 앱-컴포넌트 매핑 수정 | `AppToCompModifyRequest` | `ResponseEntity<String>` |

### 1.2. CompRoleDetailsViewController (`/roles`)

| Method | URI | 설명 | Request | Response |
| --- | --- | --- | --- | --- |
| GET | /RoleToCompRoleList | 역할별 컴포넌트 목록 조회 (페이징) | `RoleToCompListRequest` | `Page<RoleToCompListResponse>` |
| GET | /RoleToCompMappingList | 역할-컴포넌트 매핑 목록 조회 | `roleId` (int) | `RoleToCompMappingListResponse` |
| POST | /RoleToCompModify | 역할-컴포넌트 매핑 수정 | `RoleToCompModifyRequest` | `ResponseEntity<String>` |
| GET | /CompToRoleCompList | 컴포넌트별 역할 목록 조회 (페이징) | `CompToRoleListRequest` | `Page<CompToRoleListResponse>` |
| GET | /CompToRoleMappingList | 컴포넌트-역할 매핑 목록 조회 | `compId` (String) | `CompToRoleMappingListResponse` |
| POST | /CompToRoleModify | 컴포넌트-역할 매핑 수정 | `CompToRoleModifyRequest` | `ResponseEntity<String>` |

### 1.3. KpiKopAdminController (`/KpiKopAdmin`)

| Method | URI | 설명 | Request | Response |
| --- | --- | --- | --- | --- |
| GET | /getAllList | KPI/KOP 전체 목록 조회 (페이징) | `isActive`, `page`, `size` | `Page<KpiKopAdminEntity>` |
| GET | /searchList | KPI/KOP 검색 (페이징) | `column`, `keyword`, `isActive`, `page`, `size` | `Page<KpiKopAdminEntity>` |
| POST | /insert | KPI/KOP 정보 추가 | `KpiKopAdminInsertRequest` | `ResponseEntity<String>` |
| POST | /update | KPI/KOP 정보 수정 | `KpiKopAdminUpdateRequest` | `ResponseEntity<String>` |
| GET | /select | KPI/KOP 정보 조회 (페이징) | `KpiKopAdminSelectRequest` | `Page<KpiKopAdminSelectResponse>` |

### 1.4. KpiKopFilterAdminController (`/kpikopfilter`)

| Method | URI | 설명 | Request | Response |
| --- | --- | --- | --- | --- |
| GET | /select | KPI/KOP 필터 목록 조회 (페이징) | `KpiKopFilterAdminSelectRequest` | `Page<KpiKopFilterAdminSelectResponse>` |
| POST | /insert | KPI/KOP 필터 추가 | `KpiKopFilterAdminInsertRequest` | `ResponseEntity<String>` |
| POST | /update | KPI/KOP 필터 수정 | `KpiKopFilterAdminUpdateRequest` | `ResponseEntity<String>` |
| GET | /delete | KPI/KOP 필터 삭제 | `filterId` (int) | `ResponseEntity<String>` |

### 1.5. ReportAdminController (`/ReportAdmin`)

| Method | URI | 설명 | Request | Response |
| --- | --- | --- | --- | --- |
| GET | /getAllList | 리포트 전체 목록 조회 (페이징) | `isActive`, `page`, `size` | `Page<ReportAdminEntity>` |
| GET | /searchList | 리포트 검색 (페이징) | `column`, `keyword`, `isActive`, `page`, `size` | `Page<ReportAdminEntity>` |
| POST | /insert | 리포트 정보 추가 | `ReportAdminInsertRequest` | `ResponseEntity<String>` |
| POST | /update | 리포트 정보 수정 | `ReportAdminUpdateRequest` | `ResponseEntity<String>` |
| GET | /select | 리포트 정보 조회 (페이징) | `ReportAdminSelectRequest` | `Page<ReportAdminSelectResponse>` |

## 2. DB 프로시저 정의

### 2.1. AppMapping

-   `sg_SelectAppMaster`: 앱 마스터 목록 조회
-   `sg_InsertAppMaster`: 앱 마스터 정보 추가
-   `sg_UpdateAppMaster`: 앱 마스터 정보 수정
-   `sg_SelectAppToCompMappingList`: 앱-컴포넌트 매핑 목록 조회
-   `sg_ModifyAppToCompMapping`: 앱-컴포넌트 매핑 수정

### 2.2. CompRoleMapping

-   `sg_SelectRoleToCompRoleList`: 역할 목록 조회 (컴포넌트 매핑용)
-   `sg_SelectRoleToCompMappingList`: 역할-컴포넌트 매핑 목록 조회
-   `sg_ModifyRoleToCompMapping`: 역할-컴포넌트 매핑 수정
-   `sg_SelectCompToRoleCompList`: 컴포넌트 목록 조회 (역할 매핑용)
-   `sg_SelectCompToRoleMappingList`: 컴포넌트-역할 매핑 목록 조회
-   `sg_ModifyCompToRoleMapping`: 컴포넌트-역할 매핑 수정

### 2.3. KpiKopAdmin

-   `sg_InsertKpiKopAdmin`: KPI/KOP 정보 추가
-   `sg_UpdateKpiKopAdmin`: KPI/KOP 정보 수정
-   `sg_SelectKpiKopAdmin`: KPI/KOP 정보 조회

### 2.4. KpiKopFilterAdmin

-   `sg_SelectKpikopFilterAdmin`: KPI/KOP 필터 목록 조회
-   `sg_InsertKpikopFilterAdmin`: KPI/KOP 필터 추가
-   `sg_UpdateKpikopFilterAdmin`: KPI/KOP 필터 수정
-   `sg_DeleteKpikopFilterAdmin`: KPI/KOP 필터 삭제

### 2.5. ReportAdmin

-   `sg_InsertReportAdmin`: 리포트 정보 추가
-   `sg_UpdateReportAdmin`: 리포트 정보 수정
-   `sg_SelectReportAdmin`: 리포트 정보 조회

## 3. Request/Response 파라미터

### 3.1. AppMapping

-   **AppMasterListRequest**: `pageNo`, `pageSize`, `orderBy`, `searchType`, `searchKeyword`
-   **AppMasterInsertRequest**: `appName`, `appId`, `appType`
-   **AppMasterUpdateRequest**: `aid`, `appName`, `appId`, `appType`
-   **AppToCompModifyRequest**: `aid`, `addList`, `removeList`
-   **AppMasterListResponse**: `aid`, `appName`, `appId`, `appType`, `compCount`
-   **AppToCompMappingListResponse**: `mappedList`, `unmappedList` (`AppToCompInfo`: `cid`, `compId`, `compType`, `nameKor`, `nameEng`, `mappingType`)

### 3.2. CompRoleMapping

-   **RoleToCompListRequest**: `pageNo`, `pageSize`, `orderBy`, `searchType`, `searchKeyword`
-   **RoleToCompModifyRequest**: `roleId`, `addedList`, `removedList`
-   **CompToRoleListRequest**: `pageNo`, `pageSize`, `orderBy`, `searchType`, `searchKeyword`
-   **CompToRoleModifyRequest**: `compId`, `compType`, `addedRole`, `removedRole`
-   **RoleToCompListResponse**: `roleId`, `roleName`, `componentCount`
-   **RoleToCompMappingListResponse**: `mappedList`, `unmappedList` (`CompInfo`: `compId`, `compType`, `compName`, `compNameKorean`)
-   **CompToRoleListResponse**: `compId`, `compType`, `compName`, `compNameKorean`, `roleCount`
-   **CompToRoleMappingListResponse**: `mappedList`, `unmappedList` (`RoleInfo`: `roleId`, `roleName`)

### 3.3. KpiKopAdmin

-   **KpiKopAdminSelectRequest**: `pageNo`, `pageSize`, `orderBy`, `searchType`, `searchKeyword`, `isActive`
-   **KpiKopAdminInsertRequest**: `compId`, `compType`, `uom`, `uomKorean`, `etlJobName`, `nameKor`, `nameEng`, `widgetObjectId`, `overviewWidgetId`
-   **KpiKopAdminUpdateRequest**: `cid`, `compId`, `compType`, `uom`, `uomKorean`, `etlJobName`, `nameKor`, `nameEng`, `widgetObjectId`, `overviewWidgetId`, `updatedBy`, `isActive`
-   **KpiKopAdminSelectResponse**: `cid`, `compId`, `nameENG`, `nameKOR`, `widgetObjectId`, `overviewWidgetId`, `uom`, `uomKorean`, `ETLJobName`, `isActive`

### 3.4. KpiKopFilterAdmin

-   **KpiKopFilterAdminSelectRequest**: `pageNo`, `pageSize`, `orderBy`, `searchType`, `searchKeyword`
-   **KpiKopFilterAdminInsertRequest**: `compId`, `filterType`, `filterName`, `fieldName`, `filterLabel`, `widgetObjectId`, `filterObjId`, `filterQlikId`, `filterSequence`, `pageName`
-   **KpiKopFilterAdminUpdateRequest**: `filterId`, `filterType`, `filterName`, `fieldName`, `filterLabel`, `widgetObjectId`, `filterObjId`, `filterQlikId`, `filterSequence`, `pageName`
-   **KpiKopFilterAdminSelectResponse**: `compId`, `compName`, `compNameKorean`, `compType`, `filterCount`, `filters` (`FilterInfo`: `filterId`, `filterName`, `filterLabel`, `filterType`, `filterSequence`, `widgetObjectId`, `filterObjId`, `filterVariableOptionId`, `pageName`, `filterQlikId`, `fieldName`)

### 3.5. ReportAdmin

-   **ReportAdminSelectRequest**: `pageNo`, `pageSize`, `orderBy`, `searchType`, `searchKeyword`, `isActive`
-   **ReportAdminInsertRequest**: `compId`, `compName`, `compNameKorean`, `sourceSys`, `sourceSysName`, `ownerDept`, `hyperLink`, `etlJobName`, `isActive`, `createdBy`
-   **ReportAdminUpdateRequest**: `compId`, `compName`, `compNameKorean`, `sourceSys`, `sourceSysName`, `ownerDept`, `hyperLink`, `etlJobName`, `updatedBy`, `isActive`
-   **ReportAdminSelectResponse**: `compId`, `compName`, `compNameKorean`, `sourceSys`, `sourceSysName`, `ownerDept`, `hyperLink`, `isActive`, `etlJobName`, `compSeq`

## 4. 기능 정의

### 4.1. App Mapping (앱-컴포넌트 매핑 관리)

-   **앱 마스터 관리**: 앱(Application)의 기본 정보를 등록, 수정, 조회합니다.
-   **앱-컴포넌트 매핑**: 특정 앱에 어떤 컴포넌트(KPI, KOP, Report 등)가 포함되는지 매핑 관계를 설정하고 조회합니다.

### 4.2. Comp-Role Mapping (컴포넌트-역할 매핑 관리)

-   **역할별 컴포넌트 관리**: 특정 역할(Role)이 접근할 수 있는 컴포넌트들을 매핑하고 관리합니다.
-   **컴포넌트별 역할 관리**: 특정 컴포넌트에 접근 권한을 가진 역할들을 매핑하고 관리합니다.

### 4.3. KPI/KOP Admin (KPI/KOP 관리)

-   KPI(Key Performance Indicator) 및 KOP(Key Operating Performance) 항목의 마스터 데이터를 관리합니다.
-   생성, 수정, 조회 기능을 통해 KPI/KOP의 속성(ID, 이름, 단위, ETL 작업명 등)을 관리합니다.

### 4.4. KPI/KOP Filter Admin (필터 관리)

-   KPI/KOP 대시보드에서 사용될 필터의 마스터 데이터를 관리합니다.
-   필터의 유형, 이름, 순서, Qlik Sense 오브젝트 ID 등을 설정하고 관리합니다.

### 4.5. Report Admin (리포트 관리)

-   리포트의 마스터 데이터를 관리합니다.
-   리포트의 ID, 이름, 원본 시스템, 소유 부서, 하이퍼링크 등의 정보를 관리합니다.
