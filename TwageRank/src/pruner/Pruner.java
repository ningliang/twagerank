package pruner;

import java.io.File;
import java.util.List;

import log_utils.LogFile;
import utils.Utility;

public class Pruner {
	public static void main(String[] args) {
		// Usage
		if (args.length != 2) {
			System.out.println("usage: Pruner <indir> <outdir>");
			System.exit(1);
		}
		
		List<File> files = Utility.getFiles(args[0]);
		
		// Grab all valid ids
		System.out.println("Filtering valid identifiers.");
		IdentifierSet set = new IdentifierSet();		
		try {
			for (File file : files) {
				LogFile log = new LogFile(file.getPath());
				log.readAll(set);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		System.out.println("Found " + set.getCount() + " valid results.");
		
		// Filter the log files into the output dir
		System.out.println("Writing valid results to log.");
		FilteringLogger filter = new FilteringLogger(args[1], set.getSet());
		try {
			for (File file : files) {
				LogFile log = new LogFile(file.getPath());
				log.readAll(filter);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		filter.flushQueue();
		filter.close();
		System.out.println("Done.");
	}
}
