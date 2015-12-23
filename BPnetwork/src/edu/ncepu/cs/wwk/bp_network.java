package edu.ncepu.cs.wwk;

import java.util.Random;

/**
 * <p>BP神经网络</p>
 * <p>Description: </p>
 * <p>利用梯度下降法训练输入样本，
 * 调节连接权重</p>
 * <p>Company: </p>
 * @author xuwei
 * @version 1.0
 */
public class bp_network extends Object {
    int inNum; //输入接点数
    int hideNum; //隐含接点数
    int outNum; //输出接点数
    Random R;
    int epochs;//迭代期数

    double input[]; //输入向量
    double hidden[]; //隐含接点状态值
    double output[]; //输出接点状态值
    double o1[];//暂存第一层输出值
    double o2[];//暂存第二层输出值
    double w_ih[][]; //隐含接点权值
    double w_ho[][]; //输出接点权值
    double rate_w; //权值学习率（输入层-隐含层)
    double rate_w1; //权值学习率 (隐含层-输出层)
    double rate_b1; //隐含层阀值学习率
    double rate_b2; //输出层阀值学习率
    double b1[]; //隐含接点阀值
    double b2[]; //输出接点阀值
    double correct_ih[];//输入层的隐含层的调整值
    double correct_ho[];//隐含层和输出层的调整值
    double yd[];
    double e;
    double de;
    double in_rate; //输入归一化比例系数
    double out_rate;//输出归一化比例系数
    
    public bp_network(int inNum, int hideNum, int outNum) {
        R = new Random();
        this.epochs = 500;
        this.inNum = inNum;
        this.hideNum = hideNum;
        this.outNum = outNum;
        input = new double[inNum]; //输入向量
        hidden = new double[hideNum]; //隐含接点状态值
        output = new double[outNum]; //输出接点状态值

        o1 = new double[hideNum];
        o2 = new double[outNum];
        w_ih = new double[inNum][hideNum]; //隐含接点权值
        w_ho = new double[hideNum][outNum]; //输出接点权值
        b1 = new double[hideNum]; //隐含接点阀值
        b2 = new double[outNum]; //输出接点阀值
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
        rate_w = 0.08; //权值学习率（输入层-隐含层)
        rate_w1 = 0.08; //权值学习率 (隐含层-输出层)
        rate_b1 = 0.05; //隐含层阀值学习率
        rate_b2 = 0.05; //输出层阀值学习率
        e = 0.0;
        de = 0;
        in_rate = 1.0; //输入归一化系数
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
    /*****BP神经控制器算法训练函数*****/
    public void train(double p[][], double t[][],int samplenum) {
    	for(int epoch=0;epoch<epochs;epoch++){
        e = 0.0;
        //归一化
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

        for (int isamp = 0; isamp < samplenum; isamp++) { //循环训练一次样本
            for (int i = 0; i < inNum; i++) {
                input[i] = p[isamp][i] / in_rate;
            }
            for (int i = 0; i < outNum; i++) {
                yd[i] = t[isamp][i] / out_rate;
            }
        /*this.input=p[isamp];
        this.yd=t[isamp];*/
            //构造每个样本的输入和输出标准

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
                e += Math.abs(yd[k] - output[k]) * Math.abs(yd[k] - output[k]); //计算均方差
            }

            //System.out.println("ok1");
            //update hidden layer weights
            for (int k = 0; k < outNum; k++) {
                correct_ho[k] = (yd[k] - output[k]) * output[k] * (1. - output[k]);
                //correct_ho[k]=(yd[k]-output[k]);
                //e+=Math.abs(yd[k]-output[k])*Math.abs(yd[k]-output[k]);//计算均方差
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
        } //end isamp样本循环
        e = Math.sqrt(e);
    	}
    } //end train

    /***************************************/
    /*****BP神经控制器算法模拟计算函数*****/
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
    public int getInNum() {
		return inNum;
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
	public int getEpochs() {
		return epochs;
	}
	public void setEpochs(int epochs) {
		this.epochs = epochs;
	}
	

	
} //end bp class

