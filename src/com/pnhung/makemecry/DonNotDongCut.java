/**
 * Copyright(C) 2019 Luvina Software
 * DonNotDongCut.java,09/12/2019 Phùng Nghĩa Hùng
 */
package com.pnhung.makemecry;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.pnhung.makemecry.common.Common;
import com.pnhung.makemecry.entity.InputData;
import com.pnhung.makemecry.entity.OutputData;

/**
 * @author Phùng Nghĩa Hùng
 *
 */
public class DonNotDongCut {
	public static final int NGAY_GHI_SO = 0;
	public static final int SO_CHUNG_TU = 1;
	public static final int NGAY_CHUNG_TU = 2;
	public static final int DIEN_GIAI = 3;
	public static final int SU_KIEN = 4;
	public static final int LOAI_TAI_KHOAN = 5;

	public static final int TAI_KHOAN = 6;
	public static final int PS_NO = 7;
	public static final int PS_CO = 8;

	public static final int COLUMN1 = 9;
	public static final int COLUMN2 = 10;
	public static final int COLUMN3 = 11;
	public static final int COLUMN4 = 12;
	public static final int COLUMN5 = 13;
	public static final int COLUMN6 = 14;
	public static final int COLUMN7 = 15;
	public static final int COLUMN8 = 16;
	public static final int COLUMN9 = 17;

	public static final String NO = "NO";
	public static final String CO = "CO";

	private List<InputData> listData;
	private List<OutputData> listDataOut;
	private int numberColumn;

	public DonNotDongCut() {
		listData = new ArrayList<>();
		listDataOut = new ArrayList<>();
		numberColumn = InputData.LIST_COLUMN.length;
	}

	public void doEveryThingHere(String filePath, String maChungTu) {
		try {

			@SuppressWarnings("resource")
			XSSFWorkbook workbook = new XSSFWorkbook(filePath);
			Sheet sheet = workbook.getSheet("GL đích");
			for (int rowNum = 1; sheet.getRow(rowNum) != null; rowNum++) {
				// for (int rowNum = 1; rowNum < 14; rowNum++) {
				Row row = sheet.getRow(rowNum);
				InputData inputData = new InputData();
				inputData.setIndex(rowNum);
				for (int cellNum = 0; cellNum <= (8 + numberColumn); cellNum++) {
					Cell cell = row.getCell(cellNum);
					Object data = null;
					if (cell == null) {
						data = "";
					} else {
						switch (cell.getCellType()) {
						case NUMERIC:
							data = cell.getNumericCellValue();
							break;
						case STRING:
							data = cell.getStringCellValue();
							break;
						default:
							data = "";
							break;
						}
					}
					// if (data == null) {
					// break;
					// }

					switch (cellNum) {
					case NGAY_GHI_SO:
						if (data == "") {
							data = 0.0;
						}
						inputData.setNgayGhiSo(data + "");
						break;
					case SO_CHUNG_TU:
						inputData.setSoChungTu(data + "");
						break;
					case NGAY_CHUNG_TU:
						if (data == "") {
							data = 0.0;
						}
						inputData.setNgayChungTu(data + "");
						break;
					case DIEN_GIAI:
						inputData.setDienGiai(data + "");
						break;
					case SU_KIEN:
						inputData.setSuKien(data + "");
						break;
					case LOAI_TAI_KHOAN:
						inputData.setLoaiTK(data + "");
						break;
					case TAI_KHOAN:
						inputData.setTaiKhoan(data + "");
						break;
					case PS_NO:
						inputData.setPsNo((double) data);
						break;
					case PS_CO:
						inputData.setPsCo((double) data);
						break;
					default:
						inputData.setColumnNumber(cellNum - 9, data + "");
						break;
					}
				}
				listData.add(inputData);

			}
			workbook.close();
			System.out.println(listData.size());
		} catch (EncryptedDocumentException | IOException e) {
			e.printStackTrace();
		}
	}

