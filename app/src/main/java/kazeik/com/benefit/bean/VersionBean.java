package kazeik.com.benefit.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/1.
 */

public class VersionBean implements Serializable{

    /**
     * capVerson : 2.0.4
     * capFileName : cap-v2.0.4-201609300923.apk
     * capUpdateContent : 更新内容(2016年09月30日) 1、修正银联通道提示输入验证码的问题
     */

    public String capVerson;
    public String capFileName;
    public String capUpdateContent;
    public int versionCode;
}
