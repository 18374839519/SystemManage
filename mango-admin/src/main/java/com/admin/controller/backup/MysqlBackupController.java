package com.admin.controller.backup;

import com.admin.service.backup.impl.MysqlBackupServiceImpl;
import com.admin.utils.BackupConstants;
import com.admin.utils.exception.BaseException;
import com.admin.utils.export.FileUtils;
import com.admin.utils.http.HttpResult;
import com.admin.utils.http.HttpResultUtils;
import com.admin.utils.http.HttpStatus;
import com.admin.config.BackupDataSourceProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 系统数据备份还原
 * 采用MYSQL备份还原命令
 */
@RestController
@RequestMapping("/backup")
public class MysqlBackupController {

    private static Logger logger = LoggerFactory.getLogger(MysqlBackupController.class);

    @Autowired
    private MysqlBackupServiceImpl mysqlBackupService;

    @Autowired
    BackupDataSourceProperties properties;

    /**
     * 备份
     * @return
     */
    @GetMapping("/backupData")
    public HttpResult backupDta() {
        String backupFolderName = BackupConstants.DEFAULT_BACKUP_NAME + "_"
                + (new SimpleDateFormat(BackupConstants.DATE_FORMAT)).format(new Date());
        return backup(backupFolderName);
    }

    /**
     * 还原
     * @param name
     * @return
     */
    @GetMapping("/restoreData")
    public HttpResult restore(String name) {
        String host = properties.getHost();
        String userName = properties.getUserName();
        String password = properties.getPassword();
        String database = properties.getDatabase();
        String restoreFilePath = BackupConstants.RESTORE_FOLDER + name;
        try {
            boolean result = mysqlBackupService.restore(restoreFilePath, host, userName, password, database);
            if (!result) {
                throw new BaseException(HttpStatus.ERROR_SERVICE_VALIDATOR, "数据还原失败");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new BaseException(HttpStatus.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return HttpResultUtils.success();
    }

    /**
     * 备份查找
     * @return
     */
    @GetMapping("/findRecords")
    public HttpResult findBackupRecords() {
        if(!new File(BackupConstants.DEFAULT_RESTORE_FILE).exists()) {
            // 初始默认备份文件
            backup(BackupConstants.DEFAULT_BACKUP_NAME);
        }
        List<Map<String, String>> backupRecords = new ArrayList<>();
        File restoreFolderFile = new File(BackupConstants.RESTORE_FOLDER);
        if(restoreFolderFile.exists()) {
            for(File file:restoreFolderFile.listFiles()) {
                Map<String, String> backup = new HashMap<>();
                backup.put("name", file.getName());
                backup.put("title", file.getName());
                if(BackupConstants.DEFAULT_BACKUP_NAME.equalsIgnoreCase(file.getName())) {
                    backup.put("title", "系统默认备份");
                }
                backupRecords.add(backup);
            }
        }
        // 排序，默认备份最前，然后按时间戳排序，新备份在前面
        backupRecords.sort((o1, o2) -> BackupConstants.DEFAULT_BACKUP_NAME.equalsIgnoreCase(o1.get("name")) ? -1
                : BackupConstants.DEFAULT_BACKUP_NAME.equalsIgnoreCase(o2.get("name")) ? 1 : o2.get("name").compareTo(o1.get("name")));
        return HttpResultUtils.success(backupRecords);
    }

    /**
     * 备份删除
     * @param name
     * @return
     */
    @PostMapping("/delete")
    public HttpResult deleteBackupRecord(String name) {
        if(BackupConstants.DEFAULT_BACKUP_NAME.equals(name)) {
            throw new BaseException(HttpStatus.ERROR_SERVICE_VALIDATOR, "系统默认备份无法删除");
        }
        String restoreFilePath = BackupConstants.BACKUP_FOLDER + name;
        try {
            FileUtils.deleteFile(new File(restoreFilePath));
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new BaseException(HttpStatus.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return HttpResultUtils.success();
    }

    private HttpResult backup(String backupFodlerName) {
        String host = properties.getHost();
        String userName = properties.getUserName();
        String password = properties.getPassword();
        String database = properties.getDatabase();
        String backupFolderPath = BackupConstants.BACKUP_FOLDER + backupFodlerName + File.separator;
        String fileName = BackupConstants.BACKUP_FILE_NAME;
        try {
            boolean result = mysqlBackupService.backup(host, userName, password, backupFolderPath, fileName, database);
            if(!result) {
                throw new BaseException(HttpStatus.ERROR_SERVICE_VALIDATOR, "数据备份失败");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new BaseException(HttpStatus.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return HttpResultUtils.success();
    }
}
