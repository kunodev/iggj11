package Entity;

import JSONUtil.JSONSerializable;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

public class Country implements JSONSerializable
{
	private String countryCode;
	private String name;
	private int highscore;

	public Country (String countryCode, String name) {
		this.countryCode = countryCode;
		this.name = name;
		highscore = 0;
	}

	public String getCountryCode() {
		return this.countryCode;
	}

	@Override
	public JSONAware toJSON()
	{
		JSONObject obj = new JSONObject();
		obj.put("countryCode", countryCode);
		obj.put("name", name);
		return obj;
	}

	public int getHighscore(){
		return highscore;
	}
}
