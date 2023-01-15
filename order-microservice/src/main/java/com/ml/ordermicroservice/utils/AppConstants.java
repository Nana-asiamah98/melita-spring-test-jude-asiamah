package com.ml.ordermicroservice.utils;

public interface AppConstants {

    /* Order Status */
    static final String ORDER_CREATED = "Created";
    static final String ORDER_CANCELLED = "Cancelled";
    static final String ORDER_PROCESSING = "Processing";
    static final String ORDER_PROCESSED = "Processed";
    static final String ORDER_COMPLETED = "Completed";

    /* PRODUCT STATUS*/
    static final String PRODUCT_CREATED = "Created";
    static final String PRODUCT_CANCELLED = "Cancelled";
    static final String RESPONSE_MESSAGE_RETRIEVED = "Data Retrieved Successfully";


    /* Pagination */
    static final String DEFAULT_PAGE_NUMBER = "0";
    static final String DEFAULT_PAGE_SIZE = "10";
    static final String DEFAULT_SORT_BY = "id";
    static final String DEFAULT_SORT_DIRECTION = "asc";
}
