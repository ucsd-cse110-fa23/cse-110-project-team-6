package pantrypal;

import com.sun.net.httpserver.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class PPServer {

    // initialize server port and hostname
    private static final int SERVER_PORT = 8100;
    private static final String SERVER_HOSTNAME = "localhost";

    public PPServer(ArrayList<Recipe> recipes) throws IOException {
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

        // create a map to store data
        // create a server
        HttpServer server = HttpServer.create(
                new InetSocketAddress(SERVER_HOSTNAME, SERVER_PORT),
                0);

        // create a context to get the request to the server
        server.createContext("/", new RequestHandler(recipes));
        // set the executor
        server.setExecutor(threadPoolExecutor);
      
        
        server.start();
        System.out.println("Server started on port " + SERVER_PORT);
    }
}