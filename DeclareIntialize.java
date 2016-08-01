package symbolTable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.lang3.math.NumberUtils;

public class DeclareIntialize {
	String type = null;
	String name = null;
	Boolean declared = null;
	String value = null;

	public ArrayList<String> intFloatDeclaration(ArrayList<String> symbol, String tokens,
			TreeMap<Integer, ArrayList<String>> treemap, Integer scope) throws ScriptException {

		this.type = null;
		this.name = null;
		String line1[] = null;
		String line2[] = null;
		String line3[] = null;
		line1 = tokens.split("[ ]+", 2);
		line1[0]=line1[0].trim();
		line1[1] = line1[1].replaceAll("[ ]+", "");
		line2 = line1[1].split(",");

		for (int k = 0; k < line2.length; k++) { // System.out.print("\n"+line2[k]);
			// System.out.print("\n"+line_no+"\t\t ");
			this.type = line1[0];
			// System.out.print(line1[0]+"\t\t ");
			if (line2[k].contains("=") == true) {

				line3 = line2[k].split("=");
				for (int l = 0; l < line3.length; l++) {
					if (l != line3.length - 1) {
						this.name = line3[l];
					} else {
						if (NumberUtils.isParsable(line3[l])) {
							if (line1[0].equals("int")) {
								Integer valu = (int) Double.parseDouble(line3[l]);
								this.value = valu.toString();
								symbol.add(this.type);
								symbol.add(this.name);
								symbol.add(this.value);
							} else if (line1[0].equals("float")) {

								Float valu = (float) Double.parseDouble(line3[l]);
								this.value = valu.toString();
								symbol.add(this.type);
								symbol.add(this.name);
								symbol.add(this.value);
							}

						} else {
							if (line3[l].contains("++") || line3[l].contains("--")) {
								symbol = unary(line3[l], symbol, line1[0], name, null, treemap, scope);
							} else {
								symbol = arithmetic(line3[l], symbol, line1[0], name, null, treemap, scope);
							}
						}
					}
					// System.out.print(line3[l]+"\t\t ");
				}
			} else {
				symbol.add(this.type);
				symbol.add(line2[k]);
				symbol.add(null);
				// System.out.print(line2[k]+"\t\t NULL");
			}
			symbol.add(scope.toString());
			symbol.add(this.declared.toString());
		}
		return symbol;
	}

	public ArrayList<String> charDeclaration(ArrayList<String> symbol, String tokens,
			TreeMap<Integer, ArrayList<String>> treemap, Integer scope) throws ScriptException {
		this.name = null;
		this.type = null;
		String line1[] = null;
		String line2[] = null;
		String line3[] = null;
		tokens = tokens.trim();
		line1 = tokens.split("[ ]+", 2);
		
		line2 = line1[1].split(",");

		for (int k = 0; k < line2.length; k++) { // System.out.print("\n"+line2[k]);
			line2[k] = line2[k].trim();
			line1[0] = line1[0].trim();
			this.type = line1[0];

			if (line2[k].contains("=") == true) {
				if (line2[k].contains("+=") || line2[k].contains("-=") || line2[k].contains("*=")
						|| line2[k].contains("/=") || line2[k].contains("%=")) {
					line2[k] = opEqual(line2[k]);
				}
				line3 = line2[k].split("=");
				for (int l = 0; l < line3.length; l++) {
					line3[l] = line3[l].trim();
					if (l != line3.length - 1) {

						this.name = line3[l];
					} else {

						if (NumberUtils.isDigits(line3[l])) {
							Character valu = (char) Integer.parseInt(line3[l]);
							this.value = valu.toString();
							symbol.add(this.type);
							symbol.add(this.name);
							symbol.add(this.value);
						} else if (line3[l].contains("\'")) {
							line3[l] = line3[l].substring(0, line3[l].indexOf('\'')).replaceAll("[ ]+", "")
									+ line3[l].substring(line3[l].indexOf('\''));
							line3[l] = line3[l].replaceAll("\'", "");
							symbol.add(this.type);
							symbol.add(this.name);
							symbol.add(line3[l]);
						} else {
							if (line3[l].contains("++") || line3[l].contains("--")) {
								symbol = unary(line3[l], symbol, line1[0], this.type, null, treemap, scope);
							} else {
								symbol = arithmetic(line3[l], symbol, line1[0], this.name, null, treemap, scope);
							}
						}
					}
				}
			} else {
				symbol.add(this.type);
				symbol.add(line2[k]);
				symbol.add(null);
				// System.out.print(line2[k]+"\t\t NULL");
			}
			symbol.add(scope.toString());
			symbol.add(this.declared.toString());
		}

		return symbol;
	}

