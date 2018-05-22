import java.io.*;

public class FastqShuffleFilter extends ShuffleFilter
{

	String quality( String s)
	{
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++)
			sb.append('~');
		return sb.toString();
	}

	public void format( String s, int lineno, PrintWriter pw)
	{
		if (reverse) {
			super.format( s, lineno, pw);
			return;
		}
		pw.println( "@ahab-" + lineno);
		pw.print( s);
		pw.println( "+");
		pw.println( quality( s));
	}

	int processFlag( String[] args, int i)
	{
		if ( args[i].equals("-r")) {
			reverse = true;
			return 0;
		} else
		if ( args[i].equals("-seq")) {
			shuffle = false;
			return 0;
		}
		return -1;
	}



	StringBuffer sb2 = new StringBuffer();

	public String doit( int i)
	{
		if (!reverse)
			return super.doit(i);

		char c = (char)i;
		if (c == '\n') {
			char a = sb.charAt(0);
			if ( Character.isUpperCase(a))
				lines.add(sb.toString());
			sb = new StringBuffer();
			return "";
		}
		sb.append(c);
		return "";
	}

	public static void main( String[] args)
	{
		FastqShuffleFilter f = new FastqShuffleFilter();

		f.run( args);
	}

}
