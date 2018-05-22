import java.io.*;

public class Cat
{
	PrintWriter pw;
	BufferedReader br;
	boolean rev = false;

	public Cat( FileReader fr, PrintWriter pw)
		throws IOException
	{
		this( new BufferedReader(fr), pw);
	}

	public Cat (BufferedReader br, PrintWriter pw)
		throws IOException
	{
		this.br = br;
		this.rev = rev;
		this.pw  = pw;
	}

	public Cat( String inf, PrintWriter pw)
		throws IOException
	{
		this( new FileReader( inf), pw);
	}

	public void run( Filter f)
		throws IOException
	{
		while (true) {
			int i = br.read();
			if (i == -1)
				break;
			String r = f.doit( i);
			if (r != null)
				pw.print(r);

		}
	}

}
