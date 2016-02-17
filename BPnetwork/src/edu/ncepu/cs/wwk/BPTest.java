package edu.ncepu.cs.wwk;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BPTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("BPnetwork begins...");
		System.out.println("Start at: "+new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
		BPData bpd = new BPData();
		System.out.println("Reading data...");
		int flag  = bpd.readData("./data/2013-04.csv", 0);
		if(flag==0){
			double p [][] = bpd.getPdata();
			double t [][] = bpd.getTdata();
			System.out.println("Data read!");
			System.out.println("Start training...");
			BPConfig bpc = new BPConfig(p[0].length, 4, t[0].length,1000);
			BPNetwork bpn = new BPNetwork(bpc);
			bpn.train(p, t, 24);
			for(int i =0;i<24;i++){
			double[] result = bpn.sim(p[i]);
			for(double k: result){
				System.out.println(k);
			}
			}
		}else{
			System.out.println("error: Error code+ "+ flag);
		}
		System.out.println("End at: "+new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
	}
}
