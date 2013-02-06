package com.example.whack_a_mole;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * もぐら
 * 
 * @author Masatoshi
 *
 */
public class Mole implements View.OnClickListener {
	
	private MainActivity activity;
	
	// もぐらを表す画像
	private final ImageView image;
	
	// もぐらを叩けるかどうか
	private boolean touchable = false;
	
	// もぐらが消える／出現するアニメーション
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
				// もぐらは、消え始めたら叩けなくする。
				touchable = false;
			}
			@Override
			public void onAnimationRepeat(Animation animation) {
			}
			@Override
			public void onAnimationEnd(Animation animation) {
				// アニメーション終了と同時に、もぐらを完全に非表示にする
				image.setVisibility(View.INVISIBLE);
//
//				// タイマーを初期化
//				timer.init();
			}
		});
		
		popup = AnimationUtils.loadAnimation(
				activity, R.anim.grow);
		popup.setAnimationListener(new Animation.AnimationListener() {			
			@Override
			public void onAnimationStart(Animation animation) {
				// アニメーション開始時に、もぐらを表示する
				image.setVisibility(View.VISIBLE);
				// もぐらが出てき始めたら、もう叩けるようにする。
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
	 * もぐらが叩かれたときに呼ばれる処理
	 * @param v 
	 */
	@Override
	public void onClick(View v) {
		if (activity.isGo()) {
			// ゲーム中
			if (touchable) {
				// もぐらヒット！
			
				// もぐらを消すアニメーションを開始する
				vanish();
				// もぐらヒット回数をカウントアップ
				hitInc();
				// タイマーをリセット
				timer.reset();
			} else {
				// お手付き
			
				activity.addMissCount();
			}
		} else {
			// ゲーム停止中
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
