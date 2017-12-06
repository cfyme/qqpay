package com.fshows.proxy.myutil;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.text.ParseException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 
 * @author wudy
 * 
 */
public class CallDetailDataUtils {
	
 


	public static final int TELCOM_OPERATOR_CODE_PROVINCE_TYPE = 100;
	public static final int MOBILE_OPERATOR_CODE_PROVINCE_TYPE = 200;
	public static final int UNION_OPERATOR_CODE_PROVINCE_TYPE = 300;
 
	public static int timeByChinaUnion(String str) {

		int timeCnt = 0;
		if (str == null || str.length() == 0) {
			return 0;
		} else {
			String regEx = "[\u4e00-\u9fa5]";
			String[] strArr = str.split(regEx);
			if (strArr.length == 1) {
				timeCnt = Integer.parseInt(strArr[0]);
			}
			if (strArr.length == 2) {
				timeCnt = Integer.parseInt(strArr[0]) * 60
						+ Integer.parseInt(strArr[1]);
			}
			if (strArr.length == 3) {
				timeCnt = Integer.parseInt(strArr[0]) * 60 * 60
						+ Integer.parseInt(strArr[1]) * 60
						+ Integer.parseInt(strArr[2]);
			}
			return timeCnt;
		}
	}

	public static Date dateTimeChinaUnion(String date,String  time){
		try {
			return	DateUtils.stringToDate(date+time, DateUtils.DATE_FORMAT_PATTERN);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * @Description: 获取中国联通的最近6个月时间，含当月
	 * @param  
	 * @return
	 */
	public static List<Map<String,String>> fetchMouthArray(){
		Calendar rightNow = Calendar.getInstance();
		List<Map<String,String>> mapList = new ArrayList<Map<String,String>>();
		int mouth = rightNow.get(Calendar.MONTH) +1;
		int year = rightNow.get(Calendar.YEAR);
		int tempDay = rightNow.get(Calendar.DAY_OF_MONTH);
		for(int index = 0;index < 6;index++){//  
			Map<String,String> tmep = new HashMap<String,String>();
			if(mouth == 0){//到了上一年了
				mouth = 12;
				year = year-1;
			}
			int days =  fetchDaysByMouth(year, mouth);
			tmep.put("beginDate", year+"-"+((mouth<10)?("0"+mouth):mouth)+"-01");
			if(index==0){//首月的情况需要对天做处理
			  if(tempDay<10){
			    tmep.put("endDate", year+"-"+((mouth<10)?("0"+mouth):mouth)+"-0"+tempDay);
			  }else{
			    tmep.put("endDate", year+"-"+((mouth<10)?("0"+mouth):mouth)+"-"+tempDay);
			  }
			}else{
			  tmep.put("endDate", year+"-"+((mouth<10)?("0"+mouth):mouth)+"-"+days);
			}
			mapList.add(tmep);
			mouth--;
		}
		return mapList;
	}
	
	/**
     * 
     * @Description: 获取中国联通的最近6个月时间，含当月
     * @param  
     * @return
     */
    public static String[] fetchYearMouthForChinaUnion(){//201506
        Calendar rightNow = Calendar.getInstance();
        String []strArr = new String[5];
        int mouth = rightNow.get(Calendar.MONTH);
        int year = rightNow.get(Calendar.YEAR);
        for(int index = 0;index < 5;index++){//  
            if(mouth == 0){//到了上一年了
                mouth = 12;
                year = year-1;
            }
            strArr[index] = (""+year)+((mouth<10)?("0"+mouth):mouth);
            mouth--;
        }
        return strArr;
    }
    
    /**
     * 
     * @Description: 获取中国联通的最近6个月时间，含当月
     * @param  
     * @return
     */
    public static String[] fetchYearMouthForChinaMobile(){//06-2015
        Calendar rightNow = Calendar.getInstance();
        String []strArr = new String[5];
        int mouth = rightNow.get(Calendar.MONTH);
        int year = rightNow.get(Calendar.YEAR);
        for(int index = 0;index < 5;index++){//  
            if(mouth == 0){//到了上一年了
                mouth = 12;
                year = year-1;
            }
            strArr[index] = ((mouth<10)?("0"+mouth):mouth)+("-"+year);
            mouth--;
        }
        return strArr;
    }
    
    /**
     * 
     * @Description: 获取中国联通的最近6个月时间，含当月
     * @param  
     * @return
     */
    public static String[] fetchYearMouth(){//2015-06
        Calendar rightNow = Calendar.getInstance();
        String []strArr = new String[5];
        int mouth = rightNow.get(Calendar.MONTH);
        int year = rightNow.get(Calendar.YEAR);
        for(int index = 0;index < 5;index++){//  
            if(mouth == 0){//到了上一年了
                mouth = 12;
                year = year-1;
            }
            strArr[index] = (year+"-")+((mouth<10)?("0"+mouth):mouth);
            mouth--;
        }
        return strArr;
    }
	public static void main(String[] args) {
	  System.out.println(CallDetailDataUtils.fetchServiceAgeForUnion(Pattern
                            .compile("[^0-9]").matcher("2015年04月01日")
                            .replaceAll("").trim()));
		System.out.println(fetchServiceAgeForUnion("20150401"));
		System.out.println("20150401151313".substring(0, 8));
        for(int index=0;index<CallDetailDataUtils.fetchYearMouthForChinaMobile().length;index++){
          System.out.println(CallDetailDataUtils.fetchYearMouthForChinaMobile()[index]);
        }
        List<NameValuePair> detailUrlParam = new ArrayList<NameValuePair>();
        detailUrlParam.add(new BasicNameValuePair("listtype", "1"));
        detailUrlParam.add(new BasicNameValuePair("listtype", "1"));
        detailUrlParam.add(new BasicNameValuePair("listtype", "1"));
        detailUrlParam.add(new BasicNameValuePair("listtype", "1"));
        for(NameValuePair nameValuePair:detailUrlParam){
          System.out.println(nameValuePair.getName()+"||"+nameValuePair.getValue());
        }
	}
	
	public static int fetchDaysByMouth(int year,int mouth){
		
		int days = 0;
		if(mouth == 1||mouth == 3||mouth == 5||mouth == 7||mouth == 8||mouth == 10||mouth == 12){
			days = 31;
		}
		if(mouth == 4||mouth == 6||mouth == 9||mouth == 11){
			days = 30;
		}
		if(mouth == 2){
			if((year%4==0 && year%100!=0)||year%400==0){
				days = 29;
			}else{
				days = 28;
			}
		}
		return days;
	}
	
	/**
	 * 
	 * @Description:根据具体的字符串获取具体的对应类型，开通状态 
	 * @param  
	 * @return
	 */
	public static byte fetchCurrentStateByStr(String str){
		byte tempState = 0;
		if(str!=null && !"".equals(str)){
			if(str.indexOf("开通")>-1||str.indexOf("开机")>-1||str.indexOf("正常")>-1){ 	
				tempState = 0;
			}else if(str.indexOf("欠费停机")>-1){
				tempState = 3;
			}else if(str.indexOf("停机")>-1){
				tempState = 1;
			}else if(str.indexOf("欠费")>-1){
				tempState = 2;
			}else{
				tempState = 4;
			}
		}
		return tempState;
	}
	/**
	 * 
	 * @Description: 获取通话类型
	 * @param  0：国内通话；1：国际通话；2：其他 默认国内
	 * @return
	 */
	public static byte fetchCurrentCallLevelByStr(String str){
		 byte tempLvel = 0;
		if(str!=null && !"".equals(str)){
			if(str.indexOf("国内通话")>-1){
				tempLvel = 0;
			}else if(str.indexOf("国际通话")>-1){
				tempLvel = 1;
			}else{
				tempLvel = 2;
			}
		}
		return tempLvel;
	}
	/**
	 * 
	 * @Description: 获取付费类型
	 * @param  0：国内通话；1：国际通话；2：其他 默认国内
	 * @return
	 */
	public static byte fetchCurrentFeeTypeByStr(String str){
		 byte tempLvel = 1;
		if(str!=null){
			if(str.indexOf("预")>-1){
				tempLvel = 0;
			}else {
				tempLvel = 1;
			}
		}
		return tempLvel;
	} 
	
	/**
	 * 
	 * @Description: 获取中国移动最近的6个月的月份记录
	 * @param  
	 * @return
	 */
	public static String[] fetchMouthArrayForChinaMobile(){
		String []tempArr = new String[6];
		Calendar rightNow = Calendar.getInstance();
		int mouth = rightNow.get(Calendar.MONTH) +1;
		int year = rightNow.get(Calendar.YEAR);
		for(int index = 0;index < 6;index++){
			if(mouth>9){
				tempArr[index] = mouth+"-"+year;
			}else{
				if(mouth==0){
					mouth= 12;
					tempArr[index] = mouth+"-"+year;
				}else{
					tempArr[index] = "0"+mouth+"-"+year;
				}
			}
			mouth--;
		}
		return tempArr;
	}
	
	public static int fetchServiceAgeForUnion(String str){
		int serviceAge = 0;
		String date = Pattern.compile("[^0-9]").matcher(str.substring(0, 8)).replaceAll("").trim();
		try {
			Date openDate = DateUtils.stringToDate(date, "yyyyMMdd");
			long  tempValue = new Date().getTime() - openDate.getTime();
			serviceAge = (int) Math.ceil(tempValue/1000/60/60/24/30.0);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return serviceAge;
	}
	
	/**
	 * 
	 * @Description:需要在联通生成详情文件最后段的内容 
	 * @param  
	 * @return
	 */
	public static String fetchUnioinConstantStr(){
	  
	  StringBuilder strBuilder = new StringBuilder();
	  
	  strBuilder.append("\r\n")
	  .append("call_date:calldate+calltime")
	  .append("\r\n")
	  .append("call_location:homeareaName")
	  .append("\r\n")
	  .append("call_fee:totalfee")
	  .append("\r\n")
	  .append("call_time:calllonghour")
	  .append("\r\n")
	  .append("paging_type:calltypeName")
	  .append("\r\n")
	  .append("call_status:landtype")
	  .append("\r\n")
	  .append("peer_phone_no:othernum");
	  
	  return strBuilder.toString();
	}
	
 
}
