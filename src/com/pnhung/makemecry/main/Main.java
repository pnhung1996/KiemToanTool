package com.pnhung.makemecry.main;

import com.pnhung.makemecry.CopyOfMakeMeCry;
import com.pnhung.makemecry.DiviceFile;
import com.pnhung.makemecry.DonNotDongCut;
import com.pnhung.makemecry.MakeMeCry;
import com.pnhung.makemecry.MakeMeCryExtension;

/**
 * Copyright(C) 2019 Luvina Software
 * Main.java,11/11/2019 Phùng Nghĩa Hùng
 */

/**
 * @author Phùng Nghĩa Hùng
 */
public class Main {
//	public static void main(String[] args) {
//		MakeMeCry why = new MakeMeCry();
//		DonNotDongCut cut = new DonNotDongCut();
//		String folderOutput = "Test";
//
//		for (int i = 1; i <= 1; i++) {
//
//			why.doEveryThingHere("D:\\akebono\\Test\\GLK" + i + ".xlsx",
//					"");
//			why.diTimDoiUng();
//			why.ghiRafile("D:\\akebono\\" + folderOutput + "\\FileDich.csv");
//			why.ghiRafileSai("D:\\akebono\\" + folderOutput + "\\ListSai" + i
//					+ ".xlsx");
//
//			cut.doEveryThingHere("D:\\akebono\\" + folderOutput + "\\ListSai"
//					+ i + ".xlsx", "");
//			cut.hotCut();
//			cut.ghiRafile("D:\\akebono\\" + folderOutput + "\\DichSai.csv");
//
//			System.out.println("Đã hoàn thành file GLK " + i);
//			why = new MakeMeCry();
//			cut = new DonNotDongCut();
//		}
////		 DiviceFile diviceFile = new DiviceFile();
////		 diviceFile.doEveryThingHere("D:\\akebono\\GL goc 1.csv",
////		 "D:\\akebono\\GL\\");
//	}
	public static void main(String[] args) {
		MakeMeCry why = new MakeMeCry();
		DonNotDongCut cut = new DonNotDongCut();
		String folderOutput = "35k";

		for (int i = 8; i <= 8; i++) {

			why.doEveryThingHere("D:\\akebono\\35k\\GLK" + i + ".xlsx",
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
//		 diviceFile.doEveryThingHere("D:\\akebono\\GL goc 7-12.csv",
//		 "D:\\akebono\\35k\\");
	}
}
