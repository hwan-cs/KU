package linearAlgebra.prj1;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.awt.image.WritableRaster;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class prj1 
{

	public static void main(String[] args) throws IOException 
	{
		//레나 사진의 경로
		BufferedImage img=ImageIO.read(new File("/Users/19juhpark/Desktop/Computer Science/JhparkLab/src/linearAlgebra/prj1/image_lena_24bit.bmp.bmp"));
		int pix[][]= new int[img.getWidth()][img.getHeight()];
		for (int r = 0; r < img.getWidth(); r++) 
		{
	        for (int c = 0; c < img.getHeight(); c++) 
	        {
	        	Color col = new Color(img.getRGB(r, c));
	        	pix[r][c] = (col.getRed() + col.getGreen() + col.getBlue())/3;
	        }
	    }
		int [][] haar = makeHaar(img.getWidth());
		//Haar Matrix를 보여주는 텍스트 파일
		FileWriter writer = new FileWriter("/Users/19juhpark/Desktop/Lenna Attemps/MyFile.txt", true);
		for(int i = 0;i<haar.length;i++)
		{
			for(int j = 0;j<haar[0].length;j++)
			{
				writer.write(haar[i][j] + " ");
			}
			writer.write("\n");
		}
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Enter how many cycles: ");
		int cycle = keyboard.nextInt();
		//사진에 2DDHWT를 한것, 몇번의 cycle을 할지 사용자는 정할 수 있음
		//3 입력시 512/2^3만큼 압축됨, 2 입력시 512/2^2만
		double [][] dhwt = do2DDHWT(pix, cycle);
		BufferedImage image = new BufferedImage(img.getWidth(),img.getHeight(), BufferedImage.TYPE_INT_RGB);
		for(int i=0; i<dhwt.length; i++) 
		{
			for(int j=0; j<dhwt[0].length; j++) 
		    {
				if(dhwt[i][j]<0)
					dhwt[i][j] = 0;
				image.setRGB(i,j,new Color((int)dhwt[i][j],(int)dhwt[i][j],(int)dhwt[i][j]).getRGB());	
		    }
		}
		File output = new File("/Users/19juhpark/Desktop/Lenna Attemps/invtest.bmp");
		ImageIO.write(image, "bmp", output);
		double [][] invdhwt = doInv2DDHWT(dhwt, cycle);
		BufferedImage invImage = new BufferedImage(img.getWidth(),img.getHeight(), BufferedImage.TYPE_INT_RGB);
		for(int i=0; i<invdhwt.length; i++) 
		{
			for(int j=0; j<invdhwt[0].length; j++) 
		    {
				if(invdhwt[i][j]<0)
					invdhwt[i][j] = 0;
				invImage.setRGB(i,j,new Color((int)invdhwt[i][j],(int)invdhwt[i][j],(int)invdhwt[i][j]).getRGB());	
		    }
		}
		//사진에 inverse 2DDHWT를 한것
		File invOutput = new File("/Users/19juhpark/Desktop/Lenna Attemps/lennainverse.bmp");
		ImageIO.write(invImage, "bmp", invOutput);
	}
	public static double[][] do2DDHWT(int[][] pixels, int num) 
	{
        int width = pixels[0].length;
        int height = pixels.length;
        double[][] hwt = new double[height][width];
        double[][] temphwt = new double[height][width];
        for (int i = 0; i < pixels.length; i++) 
        {
            for (int j = 0; j < pixels[0].length; j++) 
            {
            	hwt[i][j] = pixels[i][j];
            }
        }
        for (int i = 0; i < num; i++) 
        {
            width /= 2;
            for (int j = 0; j < height; j++) 
            {
                for (int k = 0; k < width; k++) 
                {
                    double a = hwt[j][2 * k];
                    double b = hwt[j][2 * k + 1];
                    double avgAdd = (a + b)/2;
                    double avgSub = (a - b)/2;
                    temphwt[j][k] = avgAdd;
                    temphwt[j][k + width] = avgSub;
                }
            }
            for (int j = 0; j < height; j++) 
            {
                for (int k = 0; k < width; k++) 
                {
                	hwt[j][k] = temphwt[j][k];
                	hwt[j][k + width] = temphwt[j][k + width];
                }
            }
            height /= 2;
            for (int j = 0; j < width*2; j++) 
            {
                for (int k = 0; k < height; k++) 
                {
                    double a = hwt[2 * k][j];
                    double b = hwt[2 * k + 1][j];
                    double add = a + b;
                    double sub = a - b;
                    double avgAdd = add / 2;
                    double avgSub = sub / 2;
                    temphwt[k][j] = avgAdd;
                    temphwt[k + height][j] = avgSub;
                }
            }
            for (int j = 0; j < width*2; j++) 
            {
                for (int k = 0; k < height; k++) 
                {
                	hwt[k][j] = temphwt[k][j];
                	hwt[k + height][j] = temphwt[k + height][j];
                }
            }
        }
        return hwt;
    }
	public static double[][] doInv2DDHWT(double[][] pixels, int num) 
	{
        int w = pixels[0].length;
        int h = pixels.length;
            double[][] iHwt = new double[h][w];
            double[][] tempiHwt = new double[h][w];
            for (int i = 0; i < pixels.length; i++) 
            {
                for (int j = 0; j < pixels[0].length; j++) 
                {
                	iHwt[i][j] = pixels[i][j];
                }
            }
            int hh = h / (int) Math.pow(2, num);
            int ww = w / (int) Math.pow(2, num);
            for (int i = num; i > 0; i--) 
            {
                for (int j = 0; j < ww; j++) 
                {
                    for (int k = 0; k < hh; k++) 
                    {
                        double a = iHwt[k][j];
                        double b = iHwt[k + hh][j];
                        double add = a + b;
                        double sub = a - b;
                        tempiHwt[2 * k][j] = add;
                        tempiHwt[2 * k + 1][j] = sub;
                    }
                }
                for (int j = 0; j < ww*2; j++) 
                {
                    for (int k = 0; k < hh; k++) 
                    {
                    	iHwt[2 * k][j] = tempiHwt[2 * k][j];
                    	iHwt[2 * k + 1][j] = tempiHwt[2 * k + 1][j];
                    }
                }
                hh *= 2;
                for (int j = 0; j < hh; j++) 
                {
                    for (int k = 0; k < ww; k++) 
                    {
                        double a = iHwt[j][k];
                        double b = iHwt[j][k + ww];
                        double add = a + b;
                        double sub = a - b;
                        tempiHwt[j][2 * k] = add;
                        tempiHwt[j][2 * k + 1] = sub;
                    }
                }
                for (int j = 0; j < hh; j++) 
                {
                    for (int k = 0; k < ww; k++) 
                    {
                    	iHwt[j][2 * k] = tempiHwt[j][2 * k];
                    	iHwt[j][2 * k + 1] = tempiHwt[j][2 * k + 1];
                    }
                }
                ww *= 2;
            }
            return iHwt;
    }
	public static int [][] makeHaar(int n)
	{	
		int tmax = (int)(Math.log(n) / Math.log(2));
		int div = 1;
		int jold = 1;
		int js, je, iold;
		int H[][] = new int [n][n];
		for (int i = 0; i < n; i++)
		{
			H[i][0] = 1;
		}
	
		for (int i = 0; i < n; i++)
		{
			for (int j = 1; j < n; j++)
			{
				H[i][j] = 0;
			}
		}
	
		for ( int t = 0; t < tmax; t++) 
		{
			div *= 2;
			iold = 0;
	
			js = jold; 
			je = (int)Math.pow(2,t) + js -1;
			int j;
			for (j = js; j <= je; j++) 
			{
				int i;
				for (i = iold; i < iold + (n/div); i++)
				{
					H[i][j] = 1;
				}
				
				for (i = iold + (n/div); i < iold + 2*(n/div); i++)
				{
					H[i][j] = -1;
				}
	
				iold = i;
			}
			jold = j;
		}
		return H;
	}
}
