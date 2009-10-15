package analysis;

import it.unimi.dsi.fastutil.ints.Int2DoubleOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;

public class SortPrint {
	public static void main(String[] args) {
		if (args.length != 3) {
			System.out.println("Usage: SortPrint <file> <start> <length>");
			System.exit(1);
		}
		
		int start = Integer.parseInt(args[1]);
		int length = Integer.parseInt(args[2]);
		
		Int2DoubleOpenHashMap state = null;
		if (args.length == 3) {
			System.out.println("Getting result file.");
			DataInputStream in = null;
			state = new Int2DoubleOpenHashMap();
			try {
				in = new DataInputStream(new BufferedInputStream(new FileInputStream(args[0])));
				while (in.available() > 0) {
					int key = in.readInt();
					double value = in.readDouble();
					state.put(key, value);
				}
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			} finally {
				if (in != null) {
					try { in.close();
					} catch (IOException e) { e.printStackTrace(); }
				}
			}
			System.out.println("Read " + state.size() + " keys from file.");
		}
		
		IntArrayList ids = new IntArrayList(state.size());
		for (int id : state.keySet()) ids.add(id);
		Collections.sort(ids, new PageRankComparator(state, false));
		
		for (int i = start; i < start + length; i++) {
			int id = ids.get(i);
			System.out.println(id + " " + state.get(id));
		}
	}
}

class PageRankComparator implements Comparator<Integer> {
	Int2DoubleOpenHashMap ranks = null;
	boolean ascending = false;
	public PageRankComparator(Int2DoubleOpenHashMap ranks, boolean ascending) {
		this.ascending = ascending;
		this.ranks = ranks; 
	}
	
	public int compare(Integer o1, Integer o2) {
		double first = ranks.get(o1);
		double second = ranks.get(o2);
		return ascending ? Double.compare(first, second) : Double.compare(second, first);
	}	
}