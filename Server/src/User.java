import JSONUtil.JSONSerializable;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

public class User implements JSONSerializable
{
	static final String ROLE_GAME_MASTER = "gameMaster";
	static final String ROLE_GAME_PLAYER = "player";

	private int    id;
	private String userName;
	private int    points;
	private String role;

	User(int id, String userName)
	{
		this.id = id;
		this.userName = userName;
		this.points = 0;
		this.role = this.id == 1 ? ROLE_GAME_MASTER : ROLE_GAME_PLAYER;
	}

	public JSONAware toJSON()
	{
		JSONObject obj = new JSONObject();
		obj.put("id", id);
		obj.put("name", userName);
		obj.put("points", points);
		obj.put("role", role);

		return obj;
	}

	public void addPoints(int points)
	{
		this.points += points;
	}

	public int getId()
	{
		return id;
	}
}
