package com.example.whack_a_mole;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ToggleButton;

public class CurrentMole {

	// 現在叩けるもぐらの種別を表示するかどうか。
//	private boolean show;
	private ToggleButton button;
	
	// 現在叩けるもぐらのイメージ
	private ImageView image;
	
	private int id;
	
	public CurrentMole(ToggleButton tb, ImageView iv) {
		this.button = tb;
		this.image = iv;
		this.image.setVisibility(View.INVISIBLE);
		if (tb.isChecked()) {
			this.image.setVisibility(View.VISIBLE);
		}
		
		this.id = MainActivity.nextImageID();
		this.image.setImageResource(this.id);

		this.button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					image.setVisibility(View.VISIBLE);
				} else {
					image.setVisibility(View.INVISIBLE);
				}
			}
		});
	}
	
	/**
	 * green -> red -> blue -> green -> ...
	 */
	public void update() {
		switch (id) {
		case R.drawable.green:
			update(R.drawable.red);
			break;
		case R.drawable.red:
			update(R.drawable.blue);
			break;
		case R.drawable.blue:
			update(R.drawable.green);
			break;
		default:
			// empty
			break;
		}
	}
	
	private void update(int id) {
		this.id = id;
		this.image.setImageResource(id);
	}
	
	public int getID() {
		return id;
	}
}
