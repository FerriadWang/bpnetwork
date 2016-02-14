package edu.ncepu.cs.wwk;

import java.util.Random;
/**
 * <p>BPNetwork</p>
 * <p>Description: </p>
 * <p>train the sample data with SGD��
 * adjust the connect weight</p>
 * <p>Institute: North China Electric Power U</p>
 * @author Weikang Wang
 * @version 1.1
 */
public class BPNetwork extends Object {
	
    public BPNetwork(BPConfig cf) {
        R = new Random();
        this.epochs = cf.getEpochs();
        int inNum = cf.getInNum();
        int hideNum = cf.getHideNum();
        int outNum = cf.getOutNum();
        input = new double[inNum]; 
        hidden = new double[hideNum]; 
        output = new double[outNum]; 
        o1 = new double[hideNum];
        o2 = new double[outNum];
        w_ih = new double[inNum][hideNum]; 
        w_ho = new double[hideNum][outNum]; 
        b1 = new double[hideNum]; 
        b2 = new double[outNum];
        correct_ih = new double[hideNum];
        correct_ho = new double[outNum];
        yd = new double[outNum];

        for (int i = 0; i < inNum; i++) {
            for (int j = 0; j < hideNum; j++) {
                w_ih[i][j] = R.nextDouble();
            }
        }
        for (int i = 0; i < hideNum; i++) {
            for (int j = 0; j < outNum; j++) {
                w_ho[i][j] = R.nextDouble();
            }
        }
        rate_w = cf.getRate_w(); 
        rate_w1 = cf.getRate_w1(); 
        rate_b1 = cf.getRate_b1(); 
        rate_b2 = cf.getRate_b2(); 
        e = cf.getE();
    }
    /*double p[][];
    double t[][];
    int samplenum;
    double psim[];
    public void setTrainDate(double p[][], double t[][]){
        this.p=p;
        this.t=t;
        samplenum=p.length;
    }
    public void setSimDate(double psim[]){
        this.psim=psim;
    }*/
    /**********************************/
    /*****BP�񾭿������㷨ѵ������*****/
    public void train(double p[][], double t[][],int samplenum) {
    	for(int epoch=0;epoch<epochs;epoch++){
        e = 0.0;
        //��һ��
        double pmax = 0.0;
        double tmax = 0.0;
        for (int isamp = 0; isamp < samplenum; isamp++) {
            for (int i = 0; i < inNum; i++) {
                if (Math.abs(p[isamp][i]) > pmax) {
                    pmax = Math.abs(p[isamp][i]);
                }
            }
            for (int j = 0; j < outNum; j++) {
                if (Math.abs(t[isamp][j]) > tmax) {
                	tmax = Math.abs(t[isamp][j]);
                }
            }
        } //end for isamp
        in_rate = pmax;
        out_rate = tmax;

        for (int isamp = 0; isamp < samplenum; isamp++) { //ѭ��ѵ��һ������
            for (int i = 0; i < inNum; i++) {
                input[i] = p[isamp][i] / in_rate;
            }
            for (int i = 0; i < outNum; i++) {
                yd[i] = t[isamp][i] / out_rate;
            }
        /*this.input=p[isamp];
        this.yd=t[isamp];*/
            //����ÿ������������������׼

            for (int j = 0; j < hideNum; j++) {
                o1[j] = 0.0;
                for (int i = 0; i < inNum; i++) {
                    o1[j] = o1[j] + w_ih[i][j] * input[i];
                }
                hidden[j] = 1.0 / (1.0 + Math.exp( -o1[j] - b1[j])); //
            }

            for (int k = 0; k < outNum; k++) {
                o2[k] = 0.0;
                for (int j = 0; j < hideNum; j++) {
                    o2[k] = o2[k] + w_ho[j][k] * hidden[j];
                }
                output[k] = 1.0 / (1 + Math.exp( -o2[k] - b2[k])); //
                //output[k]=o2[k]-b2[k];
                e += Math.abs(yd[k] - output[k]) * Math.abs(yd[k] - output[k]); //���������
            }

            //System.out.println("ok1");
            //update hidden layer weights
            for (int k = 0; k < outNum; k++) {
                correct_ho[k] = (yd[k] - output[k]) * output[k] * (1. - output[k]);
                //correct_ho[k]=(yd[k]-output[k]);
                //e+=Math.abs(yd[k]-output[k])*Math.abs(yd[k]-output[k]);//���������
                for (int j = 0; j < hideNum; j++) {
                    w_ho[j][k] = w_ho[j][k] + rate_w1 * correct_ho[k] * hidden[j];
                }
            }
            //update input layer weights
            for (int j = 0; j < hideNum; j++) {
                correct_ih[j] = 0.0;
                for (int k = 0; k < outNum; k++) {
                    correct_ih[j] = correct_ih[j] + correct_ho[k] * w_ho[j][k];
                }
                correct_ih[j] = correct_ih[j] * hidden[j] * (1. - hidden[j]);
                for (int i = 0; i < inNum; i++) {
                    w_ih[i][j] = w_ih[i][j] + rate_w * correct_ih[j] * input[i];
                }
            }

            for (int k = 0; k < outNum; k++) {
                b2[k] = b2[k] + rate_b2 * correct_ho[k];
            }
            for (int j = 0; j < hideNum; j++) {
                b1[j] = b1[j] + rate_b1 * correct_ih[j];
            }
        } //end isamp����ѭ��
        e = Math.sqrt(e);
    	}
    } //end train

    /***************************************/
    /*****BP�񾭿������㷨ģ����㺯��*******/
    public double[] sim(double psim[]) {
        for (int i = 0; i < inNum; i++) {
            input[i] = psim[i] / in_rate;
        }
        for (int j = 0; j < hideNum; j++) {
            o1[j] = 0.0;
            for (int i = 0; i < inNum; i++) {
                o1[j] = o1[j] + w_ih[i][j] * input[i];
            }
            hidden[j] = 1.0 / (1. + Math.exp( -o1[j] - b1[j]));
        }
        for (int k = 0; k < outNum; k++) {
            o2[k] = 0.0;
            for (int j = 0; j < hideNum; j++) {
                o2[k] = o2[k] + w_ho[j][k] * hidden[j];
            }
            output[k] = 1.0 / (1.0 + Math.exp( -o2[k] - b2[k]));
            //output[k]=o2[k]-b2[k];
            output[k] = in_rate * output[k];
        }
        return output;
    } //end sim
    double input[]; //��������
	double hidden[]; //�����ӵ�״ֵ̬
	double output[]; //����ӵ�״ֵ̬
	double o1[];//�ݴ��һ�����ֵ
	double o2[];//�ݴ�ڶ������ֵ
	double w_ih[][]; //�����ӵ�Ȩֵ
	double w_ho[][]; //����ӵ�Ȩֵ
	double b1[]; //�����ӵ㷧ֵ
    double b2[]; //����ӵ㷧ֵ
    double correct_ih[];//������������ĵ���ֵ
    double correct_ho[];//������������ĵ���ֵ
    double yd[];
    double rate_w; //Ȩֵѧϰ�ʣ������-������)
    double rate_w1; //Ȩֵѧϰ�� (������-�����)
    double rate_b1; //�����㷧ֵѧϰ��
    double rate_b2; //����㷧ֵѧϰ��
    double e;
    double in_rate; //�����һ������ϵ��
    double out_rate;//�����һ������ϵ��
    int epochs;//��������
    int inNum; //����ӵ���
	int hideNum; //�����ӵ���
	int outNum; //����ӵ���
    Random R;
} //end bp class

