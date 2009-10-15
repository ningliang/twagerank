package utils;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class Utility {
	public static List<File> getFiles(String dirPath) {
		// Ensure dir
		File dir = new File(dirPath);
		if (!dir.isDirectory()) {
			System.out.println("Not a directory: " + dir.getAbsolutePath());
			System.exit(1);
		}
	
		// Get array of File
		List<File> files = new LinkedList<File>();
		for (File file : dir.listFiles()) if (file.getPath().endsWith(".txt")) files.add(file);
		return files;
	}
}