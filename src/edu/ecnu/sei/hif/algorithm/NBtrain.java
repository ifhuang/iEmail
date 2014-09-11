package edu.ecnu.sei.hif.algorithm;

import edu.ecnu.sei.hif.model.Email;
import edu.ecnu.sei.hif.model.EmailImpl;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class NBtrain {

    private static ArrayList<String> V;
    private static long N;
    private static ArrayList<String> C;
    private static double prior[];
    private static double condprob[][];

    public NBtrain() {

        C = new ArrayList<String>();
        C.add("will reply");
        C.add("will not reply");

        prior = new double[C.size()];
    }

    public void trainMultinomialNB(String[] important, String[] normal, String dic,
            String termnum) throws Exception {
        V = extractVocabulary(dic);
        condprob = new double[V.size()][C.size()];
        N = important.length + normal.length;
        for (int i = 0; i < C.size(); i++) {
            long Nc;
            if (i == 0) {
                Nc = important.length;
            } else {
                Nc = normal.length;
            }
            prior[i] = (double) Nc / (double) N;
            String TESTc = concatenateTextOfAllDocsInClass(i, important, normal);
            long T[] = new long[V.size()];
            for (int j = 0; j < V.size(); j++) {
                if (j % 1000 == 0) {
                    System.out.println("countTokenOfTerm " + j + "/" + V.size());
                }
                T[j] = countTokenOfTerm(TESTc, V.get(j));
            }
            for (int j = 0; j < V.size(); j++) {
                if (j % 1000 == 0) {
                    System.out.println("compute " + j + "/" + V.size());
                }
                condprob[j][i] = compute(T[j], i, important, normal, termnum);
            }
        }
    }

    public void saveResult(String model) {
        try {
            FileWriter fw = new FileWriter(model);
            fw.write("prior:\n");
            for (int i = 0; i < C.size(); i++) {
                fw.write(i + "\t" + prior[i] + "\n");
            }
            fw.flush();
            fw.write("condprob:\n");
            for (int i = 0; i < V.size(); i++) {
                fw.write(i + "\t");
                for (int j = 0; j < C.size(); j++) {
                    fw.write(condprob[i][j] + "\t");
                }
                fw.write("\n");
            }
            fw.flush();
            fw.close();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    // --------------------------------------------------------------------------------
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

    private String concatenateTextOfAllDocsInClass(int c, String[] important,
            String[] normal) {
        String[] temp;
        if (c == 0) {
            temp = important;
        } else {
            temp = normal;
        }
        StringBuffer sb = new StringBuffer();
        try {
            long start, end;
            System.out.println("concatenateTextOfAllDocsInClass Begin");
            start = System.currentTimeMillis();

            for (int i = 0; i < temp.length; i++) {
                Email email = new EmailImpl(temp[i]);
                String subject = email.getSubject();
                String bodyplain = email.getBodyPlain();
                sb.append(subject).append(" ").append(bodyplain);
            }

            end = System.currentTimeMillis();
            System.out.println("concatenateTextOfAllDocsInClass End");
            System.out.println("elapsed:" + ((end - start) / 1000.) + "s");

            return sb.toString();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return sb.toString();
        }
    }

    private long countTokenOfTerm(String TESTc, String v) {
        if (TESTc == null || TESTc.equals("") || v == null || v.equals("")) {
            return 0;
        }
        try {
            // return TESTc.split(v).length - 1;
            return (TESTc.length() - TESTc.replaceAll(v, "").length())
                    / v.length();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return 0;
        }
    }

    private double compute(long Tj, int i, String[] important, String[] normal, String tpath) {
        long count = 0;
        try {
            String[] temp;
            if (i == 0) {
                temp = important;
            } else {
                temp = normal;
            }
            BufferedReader br = new BufferedReader(new FileReader(tpath));
            String line = br.readLine();
            while (line != null) {
                String[] part = line.split("\t");
                for (int j = 0; j < temp.length; j++) {
                    if (part[0].equals(temp[j])) {
                        count += Integer.parseInt(part[1]);
                        break;
                    }
                }
                line = br.readLine();
            }
            return (double) (Tj + 1) / (double) (count + V.size());
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return count;
        }
    }

    static void Usage() {
        System.err
                .println("Usage: NBtrain Dir ClassMark.txt dic.txt termnum.txt model.txt");
        System.exit(1);
    }

    /*
    public static void main(String args[]) throws Exception {
        if (args.length != 5) {
            Usage();
        }

        //NBtrain NB = new NBtrain();
        //NB.trainMultinomialNB(args[0], args[1], args[2], args[3]);
        //NB.saveResult(args[4]);
    }
    */
}
