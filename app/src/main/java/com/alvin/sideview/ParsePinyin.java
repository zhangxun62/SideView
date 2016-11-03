package com.alvin.sideview;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ParsePinyin {
    public static boolean isChinese(String str) {
        String regex = "[\\u4e00-\\u9fa5]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }

    public static String getPinyin(String chinese) {
        String zhongWenPinYin = "";
        char[] chars = chinese.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            String[] pinYin;
            try {
                pinYin = PinyinHelper.toHanyuPinyinStringArray(chars[i],
                        getDefaultOutputFormat());
                // 当转换不是中文字符时,返回null
                if (pinYin != null) {
                    zhongWenPinYin += pinYin[0];
                } else {
                    zhongWenPinYin += chars[i];
                }
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                e.printStackTrace();
            }

        }
        return zhongWenPinYin;
    }

//    /**
//     * 对数据根据26个字母顺序进行排序
//     *
//     * @param data
//     * @return
//     */
//    @SuppressLint("DefaultLocale")
//    public static List<CityModel> filledData(List<String> data) {
//	List<CityModel> mSortList = new ArrayList<CityModel>();
//	for (int i = 0; i < data.size(); i++) {
//	    String str = data.get(i);
//	    if (!"".equals(str) && null != str) {
//		CityModel sortModel = new CityModel();
//		sortModel.setName(str);
//		String pinyin = getPinyin(str);
//		sortModel.setPinyin(pinyin);
//		String sortString = pinyin.substring(0, 1).toUpperCase();
//		if (sortString.matches("[A-Z]")) {
//		    sortModel.setHeader(sortString.toUpperCase());
//		} else {
//		    sortModel.setHeader("#");
//		}
//		mSortList.add(sortModel);
//	    } else {
//		continue;
//	    }
//
//	}
//	return mSortList;
//    }

    public static String getHeader(String pinyin) {
        String sortString = pinyin.substring(0, 1).toUpperCase();
        if (sortString.matches("[A-Z]")) {
            return sortString.toUpperCase();
        } else {
            return "#";
        }
    }

    public static String decoding(String src) {

        if (src == null)
            return "";

        String result = src;

        result = result.replace(",", "").replace("，", "");

        result = result.replace("<", "").replace(">", "");

        result = result.replace("&", "");

        result = result.replace("&pc;", "").replace("&ul", "");

        result = result.replace("&shap;", "").replace("&ques", "");

        result = result.replace("。", "").replace(".", "");

        result = result.replace("-", "").replace("_", "");

        result = result.replace("《", "").replace("》", "");
        result = result.replace("（", "").replace("）", "");
        result = result.replace("(", "").replace(")", "");
        result = result.replace("+", "").replace("+", "");
        result = result.replace("、", "").replace("/", "");
        result = result.replace("\'", "").replace("、", "");
        result = result.replace("|", "").replace("|", "");

        return result;

    }

    /**
     * 输出格式
     *
     * @return
     */
    private static HanyuPinyinOutputFormat getDefaultOutputFormat() {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);// 大写
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);// 没有音调数字
        format.setVCharType(HanyuPinyinVCharType.WITH_U_AND_COLON);// u显示
        return format;
    }
}
