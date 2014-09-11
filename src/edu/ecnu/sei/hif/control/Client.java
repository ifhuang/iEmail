package edu.ecnu.sei.hif.control;

import edu.ecnu.sei.hif.algorithm.ImportantClassifier;
import edu.ecnu.sei.hif.algorithm.SpamClassifier;
import edu.ecnu.sei.hif.model.Address;
import edu.ecnu.sei.hif.model.ComputedAddress;
import edu.ecnu.sei.hif.model.EmailLabel;
import edu.ecnu.sei.hif.model.Record;
import edu.ecnu.sei.hif.view.EmailNetworkViewer;
import edu.ecnu.sei.hif.view.FolderViewer;
import edu.ecnu.sei.hif.view.FolderViewerC1;
import edu.ecnu.sei.hif.view.FolderViewerC2;
import edu.ecnu.sei.hif.view.FolderViewerC3;
import edu.ecnu.sei.hif.view.FolderViewerC4;
import edu.ecnu.sei.hif.view.FolderViewerC5;
import edu.ecnu.sei.hif.view.FolderViewerC6;
import edu.ecnu.sei.hif.view.FolderViewerI1;
import edu.ecnu.sei.hif.view.FolderViewerN1;
import edu.ecnu.sei.hif.view.FolderViewerN2;
import edu.ecnu.sei.hif.view.FolderViewerN3;
import edu.ecnu.sei.hif.view.MainViewer;
import edu.ecnu.sei.hif.view.MessageViewer;
import java.util.List;
import org.dom4j.Document;

public class Client {

    public static FolderViewer fv;
    public static MessageViewer mv;
    public static FolderViewerC1 fvcspam1;
    public static FolderViewerC2 fvcspam2;
    public static SpamClassifier sc;
    public static List<EmailLabel> spamlabel;
    public static FolderViewerC3 fvcspam3;
    public static List<String> spamtest;
    public static FolderViewerC4 fvcimportant4;
    public static FolderViewerC5 fvcimportant5;
    public static FolderViewerC6 fvcimportant6;
    public static List<EmailLabel> importantlabel;
    public static ImportantClassifier icf;
    public static List<String> importanttest;
    public static EmailNetworkViewer envinbox;
    public static EmailNetworkViewer envsent;
    public static EmailNetworkViewer envall;
    public static FolderViewerN1 fvn1;
    public static FolderViewerN2 fvn2;
    public static FolderViewerN3 fvn3;
    public static FolderViewerI1 fvi1;
    
    public static Address[] addressBook;
    public static Record[] record;
    public static ComputedAddress[] computedAddress;
    public static Document network;

    /**
     * @param args
     */
    public static void main(String[] args) {
        MainViewer mainviewer = new MainViewer();
        mainviewer.setVisible(true);
    }
}
