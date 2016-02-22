package edu.ncepu.cs.wwk.bpnetwork;

import java.util.Random;

public class BPConfig {
	
	public int getInNum() {
		return inNum;
	}
	/**
	 * <p>public BPConfig(int inNum, int hideNum, int outNum)</p>
	 * <p><b>inNum</b>: input layer node number</p>
	 * <p><b>hideNum</b>: hide layer node number</p>
	 * <p><b>inNum</b>: output layer node number</p>
	 * <p><b>ephochs</b>: output layer node number</p>*/
	public BPConfig(int inNum, int hideNum, int outNum, int epochs) {
		super();
		this.inNum = inNum;
		this.hideNum = hideNum;
		this.outNum = outNum;
		this.epochs = epochs;
		this.rate_w = 0.08; //Ȩֵѧϰ�ʣ������-������)
		this.rate_w1 = 0.08; //Ȩֵѧϰ�� (������-�����)
		this.rate_b1 = 0.05; //�����㷧ֵѧϰ��
		this.rate_b2 = 0.05; //����㷧ֵѧϰ��
		this.e = 0.00;
	}
	
	/**
	 * <p>public BPConfig(int inNum, int hideNum, int outNum)</p>
	 * <p><b>inNum</b>: input layer node number</p>
	 * <p><b>hideNum</b>: hide layer node number</p>
	 * <p><b>inNum</b>: output layer node number</p>*/
	public BPConfig(int inNum, int hideNum, int outNum) {
		super();
		this.inNum = inNum;
		this.hideNum = hideNum;
		this.outNum = outNum;
		this.epochs = 500;
		this.rate_w = 0.01; //Ȩֵѧϰ�ʣ������-������)
		this.rate_w1 = 0.01; //Ȩֵѧϰ�� (������-�����)
		this.rate_b1 = 0.02; //�����㷧ֵѧϰ��
		this.rate_b2 = 0.02; //����㷧ֵѧϰ��
		this.e = 0.00;
	}
	public void setInNum(int inNum) {
		this.inNum = inNum;
	}
	public int getHideNum() {
		return hideNum;
	}
	public void setHideNum(int hideNum) {
		this.hideNum = hideNum;
	}
	public int getOutNum() {
		return outNum;
	}
	public void setOutNum(int outNum) {
		this.outNum = outNum;
	}
	public Random getR() {
		return R;
	}
	public void setR(Random r) {
		R = r;
	}
	public int getEpochs() {
		return epochs;
	}
	public void setEpochs(int epochs) {
		this.epochs = epochs;
	}
	public double getRate_w() {
		return rate_w;
	}
	public void setRate_w(double rate_w) {
		this.rate_w = rate_w;
	}
	public double getRate_w1() {
		return rate_w1;
	}
	public void setRate_w1(double rate_w1) {
		this.rate_w1 = rate_w1;
	}
	public double getRate_b1() {
		return rate_b1;
	}
	public void setRate_b1(double rate_b1) {
		this.rate_b1 = rate_b1;
	}
	public double getRate_b2() {
		return rate_b2;
	}
	public void setRate_b2(double rate_b2) {
		this.rate_b2 = rate_b2;
	}
	public double getE() {
		return e;
	}
	public void setE(double e) {
		this.e = e;
	}
	public double getDe() {
		return de;
	}
	public void setDe(double de) {
		this.de = de;
	}
	public double getIn_rate() {
		return in_rate;
	}
	public void setIn_rate(double in_rate) {
		this.in_rate = in_rate;
	}
	public double getOut_rate() {
		return out_rate;
	}
	public void setOut_rate(double out_rate) {
		this.out_rate = out_rate;
	}
	protected int inNum; //����ӵ���
	protected int hideNum; //�����ӵ���
	protected int outNum; //����ӵ���
	protected Random R;
	protected int epochs;//��������
	protected double rate_w; //Ȩֵѧϰ�ʣ������-������)
	protected double rate_w1; //Ȩֵѧϰ�� (������-�����)
	protected double rate_b1; //�����㷧ֵѧϰ��
	protected double rate_b2; //����㷧ֵѧϰ��
	protected double e;
	protected double de;
	protected double in_rate; //�����һ������ϵ��
	protected double out_rate;//�����һ������ϵ��
}
