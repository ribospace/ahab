
public class LookupFilter extends Filter
{
	LookupTable lookup  = null;

	public LookupFilter( LookupTable lt)
	{
		this.lookup = lt;
	}

	char[] codon = new char[3];
	int  cix = 0;
	boolean upperCase = false;

	public String doit( int i)
	{
		char c = (char)i;

		if (!reverse) {
			String out = "";
			if (Character.isUpperCase(c)) {
				out = lookup.getUpper();
				c = Character.toLowerCase(c);
			}
			out = out+ lookup.get( ""+c);
			return out;
		}
		codon[cix++] = c;
		if (cix == 3) {
			cix = 0;
			String codonstr = new String( codon);
			String out =  lookup.get( codonstr);
			if (lookup.isUpper(out)) {
				upperCase = true;
				return "";
			}
			if (upperCase) {
				upperCase = false;
				out = "" + Character.toUpperCase(out.charAt(0));
			}
			return out;
		}
		return "";
	}

	public static void main( String[] args)
	{
			LookupFilter f = new LookupFilter( LookupTable.newLookupTable());

			f.run( args);
	}

}
