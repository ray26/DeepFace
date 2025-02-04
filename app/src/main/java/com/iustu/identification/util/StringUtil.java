package com.iustu.identification.util;

import android.util.Log;

import com.iustu.identification.entity.PersionInfo;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * created by sgh, 2019-04-11
 */
public class StringUtil {

    /**
     * 该方法使用在批量导入图片的时候，用来提取图片中的人员信息
     * @param pictures 需要批量导入的图片的路径
     * @return 所有人员信息,但该人员信息只包含photoPath、name、other、gender、identity
     */
    public static ArrayList<PersionInfo> clipPictures(ArrayList<String> pictures) {
        ArrayList<PersionInfo> result = new ArrayList<>();
        for(String picture : pictures) {
            PersionInfo persionInfo = new PersionInfo();
            persionInfo.photoPath = picture;
            if (picture.endsWith(".jpeg") || picture.endsWith(".JPEG"))
                picture = picture.substring(0, picture.length() - 5);
            else
                picture = picture.substring(0, picture.length() - 4);     // 去掉.jpg或者.png
            String[] s = picture.split("/");
            String info = s[s.length - 1];    // 获取图片中包含的信息
            s = info.split("_");
            String[] ss = new String[4];
            if (s.length < 4) {
                for (int i = 0; i < 4; i ++) {
                    if (i < s.length)
                        ss[i] = s[i];
                    else
                        ss[i] = "未填写";
                }
            }
            persionInfo.name = ss[0].trim();
            persionInfo.identity = ss[1].trim();
            persionInfo.gender = ss[2].trim();
            persionInfo.other = ss[3].trim();
            if (IdentityUtil.isValidatedIdentity(ss[1])) {
                IdentityUtil.getInformation(ss[1].trim());
                persionInfo.birthday = IdentityUtil.birthday;
                persionInfo.home = NativePlace.getNativePlace(Integer.valueOf(s[1].substring(0,6)));
            }
            result.add(persionInfo);
        }

        return result;
    }
}
