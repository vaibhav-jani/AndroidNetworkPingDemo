package com.example.networkpingdemo;

import java.util.LinkedList;
import java.util.List;

public class PingProcessBuilder {

	private String ip;

	public PingProcessBuilder(String ip) {

		this.ip = ip;
	}

	public ProcessRunnable create() {

		final List<String> cmd = new LinkedList<String>();

		//rtkrcv -t 4 -m 52001 -t 4
		cmd.add("/system/bin/ping");
		
		cmd.add("-c");
		
		cmd.add("1");

		cmd.add(ip);

		final ProcessBuilder pb = new ProcessBuilder(cmd);
		
		return new ProcessRunnable(pb);
	}
}