	public ArrayList<String> stringDeclaration(ArrayList<String> symbol, String tokens, Integer scope) {
		String line1[] = null;
		String line2[] = null;
		String line3[] = null;

		line1 = tokens.split("[ ]+", 2);
		line1[0]=line1[0].trim();
		line1[1]=line1[1].trim();
		if (line1[1].contains("\"")) {
			line1[1] = line1[1].substring(0, line1[1].indexOf('\"')).replaceAll("[ ]+", "")
					+ line1[1].substring(line1[1].indexOf('\"'));
			line1[1] = line1[1].replaceAll("\"", "");
		} else
			line1[1].replaceAll("[ ]+", "");
		line2 = line1[1].split(",");

		for (int k = 0; k < line2.length; k++) {
			line2[k] = line2[k].trim();
			if (line2[k].contains("\'")) {
				line2[k] = line2[k].substring(0, line2[k].indexOf('\"')).replaceAll("[ ]+", "")
						+ line2[k].substring(line2[k].indexOf('\"'));
				line2[k] = line2[k].replaceAll("\"", "");
			} else {
				line2[k].replaceAll("[ ]+", "");
			} // System.out.print("\n"+line2[k]);
				// System.out.print("\n"+line_no+"\t\t ");
			symbol.add(line1[0]);
			// System.out.print(line1[0]+"\t\t ");
			if (line2[k].contains("=") == true) {
				line3 = line2[k].split("=");
				for (int l = 0; l < line3.length; l++) {
					symbol.add(line3[l]);
					// System.out.print(line3[l]+"\t\t ");
				}
			} else {
				symbol.add(line2[k]);
				symbol.add(null);
				// System.out.print(line2[k]+"\t\t NULL");
			}
			symbol.add(scope.toString());
			symbol.add(this.declared.toString());
		}
		return symbol;
	}