	public void hotCut() {
		List<InputData> listNo = new ArrayList<InputData>();
		List<InputData> listCo = new ArrayList<InputData>();

		List<InputData> listNoAfter = new ArrayList<>();
		List<InputData> listCoAfter = new ArrayList<>();

		List<List<InputData>> listDataThoaMan = new ArrayList<List<InputData>>();
		// List<List<InputData>> listDataThoaMan = new ArrayList<>();
		Set<String> keySet = new TreeSet<String>();
		for (InputData input : listData) {
			keySet.add(input.getSoChungTu());
			if (input.getPsNo() != 0.0) {
				listNo.add(input);
			} else {
				listCo.add(input);
			}
		}

		for (String key : keySet) {
			List<InputData> listNoKey = getListKey(key, listNo);
			List<InputData> listCoKey = getListKey(key, listCo);

			for (int i = 0; i < listNoKey.size(); i++) {
				InputData conNo = listNoKey.get(i);
				// Đoạn code để lấy các giá trị = nhau trước
				InputData equalCo = Common.getEqualData(conNo, listCoKey);
				if (equalCo.getTaiKhoan() != null) {
					OutputData outputData = setOutputData(conNo, equalCo);
					listNoKey.remove(conNo);
					listCoKey.remove(equalCo);
					listDataOut.add(outputData);
					i--;
					continue;
				}
				// Kết thúc
				InputData conCo;
				for (int j = 0; j < listCoKey.size(); j++) {
					conCo = listCoKey.get(j);
					if (conNo.getPsNo() == conCo.getPsCo()) {
						OutputData outputData = setOutputData(conNo, conCo);
						listNoKey.remove(i);
						listCoKey.remove(j);
						listDataOut.add(outputData);
						if (listCoKey.size() > 0 && listNoKey.size() > 0) {
							i--;
						}
						break;
					}
				}
			}

			while (listNoKey.size() > 0 && listCoKey.size() > 0) {
				InputData firstNo = listNoKey.get(0);

				InputData firstCo = listCoKey.get(0);

				if (firstNo.getPsNo() > firstCo.getPsCo()) {
					List<InputData> tongHopCacSoHang = getListSoHang(firstNo,
							listCoKey);

					// Chỉ test thôi nhé
					// List<InputData> tongHopCacSoHang = new ArrayList<>();
					// trienThoi(listCoKey, firstNo.getSoTien(),
					// listDataThoaMan);
					//
					// if (listDataThoaMan.size() > 1 || listDataThoaMan.size()
					// == 0) {
					// listDataThoaMan.clear();
					// break;
					// }
					// End
					listNoKey.remove(firstNo);

					// Test
					// tongHopCacSoHang.addAll(listDataThoaMan.get(0));
					// listDataThoaMan.clear();
					// End

					if (tongHopCacSoHang.size() == 0) {
						listNoAfter.add(firstNo);
					} else {
						for (InputData inputData : tongHopCacSoHang) {
							OutputData outputData = setOutputData(inputData,
									firstNo);
							listDataOut.add(outputData);
							listCoKey.remove(inputData);
						}
						// listDataThoaMan.clear();
					}

				} else {
					List<InputData> tongHopCacSoHang = getListSoHang(firstCo,
							listNoKey);

					// Test
					// List<InputData> tongHopCacSoHang = new ArrayList<>();
					// trienThoi(listNoKey, firstCo.getSoTien(),
					// listDataThoaMan);
					//
					// if (listDataThoaMan.size() > 1 || listDataThoaMan.size()
					// == 0) {
					// listDataThoaMan.clear();
					// break;
					// }
					//
					//
					listCoKey.remove(firstCo);

					// Test
					// tongHopCacSoHang.addAll(listDataThoaMan.get(0));
					// listDataThoaMan.clear();
					// End
					if (tongHopCacSoHang.size() == 0) {
						listCoAfter.add(firstCo);
					} else {
						for (InputData inputData : tongHopCacSoHang) {
							OutputData outputData = setOutputData(inputData,
									firstCo);
							listDataOut.add(outputData);
							listNoKey.remove(inputData);
						}
						// listDataThoaMan.clear();
					}
				}
			}
			listCoAfter.addAll(listCoKey);
			listNoAfter.addAll(listNoKey);
			listNoKey.clear();
			listCoKey.clear();
			
			TreeMap<String, InputData> listSumNo = getListEqual(NO, listNoAfter);
			TreeMap<String, InputData> listSumCo = getListEqual(CO, listCoAfter);

			listNoAfter.clear();
			listCoAfter.clear();

			listNoKey.addAll(new ArrayList<InputData>(listSumNo.values()));
			listCoKey.addAll(new ArrayList<InputData>(listSumCo.values()));

			listSumNo.clear();
			listSumCo.clear();

			int listNoSize = listNoKey.size();
			int listCoSize = listCoKey.size();
			
			for (int i = 0; i < listNoKey.size(); i++) {
				InputData no = listNoKey.get(i);
				// Đoạn code để lấy các giá trị = nhau trước
				InputData equalCo = Common.getEqualData(no, listCoKey);
				if (equalCo.getTaiKhoan() != null) {
					OutputData outputData = setOutputData(no, equalCo);
					listNoKey.remove(no);
					listCoKey.remove(equalCo);
					listDataOut.add(outputData);
					i--;
					continue;
				}
				// Kết thúc
				for (int j = 0; j < listCoKey.size(); j++) {
					InputData co = listCoKey.get(j);
					if (no.getTaiKhoan().equals(co.getTaiKhoan())) {
						listDataOut.add(setOutputData(no, co, listNoKey,
								listCoKey));
						if(listCoSize > listCoKey.size()) {
							j--;
							listCoSize = listNoKey.size();
						}					
						if(listNoSize > listNoKey.size()) {
							i--;
							listNoSize = listNoKey.size();
							break;
						}
					}
				}
			}

			double sum = Math.round(tinhTongSoTienIn(listCoKey) * 10000) / 10000.0;

			for (int i = 0; i < listNoKey.size(); i++) {
				InputData no = listNoKey.get(i);
				for (int j = 0; j < listCoKey.size(); j++) {
					InputData co = listCoKey.get(j);
					listDataOut.add(averageFamily(no, co, sum));
				}
			}
		}
	}

