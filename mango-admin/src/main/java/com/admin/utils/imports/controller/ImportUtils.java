package com.admin.utils.imports.controller;

import com.admin.utils.exception.BaseException;
import com.admin.utils.http.HttpResult;
import com.admin.utils.http.HttpResultUtils;
import com.admin.utils.http.HttpStatus;
import com.admin.utils.imports.service.ImportServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/imports")
public class ImportUtils {

    @Autowired
    private ImportServiceImpl importService;

    /**
     * 导入数据
     * @param multipartFile multipartFile
     * @param type 类型
     * @return HttpResult
     */
    @PostMapping("/importExcel")
    public HttpResult importFiles(MultipartFile multipartFile, String type) {
        List<List<List<Object>>> lists = importFiles(multipartFile);
        if (lists.size() == 0) {
            throw new BaseException(HttpStatus.ERROR_SERVICE_VALIDATOR, "无可导数据，导入失败");
        }
        if ("user".equals(type)) {
            importService.importUsers(lists);
        } else if ("menu".equals(type)) {}
        return HttpResultUtils.success();
    }

    /**
     * 获取导入数据
     * @param multipartFile multipartFile
     * @return HttpResult
     */
    private List<List<List<Object>>> importFiles(MultipartFile multipartFile) {
        List<List<List<Object>>> listSheet = new ArrayList<>();
        try {
            // 输入流
            InputStream inputStream = multipartFile.getInputStream();
            // 获取上传文件
            String fileName = multipartFile.getOriginalFilename();
            if (fileName == null) {
                throw new BaseException(HttpStatus.ERROR_SERVICE_VALIDATOR, "未找到上传文件，导入失败");
            }
            String suffix = StringUtils.substring(".", fileName.length());
            Workbook workbook = null;
            if (".xls".equals(suffix)) {
                // 03版excel
                workbook = new HSSFWorkbook(inputStream);
            } else if (".xlsx".equals(suffix)) {
                // 07版excel
                workbook = new XSSFWorkbook(inputStream);
            } else {
                throw new BaseException(HttpStatus.ERROR_SERVICE_VALIDATOR, "上传文件格式错误，导入失败");
            }
            Sheet sheet = null;
            for (int i=0; i<workbook.getNumberOfSheets(); i++) {
                // 获取sheet页
                sheet = workbook.getSheetAt(i);
                if (sheet != null) {
                    // 获取表头
                    Row row1 = sheet.getRow(0);
                    if (row1 == null) {
                        continue;
                    }
                    List<List<Object>> listRow = new ArrayList<>();
                    for (int j=1; j<sheet.getLastRowNum(); j++) {
                        // 获取行,从第二行开始（第一行为表头）
                        Row row = sheet.getRow(j);
                        if (row == null) {
                            break;
                        }
                        List<Object> listCol = new ArrayList<>();
                        for (int k=0; k<row1.getLastCellNum(); k++) {
                            // 获取列数据
                            listCol.add(row.getCell(k).getStringCellValue());
                        }
                        listRow.add(listCol);
                    }
                    if (listRow.size() > 0) {
                        listSheet.add(listRow);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listSheet;
    }
}
