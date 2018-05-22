import java.util.Hashtable;

public class LookupTable
{
	public static char   UPPER  = '^';
	public static String UPPERS = "^";

	public static char   UNKNOWN  = '#';
	public static String UNKNOWNS = "#";

	public String getUpper()
	{
		return get( "" + UPPER);
	}


	public boolean isUpper( String s)
	{
		return UPPERS.equals(s);
	}

	public String get( String s)
	{
		String rval = h.get(s);
		if (rval == null)
			rval = h.get( UNKNOWNS);
		return rval;
	}

	LookupTable()
	{}

	static Hashtable<String,String> h = null;

	static char[] symbols = { '*', '_', '-', '?', '!', '.', ',', ';', ':', '(', ')', '\'', '"', ' ', '\n'};

	static void initLookupTable()
	{
		h = new Hashtable<String,String>();

		char c;

		init( UPPER);
		init( UNKNOWN);

		for (c='0'; c<='9'; c++)
			init( c);
		for (c='a'; c<='z'; c++)
			init( c);
		int i;
		for ( i = 0; i < symbols.length; i++)
			init( symbols[i]);

	}

	static int count = 0;

	static String[] letters = { "A", "C", "T", "G" };

	static String getNextCodon()
	{
		int i = count++;
		int b = i & 0x03;
		int g = (i>>2) & 0x03;
		int r = (i>>4) & 0x03;

		String codon = letters[r] + letters[g] + letters[b];

		return codon;
	}

	static void init( char c)
	{
		String codon = getNextCodon();
		String k = ""+c;

		// System.err.println( "Map " + k + " to " + codon);
		h.put( codon, k);
		h.put( k, codon);
	}

	public static LookupTable newLookupTable()
	{
		if (h == null)
			initLookupTable();

		return new LookupTable();
	}
}
