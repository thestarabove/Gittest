package com.egoo.mcc.dbsrv.config.db.config; /**
 * Created by fibo on 16-2-12.
 */

import com.alibaba.druid.pool.DruidDataSource;
import com.egoo.mcc.dbsrv.util.common.DES3;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * com.egoo.mcc.dbsrv.config.db.config
 *
 * MyBatis配置文件
 * @author Silence
 * @date 2014-12-08.
 */
@Configuration
@EnableAutoConfiguration
@PropertySource({"file:${db.fullpath}"})
@MapperScan(basePackages = "com.egoo.mcc.dbsrv.dao",sqlSessionFactoryRef = "primaryFactory")
@Slf4j
public class MyBatisPrimaryConfig implements ApplicationContextAware {
    /**
     * 这个接口可以用于获取spring上下文中定义的所有bean
     */
    @Autowired
    private Environment env;

    private ApplicationContext context;

    private static final String DB_GENESYS_URL = "db.genesys.url";
    private static final String DB_GENESYS_USERNAME = "db.genesys.username";
    private static final String DB_GENESYS_PASSWORD = "db.genesys.password";
    private static final String DB_GENESYS_MAX_ACTIVE = "db.genesys.maxActive";
    private static final String DB_GENESYS_MAX_IDLE = "db.genesys.maxIdle";
    private static final String DB_GENESYS_MAX_WAIT = "db.genesys.maxWait";

    @Value( "${db.driver.class.name}" )
    private static String DB_DRIVER_CLASS_NAME;

    @Bean(name = "primaryDS")
    @Qualifier("primaryDS")
    @Primary
    public DruidDataSource primaryDS() throws Exception{

        final String url = Preconditions.checkNotNull(env.getProperty(DB_GENESYS_URL));
        final String username = Preconditions.checkNotNull(env.getProperty(DB_GENESYS_USERNAME));
        final String password = env.getProperty(DB_GENESYS_PASSWORD);
        //String pass = KeyManagerUtil.decryptInfo(password);
        final int maxActive = Integer.parseInt(env.getProperty(DB_GENESYS_MAX_ACTIVE, "200"));
        final int maxIdle = Integer.parseInt(env.getProperty(DB_GENESYS_MAX_IDLE));
        final int maxWait = Integer.parseInt(env.getProperty(DB_GENESYS_MAX_WAIT));

        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(DB_DRIVER_CLASS_NAME);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(DES3.decode(password));
        dataSource.setMaxActive(maxActive);
        dataSource.setMaxIdle(maxIdle);
        dataSource.setMaxWait(maxWait);

        return dataSource;
    }

    @Bean(name = "primaryFactory")
    @Qualifier("primaryFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactoryPrimary() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        /**
         * 使用之前的datasource
         */
        factoryBean.setDataSource(primaryDS());
        /**
         * 配置了mapper的地址
         */
        factoryBean.setMapperLocations(context.getResources("classpath*:mappers/**/dbsrv/**/**.xml"));
        /**
         * 一个sqlsessionfactory就可以操作mapper里面定义的操作了
         */
        return factoryBean.getObject();
    }



    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}

