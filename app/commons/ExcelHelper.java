package commons;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.Daydata;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/**
 * 导入Excel表格
 * @author Jet
 *
 */
public abstract class ExcelHelper {
	/**
	 * Excel 2003
	 */
	private final static String XLS = "xls";
	/**
	 * Excel 2007
	 */
	private final static String XLSX = "xlsx";

	/**
	 * 由Excel文件的Sheet导出至List
	 * 
	 * @param file
	 * @param sheetNum
	 * @return
	 */
	public static List<Daydata> exportListFromExcel(File file, int sheetNum)
			throws IOException {
		return exportListFromExcel(new FileInputStream(file),
				FilenameUtils.getExtension(file.getName()), sheetNum);
	}

	/**
	 * 由Excel流的Sheet导出至List
	 * 
	 * @param is
	 * @param extensionName
	 * @param sheetNum
	 * @return
	 * @throws IOException
	 */
	public static List<Daydata> exportListFromExcel(InputStream is,
			String extensionName, int sheetNum) throws IOException {

		Workbook workbook = null;

		if (extensionName.toLowerCase().equals(XLS)) {
			workbook = new HSSFWorkbook(is);
		} else if (extensionName.toLowerCase().equals(XLSX)) {
			workbook = new XSSFWorkbook(is);
		}

		return exportListFromExcel(workbook, sheetNum);
	}

	/**
	 * 由指定的Sheet导出至List
	 * 
	 * @param workbook
	 * @param sheetNum
	 * @return
	 * @throws IOException
	 */
	private static List<Daydata> exportListFromExcel(Workbook workbook,
			int sheetNum) {

		Sheet sheet = workbook.getSheetAt(sheetNum);

		// 解析公式结果
		FormulaEvaluator evaluator = workbook.getCreationHelper()
				.createFormulaEvaluator();

		int minRowIx = sheet.getFirstRowNum();
		int maxRowIx = sheet.getLastRowNum();
		List<Daydata> datas = new ArrayList<Daydata>();
		for (int rowIx = minRowIx; rowIx <= maxRowIx; rowIx++) {
			Daydata data = new Daydata();
			Row row = sheet.getRow(rowIx);
			
			short minColIx = row.getFirstCellNum();
			short maxColIx = row.getLastCellNum();
			if(rowIx>minRowIx){
				
			for (short colIx = minColIx; colIx <= maxColIx; colIx++) {
				Cell cell = row.getCell(new Integer(colIx));
				CellValue cellValue = evaluator.evaluate(cell);
				if (cellValue == null) {
					continue;
				}
				// 经过公式解析，最后只存在Boolean、Numeric和String三种数据类型，此外就是Error了
				// 其余数据类型，根据官方文档，完全可以忽略http://poi.apache.org/spreadsheet/eval.html
				switch (cellValue.getCellType()) {
				case Cell.CELL_TYPE_BOOLEAN:
					break;
				case Cell.CELL_TYPE_NUMERIC:
					// 这里的日期类型会被转换为数字类型，需要判别后区分处理
					if (DateUtil.isCellDateFormatted(cell)) {
						Date d = cell.getDateCellValue();
						SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
						data.date = df.format(d);
					} else {
						switch (colIx) {
						case 1:
							data.SPJ = cell.getNumericCellValue();
							break;
						case 2:
							data.MA3 = cell.getNumericCellValue();
							break;
						case 3:
							data.MA5 = cell.getNumericCellValue();
							break;
						case 4:
							data.MA10 = cell.getNumericCellValue();
							break;
						case 5:
							data.MA21 = cell.getNumericCellValue();
							break;
						case 6:
							data.ABJBCURRENT = cell.getNumericCellValue();
							break;
						case 7:
							data.ABJBTOP = cell.getNumericCellValue();
							break;
						case 8:
							data.ABJBSALE = cell.getNumericCellValue();
							break;
						case 9:
							data.ABJBBUY = cell.getNumericCellValue();
							break;
						case 10:
							data.ABJBBOTTOM = cell.getNumericCellValue();
							break;
						case 11:
							data.LTSH = cell.getNumericCellValue();
							break;
						case 12:
							data.QSDD = cell.getNumericCellValue();
							break;
						default:
							break;
						}
					}
					break;
				case Cell.CELL_TYPE_STRING:
					break;
				case Cell.CELL_TYPE_FORMULA:
					break;
				case Cell.CELL_TYPE_BLANK:
					break;
				case Cell.CELL_TYPE_ERROR:
					break;
				default:
					break;
				}
				datas.add(data);
			}
		}
		}
		return datas;
	}
}
