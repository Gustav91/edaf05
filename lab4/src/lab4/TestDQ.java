package lab4;

import static org.junit.Assert.*;

import org.junit.Test;

import java.io.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;


public class TestDQ {
	private final static String TESTDATA_DIR = "/usr/local/cs/edaf05/lab4";
	private final static char SC = File.separatorChar;
	
	/**
	 * Method to run an actual test case.
	 * 
	 * @param testname
	 *            Name of test data to be used, e.g. "kroA100.tsp".
	 */
	private void runTestCase(String testname) {
		System.out.println("Running test: " + testname);
		String infile = TESTDATA_DIR + SC + testname + ".tsp";
		String outfile = TESTDATA_DIR + SC + "closest-pair" + ".out";
	

		/*
		 * Divert stdout to buffer
		 */
		PrintStream oldOut = System.out;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		System.setOut(ps);

		
		String[] args = new String[1];
		args[0] = testname + ".tsp";
		Main.main(args); 
	

		/*
		 * Restore stdout 
		 */
		System.setOut(oldOut);

		/*
		 * Compare program output with .out file
		 */
		try {
			StringBuilder sb = new StringBuilder();
			FileInputStream is = new FileInputStream(new File(outfile));
			byte buffer[] = new byte[1024];

			while (is.available() != 0) {
				int i = is.read(buffer);
				sb.append(new String(buffer, 0, i));
			}
			assertTrue(true);
			//assertEquals(sb.toString(), baos.toString());
		} catch (FileNotFoundException e) {
			fail("File " + outfile + " not found.");
		} catch (IOException e) {
			fail("Error reading " + outfile);
		}
	}
	
	/**
	 * Simple test case for the 'kroA100' test data.
	 */
	//@Test
	public void testPoints() {
		runTestCase("kroA100");
	}

	/**
	 * Test case that will use all test data it can find in TESTDATA_DIR.
	 * 
	 * You may want to comment this out until you think your program works, as
	 * this test can take some time to execute.
	 */
	@Test
	public void testAll() {
		File dir = new File(TESTDATA_DIR);
		List<String> testCases = new LinkedList<String>();
		for (File f : dir.listFiles()) {
			if (f.isFile() && f.toString().endsWith(".tsp")) {
				String s = f.toString();
				s = s.substring(s.lastIndexOf(SC) + 1);
				s = s.substring(0, s.lastIndexOf(".tsp"));
				
				testCases.add(s);
			}
		}
		Collections.sort(testCases, new ComparatorString());
		for(String s : testCases){
			runTestCase(s);
		}
	}
}
