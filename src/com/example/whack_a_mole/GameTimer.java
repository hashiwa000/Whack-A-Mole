package com.example.whack_a_mole;

import android.os.Handler;

public class GameTimer extends Handler implements Runnable {
	public static final int DEFAULT_TIME_LIMIT_MSEC = 30 * 1000;
	public static final int DEFAULT_MAX_COUNT = 100;

	private final MainActivity activity;
	
	private final int timeLimitMSec;
	private final int maxCount;
	private int count;
	
	public GameTimer(MainActivity activity) {
		this(activity, DEFAULT_TIME_LIMIT_MSEC, DEFAULT_MAX_COUNT);
	}
	public GameTimer(MainActivity activity,
			final int timeLimitMSec, final int maxCount) {
		super();
		
		activity.setProgressBarVisibility(true);
		activity.setProgress(0);
		
		this.activity = activity;
		this.timeLimitMSec = timeLimitMSec;
		this.maxCount = maxCount;
		
//		postDelayed(this, timeLimitMSec/maxCount);
	}
	
	@Override
	public void run() {
		if (count < maxCount) {
			count++;
			activity.setProgress(10000 * count / maxCount);
			postDelayed(this, timeLimitMSec/maxCount);
		} else {
			activity.finishGame();
		}
	}
	
	public void start() {
		postDelayed(this, timeLimitMSec/maxCount);
	}
	
	public void reset() {
		removeCallbacks(this);
		activity.setProgress(0);
		count = 0;
	}

}
