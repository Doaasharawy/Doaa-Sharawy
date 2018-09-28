package path;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;

import javaclasses.MapsInterface;


public class ConnectAsyncTask extends AsyncTask<Void, Void, String> {
	private ProgressDialog progressDialog;
	String url;
	MapsInterface currentActivity;

	 GoogleMap currentMap;
	public ConnectAsyncTask(String urlPass, MapsInterface currentActivity) {
		url = urlPass;
		this.currentActivity = currentActivity;
		 this.currentMap=currentActivity.getmMap();
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		progressDialog = new ProgressDialog((Activity)currentActivity);
		progressDialog.setMessage("Fetching route, Please wait...");
		progressDialog.setIndeterminate(true);
		progressDialog.show();
	}

	@Override
	protected String doInBackground(Void... params) {
		JSONParser jParser = new JSONParser();
		String json = jParser.getJSONFromUrl(url);
		return json;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		progressDialog.hide();
		if (result != null) {
			new DrawPath().drawPath(result, currentMap);
		}
	}
}