	public ArrayList<String> expression(String tokens, ArrayList<String> symbol,
			TreeMap<Integer, ArrayList<String>> treemap, Integer scope) throws ScriptException {
		String line1[] = null;
		if (tokens.contains("+=") || tokens.contains("-=") || tokens.contains("*=") || tokens.contains("/=")
				|| tokens.contains("%=")) {
			tokens = opEqual(tokens);
		}
		line1 = tokens.split("=");
		this.declared = false;
		List<String> previous = new ArrayList<String>();
		this.type = null;
		int last = line1.length - 1;
		line1[last] = line1[last].trim();
		for (int j = 0; j < line1.length - 1; j++) {
			line1[j] = line1[j].trim();
			Set<Entry<Integer, ArrayList<String>>> set = treemap.entrySet();
			Iterator<Entry<Integer, ArrayList<String>>> iterate = set.iterator();
			// Display elements
			iterator: while (iterate.hasNext()) {
				@SuppressWarnings("rawthis.types")
				Map.Entry me = (Map.Entry) iterate.next();

				previous = treemap.get(me.getKey());

				for (int l = 1; l < previous.size(); l = l + 5) {

					if (line1[j].equals(previous.get(l))) {

						if (scope >= Integer.parseInt(previous.get(l + 2))) {
							this.type = previous.get(l - 1);
							this.name = previous.get(l);

							if (line1[last].startsWith("\"") == true) {
								line1[last] = line1[last].replaceFirst("\"", "");
								line1[last] = line1[last].substring(0, line1[last].lastIndexOf('\"'));
								symbol.add(this.type);
								symbol.add(this.name);
								symbol.add(line1[last]);
								line1[last] = "\"" + line1[last] + "\"";
							} else if (line1[last].startsWith("\'") == true) {
								line1[last] = line1[last].replaceFirst("\'", "");
								line1[last] = line1[last].substring(0, line1[last].lastIndexOf('\''));
								symbol.add(this.type);
								symbol.add(this.name);
								symbol.add(line1[last]);
								line1[last] = "\'" + line1[last] + "\'";
							} else if (NumberUtils.isParsable(line1[last])) {
								if (this.type.equals("int")) {
									// System.out.println("i am here");

									Integer valu = (int) Double.parseDouble(line1[last]);
									this.value = valu.toString();
									symbol.add(this.type);
									symbol.add(this.name);
									symbol.add(this.value);
								} else if (this.type.equals("float")) {
									Float valu = (float) Double.parseDouble(line1[last]);
									this.value = valu.toString();
									symbol.add(this.type);
									symbol.add(this.name);
									symbol.add(this.value);
								} else if (this.type.equals("char")) {
									Character valu = (char) Integer.parseInt(line1[last]);
									this.value = valu.toString();
									symbol.add(this.type);
									symbol.add(this.name);
									symbol.add(this.value);
								} else if (this.type.equals("bool")) {
									if (line1[last].equals("0")) {
										this.value = "false";

									} else {
										this.value = "true";
									}
									symbol.add(this.type);
									symbol.add(this.name);
									symbol.add(this.value);
								}
							} else if (this.type.equals("bool")
									&& (line1[last].equals("true") || line1[last].equals("false"))) {
								if (line1[last].equals("true")) {
									this.value = "true";

								} else {
									this.value = "false";
								}
								symbol.add(this.type);
								symbol.add(this.name);
								symbol.add(this.value);
							}

							else {
								line1[last] = line1[last].trim();
								if (!line1[last].contains("+") && !line1[last].contains("-")
										&& !line1[last].contains("/") && !line1[last].contains("%")
										&& !line1[last].contains("*") && !line1[last].contains("(")
										&& !line1[last].contains(")") && !line1[last].contains(">")
										&& !line1[last].contains("<") && !line1[last].contains("=")
										&& !line1[last].contains("!") && !line1[last].contains("&")
										&& !line1[last].contains("|")) {
									Set<Entry<Integer, ArrayList<String>>> set1 = treemap.entrySet();
									Iterator<Entry<Integer, ArrayList<String>>> iterate1 = set1.iterator();
									iterator1: while (iterate1.hasNext()) {
										Map.Entry me1 = (Map.Entry) iterate1.next();

										previous = treemap.get(me1.getKey());
										for (int m = 1; m < previous.size(); m = m + 5) {
											if (line1[last].equals(previous.get(m))) {
												if (scope >= Integer.parseInt(previous.get(m + 2))) {
													symbol.add(this.type);
													symbol.add(this.name);
													symbol.add(previous.get(m + 1));

													m = previous.size() + 1;
													break iterator1;

												}

											}

										}
									}
								} else {
									if (line1[last].contains("++") || line1[last].contains("--")) {
										symbol = unary(line1[last], symbol, this.type, this.name, previous, treemap,
												scope);
									} else {
										symbol = arithmetic(line1[last], symbol, this.type, this.name, previous,
												treemap, scope);
									}
								}
							}
							symbol.add(previous.get(l + 2));
							symbol.add(this.declared.toString());

							break iterator;

						}

					}

				}

			}

		}

		return (ArrayList<String>) symbol;

	}

