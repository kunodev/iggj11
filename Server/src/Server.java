import java.io.*;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Actions.*;
import Entity.Country;
import ServerUtil.ParameterFilter;
import State.ServerState;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class Server
{
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
		private ServerState                 state;
		private Map<String, AbstractAction> actions;

		RequestHandler(ServerState state)
		{
			this.state = state;
			this.actions = new HashMap<>();
			this.actions.put(AbstractAction.ACTION_LOGIN, new ActionLogin());
			this.actions.put(AbstractAction.ACTION_START, new ActionStart());
			this.actions.put(AbstractAction.ACTION_ANSWER, new ActionAnswer());
			this.actions.put(AbstractAction.ACTION_ANSWER_CHECK, new ActionAnswerCheck());
			this.actions.put(AbstractAction.ACTION_ANSWER_CHECK_SUBMIT, new ActionAnswerCheckSubmit());
			this.actions.put(AbstractAction.ACTION_SET_QUESTION, new ActionSetQuestion());
			this.actions.put(AbstractAction.ACTION_CHOSE_COUNTRY, new ActionChoseCountry());

//			state.addCountry(new Country("br", "Brasilien"));
//			state.addCountry(new Country("jp", "Japan"));
//			state.addCountry(new Country("tr", "Türkei"));
//			state.addCountry(new Country("de", "Deutschland"));
			state.addCountry(new Country("fr", "Frankreich"));
			state.addCountry(new Country("ru", "Russland"));
			state.addCountry(new Country("eg", "Ägypten"));
			state.addCountry(new Country("us", "USA"));
		}

		@Override
		public void handle(HttpExchange exch) throws IOException
		{
			if ("post".equalsIgnoreCase(exch.getRequestMethod()))
			{
				Map<String, Object> params = (Map<String, Object>) exch.getAttribute("parameters");
				String              requestedAction = (String) params.get("action");

				AbstractAction action = this.actions.get(requestedAction);
				if (action != null) {
					action.execute(this.actions, state, params);
				}

			}

			sendResponse(exch, state.toJSON().toString());
		}

		private void sendResponse(HttpExchange exch, String response) throws IOException
		{
			exch.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
			exch.sendResponseHeaders(200, response.getBytes().length);
			OutputStream os = exch.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	}

}
