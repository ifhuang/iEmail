/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ecnu.sei.hif.algorithm;

import java.io.File;
import java.io.FileWriter;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author If
 */
public class ImportantClassifier {

    private static Set<String> dictionary;
    private static String NBmodel = "D:\\workspace\\netbeans\\Project\\data\\NBmodel.txt";
    private static String NBtermnum = "D:\\workspace\\netbeans\\Project\\data\\NBtermnum.txt";
    private static String NBdic = "D:\\workspace\\netbeans\\Project\\data\\NBdic.txt";
    private static String NBpredictdir = "D:\\workspace\\netbeans\\Project\\data\\";

    public ImportantClassifier() {
        dictionary = new TreeSet<String>();
    }

    public void train(String[] important, String[] normal) {
        try {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < important.length; i++) {
                ImportantFeatureExtracter ife = new ImportantFeatureExtracter(important[i]);
                String[] temp = ife.extract();
                for (int j = 0; j < temp.length; j++) {
                    dictionary.add(temp[j]);
                }
                sb.append(important[i]).append("\t").append(temp.length).append("\n");
            }
            for (int i = 0; i < normal.length; i++) {
                ImportantFeatureExtracter ife = new ImportantFeatureExtracter(normal[i]);
                String[] temp = ife.extract();
                for (int j = 0; j < temp.length; j++) {
                    dictionary.add(temp[j]);
                }
                sb.append(normal[i]).append("\t").append(temp.length).append("\n");
            }
            FileWriter fwdic = new FileWriter(NBdic);
            for (String t : dictionary) {
                fwdic.write(t + "\n");
                fwdic.flush();
            }
            fwdic.close();


            FileWriter fwterm = new FileWriter(NBtermnum);
            fwterm.write(sb.toString());
            fwterm.flush();
            fwterm.close();

            NBtrain t = new NBtrain();
            t.trainMultinomialNB(important, normal, NBdic, NBtermnum);
            t.saveResult(NBmodel);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void predict(String[] coming) {
        try {
            NBpredict p = new NBpredict(NBdic, NBmodel);
            for (int i = 0; i < coming.length; i++) {
                ImportantFeatureExtracter ife = new ImportantFeatureExtracter(coming[i]);
                String[] temp = ife.extract();
                p.applyMultinomialNB(temp);
                p.predict(NBpredictdir + "\\" + coming[i].substring(coming[i].lastIndexOf("\\")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     public static void main(String[] args) {
     File importantdir = new File("D:\\workspace\\netbeans\\Project\\data\\INBOX");
     File normaldir = new File("D:\\workspace\\netbeans\\Project\\data\\Junk");
     File incomingdir = new File("D:\\workspace\\netbeans\\Project\\data\\INCOMING");
     File[] importantemail = importantdir.listFiles();
     File[] normalemail = normaldir.listFiles();
     File[] incomingemail = incomingdir.listFiles();
     String[] important = new String[importantemail.length];
     String[] normal = new String[normalemail.length];
     String[] incoming = new String[incomingemail.length];
     for (int i = 0; i < importantemail.length; i++) {
     important[i] = importantemail[i].getAbsolutePath();
     }
     for (int i = 0; i < normalemail.length; i++) {
     normal[i] = normalemail[i].getAbsolutePath();
     }
     for (int i = 0; i < incomingemail.length; i++) {
     incoming[i] = incomingemail[i].getAbsolutePath();
     }

     ImportantClassifier ic = new ImportantClassifier();
     ic.train(important, normal);
     ic.predict(incoming);

     }
     */
}