	public ArrayList<String> unary(String expression, ArrayList<String> symbol, String typ, String nam,
			List<String> previous, TreeMap<Integer, ArrayList<String>> treemap, Integer scope) throws ScriptException {
		this.type = typ;
		this.name = nam;

		while (expression.contains("++") || expression.contains("--")) {
			String unary = null;
			if (expression.contains("++")) {
				unary = "++";
			} else if (expression.contains("--")) {
				unary = "--";
			}
			int i = expression.indexOf(unary);
			// post unary
			if (i + 2 == expression.length() || (i + 2 < expression.length()
					&& (expression.charAt(i + 2) == '+' || expression.charAt(i + 2) == '-'
							|| expression.charAt(i + 2) == '*' || expression.charAt(i + 2) == '/'
							|| expression.charAt(i + 2) == '(' || expression.charAt(i + 2) == ')'
							|| expression.charAt(i + 2) == '<' || expression.charAt(i + 2) == '>')
					|| expression.charAt(i + 2) == '=' || expression.charAt(i + 2) == '!'
					|| expression.charAt(i + 2) == '&' || expression.charAt(i + 2) == '|')) {
				int j;
				for (j = i - 1; j >= 0 && expression.charAt(j) != '+' && expression.charAt(j) != '-'
						&& expression.charAt(j) != '*' && expression.charAt(j) != '/' && expression.charAt(j) != '('
						&& expression.charAt(j) != ')' && expression.charAt(j) != '>' && expression.charAt(j) != '<'
						&& expression.charAt(j) != '=' && expression.charAt(j) != '!' && expression.charAt(j) != '&'
						&& expression.charAt(j) != '|'; j--) {
				}
				Set<Entry<Integer, ArrayList<String>>> set1 = treemap.entrySet();
				Iterator<Entry<Integer, ArrayList<String>>> iterate1 = set1.iterator();
				iterator1: while (iterate1.hasNext()) {
					@SuppressWarnings("rawthis.types")
					Map.Entry me1 = (Map.Entry) iterate1.next();

					previous = treemap.get(me1.getKey());
					// System.out.println(expression+"here it is");
					for (int m = 1; m < previous.size(); m = m + 5) { // System.out.println(previous.get(l)+"comes
						// here12");
						if (!expression.substring(j + 1, i).equals("true")
								&& !expression.substring(j + 1, i).equals("false")) {
							if (expression.substring(j + 1, i).equals(previous.get(m))) { // System.out.println("comes
								// here13");
								if (scope >= Integer.parseInt(previous.get(m + 2))) {
									// System.out.println("\nReached Hereas
									// well");

									expression = expression.replaceFirst(expression.substring(j + 1, i + 2),
											previous.get(m + 1));
									String Value = null;
									if (unary.equals("++")) {
										expression = expression.replaceFirst("\\+\\+", "");
										if (previous.get(m - 1).equals("int")) {
											Integer valu = Integer.parseInt(previous.get(m + 1)) + 1;
											this.value = valu.toString();

										}

										else if (previous.get(m - 1).equals("float")) {
											Float valu = Float.parseFloat(previous.get(m + 1)) + 1;
											this.value = valu.toString();

										}

										else if (previous.get(m - 1).equals("char")) {
											Character valu = (char) (Integer.parseInt(previous.get(m + 1)) + 1);
											this.value = valu.toString();

										}
										symbol.add(previous.get(m - 1));
										symbol.add(previous.get(m));
										symbol.add(this.value);
										symbol.add(previous.get(m + 2));
										symbol.add(this.declared.toString());

									} else if (unary.equals("--")) {
										expression = expression.replaceFirst("\\-\\-", "");
										if (previous.get(m - 1).equals("int")) {
											Integer valu = Integer.parseInt(previous.get(m + 1)) - 1;
											this.value = valu.toString();
										}

										else if (previous.get(m - 1).equals("float")) {
											Float valu = Float.parseFloat(previous.get(m + 1)) - 1;
											this.value = valu.toString();
										}

										else if (previous.get(m - 1).equals("char")) {
											Character valu = (char) (Integer.parseInt(previous.get(m + 1)) - 1);
											this.value = valu.toString();
										}

										symbol.add(previous.get(m - 1));
										symbol.add(previous.get(m));
										symbol.add(this.value);
										symbol.add(previous.get(m + 2));
										symbol.add(this.declared.toString());

									}
								}
								m = previous.size() + 1;
								break iterator1;

							}

						}
					}
				}
			} // pre-unary
			else if (i == 0
					|| (i - 1 >= 0 && expression.charAt(i - 1) == '+' || expression.charAt(i - 1) == '-'
							|| expression.charAt(i - 1) == '*' || expression.charAt(i - 1) == '/'
							|| expression.charAt(i - 1) == '(' || expression.charAt(i - 1) == ')'
							|| expression.charAt(i - 1) == '>' || expression.charAt(i - 1) == '<')
					|| expression.charAt(i - 1) == '=' || expression.charAt(i - 1) == '!'
					|| expression.charAt(i - 1) == '&' || expression.charAt(i - 1) == '|') {
				int j;
				for (j = i + 2; j < expression.length() && expression.charAt(j) != '+' && expression.charAt(j) != '-'
						&& expression.charAt(j) != '*' && expression.charAt(j) != '/' && expression.charAt(j) != '('
						&& expression.charAt(j) != ')' && expression.charAt(j) != '>' && expression.charAt(j) != '<'
						&& expression.charAt(j) != '=' && expression.charAt(j) != '!' && expression.charAt(j) != '&'
						&& expression.charAt(j) != '|'; j++) {
				}
				Set<Entry<Integer, ArrayList<String>>> set1 = treemap.entrySet();
				Iterator<Entry<Integer, ArrayList<String>>> iterate1 = set1.iterator();
				iterator1: while (iterate1.hasNext()) {
					@SuppressWarnings("rawthis.types")
					Map.Entry me1 = (Map.Entry) iterate1.next();

					previous = treemap.get(me1.getKey());
					// System.out.println(expression+"here it is");
					for (int m = 1; m < previous.size(); m = m + 5) { // System.out.println(previous.get(l)+"comes
						// here12");
						if (!expression.substring(i + 2, j).equals("true")
								&& !expression.substring(i + 2, j).equals("true")) {
							if (expression.substring(i + 2, j).equals(previous.get(m))) { // System.out.println("comes
								// here13");
								if (scope >= Integer.parseInt(previous.get(m + 2))) {
									// System.out.println("\nReached Hereas
									// well");

									if (unary.equals("++")) {
										expression = expression.replaceFirst("\\+\\+", "");
										if (previous.get(m - 1).equals("int")) {
											Integer valu = Integer.parseInt(previous.get(m + 1)) + 1;
											this.value = valu.toString();
										}

										else if (previous.get(m - 1).equals("float")) {
											Float valu = Float.parseFloat(previous.get(m + 1)) + 1;
											previous.set(m + 1, valu.toString());
											this.value = valu.toString();
										}

										if (previous.get(m - 1).equals("char")) {
											Character valu = (char) (Integer.parseInt(previous.get(m + 1)) + 1);
											this.value = valu.toString();
										}

									} else if (unary.equals("--")) {
										expression = expression.replaceFirst("\\-\\-", "");
										if (previous.get(m - 1).equals("int")) {
											Integer valu = Integer.parseInt(previous.get(m + 1)) - 1;
											this.value = valu.toString();
										}

										else if (previous.get(m - 1).equals("float")) {
											Float valu = Float.parseFloat(previous.get(m + 1)) - 1;
											this.value = valu.toString();
										}

										if (previous.get(m - 1).equals("char")) {
											Character valu = (char) (Integer.parseInt(previous.get(m + 1)) - 1);
											this.value = valu.toString();
										}
										expression = expression.replaceFirst(expression.substring(i, j - 2),
												this.value);

									}
									symbol.add(previous.get(m - 1));
									symbol.add(previous.get(m));
									symbol.add(this.value);
									symbol.add(previous.get(m + 2));
									symbol.add(this.declared.toString());
									m = previous.size() + 1;
									break iterator1;

								}
							}
						}
					}
				}
			}
		}

		symbol = arithmetic(expression, symbol, this.type, this.name, previous, treemap, scope);

		return symbol;
	}

