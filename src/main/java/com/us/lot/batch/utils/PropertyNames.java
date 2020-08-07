package com.us.lot.batch.utils;

/**
 * @author chandra khadka
 * @since 2020-08-06
 */
public final class PropertyNames {
    public static final String BATCH_SIZE = "${batch.size:100000}";
    public static final String CSV_FILE_HEADER = "${batch.csv.fileHeader: name, department, salary}";

}
