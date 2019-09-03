package com.prosegur.core;

import java.io.IOException;

public class PythonRunTime {
	
	private static PythonRunTime instance;
	
	private PythonRunTime() {
		
	}
	
	public static PythonRunTime getInstance() {
		if(instance==null) {
			instance = new PythonRunTime();
		}
		return instance;
	}
	
	public void reload() {
		try {
			Runtime.getRuntime().exec("");
		} catch (IOException e) {
			
		}
	}
}
