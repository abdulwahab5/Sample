package symbolTable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.script.ScriptException;

public class SymbolTableVersion10 {
	@SuppressWarnings("unused")
	public static void main(String args[]) throws IOException, ScriptException {

		String inputFile = "code.txt";
		String outputFile = "symbol.txt";
		PreProcessor preprocess = new PreProcessor(inputFile);
		CodeParser codeParser = new CodeParser(inputFile);
		TreeMap<Integer, ArrayList<String>> treemap = codeParser.getSymbolTable();
		SymbolTableWriter writer = new SymbolTableWriter(treemap, outputFile);
	}
}
