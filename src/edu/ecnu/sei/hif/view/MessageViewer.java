package edu.ecnu.sei.hif.view;

import edu.ecnu.sei.hif.model.Address;
import edu.ecnu.sei.hif.model.Email;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.activation.*;
import javax.swing.JPanel;

/**
 * @author	If
 */
public class MessageViewer extends JPanel {

    Email displayed = null;
    DataHandler dataHandler = null;
    String verb = null;
    TextArea mainbody;
    TextArea headers;

    public MessageViewer() {
        this(null);
    }

    public MessageViewer(Email what) {
        // set our layout
        super(new GridBagLayout());

        // add the toolbar
        addToolbar();

        GridBagConstraints gb = new GridBagConstraints();
        gb.gridwidth = GridBagConstraints.REMAINDER;
        gb.fill = GridBagConstraints.BOTH;
        gb.weightx = 1.0;
        gb.weighty = 0.0;

        // add the headers
        headers = new TextArea("", 4, 80, TextArea.SCROLLBARS_NONE);
        headers.setEditable(false);
        add(headers, gb);

        // now display our message
        setMessage(what);
    }

    /**
     * sets the current message to be displayed in the viewer
     */
    public void setMessage(Email what) {
        displayed = what;

        if (mainbody != null) {
            remove(mainbody);
        }

        if (what != null) {
            loadHeaders();
            loadBodyComponent();
        } else {
            headers.setText("");
            TextArea dummy = new TextArea("", 24, 80, TextArea.SCROLLBARS_NONE);
            dummy.setEditable(false);
            mainbody = dummy;
        }

        // add the main body
        GridBagConstraints gb = new GridBagConstraints();
        gb.gridwidth = GridBagConstraints.REMAINDER;
        gb.fill = GridBagConstraints.BOTH;
        gb.weightx = 1.0;
        gb.weighty = 1.0;
        add(mainbody, gb);

        invalidate();
        validate();
    }

    protected void addToolbar() {
        GridBagConstraints gb = new GridBagConstraints();
        gb.gridheight = 1;
        gb.gridwidth = 1;
        gb.fill = GridBagConstraints.NONE;
        gb.anchor = GridBagConstraints.WEST;
        gb.weightx = 0.0;
        gb.weighty = 0.0;
        gb.insets = new Insets(4, 4, 4, 4);

        // structure button
        gb.gridwidth = GridBagConstraints.REMAINDER; // only for the last one
    }

    protected void loadHeaders() {
        // setup what we want in our viewer
        StringBuilder sb = new StringBuilder();

        // date
        sb.append("Date: ");
        try {
            Date duh = displayed.getSentDate();
            if (duh != null) {
                SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E");
                sb.append(dateformat.format(duh));
            } else {
                sb.append("Unknown");
            }

            sb.append("\n");

            // from
            sb.append("From: ");
            Address[] adds = displayed.getFrom();
            if (adds != null && adds.length > 0) {
                sb.append(adds[0].toString());
            }
            sb.append("\n");

            // to
            sb.append("To: ");
            adds = displayed.getTo();
            if (adds != null && adds.length > 0) {
                for (int i = 0; i < adds.length; i++) {
                    sb.append(adds[i].toString()).append(";");
                }
            }
            sb.append("\n");

            // to
            sb.append("Cc: ");
            adds = displayed.getCc();
            if (adds != null && adds.length > 0) {
                for (int i = 0; i < adds.length; i++) {
                    sb.append(adds[i].toString()).append(";");
                }
            }
            sb.append("\n");

            // subject
            sb.append("Subject: ");
            sb.append(displayed.getSubject());

            headers.setText(sb.toString());
        } catch (Exception me) {
            headers.setText("");
        }
    }

    protected void loadBodyComponent() {
        StringBuilder sb = new StringBuilder();
        sb.append("Body:\n");
        if (displayed.getBodyPlain() != null) {
            sb.append(displayed.getBodyPlain());
        } else if (displayed.getBodyHtml() != null) {
            sb.append(displayed.getBodyHtml());
        }
        sb.append("\n");
        sb.append("Attachment:\n");
        sb.append(displayed.getAttachment());
        mainbody.setText(sb.toString());
    }
}
