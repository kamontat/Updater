package com.kamontat.code.object;

/**
 * @author kamontat
 * @version 1.0
 * @since Thu 09/Mar/2017 - 12:00 AM
 */
public class Owner {
	private String name;
	private String projectName;
	
	public Owner(String name, String projectName) {
		this.name = name;
		this.projectName = projectName;
	}
	
	public String getName() {
		return name;
	}
	
	public String getProjectName() {
		return projectName;
	}
}
