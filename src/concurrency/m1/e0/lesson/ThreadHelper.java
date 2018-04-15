package concurrency.m1.e0.lesson;

public class ThreadHelper {
	
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
			this.numberResFib = fibProcessing(numberCalc);
		}
		
		public int getResult() {
			return numberResFib;
		}
		// END
	}
}
