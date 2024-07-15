package PassFail;
class TimeTimer implements Runnable
{
	int start_time;
	final int end_time = 1200;
	Thread t;

	TimeTimer()
	{
		 this.start_time = 0;
	}
 
	void startTimeTimer() 
	{
		this.t = new Thread(this);
		this.t.start();
	}
	
	public int getTime()
	{
		return this.start_time;
	}
 	public void run() 
	{
		for (int i = 0; i < 1200; i++)
		{
			try
			{
				this.start_time += 10;
				Thread.sleep(1000);
			}
			catch (Exception localException)
			{
			}
		}
   }
}

