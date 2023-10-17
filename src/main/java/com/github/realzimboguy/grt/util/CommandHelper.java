package com.github.realzimboguy.grt.util;

import com.github.realzimboguy.grt.model.DisplayField;
import com.github.realzimboguy.grt.model.Report;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CommandHelper {

	public static String searchFileForContentHead(String path, String file, int headSize) {

		ProcessBuilder processBuilder = new ProcessBuilder();

		// -- Linux --

		// Run a shell command
		processBuilder.command("/bin/bash", "-c", "ls / | head -n" + headSize + " | grep oo");

		// Run a shell script
		//processBuilder.command("path/to/hello.sh");

		// -- Windows --

		// Run a command
		//processBuilder.command("cmd.exe", "/c", "dir C:\\Users\\mkyong");

		// Run a bat file
		//processBuilder.command("C:\\Users\\mkyong\\hello.bat");

		try {

			Process process = processBuilder.start();

			StringBuilder output = new StringBuilder();

			inheritIO(process.getInputStream(), System.out);
			inheritIO(process.getErrorStream(), System.err);

//            BufferedReader reader = new BufferedReader(
//                    new InputStreamReader(process.getInputStream()));
//
//            BufferedReader readerErr = new BufferedReader(
//                    new InputStreamReader(process.getErrorStream()));
//
//            String line;
//            while ((line = reader.readLine()) != null) {
//                output.append(line + "\n");
//            }

			int exitVal = process.waitFor();
			if (exitVal == 0) {
				System.out.println("Success!");
				System.out.println(output);
				System.exit(0);
			} else {
				System.out.println("ERROR!");
				//abnormal...
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String searchFileForContent(String path, String file, int headSize) {

		ProcessBuilder processBuilder = new ProcessBuilder();

		// -- Linux --

		// Run a shell command
		processBuilder.command("/bin/bash", "-c", "ls / | head -n" + headSize + " | grep oo");

		// Run a shell script
		//processBuilder.command("path/to/hello.sh");

		// -- Windows --

		// Run a command
		//processBuilder.command("cmd.exe", "/c", "dir C:\\Users\\mkyong");

		// Run a bat file
		//processBuilder.command("C:\\Users\\mkyong\\hello.bat");

		try {

			Process process = processBuilder.start();

			StringBuilder output = new StringBuilder();

			inheritIO(process.getInputStream(), System.out);
			inheritIO(process.getErrorStream(), System.err);

//            BufferedReader reader = new BufferedReader(
//                    new InputStreamReader(process.getInputStream()));
//
//            BufferedReader readerErr = new BufferedReader(
//                    new InputStreamReader(process.getErrorStream()));
//
//            String line;
//            while ((line = reader.readLine()) != null) {
//                output.append(line + "\n");
//            }

			int exitVal = process.waitFor();
			if (exitVal == 0) {
				System.out.println("Success!");
				System.out.println(output);
				System.exit(0);
			} else {
				System.out.println("ERROR!");
				//abnormal...
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return null;
	}


	private static void inheritIO(final InputStream src, final PrintStream dest) {

		new Thread(new Runnable() {
			public void run() {

				Scanner sc = new Scanner(src);
				while (sc.hasNextLine()) {
					dest.println(sc.nextLine());
				}
			}
		}).start();
	}

	public static String getAwkCommand(Report report, List<DisplayField> displayFields) {
		//awk 'match($0,/Country":"([^"]{1,})"/,a) {printf a[1]};match($0,/Total Revenue":"([^"]{1,})"/,b) {printf "\t" b[1]}; {print ""} '

		StringBuilder sb = new StringBuilder();
		String prefix = "awk -M -v PREC=100 '";
		String suffix = "; {print \"\"} '";
		List<String> vars = new ArrayList<String>(Arrays.asList("a", "b", "c", "d", "e", "f", "g"));
		List<String> usedVars = new ArrayList<>();

		if (report.getReportDisplayType() == Report.ReportDisplayType.FIELDS_ONLY) {
			sb.append(prefix);
			for (DisplayField displayField : displayFields) {
				String variable = vars.get(0);
				vars.remove(0);
				sb.append("match($0,/");
				sb.append(displayField.getRegex());
				sb.append("/,");
				sb.append(variable);
				sb.append(") {printf ");
				sb.append(variable);
				sb.append("[1] \"\\t\"} ");
			}
			sb.append(suffix);
		}
		if (report.getReportDisplayType() == Report.ReportDisplayType.FIELDS_AND_SUM) {
			sb.append(prefix);
			String prefixKey = "";
//            for (DisplayField displayField : displayFields) {
//                if (displayField.getFieldOptions().equalsIgnoreCase("JSON_STRING")){
//                    prefixKey = prefixKey + displayField.getFieldOptions();
//                }
//            }

			//match($0,/payee":"([^"]{5,})"/,a){count[a[1]]++}

			for (DisplayField displayField : displayFields) {
				if (displayField.getFieldOptions().equalsIgnoreCase("JSON_STRING")) {
					//match($0,/payee":"([^"]{5,})"/,a){count[a[1]]++};
					String variable = vars.get(0);
					usedVars.add(variable);
					vars.remove(0);
					sb.append("match($0,/");
					sb.append(displayField.getRegex());
					sb.append("/,");
					sb.append(variable);
					sb.append(")");
				}
			}

			//aggregate the json strings to create a key used for grouping on
			sb.append("{agg[");
			for (String usedVar : usedVars) {
				sb.append(usedVar);
				sb.append("[1]");
				sb.append("\"\\t\"");
			}
			sb.append("]=");
			for (String usedVar : usedVars) {
				sb.append(usedVar);
				sb.append("[1]");
				sb.append("\"\\t\"");
			}
			sb.append(" ;var=");
			for (String usedVar : usedVars) {
				sb.append(usedVar);
				sb.append("[1]");
				sb.append("\"\\t\"");
			}

			sb.append("} ; {");

			for (DisplayField displayField : displayFields) {
				if (displayField.getFieldOptions().equalsIgnoreCase("SUM")) {
					String variable = vars.get(0);
//                    usedVars.add(variable);
					vars.remove(0);
					sb.append("match($0,/");
					sb.append(displayField.getRegex());
					sb.append("/,");
					sb.append(variable);
					sb.append(");");
					sb.append(" agg_sum[");
					sb.append("var");
					sb.append("\"" + displayField.getName() + "\"");
					sb.append("]+=");
					sb.append(variable);
					sb.append("[1]");
				}
			}

			// cat *.gz | zgrep "" | awk  -M 'match($0,/Country":"([^"]{1,})"/,a)match($0,/Region":"([^"]{1,})"/,b){agg[a[1]b[1]]=a[1]"*"b[1]"*" ;var=a[1]"*"b[1]"*"} ; {match($0,/Total Revenue":"([^"]{1,})"/,c)
			//                                agg_sum[var"Total Revenue"]+=c[1]; print var c[1]} END {OFMT="%.10f"; for (var in agg_sum) printf "%s %.3f\n",var, agg_sum[var]}'
			sb.append("} ");
			sb.append("END { for  (var in agg) printf(\"%s%.2f\\n\", agg[var], agg_sum[var\"Total Revenue\"])}'");

		}
		if (report.getReportDisplayType() == Report.ReportDisplayType.FIELDS_AND_COUNT) {
			sb.append(prefix);
			String prefixKey = "";
//            for (DisplayField displayField : displayFields) {
//                if (displayField.getFieldOptions().equalsIgnoreCase("JSON_STRING")){
//                    prefixKey = prefixKey + displayField.getFieldOptions();
//                }
//            }

			//match($0,/payee":"([^"]{5,})"/,a){count[a[1]]++}

			for (DisplayField displayField : displayFields) {
				if (displayField.getFieldOptions().equalsIgnoreCase("JSON_STRING")) {
					//match($0,/payee":"([^"]{5,})"/,a){count[a[1]]++};
					String variable = vars.get(0);
					usedVars.add(variable);
					vars.remove(0);
					sb.append("match($0,/");
					sb.append(displayField.getRegex());
					sb.append("/,");
					sb.append(variable);
					sb.append(")");
				}
			}

			//aggregate the json strings to create a key used for grouping on
			sb.append("{agg[");
			for (String usedVar : usedVars) {
				sb.append(usedVar);
				sb.append("[1]");
				sb.append("\"\\t\"");
			}
			sb.append("]=");
			for (String usedVar : usedVars) {
				sb.append(usedVar);
				sb.append("[1]");
				sb.append("\"\\t\"");
			}
			sb.append(" ;var=");
			for (String usedVar : usedVars) {
				sb.append(usedVar);
				sb.append("[1]");
				sb.append("\"\\t\"");
			}

			sb.append("} ; {");

			for (DisplayField displayField : displayFields) {
				if (displayField.getFieldOptions().equalsIgnoreCase("COUNT")) {
					String variable = vars.get(0);
//                    usedVars.add(variable);
					vars.remove(0);
					sb.append(" agg_count[");
					sb.append("var");
					sb.append("\"counter\"");
					sb.append("]++");
				}
			}

			sb.append("} ");
			//note count prescision is always set to 0
			sb.append("END { for  (var in agg) printf(\"%s%.0f\\n\", agg[var], agg_count[var\"counter\"])}'");

		}
		if (report.getReportDisplayType() == Report.ReportDisplayType.FIELDS_AND_SUM_AND_COUNT) {
			sb.append(prefix);
			String prefixKey = "";
//            for (DisplayField displayField : displayFields) {
//                if (displayField.getFieldOptions().equalsIgnoreCase("JSON_STRING")){
//                    prefixKey = prefixKey + displayField.getFieldOptions();
//                }
//            }

			//match($0,/payee":"([^"]{5,})"/,a){count[a[1]]++}

			for (DisplayField displayField : displayFields) {
				if (displayField.getFieldOptions().equalsIgnoreCase("JSON_STRING")) {
					//match($0,/payee":"([^"]{5,})"/,a){count[a[1]]++};
					String variable = vars.get(0);
					usedVars.add(variable);
					vars.remove(0);
					sb.append("match($0,/");
					sb.append(displayField.getRegex());
					sb.append("/,");
					sb.append(variable);
					sb.append(")");
				}
			}

			//aggregate the json strings to create a key used for grouping on
			sb.append("{agg[");
			for (String usedVar : usedVars) {
				sb.append(usedVar);
				sb.append("[1]");
				sb.append("\"\\t\"");
			}
			sb.append("]=");
			for (String usedVar : usedVars) {
				sb.append(usedVar);
				sb.append("[1]");
				sb.append("\"\\t\"");
			}
			sb.append(" ;var=");
			for (String usedVar : usedVars) {
				sb.append(usedVar);
				sb.append("[1]");
				sb.append("\"\\t\"");
			}

			sb.append("} ; {");

			for (DisplayField displayField : displayFields) {
				if (displayField.getFieldOptions().equalsIgnoreCase("SUM")) {
					String variable = vars.get(0);
//                    usedVars.add(variable);
					vars.remove(0);
					sb.append("match($0,/");
					sb.append(displayField.getRegex());
					sb.append("/,");
					sb.append(variable);
					sb.append(");");
					sb.append(" agg_sum[");
					sb.append("var");
					sb.append("\"" + displayField.getName() + "\"");
					sb.append("]+=");
					sb.append(variable);
					sb.append("[1]");
				} else if (displayField.getFieldOptions().equalsIgnoreCase("COUNT")) {
//					String variable = vars.get(0);
//                    usedVars.add(variable);
					vars.remove(0);
					sb.append(" agg_count[");
					sb.append("var");
					sb.append("\"counter\"");
					sb.append("]++");
				}
			}

			// cat *.gz | zgrep "" | awk  -M 'match($0,/Country":"([^"]{1,})"/,a)match($0,/Region":"([^"]{1,})"/,b){agg[a[1]b[1]]=a[1]"*"b[1]"*" ;var=a[1]"*"b[1]"*"} ; {match($0,/Total Revenue":"([^"]{1,})"/,c)
			//                                agg_sum[var"Total Revenue"]+=c[1]; print var c[1]} END {OFMT="%.10f"; for (var in agg_sum) printf "%s %.3f\n",var, agg_sum[var]}'
			sb.append("} ");
			sb.append("END { for  (var in agg) printf(\"%s%.2f\\t%s\\n\", agg[var], agg_sum[var\"Total Revenue\"], agg_count[var\"counter\"])}'");

		}
		System.out.println(sb.toString());

		return sb.toString();

	}
}
