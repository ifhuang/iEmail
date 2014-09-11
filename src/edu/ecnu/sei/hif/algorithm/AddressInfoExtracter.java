/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ecnu.sei.hif.algorithm;

import edu.ecnu.sei.hif.model.Contact;
import edu.ecnu.sei.hif.model.Address;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author If
 */
public class AddressInfoExtracter {

    private final static String _qqDOMAIN = "qq.com";
    private final static String _139DOMAIN = "139.com";
    private final static String _ecnuDOMAIN = "ecnu.cn";
    private final static String _phonePATTERN = "^[1]([3][0-9]{1}|59|58|88|89)[0-9]{8}$";
    private final static String _telPATTERN = "[0-9]{7,8}";
    private final static String _numberPATTERN = "[0-9]*";
    private final static String _birthPATTERN1 = ".*[^0-9][0-9]{2}$";
    private final static String _birthPATTERN2 = ".*[^0-9][0-9]{4}$";
    private final static Set<String> _mailSET = new HashSet<>();
    private final static Map<String, String> _domainMAP = new HashMap<>();

    static {
        _mailSET.add("gmail");
        _mailSET.add("yahoo");
        _mailSET.add("msn");
        _mailSET.add("hotmail");
        _mailSET.add("live");
        _mailSET.add("qq");
        _mailSET.add("126");
        _mailSET.add("163");
        _mailSET.add("yeah");
        _mailSET.add("sina");

        _domainMAP.put("ac", "阿森松岛");
        _domainMAP.put("ad", "安道尔");
        _domainMAP.put("ae", "阿拉伯联合酋长国");
        _domainMAP.put("af", "阿富汗");
        _domainMAP.put("ag", "安提瓜和巴布达");
        _domainMAP.put("ai", "安圭拉");
        _domainMAP.put("al", "阿尔巴尼亚");
        _domainMAP.put("am", "亚美尼亚");
        _domainMAP.put("an", "荷属安地列斯群岛");
        _domainMAP.put("ao", "安哥拉");
        _domainMAP.put("aq", "南极洲");
        _domainMAP.put("ar", "阿根廷");
        _domainMAP.put("as", "美属萨摩亚");
        _domainMAP.put("at", "奥地利");
        _domainMAP.put("au", "澳大利亚");
        _domainMAP.put("aw", "阿鲁巴");
        _domainMAP.put("az", "阿塞拜疆");
        _domainMAP.put("ba", "波斯尼亚和黑塞哥维那");
        _domainMAP.put("bb", "巴巴多斯");
        _domainMAP.put("bd", "孟加拉国");
        _domainMAP.put("be", "比利时");
        _domainMAP.put("bf", "布基纳法索");
        _domainMAP.put("bg", "保加利亚");
        _domainMAP.put("bh", "巴林");
        _domainMAP.put("bi", "布隆迪");
        _domainMAP.put("bj", "贝宁");
        _domainMAP.put("bm", "百慕大");
        _domainMAP.put("bn", "文莱");
        _domainMAP.put("bo", "玻利维亚");
        _domainMAP.put("br", "巴西");
        _domainMAP.put("bs", "巴哈马");
        _domainMAP.put("bt", "不丹");
        _domainMAP.put("bv", "布维岛");
        _domainMAP.put("bw", "博茨瓦纳");
        _domainMAP.put("by", "白俄罗斯");
        _domainMAP.put("bz", "伯利兹");
        _domainMAP.put("ca", "加拿大");
        _domainMAP.put("cc", "可可群岛");
        _domainMAP.put("cd", "刚果民主共和国");
        _domainMAP.put("cf", "中非共和国");
        _domainMAP.put("cg", "刚果");
        _domainMAP.put("ch", "瑞士");
        _domainMAP.put("ci", "科特迪瓦");
        _domainMAP.put("ck", "库克群岛");
        _domainMAP.put("cl", "智利");
        _domainMAP.put("cm", "喀麦隆");
        _domainMAP.put("cn", "中国");
        _domainMAP.put("co", "哥伦比亚");
        _domainMAP.put("cr", "哥斯达黎加");
        _domainMAP.put("cu", "古巴");
        _domainMAP.put("cv", "佛得角");
        _domainMAP.put("cx", "圣诞岛");
        _domainMAP.put("cy", "塞浦路斯");
        _domainMAP.put("cz", "捷克共和国");
        _domainMAP.put("de", "德国");
        _domainMAP.put("dj", "吉布提");
        _domainMAP.put("dk", "丹麦");
        _domainMAP.put("dm", "多米尼克");
        _domainMAP.put("do", "多米尼加共和国");
        _domainMAP.put("dz", "阿尔及利亚");
        _domainMAP.put("ec", "厄瓜多尔");
        _domainMAP.put("ee", "爱沙尼亚");
        _domainMAP.put("eg", "埃及");
        _domainMAP.put("eh", "西撒哈拉");
        _domainMAP.put("er", "厄立特里亚");
        _domainMAP.put("es", "西班牙");
        _domainMAP.put("et", "埃塞俄比亚");
        _domainMAP.put("eu", "欧洲联盟");
        _domainMAP.put("fi", "芬兰");
        _domainMAP.put("fj", "斐济");
        _domainMAP.put("fk", "福克兰群岛");
        _domainMAP.put("fm", "密克罗尼西亚联邦");
        _domainMAP.put("fo", "法罗群岛");
        _domainMAP.put("fr", "法国");
        _domainMAP.put("ga", "加蓬");
        _domainMAP.put("gd", "格林纳达");
        _domainMAP.put("ge", "格鲁吉亚");
        _domainMAP.put("gf", "法属圭亚那");
        _domainMAP.put("gg", "格恩西岛");
        _domainMAP.put("gh", "加纳");
        _domainMAP.put("gi", "直布罗陀");
        _domainMAP.put("gl", "格陵兰");
        _domainMAP.put("gm", "冈比亚");
        _domainMAP.put("gn", "几内亚");
        _domainMAP.put("gp", "瓜德罗普");
        _domainMAP.put("gq", "赤道几内亚");
        _domainMAP.put("gr", "希腊");
        _domainMAP.put("gs", "南喬治亞島與南桑威奇群島|南乔治亚岛和南桑德韦奇岛");
        _domainMAP.put("gt", "危地马拉");
        _domainMAP.put("gu", "关岛");
        _domainMAP.put("gw", "几内亚比绍");
        _domainMAP.put("gy", "圭亚那");
        _domainMAP.put("hk", "香港");
        _domainMAP.put("hm", "赫德和麦克唐纳群岛");
        _domainMAP.put("hn", "洪都拉斯");
        _domainMAP.put("hr", "克罗地亚");
        _domainMAP.put("ht", "海地");
        _domainMAP.put("hu", "匈牙利");
        _domainMAP.put("id", "印度尼西亚");
        _domainMAP.put("ie", "爱尔兰共和国|爱尔兰");
        _domainMAP.put("il", "以色列");
        _domainMAP.put("im", "马恩岛");
        _domainMAP.put("in", "印度");
        _domainMAP.put("io", "英属印度洋地区");
        _domainMAP.put("iq", "伊拉克");
        _domainMAP.put("ir", "伊朗");
        _domainMAP.put("is", "冰岛");
        _domainMAP.put("it", "意大利");
        _domainMAP.put("je", "泽西岛");
        _domainMAP.put("jm", "牙买加");
        _domainMAP.put("jo", "约旦");
        _domainMAP.put("jp", "日本");
        _domainMAP.put("ke", "肯尼亚");
        _domainMAP.put("kg", "吉尔吉斯斯坦");
        _domainMAP.put("kh", "柬埔寨");
        _domainMAP.put("ki", "基里巴斯");
        _domainMAP.put("km", "科摩罗");
        _domainMAP.put("kn", "圣基茨和尼维斯");
        _domainMAP.put("kp", "朝鲜");
        _domainMAP.put("kr", "韩国");
        _domainMAP.put("kw", "科威特");
        _domainMAP.put("ky", "开曼群岛");
        _domainMAP.put("kz", "哈萨克斯坦");
        _domainMAP.put("la", "老挝");
        _domainMAP.put("lb", "黎巴嫩");
        _domainMAP.put("lc", "圣卢西亚");
        _domainMAP.put("li", "列支敦士登");
        _domainMAP.put("lk", "斯里兰卡");
        _domainMAP.put("lr", "利比里亚");
        _domainMAP.put("ls", "莱索托");
        _domainMAP.put("lt", "立陶宛");
        _domainMAP.put("lu", "卢森堡");
        _domainMAP.put("lv", "拉脱维亚");
        _domainMAP.put("ly", "利比亚");
        _domainMAP.put("ma", "摩洛哥");
        _domainMAP.put("mc", "摩纳哥");
        _domainMAP.put("md", "摩尔多瓦");
        _domainMAP.put("me", "黑山");
        _domainMAP.put("mg", "马达加斯加");
        _domainMAP.put("mh", "马绍尔群岛");
        _domainMAP.put("mk", "马其顿");
        _domainMAP.put("ml", "马里");
        _domainMAP.put("mm", "缅甸");
        _domainMAP.put("mn", "蒙古");
        _domainMAP.put("mo", "澳门");
        _domainMAP.put("mp", "北马里亚纳群岛");
        _domainMAP.put("mq", "马提尼克岛");
        _domainMAP.put("mr", "毛里塔尼亚");
        _domainMAP.put("ms", "蒙特塞拉特岛");
        _domainMAP.put("mt", "马耳他");
        _domainMAP.put("mu", "毛里求斯");
        _domainMAP.put("mv", "马尔代夫");
        _domainMAP.put("mw", "马拉维");
        _domainMAP.put("mx", "墨西哥");
        _domainMAP.put("my", "马来西亚");
        _domainMAP.put("mz", "莫桑比克");
        _domainMAP.put("na", "纳米比亚");
        _domainMAP.put("nc", "新喀里多尼亚");
        _domainMAP.put("ne", "尼日尔");
        _domainMAP.put("nf", "诺福克岛");
        _domainMAP.put("ng", "尼日利亚");
        _domainMAP.put("ni", "尼加拉瓜");
        _domainMAP.put("nl", "荷兰");
        _domainMAP.put("no", "挪威");
        _domainMAP.put("np", "尼泊尔");
        _domainMAP.put("nr", "瑙鲁");
        _domainMAP.put("nu", "纽埃岛");
        _domainMAP.put("nz", "新西兰");
        _domainMAP.put("om", "阿曼");
        _domainMAP.put("pa", "巴拿马");
        _domainMAP.put("pe", "秘鲁");
        _domainMAP.put("pf", "法属波利尼西亚");
        _domainMAP.put("pg", "巴布亚新几内亚");
        _domainMAP.put("ph", "菲律宾");
        _domainMAP.put("pk", "巴基斯坦");
        _domainMAP.put("pl", "波兰");
        _domainMAP.put("pm", "圣皮埃尔岛及密客隆岛");
        _domainMAP.put("pn", "皮特凯恩群岛");
        _domainMAP.put("pr", "波多黎各");
        _domainMAP.put("ps", "巴勒斯坦");
        _domainMAP.put("pt", "葡萄牙");
        _domainMAP.put("pw", "帕劳");
        _domainMAP.put("py", "巴拉圭");
        _domainMAP.put("qa", "卡塔尔");
        _domainMAP.put("re", "留尼汪");
        _domainMAP.put("ro", "罗马尼亚");
        _domainMAP.put("ru", "俄罗斯");
        _domainMAP.put("rw", "卢旺达");
        _domainMAP.put("sa", "沙特阿拉伯");
        _domainMAP.put("sb", "所罗门群岛");
        _domainMAP.put("sc", "塞舌尔");
        _domainMAP.put("sd", "苏丹共和国|苏丹");
        _domainMAP.put("se", "瑞典");
        _domainMAP.put("sg", "新加坡");
        _domainMAP.put("sh", "圣赫勒拿岛");
        _domainMAP.put("si", "斯洛文尼亚");
        _domainMAP.put("sj", "斯瓦尔巴岛和扬马延岛");
        _domainMAP.put("sk", "斯洛伐克");
        _domainMAP.put("sl", "塞拉利昂");
        _domainMAP.put("sm", "圣马力诺");
        _domainMAP.put("sn", "塞内加尔");
        _domainMAP.put("so", "索马里");
        _domainMAP.put("sr", "苏里南");
        _domainMAP.put("ss", "南苏丹");
        _domainMAP.put("st", "圣多美和普林西比");
        _domainMAP.put("sv", "萨尔瓦多");
        _domainMAP.put("sy", "叙利亚");
        _domainMAP.put("sz", "斯威士兰");
        _domainMAP.put("tc", "特克斯和凯科斯群岛");
        _domainMAP.put("td", "乍得");
        _domainMAP.put("tf", "法属南部领土");
        _domainMAP.put("tg", "多哥");
        _domainMAP.put("th", "泰国");
        _domainMAP.put("tj", "塔吉克斯坦");
        _domainMAP.put("tk", "托克劳");
        _domainMAP.put("tl", "东帝汶");
        _domainMAP.put("tm", "土库曼斯坦");
        _domainMAP.put("tn", "突尼斯");
        _domainMAP.put("to", "汤加");
        _domainMAP.put("tp", "东帝汶");
        _domainMAP.put("tr", "土耳其");
        _domainMAP.put("tt", "特立尼达和多巴哥");
        _domainMAP.put("tv", "图瓦卢");
        _domainMAP.put("tw", "臺灣");
        _domainMAP.put("tz", "坦桑尼亚");
        _domainMAP.put("ua", "乌克兰");
        _domainMAP.put("ug", "乌干达");
        _domainMAP.put("uk", "英国");
        _domainMAP.put("um", "美国本土外小岛屿");
        _domainMAP.put("us", "美国");
        _domainMAP.put("uy", "乌拉圭");
        _domainMAP.put("uz", "乌兹别克斯坦");
        _domainMAP.put("va", "梵蒂冈");
        _domainMAP.put("vc", "圣文森特和格林纳丁斯");
        _domainMAP.put("ve", "委内瑞拉");
        _domainMAP.put("vg", "英属维尔京群岛");
        _domainMAP.put("vi", "美属维尔京群岛");
        _domainMAP.put("vn", "越南");
        _domainMAP.put("vu", "瓦努阿图");
        _domainMAP.put("wf", "瓦利斯和富图纳群岛");
        _domainMAP.put("ws", "萨摩亚");
        _domainMAP.put("ye", "也门");
        _domainMAP.put("yt", "马约特岛");
        _domainMAP.put("yu", "塞尔维亚和黑山");
        _domainMAP.put("yr", "耶纽");
        _domainMAP.put("za", "南非");
        _domainMAP.put("zm", "赞比亚");
        _domainMAP.put("zw", "津巴布韦");
    }
    private Address addr;

