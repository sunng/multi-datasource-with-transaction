package self.sunng.multidatasourcewithtransaction.common;

/**
 * Created by sunxiaodong on 2016/11/12.
 */

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@MapperScan(basePackages = "self.sunng.multidatasourcewithtransaction.dao.mapper")
public class MyBatisConfig {

    private Class<? extends DataSource> datasourceType = DruidDataSource.class;

    @Bean(name = "primaryDataSource")
    @ConfigurationProperties(prefix = "datasource.master")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create().type(datasourceType).build();
    }

    @Bean(name = "secondaryDataSource")
    @ConfigurationProperties(prefix = "datasource.slave")
    public DataSource secondaryDataSource() {
        return DataSourceBuilder.create().type(datasourceType).build();
    }

    @Bean(name = "dynamicDataSource")
    @Primary
    @DependsOn("primaryDataSource")
    public DynamicDataSource dynamicDataSource(@Qualifier("primaryDataSource") DataSource primaryDataSource,
                                               @Qualifier("secondaryDataSource") DataSource secondaryDataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DbContextHolder.DbType.MASTER, primaryDataSource);
        targetDataSources.put(DbContextHolder.DbType.SLAVE, secondaryDataSource);

        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(targetDataSources);
        dataSource.setDefaultTargetDataSource(primaryDataSource);

        return dataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DynamicDataSource dynamicDataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        // 指定数据源(这个必须有，否则报错)
        sqlSessionFactoryBean.setDataSource(dynamicDataSource);
        // 下边两句仅仅用于*.xml文件，如果整个持久层操作不需要使用到xml文件的话（只用注解就可以搞定），则不加
        // sqlSessionFactoryBean.setTypeAliasesPackage("self.sunng");
        // sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/**/*.xml"));

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
