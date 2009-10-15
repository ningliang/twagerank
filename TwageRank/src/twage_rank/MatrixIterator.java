package twage_rank;

import it.unimi.dsi.fastutil.ints.Int2DoubleOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import log_utils.Result;
import log_utils.ResultHandler;

public class MatrixIterator implements ResultHandler {
	private static final double RANDOM_JUMP = 0.15;
	
	private Int2DoubleOpenHashMap before;
	private Int2IntOpenHashMap outDegrees;
	private Int2DoubleOpenHashMap after;
	
	public MatrixIterator(Int2DoubleOpenHashMap before, Int2IntOpenHashMap outDegrees) {
		this.before = before;
		this.outDegrees = outDegrees;
		this.after = new Int2DoubleOpenHashMap(before.size());
		
		double sum = 0.0;
		for (int key : before.keySet()) sum += before.get(key);
		double average = sum / ((double) before.size());
		double jumpFactor = RANDOM_JUMP * average;
		for (int key : before.keySet()) after.put(key, jumpFactor);
	}
	
	public void handleResult(Result result) {
		double sum = 0.0; 			
		for (int id : result.getFollowerIds()) sum += before.get(id) / ((double) outDegrees.get(id));
		after.put(result.getId(), after.get(result.getId()) + (1.0 - RANDOM_JUMP) * sum);
	}
	
	public Int2DoubleOpenHashMap getProbabilityMap() { return this.after; }
}
