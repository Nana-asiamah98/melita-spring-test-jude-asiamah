package com.ml.ordermicroservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ml.ordermicroservice.utils.AppConstants;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
public class ResponseDTO<T> implements Serializable {
    private static final String DEFAULT_MESSAGE = AppConstants.RESPONSE_MESSAGE_RETRIEVED;
    private static final Integer DEFAULT_STATUS = 200;

    private String timestamp;
    private Integer status;
    private String message;
    private String requestId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long totalRecords = null;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer totalPages = null;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer page = null;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer pageSize = null;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data = null;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<T> dataList = null;

    public ResponseDTO() {
        this.timestamp = ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT);
    }

    public ResponseDTO(Integer status, String message) {
        this();
        this.status = status;
        this.message = message;
    }

    public ResponseDTO(Integer status, String message, T data) {
        this(status,message);
        this.data = data;
    }

    public ResponseDTO(Integer status, String message, T data, String requestId) {
        this(status,message);
        this.data = data;
        this.requestId = requestId;
    }

    public ResponseDTO( T data) {
        this(DEFAULT_STATUS,DEFAULT_MESSAGE);
        this.data = data;
    }

    public ResponseDTO( List<T> dataList, Long totalRecords) {
        this(DEFAULT_STATUS,DEFAULT_MESSAGE);
        this.dataList = dataList;
        this.totalRecords = totalRecords;
    }

    public ResponseDTO(Integer status, String message, List<T> dataList, Long totalRecords) {
        this(status,message);
        this.dataList = dataList;
        this.totalRecords = totalRecords;
    }

    public ResponseDTO(Integer status, String message, List<T> dataList, Long totalRecords, String requestId) {
        this(status, message);
        this.dataList = dataList;
        this.totalRecords = totalRecords;
        this.requestId = requestId;
    }

    public ResponseDTO(Integer status, String message, List<T> dataList, Integer page, Integer totalPages, Integer pageSize, Long totalRecords) {
        this(status, message, dataList, totalRecords);
        this.page = page;
        this.totalPages = totalPages;
        this.pageSize = pageSize;
    }

    public ResponseDTO(Page page) {
        this(DEFAULT_STATUS, DEFAULT_MESSAGE, page.getContent(), page.getNumber() + 1, page.getTotalPages(), page.getNumberOfElements(), page.getTotalElements());
    }

    public ResponseDTO(Integer status, String message, Page page) {
        this(status, message, page.getContent(), page.getNumber() + 1, page.getTotalPages(), page.getNumberOfElements(), page.getTotalElements());
    }

    public ResponseDTO(List data) {
        this(DEFAULT_STATUS, DEFAULT_MESSAGE, data, (long) data.size());
    }
}
