package com.face.service.youtu;

import com.face.service.youtu.sign.*;
import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;


/**
 * 
 * @author tyronetao
 */
public class Youtu {
	
	private static class TrustAnyTrustManager implements X509TrustManager {
		
		public void checkClientTrusted(X509Certificate[] chain, String authType)
		throws CertificateException {
		}
		
		public void checkServerTrusted(X509Certificate[] chain, String authType)
		throws CertificateException {
		}
		
		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[] {};
		}
	}
	
	private static class TrustAnyHostnameVerifier implements HostnameVerifier {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}

	public final static  String API_YOUTU_END_POINT = "http://api.youtu.qq.com/youtu/";
	public final static String API_TENCENTYUN_END_POINT = "https://youtu.api.qcloud.com/youtu/";
	// 30 days
	private static int EXPIRED_SECONDS = 2592000;
	private String m_appid;
	private String m_secret_id;
	private String m_secret_key;
	private String m_end_point;
	private boolean m_use_youtu;
	
	/**
	 * PicCloud 构造方法
	 *
	 * @param appid
	 *            授权appid
	 * @param secret_id
	 *            授权secret_id
	 * @param secret_key
	 *            授权secret_key
	 */
	public Youtu(String appid, String secret_id, String secret_key,String end_point) {
		m_appid = appid;
		m_secret_id = secret_id;
		m_secret_key = secret_key;
		m_end_point=end_point;
		m_use_youtu=!end_point.startsWith("https");
	}

	public String StatusText(int status) {

		String statusText = "UNKOWN";

        switch (status)
        {
        	case 0:
                statusText = "CONNECT_FAIL";
                break;
            case 200:
                statusText = "HTTP OK";
                break;
            case 400:
                statusText = "BAD_REQUEST";
                break;
            case 401:
                statusText = "UNAUTHORIZED";
                break;
            case 403:
                statusText = "FORBIDDEN";
                break;
            case 404:
                statusText = "NOTFOUND";
                break;
            case 411:
                statusText = "REQ_NOLENGTH";
                break;
            case 423:
                statusText = "SERVER_NOTFOUND";
                break;
            case 424:
                statusText = "METHOD_NOTFOUND";
                break;
            case 425:
                statusText = "REQUEST_OVERFLOW";
                break;
            case 500:
                statusText = "INTERNAL_SERVER_ERROR";
                break;
            case 503:
                statusText = "SERVICE_UNAVAILABLE";
                break;
            case 504:
                statusText = "GATEWAY_TIME_OUT";
                break;
        }
        return statusText;
	}


	private void GetBase64FromFile(String filePath, StringBuffer base64)
	throws IOException {
		File imageFile = new File(filePath);
		if (imageFile.exists()) {
			InputStream in = new FileInputStream(imageFile);
			byte data[] = new byte[(int) imageFile.length()]; // 创建合适文件大小的数组
			in.read(data); // 读取文件中的内容到b[]数组
			in.close();
			base64.append(Base64Util.encode(data));

		} else {
			throw new FileNotFoundException(filePath + " not exist");
		}

	}

	private JSONObject SendHttpRequest(JSONObject postData, String mothod)
	throws IOException, JSONException, KeyManagementException, NoSuchAlgorithmException {

		StringBuffer mySign = new StringBuffer("");
		YoutuSign.appSign(m_appid, m_secret_id, m_secret_key,
			System.currentTimeMillis() / 1000 + EXPIRED_SECONDS,
			"", mySign);

		System.setProperty("sun.net.client.defaultConnectTimeout", "30000");
		System.setProperty("sun.net.client.defaultReadTimeout", "30000");
		URL url = new URL(m_end_point + mothod);

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		// set header
		connection.setRequestMethod("POST");
		connection.setRequestProperty("accept", "*/*");
		connection.setRequestProperty("Host", "api.youtu.qq.com");
		connection.setRequestProperty("user-agent", "youtu-java-sdk");
		connection.setRequestProperty("Authorization", mySign.toString());

		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setUseCaches(false);
		connection.setInstanceFollowRedirects(true);
		connection.setRequestProperty("Content-Type", "text/json");
		connection.connect();

		// POST请求
		DataOutputStream out = new DataOutputStream(
			connection.getOutputStream());

		postData.put("app_id", m_appid);
		out.write(postData.toString().getBytes("utf-8"));
		//out.writeBytes(postData.toString());
		out.flush();
		out.close();
		// 读取响应
		BufferedReader reader = new BufferedReader(new InputStreamReader(
			connection.getInputStream()));
		String lines;
		StringBuffer resposeBuffer = new StringBuffer("");
		while ((lines = reader.readLine()) != null) {
			lines = new String(lines.getBytes(), "utf-8");
			resposeBuffer.append(lines);
		}
		// System.out.println(resposeBuffer+"\n");
		reader.close();
		// 断开连接
		connection.disconnect();

		JSONObject respose = new JSONObject(resposeBuffer.toString());

		return respose;

	}


	private  JSONObject SendHttpsRequest(JSONObject postData,String mothod)
	throws NoSuchAlgorithmException, KeyManagementException,
	IOException, JSONException {
		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, new TrustManager[]{new TrustAnyTrustManager()},
				new java.security.SecureRandom());

		StringBuffer mySign = new StringBuffer("");
		YoutuSign.appSign(m_appid, m_secret_id, m_secret_key,
			System.currentTimeMillis() / 1000 + EXPIRED_SECONDS,
			"", mySign);

		System.setProperty("sun.net.client.defaultConnectTimeout", "30000");
		System.setProperty("sun.net.client.defaultReadTimeout", "30000");

		URL url = new URL(m_end_point + mothod);
		HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
		connection.setSSLSocketFactory(sc.getSocketFactory());
		connection.setHostnameVerifier(new TrustAnyHostnameVerifier());
     // set header
		connection.setRequestMethod("POST");
		connection.setRequestProperty("accept", "*/*");
		connection.setRequestProperty("Host", "youtu.api.qcloud.com");
		connection.setRequestProperty("user-agent", "youtu-java-sdk");
		connection.setRequestProperty("Authorization", mySign.toString());

		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setUseCaches(false);
		connection.setInstanceFollowRedirects(true);
		connection.setRequestProperty("Content-Type", "text/json");
		connection.connect();

    	// POST请求
		DataOutputStream out = new DataOutputStream(connection.getOutputStream());

		postData.put("app_id", m_appid);
		out.write(postData.toString().getBytes("utf-8"));
		// 刷新、关闭
		out.flush();
		out.close();

		// 读取响应
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String lines;
		StringBuffer resposeBuffer = new StringBuffer("");
		while ((lines = reader.readLine()) != null) {
			lines = new String(lines.getBytes(), "utf-8");
			resposeBuffer.append(lines);
		}
     	// System.out.println(resposeBuffer+"\n");
		reader.close();
     	// 断开连接
		connection.disconnect();

		JSONObject respose = new JSONObject(resposeBuffer.toString());

		return respose;
	}

	
	public JSONObject DetectFace(String image_path,int mode) throws IOException,
	JSONException, KeyManagementException, NoSuchAlgorithmException {

		StringBuffer image_data = new StringBuffer("");
		JSONObject data = new JSONObject();

		GetBase64FromFile(image_path, image_data);
		data.put("image", image_data.toString());
		data.put("mode", mode);
		JSONObject respose =m_use_youtu?SendHttpRequest(data, "api/detectface"):SendHttpsRequest(data, "api/detectface");

		return respose;
	}
	



	public JSONObject DetectFaceUrl(String url, int mode)
	throws IOException, JSONException, KeyManagementException,
	NoSuchAlgorithmException {
		JSONObject data = new JSONObject();
		data.put("url", url);
		data.put("mode", mode);
		JSONObject respose = m_use_youtu ? SendHttpRequest(data, "api/detectface")
		: SendHttpsRequest(data, "api/detectface");

		return respose;
	}

	public JSONObject DetectFaceBase64(String image_base64, int mode)
			throws IOException, JSONException, KeyManagementException,
			NoSuchAlgorithmException {
		JSONObject data = new JSONObject();
		data.put("image", image_base64);
		data.put("mode", mode);
		JSONObject respose = m_use_youtu ? SendHttpRequest(data, "api/detectface")
				: SendHttpsRequest(data, "api/detectface");
		return respose;
	}
	
	public JSONObject FaceShape(String image_path,int mode) throws IOException,
	JSONException, KeyManagementException, NoSuchAlgorithmException  {

		StringBuffer image_data = new StringBuffer("");
		JSONObject data = new JSONObject();

		GetBase64FromFile(image_path, image_data);
		data.put("image", image_data.toString());
		data.put("mode", mode);
		JSONObject respose =m_use_youtu?SendHttpRequest(data, "api/faceshape"):SendHttpsRequest(data, "api/faceshape");

		return respose;
	}
	
	public JSONObject FaceShapeUrl(String url,int mode) throws IOException,
	JSONException, KeyManagementException, NoSuchAlgorithmException  {

		JSONObject data = new JSONObject();
		data.put("url", url);
		data.put("mode", mode);
		JSONObject respose =m_use_youtu?SendHttpRequest(data, "api/faceshape"):SendHttpsRequest(data, "api/faceshape");

		return respose;
	}
	
	public JSONObject FaceCompare(String image_path_a, String image_path_b)
	throws IOException, JSONException, KeyManagementException, NoSuchAlgorithmException {

		StringBuffer image_data = new StringBuffer("");
		JSONObject data = new JSONObject();

		GetBase64FromFile(image_path_a, image_data);
		data.put("imageA", image_data.toString());
		image_data.setLength(0);

		GetBase64FromFile(image_path_b, image_data);
		data.put("imageB", image_data.toString());
		
		JSONObject respose =m_use_youtu?SendHttpRequest(data, "api/facecompare"):SendHttpsRequest(data, "api/facecompare");

		return respose;
	}
	
	public JSONObject FaceCompareUrl(String urlA, String urlB)
	throws IOException, JSONException, KeyManagementException, NoSuchAlgorithmException {

		JSONObject data = new JSONObject();

		data.put("urlA", urlA);
		data.put("urlB", urlB);
		
		JSONObject respose =m_use_youtu?SendHttpRequest(data, "api/facecompare"):SendHttpsRequest(data, "api/facecompare");

		return respose;
	}

	public JSONObject FaceVerify(String image_path, String person_id)
	throws IOException, JSONException, KeyManagementException, NoSuchAlgorithmException {

		StringBuffer image_data = new StringBuffer("");
		JSONObject data = new JSONObject();

		GetBase64FromFile(image_path, image_data);
		data.put("image", image_data.toString());
		image_data.setLength(0);

		data.put("person_id", person_id);

		JSONObject respose =m_use_youtu?SendHttpRequest(data, "api/faceverify"):SendHttpsRequest(data, "api/faceverify");

		return respose;
	}

	public JSONObject FaceVerifyUrl(String url, String person_id)
	throws IOException, JSONException, KeyManagementException, NoSuchAlgorithmException {

		JSONObject data = new JSONObject();

		data.put("url", url);

		data.put("person_id", person_id);

		JSONObject respose =m_use_youtu?SendHttpRequest(data, "api/faceverify"):SendHttpsRequest(data, "api/faceverify");

		return respose;
	}

	public JSONObject FaceIdentify(String image_path, String group_id)
	throws IOException, JSONException, KeyManagementException, NoSuchAlgorithmException {
		StringBuffer image_data = new StringBuffer("");
		JSONObject data = new JSONObject();

		GetBase64FromFile(image_path, image_data);
		data.put("image", image_data.toString());
		image_data.setLength(0);

		data.put("group_id", group_id);

		JSONObject respose =m_use_youtu?SendHttpRequest(data, "api/faceidentify"):SendHttpsRequest(data, "api/faceidentify");

		return respose;
	}
	public JSONObject FaceIdentifyBase64(StringBuffer image_data, String group_id)
			throws IOException, JSONException, KeyManagementException, NoSuchAlgorithmException {
		JSONObject data = new JSONObject();
		data.put("image", image_data.toString());
		image_data.setLength(0);
		data.put("group_id", group_id);

		JSONObject respose =m_use_youtu?SendHttpRequest(data, "api/faceidentify"):SendHttpsRequest(data, "api/faceidentify");

		return respose;
	}

	public JSONObject FaceIdentifyUrl(String url, String group_id)
	throws IOException, JSONException, KeyManagementException, NoSuchAlgorithmException {
		JSONObject data = new JSONObject();
		data.put("url", url);
		data.put("group_id", group_id);

		JSONObject respose =m_use_youtu?SendHttpRequest(data, "api/faceidentify"):SendHttpsRequest(data, "api/faceidentify");

		return respose;
	}

	public JSONObject NewPerson(String image_path, String person_id,
		List<String> group_ids) throws IOException, JSONException, KeyManagementException, NoSuchAlgorithmException {
		StringBuffer image_data = new StringBuffer("");
		JSONObject data = new JSONObject();

		GetBase64FromFile(image_path, image_data);
		data.put("image", image_data.toString());
		image_data.setLength(0);

		data.put("person_id", person_id);
		data.put("group_ids", group_ids);

		JSONObject respose =m_use_youtu?SendHttpRequest(data, "api/newperson"):SendHttpsRequest(data, "api/newperson");

		return respose;
	}

	public JSONObject NewPersonUrl(String url, String person_id,
		List<String> group_ids) throws IOException, JSONException, KeyManagementException, NoSuchAlgorithmException {
		JSONObject data = new JSONObject();
		data.put("url", url);

		data.put("person_id", person_id);
		data.put("group_ids", group_ids);

		JSONObject respose =m_use_youtu?SendHttpRequest(data, "api/newperson"):SendHttpsRequest(data, "api/newperson");

		return respose;
	}

	public JSONObject DelPerson(String person_id) throws IOException,
	JSONException, KeyManagementException, NoSuchAlgorithmException {

		JSONObject data = new JSONObject();

		data.put("person_id", person_id);

		JSONObject respose =m_use_youtu?SendHttpRequest(data, "api/delperson"):SendHttpsRequest(data, "api/delperson");

		return respose;
	}

	public JSONObject AddFace(String person_id, List<String> image_path_arr)
	throws IOException, JSONException, KeyManagementException, NoSuchAlgorithmException {
		StringBuffer image_data = new StringBuffer("");
		JSONObject data = new JSONObject();
		List<String> images = new ArrayList<String>();
		for (String image_path : image_path_arr) {
			image_data.setLength(0);
			GetBase64FromFile(image_path, image_data);
			images.add(image_data.toString());
		}

		data.put("images", images);
		image_data.setLength(0);

		data.put("person_id", person_id);

		JSONObject respose =m_use_youtu?SendHttpRequest(data, "api/addface"):SendHttpsRequest(data, "api/addface");

		return respose;
	}

	public JSONObject AddFaceUrl(String person_id, List<String> url_arr)
	throws IOException, JSONException, KeyManagementException, NoSuchAlgorithmException {
		JSONObject data = new JSONObject();
		
		data.put("urls", url_arr);
		data.put("person_id", person_id);

		JSONObject respose =m_use_youtu?SendHttpRequest(data, "api/addface"):SendHttpsRequest(data, "api/addface");

		return respose;
	}

	public JSONObject DelFace(String person_id, List<String> face_id_arr)
	throws IOException, JSONException, KeyManagementException, NoSuchAlgorithmException {

		JSONObject data = new JSONObject();

		data.put("face_ids", face_id_arr);
		data.put("person_id", person_id);
		JSONObject respose =m_use_youtu?SendHttpRequest(data, "api/delface"):SendHttpsRequest(data, "api/delface");

		return respose;

	}

	public JSONObject SetInfo(String person_name, String person_id)
	throws IOException, JSONException, KeyManagementException, NoSuchAlgorithmException {
		JSONObject data = new JSONObject();

		data.put("person_name", person_name);
		data.put("person_id", person_id);
		JSONObject respose =m_use_youtu?SendHttpRequest(data, "api/setinfo"):SendHttpsRequest(data, "api/setinfo");
		return respose;
	}

	public JSONObject GetInfo(String person_id) throws IOException,
	JSONException, KeyManagementException, NoSuchAlgorithmException {
		JSONObject data = new JSONObject();
		data.put("person_id", person_id);
		JSONObject respose =m_use_youtu?SendHttpRequest(data, "api/getinfo"):SendHttpsRequest(data, "api/getinfo");
		return respose;
	}

	public JSONObject GetGroupIds() throws IOException, JSONException, KeyManagementException, NoSuchAlgorithmException {
		JSONObject data = new JSONObject();

		JSONObject respose =m_use_youtu?SendHttpRequest(data, "api/getgroupids"):SendHttpsRequest(data, "api/getgroupids");

		return respose;
	}

	public JSONObject GetPersonIds(String group_id) throws IOException,
	JSONException, KeyManagementException, NoSuchAlgorithmException {
		JSONObject data = new JSONObject();

		data.put("group_id", group_id);
		JSONObject respose =m_use_youtu?SendHttpRequest(data, "api/getpersonids"):SendHttpsRequest(data, "api/getpersonids");

		return respose;
	}

	public JSONObject GetFaceIds(String person_id) throws IOException,
	JSONException, KeyManagementException, NoSuchAlgorithmException {
		JSONObject data = new JSONObject();

		data.put("person_id", person_id);
		JSONObject respose =m_use_youtu?SendHttpRequest(data, "api/getfaceids"):SendHttpsRequest(data, "api/getfaceids");

		return respose;
	}

	public JSONObject GetFaceInfo(String face_id) throws IOException,
	JSONException, KeyManagementException, NoSuchAlgorithmException {
		JSONObject data = new JSONObject();

		data.put("face_id", face_id);
		JSONObject respose =m_use_youtu?SendHttpRequest(data, "api/getfaceinfo"):SendHttpsRequest(data, "api/getfaceinfo");

		return respose;
	}


	public JSONObject FuzzyDetect(String image_path) throws IOException,
	JSONException, KeyManagementException, NoSuchAlgorithmException {
		StringBuffer image_data = new StringBuffer("");
		JSONObject data = new JSONObject();

		GetBase64FromFile(image_path, image_data);
		data.put("image", image_data.toString());

		JSONObject respose =m_use_youtu?SendHttpRequest(data, "imageapi/fuzzydetect"):SendHttpsRequest(data, "imageapi/fuzzydetect");

		return respose;
	}

	public JSONObject FuzzyDetectUrl(String url) throws IOException,
	JSONException, KeyManagementException, NoSuchAlgorithmException {
		JSONObject data = new JSONObject();

		data.put("url", url);
		JSONObject respose =m_use_youtu?SendHttpRequest(data, "imageapi/fuzzydetect"):SendHttpsRequest(data, "imageapi/fuzzydetect");
		return respose;
	}

	public JSONObject FoodDetect(String image_path) throws IOException,
	JSONException, KeyManagementException, NoSuchAlgorithmException {
		StringBuffer image_data = new StringBuffer("");
		JSONObject data = new JSONObject();

		GetBase64FromFile(image_path, image_data);
		data.put("image", image_data.toString());
		JSONObject respose =m_use_youtu?SendHttpRequest(data, "imageapi/fooddetect"):SendHttpsRequest(data, "imageapi/fooddetect");
		return respose;
	}

	public JSONObject FoodDetectUrl(String url) throws IOException,
	JSONException, KeyManagementException, NoSuchAlgorithmException {
		JSONObject data = new JSONObject();

		data.put("url", url);
		JSONObject respose =m_use_youtu?SendHttpRequest(data, "imageapi/fooddetect"):SendHttpsRequest(data, "imageapi/fooddetect");
		return respose;
	}


	public JSONObject ImageTag(String image_path) throws IOException,
	JSONException, KeyManagementException, NoSuchAlgorithmException {
		StringBuffer image_data = new StringBuffer("");
		JSONObject data = new JSONObject();

		GetBase64FromFile(image_path, image_data);
		data.put("image", image_data.toString());
		JSONObject respose =m_use_youtu?SendHttpRequest(data, "imageapi/imagetag"):SendHttpsRequest(data, "imageapi/imagetag");
		return respose;
	}

	public JSONObject ImageTagUrl(String url) throws IOException,
	JSONException, KeyManagementException, NoSuchAlgorithmException {
		JSONObject data = new JSONObject();
		data.put("url", url);
		JSONObject respose =m_use_youtu?SendHttpRequest(data, "imageapi/imagetag"):SendHttpsRequest(data, "imageapi/imagetag");
		return respose;
	}


}