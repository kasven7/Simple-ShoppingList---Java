package Database;

import lombok.Getter;

public class DBProperties {
	@Getter
	private static String url;

    @Getter
    private static String user;

    @Getter
	private static String password;

	private static String host;
	private static String port;
	private static String databaseName;

	public static void setUrl(String url) {
		DBProperties.url = url;
	}

	public static void setUser(String user) {
		DBProperties.user = user;
	}

	public static void setPassword(String password) {
		DBProperties.password = password;
	}

	public static void setHost(String host) {
		DBProperties.host = host;
	}

	public static void setPort(String port) {
		DBProperties.port = port;
	}

	public static void setDatabaseName(String databaseName) {
		DBProperties.databaseName = databaseName;
	}
}