	private List<InputData> getListSoHang(InputData first, List<InputData> list) {
		List<InputData> listSoHang = new ArrayList<InputData>();
		for (int i = 0; i < list.size() - 1; i++) {
			double hieuSo = first.getSoTien() - list.get(i).getSoTien();
			listSoHang.add(list.get(i));
			for (int j = 0; j < list.size(); j++) {
				if (i == j) {
					continue;
				}
				InputData second = list.get(j);
				// listSoHang.add(second);
				if (Math.round(hieuSo * 100) / 100.0 >= second.getSoTien()) {
					hieuSo = Math.abs(Math.round(hieuSo * 100)) / 100.0
							- Math.round(second.getSoTien() * 100) / 100.0;
					listSoHang.add(second);
				}
				if (Math.abs(Math.round(hieuSo * 100)) / 100.0 == 0.00) {
					return listSoHang;
				}
			}
			listSoHang.clear();
		}
		return listSoHang;
	}

	/**
	 * @param conNo
	 * @param conCo
	 * @return
	 */
	private OutputData setOutputData(InputData conNo, InputData conCo) {
		OutputData outputData = new OutputData();
		outputData.setNgayGhiSo(conNo.getNgayGhiSo());
		outputData.setSoChungTu(conNo.getSoChungTu());
		outputData.setNgayChungTu(conNo.getNgayChungTu());
		if (conNo.getPsNo() != 0) {
			outputData.setDienGiai1(conNo.getDienGiai());
			outputData.setDienGiai2(conCo.getDienGiai());
			outputData.setSuKien1(conNo.getSuKien());
			outputData.setSuKien2(conCo.getSuKien());
			outputData.setLoaiTK1(conNo.getLoaiTK());
			outputData.setLoaiTK2(conCo.getLoaiTK());
			outputData.setTaiKhoan1(conNo.getTaiKhoan());
			outputData.setTaiKhoan2(conCo.getTaiKhoan());
			for (int i = 0; i < numberColumn; i++) {
				outputData.setColumnNumber(2 * i, conNo.getColumnNumber(i));
				outputData.setColumnNumber(2 * i + 1, conCo.getColumnNumber(i));
			}
		} else {
			outputData.setDienGiai2(conNo.getDienGiai());
			outputData.setDienGiai1(conCo.getDienGiai());
			outputData.setSuKien2(conNo.getSuKien());
			outputData.setSuKien1(conCo.getSuKien());
			outputData.setLoaiTK2(conNo.getLoaiTK());
			outputData.setLoaiTK1(conCo.getLoaiTK());
			outputData.setTaiKhoan2(conNo.getTaiKhoan());
			outputData.setTaiKhoan1(conCo.getTaiKhoan());
			for (int i = 0; i < numberColumn; i++) {
				outputData.setColumnNumber(2 * i, conCo.getColumnNumber(i));
				outputData.setColumnNumber(2 * i + 1, conNo.getColumnNumber(i));
			}
		}

		outputData.setSoTien(conNo.getSoTien());
		return outputData;
	}