    public AddressInfoExtracter(Address addr) {
        this.addr = addr;
    }

    public Contact extract() {

        Contact contact = new Contact();
        String personal = addr.getPersonal();
        String address = addr.getAddress();

        // personal
        if (personal != null && !personal.equals("")) {
            contact.addPersonal(personal);
        }

        // address
        if (address != null && address.contains("@")) {
            // part[0] prefix part[1] postfix
            String[] part = address.split("@");
            boolean prefixneedprocess = true;
            if (part[1].equals(_qqDOMAIN)) {
                if (isNumeric(part[0])) {
                    contact.addId("QQ:" + part[0]);
                } else {
                    contact.addPersonal("qq_name:" + part[0]);
                }
                prefixneedprocess = false;
            } else if (part[1].equals(_139DOMAIN)) {
                if (isNumeric(part[0])) {
                    contact.addPhone("mobile:" + part[0]);
                } else {
                    contact.addPersonal("139_name:" + part[0]);
                }
                prefixneedprocess = false;
            } else if (part[1].equals(_ecnuDOMAIN)) {
                contact.addRealaddress("country:中国");
                contact.addCompany("university:" + "ecnu");
                if (isNumeric(part[0])) {
                    contact.addId("ecnu_id:" + part[0]);
                } else {
                    contact.addPersonal("ecnu_name:" + part[0]);
                }
                prefixneedprocess = false;
            } else {
                // part[1] spilt by .
                String[] postfix = part[1].split("\\.");
                for (int i = postfix.length - 1; i >= 0; i--) {
                    String country = checkCountry(postfix[i]);
                    if (country != null) {
                        contact.addRealaddress("country:" + country);
                        continue;
                    }
                    if (postfix[i].equals("edu") || (postfix[i].equals("ac"))) {
                        if (i - 1 >= 0) {
                            contact.addCompany("university:" + postfix[i - 1]);
                            if (isNumeric(part[0])) {
                                contact.addId(postfix[i - 1] + "_id:" + part[0]);
                            } else {
                                contact.addPersonal(postfix[i - 1] + "_name:" + part[0]);
                            }
                            prefixneedprocess = false;
                            if (i - 2 >= 0) {
                                contact.addCompany("college:" + postfix[i - 2]);
                                if (i - 3 >= 0) {
                                    contact.addCompany("laboratory:" + postfix[i - 3]);
                                }
                            }
                        }
                        continue;
                    } else if (postfix[i].equals("com") || (postfix[i].equals("org"))) {
                        if (i - 1 >= 0) {
                            if (!_mailSET.contains(postfix[i - 1])) {
                                contact.addCompany("company:" + postfix[i - 1]);
                                if (isNumeric(part[0])) {
                                    contact.addId(postfix[i - 1] + "_id:" + part[0]);
                                } else {
                                    contact.addPersonal(postfix[i - 1] + "_name:" + part[0]);
                                }
                                prefixneedprocess = false;
                                if (i - 2 >= 0) {
                                    contact.addCompany("department:" + postfix[i - 2]);
                                    if (i - 3 >= 0) {
                                        contact.addCompany("team:" + postfix[i - 3]);
                                    }
                                }
                            }
                        }
                        continue;
                    }
                }
            }
            // prefix need process
            if (prefixneedprocess) {
                String birth = checkBirth(part[0]);
                if (birth != null) {
                    contact.addBirthday(birth);
                }
                String phone = checkPhone(part[0]);
                if (phone != null) {
                    contact.addPhone("moble:" + phone);
                }
                String tel = checkTel(part[0]);
                if (tel != null) {
                    contact.addPhone("tel:" + tel);
                }
            }
        }

        return contact;
    }

