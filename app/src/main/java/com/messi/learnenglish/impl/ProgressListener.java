package com.messi.learnenglish.impl;

public interface ProgressListener {

	void update(long bytesRead, long contentLength, boolean done);
	
}
