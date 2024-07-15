package PassFail;
class question_timer
{
	static int que_no;
	static final int last_que=30;
	
	static
	{
		que_no=0;
	}
	static void addLevel()
	{
		if(que_no==last_que) return;
		que_no++;
	}
}