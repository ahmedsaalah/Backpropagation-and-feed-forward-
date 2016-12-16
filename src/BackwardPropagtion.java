import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;


public class BackwardPropagtion 
{
	double matH[][];
	double matO[][];
	double newH[][];
	double sigmaHidden[];
	double newO[][];
	int M ,L,N;
	double learningRate=0.3;
	double matInputs[];
	double actualout[];
	double netHidden[];
	double netOut[];
	double sigma[];
	double sigmanode[];
	int numberofItertions;
	ArrayList<String>inputFile;
	BackwardPropagtion() throws Exception
    {
    	
		inputFile =readFile("train.txt",2);

  String line =inputFile.get(0);

if (inputFile ==null)
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
	newH=new double[L][M];
	newO=new double[N][L];
	matInputs=new double[M];
	netHidden=new double[L];
	sigmaHidden=new double[L];
	netOut=new double[N];
	sigma=new double[N];
	sigmanode=new double[N];
	actualout=new double[N];
for(int i=0;i<L;i++)
{
	 for(int y=0;y<M;y++)
		{
			matH[i][y]=Math.random()*(1+1)- 1;
		}
}
for(int i=0;i<N;i++)
{
	 for(int y=0;y<L;y++)
		{
			matO[i][y]=Math.random()*(1+1)- 1;
		}
}

	
numberofItertions =Integer.parseInt(inputFile.get(1));

MakeIteration();
   
   
    }
	
    
    private void MakeIteration() throws IOException 
    {
    	
	
   
	   for (int i = 0; i < numberofItertions; i++)
	   {
		   
		   String[]tempinputs =(inputFile.get(2+i)).split(" ");
		 
		 	int j=0;
		   for(String a:tempinputs)
		   {
			      // there's a digit somewhere in the input string 
			if(!isBlank(a))
			{
			  
			   if(j<M)
			   {
				   matInputs[j]=Double.parseDouble(a);
			   }
			   else 
			   {
				   actualout[j-M]=Double.parseDouble(a);
				   
			   }
			 j++;  
			 
			}
		
		   }   
		
		   
		   
		   
		   
		   
		   
			for (int i1 = 0; i1 < L; i1++)
			{	netHidden[i1]=0;
				for (int j1 = 0; j1 < M; j1++)
				{
					netHidden[i1]+=matH[i1][j1]*matInputs[j1];	
					
				}
			
		netHidden[i1]=sigmoid(netHidden[i1]);

			}
			for (int i1 = 0; i1 < N; i1++) 
			{
				netOut[i1]=0;
				for (int j1 = 0; j1 < L; j1++)
				{
					
					netOut[i1]+=matO[i1][j1]*netHidden[j1];	
				}
				//netOut[i]=netOut[i]>0?1:0;
				netOut[i1]=sigmoid(netOut[i1]);
			}
			
			for (int i1 = 0; i1 < N; i1++) 
			{
				
				System.out.println("Calculated  = "+netOut[i1]+"    : actual =   "+actualout[i1]);
				sigma[i1]=actualout[i1]-netOut[i1];
				sigmanode[i1]=netOut[i1]*(1-netOut[i1])*sigma[i1];
	//	System.out.println("sigmoid = "+sigmanode[i1]);	
			}
		   
		updateWeightonOutputLayer();	
			
		errorTermToHiddenLayer();
			
			
			
			updateWeightonHiddenLayer();
			
			
			
			makeTheNewHweight();
			makeTheNewOweight();
			
			
		
			if(!calculateMSE())
			{
				break;
			}
		
		
	   
	   }
	   
	   writeTheWeightonFile();
	  
	
   }
    
		
	

    private void errorTermToHiddenLayer()
    {int sum=0;
	
	for (int i1 = 0; i1 < L; i1++) 
	{
		sum=0;
		for(int e=0;e<N;e++)
		{
			sum+=(sigma[e]*matO[e][i1]);
		}
		
		
		
		
		sigmaHidden[i1]=netHidden[i1]*(1-netHidden[i1])*sum;
	}
	
	
	}


	private void updateWeightonOutputLayer() 
    {
		// TODO Auto-generated method stub
    	for (int i1 = 0; i1 < N; i1++) 
		{
			
			for (int j1 = 0; j1 < L; j1++)
			{
				
				newO[i1][j1]=matO[i1][j1]+(learningRate*sigmanode[i1]*netHidden[j1]);
			}
			//netOut[i]=netOut[i]>0?1:0;
	
		}
			
	}


	private void updateWeightonHiddenLayer()
    {
		// TODO Auto-generated method stub
    	for (int i1 = 0; i1 < L; i1++) 
		{
			
			for (int j1 = 0; j1 < M; j1++)
			{
				
				newH[i1][j1]=matH[i1][j1]+(learningRate*sigmaHidden[i1]*matInputs[j1]);
			}
			//netOut[i]=netOut[i]>0?1:0;
	
		}	
	}


	private boolean calculateMSE() 
    {
 	   double Ep=0.0;
	   for(int i1=0;i1<N;i1++)
	   {
		   Ep+=(sigma[i1]*sigma[i1]);
	   }
	   if(Ep<0.0001)
	   {
		   return false;
	   }
	   return true;
		
	}


	private void makeTheNewOweight() 
    {
		// TODO Auto-generated method stub
		
    	for (int i1 = 0; i1 < N; i1++) 
		{
			
			for (int j1 = 0; j1 < L; j1++)
			{
				
				matO[i1][j1]=newO[i1][j1];
				System.out.println(matO[i1][j1]);
			}
			//netOut[i]=netOut[i]>0?1:0;
	
		}
	}


	private void makeTheNewHweight()
	{
		for (int i1 = 0; i1 < L; i1++) 
		{
			
			for (int j1 = 0; j1 < M; j1++)
			{
				
				matH[i1][j1]=newH[i1][j1];
			//	System.out.println(matH[i1][j1]);
			}
			//netOut[i]=netOut[i]>0?1:0;
	
		}

	}


	private void writeTheWeightonFile() throws IOException
    {
	
    	 BufferedWriter writer = new BufferedWriter(new FileWriter("weights.txt"));

 		for (int i1 = 0; i1 < L; i1++) 
 		{
 			
 			for (int j1 = 0; j1 < M; j1++)
 			{
 				
 				writer.write(""+matH[i1][j1]);
 				
 				if(j1!=M-1)
 				{
 					writer.write(" ");
 				}
 					
 			}
 			writer.newLine();
 			//netOut[i]=netOut[i]>0?1:0;
 	
 		}
 		
 		for (int i1 = 0; i1 < N; i1++) 
 		{
 			
 			for (int j1 = 0; j1 < L; j1++)
 			{
 				
 				
 	writer.write(""+matO[i1][j1]);
 	if(j1!=L-1)
 	{
 		writer.write(" ");
 	}
 			}
 			writer.newLine();
 	
 		}

 		
 	writer.close();		
	}


	public boolean isBlank(String value) {
        return (value == null || value.equals("") ||value.contains("Ljava.lang.")|| value.equals("null") || value.trim().equals(""));
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
		BackwardPropagtion b1= new BackwardPropagtion();

	}
}
