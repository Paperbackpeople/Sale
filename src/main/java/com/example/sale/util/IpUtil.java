package com.example.sale.util;
import jakarta.servlet.http.HttpServletRequest;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;
import org.lionsoul.ip2region.Util;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;
import org.apache.commons.io.FileUtils;

@Component
public class IpUtil {
    public static String getIpAddr(HttpServletRequest request){
        String ipAddress = null;
        try {
            ipAddress = request.getHeader("X-Forwarded-For");
            System.out.println("ipAddress1:"+ipAddress);
            if (ipAddress != null && ipAddress.length() != 0 && !"unknown".equalsIgnoreCase(ipAddress)) {
                // 多次反向代理后会有多个ip值，第一个ip才是真实ip
                if (ipAddress.indexOf(",") != -1) {
                    ipAddress = ipAddress.split(",")[0];
                }
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("HTTP_CLIENT_IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return ipAddress;
    }
    /**
     * 获取ip属地
     * @param ip
     * @return
     * @throws Exception
     */
    public static String getCityInfo(String ip) throws Exception {
        //获得文件流时，因为读取的文件是在打好jar文件里面，不能直接通过文件资源路径拿到文件，但是可以在jar包中拿到文件流
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("ip2region.db");
        Resource resource = resources[0];
        InputStream is = resource.getInputStream();
        File target = new File("ip2region.db");
        FileUtils.copyInputStreamToFile(is, target);
        is.close();
        if (StringUtils.isEmpty(String.valueOf(target))) {
            return null;
        }
        DbConfig config = new DbConfig();
        DbSearcher searcher = new DbSearcher(config, String.valueOf(target));
        //查询算法
        //B-tree, B树搜索（更快）
        int algorithm = DbSearcher.BTREE_ALGORITHM;
        try {
            //define the method
            Method method;
            method = searcher.getClass().getMethod("btreeSearch", String.class);
            DataBlock dataBlock;
            if (!Util.isIpAddress(ip)) {
                return "IP地址格式错误";
            }
            dataBlock = (DataBlock) method.invoke(searcher, ip);
            String ipInfo = dataBlock.getRegion();
            if (!StringUtils.isEmpty(ipInfo)) {
                ipInfo = ipInfo.replace("|0", "");
                ipInfo = ipInfo.replace("0|", "");
            }
            return ipInfo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String getIpPossession(String ip) throws Exception {
        String cityInfo = IpUtil.getCityInfo(ip);
        if (!StringUtils.isEmpty(cityInfo)) {
            cityInfo = cityInfo.replace("|", " ");
            String[] cityList = cityInfo.split(" ");
            if (cityList.length > 0) {
                // 国内的显示到具体的省
                if ("中国".equals(cityList[0])) {
                    if (cityList.length > 1) {
                        return cityList[1];
                    }
                }
                // 国外显示到国家
                return cityList[0];
            }
        }
        return "未知";
    }

}
