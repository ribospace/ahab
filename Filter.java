import java.io.*;

public abstract class Filter
{
	public boolean reverse = false;

	public abstract String doit( int i);

	int processFlag( String[] args, int i)
	{
		if ( args[i].equals("-r")) {
			reverse = true;
			return 0;
		}
		return -1;
	}

	public void error( String s)
	{
		System.err.println( s);
		System.exit(-1);
	}

	public void run( String[] args)
	{
			String infile  = null;
			String outfile = null;

			for (int i = 0; i < args.length; i++) {
				int j = processFlag( args, i);
				if (j >= 0) {
					i = i + j;
					continue;
				}
				else if (args[i].startsWith("-"))
					continue;
				else if (infile == null)
					infile = args[i];
				else if (outfile == null)
					outfile = args[i];
			}
			if (infile == null)
				infile = "testin";
			if (outfile == null)
				outfile = "testout";

			run( infile, outfile);
	}
	public void run( String infile, String outfile)
	{
		try {
			FileWriter fw = new FileWriter( outfile);
			BufferedWriter bw = new BufferedWriter( fw);
			PrintWriter pw = new PrintWriter( bw);

			run ( infile, pw);

			pw.flush();
			pw.close();
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run( String infile, PrintWriter pw)
		throws IOException
	{
			Cat c = new Cat( infile, pw);
			c.run( this);
	}
}
