package edu.ncepu.cs.wwk;

public class BPTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BPData bpd = new BPData();
		int flag  = bpd.readData("./data/2013-01.csv", 0);
		double k1 [][] = bpd.getPdata();
		double k2 [][] = bpd.getTdata();
		for(int i = 0; i<k1.length; i++){
			for(int j  = 0; j<k1[i].length;j++){
				System.out.println(k1[i][j]);
			}
		}
	}

}
