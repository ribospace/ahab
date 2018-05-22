import java.util.Random;
import java.io.*;
import java.util.Collections;
import java.util.Vector;
import java.util.Enumeration;

public class ShuffleFilter extends Filter
{

	public ShuffleFilter()
	{
	}

	int processFlag( String[] args, int i)
	{
		return -1;
	}

	int linecount = 0;
	boolean pass1 = false;
	Vector<String> lines = new Vector<String>();
	StringBuffer sb = new StringBuffer();
	int gccount = 0;

	public String doit( int i)
	{
		char c = (char)i;
			sb.append(c);
			if (c == '\n') {
				linecount++;
				gccount++;
				lines.add(sb.toString());
				sb = null;
				sb = new StringBuffer();
				if (gccount == 500000) {
					gccount = 0;
					System.err.println(">> " + linecount);
					System.gc();
				}
			}
			return "";
	}

	public String getTail()
	{
		return sb.toString();
	}

	public boolean shuffle = true;

	public void run( String infile, PrintWriter pw)
		throws IOException
	{
		Cat c = new Cat( infile, pw);
		c.run( this);
		String s = getTail();
		if (s.length() > 0)
			lines.add( s);
		if (shuffle) {
			Random r = new Random( s.length());
			int todo = lines.size();
			int bound = todo;
			int i = 0;

			System.err.println("Shuffling");
			//while (todo != 0) {
			//        int j = r.nextInt( bound);
			//        s = lines.elementAt(j);
			//        if (s != null) {
			//                format( s, i++, pw);
			//                lines.setElementAt( null, j);
			//                todo--;
			//        }
			//}
			Collections.shuffle( lines);
			System.err.println("Shuffle done");
		} else {
			Enumeration<String> e = lines.elements();
			int i = 0;
			while (e.hasMoreElements())
				format( e.nextElement(), i++, pw);
		}
	}

	public void format( String s, int lineno, PrintWriter pw)
	{
		pw.println( s);
	}

	public static void main( String[] args)
	{
		ShuffleFilter f = new ShuffleFilter();

		f.run( args);
	}

}
