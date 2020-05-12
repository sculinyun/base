package damowang.base.util;

import net.dongliu.apk.parser.ApkFile;
import net.dongliu.apk.parser.bean.ApkMeta;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

/**
 * 描述:
 * apk提取信息工具类
 *
 * @author sculi
 * @create 2019-11-26 14:51
 */
public class ApkUtil {

    public static ApkMeta apkMeta(String path) {
        try {
            try (ApkFile apkFile = new ApkFile(new File(path))) {
                apkFile.setPreferredLocale(Locale.SIMPLIFIED_CHINESE);
                ApkMeta apkMeta = apkFile.getApkMeta();
                return apkMeta;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
