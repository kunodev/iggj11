package Entity;

import JSONUtil.JSONSerializable;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;
import questions.Question;

import java.util.ArrayList;
import java.util.List;

public class User implements JSONSerializable
{
	static final String ROLE_GAME_MASTER = "gameMaster";
	static final String ROLE_GAME_PLAYER = "player";

	private int    id;
	private String userName;
	private int    points;
	private String role;
	private List<Question> correctAnsweredQuestions;

	public User(int id, String userName)
	{
		this.id = id;
		this.userName = userName;
		this.points = 0;
		this.role = this.id == 1 ? ROLE_GAME_MASTER : ROLE_GAME_PLAYER;
		this.correctAnsweredQuestions = new ArrayList<Question>();
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

	public void addCorrectAnsweredQuestion(Question question){
		this.correctAnsweredQuestions.add(question);
	}

	public int getId()
	{
		return id;
	}
}
