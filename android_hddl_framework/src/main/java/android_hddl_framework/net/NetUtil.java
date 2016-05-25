package android_hddl_framework.net;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
/**
 * 获取本机IP
 * @author Administrator
 *
 */
public class NetUtil {
	/**
	 * 获取wifi Ip
	 * @param mContext
	 * @return
	 */
	private static String getLocalIpAddress(Context mContext) {
        WifiManager wifiManager = (WifiManager) mContext.getSystemService(mContext.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        // 获取32位整型IP地址
        int ipAddress = wifiInfo.getIpAddress();
        
        //返回整型地址转换成“*.*.*.*”地址
        return String.format("%d.%d.%d.%d",
                (ipAddress & 0xff), (ipAddress >> 8 & 0xff),
                (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff));
    }
	/**
	 * 获取3G Ip
	 * @param mcContext
	 * @return
	 */
	private static String getIpAddress(Context mContext) {
		try {
		            for (Enumeration<NetworkInterface> en = NetworkInterface
		                    .getNetworkInterfaces(); en.hasMoreElements();) {
		                NetworkInterface intf = en.nextElement();
		                for (Enumeration<InetAddress> enumIpAddr = intf
		                        .getInetAddresses(); enumIpAddr.hasMoreElements();) {
		                    InetAddress inetAddress = enumIpAddr.nextElement();
		                    if (!inetAddress.isLoopbackAddress()
		                            && inetAddress instanceof Inet4Address) {
		                        // if (!inetAddress.isLoopbackAddress() && inetAddress
		                        // instanceof Inet6Address) {
		                        return inetAddress.getHostAddress().toString();
		                    }
		                }
		            }
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		        return null;
		    }
	/**
	 * 判断是否联网
	 * 
	 * @param context
	 * @return
	 */
	private static boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}
	
	/**
	 * 判断wifi网络连接情况
	 * 
	 * @param context
	 * @return
	 */
	private static boolean isWifiConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mWiFiNetworkInfo = mConnectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if (mWiFiNetworkInfo != null) {
				return mWiFiNetworkInfo.isAvailable()
						&& mWiFiNetworkInfo.isConnected();
			}
		}
		return false;
	}
	
	
	/**
	 * 判断3G网络是否可用
	 * 
	 * @param context
	 * @return
	 */
	private static boolean isMobileConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mMobileNetworkInfo = mConnectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			if (mMobileNetworkInfo != null) {
				return mMobileNetworkInfo.isConnected()
						&& mMobileNetworkInfo.isAvailable();
			}
		}
		return false;
	}
	/**
	 * 获取本机Ip
	 * @param mContext
	 * @return
	 */
	public static String getIp(Context mContext){
		String str="";
		if(isNetworkConnected(mContext)){
			if(isWifiConnected(mContext)){
				return getLocalIpAddress(mContext);
			}
			if(isMobileConnected(mContext)){
				return getIpAddress(mContext);
			}
		}else{
			return str;
		}
		return str;
	}
}
