package com.example.networkpingdemo;

import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.example.networkpingdemo.ProcessRunnable.ProcessListener;

public class MainActivity extends Activity {

	private TextView tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		tv = (TextView) findViewById(R.id.tv);

		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {

				try {
					
					/*
					InetAddress address = InetAddress
							.getByName("http://www.google.com");

					String myIP = address.getHostAddress();

					try {

						String[] parts = myIP.split("\\.");
						List<String> arrayList = Arrays.asList(parts);
						Collections.reverse(arrayList);

						myIP = "";

						for (String string : arrayList) {

							myIP += string + ".";
						}

						myIP = myIP.substring(0, myIP.length() - 1);

					} catch (Exception e) {

						e.printStackTrace();
					}*/

					String myIP = "www.google.com";
					PingProcessBuilder job = new PingProcessBuilder(myIP );

					ProcessRunnable processRunnable = job.create();

					processRunnable.setProcessListener(new ProcessListener() {

						@Override
						public void stdOut(InputStream stream) {

							java.util.Scanner s = new java.util.Scanner(stream)
									.useDelimiter("\\A");

							while (s.hasNext()) {

								String logText = "Out : " + s.next();
								System.out.println(logText);
								setText(logText, MainActivity.this);
							}

						}

						@Override
						public void stdErr(InputStream stream) {

							java.util.Scanner s = new java.util.Scanner(stream)
									.useDelimiter("\\A");

							while (s.hasNext()) {

								String logText = "Error : " + s.next();
								System.out.println(logText);
								setText(logText, MainActivity.this);
							}

						}

						@Override
						public void onExit(int exitCode) {

							String logText = "Exit : " + "" + exitCode;

							System.out.println(logText);
							setText(logText, MainActivity.this);
						}

					});

					processRunnable.run();

					// new Thread(processRunnable).start();

				} catch (Exception e) {

					e.printStackTrace();
				}

				return null;

			}

		}.execute();

	}

	protected void setText(final String logText, MainActivity mainActivity) {

		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				tv.setText(tv.getText() + "\n\n" + logText);
			}
		});
	}

}
