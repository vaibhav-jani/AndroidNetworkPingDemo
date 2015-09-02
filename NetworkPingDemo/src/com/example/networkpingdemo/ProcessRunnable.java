package com.example.networkpingdemo;

import java.io.IOException;
import java.io.InputStream;

import android.util.Log;

public class ProcessRunnable implements Runnable {

	private static final String TAG = "ProcessRunnable";
	
	private final ProcessBuilder processBuilder;
	
	private ProcessListener mListener;
	
	public ProcessRunnable(ProcessBuilder processBuilder) {
	
		this.processBuilder = processBuilder;
	}
	
	@Override
	public void run() {
		
		Process process = null;
		
		try {
		
			process = processBuilder.start();
		
		} catch (IOException e) {
		
			Log.e(TAG, "IOException starting process", e);
			
			return;
		}
		
		// Consume the stdout and stderr
		if (mListener != null) {
			
			mListener.stdOut(process.getInputStream());
			mListener.stdErr(process.getErrorStream());
		}
		
		// Wait for process to exit
		int exitCode = 1; // Assume error
		
		try {
		
			exitCode = process.waitFor();
		
		} catch (InterruptedException e) {
		
			Log.e(TAG, "Process interrupted!", e);
		}
		
		if (mListener != null) {
			
			mListener.onExit(exitCode);
		}
	}
	
	public void setProcessListener(ProcessListener listener) {
	
		mListener = listener;
	}
	
	public interface ProcessListener {
		
		public void stdOut(InputStream stream);
		public void stdErr(InputStream stream);
		
		public void onExit(int exitCode);
	}

}