package utils;

import org.apache.commons.dbcp2.BasicDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

//加载配置文件，然后返回DataSource数据源用于数据池的链接
public class PropertiesUtils {
    private static Properties properties = new Properties();
    static {
        InputStream inputStream =PropertiesUtils.class.getClassLoader().getResourceAsStream("db.properties");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static DataSource getDataSource() throws Exception {
        return BasicDataSourceFactory.createDataSource(properties);
    }
}

