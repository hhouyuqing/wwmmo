package au.com.codeka.warworlds.client.net;

import com.google.common.io.ByteStreams;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * Simple wrapper around {@link HttpURLConnection} that lets us make HTTP requests more
 * easily.
 */
public class HttpRequest {
  public enum Method {
    GET,
    POST,
    PUT,
    DELETE
  }

  private HttpURLConnection conn;
  private IOException exception;
  private byte[] body;

  private HttpRequest(Builder builder) {
    try {
      URL url = new URL(builder.url);
      conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod(builder.method.toString());
      for (String key : builder.headers.keySet()) {
        conn.setRequestProperty(key, builder.headers.get(key));
      }
      if (builder.body != null) {
        conn.setRequestProperty("Content-Length", Integer.toString(builder.body.length));
        conn.getOutputStream().write(builder.body);
      }
    } catch (IOException e) {
      this.exception = e;
    }
  }

  public int getResponseCode() {
    if (exception != null) {
      return -1;
    }

    try {
      return conn.getResponseCode();
    } catch(IOException e) {
      exception = e;
      return -1;
    }
  }

  public IOException getException() {
    return exception;
  }

  public byte[] getBody() {
    if (exception != null) {
      return null;
    }

    if (body == null) {
      try {
        body = ByteStreams.toByteArray(conn.getInputStream());
      } catch (IOException e) {
        exception = e;
        return null;
      }
    }

    return body;
  }

  public static class Builder {
    private String url;
    private Method method;
    private HashMap<String, String> headers;
    private byte[] body;

    public Builder() {
      method = Method.GET;
      headers = new HashMap<>();
    }

    public Builder url(String url) {
      this.url = url;
      return this;
    }

    public Builder method(Method method) {
      this.method = method;
      return this;
    }

    public Builder header(String name, String value) {
      headers.put(name, value);
      return this;
    }

    public Builder body(byte[] body) {
      this.body = body;
      return this;
    }

    public HttpRequest build() {
      return new HttpRequest(this);
    }
  }
}
