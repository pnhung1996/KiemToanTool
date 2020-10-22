package com.pnhung.makemecry.main;

import com.pnhung.makemecry.DiviceFile;
import com.pnhung.makemecry.DonNotDongCut;
import com.pnhung.makemecry.MakeMeCry;

/**
 * Copyright(C) 2019 Luvina Software
 * Main.java,11/11/2019 Phùng Nghĩa Hùng
 */

/**
 * @author Phùng Nghĩa Hùng
 */
public class Main {
	public static void main(String[] args) {
		MakeMeCry why = new MakeMeCry();
		DonNotDongCut cut = new DonNotDongCut();

		for (int i = 1; i <= 20; i++) {

			why.doEveryThingHere("D:\\akebono\\source12345\\GLK" + i + ".xlsx", "");
			why.diTimDoiUng();
			why.ghiRafile("D:\\akebono\\output12345\\FileDich.csv");
			why.ghiRafileSai("D:\\akebono\\output12345\\ListSai" + i + ".xlsx");

			cut.doEveryThingHere("D:\\akebono\\output12345\\ListSai" + i + ".xlsx", "");
			cut.hotCut();
			cut.ghiRafile("D:\\akebono\\output12345\\DichSai.csv");

			System.out.println("Đã hoàn thành file GLK " + i);
			why = new MakeMeCry();
			cut = new DonNotDongCut();
		}
//		 DiviceFile diviceFile = new DiviceFile();
//		 diviceFile.doEveryThingHere("D:\\akebono\\GL goc 12345.csv", "D:\\akebono\\source12345\\");

	}
}
