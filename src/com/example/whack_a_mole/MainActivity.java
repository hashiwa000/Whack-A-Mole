package com.example.whack_a_mole;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	// ������
	private List<Mole> moles = new ArrayList<Mole>();
	
	// �X�R�A�\���p�̃r���[
	private TextView scoreView, missView;
	
	// ������̏o���񐔁A�������@�����񐔁A����t��������
	private int sumCount, hitCount, missCount;
	
	// �Q�[�����J�n��Ԃ��ǂ���
	private boolean go = false;

	// �v���O���X�o�[�̐ݒ�
	private GameTimer gameTimer;
	
	private Button button;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// �v���O���X�o�[�̕\���ɕK�v
		// setContentView()�̑O�ɌĂԕK�v������B
		requestWindowFeature(Window.FEATURE_PROGRESS);
		
		setContentView(R.layout.activity_main);

		Log.v("MainActivity", "onCreate");

		// �X�R�A�ݒ�
		scoreView = (TextView)findViewById(R.id.score_text);
		missView = (TextView)findViewById(R.id.miss_text);
		updateScore(0, 0);

		// ������ݒ�
		moles.add(new Mole(this, R.id.mole1,  R.drawable.mole));
		moles.add(new Mole(this, R.id.mole2, R.drawable.mole));
		moles.add(new Mole(this, R.id.mole3, R.drawable.mole));
		moles.add(new Mole(this, R.id.mole4, R.drawable.mole));
		moles.add(new Mole(this, R.id.mole5, R.drawable.mole));
		moles.add(new Mole(this, R.id.mole6, R.drawable.mole));
		moles.add(new Mole(this, R.id.mole7, R.drawable.mole));
		moles.add(new Mole(this, R.id.mole8, R.drawable.mole));
		moles.add(new Mole(this, R.id.mole9, R.drawable.mole));
		
		
		for (Mole m: moles) m.show();
		
		// �{�^���ݒ�
//		final Button button = (Button)findViewById(R.id.start_button);
		button = (Button)findViewById(R.id.start_button);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (go) {
					// Reset
					for (Mole m: moles) m.stop();
					button.setText("Start");
					go = false;
					
					setScore(0, 0);
					setMissCount(0);
					
					gameTimer.reset();
				} else {
					// Start
					for (Mole m: moles) m.start();
					button.setText("Reset");
					go = true;
					
					gameTimer.start();
				}
			}
		});
		
		gameTimer = new GameTimer(this);
	}
	
	public void updateScore(int dsum, int dhit) {
		setScore(sumCount+dsum, hitCount+dhit);
	}
	public void setScore(int sum, int hit) {
		synchronized (scoreView) {
			sumCount = sum;
			hitCount = hit;
			scoreView.setText(hit + " / " + sum);
		}
	}
	
	public void addMissCount() {
		setMissCount(missCount+1);
	}
	public void setMissCount(int miss) {
		synchronized (missView) {
			missCount = miss;
			
			String label = getString(R.string.miss_text_label);
			missView.setText(label + missCount);
		}
	}
	
	public boolean isGo() {
		return go;
	}
	
	public void finishGame() {
		final int sum = this.sumCount;
		final int hit = this.hitCount;
		final int miss = this.missCount;
		
		{
			for (Mole m: moles) m.stop();
			button.setText("Start");
			go = false;
		
			setScore(0, 0);
			setMissCount(0);
			
			gameTimer.reset();
		}
		
		TextView tv = (TextView)findViewById(R.id.result_view);
		String os = (String)tv.getText();
		String ns = hit + " / " + sum + " (" + miss + ")\n";
		if (os == null) {
			tv.setText(ns);
		} else {
			tv.setText(ns + os);
		}
	}
}
