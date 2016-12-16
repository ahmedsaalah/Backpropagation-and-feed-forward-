import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class feedForward 
{
	double matH[][];
	double matO[][];
	int M ,L,N;
	double matInputs[];
	double netHidden[];
	double netOut[];
    feedForward() throws Exception
    {
    	
    	String line =readFile("train.txt",1).get(0);

  

if (line ==null)
{
	return ;
}
	String arr[]=line.split(" ");
	
	for(int i=0;i<3;i++)
	{
		if(i==0)
		{
			M=Integer.parseInt(arr[i]);
		}
		else if(i==1)
		{
			L=Integer.parseInt(arr[i]);
		}else if(i==2)
		{
			N=Integer.parseInt(arr[i]);
		}
		
	}
	
	matH=new double[L][M];
	matO=new double[N][L];
	matInputs=new double[M];
	netHidden=new double[L];
	netOut=new double[N];

	ArrayList<String> weights=new ArrayList<String>();
	weights=readFile("weights.txt",2);
	
	String arr1[];
for(int i=0;i<weights.size();i++)
{
	 arr1=weights.get(i).split(" ");
	if (i<L)//hidden weights
	{
		for(int y=0;y<arr1.length;y++)
		{
			
			matH[i][y]=Double.parseDouble(arr1[y]);
		}
		
	}
	else//output weights
	{
		for(int y=0;y<arr1.length;y++)
		{
			//System.out.println(arr1[y]);
			matO[i-L][y]=Double.parseDouble(arr1[y]);
		}
		
		
	}
}


    
        // close the BufferedReader when we're done
    ArrayList <String>tempinputs=readFile("inputs.txt",2);
    int y=0;
   for(String a:tempinputs)
   {
	   matInputs[y++]=Double.parseDouble(a);
   }
       

   
	for (int i = 0; i < L; i++)
	{	netHidden[i]=0;
		for (int j = 0; j < M; j++)
		{
			netHidden[i]+=matH[i][j]*matInputs[j];	
			
		}
	
netHidden[i]=sigmoid(netHidden[i]);

	}
	for (int i = 0; i < N; i++) 
	{
		netOut[i]=0;
		for (int j = 0; j < L; j++)
		{
			
			netOut[i]+=matO[i][j]*netHidden[j];	
		}
		//netOut[i]=netOut[i]>0?1:0;
		netOut[i]=sigmoid(netOut[i]);
	}
	
	for (int i = 0; i < N; i++) 
	{
		
		System.out.println(netOut[i]);
	
	}
		
	
   
    }
	
    
    private ArrayList<String> readFile(String filename,int numberofLines)
    		throws Exception
    		{
    		    String line = null;
    		    ArrayList<String> records = new ArrayList<String>();

    		    // wrap a BufferedReader around FileReader
    		    BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));

    		    // use the readLine method of the BufferedReader to read one line at a time.
    		    // the readLine method returns null when there is nothing else to read.
    		    if (numberofLines==1)
    		    {
    		    	line = bufferedReader.readLine();
    		    	   records.add(line);
    		    }
    		    else
    		    {	
    		    
    		    while ((line = bufferedReader.readLine()) != null)
    		    {
    		        records.add(line);
    		    }
    		    }
    		    // close the BufferedReader when we're done
    		    bufferedReader.close();
    		    return records;
    		}
    
    private double sigmoid(double x) {
        return (1.0 / (1 + Math.exp(-x)));
    }
    
	public static void main(String[] args) throws Exception
	{
		feedForward f1= new feedForward();

	}

}
