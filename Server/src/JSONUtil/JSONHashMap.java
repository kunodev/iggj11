package JSONUtil;

import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class JSONHashMap<K, V> extends HashMap<K, V> implements JSONSerializable
{
	@Override
	public JSONAware toJSON()
	{
		JSONObject obj = new JSONObject();
		for (Object o : this.entrySet())
		{
			Map.Entry pair = (Map.Entry) o;
			obj.put(pair.getKey(), pair.getValue());
		}

		return obj;
	}
}
