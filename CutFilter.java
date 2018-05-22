import java.util.Random;
import java.io.*;

public class CutFilter extends Filter
{
	public int copies = 1;
	public int min    = 20;
	public int max    = 400;
	int    seed = 1;

	public CutFilter()
	{
	}

	int processFlag( String[] args, int i)
	{
		if ( args[i].equals("-n")) {
			i++;
			if (args.length < i)
				error("-n arg error");
			copies = Integer.parseInt( args[i]);
			return 1;
		} else
		if ( args[i].equals("-max")) {
			i++;
			if (args.length < i)
				error("-max arg error");
			max = Integer.parseInt( args[i]);
			return 1;
		} else
		if ( args[i].equals("-min")) {
			i++;
			if (args.length < i)
				error("-min arg error");
			min = Integer.parseInt( args[i]);
			return 1;
		}

		return super.processFlag( args, i);
	}

	Random r = null;
	StringBuffer sbuf = null;
	int nextseg = 0;
	int segcount = 0;

	public String doit( int i)
	{
		char c = (char)i;
		if (reverse) {
			if (c == '\n')
				return "";
			return ""+c;
		}
		sbuf.append(c);
		if (nextseg == 0) {
			nextseg = nextSegment();
			String rval = sbuf.toString() + "\n";
			sbuf = new StringBuffer();
			segcount++;

			return rval;
		}
		nextseg--;
		return "";
	}

	public String getTail()
	{
		return sbuf.toString();
	}

	int nextSegment()
	{
		int val = r.nextInt( (max-min));

		return val+min;
	}


	public void run( String infile, PrintWriter pw)
		throws IOException
	{
		if (!reverse) {
			r = new Random( seed);
			sbuf = new StringBuffer();
			nextseg = nextSegment();
		}
		for (int i = 0; i < copies; i++) {
			if (copies > 1) {
				System.err.print('.'); System.err.flush();
			}
			Cat c = new Cat( infile, pw);
			c.run( this);
			if (!reverse)
				pw.println(getTail());
		}
		if (copies > 1)
			System.err.println("");
		System.err.println( "Segments: " + segcount);

	}

	public static void main( String[] args)
	{
			CutFilter f = new CutFilter();

			f.run( args);
	}

}
