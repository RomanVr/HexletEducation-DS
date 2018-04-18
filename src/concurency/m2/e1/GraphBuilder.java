package concurency.m2.e1;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import concurency.m0.e2.GraphNode;
import concurency.m0.e2.XOField;
import concurency.m0.e2.XOField.Figure;

public class GraphBuilder implements Callable<GraphNode> {

	private final ExecutorService executorService;

	private final XOField.Figure nextFigure;

	private final XOField currentField;

	private final int deepLevel;

	public GraphBuilder(final ExecutorService executorService, final Figure currentFigure, final XOField currentField,
			final int deepLevel) {
		super();
		this.executorService = executorService;
		this.nextFigure = currentFigure == XOField.Figure.O ? XOField.Figure.X : XOField.Figure.X;
		this.currentField = currentField;
		this.deepLevel = deepLevel;
	}

	@Override
	public GraphNode call() {
		final List<Future<GraphNode>> futures = new ArrayList<>();
		final Set<GraphNode> children = new CopyOnWriteArraySet<>();

		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				if (currentField.getFigure(x, y) != null) {
					continue;
				}
				final XOField newField = new XOField(currentField);
				newField.setFigure(x, y, nextFigure);
				final GraphBuilder graphBuilder = new GraphBuilder(executorService, nextFigure, newField,
						deepLevel + 1);

				if (isAsync()) {
					final Future<GraphNode> future = executorService.submit(graphBuilder);
					futures.add(future);
				} else {
					children.add(graphBuilder.call());
				}
			}
		}
		if (!futures.isEmpty()) {
			for (Future<GraphNode> future : futures) {
				try {
					children.add(future.get());
				} catch (final InterruptedException | ExecutionException e) {
					throw new RuntimeException(e);
				}
			}
		}
		return new GraphNode(currentField, children);
	}
	
	private boolean isAsync() {		
		if (deepLevel > 3) { 
			return false;
		} else {
			return true;
		}
	}
}
