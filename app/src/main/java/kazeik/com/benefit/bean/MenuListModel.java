package kazeik.com.benefit.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/12/3.
 */

public class MenuListModel {

    /**
     * item : 第一个
     * value : [{"subMenuName":"百度","subMenuUrl":"http://www.baidu.com/"},{"subMenuName":"谷歌","subMenuUrl":"http://www.google.com/"},{"subMenuName":"网易","subMenuUrl":"http://www.163.com/"}]
     */

    public String item;
    public List<ValueBean> value;

    public static class ValueBean {
        /**
         * subMenuName : 百度
         * subMenuUrl : http://www.baidu.com/
         */

        public String subMenuName;
        public String subMenuUrl;

        @Override
        public String toString() {
            return "ValueBean{" +
                    "subMenuName='" + subMenuName + '\'' +
                    ", subMenuUrl='" + subMenuUrl + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "MenuListModel{" +
                "item='" + item + '\'' +
                ", value=" + value +
                '}';
    }
}
