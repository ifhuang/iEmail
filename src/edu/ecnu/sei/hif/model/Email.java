package edu.ecnu.sei.hif.model;

import java.util.Date;


public interface Email
{
	public Address[] getFrom();
	public void setFrom(Address[] addr);
	
	public Address[] getSender();
	public void setSender(Address[] addr);
	
	public Address[] getTo();
	public void setTo(Address[] addr);
	
	public Address[] getCc();
	public void setCc(Address[] addr);
	
	public Address[] getBcc();
	public void setBcc(Address[] addr);
	
	public Address[] getNewsgroups();
	public void setNewsgroups(Address[] addr);
	
	public Address[] getReplyTo();
	public void setReplyTo(Address[] addr);
	
	public String getSubject();
	public void setSubject(String subj);
	
	public Date getSentDate();
	public void setSentDate(Date d);
	
	public Date getReceivedDate();
	
	public int getSize();
	
	public int getLineCount();
	
	public String getMessageID();
	
	public String getBodyPlain();
	public void setBodyPlain(String body);
	
	public String getBodyHtml();
	public void setBodyHtml(String body);
	
	public String getAttachment();
	public void setAttachment(String attach);
        
        public String getSignature();
}
