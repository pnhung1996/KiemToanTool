/**
 * Copyright(C) 2019 Luvina Software
 * InputClone.java,14/12/2019 Phùng Nghĩa Hùng
 */
package com.pnhung.makemecry.entity;

/**
 * @author Phùng Nghĩa Hùng
 *
 */
public class InputClone {
	private String soChungTu, dienGiai, suKien, loaiTK, taiKhoan, ngayChungTu, ngayGhiSo;
	private double psNo, psCo;
	private String[] addColumn;
	
	/**
	 * 
	 */
	public InputClone(InputData inputData) {
		addColumn = new String[InputData.LIST_COLUMN.length];
		soChungTu = inputData.getSoChungTu();
		dienGiai = inputData.getDienGiai();
		suKien = inputData.getSuKien();
		loaiTK = inputData.getLoaiTK();
		taiKhoan = inputData.getTaiKhoan();
		ngayChungTu = inputData.getNgayChungTu();
		psNo = inputData.getPsNo();
		psCo = inputData.getPsCo();
		ngayGhiSo = inputData.getNgayGhiSo();
		for (int i = 0; i < addColumn.length; i++) {
			addColumn[i] = inputData.getColumnNumber(i);
		}
	}

	/**
	 * @return the soChungTu
	 */
	public String getSoChungTu() {
		return soChungTu;
	}

	/**
	 * @return the dienGiai
	 */
	public String getDienGiai() {
		return dienGiai;
	}

	/**
	 * @return the suKien
	 */
	public String getSuKien() {
		return suKien;
	}

	/**
	 * @return the loaiTK
	 */
	public String getLoaiTK() {
		return loaiTK;
	}

	/**
	 * @return the taiKhoan
	 */
	public String getTaiKhoan() {
		return taiKhoan;
	}

	/**
	 * @return the ngayChungTu
	 */
	public String getNgayChungTu() {
		return ngayChungTu;
	}

	/**
	 * @return the psNo
	 */
	public double getPsNo() {
		return psNo;
	}

	public void setPsNo(double psNo) {
		this.psNo = psNo;
	}

	public void setPsCo(double psCo) {
		this.psCo = psCo;
	}

	/**
	 * @return the psCo
	 */
	public double getPsCo() {
		return psCo;
	}

	/**
	 * @return the ngayGhiSo
	 */
	public String getNgayGhiSo() {
		return ngayGhiSo;
	}
	public String getColumnNumber(int index) {
		return addColumn[index];
	}
	
}
