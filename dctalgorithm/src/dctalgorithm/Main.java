package dctalgorithm;
import java.util.Arrays;

import org.apache.commons.math3.*;
import org.jtransforms.dct.*; 

public class Main {

	public static void main(String[] args) {
		int start=2;
		int end = 50;
		int step = 2;
		for(int i = start; i<end-1;i+=step) {
			double [][] testmat1= randomizeMatrix(i);
			double [][] testmat2= new double [i][i];
			for(int c=0; i<testmat1.length; i++)
				  for(int j=0; j<testmat1[i].length; j++)
				    testmat1[i][j]=testmat2[i][j];
			double [][] resultmat;
			
			long time1 = new java.util.Date().getTime();
			resultmat= DCT2HomeMade(i,i,testmat1);
			time1= new java.util.Date().getTime()-time1;
			
			long time2 = new java.util.Date().getTime();
			DoubleDCT_2D dct2d = new DoubleDCT_2D(i,i);
			dct2d.forward(testmat2, true);
			time2= new java.util.Date().getTime()-time2;
			
			System.out.print("\n"+"step: "+i+"TEMPO HOMEMADE: "+time1+";    TEMPO LIBRERIA: "+time2+";");
		}
		
	}	


	public static double[][] randomizeMatrix(int n) {
		double [][] mtx = new double [n][n];
		double c= 255;
		for(int i=0; i<n; i++) {
			for(int j=0; j<n; j++) {
				mtx[i][j] = Math.ceil(Math.random()*c);
			}
		}
		return mtx;

	}

	public static double [][] DCT2HomeMade(int i,int j,double [][] mat){
		double alfa_i,alfa_j,temp,x,sum,sum2;
		double [][] dct = new double [i][j];
		for(int c = 0; c < i; c++) {
			for(int h = 0; h < j; h++) {
				if(c==0) {
					alfa_i=1/Math.sqrt(i);
				}
				else
					alfa_i=Math.sqrt(2)/Math.sqrt(i);
				
				if(h==0) {
					alfa_j=1/Math.sqrt(j);
				}
				else
					alfa_j=Math.sqrt(2)/Math.sqrt(j);
				sum2=0;
				for(int k = 0; k < i; k++) {
					sum = 0;
					for(int l = 0; l < j; l++) {
						temp = mat[k][l]*Math.cos((2*l+1)*i*(3.14)/(2*j));
						sum=sum+temp;
					}
					x=Math.cos((2*k+1)*c*3.14/(2*i));
					sum=sum*x;
					sum2=sum2+sum;
				}
				dct[c][h] = alfa_i*alfa_j*sum2;
			}
		}
		return dct;
	}
}