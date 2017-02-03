package ua.pp.blastorq.planebattle;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.vending.billing.IInAppBillingService;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ua.pp.blastorq.planebattle.loader.ResourceLoader;

public class AndroidLauncher extends AndroidApplication implements Bill {
	final String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiR7MLe+wtDcps8/q4xOTSX6SW2OH+jTEfyzCRMaUL1bpF54ChF6zHAT+ef3+Gyu25fGBvTuL2/QNaPBzEQG5Tg9+QNZkw8azQ0YiK00AbHWJh84vFvbvv6ygVnjpdRZ+AwRLyIUG+go8PDo2uQYZMkuaKPzULiuusVIVOGlmBB1v8odz9uy0EInaQzF3ndpbyd0mT/zwSeG/3CQAaQKXBx2xBoUqrbraI9xnsdbkhr4kUPPvohhvKzoT2Iy3WONOBhYWq0r0wa43chGZQ1tdfoTjZiUC72y14a7sUvd4yI0MkwqSCF3Ea883u+azcxSVlvUVukvW0P2awy/M71YZ+QIDAQAB";
	RelativeLayout layout;
	View gameView;
	Bill bill;
	AndroidApplicationConfiguration cfg;
	IInAppBillingService mService;
	Preferences prefs = Gdx.app.getPreferences("noads");
	ServiceConnection connection;
	Button btn;
	String inAppId = "ua.pp.blastorq.planebattle.removeads";
	String removeAdPrice;
	ResourceLoader loader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		loader.load();
		someInitialize();
		cfg = new AndroidApplicationConfiguration();
		layout = new RelativeLayout(this);
		gameView = initializeForView(new PlaneBattle(this, false), cfg);
		layout.addView(gameView);
		//if(!prefs.getBoolean("noads", true)){
		AdView adView;
		adView = new AdView(this);
		adView.setAdSize(AdSize.SMART_BANNER);
		adView.setAdUnitId("ca-app-pub-3418075909647949/3192256714");
		AdRequest.Builder builder = new AdRequest.Builder();
		builder.addTestDevice("");
		RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		adParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		layout.addView(adView, adParams);
		adView.loadAd(builder.build());
		//	}
		setContentView(layout);
	}

	protected void someInitialize() {
		connection = new ServiceConnection() {
			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				mService = IInAppBillingService.Stub.asInterface(service);
			}
			@Override
			public void onServiceDisconnected(ComponentName name) {
				mService = null;
			}
		};
		Intent serviceIntent =
				new Intent("com.android.vending.billing.InAppBillingService.BIND");
		serviceIntent.setPackage("com.android.vending");
		bindService(serviceIntent, connection, Context.BIND_AUTO_CREATE);
		//btn.setOnClickListener(new View.OnClickListener() {


	}

	public void clicked() {
		ArrayList<String> skuList = new ArrayList<String>();
		skuList.add(inAppId);
		Bundle querySkus = new Bundle();
		querySkus.putStringArrayList("ITEM_ID_LIST", skuList);
		try {
			Bundle skuDetails = mService.getSkuDetails(3,
					getPackageName(), "inapp", querySkus);
			int response = skuDetails.getInt("RESPONSE_CODE");
			if (response == 0) {
				ArrayList<String> responseList
						= skuDetails.getStringArrayList("DETAILS_LIST");
				for (String thisResponse : responseList) {
					JSONObject object = new JSONObject(thisResponse);
					String sku = object.getString("productId");
					String price = object.getString("price");
					if (sku.equals(inAppId)) removeAdPrice = price;
					Bundle buyIntentBundle = mService.getBuyIntent(3, getPackageName(),
							sku, "inapp", base64EncodedPublicKey);
					PendingIntent pendingIntent = buyIntentBundle.getParcelable("BUY_INTENT");
					startIntentSenderForResult(pendingIntent.getIntentSender(),
							1001, new Intent(), Integer.valueOf(0), Integer.valueOf(0),
							Integer.valueOf(0));
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1001) {
			int responseCode = data.getIntExtra("RESPONSE_CODE", 0);
			String purchaseData = data.getStringExtra("INAPP_PURCHASE_DATA");
			String dataSignature = data.getStringExtra("INAPP_DATA_SIGNATURE");

			if (resultCode == RESULT_OK) {
				try {
					JSONObject jo = new JSONObject(purchaseData);
					String sku = jo.getString("productId");
					prefs.putBoolean("noads", true);
					prefs.flush();
					Toast.makeText(this, "Thanks for purchasing", Toast.LENGTH_LONG).show();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (connection != null) {
			unbindService(connection);
		}
	}

	@Override
	public void OnClick() {
		clicked();
	}
}
