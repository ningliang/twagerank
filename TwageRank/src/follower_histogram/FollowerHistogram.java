package follower_histogram;

import java.io.File;
import java.util.List;

import log_utils.LogFile;
import log_utils.Result;
import log_utils.ResultHandler;
import utils.Utility;

public class FollowerHistogram {
	public static void main(String[] args) {
		// Setup
		if (args.length != 2) {
			System.out.println("Usage: FollowerHistogram <bucket_size> <dir>");
			System.exit(-1);
		}
		
		// Get bucket size
		int bucketSize = Integer.parseInt(args[0]);
		if (bucketSize <= 0) {
			System.out.println("Bucket size must be greater than or equal to 0.");
			System.exit(-1);
		}
		
		// Get files and iterate
		List<File> files = Utility.getFiles(args[1]);
		Histogram histogram = new Histogram(bucketSize);
		try {
			for (File file : files) {
				LogFile log = new LogFile(file.getPath());
				log.readAll(histogram);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		// Output result
		int[] buckets = histogram.getBuckets();
		int maxBucket = histogram.getMaxBucket();
		for (int i = 0; i <= maxBucket; i++) {
			int min = i * histogram.getBucketSize();
			System.out.println(min + " " + buckets[i]);
		}
	}
}

class Histogram implements ResultHandler {
	private int bucketSize;
	private int[] buckets;
	private int maxBucket;
	
	public Histogram(int bucketSize) { 
		this.bucketSize = bucketSize; 
		this.buckets = new int[1];
	}
	
	public void handleResult(Result result) {
		int bucketKey = result.getFollowerIds().length / this.bucketSize;
		while (this.buckets.length < bucketKey + 1) {
			int[] newBuckets = new int[this.buckets.length * 2];
			for (int i = 0; i < this.buckets.length; i++) newBuckets[i] = this.buckets[i];
			this.buckets = newBuckets;
		}
		this.maxBucket = Math.max(bucketKey, this.maxBucket);
		this.buckets[bucketKey]++;
	}
	
	public int getBucketSize() { return this.bucketSize; }
	public int getMaxBucket() { return this.maxBucket; }
	public int[] getBuckets() { return this.buckets; }
}
