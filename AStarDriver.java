import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 
 */

/**
 * @author EdwardGuaman
 *
 */
public class AStarDriver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		problem p;
		AStar a = new AStar();
		Node result = null;
		int[] input = new int[9];
		int hchoice;
		int tgchoice;
		heuristicLambda h1 = (state) -> {
			int count = 0;
			for (int i = 0; i < state.length; i++) {
				if (state[i] != i)
					count++;
			}
			return count;
		};
		heuristicLambda h2 = (state) -> {
			int count = 0;
			for (int i = 0; i < state.length; i++) {
				if (state[i] != i && state[i] != 0) {
					count += Math.abs((i - state[i]) / 3) + Math.abs((i - state[i]) % 3);
				}
			}
			return count;
		};
//		ui
		System.out.println("1) Generate a random 8-puzzle problem");
		System.out.println("2) Enter a specific 8-puzzle configuration");
		System.out.println();
		int temp = sc.nextInt();
//		user entered puzzle
		if (temp == 2) {
			System.out.print("Enter puzzle: ");
			sc.nextLine();
			System.out.println();
			String tempa = sc.nextLine();
			String[] given = tempa.split(" ");
			System.out.println(given.length);
			for (int i = 0; i < given.length; i++) {
				System.out.print(given[i]);
			}
			System.out.println();
			for (int i = 0; i < given.length; i++) {
				input[i] = Integer.parseInt(given[i]);
			}
			p = new problem(input);
			System.out.println("1) Use H1");
			System.out.println("2) Use H2");
			hchoice = sc.nextInt();
			System.out.println("");
			System.out.println("0) Graph");
			System.out.println("1) Tree");
			tgchoice = sc.nextInt();
			System.out.println("");
//			which heuristic is used and graph or tree search
			if (hchoice == 1) {
				if (tgchoice == 0) {
					result = a.AStarSearch(p, h1, 0);
				} else {
					result = a.AStarSearch(p, h1, 1);
				}
			} else {
				if (tgchoice == 0) {
					result = a.AStarSearch(p, h2, 0);
				} else {
					result = a.AStarSearch(p, h2, 1);
				}
			}
//			random puzzle
		} else {
			int[] test = { 5, 0, 4, 8, 6, 7, 1, 2, 3 };
			System.out.println("1) Use H1");
			System.out.println("2) Use H2");
			hchoice = sc.nextInt();
			System.out.println("");
			System.out.println("0) Graph");
			System.out.println("1) Tree");
			tgchoice = sc.nextInt();
			System.out.println("");

			for (int i = 0; i < 1; i++) {
				shuffle(test);
				if (isValid(test)) {
					p = new problem(test);
					if (hchoice == 1) {
						if (tgchoice == 0) {
							result = a.AStarSearch(p, h1, 0);
						} else {
							result = a.AStarSearch(p, h1, 1);
						}
					} else {
						if (tgchoice == 0) {
							result = a.AStarSearch(p, h2, 0);
						} else {
							result = a.AStarSearch(p, h2, 1);
						}
					}
				} else {
					i--;
				}
			}
//			print result of search
			int counter = 0;
			System.out.println("A* Resulting Path: ");
			result.printState();
			while (result.getParent() != null) {
				result = result.getParent();
				result.printState();
				counter++;
			}
			System.out.println("Depth: " + counter);
			System.out.println();
			sc.close();
		}
	}

	public static boolean isValid(int[] first) {
		int count = 0;
		int n = first.length;
		for (int i = 0; i < n - 1; i++) {
			if (first[i] == 0)
				continue;
			for (int j = i + 1; j < n; j++) {
				if (first[j] == 0)
					continue;
				if (first[j] < first[i])
					count++;
			}
		}
		return count % 2 == 0;
	}

	public static void shuffle(int[] arr) {
		Random rnd = ThreadLocalRandom.current();
		for (int i = 8; i > 0; i--) {
			int j = rnd.nextInt(i + 1);
			if (j != i) {
				arr[j] ^= arr[i];
				arr[i] ^= arr[j];
				arr[j] ^= arr[i];
			}
		}
	}

}
