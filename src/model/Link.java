package model;

import java.util.ArrayList;

public class Link {	
	private String name;
	private String url;
	private String description;
	
	public Link(String name, String url, String desc){
		this.name = name ;
		this.url = url ;
		this.description = desc ;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
