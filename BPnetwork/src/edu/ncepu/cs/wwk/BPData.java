package edu.ncepu.cs.wwk;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class BPData {
	private ArrayList<double[]> pDataList;
	private ArrayList<Double> tDataList;
	private double [][] pdata;
	private double [][] tdata;
	public BPData() {
		super();
		pDataList = new ArrayList<double[]>();
		tDataList = new ArrayList<Double>();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * <br>This is a method of BPData class. It reads pdata by two ways:</br>
	 * <br>type = 0 ---> read pdata from a file whose URL is url.</br>
	 * <br>type = 1 ---> read pdata from a directory whose URL is url.</br>
	 * <br>After the data are read, they are stored in pdata and tdata</br>
	 * <br>User can use getter method to obtain pdat aand tdata</br>
	 * @author Ferriad*/
	public int readData(String url,int type){
		int flag = 0;
		//File dataFilePackage = new File(".\\rawdata\\");
		if(type==0){
			File file = new File(url);
			flag = readTxtFile(file);
		}else if(type==1){
			File dataFilePackage = new File(url);
			File[] dataFiles = dataFilePackage.listFiles();
			for(File file: dataFiles){
			flag = readTxtFile(file);
			}
		}
		pdata = new double[pDataList.size()][];
		for(int i = 0; i < pdata.length; i++){
			pdata[i]=pDataList.get(i);
		}
		tdata = new double[tDataList.size()][1];
		for(int i = 0; i < tdata.length; i++){
			tdata[i][0]=tDataList.get(i);
		}
		//pdata = (double[][]) pDataList.toArray();
		System.out.println(pdata.length);
		System.out.println(tdata.length);
		return flag;		
	}
	
	@SuppressWarnings("resource")
	private int readTxtFile(File file) {
		// TODO Auto-generated method stub
		FileReader fileReader = null;
		BufferedReader br = null;
		String dataLine = null;
		try {
			fileReader=new FileReader(file);
			br=new BufferedReader(fileReader);
			br.readLine();
			while((dataLine=br.readLine())!=null){
				String[] strTemp = dataLine.split(",");
				double[] pDataTemp = new double[strTemp.length-1];
				double tDataTemp = 0.00;
				for(int i=0;i<strTemp.length;i++){  
				    if(i<strTemp.length-1){
				    	pDataTemp[i]=Double.parseDouble(strTemp[i]);
					}else{
						tDataTemp=Double.parseDouble(strTemp[i]);	
					}
				}
				pDataList.add(pDataTemp);
				tDataList.add(tDataTemp);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 1;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 2;
		} 
		return 0;
	}
	
	public double[][] getPdata() {
		return pdata;
	}

	public double[][] getTdata() {
		return tdata;
	}
}
