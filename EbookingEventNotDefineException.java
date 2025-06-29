package ebook.controller;

public class EbookingEventNotDefineException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public EbookingEventNotDefineException(String event) {
		super(event);
	}

}
