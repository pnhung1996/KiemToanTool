/**
 * Copyright(C) 2019 Luvina Software
 * OutputData.java,12/11/2019 Phùng Nghĩa Hùng
 */
package com.pnhung.makemecry.entity;

/**
 * @author Phùng Nghĩa Hùng
 *
 */
public class OutputData {
	;
	private String[] addColumn = new String[2 * InputData.LIST_COLUMN.length];
	private String soChungTu, dienGiai1, dienGiai2, suKien1, suKien2, loaiTK1, loaiTK2, taiKhoan1, taiKhoan2,ngayChungTu,ngayGhiSo;
	private double soTien;

	/**
	 * @return the ngayGhiSo
	 */
	public String getNgayGhiSo() {
		return ngayGhiSo;
	}

	/**
	 * @param ngayGhiSo the ngayGhiSo to set
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
	 * @param ngayChungTu the ngayChungTu to set
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
	 * @param soChungTu the soChungTu to set
	 */
	public void setSoChungTu(String soChungTu) {
		this.soChungTu = soChungTu;
	}

	/**
	 * @return the dienGiai1
	 */
	public String getDienGiai1() {
		return dienGiai1;
	}

	/**
	 * @param dienGiai1 the dienGiai1 to set
	 */
	public void setDienGiai1(String dienGiai1) {
		this.dienGiai1 = dienGiai1;
	}

	/**
	 * @return the dienGiai2
	 */
	public String getDienGiai2() {
		return dienGiai2;
	}

	/**
	 * @param dienGiai2 the dienGiai2 to set
	 */
	public void setDienGiai2(String dienGiai2) {
		this.dienGiai2 = dienGiai2;
	}

	/**
	 * @return the suKien1
	 */
	public String getSuKien1() {
		return suKien1;
	}

	/**
	 * @param suKien1 the suKien1 to set
	 */
	public void setSuKien1(String suKien1) {
		this.suKien1 = suKien1;
	}

	/**
	 * @return the suKien2
	 */
	public String getSuKien2() {
		return suKien2;
	}

	/**
	 * @param suKien2 the suKien2 to set
	 */
	public void setSuKien2(String suKien2) {
		this.suKien2 = suKien2;
	}

	/**
	 * @return the loaiTK1
	 */
	public String getLoaiTK1() {
		return loaiTK1;
	}

	/**
	 * @param loaiTK1 the loaiTK1 to set
	 */
	public void setLoaiTK1(String loaiTK1) {
		this.loaiTK1 = loaiTK1;
	}

	/**
	 * @return the loaiTK2
	 */
	public String getLoaiTK2() {
		return loaiTK2;
	}

	/**
	 * @param loaiTK2 the loaiTK2 to set
	 */
	public void setLoaiTK2(String loaiTK2) {
		this.loaiTK2 = loaiTK2;
	}

	/**
	 * @return the taiKhoan1
	 */
	public String getTaiKhoan1() {
		return taiKhoan1;
	}

	/**
	 * @param taiKhoan1 the taiKhoan1 to set
	 */
	public void setTaiKhoan1(String taiKhoan1) {
		this.taiKhoan1 = taiKhoan1;
	}

	/**
	 * @return the taiKhoan2
	 */
	public String getTaiKhoan2() {
		return taiKhoan2;
	}

	/**
	 * @param taiKhoan2 the taiKhoan2 to set
	 */
	public void setTaiKhoan2(String taiKhoan2) {
		this.taiKhoan2 = taiKhoan2;
	}

	/**
	 * @return the soTien
	 */
	public double getSoTien() {
		return soTien;
	}

	/**
	 * @param soTien the soTien to set
	 */
	public void setSoTien(double soTien) {
		this.soTien = soTien;
	}

	public String getColumnNumber(int index) {
		return addColumn[index];
	}

	public void setColumnNumber(int index, String value) {
		addColumn[index] = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ngayGhiSo + ":" + soChungTu + ":" + taiKhoan1 + ":" + taiKhoan2 + ":" + soTien + "\n";
	}
}
