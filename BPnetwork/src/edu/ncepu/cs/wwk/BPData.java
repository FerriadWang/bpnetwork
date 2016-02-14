package edu.ncepu.cs.wwk;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class BPData {
	private ArrayList<double[]> dataList;
	private double [][]data;
	public BPData() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * <p>This is a method of BPData class. It reads data by two ways:</p>
	 * <p>type = 0 ---> read data from a file whose URL is url.</p>
	 * <p>type = 1 ---> read data from a directory whose URL is url.</p>
	 * @author Ferriad*/
	public double[][] readData(String url,int type){
		
		//File dataFilePackage = new File(".\\rawdata\\");
		
		if(type==0){
			File file = new File(url);
			readTxtFile(file);
		}else{
			File dataFilePackage = new File(url);
			File[] dataFiles = dataFilePackage.listFiles();
			for(File file: dataFiles){
			readTxtFile(file);
			}
		}
		data = (double[][]) dataList.toArray();
		return data;		
	}
	private ArrayList<double[]> readTxtFile(File file) {
		// TODO Auto-generated method stub
		FileReader fileReader = null;
		BufferedReader br = null;
		String dataLine = null;
		try {
			fileReader=new FileReader(file);
			br=new BufferedReader(fileReader);
			while((dataLine=br.readLine())!=null){
				String[] strTemp = dataLine.split(",");
				double[] dataTemp = new double[strTemp.length];
				for(int i=0;i<strTemp.length;i++){  
				    dataTemp[i]=Integer.parseInt(strTemp[i]);
				}
				dataList.add(dataTemp);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return dataList;
	}

}
