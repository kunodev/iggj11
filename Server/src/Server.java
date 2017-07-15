import java.io.*;
import java.net.InetSocketAddress;
import java.util.Map;

import ServerUtil.ParameterFilter;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class Server
{
	public static final String ACTION_LOGIN               = "login";
	public static final String ACTION_START               = "startgame";
	public static final String ACTION_ANSWER              = "answer";
	public static final String ACTION_ANSWER_CHECK        = "answercheck";
	public static final String ACTION_ANSWER_CHECK_SUBMIT = "answerchecksubmit";

	public static void main(String[] args) throws IOException
	{
		new Server();
	}

	public Server() throws IOException
	{
		HttpServer  server  = HttpServer.create(new InetSocketAddress(1337), 0);
		HttpContext context = server.createContext("/", new RequestHandler(new ServerState()));
		context.getFilters().add(new ParameterFilter());
		server.start();
	}

	private class RequestHandler implements HttpHandler
	{
		private ServerState state;

		RequestHandler(ServerState state)
		{
			this.state = state;
		}

		@Override
		public void handle(HttpExchange exch) throws IOException
		{
			// todo: action handling in eigene Klasse
			if ("post".equalsIgnoreCase(exch.getRequestMethod()))
			{
				Map<String, Object> params = (Map<String, Object>) exch.getAttribute("parameters");
				String              action = (String) params.get("action");

				int userId;
				switch (action)
				{
					case ACTION_LOGIN:
						state.addUser(new User(state.generateUserId(), (String) params.get("name")));
						break;

					case ACTION_START:
						state.setState(ServerState.STATE_WORLD);

						// todo: Timeouts in eigene Methode verschieben
						setTimeout(() ->
						{
							// todo: Frage aus GameDesign Daten generieren
							state.setQuestion("Warum macht deine Mudda Passfotos bei GoogleEarth?", "Weil sie ein Weltling ist!");
							state.setState(ServerState.STATE_QUESTION);
						}, 5000);
						break;

					case ACTION_ANSWER:
						userId = Integer.parseInt((String) params.get("user_id"));
						String answer = (String) params.get("answer");

						state.addAnswer(userId, answer);

						if (state.didAllPlayersAnswer())
						{
							state.setState(ServerState.STATE_ANSER_CHECK);
						}

						break;

					case ACTION_ANSWER_CHECK:
						userId = Integer.parseInt((String) params.get("user_id"));
						String answerState = (String) params.get("answer_state");
						state.setAnswerState(userId, answerState);

						break;

					case ACTION_ANSWER_CHECK_SUBMIT:
						state.rewardCorrectAnswers();
						state.resetAnswers();
						state.setState(ServerState.STATE_WORLD);

						// todo: Timeout Methode wiederverwenden
						setTimeout(() ->
						{
							state.setQuestion("PT. II: Warum macht deine Mudda Passfotos bei GoogleEarth", "Weil sie ein Weltling ist!");
							state.setState(ServerState.STATE_QUESTION);
						}, 5000);
						break;
				}
			}

			sendResponse(exch, state.toJSON().toString());
		}

		private void sendResponse(HttpExchange exch, String response) throws IOException
		{
			exch.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
			exch.sendResponseHeaders(200, response.length());
			OutputStream os = exch.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	}

	public static void setTimeout(Runnable runnable, int delay)
	{
		new Thread(() ->
		{
			try
			{
				Thread.sleep(delay);
				runnable.run();
			}
			catch (Exception e)
			{
				System.err.println(e);
			}
		}).start();
	}
}
