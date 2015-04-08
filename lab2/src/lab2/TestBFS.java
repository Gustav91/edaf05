package lab2;

import static org.junit.Assert.*;
import org.junit.Test;

import java.io.*;


public class TestBFS {
	private final static String TESTDATA_DIR = "/usr/local/cs/edaf05/lab2";
	private final static char SC = File.separatorChar;
	
	/**
	 * Method to run an actual test case.
	 * 
	 * @param testname
	 *            Name of test data to be used, e.g. "words-10-test.in".
	 */
	private void runTestCase(String testname) {
		System.out.println("Running test: " + testname);
		String infile = TESTDATA_DIR + SC + testname + "-test.in";
		String datfile = TESTDATA_DIR + SC + testname + ".dat";
		String outfile = TESTDATA_DIR + SC + testname + "-test.out";

		/*
		 * Divert stdout to buffer
		 */
		PrintStream oldOut = System.out;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		System.setOut(ps);

		
		String[] args = new String[2];
		args[0] = datfile;
		args[1] = infile;
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

			assertEquals(sb.toString(), baos.toString());
		} catch (FileNotFoundException e) {
			fail("File " + outfile + " not found.");
		} catch (IOException e) {
			fail("Error reading " + outfile);
		}
	}
	
	/**
	 * Simple test case for the 'words-10' test data.
	 */
	//@Test
	public void testWords() {
		runTestCase("words-250");
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

		for (File f : dir.listFiles()) {
			if (f.isFile() && f.toString().endsWith(".out")) {
				String s = f.toString();
				s = s.substring(s.lastIndexOf(SC) + 1);
				s = s.substring(0, s.lastIndexOf("-test"));

				runTestCase(s);
			}
		}
	}
}
