package com.hbnu.client.util;

import com.hbnu.common.Account;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.regex.Pattern;

/**
 * @author luanhao
 * @date 2022年11月10日 0:31
 */
public class CheckInfo {
    private static boolean flag = false;

    public static boolean isFlag(String username) {
        if (username.equals("")) {
            return flag;
        }
        String pattern = "[0-9]{1,10}";
        if (Pattern.matches(pattern, username)) {
            return flag = true;
        } else {
            return flag;
        }
    }

    public static boolean isFlag(String username, String password) {
        if (username.equals("") || password.equals("")) {
            return flag;
        }
        String pattern = "[0-9]{1,10}";
        if (Pattern.matches(pattern, username)) {
            return flag = true;
        } else {
            return flag;
        }
    }

    public static Account CheckAccount(Account account) throws IOException {
        int id = account.getId();
        String name = account.getName();
        byte[] headPortrait = account.getHeadPortrait();
        if (name.equals("")){
            name = String.valueOf(id);
            account.setName(name);
        }
        if (headPortrait == null) {
            File file = new File("images/005.jpg");
            BufferedImage bi = ImageIO.read(file);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bi, "jpg", baos);
            headPortrait = baos.toByteArray();
            account.setHeadPortrait(headPortrait);
        }
        return account;
    }
}
