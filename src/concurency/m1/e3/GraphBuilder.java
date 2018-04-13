package concurency.m1.e3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.experimental.theories.FromDataPoints;

import com.sun.istack.internal.FinalArrayList;

import concurency.m0.e2.GraphNode;
import concurency.m0.e2.XOField;

public class GraphBuilder {
	
	public GraphNode build(final XOField.Figure currentFigure,
						   final XOField currentField,
						   final int deepLevel) {
		if (deepLevel > 3) return new GraphNode(currentField, Collections.emptySet());
		final List<Thread> threads = new ArrayList<>();
		final XOField.Figure nextFigure = 
				currentFigure == XOField.Figure.O ? XOField.Figure.X : XOField.Figure.O;
		final Set<GraphNode> children = new HashSet<>();
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				if (currentField.getFigure(x, y) != null) {
					continue;
				}
				final XOField newField = new XOField(currentField);
				newField.setFigure(x, y, nextFigure);
				final Thread threadThatAddChildren = new Thread() {
					@Override
					public void run() {
						children.add(build(nextFigure, newField, deepLevel + 1));
					}
				};
				threadThatAddChildren.start();
				threads.add(threadThatAddChildren);
			}
		}
		for (Thread th : threads) {
			try {
				th.join();				
			} catch (final InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
		return new GraphNode(currentField, children);
	}
 }
