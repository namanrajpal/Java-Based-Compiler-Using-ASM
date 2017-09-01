package cop5556sp17;

/**
 * 
 * A simple globalLog that can be used to record a trace of 
 * the PLPRuntime* method calls and output from 
 * CodeGenUtils.genPrint and CodeGenUtils.getPrintTOS.  
 * This is required at runtime.  
 * 
 * The output can be used for grading and debugging.
 *
 */
public class PLPRuntimeLog {

	private StringBuffer sb;

	public static PLPRuntimeLog globalLog;

	public static void initLog() {
		globalLog = new PLPRuntimeLog();
		globalLog.sb = new StringBuffer();
	}
	
	public static void globalLogAddEntry(String entry){
		if (globalLog != null) globalLog.addEntry(entry);
	}
	
	private void addEntry(String entry) {
		sb.append(entry);
	}

	public static String getString() {
		return (globalLog != null) ? globalLog.toString() : "";
	}

	@Override
	public String toString() {
		return sb.toString();
	}

	public static void resetLogToNull() {
		globalLog = null;
	}

}