	private String opEqual(String tokens) {
		Character operator = tokens.charAt(tokens.indexOf('=') - 1);

		String variable = tokens.substring(0, tokens.indexOf(operator));
		variable = variable.replaceAll("[ ]+", "");
		tokens = tokens.replace(operator.toString() + "=", "=" + variable + operator.toString());
		return tokens;
	}

	public ArrayList<String> arithmetic(String expression, ArrayList<String> symbol, String type, String name,

			List<String> previous, TreeMap<Integer, ArrayList<String>> treemap, Integer scope) throws ScriptException {
		expression = expression.trim();
		String b = "@#$";
		for (int n = 0; n < (expression.length()); n++) {
			if (expression.charAt(n) != ' ' && expression.charAt(n) != '(' && expression.charAt(n) != ')'
					&& expression.charAt(n) != '+' && expression.charAt(n) != '-' && expression.charAt(n) != '*'
					&& expression.charAt(n) != '/' && expression.charAt(n) != '%' && expression.charAt(n) != '.'
					&& expression.charAt(n) != '>' && expression.charAt(n) != '<' && expression.charAt(n) != '='
					&& expression.charAt(n) != '!' && expression.charAt(n) != '&' && expression.charAt(n) != '|'
					&& !NumberUtils.isDigits(expression.substring(n, n + 1))) {
				int o;
				for (o = n; o < expression.length() && expression.charAt(o) != ' ' && expression.charAt(o) != '('
						&& expression.charAt(o) != ')' && expression.charAt(o) != '+' && expression.charAt(o) != '-'
						&& expression.charAt(o) != '*' && expression.charAt(o) != '/' && expression.charAt(o) != '%'
						&& expression.charAt(o) != '.' && expression.charAt(o) != '>' && expression.charAt(o) != '<'
						&& expression.charAt(o) != '=' && expression.charAt(o) != '!' && expression.charAt(o) != '&'
						&& expression.charAt(o) != '|'; o++) {
				}
				
				if (!expression.substring(n, o).equals("true") && !expression.equals("false")) {
					Set<Entry<Integer, ArrayList<String>>> set1 = treemap.entrySet();
					Iterator<Entry<Integer, ArrayList<String>>> iterate1 = set1.iterator();
					iterator1: while (iterate1.hasNext()) {
						@SuppressWarnings("rawtypes")
						Map.Entry me1 = (Map.Entry) iterate1.next();
						previous = treemap.get(me1.getKey());
						// System.out.println(expression+"here it is");
						for (int m = 1; m < previous.size(); m = m + 5) { // System.out.println(previous.get(l)+"comes
							// here12");
							if (expression.substring(n, o).equals(previous.get(m))) { // System.out.println("comes
								// here13");
								if (scope >= Integer.parseInt(previous.get(m + 2))) {
									// System.out.println("\nReached Hereas
									// well");
									if (previous.get(m - 1).equals("char")) {
										char character = previous.get(m + 1).charAt(0);
										Integer characterValue = (int) character;
										expression = expression.replace(expression.substring(n, o), b);
										expression = expression.replace(b, characterValue.toString());
									} else {

										expression = expression.replace(expression.substring(n, o), b);
										expression = expression.replace(b, previous.get(m + 1));
									}
								}
								n = o - 1;
								m = previous.size() + 1;
								break iterator1;

							}

						}

					}
				}
			}
		}
		while(expression.contains("/")){
			expression=intDivision(expression);
		}
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("JavaScript");
		String foo = expression;
		
		String result = engine.eval(foo).toString();
		if (type.equals("bool") || type.equals("if") || type.equals("else if")) {
			Boolean value = (Boolean) engine.eval(foo);
			symbol.add(type);
			symbol.add(name);
			symbol.add(value.toString());
		} else if (type.equals("int")) {
			// System.out.println("i am here");

			Integer value = (int) Double.parseDouble(result);
			symbol.add(type);
			symbol.add(name);
			symbol.add(value.toString());
		} else if (type.equals("float")) {
			Float value = (float) Double.parseDouble(result);
			symbol.add(type);
			symbol.add(name);
			symbol.add(value.toString());
		} else if (type.equals("char")) {
			Character value = (char) Double.parseDouble(result);
			symbol.add(type);
			symbol.add(name);
			symbol.add(value.toString());
		}
		return symbol;
	}

