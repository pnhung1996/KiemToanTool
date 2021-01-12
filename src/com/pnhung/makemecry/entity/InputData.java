/**
 * Copyright(C) 2019 Luvina Software
 * InputData.java,11/11/2019 Phùng Nghĩa Hùng
 */
package com.pnhung.makemecry.entity;

/**
 * @author Phùng Nghĩa Hùng
 *
 */
public class InputData {
	public static String[] LIST_COLUMN = {};
	private String soChungTu, dienGiai, suKien, loaiTK, taiKhoan;
	private String ngayChungTu, ngayGhiSo;
	private double psNo, psCo;
	private double index;
	private String[] addColumn = new String[LIST_COLUMN.length];

	public InputData() {
	}

	public InputData(InputData inputData) {
		index = inputData.index + 0.5;
		addColumn = new String[InputData.LIST_COLUMN.length];
		soChungTu = inputData.soChungTu;
		dienGiai = inputData.dienGiai;
		suKien = inputData.suKien;
		loaiTK = inputData.loaiTK;
		taiKhoan = inputData.taiKhoan;
		ngayChungTu = inputData.ngayChungTu;
		psNo = inputData.psNo;
		psCo = inputData.psCo;
		ngayGhiSo = inputData.ngayGhiSo;
		for (int i = 0; i < addColumn.length; i++) {
			addColumn[i] = inputData.getColumnNumber(i);
		}
	}

	/**
	 * @return the ngayGhiSo
	 */
	public String getNgayGhiSo() {
		return ngayGhiSo;
	}

	/**
	 * @param ngayGhiSo
	 *            the ngayGhiSo to set
	 */
	public void setNgayGhiSo(String ngayGhiSo) {
		this.ngayGhiSo = ngayGhiSo;
	}

	/**
	 * @return the ngayChungTu
	 */
	public String getNgayChungTu() {
		return ngayChungTu;
	}

	/**
	 * @param ngayChungTu
	 *            the ngayChungTu to set
	 */
	public void setNgayChungTu(String ngayChungTu) {
		this.ngayChungTu = ngayChungTu;
	}

	/**
	 * @return the soChungTu
	 */
	public String getSoChungTu() {
		return soChungTu;
	}

	/**
	 * @param soChungTu
	 *            the soChungTu to set
	 */
	public void setSoChungTu(String soChungTu) {
		this.soChungTu = soChungTu;
	}

	/**
	 * @return the dienGiai
	 */
	public String getDienGiai() {
		return dienGiai;
	}

	/**
	 * @param dienGiai
	 *            the dienGiai to set
	 */
	public void setDienGiai(String dienGiai) {
		this.dienGiai = dienGiai;
	}

	/**
	 * @return the suKien
	 */
	public String getSuKien() {
		return suKien;
	}

	/**
	 * @param suKien
	 *            the suKien to set
	 */
	public void setSuKien(String suKien) {
		this.suKien = suKien;
	}

	/**
	 * @return the loaiTK
	 */
	public String getLoaiTK() {
		return loaiTK;
	}

	/**
	 * @param loaiTK
	 *            the loaiTK to set
	 */
	public void setLoaiTK(String loaiTK) {
		this.loaiTK = loaiTK;
	}

	/**
	 * @return the taiKhoan
	 */
	public String getTaiKhoan() {
		return taiKhoan;
	}

	/**
	 * @param taiKhoan
	 *            the taiKhoan to set
	 */
	public void setTaiKhoan(String taiKhoan) {
		this.taiKhoan = taiKhoan;
	}

	/**
	 * @return the psNo
	 */
	public double getPsNo() {
		return psNo;
	}

	/**
	 * @param psNo
	 *            the psNo to set
	 */
	public void setPsNo(double psNo) {
		this.psNo = psNo;
	}

	/**
	 * @return the psCo
	 */
	public double getPsCo() {
		return psCo;
	}

	/**
	 * @param psCo
	 *            the psCo to set
	 */
	public void setPsCo(double psCo) {
		this.psCo = psCo;
	}

	public double getSoTien() {
		if (psCo != 0) {
			return psCo;
		} else if (psNo != 0) {
			return psNo;
		}
		return 0;
	}

	public void setSoTien(double soTien) {
		if (psCo != 0) {
			psCo = soTien;
		} else if (psNo != 0) {
			psNo = soTien;
		}
	}

	/**
	 * @return the index
	 */
	public double getIndex() {
		return index;
	}

	/**
	 * @param index
	 *            the index to set
	 */
	public void setIndex(double index) {
		this.index = index;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

		return soChungTu + "::" + taiKhoan + "::" + psNo + "::" + psCo + "\n";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof InputData) {
			InputData input2 = (InputData) obj;
			return index == input2.index;
		}
		return super.equals(obj);
	}

	public String getColumnNumber(int index) {
		return addColumn[index];
	}

	public void setColumnNumber(int index, String value) {
		addColumn[index] = value;
	}
}
