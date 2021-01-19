package com.pnhung.makemecry;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.pnhung.makemecry.entity.InputData;

public class DiviceFile {
	private static final int MAX_FILE_SIZE = 20000;
	private static final String FORMAT_NUMBER = "[-]?[0-9]+[,]?[0-9]*";
	private static final String FORMAT_NUMBER_WITH_DOT = "[-]?[0-9]+[\\.]?[0-9]*";
	private static final String SEPARATOR = ",";
	private List<InputData> listData;
	private int numberColumn;

	public DiviceFile() {
		listData = new ArrayList<>();
		numberColumn = InputData.LIST_COLUMN.length;
	}

	public void doEveryThingHere(String filePath, String savePath) {
		try {
			System.out.println("Đã hoàn thành file GLK ");
			int index = 0;
			int fileIndex = 1;
			String[] input = null;
			BufferedReader bir = new BufferedReader(new InputStreamReader(
					new FileInputStream(filePath), "UTF8"));
			String additionString = InputData.LIST_COLUMN.length == 0 ? ""
					: " ";
			String line = bir.readLine() + additionString;

			while (line != null) {
				input = line.split(SEPARATOR);
				while (input.length < (9 + InputData.LIST_COLUMN.length)) {
					line += (" " + bir.readLine() + additionString);
					input = line.split(SEPARATOR);
				}
				// input = splitString(line);
				InputData inputData = new InputData();
				inputData.setIndex(index++);

				System.out.println(index);
				// System.out.println(line);
				// System.out.println(input.length);
				for (int i = 0; i < input.length; i++) {
					String data = input[i].replace("ⱴ", SEPARATOR);
					switch (i) {
					case MakeMeCry.NGAY_GHI_SO:
						if (data == "" || data == "-") {
							data = "0";
						}
						inputData.setNgayGhiSo(data.replace(",", "ⱴ"));
						break;
					case MakeMeCry.SO_CHUNG_TU:
						inputData.setSoChungTu(data.replace(",", "ⱴ"));
						break;
					case MakeMeCry.NGAY_CHUNG_TU:
						if (data == "" || data == "-") {
							data = "0";
						}
						inputData.setNgayChungTu(data.replace(",", "ⱴ"));
						break;
					case MakeMeCry.DIEN_GIAI:
						inputData.setDienGiai(data.replace(",", "ⱴ"));
						break;
					case MakeMeCry.SU_KIEN:
						inputData.setSuKien(data.replace(",", "ⱴ"));
						break;
					case MakeMeCry.LOAI_TAI_KHOAN:
						inputData.setLoaiTK(data.replace(",", "ⱴ"));
						break;
					case MakeMeCry.TAI_KHOAN:
						inputData.setTaiKhoan(data.replace(",", "ⱴ"));
						break;
					case MakeMeCry.PS_NO:
						if (!data.matches(FORMAT_NUMBER)
								&& !data.matches(FORMAT_NUMBER_WITH_DOT)) {
							data = "0.0";
						}
						inputData.setPsNo(Double.parseDouble(data.replace(",",
								".")));
						break;
					case MakeMeCry.PS_CO:
						if (!data.matches(FORMAT_NUMBER)
								&& !data.matches(FORMAT_NUMBER_WITH_DOT)) {
							data = "0.0";
						}
						inputData.setPsCo(Double.parseDouble(data.replace(",",
								".")));
						break;
					default:
						inputData
								.setColumnNumber(i - 9, data.replace(",", "ⱴ"));
						break;
					}

				}
				if (listData.size() >= MAX_FILE_SIZE) {
					if (!inputData.getSoChungTu().equals(
							listData.get(listData.size() - 1).getSoChungTu())) {
						listData.remove(inputData);
						batDauTachFile(fileIndex++, savePath);
						listData.clear();
						Runtime.getRuntime().gc();
					}

				}
				listData.add(inputData);
				line = bir.readLine();
				if (line != null) {
					line += additionString;
				}
			}
			bir.close();
			if (listData.size() > 0) {
				batDauTachFile(fileIndex++, savePath);
			}
			System.out.println(index);
		} catch (EncryptedDocumentException | IOException e) {
			e.printStackTrace();
		}
	}

	private void batDauTachFile(int fileIndex, String savePath) {
		try {
			System.out.println("Xong : " + fileIndex);
			XSSFWorkbook workbook = new XSSFWorkbook();
			Sheet sheet = workbook.createSheet("Sheet 1");
			int rowNum = 0;
			Row row1 = sheet.createRow(rowNum++);
			int index = 0;
			row1.createCell(index++).setCellValue("Ngày ghi sổ");
			row1.createCell(index++).setCellValue("Số chứng từ");
			row1.createCell(index++).setCellValue("Ngày chứng từ");
			row1.createCell(index++).setCellValue("Diễn giải");
			row1.createCell(index++).setCellValue("Sự kiện");
			row1.createCell(index++).setCellValue("Loại TK");
			row1.createCell(index++).setCellValue("Tài khoản");
			row1.createCell(index++).setCellValue("PS nợ");
			row1.createCell(index++).setCellValue("PS có");

			for (int i = 0; i < numberColumn; i++) {
				row1.createCell(index++).setCellValue(InputData.LIST_COLUMN[i]);
			}
			index = 0;
			for (InputData inputData : listData) {
				Row row = sheet.createRow(rowNum++);
				row.createCell(index++).setCellValue(inputData.getNgayGhiSo());
				row.createCell(index++).setCellValue(inputData.getSoChungTu());
				row.createCell(index++)
						.setCellValue(inputData.getNgayChungTu());
				row.createCell(index++).setCellValue(inputData.getDienGiai());
				row.createCell(index++).setCellValue(inputData.getSuKien());
				row.createCell(index++).setCellValue(inputData.getLoaiTK());
				row.createCell(index++).setCellValue(inputData.getTaiKhoan());
				row.createCell(index++).setCellValue(inputData.getPsNo());
				row.createCell(index++).setCellValue(inputData.getPsCo());

				for (int i = 0; i < numberColumn; i++) {
					row.createCell(index++).setCellValue(
							inputData.getColumnNumber(i));
				}
				index = 0;
			}
			// Write the output to a file
			File file = new File(savePath + "GLK" + fileIndex + ".xlsx");
			file.createNewFile();

			FileOutputStream fileOut = new FileOutputStream(file);
			workbook.write(fileOut);
			fileOut.close();

			// Closing the workbook
			workbook.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