	/**
	 * @param no2
	 * @param co2
	 * @return
	 */
	private OutputData averageFamily(InputData no, InputData co, double sum) {
		OutputData outputData = new OutputData();
		outputData.setNgayGhiSo(no.getNgayGhiSo());
		outputData.setSoChungTu(no.getSoChungTu());
		outputData.setNgayChungTu(no.getNgayChungTu());
		outputData.setDienGiai1(no.getDienGiai());
		outputData.setDienGiai2(co.getDienGiai());
		outputData.setSuKien1(no.getSuKien());
		outputData.setSuKien2(co.getSuKien());
		outputData.setLoaiTK1(no.getLoaiTK());
		outputData.setLoaiTK2(co.getLoaiTK());
		outputData.setTaiKhoan1(no.getTaiKhoan());
		outputData.setTaiKhoan2(co.getTaiKhoan());
		for (int i = 0; i < numberColumn; i++) {
			outputData.setColumnNumber(2 * i, no.getColumnNumber(i));
			outputData.setColumnNumber(2 * i + 1, co.getColumnNumber(i));
		}

		double soTien = no.getSoTien() * co.getSoTien() / sum;
		outputData.setSoTien(Math.round(soTien * 10000) / 10000.0);
		return outputData;
	}

	private double tinhTongSoTienIn(List<InputData> listCoTmp) {
		double sum = 0.0;
		for (InputData output : listCoTmp) {
			sum += Math.round(output.getSoTien() * 100) / 100.0;
		}
		return sum;
	}

	private OutputData setOutputData(InputData conNo, InputData conCo,
			List<InputData> listNo, List<InputData> listCo) {
		OutputData outputData = new OutputData();
		outputData.setNgayGhiSo(conNo.getNgayGhiSo());
		outputData.setSoChungTu(conNo.getSoChungTu());
		outputData.setNgayChungTu(conNo.getNgayChungTu());
		outputData.setDienGiai1(conNo.getDienGiai());
		outputData.setDienGiai2(conCo.getDienGiai());
		outputData.setSuKien1(conNo.getSuKien());
		outputData.setSuKien2(conCo.getSuKien());
		outputData.setLoaiTK1(conNo.getLoaiTK());
		outputData.setLoaiTK2(conCo.getLoaiTK());
		outputData.setTaiKhoan1(conNo.getTaiKhoan());
		outputData.setTaiKhoan2(conCo.getTaiKhoan());

		for (int i = 0; i < numberColumn; i++) {
			outputData.setColumnNumber(2 * i, conNo.getColumnNumber(i));
			outputData.setColumnNumber(2 * i + 1, conCo.getColumnNumber(i));
		}

		if (conNo.getSoTien() > conCo.getSoTien()) {
			conNo.setSoTien(Math.round((conNo.getSoTien() - conCo.getSoTien()) * 10000) / 10000.0);
			outputData.setSoTien(conCo.getSoTien());
			listCo.remove(conCo);
		} else if (conNo.getSoTien() < conCo.getSoTien()) {
			conCo.setSoTien(Math.round((conCo.getSoTien() - conNo.getSoTien()) * 10000) / 10000.0);
			outputData.setSoTien(conNo.getSoTien());
			listNo.remove(conNo);
		} else {
			outputData.setSoTien(conNo.getSoTien());
			listNo.remove(conNo);
			listCo.remove(conCo);
		}

		return outputData;
	}

