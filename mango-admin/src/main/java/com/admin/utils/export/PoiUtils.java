package com.admin.utils.export;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * 生成excel文件(仅支持2007及以上版本)
 */
public class PoiUtils {

    private static Logger logger = LoggerFactory.getLogger(PoiUtils.class);

    public static File createExcelFile(Workbook workbook, String fileName) {
        OutputStream stream = null;
        File file = null;
        try {
            file = File.createTempFile(fileName, ".xlsx");
            stream = new FileOutputStream(file.getAbsoluteFile());
            workbook.write(stream);
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            IOUtils.closeQuietly(workbook);
            IOUtils.closeQuietly(stream);
        }
        return file;
    }
}
