package ua.pp.blastorq.planebattle;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class AndroidLauncher extends Activity {
	RelativeLayout layout;
	Button btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		//initialize(new PlaneBattle(), config);
		btn = new Button(this);
		layout = new RelativeLayout(this);
		layout.addView(btn);
		setContentView(layout);
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(AndroidLauncher.this, MainActivity.class);
				startActivity(intent);
			}
		});
	}
}