	public ArrayList<String> boolDeclaration(ArrayList<String> symbol, String tokens,
			TreeMap<Integer, ArrayList<String>> treemap, Integer scope) throws ScriptException {
		this.type = null;
		this.name = null;
		String line1[] = null;
		String line2[] = null;
		String line3[] = null;
		line1 = tokens.split("[ ]+", 2);
		line1[0]=line1[0].trim();
		line1[1] = line1[1].replaceAll("[ ]+", "");
		line2 = line1[1].split(",");

		for (int k = 0; k < line2.length; k++) { // System.out.print("\n"+line2[k]);
			// System.out.print("\n"+line_no+"\t\t ");
			this.type = line1[0];
			// System.out.print(line1[0]+"\t\t ");
			if (line2[k].contains("=") == true) {

				line3 = line2[k].split("=");
				for (int l = 0; l < line3.length; l++) {
					line3[l] = line3[l].trim();
					if (l != line3.length - 1) {
						this.name = line3[l];
					} else {
						if (NumberUtils.isParsable(line3[l])) {
							if (line3[l].equals("0")) {
								this.value = "false";

							} else {
								this.value = "true";
							}
							symbol.add(this.type);
							symbol.add(this.name);
							symbol.add(this.value);

						} else if (line3[l].equals("true") || line3[l].equals("false")) {
							if (line3[l].equals("true")) {
								this.value = "true";

							} else {
								this.value = "false";
							}
							symbol.add(this.type);
							symbol.add(this.name);
							symbol.add(this.value);

						} else {
							if (line3[l].contains("++") || line3[l].contains("--")) {
								symbol = unary(line3[l], symbol, line1[0], this.name, null, treemap, scope);

							} else {
								symbol = arithmetic(line3[l], symbol, line1[0], this.name, null, treemap, scope);
							}
						}
					}
					// System.out.print(line3[l]+"\t\t ");
				}
			} else {
				symbol.add(this.type);
				symbol.add(line2[k]);
				symbol.add(null);
				// System.out.print(line2[k]+"\t\t NULL");
			}
			symbol.add(scope.toString());
			symbol.add(this.declared.toString());
		}
		return symbol;
	}

