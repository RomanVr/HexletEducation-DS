package concurency.m2.e1.lesson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import com.sun.org.glassfish.external.statistics.Statistic;

public class GraphBuilder implements Callable<Set<GoField>> {

	private final ExecutorService executorService;

	private final Figure nextFigure;

	private final GoField currentField;

	private final int deepLevel;

	public GraphBuilder(final ExecutorService executorService, final Figure currentFigure, final GoField currentField,
			final int deepLevel) {
		this.executorService = executorService;
		this.currentField = currentField;
		this.nextFigure = currentFigure == Figure.WHITE ? Figure.BLACK : Figure.WHITE;
		this.deepLevel = deepLevel;
	}

	@Override
	public Set<GoField> call() throws Exception {
		// BEGIN (write your solution here) #1
		final Set<GoField> fields = new CopyOnWriteArraySet<>();
		final List<Future<Set<GoField>>> futures = new ArrayList<>();
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				if (currentField.figures[x][y] != null) {
					continue;
				}
				final GoField newfield = new GoField(currentField);
				newfield.figures[x][y] = nextFigure;
				final GraphBuilder graphBuilder = new GraphBuilder(executorService, nextFigure, newfield,
						deepLevel + 1);
				if (isAsync()) {
					final Future<Set<GoField>> future = executorService.submit(graphBuilder);
					futures.add(future);
				} else {
					fields.addAll(graphBuilder.call());
				}
			}
		}
		if (!futures.isEmpty()) {
			for (Future<Set<GoField>> future : futures) {
				try {
					fields.addAll(future.get());
				} catch (final InterruptedException | ExecutionException e) {
					throw new RuntimeException(e);
				}
			}
		}
		if (isCurrentFieldFinal()) 
			 { fields.add(currentField); }
		return fields;
		// END #4
	}

	private boolean isAsync() {
		if (deepLevel > 3) {
			return false;
		} else {
			return true;
		}
	}

	private boolean isCurrentFieldFinal() {
		for (Figure[] line : currentField.figures) {
			for (Figure f : line) {
				if (f == null) {
					return false;
				}
			}
		}
		return true;
	}

	private static class Point {

		private final int x;

		private final int y;

		public Point(final int x, final int y) {
			super();
			this.x = x;
			this.y = y;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}
	}
}
