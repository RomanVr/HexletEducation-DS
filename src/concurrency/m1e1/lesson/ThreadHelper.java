package concurrency.m1e1.lesson;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadHelper {
	
	public static final ExecutorService EXECUTOR_SERVICE
	// BEGIN (write your solution here) 
		= Executors.newWorkStealingPool(8);
	// END
	
	public static int fib(final int numberToCalculate) throws Exception {
		// BEGIN (write your solution here)
		final FibCalculator calculator = new FibCalculator(numberToCalculate);
		Thread thread = new Thread(calculator);
		thread.start();
		thread.join();
		return calculator.getResult();
		// END
	}

	private static class FibCalculator implements Runnable {
		
		private final int currentNum;

		private int result;

		public FibCalculator(final int numberToCalculate) {
			this.currentNum = numberToCalculate;
		}

		@Override
		public void run() {
			if (currentNum == 1 || currentNum == 2) {
				result = 1;
				return;
			}
			final FibCalculator left = new FibCalculator(currentNum - 1);
			final FibCalculator right = new FibCalculator(currentNum - 2);
			final Future leftF =  EXECUTOR_SERVICE.submit(left);
			final Future rightF =  EXECUTOR_SERVICE.submit(right);
			try {
				leftF.get();
				rightF.get();
			} catch (final InterruptedException | ExecutionException e) {}
			result = left.getResult() + right.getResult();
		}

		public int getResult() {
			return result;
		}
	}
}