	private List<InputData> getListKey(String key, List<InputData> list) {
		List<InputData> listKey = new ArrayList<InputData>();
		for (InputData inputData : list) {
			if (inputData.getSoChungTu().equals(key)) {
				listKey.add(inputData);
			}
		}
		return listKey;
	}

	private TreeMap<String, InputData> getListEqual(String type,
			List<InputData> list) {
		TreeMap<String, InputData> listEq = new TreeMap<String, InputData>();
		for (int i = 0; i < list.size(); i++) {
			InputData input = list.get(i);
			if (type.equals(NO)) {
				if (input.getPsCo() != 0) {
					continue;
				}
			} else if (type.equals(CO)) {
				if (input.getPsNo() != 0) {
					continue;
				}
			}
			if (listEq.containsKey(input.getTaiKhoan())) {
				InputData existData = listEq.get(input.getTaiKhoan());
				existData.setDienGiai(existData.getDienGiai() + "" + "/"
						+ input.getDienGiai());
				existData.setSuKien(existData.getSuKien() + "" + "/"
						+ input.getSuKien());
				existData.setSoTien(Math.round((existData.getSoTien() + input
						.getSoTien()) * 100) / 100.0);
				for (int j = 0; j < numberColumn; j++) {
					existData.setColumnNumber(j, existData.getColumnNumber(j)
							+ "" + "/" + input.getColumnNumber(j));
				}
			} else {
				listEq.put(input.getTaiKhoan(), input);
			}
		}
		return listEq;
	}

