package ca.ulaval.glo2003.interfaces.rest;

import static spark.Spark.exception;
import static spark.Spark.get;

import ca.ulaval.glo2003.interfaces.rest.handlers.CatchallExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.jetty.http.HttpStatus;
import spark.Request;
import spark.Response;
import spark.RouteGroup;

public class RootResource implements RouteGroup {

  public static final String ROOT_PATH = "/";

  @Override
  public void addRoutes() {
    get("/hello", this::helloWorld, new ObjectMapper()::writeValueAsString);
    exception(Exception.class, new CatchallExceptionHandler());
  }

  private Object helloWorld(Request request, Response response) throws Exception {
    response.status(HttpStatus.OK_200);
    throw new Exception();
  }
}
