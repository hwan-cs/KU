package pa5.jhpark.konkuk;

public class Word 
{
	String eng;
	String kor;
	int search;
	public Word(String eng, String kor, int search) 
	{
		super();
		this.eng = eng;
		this.kor = kor;
		this.search = search;
	}
	
	@Override
	public String toString()
	{
		return eng + " : " + kor;
	}
	
}
