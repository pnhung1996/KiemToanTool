/**
 * Copyright(C) 2019 Luvina Software
 * MakeMeCry.java,11/11/2019 Phùng Nghĩa Hùng
 */
package com.pnhung.makemecry;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
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
import com.pnhung.makemecry.entity.InputClone;
import com.pnhung.makemecry.entity.InputData;
import com.pnhung.makemecry.entity.OutputData;

/**
 * @author Phùng Nghĩa Hùng
 *
 */
public class CopyOfMakeMeCry {
	public static final int NGAY_GHI_SO = 0;
	public static final int SO_CHUNG_TU = 1;
	public static final int NGAY_CHUNG_TU = 2;
	public static final int DIEN_GIAI = 3;
	public static final int SU_KIEN = 4;
	public static final int LOAI_TAI_KHOAN = 5;

	public static final int TAI_KHOAN = 6;
	public static final int PS_NO = 7;
	public static final int PS_CO = 8;

	public static final String NO = "NO";
	public static final String CO = "CO";

	private List<InputData> listData;
	private List<InputClone> listDataSai;
	private List<OutputData> listDataOut;
	private int numberColumn;

	private long startTime;
	private long currentTime;

	public CopyOfMakeMeCry() {
		listData = new ArrayList<>();
		listDataOut = new ArrayList<>();
		listDataSai = new ArrayList<>();
		numberColumn = InputData.LIST_COLUMN.length;
	}

	private Comparator<InputData> descNo = new Comparator<InputData>() {
		public int compare(InputData o1, InputData o2) {
			if (o1.getPsNo() - o2.getPsNo() > 0) {
				return -1;
			} else if (o1.getPsNo() - o2.getPsNo() < 0) {
				return 1;
			} else {
				return 0;
			}
		};
	};

	private Comparator<InputData> descCo = new Comparator<InputData>() {
		public int compare(InputData o1, InputData o2) {
			if (o1.getPsCo() - o2.getPsCo() > 0) {
				return -1;
			} else if (o1.getPsCo() - o2.getPsCo() < 0) {
				return 1;
			} else {
				return 0;
			}
		};
	};

