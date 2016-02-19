package commons;

import java.io.File;
import java.io.IOException;
import java.util.List;

import models.Daydata;

public class ImportExcel {
	public static void insertDataToDBFromExcel(String path){
		List<Daydata> list = null;
		try {
			list = ExcelHelper.exportListFromExcel(new File(path), 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (Daydata data : list) {
			data.save();
		}
	}
	public static List insertDataToTxtFromExcel(String path){
		List<String> list = null;
		try {
			list = ExcelToTxtHelper.exportListFromExcel(new File(path), 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
}
