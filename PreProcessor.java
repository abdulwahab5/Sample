package symbolTable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class PreProcessor {
	public PreProcessor(String inputFile) throws IOException {
		File f = new File(inputFile);
		FileReader fl = new FileReader(f);
		BufferedReader bf = new BufferedReader(fl);
		// System.out.print("Line no. \t Datatype \t Variable \t Current
		// Value");
		String line = null;
		String my = null;
		while ((line = bf.readLine()) != null) {
			line = line.trim();
			if (line.contains(";") && line.indexOf(';') != line.length() - 1) {
				line = line.replaceAll(";", ";\n");
			}
			if (line.contains("{") && line.indexOf('{') != 0) {
				line = line.replaceAll("\\{", "\n{");
			}
			if (line.contains("}") && line.indexOf('}') != 0) {
				line = line.replaceAll("\\}", "\n}");
			}
			if (line.contains("{") && line.indexOf('{') != line.length() - 1) {
				line = line.replaceAll("\\{", "{\n");
			}
			if (line.contains("}") && line.indexOf('}') != line.length() - 1) {
				line = line.replaceAll("\\}", "}\n");
			}

			if (line.contains("if")) {
				line=line.trim();
				int count = 0;
				int i;
				for (i = line.indexOf('('); i < line.length(); i++) {
					if (line.charAt(i) == '(') {
						count++;
					} else if (line.charAt(i) == ')') {
						count--;
					} else if (count == 0) {
						break;
					}
				}
				if(i != line.length() - 1)
				line = line.substring(0, i) + "\n" + line.substring(i, line.length());

			}
			if(line.contains("else ") && !line.contains("else if")){
				line=line.substring(0,line.indexOf("else")+4)+"\n"+line.substring(line.indexOf("else")+4,line.length());
			}

			line = line.trim();
			if (my == null) {
				my = line;
			} else {
				my = my + "\n" + line;
			}
		}
		bf.close();
		File file = new File(inputFile);
		file.createNewFile();
		PrintWriter writer = new PrintWriter(file);
		String lines[] = my.split("\\r?\\n");
		for (int i = 0; i < lines.length; i++) {
			lines[i]=lines[i].trim();
			if (!lines[i].equals("") &&!lines[i].equals(" "))
				writer.println(lines[i]);
		}
		writer.close();
	}
}
