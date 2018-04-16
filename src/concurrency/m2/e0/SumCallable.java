package concurrency.m2.e0;

import java.util.concurrent.Callable;

public class SumCallable implements Callable<Integer> {
	
	private final int a;
	
	private final int b;
	
	public SumCallable(final int a, final int b) {
		this.a = a;
		this.b = b;
	}
	
	@Override
	public Integer call()throws Exception {
		return a+b;
	}

}
