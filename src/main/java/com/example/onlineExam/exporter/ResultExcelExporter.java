package com.example.onlineExam.exporter;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.example.onlineExam.entities.PaperDTO;
 
public class ResultExcelExporter {
	private  XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private List<PaperDTO> dtos;
	public ResultExcelExporter(List<PaperDTO> dtos) {
		this.dtos = dtos;
		workbook = new XSSFWorkbook();
	}
	
	private void writeHeaderLine() {
		sheet = workbook.createSheet("Kết quả");
		Row row = sheet.createRow(0);
		
		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);
		
		createCell(row, 0, "Môn học", style);
		createCell(row, 1, "Đề", style);
		createCell(row, 2, "Điểm tối đa", style);
		createCell(row, 3, "Điểm thi", style);
		createCell(row, 4, "Đánh giá", style);
	}
	
	private void createCell(Row row , int columnCount,Object value,CellStyle style) {
		sheet.autoSizeColumn(columnCount);
		Cell cell = row.createCell(columnCount);
		if (value instanceof Integer) {
			cell.setCellValue((Integer)value);
		}else if (value instanceof Boolean) {
			cell.setCellValue((Boolean)value);
		}else if (value instanceof String) {
			cell.setCellValue((String)value);
		}
		cell.setCellStyle(style);
	}
	
	private void writeDataLine() {
		int rowCount = 1;
		
		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(14);
		style.setFont(font);
		
		for (PaperDTO pp : dtos) {
			Row row = sheet.createRow(rowCount++);
			int columnCount = 0;
			String passed = "";
			if (pp.isPassed()) {
				passed = "Đạt";
			}else {
				passed = "Không đạt";
			}
			createCell(row, columnCount++, pp.getSubName(), style);
			createCell(row, columnCount++, pp.getExamName(), style);
			createCell(row, columnCount++, pp.getTotalMarks(), style);
			createCell(row, columnCount++, pp.getEarnedMark(), style);
			createCell(row, columnCount++, passed , style);
		}
	}
	
	public void export(HttpServletResponse response) throws IOException {
		writeHeaderLine();
		writeDataLine();
		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		
		outputStream.close();
	}
}
