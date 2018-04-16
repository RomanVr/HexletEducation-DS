package concurrency.m2.e0;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import jdk.nashorn.internal.codegen.CompilerConstants.Call;

public class Main {
	
	public static void main(String[] args) throws Exception {
		final ExecutorService executorService = Executors.newWorkStealingPool(3);
		final Callable<Integer> sum = new SumCallable(1, 2);
		final Future<Integer> future = executorService.submit(sum);
		System.out.println(future.get());
	}

}
