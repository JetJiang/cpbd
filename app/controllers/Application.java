package controllers;

import play.*;
import play.mvc.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

import commons.ExcelHelper;
import commons.FileOperation;
import commons.ImportExcel;
import models.*;

public class Application extends Controller {

	public static void index() {
		render();
	}
	public static void importData(){
		ImportExcel.insertDataToDBFromExcel("E:/data.xlsx");
		index();
	}
	/**
	 * 将Excel中的数据按照规定格式写入到txt文件中
	 */
	public static void excel2txt(){
		List<String> list= ImportExcel.insertDataToTxtFromExcel("D:/k9.xlsx");
		StringBuffer sb = new StringBuffer();
		for (String string : list) {
			sb.append(string+"\r\n");
		}
		try {
			File f = new File("D:/k9.txt");
			FileOperation.createFile(f);
			FileOperation.writeTxtFile(sb.toString().substring(0, sb.toString().length()-1), f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		index();
	}
	/**
	 * excel导出列表并去重
	 */
	public static void excel2list(){
		List<String> list= ImportExcel.insertDataToTxtFromExcel("D:/filter.xls");
		List<String> templist = new ArrayList<String>();
		for (String string : list) {
			if(!templist.contains(string)){
				templist.add(string);
			}else{
				templist.remove(templist.indexOf(string));
			}
		}
		for (String s : templist) {
			System.out.println(s);
		}
	}
	/**
	 * excel直接导入到mysql
	 */
	public static void excel2mysql(){
		List<String> list= ImportExcel.insertDataToTxtFromExcel("D:/mysql.xlsx");
		for (String string : list) {
			VehicleBase vb = new VehicleBase();
			vb.plateNumber=string;
			vb.vehilceType="K9";
			if(VehicleBase.isExist(string)){
				System.out.println(string+"--设备号已存在--ID为"+VehicleBase.getIdByPlateNumber(string));
			}else{
				vb.save();
				System.out.println(string+" 添加成功");
			}
		}
	}

}