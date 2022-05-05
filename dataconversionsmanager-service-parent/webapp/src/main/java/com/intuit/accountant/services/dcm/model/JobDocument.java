package com.intuit.accountant.services.dcm.model;

public class JobDocument {
	
	private String DocURI;
	private String DocRepository;
	private Long size;
	private String format;
	private String md5hash;
	
	
	public String getDocURI() {
		return DocURI;
	}
	public void setDocURI(String docURI) {
		DocURI = docURI;
	}
	public String getDocRepository() {
		return DocRepository;
	}
	public void setDocRepository(String docRepository) {
		DocRepository = docRepository;
	}
	public Long getSize() {
		return size;
	}
	public void setSize(Long size) {
		this.size = size;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getMd5hash() {
		return md5hash;
	}
	public void setMd5hash(String md5hash) {
		this.md5hash = md5hash;
	}
	


}
