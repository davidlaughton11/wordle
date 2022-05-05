package a2;

import static org.junit.Assert.*;

import java.util.ArrayList;

//static lets you import all the static features
import org.junit.Test;

public class TestWordleUtils {

	@Test
	public void test01a_replace() {
		// method args
		String s = "A";
		int index = 0;
		char c = 'B';

		// expected answer
		String exp = "B";

		// run test
		assertEquals(exp, WordleUtils.replace(s, index, c));
	}

	@Test
	public void test01b_replace() {
		// method args
		String s = "GUMBO";
		int index = 0;
		char c = 'J';

		// expected answer
		String exp = "JUMBO";

		// run test
		assertEquals(exp, WordleUtils.replace(s, index, c));
	}

	@Test
	public void test01c_replace() {
		// method args
		String s = "JIMBO";
		int index = 1;
		char c = 'U';

		// expected answer
		String exp = "JUMBO";

		// run test
		assertEquals(exp, WordleUtils.replace(s, index, c));
	}

	@Test
	public void	exercise1_isGreen_throws() {
		String answer = "GREEN";
		String guess  = "GREENY";
		
		try {
			WordleUtils.isGreen(guess, answer);
			fail("IllegalArgumentException expected for guess = " + guess);
		}
		catch(IllegalArgumentException x){
			// okay expected result
		}
	}
	@Test
	public void exercise2_isYellow() {
		String answer = "WHACK";
		String guess  = "KAYAK";
		
		ArrayList<Boolean> exp = new ArrayList<>();
		exp.add(false);
		exp.add(true);
		exp.add(false);
		exp.add(false);
		exp.add(false);
		
		assertEquals(exp, WordleUtils.isYellow(guess, answer));
	}
	@Test
	public void excerise3_getColors() {
		String answer = "COVER";
		String guess  = "COYER";
		ArrayList<WordleColor> exp = new ArrayList<>();
		exp.add(WordleColor.GREEN);
		exp.add(WordleColor.GREEN);
		exp.add(WordleColor.GRAY);
		exp.add(WordleColor.GREEN);
		exp.add(WordleColor.GREEN);
		assertEquals(exp, WordleUtils.getColors(guess, answer));
	}
}