	public void setDeclared(boolean declare) {
		this.declared = declare;
	}

	public Boolean getDeclared() {
		return this.declared;
	}
	public String intDivision(String expression) throws ScriptException{
		int x=expression.indexOf('/');
		Character div=expression.charAt(x-1);
		boolean Float=false;
		int y=x-1;
		if(NumberUtils.isDigits(div.toString())){
			
			while(y>0){
				div=expression.charAt(y);
				if(NumberUtils.isDigits(div.toString())){
					y--;
				}
				else if(div=='.'){
					Float=true;
					y--;
				}
				else{
					break;
				}
			}
		}
		else {
			int count=0;
			while(y>0){
				
				div=expression.charAt(y);
				if(div==')'){
					count++;
					y--;
				}
				else if(div=='('){
					count--;
					if(count==0){
						break;
					}
					y--;

					
				}

				else{
					y--;
				}
			}
		}
		String divLeft=expression.substring(y,x);
		div=expression.charAt(x+1);
		Float=false;
		y=x+1;
		if(NumberUtils.isDigits(div.toString())){
			
			while(y<expression.length()){
				div=expression.charAt(y);
				if(NumberUtils.isDigits(div.toString())){
					y++;
				}
				else if(div=='.'){
					Float=true;
					y++;
				}
				else{
					break;
				}
			}
		}
		else {
			int count=0;
			while(y<expression.length()){
				
				div=expression.charAt(y);
				if(div=='('){
					count++;
					y++;
				}
				else if(div==')'){
					count--;
					y++;
					if(count==0){
						break;
					}

				}

				else{
					y++;
				}
			}
		}
		String divRight=expression.substring(x+1,y);
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("JavaScript");
		String division=divLeft+"/"+divRight;
		divLeft = engine.eval(divLeft).toString();
		divRight= engine.eval(divRight).toString();
		String result=null;
		if(!divRight.contains(".")&&!divLeft.contains(".")){
			Integer intDivision=Integer.parseInt(divLeft)/Integer.parseInt(divRight);
			result=intDivision.toString();
		}
		else{
			result=engine.eval(divLeft+"/"+divRight).toString();
		}
		expression=expression.replace(division, result);
		
		return expression;
		
	}
}
