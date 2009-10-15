package generator;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import log_utils.Result;
import log_utils.Status;

public class Generator {
	public static void main(String[] args) throws Exception {
		if (args.length != 1) {
			System.out.println("Usage: Generator <out_file>");
			System.exit(1);
		}
		
		DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(args[0])));
		Result result = null;
		List<Result> queuedResults = new LinkedList<Result>();
		
		result = new Result();
		result.setStatus(Status.SUCCESS);		
		result.setId(1);		
		result.setFollowerIds(new int[] {});
		queuedResults.add(result);
		
		result = new Result();
		result.setStatus(Status.SUCCESS);
		result.setId(2);
		result.setFollowerIds(new int[] { 1, 3 });
		queuedResults.add(result);
		
		result = new Result();
		result.setStatus(Status.SUCCESS);
		result.setId(3);
		result.setFollowerIds(new int[] { 2 });
		queuedResults.add(result);
		
		for (Result queuedResult : queuedResults) {
			writeResult(out, queuedResult);
			System.out.println("Wrote result " + queuedResult.getId());
		}
		out.close();
	}
	
	public static void writeResult(DataOutputStream out, Result result) throws IOException {
		out.writeInt(result.getId());
		out.writeInt(result.getStatus().toInt());
		if (result.isSuccessful()) {
			out.writeInt(result.getFollowerIds().length);
			for (int id : result.getFollowerIds()) out.writeInt(id);
		}
		out.flush();
	}
}
