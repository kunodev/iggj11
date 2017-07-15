package JSONUtil;

import org.json.simple.JSONArray;
import org.json.simple.JSONAware;

import java.util.ArrayList;

public class JSONArrayList<E> extends ArrayList<E> implements JSONSerializable
{
	@Override
	public JSONAware toJSON()
	{
		JSONArray obj = new JSONArray();
		for (E value : this)
		{
			if (value instanceof JSONSerializable)
			{
				obj.add(((JSONSerializable) value).toJSON());
			} else {
				obj.add(value);
			}
		}

		return obj;
	}
}
