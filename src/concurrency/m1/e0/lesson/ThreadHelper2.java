package concurrency.m1.e0.lesson;

public class ThreadHelper2 {

	public static int fib(final int numberToCalculate) throws Exception {
		final FibCalculator calculator = new FibCalculator(numberToCalculate);
		calculator.start();
		calculator.join();
		return calculator.getResult();
	}

	private static class FibCalculator extends Thread {
		// BEGIN (write your solution here)
		private int numberResFib;
		
		private final int numberCalc;
		
		public FibCalculator(final int numberCalc) {
			this.numberCalc = numberCalc;
		}
		
		private int fibProcessing(final int numberCalc) {
			if (numberCalc < 1) return -1;
			if (numberCalc == 1) { return 1; }
			if (numberCalc == 2) { return 1; }
			
			
			
			return fibProcessing(numberCalc - 1) + fibProcessing(numberCalc - 2);
		}
		
		@Override
		public void run() {
			int fib1=0;
			int fib2=0;
			final Thread threadfibProcessing = new Thread() {
				@Override
				public void run() {
				fibProcessing(numberCalc - 1);
				}
			};
			threadfibProcessing.start();
			
			fibProcessing(numberCalc);
			this.numberResFib = fib1 + fib2;
		}
		
		public int getResult() {
			return numberResFib;
		}
		// END
	}
}
