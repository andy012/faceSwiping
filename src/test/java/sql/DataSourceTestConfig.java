package sql;

import com.face.init.config.DataSourceConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by sks on 2015/11/9.
 */
@Configuration
@Import({DataSourceConfig.class})
@PropertySource("classpath:application.properties")
public class DataSourceTestConfig {

}
