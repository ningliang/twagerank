package twage_rank;

import it.unimi.dsi.fastutil.ints.Int2DoubleOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import log_utils.Result;
import log_utils.ResultHandler;

public class Initializer implements ResultHandler {
	Int2DoubleOpenHashMap probabilityMap = new Int2DoubleOpenHashMap();
	Int2IntOpenHashMap outDegreeMap = new Int2IntOpenHashMap();
	
	public void handleResult(Result result) {
		if (result.isSuccessful()) {
			probabilityMap.put(result.getId(), 1.0);
			for (int id : result.getFollowerIds()) this.outDegreeMap.put(id, this.outDegreeMap.get(id) + 1);
		}
	}
	
	public Int2DoubleOpenHashMap getProbabilityMap() { return this.probabilityMap; }
	public Int2IntOpenHashMap getOutDegreeMap() { return this.outDegreeMap; }
	
}
