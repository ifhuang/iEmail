/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ecnu.sei.hif.algorithm;

import edu.ecnu.sei.hif.model.Email;
import edu.ecnu.sei.hif.model.EmailImpl;

/**
 *
 * @author If
 */
public class SpamFeatureExtracter {

    private String path;
    private int label;
    private static String[] features = {"http:", "www.", ".gif", ".jpg", "推荐", "体验", "调查", "购物", "亲爱", "客户", "您好", "感谢", "订单", "商品", "服务", "评价", "反馈", "订购", "有奖", "客服", "退订", "免费", "优惠", "合作", "发票", "实业", "缴款", "代开", "贵公司", "抵扣", "节省", "避税", "家电", "潜在", "票据", "特价", "采购", "付款", "机票", "赚钱", "特惠", "商务", "高效", "商机", "节能", "索取", "销售", "联系人", "转帐", "打扰", "验证", "钱", "科技", "AD", "广告", "点击这里", "click here", "unsubscribe", "取消订阅", ";", "\\(", "\\[", "!", "$", "#"};

    public SpamFeatureExtracter(String path) {
        this(path, 0);
    }

    public SpamFeatureExtracter(String path, int label) {
        this.path = path;
        this.label = label;
    }

    public String extract() {
        String temp;
        StringBuilder sb = new StringBuilder();
        Email email = new EmailImpl(path);

        sb.append(label).append("\t");

        if (email.getFrom() == null) {
            sb.append("1:0" + "\t");
            sb.append("2:0" + "\t");
            sb.append("3:0" + "\t");
        } else {
            temp = email.getFrom()[0].getAddress();
            sb.append("1:").append(temp.length()).append("\t");
            sb.append("2:").append(temp.indexOf("noreply") == -1 ? 0 : 1).append("\t");
            sb.append("3:").append(temp.indexOf("edu") == -1 ? 0 : 1).append("\t");
        }

        if ((temp = email.getSubject()) == null) {
            sb.append("4:0" + "\t");
        } else {
            sb.append("4:").append(temp.length()).append("\t");
        }

        String body;
        if ((body = email.getBodyPlain()) == null) {
            body = "";
        }
        body += temp;

        for (int j = 0; j < features.length; j++) {
            //System.out.println(features[j]);
            sb.append(j + 5).append(":").append(body.split(features[j]).length - 1).append("\t");
        }

        sb.append("\n");

        return sb.toString();
    }
/*
    public static void main(String[] args) {
        SpamFeatureExtracter sfe = new SpamFeatureExtracter("D:\\workspace\\netbeans\\Project\\data\\INBOX\\0",0);
        System.out.println(sfe.extract());
        sfe = new SpamFeatureExtracter("D:\\workspace\\netbeans\\Project\\data\\SPAM\\0",1);
        System.out.println(sfe.extract());
    }
    * */
}
