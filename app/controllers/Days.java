package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Daydata;
import net.sf.json.JSONArray;
import play.mvc.Controller;

public class Days extends Controller {
	public static void data() {
		List<Daydata> datas = Daydata.findDaydata();
		Map<String, Object> resultMap = listtoarray(datas);
		renderJSON(resultMap);
	}
	
	public static void getdatabycondition(String start,String end) throws ParseException{
		List<Daydata> datas = Daydata.findDaydataByCondition(start, end);
		
		List<Daydata> all = Daydata.findDaydataByDate(start, end);
		
		for(int i =0;i<all.size();i++){
			all.get(i).SPJ = 0;
			for(int j=0;j<datas.size();j++){
				if(all.get(i).id == datas.get(j).id){
					all.get(i).SPJ=datas.get(j).SPJ;
					
					break;
				}
				System.out.println(datas.get(j).SPJ);
			}
		}
		
		Map<String, Object> resultMap = listtoarray(all);
		renderJSON(resultMap);
	}
	/**
	 * List转换成json格式的数据
	 * @param datas
	 * @return
	 */
	public static Map<String, Object> listtoarray(List<Daydata> datas){
		double[] dataArray = new double[datas.size()];
		String[] dateArray = new String[datas.size()];
		for(int i = 0;i<dataArray.length;i++){
			dataArray[i]=datas.get(i).SPJ;
			dateArray[i]=datas.get(i).date;
		}
		Map<String, Object> resultMap = convertToMap(dataArray,dateArray);
		return resultMap;
	}
	/**
	 * List转换成Map
	 * 
	 * @param products
	 * @return
	 */
	public static Map<String, Object> convertToMap(double[] dataArray,String[] dateArray) {
		JSONArray daylist = JSONArray.fromObject(dataArray);
		JSONArray datelist = JSONArray.fromObject(dateArray);
		
		int flag = 0;
		if (daylist.size() > 0) {
			flag = 1;
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("days", daylist);
		resultMap.put("dates", datelist);
		resultMap.put("flag", flag);
		System.out.println(resultMap);
		return resultMap;
	}
}
