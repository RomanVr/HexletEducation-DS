package concurrency.m2.e2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

import concurency.m0.e2.GraphNode;
import concurency.m0.e2.XOField;
import concurency.m0.e2.XOField.Figure;

public class GraphBuilder extends RecursiveTask<GraphNode> {

	private final XOField.Figure nextFigure;

	private final XOField currentField;

	private final int deepLevel;

	public GraphBuilder(final Figure currentFigure, final XOField currentField, final int deepLevel) {
		super();
		this.nextFigure = currentFigure == XOField.Figure.O ? XOField.Figure.X : XOField.Figure.X;
		this.currentField = currentField;
		this.deepLevel = deepLevel;
	}

	@Override
	public GraphNode compute() {
		final List<ForkJoinTask<GraphNode>> tasks = new ArrayList<>();
		final Set<GraphNode> children = new HashSet<>();

		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				if (currentField.getFigure(x, y) != null) {
					continue;
				}
				final XOField newField = new XOField(currentField);
				newField.setFigure(x, y, nextFigure);
				final GraphBuilder graphBuilder = new GraphBuilder(nextFigure, newField, deepLevel + 1);

				if (isAsync()) {
					tasks.add(graphBuilder.fork());
				} else {
					children.add(graphBuilder.compute());
				}
			}
		}
		if (!tasks.isEmpty()) {
			for (ForkJoinTask<GraphNode> task : tasks) {
				children.add(task.join());
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
