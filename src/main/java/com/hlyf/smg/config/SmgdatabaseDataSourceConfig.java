package com.hlyf.smg.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
// 扫描 Mapper 接口并容器管理
@MapperScan(basePackages = SmgdatabaseDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "SmgSqlSessionFactory")
public class SmgdatabaseDataSourceConfig {

    // 精确到 master 目录，以便跟其他数据源隔离
    static final String PACKAGE = "com.hlyf.smg.dao.SMGDao";
    static final String MAPPER_LOCATION = "classpath:mapper/SMGmapper/*.xml";

    @Value("${smgdatabase.datasource.url}")
    private String url;

    @Value("${smgdatabase.datasource.username}")
    private String user;

    @Value("${smgdatabase.datasource.password}")
    private String password;

    @Value("${smgdatabase.datasource.driver-class-name}")
    private String driverClass;

    @Bean(name = "SmgDataSource")
    @Primary
    public DataSource masterDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean(name = "SmgTransactionManager")
    @Primary
    public DataSourceTransactionManager masterTransactionManager() {
        return new DataSourceTransactionManager(masterDataSource());
    }

    @Bean(name = "SmgSqlSessionFactory")
    @Primary
    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("SmgDataSource") DataSource masterDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        //这里是对应数据库字段为空值时返回null字段名
        sessionFactory.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
        sessionFactory.setDataSource(masterDataSource);

        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(SmgdatabaseDataSourceConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }
}