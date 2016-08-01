package symbolTable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

public class SymbolTableBuilder {
	private TreeMap<Integer, ArrayList<String>> treemap = new TreeMap<Integer, ArrayList<String>>(
			Collections.reverseOrder());

	public SymbolTableBuilder() {

	}

	public void put(int line_no, ArrayList<String> symbol) {
		treemap.put(line_no, symbol);
	}

	public TreeMap<Integer, ArrayList<String>> get() {
		return this.treemap;
	}
}
