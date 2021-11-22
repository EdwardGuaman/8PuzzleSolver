import java.util.Arrays;

public class problem {
	private int[] initialState;
	private int oindex;
//	possible actions 1 = up, 2 = right, 3 = down, 4 = left 
	private int[][] actions = { // index where zero is at
			{ 2, 3 }, // 0
			{ 2, 3, 4 }, // 1
			{ 3, 4 }, // 2
			{ 1, 2, 3 }, // 3
			{ 1, 2, 3, 4 }, // 4
			{ 1, 3, 4 }, // 5
			{ 1, 2 }, // 6
			{ 1, 2, 4 }, // 7
			{ 1, 4 } // 8
	};

	public problem(int[] start) {
		initialState = Arrays.copyOf(start, start.length);
		for (int i = 0; i < start.length; i++) {
			if (start[i] == 0) {
				oindex = i;
				break;
			}
		}
	}

	public int[] getInitialState() {
		return initialState;
	}

	public void setInitialState(int[] initialState) {
		this.initialState = initialState;
	}

	public boolean goalTest(int[] state) {
		for (int i = 0; i < state.length; i++) {
			if (state[i] != i)
				return false;
		}
		return true;
	}

	public int[][] getActions() {
		return actions;
	}

	public int getOindex() {
		return oindex;
	}

	public void setOindex(int oindex) {
		this.oindex = oindex;
	}

	public void setActions(int[][] actions) {
		this.actions = actions;
	}

	/**
	 * @param state
	 * @param action
	 * @param o
	 * @return
	 */
	public int tmodel(int[] state, int action, int o) {
		if (action == 1) {
			oindex = o - 3;
			swap(state, o, oindex);
			return oindex;
		} else if (action == 2) {
			oindex = o + 1;
			swap(state, o, oindex);
			return oindex;
		} else if (action == 3) {
			oindex = o + 3;
			swap(state, o, oindex);
			return oindex;
		} else {
			oindex = o - 1;
			swap(state, o, oindex);
			return oindex;
		}
	}

	public void swap(int[] s, int a, int b) {
		s[a] ^= s[b];
		s[b] ^= s[a];
		s[a] ^= s[b];
	}

}
