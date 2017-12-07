package john.com.zhbj.bean;

import java.util.List;

/**
 * 按照json的格式编写的bean类
 * { ： jsonObject对象，相当于bean类
 * [ : json数组，集合，相当list集合
 * 单个字符 ： 相当于bean类中一个保存信息的变量
 * 2016-11-2  下午4:38:34
 */
public class NewsCenterInfo {

    //规则：变量名必须和json串中的字段名保持一致
    public List<NewsChildInfo> data;
    public List<String> extend;
    public String retcode;

    public class NewsChildInfo {
        public List<ChildInfo> children;
        public String id;
        public String title;
        public String type;

        public String url;
        public String url1;

        public String dayurl;
        public String excurl;
        public String weekurl;
    }

    public class ChildInfo {
        public String id;
        public String title;
        public String type;

        public String url;
    }

}
