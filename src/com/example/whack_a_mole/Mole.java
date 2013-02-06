package com.example.whack_a_mole;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * ������
 * 
 * @author Masatoshi
 *
 */
public class Mole implements View.OnClickListener {
	
	private MainActivity activity;
	
	// �������\���摜
	private final ImageView image;
	
	// �������@���邩�ǂ���
	private boolean touchable = false;
	
	// �����炪������^�o������A�j���[�V����
	private final Animation vanish, popup;
	
	private final MoleTimer timer;

	/**
	 * 
	 * @param activity
	 * @param moleViewID
	 * @param moleDrawableID
	 */
	Mole(MainActivity activity, int moleViewID, int moleDrawableID) {
		this.activity = activity;
		
		this.image = (ImageView)activity.findViewById(moleViewID);
		this.image.setImageResource(moleDrawableID);
		this.image.setVisibility(View.INVISIBLE);
		this.image.setOnClickListener(this);
		
		vanish = AnimationUtils.loadAnimation(
				activity, R.anim.shrink);
		vanish.setAnimationListener(new Animation.AnimationListener() {			
			@Override
			public void onAnimationStart(Animation animation) {
				// ������́A�����n�߂���@���Ȃ�����B
				touchable = false;
			}
			@Override
			public void onAnimationRepeat(Animation animation) {
			}
			@Override
			public void onAnimationEnd(Animation animation) {
				// �A�j���[�V�����I���Ɠ����ɁA����������S�ɔ�\���ɂ���
				image.setVisibility(View.INVISIBLE);
//
//				// �^�C�}�[��������
//				timer.init();
			}
		});
		
		popup = AnimationUtils.loadAnimation(
				activity, R.anim.grow);
		popup.setAnimationListener(new Animation.AnimationListener() {			
			@Override
			public void onAnimationStart(Animation animation) {
				// �A�j���[�V�����J�n���ɁA�������\������
				image.setVisibility(View.VISIBLE);
				// �����炪�o�Ă��n�߂���A�����@����悤�ɂ���B
				touchable = true;
			}
			@Override
			public void onAnimationRepeat(Animation animation) {
			}
			@Override
			public void onAnimationEnd(Animation animation) {
			}
		});
		
		this.timer = new MoleTimer(this);
	}
	
	public void show() {
		image.setVisibility(View.VISIBLE);
	}

	public void start() {
		vanish();
		timer.start();
	}
	public void stop() {
		timer.clear();
		popup();
//		touchable = false;
	}

	/**
	 * �����炪�@���ꂽ�Ƃ��ɌĂ΂�鏈��
	 * @param v 
	 */
	@Override
	public void onClick(View v) {
		if (activity.isGo()) {
			// �Q�[����
			if (touchable) {
				// ������q�b�g�I
			
				// ������������A�j���[�V�������J�n����
				vanish();
				// ������q�b�g�񐔂��J�E���g�A�b�v
				hitInc();
				// �^�C�}�[�����Z�b�g
				timer.reset();
			} else {
				// ����t��
			
				activity.addMissCount();
			}
		} else {
			// �Q�[����~��
		}
	}
	
	void popup() {
		image.startAnimation(popup);
	}
	void vanish() {
		image.startAnimation(vanish);
	}

	void sumInc() {
		activity.updateScore(1, 0);
	}
	void hitInc() {
		activity.updateScore(0, 1);
	}

}
