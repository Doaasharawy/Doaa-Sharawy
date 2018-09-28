package path;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.DefaultHttpClient;

public class JSONParser {

	static InputStream is = null;
	static JSONObject jObj = null;
	static String json = "";

	// constructor
	public JSONParser() {
	}

//	public String getJSONFromUrl(String url) {
//
//		// Making HTTP request
//		try {
//			// defaultHttpClient
//			DefaultHttpClient httpClient = new DefaultHttpClient();
//			HttpPost httpPost = new HttpPost(url);
//
//			HttpResponse httpResponse = httpClient.execute(httpPost);
//			HttpEntity httpEntity = httpResponse.getEntity();
//			is = httpEntity.getContent();
//
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		} catch (ClientProtocolException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		try {
//			BufferedReader reader = new BufferedReader(new InputStreamReader(
//					is, "iso-8859-1"), 8);
//			StringBuilder sb = new StringBuilder();
//			String line = null;
//			while ((line = reader.readLine()) != null) {
//				sb.append(line + "\n");
//			}
//
//			json = sb.toString();
//			is.close();
//		} catch (Exception e) {
//			Log.e("Buffer Error", "Error converting result " + e.toString());
//		}
//		return json;
//
//	}

	public String getJSONFromUrl(String urlLink) {
		HttpURLConnection urlConnection = null;
		BufferedReader reader = null;

		String[] forcastArrayFromJson = null;
// Will contain the raw JSON response as a string.
		String jsonString = null;

		try {
			URL url = new URL(urlLink);

			// Create the request to OpenWeatherMap, and open the connection
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("POST");
			urlConnection.connect();

			// Read the input stream into a String
			InputStream inputStream = urlConnection.getInputStream();
			StringBuffer buffer = new StringBuffer();
			if (inputStream == null) {
				// Nothing to do.
                jsonString = null;
			}
			reader = new BufferedReader(new InputStreamReader(inputStream));

			String line;
			while ((line = reader.readLine()) != null) {
				// Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
				// But it does make debugging a *lot* easier if you print out the completed
				// buffer for debugging.
				buffer.append(line + "\n");
			}

			if (buffer.length() == 0) {
				// Stream was empty.  No point in parsing.
                jsonString = null;
			}
			jsonString = buffer.toString();

		} catch (IOException e) {
			e.printStackTrace();
			Log.e("Json parser", "Error ", e);
			// If the code didn't successfully get the weather data, there's no point in attemping
			// to parse it.
            jsonString = null;
		} finally {
			if (urlConnection != null) {
				urlConnection.disconnect();

			}
			if (reader != null) {
				try {
					reader.close();
				} catch (final IOException e) {
					Log.e("Json parser", "Error closing stream", e);
				}
			}
            return jsonString;
		}

	}
}