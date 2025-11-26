package Database;

public class DBProperties {
    private static String url;
    private static String user;
    private static String password;
    private static String host;
    private static String port;
    private static String databaseName;

    public static String getUrl() {
        return url;
    }

    public static void setUrl(String url) {
        DBProperties.url = url;
    }

    public static String getUser() {
        return user;
    }

    public static void setUser(String user) {
        DBProperties.user = user;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        DBProperties.password = password;
    }

    public static String getHost() {
        return host;
    }

    public static void setHost(String host) {
        DBProperties.host = host;
    }

    public static String getPort() {
        return port;
    }

    public static void setPort(String port) {
        DBProperties.port = port;
    }

    public static String getDatabaseName() {
        return databaseName;
    }

    public static void setDatabaseName(String databaseName) {
        DBProperties.databaseName = databaseName;
    }
}
