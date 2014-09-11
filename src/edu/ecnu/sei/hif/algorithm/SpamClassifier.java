/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ecnu.sei.hif.algorithm;

import java.io.File;
import java.io.FileWriter;

/**
 *
 * @author If
 */
public class SpamClassifier {

    private String spamTrainSet = "D:\\workspace\\netbeans\\Project\\data\\spamTrainSet.txt";
    private String spanTrainSetScale = "D:\\workspace\\netbeans\\Project\\data\\spamTrainSetScale.txt";
    private String spamClassifyModel = "D:\\workspace\\netbeans\\Project\\data\\spamClassifyModel.txt";
    private String spamTestSet = "D:\\workspace\\netbeans\\Project\\data\\spamTestSet.txt";
    private String spamTestSetScale = "D:\\workspace\\netbeans\\Project\\data\\spanTestSetScale.txt";
    private String spamTestSetOutput = "D:\\workspace\\netbeans\\Project\\data\\spamTestSetOutput.txt";
    private String[] ham;
    private String[] spam;

    public SpamClassifier(String[] ham, String[] spam) {
        this.ham = ham;
        this.spam = spam;
    }

    public void train() {
        try {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < ham.length; i++) {
                SpamFeatureExtracter sfe = new SpamFeatureExtracter(ham[i], 0);
                sb.append(sfe.extract());
            }
            for (int i = 0; i < spam.length; i++) {
                SpamFeatureExtracter sfe = new SpamFeatureExtracter(ham[i], 1);
                sb.append(sfe.extract());
            }
            FileWriter fw = new FileWriter(spamTrainSet);
            fw.write(sb.toString());
            fw.flush();
            fw.close();

            svm_scale s = new svm_scale();
            String[] scalecmd = {spamTrainSet, spanTrainSetScale};
            s.run(scalecmd);

            svm_train t = new svm_train();
            String[] traincmd = {spanTrainSetScale, spamClassifyModel};
            t.run(traincmd);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void predict(String[] coming) {
        try {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < coming.length; i++) {
                SpamFeatureExtracter sfe = new SpamFeatureExtracter(coming[i]);
                sb.append(sfe.extract());
            }
            FileWriter fw = new FileWriter(spamTestSet);
            fw.write(sb.toString());
            fw.flush();
            fw.close();

            svm_scale s = new svm_scale();
            String[] scalecmd = {spamTestSet, spamTestSetScale};
            s.run(scalecmd);

            svm_predict p = new svm_predict();
            String[] predictcmd = {spamTestSetScale, spamClassifyModel, spamTestSetOutput};
            p.run(predictcmd);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*
     public static void main(String[] args) {
     File hamdir = new File("D:\\workspace\\netbeans\\Project\\data\\INBOX");
     File spamdir = new File("D:\\workspace\\netbeans\\Project\\data\\Junk");
     File incomingdir = new File("D:\\workspace\\netbeans\\Project\\data\\INCOMING");
     File[] hamemail = hamdir.listFiles();
     File[] spamemail = spamdir.listFiles();
     File[] incomingemail = incomingdir.listFiles();
     String[] ham = new String[hamemail.length];
     String[] spam = new String[spamemail.length];
     String[] incoming = new String[incomingemail.length];
     for (int i = 0; i < hamemail.length; i++) {
     ham[i] = hamemail[i].getAbsolutePath();
     }
     for (int i = 0; i < spamemail.length; i++) {
     spam[i] = spamemail[i].getAbsolutePath();
     }
     for (int i = 0; i < incomingemail.length; i++) {
     incoming[i] = incomingemail[i].getAbsolutePath();
     }

     SpamClassifier sc = new SpamClassifier(ham, spam);
     sc.train();
     sc.predict(incoming);

     }
     */
}
