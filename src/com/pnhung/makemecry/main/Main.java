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
		String folderOutput = "output ake";

		for (int i = 1; i <= 4; i++) {

			why.doEveryThingHere("D:\\akebono\\source ake\\GLK" + i + ".xlsx",
					"");
			why.diTimDoiUng();
			why.ghiRafile("D:\\akebono\\" + folderOutput + "\\FileDich.csv");
			why.ghiRafileSai("D:\\akebono\\" + folderOutput + "\\ListSai" + i
					+ ".xlsx");

			cut.doEveryThingHere("D:\\akebono\\" + folderOutput + "\\ListSai"
					+ i + ".xlsx", "");
			cut.hotCut();
			cut.ghiRafile("D:\\akebono\\" + folderOutput + "\\DichSai.csv");

			System.out.println("Đã hoàn thành file GLK " + i);
			why = new MakeMeCry();
			cut = new DonNotDongCut();
		}
//		 DiviceFile diviceFile = new DiviceFile();
//		 diviceFile.doEveryThingHere("D:\\akebono\\GL format goc ake.csv",
//		 "D:\\akebono\\source ake\\");
	}
}
