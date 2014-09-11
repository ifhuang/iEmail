/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ecnu.sei.hif.algorithm;

import edu.ecnu.sei.hif.model.Email;
import edu.ecnu.sei.hif.model.EmailImpl;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

/**
 *
 * @author If
 */
public class ImportantFeatureExtracter {

    private String path;

    public ImportantFeatureExtracter(String path) {
        this.path = path;
    }

    public String[] extract() {
        try {
            Email email = new EmailImpl(path);
            String subject = email.getSubject();
            if (subject == null) {
                subject = "";
            }
            String bodyplain = email.getBodyPlain();
            if (bodyplain == null) {
                bodyplain = "";
            }
            String temp = subject + " " + bodyplain;
            IKAnalyzer ikAnalyzer = new IKAnalyzer();
            Reader reader = new StringReader(temp);
            TokenStream stream = (TokenStream) ikAnalyzer.tokenStream("",
                    reader);
            TermAttribute termAtt = (TermAttribute) stream
                    .addAttribute(TermAttribute.class);
            ArrayList<String> array = new ArrayList<String>();
            while (stream.incrementToken()) {
                array.add(termAtt.term());
            }
            String[] result = new String[array.size()];
            result = array.toArray(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
/*
    public static void main(String[] args) {
        ImportantFeatureExtracter ife = new ImportantFeatureExtracter("D:\\workspace\\netbeans\\Project\\data\\INBOX\\0");
        String[] term = ife.extract();
        for (int i = 0; i < term.length; i++) {
            System.out.println(term[i]);
        }
    }
    * */
}
