package edu.ecnu.sei.hif.control;

import javax.mail.Folder;
import javax.mail.Store;

public interface Server
{
        public Store getStore();
	public Folder getRootFolder();
}
