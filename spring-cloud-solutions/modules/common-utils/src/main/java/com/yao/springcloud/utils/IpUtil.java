package com.yao.springcloud.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class IpUtil {
    private static Logger LOGGER = LoggerFactory.getLogger(IpUtil.class);
    private static String HOST_IP = "";
    private static String HOST_NAME = "";

    //静态代码块初始化hostIP
    static {
        HOST_IP = getRealIpInner();
        HOST_NAME = getHostNameInner();
    }

    public static String getRealIp() {
        return HOST_IP;
    }

    public static String getHostName() {
        return HOST_NAME;
    }

    /**
     * @return 获取ip信息
     */
    private static String getRealIpInner() {
        String localip = null;
        String netip = null;
        Enumeration<NetworkInterface> netInterfaces;
        try {
            netInterfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            return "";
        }
        InetAddress ip = null;
        boolean finded = false;
        while ((netInterfaces.hasMoreElements()) && (!finded)) {
            NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
            Enumeration<InetAddress> address = ni.getInetAddresses();
            while (address.hasMoreElements()) {
                ip = (InetAddress) address.nextElement();
                if ((!ip.isSiteLocalAddress()) && (!ip.isLoopbackAddress()) && (ip.getHostAddress().indexOf(":") == -1)) {
                    netip = ip.getHostAddress();
                    finded = true;
                    break;
                }
                if ((ip.isSiteLocalAddress()) && (!ip.isLoopbackAddress()) && (ip.getHostAddress().indexOf(":") == -1)) {
                    localip = ip.getHostAddress();
                }
            }
        }
        if ((netip != null) && (!"".equals(netip))) {
            return netip;
        }
        return localip;
    }


    private static String getHostNameInner() {
        InetAddress addr = null;
        String result = "";
        try {
            addr = InetAddress.getLocalHost();
            result = addr.getHostName().toString();
        } catch (UnknownHostException e) {
            throw new RuntimeException("getHostName error!", e);
        }

        return result;
    }
}