    private static String checkCountry(String country) {
        if (_domainMAP.containsKey(country)) {
            return _domainMAP.get(country);
        } else {
            return null;
        }
    }

    private static String checkPhone(String phone) {
        Pattern pattern = Pattern.compile(_phonePATTERN);
        Matcher isPhone = pattern.matcher(phone);
        if (!isPhone.matches()) {
            return null;
        }
        return phone;
    }

    private static String checkTel(String tel) {
        Pattern pattern = Pattern.compile(_telPATTERN);
        Matcher isTel = pattern.matcher(tel);
        if (!isTel.matches()) {
            return null;
        }
        return tel;
    }

    private static String checkBirth(String birth) {
        Pattern pattern1 = Pattern.compile(_birthPATTERN1);
        Matcher isBirth1 = pattern1.matcher(birth);
        if (!isBirth1.matches()) {
            Pattern pattern2 = Pattern.compile(_birthPATTERN2);
            Matcher isBirth2 = pattern2.matcher(birth);
            if (!isBirth2.matches()) {
                return null;
            } else {
                return birth.substring(birth.length() - 4);
            }
        } else {
            return birth.substring(birth.length() - 2);
        }
    }

    private static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile(_numberPATTERN);
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /*
     public static void main(String[] args) {         
     try {
     BufferedReader br = new BufferedReader(new FileReader("D:\\workspace\\netbeans\\Project\\data\\contact.txt"));
     String line = br.readLine();
     while (line != null) {
     String[] part = line.split("\t");
     Address addr = new Address();
     addr.setPersonal(part[1]);
     addr.setAddress(part[0]);
     Contact pre = new Contact();
     pre.addPersonal(part[1]);
     pre.addAddress(part[0]);
     AddressInfoExtracter aie = new AddressInfoExtracter(addr);
     Contact c = aie.extract();
     Contact post = pre.merge(c);
     System.out.println(post.toString());
     line = br.readLine();
     }
     } catch (Exception e) {
     e.printStackTrace();
     }

     }
     */
}
