package com.pnhung.makemecry;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class MergeFiles {
	public MergeFiles() {
		try {
			File file = new File(
					"D:\\akebono\\New Microsoft Excel Worksheet.csv");
			String lol = "ⱴCuakali";
			if (!file.exists()) {
				file.createNewFile();
			}
			OutputStreamWriter writer = new OutputStreamWriter(
					new FileOutputStream(file, true), StandardCharsets.UTF_8);
			writer.write(lol.replace(",", "ⱴ"));
			writer.write(",");
			writer.write("Hùng;pro");
			writer.write(",");
			writer.write("Đậu xanh; AloALo");
			writer.write("\r\n");
			writer.write(lol.replace(",", "ⱴ"));
			writer.write(",");
			writer.write("Hùng;pro");
			writer.write(",");
			writer.write("Đậu xanh; AloALo");
			writer.write("\r\n");
			writer.flush();
			writer.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
