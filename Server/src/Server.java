import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.json.simple.JSONObject;

public class Server
{
	public static void main(String[] args) throws IOException
	{
		HttpServer server = HttpServer.create(new InetSocketAddress(1337), 0);
		server.createContext("/", new GetGameState());
		server.start();
	}

	static class GetGameState implements HttpHandler {
		@Override
		public void handle(HttpExchange t) throws IOException {
			JSONObject obj = new JSONObject();
			obj.put("WTF", "trololo");

			String response = obj.toString();
			t.sendResponseHeaders(200, response.length());
			OutputStream os = t.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	}

}
