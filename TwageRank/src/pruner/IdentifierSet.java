package pruner;

import log_utils.Result;
import log_utils.ResultHandler;

public class IdentifierSet implements ResultHandler {
	byte[] idMap = new byte[80000000];
	int count = 0;
	private static final int LOG_THRESHOLD = 1000000;
	
	public void handleResult(Result result) {
		if (result.isSuccessful() && idMap[result.getId()] != 1) {
			count++;
			idMap[result.getId()] = 1;
			if (count % LOG_THRESHOLD == 0) System.out.println(count + " valid identifiers.");
		}
	}
	
	public byte[] getSet() { 
		return this.idMap; 
	}
	
	public Boolean hasId(int id) {
		return (idMap[id] != 0);
	}
	
	public int getCount() {
		return this.count;
	}
}
