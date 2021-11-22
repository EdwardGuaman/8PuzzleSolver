import java.util.PriorityQueue;
import java.util.HashSet;
import java.util.Arrays;

/**
 * 
 */

/**
 * @author EdwardGuaman
 *
 */

public class AStar {
	public Node AStarSearch(problem p, heuristicLambda h, int t) {
		Node node;
		Node child;
		PriorityQueue<Node> frontier = new PriorityQueue<>();
		HashSet<String> explored = new HashSet<>();
		node = new Node(p.getInitialState(), h, p.getOindex());
		frontier.add(node);
		while (true) {
			if (frontier.isEmpty())
				return null;
			node = frontier.remove();
			int[] currentState = node.getState();
//			goal test
			if (p.goalTest(currentState)) {
				return node;
			}
			if (t == 0) {
				// check to see if removed node has already been explored
				if (!explored.add(Arrays.toString(currentState))) {
					while (!frontier.isEmpty()) {
						node = frontier.remove();
						currentState = node.getState();
						if (explored.add(Arrays.toString(currentState)))
							break;
					}
					if (p.goalTest(currentState)) {
						return node;
					}
				}
			}
			// getting actions available based on position of 0
			int[] currentActions = p.getActions()[node.getOindex()];
			for (int i = 0; i < currentActions.length; i++) {
				child = new Node(p, h, node, currentActions[i]);
				if (t == 0) {
					if (!explored.contains(Arrays.toString(child.getState()))) {
						frontier.add(child);
					}
				} else {
					frontier.add(child);
				}
			}
		}
	}
}

class Node implements Comparable<Node> {
	private Node parent;
	private int[] state;
	private int pathcost;
	private int hval;
	private int goalcost;
	private int oindex;

	/**
	 * @param is The initial state of the problem
	 * @param h  The heuristic to be used
	 * @param o  The index that contains '0'
	 */
	public Node(int[] is, heuristicLambda h, int o) {
		state = Arrays.copyOf(is, 9);
		pathcost = 0;
		hval = h.heuristic(state);
		goalcost = pathcost + hval;
		oindex = o;
	}

	/**
	 * @param p      The problem for this AStar algorithm to use
	 * @param h      The heuristic to be used to calculate new heuristic value
	 * @param n      Parent Node
	 * @param action Which of the possible actions is taken
	 */
	public Node(problem p, heuristicLambda h, Node n, int action) {
		parent = n;
		pathcost = n.getPathcost() + 1;
		state = Arrays.copyOf(n.getState(), 9);
		oindex = p.tmodel(state, action, n.getOindex());
		hval = h.heuristic(state);
		goalcost = pathcost + hval;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public int[] getState() {
		return state;
	}

	public void setState(int[] state) {
		this.state = state;
	}

	public int getPathcost() {
		return pathcost;
	}

	public void setPathcost(int pathcost) {
		this.pathcost = pathcost;
	}

	public int getHval() {
		return hval;
	}

	public void setHval(int hval) {
		this.hval = hval;
	}

	public int getGoalcost() {
		return goalcost;
	}

	public void setGoalcost(int goalcost) {
		this.goalcost = goalcost;
	}

	public int getOindex() {
		return oindex;
	}

	public void setOindex(int oindex) {
		this.oindex = oindex;
	}

	@Override
	public int compareTo(Node arg0) {
		if (arg0.getGoalcost() == goalcost)
			return 0;
		else if (arg0.getGoalcost() > goalcost)
			return -1;
		else
			return 1;
	}

	public void printState() {
		for (int i : state)
			System.out.print(i);
		System.out.println();
	}

	public void printPath() {
		Node currentNode;
		int counter = 1;
		printState();
		if (parent == null) {
			return;
		}
		currentNode = parent;
		currentNode.printState();
		counter++;
		while (currentNode.getParent() != null) {
			currentNode = currentNode.getParent();
			currentNode.printState();
			counter++;
		}
		System.out.println("depth : " + counter);

	}

}
