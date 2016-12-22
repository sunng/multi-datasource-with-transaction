package self.sunng.multidatasourcewithtransaction.common;

/**
 * Created by sunxiaodong on 2016/11/12.
 */

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@MapperScan(basePackages = "self.sunng.multidatasourcewithtransaction.dao")
public class MyBatisConfig {

    @Autowired
    private Environment env;

    @Bean(name = "primaryDataSource")
    @Qualifier("primaryDataSource")
    @ConfigurationProperties(prefix="spring.datasource.master")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "secondaryDataSource")
    @Qualifier("secondaryDataSource")
    @Primary
    @ConfigurationProperties(prefix="spring.datasource.slave")
    public DataSource secondaryDataSource() {
        return DataSourceBuilder.create().build();
    }

//    @Bean
//    public DataSource masterDataSource() throws Exception {
//        Properties props = new Properties();
//        props.put("driverClassName", env.getProperty("spring.datasource.master.driver-class-name"));
//        props.put("url", env.getProperty("spring.datasource.master.url"));
//        props.put("username", env.getProperty("spring.datasource.master.username"));
//        props.put("password", env.getProperty("spring.datasource.master.password"));
//        return DruidDataSourceFactory.createDataSource(props);
//    }
//
//    @Bean
//    public DataSource slaveDataSource() throws Exception {
//        Properties props = new Properties();
//        props.put("driverClassName", env.getProperty("spring.datasource.slave.driver-class-name"));
//        props.put("url", env.getProperty("spring.datasource.slave.url"));
//        props.put("username", env.getProperty("spring.datasource.slave.username"));
//        props.put("password", env.getProperty("spring.datasource.slave.password"));
//        return DruidDataSourceFactory.createDataSource(props);
//    }

    @Bean
    public DynamicDataSource dynamicDataSource(@Qualifier("primaryDataSource") DataSource masterDataSource,
                                               @Qualifier("secondaryDataSource") DataSource slaveDataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DbContextHolder.DbType.MASTER, masterDataSource);
        targetDataSources.put(DbContextHolder.DbType.SLAVE, slaveDataSource);

        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(targetDataSources);
        dataSource.setDefaultTargetDataSource(masterDataSource);

        return dataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DynamicDataSource dynamicDataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dynamicDataSource);// 指定数据源(这个必须有，否则报错)
        // 下边两句仅仅用于*.xml文件，如果整个持久层操作不需要使用到xml文件的话（只用注解就可以搞定），则不加
//        sqlSessionFactoryBean.setTypeAliasesPackage("self.sunng");// 指定基包
//        sqlSessionFactoryBean.setMapperLocations(
//                new PathMatchingResourcePatternResolver().getResources("classpath:mapper/**/*.xml"));//

        return sqlSessionFactoryBean.getObject();
    }

    /**
     * 配置事务管理器
     */
    @Bean
    public DataSourceTransactionManager transactionManager(DynamicDataSource dataSource) throws Exception {
        return new DataSourceTransactionManager(dataSource);
    }
}
