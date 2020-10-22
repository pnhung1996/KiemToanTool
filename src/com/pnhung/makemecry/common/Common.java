/**
 * Copyright(C) 2019 Luvina Software
 * Common.java,11/11/2019 Phùng Nghĩa Hùng
 */
package com.pnhung.makemecry.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.pnhung.makemecry.entity.InputData;

/**
 * @author Phùng Nghĩa Hùng
 *
 */
public class Common {
	private static final int MAX_LENGTH = 3;

	public static String getFormatDate(double dateNum) {
		String s = String.format("%06d", (int) dateNum);
		DateFormat formatDate = new SimpleDateFormat("HHmmss");
		try {
			Date date = formatDate.parse(s);
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			return format.format(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	public static boolean compare3LetterHead(String object, String dest) {
		boolean result = true;
		if (object.length() >= MAX_LENGTH && dest.length() >= MAX_LENGTH) {
			for (int i = 0; i < MAX_LENGTH; i++) {
				if (object.charAt(i) != dest.charAt(i)) {
					result = false;
					break;
				}
			}
		}
		return result;
	}

	public static InputData getEqualData(InputData input, List<InputData> list) {
		InputData outputData = new InputData();
		InputData tmp = new InputData();
		for (int i = 0; i < list.size(); i++) {
			tmp = list.get(i);
			if ((tmp.getTaiKhoan().equals(input.getTaiKhoan()))
					&& ((Math.round(tmp.getSoTien() * 100) / 100.0) == (Math
							.round(input.getSoTien() * 100) / 100.0))) {
				outputData = tmp;
//				list.remove(i);
				return tmp;
			}
		}
		for (int i = 0; i < list.size(); i++) {
			tmp = list.get(i);
			if (compare3LetterHead(input.getTaiKhoan(), tmp.getTaiKhoan())
					&& ((Math.round(tmp.getSoTien() * 100) / 100.0) == (Math
							.round(input.getSoTien() * 100) / 100.0))) {
				outputData = tmp;
//				list.remove(i);
				return tmp;
			}
		}
		return outputData;
	}
}
