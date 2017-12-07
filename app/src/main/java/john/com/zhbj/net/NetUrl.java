package john.com.zhbj.net;
/**
 * 请求服务器的路径
 *
 *2016-11-2  下午3:42:33
 */
public class NetUrl {

	private static String SERVER_URL = "http://10.0.2.2:8080/zhbj";
	//新闻中心
	public static final String NEWS_CENTER_URL=SERVER_URL+"/categories.json";
	//组图
	public static final String PHOTO_URL=SERVER_URL+"/photos/photos_1.json";
	
}
