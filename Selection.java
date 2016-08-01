package symbolTable;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.script.ScriptException;

public class Selection {
	BufferedReader bf;
	ArrayList<String> symbol;
	int line_no;
	public void setLineNo(int x){
		this.line_no=x;
	}
	public int getLineNo()
	{
		return this.line_no;
	}
	public ArrayList<String> containsIf(String expression, ArrayList<String> symbol, BufferedReader bf1,
			TreeMap<Integer, ArrayList<String>> treeMap, DeclareIntialize declareInit, Integer scope)
					throws ScriptException, IOException {
		bf = bf1;
		expression = expression.substring(expression.indexOf('('), expression.lastIndexOf(')') + 1);
		if (expression.contains("++") || expression.contains("--")) {
			symbol = declareInit.unary(expression, symbol, "if", expression, null, treeMap, scope);
		} else {
			symbol = declareInit.arithmetic(expression, symbol, "if", expression, null, treeMap, scope);
		}
		symbol.add(scope.toString());
		symbol.add("true");
		boolean check = false;
		for (int i = 0; i < symbol.size(); i += 5) {
			if (symbol.get(i).equals("if")) {
				if (symbol.get(i + 2).equals("true")) {
					check = true;
				}
			}
		}
		if (check == false)
			ignoreBlock(scope, bf);
		return symbol;
	}

	public ArrayList<String> containsIfelse(String expression, ArrayList<String> symbol, BufferedReader bf1,
			TreeMap<Integer, ArrayList<String>> treeMap, DeclareIntialize declareInit, Integer scope)
					throws ScriptException, IOException {
		if (checkPreviousIfs(treeMap, scope) == true) {
			bf = bf1;
			expression = expression.substring(expression.indexOf('('), expression.lastIndexOf(')') + 1);
			if (expression.contains("++") || expression.contains("--")) {
				symbol = declareInit.unary(expression, symbol, "else if", expression, null, treeMap, scope);
			} else {
				symbol = declareInit.arithmetic(expression, symbol, "else if", expression, null, treeMap, scope);
			}
			symbol.add(scope.toString());
			symbol.add("true");
			boolean check = false;
			for (int i = 0; i < symbol.size(); i += 5) {
				if (symbol.get(i).equals("else if")) {
					if (symbol.get(i + 2).equals("true")) {
						check = true;
					}
				}
			}

			if (check == false)
				ignoreBlock(scope, bf);
		} else {
			ignoreBlock(scope, bf);
		}
		return symbol;
	}

	public ArrayList<String> containsElse(String expression, ArrayList<String> symbol, BufferedReader bf1,
			TreeMap<Integer, ArrayList<String>> treeMap, DeclareIntialize declareInit, Integer scope)
					throws ScriptException, IOException {
		if (checkPreviousIfs(treeMap, scope) == true) {
		} else {
			ignoreBlock(scope, bf);
		}
		return symbol;
	}

	public boolean checkPreviousIfs(TreeMap<Integer, ArrayList<String>> treemap, Integer scope) {
		ArrayList<String> symbols = new ArrayList<String>();
		Set<Entry<Integer, ArrayList<String>>> set1 = treemap.entrySet();
		Iterator<Entry<Integer, ArrayList<String>>> iterate1 = set1.iterator();
		iterator1: while (iterate1.hasNext()) {
			@SuppressWarnings("rawthis.types")
			Map.Entry me1 = (Map.Entry) iterate1.next();

			symbols = treemap.get(me1.getKey());
			for (int m = 0; m < symbols.size(); m = m + 5) {
				if (symbols.get(m).equals("if") && symbols.get(m + 3).equals(scope.toString())) {

					if (symbols.get(m + 2).equals("true")) {
						return false;
					} else {
						return true;
					}
				} else if (symbols.get(m).equals("else if") && symbols.get(m + 3).equals(scope.toString())) {
					if (symbols.get(m + 2).equals("true")) {
						return false;
					}
				}
			}
		}
		return true;
	}

	public void ignoreBlock(Integer scope, BufferedReader bf) throws IOException {
		int ifScope = scope;
		String line = null;
		if ((line = bf.readLine()) != null) {
			line_no++;
			line = line.trim();
			if (line.equals("{")) {
				ifScope++;
				while (ifScope != scope) {
					line = bf.readLine();
					line_no++;
					line = line.trim();
					if (line.equals("{"))
						ifScope++;
					else if (line.equals("}"))
						ifScope--;
				}
			} else {
				if (!line.contains(";"))
					while (!(line = bf.readLine()).contains(";")) {
					line_no++;
					}
			}
		}
	}

}
