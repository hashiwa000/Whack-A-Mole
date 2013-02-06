package com.example.whack_a_mole;

import android.os.Handler;
import android.util.Log;

public class MoleTimer extends Handler {
	
	Runnable popupTask, vanishTask;

	// �o������Ԋu���擾����B
	// 1sec - 9sec �̊ԂŃ����_��������B
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
	 * �o�^�ς݂̏o���������폜���A�V�����ǉ�����B
	 * ���ɏo������܂ł̎��Ԃ̓����_���B
	 */
	void reset() {
		clear();
		postDelayed(popupTask, getIntervalTimeMSec());
	}

	private class PopupTask implements Runnable {
		
		/**
		 * ������̏o������
		 */
		@Override
		public void run() {
			Log.v("PopupTask", "run()");
			
			// ������̏o���A�j���[�V�������J�n����B
			mole.popup();
			
			// �����炪�o��������A�o���񐔂��J�E���g�A�b�v
			mole.sumInc();
			
			// �o���������Ăђǉ����Ă���
//			init();
			
			// ��������ŏ������o�^���Ă����B
			// ���ł܂ł�2sec
			postDelayed(vanishTask, 2*1000);
		}
	}
	
	private class vanishTask implements Runnable {

		@Override
		public void run() {
			Log.v("VanishTask", "run()");
			
			// ��������ŃA�j���[�V�������J�n����B
			mole.vanish();
			
			// �������@�����񐔂̓J�E���g�A�b�v���Ȃ�
			
			postDelayed(popupTask, getIntervalTimeMSec());
		}
		
	}
}
