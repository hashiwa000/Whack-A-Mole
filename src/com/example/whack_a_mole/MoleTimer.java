package com.example.whack_a_mole;

import android.os.Handler;
import android.util.Log;

public class MoleTimer extends Handler {
	
	Runnable popupTask, vanishTask;

	// 出現する間隔を取得する。
	// 1sec - 9sec の間でランダム化する。
	private static int getIntervalTimeMSec() {
		int difSec = (int)(Math.random()*8)-4;
		int aveSec = 5;
		return (aveSec + difSec) * 1000;
	}
	
	private Mole mole;
	
	MoleTimer(Mole mole) {
		this.mole = mole;
		
		popupTask = new PopupTask();
		vanishTask = new vanishTask();
		
	}
	
	public void start() {
		postDelayed(popupTask, getIntervalTimeMSec());
	}
	
	public void clear() {
		removeCallbacks(popupTask);
		removeCallbacks(vanishTask);
	}
	
	/**
	 * 登録済みの出現処理を削除し、新しく追加する。
	 * 次に出現するまでの時間はランダム。
	 */
	void reset() {
		clear();
		postDelayed(popupTask, getIntervalTimeMSec());
	}

	private class PopupTask implements Runnable {
		
		/**
		 * もぐらの出現処理
		 */
		@Override
		public void run() {
			Log.v("PopupTask", "run()");
			
			// もぐらの出現アニメーションを開始する。
			mole.popup();
			
			// もぐらが出現したら、出現回数をカウントアップ
			mole.sumInc();
			
			// 出現処理を再び追加しておく
//			init();
			
			// もぐら消滅処理も登録しておく。
			// 消滅までは2sec
			postDelayed(vanishTask, 2*1000);
		}
	}
	
	private class vanishTask implements Runnable {

		@Override
		public void run() {
			Log.v("VanishTask", "run()");
			
			// もぐら消滅アニメーションを開始する。
			mole.vanish();
			
			// もぐらを叩いた回数はカウントアップしない
			
			postDelayed(popupTask, getIntervalTimeMSec());
		}
		
	}
}
