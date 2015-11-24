package cloudappMP1;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class testMP1 {

	private String[] zeroResult = {"list",
			"de",
			"state",
			"school",
			"disambiguation",
			"county",
			"new",
			"john",
			"album",
			"c",
			"river",
			"station",
			"united",
			"highway",
			"national",
			"saint",
			"william",
			"route",
			"f",
			"film"};

	private String[] oneResult = {"list",
			"de",
			"state",
			"school",
			"disambiguation",
			"county",
			"new",
			"john",
			"river",
			"route",
			"film",
			"album",
			"c",
			"high",
			"united",
			"william",
			"st",
			"national",
			"football",
			"saint"};

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testZero() throws Exception {
		String userName = "0";
		MP1 mp = new MP1(userName, "./input.txt");
		String[] result = mp.process();
		for(int i =0; i< this.zeroResult.length;i++){
			assertEquals(String.format("{0}th element not match in 0 array", i),zeroResult[i],result[i]);
		}
	}

	@Test
	public void testOne() throws Exception {
		String userName = "1";
		MP1 mp = new MP1(userName, "./input.txt");
		String[] result = mp.process();
		for(int i =0; i< this.oneResult.length;i++){
			assertEquals(String.format("{0}th element not match in 0 array", i),oneResult[i],result[i]);
		}
	}	
}
