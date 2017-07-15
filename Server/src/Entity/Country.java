package Entity;

import JSONUtil.JSONSerializable;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

public class Country implements JSONSerializable
{
	private String countryCode;
	private String name;

	public Country (String countryCode, String name) {
		this.countryCode = countryCode;
		this.name = name;
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
}