	public void doEveryThingHere(String filePath, String savePath) {
		try {

			@SuppressWarnings("resource")
			XSSFWorkbook workbook = new XSSFWorkbook(filePath);
			Sheet sheet = workbook.getSheet("Sheet 1");
			for (int rowNum = 1; sheet.getRow(rowNum) != null; rowNum++) {

				// for (int rowNum = 2; rowNum < 12; rowNum++) {
				Row row = sheet.getRow(rowNum);
				InputData inputData = new InputData();
				inputData.setIndex(rowNum);
				// System.out.println(rowNum);
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
						if (data == "") {
							data = 0.0;
						}
						inputData.setPsNo((double) data);
						break;
					case PS_CO:
						if (data == "") {
							data = 0.0;
						}
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

	public void diTimDoiUng() {
		List<InputData> listNo = new ArrayList<>();
		List<InputData> listCo = new ArrayList<>();
		List<OutputData> listOutTmp = new ArrayList<>();
		List<InputClone> listSaiTmp = new ArrayList<>();
		List<InputData> listNoAfter = new ArrayList<>();
		List<InputData> listCoAfter = new ArrayList<>();
		List<List<InputData>> listDataThoaMan = new ArrayList<>();
		Set<String> keySet = new TreeSet<String>();

		for (InputData input : listData) {
			keySet.add(input.getSoChungTu());
			if (input.getPsNo() != 0.0 && input.getPsCo() == 0.0) {
				listNo.add(input);
			} else if (input.getPsNo() == 0.0 && input.getPsCo() != 0.0) {
				listCo.add(input);
			} else if (input.getPsCo() != 0.0 && input.getPsNo() != 0.0) {
				InputData cloneCo = new InputData();
				cloneCo.setSoChungTu(input.getSoChungTu());
				cloneCo.setDienGiai(input.getDienGiai());
				cloneCo.setSuKien(input.getSuKien());
				cloneCo.setLoaiTK(input.getLoaiTK());
				cloneCo.setTaiKhoan(input.getTaiKhoan());
				cloneCo.setNgayChungTu(input.getNgayChungTu());
				cloneCo.setPsNo(0);
				cloneCo.setPsCo(input.getPsCo());
				cloneCo.setNgayGhiSo(input.getNgayGhiSo());
				for (int i = 0; i < InputData.LIST_COLUMN.length; i++) {
					cloneCo.setColumnNumber(i, input.getColumnNumber(i));
				}
				cloneCo.setIndex(input.getIndex() + 1000000);
				listCo.add(cloneCo);

				input.setPsCo(0);
				listNo.add(input);

			}
		}
		listNo.sort(descNo);
		listCo.sort(descCo);
		for (String key : keySet) {
			// Loại bỏ các key sai
			// if(!key.equals("C7101-190125001")) {
			// continue;
			// }
			// boolean bangNhau = xuLyTruongHopSai(listNo, listCo, key);
			List<InputData> listNoKey = getListKey(key, listNo);
			List<InputData> listCoKey = getListKey(key, listCo);
			// List<InputData> listNoTmp = new ArrayList<InputData>();
			List<InputData> listCoTmp = new ArrayList<InputData>();
			copyData(listSaiTmp, listNoKey);
			copyData(listSaiTmp, listCoKey);
			// listNoTmp.addAll(listNoKey);
			listCoTmp.addAll(listCoKey);
			double sum2 = Math.round(tinhTongSoTienIn(listCoTmp) * 100) / 100.0;
			// bangNhau = true;
			// if (bangNhau) {
			// Tìm các cặp bằng nhau
			for (int i = 0; i < listNoKey.size(); i++) {
				InputData conNo = listNoKey.get(i);
				// Đoạn code để lấy các giá trị = nhau trước
				InputData equalCo = Common.getEqualData(conNo, listCoKey);
				if (equalCo.getTaiKhoan() != null) {
					OutputData outputData = setOutputData(conNo, equalCo);
					listNoKey.remove(conNo);
					listCoKey.remove(equalCo);
					listOutTmp.add(outputData);
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
						listOutTmp.add(outputData);
						if (listCoKey.size() > 0 && listNoKey.size() > 0) {
							i--;
						}
						break;
					}
				}
			}

			// Tìm kiếm ứng với từng phần tử trong list nợ
			timDoiUng(listNoKey, listCoKey, listDataThoaMan, listOutTmp);

			// Tìm kiếm ứng với từng phần tử trong list có
			timDoiUng(listCoKey, listNoKey, listDataThoaMan, listOutTmp);

			listCoAfter.addAll(listCoKey);
			listNoAfter.addAll(listNoKey);
			TreeMap<String, InputData> listSumNo = getListEqual(NO, listNoAfter);
			TreeMap<String, InputData> listSumCo = getListEqual(CO, listCoAfter);
			listNoAfter.clear();
			listCoAfter.clear();
			listNoAfter.addAll(new ArrayList<InputData>(listSumNo.values()));
			listCoAfter.addAll(new ArrayList<InputData>(listSumCo.values()));
			listCoAfter.sort(descCo);
			listNoAfter.sort(descNo);

			for (int i = 0; i < listNoAfter.size(); i++) {
				InputData conNo = listNoAfter.get(i);
				// Đoạn code để lấy các giá trị = nhau trước
				InputData equalCo = Common.getEqualData(conNo, listCoKey);
				if (equalCo.getTaiKhoan() != null) {
					OutputData outputData = setOutputData(conNo, equalCo);
					listNoAfter.remove(conNo);
					listCoAfter.remove(equalCo);
					listOutTmp.add(outputData);
					i--;
					continue;
				}
				// Kết thúc
				InputData conCo;
				for (int j = 0; j < listCoAfter.size(); j++) {
					conCo = listCoAfter.get(j);
					if (conNo.getPsNo() == conCo.getPsCo()) {
						OutputData outputData = setOutputData(conNo, conCo);
						listNoAfter.remove(i);
						listCoAfter.remove(j);
						listOutTmp.add(outputData);
						if (listCoAfter.size() > 0 && listNoAfter.size() > 0) {
							i--;
						}
						break;
					}
				}
			}
			while (listNoAfter.size() > 0 && listCoAfter.size() > 0) {
				InputData firstNo = listNoAfter.get(0);
				InputData firstCo = listCoAfter.get(0);

				if (firstNo.getPsNo() > firstCo.getPsCo()) {
					List<InputData> tongHopCacSoHang = getListSoHang(firstNo,
							listCoAfter);
					listNoAfter.remove(firstNo);

					for (InputData inputData : tongHopCacSoHang) {
						OutputData outputData = setOutputData(inputData,
								firstNo);
						listOutTmp.add(outputData);
						listCoAfter.remove(inputData);
					}

				} else {
					List<InputData> tongHopCacSoHang = getListSoHang(firstCo,
							listNoAfter);
					listCoAfter.remove(firstCo);

					for (InputData inputData : tongHopCacSoHang) {
						OutputData outputData = setOutputData(inputData,
								firstCo);
						listOutTmp.add(outputData);
						listNoAfter.remove(inputData);
					}

				}
			}
			double sum1 = Math.round(tinhTongSoTienOut(listOutTmp) * 100) / 100.0;
			if (Math.round(sum1 * 100) / 100.0 == Math.round(sum2 * 100) / 100.0) {
				listDataOut.addAll(listOutTmp);
			} else {
				listDataSai.addAll(listSaiTmp);
			}
			listOutTmp.clear();
			listCoTmp.clear();
			listSaiTmp.clear();
			// listNoTmp.clear();
			listCoAfter.clear();
			listNoAfter.clear();
		}
		// }
		// System.out.println(listDataOut);
	}

	private void timDoiUng(List<InputData> listSource,
			List<InputData> listDest, List<List<InputData>> listDataThoaMan,
			List<OutputData> listOutTmp) {
		InputData input = new InputData();
		for (int i = 0; i < listSource.size(); i++) {
			input = listSource.get(i);
			List<InputData> tongHopCacSoHang = new ArrayList<>();
			startTime = Calendar.getInstance().getTimeInMillis();
			trienThoi(listDest, input.getSoTien(), listDataThoaMan);
			if (listDataThoaMan.size() == 1) {
				tongHopCacSoHang.addAll(listDataThoaMan.get(0));
				if (tongHopCacSoHang.size() > 0) {
					listSource.remove(input);
					i--;
					for (InputData soHangTimThay : tongHopCacSoHang) {
						OutputData outputData = setOutputData(soHangTimThay,
								input);
						listOutTmp.add(outputData);

						listDest.remove(soHangTimThay);
					}
					listDataThoaMan.clear();
				}
			}
			input = new InputData();
		}

	}

	/**
	 * @param listSaiTmp
	 * @param listNoKey
	 */
	private void copyData(List<InputClone> listSaiTmp, List<InputData> listKey) {
		for (int i = 0; i < listKey.size(); i++) {
			listSaiTmp.add(new InputClone(listKey.get(i)));
		}

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
				existData.setDienGiai(existData.getDienGiai() + "" + "\\"
						+ input.getDienGiai());
				existData.setSuKien(existData.getSuKien() + "" + "\\"
						+ input.getSuKien());
				for (int j = 0; j < numberColumn; j++) {
					existData.setColumnNumber(j, existData.getColumnNumber(j)
							+ "" + "\\" + input.getColumnNumber(j));
				}
				existData.setSoTien(Math.round((existData.getSoTien() + input
						.getSoTien()) * 100) / 100.0);
			} else {
				listEq.put(input.getTaiKhoan(), input);
			}
		}
		return listEq;
	}

