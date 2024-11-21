package main;

public interface Query {
    String query(String url);

    String getUrl();
    void setUrl(String url);
    QueryMethod getMethod();

    void setMethod(QueryMethod method);

    public enum QueryMethod {
        GET, POST
    }
}
