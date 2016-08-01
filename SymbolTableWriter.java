package symbolTable;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

public class SymbolTableWriter {
	public SymbolTableWriter(TreeMap<Integer, ArrayList<String>> treemap, String outputFile) throws IOException {
		Set<Entry<Integer, ArrayList<String>>> set = treemap.entrySet();
		Iterator<Entry<Integer, ArrayList<String>>> iterate = set.iterator();
		TreeMap<Integer, List<String>> treemap1 = new TreeMap<Integer, List<String>>();

		while (iterate.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry me = (Map.Entry) iterate.next();
			treemap1.put(Integer.parseInt(me.getKey().toString()), treemap.get(me.getKey()));
		}

		System.out.println("\nTreeMap Generated:\n" + treemap1);
		System.out.print("\nLine_no\t\tType\t\tVariable\tValue\t\tScope\t\tDeclared");
		List<String> display = new ArrayList<String>();
		Set<Entry<Integer, List<String>>> set1 = treemap1.entrySet();
		Iterator<Entry<Integer, List<String>>> iterate1 = set1.iterator();

		// Display elements
		File file = new File(outputFile);
		file.createNewFile();
		 PrintWriter writer=new PrintWriter(file);

		while (iterate1.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry me1 = (Map.Entry) iterate1.next();
			display = treemap1.get(me1.getKey());
			int a = 1;
			int b = 2;
			for (int l = 0; l < display.size(); l++) {
				if(display.get(1).equals("main()")){
					break;
				}
				if (l % 5 == 0) {
					System.out.println();
					System.out.print(me1.getKey());
				}
				 if(l==a || l==b){
				System.out.print("\t\t" + display.get(l)); }
				 if(l%5==0 && l!=0){
				 a+=5;b+=5;
				 }
			}
		}
		 writer.close();
	}

}