	public void ghiRafile(String filePath1) {
		try {
			System.out.println("Xong");
			// XSSFWorkbook workbook = new XSSFWorkbook();
			// Sheet sheet = workbook.getSheet("GL đích");
			// int rowNum = 0;
			// Row row1 = sheet.createRow(rowNum++);
			// int index = 0;
			// row1.createCell(index++).setCellValue("Ngày ghi sổ");
			// row1.createCell(index++).setCellValue("Số chứng từ");
			// row1.createCell(index++).setCellValue("Ngày chứng từ");
			// row1.createCell(index++).setCellValue("Diễn giải 1");
			// row1.createCell(index++).setCellValue("Diễn giải 2");
			// row1.createCell(index++).setCellValue("Sự kiện 1");
			// row1.createCell(index++).setCellValue("Sự kiện 2");
			// row1.createCell(index++).setCellValue("Loại TK 1");
			// row1.createCell(index++).setCellValue("Loại TK 2");
			// row1.createCell(index++).setCellValue("Tài khoản 1");
			// row1.createCell(index++).setCellValue("Tài khoản 2");
			// row1.createCell(index++).setCellValue("Số tiền");
			//
			// for (int i = 0; i < numberColumn; i++) {
			// row1.createCell(index++).setCellValue(
			// InputData.LIST_COLUMN[i] + "1");
			// row1.createCell(index++).setCellValue(
			// InputData.LIST_COLUMN[i] + "2");
			// }
			//
			// index = 0;
			// for (OutputData outputData : listDataOut) {
			// Row row = sheet.createRow(rowNum++);
			//
			// row.createCell(index++).setCellValue(outputData.getNgayGhiSo());
			// row.createCell(index++).setCellValue(outputData.getSoChungTu());
			// row.createCell(index++).setCellValue(
			// outputData.getNgayChungTu());
			// row.createCell(index++).setCellValue(outputData.getDienGiai1());
			// row.createCell(index++).setCellValue(outputData.getDienGiai2());
			// row.createCell(index++).setCellValue(outputData.getSuKien1());
			// row.createCell(index++).setCellValue(outputData.getSuKien2());
			// row.createCell(index++).setCellValue(outputData.getLoaiTK1());
			// row.createCell(index++).setCellValue(outputData.getLoaiTK2());
			// row.createCell(index++).setCellValue(outputData.getTaiKhoan1());
			// row.createCell(index++).setCellValue(outputData.getTaiKhoan2());
			// row.createCell(index++).setCellValue(outputData.getSoTien());
			// for (int i = 0; i < numberColumn; i++) {
			// row.createCell(index++).setCellValue(
			// outputData.getColumnNumber(2 * i));
			// row.createCell(index++).setCellValue(
			// outputData.getColumnNumber(2 * i + 1));
			// }
			// index = 0;
			// }
			//
			// FileOutputStream fileOut = new FileOutputStream(filePath1, true);
			// workbook.write(fileOut);
			// fileOut.close();
			// workbook.close();
			System.out.println(listDataOut.size());
			File file = new File(filePath1);
			if (!file.exists()) {
				file.createNewFile();
			}
			OutputStreamWriter writer = new OutputStreamWriter(
					new FileOutputStream(file, true), StandardCharsets.UTF_8);
			for (OutputData outputData : listDataOut) {
				writer.write(outputData.getNgayGhiSo().replace(',', 'ⱴ'));
				writer.write(",");
				writer.write(outputData.getSoChungTu().replace(',', 'ⱴ'));
				writer.write(",");
				writer.write(outputData.getNgayChungTu().replace(',', 'ⱴ'));
				writer.write(",");
				writer.write(outputData.getDienGiai1().replace(',', 'ⱴ'));
				writer.write(",");
				writer.write(outputData.getDienGiai2().replace(',', 'ⱴ'));
				writer.write(",");
				writer.write(outputData.getSuKien1().replace(',', 'ⱴ'));
				writer.write(",");
				writer.write(outputData.getSuKien2().replace(',', 'ⱴ'));
				writer.write(",");
				writer.write(outputData.getLoaiTK1().replace(',', 'ⱴ'));
				writer.write(",");
				writer.write(outputData.getLoaiTK2().replace(',', 'ⱴ'));
				writer.write(",");
				writer.write(outputData.getTaiKhoan1().replace(',', 'ⱴ'));
				writer.write(",");
				writer.write(outputData.getTaiKhoan2().replace(',', 'ⱴ'));
				writer.write(",");
				writer.write(String.valueOf(outputData.getSoTien()).replace('.', 'ⱴ'));

				for (int i = 0; i < numberColumn; i++) {
					writer.write(",");
					writer.write(outputData.getColumnNumber(2 * i).replace(',',
							'ⱴ'));
					writer.write(",");
					writer.write(outputData.getColumnNumber(2 * i + 1).replace(
							',', 'ⱴ'));
				}
				writer.write("\r\n");
			}
			writer.flush();
			writer.close();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	private void trienThoi(List<InputData> listInput, double target,
			List<List<InputData>> cacListThoaMan) {
		diTimSoHang(listInput, target, new ArrayList<InputData>(),
				cacListThoaMan);
	}

	private void diTimSoHang(List<InputData> listInput, double target,
			List<InputData> listSoHang, List<List<InputData>> cacListThoaMan) {
		if (cacListThoaMan.size() == 1) {
			return;
		}
		double sum = tinhTongSoTienIn(listSoHang);
		if (Math.round(sum * 100) / 100.0 == Math.round(target * 100) / 100.0) {
			cacListThoaMan.add(listSoHang);
			return;
		}
		if (Math.round(sum * 100) / 100.0 > Math.round(target * 100) / 100.0) {
			listSoHang.clear();
			return;
		}
		for (int i = 0; i < listInput.size(); i++) {
			if (cacListThoaMan.size() == 2) {
				return;
			}
			ArrayList<InputData> remaining = new ArrayList<>();
			InputData n = listInput.get(i);
			for (int j = i + 1; j < listInput.size(); j++) {
				remaining.add(listInput.get(j));
			}
			ArrayList<InputData> partial_rec = new ArrayList<>(listSoHang);
			partial_rec.add(n);
			diTimSoHang(remaining, target, partial_rec, cacListThoaMan);
		}
	}
}
