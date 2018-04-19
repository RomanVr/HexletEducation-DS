package concurrency.m2.e2;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

import concurency.m0.e2.GraphNode;
import concurency.m0.e2.XOField;
import concurency.m0.e3.GraphHelper;


public class Main {
	
	public static void main(String[] args) throws ExecutionException, InterruptedException {
		final ForkJoinPool forkJoinPool = new ForkJoinPool();
		final GraphBuilder gb = new GraphBuilder(XOField.Figure.X, new XOField(), 0);
		ForkJoinTask<GraphNode> rootTask = gb.fork();
		forkJoinPool.submit(rootTask);
		System.out.println(GraphHelper.countNodes(rootTask.join()));
	}

}
