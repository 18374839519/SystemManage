package com.admin.service.backup.impl;

import com.admin.service.backup.MysqlBackupService;
import com.admin.utils.MysqlBackupRestoreUtils;
import org.springframework.stereotype.Service;

@Service
public class MysqlBackupServiceImpl implements MysqlBackupService {
    @Override
    public boolean backup(String host, String userName, String password, String backupFolderPath,
                          String fileName, String databaseName) throws Exception {
        return MysqlBackupRestoreUtils.backup(host, userName, password, backupFolderPath, fileName, databaseName);
    }

    @Override
    public boolean restore(String restoreFilePath, String host, String userName, String password,
                           String databaseName) throws Exception {
        return MysqlBackupRestoreUtils.restore(restoreFilePath, host, userName, password, databaseName);
    }
}
