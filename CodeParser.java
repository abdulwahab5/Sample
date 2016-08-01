package symbolTable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.script.ScriptException;

public class CodeParser {
	String line;
	int line_no;
	int scope = 0;
	SymbolTableBuilder symbolTable = new SymbolTableBuilder();
	DeclareIntialize declareInit = new DeclareIntialize();
	Selection selection = new Selection();

	public CodeParser(String inputFile) throws ScriptException, IOException {

		File f = new File(inputFile);
		FileReader fl = new FileReader(f);
		BufferedReader bf = new BufferedReader(fl);
		// System.out.print("Line no. \t Datatype \t Variable \t Current
		// Value");
		while ((line = bf.readLine()) != null) {
			ArrayList<String> symbol = new ArrayList<String>();
			line = line.trim();
			line_no++;
			String delims = ";";
			String[] tokens = line.split(delims);
			for (int i = 0; i < tokens.length; i++) {
				if (tokens[i].startsWith(" ") == true) {
					tokens[i] = tokens[i].replaceFirst("[ ]+", "");
				}
				if (tokens[i].startsWith("{") == true) {
					scope++;
				}
				if (tokens[i].startsWith("}") == true) {
					scope--;
				}
				if (tokens[i].startsWith("string ") == true) {
					declareInit.setDeclared(true);
					symbol = declareInit.stringDeclaration(symbol, tokens[i], scope);
					symbolTable.put(line_no, symbol);
				}

				else if (tokens[i].startsWith("char ") == true) {
					declareInit.setDeclared(true);
					symbol = declareInit.charDeclaration(symbol, tokens[i], symbolTable.get(), scope);
					symbolTable.put(line_no, symbol);
				}

				else if (tokens[i].startsWith("int ") == true || tokens[i].startsWith("float ") == true) {
					declareInit.setDeclared(true);
					symbol = declareInit.intFloatDeclaration(symbol, tokens[i], symbolTable.get(), scope);
					symbolTable.put(line_no, symbol);
				}

				else if (tokens[i].startsWith("bool ") == true) {
					declareInit.setDeclared(true);
					symbol = declareInit.boolDeclaration(symbol, tokens[i], symbolTable.get(), scope);
					symbolTable.put(line_no, symbol);
				} 
				else if (tokens[i].startsWith("else if")) {
					selection.setLineNo(line_no);
					symbol = selection.containsIfelse(tokens[i], symbol, bf, symbolTable.get(), declareInit, scope);
					symbolTable.put(line_no, symbol);
					line_no=selection.getLineNo();
				} 
				else if (tokens[i].startsWith("if")) {
					selection.setLineNo(line_no);
					symbol = selection.containsIf(tokens[i], symbol, bf, symbolTable.get(), declareInit, scope);
					symbolTable.put(line_no, symbol);
					line_no=selection.getLineNo();
					
				} 
				else if (tokens[i].startsWith("else")) {
					selection.setLineNo(line_no);
					symbol = selection.containsElse(tokens[i], symbol, bf, symbolTable.get(), declareInit, scope);
					symbolTable.put(line_no, symbol);
					line_no=selection.getLineNo();
					
				} 
				else if (tokens[i].contains("=") == true) {
					declareInit.setDeclared(false);
					symbol = declareInit.expression(tokens[i], symbol, symbolTable.get(), scope);
					symbolTable.put(line_no, symbol);
					// the condition for checking "=" ends here
				} else if (tokens[i].contains("++") || tokens[i].contains("--")) {
					declareInit.setDeclared(false);
					symbol = declareInit.unary(tokens[i], symbol, "null", "null", symbol, symbolTable.get(), scope);
					symbolTable.put(line_no, symbol);
				}
				// the loop on line separated by ; ends here
			}
			// the loop on each line of file ends here
		}

		bf.close();
	}

	public TreeMap<Integer, ArrayList<String>> getSymbolTable() {
		return symbolTable.get();

	}
	
}
