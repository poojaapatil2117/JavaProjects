package PassFail;
class Question 
{
	int id;
	String question;
	String opt1;
	String opt2;
	String opt3;
	String opt4;
	String answer;

	Question()
	{
		id=0;
		question=new String();
		opt1=new String();
		opt2=new String();
		opt3=new String();
		opt4=new String();
		answer=new String();
	}

	void setID(int id)
	{
		this.id=id;
	}
	void setQuestion(String question)
	{
		this.question=question;
	}
	void setOption1(String opt1)
	{
		this.opt1=opt1;
	}
	void setOption2(String opt2)
	{
		this.opt2=opt2;
	}
	void setOption3(String opt3)
	{
		this.opt3=opt3;
	}
	void setOption4(String opt4)
	{
		this.opt4=opt4;
	}
	void setAnswer(String answer)
	{
		this.answer=answer;
	}
	int getId()
	{
		return id;
	}
	String getQuestion()
	{
		return question;
	}
	String getOpt1()
	{
		return opt1;
	}
	String getOpt2()
	{
		return opt2;
	}
	String getOpt3()
	{
		return opt3;
	}
	String getOpt4()
	{
		return opt4;
	}
	String getAnswer()
	{
		return answer;
	}

	public String toString()
	{
		return("\n Question "+question+"\n 1 "+opt1+"\n 2 "+opt2+"\n 3 "+opt3+"\n 4 "+opt4);
	}
}
