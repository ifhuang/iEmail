package edu.ecnu.sei.hif.algorithm;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class NBpredict {

    private static double[] score;
    private static ArrayList<String> C;
    private static double prior[];
    private static double condprob[][];
    private static ArrayList<String> V;

    public NBpredict(String dic, String model) throws Exception {

        C = new ArrayList<String>();
        C.add("will reply");
        C.add("will not reply");

        V = extractVocabulary(dic);

        prior = new double[C.size()];

        condprob = new double[V.size()][C.size()];

        BufferedReader br = new BufferedReader(new FileReader(model));
        br.readLine();
        for (int i = 0; i < C.size(); i++) {
            String line = br.readLine();
            String[] part = line.split("\t");
            prior[i] = Double.parseDouble(part[1]);
        }

        br.readLine();
        for (int i = 0; i < V.size(); i++) {
            String line = br.readLine();
            String[] part = line.split("\t");
            for (int j = 0; j < C.size(); j++) {
                condprob[i][j] = Double.parseDouble(part[j + 1]);
            }
        }

    }

    private ArrayList<String> extractVocabulary(String dicpath) {
        try {
            long start, end;
            System.out.println("extractVocabulary Begin");
            start = System.currentTimeMillis();

            ArrayList<String> V = new ArrayList<String>();
            String line;
            InputStreamReader insReader = new InputStreamReader(
                    new FileInputStream(dicpath), "gb2312");
            BufferedReader br = new BufferedReader(insReader);
            br.readLine();
            line = br.readLine();
            while (line != null) {
                V.add(line);
                line = br.readLine();
            }

            end = System.currentTimeMillis();
            System.out.println("extractVocabulary End");
            System.out.println("elapsed:" + ((end - start) / 1000.) + "s");

            return V;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return null;
        }

    }

    public void applyMultinomialNB(String[] temp) {
        score = new double[C.size()];
        for (int i = 0; i < C.size(); i++) {
            score[i] = Math.log(prior[i]);
            for (int j = 0; j < temp.length; j++) {
                if (V.contains(temp[j])) {
                    score[i] += Math.log(condprob[V.indexOf(temp[j])][i]);
                }
            }
        }
    }

    public void predict(String predict) throws Exception {
        FileWriter fw = new FileWriter(predict);
        fw.write("score:\n");
        for (int i = 0; i < C.size(); i++) {
            fw.write(i + "\t" + score[i] + "\n");
        }
        fw.flush();
        fw.close();
    }

    private ArrayList<String> extractTokenFromDoc(String docpath) {
        try {
            long start, end;
            System.out.println("extractTokenFromDoc Begin");
            start = System.currentTimeMillis();

            ArrayList<String> W = new ArrayList<String>();
            String line;
            InputStreamReader insReader = new InputStreamReader(
                    new FileInputStream(docpath), "UTF8");
            BufferedReader br = new BufferedReader(insReader);
            line = br.readLine();
            while (line != null) {
                if (V.contains(line)) {
                    W.add(line);
                }
                line = br.readLine();
            }

            end = System.currentTimeMillis();
            System.out.println("extractTokenFromDoc End");
            System.out.println("elapsed:" + ((end - start) / 1000.) + "s");

            return W;
        } catch (Exception e) { // TODO: handle exception
            e.printStackTrace();
            return null;
        }
    }

    static void Usage() {
        System.err
                .println("Usage: NBpredict dic.txt model.txt IncomingDir PredictDir");
        System.exit(1);
    }
    /*
     public static void main(String args[]) throws Exception {
     if (args.length != 4) {
     Usage();
     }

     //NBpredict nb = new NBpredict(args[0], args[1]);

     //File f = new File(args[2]);
     //File[] files = f.listFiles();

     //for (int i = 0; i < files.length; i++) {
     //    nb.applyMultinomialNB(files[i].getAbsolutePath());
     //    nb.predict(args[3] + "\\" + files[i].getName());
     //}

     }
     */
}
