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

	private final String[] colors = {"#ff1e25", "#1e74ff", "#38ce25", "#ede923", "#ed2295", "#ed9822", "#22dced"};

	private int    id;
	private String userName;
	private int    points;
	private String role;
	private String color;
    private List<Question> correctAnsweredQuestions;
    private int totalPoints;

	public User(int id, String userName)
	{
		this.id = id;
		this.userName = userName;
		this.points = 0;
		this.role = this.id == 1 ? ROLE_GAME_MASTER : ROLE_GAME_PLAYER;
		this.color = this.colors[id % colors.length];
        this.correctAnsweredQuestions = new ArrayList<Question>();
	}

	public JSONAware toJSON()
	{
		JSONObject obj = new JSONObject();
		obj.put("id", this.id);
		obj.put("name", this.userName);
		obj.put("points", this.points);
		obj.put("totalPoints", this.totalPoints);
		obj.put("role", this.role);
		obj.put("color", this.color);

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

	public int getPoints(){
		return points;
	}

	/**
	 * Setzt die aktuellen Punkte zurück und addiert sie vorher zur Gesamtpunktzahl
	 */
	public void resetPoints(){
		totalPoints+=points;
		points = 0;
	}
}
