package com.admin.utils.uuid;

import java.util.UUID;

public class UUIDUtils {

    public static String getUUID() {

        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }

    public static void main(String[] ars) {
        System.out.print(UUIDUtils.getUUID());
    }
}