	/**
	 * @param listCoTmp
	 * @return
	 */
	private double tinhTongSoTienIn(List<InputData> listCoTmp) {
		double sum = 0.0;
		for (InputData output : listCoTmp) {
			sum += Math.round(output.getSoTien() * 100) / 100.0;
		}
		return sum;
	}

	/**
	 * @param listOutTmp
	 * @return
	 */
	private double tinhTongSoTienOut(List<OutputData> listOutTmp) {
		double sum = 0.0;
		for (OutputData output : listOutTmp) {
			sum += Math.round(output.getSoTien() * 100) / 100.0;
		}
		return sum;
	}

	/**
	 * @param key
	 * @param listNo
	 * @return
	 */
	private List<InputData> getListKey(String key, List<InputData> list) {
		List<InputData> listKey = new ArrayList<InputData>();
		for (InputData inputData : list) {
			if (inputData.getSoChungTu().equals(key)) {
				listKey.add(inputData);
			}
		}
		return listKey;
	}

	/**
	 * @param key
	 * @param listNo
	 * @return
	 */
	private InputData getFirst(String key, List<InputData> list) {
		for (InputData inputData : list) {
			if (inputData.getSoChungTu().equals(key)) {
				return inputData;
			}
		}
		return null;
	}

	/**
	 * @param firstNo
	 * @param listCo
	 * @return
	 */
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
	 * @param key
	 * @param listNo
	 * @return
	 */
	private boolean exist(String key, List<InputData> list) {
		for (InputData inputData : list) {
			if (inputData.getSoChungTu().equals(key)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param hieuSo
	 * @param listCoTmp
	 * @return
	 */
	private int timBanGhiQuaHieuSo(double hieuSo, List<InputData> list) {
		int soBanGhi = 0;
		for (InputData input : list) {
			if (input.getPsCo() == hieuSo || input.getPsNo() == hieuSo) {
				soBanGhi++;
			}
		}
		return soBanGhi;
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
	 * 
	 */
	public void ghiRafile(String filePath1) {
		try {
			System.out.println("Xong");
			// Cách ghi file kiểu cũ
			// XSSFWorkbook workbook = new XSSFWorkbook(filePath1);
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
			// index = 0;
			// System.out.println(listDataOut.size());
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
			//
			// for (int i = 0; i < numberColumn; i++) {
			// row.createCell(index++).setCellValue(
			// outputData.getColumnNumber(2 * i));
			// row.createCell(index++).setCellValue(
			// outputData.getColumnNumber(2 * i + 1));
			// }
			// index = 0;
			// }
			//
			// FileOutputStream fileOut = new FileOutputStream(filePath2);
			// workbook.write(fileOut);
			// fileOut.close();
			//
			//
			// workbook.close();
			// Kết thúc ghi ra file kiểu cũ
			File file = new File(filePath1);
			if (!file.exists()) {
				file.createNewFile();
			}
			OutputStreamWriter writer = new OutputStreamWriter(
					new FileOutputStream(file, true), StandardCharsets.UTF_8);
			System.out.println(listDataOut.size());
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
				writer.write(String.valueOf(outputData.getSoTien()).replace(
						'.', 'ⱴ'));

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

	public void ghiRafileSai(String filePath1) {
		try {
			System.out.println("Xong");
			XSSFWorkbook workbook = new XSSFWorkbook();
			Sheet sheet = workbook.createSheet("GL đích");
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
			for (InputClone inputData : listDataSai) {
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
			File file = new File(filePath1);
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

	private void trienThoi(List<InputData> listInput, double target,
			List<List<InputData>> cacListThoaMan) {
		diTimSoHang(listInput, target, new ArrayList<InputData>(),
				cacListThoaMan);
	}

	private void diTimSoHang(List<InputData> listInput, double target,
			List<InputData> listSoHang, List<List<InputData>> cacListThoaMan) {
		currentTime = Calendar.getInstance().getTimeInMillis();
		if (cacListThoaMan.size() == 1 || currentTime - startTime >= 1) {
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
